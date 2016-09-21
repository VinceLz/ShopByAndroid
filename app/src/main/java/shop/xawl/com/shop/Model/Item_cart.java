package shop.xawl.com.shop.Model;

import java.io.Serializable;

/**
 * Created by doter on 2016/9/1.
 */
public class Item_cart implements Serializable{

    /**
     * bname : 1
     * cartitem_id : 1
     * date :
     * gid : 1
     * gname : 1
     * gprice : 1
     * uid : 0
     */

    private String bname;
    private int cartitem_id;
    private String date;
    private int gid;
    private String gname;
    private float gprice;

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public int getCartitem_id() {
        return cartitem_id;
    }

    public void setCartitem_id(int cartitem_id) {
        this.cartitem_id = cartitem_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public float getGprice() {
        return gprice;
    }

    public void setGprice(float gprice) {
        this.gprice = gprice;
    }
}
