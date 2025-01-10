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

package me.theentropyshard.vikotus.api;

import com.google.gson.JsonArray;

public class RecommendedVideo {
    // Numbers below are indices in the video info array

    private int ownerId;                    // 0
    private int videoId;                    // 1
    private String largeThumbnailUrl;       // 2
    private String title;                   // 3
    private String formattedDuration;       // 5

    // This one is actually a JSON object, which has "duration" field inside which, in turn, has the actual value
    private String longDuration;            // 14
    private int videoLengthSeconds;         // 19
    private String smallThumbnailUrl;       // 28

    public RecommendedVideo() {

    }

    public RecommendedVideo(int ownerId, int videoId, String largeThumbnailUrl, String title, String formattedDuration,
                            String longDuration, int videoLengthSeconds, String smallThumbnailUrl) {

        this.ownerId = ownerId;
        this.videoId = videoId;
        this.largeThumbnailUrl = largeThumbnailUrl;
        this.title = title;
        this.formattedDuration = formattedDuration;
        this.longDuration = longDuration;
        this.videoLengthSeconds = videoLengthSeconds;
        this.smallThumbnailUrl = smallThumbnailUrl;
    }

    public static RecommendedVideo fromInfoArray(JsonArray videoInfoArray) {
        if (videoInfoArray.size() != 50) {
            throw new RuntimeException("Expected array length to be 50; actual: " + videoInfoArray.size());
        }

        return new RecommendedVideo(
            videoInfoArray.get(0).getAsInt(),
            videoInfoArray.get(1).getAsInt(),
            videoInfoArray.get(2).getAsString(),
            videoInfoArray.get(3).getAsString(),
            videoInfoArray.get(5).getAsString(),
            videoInfoArray.get(14).getAsJsonObject().get("duration").getAsString(),
            videoInfoArray.get(19).getAsInt(),
            videoInfoArray.get(28).getAsString()
        );
    }

    @Override
    public String toString() {
        return "RecommendedVideo{" +
            "ownerId=" + this.ownerId +
            ", videoId=" + this.videoId +
            ", largeThumbnailUrl='" + this.largeThumbnailUrl + '\'' +
            ", title='" + this.title + '\'' +
            ", formattedDuration='" + this.formattedDuration + '\'' +
            ", longDuration='" + this.longDuration + '\'' +
            ", videoLengthSeconds=" + this.videoLengthSeconds +
            ", smallThumbnailUrl='" + this.smallThumbnailUrl + '\'' +
            '}';
    }

    public int getOwnerId() {
        return this.ownerId;
    }

    public int getVideoId() {
        return this.videoId;
    }

    public String getLargeThumbnailUrl() {
        return this.largeThumbnailUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public String getFormattedDuration() {
        return this.formattedDuration;
    }

    public String getLongDuration() {
        return this.longDuration;
    }

    public int getVideoLengthSeconds() {
        return this.videoLengthSeconds;
    }

    public String getSmallThumbnailUrl() {
        return this.smallThumbnailUrl;
    }
}
