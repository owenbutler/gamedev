package org.owenbutler.theta.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.theta.constants.AssetConstants;

public class EnemyBullet
        extends BaseDrawableGameObject {

    public EnemyBullet(float x, float y) {
        super(AssetConstants.gfx_enemyBullet, x, y, 8, 8);
        setScreenClipRemove(true);

        setCollidable(true);
        setCollisionRadius(4);

        setSortZ(AssetConstants.Z_ENEMY_BULLET);
    }

    public void think() {
        baseDrawableThink();
    }

    public void collision(Collidable otherBody) {
        if (otherBody instanceof Player) {
            removeSelf();
        } else if (otherBody instanceof PlayerHitWave) {
            turnIntoDust();
        }

    }

    private void turnIntoDust() {
        Debri debri = new Debri(getX(), getY());

        gameEngine.addGameObject(debri);

        removeSelf();
    }

}