package main.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
<<<<<<< HEAD
import java.sql.*;
import java.util.HashMap;
=======
import java.util.Map;
>>>>>>> 5ba82f24a6c096b666dea00ea87310f28a676d07
import java.util.Vector;

public class OrderForm extends JInternalFrame implements ActionListener {

    private Vector<Object> t_heads;
<<<<<<< HEAD
    private Vector<Object> t_heads_detail;
    private Vector<Vector<Object>> t_bodys;
    private Vector<Vector<Object>> t_detail;
=======
    private Vector<Vector<Object>> t_bodys;
    private Vector<Vector<Object>> t_orders;

    private JButton btn_add;
    private JButton btn_save;

    private Map<String, String> map;

    private Order order;

    private JTable table_order;

    private JLabel l_desk;
    private JTextField tf_deck;


    public OrderForm(){
        super("��������",true,true,true,true);
>>>>>>> 5ba82f24a6c096b666dea00ea87310f28a676d07

    private JButton btn_search;
    private JButton btn_change;
    private JButton btn_checkOut;

    private JTable table_detail;
    private JTable table;
    private JLabel l_tp;

    private JLabel l_desk;
    private JTextField tf_desk;

    private String orderId;

    public OrderForm() {
        super("��������", true, true, true, true);

        t_heads = new Vector<>();
        t_heads_detail = new Vector<>();
        t_bodys = new Vector<>();
        t_detail = new Vector<>();

        t_heads.add("���");
        t_heads.add("������");
        t_heads.add("����");
        t_heads.add("�Ƿ����");


        t_heads_detail.add("���");
        t_heads_detail.add("������");
        t_heads_detail.add("����");
        t_heads_detail.add("�۸�");
//        t_heads_detail.add("����");

        l_desk = new JLabel("������");
        tf_desk = new JTextField(10);

        l_tp = new JLabel("�����ѣ�0");

        btn_change = new JButton("����");
        btn_checkOut = new JButton("����");

//        table_detail.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        btn_search = new JButton("ˢ��");

        getMOrder();

        table = new JTable(t_bodys, t_heads);
        table.setSize(300, 300);
        table_detail = new JTable(t_detail, t_heads_detail);

        JScrollPane scrollPane = new JScrollPane(table);
        JScrollPane scrollPane_detail = new JScrollPane(table_detail);

        add(scrollPane);
        add(scrollPane_detail);
        add(btn_search);
        add(l_desk);
        add(tf_desk);
        add(btn_change);
        add(btn_checkOut);
        add(l_tp);

        btn_search.addActionListener(this);
        btn_change.addActionListener(this);
        btn_checkOut.addActionListener(this);


        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //�õ�ѡ�е����е�����ֵ
                int r = table.getSelectedRow();

                //�õ�ѡ�еĵ�Ԫ���ֵ������ж����ַ���
                orderId = String.valueOf(table.getValueAt(r, 1));
                String desk = String.valueOf(table.getValueAt(r, 2));
//                System.out.println(orderId + "   " + desk);
                getDetail(orderId, desk);

            }
        });

        setLayout(new FlowLayout());
        setSize(1000, 600);
    }

    private void getDetail(String orderId, String desk) {
        if (t_detail.size() != 0) {
            t_detail.clear();
        }
        Connection conn = null;
        Statement stmt = null;
        int i = 1;
        float total_price = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/food?characterEncoding=utf8&useSSL=false", "root", "Loveyou123,");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from orders where desk='" + desk + "' and orderId='" + orderId + "'");

            while (rs.next()) {
                total_price += rs.getFloat("price");
                Vector<Object> vector = new Vector<>();
                vector.add(rs.getString("id"));
                vector.add(rs.getString("orderId"));
                vector.add(rs.getString("name"));
                vector.add(rs.getString("price"));
                t_detail.add(vector);
            }


            l_tp.setText("�����ѣ�" + total_price);
            table_detail.validate();
            table_detail.updateUI();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "��������ʧ��");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "��ѯʧ��");

        }
    }

    private void getMOrder() {
        t_bodys.clear();
        Connection conn = null;
        Statement stmt = null;
        int i = 1;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/food?characterEncoding=utf8&useSSL=false", "root", "Loveyou123,");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select orderId,desk,checkOut from orders group by orderId,desk,checkOut");

            while (rs.next()) {
                Vector<Object> vector = new Vector<>();
                vector.add(i++);
                vector.add(rs.getString("orderId"));
                vector.add(rs.getString("desk"));
                vector.add(rs.getInt("checkOut") == 0 ? "δ����" : "�ѽ���");
                t_bodys.add(vector);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "��������ʧ��");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "��ѯʧ��");

        }
<<<<<<< HEAD
=======
        setLayout(new FlowLayout());
        setSize(1000,600);
//        setVisible(true);
>>>>>>> 5ba82f24a6c096b666dea00ea87310f28a676d07
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btn_search) {
            getMOrder();
            table.validate();
            table.updateUI();
        }
        if (o == btn_checkOut) {
            Connection conn = null;
            Statement stmt = null;


            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/food?characterEncoding=utf8&useSSL=false", "root", "Loveyou123,");
                stmt = conn.createStatement();
                String sql = "update orders set checkOut=1 where orderId=" + orderId + "";
                int n = stmt.executeUpdate(sql);
                if (n > 0) {
                    JOptionPane.showMessageDialog(this, "���˳ɹ�");
                    t_bodys.clear();
                    getMOrder();
                    table.validate();
                    table.updateUI();
                } else {
                    JOptionPane.showMessageDialog(this, "����ʧ��");
                }

            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(this, "��������ʧ��");
            } catch (SQLException e1) {
                e1.printStackTrace();
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
        if (o == btn_change) {
            Connection conn = null;
            Statement stmt = null;

            String num = tf_desk.getText().trim();

            if (num.isEmpty()) {
                JOptionPane.showMessageDialog(this, "���Ų���Ϊ��");
            }

            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/food?characterEncoding=utf8&useSSL=false", "root", "Loveyou123,");
                stmt = conn.createStatement();
                String sql = "update orders set desk='" + num + "' where orderId=" + orderId + "";
                int n = stmt.executeUpdate(sql);
                if (n > 0) {
                    JOptionPane.showMessageDialog(this, "�����ɹ�");
                    t_bodys.clear();
                    getMOrder();
                    table.validate();
                    table.updateUI();
                } else {
                    JOptionPane.showMessageDialog(this, "����ʧ��");
                }

            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(this, "��������ʧ��");
            } catch (SQLException e1) {
                e1.printStackTrace();
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
    }

    @Override
    public void doDefaultCloseAction() {
        super.doDefaultCloseAction();
        Window.map.remove("orderForm");
    }
}
