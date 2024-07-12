package models;

import java.util.ArrayList;
import java.util.List;

public class DataValidator {
    public boolean validateCode(String code) {
        return code.matches("^[A-Z]{3}$");
    }
    public boolean validateAmount(double amount) {
        final double EPS = 1e-9;
        return amount > 0 || Math.abs(amount) < EPS; // amount >= 0
    }
    public boolean invalidResponse(String response) {
        return response.contains("error");
    }
    public List<String> getInputIssues(String base_code, String target_code, double amount) {
        List<String> invalid = new ArrayList<>();
        if (!validateCode(base_code)) {
            invalid.add("Codigo de moneda base");
        }
        if (!validateCode(target_code)) {
            invalid.add("Codigo de moneda objetivo");
        }
        if (!validateAmount(amount)) {
            invalid.add("Monto");
        }
        return invalid;
    }
}
