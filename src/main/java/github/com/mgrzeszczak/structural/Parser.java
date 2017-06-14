package github.com.mgrzeszczak.structural;

import github.com.mgrzeszczak.json.JsonNode;
import github.com.mgrzeszczak.lexical.Tokenizer;

public interface Parser {

    JsonNode parse();

    static Parser of(Tokenizer tokenizer) {
        return new BasicParser(tokenizer);
    }

}
