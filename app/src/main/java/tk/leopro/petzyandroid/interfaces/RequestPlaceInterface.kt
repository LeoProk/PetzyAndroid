package tk.leopro.petzyandroid.interfaces


import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable
import tk.leopro.petzyandroid.pojo.GooglePredictionData

//interface for retrofit

interface RequestPlaceInterface {

    @GET("json?")
    fun getJSON(@Query("query") query: String, @Query("location") location: String, @Query("radius") radius: String, @Query("language") language: String, @Query("key") key: String): Observable<GooglePredictionData>
}