package org.jgameengine.common.gameobjects;

public interface Collidable {

    float getX();

    float getY();

    float getCollisionWidth();

    float getCollisionHeight();

    float getCollisionRadius();

    float getCollisionRadiusSquared();

    boolean collidable();

    void collision(Collidable otherBody);

    boolean ignore(Collidable otherBody);

}
