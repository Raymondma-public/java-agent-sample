       /*
        * Decompiled with CFR.
        */
       package bank;
       
       import java.math.BigDecimal;
       
       public class Account {
           private String name;
           private BigDecimal balance;
       
           public String getName() {
/*19*/         return this.name;
           }
       
           public Account(String name, BigDecimal balance) {
/*10*/         this.name = name;
/*11*/         this.balance = balance;
           }
       
           public String toString() {
               return "bank.Account{name='" + this.name + '\'' + ", balance=" + this.balance + '}';
           }
       
           public void transfer(Account toAccount, BigDecimal amount) {
/*23*/         this.balance = this.balance.subtract(amount);
/*24*/         toAccount.balance = toAccount.balance.add(amount);
           }
       
           public BigDecimal getBalance() {
/*15*/         return this.balance;
           }
       }

