package home .service.impl;

import home.dao.UserDao;
import home.dao.impl.UserDaoImpl;
import home.Models.User;
import home.service.UserService;
import java.sql.Date;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public void insert(User user) {
        userDao.insert(user);
    }

    @Override
    public boolean register(String email, String password, String username, String fullname, String phone) {
        if (userDao.checkExistEmail(email) || userDao.checkExistUsername(username) || userDao.checkExistPhone(phone)) {
            return false;
        }
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        userDao.insert(new User(email, username, fullname, password, null, 5, phone, date));
        return true;
    }

    @Override
    public boolean checkExistEmail(String email) {
        return userDao.checkExistEmail(email);
    }

    @Override
    public boolean checkExistUsername(String username) {
        return userDao.checkExistUsername(username);
    }

    @Override
    public boolean checkExistPhone(String phone) {
        return userDao.checkExistPhone(phone);
    }

    @Override
    public User login(String username, String password) {
        User user = userDao.findByUserName(username);
        if (user != null && user.getPassWord().equals(password)) {
            return user;
        }
        return null;
    }
}