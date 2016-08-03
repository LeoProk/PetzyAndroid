package tk.leopro.petzyandroid.interfaces;


import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import tk.leopro.petzyandroid.pojo.GooglePredictionData;

//interface for retrofit

public interface RequestPlaceInterface {

    @GET("json?")
    Observable<GooglePredictionData> getJSON(@Query("query") String query, @Query("location") String location
            , @Query("radius") String radius, @Query("language") String language, @Query("key") String key);
}