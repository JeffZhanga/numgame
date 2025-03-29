package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextArea resultArea;
    private UserService userService;

    public LoginGUI() {
        userService = new UserService();  // 创建 UserService 实例

        setTitle("登录 - 猜数字游戏");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 创建组件
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        JLabel usernameLabel = new JLabel("用户名:");
        usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("密码:");
        passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("登录");
        JButton registerButton = new JButton("注册");

        resultArea = new JTextArea(5, 20);
        resultArea.setEditable(false);

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
            }
        });

        setSize(400, 300);
        setLocationRelativeTo(null);  // 窗口居中显示
        setVisible(true);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (userService.loginUser(username, password)) {
            resultArea.setText("登录成功！\n进入游戏...");
            // 启动游戏界面
            new GameGUI(username);  // 登录成功后启动游戏
            dispose();  // 关闭登录窗口
        } else {
            resultArea.setText("登录失败，请检查用户名和密码。\n");
        }
    }

    private void handleRegister() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // 注册新用户
        if (userService.registerUser(username, password, "dummy_phone")) {
            resultArea.setText("注册成功！请登录。\n");
        } else {
            resultArea.setText("注册失败，请稍后再试。\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginGUI();
            }
        });
    }
}
