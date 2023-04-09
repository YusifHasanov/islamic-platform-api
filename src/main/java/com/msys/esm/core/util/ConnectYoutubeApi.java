package com.msys.esm.core.util;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class ConnectYoutubeApi {
    public static final String DEVELOPER_KEY = "AIzaSyDlfU5gNKiS8qEqW8yW7VHLNJO7h0vawNw";

    public static final String CHANNEL_ID = "UC1B8clCxtmb-bzCDdxmVPDA";
    public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    public static YouTube youtubeService;

    static {
        try {
            youtubeService = getService();
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Define and execute the API request
    public static YouTube.Search.List request;

    static {
        try {
            request = youtubeService.search()
                    .list("snippet");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SearchListResponse getVideosJSON() throws IOException {
        return request.setKey(DEVELOPER_KEY)
                .setChannelId(CHANNEL_ID)
                .setOrder("date")
                .setType("video")
                .setMaxResults(50L)
                .execute();
    }

    public static SearchListResponse getPlayListJSON() throws IOException {
        return request.setKey(DEVELOPER_KEY)
                .setChannelId(CHANNEL_ID)
                .setOrder("date")
                .setType("playlist")
                .setMaxResults(50L)
                .execute();
    }

    private ConnectYoutubeApi() {
    }

    public static YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                .build();
    }
}
