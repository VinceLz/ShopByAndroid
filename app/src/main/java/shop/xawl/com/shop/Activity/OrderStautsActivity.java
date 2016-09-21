package shop.xawl.com.shop.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shop.xawl.com.shop.Model.Order;
import shop.xawl.com.shop.R;
import shop.xawl.com.shop.Adapter.Adapter_ListView_OrderList;
import shop.xawl.com.shop.Adapter.MyListView;
import shop.xawl.com.shop.http.HttpUtil;

public class OrderStautsActivity extends AppCompatActivity {
    TextView tvTitle;
    int status;
    MyListView myListView;
    boolean isbusiness;
    private List<Order> list;
    private Adapter_ListView_OrderList adapter_listView_orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_stauts);
        initUI();
        GetData();
    }

    private void GetData() {
        String url = HttpUtil.getApi_path("order/statusBycode");
        Map map = new HashMap();
        map.put("status", status);
        HttpUtil.post(url, map, new HttpUtil.callBlack() {
            @Override
            public void succcess(String code) {
                Log.v("######code", code);
                JSONObject object = JSONObject.parseObject(code);
                String sts = object.getString("orders");
                list = JSON.parseArray(sts, Order.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initData();
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


    private void initUI() {
        tvTitle = (TextView) findViewById(R.id.order_stauts_Title);
        myListView = (MyListView) findViewById(R.id.order_stauts_listView);
        status = getIntent().getIntExtra("status", -2);
        isbusiness = getIntent().getBooleanExtra("business", false);
        String stautss = "";
        if (isbusiness) {
            switch (status) {
                case 0:
                    stautss = "待处理";
                    break;
                case 1:
                    stautss = "退款";
                    break;
                case 3:
                    stautss = "完成";
                    break;
                case 2:
                    stautss = "已处理";
                    break;
            }

        } else {
            switch (status) {
                case -1:
                    stautss = "未付款";
                    break;
                case 0:
                    stautss = "已付款";
                    break;
                case 1:
                    stautss = "退款中";
                    break;
                case 3:
                    stautss = "完成";
                    break;
                case 2:
                    stautss = "待确认";
                    break;
            }
        }
        tvTitle.setText(stautss);
    }
    private void initData() {
        adapter_listView_orderList = new Adapter_ListView_OrderList(OrderStautsActivity.this, list, isbusiness);
        myListView.setAdapter(adapter_listView_orderList);

    }
}
