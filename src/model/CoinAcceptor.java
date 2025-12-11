package model;

import exceptions.MoneyValueException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CoinAcceptor implements PaymentDevice {
    private Scanner sc = new Scanner(System.in);
    private int[] coins = {1, 3, 5, 10};
    @Override
    public int acceptPayment() {
        System.out.println("Принимаются монеты: 1, 3, 5, 10");
        int coinValue;
        while (true) {
            System.out.print("Введите номинал монеты: ");
            try {
                coinValue = sc.nextInt();

                for (int c : coins) {
                    if (coinValue == c) {
                        return coinValue;
                    }
                }
                throw new MoneyValueException("Ошибка: неизвестная монета");
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите число");
                sc.nextLine();
            } catch (MoneyValueException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
