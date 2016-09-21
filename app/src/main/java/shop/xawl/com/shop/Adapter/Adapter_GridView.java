package shop.xawl.com.shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import shop.xawl.com.shop.Frangment.UserInfo;
import shop.xawl.com.shop.R;

public class Adapter_GridView extends BaseAdapter {
    private Context context;
    private int[] data;
    private int[] number;
    private String[] tname;
    public Adapter_GridView(Context context, int[] data, String[] tname, int[] stats_arry) {
        this.context = context;
        this.data = data;
        this.tname = tname;
        this.number=stats_arry;
    }

    @Override
    public int getCount() {
        return (tname != null && tname.length == 0) ? 0 : tname.length;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
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
            currentView = LayoutInflater.from(context).inflate(R.layout.adapter_grid_home, null);
            holderView.iv_pic = (ImageView) currentView.findViewById(R.id.iv_adapter_grid_pic);
            holderView.cname = (TextView) currentView.findViewById(R.id.cname_title);
            holderView.number = (TextView) currentView.findViewById(R.id.tv_adpter_number);
            currentView.setTag(holderView);
        } else {
            holderView = (HolderView) currentView.getTag();
        }
        holderView.iv_pic.setImageResource(data[position]);
        holderView.cname.setText(tname[position]);
        if (number != null) {
            if (position <= number.length - 1) {
                holderView.number.setText("" + number[position]);
            }
        }
        return currentView;
    }


    public class HolderView {
        private ImageView iv_pic;
        private TextView cname;
        private TextView number;
    }

}
