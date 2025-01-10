/*
 * Vikotus - https://github.com/TheEntropyShard/Vikotus
 * Copyright (C) 2024-2025 TheEntropyShard
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package me.theentropyshard.vikotus;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.theentropyshard.vikotus.api.RecommendedVideo;
import me.theentropyshard.vikotus.api.VideoItem;
import me.theentropyshard.vikotus.gui.player.PlayerView;
import okhttp3.*;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VideoPlayer {
    public static void main(String[] args) {
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new Gson();
        Random random = new Random();

        RecommendedVideo video = null;

        try {
            List<RecommendedVideo> recommendedVideos = VideoPlayer.getRecommendedVideos(httpClient, gson);
            video = recommendedVideos.get(random.nextInt(recommendedVideos.size()));
        } catch (IOException e) {
            System.err.println("Could not get recommendations");
            e.printStackTrace();
        }

        String token = null;

        try {
            token = VideoPlayer.getAnonymousToken(httpClient, gson);
        } catch (IOException e) {
            System.err.println("Could not get anonymous token");
            e.printStackTrace();
        }

        if (token == null) {
            return;
        }

        VideoItem videoItem = null;

        try {
            videoItem = VideoPlayer.getVideoInfo(video.getOwnerId(), video.getVideoId(), token, httpClient, gson);
        } catch (IOException e) {
            System.err.println("Failed to get video discover");
            e.printStackTrace();
        }

        if (videoItem == null) {
            return;
        }

        JDialog.setDefaultLookAndFeelDecorated(true);
        JFrame.setDefaultLookAndFeelDecorated(true);
        FlatIntelliJLaf.setup();

        JFrame frame = new JFrame("Vikotus - " + videoItem.getTitle());
        PlayerView playerView = new PlayerView(videoItem.getTitle(), frame);

        playerView.setBorder(new EmptyBorder(5, 5, 5, 5));


        JPanel root = new JPanel(new BorderLayout());
        root.setPreferredSize(new Dimension(960, 540));

        root.add(playerView, BorderLayout.CENTER);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                playerView.getC().release();
                System.exit(0);
            }
        });
        frame.add(root, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        playerView.toggleOverlay(true);
        playerView.getC().mediaPlayer().media().play(videoItem.getFiles().getHls(), ":adaptive-logic=highest");
    }

    private static String getAnonymousToken(OkHttpClient httpClient, Gson gson) throws IOException {
        RequestBody tokenBody = new FormBody.Builder()
            .add("client_secret", "o557NLIkAErNhakXrQ7A")
            .add("client_id", "52461373")
            .add("scopes", "audio_anonymous,video_anonymous,photos_anonymous,profile_anonymous")
            .add("version", "1")
            .add("app_id", "6287487")
            .build();

        Request tokenRequest = new Request.Builder()
            .url("https://login.vk.com/?act=get_anonym_token")
            .post(tokenBody)
            .build();

        try (Response tokenResponse = httpClient.newCall(tokenRequest).execute()) {
            return gson.fromJson(tokenResponse.body().charStream(), JsonObject.class).getAsJsonObject("data").get("access_token").getAsString();
        } catch (IOException | NullPointerException e) {
            System.err.println("Could not get anonymous token");
            e.printStackTrace();
        }

        return null;
    }

    private static List<RecommendedVideo> getRecommendedVideos(OkHttpClient httpClient, Gson gson) throws IOException {
        List<RecommendedVideo> recommendedVideos = new ArrayList<>();

        RequestBody recommendationsBody = new FormBody.Builder()
            .add("al", "1")
            .add("silent_loading", "1")
            .build();

        Request recommendationsRequest = new Request.Builder()
            .post(recommendationsBody)
            .url("https://vkvideo.ru/")
            .build();

        try (Response recommendationsResponse = httpClient.newCall(recommendationsRequest).execute()) {
            Reader reader = recommendationsResponse.body().charStream();
            reader.skip(4);

            JsonArray arr = gson.fromJson(reader, JsonObject.class).getAsJsonArray("payload").get(1).getAsJsonArray();
            String unescaped = VideoPlayer.unescapeJson(arr.get(0).toString());
            String replaced = unescaped.substring(1, unescaped.length() - 1);
            JsonObject seObj = gson.fromJson(replaced, JsonObject.class);

            JsonArray videosArray = seObj.getAsJsonArray("videos");

            for (JsonElement element : videosArray) {
                recommendedVideos.add(RecommendedVideo.fromInfoArray(element.getAsJsonArray()));
            }
        }

        return recommendedVideos;
    }

    public static VideoItem getVideoInfo(int ownerId, int videoId, String token, OkHttpClient httpClient, Gson gson) throws IOException {
        RequestBody discoveryBody = new FormBody.Builder()
            .add("video_id", String.valueOf(videoId))
            .add("owner_id", String.valueOf(ownerId))
            .add("count", "1")
            .add("ref", "video_for_you")
            .add("access_token", token)
            .build();

        Request discoveryRequest = new Request.Builder()
            .url("https://api.vk.com/method/video.getVideoDiscover?v=5.245&client_id=6287487")
            .post(discoveryBody)
            .build();

        try (Response discoveryResponse = httpClient.newCall(discoveryRequest).execute()) {
            JsonObject jsonObject = gson.fromJson(discoveryResponse.body().string(), JsonObject.class);

            return gson.fromJson(jsonObject.getAsJsonObject("response").getAsJsonObject("current_video"), VideoItem.class);
        } catch (IOException e) {
            System.err.println("Failed to get video discover");
            e.printStackTrace();
        }

        return null;
    }

    public static String unescapeJson(String json) {
        return json.replace("\\\"", "\"")
            .replace("\\\\", "\\")
            .replace("\\/", "/")
            .replace("\\b", "\b")
            .replace("\\f", "\f")
            .replace("\\n", "\n")
            .replace("\\r", "\r")
            .replace("\\t", "\t");
    }
}
