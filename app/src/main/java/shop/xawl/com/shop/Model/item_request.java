package shop.xawl.com.shop.Model;

/**
 * Created by doter on 2016/8/10.
 */
public class item_request {

    /**
     * cid : 2
     * cname : 2
     * content : 啊实打实大师
     * pdate : 2016-07-28 16:15:26.0
     * pid : 3
     * status : 2
     * title : 2
     * uid : 2
     * uname : 2
     * uphone : 2
     */

    private int cid;
    private String cname;
    private String content;
    private String pdate;
    private int pid;
    private int status;
    private String title;
    private int uid;
    private String uname;
    private String uphone;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUphone() {
        return uphone;
    }

    public void setUphone(String uphone) {
        this.uphone = uphone;
    }

    public item_request() {
    }

    public item_request(int cid, String cname, String content, String pdate, int pid, int status, String title, int uid, String uname, String uphone) {
        this.cid = cid;
        this.cname = cname;
        this.content = content;
        this.pdate = pdate;
        this.pid = pid;
        this.status = status;
        this.title = title;
        this.uid = uid;
        this.uname = uname;
        this.uphone = uphone;
    }

    @Override
    public String toString() {
        return "item_request{" +
                "cid=" + cid +
                ", cname='" + cname + '\'' +
                ", content='" + content + '\'' +
                ", pdate='" + pdate + '\'' +
                ", pid=" + pid +
                ", status=" + status +
                ", title='" + title + '\'' +
                ", uid=" + uid +
                ", uname='" + uname + '\'' +
                ", uphone='" + uphone + '\'' +
                '}';
    }
}
