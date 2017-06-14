package github.com.mgrzeszczak.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonObject implements JsonNode {

    private Map<String, JsonNode> nodes;

    public JsonObject(Map<String, JsonNode> nodes) {
        this.nodes = nodes;
    }

    public JsonNode getNode(String key) {
        return nodes.get(key);
    }

    @Override
    public String getValue() {
        List<String> children = new ArrayList<>();
        for (Map.Entry<String, JsonNode> entry : nodes.entrySet()) {
            children.add("\"" + entry.getKey() + "\"" + " : " + entry.getValue().getValue());
        }
        return "{ " + children.stream().collect(Collectors.joining(", ")) + " }";
    }
}
