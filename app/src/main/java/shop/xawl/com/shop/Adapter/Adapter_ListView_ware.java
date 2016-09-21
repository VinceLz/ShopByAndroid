package shop.xawl.com.shop.Adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import shop.xawl.com.shop.Model.Item_provide;
import shop.xawl.com.shop.R;

public class Adapter_ListView_ware extends BaseAdapter {
    private Context context;
    @SuppressWarnings("unused")
    private int[] data;
    private List<Item_provide> arrayList;

    public Adapter_ListView_ware(Context context, List<Item_provide> arrayList) {

        this.context = context;
        this.arrayList = arrayList;
    }

    public Adapter_ListView_ware(Context context) {
        this.context = context;

    }

    @Override
    public int getCount() {
        return (arrayList != null && arrayList.size() == 0) ? 0 : arrayList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return arrayList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View currentView, ViewGroup arg2) {
        HolderView holderView = null;
        if (currentView == null) {
            holderView = new HolderView();
            currentView = LayoutInflater.from(context).inflate(R.layout.adapter_listview_ware, null);
            holderView.tv_name = (TextView) currentView.findViewById(R.id.name);
            holderView.tv_price = (TextView) currentView.findViewById(R.id.price);
            holderView.tv_sale_num = (TextView) currentView.findViewById(R.id.sale_num);
            currentView.setTag(holderView);
        } else {
            holderView = (HolderView) currentView.getTag();
        }
        if (arrayList.size() != 0) {


            holderView.tv_name.setText(arrayList.get(position).getGname());
            holderView.tv_price.setText("价格" + arrayList.get(position).getGprice());
            holderView.tv_sale_num.setText("交易量:" + arrayList.get(position).getSale());
        }
        return currentView;
    }

    public class HolderView {


        private TextView tv_sale_num; //交易量
        private TextView tv_name; //名字
        private TextView tv_price; //价格

    }

}
