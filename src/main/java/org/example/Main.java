package org.example;
import java.io.IOException;
import java.util.*;
import java.util.Scanner;

public class Main {
    static ArrayList<Army> army = new ArrayList<>();
    static ArrayList<Food> food = new ArrayList<>();
    static Food meat;

    static Log myLog;
    static {
        try {
            myLog = new Log("mainLogger.log", "mainLog");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            meat = new Food("Meat", 100);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static Food water;

    static {
        try {
            water = new Food("Water", 100);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static boolean isRider = false;

    public static void main(String[] args)  {
        food.add(meat); food.add(water);
        System.out.println("Добро пожаловать в средневековый замок!");
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (choice != 11) {
            System.out.println("===== Game Menu =====");
            System.out.println("1. Добавить солдат в армию");
            System.out.println("2. Удалить солдат из армии");
            System.out.println("3. Обновить воинское звание");
            System.out.println("4. Накормить армию");
            System.out.println("5. Пополнить запасы продовольствия");
            System.out.println("6. Добавить продукты в хранилище");
            System.out.println("7. Узнать количество солдат в армии");
            System.out.println("8. Удаление запасов еды");
            System.out.println("9. Узнать текущие запасы еды");
            System.out.println("10. Узнать характеристики");
            System.out.println("11. Выход из игрового меню");

            System.out.print("Введите свой выбор: ");
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                myLog.logger.warning("Предупреждение: Пожалуйста, введите число.");
                scanner.next();
                continue;
            }
            switch (choice) {
                case 1 -> {
                    System.out.print("Введите звание: ");
                    scanner.nextLine();
                    String rank = scanner.nextLine();
                    try {
                        System.out.print("Введите количество: ");
                        int count = Integer.parseInt(scanner.nextLine());
                        int special1;
                        int special2;
                        if (!isRider) {
                            System.out.print("Введите уровень луков: ");
                            special1 = Integer.parseInt(scanner.nextLine());
                            System.out.print("Введите уровень стрел: ");
                            special2 = Integer.parseInt(scanner.nextLine());
                        } else {
                            System.out.print("Введите количество лошадей: ");
                            special1 = Integer.parseInt(scanner.nextLine());
                            System.out.print("Введите крепкость брони: ");
                            special2 = Integer.parseInt(scanner.nextLine());
                        }
                        addArmy(rank, count, special1, special2);
                        myLog.logger.info("Солдаты успешно добавлены в армию!");
                        isRider = !isRider;
                    } catch (NumberFormatException e) {
                        myLog.logger.severe("Ошибка: введите число");
                    }
                }
                case 2 -> {
                    System.out.print("Введите звание: ");
                    scanner.nextLine();
                    String rank1 = scanner.nextLine();

                    boolean rankExists = false;
                    for (Army person : army) {
                        if (person.getRank().equals(rank1)) {
                            rankExists = true;
                            break;
                        }
                    }

                    if (!rankExists) {
                        myLog.logger.severe("Такого звания не существует");
                    } else {
                        boolean correctInput = false;
                        do {
                            try {
                                myLog.logger.info("Введите количество доступное: ");
                                int count1 = Integer.parseInt(scanner.nextLine());
                                if (count1 > 0) {
                                    int totalSoldiers = 0;
                                    for (Army soldier : army) {
                                        if (Objects.equals(soldier.getRank(), rank1)) {
                                            totalSoldiers += soldier.getCount();
                                        }
                                    }

                                    if (count1 <= totalSoldiers) {
                                        removeArmy(rank1, count1);
                                        correctInput = true;
                                    } else {
                                        myLog.logger.severe("Ошибка: введенное количество превышает доступное количество солдат этого звания");
                                    }
                                } else {
                                    myLog.logger.severe("Ошибка: количество должно быть больше 0");
                                }
                            } catch (NumberFormatException e) {
                                myLog.logger.severe("Ошибка ввода количества. Введите число.");
                            }
                        } while (!correctInput);
                    }
                }
                case 3 -> {

                    System.out.print("Введите старое звание: ");
                    scanner.nextLine();
                    String rank1 = scanner.nextLine();

                    boolean rankExists = false;
                    for (Army person : army) {
                        if (person.getRank().equals(rank1)) {
                            rankExists = true;
                            System.out.print("Введите новое звание: ");
                            scanner.nextLine();
                            String rankNew = scanner.nextLine();
                            person.setRank(rankNew); // установить новое звание
                            break;
                        }
                    }

                    if (!rankExists) {
                        myLog.logger.severe("Такого звания не существует");
                    } else {
                        myLog.logger.info("Введите количество войнов: ");
                        int countNew = 0;
                        try {
                            countNew = Integer.parseInt(scanner.nextLine());
                            if (countNew <= 0) {
                                myLog.logger.severe("Число должно быть больше нуля");
                            } else {
                                // Добавьте здесь логику обработки введенного количества
                            }
                        } catch (NumberFormatException e) {
                            myLog.logger.severe("Ошибка: введите целое число");
                        }
                    }
                }
                case 4 -> {
                    myLog.logger.info("Введите количество порций еды: ");
                    boolean inputValid = false;
                    while (!inputValid) {
                        try {
                            if (scanner.hasNextInt()) {
                                int portions = scanner.nextInt();
                                scanner.nextLine();
                                feedArmy(portions);
                                inputValid = true; // Set inputValid to true if input was successful
                            } else {
                                throw new NumberFormatException(); // Throw an exception to handle incorrect input
                            }
                        } catch (NumberFormatException e) {
                            myLog.logger.severe("Ошибка ввода количества. Введите число.");
                            scanner.nextLine(); // Reset input to skip the incorrect value
                        }
                    }
                }

                case 5 -> {
                    boolean validInput = false;
                    boolean errorMessageDisplayed = false; // Track if error message has been displayed
                    myLog.logger.info("Введите количество еды для запаса: ");
                    while (!validInput) {
                        try {
                            if (scanner.hasNextInt()) {
                                int foodSupply = scanner.nextInt();
                                if (foodSupply > 0) {
                                    addSupply(foodSupply);
                                    validInput = true;
                                } else {
                                    if (!errorMessageDisplayed) {
                                        myLog.logger.severe("Ошибка ввода. Введите неотрицательное целое число.");
                                        errorMessageDisplayed = true; // Set flag to true after displaying error message
                                    }
                                }
                            } else {
                                if (!errorMessageDisplayed) {
                                    myLog.logger.severe("Ошибка ввода. Введите целое число.");
                                    errorMessageDisplayed = true; // Set flag to true after displaying error message
                                }
                                scanner.nextLine(); // Move this line here to skip the incorrect value
                            }
                        } catch (InputMismatchException e) {
                            if (!errorMessageDisplayed) {
                                myLog.logger.severe("Ошибка ввода. Введите целое число.");
                                errorMessageDisplayed = true; // Set flag to true after displaying error message
                            }
                            scanner.nextLine(); // Reset input to skip the incorrect value
                        }
                    }
                }//доделать

                case 6 -> {
                    myLog.logger.info("Введите название продукта, количество которого вы хотите увеличить: ");
                    scanner.nextLine();
                    String nameFood = scanner.nextLine();

                    int foodCount = 0;
                    boolean validInput = false;
                    while (!validInput) {
                        myLog.logger.info("Введите количество продуктов для добавления: ");
                        String input = scanner.nextLine();
                        try {
                            foodCount = Integer.parseInt(input);
                            if (foodCount > 0) {
                                validInput = true;
                            } else {
                                myLog.logger.severe("Количество продуктов должно быть больше 0. Пожалуйста, введите еще раз.");
                            }
                        } catch (NumberFormatException e) {
                            myLog.logger.severe("Введите целочисленное значение для количества продуктов. Пожалуйста, введите еще раз.");
                        }
                    }

                    boolean isAvailable = isFoodTypeAvailable(nameFood);
                    if (isAvailable) {
                        modifySupply(nameFood, foodCount);
                    } else {
                        myLog.logger.severe("Этот вид не входит в состав продуктов питания");
                    }
                }
                case 7 -> {
                    printArmySize();
                }
                case 8 -> {
                    boolean validInput = false;
                    while (!validInput) {
                        myLog.logger.info("Введите название еды, которую хотите удалить в количестве: ");
                        scanner.nextLine();
                        String nameFood2 = scanner.nextLine();

                        if (!isFoodTypeAvailable(nameFood2)) {
                            myLog.logger.severe("Такой вид еды не существует. Пожалуйста, введите корректное название.");
                            // продолжаем цикл, чтобы запросить ввод снова
                        }

                        int foodCount2 = 0;
                        while (!validInput) {
                            myLog.logger.info("Введите количество еды для удаления: ");
                            String input = scanner.nextLine();
                            try {
                                foodCount2 = Integer.parseInt(input);
                                if (foodCount2 >= 0) {
                                    validInput = true;
                                } else {
                                    myLog.logger.severe("Количество еды должно быть целочисленным и не отрицательным. Пожалуйста, введите еще раз.");
                                }
                            } catch (NumberFormatException e) {
                                myLog.logger.severe("Введите целочисленное значение для количества еды. Пожалуйста, введите еще раз.");
                            }
                        }

                        removeSupply(nameFood2, foodCount2);
                    }
                }
                case 9 -> {
                    myLog.logger.info("Текущие запасы еды: ");
                    printFoodSupply();
                }
                case 10 -> {
                    boolean validId = false;
                    while (!validId) {
                        myLog.logger.info("Введите id война");
                        if (scanner.hasNextInt()) {
                            int id = scanner.nextInt();
                            if (id >= army.size()) {
                                myLog.logger.info("Id не существует");
                            } else {
                                army.get(id).Work();
                                validId = true; // Устанавливаем флаг в true, чтобы выйти из цикла
                            }
                        } else {
                            myLog.logger.warning("Id должно быть целым числом");
                        }
                        break;
                    }
                }
                case 11 -> {
                    System.out.println("Выход из игрового меню");
                }
                default -> {
                    myLog.logger.severe("Неверный выбор. Пожалуйста, попробуйте снова.");
                }
            }
        }
        scanner.close();
    }
    public static void addArmy(String rank, int count, int special1, int special2) {
        Army member;
        if (isRider){
            member = new Riders(rank, count, special1, special2);
        }
        else {
            member = new Archers(rank, count, special1, special2);
        }
        army.add(member);
    }
    public static void removeArmy(String rank, int count) {
        Iterator<Army> iterator = army.iterator();
        while (iterator.hasNext()) {
            Army item = iterator.next();
            if (Objects.equals(item.getRank(), rank)) {
                item.setCount(item.getCount() - count);
                if (item.getCount() <= 0) {
                    iterator.remove();
                    break;
                }
            }
        }
    }
    public static void updateArmy(String oldRank, String newRank, int newCount) {
        boolean foundOldRank = false;

        for (Army armyMember : army) {
            if (armyMember.getRank().equals(oldRank)) {
                foundOldRank = true;
                if (newCount > 0) {
                    armyMember.setRank(newRank);
                    armyMember.setCount(newCount);
                } else {
                    System.out.println("Новый подсчет < 0");
                }
                break;
            }
        }

        if (!foundOldRank) {
            myLog.logger.severe("Такого звания не существует");
        }
    }

    public static void printArmySize() {
        myLog.logger.info("Солдат в армии: " + army.size());
    }

    public static void feedArmy(Integer portions) {
        if (portions > 0) {
            int allmembers = 0;
            for(Army member : army){
                allmembers += member.count;
            }
            int allPortions = allmembers*portions;
            boolean isEnough = true;
            for(Food item : food){
                if(!(item.getCount() >= allPortions)) {
                    isEnough = false;
                }
            }
            if(isEnough){
                for(Food item : food){
                    item.setCount(item.getCount()-allPortions);
                }
            }
            else {
                int deficit = allPortions - food.getFirst().getCount();
                for (Army member : army) {
                    member.setCount(member.getCount()-deficit);
                }
                for(Food item : food){
                    item.setCount(item.getCount()-allPortions);
                }
            }
        } else {
            myLog.logger.severe("Порции не могут быть меньше 0");
        }
    }
    public static void addSupply(int count){
        for (Food supply : food)
        {
            supply.setCount(count);
        }
    }
    public static void removeSupply(String type, int count) {
        for (Food item : food){
            if(Objects.equals(item.getType(), type)){
                if (item.getCount() >= count) {
                    item.setCount(count);
                } else {
                    myLog.logger.severe("Количество не может быть больше текущего количества");
                }
                return;
            }
        }
        myLog.logger.severe("Этот тип не входит в состав продуктов питания");
    }
    public static void modifySupply(String type, int count) {
        for(Food item : food){
            if(Objects.equals(item.getType(), type)){
                if (item.getCount() + count >= 0) {
                    item.setCount(item.getCount() + count);
                } else {
                    myLog.logger.severe("Сумма меньше 0");
                }
                return;
            }
        }
        myLog.logger.severe("Этот тип не входит в состав продуктов питания");
    }
    public static boolean isFoodTypeAvailable(String type) {
        for (Food item : food) {
            if (Objects.equals(item.getType(), type)) {
                return true;
            }
        }
        return false;
    }
    public static void printFoodSupply() {
        myLog.logger.info("Текущие запасы продовольствия:");
        for (Food item : food) {
            System.out.println(item.getType() + ": " + item.getCount());
        }
    }
}