package org.jgameengine.common.events;

public interface EventHandler {

    void addEventIn(Object eventOwner, float wait, Event newEvent);

    void addEventInRepeat(Object eventOwner, float wait, float interval, int repeat, Event newEvent);

    void addEventInLoop(Object eventOwner, float wait, float interval, Event newEvent);

    void processScheduledEvents();

    void removeEventsForOwner(Object eventOwner);
}
