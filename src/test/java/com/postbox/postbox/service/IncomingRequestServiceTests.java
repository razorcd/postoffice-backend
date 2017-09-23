package com.postbox.postbox.service;

import com.postbox.postbox.model.IncomingRequest;
import com.postbox.postbox.repository.IncomingRequestRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.BDDMockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class IncomingRequestServiceTests {

    IncomingRequestService subject;

    @MockBean
    IncomingRequestRepository incomingRequestRepositoryMock;

    @Before
    public void init() {
        subject = new IncomingRequestService(incomingRequestRepositoryMock);
    }

    @Test
    public void testSave() {
        //TODO: create entity factories
        IncomingRequest incomingRequestDummy = new IncomingRequest();
        incomingRequestDummy.setBody("test body");

        subject.save(incomingRequestDummy.getBody());

        verify(incomingRequestRepositoryMock, times(1)).save(refEq(incomingRequestDummy));

    }

}