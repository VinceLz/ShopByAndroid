package shop.xawl.com.shop.Frangment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import shop.xawl.com.shop.Activity.MyGoodsActivity;
import shop.xawl.com.shop.Activity.MyPostActivity;
import shop.xawl.com.shop.Adapter.MyImage;
import shop.xawl.com.shop.MyApplication;
import shop.xawl.com.shop.Activity.OrderStautsActivity;
import shop.xawl.com.shop.R;
import shop.xawl.com.shop.Adapter.Adapter_GridView;
import shop.xawl.com.shop.Adapter.MyGridView;
import shop.xawl.com.shop.http.HttpUtil;

/**
 * Created by Administrator on 2016/8/22.
 */
public class UserInfo extends Fragment implements View.OnClickListener {
    private MyGridView myGridView;
    private Adapter_GridView adapter_gridView;//適配器
    private int[] views = {R.mipmap.menu_guide_1, R.mipmap.menu_guide_1, R.mipmap.menu_guide_1, R.mipmap.menu_guide_1, R.mipmap.menu_guide_1};
    private String[] tname = {"待付款", "已支付", "退款中", "待确认", "已完成"};
    private String[] bname = {"待处理", "退款", "已处理", "已完成"};
    private TextView tv_phone;
    private TextView tv_uname;
    private TextView tv_exit;
    private MyImage myImage;
    private int[] stats_arry;
    private int[] stats_business;
    private LinearLayout mypost;
    private Adapter_GridView adapter_gridView_businessOrder;
    private MyGridView myGridView_business;
    private LinearLayout businessCate;
    private BroadcastReceiver orderRefushBoradcast;
    private LinearLayout goods;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.user_f, null);
        initView(view); //对girdView进行数据填充
        initDate();
        return view;
    }

    public void initView(View view) {
        stats_arry = new int[4];
        stats_business = new int[3];
        mypost = (LinearLayout) view.findViewById(R.id.my_post);
        goods = (LinearLayout) view.findViewById(R.id.my_business);
        businessCate = (LinearLayout) view.findViewById(R.id.business_order);
        myImage = (MyImage) view.findViewById(R.id.my_image);
        tv_uname = (TextView) view.findViewById(R.id.user_uname);
        tv_phone = (TextView) view.findViewById(R.id.user_phone);
        tv_exit = (TextView) view.findViewById(R.id.user_exit);
        if (MyApplication.isLogin()) {
            tv_phone.setText(MyApplication.user.getUphone());
            tv_uname.setText(MyApplication.user.getUname());
            if (!MyApplication.user.getUimage().isEmpty()) {
                HttpUtil.picasso.with(getActivity()).load(HttpUtil.getImage_path(MyApplication.user.getUimage())).into(myImage);
            }
        }
        mypost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //启动我的需求页面
                Intent intent = new Intent(getActivity(), MyPostActivity.class);
                getActivity().startActivity(intent);
            }
        });
        tv_exit.setOnClickListener(this);
        myGridView = (MyGridView) view.findViewById(R.id.gridView_user);
        myGridView_business = (MyGridView) view.findViewById(R.id.gridView_business);
        adapter_gridView = new Adapter_GridView(getActivity(), views, tname, stats_arry);
        if (MyApplication.isBusiness) {
            goods.setVisibility(View.VISIBLE);
            businessCate.setVisibility(View.VISIBLE);
            adapter_gridView_businessOrder = new Adapter_GridView(getActivity(), views, bname, stats_business);
            myGridView_business.setAdapter(adapter_gridView_businessOrder);
            myGridView_business.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(), OrderStautsActivity.class);
                    intent.putExtra("status", i);
                    intent.putExtra("business", true);  //1是商家  0 是普通用户
                    startActivity(intent);
                }
            });
            goods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().startActivity(new Intent(getActivity(), MyGoodsActivity.class));
                }
            });
        } else {
            businessCate.setVisibility(View.GONE);
            goods.setVisibility(View.GONE);

        }
        myGridView.setAdapter(adapter_gridView);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //點擊事件
                Intent intent = new Intent(getActivity(), OrderStautsActivity.class);
                intent.putExtra("status", i - 1);
                startActivity(intent);
            }
        });
        IntentFilter filter = new IntentFilter();
        filter.addAction("order.refush");
        orderRefushBoradcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("##########123", "监听到了");
                initDate();
            }
        };
        getActivity().registerReceiver(orderRefushBoradcast, filter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_exit:
                break;
        }
    }

    public void initDate() {
        //重写加载数
        String url = HttpUtil.getApi_path("order/statusList");
        Log.d("#######1", url + "");
        HttpUtil.post(url, null, new HttpUtil.callBlack() {
            @Override
            public void succcess(String code) {

                Log.d("#######4", code + "------");
                JSONObject object = JSON.parseObject(code);
                JSONObject arry = object.getJSONObject("status_array");
                JSONObject array_business = object.getJSONObject("status_business");
                stats_arry[0] = arry.getInteger("ORDER_NO_PLAY");
                stats_arry[1] = arry.getInteger("ORDER_YES_PLAY");
                stats_arry[2] = arry.getInteger("ORDER_FAIL");
                stats_arry[3] = arry.getInteger("ORDER_DEAL");
                if (array_business != null) {
                    stats_business[0] = array_business.getInteger("ORDER_YES_PLAY");
                    stats_business[1] = array_business.getInteger("ORDER_FAIL");
                    stats_business[2] = array_business.getInteger("ORDER_DEAL");
                }
                Log.d("#######4", stats_business[2] + "---" + stats_arry[3]);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter_gridView.notifyDataSetChanged();
                        adapter_gridView_businessOrder.notifyDataSetChanged();
                    }
                });

            }

            @Override
            public void fail(String code) {
                Log.d("#######1", "----1");
            }

            @Override
            public void err() {
                Log.d("#######1", "----3");
            }
        });
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(orderRefushBoradcast);
        super.onDestroy();
    }
}

