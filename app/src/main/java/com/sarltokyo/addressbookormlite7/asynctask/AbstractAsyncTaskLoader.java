package com.sarltokyo.addressbookormlite7.asynctask;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Android の非同期処理を行う Loader の起動方法
 * http://tomoyamkung.net/2014/02/24/android-loader-execute/
 */
public abstract class AbstractAsyncTaskLoader<T> extends AsyncTaskLoader<T> {

    protected T mResult;

    public AbstractAsyncTaskLoader(Context context) {
        super(context);
    }


    abstract public T loadInBackground();

    @Override
    public void deliverResult(T data) {
        if (isReset()) {
            return;
        }

        mResult = data;
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        if (mResult != null) {
            deliverResult(mResult);
        }

        if (takeContentChanged() || mResult == null) {
            forceLoad(); // 非同期処理を開始
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad(); // 非同期処理のキャンセル
    }

    @Override
    public void onCanceled(T data) {
        // nop
    }

    @Override
    protected void onReset() {
        super.onReset();

        onStopLoading();
        mResult = null;
    }
}
