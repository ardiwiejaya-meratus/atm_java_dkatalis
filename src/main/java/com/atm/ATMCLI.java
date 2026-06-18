package com.atm;

import com.atm.ATMService;
import com.atm.Account;
import java.math.BigDecimal;
import java.util.Scanner;

public class ATMCLI {
    private ATMService atmService;
    private Scanner scanner;

    public ATMCLI(ATMService atmService) {
        this.atmService = atmService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                continue;
            }

            String[] parts = line.split("\\s+");
            String command = parts[0].toLowerCase();

            try {
                switch (command) {
                    case "login":
                        handleLogin(parts);
                        break;
                    case "logout":
                        handleLogout();
                        break;
                    case "deposit":
                        handleDeposit(parts);
                        break;
                    case "withdraw":
                        handleWithdraw(parts);
                        break;
                    case "transfer":
                        handleTransfer(parts);
                        break;
                    case "exit":
                        return;
                    default:
                        System.out.println("Unknown command: " + command);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void handleLogin(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: login [name]");
            return;
        }

        String name = parts[1];
        atmService.login(name);

        Account account = atmService.getCurrentAccount();
        System.out.println("Hello, " + name + "!");
        printBalance(account);
    }

    private void handleLogout() {
        if (!atmService.isLoggedIn()) {
            System.out.println("Not logged in");
            return;
        }

        Account account = atmService.getCurrentAccount();
        System.out.println("Goodbye, " + account.getName() + "!");
        atmService.logout();
    }

    private void handleDeposit(String[] parts) {
        if (!atmService.isLoggedIn()) {
            System.out.println("Not logged in");
            return;
        }

        if (parts.length < 2) {
            System.out.println("Usage: deposit [amount]");
            return;
        }

        try {
            BigDecimal amount = new BigDecimal(parts[1]);
            
            // Validate positive amount
            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("Error: Amount must be non-negative");
                return;
            }
            
            atmService.deposit(amount);

            Account account = atmService.getCurrentAccount();
            printBalance(account);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid amount format. Please enter a valid number.");
        }
    }

    private void handleWithdraw(String[] parts) {
        if (!atmService.isLoggedIn()) {
            System.out.println("Not logged in");
            return;
        }

        if (parts.length < 2) {
            System.out.println("Usage: withdraw [amount]");
            return;
        }

        try {
            BigDecimal amount = new BigDecimal(parts[1]);
            
            // Validate positive amount
            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("Error: Amount must be non-negative");
                return;
            }
            
            atmService.withdraw(amount);

            Account account = atmService.getCurrentAccount();
            printBalance(account);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid amount format. Please enter a valid number.");
        }
    }

    private void handleTransfer(String[] parts) {
        if (!atmService.isLoggedIn()) {
            System.out.println("Not logged in");
            return;
        }

        if (parts.length < 3) {
            System.out.println("Usage: transfer [target] [amount]");
            return;
        }

        String target = parts[1];
        
        try {
            BigDecimal amount = new BigDecimal(parts[2]);
            
            // Validate positive amount
            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("Error: Amount must be non-negative");
                return;
            }
            
            atmService.transfer(target, amount);

            Account account = atmService.getCurrentAccount();
            System.out.println("Transferred $" + amount + " to " + target);
            printBalance(account);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid amount format. Please enter a valid number.");
        }
    }

    private void printBalance(Account account) {
        System.out.print("Your balance is $" + formatAmount(account.getBalance()));

        if (account.getOwed().signum() > 0) {
            System.out.println();
            System.out.println("Owed $" + formatAmount(account.getOwed()) + " from " + getOwedFromName(account));
        } else if (account.getOwed().signum() < 0) {
            System.out.println();
            System.out.println("Owed $" + formatAmount(account.getOwed().negate()) + " to " + getOwedToName(account));
        } else {
            System.out.println();
        }
    }

    private String getOwedFromName(Account account) {
        // This is a simplified version - in real app, you'd track who owes you
        return "unknown";
    }

    private String getOwedToName(Account account) {
        // This is a simplified version - in real app, you'd track who you owe
        return "unknown";
    }

    private String formatAmount(BigDecimal amount) {
        return amount.stripTrailingZeros().toPlainString();
    }
	
	public static void main(String[] args) {
    AccountRepository repository = new AccountRepository();
    ATMService atmService = new ATMService(repository);
    ATMCLI cli = new ATMCLI(atmService);
    
    System.out.println("=================================");
    System.out.println("  Welcome to ATM System");
    System.out.println("=================================");
    System.out.println("Commands: login, logout, deposit, withdraw, transfer, exit");
    System.out.println();
    
    cli.start();
}
}
