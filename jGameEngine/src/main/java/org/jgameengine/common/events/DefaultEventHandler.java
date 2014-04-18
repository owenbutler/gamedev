package org.jgameengine.common.events;

import org.apache.log4j.Logger;
import org.jgameengine.common.comparators.ScheduledEventTimeComparator;
import org.jgameengine.engine.Engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DefaultEventHandler
        implements EventHandler {

    private static final Logger logger = Logger.getLogger(DefaultEventHandler.class.getName());

    private Engine engine;

    private boolean currentlyTriggeringEvents = false;

    private List<ScheduledEvent> scheduledEvents = new LinkedList<>();

    private Map<Object, Set<ScheduledEvent>> ownerToEventsMap = new HashMap<>();

    private List<ScheduledEvent> deletedEvents = new ArrayList<>();

    private List<ScheduledEvent> eventsAddedThisFrame = new ArrayList<>();

    public void addEventIn(Object eventOwner, float wait, Event newEvent) {
        ScheduledEvent scheduledEvent = new ScheduledEvent(newEvent, engine.getCurrentTime() + wait);
        addEvent(eventOwner, scheduledEvent);
    }

    public void addEventInRepeat(Object eventOwner, float wait, float interval, int repeat, Event newEvent) {
        ScheduledEvent scheduledEvent = new ScheduledEvent(newEvent, engine.getCurrentTime() + wait);
        scheduledEvent.setRepeat(interval, repeat);
        addEvent(eventOwner, scheduledEvent);
    }

    public void addEventInLoop(Object eventOwner, float wait, float interval, Event newEvent) {
        ScheduledEvent scheduledEvent = new ScheduledEvent(newEvent, engine.getCurrentTime() + wait);
        scheduledEvent.setLoop(interval);
        addEvent(eventOwner, scheduledEvent);
    }

    private void addEvent(Object eventOwner, ScheduledEvent event) {

        Set<ScheduledEvent> ownerEventSet = ownerToEventsMap.get(eventOwner);
        if (ownerEventSet == null) {
            ownerEventSet = new HashSet<>();
            ownerToEventsMap.put(eventOwner, ownerEventSet);
        }
        ownerEventSet.add(event);
        event.setOwner(eventOwner);
        eventsAddedThisFrame.add(event);
    }

    public void processScheduledEvents() {

        scheduledEvents.addAll(eventsAddedThisFrame);
        eventsAddedThisFrame.clear();

        Collections.sort(scheduledEvents, new ScheduledEventTimeComparator());

        float time = engine.getCurrentTime();

        currentlyTriggeringEvents = true;

        for (ScheduledEvent event : scheduledEvents) {
            // check the time of the event
            // if it's in the future, we can stop iterating
            if (event.getTriggerTime() > time) {
                break;
            }

            if (event.getTriggerTime() <= time) {

                if (logger.isTraceEnabled()) {
                    logger.trace("triggering because: trigger time is " + event.getTriggerTime() + " time is " + time);
                }

                event.trigger();

                if (event.needsDelete()) {
                    Set<ScheduledEvent> events = ownerToEventsMap.get(event.getOwner());
                    if (events != null) {
                        events.remove(event);
                    }

                    deletedEvents.add(event);
                }
            }
        }

        currentlyTriggeringEvents = false;

        scheduledEvents.removeAll(deletedEvents);
        eventsAddedThisFrame.removeAll(deletedEvents);
        deletedEvents.clear();
    }

    public void removeEventsForOwner(Object eventOwner) {

        Set<ScheduledEvent> ownerEventSet = ownerToEventsMap.remove(eventOwner);
        if (ownerEventSet != null) {
            // we can't delete the list of events for the owner right now, because the owner might have
            // deleted themselves in an event that was fired while iterating over the scheduled events
            // if we delete straight from it, we would cause a ConcurrentModification
            // so we save the events associated with the owner in a list which is picked up and deleted after all events
            // are triggered
            if (currentlyTriggeringEvents) {
                deletedEvents.addAll(ownerEventSet);
            } else {
                scheduledEvents.removeAll(ownerEventSet);
                eventsAddedThisFrame.removeAll(ownerEventSet);
            }
        }
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
