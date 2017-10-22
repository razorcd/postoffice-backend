package com.postbox.service;

import com.postbox.factory.IncomingRequestFactory;
import com.postbox.document.IncomingRequest;
import com.postbox.repository.IncomingRequestNoSqlRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class IncomingRequestServiceTests {

    IncomingRequestService subject;

//    @MockBean
    @Autowired
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

        IncomingRequest incomingRequestDummy = IncomingRequestFactory.generateIncomingRequest();

        subject.save(incomingRequestDummy);

//        verify(incomingRequestNoSqlRepositoryMock, times(1)).save(refEq(incomingRequestDummy));

    }

}
