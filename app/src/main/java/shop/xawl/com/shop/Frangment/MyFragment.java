package shop.xawl.com.shop.Frangment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

import shop.xawl.com.shop.Adapter.Adapter_ListView_categroy_right;
import shop.xawl.com.shop.Adapter.MyListView;
import shop.xawl.com.shop.Model.LeveMenu;
import shop.xawl.com.shop.R;

public class MyFragment extends Fragment {

    public static final String TAG = "MyFragment";
    private String str;
    private MyListView myGridView;
    private Fragment MyFragment;
    private Adapter_ListView_categroy_right adapter_listView_categroy_right;
    List<LeveMenu> list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        list=(List<LeveMenu>)getArguments().getSerializable(TAG);
        View view = inflater.inflate(R.layout.myfragment, null);
        //TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        //得到数据
        myGridView = (MyListView) view.findViewById(R.id.my_listView);
        adapter_listView_categroy_right = new Adapter_ListView_categroy_right(getActivity(), list);
        myGridView.setAdapter(adapter_listView_categroy_right);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                MyFragment = new MyFragment();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.setCustomAnimations(R.anim.cu_push_left_out,R.anim.cu_push_right_in);
//                fragmentTransaction.replace(R.id.fragment_container, MyFragment);
//                Log.d("####", "点击了" + i);
//              //  Bundle bundle = new Bundle();
//               // bundle.putString(MyFragment.TAG, strs[position]);
//           //     myFragment.setArguments(bundle);
//                fragmentTransaction.commit();
            }
        });
        //	str = getArguments().getString(TAG);
        //	tv_title.setText(str);
        return view;
    }
}
