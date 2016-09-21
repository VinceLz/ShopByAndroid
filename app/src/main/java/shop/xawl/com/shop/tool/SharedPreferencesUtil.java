package shop.xawl.com.shop.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by dq_da on 2016/5/25
 * 用于保存各种信息
 */
public class SharedPreferencesUtil {

    private SharedPreferences mySharedP;

    public SharedPreferencesUtil(Context c) {
        this.mySharedP =c.getSharedPreferences("MyTrade", Context.MODE_PRIVATE);
    }

    /**
     * 保存登录信息
     * @param userName :　用户昵称
     * @param userPwd  　：　用户密码
     * @param userImg : 用户图片名
     */
    public void saveUserInfo(String userName, String userPwd, String userImg) {
        SharedPreferences.Editor editor = mySharedP.edit();
        editor.putString("userName", userName);
        editor.putString("userPwd", userPwd);
        editor.putString("userImg", userImg);
        editor.apply();
    }

    public boolean getUserLoginStatus() {
        boolean loginStatus = mySharedP.getBoolean("isLogin", false);
        Log.i("SharedPreferencesUtil", "getUserLoginStatus : " + loginStatus);
        return loginStatus;
    }

    public String getUserId() {
        String userId = mySharedP.getString("userId", "");
        Log.i("SharedPreferencesUtil", "getUserId : " + userId);
        return userId;
    }

    public String getUserName() {
        String userName = mySharedP.getString("userName", "");
        Log.i("SharedPreferencesUtil", "getUserName : " + userName);
        return userName;
    }

    public String getUserPwd() {
        String userPwd = mySharedP.getString("userPwd", "");
        Log.i("SharedPreferencesUtil", "getUserPwd : " + userPwd);
        return userPwd;
    }

    public String getUserImg() {
        String userImg = mySharedP.getString("userImg", "");
        Log.i("SharedPreferencesUtil", "getUserImg : " + userImg);
        return userImg;
    }

    public String getLastUserName() {
        String lastUserName = mySharedP.getString("lastUserName", "");
        Log.i("SharedPreferencesUtil", "getLastUserName : " + lastUserName);
        return lastUserName;
    }

}