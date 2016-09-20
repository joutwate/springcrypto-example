##Quick Start

***Ensure that you have the unlimited [Java Cryptography Extension](http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html) installed***

Run the application
```
cd springcrypto-example
mvn spring-boot:run
```

Create a new `PersonalInfo` instance using a tool like curl.
```
curl -X POST -d @src/main/resources/create_personal_info_payload.json http://localhost:8080/pi --header "Content-Type:application/json"
```

Look at how the data is stored in the DB from the [h2-console](http://localhost:8080/h2-console).
The JDBC URL is `jdbc:h2:mem:pi` with `sa` as the username and no password.
```
SELECT * FROM PERSONAL_INFO
```
You should see something like the following
```
ID  EMAIL                             FIRST_NAME  LAST_NAME  SSN  
1   4e86ff25feaf958633b31eb1c075d306  Joshua      Outwater   efbca6b5a652f35226a7f9b7a707c104
```
where email and SSN are both encrypted strings


##Summary

This demo contains a SpringBoot application that utilizes Spring Security to implement application level encryption
using AES 256 with 1024 iterations.  The code to create the encryptor is in `SpringEncryptionApplication` and exposed
as a bean, see `textEncryptor()`, that is used by the application to encrypt and decrypt data.

A bean When you bring up the service a salt and password will be generated for the run and printed to the
console.
```
Generated salt = 611632d2d3088b20
Generated password = A%L8n=C&?)}EH}ELC cf}{P#VvUVh$)24f6]as?d5rHiT3#?Z~-XC+M6a3imVaQ7BXh{d.-%yqwN5S~j7r@z:HV|vb++)kn!N=^}E6J><]tZ/<i-P1Xa*L{p(~FR6QE'
```
To generate the password we use a utility class, `SecureRandomStringUtils`, based on Apache's `RandomStringUtils` that 
generates random ascii strings using `SecureRandom`.

Application data is encrypted prior to being stored in the database and decrypted when it
is retrieved using [AttributeConverter](https://docs.oracle.com/javaee/7/api/javax/persistence/AttributeConverter.html).

Looking at the model `PersonalInfo` you will see that both the email and SSN are annotated with `@Convert`.
```
    @Convert(converter = EncryptionAttributeConverter.class)
    private String ssn;

    @Convert(converter = EncryptionAttributeConverter.class)
    private String email;
```
This indicates that the `EncryptionAttributeConverter` class should be called any time those attributes are read or written
via JPA.

If we create a new `PersonalInfo` instance as mentioned in the quick start guide you would see something like the
following printed to the console of the app.
```
encrypting = [test@test.com] -> [4e86ff25feaf958633b31eb1c075d306]
encrypting = [111111111] -> [efbca6b5a652f35226a7f9b7a707c104]
```
This output shows how the converter is being called on our annotated fields resulting
in both the email and SSN being encrypted.

Follow this up by retrieving the `PersonalInfo` instance we created by SSN
```
http://localhost:8080/pi/ssn/111111111
```
You will see something like the following on the console
```
encrypting = [111111111] -> [efbca6b5a652f35226a7f9b7a707c104]
decrypting = [4e86ff25feaf958633b31eb1c075d306] -> [test@test.com]
decrypting = [efbca6b5a652f35226a7f9b7a707c104] -> [111111111]
```
The first line shows how our SSN input is being encrypted so we can compare data properly in the DB.  The lines after
show the query result (email and SSN) being decrypted when re-constituting a `PersonalInfo` instance.  You can see that 
the encrypted values for both the email and SSN match those from the step where we created the `PersonalInfo`.