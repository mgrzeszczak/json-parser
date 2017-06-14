package github.com.mgrzeszczak.lexical;

import static github.com.mgrzeszczak.lexical.TokenType.STRING;

class StringMatcher implements TokenMatcher {

    static StringMatcher instance() {
        return new StringMatcher();
    }

    @Override
    public Token match(String input) {
        return findMatch(input);
    }

    private Token findMatch(String input) {
        if (input.charAt(0) != '"') {
            return null;
        }
        int offset = 1;
        int index;
        while ((index = input.indexOf("\"", offset)) != -1) {
            if (index == 0 || input.charAt(index - 1) != '\\') {
                return Token.of(STRING, input.substring(0, index + 1));
            }
            offset = index + 1;
        }
        return null;
    }
}
