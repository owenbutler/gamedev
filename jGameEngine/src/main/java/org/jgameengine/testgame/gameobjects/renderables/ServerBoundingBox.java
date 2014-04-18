package org.jgameengine.testgame.gameobjects.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;

public class ServerBoundingBox
        extends BaseDrawableGameObject {

    private Collidable owner;


    public ServerBoundingBox(float x, float y) {
        super("spark/spark_01.png", x, y, 1, 1);
        setScreenClipRemove(true);
    }


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
