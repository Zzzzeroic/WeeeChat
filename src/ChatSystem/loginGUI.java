package ChatSystem;

import java.awt.*;
import javax.swing.*;

public class loginGUI extends JFrame {
    private Main main;

    public void login(Main main) {
        this.main = main;
        SwingUtilities.invokeLater(() -> {
            loginGUI gui = new loginGUI(main);
            gui.setVisible(true);
        });
    }

    // 北部区域
    JLabel jLabel1;
    // 南部区域
    JButton login, quit, register;
    JPanel jPanel1;
    // 中部区域
    JTabbedPane jTabbedPane;// 选项卡
    JPanel jPanel2;
    JLabel disLabel;
    JTextField user;
    JPasswordField password;

    loginGUI(Main main) {
        super();
        this.main = main;

        // 北部区域
        ImageIcon ic = new ImageIcon("hui.jpg");
        ic.setImage(ic.getImage().getScaledInstance(256, 256, Image.SCALE_DEFAULT));
        jLabel1 = new JLabel(ic);

        // 南部区域
        jPanel1 = new JPanel();
        login = new JButton("登录");
        quit = new JButton("取消");
        register = new JButton("注册");

        login.addActionListener((e) -> onButtonLogin());
        quit.addActionListener((e) -> onButtonQuit());
        register.addActionListener((e) -> onButtonRegister());

        jPanel1.add(login);
        jPanel1.add(register);
        jPanel1.add(quit);

        jTabbedPane = new JTabbedPane();
        jPanel2 = new JPanel();
        disLabel = new JLabel("id");

        user = new JTextField(16);
        password = new JPasswordField(16);
        jPanel2.setLayout(new GridLayout(2, 1));

        jPanel2.add(disLabel);
        jPanel2.add(user);

        disLabel = new JLabel("password");
        jPanel2.add(disLabel);
        jPanel2.add(password);

        // 将面板添加到选项卡窗格上
        jTabbedPane.add("QQ号码", jPanel2);
        this.add(jLabel1, BorderLayout.NORTH);
        this.add(jPanel1, BorderLayout.SOUTH);
        this.add(jTabbedPane);
        this.setVisible(true);

        this.setLocation(200, 200);
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.pack();
        this.setTitle("WeeeChat");
    }

    private void onButtonLogin() {
        ChatSystem chatSystem = new ChatSystem();
        String loginUser = user.getText();
        String loginPass = password.getText();

        if (loginUser.equals("")) {
            Object[] options = {"OK", "CANCEL"};
            JOptionPane.showOptionDialog(null, "您还没有输入", "提示", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        } else {
            JOptionPane.showMessageDialog(this, "登陆中");
            SwingUtilities.invokeLater(() -> {
                User loggedInUser = chatSystem.login(loginUser, loginPass);
                if (loggedInUser != null) {
                    main.loggedInUser = loggedInUser;
                    Weeechat wct = new Weeechat();
                    System.out.println("User logged in successfully.");
                    wct.setLoginState();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "登录失败，请检查用户名和密码。");
                }
            });
        }
    }

    private void onButtonRegister() {
        ChatSystem chatSystem = new ChatSystem();
        String registerUser = user.getText();
        String registerPass = password.getText();
        if (registerUser.equals("")) {
            Object[] options = {"OK", "CANCEL"};
            JOptionPane.showOptionDialog(null, "您还没有输入", "提示", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        } else {
            JOptionPane.showMessageDialog(this, "注册中");
            chatSystem.registerUser(registerUser, registerPass);
        }
    }

    private void onButtonQuit() {
        dispose();
    }
}
