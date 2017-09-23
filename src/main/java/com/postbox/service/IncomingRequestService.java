package com.postbox.postbox.service;

import com.postbox.postbox.model.IncomingRequest;

public interface IncomingRequestService {
    IncomingRequest save(String body);
}
