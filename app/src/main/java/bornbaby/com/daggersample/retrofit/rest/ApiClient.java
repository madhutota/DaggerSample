package bornbaby.com.daggersample.retrofit.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by madhu on 27-Nov-17.
 */

public class ApiClient {
    //http://api.themoviedb.org/3/movie/top_rated?api_key=b201ceac99a36657751fbc21f2840e9e
    public static final String BASE_URL = "http://api.themoviedb.org/3/";

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;


    }


}
