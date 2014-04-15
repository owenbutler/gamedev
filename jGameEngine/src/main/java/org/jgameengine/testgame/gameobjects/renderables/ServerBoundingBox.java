package org.jgameengine.testgame.gameobjects.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;

/**
 * User: Owen Butler
 * Date: 11/04/2005
 * Time: 17:12:46
 */
public class ServerBoundingBox
        extends BaseDrawableGameObject {

    private Collidable owner;


    /**
     * create a new player bullet.
     *
     * @param x    x position
     * @param y    y position
     */
    public ServerBoundingBox(float x, float y) {
        super("spark/spark_01.png", x, y, 1, 1);
        setScreenClipRemove(true);
    }


    /**
     * Ghost our owner.
     */
    public void think() {
        baseDrawableThink();
        setX(owner.getX());
        setY(owner.getY());
        setWidth(owner.getCollisionWidth());
        setHeight(owner.getCollisionHeight());
    }


    public Collidable getConnectionOwner() {
        return owner;
    }


    public void setOwner(Collidable owner) {
        this.owner = owner;
    }
}
