package com.javaee.exercises.reminders.business.todos.boundary;

import java.io.IOException;
import java.io.Writer;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonWriter;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class JsonEncoder implements Encoder.TextStream<JsonObject> {
    @Override
    public void init(EndpointConfig config) {
    }
    @Override
    public void encode(JsonObject payload, Writer writer) throws
            EncodeException, IOException {
        try (JsonWriter jsonWriter = Json.createWriter(writer)) {
            jsonWriter.writeObject(payload);
        }
    }
    @Override
    public void destroy() {
    }
}