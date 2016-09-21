package shop.xawl.com.shop.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import shop.xawl.com.shop.Model.Order;
import shop.xawl.com.shop.R;
import shop.xawl.com.shop.Adapter.Adapter_ListView_Order;
import shop.xawl.com.shop.Adapter.MyListView;

public class OrderListActivity extends AppCompatActivity {
    private MyListView listview;
    private List<Order> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        listview= (MyListView) findViewById(R.id.order_listView);
        initData();
    }

    private void initData() {
        list= (List<Order>) getIntent().getSerializableExtra("order_list");
        Adapter_ListView_Order adapter=new Adapter_ListView_Order(OrderListActivity.this,list);
        listview.setAdapter(adapter);
    }

}
