package shop.xawl.com.shop.Activity;


import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;

import shop.xawl.com.shop.BaseApplication;
import shop.xawl.com.shop.Model.LeveMenu;
import shop.xawl.com.shop.MyApplication;
import shop.xawl.com.shop.R;
import shop.xawl.com.shop.Frangment.Send;
import shop.xawl.com.shop.Frangment.Cart_F;
import shop.xawl.com.shop.Frangment.Category;
import shop.xawl.com.shop.Frangment.Home;
import shop.xawl.com.shop.Adapter.IBtnCallListener;
import shop.xawl.com.shop.Frangment.UserInfo;
import shop.xawl.com.shop.http.HttpUtil;
import shop.xawl.com.shop.tool.LocalUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IBtnCallListener {
    //除去tab的界面
    private Home home;
    private UserInfo user_info;
    private Cart_F cart;
    private Category category;//分类栏
    private Send send;
    private ImageView[] bt_menu = new ImageView[5]; //轮播图
    //对应的5个tab按钮
    private int[] bt_menu_id = {R.id.iv_menu_0, R.id.iv_menu_1, R.id.iv_menu_2, R.id.iv_menu_3, R.id.iv_menu_4};

    //选中对应的tab按钮后的背景加深颜色
    private int[] select_on = {R.mipmap.guide_home_on, R.mipmap.guide_tfaccount_on, R.mipmap.guide_discover_on, R.mipmap.guide_cart_on, R.mipmap.guide_account_on};

    private TextView[] select_on_title = new TextView[5];
    //不加深颜色
    private int[] select_off = {R.drawable.bt_menu_0_select, R.drawable.bt_menu_1_select, R.drawable.bt_menu_2_select, R.drawable.bt_menu_3_select, R.drawable.bt_menu_4_select};

    private int cFid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fa);
        //这里应该获取一些之前缓存下的数据
        BaseApplication.saveDisplaySize(this);  //保存屏幕
        initView();
        initData();
    }

    //加载视图
    private void initView() {

        for (int i = 0; i < bt_menu.length; i++) { //热门市场
            bt_menu[i] = (ImageView) findViewById(bt_menu_id[i]);
            bt_menu[i].setOnClickListener(this);
        }
        select_on_title[0] = (TextView) findViewById(R.id.menu_1);
        select_on_title[1] = (TextView) findViewById(R.id.menu_2);
        select_on_title[2] = (TextView) findViewById(R.id.menu_3);
        select_on_title[3] = (TextView) findViewById(R.id.menu_4);
        select_on_title[4] = (TextView) findViewById(R.id.menu_5);
        if (home == null) {
            home = new Home();
            addFragment(home);
            showFragment(home);
        } else {
            showFragment(home);
        }
        //设置首页被选中
        bt_menu[0].setImageResource(select_on[0]);
    }


    public void addFragment(Fragment fragment) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.add(R.id.show_layout, fragment); // TODO: 2016/8/26   replace
        ft.commitAllowingStateLoss();
    }

    public void removeFragment(Fragment fragment) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.remove(fragment);
        ft.commitAllowingStateLoss();
    }

    public void showFragment(Fragment fragment) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        // 动画，暂时隐藏
        //ft.setCustomAnimations(R.anim.cu_push_right_in, R.anim.cu_push_left_out);

        if (home != null) {
            ft.hide(home);
        }
        //后续其他tab
        if (user_info != null) {
            ft.hide(user_info);
        }
        if (cart != null) {
            ft.hide(cart);
        }
        if (send != null) {
            ft.hide(send);
        }
        if (category != null) {
            ft.hide(category);
        }
        ft.show(fragment);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_menu_0://点击首页
                OnChangeFragment(0);
                break;
            //后续其他tab按钮操作
            case R.id.iv_menu_4:  //点击我的中心，进行登陆
                //这里应该是判断是否登陆，对应有user一个类进行显示
                //1 暂时先弹出登陆界面
                if (!MyApplication.isLogin()) {
                    showLogin(4);
                } else {
                    OnChangeFragment(4);
                }
                break;


            case R.id.iv_menu_3://点击了购物车
                if (!MyApplication.isLogin()) {
                    showLogin(3);
                } else {
                    OnChangeFragment(3);
                }
                break;
            case R.id.iv_menu_1://点击发布
                OnChangeFragment(1);
                break;
            case R.id.iv_menu_2://点击搜索
                OnChangeFragment(2);
                break;
        }

        //设置选中状态
        for (int i = 0; i < bt_menu.length; i++) {
            bt_menu[i].setImageResource(select_off[i]);
            select_on_title[i].setTextColor(Color.rgb(0, 0, 0));
            if (view.getId() == bt_menu_id[i]) {
                bt_menu[i].setImageResource(select_on[i]);
                select_on_title[i].setTextColor(Color.rgb(218, 106, 53));
            }
        }
    }

    @SuppressWarnings("unused")
    private IBtnCallListener btnCallListener;


    private void OnChangeFragment(int item_id) {
        //如果点击了，就应该去请求数据填充进去
        switch (item_id) {
            case 0:
                if (home == null) {
                    home = new Home();  //创建首页
                    addFragment(home);
                    showFragment(home);

                } else {
                    if (home.isHidden()) {
                        showFragment(home);
                        Log.d("####", "點擊了");
                    }
                }

                break;
            case 1:
                if (send == null) {
                    send = new Send();
                    addFragment(send);
                    showFragment(send);
                } else {
                    if (send.isHidden()) {
                        showFragment(send);
                        Log.d("####", "点击了");
                        send.initDate();
                    }
                }

                break;
            case 2:
                if (category == null) {
                    category = new Category();
                    addFragment(category);
                    showFragment(category);
                } else {
                    if (category.isHidden()) {
                        showFragment(category);
                        Log.d("####", "点击了");
                        category.initDate();
                    }
                }

                break;
            case 4:
                if (user_info == null) {
                    user_info = new UserInfo();
                    addFragment(user_info);
                    showFragment(user_info);
                } else {
                    if (user_info.isHidden()) {
                        showFragment(user_info);
                        Log.d("####", "點擊了");
                        user_info.initDate();
                    }
                }

                break;
            case 3:
                if (cart == null) {
                    cart = new Cart_F();
                    addFragment(cart);
                    showFragment(cart);
                } else {
                    if (cart.isHidden()) {
                        showFragment(cart);
                        cart.initDate();
                    }
                }


                break;

        }
        //设置选中状态
        for (int i = 0; i < bt_menu.length; i++) {
            bt_menu[i].setImageResource(select_off[i]);
            select_on_title[i].setTextColor(Color.rgb(0, 0, 0));
            if (bt_menu_id[i] == bt_menu_id[item_id]) {
                bt_menu[i].setImageResource(select_on[i]);
                select_on_title[i].setTextColor(Color.rgb(218, 106, 53));
            }
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        try {
            btnCallListener = (IBtnCallListener) fragment;
        } catch (Exception e) {
        }
        super.onAttachFragment(fragment);
    }

    @Override
    public void transferMsg() {
        if (home == null) {
            home = new Home();
            addFragment(home);
            showFragment(home);
        } else {
            showFragment(home);
        }
        bt_menu[3].setImageResource(select_off[3]);
        bt_menu[0].setImageResource(select_on[0]);
    }

    void showLogin(int cFid) {
        startActivityForResult(new Intent(this, LoginActivity.class), 1);
        this.cFid = cFid;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {//登录成功！
            MyApplication.login = true;
            OnChangeFragment(cFid);
            Toast.makeText(MainActivity.this, "登陆成功！", Toast.LENGTH_SHORT).show();
        } else {
            if (home == null) {
                home = new Home();  //创建首页
                addFragment(home);
                showFragment(home);

            } else {
                if (home.isHidden()) {
                    showFragment(home);
                    Log.d("####", "點擊了");
                }
            }
            bt_menu[0].setImageResource(select_on[0]);
            select_on_title[0].setTextColor(Color.rgb(218, 106, 53));
            select_on_title[1].setTextColor(Color.rgb(0, 0, 0));
            select_on_title[3].setTextColor(Color.rgb(0, 0, 0));
            select_on_title[4].setTextColor(Color.rgb(0, 0, 0));
            bt_menu[1].setImageResource(select_off[1]);
            bt_menu[3].setImageResource(select_off[3]);
            bt_menu[4].setImageResource(select_off[4]);
        }
    }

    protected void initData() {
        LocalUtil.getSDExternalRootPath();
        Log.v("###dir", LocalUtil.SDDIR);
        LocalUtil.createSdPath();
    }
}
