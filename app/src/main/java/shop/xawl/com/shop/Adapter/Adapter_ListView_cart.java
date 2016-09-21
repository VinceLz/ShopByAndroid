package shop.xawl.com.shop.Adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import shop.xawl.com.shop.Model.Item_cart;
import shop.xawl.com.shop.R;


public class Adapter_ListView_cart extends BaseAdapter {
    private Context context;
    private List<Item_cart> arrayList =null;
    private onCheckedChanged listener;

    public Adapter_ListView_cart(Context context, List<Item_cart> arrayList) {

        this.context = context;
        this.arrayList = arrayList;
    }

    public Adapter_ListView_cart(Context context) {
        this.context = context;

    }

    @Override
    public int getCount() {
        return (arrayList != null && arrayList.size() == 0) ? 0 : arrayList.size();
    }

    @Override
    public Item_cart getItem(int arg0) {

        return (Item_cart)arrayList.get(arg0);
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
            currentView = LayoutInflater.from(context).inflate(R.layout.adapter_listview_cart, null);
            holderView.content = (TextView) currentView.findViewById(R.id.tv_name); //服务标题
            holderView.price = (TextView) currentView.findViewById(R.id.tv_price); //价格
            holderView.cb_choice = (CheckBox) currentView.findViewById(R.id.cb_choice);
            holderView.bname = (TextView) currentView.findViewById(R.id.tv_bname);
            currentView.setTag(holderView);
        } else {
            holderView = (HolderView) currentView.getTag();
        }
        if (arrayList.size() != 0) {
            holderView.content.setText(""+((Item_cart) arrayList.get(position)).getGname() + "");
            holderView.price.setText(""+((Item_cart) arrayList.get(position)).getGprice()+"¥");
            holderView.bname.setText(""+((Item_cart) arrayList.get(position)).getBname());
            holderView.cb_choice.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton arg0, boolean choice) {
                    listener.getChoiceData(position, choice);
                }
            });
        }
        return currentView;
    }

    public class HolderView {
        private TextView content;
        private TextView price;
        private TextView bname;
        private CheckBox cb_choice;

    }

    public interface onCheckedChanged {

        public void getChoiceData(int position, boolean isChoice);
    }

    public void setOnCheckedChanged(onCheckedChanged listener) {
        this.listener = listener;
    }

}
