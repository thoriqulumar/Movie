package com.thor.submission2;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.thor.submission2.adapter.MovieAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {
    Button btnSearch;
    EditText edtSearchFilm;
    RecyclerView recyclerView;
    static final String EXTRA_TITLE = "extra_title";
    MovieAdapter adapter;



    public SearchFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSearch = view.findViewById(R.id.btn_search);
        edtSearchFilm = view.findViewById(R.id.et_search);
        recyclerView = view.findViewById(R.id.recycler_search);

        btnSearch.setOnClickListener(myListener);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MovieAdapter(getContext());
        recyclerView.setAdapter(adapter);

        String title = edtSearchFilm.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TITLE,title);
        getLoaderManager().initLoader(0,bundle,  this);



    }



    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String title = edtSearchFilm.getText().toString();

            if (TextUtils.isEmpty(title)){
                return;
            }

            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_TITLE,title);
            getLoaderManager().restartLoader(0,bundle,  SearchFragment.this);
        }
    };

    @NonNull
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int i, @Nullable Bundle bundle) {
        String title = "";
        if (bundle!=null){
            title = bundle.getString(EXTRA_TITLE);
        }
        return new AsyncTaskSearch(getContext(),title);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
        adapter.setListMovie(movies);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {
        adapter.setListMovie(null);
    }
}
