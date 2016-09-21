package shop.xawl.com.shop.Frangment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.io.Serializable;

import shop.xawl.com.shop.MyApplication;
import shop.xawl.com.shop.R;
import shop.xawl.com.shop.Adapter.Adapter_ListView_cateogry_f;

/**
 * @author qdwang
 */
public class Category extends Fragment implements
        OnItemClickListener {
    private ListView listView;
    private Adapter_ListView_cateogry_f adapter;
    private MyFragment myFragment;
    public static int mPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.category_f, null);
        initView(view);
        return view;
    }

    /**
     * 初始化view
     */
    private void initView(View view) {
        // TODO Auto-generated method stub
        listView = (ListView) view.findViewById(R.id.listview); //左侧固定菜单
        if (MyApplication.leveMenus != null) {
            adapter = new Adapter_ListView_cateogry_f(getActivity());
            listView.setAdapter(adapter); //给listview填充数据
            listView.setOnItemClickListener(this);
            //创建MyFragment对象
//            myFragment = new MyFragment();
//            FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
//            fragmentTransaction.setCustomAnimations(R.anim.cu_push_right_in, R.anim.cu_push_left_out);
//            fragmentTransaction.replace(R.id.fragment_container, myFragment);
//            //通过bundle传值给MyFragment
//            Bundle bundle = new Bundle();
//            bundle.putString(MyFragment.TAG, MyApplication.getLeveMenus().get(mPosition).getCname());
//            bundle.putSerializable(MyFragment.TAG, (Serializable) MyApplication.getLeveMenus().get(0).getChildren());
//            myFragment.setArguments(bundle);
//            fragmentTransaction.commit();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
//        //拿到当前位置
//        mPosition = position;
//        //即使刷新adapter
//        adapter.notifyDataSetChanged();
//        myFragment = new MyFragment();
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.anim.cu_push_left_out, R.anim.cu_push_right_in);
//        fragmentTransaction.replace(R.id.fragment_container, myFragment);
//        Log.d("####", "点击了" + position);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(MyFragment.TAG, (Serializable) MyApplication.getLeveMenus().get(position).getChildren());
//        myFragment.setArguments(bundle);
//        fragmentTransaction.commit();
    }

    public void initDate() {


    }
}

