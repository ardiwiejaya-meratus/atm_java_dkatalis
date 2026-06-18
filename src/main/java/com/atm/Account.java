package com.atm;
import java.math.BigDecimal;

public class Account {
    private String name;
    private BigDecimal balance;
    private BigDecimal owed; // Negative = owed to others, Positive = others owe us
    private String debtorName; // Track siapa yang kita owe atau yang owe kita

    public Account(String name) {
        this.name = name;
        this.balance = BigDecimal.ZERO;
        this.owed = BigDecimal.ZERO;
        this.debtorName = null;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getOwed() {
        return owed;
    }

    public void setOwed(BigDecimal owed) {
        this.owed = owed;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    public void addBalance(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public void subtractBalance(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
    }

    public void addOwed(BigDecimal amount) {
        this.owed = this.owed.add(amount);
    }

    public void subtractOwed(BigDecimal amount) {
        this.owed = this.owed.subtract(amount);
    }
}