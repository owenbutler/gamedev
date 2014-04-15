package org.owenbutler.planetesimal.constants;

public class AssetConstants {

    public static String gfx_player = "gfx/player.png";
    public static String gfx_asteroid1 = "gfx/asteroid1.png";
    public static String gfx_smokePuff = "gfx/smokePuff.png";
    public static String gfx_playerBullet1 = "gfx/bullet1.png";
    public static String gfx_debri1 = "gfx/debri1.png";
    public static String gfx_smallExplosion = "gfx/smallExplosion-16x16-13frames.png";
    public static String gfx_particle = "gfx/particle.png";
    public static String gfx_largeExplosion = "gfx/transExp.png";

    public static String snd_playerFire = "sfx/playerFire.ogg";
    public static String snd_asteroidDamage = "sfx/asteroidDamage.ogg";
    public static String snd_asteroidDestroy = "sfx/asteroidDestroy.ogg";
    public static String snd_playerDead = "sfx/shipHittingAsteroid.ogg";

    public static void main(String[] args) {

        System.out.println((88 % 32) / 16);
        System.out.println((88 % 256) / 128);
        System.out.println((88 % 64) / 32);

    }

}
