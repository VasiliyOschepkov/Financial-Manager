CREATE TABLE IF NOT EXISTS USERS
(
    NAME        TEXT    PRIMARY KEY NOT NULL,
    PASSWORD    CHAR(32)
);

CREATE TABLE IF NOT EXISTS ACCOUNTS
(
    ID           INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    NAME_ACCOUNT TEXT               NOT NULL,
    BALANCE      REAL               NOT NULL,
    DESCR        TEXT               NOT NULL,
    USER_NAME    TEXT               NOT NULL
);

CREATE TABLE IF NOT EXISTS RECORDS
(
    DATE         TEXT               NOT NULL,
    IS_PUT       TEXT               NOT NULL,
    AMOUNT       REAL               NOT NULL,
    DESCR        TEXT               NOT NULL,
    CATEGORY_ID  INT                NOT NULL,
    ACCOUNT_ID   INT                NOT NULL
);

CREATE TABLE IF NOT EXISTS CATEGORIES
(
    ID          INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    NAME        TEXT                NOT NULL
);