package shop.xawl.com.shop.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import shop.xawl.com.shop.MyApplication;
import shop.xawl.com.shop.R;
import shop.xawl.com.shop.Adapter.Adpater_GridView_Image;
import shop.xawl.com.shop.http.HttpUtil;
import shop.xawl.com.shop.tool.ImageTool;

/**
 * Created by Administrator on 2016/8/27.
 */
public class SendGoodsActivity extends AppCompatActivity {
    private GridView gridView;
    private Adpater_GridView_Image adpater_gridView_image;
    private RelativeLayout select_item;
    private RelativeLayout select_item_price;
    private ArrayList<Map<String, Object>> ilist = new ArrayList<>();
    private int START_Key = 10;
    String stCname;
    int iCid = 1;
    EditText etTitle;
    EditText etphone;
    EditText etlocation;
    EditText etprice;
    EditText etText;
    TextView tvLeve;
    String gkey = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_goods);
        if (gridView == null) {
            HashMap<String, Object> map = new HashMap<>();
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.upload);
            map.put("icon", bitmap);
            ilist.add(map);
        }
        select_item = (RelativeLayout) findViewById(R.id.select_category2);
        select_item_price = (RelativeLayout) findViewById(R.id.select_price1);
        select_item_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etprice.requestFocus();
                etprice.setText("");
            }
        });


        select_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SendGoodsActivity.this, CategoryActivity.class);
                startActivityForResult(intent, START_Key);
            }
        });
        initEdit();
        initGrid();
        initSend();
    }

    private void initSend() {
        findViewById(R.id.goods_Send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });
    }

    /**
     * 提交数据
     */
    private void submitData() {
        gkey = UUID.randomUUID().toString();
        String stTitle = etTitle.getText().toString();
        String stText = etText.getText().toString();
        String stphone = etphone.getText().toString();
        String stlocation = etlocation.getText().toString();
        String price = etprice.getText().toString();
        if (Checkdata(stTitle, stText, stphone, stlocation, price)) {
            Map map = new HashMap<String, Object>();
            map.put("gname", stTitle);
            map.put("gprice", price);
            map.put("cid", "" + iCid);
            map.put("bid", "" + MyApplication.business.getBid());
            map.put("gcontent", stText);
            map.put("glocation", stlocation);
            map.put("gphone", stphone);
            map.put("bname", MyApplication.business.getBname());
            map.put("cname", stCname);
            map.put("gkey", gkey);
            final String url = HttpUtil.getApi_path("goods/add");
            HttpUtil.post(url, map, new HttpUtil.callBlack() {
                @Override
                public void succcess(String code) {
                    //// TODO: 2016/8/30 成功跳转
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MyApplication.showToast("发布成功");
                            //跳转搜索页面
                        }
                    });
                    if (ilist.size() <= 1) {
                        return;
                    }
                    String url = HttpUtil.getApi_path("goods/upload");
                    List lists = new ArrayList();
                    for (int i = 1; i < ilist.size(); i++) {
                        Map m = (Map) ilist.get(i);
                        Bitmap bit = (Bitmap) m.get("image");
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bit.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] reslut = stream.toByteArray();
                        lists.add(reslut);
                    }
                    HttpUtil.uploadFile(url, gkey, lists, new HttpUtil.callBlack() {
                        @Override
                        public void succcess(String code) {//发帖成功
                            // TODO: 2016/8/30 成功跳转
                            Log.v("####upload", "chenggongshangchuan");
                            return;
                        }
                        @Override
                        public void fail(String code) {
                            Log.v("#####upload", "hehehehe");
                        }
                        @Override
                        public void err() {
                            Log.v("#####upload", "eeeeeee");
                        }
                    });
                }
                @Override
                public void fail(final String code) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MyApplication.showToast("发布失败");
                        }
                    });

                }
                @Override
                public void err() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MyApplication.showToast("发布失败");
                        }
                    });

                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MyApplication.showToast("数据不完整");
                }
            });

        }
    }

    /**
     * 数据检查
     *
     * @param stTitle
     * @param stText
     * @return
     */
    private boolean Checkdata(String stTitle, String stText, String stPhone, String location, String price) {
        if (TextUtils.isEmpty(stTitle)) return false;
        if (TextUtils.isEmpty(stText)) return false;
        if (TextUtils.isEmpty(stPhone)) return false;
        if (TextUtils.isEmpty(location)) return false;
        if (TextUtils.isEmpty(price)) return false;
        if (TextUtils.isEmpty(stCname)) return false;
        return true;
    }


    private void initEdit() {
        etTitle = (EditText) findViewById(R.id.goods_title);
        etphone = (EditText) findViewById(R.id.goods_phone);
        etphone.setText(MyApplication.user.getUphone());
        etlocation = (EditText) findViewById(R.id.goods_location);
        etlocation.setText(MyApplication.business.getBaddress());
        etText = (EditText) findViewById(R.id.goods_context);
        etprice = (EditText) findViewById(R.id.goods_price);
        tvLeve = (TextView) findViewById(R.id.goods_leve);
    }

    void initGrid() {
        gridView = (GridView) findViewById(R.id.gridView1);
        adpater_gridView_image = new Adpater_GridView_Image(this, ilist);
        gridView.setAdapter(adpater_gridView_image);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popuMenu();
            }
        });
    }

    /**
     * 弹出一个菜单  提示选择图片方式
     */
    private void popuMenu() {
        if (ilist.size() >= MyApplication.Image_Limt_size) {
            Toast.makeText(SendGoodsActivity.this, "上传图片数量达到上限", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(SendGoodsActivity.this).setTitle("选择操作").
                setItems(new String[]{"照相", "图库"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Log.v("###hahdaa", "onActivityResult: 正在获取返回的数据");
                            ImageTool.getByCamera(SendGoodsActivity.this);
                        } else if (which == 1) {
                            Log.v("###hahdaa", "onActivityResult: 正在获取返回的数据");
                            ImageTool.getByAlbum(SendGoodsActivity.this);
                        } else {
                            dialog.dismiss();
                        }
                    }
                }).
                setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                })

                .show();

    }

    /**
     * 处理获取图片跳转的结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("##########code", "请求嘛" + requestCode + "resultcode:" + resultCode);
        if (requestCode == START_Key && data != null) {//分类结果处理
            iCid = data.getIntExtra("cid", 0);
            stCname = data.getStringExtra("cname");
            tvLeve.setText(stCname);
            Log.v("####cid", stCname);
            return;
        }
        Log.v("#####ilistSize", "" + ilist.size());
        Uri uri = null;
        Log.v("###hahdaa", "onActivityResult1111: 正在获取返回的数据");
        if (requestCode == ImageTool.GET_BY_ALBUM) {
            if (data == null) {
                return;
            } else uri = data.getData();
        } else if (requestCode == ImageTool.GET_BY_CAMERA) {
            uri = Uri.parse(ImageTool.lsimg);
        }
        if (uri == null) return;
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            Log.v("###bitmap0", "" + w + "|" + h);
            if (w > MyApplication.image_size || h > MyApplication.image_size) {
                if (w > h) {
                    h = h * 500 / w;
                    w = MyApplication.image_size;
                    Log.v("###bitmap0", "" + w + "|" + h);
                } else {
                    w = w * 500 / h;
                    h = MyApplication.image_size;
                    Log.v("###bitmap", "" + w + "|" + h);
                }
            }
            Bitmap b = ThumbnailUtils.extractThumbnail(bitmap, w, h);
            Bitmap b1 = ThumbnailUtils.extractThumbnail(b, w / 10, h / 10);
            if (b != null) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("icon", b1);
                map.put("image", b);
                ilist.add(map);
                adpater_gridView_image.notifyDataSetChanged();
                Log.v("####", "onActivityResult: " + ilist.size());
                gridView.setAdapter(adpater_gridView_image);
            } else {
                Log.v("####fragement", "图片为空");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
