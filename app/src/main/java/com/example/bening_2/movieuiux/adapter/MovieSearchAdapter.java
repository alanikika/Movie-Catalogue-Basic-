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
 * Created by Bening_2 on 3/2/2018.
 */

public class MovieSearchAdapter extends RecyclerView.Adapter<MovieSearchAdapter.MovieViewHolder>{

    private Context context;

    private ArrayList<MovieItems> listMovieItems;

    private LayoutInflater mInflater;

    public MovieSearchAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public ArrayList<MovieItems> getListMovieItems(){
        return listMovieItems;
    }

    public void setListMovieAdapter(ArrayList<MovieItems> listMovieItems){
        this.listMovieItems = listMovieItems;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_search_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.tvTitle.setText(getListMovieItems().get(position).getTitle());
        holder.tvLead.setText(getListMovieItems().get(position).getLead());
        holder.tvDate.setText(getListMovieItems().get(position).getDate());

        Glide.with(context)
                .load(getListMovieItems().get(position).getPhoto())
                .override(60, 60)
                .crossFade()
                .into(holder.ivPhoto);
    }

    @Override
    public int getItemCount() {
        return getListMovieItems().size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle, tvLead, tvDate;
        ImageView ivPhoto;
        CardView cardView;
        public MovieViewHolder(View itemView) {
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
