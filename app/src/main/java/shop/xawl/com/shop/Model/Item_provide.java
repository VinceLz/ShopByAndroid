package shop.xawl.com.shop.Model;

import java.io.Serializable;

/**
 * Created by doter on 2016/8/11.
 */
public class Item_provide implements Serializable{


    /**
     * bid :
     * bname :
     * cid :
     * cname : 四级
     * gcontent :
     * gdate : 2016-08-09 01:48:35.0
     * gid : 38
     * gimage : /img/3f11f9d8-ba26-4c09-b534-2f9dd7a07c8ae3fba28a-d124-4e5f-9a59-a4bee5135fe5.png,/img/defc9b8e-35b3-4d3d-85f8-d5769c05b4429544caca-1c5b-4243-871c-5de9015a1175.png,/img/f43aa5ac-33c5-4074-9692-1d3082527ffe9182fbc9-e5f8-4708-b077-1fc197ff563f.png
     * gkey :
     * glocation :
     * gname : 12455
     * gphone : 18309225255
     * gprice : 1245.00
     * gscore :
     * status : 0
     * sale :售量
     */

    private int bid;
    private String bname;//商家名称
    private int cid;
    private String cname;//类别
    private String gcontent;//服务信息
    private String gdate;//
    private int gid;
    private String gimage[];
    private String gkey;//上传图片
    private String glocation;
    private String gname;
    private String gphone;
    private double gprice;
    private String gscore;//评分
    private int status;
    private int sale;



    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getGcontent() {
        return gcontent;
    }

    public void setGcontent(String gcontent) {
        this.gcontent = gcontent;
    }

    public String getGdate() {
        return gdate;
    }

    public void setGdate(String gdate) {
        this.gdate = gdate;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String[] getGimage() {
        return gimage;
    }

    public void setGimage(String[] gimage) {
        this.gimage = gimage;
    }

    public String getGkey() {
        return gkey;
    }

    public void setGkey(String gkey) {
        this.gkey = gkey;
    }

    public String getGlocation() {
        return glocation;
    }

    public void setGlocation(String glocation) {
        this.glocation = glocation;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getGphone() {
        return gphone;
    }

    public void setGphone(String gphone) {
        this.gphone = gphone;
    }

    public double getGprice() {
        return gprice;
    }

    public void setGprice(double gprice) {
        this.gprice = gprice;
    }

    public String getGscore() {
        return gscore;
    }

    public void setGscore(String gscore) {
        this.gscore = gscore;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Item_provide{" +
                "bid=" + bid +
                ", bname='" + bname + '\'' +
                ", cid=" + cid +
                ", cname='" + cname + '\'' +
                ", gcontent='" + gcontent + '\'' +
                ", gdate='" + gdate + '\'' +
                ", gid=" + gid +
                ", gimage='" + gimage + '\'' +
                ", gkey='" + gkey + '\'' +
                ", glocation='" + glocation + '\'' +
                ", gname='" + gname + '\'' +
                ", gphone='" + gphone + '\'' +
                ", gprice=" + gprice +
                ", gscore='" + gscore + '\'' +
                ", status=" + status +
                ", sale=" + sale +
                '}';
    }
}
