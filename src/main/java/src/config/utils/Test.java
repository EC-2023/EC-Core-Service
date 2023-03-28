package src.config.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Test {
    public static void main(String[] args) {

        String query = "?duration{{search}}=9&price{{lt}}=1500&sort=-price&fields=price,description&page=1";

        Pattern pattern = Pattern.compile("(?i)(\\w+)\\{\\{(lt|lte|gt|gte|search)\\}\\}=(.*?)(&|$)");

        Map<String, String> params = new HashMap<>();
        Matcher matcher = pattern.matcher(query);
        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(3);
            params.put(key, value);
        }

        for (Map.Entry<String, String> entry : params.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }
}
