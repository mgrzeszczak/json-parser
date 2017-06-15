package github.com.mgrzeszczak.jsonparser;

import github.com.mgrzeszczak.jsonparser.json.JsonNode;

public class JsonParser {

    private JsonParser() {
        throw new AssertionError("no instances");
    }

    public static JsonNode parse(String json) {
        return Parser.of(Tokenizer.of(json)).parse();
    }

}
