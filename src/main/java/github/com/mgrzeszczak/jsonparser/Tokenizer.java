package github.com.mgrzeszczak.jsonparser;

interface Tokenizer {

    Token peek();

    Token next();

    static Tokenizer of(String input) {
        return new BasicTokenizer(input);
    }

}
