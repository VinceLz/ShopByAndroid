package shop.xawl.com.shop.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by doter on 2016/8/6.
 */
public class LeveMenu implements Serializable{
    private List<LeveMenu> children=new ArrayList<>();
    private String cname;
    private String desc;
    private int cid;
    private int pid;



    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public List<LeveMenu> getChildren() {
        return children;
    }

    public void setChildren(List<LeveMenu> children) {
        this.children = children;
    }





}
