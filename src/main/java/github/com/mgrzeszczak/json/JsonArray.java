package github.com.mgrzeszczak.json;

import java.util.List;
import java.util.stream.Collectors;

public class JsonArray implements JsonNode {

    private List<JsonNode> nodes;

    public JsonArray(List<JsonNode> nodes) {
        this.nodes = nodes;
    }

    public List<JsonNode> getNodes() {
        return nodes;
    }

    @Override
    public String getValue() {
        return "[ " + nodes.stream().map(JsonNode::getValue).collect(Collectors.joining(", ")) + " ]";
    }
}
