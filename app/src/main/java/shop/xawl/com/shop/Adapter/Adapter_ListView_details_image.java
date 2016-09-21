package shop.xawl.com.shop.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.RequestCreator;

import java.io.IOException;
import java.util.List;

import shop.xawl.com.shop.MyApplication;
import shop.xawl.com.shop.R;
import shop.xawl.com.shop.http.HttpUtil;


public class Adapter_ListView_details_image extends BaseAdapter {
    private Context context;
    private String[] array;

    public Adapter_ListView_details_image(Context context, String[] array) {
        this.context = context;
        this.array = array;
    }

    @Override
    public int getCount() {
        return (array != null && array.length == 0) ? 0 : array.length;
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
    public View getView(final int position, View currentView, ViewGroup arg2) {
        HolderView holderView = null;
        if (currentView == null) {
            holderView = new HolderView();
            currentView = View.inflate(context, R.layout.adapter_details_item, null);
            holderView.content = (ImageView) currentView.findViewById(R.id.details_image);
            holderView.tv = (TextView) currentView.findViewById(R.id.tv);
            currentView.setTag(holderView);
        } else {
            holderView = (HolderView) currentView.getTag();
        }
        holderView.tv.setText("");
        HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(array[position])).resize(MyApplication.getDisplaySize()[0], MyApplication.getDisplaySize()[0]).into(holderView.content);
        return currentView;
    }
    public class HolderView {
        private ImageView content;
        private TextView tv;
    }
}
