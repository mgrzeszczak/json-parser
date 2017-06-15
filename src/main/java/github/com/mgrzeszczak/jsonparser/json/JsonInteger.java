package github.com.mgrzeszczak.jsonparser.json;

public class JsonInteger extends JsonNode {

    private final int value;

    public JsonInteger(int value) {
        this.value = value;
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public boolean isInteger() {
        return true;
    }

    @Override
    public int getInteger() {
        return value;
    }

    @Override
    public String print(JsonPrintType type) {
        return String.valueOf(value);
    }
}
