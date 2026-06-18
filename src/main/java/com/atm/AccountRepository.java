package com.atm;

import com.atm.Account;
import java.util.HashMap;
import java.util.Map;

public class AccountRepository {
    private Map<String, Account> accounts;

    public AccountRepository() {
        this.accounts = new HashMap<>();
    }

    public Account getOrCreate(String name) {
        return accounts.computeIfAbsent(name, Account::new);
    }

    public Account getAccount(String name) {
        return accounts.get(name);
    }

    public void saveAccount(Account account) {
        accounts.put(account.getName(), account);
    }

    public boolean exists(String name) {
        return accounts.containsKey(name);
    }
}
