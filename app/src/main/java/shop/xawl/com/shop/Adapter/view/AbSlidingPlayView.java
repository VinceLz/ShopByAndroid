
package shop.xawl.com.shop.Adapter.view;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;


/**
 */
@SuppressLint("HandlerLeak")
public class AbSlidingPlayView extends LinearLayout {


    private Context context;

    /**
     * The m view pager.
     */
    private AbInnerViewPager mViewPager;

    /**
     * The page line layout.
     */
    private LinearLayout pageLineLayout;

    /**
     * The layout params pageLine.
     */
    public LayoutParams pageLineLayoutParams = null;

    /**
     * The i.
     */
    private int count, position;

    /**
     * The hide image.
     */
    private Bitmap displayImage, hideImage;

    /**
     * The m on item click listener.
     */
    private AbOnItemClickListener mOnItemClickListener;

    /**
     * The m ab change listener.
     */
    private AbOnChangeListener mAbChangeListener;

    /**
     * The m ab scrolled listener.
     */
    private AbOnScrollListener mAbScrolledListener;

    /**
     * The m ab touched listener.
     */
    private AbOnTouchListener mAbOnTouchListener;

    /**
     * The layout params ff.
     */
    public LayoutParams layoutParamsFF = null;

    /**
     * The layout params fw.
     */
    public LayoutParams layoutParamsFW = null;

    /**
     * The layout params wf.
     */
    public LayoutParams layoutParamsWF = null;

    /**
     * The m list views.
     */
    private ArrayList<View> mListViews = null;

    /**
     * The m ab view pager adapter.
     */
    private AbViewPagerAdapter mAbViewPagerAdapter = null;

    /**
     * �����ĵ㸸View
     */
    private LinearLayout mPageLineLayoutParent = null;

    /**
     * The page line horizontal gravity.
     */
    private int pageLineHorizontalGravity = Gravity.RIGHT;

    /**
     * ���ŵķ���
     */
    private int playingDirection = 0;

    /**
     * ���ŵĿ���
     */
    private boolean play = false;
    /**
     * ���ŵļ��ʱ��
     */
    private int sleepTime = 5000;
    /**
     * ���ŷ���ʽ��1˳�򲥷ź�0���ز��ţ�
     */
    private int playType = 1;

    /**
     * ����һ��AbSlidingPlayView
     *
     * @param context
     */
    public AbSlidingPlayView(Context context) {
        super(context);
        initView(context);
    }

    /**
     * ��xml��ʼ����AbSlidingPlayView
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public AbSlidingPlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }


    /**
     * ��������ʼ�����View
     *
     * @param context
     * @throws
     */
    public void initView(Context context) {
        this.context = context;
        //LayoutParams 2个参数   1 是宽  2 是高
        layoutParamsFF = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParamsFW = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParamsWF = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        pageLineLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        this.setOrientation(LinearLayout.VERTICAL);
        this.setBackgroundColor(Color.rgb(255, 255, 255)); //设置轮播图的当前颜色被白色

        RelativeLayout mRelativeLayout = new RelativeLayout(context); // 相对布局

        mViewPager = new AbInnerViewPager(context);
        // �ֶ�������ViewPager,�����fragment�������setId()��������һ��id

        // �����ĵ�
        mPageLineLayoutParent = new LinearLayout(context);
        mPageLineLayoutParent.setPadding(0, -5, 0, -5); //下面的几个圆形
        pageLineLayout = new LinearLayout(context);
        pageLineLayout.setPadding(15, 1, 15, 1);
        pageLineLayout.setVisibility(View.INVISIBLE);
        mPageLineLayoutParent.addView(pageLineLayout, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        lp1.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        lp1.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        mRelativeLayout.addView(mViewPager, lp1);

        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        mRelativeLayout.addView(mPageLineLayoutParent, lp2);
        addView(mRelativeLayout, layoutParamsFW);

        //�õ��������ͼƬ��Դ�ļ�
        displayImage = getBitmapFormSrc("play_display.png");
        hideImage = getBitmapFormSrc("play_hide.png");

        mListViews = new ArrayList<View>();
        mAbViewPagerAdapter = new AbViewPagerAdapter(context, mListViews);
        mViewPager.setAdapter(mAbViewPagerAdapter);
        mViewPager.setFadingEdgeLength(0);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                makesurePosition();
                onPageSelectedCallBack(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                onPageScrolledCallBack(position);
            }

        });

    }


    private Bitmap getBitmapFormSrc(String name) {
        Bitmap bitmap = null;

        try {
            InputStream is = getResources().getAssets().open(name);
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {

        }
        return bitmap;
    }


    public void creatIndex() {

        pageLineLayout.removeAllViews();
        mPageLineLayoutParent.setHorizontalGravity(pageLineHorizontalGravity);
        pageLineLayout.setGravity(Gravity.CENTER);
        pageLineLayout.setVisibility(View.VISIBLE);
        count = mListViews.size();
        for (int j = 0; j < count; j++) {
            ImageView imageView = new ImageView(context);
            pageLineLayoutParams.setMargins(5, 5, 5, 5);
            imageView.setLayoutParams(pageLineLayoutParams);
            if (j == 0) {
                imageView.setImageBitmap(displayImage);
            } else {
                imageView.setImageBitmap(hideImage);
            }
            pageLineLayout.addView(imageView, j);
        }
    }


    public void makesurePosition() {
        position = mViewPager.getCurrentItem();
        for (int j = 0; j < count; j++) {
            if (position == j) {
                ((ImageView) pageLineLayout.getChildAt(position)).setImageBitmap(displayImage);
            } else {
                ((ImageView) pageLineLayout.getChildAt(j)).setImageBitmap(hideImage);
            }
        }
    }

    public void addView(View view) {
        mListViews.add(view);
        if (view instanceof AbsListView) {
        } else {
            view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onClick(position);
                    }
                }
            });
            view.setOnTouchListener(new OnTouchListener() {

                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if (mAbOnTouchListener != null) {
                        mAbOnTouchListener.onTouch(event);
                    }
                    return false;
                }
            });
        }

        mAbViewPagerAdapter.notifyDataSetChanged();
        creatIndex();
    }


    public void addViews(List<View> views) {
        mListViews.clear();
        mListViews.addAll(views);
        for (View view : views) {
            //点击事件 暂时不需要
//			view.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					if (mOnItemClickListener != null) {
//						mOnItemClickListener.onClick(position);
//					}
//				}
//			});

            view.setOnTouchListener(new OnTouchListener() {

                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if (mAbOnTouchListener != null) {
                        mAbOnTouchListener.onTouch(event);
                    }
                    return false;
                }
            });
        }
        mAbViewPagerAdapter.notifyDataSetChanged();
        creatIndex();
    }

    /**
     * ������ɾ��ɲ�����ͼ.
     */
    @Override
    public void removeAllViews() {
        mListViews.clear();
        mAbViewPagerAdapter.notifyDataSetChanged();
        creatIndex();
    }


    private void onPageScrolledCallBack(int position) {
        if (mAbScrolledListener != null) {
            mAbScrolledListener.onScroll(position);
        }

    }


    private void onPageSelectedCallBack(int position) {
        if (mAbChangeListener != null) {
            mAbChangeListener.onChange(position);
        }

    }


    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {

                ShowPlay();
                if (play) {
                    handler.postDelayed(runnable, sleepTime);
                }
            }
        }

    };


    private Runnable runnable = new Runnable() {
        public void run() {
            if (mViewPager != null) {
                handler.sendEmptyMessage(0);
            }
        }
    };


    public void startPlay() {
        if (handler != null) {
            play = true;
            handler.postDelayed(runnable, sleepTime);
        }
    }

    public void stopPlay() {
        if (handler != null) {
            play = false;
            handler.removeCallbacks(runnable);
        }
    }


    public void setOnItemClickListener(AbOnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    public void setOnPageChangeListener(AbOnChangeListener abChangeListener) {
        mAbChangeListener = abChangeListener;
    }


    public void setOnPageScrolledListener(AbOnScrollListener abScrolledListener) {
        mAbScrolledListener = abScrolledListener;
    }


    public void setOnTouchListener(AbOnTouchListener abOnTouchListener) {
        mAbOnTouchListener = abOnTouchListener;
    }

    public void setPageLineImage(Bitmap displayImage, Bitmap hideImage) {
        this.displayImage = displayImage;
        this.hideImage = hideImage;
        creatIndex();

    }


    public ViewPager getViewPager() {
        return mViewPager;
    }


    public int getCount() {
        return mListViews.size();
    }

    public void setPageLineHorizontalGravity(int horizontalGravity) {
        pageLineHorizontalGravity = horizontalGravity;
    }


    public void setParentScrollView(ScrollView parentScrollView) {
        this.mViewPager.setParentScrollView(parentScrollView);
    }


    public void setParentListView(ListView parentListView) {
        this.mViewPager.setParentListView(parentListView);
    }


    public void setPageLineLayoutBackground(int resid) {
        pageLineLayout.setBackgroundResource(resid);
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }


    public void setPlayType(int playType) {
        this.playType = playType;
    }


    public void ShowPlay() {

        int count = mListViews.size();

        int i = mViewPager.getCurrentItem();
        switch (playType) {
            case 0:

                if (playingDirection == 0) {
                    if (i == count - 1) {
                        playingDirection = -1;
                        i--;
                    } else {
                        i++;
                    }
                } else {
                    if (i == 0) {
                        playingDirection = 0;
                        i++;
                    } else {
                        i--;
                    }
                }
                break;
            case 1:

                if (i == count - 1) {
                    i = 0;
                } else {
                    i++;
                }

                break;

            default:
                break;
        }

        mViewPager.setCurrentItem(i, true);
    }

}
