package com.example.project.network.util;

import java.util.concurrent.CopyOnWriteArrayList;

import retrofit2.Call;

/**
 * Provides response of network call to the caller.
 */

public class ResponsePublisher<T extends BaseResponse> implements IResponsePublisher<T> {

    private CopyOnWriteArrayList<IResponsePublisher> mResponsePublisher;

    public ResponsePublisher() {
        mResponsePublisher = new CopyOnWriteArrayList<IResponsePublisher>();
    }


    @Override
    public void onSuccess(@RequestTypes.Interface int requestType, Call call, T responseBean) {
        for (IResponsePublisher publisher : mResponsePublisher) {
            publisher.onSuccess(requestType, call, responseBean);
        }
    }

    @Override
    public void onUnauthorised(@RequestTypes.Interface int requestType, Call call, T responseBean) {
        for (IResponsePublisher publisher : mResponsePublisher) {
            publisher.onUnauthorised(requestType, call, responseBean);
        }
    }

    @Override
    public void onError(@RequestTypes.Interface int requestType, Call call, Throwable error) {
        for (IResponsePublisher publisher : mResponsePublisher) {
            publisher.onError(requestType, call, error);
        }
    }

    /**
     * Register response publisher to get the callback of network request.
     *
     * @param responsePublisher - response publisher
     */
    public void registerResponsePublisher(IResponsePublisher responsePublisher) {
        if (!mResponsePublisher.contains(responsePublisher)) {
            mResponsePublisher.add(responsePublisher);
        }
    }

    /**
     * Unregister response publisher.
     *
     * @param responsePublisher - responsePublisher
     */
    public void unregisterResponsePublisher(IResponsePublisher responsePublisher) {
        if (mResponsePublisher != null) {
            mResponsePublisher.remove(responsePublisher);
        }
    }

    /**
     * Unregister all response publisher
     */
    public void unregisterAllResponsePublisher() {
        mResponsePublisher.clear();
    }
}
