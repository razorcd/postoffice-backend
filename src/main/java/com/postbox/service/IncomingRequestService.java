package com.postbox.service;

import com.postbox.document.IncomingRequest;

import java.util.List;

public interface IncomingRequestService {

    List<IncomingRequest> getAll();

    void save(IncomingRequest incomingRequest);
}
