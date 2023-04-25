package src.config.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Utils {
    private static final int LENGTH = 10;
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Set<String> generatedStrings = new HashSet<>();

    public static String generateCodeOrder() {
        Random random = new Random();
        String generatedString;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char c = CHARACTERS.charAt(randomIndex);
            sb.append(c);
        }
        generatedString = sb.toString();
        return generatedString;
    }
}
