package org.owenbutler.theta.renderables;

import org.owenbutler.theta.constants.AssetConstants;

public class Enemy2
        extends LargeEnemySupport {

    public Enemy2(float x, float y, boolean bossmode) {
        super(AssetConstants.gfx_enemy2, x, y, 64, 64);
        setScreenClipRemove(false);

        setCollidable(true);
        setCollisionRadius(30);

        setSortZ(AssetConstants.Z_ENEMY);

        setVelY(20);

        if (bossmode) {
            initBossMode();
            health = 50;

            initFireTypes(3.0f,
                    10, 2.0f,
                    10, 2.0f,
                    10, 2.0f,
                    15, 2.0f);
        } else {
            health = 6;

            initFireTypes(3.0f,
                    6, 1.7f,
                    6, 1.7f,
                    6, 1.7f,
                    10, 1.7f);

        }

    }

}