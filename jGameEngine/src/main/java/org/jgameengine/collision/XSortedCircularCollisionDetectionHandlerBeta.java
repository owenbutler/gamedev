package org.jgameengine.collision;

import org.jgameengine.common.gameobjects.Collidable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class XSortedCircularCollisionDetectionHandlerBeta
        implements CollisionDetectionHandler {

    protected List<Collidable> currentCollideCollidables = new ArrayList<Collidable>();

    protected List<Collidable> collidablesAddedThisFrame = new ArrayList<Collidable>();
    protected List<Collidable> collidablesRemovedThisFrame = new ArrayList<Collidable>();

    private ListShellSort sorter = new ListShellSort();

    class ListShellSort {

        private int[] gaps = {1, 4, 10, 23, 57, 132, 301, 701, 1750};

        public final List<Collidable> sort(final List<Collidable> list) {
            for (int sizeIndex = this.gaps.length - 1; sizeIndex >= 0; --sizeIndex) {

                int gap = this.gaps[sizeIndex];
                for (int i = gap; i < list.size(); ++i) {
                    Collidable value = list.get(i);
                    int j;
                    for (j = i - gap; j >= 0
                            && (Float.compare(value.getX(), list.get(j).getX()) > 0); j -= gap) {
                        list.set(j + gap, list.get(j));
                    }
                    list.set(j + gap, value);
                }
            }
            return list;
        }

    }

    public void doCollisionDetection(Set gameObjects) {

        currentCollideCollidables.addAll(collidablesAddedThisFrame);
        collidablesAddedThisFrame.clear();

        currentCollideCollidables.removeAll(collidablesRemovedThisFrame);
        collidablesRemovedThisFrame.clear();

        sorter.sort(currentCollideCollidables);

        for (int i = 0; i < currentCollideCollidables.size(); ++i) {

            Collidable leftObject = currentCollideCollidables.get(i);
            int roamIndex = i + 1;

            float leftObjectRightBound = leftObject.getX() + leftObject.getCollisionRadius();

            while (roamIndex < currentCollideCollidables.size()) {
                Collidable rightObject = currentCollideCollidables.get(roamIndex);
                ++roamIndex;
                if (leftObject.ignore(rightObject)) {
                    continue;
                }

                float rightObjectLeftBound = rightObject.getX() - rightObject.getCollisionRadius();

                if (rightObjectLeftBound <= leftObjectRightBound) {
                    checkCollision(leftObject, rightObject);
                } else {
                    // we can break out here, as the objects are sorted by the left most x point
                    // therefore, once we go past the width of the current object in the list, we can't collide
                    break;
                }

            }
        }
    }

    public void collideableAdded(Collidable newCollidable) {

        if (newCollidable.collidable()) {
            collidablesAddedThisFrame.add(newCollidable);
        }
    }

    public void collideableRemoved(Collidable oldCollidable) {
        if (oldCollidable.collidable()) {
            collidablesRemovedThisFrame.add(oldCollidable);
        }
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