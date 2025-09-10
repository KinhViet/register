<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng ký tài khoản</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .login-input { margin-bottom: 15px; }
        .alert { margin: 20px auto; width: 50%; text-align: center; }
    </style>
</head>
<body>
    <div class="container">
        <form action="register" method="post">
            <h2>Tạo tài khoản mới</h2>
            <c:if test="${alert != null}">
                <div class="alert ${alertType} alert-dismissible fade show" role="alert">
                    ${alert}
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:if>
            <section>
                <label class="input login-input">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="fa fa-user"></i></span>
                        <input type="text" placeholder="Tài khoản" name="username" class="form-control" required>
                    </div>
                </label>
            </section>
            <section>
                <label class="input login-input">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                        <input type="password" placeholder="Mật khẩu" name="password" class="form-control" required>
                    </div>
                </label>
            </section>
            <section>
                <label class="input login-input">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="fa fa-envelope"></i></span>
                        <input type="email" placeholder="Email" name="email" class="form-control" required>
                    </div>
                </label>
            </section>
            <section>
                <label class="input login-input">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="fa fa-user"></i></span>
                        <input type="text" placeholder="Họ và tên" name="fullname" class="form-control" required>
                    </div>
                </label>
            </section>
            <section>
                <label class="input login-input">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="fa fa-phone"></i></span>
                        <input type="text" placeholder="Số điện thoại" name="phone" class="form-control">
                    </div>
                </label>
            </section>
            <button type="submit" class="btn btn-primary">Đăng ký</button>
            <a href="${pageContext.request.contextPath}/login" class="btn btn-link">Đã có tài khoản? Đăng nhập</a>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</body>
</html>