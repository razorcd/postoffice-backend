package com.postbox.postbox.service;

import com.postbox.postbox.model.IncomingRequest;
import com.postbox.postbox.repository.IncomingRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomingRequestService {

    IncomingRequestRepository incomingRequestRepository;

    @Autowired
    public IncomingRequestService(IncomingRequestRepository incomingRequestRepository) {
        this.incomingRequestRepository = incomingRequestRepository;
    }

    public IncomingRequest save(String body) {
        IncomingRequest incomingRequest = new IncomingRequest();
        incomingRequest.setBody(body);
        return incomingRequestRepository.save(incomingRequest);
    }
}
