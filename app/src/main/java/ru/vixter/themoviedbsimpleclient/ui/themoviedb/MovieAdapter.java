package ru.vixter.themoviedbsimpleclient.ui.themoviedb;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.vixter.themoviedbsimpleclient.R;
import ru.vixter.themoviedbsimpleclient.model.themoviedb.Movie;

/**
 * Created by vixter on 17.01.16.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.CardViewHolder> {


    private ArrayList<Movie> movieArrayList;
    private LayoutInflater inflater;

    public MovieAdapter(Context context){
        movieArrayList = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.card_view_textview) public TextView textView;
        @Bind(R.id.card_view_image) public SimpleDraweeView imageView;

        public CardViewHolder(final View item) {
            super(item);
            ButterKnife.bind(this, item);
        }

    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CardViewHolder(inflater.inflate(R.layout.recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Movie item = movieArrayList.get(position);
        holder.textView.setText(item.getTitle());

        // TODO: 15.01.16 check how this work
        holder.imageView.setImageURI(Uri.withAppendedPath(Constants.basePosterUrl, item.getPoster_path()));
    }

    public void addAll(@NonNull Collection collection){
        int curSize = getItemCount();
        movieArrayList.addAll(collection);
        notifyItemRangeInserted(curSize, getItemCount());
    }

    // TODO: 15.01.16 replace with getItem(position)
    public Movie getItem(int position){
        return movieArrayList.get(position);
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

}
