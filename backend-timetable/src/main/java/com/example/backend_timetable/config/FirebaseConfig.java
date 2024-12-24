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
        "  \"private_key_id\": \"a4ffd8f6eedb409bd2b1f06c22f34bcd4861c97d\",\n" +
        "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCyOpPOnp3JtoC5\\nfK8RgwI+eexLLOWbERE01rdM7w+p5xCzpjWU+JaxL/Rm2pnWKJvUrDGonwEICDIb\\n0D4dQb7dOh6c86dgbGdf5P/hlFe40f95KpDTJhnBQTYwqnrFvcjU9uRhjCWGtplY\\nWyxoA2KgdvQPa2ZA4kz+TOLS26P1ABegmnOr4sVYXdfjr6+ZcbysdLrJ7/PJFLp3\\nNPB1WY80qkCXrFGu3tm7Xtw+AcF8bjF3JMVBKhKFEAQVvtsrPSQvVihHGv0iXgzj\\nA6Bri8DdH+46FCc8j318Lz9vZzD4lAoKkxAIBiLXo92hisY/bv9hGR8ijM2D54Yq\\n+ml+yzIpAgMBAAECggEABPPjbe9IHjYFRLebcSaiq37IMLHviCFvc3hGAWF2niI5\\ne2yHz6XKy44JbMmaqHIExoz/F5r3id7EbwkYjh0+GGKy6BSjQZGG8TS+tujgOSwT\\nZ9gQIeCiAECiKLRhaK5PRDpqJUXM4yie1pmOAIPw5oRNCu1RRZtO8sAgi3smsWUC\\ngihDwgmt2C0Je83URVbpfuomCD1LeL+lY1QW7yFQHfSbCG28XnWc3VmUbrw1i1U3\\nO65HwD2Ev/Rc8M3dE6WeJQcLJBvqwoJUEW2FkXPRQmALII6tpOI1t8xKMTXeqB64\\n3Od2rXMhAGGNztGoy120WpicTmhbhwFgsLF1s/O9SwKBgQDp1t88qecKO0qFuhrI\\nM7XQrHmOWHRbMU09Omwfk04O37eSqLzuDTvUeiMQaevAwBPeQOtYxqsaVIFb0xU3\\nj2ux2VIH+YYGrbxgrBxIJ1T3rsTonCKWmnoQeRzaLnrD2pSW44R282a9g8fXpxYt\\nF1upEbu9g1HSeWbMhQhpaC5d2wKBgQDDHozExrIE1s65YtHUg5RN9EUq161x7iR6\\n3/ENV/6gvf3/M3LBkxEoZwH1iIKTpes+HhdkZJN+deONaV7BgGc6oHHLGL48/CC8\\niAXfT23D+fNyDU1CEdVV3SP30gd1J4zXaFQ3EbKpIJ07jZbVmQXnwcUKm2l4qK4U\\nADduxosJSwKBgDBLJzmhIU00p5RstiFIxLD/He+vBwDacshFa5Ut1/4sZ/5LCRHk\\n+8lLHDjEl2LsiG3rXG+uQCIk975eHtCltyk4578r4mk4smIIyaKBNQQePR6JOH+A\\nEdubF8FbvhD68JslC/OBG2PidoBsSqEvZMfLGMnv+BMO7ZMpmm4ijQjnAoGBAIr+\\n0kzDNsGXvD/4E34OFbHKlxND/Tyx5q7ZpMBatPsRHUWVAuS+vXRInO6qRkqj4Yao\\nrQVJWIC3vlhYfbXeoKr07Ns/TxXmjfN/+p7v2EPJZREURLsSNSUrmZnKtqFQgVZZ\\n7dQk+V7d6uQnoxGaJsjIEnPR7jqT/x4kYVi6ewUtAoGBAMZ7YdkKrzq8he/IGiBy\\nArkM5T0uaIcRYqs23YkkDTNZfGqrBnuiYveeaWdkmvUbCZAf0HRo/Zx1M81ruh2F\\nrgeOJ4/YUIrZAm1E/WLWC0liu9r/pgV5qYyTgWigWgwPiUGggd20TeSZ6YJSQ2zc\\nKcLKPERGGe98eoJSZtk37Imm\\n-----END PRIVATE KEY-----\\n\",\n" +
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
