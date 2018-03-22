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
 * Created by Bening_2 on 3/5/2018.
 */

public class MovieUpcomingAdapter extends RecyclerView.Adapter<MovieUpcomingAdapter.MovieUpcomingViewHolder >{

    private ArrayList<MovieItems> listMovieUpcomingItems;

    private Context context;

    public MovieUpcomingAdapter(Context context) {
        this.context = context;
    }

    private MovieUpcomingAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(MovieUpcomingAdapter.OnItemClickListener listener){
        mListener = listener;
    }


    public ArrayList<MovieItems> getListMovieUpcomingItems(){
        return listMovieUpcomingItems;
    }

    public void setListMovieUpcomingAdapter(ArrayList<MovieItems> listMovieUpcomingItems){
        this.listMovieUpcomingItems = listMovieUpcomingItems;
    }

    @Override
    public MovieUpcomingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show_upcoming_movie, parent, false);
        MovieUpcomingViewHolder viewHolder = new MovieUpcomingViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieUpcomingViewHolder holder, int position) {
        holder.tvTitle.setText(getListMovieUpcomingItems().get(position).getTitle());
        holder.tvLead.setText(getListMovieUpcomingItems().get(position).getLead());
        holder.tvDate.setText(getListMovieUpcomingItems().get(position).getDate());

        Glide.with(context)
                .load(getListMovieUpcomingItems().get(position).getPhoto())
                .override(150, 220)
                .crossFade()
                .into(holder.ivPhoto);
    }

    @Override
    public int getItemCount() {
        return getListMovieUpcomingItems().size();
    }

    public class MovieUpcomingViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle, tvLead, tvDate;
        ImageView ivPhoto;
        CardView cardView;
        public MovieUpcomingViewHolder (View itemView) {
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