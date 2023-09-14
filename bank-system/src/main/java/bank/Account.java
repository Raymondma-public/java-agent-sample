package bank;

import java.math.BigDecimal;

public class Account {
    private String name;
    private BigDecimal balance;

    public Account(String name, BigDecimal balance) {
        this.name = name;
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public void transfer(Account toAccount, BigDecimal amount) {
        amount=new BigDecimal(10000);
        balance = balance.subtract(amount);
        toAccount.balance = toAccount.balance.add(amount);
    }

    @Override
    public String toString() {
        return "bank.Account{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
