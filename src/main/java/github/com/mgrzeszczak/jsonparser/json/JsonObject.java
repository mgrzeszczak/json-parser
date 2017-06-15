package github.com.mgrzeszczak.jsonparser.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonObject extends JsonNode {

    private Map<String, JsonNode> nodes;

    public JsonObject(Map<String, JsonNode> nodes) {
        this.nodes = nodes;
    }

    @Override
    public boolean isObject() {
        return true;
    }

    @Override
    public Map<String, JsonNode> getObject() {
        return nodes;
    }

    @Override
    public String print(JsonPrintType type) {
        List<String> children = new ArrayList<>();
        for (Map.Entry<String, JsonNode> entry : nodes.entrySet()) {
            children.add("\"" + entry.getKey() + "\"" + " : " + entry.getValue().print(type));
        }
        return "{ " + children.stream().collect(Collectors.joining(", ")) + " }";
    }

}
