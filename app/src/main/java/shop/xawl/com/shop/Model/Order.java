package shop.xawl.com.shop.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by doter on 2016/9/4.
 */
public class Order implements Serializable {

    /**
     * oid : 34e1c745-521d-46e1-93c0-d5b847bba03b
     * status : 0
     * total : 33
     * itemlist : []
     * allTotal : 0
     * uid : 3
     * ordertime : 2016-09-04 09:27:16
     * bid : 41
     * oinspect : 0
     * bname : 45456
     * uname : 五把
     * currentprice : 33
     * bphone:18309225255
     */

    private String oid;
    private int status;
    private float total;
    private float allTotal;
    private int uid;
    private String ordertime;
    private int bid;
    private int oinspect;
    private String bname;
    private String uname;
    private int currentprice;
    private String uphone;

    private String itemlist;

    public String getUphone() {
        return uphone;
    }

    public void setUphone(String uphone) {
        this.uphone = uphone;
    }

    public void setItemlist(String itemlist) {
        this.itemlist = itemlist;
    }

    public String getBphone() {
        return bphone;
    }

    public void setBphone(String bphone) {
        this.bphone = bphone;
    }

    private String bphone;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getAllTotal() {
        return allTotal;
    }

    public void setAllTotal(float allTotal) {
        this.allTotal = allTotal;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public int getOinspect() {
        return oinspect;
    }

    public void setOinspect(int oinspect) {
        this.oinspect = oinspect;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public int getCurrentprice() {
        return currentprice;
    }

    public void setCurrentprice(int currentprice) {
        this.currentprice = currentprice;
    }

    public String getItemlist() {
        return itemlist;
    }


}
