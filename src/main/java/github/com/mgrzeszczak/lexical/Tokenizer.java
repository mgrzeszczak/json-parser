package github.com.mgrzeszczak.lexical;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static github.com.mgrzeszczak.lexical.TokenType.BLANK;
import static github.com.mgrzeszczak.lexical.TokenType.BOOLEAN;
import static github.com.mgrzeszczak.lexical.TokenType.CLOSE_BRACE;
import static github.com.mgrzeszczak.lexical.TokenType.CLOSE_BRACKET;
import static github.com.mgrzeszczak.lexical.TokenType.COLON;
import static github.com.mgrzeszczak.lexical.TokenType.COMMA;
import static github.com.mgrzeszczak.lexical.TokenType.LITERAL;
import static github.com.mgrzeszczak.lexical.TokenType.NULL;
import static github.com.mgrzeszczak.lexical.TokenType.NUMBER;
import static github.com.mgrzeszczak.lexical.TokenType.OPEN_BRACE;
import static github.com.mgrzeszczak.lexical.TokenType.OPEN_BRACKET;

public class Tokenizer {

    private final static List<TokenMatcher> MATCHERS = Stream.of(
            StringMatcher.instance(),
            RegexpMatcher.of("null", NULL),
            RegexpMatcher.of("\\[", OPEN_BRACKET),
            RegexpMatcher.of("\\]", CLOSE_BRACKET),
            RegexpMatcher.of("\\{", OPEN_BRACE),
            RegexpMatcher.of("\\}", CLOSE_BRACE),
            RegexpMatcher.of("([0-9]+\\.[0-9]+)|([0-9]+)", NUMBER),
            RegexpMatcher.of("(true)|(false)", BOOLEAN),
            RegexpMatcher.of("\\s+", BLANK),
            RegexpMatcher.of("\\w+", LITERAL),
            RegexpMatcher.of(",", COMMA),
            RegexpMatcher.of(":", COLON)
    ).collect(Collectors.toList());

    private String input;
    private Token token;

    private Tokenizer(String input) {
        this.input = input;
        this.token = readToken();
    }

    public static Tokenizer of(String input) {
        return new Tokenizer(input);
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
        if (token.getType() == BLANK) {
            return readToken();
        } else if (token.getType() == LITERAL) {
            throw reject();
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
                    .orElseThrow(this::reject);
        }
    }

    private IllegalArgumentException reject() {
        return new IllegalArgumentException("failed to find token: " + input.substring(0, Math.min(20, input.length())));
    }

}
