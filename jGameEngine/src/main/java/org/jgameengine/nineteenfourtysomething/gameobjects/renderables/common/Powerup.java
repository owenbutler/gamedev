package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common;

import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.Collidable;
import org.jgameengine.nineteenfourtysomething.constants.GameConstants;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.Player;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;

/**
 * Powerup that spawns and slowly drifts to the bottom of the screen.
 *
 * The powerup upgrades one of 3 things:  Missles, straight shot or wide shot.
 *
 * @author Owen Butler
 */
public class Powerup
        extends BaseDrawableGameObject194x {

    public static final int TYPE_MISSILE = 0;
    public static final int TYPE_SHOT_STRAIGHT = 1;
    public static final int TYPE_SHOT_WIDE = 2;

    private int type;

    public Powerup(float x, float y, int newType) {
        super(AssetConstants.gfx_powerups, x, y, 32, 32);
        setScreenClipRemove(false);

        getSurface().enableAnimation(null, 4, 4);

        type = newType;
        setFrameFromType();

        setCollidable(true);
        setCollisionRadius(16);

        setVelY(10);

        gameEngine.getEventHandler().addEventInLoop(this, GameConstants.POWERUP_SWITCH_RATE, GameConstants.POWERUP_SWITCH_RATE, new Event() {
            public void trigger() {
                switchPowerup();
            }
        });
    }


    private void setFrameFromType() {
        int frame;
        switch (type) {
            case TYPE_MISSILE:
                frame = 10;
                break;
            case TYPE_SHOT_STRAIGHT:
                frame = 2;
                break;
            case TYPE_SHOT_WIDE:
                frame = 3;
                break;
            default:
                frame = 13;
                break;
        }

        getSurface().selectAnimationFrame(frame);
    }


    private void switchPowerup() {

        type++;
        type %= 3;

        setFrameFromType();
    }


    /**
     * Run a frame of think time.
     */
    public void think() {
        baseDrawableThink();
    }

    public void setTypeHealth() {
        getSurface().selectAnimationFrame(13);
    }


    public int getType() {
        return type;
    }


    public void setType(int type) {
        this.type = type;
    }


    /**
     * Collision with another object.
     *
     * @param otherBody the object we collided with
     */
    public void collision(Collidable otherBody) {
        if (otherBody instanceof Player) {

            Player player = (Player) otherBody;
            player.upgrade(this);
            removeSelf();
        }
    }
}
