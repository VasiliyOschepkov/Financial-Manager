package view;

import constan.Constans;
import javax.swing.*;
import java.awt.event.*;

public class RegistrationWindow extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldNameUser;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    public volatile JLabel info;

    public RegistrationWindow() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(300, 200);
        setLocationRelativeTo(null);


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Constans.controller.registration(textFieldNameUser.getText(), passwordField1.getPassword(), passwordField2.getPassword());
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

    }
}
