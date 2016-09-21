package shop.xawl.com.shop.Frangment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import shop.xawl.com.shop.Activity.LoginActivity;
import shop.xawl.com.shop.MyApplication;
import shop.xawl.com.shop.R;
import shop.xawl.com.shop.Activity.SendGoodsActivity;
import shop.xawl.com.shop.Activity.SendPostActivity;

/**
 * Created by Administrator on 2016/8/26.
 */
public class Send extends Fragment {
    private LinearLayout post;
    private LinearLayout goods;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.send, null);
        initView(view);
        return view;
    }


    private void initView(View view) {
        post = (LinearLayout) view.findViewById(R.id.send_post);
        goods = (LinearLayout) view.findViewById(R.id.send_goods);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  //发布需求
                //添加发布界面
                //先判断是否登陆
                if (!MyApplication.login) {
                    //没有登陆
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(intent);
                    return;
                } else {
                    Intent intent = new Intent(getActivity(), SendPostActivity.class);
                    getActivity().startActivity(intent);
                }

            }
        });
        goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //发布服务
                //添加发布界面
                if (!MyApplication.isLogin()) {
                    //没有登陆
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(intent);
                    return;
                }
                if (MyApplication.isBusiness) {
                    Intent intent = new Intent(getActivity(), SendGoodsActivity.class);
                    getActivity().startActivity(intent);
                } else {
                    MyApplication.showToast("您不是商家，赶快去申请吧");
                }

            }
        });
    }

    public void addFragment(Fragment fragment) {
        FragmentTransaction ft = fragment.getFragmentManager().beginTransaction();
        ft.add(R.id.show_layout, fragment);
        ft.commitAllowingStateLoss();
    }

    public void removeFragment(Fragment fragment) {
        FragmentTransaction ft = fragment.getFragmentManager().beginTransaction();
        ft.remove(fragment);
        ft.commitAllowingStateLoss();
    }

    public void showFragment(Fragment fragment) {
        FragmentTransaction ft = fragment.getFragmentManager().beginTransaction();
        //动画，暂时隐藏
        //  ft.setCustomAnimations(R.anim.cu_push_right_in, R.anim.cu_push_left_out);
        ft.show(fragment);
        ft.commitAllowingStateLoss();
    }

    public void initDate() {

    }
}
