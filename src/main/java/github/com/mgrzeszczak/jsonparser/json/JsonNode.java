package github.com.mgrzeszczak.jsonparser.json;

import java.util.List;
import java.util.Map;

public abstract class JsonNode {

    public boolean isObject() {
        return false;
    }

    public boolean isArray() {
        return false;
    }

    public boolean isNumber() {
        return false;
    }

    public boolean isInteger() {
        return false;
    }

    public boolean isDouble() {
        return false;
    }

    public boolean isString() {
        return false;
    }

    public boolean isNull() {
        return false;
    }

    public boolean isBoolean() {
        return false;
    }

    public Map<String, JsonNode> getObject() {
        return null;
    }

    public List<JsonNode> getArray() {
        return null;
    }

    public double getDouble() {
        return 0.0d;
    }

    public int getInteger() {
        return 0;
    }

    public String getString() {
        return null;
    }

    public boolean getBoolean() {
        return false;
    }

    public abstract String print(JsonPrintType type);

    @Override
    public String toString() {
        return print(JsonPrintType.MINIFIED);
    }
}
