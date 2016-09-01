package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.joke.JokeActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements OnTaskCompleted{
    Button mJokeButton;
    ProgressBar mProgressBar;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mJokeButton = (Button) root.findViewById(R.id.btn_joke);

        mProgressBar = (ProgressBar) root.findViewById(R.id.progressBar);

        mJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });

        return root;
    }

    public void loadData() {
        mProgressBar.setVisibility(View.VISIBLE);

        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(this);
        endpointsAsyncTask.execute();
    }

    @Override
    public void onTaskCompleted(String result) {
        Intent intent = new Intent(getActivity(), JokeActivity.class);
        intent.putExtra(JokeActivity.JOKE_KEY, result);

        mProgressBar.setVisibility(View.GONE);

        startActivity(intent);
    }
}
