package github.com.mgrzeszczak.jsonparser;

import github.com.mgrzeszczak.jsonparser.exception.StructuralException;
import github.com.mgrzeszczak.jsonparser.json.JsonArray;
import github.com.mgrzeszczak.jsonparser.json.JsonBoolean;
import github.com.mgrzeszczak.jsonparser.json.JsonDouble;
import github.com.mgrzeszczak.jsonparser.json.JsonInteger;
import github.com.mgrzeszczak.jsonparser.json.JsonNode;
import github.com.mgrzeszczak.jsonparser.json.JsonNull;
import github.com.mgrzeszczak.jsonparser.json.JsonObject;
import github.com.mgrzeszczak.jsonparser.json.JsonString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static github.com.mgrzeszczak.jsonparser.TokenType.CLOSE_BRACE;
import static github.com.mgrzeszczak.jsonparser.TokenType.CLOSE_BRACKET;
import static github.com.mgrzeszczak.jsonparser.TokenType.COLON;
import static github.com.mgrzeszczak.jsonparser.TokenType.COMMA;
import static github.com.mgrzeszczak.jsonparser.TokenType.EOF;
import static github.com.mgrzeszczak.jsonparser.TokenType.OPEN_BRACE;
import static github.com.mgrzeszczak.jsonparser.TokenType.OPEN_BRACKET;
import static github.com.mgrzeszczak.jsonparser.TokenType.STRING;

class BasicParser implements Parser {

    private final Tokenizer tokenizer;

    BasicParser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public JsonNode parse() {
        JsonNode jsonNode = parseJson();
        match(EOF);
        return jsonNode;
    }

    private JsonNode parseJson() {
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
            JsonNode jsonNode = parseJson();
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
            nodes.add(parseJson());
            if (tokenizer.peek().getType() != CLOSE_BRACKET) {
                match(COMMA);
            }
        }
        match(CLOSE_BRACKET);
        return new JsonArray(nodes);
    }

    private JsonNode parseValue() {
        Token next = tokenizer.next();
        try {
            switch (next.getType()) {
                case NULL:
                    return new JsonNull();
                case NUMBER:
                    try {
                        return new JsonInteger(Integer.parseInt(next.getContent()));
                    } catch (NumberFormatException e) {
                        return new JsonDouble(Double.parseDouble(next.getContent()));
                    }
                case STRING:
                    return new JsonString(next.getContent().substring(1, next.getContent().length() - 1));
                case BOOLEAN:
                    return new JsonBoolean(Boolean.valueOf(next.getContent()));
            }
        } catch (Exception e) {
            reject("cannot parse value: " + next.getContent());
        }
        reject("logic error");
        return null;
    }

    private void match(TokenType expected) {
        TokenType actual = tokenizer.next().getType();
        if (actual != expected) {
            reject("expected " + expected.name() + ", got " + actual.name());
        }
    }

    private void reject(String message) {
        throw new StructuralException(message);
    }

}
