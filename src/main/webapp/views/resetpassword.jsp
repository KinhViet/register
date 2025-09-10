<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đặt lại mật khẩu</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .login-input { margin-bottom: 15px; }
        .alert { margin: 20px auto; width: 50%; text-align: center; }
    </style>
</head>
<body>
    <div class=" developmental">
        <form action="resetpassword" method="post">
            <h2>Đặt lại mật khẩu</h2>
            <c:if test="${alert != null}">
                <div class="alert ${alertType} alert-dismissible fade show" role="alert">
                    ${alert}
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:if>
            <input type="hidden" name="token" value="${token}">
            <section>
                <label class="input login-input">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                        <input type="password" placeholder="Mật khẩu mới" name="newPassword" class="form-control" required>
                    </div>
                </label>
            </section>
            <button type="submit" class="btn btn-primary">Cập nhật mật khẩu</button>
            <a href="${pageContext.request.contextPath}/login" class="btn btn-link">Quay lại đăng nhập</a>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</body>
</html>