package shop.xawl.com.shop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import shop.xawl.com.shop.Model.LeveMenu;
import shop.xawl.com.shop.MyApplication;
import shop.xawl.com.shop.R;
import shop.xawl.com.shop.Adapter.Adapter_ListView_category;
import shop.xawl.com.shop.Adapter.MyListView;

/**
 * Created by Administrator on 2016/8/26.
 */
public class CategoryActivity extends AppCompatActivity {
    private MyListView listView;
    private Adapter_ListView_category adapter_ListView_category;
    private List list;
    private int type=10;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);
        initUI();
    }

    protected  void initUI(){
        list=new ArrayList();
        list.addAll(MyApplication.leveMenus);
        listView = (MyListView) findViewById(R.id.category_list);
        adapter_ListView_category = new Adapter_ListView_category(this, list);
        listView.setAdapter(adapter_ListView_category);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LeveMenu leve= (LeveMenu) list.get(position);
                list.clear();
                list.addAll(leve.getChildren());
                if(list.size()==0){
                    Intent intent=new Intent();
                    intent.putExtra("cname",leve.getCname());
                    intent.putExtra("cid",leve.getCid());
                    setResult(type,intent);
                    finish();
                }else{
                    adapter_ListView_category.notifyDataSetChanged();
                }
            }
        });
        initView();
    }
    private void initView() {
    }
}
