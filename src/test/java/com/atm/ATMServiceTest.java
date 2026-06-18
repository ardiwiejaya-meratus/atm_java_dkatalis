package com.atm;

import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;
import static org.junit.Assert.*;

public class ATMServiceTest {
    private ATMService atmService;
    private AccountRepository repository;

    @Before
    public void setUp() {
        repository = new AccountRepository();
        atmService = new ATMService(repository);
    }

    // ── Login/Logout Tests ────────────────────────────────────────
    @Test
    public void testLogin() {
        atmService.login("Alice");
        assertNotNull(atmService.getCurrentAccount());
        assertEquals("Alice", atmService.getCurrentAccount().getName());
    }

    @Test
    public void testLogout() {
        atmService.login("Alice");
        atmService.logout();
        assertNull(atmService.getCurrentAccount());
    }

    @Test(expected = IllegalStateException.class)
    public void testOperationWhenNotLoggedIn() {
        atmService.deposit(new BigDecimal("100"));
    }

    // ── Deposit Tests ────────────────────────────────────────────────
    @Test
    public void testDeposit() {
        atmService.login("Alice");
        atmService.deposit(new BigDecimal("100"));
        assertEquals(new BigDecimal("100"), atmService.getCurrentAccount().getBalance());
    }

    @Test
    public void testMultipleDeposits() {
        atmService.login("Bob");
        atmService.deposit(new BigDecimal("50"));
        atmService.deposit(new BigDecimal("75"));
        assertEquals(new BigDecimal("125"), atmService.getCurrentAccount().getBalance());
    }

    @Test
    public void testDepositZero() {
        atmService.login("Charlie");
        atmService.deposit(new BigDecimal("100"));
        atmService.deposit(new BigDecimal("0"));
        assertEquals(new BigDecimal("100"), atmService.getCurrentAccount().getBalance());
    }

    // ── Withdraw Tests ────────────────────────────────────────────────
    @Test
    public void testWithdraw() {
        atmService.login("Diana");
        atmService.deposit(new BigDecimal("100"));
        atmService.withdraw(new BigDecimal("30"));
        assertEquals(new BigDecimal("70"), atmService.getCurrentAccount().getBalance());
    }

    @Test
    public void testWithdrawMultipleTimes() {
        atmService.login("Eve");
        atmService.deposit(new BigDecimal("200"));
        atmService.withdraw(new BigDecimal("50"));
        atmService.withdraw(new BigDecimal("30"));
        assertEquals(new BigDecimal("120"), atmService.getCurrentAccount().getBalance());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawMoreThanBalance() {
        atmService.login("Frank");
        atmService.deposit(new BigDecimal("50"));
        atmService.withdraw(new BigDecimal("100")); // Should throw error
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawNegativeAmount() {
        atmService.login("Grace");
        atmService.deposit(new BigDecimal("100"));
        atmService.withdraw(new BigDecimal("-50"));
    }

    @Test
    public void testWithdrawZero() {
        atmService.login("Henry");
        atmService.deposit(new BigDecimal("100"));
        atmService.withdraw(new BigDecimal("0"));
        assertEquals(new BigDecimal("100"), atmService.getCurrentAccount().getBalance());
    }

    @Test(expected = IllegalStateException.class)
    public void testWithdrawWhenNotLoggedIn() {
        atmService.withdraw(new BigDecimal("50"));
    }

    // ── Transfer Tests ────────────────────────────────────────────────
    @Test
    public void testTransfer() {
        atmService.login("Ivan");
        atmService.deposit(new BigDecimal("100"));
        atmService.transfer("Jack", new BigDecimal("30"));
        
        assertEquals(new BigDecimal("70"), atmService.getCurrentAccount().getBalance());
        Account jack = repository.getAccount("Jack");
        assertEquals(new BigDecimal("30"), jack.getBalance());
    }

    @Test
    public void testTransferMultipleTimes() {
        atmService.login("Kate");
        atmService.deposit(new BigDecimal("200"));
        atmService.transfer("Liam", new BigDecimal("50"));
        atmService.transfer("Mia", new BigDecimal("75"));
        
        assertEquals(new BigDecimal("75"), atmService.getCurrentAccount().getBalance());
        assertEquals(new BigDecimal("50"), repository.getAccount("Liam").getBalance());
        assertEquals(new BigDecimal("75"), repository.getAccount("Mia").getBalance());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferMoreThanBalance() {
        atmService.login("Noah");
        atmService.deposit(new BigDecimal("50"));
        atmService.transfer("Olivia", new BigDecimal("100")); // Should throw error
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferNegativeAmount() {
        atmService.login("Paul");
        atmService.deposit(new BigDecimal("100"));
        atmService.transfer("Quinn", new BigDecimal("-50"));
    }

    @Test
    public void testTransferZero() {
        atmService.login("Rachel");
        atmService.deposit(new BigDecimal("100"));
        atmService.transfer("Steve", new BigDecimal("0"));
        assertEquals(new BigDecimal("100"), atmService.getCurrentAccount().getBalance());
    }

    @Test(expected = IllegalStateException.class)
    public void testTransferWhenNotLoggedIn() {
        atmService.transfer("Tina", new BigDecimal("50"));
    }

    @Test
    public void testTransferToNewAccount() {
        atmService.login("Uma");
        atmService.deposit(new BigDecimal("100"));
        atmService.transfer("Victor", new BigDecimal("40"));
        
        Account victor = repository.getAccount("Victor");
        assertNotNull(victor);
        assertEquals(new BigDecimal("40"), victor.getBalance());
    }

    // ── Complex Scenarios ────────────────────────────────────────
    @Test
    public void testMultipleAccountsIndependent() {
        atmService.login("Wendy");
        atmService.deposit(new BigDecimal("150"));
        atmService.transfer("Xavier", new BigDecimal("50"));
        
        atmService.login("Xavier");
        atmService.deposit(new BigDecimal("100"));
        atmService.withdraw(new BigDecimal("30"));
        
        assertEquals(new BigDecimal("120"), atmService.getCurrentAccount().getBalance());
        assertEquals(new BigDecimal("100"), repository.getAccount("Wendy").getBalance());
    }

    @Test
    public void testDecimalPrecision() {
        atmService.login("Yara");
        atmService.deposit(new BigDecimal("99.99"));
        atmService.withdraw(new BigDecimal("25.50"));
        assertEquals(new BigDecimal("74.49"), atmService.getCurrentAccount().getBalance());
    }
}