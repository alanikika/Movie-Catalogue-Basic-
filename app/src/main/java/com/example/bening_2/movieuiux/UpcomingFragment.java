package com.example.bening_2.movieuiux;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bening_2.movieuiux.adapter.MovieNowPlayingAdapter;
import com.example.bening_2.movieuiux.adapter.MovieUpcomingAdapter;
import com.example.bening_2.movieuiux.loader.MovieUpcomingLoader;

import org.json.JSONException;

import java.util.ArrayList;


public class UpcomingFragment extends Fragment implements View.OnClickListener,
        LoaderManager.LoaderCallbacks<ArrayList<MovieItems>>, MovieUpcomingAdapter.OnItemClickListener{


    private ProgressBar progressBar;

    private ArrayList<MovieItems> list;

    public  ArrayList<MovieItems> getListMovie(){
        return list;
    }

    private RecyclerView recyclerView;

     public UpcomingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_search_result);progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.VISIBLE);

        MovieUpcomingAdapter movieUpcomingAdapter= new MovieUpcomingAdapter(getActivity());
        movieUpcomingAdapter.setOnItemClickListener(UpcomingFragment.this);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //showRecyclerList();

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, Bundle args) {
        return new MovieUpcomingLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {
        Log.d("+++++++++++KESINI","+++++++++++++");

        list = new ArrayList<>();
        try {
            list.addAll(MovieUpcomingLoader.getListFilm());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("KELAS MOVIE SEARCH", "ERROR");
        }

        showRecyclerList();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItems>> loader) {
        resetRecyclerListData();
    }

    private void showRecyclerList(){
        progressBar.setVisibility(View.INVISIBLE);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MovieUpcomingAdapter movieUpcomingAdapter = new MovieUpcomingAdapter(getActivity());
        movieUpcomingAdapter.setListMovieUpcomingAdapter(list);
        recyclerView.setAdapter(movieUpcomingAdapter);

        movieUpcomingAdapter.setOnItemClickListener(this);
    }

    private void resetRecyclerListData(){
        progressBar.setVisibility(View.INVISIBLE);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MovieUpcomingAdapter movieUpcomingAdapter = new MovieUpcomingAdapter(getActivity());
        movieUpcomingAdapter.setListMovieUpcomingAdapter(null);
        recyclerView.setAdapter(movieUpcomingAdapter);

        movieUpcomingAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        MovieResultDetailFragment mMovieResultDetailFragment = new MovieResultDetailFragment();
        MovieItems mMovieItems = getListMovie().get(position);
        Bundle mBundle = new Bundle();
        mBundle.putString(MovieResultDetailFragment.EXTRA_TITLE, mMovieItems.getTitle());
        mBundle.putString(MovieResultDetailFragment.EXTRA_LEAD, mMovieItems.getLead());
        mBundle.putString(MovieResultDetailFragment.EXTRA_DATE, mMovieItems.getDate());
        mBundle.putString(MovieResultDetailFragment.EXTRA_PHOTO, mMovieItems.getPhoto());
        mMovieResultDetailFragment.setArguments(mBundle);
        FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.frame_container, mMovieResultDetailFragment, MovieResultDetailFragment.class.getSimpleName());
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }
}
