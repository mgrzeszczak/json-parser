package github.com.mgrzeszczak.lexical;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
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
    private final String input;
    private Stack<Token> stack;

    private Tokenizer(String input) {
        this.input = input;
        this.stack = new Stack<>();
        this.tokenize();
    }

    public static Tokenizer of(String input) {
        return new Tokenizer(input);
    }

    public Token peek() {
        return stack.size() > 0 ? stack.peek() : Token.EOF;
    }

    public Token next() {
        return stack.size() > 0 ? stack.pop() : Token.EOF;
    }

    private void tokenize() {
        String content = input;
        List<Token> tokens = new ArrayList<>();
        while (!content.isEmpty()) {
            final String text = content;
            TokenMatch tokenMatch = MATCHERS.stream()
                    .map(t -> t.match(text))
                    .filter(Objects::nonNull)
                    .sorted(Comparator.comparingInt(TokenMatch::getLength).reversed())
                    .findFirst()
                    .orElseThrow(() -> reject(text));


            if (tokenMatch.getToken().getType() == LITERAL) {
                throw reject(content);
            }
            content = content.substring(tokenMatch.getLength());
            if (tokenMatch.getToken().getType() == BLANK) continue;
            tokens.add(tokenMatch.getToken());
        }
        Collections.reverse(tokens);
        tokens.forEach(stack::push);
    }

    private static IllegalArgumentException reject(String input) {
        return new IllegalArgumentException("cannot find tokens for " + input);
    }


}
