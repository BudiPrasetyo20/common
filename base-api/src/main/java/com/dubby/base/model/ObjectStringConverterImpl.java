package com.dubby.base.model;

import com.dubby.base.exception.BaseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


@Component
public class ObjectStringConverterImpl implements ObjectStringConverter {
    
    protected String inflate(byte[] source) throws UnsupportedEncodingException, DataFormatException {

        Inflater inflater = new Inflater();
        inflater.setInput(source);

        ByteArrayOutputStream rawResult = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        while (!inflater.finished()) {
            int uncompressedLength = inflater.inflate(buffer);
            rawResult.write(buffer, 0, uncompressedLength);
        }

        inflater.end();

        // Decode the bytes into a String
        return new String(rawResult.toByteArray());

    }
    
    protected byte[] deflate(String source) throws IOException {

        // Compress the bytes
        Deflater compresser = new Deflater(Deflater.BEST_COMPRESSION);
        compresser.setInput(source.getBytes("UTF-8"));
        compresser.finish();

        ByteArrayOutputStream rawResult = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        while (!compresser.finished()) {
            int compressedLength = compresser.deflate(buffer);
            rawResult.write(buffer, 0, compressedLength);
        }

        rawResult.close();

        return rawResult.toByteArray();

    }
    
    @Override
    public <T> T convertToObject(Class<T> objectClass, byte[] content) throws Exception {
        
        T result = null;
        
        if (NullEmptyChecker.isNotNullOrEmpty(content)) {
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(inflate(content), objectClass);
        }
        
        if (result == null) {
            result = objectClass.newInstance();
        }
        
        return result;
        
    }

    @Override
    public <T> T convertToObject(Class<T> objectClass, String content) throws Exception {

        T result = null;

        if (NullEmptyChecker.isNotNullOrEmpty(content)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            try {
                result = mapper.readValue(content, objectClass);
            } catch (Exception e) {
                result = objectClass.newInstance();
            }
        }

        if (result == null) {
            result = objectClass.newInstance();
        }

        return result;

    }

    @Override
    public <T> byte[] convertToDeflatedString(Class<T> objectClass, Object object) throws Exception {

        if (!object.getClass().isAssignableFrom(objectClass)) {
            throw new BaseException("Object is not compatible with the specified class.");
        }

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writerWithType(objectClass);

        if (object == null) {
            object = objectClass.newInstance();
        }

        return deflate(ow.writeValueAsString(object));
    }

    @Override
    public <T> String convertToString(Class<T> objectClass, Object object) throws Exception {

        if (!objectClass.isAssignableFrom(object.getClass())) {
            throw new BaseException("Object is not compatible with the specified class.");
        }

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writerWithType(objectClass);

        if (object == null) {
            object = objectClass.newInstance();
        }

        return ow.writeValueAsString(object);
    }

    @Override
    public <T> T convertToListOfObjects(final TypeReference<T> type, final String content) throws Exception {
        return new ObjectMapper().readValue(content, type);
    }

}
