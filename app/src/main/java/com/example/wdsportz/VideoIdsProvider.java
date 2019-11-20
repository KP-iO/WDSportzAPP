package com.example.wdsportz;

import java.util.Random;

/**
 * Created by khrishawn
 */
class VideoIdsProvider {private static String[] videoIds = {"6JYIGclVQdw", "LvetJ9U_tVY", "S0Q4gqBUs7c", "zOa-rSM4nms"};
    private static String[] liveVideoIds = {"Xs5ArTX1KxA"};
    private static Random random = new Random();

    public static String getNextVideoId() {
        return videoIds[random.nextInt(videoIds.length)];
    }

    public static String getNextLiveVideoId() {
        return liveVideoIds[random.nextInt(liveVideoIds.length)];
    }
}
