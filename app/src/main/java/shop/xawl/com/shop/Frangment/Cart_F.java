package shop.xawl.com.shop.Frangment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import shop.xawl.com.shop.Model.Item_cart;
import shop.xawl.com.shop.R;

public class Cart_F extends Fragment {
    private AllBaby_F allBaby_F;
    private boolean isDel = true;
    private View view;
    private List<Item_cart> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.cart_f, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        allBaby_F = new AllBaby_F();
        FragmentTransaction ft = this.getFragmentManager().beginTransaction();
        ft.add(R.id.show_cart_view, allBaby_F);
        ft.commitAllowingStateLoss();
    }

    public void initDate() {//进行网络请求数据，进行加载
        allBaby_F.getCartData();
    }
}
