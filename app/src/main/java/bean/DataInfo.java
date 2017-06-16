package bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/25.
 */
public class DataInfo {

    public String error;
    public ArrayList<Info> results;

    public class Info {
        public String _id;
        public String url;
    }
}
