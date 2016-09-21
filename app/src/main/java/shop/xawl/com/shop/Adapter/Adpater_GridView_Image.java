package shop.xawl.com.shop.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Map;

import shop.xawl.com.shop.R;

/**
 * Created by Administrator on 2016/8/27.
 */
public class Adpater_GridView_Image extends BaseAdapter {
    private Context context;
    private ArrayList<Map<String, Object>> ilist;

    public Adpater_GridView_Image(Context context, ArrayList<Map<String, Object>> list) {
        this.context = context;
        this.ilist = list;
    }

    @Override
    public int getCount() {
        return (ilist != null && ilist.size() == 0) ? 0 : ilist.size();
    }

    @Override
    public Object getItem(int i) {
        return ilist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            ImageView imageView;
            view = LayoutInflater.from(context).inflate(R.layout.adapter_grid_image_item, null);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.Grid_item_image);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imageView.setImageBitmap((Bitmap) ilist.get(position).get("icon"));
        return view;
    }


    private class ViewHolder {
        private ImageView imageView;
    }

}
