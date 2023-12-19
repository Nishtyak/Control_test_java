import java.util.HashSet;
import java.util.Set;

public class program {

    public static void main(String[] args) {

        Set<ToyLottery> toys = new HashSet<>();
        ToyLottery toy1 = new ToyLottery("мишка", 20, toys);
        ToyLottery toy2 = new ToyLottery("зайчик", 10, toys);
        ToyLottery toy3 = new ToyLottery("котик", 10, toys);
        ToyLottery toy4 = new ToyLottery("пингвин", 15, toys);

        /*Вызов функции лотереи*/
        ToyLottery.lottery(toys);
    }
}
