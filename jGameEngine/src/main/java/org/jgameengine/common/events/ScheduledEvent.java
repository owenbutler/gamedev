package org.jgameengine.common.events;

import org.apache.log4j.Logger;

public class ScheduledEvent {

    private static final Logger logger = Logger.getLogger(ScheduledEvent.class.getName());

    private Event event;

    private boolean repeat = false;
    private int numRepeat;

    private boolean loop = false;

    private float triggerTime;
    private float interval;

    private Object owner;

    public ScheduledEvent(Event event, float triggerTime) {
        this.event = event;
        this.triggerTime = triggerTime;
    }

    public void setRepeat(float interval, int numRepeat) {
        repeat = true;

        this.numRepeat = numRepeat;
        this.interval = interval;
    }

    public void setLoop(float interval) {
        loop = true;
        this.interval = interval;
    }

    public float getTriggerTime() {
        return triggerTime;
    }

    public void trigger() {
        event.trigger();

        if (repeat) {
            numRepeat--;
            if (logger.isTraceEnabled()) {
                logger.trace("trigger is repeating num repeat is now " + numRepeat);
            }
        }

        if (logger.isTraceEnabled()) {
            logger.trace("triggerTime = " + triggerTime);
            logger.trace("interval = " + interval);
        }

        triggerTime += interval;
    }

    public boolean needsDelete() {
        if (loop) {
            return false;
        }

        if (repeat) {
            if (numRepeat > 0) {
                return false;
            }
        }

        return true;
    }

    public Object getOwner() {
        return owner;
    }

    public void setOwner(Object owner) {
        this.owner = owner;
    }
}
