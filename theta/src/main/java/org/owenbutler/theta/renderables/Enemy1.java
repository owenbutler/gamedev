package org.owenbutler.theta.renderables;

import org.owenbutler.theta.constants.AssetConstants;

public class Enemy1
        extends LargeEnemySupport {

    /**
     * create a new enemy.
     *
     * @param x        x position
     * @param y        y position
     * @param bossmode whether this is a bossmode enemy
     */
    public Enemy1(float x, float y, boolean bossmode) {
        super(AssetConstants.gfx_enemy1, x, y, 64, 64);
        setScreenClipRemove(false);

        setCollidable(true);
        setCollisionRadius(30);

        setSortZ(AssetConstants.Z_ENEMY);

        setVelY(20);

        if (bossmode) {
            initBossMode();
            health = 50;
            initFireTypes(4.0f,
                    10, 2,
                    10, 2,
                    10, 2,
                    20, 2);

        } else {
            health = 5;
            initFireTypes(4.0f,
                    5, 2,
                    5, 2,
                    5, 2,
                    8, 2);
        }

    }

}