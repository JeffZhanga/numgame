package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.security.NoSuchAlgorithmException;

public class UserService {

    // 保存猜测记录
    public void saveGuessRecord(String username, int guessNumber, int isSuccess) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO guessing_records (user_id, guess_number, guess_time, success) " +
                         "VALUES ((SELECT id FROM users WHERE username = ?), ?, NOW(), ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setInt(2, guessNumber);
                stmt.setInt(3, isSuccess);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 用户注册方法
    public boolean registerUser(String username, String password, String phone) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO users (username, password, phone, login_count, last_login) VALUES (?, ?, ?, 0, NOW())";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, MD5Util.encrypt(password));  // 使用MD5加密密码
                stmt.setString(3, phone);
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 用户登录方法
    public boolean loginUser(String username, String password) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, MD5Util.encrypt(password));  // 加密后比较密码
                return stmt.executeQuery().next();  // 如果能查询到用户，则说明登录成功
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }
}
