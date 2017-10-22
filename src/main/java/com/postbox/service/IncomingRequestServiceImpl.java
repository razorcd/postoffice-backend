package com.postbox.service;

import com.postbox.document.IncomingRequest;
import com.postbox.repository.IncomingRequestNoSqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomingRequestServiceImpl implements IncomingRequestService {

    IncomingRequestNoSqlRepository incomingRequestNoSqlRepository;

    @Autowired
    public IncomingRequestServiceImpl(IncomingRequestNoSqlRepository incomingRequestNoSqlRepository) {
        this.incomingRequestNoSqlRepository = incomingRequestNoSqlRepository;
    }

    public List<IncomingRequest> getAll() {
        return incomingRequestNoSqlRepository.findAll();
    }

    public void save(IncomingRequest incomingRequest) {
        incomingRequestNoSqlRepository.save(incomingRequest);
    }
}
