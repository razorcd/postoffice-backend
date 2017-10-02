package com.postbox.service;

import com.postbox.model.IncomingRequest;
import com.postbox.repository.IncomingRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomingRequestServiceImpl implements IncomingRequestService {

    IncomingRequestRepository incomingRequestRepository;

    @Autowired
    public IncomingRequestServiceImpl(IncomingRequestRepository incomingRequestRepository) {
        this.incomingRequestRepository = incomingRequestRepository;
    }

    public List<IncomingRequest> getAll() {
        return incomingRequestRepository.findAll();
    }

    public void save(IncomingRequest incomingRequest) {
        incomingRequestRepository.save(incomingRequest);
    }
}
