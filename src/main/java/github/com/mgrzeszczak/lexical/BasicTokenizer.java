package github.com.mgrzeszczak.lexical;

import github.com.mgrzeszczak.exception.LexicalException;

import java.util.Collections;
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

class BasicTokenizer implements Tokenizer {

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

    private final static List<TokenType> IGNORED = Collections.singletonList(BLANK);
    private final static List<TokenType> REJECTED = Collections.singletonList(LITERAL);

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
