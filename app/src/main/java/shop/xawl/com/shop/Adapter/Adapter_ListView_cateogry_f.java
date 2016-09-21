package shop.xawl.com.shop.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import shop.xawl.com.shop.Frangment.Category;
import shop.xawl.com.shop.Model.LeveMenu;
import shop.xawl.com.shop.MyApplication;
import shop.xawl.com.shop.R;

public class Adapter_ListView_cateogry_f extends BaseAdapter {

    private Context context;
    private String[] strings;
    public static int mPosition;

    public Adapter_ListView_cateogry_f(Context context) {
        this.context = context;
        this.strings = strings;
    }

    @Override
    public int getCount() {
        return MyApplication.leveMenus.size() == 0 && MyApplication.leveMenus != null ? 0 : MyApplication.leveMenus.size();
    }

    @Override
    public LeveMenu getItem(int position) {
        return MyApplication.leveMenus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViemHold hole = null;
        if (convertView == null) {
            hole = new ViemHold();
            convertView = LayoutInflater.from(context).inflate(R.layout.category_f_listview, null);
            hole.textView = (TextView) convertView.findViewById(R.id.tv);
            convertView.setTag(hole);
        } else {
            hole = (ViemHold) convertView.getTag();
        }
        mPosition = position;
        hole.textView.setText(MyApplication.leveMenus.get(position).getCname());
        if (position == Category.mPosition) {
            convertView.setBackgroundResource(R.mipmap.tongcheng_all_bg01);
        } else {
            convertView.setBackgroundColor(Color.parseColor("#f4f4f4"));
        }
        return convertView;
    }

    private class ViemHold {
        private TextView textView;
        private ImageView imageView;
    }
}
