package lab4;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import lab1.*;
import lab2.Lab2;
import lab3.Translator;
import lab3.FileReadException;
import lab3.InvalidFileFormatException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class SceneController implements Initializable {

    @FXML private ComboBox<String> labComboBox;
    @FXML private VBox lab1Content, lab2Content, lab3Content, lab4Content;
    @FXML private TextArea lab1Output, lab2Output, translationOutput, inputText; // Изменено на TextArea
    @FXML private TextField dictionaryPath; // Оставил как TextField
    @FXML private TextArea avgOutput, stringsOutput, uniqueOutput, lastElementOutput, evenOutput, mapOutput;
    @FXML private TextField avgNumbersInput, stringsInput, uniqueNumbersInput, lastElementInput, evenNumbersInput, mapStringsInput;

    private Hero hero;
    private StringBuilder lab1Log;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize lab selection
        labComboBox.getItems().addAll(
                "Лабораторная работа 1: Стратегия",
                "Лабораторная работа 2: Аннотации",
                "Лабораторная работа 3: Переводчик",
                "Лабораторная работа 4: Stream API"
        );

        labComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showLabContent(newValue)
        );

        // Initialize lab1
        hero = new Hero();
        lab1Log = new StringBuilder();
        lab1Log.append("The little hero wake up and his day starts...\nHe get out from home.\n\n");
        updateLab1Output();

        // Select first lab by default
        labComboBox.getSelectionModel().selectFirst();
    }

    private void showLabContent(String labName) {
        // Hide all first
        lab1Content.setVisible(false);
        lab2Content.setVisible(false);
        lab3Content.setVisible(false);
        lab4Content.setVisible(false);

        if (labName != null) {
            switch (labName) {
                case "Лабораторная работа 1: Стратегия":
                    lab1Content.setVisible(true);
                    break;
                case "Лабораторная работа 2: Аннотации":
                    lab2Content.setVisible(true);
                    break;
                case "Лабораторная работа 3: Переводчик":
                    lab3Content.setVisible(true);
                    break;
                case "Лабораторная работа 4: Stream API":
                    lab4Content.setVisible(true);
                    break;
            }
        }
    }

    // Lab 1 Methods
    @FXML
    private void onWalkButtonClick() {
        hero.setMoveStrategy(new WalkStrategy());
        lab1Log.append("Hero chose to walk\n");
        lab1Log.append("Hero walks in the forest...\n\n");
        updateLab1Output();
    }

    @FXML
    private void onRideButtonClick() {
        hero.setMoveStrategy(new RideStrategy());
        lab1Log.append("Hero chose to ride\n");
        lab1Log.append("Hero ride a black hourse in the field ...\n\n");
        updateLab1Output();
    }

    @FXML
    private void onFlyButtonClick() {
        hero.setMoveStrategy(new FlyStrategy());
        lab1Log.append("Hero chose to fly\n");
        lab1Log.append("I dont know how...But he flying in the sky...\n\n");
        updateLab1Output();
    }

    @FXML
    private void onEndDayButtonClick() {
        lab1Log.append("Day ends. Lets sleep\n");
        updateLab1Output();
    }

    private void updateLab1Output() {
        lab1Output.setText(lab1Log.toString());
        // Auto-scroll to bottom
        lab1Output.positionCaret(lab1Output.getText().length());
    }

    // Lab 2 Methods
    @FXML
    private void onLab2Execute() {
        // Redirect console output to capture Lab2 output
        ConsoleCapturer capturer = new ConsoleCapturer();
        capturer.startCapture();

        try {
            // Since Lab2 has main method, we'll call it directly
            Lab2.main(new String[]{});
        } catch (Exception e) {
            lab2Output.setText("Error: " + e.getMessage());
            return;
        }

        String output = capturer.stopCapture();
        lab2Output.setText(output);
    }

    // Lab 3 Methods
    @FXML
    private void onBrowseDictionary() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл словаря");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            dictionaryPath.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void onTranslateButtonClick() {
        String text = inputText.getText();
        if (text.isEmpty()) {
            translationOutput.setText("Пожалуйста, введите текст для перевода");
            return;
        }

        try {
            // Use the modified Translator with file path parameter
            var dictionary = Translator.checkFile(dictionaryPath.getText());
            String translation = Translator.translateText(dictionary, text);
            translationOutput.setText(translation);
        } catch (FileReadException e) {
            translationOutput.setText("Ошибка чтения файла: " + e.getMessage());
        } catch (InvalidFileFormatException e) {
            translationOutput.setText("Неверный формат файла: " + e.getMessage());
        } catch (Exception e) {
            translationOutput.setText("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Lab 4 Methods
    @FXML
    private void onCalculateAverage() {
        try {
            List<Integer> numbers = parseNumbers(avgNumbersInput.getText());
            double result = calculateAverage(numbers);
            avgOutput.setText(String.format("Среднее значение: %.2f\nЧисла: %s", result, numbers));
        } catch (Exception e) {
            avgOutput.setText("Ошибка: " + e.getMessage());
        }
    }

    @FXML
    private void onTransformStrings() {
        try {
            List<String> strings = parseStrings(stringsInput.getText());
            List<String> result = transformStrings(strings);
            stringsOutput.setText(String.format("Результат: %s\nИсходные строки: %s", result, strings));
        } catch (Exception e) {
            stringsOutput.setText("Ошибка: " + e.getMessage());
        }
    }

    @FXML
    private void onPowNumbers() {
        try {
            List<Integer> numbers = parseNumbers(uniqueNumbersInput.getText());
            List<Integer> result = powNumbersCalculate(numbers);
            uniqueOutput.setText(String.format("Квадраты уникальных чисел: %s\nИсходные числа: %s", result, numbers));
        } catch (Exception e) {
            uniqueOutput.setText("Ошибка: " + e.getMessage());
        }
    }

    @FXML
    private void onGetLastElement() {
        try {
            List<String> elements = parseStrings(lastElementInput.getText());
            String result = getLastElement(elements);
            lastElementOutput.setText(String.format("Последний элемент: %s\nВсе элементы: %s", result, elements));
        } catch (Exception e) {
            lastElementOutput.setText("Ошибка: " + e.getMessage());
        }
    }

    @FXML
    private void onSumEven() {
        try {
            List<Integer> numbers = parseNumbers(evenNumbersInput.getText());
            int[] array = numbers.stream().mapToInt(i -> i).toArray();
            int result = sumEvenCalculate(array);
            evenOutput.setText(String.format("Сумма четных чисел: %d\nВсе числа: %s", result, numbers));
        } catch (Exception e) {
            evenOutput.setText("Ошибка: " + e.getMessage());
        }
    }

    @FXML
    private void onStringsToMap() {
        try {
            List<String> strings = parseStrings(mapStringsInput.getText());
            Map<Character, String> result = stingToMap(strings);
            mapOutput.setText(String.format("Результат: %s\nИсходные строки: %s", result, strings));
        } catch (Exception e) {
            mapOutput.setText("Ошибка: " + e.getMessage());
        }
    }

    // Utility methods for parsing
    private List<Integer> parseNumbers(String input) {
        return Arrays.stream(input.split("[,\\s]+"))  // Разделяем и по запятым, и по пробелам
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private List<String> parseStrings(String input) {
        return Arrays.stream(input.split(","))  // Разделяем только по запятым для строк
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    // Lab 4 methods (copied from Lab4.java with corrections)
    public static double calculateAverage(List<Integer> numbers) {
        return numbers.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    public static List<String> transformStrings(List<String> strings) {
        return strings.stream().map(word -> "_new_" + word.toUpperCase()).collect(Collectors.toList());
    }

    public static List<Integer> powNumbersCalculate(List<Integer> numbers) {
        return numbers.stream()
                .filter(i -> Collections.frequency(numbers, i) == 1)
                .map(n -> n * n)
                .collect(Collectors.toList());
    }

    private static <V> V getLastElement(Collection<V> testList) {
        return testList.stream()
                .reduce((first, second) -> second)
                .orElseThrow(() -> new NoSuchElementException("Коллекция пуста"));
    }

    public static int sumEvenCalculate(int[] numbers) {
        return Arrays.stream(numbers).filter(i -> i % 2 == 0).sum();
    }

    public static Map<Character, String> stingToMap(List<String> strings) {
        return strings.stream()
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toMap(
                        s -> s.charAt(0),
                        s -> s.length() > 1 ? s.substring(1) : "",
                        (first, second) -> first
                ));
    }
}

// Helper class to capture console output
class ConsoleCapturer {
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    public void startCapture() {
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    public String stopCapture() {
        System.setOut(originalOut);
        return outputStream.toString();
    }
}