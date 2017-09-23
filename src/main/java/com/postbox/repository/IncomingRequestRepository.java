package com.postbox.repository;

import com.postbox.model.IncomingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomingRequestRepository extends JpaRepository<IncomingRequest, Long> {

    IncomingRequest save(IncomingRequest incomingRequest);
}
