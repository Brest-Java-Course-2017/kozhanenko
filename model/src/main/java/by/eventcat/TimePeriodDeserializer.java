package by.eventcat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * TimePeriod model Deserializer
 */
public class TimePeriodDeserializer extends StdDeserializer<TimePeriod> {

    public TimePeriodDeserializer() {
        this(null);
    }

    public TimePeriodDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public TimePeriod deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);

        int timePeriodId = (Integer) (node.get("timePeriodId")).numberValue();
        JsonNode eventNode = node.get("event");
            int eventId = (Integer) (eventNode.get("eventId")).numberValue();
            JsonNode categoryNode = eventNode.get("category");
                int categoryId = (Integer) (categoryNode.get("categoryId")).numberValue();
                String categoryName = categoryNode.get("categoryName").asText();
                Category category = new Category(categoryId,categoryName);
            String eventName = eventNode.get("eventName").asText();
            String eventPlace = eventNode.get("eventPlace").asText();
            Event event = new Event(eventId, category, eventName, eventPlace);
        long beginning = (Integer) (node.get("beginning")).numberValue();
        long end = (Integer) (node.get("end")).numberValue();

        return new TimePeriod(timePeriodId, event, beginning, end);
    }
}
