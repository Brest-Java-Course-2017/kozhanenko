package by.eventcat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * TimePeriod Serializer
 */
public class TimePeriodSerializer extends StdSerializer<TimePeriod> {

    public TimePeriodSerializer() {
        this(null);
    }

    private TimePeriodSerializer(Class<TimePeriod> t) {
        super(t);
    }

    @Override
    public void serialize(
            TimePeriod value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException{

        jgen.writeStartObject();
        jgen.writeNumberField("timePeriodId", value.getTimePeriodId());
        jgen.writeObjectFieldStart("event");
            jgen.writeObjectField("eventId", value.getEvent().getEventId());
            jgen.writeObjectFieldStart("category");
                if(value.getEvent().getCategory() != null){
                    jgen.writeObjectField("categoryId", value.getEvent().getCategory().getCategoryId());
                    jgen.writeObjectField("categoryName", value.getEvent().getCategory().getCategoryName());
                } else {
                    jgen.writeObjectField("categoryId", null);
                    jgen.writeObjectField("categoryName", null);
                }
            jgen.writeEndObject();
            jgen.writeObjectField("eventName", value.getEvent().getEventName());
            jgen.writeObjectField("eventPlace", value.getEvent().getEventPlace());
        jgen.writeEndObject();
        jgen.writeNumberField("beginning", value.getBeginning());
        jgen.writeNumberField("end", value.getEnd());
        jgen.writeEndObject();
    }
}
