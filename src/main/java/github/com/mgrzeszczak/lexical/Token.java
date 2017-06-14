package github.com.mgrzeszczak.lexical;

public class Token {

    public final static Token EOF = Token.of(TokenType.EOF, "");

    private TokenType type;
    private String content;

    private Token(TokenType type, String content) {
        this.type = type;
        this.content = content;
    }

    static Token of(TokenType type, String content) {
        return new Token(type, content);
    }

    public TokenType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (type != token.type) return false;
        return content != null ? content.equals(token.content) : token.content == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
