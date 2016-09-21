package shop.xawl.com.shop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import shop.xawl.com.shop.MyApplication;
import shop.xawl.com.shop.R;
import shop.xawl.com.shop.http.HttpUtil;

/**
 * Created by Administrator on 2016/8/26.
 */
public class SendPostActivity extends AppCompatActivity {
    private RelativeLayout select_category;
    EditText etTitle;
    EditText etUname;
    EditText etPhone;
    EditText etText;
    TextView etLeve;
    private int START_Key = 10;

    String stCname;
    int iCid = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_post);
        select_category = (RelativeLayout) findViewById(R.id.select_category);
        select_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SendPostActivity.this, CategoryActivity.class);
                startActivityForResult(intent, START_Key);
            }
        });
        initUI();
    }

    private void initUI() {
        etTitle = (EditText) findViewById(R.id.post_title);
        etUname = (EditText) findViewById(R.id.post_name);
        etPhone = (EditText) findViewById(R.id.post_phone);
        etText = (EditText) findViewById(R.id.post_text);
        etLeve = (TextView) findViewById(R.id.post_leve);
        etUname.setText(MyApplication.user.getUname());
        etPhone.setText(MyApplication.user.getUphone());
        findViewById(R.id.post_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });
        etLeve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SendPostActivity.this, CategoryActivity.class);
                startActivityForResult(intent, START_Key);
            }
        });

    }

    private void submitData() {
        String stText = etText.getText().toString();
        String stTitle = etTitle.getText().toString();
        String stPhone = etPhone.getText().toString();
        String stName = etUname.getText().toString();
        if (!cheackdata(stText, stTitle, stPhone, stName)) {
            Toast.makeText(SendPostActivity.this, "数据不完整！", Toast.LENGTH_SHORT).show();
            return;
        }
        Map map = new HashMap<String, Object>();
        map.put("title", stTitle);
        map.put("content", stText);
        map.put("uphone", stPhone);
        map.put("uname", stName);
        map.put("uid", MyApplication.user.getUid());
        map.put("cid", iCid);
        map.put("cname", stCname);
        final String url = HttpUtil.getApi_path("post/add");
        HttpUtil.post(url, map, new HttpUtil.callBlack() {
            @Override
            public void succcess(String code) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(SendPostActivity.this, SerachActivity.class);
                        i.putExtra("cid", iCid);
                        i.putExtra("cname", stCname);
                        startActivity(i);
                        finish();
                    }
                });
            }

            @Override
            public void fail(String code) {

            }

            @Override
            public void err() {

            }
        });


    }

    private boolean cheackdata(String stText, String stTitle, String stPhone, String stName) {
        if (TextUtils.isEmpty(stText)) return false;
        if (TextUtils.isEmpty(stTitle)) return false;
        if (TextUtils.isEmpty(stPhone)) return false;
        if (TextUtils.isEmpty(stName)) return false;
        if (TextUtils.isEmpty(stCname)) return false;
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == START_Key || data != null) {//分类结果处理
            iCid = data.getIntExtra("cid", 0);
            stCname = data.getStringExtra("cname");
            etLeve.setText(stCname);
            Log.v("####cid", stCname);
            return;
        }
    }
}
