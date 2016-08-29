package com.amsen.par.cewrncyconv.base.functional;

import android.os.AsyncTask;

/**
 * Generic wrapper to run some Async code in a
 * functional way
 *
 * @author Pär Amsen 2016
 */
public class Async {
    public static <T> void go(Func1<T> run, Action1<T> callback) {
        new AsyncTask<Void, Void, T>() {
            @Override
            protected T doInBackground(Void... params) {
                return run.call();
            }

            @Override
            protected void onPostExecute(T result) {
                callback.call(result);
            }
        }.execute();
    }
}
