package com.epam.lab.magazineStore.service;

import com.epam.lab.magazineStore.common.Contract;
import com.epam.lab.magazineStore.dao.MagazineDao;
import com.epam.lab.magazineStore.dao.OrderDao;
import com.epam.lab.magazineStore.dao.UserDao;
import com.epam.lab.magazineStore.domain.Magazine;
import com.epam.lab.magazineStore.domain.Order;
import com.epam.lab.magazineStore.domain.Role;
import com.epam.lab.magazineStore.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.security.spec.KeySpec;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private UserDao userDao = new UserDao();

    public void add(User newUser) {
        newUser.setRole(Role.CUSTOMER);
        newUser.setBalance(new BigDecimal(0.0));
        userDao.add(newUser);
    }

    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    public boolean isPasswordCorrect(User user, Integer writtenPassword) {
        if(writtenPassword == null) {
            return false;
        }
        return (user != null) && user.getPassword().equals(writtenPassword);
    }

    public List<User> getAll() {
        return userDao.getAll();
    }

    public User findById(Long id) {
        return userDao.findById(id);
    }

    public void update(User data) {
        userDao.update(data);
    }

    public void validateFields(String firstName, String LastName, String birthDate, String login, String password, String passwordAgain) {
        if (!isAllFieldsFilled(firstName, LastName, birthDate, login, password, passwordAgain)) {
            throw new RuntimeException(Contract.NOT_FILLED_FIELDS_MSG);
        }
        if (!isPasswordsMatch(password, passwordAgain)) {
            throw new RuntimeException(Contract.PASSWORDS_MISMATCH_MSG);
        }
        if (userDao.getUserByEmail(login) != null) {
            throw new RuntimeException(Contract.EMAIL_IS_ALREADY_EXISTS_MSG);
        }
        if (!isBirthDateCorrect(birthDate)) {
            throw new RuntimeException(Contract.INCORRECT_BIRTH_DATE_MSG);
        }
    }

    private boolean isAllFieldsFilled(String firstName, String LastName, String birthDate, String login, String password, String passwordAgain) {
        return !(firstName.trim().isEmpty() ||
                LastName.trim().isEmpty() ||
                birthDate.trim().isEmpty() ||
                login.trim().isEmpty() ||
                password.trim().isEmpty() ||
                passwordAgain.trim().isEmpty());
    }

    private boolean isPasswordsMatch(String password, String passwordAgain) {
        if(password != null) {
            return password.equals(passwordAgain);
        }
        return false;
    }

    private boolean isBirthDateCorrect(String birthDate) {
        return getBirthDateTimeStamp(birthDate) != null;
    }

    public Timestamp getBirthDateTimeStamp(String birthDate) {
        SimpleDateFormat birthDateFormat = new SimpleDateFormat(Contract.dateFormat);
        Date parsedDate = null;
        try {
            parsedDate = birthDateFormat.parse(birthDate);
        } catch (ParseException e) {
            logger.error("error while parsing birth date");
        }

        return (parsedDate != null) ? new Timestamp(parsedDate.getTime()) : null;
    }

    public Integer getPasswordHash(String passwordStr) {
        byte[] salt = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        KeySpec spec = new PBEKeySpec(passwordStr.toCharArray(), salt, 65536, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            ByteBuffer wrapped = ByteBuffer.wrap(hash);
            return wrapped.getInt();
        } catch (Exception e) {
            logger.error("can not calculate hash of password");
        }
        return null;
    }

    public void checkUserPermission(User user, String writtenPassword) {
        if(user == null) {
            throw new RuntimeException(Contract.USER_DOESNT_EXIST_MSG);
        }
        if(!isPasswordCorrect(user, getPasswordHash(writtenPassword))) {
            throw new RuntimeException(Contract.INCORRECT_PASSWORD_MSG);
        }
    }

    public void changeUserPassword(User user, String oldPassword, String newPassword) {
        if (user == null) {
            throw new RuntimeException(Contract.USER_DOESNT_EXIST_MSG);
        }
        if(!getPasswordHash(oldPassword).equals(user.getPassword())) {
            throw new RuntimeException(Contract.INCORRECT_PASSWORD_MSG);
        }
        user.setPassword(getPasswordHash(newPassword));
        userDao.update(user);
    }

    public void topUpUserBalance(User user, String amount) {
        BigDecimal oldBalance = user.getBalance();
        BigDecimal income = BigDecimal.valueOf(Double.valueOf(amount));
        BigDecimal newBalance = oldBalance.add(income);
        user.setBalance(newBalance);
        update(user);
    }

    public Map<Order, List<Magazine>> getUserHistory(Long userId) {
        Map<Order, List<Magazine>> userHistory = new LinkedHashMap<>();
        OrderDao orderDao = new OrderDao();
        MagazineDao magazineDao = new MagazineDao();
        List<Order> orderList = orderDao.getOrdersByUserId(userId);
        for (Order order : orderList) {
            List<Magazine> magazineList = new ArrayList<>();
            Map<Magazine,Integer> magazineMap = magazineDao.getMagazinesByOrderId(order.getId());
            Iterator<Map.Entry<Magazine, Integer>> entries = magazineMap.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<Magazine, Integer> entry = entries.next();
                magazineList.add(entry.getKey());
            }
            userHistory.put(order, magazineList);
        }
        return userHistory;
    }

    public Map<Magazine, Timestamp> getUserLibrary(Long userId) {
        return userDao.getUserLibrary(userId);
    }
}
