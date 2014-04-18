package org.owenbutler.grazier.renderables;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.grazier.constants.AssetConstants;

public class EnemyDebri
        extends BaseDrawableGameObject {

    public EnemyDebri(float x, float y) {
        super(AssetConstants.gfx_enemyDebri, x, y, 16, 8);

        setScreenClipRemove(true);

        setCollidable(false);

        setRotate(RandomUtils.nextInt(500) - 250);

        setSortZ(AssetConstants.z_enemyDebri);
    }

    public void think() {
        baseDrawableThink();
    }

    public void collision(Collidable otherBody) {
        if (otherBody instanceof Enemy1) {
            removeSelf();
        }
    }

}