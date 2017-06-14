package github.com.mgrzeszczak.lexical;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

class RegexpMatcher implements TokenMatcher {

    private final Pattern pattern;
    private final TokenType type;

    private RegexpMatcher(String regexp, TokenType type) {
        this.type = type;
        this.pattern = Pattern.compile(regexp);
    }

    static RegexpMatcher of(String regexp, TokenType type) {
        return new RegexpMatcher(regexp, type);
    }

    @Override
    public Token match(String input) {
        return matchStream(pattern.matcher(input), input)
                .max(Comparator.comparingInt(Token::getLength))
                .orElse(null);
    }

    private Stream<Token> matchStream(Matcher matcher, String input) {
        List<Token> matches = new ArrayList<>();
        while (matcher.find() && matcher.start() == 0) {
            int length = matcher.end() - matcher.start();
            matches.add(Token.of(type, input.substring(0, length)));
        }
        return matches.stream();
    }
}
