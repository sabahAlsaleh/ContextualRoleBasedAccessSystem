package se.kth.ContextualRoleBasedAccessSystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Data
public class Event {
    private String eventId;
    private String eventType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Date timestamp;
    private Long userId;
    private Map<String, String> details;

    public Event(String eventType, Long userId, Map<String, String> details) {
        this.eventId = UUID.randomUUID().toString();
        this.eventType = eventType;
        this.timestamp = new Date();  // Sätter tidsstämpeln till nuvarande tidpunkt
        this.userId = userId;
        this.details = details;
    }


    public String toJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing event to JSON", e);
        }
    }
}
