package eu.dreamix.encryptor;

import org.jasypt.util.text.StrongTextEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/data/jdbc-pg.properties")
public class RootConfig {

    @Bean
    public StrongTextEncryptor encryptor() {

        StrongTextEncryptor encryptor = new StrongTextEncryptor();
        encryptor.setPassword(System.getenv().get("ENC_KEY"));

        return encryptor;
    }
}