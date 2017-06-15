package github.com.mgrzeszczak.jsonparser.exception;

public class LexicalException extends RuntimeException {

    public LexicalException(String s) {
        super("invalid input: " + s);
    }

}
