package com.example.backend_timetable.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;  
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class FirebaseConfig {

    private static final Logger logger = Logger.getLogger(FirebaseConfig.class.getName());

    private static final String SERVICE_ACCOUNT_JSON = "{\n" +
            "  \"type\": \"service_account\",\n" +
            "  \"project_id\": \"fetprojet\",\n" +
            "  \"private_key_id\": \"18a6c6f624ab40e162cdf074151a0e301adb60f1\",\n" +
            "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDlWTCW9ob2K7WM\\nAc+3FHY7hpxcufIKQxDRforrBPgKDJtw0yh8JVLweulH6NqkoQqwBtTVCT350bjt\\n1ySDPLnj/DW1aPLFA+kwPPOV8AqOzLg/E/q/t0hp3xLf0UgzAA3wVRtWCeJK/Yut\\nzwl6an/5y02prC2e/5c0Wom+ZxMh2pWseRQaAJpRlT3ygl9UkPEsP/qAxJ8/VDML\\nitmLIn9GwsTKWwpj9JqbU58e+vJpE9PGhi5s+GS34mJOw3LJ8EXSld+5pypF7W3S\\nx7BFpMFSkZgTTi8uINHjmw0VEjZtPZK8yKz1NHBPf2QZ9Q55OEx8Px93F8uG2G8J\\nfuttKJopAgMBAAECggEACZcLBnb4wfymJtxxB3BkOL06VtDCJ45yGrZyP91Jc18f\\nVU6JDfbgEtTq4gJBOTrQJ/fjKKTOKf7sXwqc2N1+TuOOCd/Bbf5JvwX/b/y1bk7u\\nYdnXpXJYJXAp2YDGZnaJPqxKXDTF2Pmk4G/Mr0kagJMRFGgSEMjSdWKXSaZE+Sh2\\nzbLARFixOo7RO2gC3x0LoGHBp0MAaxf/ZEqwaYzsMoWyqZQ/5s8987cuvNvJ+WNO\\njdJT0hC0vPc1BgePPYH10moW+TGq4okx2506nppAup4oBOwNTb+HSK3URUwWmX2i\\nQFFOUpE6uurGWSLapAita2FnCR/izEdUhOchA4z1EQKBgQD5aeTNmb5+LjudeD2a\\nKdlwFKeHIGssmLVxfyfz1pKXUIrOo4U/I/Y9B72RZBH6f3jJosS2y9f4WIlRoG27\\nT3iC6oYWQEHwZ59B12vGKKrkQ+NVhkXOhqolri9r8ynYU72+oI2JaSxKKjKzX5VM\\nTV1oTBzNfLMHBfO8ci3Wn8vOkQKBgQDrZ6Y5/0x16aMMsWi+wY81ApZhakK1Busg\\nYx+DI4XgJKg1jnqSvQIi3goPRIYT7tuD2eWJY4nXJQxwrkfFsep7lPyEtC1X1iWv\\nYVoAPuASkxODG2UY2J32ral6DHJJDMLSVAT9d7tg4VNwODlceRryAipum6/ZB0NT\\nUXAgi12OGQKBgH1i2g7uktC2h2vjzfxZu6FYENyi1J5n6xey2DACmFGPwSifFlam\\nypzl7kxQZaVsPhoIRSKsmdeVEvvBTkkTa79NDbnT9hJY1bxxAxJs0XTOGFSfmyp3\\nksQ8rER1y3ygpMxXxK/m+sgSLMj2bYUdg67yTKyVWx9Vw7HiKN80GG6xAoGAYA1R\\n2TZfh3lGi2ZUPAmUmmes3qDSCTWCeRuY/xReit/KNtglEXzHw1xgNGycXMPIlBsz\\nuoR/+OZoFg42wObgq35WLvupyL5bcB1D8Pe2zD1w570k2ieBBOdFUMSFWWM4l0Or\\nKZVSQqyONCrs1NuhcAAMOkeST/bTtMp0+wKCsPkCgYEA6lAUAhmZAjdNWYiv8C07\\nN+NvD8o7rfNLUbZS8gtam8paaSVJQbitjvTGrt4Vit5X3D/IzmYFHL5mLyS7XEav\\nEFFiOWooLaWFDP7a2M50tqsY5d6GVdkm1xUdTRUg5bXMRa+Fe+OMjJkPZ8xcAupK\\nidpe/SLbbTA9RkdsCFsqWQo=\n-----END PRIVATE KEY-----\\n\",\n" +
            "  \"client_email\": \"firebase-adminsdk-sc3ua@fetprojet.iam.gserviceaccount.com\",\n" +
            "  \"client_id\": \"118168736354118244774\",\n" +
            "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
            "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
            "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
            "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-sc3ua%40fetprojet.iam.gserviceaccount.com\",\n" +
            "  \"universe_domain\": \"googleapis.com\"\n}";

    @PostConstruct
    public void initialize() {
        try {
            // Convert JSON string to InputStream
            InputStream serviceAccount = new ByteArrayInputStream(SERVICE_ACCOUNT_JSON.getBytes(StandardCharsets.UTF_8));

            FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                logger.info("FirebaseApp initialized successfully.");
            } else {
                logger.warning("FirebaseApp has already been initialized.");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize Firebase with the service account key.", e);
        }
    }
}
