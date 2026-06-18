package com.atm;

import com.atm.Account;
import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;

import static org.junit.Assert.*;

public class AccountRepositoryTest {
    private AccountRepository repository;

    @Before
    public void setUp() {
        repository = new AccountRepository();
    }

    @Test
    public void testGetOrCreateNewAccount() {
        Account account = repository.getOrCreate("Alice");
        assertNotNull(account);
        assertEquals("Alice", account.getName());
        assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test
    public void testGetOrCreateExistingAccount() {
        Account account1 = repository.getOrCreate("Alice");
        account1.addBalance(new BigDecimal("100"));

        Account account2 = repository.getOrCreate("Alice");
        assertEquals(new BigDecimal("100"), account2.getBalance());
        assertSame(account1, account2); // Same object reference
    }

    @Test
    public void testGetAccount() {
        repository.getOrCreate("Bob");
        Account account = repository.getAccount("Bob");
        assertNotNull(account);
        assertEquals("Bob", account.getName());
    }

    @Test
    public void testGetAccountNonExistent() {
        Account account = repository.getAccount("NonExistent");
        assertNull(account);
    }

    @Test
    public void testAccountExists() {
        repository.getOrCreate("Charlie");
        assertTrue(repository.exists("Charlie"));
        assertFalse(repository.exists("NonExistent"));
    }

    @Test
    public void testSaveAccount() {
        Account account = new Account("Diana");
        account.addBalance(new BigDecimal("500"));
        repository.saveAccount(account);

        Account retrieved = repository.getAccount("Diana");
        assertNotNull(retrieved);
        assertEquals(new BigDecimal("500"), retrieved.getBalance());
    }

    @Test
    public void testMultipleAccounts() {
        repository.getOrCreate("Alice").addBalance(new BigDecimal("100"));
        repository.getOrCreate("Bob").addBalance(new BigDecimal("200"));
        repository.getOrCreate("Charlie").addBalance(new BigDecimal("300"));

        assertEquals(new BigDecimal("100"), repository.getAccount("Alice").getBalance());
        assertEquals(new BigDecimal("200"), repository.getAccount("Bob").getBalance());
        assertEquals(new BigDecimal("300"), repository.getAccount("Charlie").getBalance());
    }

    @Test
    public void testFreshRepositoryPerInstance() {
        AccountRepository repo1 = new AccountRepository();
        AccountRepository repo2 = new AccountRepository();

        repo1.getOrCreate("Alice").addBalance(new BigDecimal("100"));

        Account alice2 = repo2.getOrCreate("Alice");
        assertEquals(BigDecimal.ZERO, alice2.getBalance());
    }
}
