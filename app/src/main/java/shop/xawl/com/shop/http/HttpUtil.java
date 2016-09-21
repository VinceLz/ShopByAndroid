package shop.xawl.com.shop.http;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.ResponseBody;
import shop.xawl.com.shop.Model.KeyWord;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import shop.xawl.com.shop.MyApplication;
import shop.xawl.com.shop.tool.LocalUtil;

/**
 * Created by doter on 2016/8/8.
 */
public class HttpUtil {
    public static final String TYPE = "application/octet-stream";
    public static String server_name = "http://172.16.120.65:8080/shop/admin/category/list.action";
    public static String server = "http://172.16.120.65:8080/shop/";
    public static String server_img = "http://172.16.120.65:8080/shop";
    private static String api_path = "front/";
    private static String admin_path = "admin/";
    private static String api_end = ".action";
    private static String img_user_path = "user_img/";
    private static String img_store_path = "store_img/";
    private static String img_food_path = "food_img/";
    public static OkHttpClient client;
    private static Context context;
    public static Picasso picasso;

    public static String getApi_path(String action) {
        return server + api_path + action + api_end;
    }

    private static final OkHttpClient.Builder builder = new OkHttpClient.Builder();

    public static String getLevel_path() {
        return server + "admin/category/list.action";
    }

    public static String getImage_path(String prame) {
        return server_img + prame;
    }

    public static int POOL_SIZE = 8;

    private static ExecutorService sExecutorService;

    private static int READ_TIME_OUT = 10 * 1000;

    private static int CONNECTE_TIME_OUT = 10 * 1000;


    public HttpUtil(Context mcontext) {
        this.context = mcontext;
        //// TODO: 2016/9/8   cache
        File sdcache = context.getFilesDir();
        int cacheSize = 40 * 1024 * 1024; // 10 MiB
        builder.cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
        client = builder.connectTimeout(60, TimeUnit.SECONDS).cookieJar(new CookiesManager()).build();
        picasso = new Picasso.Builder(context)
                .downloader(new OkHttpDownLoader(client))
                .build();
        Picasso.setSingletonInstance(picasso);
    }

    static {
        sExecutorService = Executors.newFixedThreadPool(POOL_SIZE);
    }


    /**
     * post请求
     *
     * @param Url
     * @param map
     * @return
     * @throws IOException
     */
    public static void post(final String Url, final Map<String, Object> map, final callBlack callb) {
        if (callb != null) {
            Log.d("#######", "post调用了");
            sExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        FormBody.Builder body = new FormBody.Builder();
                        if (map != null) {
                            Set<String> item = map.keySet();

                            for (String str : item) {
                                Log.v("----------", str);
                                Log.v("--------++", "" + map.get(str));
                                body.add(str, "" + map.get(str));
                            }
                        }

                        FormBody formbody = body.build();
                        Request request = new Request.Builder()
                                .url(Url)
                                .post(formbody)
                                .build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                callb.err();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String stre = response.body().string();
                                Log.v("####reslut", stre);
                                JSONObject obj = null;
                                try {
                                    obj = new JSONObject(stre);
                                    int reCode = obj.getInt("status");
                                    if (KeyWord.SERVICE_SUCCESS == reCode) {
                                        callb.succcess(stre);
                                    } else {
                                        callb.fail(stre);
                                    }
                                } catch (JSONException e) {
                                    callb.fail(stre);
                                }
                            }
                        });
                    } catch (Exception e) {
                        Log.v("####POSTError", "" + e.toString());
                        callb.err();
                    }
                }

            });
        }


    }

    /**
     * 上傳文件
     *
     * @param url
     * @param gkey
     * @param files
     * @param callb
     */
    public static void uploadFile(final String url, final String gkey, final List<byte[]> files, final callBlack callb) {

        if (files == null) {
            callb.err();
            return;
        }
        sExecutorService.submit(new Runnable() {
            @Override
            public void run() {


                MultipartBody.Builder builder = new MultipartBody.Builder();
                builder.addFormDataPart("gkey", gkey);
                for (byte[] f : files) {
                    if (f.length > 0) {
                        RequestBody fileBody = RequestBody.create(MediaType.parse(TYPE), f);
                        builder.addFormDataPart("file", LocalUtil.getDateString() + ".jpg", fileBody);
                    }
                }

                RequestBody requestBody = builder.build();
                Request requestPostFile = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                client.newCall(requestPostFile).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        callb.fail(e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        callb.succcess(response.body().string());
                    }
                });
            }
        });
    }

    public interface callBlack {
        void succcess(String code);

        void fail(String code);

        void err();
    }


    private class CookiesManager implements CookieJar {

        private final PersistentCookieStore cookieStore = new PersistentCookieStore(context);

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies != null && cookies.size() > 0) {
                for (Cookie item : cookies) {
                    cookieStore.add(url, item);

                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url);
            return cookies;
        }

    }

    private class OkHttpDownLoader implements Downloader {

        OkHttpClient mClient = null;

        public OkHttpDownLoader(OkHttpClient client) {
            mClient = client;
        }

        @Override
        public Response load(Uri uri, int networkPolicy) throws IOException {
            CacheControl.Builder builder = new CacheControl.Builder();
            if (networkPolicy != 0) {
                if (NetworkPolicy.isOfflineOnly(networkPolicy)) {
                    builder.onlyIfCached();
                } else {
                    if (!NetworkPolicy.shouldReadFromDiskCache(networkPolicy)) {
                        builder.noCache();
                    }
                    if (!NetworkPolicy.shouldWriteToDiskCache(networkPolicy)) {
                        builder.noStore();
                    }
                }
            }
            Request request = new Request.Builder()
                    .cacheControl(builder.build())
                    .url(uri.toString())
                    .build();
            okhttp3.Response response = mClient.newCall(request).execute();
            return new Response(response.body().byteStream(), false, response.body().contentLength());
        }

        @Override
        public void shutdown() {

        }
    }


    public static void get(final String url, final callBlack call, final boolean isCache) {
        sExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                try {

                    Request request = new Request.Builder().url(url).build();

                    if (!isCache) {
                        //走网络
                        request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
                    }
                    okhttp3.Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String str = response.body().string();
                        com.alibaba.fastjson.JSONObject object = JSON.parseObject(str);
                        Integer code = object.getInteger("status");
                        if (code == 1) {
                            call.succcess(str);
                        } else if (code == 0) {
                            call.fail(str);
                        }
                    } else {
                        call.err();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    //同步post
    public static String postMap(String url, Map<String, String> map) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();

        //遍历map
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.add(entry.getKey(), entry.getValue().toString());
            }
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder().url(url).post(body).build();
        Response execute = client.newCall(request).execute();
        if (execute.isSuccessful()) {
            return execute.body().string();
        } else {
            return null;
        }
    }

}






