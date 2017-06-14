package github.com.mgrzeszczak.lexical;

public interface Tokenizer {

    Token peek();

    Token next();

    static Tokenizer of(String input) {
        return new BasicTokenizer(input);
    }

}
