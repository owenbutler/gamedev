package org.owenbutler.goose.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.goose.constants.AssetConstants;
import org.owenbutler.goose.constants.GameConstants;

public class WingmanSensor extends BaseDrawableGameObject {

    protected Wingman wingman;

    /**
     * constructor.
     * <p/>
     * The WingmanSensor reports wingman movement within a certain radius.
     * <p/>
     * This is used by the wingman to determine if evasive action is needed.
     *
     * @param myWingman the wingman we are sensing for
     */
    public WingmanSensor(Wingman myWingman) {
        super(AssetConstants.gfx_enemy1, 100, 100, 100, 100);
        setBlRender(false);

        setScreenClipRemove(false);

        setCollidable(true);
        setCollisionRadius(GameConstants.GAME_WINGMAN_SENSOR_RADIUS);

        wingman = myWingman;

    }

    /**
     * Run a frame of think time.
     */
    public void think() {
        x = wingman.getX();
        y = wingman.getY();

        baseDrawableThink();

    }

    /**
     * Collision with another object.
     *
     * @param otherBody the object we collided with
     */
    public void collision(Collidable otherBody) {
//        if (otherBody instanceof Player || otherBody instanceof Grazer) {
//            removeSelf();
//        }

        if (otherBody == wingman) {
            return;
        }

        if (otherBody instanceof Wingman || otherBody instanceof Player) {
            wingman.wingManMovementWithinRadius((BaseDrawableGameObject) otherBody);
        }
    }

}