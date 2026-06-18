package com.atm;
import com.atm.AccountRepository;
import com.atm.ATMService;
import com.atm.ATMCLI;

public class ATMApp {
    public static void main(String[] args) {
        // Setup: Create fresh repository for each run
        AccountRepository repository = new AccountRepository();
        ATMService atmService = new ATMService(repository);
        ATMCLI cli = new ATMCLI(atmService);

        // Start CLI
        cli.start();
    }
}
