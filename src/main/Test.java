package main;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;

public class Test {
    public static void main(String[] args) {
        JFrame jf = new JFrame("���Դ���");
        jf.setSize(400, 400);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // ���� �������
        JDesktopPane desktopPane = new JDesktopPane();
        desktopPane.setBackground(Color.BLACK);

        // ���� �ڲ�����
        JInternalFrame internalFrame = createInternalFrame();

        // ��� �ڲ����� �� �������
        desktopPane.add(internalFrame);

        // �� ������� ��Ϊ ������� ���õ����ڲ���ʾ
        jf.setContentPane(desktopPane);
        jf.setVisible(true);

        try {
            // ���� �ڲ����� ��ѡ��
            internalFrame.setSelected(true);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private static JInternalFrame createInternalFrame() {
        // ����һ���ڲ�����
        JInternalFrame internalFrame = new JInternalFrame(
                "�ڲ�����",  // title
                true,       // resizable
                true,       // closable
                true,       // maximizable
                true        // iconifiable
        );

        // ���ô��ڵĿ��
        internalFrame.setSize(200, 200);
        // ���ô��ڵ���ʾλ��
        internalFrame.setLocation(50, 50);
        // �ڲ����ڵĹرհ�ť����Ĭ�Ͼ������ٴ��ڣ����в�������
        // internalFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // �����������
        JPanel panel = new JPanel();

        // �����������
        panel.add(new JLabel("Label001"));
        panel.add(new JButton("JButton001"));

        // �����ڲ����ڵ��������
        internalFrame.setContentPane(panel);

        /*
         * �����ڲ����ڣ������Բ���Ҫ�ֶ�����������壬ֱ�ӰѴ��ڵ�����ͨ���ʹ�ã�
         * ��ֱ�����ò��֣�Ȼ��ͨ�� add �����������´���:
         *     internalFrame.setLayout(new FlowLayout());
         *     internalFrame.add(new JLabel("Label001"));
         *     internalFrame.add(new JButton("JButton001"));
         */

        // ��ʾ�ڲ�����
        internalFrame.setVisible(true);

        return internalFrame;
    }
}
