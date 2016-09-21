package shop.xawl.com.shop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shop.xawl.com.shop.Model.Item_cart;
import shop.xawl.com.shop.Model.Item_provide;
import shop.xawl.com.shop.MyApplication;
import shop.xawl.com.shop.R;
import shop.xawl.com.shop.Adapter.Adapter_ListView_details_image;
import shop.xawl.com.shop.Adapter.MyListView;
import shop.xawl.com.shop.http.GetHttp;
import shop.xawl.com.shop.http.HttpUtil;


/**
 * Created by Administrator on 2016/8/31.
 */
public class GoodsInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private Adapter_ListView_details_image adapter_listView_details_image;

    private MyListView myListView;
    private TextView tvGname;
    private TextView tvPrice;
    private TextView tvBname;
    private TextView tvPhone;
    private TextView tvLocation;
    private TextView tvContent;
    private TextView tvDate;
    private TextView tvToCart;
    private TextView tvToBuy;
    private int gid;
    private Item_provide provide;
    private float Allprice_cart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_details);
        gid = getIntent().getIntExtra("gid", -1);
        initUI();
        InitPostdata(gid);
    }

    private void initUI() {
        tvToCart = (TextView) findViewById(R.id.goods_info_toCart);
        tvToBuy = (TextView) findViewById(R.id.goods_info_toBuy);
        tvGname = (TextView) findViewById(R.id.goods_info_Gname);
        tvPrice = (TextView) findViewById(R.id.goods_info_Price);
        tvBname = (TextView) findViewById(R.id.goods_info_Bname);
        tvPhone = (TextView) findViewById(R.id.goods_info_Phone);
        tvLocation = (TextView) findViewById(R.id.goods_info_Location);
        tvContent = (TextView) findViewById(R.id.goods_info_content);
        tvDate = (TextView) findViewById(R.id.goods_info_Date);
        myListView = (MyListView) findViewById(R.id.listView_details_image);
        tvToCart.setOnClickListener(this);
        tvToBuy.setOnClickListener(this);
    }


    public void InitPostdata(int gid) {
        String url = HttpUtil.getApi_path("goods/get") + "?gid=" + gid;
        HttpUtil.get(url, new HttpUtil.callBlack() {
            @Override
            public void succcess(String code) {
                JSONObject object = JSON.parseObject(code);
                provide = JSON.parseObject(object.getString("goods"), Item_provide.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initdata();
                    }
                });
            }

            @Override
            public void fail(String code) {

            }

            @Override
            public void err() {

            }
        }, false);
    }

    void initdata() {
        if (provide == null) return;
        if (provide.getGimage() != null && provide.getGimage().length != 0) {
            adapter_listView_details_image = new Adapter_ListView_details_image(GoodsInfoActivity.this, provide.getGimage());
            myListView.setAdapter(adapter_listView_details_image);
        }
        tvGname.setText(provide.getGname());
        tvPrice.setText("Y:" + provide.getGprice());
        tvBname.setText(provide.getBname());
        tvPhone.setText(provide.getGphone());
        tvLocation.setText("" + provide.getGlocation());
        tvContent.setText(provide.getGcontent());
        tvDate.setText(provide.getGdate());
        Log.d("#######33", myListView.getCount() + "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goods_info_toBuy:
                if (MyApplication.isLogin()) {
                    toBuy();
                } else {
                    gotologin();
                }

                break;
            case R.id.goods_info_toCart:
                if (MyApplication.isLogin()) {
                    toCart();
                } else {
                    gotologin();
                }

                break;
        }
    }
    void toCart() {
        String url = HttpUtil.getApi_path("cartitem/add");
        Map map = new HashMap();
        map.put("gid", gid);
        map.put("gname", provide.getGname());
        map.put("gprice", provide.getGprice());
        map.put("bname", provide.getBname());
        HttpUtil.post(url, map, new HttpUtil.callBlack() {
            @Override
            public void succcess(String code) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(GoodsInfoActivity.this, "成功加入购物车！", Toast.LENGTH_SHORT).show();
                        Log.v("#######Cart", "成功加入购物车");
                    }
                });
            }

            @Override
            public void fail(String code) {
                Log.v("#######Cart", "加入购物车失败！");
            }

            @Override
            public void err() {
                Log.v("#######Cart", "加入购物车失败");
            }
        });
    }

    void toBuy() {
        Item_cart item = new Item_cart();
        item.setBname(provide.getBname());
        item.setDate(provide.getGdate());
        item.setGid(provide.getGid());
        item.setGname(provide.getGname());
        item.setGprice((float) provide.getGprice());
        item.setCartitem_id(provide.getCid());
        List<Item_cart> lists = new ArrayList<>();
        lists.add(item);
        Intent intent = new Intent(GoodsInfoActivity.this, BuyNowActivity.class);
        intent.putExtra("buy_list", (Serializable) lists);
        startActivity(intent);
    }


    public void gotologin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
