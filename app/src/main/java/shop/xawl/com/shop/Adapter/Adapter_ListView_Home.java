package shop.xawl.com.shop.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import shop.xawl.com.shop.Model.Home_content;
import shop.xawl.com.shop.R;
import shop.xawl.com.shop.http.HttpUtil;

/**
 * Created by Administrator on 2016/9/8.
 */
public class Adapter_ListView_Home extends BaseAdapter {
    private Context context;
    private List<Home_content> home_contents;

    public Adapter_ListView_Home(List<Home_content> content, Context context) {
        this.context = context;
        this.home_contents = content;
    }


    @Override
    public int getItemViewType(int position) {
        if (home_contents.get(position).getType() == 0) {
            return 0;
        } else if (home_contents.get(position).getType() == 1) {
            return 1;
        }
        return -1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return (home_contents != null && home_contents.size() == 0) ? 0 : home_contents.size();
    }

    @Override
    public Object getItem(int i) {
        return home_contents.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        HolderView0 holderView0 = null;
        HolderView1 holderView1 = null;
        int type = getItemViewType(i);
        if (view == null) {
            holderView0 = new HolderView0();
            holderView1 = new HolderView1();
            switch (type) {
                case 0:  //有文字和图片
                    view = View.inflate(context, R.layout.home_listview_item, null);
                    holderView0.c1 = (ImageView) view.findViewById(R.id.c1);
                    holderView0.c1_title_bottom = (TextView) view.findViewById(R.id.c1_title_bottom);
                    holderView0.c1_title_top = (TextView) view.findViewById(R.id.c1_title_top);
                    holderView0.c2 = (ImageView) view.findViewById(R.id.c2);
                    holderView0.c2_title_bottom = (TextView) view.findViewById(R.id.c2_title_bottom);
                    holderView0.c2_title_top = (TextView) view.findViewById(R.id.c2_title_top);
                    holderView0.c3 = (ImageView) view.findViewById(R.id.c3);
                    holderView0.c3_title_bottom = (TextView) view.findViewById(R.id.c3_title_bottom);
                    holderView0.c3_title_top = (TextView) view.findViewById(R.id.c3_title_top);
                    holderView0.c4 = (ImageView) view.findViewById(R.id.c4);
                    holderView0.c4_title_bottom = (TextView) view.findViewById(R.id.c4_title_bottom);
                    holderView0.c4_title_top = (TextView) view.findViewById(R.id.c4_title_top);
                    holderView0.head_left = (TextView) view.findViewById(R.id.top_left);
                    holderView0.head_right = (TextView) view.findViewById(R.id.top_right);
                    view.setTag(holderView0);
                    break;
                case 1:
                    view = View.inflate(context, R.layout.home_listview_item, null);
                    holderView1.c1 = (ImageView) view.findViewById(R.id.c1);
                    holderView1.c2 = (ImageView) view.findViewById(R.id.c2);
                    holderView1.c3 = (ImageView) view.findViewById(R.id.c3);
                    holderView1.c4 = (ImageView) view.findViewById(R.id.c4);
                    holderView1.head_left = (TextView) view.findViewById(R.id.top_left);
                    holderView1.head_right = (TextView) view.findViewById(R.id.top_right);
                    view.setTag(holderView1);
                    break;

            }
        } else {
            switch (type) {
                case 0:
                    holderView0 = (HolderView0) view.getTag();
                    break;
                case 1:
                    holderView1 = (HolderView1) view.getTag();
                    break;
            }
        }
        //填充数据
        switch (type) {
            case 0:
                holderView0.c1_title_top.setText(home_contents.get(i).getC1_title_top());
                holderView0.c1_title_bottom.setText(home_contents.get(i).getC1_title_bottom());
                holderView0.c2_title_top.setText(home_contents.get(i).getC2_title_top());
                holderView0.c2_title_bottom.setText(home_contents.get(i).getC2_title_bottom());
                holderView0.c3_title_top.setText(home_contents.get(i).getC3_title_top());
                holderView0.c3_title_bottom.setText(home_contents.get(i).getC3_title_bottom());
                holderView0.c4_title_top.setText(home_contents.get(i).getC4_title_top());
                holderView0.c4_title_bottom.setText(home_contents.get(i).getC4_title_bottom());
                holderView0.head_left.setText(home_contents.get(i).getHead_title_left());
                holderView0.head_right.setText(home_contents.get(i).getHead_title_left());

                //这只文字为显示
                holderView0.c1_title_top.setVisibility(View.VISIBLE);
                holderView0.c1_title_bottom.setVisibility(View.VISIBLE);
                holderView0.c2_title_top.setVisibility(View.VISIBLE);
                holderView0.c2_title_bottom.setVisibility(View.VISIBLE);
                holderView0.c1_title_top.setVisibility(View.VISIBLE);
                holderView0.c1_title_top.setVisibility(View.VISIBLE);
                holderView0.c1_title_top.setVisibility(View.VISIBLE);

                HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(home_contents.get(i).getC1_image())).into(holderView0.c1);
                HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(home_contents.get(i).getC2_image())).into(holderView0.c2);
                HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(home_contents.get(i).getC3_image())).into(holderView0.c3);
                HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(home_contents.get(i).getC4_image())).into(holderView0.c4);
                break;
            case 1:
                HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(home_contents.get(i).getC1_image())).into(holderView1.c1);
                HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(home_contents.get(i).getC2_image())).into(holderView1.c2);
                HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(home_contents.get(i).getC3_image())).into(holderView1.c3);
                HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(home_contents.get(i).getC4_image())).into(holderView1.c4);
                holderView1.head_left.setText(home_contents.get(i).getHead_title_left());
                holderView1.head_right.setText(home_contents.get(i).getHead_title_left());
                break;
        }
        return view;
    }

    //有文字的类型
    private class HolderView0 {
        private TextView head_left;
        private TextView head_right;
        private TextView top_left;
        private TextView top_right;
        private TextView c1_title_top;
        private TextView c1_title_bottom;
        private ImageView c1;
        private TextView c2_title_top;
        private TextView c2_title_bottom;
        private ImageView c2;
        private TextView c3_title_top;
        private TextView c3_title_bottom;
        private ImageView c3;
        private TextView c4_title_top;
        private TextView c4_title_bottom;
        private ImageView c4;
    }

    //只有图片
    private class HolderView1 {
        private TextView head_left;
        private TextView head_right;
        private ImageView c1;
        private ImageView c2;
        private ImageView c3;
        private ImageView c4;
    }
}
