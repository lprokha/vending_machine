package model;

import exceptions.MoneyValueException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CoinAcceptor implements PaymentDevice {
    private Scanner sc = new Scanner(System.in);
    @Override
    public int acceptPayment() {
        System.out.println("Принимаются монеты: 1, 3, 5, 10");
        int coinValue;
        int[] permittedValues = {1, 3, 5, 10};
        while (true) {
            System.out.print("Введите номинал купюры: ");
            try {
                coinValue = sc.nextInt();

                for (int p : permittedValues) {
                    if (coinValue == p) {
                        return coinValue;
                    }
                }
                throw new MoneyValueException("Ошибка: неизвестная купюра");
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите число");
                sc.nextLine();
            } catch (MoneyValueException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
