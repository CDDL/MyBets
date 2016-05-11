package project.catalin.mybets.controladores.utils;

import android.os.AsyncTask;

/**
 * Created by Trabajo on 06/05/2016.
 */
public abstract class ExceptionHandlingAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    private Exception mError;

    @Override
    protected Result doInBackground(Params... params) {
        try {
            return executeTask(params);
        } catch (Exception e) {
            mError = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        if(mError == null) onTaskSuccess(result);
        else onTaskFailture(mError);
    }

    protected abstract Result executeTask(Params... params) throws Exception;

    protected abstract void onTaskFailture(Exception e);

    protected abstract void onTaskSuccess(Result result);
}
