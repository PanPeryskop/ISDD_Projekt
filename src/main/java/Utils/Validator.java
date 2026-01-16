package Utils;

import java.time.LocalDate;
import java.time.Period;

public class Validator {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PHONE_REGEX = "^\\d{9,15}$";
    private static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";
    private static final String NAME_REGEX = "^[a-zA-Záéíóúàèìòùäëïöüñśćęąńóżź\\s\\-]{2,100}$";
    private static final String ID_REGEX = "^\\d{8}[A-Z]$";
    private static final String ACTIVITY_NAME_REGEX = "^[a-zA-Z0-9áéíóúàèìòùäëïöüñśćęąńóżź\\s\\-]{3,100}$";
    private static final String DAY_REGEX = "^(Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday)$";
    private static final String NUMERIC_REGEX = "^[0-9]+$";

    public static boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && !phone.isBlank() && phone.matches(PHONE_REGEX);
    }

    public static boolean isValidDate(String date) {
        if (date == null || !date.matches(DATE_REGEX)) {
            return false;
        }
        try {
            LocalDate birthDate = LocalDate.parse(date);
            int age = Period.between(birthDate, LocalDate.now()).getYears();
            return age >= 18;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidName(String name) {
        return name != null && name.matches(NAME_REGEX);
    }

    public static boolean isValidId(String id) {
        return id != null && id.matches(ID_REGEX);
    }

    public static boolean isValidActivityName(String name) {
        return name != null && name.matches(ACTIVITY_NAME_REGEX);
    }

    public static boolean isValidDay(String day) {
        return day != null && day.matches(DAY_REGEX);
    }

    public static boolean isValidNumeric(String value) {
        return value != null && !value.isBlank() && value.matches(NUMERIC_REGEX);
    }

    public static boolean isValidCategory(String category) {
        return category != null && category.matches("^[A-E]$");
    }

    public static boolean isOptionalEmailValid(String email) {
        return email.isBlank() || isValidEmail(email);
    }

    public static boolean isOptionalPhoneValid(String phone) {
        return phone.isBlank() || isValidPhone(phone);
    }
}