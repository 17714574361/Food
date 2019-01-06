package main.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Register extends JFrame implements ActionListener {

    private JTextField tf_user;
    private JPasswordField tf_pwd;
    private JPasswordField tf_repwd;

    private JButton btn_login;
    private JButton btn_reg;

    public Register() {
        super("注册");

        JLabel l_user = new JLabel("账号");
        JLabel l_pwd = new JLabel("密码");
        JLabel l_repwd = new JLabel("确认密码");

        tf_user = new JTextField(20);
        tf_pwd = new JPasswordField(20);
        tf_repwd = new JPasswordField(18);

        btn_login = new JButton("登录");
        btn_reg = new JButton("注册");


        add(l_user);
        add(tf_user);
        add(l_pwd);
        add(tf_pwd);
        add(l_repwd);
        add(tf_repwd);
        add(btn_login);
        add(btn_reg);

        btn_login.addActionListener(this);
        btn_reg.addActionListener(this);

        //设置是否可缩放
        setResizable(false);
        setLayout(new FlowLayout());
        setSize(300, 180);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //获取事件源
        Object o = e.getSource();
        //判断点的是哪个按钮
        if (o == btn_login) {
            dispose();
            new Login();
        }
        if (o == btn_reg) {
            //判断输入框是否为空
            if (tf_user.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "账号不能为空");
                return;
            }
            if (tf_pwd.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "密码不能为空");
                return;
            }
            //判断两次密码是否相等
            if (!tf_pwd.getText().equals(tf_repwd.getText())) {
                JOptionPane.showMessageDialog(this, "密码不一致");
                return;
            }
            Connection conn = null;
            PreparedStatement ptmt = null;
            try {
                //加载驱动
                Class.forName("com.mysql.jdbc.Driver");
                //连接数据库
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/food?characterEncoding=utf8&useSSL=false", "root", "root");
                //预编译
                ptmt = conn.prepareStatement("insert into users(users,pwd) values (?,?)");

                //给sql语句设置参数
                ptmt.setString(1, tf_user.getText());
                ptmt.setString(2, tf_pwd.getText());

                //执行sql
                ptmt.execute();

                JOptionPane.showMessageDialog(this, "注册成功");
                this.dispose();
                new Login();

            } catch (ClassNotFoundException e1) {
                JOptionPane.showMessageDialog(this, "驱动加载失败");
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(this, "数据库连接失败");
            } finally {
                if (conn != null && ptmt != null) {
                    try {
                        ptmt.close();
                        conn.close();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }

        }
    }
}
