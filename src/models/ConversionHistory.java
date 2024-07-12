package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ConversionHistory {
    private List<String> history = new ArrayList<>();

    public void saveConversion(String base_code, String target_code, double amount, double conversion_result) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        String date = now.format(formatter);
        String conversion = date + " - " + base_code + " a " + target_code + " - " + amount + " = " + conversion_result;
        history.add(conversion);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String conversion : history) {
            sb.append(conversion).append("\n");
        }
        return sb.toString();
    }
}
