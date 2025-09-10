package home.service.impl;

import home.dao.UserDao;
import home.dao.impl.UserDaoImpl;
import home.Models.User;
import home.service.UserService;
import home.util.SendMail;
import java.sql.Date;
import java.util.UUID;

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
        User user = new User(email, username, fullname, password, null, 5, phone, date);
        userDao.insert(user);

        // Gửi email xác nhận đăng ký
        try {
            SendMail sm = new SendMail();
            String content = "Chào " + fullname + ",<br>Chúc mừng bạn đã đăng ký thành công tài khoản với username: " + username + ".<br>Vui lòng đăng nhập tại: <a href='http://localhost:8080/log_in/login'>Đăng nhập</a>";
            sm.sendMail(email, "Xác nhận đăng ký tài khoản", content);
        } catch (Exception e) {
            e.printStackTrace();
            // Lưu ý: Không trả về false ở đây để không làm thất bại đăng ký nếu gửi email lỗi
        }
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

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public boolean sendResetPasswordEmail(String email) {
        User user = userDao.findByEmail(email);
        if (user == null) {
            return false;
        }
        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        userDao.updatePassword(user);

        // Gửi email liên kết đặt lại mật khẩu
        try {
            SendMail sm = new SendMail();
            String resetLink = "http://localhost:8080/log_in/resetpassword?token=" + resetToken;
            String content = "Nhấn vào liên kết để đặt lại mật khẩu: <a href='" + resetLink + "'>Đặt lại mật khẩu</a>";
            sm.sendMail(email, "Khôi phục mật khẩu", content);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User findByResetToken(String resetToken) {
        return userDao.findByResetToken(resetToken);
    }

    @Override
    public void updatePassword(User user) {
        userDao.updatePassword(user);
    }
}