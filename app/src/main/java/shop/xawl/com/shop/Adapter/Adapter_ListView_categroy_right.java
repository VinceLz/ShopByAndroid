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
 * Created by Administrator on 2016/8/28.
 */
public class Adapter_ListView_categroy_right extends BaseAdapter {
    private Context context;
    private List<LeveMenu> arrayList;

    public Adapter_ListView_categroy_right(Context context, List<LeveMenu> list) {
        this.arrayList =list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return (arrayList != null && arrayList.size() == 0) ? 0 : arrayList.size();
    }

    @Override
    public LeveMenu getItem(int i) {
        return (LeveMenu)arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.adapt_right_listview, null);
        TextView tv = (TextView) view.findViewById(R.id.title);
       MyListView listView= (MyListView) view.findViewById(R.id.adpter_right_list);
        Adapter_ListView_category adapter_listView_category=new Adapter_ListView_category(context,arrayList.get(i).getChildren());
        listView.setAdapter(adapter_listView_category);
        tv.setText(arrayList.get(i).getCname()+"");
        return view;
    }
}
