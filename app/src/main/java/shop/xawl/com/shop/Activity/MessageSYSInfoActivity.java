package shop.xawl.com.shop.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import shop.xawl.com.shop.Model.Message;
import shop.xawl.com.shop.R;
import shop.xawl.com.shop.http.HttpUtil;

public class MessageSYSInfoActivity extends AppCompatActivity {
    Message mess;
    TextView textContent;
    TextView textData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_info);
        getData();
        textContent = (TextView) findViewById(R.id.sysMessage_content);
        textData = (TextView) findViewById(R.id.sysMessage_data);
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
        if (mess != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textContent.setText(mess.getMessage());
                    textData.setText("日期：" + mess.getPdate());
                }
            });

        }
    }
}
