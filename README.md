# 猜数游戏 (J2EE Homework)

一个简单的 Java 项目，实现了一个图形化界面的猜数字游戏。用户可以进行登录、注册，并参与猜数游戏，系统会在每次用户猜对时重新开始游戏，并将游戏结果保存到 MySQL 数据库。

## 功能

- **用户登录与注册**：用户需要先登录才能进行游戏。如果用户没有账号，可以进行注册。
- **猜数游戏**：用户输入猜测数字，并根据提示（如“猜的数字太大了”或“猜的数字太小了”）进行调整，直到猜对数字。
- **游戏重置**：每当用户猜对数字时，游戏会重新生成一个新的随机数，并开始新一轮的游戏。
- **记录保存**：每次猜测的记录（包括猜测的数字和结果）都会保存到数据库中，方便后续查看。

## 技术栈

- **编程语言**：Java 21
- **图形界面**：Java Swing
- **数据库**：MySQL
- **构建工具**：Maven
- **依赖管理**：JDBC

## 项目结构
GuessingGame
├── src
│   └── main
│       └── java
│           └── com
│               └── example
│                   ├── DBUtil.java         # 数据库连接工具类
│                   ├── GameApp.java        # 游戏入口类
│                   ├── GameGUI.java        # 游戏界面类
│                   ├── LoginGUI.java       # 登录界面类
│                   ├── UserService.java    # 用户服务类（包含登录、注册功能）
│                   ├── MD5Util.java        # MD5加密工具类
├── pom.xml                # Maven 构建文件
├── target/                # 编译后文件目录
└── README.md              # 项目说明文档

## 数据库设计
- **Database**：guessinggame
+-------------------------+
| Tables_in_guessing_game |
+-------------------------+
| guessing_records        |
| users                   |
+-------------------------+
- **table**：guessing_records
+--------------+------------+------+-----+---------+----------------+
| Field        | Type       | Null | Key | Default | Extra          |
+--------------+------------+------+-----+---------+----------------+
| id           | int        | NO   | PRI | NULL    | auto_increment |
| user_id      | int        | YES  | MUL | NULL    |                |
| guess_number | int        | YES  |     | NULL    |                |
| guess_time   | timestamp  | YES  |     | NULL    |                |
| success      | tinyint(1) | YES  |     | NULL    |                |
+--------------+------------+------+-----+---------+----------------+
- **table**：users
+-------------+--------------+------+-----+---------+----------------+
| Field       | Type         | Null | Key | Default | Extra          |
+-------------+--------------+------+-----+---------+----------------+
| id          | int          | NO   | PRI | NULL    | auto_increment |
| username    | varchar(50)  | NO   |     | NULL    |                |
| password    | varchar(255) | NO   |     | NULL    |                |
| phone       | varchar(15)  | YES  |     | NULL    |                |
| login_count | int          | YES  |     | 0       |                |
| last_login  | timestamp    | YES  |     | NULL    |                |
+-------------+--------------+------+-----+---------+----------------+
