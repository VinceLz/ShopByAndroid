package shop.xawl.com.shop.Frangment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import shop.xawl.com.shop.Activity.MessageActivity;
import shop.xawl.com.shop.Activity.SerachActivity;
import shop.xawl.com.shop.Model.HomeImage;
import shop.xawl.com.shop.Model.Home_content;
import shop.xawl.com.shop.Adapter.Adapter_GridView_Home;
import shop.xawl.com.shop.Adapter.Adapter_ListView_Home;
import shop.xawl.com.shop.Adapter.MyListView;
import shop.xawl.com.shop.Adapter.view.AbOnItemClickListener;
import shop.xawl.com.shop.Adapter.view.AbSlidingPlayView;
import shop.xawl.com.shop.R;
import shop.xawl.com.shop.Activity.WareActivity;
import shop.xawl.com.shop.http.HttpUtil;

/**
 * Created by Administrator on 2016/8/21.
 */
public class Home extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private TextView tv_top_title; //搜索
    private GridView gridView_classify; //类型
    //  private GridView my_gridView_hot; //热门市场
    private Adapter_GridView_Home adapter_GridView_classify;
    private MyListView myListView;  //内容区
    //private Adapter_GridView_hot adapter_GridView_hot;
    private Adapter_ListView_Home adapter_listView_home;
    private AbSlidingPlayView viewPager; //轮播图
    //  private int[] pic_path_classify = {R.mipmap.menu_guide_2, R.mipmap.menu_guide_3, R.mipmap.menu_guide_4, R.mipmap.menu_guide_5, R.mipmap.menu_guide_6, R.mipmap.menu_guide_1, R.mipmap.menu_guide_8};
    //private String[] tname = {"律师服务", "律师服务", "律师服务", "律师服务", "律师服务", "律师服务", "律师服务"};
    List<HomeImage> top;
    //private int[] pic_path_hot = {R.mipmap.menu_1, R.mipmap.menu_1, R.mipmap.menu_2, R.mipmap.menu_3, R.mipmap.menu_4, R.mipmap.menu_5, R.mipmap.menu_6};
    private ArrayList<View> allListView;
    List<Home_content> content;
    private int[] resId1 = {R.mipmap.show_m1, R.mipmap.menu_viewpager_1, R.mipmap.menu_viewpager_2, R.mipmap.menu_viewpager_4, R.mipmap.menu_viewpager_5};
    private List<HomeImage> resId;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_f, null);
        initDate(true);
        initView(view); //对girdView进行数据填充
        return view;
    }

    private void initView(View view) {
        //内信
        view.findViewById(R.id.iv_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MessageActivity.class));
            }
        });
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.home_refresh);
        tv_top_title = (TextView) view.findViewById(R.id.tv_top_title);//搜索
        swipeRefreshLayout.setOnRefreshListener(this);
        tv_top_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //关键字搜索
                Intent intent = new Intent(getActivity(), WareActivity.class);
                startActivity(intent);
            }
        });
        myListView = (MyListView) view.findViewById(R.id.home_listview);
        gridView_classify = (GridView) view.findViewById(R.id.my_gridview);
        //    my_gridView_hot = (GridView) view.findViewById(R.id.my_gridview_hot);
        gridView_classify.setSelector(new ColorDrawable(Color.TRANSPARENT));
        //    my_gridView_hot.setSelector(new ColorDrawable(Color.TRANSPARENT));
        //   adapter_GridView_hot = new Adapter_GridView_hot(getActivity(), pic_path_hot);
        //     my_gridView_hot.setAdapter(adapter_GridView_hot);
        //为了使用notifyDataSetChanged 所以需要给adapt配置同一个数据源对象

        resId = new ArrayList<HomeImage>();
        top = new ArrayList<HomeImage>();
        content = new ArrayList<Home_content>();

        viewPager = (AbSlidingPlayView) view.findViewById(R.id.viewPager_menu);
        viewPager.setPlayType(1);
        viewPager.setSleepTime(3000); //设置滚动时间为3秒
        gridView_classify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //启动搜索界面
                Intent i = new Intent(getActivity(), SerachActivity.class);
                Log.d("########3", top.get(arg2).getCid() + "");
                i.putExtra("cid", top.get(arg2).getCid());
                startActivity(i);
            }
        });

    }

    private void initViewPager() {

        if (allListView != null) {
            allListView.clear();
            allListView = null;
        }
        allListView = new ArrayList<View>();

        for (int i = 0; i < resId.size(); i++) {

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.pic_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.pic_item);
            HttpUtil.picasso.with(getActivity()).load(HttpUtil.getImage_path(resId.get(i).getImage())).into(imageView);
            // imageView.setImageResource(resId1[i]);
            allListView.add(view);
        }
        Log.d("###", "lISTvIEW" + allListView.size());
        viewPager.addViews(allListView);
        viewPager.startPlay();

        //暂时对轮播图不加点击事件
//        viewPager.setOnItemClickListener(new AbOnItemClickListener() {
//            @Override
//            public void onClick(int position) {
//
//            }
//        });
    }

    //负责网络请求，加载数据
    public void initDate(boolean isCache) {
        String stUrl = HttpUtil.getApi_path("home/index");
        HttpUtil.get(stUrl, new HttpUtil.callBlack() {
            @Override
            public void succcess(String code) {
                Log.d("####1", code + "");
                JSONObject object = JSON.parseObject(code);
                List<HomeImage> image1 = JSON.parseArray(object.getString("image"), HomeImage.class);//首页轮播图
                if (image1 != null) {
                    resId.clear();
                    resId.addAll(image1);
                    image1 = null;
                }
                List<HomeImage> top1 = JSON.parseArray(object.getString("top"), HomeImage.class);
                Log.d("########3", object.getString("top"));
                if (top1 != null) {
                    top.clear();
                    top.addAll(top1);
                    top1 = null;
                }
                List<Home_content> content1 = JSON.parseArray(object.getString("content"), Home_content.class);
                if (content1 != null) {
                    content.clear();
                    content.addAll(content1);
                    content1 = null;
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (adapter_listView_home == null) {
                            adapter_listView_home = new Adapter_ListView_Home(Home.this.content, getActivity());
                            myListView.setAdapter(adapter_listView_home);
                        } else {
                            adapter_listView_home.notifyDataSetChanged();
                        }
                        if (adapter_GridView_classify == null) {
                            adapter_GridView_classify = new Adapter_GridView_Home(getActivity(), Home.this.top);
                            gridView_classify.setAdapter(adapter_GridView_classify);
                        } else {
                            adapter_GridView_classify.notifyDataSetChanged();
                        }
                        if (resId.size() != 0)
                            initViewPager();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void fail(String code) {

            }

            @Override
            public void err() {

            }
        }, isCache);


//        HttpUtil.post(stUrl, null, new HttpUtil.callBlack() {
//            @Override
//            public void succcess(String code) {
//                JSONObject object = JSON.parseObject(code);
//                resId = JSON.parseArray(object.getString("image"), HomeImage.class); //首页轮播图
//                Log.d("###", "轮播图" + resId.size());
//                top = JSON.parseArray(object.getString("top"), HomeImage.class);
//                content = JSON.parseArray(object.getString("content"), Home_content.class);
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (adapter_listView_home == null) {
//                            adapter_listView_home = new Adapter_ListView_Home(content, getActivity());
//                            myListView.setAdapter(adapter_listView_home);
//                        } else {
//                            adapter_listView_home.notifyDataSetChanged();
//                        }
//                        if (adapter_GridView_classify == null) {
//                            adapter_GridView_classify = new Adapter_GridView_Home(getActivity(), top);
//                            gridView_classify.setAdapter(adapter_GridView_classify);
//                        } else {
//                            adapter_GridView_classify.notifyDataSetChanged();
//                        }
//                        if (resId.size() != 0)
//                            initViewPager();
//                    }
//                });
//
//            }
//
//            @Override
//            public void fail(String code) {
//
//            }
//
//            @Override
//            public void err() {
//
//            }
//        });

    }

    @Override
    public void onRefresh() {
        //下拉刷新
        initDate(false);
    }
}
