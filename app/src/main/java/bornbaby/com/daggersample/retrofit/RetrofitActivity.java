package bornbaby.com.daggersample.retrofit;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import bornbaby.com.daggersample.R;
import bornbaby.com.daggersample.retrofit.model.Movie;
import bornbaby.com.daggersample.retrofit.model.MovieResponse;
import bornbaby.com.daggersample.retrofit.rest.ApiClient;
import bornbaby.com.daggersample.retrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RetrofitActivity extends AppCompatActivity implements MoviesAdapter.MoviesAdapterListener {

    ;

    private static final String TAG = RetrofitActivity.class.getSimpleName();
    private RecyclerView recycler_view;
    private LinearLayout ll_main;
    private final static String API_KEY = "b201ceac99a36657751fbc21f2840e9e";

    private MoviesAdapter moviesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        inItUi();
    }

    private void inItUi() {
        ll_main = findViewById(R.id.ll_main);
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        if (API_KEY.isEmpty()) {

        }

        ApiInterface apiInterface = ApiClient.getRetrofitClient().create(ApiInterface.class);


        Call<MovieResponse> call = apiInterface.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                try {
                    final List<Movie> movies = response.body().getResults();
                   // recycler_view.setAdapter(new MoviesAdapter(recycler_view, movies,R.layout.item_recycler_view, getApplicationContext(),RetrofitActivity.this));

                    moviesAdapter = new MoviesAdapter(recycler_view, movies,R.layout.item_recycler_view, getApplicationContext(),RetrofitActivity.this);

                    recycler_view.setAdapter(moviesAdapter);
                    moviesAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                        @Override
                        public void onLoadMore() {
                            if (movies.size() <= 20){

                            }
                        }
                    });
                    Log.d(TAG, "Number Of Movies Received" + movies.size());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

                Log.d(TAG, t.toString());

            }
        });


    }

    @Override
    public void onMovieSelected(Movie movie) {
      //  Toast.makeText(this, ""+movie.getTitle(), Toast.LENGTH_SHORT).show();

       Snackbar.make(ll_main,"Selected Movie "+movie.getTitle(),Snackbar.LENGTH_INDEFINITE).show();

    }
}
