package org.owenbutler.bcatch.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.bcatch.constants.AssetConstants;

public class ReboundBullet
        extends BaseDrawableGameObject {

    int score;

    public ReboundBullet(float x, float y) {
        super(AssetConstants.gfx_reboundBullet, x, y, 8, 8);
        setScreenClipRemove(true);

        setCollidable(true);
        setCollisionRadius(4);

    }

    public void think() {
        baseDrawableThink();
    }

    public void collision(Collidable otherBody) {
        if (otherBody instanceof Enemy) {
            removeSelf();
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}