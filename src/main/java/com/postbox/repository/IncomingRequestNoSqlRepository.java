package com.postbox.repository;

import com.postbox.document.IncomingRequest;
import com.postbox.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IncomingRequestNoSqlRepository extends MongoRepository<IncomingRequest, Long> {
//    List<IncomingRequest> findAll();
//
//    IncomingRequest save(IncomingRequest incomingRequest);

    List<IncomingRequest> findByUserPathIdentifier(String userPathIdentifier);
}
