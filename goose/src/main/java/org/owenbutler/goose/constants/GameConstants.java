package org.owenbutler.goose.constants;

public class GameConstants {

    public static final int PLAYER_WIDTH = 32;
    public static final int PLAYER_HEIGHT = 32;

    public static final float PLAYER_VELOCITY = 150.0f;

    public static final float PLAYER_FIRE_INTERVAL = 0.05f;
    public static final float PLAYER_BULLET_SPEED = -700.0f;

    public static final int WINGMEN = 4;
    public static final float WINGMAN_MOVE_CHECK_INTERVAL = 0.1f;
    public static final float WINGMAN_AI_CHECK_INTERVAL = 0.1f;

    public static final int WINGMAN_AI_X_RANGE_ABOVE_CHECK = 35;

    public static final float GAME_ENEMY_MOVEMENT_FADE_CHECK = 0.05f;
    public static float GAME_ENEMY_MOVEMENT_FADE_THRESHOLD = 0.2f;

    public static final float GAME_WINGMAN_SENSOR_RADIUS = 50.0f;
}
