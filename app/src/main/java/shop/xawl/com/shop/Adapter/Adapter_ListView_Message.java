package shop.xawl.com.shop.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import shop.xawl.com.shop.Model.Message;
import shop.xawl.com.shop.R;

/**
 * Created by doter on 2016/9/13.
 */
public class Adapter_ListView_Message extends BaseAdapter {
    public final int MESSAGE_SEND_ADMIN = 0;
    public final int MESSAGE_SEND_USER = 1;
    public final int MESSAGE_SEND_BUSINESS = 2;

    public final int MESSAGE_NOREAD = 0;
    public final int MESSAGE_READ = 1;

    private List<Message> list;
    private Context context;

    public Adapter_ListView_Message(Context context, List<Message> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getCount() {
        return (list != null && list.size() == 0) ? 0 : list.size();
    }

    @Override
    public Message getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderView holderView;
        if (convertView == null) {
            holderView = new HolderView();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_listview_message, null);
            holderView.imageView = (ImageView) convertView.findViewById(R.id.adpter_message_image);
            holderView.textView = (TextView) convertView.findViewById(R.id.adpter_message_content);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }
        Message m = list.get(position);
        Log.v("#####messagelist", "" + list.size());
        holderView.textView.setText(m.getSend_name());
        switch (list.get(position).getType()) {
            case MESSAGE_SEND_ADMIN:
                holderView.imageView.setImageResource(R.drawable.sysmessage);
                break;
            case MESSAGE_SEND_USER:
                holderView.imageView.setImageResource(R.drawable.usermessage);
                break;
            case MESSAGE_SEND_BUSINESS:
                holderView.imageView.setImageResource(R.drawable.business);
                break;
        }
        return convertView;
    }

    public class HolderView {
        private ImageView imageView;
        private TextView textView;
    }

}

