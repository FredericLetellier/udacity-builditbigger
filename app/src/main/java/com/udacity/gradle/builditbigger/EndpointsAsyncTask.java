package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.joke.JokeActivity;
import com.joke.endpoint.backend.myApi.MyApi;

import java.io.IOException;

/**
 * udacity-builditbigger
 * Created on 30/08/2016 by Espace de travail.
 */
public class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
    private static MyApi myApiService = null;
    private Context mContext;

    public EndpointsAsyncTask (Context context){
        mContext = context;
    }

    @Override
    protected String doInBackground(Void... params){
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - set to test on a physical device with a local development server
                    // (to get your ip address, go to cmd -> ipconfig -> IPV4 address with port number)
                    .setRootUrl("http://192.168.43.170:8080/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {

        Intent intent = new Intent(mContext, JokeActivity.class);

        intent.putExtra(JokeActivity.JOKE_KEY, result);
        mContext.startActivity(intent);
    }
}
