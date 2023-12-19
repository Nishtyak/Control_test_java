import java.util.Random;
import java.util.Set;

/**
 * ToyLottery
 */
public class ToyLottery {
    private int id;
    private String name;
    private int count;
    private double chance;

    private ToyLottery ToyLottery (int id, String name, int count, double chance) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.chance = chance;
        return this;
    }

    public ToyLottery (int id, String name, int count, double chance) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.chance = chance;
    }

    public ToyLottery (String name, int count, Set<ToyLottery> toys) {
        this.id = toys.size() + 1;
        this.name = name;
        this.count = count;
        this.chance = calculateChance(toys, count);
        toys.add(ToyLottery(this.id, this.name, this.count, this.chance));
    }

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
}