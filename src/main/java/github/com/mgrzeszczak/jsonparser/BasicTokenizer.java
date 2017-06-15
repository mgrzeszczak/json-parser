package github.com.mgrzeszczak.jsonparser;

import github.com.mgrzeszczak.jsonparser.exception.LexicalException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class BasicTokenizer implements Tokenizer {

    private final static List<TokenMatcher> MATCHERS = Stream.of(
            StringMatcher.instance(),
            RegexpMatcher.of("null", TokenType.NULL),
            RegexpMatcher.of("\\[", TokenType.OPEN_BRACKET),
            RegexpMatcher.of("\\]", TokenType.CLOSE_BRACKET),
            RegexpMatcher.of("\\{", TokenType.OPEN_BRACE),
            RegexpMatcher.of("\\}", TokenType.CLOSE_BRACE),
            RegexpMatcher.of("([0-9]+\\.[0-9]+)|([0-9]+)", TokenType.NUMBER),
            RegexpMatcher.of("(true)|(false)", TokenType.BOOLEAN),
            RegexpMatcher.of("\\s+", TokenType.BLANK),
            RegexpMatcher.of("\\w+", TokenType.LITERAL),
            RegexpMatcher.of(",", TokenType.COMMA),
            RegexpMatcher.of(":", TokenType.COLON)
    ).collect(Collectors.toList());

    private final static List<TokenType> IGNORED = Collections.singletonList(TokenType.BLANK);
    private final static List<TokenType> REJECTED = Collections.singletonList(TokenType.LITERAL);

    private String input;
    private Token token;

    BasicTokenizer(String input) {
        this.input = input;
        this.token = readToken();
    }

    public Token peek() {
        return token;
    }

    public Token next() {
        Token value = token;
        token = readToken();
        return value;
    }

    private Token readToken() {
        token = nextToken();
        input = input.substring(token.getLength());
        if (IGNORED.contains(token.getType())) {
            return readToken();
        } else if (REJECTED.contains(token.getType())) {
            throw new LexicalException(token.getContent());
        } else {
            return token;
        }
    }

    private Token nextToken() {
        if (input.isEmpty()) {
            return Token.EOF;
        } else {
            return MATCHERS.stream()
                    .map(t -> t.match(input))
                    .filter(Objects::nonNull)
                    .max(Comparator.comparingInt(Token::getLength))
                    .orElseThrow(() -> new LexicalException(input.substring(0, Math.min(input.length(), 10))));
        }
    }

}
