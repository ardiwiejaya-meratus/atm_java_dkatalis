package com.atm;

import java.math.BigDecimal;

public class ATMService {
    private AccountRepository repository;
    private Account currentAccount;

    public ATMService(AccountRepository repository) {
        this.repository = repository;
    }

    public void login(String name) {
        currentAccount = repository.getOrCreate(name);
    }

    public void logout() {
        currentAccount = null;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }
	
	public void withdraw(BigDecimal amount) {
		if (!isLoggedIn()) {
			throw new IllegalStateException("Not logged in");
		}
		
		if (amount.signum() < 0) {
			throw new IllegalArgumentException("Amount must be non-negative");
		}
		
		// RESTRICT: Check sufficient balance
		if (currentAccount.getBalance().compareTo(amount) < 0) {
			throw new IllegalArgumentException("Insufficient balance");
		}
		
		currentAccount.subtractBalance(amount);
		repository.saveAccount(currentAccount);
	}

    public void transfer(String recipientName, BigDecimal amount) {
		if (!isLoggedIn()) {
			throw new IllegalStateException("Not logged in");
		}
		
		if (amount.signum() < 0) {
			throw new IllegalArgumentException("Amount must be non-negative");
		}
		
		// Allow zero transfer (no-op)
		if (amount.signum() == 0) {
			return;
		}
		
		// RESTRICT: Check sufficient balance
		if (currentAccount.getBalance().compareTo(amount) < 0) {
			throw new IllegalArgumentException("Insufficient balance");
		}
		
		Account recipient = repository.getOrCreate(recipientName);
		
		// Direct transfer only (no debt tracking)
		currentAccount.subtractBalance(amount);
		recipient.addBalance(amount);
		
		repository.saveAccount(currentAccount);
		repository.saveAccount(recipient);
	}

    public void deposit(BigDecimal amount) {
		if (!isLoggedIn()) {
			throw new IllegalStateException("Not logged in");
		}
		
		// Settle owed amount if exists
		if (currentAccount.getOwed().signum() < 0) { // Negative = we owe others
			BigDecimal owed = currentAccount.getOwed().negate();
			BigDecimal toTransfer = amount.min(owed);
			
			// Use deposit to settle debt
			currentAccount.addOwed(toTransfer); // Reduces negative owed (makes it less negative)
			BigDecimal remaining = amount.subtract(toTransfer);
			currentAccount.addBalance(remaining); // Add remaining to balance
			
			// If creditor exists, transfer money to them
			if (toTransfer.signum() > 0 && currentAccount.getDebtorName() != null) {
				Account creditor = repository.getAccount(currentAccount.getDebtorName());
				creditor.addBalance(toTransfer);
				creditor.subtractOwed(toTransfer);
				repository.saveAccount(creditor);
				
				// Clear debtor if fully settled
				if (currentAccount.getOwed().signum() >= 0) {
					currentAccount.setDebtorName(null);
				}
			}
		} else {
			// No debt, just add to balance
			currentAccount.addBalance(amount);
		}
		
		repository.saveAccount(currentAccount);
	}

    public boolean isLoggedIn() {
        return currentAccount != null;
    }
}