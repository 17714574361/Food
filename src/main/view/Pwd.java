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
        super("�޸�����", true, true, true, true);

        JLabel l_user = new JLabel("�˺�");
        JLabel l_pwd = new JLabel("����");
        JLabel l_repwd = new JLabel("ȷ������");

        tf_user = new JTextField(20);
        tf_pwd = new JPasswordField(20);
        tf_repwd = new JPasswordField(18);

        btn_ok = new JButton("ȷ��");

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
                ptmt = conn.prepareStatement("update users set pwd=? where users=?");

                //��sql������ò���
                ptmt.setString(1, tf_pwd.getText());
                ptmt.setString(2, tf_user.getText());

                //ִ��sql
                int i = ptmt.executeUpdate();

                if (i > 0) {
                    JOptionPane.showMessageDialog(null, "�޸�����ɹ�");
                } else {
                    JOptionPane.showMessageDialog(null, "�޸�����ʧ��");

                }

            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "��������ʧ��");
            } catch (SQLException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "���ݿ�����ʧ��");
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
