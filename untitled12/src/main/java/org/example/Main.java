package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    // Синхронізований список для збереження кінцевого результату
    private static final List<String> finalResult = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws InterruptedException {
        // Список файлів для обробки
        List<String> fileNames = List.of("src/main/resources/file1.txt", "src/main/resources/file2.txt", "src/main/resources/file3.txt");

        // Список потоків
        List<Thread> threads = new ArrayList<>();

        // Створення потоків для обробки файлів
        for (String fileName : fileNames) {
            Thread thread = new Thread(() -> processFile(fileName));
            threads.add(thread);
            thread.start();
        }

        // Очікування завершення всіх потоків
        for (Thread thread : threads) {
            thread.join();
        }

        // Виведення результуючого масиву
        System.out.println("Final result: " + finalResult);
    }

    // Метод для обробки файлу
    private static void processFile(String fileName) {
        long startTime = System.currentTimeMillis();
        try {
            // Читання файлу
            Path path = Paths.get(fileName);
            List<String> lines = Files.readAllLines(path);

            // Виведення початкових рядків
            System.out.println("Original sentences from " + fileName + ": " + lines);

            // Обробка: видалення літер
            List<String> processedLines = lines.stream()
                    .map(line -> line.replaceAll("[a-zA-Z]", ""))
                    .toList();

            // Додавання результату до загального списку
            finalResult.addAll(processedLines);

        } catch (IOException e) {
            System.err.println("Error reading file: " + fileName + " - " + e.getMessage());
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Processed file: " + fileName + " in " + (endTime - startTime) + "ms");
    }
}
