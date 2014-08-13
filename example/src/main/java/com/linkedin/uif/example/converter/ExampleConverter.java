package com.linkedin.uif.example.converter;

import java.lang.reflect.Type;
import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import com.linkedin.uif.configuration.WorkUnitState;
import com.linkedin.uif.converter.ToAvroConverterBase;

/**
 * An example {@link com.linkedin.uif.converter.Converter} for testing and
 * demonstration purposes.
 */
public class ExampleConverter extends ToAvroConverterBase<String, String> {

    private static final Gson GSON = new Gson();
    // Expect the input JSON string to be key-value pairs
    private static final Type FIELD_ENTRY_TYPE =
            new TypeToken<Map<String, Object>>(){}.getType();

    @Override
    public Schema convertSchema(String schema, WorkUnitState workUnit) {
        return new Schema.Parser().parse(schema);
    }

    @Override
    public GenericRecord convertRecord(Schema schema, String inputRecord,
        WorkUnitState workUnit) {

        JsonElement element = GSON.fromJson(inputRecord, JsonElement.class);
        Map<String, Object> fields = GSON.fromJson(element, FIELD_ENTRY_TYPE);
        GenericRecord record = new GenericData.Record(schema);
        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            record.put(entry.getKey(), entry.getValue());
        }

        return record;
    }
}