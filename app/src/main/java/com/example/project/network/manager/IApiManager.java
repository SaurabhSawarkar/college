package com.example.project.network.manager;

import com.example.project.network.util.IResponsePublisher;
import com.example.project.network.util.RequestTypes;

/**
 * Defines network calls used in the application.
 */

public interface IApiManager {

    /**
     * Register response publisher to get the callback of network request.
     *
     * @param responsePublisher - response publisher
     */
    void registerResponseObserver(IResponsePublisher responsePublisher);

    /**
     * Unregister response publisher.
     *
     * @param responsePublisher - responsePublisher
     */
    void unregisterResponseObserver(IResponsePublisher responsePublisher);

    /**
     * Sets the base url used in retrofit client.
     * This could be useful if base url changes for few network calls.
     *
     * @param baseUrl - base url
     */
    void setBaseUrl(String baseUrl);

    /**
     * Unregister all response publisher
     */
    void unregisterAllResponseObserver();

    void login(@RequestTypes.Interface int requestType, String username, String password,
               String oAuthCode, String redirectUrl);
}
