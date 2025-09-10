package home.dao;

import home.Models.User;

public interface UserDao {
    void insert(User user);
    boolean checkExistEmail(String email);
    boolean checkExistUsername(String username);
    boolean checkExistPhone(String phone);
    User findByUserName(String username);
    User findByEmail(String email);
    User findByResetToken(String resetToken);
    void updatePassword(User user);
}