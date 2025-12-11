package model;

import exceptions.MoneyValueException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BillAcceptor implements PaymentDevice {
    private Scanner sc = new Scanner(System.in);
    private int[] bills = {20, 50, 100, 200, 500, 1000};
    @Override
    public int acceptPayment() {
        System.out.println("Принимаются купюры: 20, 50, 100, 200, 500, 1000");
        int billValue;

        while (true) {
            System.out.print("Введите номинал купюры: ");
            try {
                billValue = sc.nextInt();

                for (int b : bills) {
                    if (billValue == b) {
                        return billValue;
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
