package home.service;

import home.Models.User;

public interface UserService {
    void insert(User user);
    boolean register(String email, String password, String username, String fullname, String phone);
    boolean checkExistEmail(String email);
    boolean checkExistUsername(String username);
    boolean checkExistPhone(String phone);
    User login(String username, String password);
    User findByEmail(String email);
    boolean sendResetPasswordEmail(String email);
    User findByResetToken(String resetToken);
    void updatePassword(User user);
}