package com.thexbyte.bioaqua.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Configuration
public class FirebaseConfig {

    private static final String GITHUB_FILE_URL = "https://raw.github.com/bioaquasolution/backend//main/src/main/resources/firebase-adminsdk.json";

    @PostConstruct
    public void initialize() throws IOException {
        // Fetch file from GitHub
        InputStream serviceAccount = fetchFileFromGitHub(GITHUB_FILE_URL);

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
    }

    private InputStream fetchFileFromGitHub(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        if (connection.getResponseCode() != 200) {
            throw new IOException("Failed to fetch Firebase config: HTTP " + connection.getResponseCode());
        }

        return connection.getInputStream();
    }
}
