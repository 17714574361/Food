package main.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.sql.*;
import java.util.*;
import java.util.List;

public class Repair extends JInternalFrame implements ActionListener {

    private Vector<Object> t_heads;
    private Vector<Vector<Object>> t_bodys;
    private JButton btn_search;
    private Repair repair;

    private JTextField tf_id;
    private JTextField tf_name;
    private JTextField tf_price;
    private JTextArea tf_desc;

    private JButton btn_modify;
    private JButton btn_del;

    private JTable table;

    private Map<String, String> map;

    public Repair() {
        super("菜单维护", true, true, true, true);

        repair = this;

        t_heads = new Vector<>();
        t_bodys = new Vector<>();
        t_heads.add("编号");
        t_heads.add("菜名");
        t_heads.add("价格");
        t_heads.add("描述");

        JLabel l_id = new JLabel("编号");
        JLabel l_name = new JLabel("菜名");
        JLabel l_price = new JLabel("价格");
        JLabel l_desc = new JLabel("描述 ");

        tf_id = new JTextField(8);
        tf_name = new JTextField(10);
        tf_price = new JTextField(10);
        tf_desc = new JTextArea(5, 36);

        tf_id.setEditable(false);

        btn_modify = new JButton("修改");
        btn_del = new JButton("删除");


        getMenu();

        table = new JTable(t_bodys, t_heads);

        JScrollPane scrollPane = new JScrollPane(table);

        btn_search = new JButton("查询");

        btn_search.addActionListener(this);
        btn_modify.addActionListener(this);
        btn_del.addActionListener(this);

        add(btn_search);
        add(scrollPane);

        add(l_id);
        add(tf_id);
        add(l_name);
        add(tf_name);
        add(l_price);
        add(tf_price);
        add(l_desc);
        add(tf_desc);
        add(btn_modify);
        add(btn_del);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //得到选中的行列的索引值
                int r = table.getSelectedRow();

                //得到选中的单元格的值，表格中都是字符串
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

                tf_id.setText(id);
                tf_name.setText(name);
                tf_price.setText(price);
                tf_desc.setText(desc);

            }
        });

        setLayout(new FlowLayout());
        setSize(500, 680);
    }

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
            JOptionPane.showMessageDialog(this, "驱动连接失败");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "查询失败");

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btn_search) {
            getMenu();
            table.validate();
            table.updateUI();
        }
        if (o == btn_modify) {
            String id = tf_id.getText().trim();
            String name = tf_name.getText().trim();
            String price = tf_price.getText().trim();
            String desc = tf_desc.getText().trim();

            if (!name.equals(map.get("name")) || !price.equals(map.get("price")) || !desc.equals(map.get("desc"))) {
                Connection conn = null;
                Statement stmt = null;

                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/food?characterEncoding=utf8&useSSL=false", "root", "Loveyou123,");
//                    stmt = conn.prepareStatement("update menus set name=? and price=? and descs=? where id=?");
                    stmt = conn.createStatement();

                    System.out.println(id);
                    System.out.println(name);
                    System.out.println(price);
                    System.out.println(desc);


                    String sql = "update menus set name='" + tf_name.getText().trim() + "', price=" + price + ", descs='" + desc + "' where id=" + id + "";

                    int n = stmt.executeUpdate(sql);

                    if (n > 0) {
                        JOptionPane.showMessageDialog(this, "修改成功");
                        getMenu();
                        table.validate();
                        table.updateUI();
                    } else {
                        JOptionPane.showMessageDialog(this, "修改失败");
                    }

                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(this, "驱动连接失败");
                } catch (SQLException e1) {
                    e1.printStackTrace();
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
            } else {
                JOptionPane.showMessageDialog(this, "修改成功");
            }
        }
        if (o == btn_del) {

            String id = tf_id.getText();

            Connection conn = null;
            PreparedStatement psmt = null;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/food?characterEncoding=utf8&useSSL=false", "root", "Loveyou123,");
                psmt = conn.prepareStatement("delete from menus where id=?");

                psmt.setInt(1, Integer.parseInt(id));

                int n = psmt.executeUpdate();

                if (n > 0) {
                    JOptionPane.showMessageDialog(this, "删除成功");
                    getMenu();
                    table.validate();
                    table.updateUI();
                } else {
                    JOptionPane.showMessageDialog(this, "删除失败");
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
    }

    @Override
    public void doDefaultCloseAction() {
        super.doDefaultCloseAction();
        Window.map.remove("repair");
    }
}
