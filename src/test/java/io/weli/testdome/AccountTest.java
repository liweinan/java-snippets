package io.weli.testdome;


import org.junit.jupiter.api.Test;
import org.testng.Assert;

// // https://www.testdome.com/library?page=1&skillArea=30&questionId=38495
public class AccountTest {
    private double epsilon = 1e-6;

    // Test negative overdraft limit
    @Test
    public void accountCannotHaveNegativeOverdraftLimit() {
        Account account = new Account(-20);
        Assert.assertEquals(0d, account.getOverdraftLimit(), epsilon);
    }

    // Test deposit does not accept negative numbers and returns false
    @Test
    public void depositShouldRejectNegativeAmount() {
        Account account = new Account(100);
        boolean result = account.deposit(-50);
        Assert.assertFalse(result);
        Assert.assertEquals(0d, account.getBalance(), epsilon);
    }

    // Test withdraw does not accept negative numbers and returns false
    @Test
    public void withdrawShouldRejectNegativeAmount() {
        Account account = new Account(100);
        boolean result = account.withdraw(-50);
        Assert.assertFalse(result);
        Assert.assertEquals(0d, account.getBalance(), epsilon);
    }

    // Test deposit updates correct amount and returns true
    @Test
    public void depositShouldUpdateCorrectAmount() {
        Account account = new Account(100);
        boolean result = account.deposit(50);
        Assert.assertTrue(result);
        Assert.assertEquals(50d, account.getBalance(), epsilon);
    }

    // Test withdraw updates correct amount, respects overdraft limit, and returns correct result
    @Test
    public void withdrawShouldHandleCorrectly() {
        Account account = new Account(100);
        account.deposit(50); // Balance = 50
        // Valid withdraw
        boolean result1 = account.withdraw(50);
        Assert.assertTrue(result1);
        Assert.assertEquals(0d, account.getBalance(), epsilon);
        // Exceeds overdraft limit
        boolean result2 = account.withdraw(150);
        Assert.assertFalse(result2);
        Assert.assertEquals(0d, account.getBalance(), epsilon);
    }
}