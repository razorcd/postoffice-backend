package com.postbox.service;

import com.postbox.document.User;
import com.postbox.factory.IncomingRequestDocumentFactory;
import com.postbox.document.IncomingRequest;
import com.postbox.factory.UserDocumentFactory;
import com.postbox.repository.IncomingRequestNoSqlRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.*;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


@RunWith(SpringJUnit4ClassRunner.class)
public class IncomingRequestServiceImplTests {

    IncomingRequestService subject;

    @MockBean
    IncomingRequestNoSqlRepository incomingRequestNoSqlRepositoryMock;

    final Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

    @Before
    public void init() {
        subject = new IncomingRequestServiceImpl(incomingRequestNoSqlRepositoryMock, clock);
    }

    @Before
    public void before() {
        incomingRequestNoSqlRepositoryMock.deleteAll();
    }

    @Test
    public void testsGetAll() {
        List<IncomingRequest> incomingRequestDummyList = Arrays.asList(
                IncomingRequestDocumentFactory.generateIncomingRequest(),
                IncomingRequestDocumentFactory.generateIncomingRequest());

        when(incomingRequestNoSqlRepositoryMock.findAll()).thenReturn(incomingRequestDummyList);

        assertEquals(subject.getAll(), incomingRequestDummyList);

        verify(incomingRequestNoSqlRepositoryMock, times(1)).findAll();
    }

    @Test
    public void testGetByUserPathIdentifier() {
        String pathIdentifier = "testGetByUserPathIdentifier_1";
        IncomingRequest incomingRequest1 = IncomingRequestDocumentFactory.generateIncomingRequest();
        incomingRequest1.setUserPathIdentifier(pathIdentifier);
        IncomingRequest incomingRequest2 = IncomingRequestDocumentFactory.generateIncomingRequest();
        incomingRequest2.setUserPathIdentifier(pathIdentifier);
        List<IncomingRequest> incomingRequestDummyList = Arrays.asList(incomingRequest1, incomingRequest2);

        when(incomingRequestNoSqlRepositoryMock.findByUserPathIdentifier(pathIdentifier)).thenReturn(incomingRequestDummyList);

        assertEquals(subject.getByUserPathIdentifier(pathIdentifier), incomingRequestDummyList);

        verify(incomingRequestNoSqlRepositoryMock, times(1)).findByUserPathIdentifier(pathIdentifier);
    }

    @Test
    public void testSave() {
        IncomingRequest incomingRequestDummy = IncomingRequestDocumentFactory.generateIncomingRequest();
        User userDummy = UserDocumentFactory.generateUser();

        subject.save(incomingRequestDummy, userDummy.getPathIdentifier());

        ArgumentCaptor<IncomingRequest> argument = ArgumentCaptor.forClass(IncomingRequest.class);
        verify(incomingRequestNoSqlRepositoryMock, times(1)).save(argument.capture());

        assertEquals(incomingRequestDummy.getBody(), argument.getValue().getBody());
        assertEquals(incomingRequestDummy.getMethod(), argument.getValue().getMethod());
        assertEquals(incomingRequestDummy.getUrl(), argument.getValue().getUrl());
        assertTrue(LocalDateTime.now(clock).isEqual(argument.getValue().getTimestamp()));
        assertEquals(userDummy.getPathIdentifier(), argument.getValue().getUserPathIdentifier());
    }

}
