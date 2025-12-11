import enums.ActionLetter;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();

    private final UniversalArray<PaymentDevice> paymentDevices = new UniversalArrayImpl<>();

    private static boolean isExit = false;

    private int currentAmount = 0;

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
        paymentDevices.add(new CoinAcceptor());
        paymentDevices.add(new BillAcceptor());
    }

    public static void run() {
        AppRunner app = new AppRunner();
        while (!isExit) {
            app.startSimulation();
        }
    }

    private void insertMoney() {
        Scanner sc = new Scanner(System.in);
        boolean continueInserting = true;
        while (continueInserting) {
            System.out.println("\nТекущая сумма: " + currentAmount);
            System.out.println("Что вы хотите ввести? " +
                    "\n\t1 - Монеты" +
                    "\n\t2 - Купюры" +
                    "\n\t0 - Закончить ввод");
            System.out.print("Ваш выбор: ");

            int choice;
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите число");
                sc.nextLine();
                continue;
            }

            switch (choice) {
                case 0:
                    if (currentAmount < 25) {
                        System.out.println("Недостаточно денег для покупки, введите еще");

                    } else {
                        continueInserting = false;
                    }
                    break;
                case 1:
                case 2:
                    PaymentDevice device = paymentDevices.get(choice - 1);
                    int payment = device.acceptPayment();
                    currentAmount += payment;
                    System.out.println("Вы ввели " + payment + ". Текущая сумма: " + currentAmount);
                    break;
                default:
                    System.out.println("Ошибка: несуществующее действие, введите число снова");
                    break;
            }
        }

    }

    private void startSimulation() {
        print("В автомате доступны:");
        showProducts(products);
        insertMoney();

        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());
        chooseAction(allowProducts);

    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (currentAmount >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        showActions(products);
        print(" h - Выйти");
        String action = fromConsole().substring(0, 1);
        try {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                    currentAmount -= products.get(i).getPrice();
                    print("Вы купили " + products.get(i).getName());
                    break;
                } else if ("h".equalsIgnoreCase(action)) {
                    isExit = true;
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            print("Недопустимая буква. Попробуйте еще раз.");
            chooseAction(products);
        }


    }


    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
