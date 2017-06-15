package github.com.mgrzeszczak.jsonparser.json;

import java.util.List;
import java.util.stream.Collectors;

public class JsonArray extends JsonNode {

    private List<JsonNode> nodes;

    public JsonArray(List<JsonNode> nodes) {
        this.nodes = nodes;
    }

    @Override
    public boolean isArray() {
        return true;
    }

    @Override
    public List<JsonNode> getArray() {
        return nodes;
    }

    @Override
    public String print(JsonPrintType type) {
        String join = type.getJoinCharacter();
        return "[" + join + nodes.stream().map(JsonNode::toString).collect(Collectors.joining("," + join)) + join + "]";
    }
}
