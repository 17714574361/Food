package main.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    private JTextField tf_user;
    private JPasswordField tf_pwd;

    private JButton btn_login;
    private JButton btn_reg;

    public Login() {
        super("登录");

        //创建控件
        JLabel l_user = new JLabel("账号");
        JLabel l_pwd = new JLabel("密码");

        tf_user = new JTextField(20);
        tf_pwd = new JPasswordField(20);

        btn_login = new JButton("登录");
        btn_reg = new JButton("注册");


        //添加控件
        add(l_user);
        add(tf_user);
        add(l_pwd);
        add(tf_pwd);
        add(btn_login);
        add(btn_reg);

        //给按钮添加点击事件
        btn_login.addActionListener(this);
        btn_reg.addActionListener(this);

        //设置是否可缩放
        setResizable(false);
        //设置布局
        setLayout(new FlowLayout());
        //设置宽度
        setSize(300, 120);
        //设置窗口是否可见
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btn_login) {
            if (tf_user.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "账号不能为空");
                return;
            }
            if (tf_pwd.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "密码不能为空");
                return;
            }

            Connection conn = null;
            Statement stmt = null;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/food?characterEncoding=utf8&useSSL=false", "root", "root");
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from users where users='" + tf_user.getText() + "' and pwd='" + tf_pwd.getText() + "'");

                if (rs.next()) {
                    dispose();
                    new Window();
                } else {
                    JOptionPane.showMessageDialog(this, "登录失败");
                }

            } catch (ClassNotFoundException e1) {
                JOptionPane.showMessageDialog(this, "驱动加载失败");
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(this, "数据库连接失败");
            } finally {
                if (conn != null && stmt != null) {
                    try {
                        stmt.close();
                        conn.close();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }


        }
        if (o == btn_reg) {
            dispose();
            new Register();
        }
    }
}
