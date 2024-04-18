package se.kth.ContextualRoleBasedAccessSystem.service;

import com.eventstore.dbclient.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.ContextualRoleBasedAccessSystem.model.Event;

import java.util.Map;

@Service
public class EventService {
    @Autowired
    private  EventStoreDBClient eventStoreClient;

    public void logEvent(String eventType, Long userId, Map<String, String> details) {
        Event event = new Event(eventType, userId, details);
        EventData eventData = EventData.builderAsJson(eventType, event.toJson().getBytes()).build();

        try {
            eventStoreClient.appendToStream("user-activity-stream", eventData).get();
        } catch (Exception e) {
            throw new RuntimeException("Failed to append event to stream", e);
        }
    }
}

