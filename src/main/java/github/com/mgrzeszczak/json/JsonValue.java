package github.com.mgrzeszczak.json;

public class JsonValue implements JsonNode {

    private final String value;

    public JsonValue(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
