package Network.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private final DateTimeFormatter dateTimeFormatter;

    public Logger(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    }

    public void log(String content) {
        System.out.printf("%9s | %s\n", this.dateTimeFormatter.format(LocalDateTime.now()), content);
    }

}
