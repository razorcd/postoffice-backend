package com.postbox.service;

import com.postbox.document.IncomingRequest;
import com.postbox.document.User;

import java.util.List;

public interface IncomingRequestService {

    /**
     * Get all existing incoming requests
     *
     * @return [List<IncomingRequest>] list of incoming requests
     */
    List<IncomingRequest> getAll();

    /**
     * Get all existing incoming requests for userPathIdentifier
     *
     * @param userPathIdentifier the identifier that define inciming requests ownership
     * @return [List<IncomingRequest>] list of incoming requests
     */
    List<IncomingRequest> getByUserPathIdentifier(String userPathIdentifier);

    /**
     * Save an incoming request for user defined by it's pathIdentifier
     *
     * @param incomingRequest the incoming request to save
     * @param userPathIdentifier the public path identifier of the user
     */
    void save(IncomingRequest incomingRequest, String userPathIdentifier);
}
