package shop.xawl.com.shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import shop.xawl.com.shop.Model.Item_provide;
import shop.xawl.com.shop.R;

/**
 * Created by Administrator on 2016/9/7.
 */
public class Adapter_ListView_OrderList_Item extends BaseAdapter {
    private Context context;
    private List<Item_provide> arrayList = null;

    public Adapter_ListView_OrderList_Item(Context context, List<Item_provide> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public Adapter_ListView_OrderList_Item(Context context) {
        this.context = context;

    }

    @Override
    public int getCount() {
        return (arrayList != null && arrayList.size() == 0) ? 0 : arrayList.size();
    }

    @Override
    public Item_provide getItem(int arg0) {

        return (Item_provide) arrayList.get(arg0);
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
            currentView = LayoutInflater.from(context).inflate(R.layout.adapter_listview_orderlist_item, null);
            holderView.gname = (TextView) currentView.findViewById(R.id.tv_ibuy_name);
            holderView.price = (TextView) currentView.findViewById(R.id.tv_ibuy_price);
            currentView.setTag(holderView);
        } else {
            holderView = (HolderView) currentView.getTag();
        }
        holderView.gname.setText(arrayList.get(position).getGname());
        holderView.price.setText(arrayList.get(position).getGprice() + "");
        return currentView;
    }

    public class HolderView {
        private TextView gname;
        private TextView price;

    }

}
