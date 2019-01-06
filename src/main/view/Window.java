package main.view;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Window extends JFrame implements ActionListener, MenuListener {

    private JDesktopPane desktopPane;
    private JMenuBar menuBar;
    private JMenu m_menu;
    private JMenu m_order;
    private JMenu m_order_form;
    private JMenu m_pwd;

    private JMenuItem mi_add;
    private JMenuItem mi_repair;

    public static Map<String, JInternalFrame> map = new HashMap<>();

    public Window() {

        menuBar = new JMenuBar();
        m_menu = new JMenu("菜单管理");
        m_order_form = new JMenu("订单管理");
        m_order = new JMenu("点餐");
        m_pwd = new JMenu("修改密码");

        mi_add = new JMenuItem("添加菜单");
        mi_repair = new JMenuItem("菜单维护");

        menuBar.add(m_menu);
        menuBar.add(m_order);
        menuBar.add(m_order_form);
        menuBar.add(m_pwd);

        m_menu.add(mi_add);
        m_menu.add(mi_repair);

        m_order.addMenuListener(this);
        m_order_form.addMenuListener(this);
        m_pwd.addMenuListener(this);
        mi_add.addActionListener(this);
        mi_repair.addActionListener(this);

        desktopPane = new JDesktopPane();
        desktopPane.setBackground(Color.BLACK);
        desktopPane.setVisible(true);


        //给窗体设置菜单栏
        setJMenuBar(menuBar);

        add(desktopPane);

        //设置是否可缩放
//        setResizable(false);
//        setLayout(new FlowLayout());
        setSize(1400, 800);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        System.out.println(o);
        if (o == mi_add) {
            if (!map.containsKey("add")) {
                Add add = new Add();
                map.put("add", add);
                desktopPane.add(add);
                add.setVisible(true);
            }
        }
        if (o == mi_repair) {
            if (!map.containsKey("repair")) {
                Repair repair = new Repair();
                map.put("repair", repair);
                desktopPane.add(repair);
                repair.setVisible(true);
            }
        }
    }

    @Override
    public void menuSelected(MenuEvent e) {
        Object o = e.getSource();
        System.out.println(o);
        if (o == m_order) {
            if (!map.containsKey("order")) {
                Order order = new Order();
                map.put("order", order);
                desktopPane.add(order);
                order.setVisible(true);
            }
        }
        if (o == m_order_form) {
            if (!map.containsKey("orderForm")) {
                OrderForm orderForm = new OrderForm();
                map.put("orderForm", orderForm);
                desktopPane.add(orderForm);
                orderForm.setVisible(true);
            }
        }
        if (o == m_pwd) {
            if (!map.containsKey("pwd")) {
                Pwd pwd = new Pwd();
                map.put("pwd", pwd);
                desktopPane.add(pwd);
                pwd.setVisible(true);
            }
        }
    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }
}
