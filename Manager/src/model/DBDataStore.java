package model;

import constan.Constans;
import db.DataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DBDataStore implements DataStore {
    private static DataBase dataBase = DataBase.init();
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    @Override
    public User getUser(String name, char[] password) {
        User user = null;
        try {
            stmt = dataBase.getConn().prepareStatement("SELECT * FROM USERS WHERE NAME=?");
            stmt.setString(1, name);
            rs = stmt.executeQuery();

            if (!rs.isClosed()) {
                  if(!rs.getString("NAME").isEmpty() & Arrays.equals(password, rs.getString("PASSWORD").toCharArray())) {
                      user = new User(name, password);
                  }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataBase.closeResource(stmt);
            dataBase.closeConnection();
            return user;
        }
    }

    @Override
    public Set<String> getUserNames() {
        return null;
    }

    @Override
    public Set<Account> getAccounts(User owner) {
        Set<Account> accountSet = new LinkedHashSet<Account>();
        try {
            stmt = dataBase.getConn().prepareStatement("SELECT * FROM ACCOUNTS WHERE USER_NAME=?");
            stmt.setString(1, owner.getUserName());
            rs = stmt.executeQuery();

            while (rs.next()) {
                String nameAccount = rs.getString("NAME_ACCOUNT");
                String balanceAccount = rs.getString("BALANCE");
                String descriptionAccount = rs.getString("DESCR");
                String idAccount = rs.getString("ID");
                accountSet.add(new Account(nameAccount, Double.valueOf(balanceAccount), descriptionAccount, Integer.valueOf(idAccount)));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dataBase.closeResource(stmt);
            dataBase.closeConnection();
        }
        return accountSet;
    }

    @Override
    public Set<Record> getRecords(Account account) {
        Set<Record> recordSet = new LinkedHashSet<Record>();
        try {
            stmt = dataBase.getConn().prepareStatement("SELECT * FROM RECORDS WHERE ACCOUNT_ID=? ");
            stmt.setString(1, String.valueOf(account.getId()));
            rs = stmt.executeQuery();

            while (rs.next()) {
                String dateRecord = rs.getString("DATE");
                String isPutRecord = rs.getString("IS_PUT");
                String amountRecord = rs.getString("AMOUNT");
                String descriptionRecord = rs.getString("DESCR");
                String idCategoryRecord = rs.getString("CATEGORY_ID");

                recordSet.add(new Record(dateRecord, isPutRecord, Double.valueOf(amountRecord), descriptionRecord, Constans.controller.categoryList.get(Integer.valueOf(idCategoryRecord))));
            }

        }catch (NullPointerException a) {
            return recordSet; //todo
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dataBase.closeResource(stmt);
            dataBase.closeConnection();
        }
        return recordSet;
    }

    @Override
    public void addUser(User user) {
        try {
            stmt = dataBase.getConn().prepareStatement("SELECT * FROM USERS WHERE NAME=?");
            stmt.setString(1, user.getUserName());
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (user.getUserName().equals(rs.getString("NAME"))) {
                    Constans.registrationWindow.info.setText("Пользователь с таким именем уже есть");
                    return;
                }
            }

            stmt = dataBase.getConn().prepareStatement("INSERT INTO USERS (NAME, PASSWORD) VALUES (?, ?);");
            stmt.setString(1, user.getUserName());
            stmt.setString(2, String.valueOf(user.getPassword()));
            stmt.executeUpdate();

            Constans.registrationWindow.dispose();
            Constans.loggingWindow.info.setText("Регистрация прошла успешно!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataBase.closeResource(stmt);
            dataBase.closeConnection();
        }
    }

    @Override
    public void addAccount(User user, Account account) {
        try {
            stmt = dataBase.getConn().prepareStatement("INSERT INTO ACCOUNTS (NAME_ACCOUNT, BALANCE, DESCR, USER_NAME) VALUES (?, ?, ?, ?)");
            stmt.setString(1, account.getNameAccount());
            stmt.setString(2, String.valueOf(account.getBalance()));
            stmt.setString(3, account.getDescription());
            stmt.setString(4, user.getUserName());
            stmt.executeUpdate();

            stmt = dataBase.getConn().prepareStatement("SELECT ID FROM ACCOUNTS WHERE NAME_ACCOUNT=?");
            stmt.setString(1, account.getNameAccount());
            rs = stmt.executeQuery();

            while (rs.next()) {
                String idAccount = rs.getString("ID");
                Constans.controller.accountSet.add(new Account(account.getNameAccount(), account.getBalance(), account.getDescription(), Integer.valueOf(idAccount)));
                Constans.mainWindow.listOfAccounts.addItem(account.getNameAccount());
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dataBase.closeResource(stmt);
            dataBase.closeConnection();
        }
    }

    @Override
    public void addRecord(Record record) {
        try {
            stmt = dataBase.getConn().prepareStatement("INSERT INTO RECORDS (DATE, IS_PUT, AMOUNT, DESCR, CATEGORY_ID, ACCOUNT_ID) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, record.getDate());
            stmt.setString(2, record.getIsPut());
            stmt.setString(3, String.valueOf(record.getAmount()));
            stmt.setString(4, record.getDescription());
            stmt.setString(5, String.valueOf(record.getCategory().getId()));
            stmt.setString(6, String.valueOf(Constans.controller.account.getId()));
            stmt.executeUpdate();

            stmt = dataBase.getConn().prepareStatement("UPDATE ACCOUNTS SET BALANCE=? WHERE NAME_ACCOUNT=?");
            if (record.getIsPut().equals("Пополнение")) {
                Constans.controller.account.setBalance(Constans.controller.account.getBalance() + record.getAmount());
                stmt.setString(1, String.valueOf(Constans.controller.account.getBalance()));
            }else {
                Constans.controller.account.setBalance(Constans.controller.account.getBalance() - record.getAmount());
                stmt.setString(1, String.valueOf(Constans.controller.account.getBalance()));
            }
            stmt.setString(2, Constans.controller.account.getNameAccount());
            stmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dataBase.closeResource(stmt);
            dataBase.closeConnection();
        }
    }

    @Override
    public User removeUser(String name) {
        //todo
        return null;
    }

    @Override
    public Account removeAccount(User owner, Account account) {
        //todo
        return null;
    }

    @Override
    public Record removeRecord(Account from, Record record) {
        //todo
        return null;
    }

    public List<Category> getCategory() {
        List<Category> categoryList = new ArrayList<Category>();
        try {
            stmt = dataBase.getConn().prepareStatement("SELECT * FROM CATEGORIES");
            rs = stmt.executeQuery();

            while (rs.next()) {
                String nameCategory = rs.getString("NAME");
                String idCategory = rs.getString("ID");
                categoryList.add(new Category(nameCategory, idCategory));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            dataBase.closeResource(rs);
            dataBase.closeResource(stmt);
            dataBase.closeConnection();
        }
        return categoryList;
    }
}
