package com.postbox.service;

import com.postbox.model.IncomingRequest;

public interface IncomingRequestService {
    IncomingRequest save(String body);
}
