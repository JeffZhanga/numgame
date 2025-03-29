package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameGUI extends JFrame {
    private int targetNumber;
    private int attempts;
    private JTextField guessField;
    private JTextArea resultArea;
    private JButton guessButton;
    private String username;
    private UserService userService;

    public GameGUI(String username) {
        this.username = username;
        this.userService = new UserService();  // 创建 UserService 实例

        setTitle("猜数字游戏 - " + username);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Random random = new Random();
        targetNumber = random.nextInt(100) + 1;
        attempts = 0;

        // 创建组件
        JLabel instructionLabel = new JLabel("请输入一个 1 到 100 之间的数字:");
        guessField = new JTextField(10);
        guessButton = new JButton("猜测");
        resultArea = new JTextArea(5, 20);
        resultArea.setEditable(false);

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String guessText = guessField.getText();
                try {
                    int guess = Integer.parseInt(guessText);
                    attempts++;
                    String result = checkGuess(guess);
                    resultArea.append(result + "\n");
                    if (guess == targetNumber) {
                        // 猜对了后记录猜测，并提示游戏开始新的一轮
                        userService.saveGuessRecord(username, guess, 1); // 保存猜测记录，猜对时 success 为 1
                        resultArea.append("游戏已重新开始！请继续猜测。\n");
                        resetGame();  // 重新开始游戏
                    }
                } catch (NumberFormatException ex) {
                    resultArea.append("请输入有效的数字！\n");
                }
            }
        });

        // 创建一个面板来布局组件
        JPanel panel = new JPanel();
        panel.add(instructionLabel);
        panel.add(guessField);
        panel.add(guessButton);

        // 将组件添加到窗口
        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        setSize(400, 300);
        setLocationRelativeTo(null);  // 窗口居中
        setVisible(true);
    }

    // 检查用户的猜测
    private String checkGuess(int guess) {
        if (guess < targetNumber) {
            return "您猜的是 " + guess + "，猜的数字太小了！";
        } else if (guess > targetNumber) {
            return "您猜的是 " + guess + "，猜的数字太大了！";
        } else {
            return "恭喜你，猜对了！您猜的是 " + guess + "，尝试次数: " + attempts;
        }
    }

    // 重置游戏
    private void resetGame() {
        Random random = new Random();
        targetNumber = random.nextInt(100) + 1;  // 生成新的随机数
        attempts = 0;  // 重置尝试次数
        guessField.setText("");  // 清空输入框
        // 不立即清空结果显示区域，而是等待用户重新猜测
    }

    public static void main(String[] args) {
        // 启动游戏GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameGUI("用户1");  // 默认用户
            }
        });
    }
}
