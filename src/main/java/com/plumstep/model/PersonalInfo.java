package com.plumstep.model;

import com.plumstep.converter.EncryptionAttributeConverter;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Example entity whose data is stored in a database.  In this example certain fields like ssn and email are
 * annotated to use a converter that will handle the encryption/decryption of data when it is saved/retrieved
 * from the database.
 */
@Entity
public class PersonalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Unique identifier for this contact", readOnly = true)
    private Long id;

    @ApiModelProperty(value = "First name", required = true)
    private String firstName;

    @ApiModelProperty(value = "Last name", required = true)
    private String lastName;

    @ApiModelProperty(value = "Social security number", required = true)
    @Convert(converter = EncryptionAttributeConverter.class)
    private String ssn;

    @ApiModelProperty(value = "Email address", required = true)
    @Convert(converter = EncryptionAttributeConverter.class)
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
