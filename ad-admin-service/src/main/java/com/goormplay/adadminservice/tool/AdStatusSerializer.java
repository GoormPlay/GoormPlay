package com.goormplay.adadminservice.tool;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.goormplay.adadminservice.entity.AdStatus;

import java.io.IOException;

public class AdStatusSerializer extends JsonSerializer<AdStatus> {
    @Override
    public void serialize(AdStatus status, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(status.getStatusCode());
    }
}
