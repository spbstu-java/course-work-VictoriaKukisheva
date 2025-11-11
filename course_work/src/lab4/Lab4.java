package lab4;

import java.util.*;
import java.util.stream.Collectors;

public class Lab4 {

    // Fixed method name - was avgNumbersCalculate, should be calculateAverage
    public static double calculateAverage(List<Integer> numbers) {
        return numbers.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    public static List<String> transformStrings(List<String> strings){
        return strings.stream().map(word -> "_new_" + word.toUpperCase()).collect(Collectors.toList());
    }

    public static List<Integer> powNumbersCalculate(List<Integer> numbers) {
        return numbers.stream().filter(i -> Collections.frequency(numbers, i)==1).
                map(n -> n*n).collect(Collectors.toList());
    }

    private static <V> V getLastElement(Collection<V> testList) {
        return testList.stream().skip(testList.size() -1).findFirst().
                orElseThrow( () -> new NoSuchElementException("Its nothing inside, brother"));
    }

    public static int sumEvenCalculate(int[] numbers) {
        return Arrays.stream(numbers).filter(i -> i % 2 == 0).sum();
    }

    public static Map<Character, String> stingToMap(List<String> strings) {
        return strings.stream().filter(s -> !s.isEmpty()).collect(Collectors.toMap(s -> s.charAt(0),
                s -> s.substring(1), (first, second) -> first));
    }

    public static void main(String[] args) {
        List<Integer> avgNumbers = Arrays.asList(10, 40, 3, 96, 1);
        // Fixed: changed avgNumbersCalculate to calculateAverage
        double resultAvg = calculateAverage(avgNumbers);
        System.out.println("Среднее: " + resultAvg);

        List<String> newWords = Arrays.asList("cat", "dog");
        List<String> transformed = transformStrings(newWords);
        System.out.println("Новые словечки: " + transformed);

        List<Integer> powNumbers = Arrays.asList(10, 40, 3, 96, 96, 1);
        List<Integer> resultPow = powNumbersCalculate(powNumbers);
        System.out.println("Квадрат чисел: " + resultPow);

        List<Integer> testList = Arrays.asList(10, 40, 3, 96, 1);
        Integer lastElement = getLastElement(testList);
        System.out.println("Последний элемент: " + lastElement);

        try {
            List<Integer> emptyList = Collections.emptyList();
            Integer emptyResult = getLastElement(emptyList);
            System.out.println("Последний элемент: " + emptyResult);
        } catch (NoSuchElementException e) {
            System.out.println("Ошибка для пустого списка: " + e.getMessage());
        }

        int[] allNumbersList = {10, 40, 3, 96, 1};
        int evenSumElement = sumEvenCalculate(allNumbersList);
        System.out.println("Сумма четных: " + evenSumElement);

        List<String> toMapWords = Arrays.asList("cat", "dog");
        Map<Character, String> resultMap = stingToMap(toMapWords);
        System.out.println("Что вышло: " + resultMap);
    }
}