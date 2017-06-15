package github.com.mgrzeszczak.jsonparser;

enum TokenType {

    STRING,
    NULL,
    OPEN_BRACKET,
    CLOSE_BRACKET,
    OPEN_BRACE,
    CLOSE_BRACE,
    NUMBER,
    BOOLEAN,
    BLANK,
    LITERAL,
    COMMA,
    COLON,
    EOF;

}
