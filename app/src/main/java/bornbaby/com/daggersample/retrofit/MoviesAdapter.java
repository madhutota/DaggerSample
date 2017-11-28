package bornbaby.com.daggersample.retrofit;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import bornbaby.com.daggersample.R;
import bornbaby.com.daggersample.retrofit.model.Movie;

/**
 * Created by madhu on 27-Nov-17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Movie> movies;
    private int rowLayout;
    private Context context;
    private MoviesAdapterListener moviesAdapterListener;

    private OnLoadMoreListener onLoadMoreListener;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private boolean isLoading;
    private int lastVisibleItem, totalItemCount;
    private int visibleThreshold = 5;


    public MoviesAdapter(RecyclerView recyclerView, List<Movie> movies, int rowLayout, Context context, MoviesAdapterListener moviesAdapterListener) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
        this.moviesAdapterListener = moviesAdapterListener;


        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
          /*  @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }*/

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return movies.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;


        private MovieViewHolder(View v) {
            super(v);
            moviesLayout = (LinearLayout) v.findViewById(R.id.movies_layout);
            movieTitle = (TextView) v.findViewById(R.id.title);
            data = (TextView) v.findViewById(R.id.subtitle);
            movieDescription = (TextView) v.findViewById(R.id.description);
            rating = (TextView) v.findViewById(R.id.rating);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moviesAdapterListener.onMovieSelected(movies.get(getAdapterPosition()));

                }
            });


        }

    }

    public class OnLoadMoreViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        private OnLoadMoreViewHolder(View itemView) {

            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_bar);


        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
            return new MovieViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new OnLoadMoreViewHolder(view);

        }
        return null;


       /* View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);*/
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MovieViewHolder) {
            MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
            movieViewHolder.movieTitle.setText(movies.get(position).getTitle());
            movieViewHolder.data.setText(movies.get(position).getReleaseDate());
            movieViewHolder.movieDescription.setText(movies.get(position).getOverview());
            movieViewHolder.rating.setText(movies.get(position).getVoteAverage().toString());
        } else if (holder instanceof OnLoadMoreViewHolder) {
            OnLoadMoreViewHolder onLoadMoreViewHolder = (OnLoadMoreViewHolder) holder;
            onLoadMoreViewHolder.progressBar.setIndeterminate(true);

        }

    }


   /* @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        holder.movieTitle.setText(movies.get(position).getTitle());
        holder.data.setText(movies.get(position).getReleaseDate());
        holder.movieDescription.setText(movies.get(position).getOverview());
        holder.rating.setText(movies.get(position).getVoteAverage().toString());
    }
*/
    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    /*onItem Click Listner For RecyclerView*/

    public interface MoviesAdapterListener {
        void onMovieSelected(Movie movie);

    }


    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}