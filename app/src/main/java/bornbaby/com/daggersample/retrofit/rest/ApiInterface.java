package bornbaby.com.daggersample.retrofit.rest;

import bornbaby.com.daggersample.retrofit.model.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by madhu on 27-Nov-17.
 */

public interface ApiInterface {
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String api_key);

    @GET("movie/{id}")
    Call<MovieResponse> getTopRatedMoviesByID(@Path("id") int id, @Query("api_key") String api_key);
}
