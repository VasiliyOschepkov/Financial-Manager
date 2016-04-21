package view;

import constan.Constans;
import javax.swing.*;
import javax.swing.plaf.synth.SynthButtonUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton buttonAddAccount;
    public JLabel labelInfoBalance;
    public JComboBox listOfAccounts;
    private JPanel panelTable;
    public JTable table1;
    private JTextField textFieldAmountMoneyRefill;
    private JTextField descriptionRefill;
    private JButton buttonOKRefill;
    private JTextField textFieldAmountMoneyWithdrawal;
    public JComboBox comboBoxCategoriesWithdrawal;
    private JTextField textAreaDescrWithdrawal;
    private JButton buttonOKWithdrawal;
    public JLabel infoRefill;
    public JLabel infoWithdrawal;

    public MainWindow() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(800, 500);
        setLocationRelativeTo(null);


        buttonOKRefill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Constans.controller.addMoneyToTheAccount(textFieldAmountMoneyRefill.getText(), descriptionRefill.getText());
            }
        });

        buttonAddAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Constans.addAccountWindow.setVisible(true);
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonOKWithdrawal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Constans.controller.writeOffMoneyFromTheAccount(textFieldAmountMoneyWithdrawal.getText(), comboBoxCategoriesWithdrawal.getSelectedItem().toString(), textAreaDescrWithdrawal.getText());

            }
        });

        listOfAccounts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Constans.controller.getActiveAccountAndHimRecords(listOfAccounts.getSelectedItem().toString());
                Constans.controller.fillingTable();
                Constans.mainWindow.validate();
            }
        });
    }

    public void createUIComponents() {
        Constans.tableModel = new TableModel();
        table1 = new JTable(Constans.tableModel);
    }
}
