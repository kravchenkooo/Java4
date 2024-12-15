package org.example;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis(); // Початок вимірювання часу

        // Генерація послідовності
        CompletableFuture<List<Double>> sequenceFuture = CompletableFuture.supplyAsync(() -> {
            List<Double> sequence = generateRandomSequence(20);
            System.out.println("Generated sequence: " + sequence);
            return sequence;
        });

        // Обчислення суми
        CompletableFuture<Double> resultFuture = sequenceFuture.thenApplyAsync(sequence -> {
            double result = calculateSum(sequence);
            System.out.println("Calculated sum: " + result);
            return result;
        });

        // Завершення операцій
        resultFuture.thenRun(() -> {
            long endTime = System.currentTimeMillis();
            System.out.println("Total execution time: " + (endTime - startTime) + "ms");
        });

        // Очікування завершення усіх операцій
        resultFuture.join();
    }

    // Генерація випадкової послідовності
    private static List<Double> generateRandomSequence(int size) {
        Random random = new Random();
        return IntStream.range(0, size)
                .mapToDouble(i -> random.nextDouble() * 100) // Генеруємо числа в діапазоні [0, 100)
                .boxed()
                .collect(Collectors.toList());
    }

    // Обчислення суми за формулою
    private static double calculateSum(List<Double> sequence) {
        double sum = 0.0;
        for (int i = 0; i < sequence.size() - 1; i++) {
            sum += sequence.get(i) * sequence.get(i + 1);
        }
        return sum;
    }
}
