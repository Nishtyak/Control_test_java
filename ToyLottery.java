import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;


public class ToyLottery {
    private int id;
    private String name;
    private int count;
    private double chance;

    /*Функция для записи изменненых данных при выпадении игрушки*/
    private ToyLottery ToyLottery (int id, String name, int count, double chance) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.chance = chance;
        return this;
    }

    /*инициализация */
    public ToyLottery (int id, String name, int count, double chance) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.chance = chance;
    }

    /*используемая инициализация для высчитывания шанса выпадения игрушки с добавлением игрушки в коллекцию*/
    public ToyLottery (String name, int count, Set<ToyLottery> toys) {
        this.id = toys.size() + 1;
        this.name = name;
        this.count = count;
        this.chance = calculateChance(toys, count);
        toys.add(ToyLottery(this.id, this.name, this.count, this.chance));
    }

    /*высчитывание шанса выадения игрушки при добавлении игрушек*/
    public int calculateChance(Set<ToyLottery> toys, int count) {
        int sum = count;
        for (ToyLottery toy : toys) {
            sum += toy.getCount();
        }

        for (ToyLottery toy : toys) {
            double result =(double) Math.round((double) toy.getCount() / (double) sum * 100);
            toy.setChance(result);
        }

        return (int) Math.round((double) count / (double) sum * 100);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public double getChance() {
        return chance;
    }

    public void setChance(double newValue) {
        this.chance = newValue;
    }

    public void setCount (int newValue) {
        this.count = newValue;
    }

    @Override
    public String toString() {
        return id + " " + name + " кол-во:" + count + " " + chance + "%";
    }

    /*Получение рандомной игрушки*/
    public static String getRandomToy (Set<ToyLottery> toys) {
        Random random = new Random();
        int chance = random.nextInt(1, 100);
        int sumChances = 0;
        ToyLottery chanceToy = new ToyLottery(0, null, 0, 0);

        for (ToyLottery toy : toys) {
            sumChances += toy.getChance();
            if (chance <= sumChances) {
                chanceToy = toy;
                break;
            }
        }

        changeChances(chanceToy, toys);
        return chanceToy.getName();
    }

    /*Изменение шанса выпадения игрушек после выпадения призовой игрушки*/
    private static void changeChances (ToyLottery toy, Set<ToyLottery> toys) {
        toy.setCount(toy.getCount() - 1);

        int sum = 0;

        for (ToyLottery toyLottery : toys) {
            sum += toyLottery.getCount();
        }

        for (ToyLottery toyLottery : toys) {
            double result =(double) Math.ceilDiv(toyLottery.getCount()  * 10000, (int) sum) / 100;
            toyLottery.setChance(result);
        }
    }

    /*проведение лотереи*/
    public static List<String> haveLottery (int lotteryNum, Set<ToyLottery> toys) {
        List<String> prizes = new ArrayList<>();

        for (int i = 0; i < lotteryNum; i++) {
            prizes.add(ToyLottery.getRandomToy(toys));
        }

        return prizes;
    }

    /*Получить приз из выпавших игрушек*/
    public static void getPrize (List<String> prizes) {
        System.out.println();
        if(prizes.size() > 0) {
            String prize = prizes.remove(0);
            try(FileWriter fileWriter = new FileWriter("receivedPrizes.txt", true);) {
                fileWriter.write(prize + "\n");
                System.out.println("Ваш приз: " + prize);
            } catch(Exception e) {
                System.out.println("Ошибка!");
            }
        } else {
            System.out.println("Призы закончились");
        }
        System.out.println();
    }

    /*Основная функция лотереи*/
    public static void lottery (Set<ToyLottery> toys) {

        System.out.print("Введите количество розыгрышей: ");
        Scanner input = new Scanner(System.in);
        int num = input.nextInt();

        List<String> prizes = ToyLottery.haveLottery(num, toys);
        System.out.println("Лотерея завершена");
        System.out.println("Выпавшие игрушки: ");
        for (String prize : prizes) {
            System.out.print(prize + " ");
        }
        System.out.println();
        System.out.println();

        while (true) {
            System.out.println("Чтобы выдать игрушку, нажмите 1" + "\n"+
                                "Для вызода нажмите 0");
            System.out.print("Введите команду: ");
            int command = input.nextInt();
            if (command == 0) {
                break;
            } else if (command == 1) {
                ToyLottery.getPrize(prizes);
            }
        }
    }

}