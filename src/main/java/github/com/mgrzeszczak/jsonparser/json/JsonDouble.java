package github.com.mgrzeszczak.jsonparser.json;

public class JsonDouble extends JsonNode {

    private double value;

    public JsonDouble(double value) {
        this.value = value;
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public boolean isDouble() {
        return true;
    }

    @Override
    public double getDouble() {
        return value;
    }

    @Override
    public String print(JsonPrintType type) {
        return String.valueOf(value);
    }

}
