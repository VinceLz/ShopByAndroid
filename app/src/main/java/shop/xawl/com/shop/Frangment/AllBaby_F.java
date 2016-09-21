package shop.xawl.com.shop.Frangment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shop.xawl.com.shop.Activity.BuyNowActivity;
import shop.xawl.com.shop.Activity.GoodsInfoActivity;
import shop.xawl.com.shop.Model.Item_cart;
import shop.xawl.com.shop.MyApplication;
import shop.xawl.com.shop.R;
import shop.xawl.com.shop.Adapter.Adapter_ListView_cart;
import shop.xawl.com.shop.Adapter.IBtnCallListener;
import shop.xawl.com.shop.http.HttpUtil;

@SuppressLint("ValidFragment")
public class AllBaby_F extends Fragment implements IBtnCallListener, Adapter_ListView_cart.onCheckedChanged, OnClickListener {
    IBtnCallListener btnCallListener;
    private TextView tv_goShop, tv_cart_Allprice, tv_cart_buy_Ordel, tv_cart_del;
    private LinearLayout ll_cart;
    private ListView listView_cart;
    private CheckBox cb_cart_all;
    private Adapter_ListView_cart adapter;
    private String str_del = "结算(0)";
    private boolean[] is_choice;
    private List<Item_cart> list;
    private List<Item_cart> modelist = new ArrayList<>();
    private float Allprice_cart = 0;

    public AllBaby_F(String del) {
        str_del = del;
    }

    public AllBaby_F() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.cart_all_f, null);
        list = new ArrayList<>();
        initView(view);
        getCartData();
        return view;
    }

    private void initdata() {
        is_choice = new boolean[list.size()]; //获取是否购物车有数据后期可以使用网络请求
        if (list != null && list.size() != 0) {
            if (adapter == null) {
                adapter = new Adapter_ListView_cart(getActivity(), list);
                adapter.setOnCheckedChanged(this);
                listView_cart.setAdapter(adapter);
                ll_cart.setVisibility(View.GONE);
            } else {
                adapter.notifyDataSetChanged();
            }
        } else { //判断是否有购物信息，显示为空提示语
            ll_cart.setVisibility(View.VISIBLE);
        }
    }

    private void initView(View view) {
        tv_cart_del = (TextView) view.findViewById(R.id.tv_cart_del);
        tv_goShop = (TextView) view.findViewById(R.id.tv_goShop);
        tv_cart_Allprice = (TextView) view.findViewById(R.id.tv_cart_Allprice);
        tv_cart_buy_Ordel = (TextView) view.findViewById(R.id.tv_cart_buy_or_del);
        tv_cart_buy_Ordel.setText(str_del);
        cb_cart_all = (CheckBox) view.findViewById(R.id.cb_cart_all);
        tv_cart_del.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除事件 支持多个删除
                String cid = "";
                boolean[] is_choice_copy = is_choice;
                if (list.size() != 0) {
                    for (int i = is_choice_copy.length - 1; i >= 0; i--) {
                        if (is_choice_copy[i]) {
                            ((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.cb_choice)).setChecked(false);
                            if (i == 0) {
                                cid = cid + list.get(i).getCartitem_id();
                            } else {
                                cid = cid + list.get(i).getCartitem_id() + ",";
                            }
                        }
                    }
                }
                delete(cid, is_choice_copy);
            }
        });
        cb_cart_all.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {  //设置选中购物业务
                int isChoice_all = 0;
                if (arg1) {
                    for (int i = 0; i < list.size(); i++) {
                        ((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.cb_choice)).setChecked(true);
                    }
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        if (((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.cb_choice)).isChecked()) {
                            isChoice_all += 1;
                        }
                    }
                    if (isChoice_all == list.size()) {
                        for (int i = 0; i < list.size(); i++) {
                            ((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.cb_choice)).setChecked(false);
                        }
                    }
                }
            }
        });


        ll_cart = (LinearLayout) view.findViewById(R.id.ll_cart);
        listView_cart = (ListView) view.findViewById(R.id.listView_cart);
        listView_cart.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //启动详情
                Intent intent = new Intent(getActivity(), GoodsInfoActivity.class);
                intent.putExtra("gid", (int) (list.get(arg2)).getGid());
                startActivity(intent);
            }
        });

        tv_cart_buy_Ordel.setOnClickListener(this);
        tv_goShop.setOnClickListener(this);
    }

    private void delete(String cid, final boolean[] is_choice_copy) {
        String url = HttpUtil.getApi_path("cartitem/delete") + "?cartitem_id=" + cid;
        HttpUtil.get(url, new HttpUtil.callBlack() {

            @Override
            public void succcess(String code) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MyApplication.showToast("删除成功");
                        Log.d("#######2", is_choice_copy.length + "list size" + list.size());
                        for (int i = is_choice_copy.length - 1; i >= 0; i--) {
                            if (is_choice_copy[i]) {
                                //之前选中的
                                list.remove(i);
                                Log.d("#######2", is_choice_copy.length + "list size" + list.size());
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
    public void onAttach(Activity activity) {
        btnCallListener = (IBtnCallListener) activity;

        super.onAttach(activity);
    }

    @Override
    public void transferMsg() {

    }

    @Override
    public void getChoiceData(int position, boolean isChoice) {

        if (isChoice) {
            if (list.size() != 0) {
                Allprice_cart += list.get(position).getGprice();
            }
        } else {
            if (list.size() != 0) {
                Allprice_cart -= list.get(position).getGprice();
            }
        }
        int num_choice = 0;
        for (int i = 0; i < list.size(); i++) {
            if (null != listView_cart.getChildAt(i) && ((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.cb_choice)).isChecked()) {
                num_choice += 1;
                is_choice[i] = true;
            }
        }
        if (num_choice == list.size()) {
            cb_cart_all.setChecked(true);
        } else {
            cb_cart_all.setChecked(false);
        }

        tv_cart_Allprice.setText("合计:" + Allprice_cart + "");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_goShop:
                btnCallListener.transferMsg();
                break;
            case R.id.tv_cart_buy_or_del:
                boolean[] is_choice_copy = is_choice;
                modelist.clear();
                if (!tv_cart_buy_Ordel.getText().toString().equals("结算")) {
                    if (list.size() != 0) {
                        for (int i = is_choice_copy.length - 1; i >= 0; i--) {
                            if (is_choice_copy[i]) {
                                ((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.cb_choice)).setChecked(false);
                                modelist.add(list.get(i));
                            }
                        }
                    }
                    is_choice = new boolean[list.size()];
                    if (modelist.size() > 0) {
                        Intent intent = new Intent(getActivity(), BuyNowActivity.class);
                        intent.putExtra("buy_list", (Serializable) modelist);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "您没有选择", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "您没有选择", Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }

    }

//    public static boolean[] deleteByIndex(boolean[] array, int index) {
//        boolean[] newArray = new boolean[array.length - 1];
//        for (int i = 0; i < newArray.length; i++) {
//            if (i < index) {
//                newArray[i] = array[i];
//            } else {
//                newArray[i] = array[i + 1];
//            }
//        }
//        return newArray;
//    }

    public void getCartData() {
        Map map = new HashMap();
        map.put("uid", MyApplication.user.getUid());
        String url = HttpUtil.getApi_path("cartitem/get");
        HttpUtil.post(url, map, new HttpUtil.callBlack() {
            @Override
            public void succcess(String code) {
                JSONObject object = JSON.parseObject(code);

                List<Item_cart> cart_list = JSON.parseArray(object.getString("cartitem"), Item_cart.class);
                list.clear();
                list.addAll(cart_list);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initdata();
                    }
                });
            }

            @Override
            public void fail(String code) {

            }

            @Override
            public void err() {

            }
        });
    }

}
