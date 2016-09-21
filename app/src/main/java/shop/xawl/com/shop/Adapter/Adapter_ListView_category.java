package shop.xawl.com.shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import shop.xawl.com.shop.Model.LeveMenu;
import shop.xawl.com.shop.R;

/**
 * Created by Administrator on 2016/8/26.
 */
public class Adapter_ListView_category extends BaseAdapter {
    private Context context;
    private List<LeveMenu> arraylist;
    private HolderView holderView = null;

    public Adapter_ListView_category(Context context, List<LeveMenu> arrayList) {

        this.context = context;
        this.arraylist = arrayList;
    }


    @Override
    public int getCount() {
        return (arraylist != null && arraylist.size() == 0) ? 0 : arraylist.size();
    }

    @Override
    public Object getItem(int i) {
        return arraylist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            holderView = new HolderView();
            view = LayoutInflater.from(context).inflate(R.layout.adapt_listview_category, null);
            holderView.category = (TextView) view.findViewById(R.id.category_name); //类型标题
            view.setTag(holderView);
        } else {
            holderView = (HolderView) view.getTag();
        }
            LeveMenu leve= (LeveMenu) arraylist.get(i);
            holderView.category.setText("" + leve.getCname() + "");
        return view;
    }

    public class HolderView {
        private TextView category;
    }

}
