package ru.vixter.themoviedbsimpleclient.ui;

import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import ru.vixter.themoviedbsimpleclient.R;

/**
 * Created by vixter on 17.01.16.
 */
public class CardViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;
    public SimpleDraweeView imageView;

    public CardViewHolder(LinearLayout item) {
        super(item);
        this.textView = (TextView) item.findViewById(R.id.card_view_textview);
        this.imageView = (SimpleDraweeView) item.findViewById(R.id.card_view_image);
    }

}
