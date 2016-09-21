package shop.xawl.com.shop.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import shop.xawl.com.shop.Model.Item_cart;
import shop.xawl.com.shop.R;

/**
 * Created by doter on 2016/9/4.
 */
public class Adapter_ListView_Buy extends BaseAdapter {

    List<Item_cart> list;
    Context context;

    public Adapter_ListView_Buy(Context context, List list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return (list != null && list.size() == 0) ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_listview_buy, null);
            holder = new ViewHolder();
            holder.tvBname = (TextView) convertView.findViewById(R.id.tv_ibuy_bname);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_ibuy_name);
            holder.tvGprice = (TextView) convertView.findViewById(R.id.tv_ibuy_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Item_cart item = list.get(position);
        holder.tvBname.setText(item.getBname());
        holder.tvName.setText(item.getGname());
        holder.tvGprice.setText("" + item.getGprice());
        return convertView;
    }

    static class ViewHolder {
        TextView tvName;
        TextView tvBname;
        TextView tvGprice;
    }
}

