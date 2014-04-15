package org.jgameengine.nineteenfourtysomething.constants;

public class PlayerConstants {

    /**
     * The speed of the player ship.
     */
    public static final float SHIPVELOCITY = 240.0f;

    /**
     * The time between shots of the ships guns.
     */
    public static float FIRE_INTERVAL_BULLET = 0.1f;
    public static float FIRE_INTERVAL_WIDE = 0.001f;
    public static final float FIRE_INTERVAL_MISSILE = 0.8f;

    public static final float FIRE_POWERUP_BULLET = 0.0018f;
    public static final float FIRE_POWERUP_WIDE = 0.0029f;
    public static final float FIRE_POWERUP_MISSILE = 0.014f;

    /**
     * The width of the player ship.
     */
    public static final float WIDTH = 64.0f;

    /**
     * The height of the player ship.
     */
    public static final float HEIGHT = 64.0f;

    /**
     * The radius of the players own collision detection.
     */
    public static final float COLLISION_RADIUS = 10.0f;

    /**
     * Starting health of the player.
     */
    public static final int PLAYER_START_HEALTH = 5;

    public static final int PLAYER_START_X = 320;
    public static final int PLAYER_START_Y = 430;

    /**
     * Speed of players bullets and missiles.
     */
    public static final float PLAYER_BULLET_SPEED = 700.0f;
    public static final float PLAYER_MISSILE_SPEED = 500.0f;
    public static final float PLAYER_BULLET_SPEED_SMALL = 500.0f;
}
