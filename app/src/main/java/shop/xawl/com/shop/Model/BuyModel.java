package shop.xawl.com.shop.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by doter on 2016/9/4.
 */
public class BuyModel  implements Serializable{
    List<Item_cart> cartlist;
    public List<Item_cart> getCartList() {
        if(cartlist==null)
        cartlist=new ArrayList<>();
        return cartlist;
    }

}
