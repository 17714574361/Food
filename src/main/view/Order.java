package main.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

public class Order extends JInternalFrame implements ActionListener {

    private Vector<Object> t_heads;
    private Vector<Vector<Object>> t_bodys;
    private Vector<Vector<Object>> t_orders;

    private JButton btn_add;
    private JButton btn_save;
    private JButton btn_del;
<<<<<<< HEAD
    private JButton btn_reset;
    private JLabel l_total_price;
=======
>>>>>>> 5ba82f24a6c096b666dea00ea87310f28a676d07

    private Map<String, String> map;

    private Order order;

    private JTable table_order;

    private JLabel l_desk;
    private JTextField tf_deck;

    private int removeIndex = -1;

    private float total_price = 0;

    public Order() {
        super("���", true, true, true, true);

        order = this;

        t_heads = new Vector<>();
        t_bodys = new Vector<>();
        t_orders = new Vector<>();
        t_heads.add("���");
        t_heads.add("����");
        t_heads.add("�۸�");
        t_heads.add("����");

        l_desk = new JLabel("����");
        tf_deck = new JTextField(10);

        l_total_price = new JLabel("�����ѣ�0");

        getMenu();

        JTable table = new JTable(t_bodys, t_heads);
        table_order = new JTable(t_orders, t_heads);

        JScrollPane scrollPane = new JScrollPane(table);
        JScrollPane scrollPane_order = new JScrollPane(table_order);

        btn_add = new JButton("���붩��");
        btn_save = new JButton("�ύ");
        btn_del = new JButton("�Ƴ�");
<<<<<<< HEAD

=======
>>>>>>> 5ba82f24a6c096b666dea00ea87310f28a676d07
        btn_add.addActionListener(this);
        btn_save.addActionListener(this);
        btn_del.addActionListener(this);

        add(scrollPane);
        add(btn_add);
        add(scrollPane_order);
        add(btn_del);
        add(l_desk);
        add(tf_deck);
        add(btn_save);
<<<<<<< HEAD
        add(l_total_price);
=======
        add(btn_del);
>>>>>>> 5ba82f24a6c096b666dea00ea87310f28a676d07

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //�õ�ѡ�е����е�����ֵ
                int r = table.getSelectedRow();

                //�õ�ѡ�еĵ�Ԫ���ֵ������ж����ַ���
                String id = String.valueOf(table.getValueAt(r, 0));
                String name = String.valueOf(table.getValueAt(r, 1));
                String price = String.valueOf(table.getValueAt(r, 2));
                String desc = String.valueOf(table.getValueAt(r, 3));

                map = null;
                map = new HashMap<>();

                map.put("id", id);
                map.put("name", name);
                map.put("price", price);
                map.put("desc", desc);

            }
        });
        table_order.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //�õ�ѡ�е����е�����ֵ
<<<<<<< HEAD
                int r = table_order.getSelectedRow();

                //�õ�ѡ�еĵ�Ԫ���ֵ������ж����ַ���
                String id = String.valueOf(table_order.getValueAt(r, 0));
                for (int i = 0; i < t_orders.size(); i++) {
                    for (int j = 0; j < 4; j++) {
                        //�ж���û��һ����id
                        if (id.equals(t_orders.get(i).get(j).toString())) {
//                            t_orders.remove(i);
                            removeIndex = i;
                        }
                    }
                }
=======
                int r = table.getSelectedRow();

                //�õ�ѡ�еĵ�Ԫ���ֵ������ж����ַ���
                String id = String.valueOf(table.getValueAt(r, 0));

>>>>>>> 5ba82f24a6c096b666dea00ea87310f28a676d07

            }
        });

        setLayout(new FlowLayout());
        setSize(1150, 600);
    }

    //��ȡ�˵�
    private void getMenu() {
        t_bodys.clear();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/food?characterEncoding=utf8&useSSL=false", "root", "root");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from menus");

            while (rs.next()) {
                Vector<Object> vector = new Vector<>();
                vector.add(rs.getString("id"));
                vector.add(rs.getString("name"));
                vector.add(rs.getString("price"));
                vector.add(rs.getString("descs"));
                t_bodys.add(vector);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "��������ʧ��");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "��ѯʧ��");

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btn_add) {
            Vector<Object> vector = new Vector<>();
            vector.add(map.get("id"));
            vector.add(map.get("name"));
            vector.add(map.get("price"));
            vector.add(map.get("desc"));
            t_orders.add(vector);
            table_order.validate();
            table_order.updateUI();

            total_price += Float.parseFloat(map.get("price"));

            l_total_price.setText("�����ѣ�" + total_price);

        }
        if (o == btn_del) {
            t_orders.remove(removeIndex);
            table_order.validate();
            table_order.updateUI();

        }
<<<<<<< HEAD
        //���涩��
=======
        if(o==btn_del){

        }
>>>>>>> 5ba82f24a6c096b666dea00ea87310f28a676d07
        if (o == btn_save) {
            if (map.size() == 0 || tf_deck.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "����Ϊ��");
            } else {
                Connection conn = null;
                PreparedStatement psmt = null;

                String orderId = createOrderId();

                try {
                    Class.forName("com.mysql.jdbc.Driver");
<<<<<<< HEAD
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/food?characterEncoding=utf8&useSSL=false", "root", "Loveyou123,");
=======
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/food?characterEncoding=utf8&useSSL=false", "root", "root");
>>>>>>> 5ba82f24a6c096b666dea00ea87310f28a676d07
                    psmt = conn.prepareStatement("insert into orders (name,price,descs,desk,orderId) values (?,?,?,?,?)");

                    int n = 0;
                    for (int i = 0; i < t_orders.size(); i++) {
                        Vector vector = t_orders.get(i);
                        String name = String.valueOf(vector.get(1));
                        String price = String.valueOf(vector.get(2));
                        String descs = String.valueOf(vector.get(3));
                        String desk = tf_deck.getText();
                        psmt.setString(1, name);
                        psmt.setFloat(2, Float.parseFloat(price));
                        psmt.setString(3, descs);
                        psmt.setString(4, desk);
                        psmt.setString(5, orderId);
                        n = psmt.executeUpdate();
                    }

                    if (n > 0) {
                        JOptionPane.showMessageDialog(this, "��ӳɹ�");
<<<<<<< HEAD
                        tf_deck.setText("");
                        t_orders.clear();
                        table_order.validate();
                        table_order.updateUI();
                        l_total_price.setText("�����ѣ�0");



=======
>>>>>>> 5ba82f24a6c096b666dea00ea87310f28a676d07
                    } else {
                        JOptionPane.showMessageDialog(this, "���ʧ��");
                    }

                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(this, "��������ʧ��");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(this, "���ݿ�����ʧ��");
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
        }
    }

<<<<<<< HEAD
    //����������
=======
>>>>>>> 5ba82f24a6c096b666dea00ea87310f28a676d07
    private String createOrderId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            result.append(random.nextInt(10));
        }
        return newDate + result;
    }

    @Override
    public void doDefaultCloseAction() {
        super.doDefaultCloseAction();
        Window.map.remove("order");
    }
}
