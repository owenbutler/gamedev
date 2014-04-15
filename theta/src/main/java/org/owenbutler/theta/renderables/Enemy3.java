package org.owenbutler.theta.renderables;

import org.owenbutler.theta.constants.AssetConstants;

public class Enemy3
        extends LargeEnemySupport {

    /**
     * create a new enemy.
     *
     * @param x        x position
     * @param y        y position
     * @param bossmode whether this is a bossmode enemy
     */
    public Enemy3(float x, float y, boolean bossmode) {
        super(AssetConstants.gfx_enemy3, x, y, 64, 64);
        setScreenClipRemove(false);

        setCollidable(true);
        setCollisionRadius(30);

        setSortZ(AssetConstants.Z_ENEMY);

        setVelY(20);

        if (bossmode) {
            initBossMode();
            health = 50;

            initFireTypes(2.5f,
                    19, 2.3f,
                    19, 2.3f,
                    19, 2.3f,
                    29, 2.3f);
        } else {
            health = 7;

            initFireTypes(2.5f,
                    8, 1.3f,
                    8, 1.3f,
                    8, 1.3f,
                    15, 1.3f);

        }

    }

}