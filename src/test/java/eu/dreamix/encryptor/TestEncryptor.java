package eu.dreamix.encryptor;

import org.jasypt.properties.PropertyValueEncryptionUtils;
import org.jasypt.util.text.StrongTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@ContextConfiguration(classes = { RootConfig.class })
public class TestEncryptor extends AbstractTestNGSpringContextTests {

	@Autowired
	private StrongTextEncryptor encryptor;

	@Value("${db.password.encrypted}")
	private String PASSWORD_ENCRYPTED;

	@Value("${db.password.plain}")
	private String PASSWORD_PLAIN;

	@Test
	public void encrypt() {
		String encypted = encryptor.encrypt(PASSWORD_PLAIN);
		Assert.assertNotEquals(PASSWORD_ENCRYPTED, encypted);
	}

	@Test
	public void decrypt() {
		if (PropertyValueEncryptionUtils.isEncryptedValue(PASSWORD_ENCRYPTED)) {
			String decypted = PropertyValueEncryptionUtils.decrypt(PASSWORD_ENCRYPTED, encryptor);
			Assert.assertEquals(PASSWORD_PLAIN, decypted);
		}
	}
}
