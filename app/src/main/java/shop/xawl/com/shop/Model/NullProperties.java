package shop.xawl.com.shop.Model;

import java.util.Properties;

/**
 * Created by Administrator on 2016/9/13.
 */
public class NullProperties extends Properties {
    @Override
    public Object setProperty(String name, String value) {
        if (value == null) {
            return super.setProperty(name, "");
        } else {
            return super.setProperty(name, value);
        }

    }
}
