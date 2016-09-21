package shop.xawl.com.shop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import shop.xawl.com.shop.Adapter.Adapter_ListView_Message;
import shop.xawl.com.shop.Model.Message;
import shop.xawl.com.shop.R;
import shop.xawl.com.shop.http.HttpUtil;

public class MessageActivity extends AppCompatActivity {
    public final int MESSAGE_SEND_ADMIN = 0;
    public final int MESSAGE_SEND_USER = 1;
    public final int MESSAGE_SEND_BUSINESS = 2;
    Adapter_ListView_Message adapter;
    ListView myListView;
    List<Message> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initUI();
        GetData();
    }

    private void initUI() {
        myListView = (ListView) findViewById(R.id.message_list);
        list = new ArrayList<>();
    }

    private void GetData() {
        String url = HttpUtil.getApi_path("message/list");
        HttpUtil.get(url, new HttpUtil.callBlack() {
            @Override
            public void succcess(String code) {
                String stmessage = JSONObject.parseObject(code).getString("message");
                List<Message> messages = JSONArray.parseArray(stmessage, Message.class);
                showData(messages);
            }

            @Override
            public void fail(String code) {

            }

            @Override
            public void err() {
            }
        }, false);
    }

    private void showData(List<Message> m) {
        if (m != null) {
            list.clear();
            list.addAll(m);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (adapter == null) {
                        adapter = new Adapter_ListView_Message(MessageActivity.this, list);
                        myListView.setAdapter(adapter);
                        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent;
                                if (adapter.getItem(position).getType() == MESSAGE_SEND_BUSINESS) {
                                    intent = new Intent(MessageActivity.this, MessageInfoActivity.class);
                                } else {
                                    intent = new Intent(MessageActivity.this, MessageSYSInfoActivity.class);
                                }
                                intent.putExtra("mid", adapter.getItem(position).getMid());
                                startActivity(intent);
                            }
                        });
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }
}
