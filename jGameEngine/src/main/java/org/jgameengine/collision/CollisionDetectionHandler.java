package org.jgameengine.collision;

import org.jgameengine.common.gameobjects.Collidable;

import java.util.Set;

public interface CollisionDetectionHandler {

    void doCollisionDetection(Set gameObjects);

    void collideableAdded(Collidable newCollidable);

    void collideableRemoved(Collidable oldCollidable);
}
