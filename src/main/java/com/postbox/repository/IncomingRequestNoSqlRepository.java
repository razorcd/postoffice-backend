package com.postbox.repository;

import com.postbox.document.IncomingRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IncomingRequestNoSqlRepository extends MongoRepository<IncomingRequest, Long> {
//    public List<IncomingRequest> findAll();
//
//    public IncomingRequest save(IncomingRequest incomingRequest);
}
