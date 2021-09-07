package engine.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    static {
        new Logger();
    }

    public enum Level {
        INFO(0), WARN(1), ERROR(2);

        public int value;
        Level(int _val) {
            value = _val;
        }
    }

    public enum Source {
        DUCK, SHADERS, RENDERING, MODELS
    }

    public static Logger INSTANCE;
    public Level minimumLogLevel = Level.INFO;

    private static final String PREFIX = "";
    private static DateTimeFormatter dtf;

    public Logger() {
        INSTANCE = this;
        dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    }

    public void info(Source src, Object message) {
        if(belowMinimumLevel(Level.INFO)) return;
        System.out.println(constructPrefix(src, Level.INFO) + message);
    }

    public void warn(Source src, Object message) {
        if(belowMinimumLevel(Level.WARN)) return;
        System.out.println(constructPrefix(src, Level.WARN) + message);
    }

    public void error(Source src, Object message) {
        if(belowMinimumLevel(Level.ERROR)) return;
        System.out.println(constructPrefix(src, Level.ERROR) + message);
    }

    public void info(String src, Object message) {
        if(belowMinimumLevel(Level.INFO)) return;
        System.out.println(constructPrefix(src, Level.INFO) + message);
    }

    public void warn(String src, Object message) {
        if(belowMinimumLevel(Level.WARN)) return;
        System.out.println(constructPrefix(src, Level.WARN) + message);
    }

    public void error(String src, Object message) {
        if(belowMinimumLevel(Level.ERROR)) return;
        System.out.println(constructPrefix(src, Level.ERROR) + message);
    }

    private boolean belowMinimumLevel(Level level) {
        return level.value - minimumLogLevel.value < 0;
    }

    private String constructPrefix(Source src, Level level) {
        return dtf.format(LocalDateTime.now()) + " [" + level.toString() + "] (" + src.toString() + ") ";
    }

    private String constructPrefix(String src, Level level) {
        return dtf.format(LocalDateTime.now()) + " [" + level.toString() + "] (" + src + ") ";
    }
}
