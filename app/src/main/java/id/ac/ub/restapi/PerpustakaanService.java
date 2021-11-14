package id.ac.ub.restapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PerpustakaanService {
    @GET("buku")
    Call<List<Buku>> listBuku();

    @GET("buku/{id}")
    Call<Buku> getBuku(@Path("id") String id);

    @FormUrlEncoded
    @POST("buku")
    Call<Buku> create(@Field("judul") String judul,
                      @Field("deskripsi") String deskripsi);
}
