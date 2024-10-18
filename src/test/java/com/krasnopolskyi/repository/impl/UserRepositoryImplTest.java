package com.krasnopolskyi.repository.impl;

import com.krasnopolskyi.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRepositoryImplTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @InjectMocks
    private UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    void testUpdate() throws Exception {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");

        // Act
        userRepository.update(user);

        // Assert
        verify(session, times(1)).persist(user);  // Ensure that session.persist() is called once
    }

    @Test
    void testFindById_UserExists() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(session.get(User.class, userId)).thenReturn(user);

        // Act
        Optional<User> foundUser = userRepository.findById(userId);

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(userId, foundUser.get().getId());
    }

    @Test
    void testFindById_UserDoesNotExist() {
        // Arrange
        Long userId = 1L;

        when(session.get(User.class, userId)).thenReturn(null);

        // Act
        Optional<User> foundUser = userRepository.findById(userId);

        // Assert
        assertFalse(foundUser.isPresent());
    }

    @Test
    void testIsUsernameExist_UsernameExists() {
        // Arrange
        String username = "test_user";
        String sql = "SELECT count(*) FROM users u WHERE u.username = :username";

        NativeQuery<Long> queryMock = mock(NativeQuery.class);
        when(session.createNativeQuery(sql, Long.class)).thenReturn(queryMock);
        when(queryMock.setParameter("username", username)).thenReturn(queryMock);
        when(queryMock.uniqueResult()).thenReturn(1L);

        // Act
        boolean exists = userRepository.isUsernameExist(username);

        // Assert
        assertTrue(exists);
        verify(queryMock, times(1)).setParameter("username", username);
    }

    @Test
    void testIsUsernameExist_UsernameDoesNotExist() {
        // Arrange
        String username = "non_existing_user";
        String sql = "SELECT count(*) FROM users u WHERE u.username = :username";

        NativeQuery<Long> queryMock = mock(NativeQuery.class);
        when(session.createNativeQuery(sql, Long.class)).thenReturn(queryMock);
        when(queryMock.setParameter("username", username)).thenReturn(queryMock);
        when(queryMock.uniqueResult()).thenReturn(0L);

        // Act
        boolean exists = userRepository.isUsernameExist(username);

        // Assert
        assertFalse(exists);
        verify(queryMock, times(1)).setParameter("username", username);
    }

    @Test
    void testFindByUsername_UserExists() {
        // Arrange
        String username = "test_user";
        String sql = "SELECT * FROM users WHERE username = :username";

        NativeQuery<User> queryMock = mock(NativeQuery.class);
        User user = new User();
        user.setUsername(username);

        when(session.createNativeQuery(sql, User.class)).thenReturn(queryMock);
        when(queryMock.setParameter("username", username)).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(List.of(user));

        // Act
        Optional<User> foundUser = userRepository.findByUsername(username);

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(username, foundUser.get().getUsername());
    }

    @Test
    void testFindByUsername_UserDoesNotExist() {
        // Arrange
        String username = "non_existing_user";
        String sql = "SELECT * FROM users WHERE username = :username";

        NativeQuery<User> queryMock = mock(NativeQuery.class);

        when(session.createNativeQuery(sql, User.class)).thenReturn(queryMock);
        when(queryMock.setParameter("username", username)).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(List.of());

        // Act
        Optional<User> foundUser = userRepository.findByUsername(username);

        // Assert
        assertFalse(foundUser.isPresent());
    }
}
