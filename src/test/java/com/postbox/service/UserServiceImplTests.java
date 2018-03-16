package com.postbox.service;

import com.postbox.config.exceptions.EntityNotFoundException;
import com.postbox.controler.dto.param.UserUpdateParam;
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
    public void testGetUserByUsername() {
        User userDummy = UserDocumentFactory.generateUser();
        when(userNoSqlRepositoryMock.findByUsername(userDummy.getUsername())).thenReturn(userDummy);

        assertEquals(userDummy, subject.getUserByUsername(userDummy.getUsername()));

        verify(userNoSqlRepositoryMock, times(1)).findByUsername(userDummy.getUsername());
    }

    @Test
    public void testGetUserByUsernameWhenUserDoesNOTExist() {
        User userDummy = UserDocumentFactory.generateUser();
        when(userNoSqlRepositoryMock.findByUsername(userDummy.getUsername())).thenReturn(null);

        try {
            subject.getUserByUsername(userDummy.getUsername());
        } catch (EntityNotFoundException e) {
            //test successful
        }

        verify(userNoSqlRepositoryMock, times(1)).findByUsername(userDummy.getUsername());
    }

    @Test
    public void testCreate() {
        User newUser = new User("create_Username1", "create_Email1", null);

        subject.create(newUser, "create_Password1");

        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(userNoSqlRepositoryMock, times(1)).save(argument.capture());

        assertEquals("create_Username1", argument.getValue().getUsername());
        assertEquals("create_Email1", argument.getValue().getEmail());
        assertTrue(passwordEncoder.matches("create_Password1", argument.getValue().getEncryptedPassword()));
    }

    @Test
    public void updateUserByUsername() {
        User userDummy = UserDocumentFactory.generateUser();
        when(userNoSqlRepositoryMock.findByUsername(userDummy.getUsername())).thenReturn(userDummy);

        UserUpdateParam userUpdateParam = new UserUpdateParam("updateUserByUsername_email1@example.com");
        subject.updateUserByUsername(userDummy.getUsername(), userUpdateParam);

        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(userNoSqlRepositoryMock, times(1)).save(argument.capture());

        assertEquals("updateUserByUsername_email1@example.com", argument.getValue().getEmail());
        assertEquals(userDummy.getUsername(), argument.getValue().getUsername());
    }

    @Test
    public void updateUserByUsernameWhenUserNotFound() {
        User userDummy = UserDocumentFactory.generateUser();
        when(userNoSqlRepositoryMock.findByUsername(userDummy.getUsername())).thenReturn(null);

        try {
            UserUpdateParam userUpdateParam = new UserUpdateParam("updateUserByUsername_email1@example.com");
            subject.updateUserByUsername(userDummy.getUsername(), userUpdateParam);
        } catch (EntityNotFoundException e) {
            //successful
        }

        verify(userNoSqlRepositoryMock, times(0)).save(any(User.class));
    }

    @Test
    public void getUserByPathIdentifier() {
        User userDummy = UserDocumentFactory.generateUser();
        when(userNoSqlRepositoryMock.findUserByPathIdentifier(userDummy.getPathIdentifier())).thenReturn(userDummy);

        assertEquals(userDummy, subject.getUserByPathIdentifier(userDummy.getPathIdentifier()));

        verify(userNoSqlRepositoryMock, times(1)).findUserByPathIdentifier(userDummy.getPathIdentifier());
    }

    @Test
    public void getUserByPathIdentifierWhenNOTFound() {
        User userDummy = UserDocumentFactory.generateUser();
        when(userNoSqlRepositoryMock.findUserByPathIdentifier(userDummy.getPathIdentifier())).thenReturn(null);

        try {
            subject.getUserByPathIdentifier(userDummy.getPathIdentifier());
        } catch (EntityNotFoundException e) {
//            successful
        }

        verify(userNoSqlRepositoryMock, times(1)).findUserByPathIdentifier(userDummy.getPathIdentifier());
    }
}
