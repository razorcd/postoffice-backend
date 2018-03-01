package com.postbox.service;

import com.postbox.document.User;
import com.postbox.factory.UserDocumentFactory;
import com.postbox.repository.UserNoSqlRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;


@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTests {

    UserServiceImpl subject;

    @MockBean
    UserNoSqlRepository userNoSqlRepositoryMock;

    @Before
    public void init() {
        subject = new UserServiceImpl(userNoSqlRepositoryMock);
    }

    @Before
    public void before() {
        userNoSqlRepositoryMock.deleteAll();
    }  // TODO: investigate

    @Test
    public void testSave() {
        User userDummy = UserDocumentFactory.generateUser();

        subject.create(userDummy.getUsername(), userDummy.getEncryptedPassword());

        verify(userNoSqlRepositoryMock, times(1)).save(userDummy);
    }

}
