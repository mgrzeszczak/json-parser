package github.com.mgrzeszczak.lexical;

class TokenMatch {

    private final String content;
    private final int from, to;
    private final TokenType type;

    TokenMatch(String content, int from, int to, TokenType type) {
        this.content = content;
        this.from = from;
        this.to = to;
        this.type = type;
    }

    Token getToken() {
        return Token.of(type, content);
    }

    int getLength() {
        return to - from;
    }

    boolean isFromStart() {
        return from == 0;
    }

}
