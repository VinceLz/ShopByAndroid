package shop.xawl.com.shop.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import shop.xawl.com.shop.Model.item_request;
import shop.xawl.com.shop.MyApplication;
import shop.xawl.com.shop.R;
import shop.xawl.com.shop.http.HttpUtil;

public class PostInfoActivity extends AppCompatActivity {
    private TextView tvleve;
    private TextView tvTitle;
    private TextView tvUname;
    private TextView tvPhone;
    private TextView tvLocation;
    private TextView tvContent;
    private TextView tvDate;
    private int pid;
    private item_request post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_details);
        pid = getIntent().getIntExtra("pid", 0);
        initUI();
    }

    private void initUI() {
        tvContent = (TextView) findViewById(R.id.post_info_content);
        tvTitle = (TextView) findViewById(R.id.post_info_title);
        tvleve = (TextView) findViewById(R.id.post_info_leve);
        tvLocation = (TextView) findViewById(R.id.post_info_location);
        tvDate = (TextView) findViewById(R.id.post_info_data);
        tvPhone = (TextView) findViewById(R.id.post_info_phone);
        tvUname = (TextView) findViewById(R.id.post_info_user);
        InitPostdata(pid);
    }

    public void InitPostdata(int pid) {
        Map map = new HashMap();
        map.put("pid", pid);
        String url = HttpUtil.getApi_path("post/get");
        HttpUtil.post(url, map, new HttpUtil.callBlack() {
            @Override
            public void succcess(String code) {
                JSONObject object = JSON.parseObject(code);
                post = JSON.parseObject(object.getString("post"), item_request.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initdata(post);
                    }
                });
            }

            @Override
            public void fail(String code) {
                MyApplication.showToast("出错了");
            }

            @Override
            public void err() {
                MyApplication.showToast("出错了");
            }
        });
    }

    void initdata(item_request ob) {
        tvleve.setText(ob.getCname());
        tvDate.setText(ob.getPdate());
        tvPhone.setText(ob.getUphone());
        tvTitle.setText(ob.getTitle());
        tvUname.setText(ob.getUname());
        tvContent.setText(ob.getContent());
    }
}
