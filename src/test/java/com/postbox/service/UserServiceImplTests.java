package com.postbox.service;

import com.postbox.document.User;
import com.postbox.factory.UserDocumentFactory;
import com.postbox.repository.UserNoSqlRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTests {

    UserServiceImpl subject;

    @MockBean
    UserNoSqlRepository userNoSqlRepositoryMock;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Before
    public void init() {
        subject = new UserServiceImpl(userNoSqlRepositoryMock, passwordEncoder);
    }

    @Before
    public void before() {
        userNoSqlRepositoryMock.deleteAll();
    }  // TODO: investigate

    @Test
    public void testSave() {
        subject.create("myCustomUsername1", "myCustomPassword1");

        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(userNoSqlRepositoryMock, times(1)).save(argument.capture());

        assertEquals("myCustomUsername1", argument.getValue().getUsername());
        assertTrue(passwordEncoder.matches("myCustomPassword1", argument.getValue().getEncryptedPassword()));
    }

}
