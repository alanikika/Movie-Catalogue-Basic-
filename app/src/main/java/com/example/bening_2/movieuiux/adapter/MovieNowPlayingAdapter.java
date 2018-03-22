package com.example.bening_2.movieuiux.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bening_2.movieuiux.MovieItems;
import com.example.bening_2.movieuiux.R;

import java.util.ArrayList;

/**
 * Created by Bening_2 on 3/6/2018.
 */

public class MovieNowPlayingAdapter extends RecyclerView.Adapter<MovieNowPlayingAdapter.MovieNowPlayingViewHolder >{
    private ArrayList<MovieItems> listMovieNowPlayingItems;

    private Context context;

    public MovieNowPlayingAdapter(Context context) {
        this.context = context;
    }

    private MovieNowPlayingAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(MovieNowPlayingAdapter.OnItemClickListener listener){
        mListener = listener;
    }

    public ArrayList<MovieItems> getListMovieNowPlayingItems(){
        return listMovieNowPlayingItems;
    }

    public void setListMovieNowPlayingAdapter(ArrayList<MovieItems> listMovieNowPlayingItems){
        this.listMovieNowPlayingItems = listMovieNowPlayingItems;
    }

    @Override
    public MovieNowPlayingAdapter.MovieNowPlayingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show_upcoming_movie, parent, false);
        MovieNowPlayingAdapter.MovieNowPlayingViewHolder viewHolder = new MovieNowPlayingAdapter.MovieNowPlayingViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieNowPlayingAdapter.MovieNowPlayingViewHolder holder, int position) {
        holder.tvTitle.setText(getListMovieNowPlayingItems().get(position).getTitle());
        holder.tvLead.setText(getListMovieNowPlayingItems().get(position).getLead());
        holder.tvDate.setText(getListMovieNowPlayingItems().get(position).getDate());

        Glide.with(context)
                .load(getListMovieNowPlayingItems().get(position).getPhoto())
                .override(150, 220)
                .crossFade()
                .into(holder.ivPhoto);
    }

    @Override
    public int getItemCount() {
        return getListMovieNowPlayingItems().size();
    }

    public class MovieNowPlayingViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle, tvLead, tvDate;
        ImageView ivPhoto;
        CardView cardView;
        public MovieNowPlayingViewHolder (View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_movie_title);
            tvLead = (TextView) itemView.findViewById(R.id.tv_movie_lead);
            tvDate = (TextView) itemView.findViewById(R.id.tv_movie_date);
            ivPhoto = (ImageView) itemView.findViewById(R.id.img_movie_photo);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
