package shop.xawl.com.shop.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shop.xawl.com.shop.Activity.GoodsInfoActivity;
import shop.xawl.com.shop.BaseApplication;
import shop.xawl.com.shop.Frangment.UserInfo;
import shop.xawl.com.shop.Model.Item_provide;
import shop.xawl.com.shop.Model.Order;
import shop.xawl.com.shop.MyApplication;
import shop.xawl.com.shop.R;
import shop.xawl.com.shop.http.HttpUtil;


public class Adapter_ListView_OrderList extends BaseAdapter {
    private Context context;
    private List<Order> arrayList = null;
    boolean isbusiness;
    static boolean issuccess;
    private Handler mhandle;

    public Adapter_ListView_OrderList(final Context context, final List<Order> arrayList, boolean isbusiness) {
        this.context = context;
        this.arrayList = arrayList;
        this.isbusiness = isbusiness;
        mhandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    int i = (int) msg.obj;
                    arrayList.remove(i);
                    notifyDataSetChanged();
                    //更新usrinfo 页面
                    //发送广播
                    context.sendBroadcast(new Intent().setAction("order.refush"));
                }
            }
        };
    }

    public Adapter_ListView_OrderList(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return (arrayList != null && arrayList.size() == 0) ? 0 : arrayList.size();
    }

    @Override
    public Order getItem(int arg0) {

        return (Order) arrayList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(final int position, View currentView, ViewGroup arg2) {
        HolderView holderView = null;
        if (currentView == null) {
            holderView = new HolderView();
            currentView = LayoutInflater.from(context).inflate(R.layout.adapter_listview_orderlist, null);
            holderView.number = (TextView) currentView.findViewById(R.id.tv_order_number); //订单号
            holderView.price = (TextView) currentView.findViewById(R.id.tv_order_sum); //价格
            holderView.bname = (TextView) currentView.findViewById(R.id.tv_order_Bname); //商家名
            holderView.bphone = (TextView) currentView.findViewById(R.id.tv_order_phone); //商家电话
            holderView.stauts = (TextView) currentView.findViewById(R.id.tv_order_stauts); //订单状态
            holderView.data = (TextView) currentView.findViewById(R.id.tv_order_data); //订单时间
            holderView.myListView = (MyListView) currentView.findViewById(R.id.tv_order_list);
            holderView.oper = (TextView) currentView.findViewById(R.id.oper);
            holderView.call = (TextView) currentView.findViewById(R.id.call);
            currentView.setTag(holderView);
        } else {
            holderView = (HolderView) currentView.getTag();
        }
        final int i = arrayList.get(position).getStatus();
        String stauts = "";
        String oper = "";
        String phone = "";
        String name = "";
        final String url = HttpUtil.getApi_path("order/updateOrder");
        if (isbusiness) {
            name = arrayList.get(position).getUname();
            phone = arrayList.get(position).getUphone(); //用户的手机
            switch (i) {
                case 0:
                    stauts = "待处理";
                    oper = "处理";
                    break;
                case 1:
                    stauts = "退款中";
                    oper = "退款";
                    break;
                case 2:
                    stauts = "已处理";
                    holderView.oper.setVisibility(View.GONE);
                    break;
                case 3:
                    stauts = "完成";
                    holderView.oper.setVisibility(View.GONE);
                    break;
            }
        } else {
            name = arrayList.get(position).getBname();
            phone = arrayList.get(position).getBphone(); //商家的手机
            switch (i) {
                case -1:
                    stauts = "未付款";
                    oper = "点击付款";
                    break;
                case 0:
                    stauts = "已付款";
                    oper = "退款";
                    break;
                case 1:
                    stauts = "退款中";
                    oper = "取消退款";
                    break;
                case 2:
                    Log.d("######3", "我中毒了1");
                    stauts = "待确认";
                    oper = "确认";
                    break;
                case 3:
                    Log.d("######3", "我中毒了2");
                    stauts = "完成";
                    holderView.oper.setVisibility(View.GONE);
                    break;
            }
        }
        holderView.number.setText(arrayList.get(position).getOid());
        holderView.price.setText("" + arrayList.get(position).getCurrentprice());
        holderView.bname.setText(name);
        holderView.bphone.setText(phone);
        holderView.stauts.setText(stauts);
        holderView.data.setText(arrayList.get(position).getOrdertime());
        holderView.oper.setText(oper);
        final String finalPhone = phone;
        holderView.oper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //对应的事件
                String oid = arrayList.get(position).getOid();
                if (isbusiness) {

                } else {
                    switch (i) {

                        case -1:  //去付款
                            goTO(url, 0, position, oid);
                            break;
                        case 0:  //去退款
                            goTO(url, 1, position, oid);
                            break;
                        case 1:
                            goTO(url, 0, position, oid);
                            break;
                        case 2:
                            goTO(url, 3, position, oid);
                            break;
                    }
                }
            }
        });
        holderView.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //拨打电话
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + finalPhone));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                context.startActivity(intent);
            }
        });
        List<Item_provide> item_provides = JSON.parseArray(arrayList.get(position).getItemlist(), Item_provide.class);
        final Adapter_ListView_OrderList_Item adapt = new Adapter_ListView_OrderList_Item(context, item_provides);
        holderView.myListView.setAdapter(adapt);
        holderView.myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item_provide item = adapt.getItem(position);
                Intent intent = new Intent(context, GoodsInfoActivity.class);
                intent.putExtra("gid", item.getGid());
                context.startActivity(intent);
            }
        });
        return currentView;
    }

    private void goTO(String url, int status, final int p, String oid) {
        final Map map = new HashMap();
        map.put("status", status);
        map.put("oid", oid);
        //对应的操作,
        HttpUtil.post(url, map, new HttpUtil.callBlack() {
            @Override
            public void succcess(String code) {
                //成功对应的条目
                //刷新
                Message message = mhandle.obtainMessage();
                message.what = 1;
                message.obj = p;
                mhandle.sendMessage(message);
            }

            @Override
            public void fail(String code) {
            }

            @Override
            public void err() {
            }
        });
    }

    public class HolderView {
        private TextView number;
        private TextView price;
        private TextView bname;
        private TextView bphone;
        private TextView stauts;
        private TextView data;
        private MyListView myListView;
        private TextView oper;
        private TextView call;
    }

}
