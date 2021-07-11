package Network.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static DateTimeFormatter dateTimeFormatter = null;

    public static void log(String content) {
        System.out.printf("%9s | %s\n", dateTimeFormatter.format(LocalDateTime.now()), content);
    }

    public static void set() {
        Logger.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    }
}
