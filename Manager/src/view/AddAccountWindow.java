package view;

import constan.Constans;

import javax.swing.*;
import java.awt.event.*;

public class AddAccountWindow extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nameAccountField;
    private JTextField balanceAccountField;
    private JTextArea infoAccountArea;
    public JLabel info;

    public AddAccountWindow() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(600,500);
        setLocationRelativeTo(null);
        pack();


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Constans.controller.addNewAccount(nameAccountField.getText(), balanceAccountField.getText(), infoAccountArea.getText());
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

    }


}
