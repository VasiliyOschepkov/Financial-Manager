package model;


import java.util.Set;

public interface DataStore {
    // return null if no such user
    User getUser(String name, char[] password);

    // If no users, return empty collection (not null)
    Set<String> getUserNames();

    // If no accounts, return empty collection (not null)
    Set<Account> getAccounts(User owner);

    // If no records, return empty collection (not null)
    Set<Record> getRecords(Account account);

    void addUser(User user);

    void addAccount(User user, Account account);

    void addRecord(Record record);

    // return removed User or null if no such user
    User removeUser(String name);

    // return null if no such account
    Account removeAccount(User owner, Account account);

    // return null if no such record
    Record removeRecord(Account from, Record record);
}
