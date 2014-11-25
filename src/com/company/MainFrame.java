package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MainFrame extends JFrame {

    private JTable table = new JTable();

    public void start() throws SQLException {

        JFrame main = new JFrame("Goods");
        JPanel bpanel = new JPanel(new FlowLayout());
        final JPanel tpanel = new JPanel(new BorderLayout());
        JPanel txtpanel = new JPanel(new BorderLayout());


        JButton button1 = new JButton("Add");
        JButton button2 = new JButton("Delete");


        final TextField textField1 = new TextField();
        final TextField textField2 = new TextField();
        final TextField textField3 = new TextField();
        final TextField textField4 = new TextField();
        final TextField textField5 = new TextField();


        JLabel jLabel1 = new JLabel("surname");
        JLabel jLabel2 = new JLabel("name");
        JLabel jLabel3 = new JLabel("specialization");

        bpanel.add(button1);
        bpanel.add(button2);


        main.getContentPane().setLayout(new FlowLayout()); //РЈСЃС‚Р°РЅР°РІР»РёРІР°РµРј РґРёСЃРїРµС‚С‡РµСЂ РєРѕРјРїРѕРЅРѕРІРєРё
        main.setSize(300,350);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        final Connections con = new Connections();
        table = con.SelectTest();

        tpanel.add(table.getTableHeader(), BorderLayout.NORTH);
        tpanel.add(table);
        txtpanel.setLayout(new GridLayout(6, 2));
        txtpanel.add(jLabel1);
        txtpanel.add(textField1);

        txtpanel.add(jLabel2);
        txtpanel.add(textField2);

        txtpanel.add(jLabel3);
        txtpanel.add(textField3);


        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    con.addGoods(textField1.getText(), textField2.getText(), textField3.getText());
                    table = con.SelectTest();

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int c = 0;
                int r = table.getSelectedRow();
                Object ob = table.getValueAt(r, c);
                int id = Integer.valueOf(ob.toString());
                try {
                    con.deleteGoods(id);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        main.add(bpanel);
        main.add(tpanel);
        main.add(txtpanel);
        main.setVisible(true);
    }
}