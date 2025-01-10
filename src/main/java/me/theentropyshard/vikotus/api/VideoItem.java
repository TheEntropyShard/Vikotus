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

import com.google.gson.annotations.SerializedName;

public class VideoItem {
    private Files files;

    @SerializedName("comments")
    private int commentsCount;

    private long date;
    private String description;
    private int duration;
    private int width;
    private int height;
    private int videoId;

    @SerializedName("owner_id")
    private int ownerId;

    private String title;
    private int views;

    @SerializedName("local_views")
    private int localViews;

    public VideoItem() {

    }

    public Files getFiles() {
        return this.files;
    }

    public int getCommentsCount() {
        return this.commentsCount;
    }

    public long getDate() {
        return this.date;
    }

    public String getDescription() {
        return this.description;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getVideoId() {
        return this.videoId;
    }

    public int getOwnerId() {
        return this.ownerId;
    }

    public String getTitle() {
        return this.title;
    }

    public int getViews() {
        return this.views;
    }

    public int getLocalViews() {
        return this.localViews;
    }

    public static final class Files {
        @SerializedName("mp4_144")
        private String mp4Of144;

        @SerializedName("mp4_240")
        private String mp4Of240;

        @SerializedName("mp4_360")
        private String mp4Of360;

        @SerializedName("mp4_480")
        private String mp4Of480;

        @SerializedName("mp4_720")
        private String mp4Of720;

        @SerializedName("mp4_1080")
        private String mp4Of1080;

        @SerializedName("hls")
        private String hls;

        @SerializedName("dash_sep")
        private String dashOfsep;

        @SerializedName("dash_webm")
        private String dashOfwebm;

        @SerializedName("dash_webm_av1")
        private String dashOfWebmOfAV1;

        public Files() {

        }

        public String getMp4Of144() {
            return this.mp4Of144;
        }

        public String getMp4Of240() {
            return this.mp4Of240;
        }

        public String getMp4Of360() {
            return this.mp4Of360;
        }

        public String getMp4Of480() {
            return this.mp4Of480;
        }

        public String getMp4Of720() {
            return this.mp4Of720;
        }

        public String getMp4Of1080() {
            return this.mp4Of1080;
        }

        public String getHls() {
            return this.hls;
        }

        public String getDashOfsep() {
            return this.dashOfsep;
        }

        public String getDashOfwebm() {
            return this.dashOfwebm;
        }

        public String getDashOfWebmOfAV1() {
            return this.dashOfWebmOfAV1;
        }
    }
}
