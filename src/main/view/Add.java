package main.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Add extends JInternalFrame implements ActionListener {

    private JTextField tf_name;
    private JTextField tf_price;
    private JTextArea tf_desc;

    private JButton btn_add;
    private JButton btn_reset;


    public Add() {
        super("添加菜单", true, true, true, true);

        setLayout(new BorderLayout());
        Panel p_e = new Panel();
        Panel p_c = new Panel();

        JLabel l_name = new JLabel("菜名");
        JLabel l_price = new JLabel("价格");
        JLabel l_desc = new JLabel("描述 ");
//        JLabel l_name = new JLabel("菜名");

        tf_name = new JTextField(20);
        tf_price = new JTextField(20);
        tf_desc = new JTextArea(10, 20);

        btn_add = new JButton("添加");
        btn_reset = new JButton("清空");

        btn_add.addActionListener(this);
        btn_reset.addActionListener(this);

        p_c.add(l_name);
        p_c.add(tf_name);
        p_c.add(l_price);
        p_c.add(tf_price);
        p_c.add(l_desc);
        p_c.add(tf_desc);

        p_c.add(btn_add);
        p_c.add(btn_reset);


        add(p_e, BorderLayout.EAST);
        add(p_c, BorderLayout.CENTER);

        setSize(340, 340);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btn_add) {
            //判断输入框是否为空
            if (tf_name.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "菜名不能为空");
                return;
            }
            if (tf_price.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "价格不能为空");
                return;
            }
            if (tf_desc.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "描述不能为空");
                return;
            }
            Connection conn = null;
            PreparedStatement psmt = null;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/food?characterEncoding=utf8&useSSL=false", "root", "root");
                psmt = conn.prepareStatement("insert into menus (name,price,descs) values (?,?,?)");

                psmt.setString(1, tf_name.getText().trim());
                psmt.setFloat(2, Float.parseFloat(tf_price.getText().trim()));
                psmt.setString(3, tf_desc.getText().trim());

                int n = psmt.executeUpdate();

                if (n > 0) {
                    JOptionPane.showMessageDialog(this, "添加成功");
                } else {
                    JOptionPane.showMessageDialog(this, "添加失败");
                }

            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(this, "驱动连接失败");
            } catch (SQLException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(this, "数据库连接失败");
            } finally {
                if (conn != null && psmt != null) {
                    try {
                        psmt.close();
                        conn.close();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }

        }
        if (o == btn_reset) {
            tf_name.setText("");
            tf_price.setText("");
            tf_desc.setText("");
        }
    }

    @Override
    public void doDefaultCloseAction() {
        super.doDefaultCloseAction();
        Window.map.remove("add");
    }
}
