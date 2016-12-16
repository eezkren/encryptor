package eu.dreamix.encryptor;

import javax.annotation.Resource;

import org.jasypt.properties.PropertyValueEncryptionUtils;
import org.jasypt.util.text.StrongTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@ContextConfiguration(classes = { RootConfig.class })
public class TestEncryptor extends AbstractTestNGSpringContextTests {

    @Resource
    private Environment environment;

    @Autowired
    private StrongTextEncryptor encryptor;

    private static final String PROPERTY_NAME_DATABASE_PASSWORD_PLAIN = "db.password";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD_ENCRYPTED = "db.password.encrypted";

    @Test
    public void encrypt() {
        String passwordPropertyValuePlain = environment.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD_PLAIN);

        String encypted = encryptor.encrypt(passwordPropertyValuePlain);

        Assert.assertNotEquals(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD_ENCRYPTED), encypted);

    }

    @Test
    public void decrypt() {

        String passwordPropertyValueEncrypted = environment
                .getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD_ENCRYPTED);

        if (PropertyValueEncryptionUtils.isEncryptedValue(passwordPropertyValueEncrypted)) {
            String decypted = PropertyValueEncryptionUtils.decrypt(passwordPropertyValueEncrypted, encryptor);
            Assert.assertEquals(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD_PLAIN), decypted);
        }

    }
}
