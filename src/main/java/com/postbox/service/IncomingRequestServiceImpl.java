package com.postbox.service;

import com.postbox.model.IncomingRequest;
import com.postbox.repository.IncomingRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomingRequestServiceImpl implements IncomingRequestService {

    IncomingRequestRepository incomingRequestRepository;

    @Autowired
    public IncomingRequestServiceImpl(IncomingRequestRepository incomingRequestRepository) {
        this.incomingRequestRepository = incomingRequestRepository;
    }

    public IncomingRequest save(IncomingRequest incomingRequest) {
        return incomingRequestRepository.save(incomingRequest);
    }
}
