package github.com.mgrzeszczak.lexical;

import github.com.mgrzeszczak.exception.LexicalException;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static github.com.mgrzeszczak.lexical.TokenType.BOOLEAN;
import static github.com.mgrzeszczak.lexical.TokenType.CLOSE_BRACE;
import static github.com.mgrzeszczak.lexical.TokenType.CLOSE_BRACKET;
import static github.com.mgrzeszczak.lexical.TokenType.COLON;
import static github.com.mgrzeszczak.lexical.TokenType.COMMA;
import static github.com.mgrzeszczak.lexical.TokenType.NULL;
import static github.com.mgrzeszczak.lexical.TokenType.NUMBER;
import static github.com.mgrzeszczak.lexical.TokenType.OPEN_BRACE;
import static github.com.mgrzeszczak.lexical.TokenType.OPEN_BRACKET;
import static github.com.mgrzeszczak.lexical.TokenType.STRING;
import static org.junit.Assert.assertTrue;

public class TokenizerTest {


    @Test
    public void testSimpleTokens() {
        Stream.of(
                TestCase.of(
                        "123123",
                        Token.of(NUMBER, "123123")
                ),
                TestCase.of(
                        "null",
                        Token.of(NULL, "null")
                ),
                TestCase.of(
                        "{",
                        Token.of(OPEN_BRACE, "{")
                ),
                TestCase.of(
                        "}",
                        Token.of(CLOSE_BRACE, "}")
                ),
                TestCase.of(
                        "[",
                        Token.of(OPEN_BRACKET, "[")
                ),
                TestCase.of(
                        "]",
                        Token.of(CLOSE_BRACKET, "]")
                ),
                TestCase.of(
                        "false",
                        Token.of(BOOLEAN, "false")
                ),
                TestCase.of(
                        "true",
                        Token.of(BOOLEAN, "true")
                ),
                TestCase.of(
                        ":::",
                        Token.of(COLON, ":"),
                        Token.of(COLON, ":"),
                        Token.of(COLON, ":")

                ),
                TestCase.of(
                        ",,,,",
                        Token.of(COMMA, ","),
                        Token.of(COMMA, ","),
                        Token.of(COMMA, ","),
                        Token.of(COMMA, ",")
                )
        ).forEach(TestCase::check);
    }

    @Test
    public void testStringTokens() {
        Stream.of(
                TestCase.of(
                        "\"hello world\" \"hello world\"",
                        Token.of(STRING, "\"hello world\""),
                        Token.of(STRING, "\"hello world\"")
                )
        ).forEach(TestCase::check);
    }

    @Test(expected = LexicalException.class)
    public void testInvalidInput() {
        Stream.of(
                TestCase.of(
                        "truefalse"
                )
        ).forEach(TestCase::check);
    }

    @Test
    public void testValidJson() {
        Stream.of(
                TestCase.of(
                        "{\"abcd\" : 1232.3232}",
                        Token.of(OPEN_BRACE, "{"),
                        Token.of(STRING, "\"abcd\""),
                        Token.of(COLON, ":"),
                        Token.of(NUMBER, "1232.3232"),
                        Token.of(CLOSE_BRACE, "}")
                ),
                TestCase.of(
                        "{\"abcd\" : {\"list\" : [1,2,3,4,5,6], \"var\" : null}}",
                        Token.of(OPEN_BRACE, "{"),
                        Token.of(STRING, "\"abcd\""),
                        Token.of(COLON, ":"),
                        Token.of(OPEN_BRACE, "{"),
                        Token.of(STRING, "\"list\""),
                        Token.of(COLON, ":"),
                        Token.of(OPEN_BRACKET, "["),
                        Token.of(NUMBER, "1"),
                        Token.of(COMMA, ","),
                        Token.of(NUMBER, "2"),
                        Token.of(COMMA, ","),
                        Token.of(NUMBER, "3"),
                        Token.of(COMMA, ","),
                        Token.of(NUMBER, "4"),
                        Token.of(COMMA, ","),
                        Token.of(NUMBER, "5"),
                        Token.of(COMMA, ","),
                        Token.of(NUMBER, "6"),
                        Token.of(CLOSE_BRACKET, "]"),
                        Token.of(COMMA, ","),
                        Token.of(STRING, "\"var\""),
                        Token.of(COLON, ":"),
                        Token.of(NULL, "null"),
                        Token.of(CLOSE_BRACE, "}"),
                        Token.of(CLOSE_BRACE, "}")
                )
        ).forEach(TestCase::check);

    }

    private static final class TestCase {

        private final String input;
        private final List<Token> tokens;

        private TestCase(String input, List<Token> tokens) {
            this.input = input;
            this.tokens = tokens;
        }

        private static TestCase of(String input, Token... tokens) {
            return new TestCase(input, Arrays.asList(tokens));
        }

        void check() {
            Tokenizer tokenizer = Tokenizer.of(input);
            tokens.forEach(t -> assertTrue(areEqual(t, tokenizer.next())));
            assertTrue(tokenizer.next().equals(Token.EOF));
        }
    }

    private static boolean areEqual(Token t1, Token t2) {
        return t1.getType() == t2.getType() && t1.getContent().equals(t2.getContent());
    }


}