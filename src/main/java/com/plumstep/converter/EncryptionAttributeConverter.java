package com.plumstep.converter;

import com.plumstep.utils.SpringApplicationContext;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Converter that can be used to transform encrypt/decrypt string based attributes.
 */
@Converter
public class EncryptionAttributeConverter implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String attribute) {
        System.out.println("attribute = [" + attribute + "]");
        TextEncryptor encryptor = SpringApplicationContext.getBean(TextEncryptor.class);
        return encryptor.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        System.out.println("dbData = [" + dbData + "]");
        TextEncryptor encryptor = SpringApplicationContext.getBean(TextEncryptor.class);
        return encryptor.decrypt(dbData);
    }
}
