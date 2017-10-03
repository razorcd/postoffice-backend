package com.postbox.service;

import com.postbox.factory.IncomingRequestFactory;
import com.postbox.model.IncomingRequest;
import com.postbox.repository.IncomingRequestRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.BDDMockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class IncomingRequestServiceTests {

    IncomingRequestService subject;

    @MockBean
    IncomingRequestRepository incomingRequestRepositoryMock;

    @Before
    public void init() {
        subject = new IncomingRequestServiceImpl(incomingRequestRepositoryMock);
    }

    @Test
    public void testSave() {

        IncomingRequest incomingRequestDummy = IncomingRequestFactory.generate();

        subject.save(incomingRequestDummy);

        verify(incomingRequestRepositoryMock, times(1)).save(refEq(incomingRequestDummy));

    }

}
