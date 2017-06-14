package github.com.mgrzeszczak.lexical;

class StringMatcher implements TokenMatcher {

    static StringMatcher instance() {
        return new StringMatcher();
    }

    @Override
    public TokenMatch match(String input) {
        try {
            return findMatch(input);
        } catch (StringMatchException e) {
            return null;
        }
    }

    private TokenMatch findMatch(String input) throws StringMatchException {
        int pos = 0;
        assertChar(input.charAt(pos), '"');
        while (true) {
            pos = move(input, ++pos);
            if (input.charAt(pos - 1) != '\\') {
                return new TokenMatch(input.substring(0, pos + 1), 0, pos + 1, TokenType.STRING);
            }
        }
    }

    private int move(String input, int pos) {
        while (input.charAt(pos) != '"') {
            pos++;
        }
        return pos;
    }

    private void assertChar(char actual, char expected) throws StringMatchException {
        if (actual != expected) throw new StringMatchException();
    }

    private class StringMatchException extends Exception {
    }

}
