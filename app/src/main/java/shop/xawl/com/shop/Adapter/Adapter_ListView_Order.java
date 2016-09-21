package shop.xawl.com.shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import shop.xawl.com.shop.Model.Order;
import shop.xawl.com.shop.R;


public class Adapter_ListView_Order extends BaseAdapter {
    private Context context;
    private List<Order> arrayList =null;

    public Adapter_ListView_Order(Context context, List<Order> arrayList) {

        this.context = context;
        this.arrayList = arrayList;
    }

    public Adapter_ListView_Order(Context context) {
        this.context = context;

    }

    @Override
    public int getCount() {
        return (arrayList != null && arrayList.size() == 0) ? 0 : arrayList.size();
    }

    @Override
    public Order getItem(int arg0) {

        return (Order)arrayList.get(arg0);
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
            currentView = LayoutInflater.from(context).inflate(R.layout.adapter_listview_order, null);
            holderView.number = (TextView) currentView.findViewById(R.id.tv_order_number); //订单号
            holderView.price = (TextView) currentView.findViewById(R.id.tv_order_sum); //价格
            holderView.bname = (TextView) currentView.findViewById(R.id.tv_order_Bname); //商家名
            holderView.bphone = (TextView) currentView.findViewById(R.id.tv_order_phone); //商家电话
            holderView.stauts = (TextView) currentView.findViewById(R.id.tv_order_stauts); //订单状态
            holderView.data = (TextView) currentView.findViewById(R.id.tv_order_data); //订单时间
            currentView.setTag(holderView);
        } else {
            holderView = (HolderView) currentView.getTag();
        }
        int i=arrayList.get(position).getStatus();
        String stauts="";
        switch (i){
            case -1:stauts="未付款";break;
            case 0:stauts="已付款";break;
            case 1:stauts="退款中";break;
            case 2:stauts="完成";break;

        }
        holderView.number.setText(arrayList.get(position).getOid());
        holderView.price.setText(""+arrayList.get(position).getCurrentprice());
        holderView.bname.setText(arrayList.get(position).getBname());
        holderView.bphone.setText(arrayList.get(position).getBphone());
        holderView.stauts.setText(stauts);
        holderView.data.setText(arrayList.get(position).getOrdertime());
        return currentView;
    }

    public class HolderView {
        private TextView number;
        private TextView price;
        private TextView bname;
        private TextView bphone;
        private TextView stauts;
        private TextView data;
    }

}
