package controler;


import constan.Constans;
import model.Account;
import model.Category;
import model.Record;
import model.User;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    public static User user;
    public static Account account;
    public static Set<Account> accountSet;
    public static Set<Record> recordSet;
    public static List<Category> categoryList;


    private boolean checkBalance(String balance) {
        Pattern pattern = Pattern.compile("(\\d+\\.\\d+)|(^\\d+$)");
        Matcher matcher = pattern.matcher(balance);
        return matcher.matches();
    }

    public void enterTheProgram(String name, char[] password) {
        if (name.isEmpty() & password.length == 0) {
            Constans.loggingWindow.info.setText("Неправильно введены данные!");
        } else {
            user = Constans.dbDataStore.getUser(name, password);
            if (user == null) {
                Constans.loggingWindow.info.setText("Пользователь не найден!");
            } else {
                // При первом входе в систему, где ведеться работа с счетами,
                // подгружаем все счета пользователя, потом автоматически определяем активный счет,
                // и подгружаем все его транзакции в таблицу.
                Constans.loggingWindow.setVisible(false);
                accountSet = Constans.dbDataStore.getAccounts(user);
                categoryList = Constans.dbDataStore.getCategory();
                for (Category category : Constans.controller.categoryList) {
                    Constans.mainWindow.comboBoxCategoriesWithdrawal.addItem(category.getNameCategory());
                }
                for (Account a : accountSet) {
                    Constans.mainWindow.listOfAccounts.addItem(a.getNameAccount());
                }
                Constans.mainWindow.setVisible(true);
            }
        }
    }

    public void registration(String name, char[] password1, char[] password2) {
        if (name.isEmpty() | !Arrays.equals(password1, password2) | password1.length == 0) {
            Constans.registrationWindow.info.setText("Неправильно введены данные!");
        } else {
            Constans.dbDataStore.addUser(new User(name, password1));
        }
    }

    public void getActiveAccountAndHimRecords(String nameActiveAccount) {
        for (Account a : accountSet) {
            if (nameActiveAccount.equals(a.getNameAccount())) {
                account = a;
                break;
            }
        }
        if (account != null) {
            Constans.mainWindow.labelInfoBalance.setText(String.valueOf(account.getBalance()));
        }
        recordSet = Constans.dbDataStore.getRecords(account);
    }

    public void fillingTable() {
        Constans.tableModel.clearTable();
        try {
            for (Record record : recordSet) {
                String[] row = {
                        record.getDate(),
                        record.getIsPut(),
                        String.valueOf(record.getAmount()),
                        record.getDescription(),
                        record.getCategory().getNameCategory()
                };
                Constans.tableModel.addDate(row);
            }
        } catch (RuntimeException r) {
            r.printStackTrace();
        }
        Constans.mainWindow.repaint();
    }

    public void addMoneyToTheAccount(String amountMoney, String descriptionText) {
        Constans.mainWindow.infoRefill.setText("");
        if (!checkBalance(amountMoney)) {
            Constans.mainWindow.infoRefill.setText("Вы неправильно ввели сумму");
        } else {
            Record record = new Record(dateFormat.format(new Date()), "Пополнение", Double.valueOf(amountMoney), descriptionText, categoryList.get(0));
            Constans.dbDataStore.addRecord(record);
            String[] row = {
                    record.getDate(),
                    record.getIsPut(),
                    String.valueOf(record.getAmount()),
                    record.getDescription(),
                    record.getCategory().getNameCategory()
            };
            Constans.tableModel.addDate(row);
            Constans.mainWindow.labelInfoBalance.setText(String.valueOf(account.getBalance()));
            Constans.tableModel.fireTableDataChanged();
        }
    }

    public void writeOffMoneyFromTheAccount(String amountMoney, String nameCategory, String description) {
        Constans.mainWindow.infoWithdrawal.setText("");
        if (!checkBalance(amountMoney)) {
            Constans.mainWindow.infoWithdrawal.setText("Вы неправильно ввели сумму");
        } else if (account.getBalance() < Double.valueOf(amountMoney) | account.getBalance() == 0) {
            Constans.mainWindow.infoWithdrawal.setText("На данном счету недостаточно денег!");
        } else {
            Category activeCategory = null;
            for (Category category : categoryList) {
                if (category.getNameCategory().equals(nameCategory)) {
                    activeCategory = category;
                }
            }
            Record record = new Record(dateFormat.format(new Date()), "Снятие", Double.valueOf(amountMoney), description, activeCategory);
            Constans.dbDataStore.addRecord(record);
            String[] row = {
                    record.getDate(),
                    record.getIsPut(),
                    String.valueOf(record.getAmount()),
                    record.getDescription(),
                    record.getCategory().getNameCategory()
            };
            Constans.tableModel.addDate(row);
            Constans.mainWindow.labelInfoBalance.setText(String.valueOf(account.getBalance()));
            Constans.tableModel.fireTableDataChanged();
        }
    }

    public void addNewAccount(String nameAccount, String balanceAccount, String infoAccount) {
        if (nameAccount.isEmpty() | !checkBalance(balanceAccount)) {
            Constans.addAccountWindow.info.setText("Вы не правильно ввели данные!");
            return;
        }

        for (Account a : accountSet) {
            if (a.getNameAccount().equals(nameAccount)) {
                Constans.addAccountWindow.info.setText("Аккаунт с таким именем уже существует");
                return;
            }
        }
        Constans.addAccountWindow.setVisible(false);
        Constans.dbDataStore.addAccount(user, new Account(nameAccount, Double.valueOf(balanceAccount), infoAccount));
    }
}





