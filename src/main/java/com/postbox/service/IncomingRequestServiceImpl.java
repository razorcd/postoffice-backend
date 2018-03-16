package com.postbox.service;

import com.postbox.document.IncomingRequest;
import com.postbox.document.User;
import com.postbox.repository.IncomingRequestNoSqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class IncomingRequestServiceImpl implements IncomingRequestService {

    private IncomingRequestNoSqlRepository incomingRequestNoSqlRepository;
    private Clock clock;

    @Autowired
    public IncomingRequestServiceImpl(IncomingRequestNoSqlRepository incomingRequestNoSqlRepository,
                                      Clock clock) {
        this.incomingRequestNoSqlRepository = incomingRequestNoSqlRepository;
        this.clock = clock;
    }

    public List<IncomingRequest> getAll() {
        return incomingRequestNoSqlRepository.findAll();
    }


    @Override
    public List<IncomingRequest> getByUserPathIdentifier(String userPathIdentifier) {
        return incomingRequestNoSqlRepository.findByUserPathIdentifier(userPathIdentifier);
    }

    @Override
    public void save(IncomingRequest incomingRequest, String userPathIdentifier) {
        IncomingRequest newIncomingRequest = new IncomingRequest(incomingRequest);
        newIncomingRequest.setTimestamp(LocalDateTime.now(clock));
        newIncomingRequest.setUserPathIdentifier(userPathIdentifier);
        incomingRequestNoSqlRepository.save(newIncomingRequest);
    }
}
