package shop.xawl.com.shop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import shop.xawl.com.shop.Model.Business;
import shop.xawl.com.shop.Model.KeyWord;
import shop.xawl.com.shop.Model.User;
import shop.xawl.com.shop.MyApplication;
import shop.xawl.com.shop.R;
import shop.xawl.com.shop.http.HttpUtil;
import shop.xawl.com.shop.tool.SharedPreferencesUtil;
import shop.xawl.com.shop.tool.StringUtils;
import shop.xawl.com.shop.tool.TDevice;

/**
 * Created by Administrator on 2016/8/22.
 */
public class LoginActivity extends AppCompatActivity {

    private Button login;
    private TextView regist;
    private EditText etuname;
    private EditText etupass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.login);
        regist = (TextView) findViewById(R.id.regist);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(intent);
            }
        });
        initUI();
        initData();

    }

    private void initData() {
        SharedPreferencesUtil share = new SharedPreferencesUtil(getApplicationContext());
        etuname.setText(share.getUserName());
        etupass.setText(share.getUserPwd());
    }

    private void initUI() {

        etuname = (EditText) findViewById(R.id.loginaccount);
        etupass = (EditText) findViewById(R.id.loginpassword);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String stname = etuname.getText().toString();
                final String stpass = etupass.getText().toString();
                if (!loginchek()) return;
                final Map map = new HashMap<String, String>();
                map.put("ulogin", stname);
                map.put("upassword", stpass);
                String stUrl = HttpUtil.getApi_path("user/login");
                Log.v("####login", "path" + stUrl);
                HttpUtil.post(stUrl, map, new HttpUtil.callBlack() {
                    @Override
                    public void succcess(String code) {
                            JSONObject object = JSON.parseObject(code);
                            int userkey = object.getInteger("user_status");
                            if (KeyWord.LOGIN_USER_SUCCESS == userkey) {
                                //登陆成功
                                String stuser = object.getString("user");
                                User user = JSON.parseObject(stuser, User.class);
                                user.setPwd(map.get("upassword") + "");
                                MyApplication.getInstance().saveUserInfo(user);
                                MyApplication.user=user;
                                if (!"0".equals(user.getBkey())) {//是商家
                                    Business business = JSON.parseObject(object.getString("business"), Business.class);
                                    Log.d("######1",business+"");
                                    MyApplication.getInstance().saveBusinessInfo(business);
                                    MyApplication.business=business;
                                }
                                new SharedPreferencesUtil(getApplicationContext()).saveUserInfo(stname, stpass, null);
                                setResult(1);
                                finish();
                            } else if (KeyWord.LOGIN_USER_FAIL == userkey) {
                                MyApplication.showToast("已经被冻结");


                            } else {
                                MyApplication.getInstance().cleanLoginInfo();
                                MyApplication.showToast("密码/用户名错误");
                            }

                    }
                    @Override
                    public void fail(String code) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "登录失败，请检查账号密码", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void err() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "请检查网络！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }
    private boolean loginchek() {
        if (!TDevice.hasInternet()) {
            MyApplication.showToastShort("没有网络");
            return false;
        }
        String uName = etuname.getText().toString();
        if (StringUtils.isEmpty(uName)) {
            MyApplication.showToastShort("用户名不能为空");
            etuname.requestFocus();
            return false;
        }
        // 去除邮箱正确性检测
        // if (!StringUtils.isEmail(uName)) {
        // AppContext.showToastShort(R.string.tip_illegal_email);
        // mEtUserName.requestFocus();
        // return false;
        // }
        String pwd = etupass.getText().toString();
        if (StringUtils.isEmpty(pwd)) {
            MyApplication.showToastShort("密码不能为空");
            etupass.requestFocus();
            return false;
        }
        return true;
    }
}
