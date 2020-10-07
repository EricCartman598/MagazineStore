package com.epam.lab.magazineStore.controller;

import com.epam.lab.magazineStore.common.Contract;
import com.epam.lab.magazineStore.dao.UserDao;
import com.epam.lab.magazineStore.domain.Role;
import com.epam.lab.magazineStore.domain.User;
import com.epam.lab.magazineStore.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    @Spy
    UserService userService;

    @Mock
    UserDao userDao;

    User user;
    List<User> userList;

    @Captor
    ArgumentCaptor<User> captor;

    private final Long userId = 1L;
    private final String userPassword = "12345";
    private final Integer userPasswordHash = 12345;
    private final Integer etalonPasswordHash = -1457449447;
    private final Integer wrongPassword = 777;
    private final String email = "test@test.ru";
    private final String emptyString = "";
    private final String firstName = "Ivan";
    private final String lastName = "Ivanov";
    private final String validBirthDate = "1986-12-06";
    private final String invalidBirthDate = "1986.12.06";
    private final String login = "login";
    private final String password = "password";
    private final String matchedPasswordAgain = password;
    private final String mismatchedPasswordAgain = password + "123";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User();
        userList = new ArrayList<>();
        userList.add(user);
        userList.add(user);
        userList.add(user);
    }

    @Test
    public void add() {
        userService.add(user);

        verify(userDao).add(captor.capture());
        User capturedUser = captor.getValue();

        assertEquals(Role.CUSTOMER, capturedUser.getRole());
        assertEquals(new BigDecimal(0.0), capturedUser.getBalance());

        verify(userDao, times(1)).add(user);
    }

    @Test
    public void getUserByEmail() {
        when(userDao.getUserByEmail(email)).thenReturn(user);
        User retrievedUser = userService.getUserByEmail(email);

        assertEquals(user, retrievedUser);
        verify(userDao, times(1)).getUserByEmail(email);
    }

    @Test
    public void isPasswordCorrect_correctPassword_true() {
        user.setPassword(userPasswordHash);
        boolean result = userService.isPasswordCorrect(user, userPasswordHash);

        assertEquals(result, true);
    }

    @Test
    public void isPasswordCorrect_incorrectPassword_false() {
        user.setPassword(userPasswordHash);
        boolean result = userService.isPasswordCorrect(user, wrongPassword);

        assertEquals(result, false);
    }

    @Test
    public void isPasswordCorrect_noUser_false() {
        boolean result = userService.isPasswordCorrect(null, userPasswordHash);

        assertEquals(result, false);
    }

    @Test
    public void getAll() {
        when(userDao.getAll()).thenReturn(userList);
        List<User> retrievedUserList = userService.getAll();

        assertEquals(retrievedUserList, userList);
        verify(userDao, times(1)).getAll();
    }

    @Test
    public void findById() {
        when(userDao.findById(userId)).thenReturn(user);
        User retrievedUser = userService.findById(userId);

        assertEquals(user, retrievedUser);
    }

    @Test
    public void update() {
        userService.update(user);
        verify(userDao, times(1)).update(user);
    }

    @Test
    public void validateFields_allFieldsAreValid() {
        when(userDao.getUserByEmail(login)).thenReturn(null);
        userService.validateFields(firstName, lastName, validBirthDate, login,
                password, matchedPasswordAgain);
    }

    @Test(expected = RuntimeException.class)
    public void validateFields_userAlreadyExists_exception() {
        when(userDao.getUserByEmail(login)).thenReturn(user);
        userService.validateFields(firstName, lastName, validBirthDate, login,
                password, matchedPasswordAgain);
    }

    @Test(expected = RuntimeException.class)
    public void validateFields_passwordsMismatch_exception() {
        when(userDao.getUserByEmail(login)).thenReturn(null);
        userService.validateFields(firstName, lastName, validBirthDate, login,
                password, mismatchedPasswordAgain);
    }

    @Test(expected = RuntimeException.class)
    public void validateFields_incorrectBirthDate_exception() {
        when(userDao.getUserByEmail(login)).thenReturn(null);
        userService.validateFields(firstName, lastName, invalidBirthDate, login,
                password, mismatchedPasswordAgain);
    }

    @Test(expected = RuntimeException.class)
    public void validateFields_firstNameFieldIsNotFilled_exception() {
        when(userDao.getUserByEmail(login)).thenReturn(null);
        userService.validateFields(emptyString, lastName, validBirthDate, login,
                password, matchedPasswordAgain);
    }

    @Test(expected = RuntimeException.class)
    public void validateFields_lastNameFieldIsNotFilled_exception() {
        when(userDao.getUserByEmail(login)).thenReturn(null);
        userService.validateFields(firstName, emptyString, validBirthDate, login,
                password, matchedPasswordAgain);
    }

    @Test(expected = RuntimeException.class)
    public void validateFields_birthDateFieldIsNotFilled_exception() {
        when(userDao.getUserByEmail(login)).thenReturn(null);
        userService.validateFields(firstName, lastName, emptyString, login,
                password, matchedPasswordAgain);
    }

    @Test(expected = RuntimeException.class)
    public void validateFields_loginFieldIsNotFilled_exception() {
        when(userDao.getUserByEmail(login)).thenReturn(null);
        userService.validateFields(firstName, lastName, validBirthDate, emptyString,
                password, matchedPasswordAgain);
    }

    @Test(expected = RuntimeException.class)
    public void validateFields_passwordFieldIsNotFilled_exception() {
        when(userDao.getUserByEmail(login)).thenReturn(null);
        userService.validateFields(firstName, lastName, validBirthDate, login,
                emptyString, matchedPasswordAgain);
    }

    @Test(expected = RuntimeException.class)
    public void validateFields_passwordAgainFieldIsNotFilled_exception() {
        when(userDao.getUserByEmail(login)).thenReturn(null);
        userService.validateFields(firstName, lastName, validBirthDate, login,
                password, emptyString);
    }

    @Test
    public void getBirthDateTimeStamp_dateIsValid() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Contract.dateFormat);
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(validBirthDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(parsedDate.getTime());
        Timestamp obtainedTimeStamp = userService.getBirthDateTimeStamp(validBirthDate);

        assertEquals(timeStamp, obtainedTimeStamp);
    }

    @Test
    public void getBirthDateTimeStamp_dateIsInvalid() {
        Timestamp obtainedTimeStamp = userService.getBirthDateTimeStamp(invalidBirthDate);

        assertEquals(null, obtainedTimeStamp);
    }

    @Test
    public void getPasswordHash_passwordIsValid() {
        Integer obtainedHash = userService.getPasswordHash(password);

        assertEquals(etalonPasswordHash, obtainedHash);
    }

    @Test
    public void getPasswordHash_passwordIsInvalid() {
        Integer obtainedHash = userService.getPasswordHash(userPassword);

        assertNotEquals(etalonPasswordHash, obtainedHash);
    }

    @Test(expected = RuntimeException.class)
    public void checkUserPermission_userIsNull_exception() {
        userService.checkUserPermission(null, userPassword);
    }

    @Test(expected = RuntimeException.class)
    public void checkUserPermission_incorrectPassword_exception() {
        doReturn(false).when(userService).isPasswordCorrect(eq(user), any());
        userService.checkUserPermission(user, userPassword);
    }
}


