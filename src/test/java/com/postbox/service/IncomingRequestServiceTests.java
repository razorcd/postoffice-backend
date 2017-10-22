package com.postbox.service;

import com.postbox.factory.IncomingRequestDocumentFactory;
import com.postbox.document.IncomingRequest;
import com.postbox.repository.IncomingRequestNoSqlRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;


@RunWith(SpringJUnit4ClassRunner.class)
public class IncomingRequestServiceTests {

    IncomingRequestService subject;

    @MockBean
    IncomingRequestNoSqlRepository incomingRequestNoSqlRepositoryMock;

    @Before
    public void init() {
        subject = new IncomingRequestServiceImpl(incomingRequestNoSqlRepositoryMock);
    }

    @Before
    public void before() {
        incomingRequestNoSqlRepositoryMock.deleteAll();
    }

    @Test
    public void testSave() {

        IncomingRequest incomingRequestDummy = IncomingRequestDocumentFactory.generateIncomingRequest();

        subject.save(incomingRequestDummy);

        verify(incomingRequestNoSqlRepositoryMock, times(1)).save(refEq(incomingRequestDummy));

    }

}
