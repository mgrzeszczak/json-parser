package github.com.mgrzeszczak.jsonparser.json;

public class JsonBoolean extends JsonNode {

    private final boolean value;

    public JsonBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public boolean isBoolean() {
        return true;
    }

    @Override
    public boolean getBoolean() {
        return value;
    }

    @Override
    public String print(JsonPrintType type) {
        return String.valueOf(value);
    }
}
