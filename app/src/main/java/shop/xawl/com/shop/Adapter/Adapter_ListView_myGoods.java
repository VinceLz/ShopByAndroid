package shop.xawl.com.shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import shop.xawl.com.shop.Model.Item_provide;
import shop.xawl.com.shop.Model.item_request;
import shop.xawl.com.shop.R;

/**
 * Created by Administrator on 2016/9/16.
 */
public class Adapter_ListView_myGoods extends BaseAdapter {
    private Context context;
    private List<Item_provide> arrayList;
    private Adapter_ListView_cart.onCheckedChanged listener;

    public Adapter_ListView_myGoods(Context activity, List<Item_provide> list) {
        this.context = activity;
        this.arrayList = list;
    }

    @Override
    public int getCount() {
        return (arrayList != null && arrayList.size() == 0) ? 0 : arrayList.size();
    }

    @Override
    public Item_provide getItem(int i) {
        return (Item_provide) arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        HolderView holderView = null;
        if (view == null) {
            holderView = new HolderView();
            view = LayoutInflater.from(context).inflate(R.layout.adapter_mypost_item, null);
            holderView.title = (TextView) view.findViewById(R.id.post_name); //帖子标题
            holderView.cname = (TextView) view.findViewById(R.id.post_cname); //
            holderView.cb_choice = (CheckBox) view.findViewById(R.id.post_choice);
            holderView.pdate = (TextView) view.findViewById(R.id.post_date);
            view.setTag(holderView);
        } else {
            holderView = (HolderView) view.getTag();
        }
        if (arrayList.size() != 0) {
            holderView.title.setText("" + ((Item_provide) arrayList.get(i)).getGname() + "");
            holderView.cname.setText("" + ((Item_provide) arrayList.get(i)).getCname() + "");
            holderView.pdate.setText("" + ((Item_provide) arrayList.get(i)).getGdate());
            holderView.cb_choice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton arg0, boolean choice) {
                    listener.getChoiceData(i, choice);
                }
            });
        }
        return view;
    }


    public class HolderView {
        private TextView title;
        private TextView cname;
        private TextView pdate;
        private CheckBox cb_choice;
    }

    public void setOnCheckedChanged(Adapter_ListView_cart.onCheckedChanged listener) {
        this.listener = listener;
    }

}
