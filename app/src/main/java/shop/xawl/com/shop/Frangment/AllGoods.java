package shop.xawl.com.shop.Frangment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import shop.xawl.com.shop.Activity.GoodsInfoActivity;
import shop.xawl.com.shop.Activity.PostInfoActivity;
import shop.xawl.com.shop.Adapter.Adapter_ListView_cart;
import shop.xawl.com.shop.Adapter.Adapter_ListView_myGoods;
import shop.xawl.com.shop.Adapter.Adapter_ListView_myPost;
import shop.xawl.com.shop.Adapter.IBtnCallListener;
import shop.xawl.com.shop.Model.Item_provide;
import shop.xawl.com.shop.Model.item_request;
import shop.xawl.com.shop.MyApplication;
import shop.xawl.com.shop.R;
import shop.xawl.com.shop.http.HttpUtil;

/**
 * Created by Administrator on 2016/9/16.
 */
public class AllGoods extends Fragment implements View.OnClickListener, Adapter_ListView_cart.onCheckedChanged {
    private boolean[] is_choice;
    IBtnCallListener btnCallListener;
    private List<Item_provide> list;
    private Adapter_ListView_myGoods adapter;
    private TextView post_del;
    private CheckBox checkBox;
    private LinearLayout ll_cart;
    private ListView listView_cart;
    private String[] Allprice_cart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.mygoods_main_all, null);
        list = new ArrayList<>();
        initView(inflate);
        getCartData();
        return inflate;
    }

    private void getCartData() {
        //获取自己的帖子
        String url = HttpUtil.getApi_path("goods/mygoods");
        HttpUtil.post(url, null, new HttpUtil.callBlack() {
            @Override
            public void succcess(String code) {
                JSONObject object = JSON.parseObject(code);
                List<Item_provide> goods = JSON.parseArray(object.getString("goods"), Item_provide.class);
                list.clear();
                list.addAll(goods);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initdata();
                    }
                });
            }

            @Override
            public void fail(String code) {
                MyApplication.showToast("出错了");
            }

            @Override
            public void err() {
                MyApplication.showToast("出错了");
            }
        });
    }

    private void initdata() {
        Log.d("#########", list.size() + "集合大小");
        is_choice = new boolean[list.size()]; //获取是否购物车有数据后期可以使用网络请求
        if (list != null && list.size() != 0) {
            if (adapter == null) {
                adapter = new Adapter_ListView_myGoods(getActivity(), list);
                listView_cart.setAdapter(adapter);
                adapter.setOnCheckedChanged(this);
                ll_cart.setVisibility(View.GONE);
            } else {
                adapter.notifyDataSetChanged();
            }
        } else { //判断是否有购物信息，显示为空提示语
            ll_cart.setVisibility(View.VISIBLE);
        }
    }

    private void initView(View view) {
        ll_cart = (LinearLayout) view.findViewById(R.id.ll_cart1);
        post_del = (TextView) view.findViewById(R.id.post_del);
        checkBox = (CheckBox) view.findViewById(R.id.post_all);
        listView_cart = (ListView) view.findViewById(R.id.post_listview);
        listView_cart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), GoodsInfoActivity.class);
                intent.putExtra("gid", list.get(i).getGid());
                getActivity().startActivity(intent);
            }
        });
        post_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除事件
                String gid = "";
                boolean[] is_choice_copy = is_choice;
                if (list.size() != 0) {
                    for (int i = is_choice_copy.length - 1; i >= 0; i--) {
                        if (is_choice_copy[i]) {
                            ((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.post_choice)).setChecked(false);
                            if (i == 0) {
                                gid = gid + list.get(i).getGid();
                            } else {
                                gid = gid + list.get(i).getGid() + ",";
                            }
                        }
                    }
                }
                delete(gid, is_choice_copy);
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {  //设置选中购物业务
                int isChoice_all = 0;
                if (arg1) {
                    for (int i = 0; i < list.size(); i++) {
                        ((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.post_choice)).setChecked(true);
                    }
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        if (((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.post_choice)).isChecked()) {
                            isChoice_all += 1;
                        }
                    }
                    if (isChoice_all == list.size()) {
                        for (int i = 0; i < list.size(); i++) {
                            ((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.post_choice)).setChecked(false);
                        }
                    }
                }
            }
        });
    }

    private void delete(String arrgid, final boolean[] is_choice_copy) {
        String url = HttpUtil.getApi_path("goods/delete") + "?gid=" + arrgid;
        HttpUtil.get(url, new HttpUtil.callBlack() {
            @Override
            public void succcess(String code) {
                //删除成功
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MyApplication.showToast("删除成功");
                        for (int i = is_choice_copy.length - 1; i >= 0; i--) {
                            if (is_choice_copy[i]) {
                                //之前选中的
                                list.remove(i);
                            }
                        }
                        if (list.size() == 0) {
                            ll_cart.setVisibility(View.VISIBLE);
                        } else {
                            ll_cart.setVisibility(View.GONE);
                        }
                        adapter.notifyDataSetChanged();
                        is_choice = new boolean[list.size()];
                    }
                });
                //删除本地的
            }

            @Override
            public void fail(String code) {
                //删除失败
                MyApplication.showToast("删除失败");
            }

            @Override
            public void err() {
                MyApplication.showToast("出措了");
                //删除失败
            }
        }, false);
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void getChoiceData(int position, boolean isChoice) {
        int num_choice = 0;
        for (int i = 0; i < list.size(); i++) {
            if (null != listView_cart.getChildAt(i) && ((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.post_choice)).isChecked()) {
                num_choice += 1;
                is_choice[i] = true;
            }
        }
        if (num_choice == list.size()) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
    }
}
