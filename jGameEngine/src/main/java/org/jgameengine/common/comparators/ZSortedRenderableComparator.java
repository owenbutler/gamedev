package org.jgameengine.common.comparators;

import org.jgameengine.renderer.Renderable;

import java.util.Comparator;

public class ZSortedRenderableComparator implements Comparator<Renderable> {

    public int compare(Renderable renderable, Renderable renderable1) {
        return new Float(renderable.getSortZ()).compareTo(renderable1.getSortZ());
    }
}
