package com.atm;

import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;

import static org.junit.Assert.*;

public class AccountTest {
    private Account account;

    @Before
    public void setUp() {
        account = new Account("TestUser");
    }

    @Test
    public void testAccountCreation() {
        assertEquals("TestUser", account.getName());
        assertEquals(BigDecimal.ZERO, account.getBalance());
        assertEquals(BigDecimal.ZERO, account.getOwed());
    }

    @Test
    public void testAddBalance() {
        account.addBalance(new BigDecimal("100"));
        assertEquals(new BigDecimal("100"), account.getBalance());
    }

    @Test
    public void testSubtractBalance() {
        account.addBalance(new BigDecimal("100"));
        account.subtractBalance(new BigDecimal("50"));
        assertEquals(new BigDecimal("50"), account.getBalance());
    }

    @Test
    public void testSubtractBalanceNegative() {
        account.subtractBalance(new BigDecimal("50"));
        assertEquals(new BigDecimal("-50"), account.getBalance());
    }

    @Test
    public void testAddOwed() {
        account.addOwed(new BigDecimal("30"));
        assertEquals(new BigDecimal("30"), account.getOwed());
    }

    @Test
    public void testSubtractOwed() {
        account.addOwed(new BigDecimal("50"));
        account.subtractOwed(new BigDecimal("30"));
        assertEquals(new BigDecimal("20"), account.getOwed());
    }

    @Test
    public void testAddOwedNegative() {
        account.addOwed(new BigDecimal("-50"));
        assertEquals(new BigDecimal("-50"), account.getOwed());
    }

    @Test
    public void testMultipleOperations() {
        account.addBalance(new BigDecimal("100"));
        account.subtractBalance(new BigDecimal("25"));
        account.addOwed(new BigDecimal("50"));

        assertEquals(new BigDecimal("75"), account.getBalance());
        assertEquals(new BigDecimal("50"), account.getOwed());
    }

    @Test
    public void testSettersAndGetters() {
        account.setBalance(new BigDecimal("250"));
        account.setOwed(new BigDecimal("-100"));

        assertEquals(new BigDecimal("250"), account.getBalance());
        assertEquals(new BigDecimal("-100"), account.getOwed());
    }

    @Test
    public void testBigDecimalPrecision() {
        account.addBalance(new BigDecimal("0.1"));
        account.addBalance(new BigDecimal("0.2"));
        
        // Should be exactly 0.3, not 0.30000000000000004 (float precision issue)
        assertEquals(new BigDecimal("0.3"), account.getBalance());
    }
}
