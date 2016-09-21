package shop.xawl.com.shop.Activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import shop.xawl.com.shop.Model.Item_provide;
import shop.xawl.com.shop.MyApplication;
import shop.xawl.com.shop.R;
import shop.xawl.com.shop.Adapter.Adapter_ListView_ware;
import shop.xawl.com.shop.Adapter.xListview.XListView;
import shop.xawl.com.shop.http.GetHttp;
import shop.xawl.com.shop.http.Url;


@SuppressLint("SimpleDateFormat")
public class WareActivity extends Activity implements OnTouchListener, XListView.IXListViewListener {

    private XListView listView;

    private LinearLayout ll_search;

    private ImageView iv_back;
    @SuppressWarnings("unused")
    private EditText ed_search;

    private AnimationSet animationSet;

    private String keyWord; //关键字

    float fist_down_Y = 0;

    private Adapter_ListView_ware adapter_listView_ware;
    public static int pageNo; // 当前页
    public static int totalPage;  //总页数
    public static int totalNum;//总的记录数
    private int pageIndex = 0;

    private Button seach;
    private HashMap<String, Object> hashMap;

    private List<Item_provide> arrayList = null;
    private View employ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ware_a);
        initView();

    }

    private void initView() {
        employ = View.inflate(WareActivity.this, R.layout.view_error_layout, null);
        arrayList = new ArrayList<>();
        ll_search = (LinearLayout) findViewById(R.id.ll_choice);
        ed_search = (EditText) findViewById(R.id.ed_Searchware);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
        seach = (Button) findViewById(R.id.seach);
        listView = (XListView) findViewById(R.id.listView_ware);
        listView.setOnTouchListener(this);
        listView.setXListViewListener(this);
        listView.setHeadSize(ll_search.getHeight());
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        seach.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) { //点击搜索
                //1 保存相关的关键字
                keyWord = ed_search.getText().toString();
                new WareTask().execute();
            }
        });
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
                dialog = ProgressDialog.show(WareActivity.this, "", "请稍后....");
                dialog.show();
            }
        }

        @Override
        protected List<Item_provide> doInBackground(Void... arg0) {
            String url = "";
            String keyWord1 = "";
            try {
                keyWord1 = URLEncoder.encode(keyWord, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(keyWord)) {
                //关键字为空，默认为全站搜索
                if (pageIndex == 0) {
                    url = Url.getService("/front/goods/list.action?pageNo=1");
                } else {
                    url = Url.getService("/front/goods/list.action?pageNo=" + (pageIndex + 1));
                }
            } else {
                if (pageIndex == 0) {
                    url = Url.getService("/front/goods/list.action?pageNo=1&params['gname']=" + keyWord1);
                } else {
                    url = Url.getService("/front/goods/list.action?pageNo=" + (pageIndex + 1) + "&params['gname']=" + keyWord1);
                }
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


            if (result != null && result.size() != 0) {

                //		arrayList.addAll((Collection<? extends HashMap<String, Object>>) result.get("data"));
                if (adapter_listView_ware == null) {
                    adapter_listView_ware = new Adapter_ListView_ware(WareActivity.this, arrayList);
                    listView.setAdapter(adapter_listView_ware);
                } else {
                    adapter_listView_ware.notifyDataSetChanged();
                }
                listView.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        Intent intent = new Intent(WareActivity.this, GoodsInfoActivity.class);
                        intent.putExtra("gid", (arrayList.get(position - 1)).getGid());
                        Log.v("#####gid", "" + (arrayList.get(position - 1)).getGid());
                        startActivity(intent);
                    }
                });

            } else {
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


}
