package org.jgameengine.common.comparators;

import org.jgameengine.common.events.ScheduledEvent;

import java.util.Comparator;

public class ScheduledEventTimeComparator implements Comparator<ScheduledEvent> {

    public int compare(ScheduledEvent scheduledEvent1, ScheduledEvent scheduledEvent2) {
        if (scheduledEvent1.getTriggerTime() < scheduledEvent2.getTriggerTime()) {
            return -1;
        } else if (scheduledEvent1.getTriggerTime() > scheduledEvent2.getTriggerTime()) {
            return 1;
        }

        return 0;
    }
}
