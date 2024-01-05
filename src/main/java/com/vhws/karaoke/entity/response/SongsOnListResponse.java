package com.vhws.karaoke.entity.response;

public record SongsOnListResponse(
        String musicId,
        String title,
        String musicGenre,
        String artist,
        String link,
        int runningTime,
        String customerName,
        String checkId
) {
}
