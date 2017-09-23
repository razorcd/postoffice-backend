package com.postbox.postbox.repository;

import com.postbox.postbox.model.IncomingRequest;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
//@Transactional
public interface IncomingRequestRepository {

    IncomingRequest save(IncomingRequest incomingRequest);
}
