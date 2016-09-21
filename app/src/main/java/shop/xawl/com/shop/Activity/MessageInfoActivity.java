package shop.xawl.com.shop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import shop.xawl.com.shop.Model.Message;
import shop.xawl.com.shop.R;
import shop.xawl.com.shop.http.HttpUtil;

public class MessageInfoActivity extends AppCompatActivity {
    Message mess;
    public final int MESSAGE_SEND_ADMIN = 0;
    public final int MESSAGE_SEND_USER = 1;
    public final int MESSAGE_SEND_BUSINESS = 2;
    TextView textContent;
    TextView textData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_info2);
        getData();
        textContent = (TextView) findViewById(R.id.MessageInfo_content);
        textData = (TextView) findViewById(R.id.MessageInfo_data);
        findViewById(R.id.MessageInfo_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessageInfoActivity.this, GoodsInfoActivity.class);
                intent.putExtra("gid", mess.getGid());
                startActivity(intent);
            }
        });
    }

    private void getData() {
        int mid = getIntent().getIntExtra("mid", 0);
        getHttpData(mid);
    }

    private void getHttpData(int mid) {
        String url = HttpUtil.getApi_path("message/get");
        Map map = new HashMap();
        map.put("mid", mid);
        HttpUtil.post(url, map, new HttpUtil.callBlack() {
            @Override
            public void succcess(String code) {
                String stmessage = JSONObject.parseObject(code).getString("message");
                Log.v("####stMessageInfo", stmessage);
                mess = JSONObject.parseObject(stmessage, Message.class);
                showData();
            }

            @Override
            public void fail(String code) {

            }

            @Override
            public void err() {

            }
        });
    }

    private void showData() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textContent.setText(mess.getMessage());
                textData.setText("日期：" + mess.getPdate());
            }
        });


    }


}
