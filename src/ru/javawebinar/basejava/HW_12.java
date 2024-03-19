package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HW_12 {
    public static void main(String[] args) {
        int[] firstArray = {1, 2, 3, 3, 2, 3};
        int[] secondArray = {9, 8};
        System.out.println(minValue(firstArray));
        System.out.println(minValue(secondArray));

        System.out.println();
        System.out.println(oddOrEven(Arrays.stream(firstArray).boxed().collect(Collectors.toList())));

    }

    private static int minValue(int[] values) {
        final int[] counter = new int[1];
        counter[0] = 1;
        return Arrays.stream(values)
                .distinct()
                .boxed()
                .sorted(Collections.reverseOrder())
                .map(x -> x * counter[0])
                .peek(x -> counter[0] = counter[0] * 10)
                .reduce(Integer::sum).orElse(0);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        return integers.stream()
                .collect((Collectors.collectingAndThen(Collectors
                                .partitioningBy(t -> t % 2 == 0),
                        booleanListMap -> booleanListMap.get(false).size() % 2 == 0
                                ? booleanListMap.get(false)
                                : booleanListMap.get(true))));
    }
}
