package com.postbox.service;

import com.postbox.model.IncomingRequest;

import java.util.List;

public interface IncomingRequestService {

    List<IncomingRequest> getAll();

    void save(IncomingRequest incomingRequest);
}
