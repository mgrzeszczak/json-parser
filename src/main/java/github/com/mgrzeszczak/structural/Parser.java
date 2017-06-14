package github.com.mgrzeszczak.structural;

import github.com.mgrzeszczak.json.JsonArray;
import github.com.mgrzeszczak.json.JsonNode;
import github.com.mgrzeszczak.json.JsonObject;
import github.com.mgrzeszczak.json.JsonValue;
import github.com.mgrzeszczak.lexical.Token;
import github.com.mgrzeszczak.lexical.TokenType;
import github.com.mgrzeszczak.lexical.Tokenizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static github.com.mgrzeszczak.lexical.TokenType.CLOSE_BRACE;
import static github.com.mgrzeszczak.lexical.TokenType.CLOSE_BRACKET;
import static github.com.mgrzeszczak.lexical.TokenType.COLON;
import static github.com.mgrzeszczak.lexical.TokenType.COMMA;
import static github.com.mgrzeszczak.lexical.TokenType.EOF;
import static github.com.mgrzeszczak.lexical.TokenType.OPEN_BRACE;
import static github.com.mgrzeszczak.lexical.TokenType.OPEN_BRACKET;
import static github.com.mgrzeszczak.lexical.TokenType.STRING;

public class Parser {

    private final Tokenizer tokenizer;

    private Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public static Parser parse(Tokenizer tokenizer) {
        return new Parser(tokenizer);
    }

    public JsonNode parse() {
        JsonNode jsonNode = doParse();
        match(EOF);
        return jsonNode;
    }

    private JsonNode doParse() {
        Token peek = tokenizer.peek();
        switch (peek.getType()) {
            case NULL:
            case NUMBER:
            case BOOLEAN:
            case STRING:
                return parseValue();
            case OPEN_BRACE:
                return parseObject();
            case OPEN_BRACKET:
                return parseArray();
            default:
                reject("expected NULL, NUMBER, BOOLEAN, OPEN_BRACE or OPEN_BRACKET, got " + peek.getType());
                break;
        }
        return null;
    }

    private JsonNode parseObject() {
        Map<String, JsonNode> children = new HashMap<>();
        match(OPEN_BRACE);
        while (tokenizer.peek().getType() != CLOSE_BRACE) {
            Token next = tokenizer.next();
            if (next.getType() != STRING) {
                reject("expected " + STRING + ", got " + next.getType());
            }
            match(COLON);
            JsonNode jsonNode = doParse();
            children.put(next.getContent().replace("\"", ""), jsonNode);
            if (tokenizer.peek().getType() != CLOSE_BRACE) {
                match(COMMA);
            }
        }
        match(CLOSE_BRACE);
        return new JsonObject(children);
    }

    private JsonNode parseArray() {
        List<JsonNode> nodes = new ArrayList<>();
        match(OPEN_BRACKET);
        while (tokenizer.peek().getType() != CLOSE_BRACKET) {
            nodes.add(doParse());
            if (tokenizer.peek().getType() != CLOSE_BRACKET) {
                match(COMMA);
            }
        }
        match(CLOSE_BRACKET);
        return new JsonArray(nodes);
    }

    private JsonNode parseValue() {
        Token next = tokenizer.next();
        return new JsonValue(next.getContent().replace("\"", ""));
    }

    private void match(TokenType expected) {
        TokenType actual = tokenizer.next().getType();
        if (actual != expected) {
            reject("expected " + expected.name() + ", got " + actual.name());
        }
    }

    private void reject(String message) {
        throw new JsonParseException(message);
    }

}
