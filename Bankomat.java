import java.util.*;

public class Bankomat {

    private static Bankomat INSTANCE;
    public static final List<Integer> BANKNOTES = new ArrayList<>(Arrays.asList(5000, 1000, 500, 100, 50));
    private static final String MESSAGE_GET_SUM_CASH = "Введите пожалуйста сумму которую Вы хотите снять со счета: ";
    private static final String MESSAGE_WRONG_SUM_CASH = "Данная сумма не может быть выдана, введите сумму кратную 50.";
    private static final String MESSAGE_WRONG_SUM_SUBZERO = "Данная сумма меньше 0, введите корректную сумму.";
    private static final String MESSAGE_WRONG_SUM_OVER = "Вы не располагаете такими средствами, введите другую сумму.";
    private int cash = getCash();
    private final TreeMap<Integer, Integer> setBanknotes = setOfBanknotes(cash);

    private Bankomat() {
    }

    public static Bankomat getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Bankomat();
        }
        return INSTANCE;
    }

    private int getCash() {
        int summ = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println(MESSAGE_GET_SUM_CASH);
        try {
            while (true) {
                summ = scanner.nextInt();
                if (summ <= 0) {
                    System.out.println(MESSAGE_WRONG_SUM_SUBZERO);
                } else if (summ > 1_000_000) {
                    System.out.println(MESSAGE_WRONG_SUM_OVER);
                } else if (!checkDivisibility(summ)) {
                    System.out.println(MESSAGE_WRONG_SUM_CASH);
                } else break;
            }
        } catch (Exception e) {
            throw new RuntimeException("Ввод не числового значения!");
        }
        return summ;
    }

    private boolean checkDivisibility(int summ) {
        return summ % 50 == 0;
    }

    private TreeMap<Integer, Integer> setOfBanknotes(int cash) {
        TreeMap<Integer, Integer> setBanknotes = new TreeMap<>();
        int balance = cash;
        while (balance > 0) {
            for (Integer banknote : BANKNOTES) {
                setBanknotes.put(banknote, balance / banknote);
                balance = cash % banknote;
            }
        }
        return setBanknotes;
    }

    public void printResult() {
        System.out.println("Банкомат выдаст требуемую сумму следующими купюрами: ");
        NavigableMap<Integer, Integer> naviMap = setBanknotes.descendingMap();
        for (Integer key : naviMap.keySet()) {
            System.out.printf("%dруб. - %dшт.\n", key, naviMap.get(key));
        }
    }
}
