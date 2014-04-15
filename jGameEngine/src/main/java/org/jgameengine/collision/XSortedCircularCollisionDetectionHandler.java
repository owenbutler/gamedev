package org.jgameengine.collision;

import org.jgameengine.common.comparators.CollidableXSortedRadiusComparator;
import org.jgameengine.common.gameobjects.Collidable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class XSortedCircularCollisionDetectionHandler
        implements CollisionDetectionHandler {

    private CollidableXSortedRadiusComparator comparator = new CollidableXSortedRadiusComparator();

    public void doCollisionDetection(Set gameObjects) {
        List<Collidable> collidables = new ArrayList<Collidable>(gameObjects.size());

        for (Object iGameObject : gameObjects) {
            if (iGameObject instanceof Collidable) {
                Collidable collidable = (Collidable) iGameObject;
                if (collidable.collidable()) {
                    collidables.add(collidable);
                }
            }
        }

        Collections.sort(collidables, comparator);

        for (int i = 0; i < collidables.size(); ++i) {
            Collidable leftObject = collidables.get(i);
            int roamIndex = i + 1;

            while (roamIndex < collidables.size()) {
                Collidable rightObject = collidables.get(roamIndex);

                float leftObjectRightBound = leftObject.getX() + leftObject.getCollisionRadius();
                float rightObjectLeftBound = rightObject.getX() - rightObject.getCollisionRadius();

                if (rightObjectLeftBound <= leftObjectRightBound) {
                    if (!leftObject.ignore(rightObject)) {
                        checkCollision(leftObject, rightObject);
                    }
                } else {
                    // we can break out here, as the objects are sorted by the left most x point
                    // therefore, once we go past the width of the current object in the list, we can't collide
                    break;
                }

                roamIndex++;
            }
        }
    }

    public void collideableAdded(Collidable newCollidable) {
    }

    public void collideableRemoved(Collidable oldCollidable) {
    }

    private void checkCollision(Collidable leftObject, Collidable rightObject) {
        float x, y;

        x = leftObject.getX() - rightObject.getX();
        y = leftObject.getY() - rightObject.getY();

        float distance = x * x + y * y;

        if (distance < leftObject.getCollisionRadiusSquared() + rightObject.getCollisionRadiusSquared()) {
            rightObject.collision(leftObject);
            leftObject.collision(rightObject);
        }

    }

}
