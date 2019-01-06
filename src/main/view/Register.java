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
        super("ע��");

        JLabel l_user = new JLabel("�˺�");
        JLabel l_pwd = new JLabel("����");
        JLabel l_repwd = new JLabel("ȷ������");

        tf_user = new JTextField(20);
        tf_pwd = new JPasswordField(20);
        tf_repwd = new JPasswordField(18);

        btn_login = new JButton("��¼");
        btn_reg = new JButton("ע��");


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

        //�����Ƿ������
        setResizable(false);
        setLayout(new FlowLayout());
        setSize(300, 180);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //��ȡ�¼�Դ
        Object o = e.getSource();
        //�жϵ�����ĸ���ť
        if (o == btn_login) {
            dispose();
            new Login();
        }
        if (o == btn_reg) {
            //�ж�������Ƿ�Ϊ��
            if (tf_user.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "�˺Ų���Ϊ��");
                return;
            }
            if (tf_pwd.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "���벻��Ϊ��");
                return;
            }
            //�ж����������Ƿ����
            if (!tf_pwd.getText().equals(tf_repwd.getText())) {
                JOptionPane.showMessageDialog(this, "���벻һ��");
                return;
            }
            Connection conn = null;
            PreparedStatement ptmt = null;
            try {
                //��������
                Class.forName("com.mysql.jdbc.Driver");
                //�������ݿ�
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/food?characterEncoding=utf8&useSSL=false", "root", "root");
                //Ԥ����
                ptmt = conn.prepareStatement("insert into users(users,pwd) values (?,?)");

                //��sql������ò���
                ptmt.setString(1, tf_user.getText());
                ptmt.setString(2, tf_pwd.getText());

                //ִ��sql
                ptmt.execute();

                JOptionPane.showMessageDialog(this, "ע��ɹ�");
                this.dispose();
                new Login();

            } catch (ClassNotFoundException e1) {
                JOptionPane.showMessageDialog(this, "��������ʧ��");
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(this, "���ݿ�����ʧ��");
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
