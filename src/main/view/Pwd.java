package main.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Pwd extends JInternalFrame implements ActionListener {

    private JTextField tf_user;
    private JPasswordField tf_pwd;
    private JPasswordField tf_repwd;

    private JButton btn_ok;

    public Pwd() {
        super("修改密码", true, true, true, true);

        JLabel l_user = new JLabel("账号");
        JLabel l_pwd = new JLabel("密码");
        JLabel l_repwd = new JLabel("确认密码");

        tf_user = new JTextField(20);
        tf_pwd = new JPasswordField(20);
        tf_repwd = new JPasswordField(18);

        btn_ok = new JButton("确定");

        btn_ok.addActionListener(this);

        add(l_user);
        add(tf_user);
        add(l_pwd);
        add(tf_pwd);
        add(l_repwd);
        add(tf_repwd);
        add(btn_ok);

        requestFocusInWindow(true);
        setLayout(new FlowLayout());
        setSize(340, 260);
//        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
//        System.out.println(o);
        if (o == btn_ok) {
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
                ptmt = conn.prepareStatement("update users set pwd=? where users=?");

                //给sql语句设置参数
                ptmt.setString(1, tf_pwd.getText());
                ptmt.setString(2, tf_user.getText());

                //执行sql
                int i = ptmt.executeUpdate();

                if (i > 0) {
                    JOptionPane.showMessageDialog(null, "修改密码成功");
                } else {
                    JOptionPane.showMessageDialog(null, "修改密码失败");

                }

            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "驱动加载失败");
            } catch (SQLException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "数据库连接失败");
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

    @Override
    public void doDefaultCloseAction() {
        super.doDefaultCloseAction();
        Window.map.remove("pwd");
    }
}
