package shop.xawl.com.shop;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import shop.xawl.com.shop.Model.Business;
import shop.xawl.com.shop.Model.LeveMenu;
import shop.xawl.com.shop.Model.NullProperties;
import shop.xawl.com.shop.Model.User;
import shop.xawl.com.shop.http.HttpUtil;
import shop.xawl.com.shop.tool.CyptoUtils;
import shop.xawl.com.shop.tool.StringUtils;

/**
 * Created by doter on 2016/9/4.
 */
public class MyApplication extends BaseApplication {
    public static final int PAGE_SIZE = 10;// 默认分页大小
    private static MyApplication instance;
    public static User user;
    public static Business business;
    public static boolean login;
    public static boolean isBusiness;

    static public Context context = null;
    public static List<LeveMenu> leveMenus;

    static public int Image_Limt_size = 5;//默认允许上传图片数量
    static public int image_size = 500;//限制上传图片的宽高最大值为500

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
        initLogin();
        initLeveMenus();
        //UIHelper.sendBroadcastForNotice(this);
    }

    //填充登陆
    private void initLogin() {
        User user1 = getLoginUser();
        if (null != user1 && user1.getUid() > 0) {
            //跑线程进行登陆
            backgroundLogin(user1, getProperty("user.ulogin"), getProperty("user.pwd"), getProperty("user.encode"));//后台登陆
        } else {
            this.cleanLoginInfo();
            this.cleanBusinessInfo();
        }
    }

    public void backgroundLogin(final User user, String ulogin, String upassword, String encode) {
        Map map = new HashMap();
        map.put("ulogin", ulogin);
        map.put("upassword", upassword);
        map.put("encode", encode);
        HttpUtil.post(HttpUtil.getApi_path("/user/blogin"), map, new HttpUtil.callBlack() {
            @Override
            public void succcess(String code) {
                Log.d("#######3", "后台登陆成功过了");
                MyApplication.user = user;
                setLogin(true);
                if (!"0".equals(user.getBkey())) {
                    //是商家
                    Business business = getLoginBusiness();
                    if (business != null && business.getBid() > 0) {
                        MyApplication.isBusiness = true;
                        MyApplication.business = business;
                    } else {
                        cleanBusinessInfo();
                    }
                }
            }

            @Override
            public void fail(String code) {
                setLogin(false);
                cleanBusinessInfo();
                cleanLoginInfo();
            }

            @Override
            public void err() {
                setLogin(false);
                cleanBusinessInfo();
                cleanLoginInfo();
            }
        });
    }


    /**
     * 清除登录信息
     */
    public void cleanLoginInfo() {
        this.login = false;
        removeProperty("user.uid", "user.uname", "user.ulogin", "user.uphone",
                "user.uaddress", "user.ulongitude", "user.bkey",
                "user.uprovince", "user.encode",
                "user.ucity", "user.lastitude",
                "user.rid", "user.rnama", "user.udate",
                "user.uemail", "user.ulastlogin", "user.status");
    }

    public void cleanBusinessInfo() {
        this.isBusiness = false;
        removeProperty("business.bid", "business.status", "business.rname"
                , "business.baddress", "business.bdate", "business.bkey", "business.bname", "business.bscore", "business.companyname", "business.rid");
    }

    public void removeProperty(String... key) {
        AppConfig.getAppConfig(this).remove(key);
    }

    private void init() {
        //初始化网络
        new HttpUtil(this);
        //后期考虑缓存地址的配置
    }

    private void initLeveMenus() {
        String url = HttpUtil.getLevel_path();
        HttpUtil.get(url, new HttpUtil.callBlack() {
            @Override
            public void succcess(String code) {
                leveMenus = new ArrayList<LeveMenu>();
                dealJsonp(JSON.parseObject(code).getJSONArray("root"), leveMenus);
            }

            @Override
            public void fail(String code) {
                leveMenus = null;
            }

            @Override
            public void err() {
                leveMenus = null;
            }
        }, true);
    }

    public static synchronized BaseApplication context() {
        return (BaseApplication) _context;
    }

    public static List<LeveMenu> getLeveMenus() {
        return leveMenus;
    }

    protected static void dealJsonp(JSONArray array, List<LeveMenu> menu) {
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            LeveMenu leveMenu = new LeveMenu();
            leveMenu.setCname(object.getString("cname"));
            leveMenu.setDesc(object.getString("desc"));
            leveMenu.setCid(object.getInteger("cid"));
            leveMenu.setPid(object.getInteger("pid"));
            JSONArray arr = object.getJSONArray("children");
            if (null != arr) {
                dealJsonp(arr, leveMenu.getChildren());
            }
            menu.add(leveMenu);
        }
    }

    public User getLoginUser() {
        User user = new User();
        user.setUid((StringUtils.toInt(getProperty("user.uid"), 0)));
        user.setUname(getProperty("user.uname"));
        user.setUlogin(getProperty("user.ulogin"));
        user.setUphone(getProperty("user.uphone"));
        user.setUaddress(getProperty("user.uaddress"));
        user.setUlongitude(getProperty("user.ulongitude"));
        user.setBkey(getProperty("user.bkey"));
        user.setUimage(getProperty("user.uimage"));
        user.setUimage(getProperty("user.uimage"));
        user.setUprovince(getProperty("user.uprovince"));
        user.setUcity(getProperty("user.ucity"));
        user.setUlatitude(getProperty("user.ulatitude"));
        user.setRid(StringUtils.toInt(getProperty("user.rid"), 0));
        user.setRname(getProperty("user.rname"));
        user.setUdate(getProperty("user.udate"));
        user.setEncode(getProperty("user.encode"));
        user.setUemail(getProperty("user.uemail"));
        user.setUlastlogin(getProperty("user.ulastlogin"));
        user.setStatus(StringUtils.toInt(getProperty("user.status"), 0));
        return user;
    }

    public String getProperty(String key) {
        String res = AppConfig.getAppConfig(this).get(key);
        return res;
    }

    public Business getLoginBusiness() {
        Business b = new Business();
        b.setBid(StringUtils.toInt(getProperty("business.bid"), 0));
        b.setStatus(StringUtils.toInt(getProperty("business.status")));
        b.setRname(getProperty("business.rname"));
        b.setBaddress(getProperty("business.baddress"));
        b.setBdate(getProperty("business.bdate"));
        b.setBname(getProperty("business.bname"));
        b.setBscore(getProperty("business.bscore"));
        b.setCompanyname(getProperty("business.companyname"));
        b.setRid(StringUtils.toInt(getProperty("business.rid"), 0));
        return b;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public boolean containsProperty(String key) {
        Properties props = getProperties();
        return props.containsKey(key);
    }

    public Properties getProperties() {
        return AppConfig.getAppConfig(this).get();
    }

    public void setProperties(Properties ps) {
        AppConfig.getAppConfig(this).set(ps);
    }

    public void setProperty(String key, String value) {
        AppConfig.getAppConfig(this).set(key, value);
    }

    /**
     * 获取App唯一标识
     *
     * @return
     */
    public String getAppId() {
        String uniqueID = getProperty(AppConfig.CONF_APP_UNIQUEID);
        if (StringUtils.isEmpty(uniqueID)) {
            uniqueID = UUID.randomUUID().toString();
            setProperty(AppConfig.CONF_APP_UNIQUEID, uniqueID);
        }
        return uniqueID;
    }


    /**
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }


    /**
     * 保存登录信息
     *
     * @param
     * @param
     */
    @SuppressWarnings("serial")
    public void saveUserInfo(final User user) {
        this.login = true;
        setProperties(new NullProperties() {
            {
                setProperty("user.uid", String.valueOf(user.getUid()));
                setProperty("user.uname", user.getUname());
                setProperty("user.ulogin", user.getUlogin());// 用户头像-文件名
                setProperty("user.uphone", user.getUphone());
                setProperty("user.pwd",
                        CyptoUtils.encode(user.getEncode(), user.getPwd()));
                setProperty("user.uaddress", user.getUaddress());
                setProperty("user.ulongitude",
                        user.getUlongitude());
                setProperty("user.bkey", user.getBkey());
                setProperty("user.uimage", user.getUimage());
                setProperty("user.uprovince", user.getUprovince());
                setProperty("user.ucity",
                        user.getUcity());
                setProperty("user.encode", user.getEncode());
                setProperty("user.ulatitude", user.getUlatitude());
                setProperty("user.rid",
                        String.valueOf(user.getRid()));// 是否记住我的信息
                setProperty("user.rname", user.getRname());
                setProperty("user.udate", user.getUdate());
                setProperty("user.uemail", user.getUemail());
                setProperty("user.ulastlogin", user.getUlastlogin());
                setProperty("user.status", String.valueOf(user.getStatus()));
            }
        });
    }

    @SuppressWarnings("serial")
    public void saveBusinessInfo(final Business business) {
        this.isBusiness = true;
        setProperties(new NullProperties() {
            {
                setProperty("business.bid", String.valueOf(business.getBid()));
                setProperty("business.status", String.valueOf(business.getStatus()));
                setProperty("business.rname", business.getRname());
                setProperty("business.baddress", business.getBaddress());
                setProperty("business.bdate", business.getBdate());
                setProperty("business.bname", business.getBname());
                setProperty("business.bscore", business.getBscore());
                setProperty("business.companyname", business.getCompanyname());
                setProperty("business.rid", String.valueOf(business.getRid()));
            }
        });

    }

    /**
     * 更新用户信息
     *
     * @param user
     */
    @SuppressWarnings("serial")
    public void updateUserInfo(final User user) {
        setProperties(new NullProperties() {
            {
                setProperty("user.uphone", user.getUphone());
                setProperty("user.uimage", user.getUimage());// 用户头像-文件名
                setProperty("user.uaddress",
                        user.getUaddress());
                setProperty("user.encode",
                        user.getEncode());
                setProperty("user.longitude", user.getUlongitude());
                setProperty("user.uname", user.getUname());
                setProperty("user.uprovince",
                        user.getUprovince());
                setProperty("user.ucity", user.getUcity());
                setProperty("user.ulatitude", user.getUlatitude());
                setProperty("user.rid", user.getRid() + "");
                setProperty("user.ulastlogin", user.getUlongitude());
                setProperty("user.rname", user.getRname());
            }
        });
    }

    /**
     * 更新商家信息
     *
     * @param
     */
    @SuppressWarnings("serial")
    public void updateUserInfo(final Business business) {
        setProperties(new NullProperties() {
            {
                setProperty("business.rname", business.getRname());
                setProperty("business.baddress", business.getBaddress());// 用户头像-文件名
                setProperty("business.bname",
                        business.getBname());
                setProperty("business.bscore", business.getBscore());
                setProperty("business.companyname", business.getCompanyname());
                setProperty("business.rid",
                        business.getRid() + "");
            }
        });
    }

    public static boolean isLogin() {
        return login;
    }

    public boolean isBusiness() {
        return isBusiness;
    }

    /**
     * 用户注销
     */
    public void Logout() {
        cleanLoginInfo();
        this.cleanCookie();
        this.login = false;
        this.isBusiness = false;
        // Intent intent = new Intent(SyncStateContract.Constants.INTENT_ACTION_LOGOUT);
        //  sendBroadcast(intent);
    }


    /**
     * 清除保存的缓存
     */
    public void cleanCookie() {
        removeProperty(AppConfig.CONF_COOKIE);
    }


    public static void setLogin(boolean login) {
        MyApplication.login = login;
    }

}
