package github.com.mgrzeszczak.structural;

public class JsonParseException extends RuntimeException {

    public JsonParseException(String s) {
        super(s);
    }

    public JsonParseException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
