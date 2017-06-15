package github.com.mgrzeszczak.jsonparser.json;

public enum JsonPrintType {

    MINIFIED(""),
    EXTENDED(" "),
    PRETTY("\n\t");

    private final String joinValue;

    JsonPrintType(String joinValue) {
        this.joinValue = joinValue;
    }

    public String getJoinCharacter() {
        return joinValue;
    }
}
