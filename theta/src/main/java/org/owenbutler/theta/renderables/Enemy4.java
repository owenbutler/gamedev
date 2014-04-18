package org.owenbutler.theta.renderables;

import org.owenbutler.theta.constants.AssetConstants;

public class Enemy4
        extends LargeEnemySupport {

    public Enemy4(float x, float y, boolean bossmode) {
        super(AssetConstants.gfx_enemy4, x, y, 64, 64);
        setScreenClipRemove(false);

        setCollidable(true);
        setCollisionRadius(30);

        setSortZ(AssetConstants.Z_ENEMY);

        setVelY(20);

        if (bossmode) {
            initBossMode();
            health = 50;

            initFireTypes(3.0f,
                    22, 2.5f,
                    22, 2.5f,
                    22, 2.5f,
                    32, 2.5f);
        } else {
            health = 8;

            initFireTypes(1.5f,
                    10, 1.0f,
                    10, 1.0f,
                    10, 1.0f,
                    20, 1.0f);

        }
    }

}