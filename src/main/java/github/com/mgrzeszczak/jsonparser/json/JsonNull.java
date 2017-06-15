package github.com.mgrzeszczak.jsonparser.json;

public class JsonNull extends JsonNode {

    @Override
    public boolean isNull() {
        return true;
    }

    @Override
    public String print(JsonPrintType type) {
        return "null";
    }
}
