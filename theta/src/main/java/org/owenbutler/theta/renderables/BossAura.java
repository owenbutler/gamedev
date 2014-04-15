package org.owenbutler.theta.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.owenbutler.theta.constants.AssetConstants;

public class BossAura
        extends BaseDrawableGameObject {

    protected LargeEnemySupport boss;

    private final int initialX = 128;
    private final int initialY = 128;

    /**
     * create a new debri.
     *
     * @param x    x position
     * @param y    y position
     * @param boss boss we are tracking
     */
    public BossAura(float x, float y, LargeEnemySupport boss) {
        super(AssetConstants.gfx_explosionSmoke, x, y, 128, 128);

        setCollidable(false);

        setSortZ(AssetConstants.Z_AURA);

        this.boss = boss;
    }

    /**
     * Run a frame of think time.
     */
    public void think() {
        x = boss.getX();
        y = boss.getY();
        baseDrawableThink();

        float time = gameEngine.getCurrentTime();

        float mod = (float) ((Math.sin(time * 4) + 1) * 20);

        setWidth(initialX + mod);
        setHeight(initialY + mod);
    }

}