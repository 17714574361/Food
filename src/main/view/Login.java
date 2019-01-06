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
        super("��¼");

        //�����ؼ�
        JLabel l_user = new JLabel("�˺�");
        JLabel l_pwd = new JLabel("����");

        tf_user = new JTextField(20);
        tf_pwd = new JPasswordField(20);

        btn_login = new JButton("��¼");
        btn_reg = new JButton("ע��");


        //��ӿؼ�
        add(l_user);
        add(tf_user);
        add(l_pwd);
        add(tf_pwd);
        add(btn_login);
        add(btn_reg);

        //����ť��ӵ���¼�
        btn_login.addActionListener(this);
        btn_reg.addActionListener(this);

        //�����Ƿ������
        setResizable(false);
        //���ò���
        setLayout(new FlowLayout());
        //���ÿ��
        setSize(300, 120);
        //���ô����Ƿ�ɼ�
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btn_login) {
            if (tf_user.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "�˺Ų���Ϊ��");
                return;
            }
            if (tf_pwd.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "���벻��Ϊ��");
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
                    JOptionPane.showMessageDialog(this, "��¼ʧ��");
                }

            } catch (ClassNotFoundException e1) {
                JOptionPane.showMessageDialog(this, "��������ʧ��");
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(this, "���ݿ�����ʧ��");
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
