package api;

import bean.DataInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2016/6/25.
 */
public interface Api {
    //http://gank.io/api/data/福利/5/1
    @GET("api/data/福利/{pageCount}/{pageIndex}")
    Call<DataInfo> getData(@Path("pageCount") int pageCount, @Path("pageIndex") int pageIndex);
}
