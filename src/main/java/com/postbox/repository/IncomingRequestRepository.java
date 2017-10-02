package com.postbox.repository;

import com.postbox.model.IncomingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomingRequestRepository extends JpaRepository<IncomingRequest, Long> {

    List<IncomingRequest> findAll();

    IncomingRequest save(IncomingRequest incomingRequest);
}
