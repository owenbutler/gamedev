package org.owenbutler.theta.renderables;

import org.owenbutler.theta.constants.AssetConstants;
import org.owenbutler.theta.constants.GameConstants;

/**
 * A small homing enemy.
 */
public class SmallHomingEnemy extends AbstractEnemyBase {

    protected Player player;

    public SmallHomingEnemy(float x, float y) {
        super(AssetConstants.gfx_smallHomingEnemy1, x, y, 16, 16);
        setScreenClipRemove(false);

        setCollidable(true);
        setCollisionRadius(8);

        setSortZ(AssetConstants.Z_ENEMY);

        player = (Player) gameEngine.getRegisteredGameService("player");

        health = 1;
    }

    public void think() {
        baseDrawableThink();

        trackPlayer();
    }

    private void trackPlayer() {

        float pVecX = player.getX();
        float pVecY = player.getY();

        setVelXY(getVelocityFacing(pVecX, pVecY, GameConstants.ENEMY_SMALL_SEEK_SPEED));

        lookat((int) pVecX, (int) pVecY);
    }

    protected void showDamage(PlayerBullet bulletThatHitMe) {

        DarkExplosionSmoke smoke = new DarkExplosionSmoke(x, y, true);
        smoke.setWidth(32);
        smoke.setHeight(32);
        smoke.setScale(-200, -200);
        smoke.setFadeAndRemove(1.0f);

        gameEngine.addGameObject(smoke);
    }
}
