package com.itmo.programming.commands.commandutils;

import java.util.regex.Pattern;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class ValidationInputData {
    private static final String EMAIL_PATTERN =
            "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@"
                    + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";
    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private static final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
    public static final String passwordRule = "Требования к паролю\n" +
            "Пароль должен содержать хотя бы одну цифру\n" +
            "Пароль должен содержать по крайней мере один строчный латинский символ\n" +
            "Пароль должен содержать по крайней мере один заглавный латинский символ\n" +
            "Пароль должен содержать хотя бы один специальный символ, например ! @ # & ( )\n" +
            "Пароль должен содержать не менее 8 символов и не более 20 символов.";
    public static final String emailRule = "Можно использовать:\n" +
            "прописные и строчные латинские буквы от A до Z и от a до z\n" +
            "цифры от 0 до 9\n" +
            "Разрешено использовать (.), подчеркивание (_) и дефис (-)\n" +
            "точка (.) не является первым или последним символом\n" +
            "точка (.) не появляется последовательно, например. mkyong..yong@example.com не разрешен\n" +
            "Максимум 64 символа";

    public static boolean validateLogin(String login) {
        return emailPattern.matcher(login).matches();
    }

    public static boolean validatePassword(String password) {
        return passwordPattern.matcher(password).matches();
    }
}
