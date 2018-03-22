package com.example.bening_2.movieuiux;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieResultDetailFragment extends Fragment implements View.OnClickListener{

    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_LEAD= "EXTRA_LEAD";
    public static final String EXTRA_DATE = "EXTRA_DATE";
    public static final String EXTRA_PHOTO = "EXTRA_PHOTO";

    private TextView tvTitle, tvLead, tvDate, tvPhoto;
    private ImageView ivPhoto;


    public MovieResultDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_result_detail, container, false);
        tvTitle = (TextView) view.findViewById(R.id.tv_movie_tittle);
        tvLead = (TextView) view.findViewById(R.id.tv_movie_lead);
        tvDate = (TextView) view.findViewById(R.id.tv_movie_date);
        ivPhoto = (ImageView) view.findViewById(R.id.img_movie_photo);
        ivPhoto.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String title = getArguments().getString(EXTRA_TITLE);
        String lead = getArguments().getString(EXTRA_LEAD);
        String date = getArguments().getString(EXTRA_DATE);
        String photo = getArguments().getString(EXTRA_PHOTO);

        Log.d("-----NILAI DETAIL----", ""+title + "//" + lead + "//" + date + "//" + photo);

        tvTitle.setText(title);
        tvLead.setText(lead);
        tvDate.setText(date);

        Glide.with(this)
                .load(photo)
                .override(150,150)
                .crossFade()
                .into(ivPhoto);

    }

    @Override
    public void onClick(View v) {

    }
}
