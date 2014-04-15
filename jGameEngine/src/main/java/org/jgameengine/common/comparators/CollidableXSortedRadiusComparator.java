package org.jgameengine.common.comparators;

import org.jgameengine.common.gameobjects.Collidable;

import java.util.Comparator;

public class CollidableXSortedRadiusComparator implements Comparator<Collidable> {

    public int compare(Collidable o, Collidable o1) {

        return Float.compare(o1.getX(), o.getX());
    }
}
