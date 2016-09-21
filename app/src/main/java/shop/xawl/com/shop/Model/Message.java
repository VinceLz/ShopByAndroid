package shop.xawl.com.shop.Model;

/**
 * Created by doter on 2016/9/13.
 */
public class Message {

    /**
     * gid : 64
     * gname : 嘻嘻
     * message : 您的商家已经解封
     * mid : 15
     * pdate : 2016-09-12 08:52:09.0
     * send_name : 系统管理员
     */

    private int gid;
    private String gname;
    private String message;
    private int mid;
    private String pdate;
    private String send_name;
    private int status;
    private int type;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getGid() {
        return gid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }

    public String getSend_name() {
        return send_name;
    }

    public void setSend_name(String send_name) {
        this.send_name = send_name;
    }
}
