package github.com.mgrzeszczak.jsonparser;

class Token {

    final static Token EOF = Token.of(TokenType.EOF, "");

    private final TokenType type;
    private final String content;

    private Token(TokenType type, String content) {
        this.type = type;
        this.content = content;
    }

    static Token of(TokenType type, String content) {
        return new Token(type, content);
    }

    TokenType getType() {
        return type;
    }

    String getContent() {
        return content;
    }

    int getLength() {
        return content.length();
    }
}
