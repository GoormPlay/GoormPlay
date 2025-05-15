package com.goormplay.adadminservice.tool;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.goormplay.adadminservice.entity.AdStatus;

import java.io.IOException;

public class AdStatusDeserializer extends JsonDeserializer<AdStatus> {
    @Override
    public AdStatus deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        int statusCode = p.getValueAsInt();
        return AdStatus.fromStatusCode(statusCode);
    }
}
