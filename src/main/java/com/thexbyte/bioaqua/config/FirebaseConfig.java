package com.thexbyte.bioaqua.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;
import java.io.*;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initialize() throws IOException {
        // Create a temporary file
        File tempFile = File.createTempFile("firebase-adminsdk", ".json");
        tempFile.deleteOnExit(); // Ensure it gets deleted after JVM exits

        // JSON content (replace with your actual JSON content)
        String jsonContent = "{\n" +
                "  \"type\": \"service_account\",\n" +
                "  \"project_id\": \"bioaqua-db603\",\n" +
                "  \"private_key_id\": \"b6dd00eb2216e57c1bc6881cfe59429077ad5f50\",\n" +
                "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBAD...\\n-----END PRIVATE KEY-----\\n\",\n" +
                "  \"client_email\": \"firebase-adminsdk-fbsvc@bioaqua-db603.iam.gserviceaccount.com\",\n" +
                "  \"client_id\": \"108958079702741829097\",\n" +
                "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
                "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-fbsvc%40bioaqua-db603.iam.gserviceaccount.com\",\n" +
                "  \"universe_domain\": \"googleapis.com\"\n" +
                "}";

        // Write JSON content to the temporary file
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(jsonContent);
        }

        // Load Firebase configuration from the temp file
        try (FileInputStream serviceAccount = new FileInputStream(tempFile)) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        }
    }
}
