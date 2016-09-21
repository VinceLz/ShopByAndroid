package shop.xawl.com.shop.Model;

/**
 * Created by doter on 2016/8/10.
 */
public class User {
    private Integer uid;
    private String ulogin;//登陆名
    private String uphone;
    private String uaddress;
    private String ulongitude;//经度
    private String uname;//用户名
    private String bkey;
    private String uprovince;//省
    private String ucity;//市
    private String ulatitude;//纬度
    private String udate;
    private String ulastlogin;
    private String uemail;
    private Integer status;
    private String rname;
    private Integer rid;
    private String uimage;
    private String pwd;
    private String encode;

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUimage() {
        if (uimage == null) {
            return "";
        }
        return uimage;
    }

    public void setUimage(String uimage) {
        this.uimage = uimage;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUlogin() {
        return ulogin;
    }

    public void setUlogin(String ulogin) {
        this.ulogin = ulogin;
    }

    public String getUphone() {
        return uphone;
    }

    public void setUphone(String uphone) {
        this.uphone = uphone;
    }

    public String getUaddress() {
        return uaddress;
    }

    public void setUaddress(String uaddress) {
        this.uaddress = uaddress;
    }

    public String getUlongitude() {
        return ulongitude;
    }

    public void setUlongitude(String ulongitude) {
        this.ulongitude = ulongitude;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getBkey() {
        return bkey;
    }

    public void setBkey(String bkey) {
        this.bkey = bkey;
    }

    public String getUprovince() {
        return uprovince;
    }

    public void setUprovince(String uprovince) {
        this.uprovince = uprovince;
    }

    public String getUcity() {
        return ucity;
    }

    public void setUcity(String ucity) {
        this.ucity = ucity;
    }

    public String getUlatitude() {
        return ulatitude;
    }

    public void setUlatitude(String ulatitude) {
        this.ulatitude = ulatitude;
    }

    public String getUdate() {
        return udate;
    }

    public void setUdate(String udate) {
        this.udate = udate;
    }

    public String getUlastlogin() {
        return ulastlogin;
    }

    public void setUlastlogin(String ulastlogin) {
        this.ulastlogin = ulastlogin;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", ulogin='" + ulogin + '\'' +
                ", uphone='" + uphone + '\'' +
                ", uaddress='" + uaddress + '\'' +
                ", ulongitude='" + ulongitude + '\'' +
                ", uname='" + uname + '\'' +
                ", bkey='" + bkey + '\'' +
                ", uprovince='" + uprovince + '\'' +
                ", ucity='" + ucity + '\'' +
                ", ulatitude='" + ulatitude + '\'' +
                ", udate='" + udate + '\'' +
                ", ulastlogin='" + ulastlogin + '\'' +
                ", uemail='" + uemail + '\'' +
                ", status=" + status +
                ", rname='" + rname + '\'' +
                ", rid=" + rid +
                '}';
    }
}
