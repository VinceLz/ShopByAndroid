package shop.xawl.com.shop.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shop.xawl.com.shop.Model.Item_cart;
import shop.xawl.com.shop.Model.Order;
import shop.xawl.com.shop.MyApplication;
import shop.xawl.com.shop.R;
import shop.xawl.com.shop.Adapter.Adapter_ListView_Buy;
import shop.xawl.com.shop.Adapter.MyListView;
import shop.xawl.com.shop.http.HttpUtil;

public class BuyNowActivity extends AppCompatActivity {
    private MyListView myListView;
    private TextView tvUname;
    private TextView tvBuy;
    private TextView tvPrice;
    private TextView tvSize;
    private List<Item_cart> list;
    private Adapter_ListView_Buy adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_now);

        initUI();
        getData();
    }


    private void initUI() {
        myListView = (MyListView) findViewById(R.id.buy_listView_cart);
        tvUname = (TextView) findViewById(R.id.tv_buy_uname);
        tvSize = (TextView) findViewById(R.id.tv_buy_size);
        tvPrice = (TextView) findViewById(R.id.tv_buy_TotalPrice);
        tvBuy = (TextView) findViewById(R.id.tv_buy_ok);
        tvBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toBuy();
            }
        });
    }

    private void toBuy() {
        String url;
        Map<String, Object> map = new HashMap<>();
        if (list.size() == 1) {
            url = HttpUtil.getApi_path("order/add");
            map.put("gid", ((Item_cart) list.get(0)).getGid());
        } else {
            url = HttpUtil.getApi_path("order/addAll");
            StringBuilder stmaps = new StringBuilder();
            for (Item_cart item : list) {
                stmaps.append(item.getGid() + ",");
            }
            stmaps = stmaps.deleteCharAt(stmaps.length() - 1);
            Log.v("#####del", stmaps.toString());
            map.put("gid", stmaps);
        }
        HttpUtil.post(url, map, new HttpUtil.callBlack() {
            @Override
            public void succcess(String code) {
                JSONObject object = JSONObject.parseObject(code);
                Log.v("###object", "" + object.getString("orders"));
                List<Order> list = JSON.parseArray(object.getString("orders"), Order.class);
                Intent intent = new Intent(BuyNowActivity.this, OrderListActivity.class);
                intent.putExtra("order_list", (Serializable) list);
                startActivity(intent);

                Log.v("#####Order", "" + code);
            }

            @Override
            public void fail(String code) {
                Log.v("#####faile", "" + code);
            }

            @Override
            public void err() {
                Log.v("#####faile", "error");
            }
        });


    }

    private void initData() {
        adapter = new Adapter_ListView_Buy(BuyNowActivity.this, list);
        myListView.setAdapter(adapter);
        tvUname.setText("联系人" + MyApplication.get("user.uname", ""));
        tvSize.setText("总共" + list.size() + "件");
        tvPrice.setText("合计：￥" + getPrice());

    }

    private Double getPrice() {
        double result = 0;
        for (Item_cart item : list) {
            result += item.getGprice();
        }
        return result;
    }

    public void getData() {
        list = (List) getIntent().getSerializableExtra("buy_list");
        initData();
    }


}
