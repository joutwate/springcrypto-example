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
        TextEncryptor encryptor = SpringApplicationContext.getBean(TextEncryptor.class);
        String result = encryptor.encrypt(attribute);
        System.out.println("encrypting = [" + attribute + "] -> [" + result + "]");
        return result;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        TextEncryptor encryptor = SpringApplicationContext.getBean(TextEncryptor.class);
        String result = encryptor.decrypt(dbData);
        System.out.println("decrypting = [" + dbData + "] -> [" + result + "]");
        return result;
    }
}
