package shop.xawl.com.shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import shop.xawl.com.shop.Model.HomeImage;
import shop.xawl.com.shop.R;
import shop.xawl.com.shop.http.HttpUtil;

/**
 * Created by Administrator on 2016/9/8.
 */
public class Adapter_GridView_Home extends BaseAdapter {
    private Context context;
    private List<HomeImage> list;

    public Adapter_GridView_Home(Context context, List<HomeImage> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return (list != null && list.size() == 0) ? 0 : list.size();
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
            currentView.setTag(holderView);
        } else {
            holderView = (HolderView) currentView.getTag();
        }
        HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(list.get(position).getImage())).into(holderView.iv_pic);
        holderView.cname.setText(list.get(position).getTitle());
        return currentView;
    }

    public class HolderView {
        private ImageView iv_pic;
        private TextView cname;
    }

}
