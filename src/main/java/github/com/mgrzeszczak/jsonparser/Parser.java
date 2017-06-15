package github.com.mgrzeszczak.jsonparser;

import github.com.mgrzeszczak.jsonparser.json.JsonNode;

interface Parser {

    JsonNode parse();

    static Parser of(Tokenizer tokenizer) {
        return new BasicParser(tokenizer);
    }

}
