package bank;

import java.math.BigDecimal;
import java.util.Scanner;

public class BankSystem {
    public static void main(String[] args){
        Account amyAccount = new Account("Amy", new BigDecimal(100));
        Account bobAccount = new Account("Bob", new BigDecimal(100));

        System.out.println("========Before");
        System.out.println(amyAccount);
        System.out.println(bobAccount);

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        scanner.close();
        amyAccount.transfer(bobAccount, new BigDecimal(100));

        System.out.println("========After");
        System.out.println(amyAccount);
        System.out.println(bobAccount);
    }
}
