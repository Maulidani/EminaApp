package emina.eminaapp.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login.php")
    Call<ModelApiResponse> postLogin(@Field("email") String email,
                                     @Field("password") String pass);

    @FormUrlEncoded
    @POST("register.php")
    Call<ModelApiResponse> postRegister(@Field("email") String email,
                                        @Field("password") String pass);

    @GET("lihat_product.php")
    Call<ModelApiResponse> getProducts();

    @GET("lihat_product_category.php")
    Call<ModelApiResponse> getProductCategory(@Query("category") int categoryid);

    @GET("lihat_news.php")
    Call<ModelApiResponse> getNews();
}
