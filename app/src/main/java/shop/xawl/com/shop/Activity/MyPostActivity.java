package shop.xawl.com.shop.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import shop.xawl.com.shop.Frangment.AllPost;
import shop.xawl.com.shop.R;

/**
 * Created by Administrator on 2016/9/16.
 */
public class MyPostActivity extends AppCompatActivity {
    private View view;
    private AllPost allPost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypost_main);
        initView();
    }

    private void initView() {
        allPost = new AllPost();
        getFragmentManager().beginTransaction().add(R.id.show_post_view, allPost).commitAllowingStateLoss();
    }
}
