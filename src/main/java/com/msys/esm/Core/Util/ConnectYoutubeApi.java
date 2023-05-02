package com.msys.esm.Core.Util;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Component
public class ConnectYoutubeApi {

    public static String DEVELOPER_KEY;
    public static final String CHANNEL_ID = "UC1B8clCxtmb-bzCDdxmVPDA";
    public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    public static YouTube youtubeService;


    static {
        if (System.getenv("DEVELOPER_KEY") == null) {

            throw new ExceptionInInitializerError("DEVELOPER_KEY is null or empty");

        }

        DEVELOPER_KEY = "AIzaSyDlfU5gNKiS8qEqW8yW7VHLNJO7h0vawNw";

        try {youtubeService = getService();}
        catch (GeneralSecurityException | IOException e) {throw new RuntimeException(e);}
    }

    public static YouTube.Search.List request;
    static {
        try {
            request = youtubeService.search()
                    .list("snippet");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                .build();
    }
}
