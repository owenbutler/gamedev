package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.jgameengine.nineteenfourtysomething.constants.GameConstants;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.Player;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;

public class EnemyBullet
        extends BaseDrawableGameObject {

    public EnemyBullet(float x, float y) {
        super(AssetConstants.gfx_enemyBullet, x, y, 13, 13);
        setScreenClipRemove(true);

        setCollidable(true);

        removeSelfIn(10.0f);

        setSortZ(GameConstants.Z_ENEMY_BULLET);
    }


    public void think() {
        baseDrawableThink();
    }


    public void collision(Collidable otherBody) {
        if (otherBody instanceof Player) {
            removeSelf();
        }
    }
}