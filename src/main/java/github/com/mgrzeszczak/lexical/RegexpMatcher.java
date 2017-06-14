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
    public TokenMatch match(String input) {
        return matchStream(pattern.matcher(input), input)
                .filter(TokenMatch::isFromStart)
                .sorted(Comparator.comparingInt(TokenMatch::getLength).reversed())
                .findFirst()
                .orElse(null);
    }

    private Stream<TokenMatch> matchStream(Matcher matcher, String input) {
        List<TokenMatch> matches = new ArrayList<>();
        while (matcher.find()) {
            matches.add(new TokenMatch(input.substring(matcher.start(), matcher.end()), matcher.start(), matcher.end(), type));
        }
        return matches.stream();
    }
}
