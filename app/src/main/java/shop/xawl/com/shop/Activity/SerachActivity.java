package shop.xawl.com.shop.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import shop.xawl.com.shop.Model.Item_provide;
import shop.xawl.com.shop.R;
import shop.xawl.com.shop.Adapter.Adapter_ListView_ware;
import shop.xawl.com.shop.Adapter.xListview.XListView;
import shop.xawl.com.shop.http.GetHttp;
import shop.xawl.com.shop.http.Url;


@SuppressLint("SimpleDateFormat")
public class SerachActivity extends Activity implements OnTouchListener, XListView.IXListViewListener {

    private XListView listView;

    private LinearLayout ll_search;

    private ImageView iv_back;
    private AnimationSet animationSet;
    private String cname = "";
    private int cid;
    float fist_down_Y = 0;
    public static int pageNo; // 当前页
    public static int totalPage;  //总页数
    public static int totalNum;//总的记录数
    private int pageIndex = 0;
    private TextView tvleve;
    private LinearLayout Lleve;
    private Button seach;
    private List<Item_provide> arrayList = null;
    private Adapter_ListView_ware adapter_listView_ware;
    private View employ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serach);
        cname = getIntent().getStringExtra("cname") == "" ? getIntent().getStringExtra("cname") : "类目";
        cid = getIntent().getIntExtra("cid", 0);
        initView();
    }

    private void initView() {
        employ = View.inflate(SerachActivity.this, R.layout.view_error_layout, null);
        arrayList = new ArrayList<>();
        Lleve = (LinearLayout) findViewById(R.id.serach_leve);
        tvleve = (TextView) findViewById(R.id.serach_leve1);
        tvleve.setText(cname);
        ll_search = (LinearLayout) findViewById(R.id.ll_choice1);
        iv_back = (ImageView) findViewById(R.id.iv_back1);
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
        listView = (XListView) findViewById(R.id.listView_ware1);
        listView.setOnTouchListener(this);
        listView.setXListViewListener(this);
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.setHeadSize(ll_search.getHeight());
        //  listView.setEmptyView(employ);
        Lleve.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(SerachActivity.this, CategoryActivity.class), 100);
            }
        });
        new WareTask().execute();
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onTouch(View arg0, MotionEvent event) {
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                fist_down_Y = y;
                break;
            case MotionEvent.ACTION_MOVE:

                if (fist_down_Y - y > 250 && ll_search.isShown()) {
                    if (animationSet != null) {
                        animationSet = null;
                    }
                    animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.up_out);
                    ll_search.startAnimation(animationSet);
                    ll_search.setY(-100);
                    ll_search.setVisibility(View.GONE);
                }

                if (y - fist_down_Y > 250 && !ll_search.isShown()) {
                    if (animationSet != null) {
                        animationSet = null;
                    }
                    animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.down_in);
                    ll_search.startAnimation(animationSet);
                    ll_search.setY(0);
                    ll_search.setVisibility(View.VISIBLE);
                }
                break;

        }
        return false;

    }

    private class WareTask extends AsyncTask<Void, Void, List<Item_provide>> {

        ProgressDialog dialog = null;

        @Override
        protected void onPreExecute() {
            ((ViewGroup) listView.getParent()).removeView(employ);
            if (dialog == null) {
                dialog = ProgressDialog.show(SerachActivity.this, "", "请稍后....");
                dialog.show();
            }
        }

        @Override
        protected List<Item_provide> doInBackground(Void... arg0) {
            String url = "";
            //关键字为空，默认为全站搜索
            if (pageIndex == 0) {
                url = Url.getService("/front/goods/list.action?pageNo=1&params['cid']=" + cid);
            } else {
                url = Url.getService("/front/goods/list.action?pageNo=" + (pageIndex + 1) + "&params['cid']=" + cid);
            }
            //最后一页
            String json = GetHttp.getStringByUrlOnce(url); //从服务器中获取json数据
            if (!TextUtils.isEmpty(json)) {
                JSONObject object = JSON.parseObject(json);
                List<Item_provide> item_provides = JSON.parseArray(object.getJSONObject("page").getString("results"), Item_provide.class);
                arrayList.clear();
                arrayList.addAll(item_provides);
                pageNo = object.getJSONObject("page").getInteger("pageNo");
                totalPage = object.getJSONObject("page").getInteger("totalPage");
                return arrayList;
            }
            return null;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void onPostExecute(List<Item_provide> result) {

            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }


            if (result != null && result.size() > 0) {
                if (adapter_listView_ware == null) {
                    adapter_listView_ware = new Adapter_ListView_ware(SerachActivity.this, arrayList);
                    listView.setAdapter(adapter_listView_ware);
                } else {
                    adapter_listView_ware.notifyDataSetChanged();
                }
                listView.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        Intent intent = new Intent(SerachActivity.this, GoodsInfoActivity.class);
                        intent.putExtra("gid", (int) (arrayList.get(position - 1)).getGid());
                        startActivity(intent);
                    }
                });

            } else {
                //设置空集合的视图
                //没有数据
                ((ViewGroup) listView.getParent()).addView(employ);
                return;
            }
            if (totalPage == (pageIndex + 1)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() { //更新ui
                        listView.setPullLoadEnable(false);
                    }
                });
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() { //更新ui
                        listView.setPullLoadEnable(true);
                    }
                });

            }

            onLoad();

        }

    }


    @Override
    public void onRefresh() {
        pageIndex = 0;
        arrayList.clear();
        new WareTask().execute();
        onLoad();

    }

    @Override
    public void onLoadMore() {
        pageIndex += 1;
//        if (pageIndex >= 4) {
//            Toast.makeText(this, "请稍后", Toast.LENGTH_SHORT).show();
//            onLoad();
//            return;
//        }
        new WareTask().execute();

    }


    private void onLoad() {
        listView.stopRefresh();
        listView.stopLoadMore();
        listView.setRefreshTime(getCurrentTime(System.currentTimeMillis()));
    }


    public static SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");

    public static String getCurrentTime(long time) {
        if (0 == time) {
            return "";
        }

        return mDateFormat.format(new Date(time));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 10) {
            cid = data.getIntExtra("cid", 0);
            cname = data.getStringExtra("cname");
            tvleve.setText(cname);
            new WareTask().execute();
        }
    }
}
