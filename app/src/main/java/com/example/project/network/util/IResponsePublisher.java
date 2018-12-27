package com.example.project.network.util;

import retrofit2.Call;

/**
 * Blue print for response publisher.
 */

public interface IResponsePublisher<T extends BaseResponse> {

    /**
     * Called when network call is successful
     *
     * @param requestType  -  request type of corresponding network call
     * @param responseBean - network response parsed in corresponding class type.
     */
    void onSuccess(@RequestTypes.Interface int requestType, Call call, T responseBean);

    /**
     * Called when user is unauthorized to access the request resource.
     *
     * @param requestType  - request type of corresponding network call
     * @param call         - object of call
     * @param responseBean - response bean
     */
    void onUnauthorised(@RequestTypes.Interface int requestType, Call call, T responseBean);

    /**
     * Called when error occurred in the network call
     *
     * @param requestType - request type of corresponding network call
     * @param call        - object of call
     * @param error       - error occurred in network call
     */
    void onError(@RequestTypes.Interface int requestType, Call call, Throwable error);
}
