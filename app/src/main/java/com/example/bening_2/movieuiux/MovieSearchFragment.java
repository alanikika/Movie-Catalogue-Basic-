package com.example.bening_2.movieuiux;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bening_2.movieuiux.adapter.MovieSearchAdapter;
import com.example.bening_2.movieuiux.loader.MovieSearchLoader;

import org.json.JSONException;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 */
public class MovieSearchFragment extends Fragment implements MovieSearchAdapter.OnItemClickListener, View.OnClickListener,
        LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {

    private EditText editTextQuery;
    private Button btnSearch;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    private ArrayList<MovieItems> list;

    public  ArrayList<MovieItems> getListMovie(){
        return list;
    }

    static final String EXTRAS_TITLE = "EXTRAS_TITLE";

    public MovieSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_search, container, false);
        editTextQuery = (EditText) view.findViewById(R.id.edit_query);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        btnSearch = (Button) view.findViewById(R.id.btn_search);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_search_result);

        btnSearch.setOnClickListener(this);

        progressBar.setVisibility(View.INVISIBLE);

        MovieSearchAdapter movieSearchAdapter = new MovieSearchAdapter(getActivity());
        movieSearchAdapter.setOnItemClickListener(MovieSearchFragment.this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //showRecyclerList();
        String query = editTextQuery.getText().toString().trim();

        if(query.isEmpty()) return;

        Bundle mBundle = new Bundle();
        mBundle.putString(EXTRAS_TITLE, query);
        getLoaderManager().initLoader(0, mBundle, this);
    }

    @Override
    public android.support.v4.content.Loader<ArrayList<MovieItems>> onCreateLoader(int id, Bundle args) {
        String title = "";
        if (args != null){
            title = args.getString(EXTRAS_TITLE);
            Log.d("TITLE", ""+title);
        }

        return new MovieSearchLoader(getActivity(),title);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {

        list = new ArrayList<>();
        try {
            list.addAll(MovieSearchLoader.getListFilm());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("KELAS MOVIE SEARCH", "ERROR");
        }

        if (list.size() != 0)
            showRecyclerList();
        else
            Toast.makeText(getActivity(), "Data Tidak Ditemukan", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onLoaderReset(android.support.v4.content.Loader<ArrayList<MovieItems>> loader) {
        resetRecyclerListData();
    }

    private void showRecyclerList(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MovieSearchAdapter movieSearchAdapter = new MovieSearchAdapter(getActivity());
        movieSearchAdapter.setListMovieAdapter(list);
        recyclerView.setAdapter(movieSearchAdapter);
        movieSearchAdapter.setOnItemClickListener(MovieSearchFragment.this);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void resetRecyclerListData(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MovieSearchAdapter movieSearchAdapter = new MovieSearchAdapter(getActivity());
        movieSearchAdapter.setListMovieAdapter(list);
        recyclerView.setAdapter(movieSearchAdapter);

        movieSearchAdapter.setOnItemClickListener(MovieSearchFragment.this);

        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_search){
            String query = editTextQuery.getText().toString().trim();

            if(query.isEmpty()) return;

            progressBar.setVisibility(View.VISIBLE);

            Bundle mBundle = new Bundle();
            mBundle.putString(EXTRAS_TITLE, query);
            getLoaderManager().restartLoader(0, mBundle, MovieSearchFragment.this);
        }
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
