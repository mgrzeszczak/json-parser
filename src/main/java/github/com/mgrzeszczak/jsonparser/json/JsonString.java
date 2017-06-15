package github.com.mgrzeszczak.jsonparser.json;

public class JsonString extends JsonNode {

    private final String value;

    public JsonString(String value) {
        this.value = value;
    }

    @Override
    public boolean isString() {
        return true;
    }

    @Override
    public String getString() {
        return value;
    }

    @Override
    public String print(JsonPrintType type) {
        return "\"" + value + "\"";
    }
}
