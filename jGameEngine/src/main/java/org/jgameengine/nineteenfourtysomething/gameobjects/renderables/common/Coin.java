package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.Player;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;

public class Coin
        extends BaseDrawableGameObject {

    public static final int TYPE_GREEN = 0;
    public static final int TYPE_GOLD = 6;
    public static final int TYPE_SILVER = 12;
    public static final int TYPE_RED = 18;

    protected Player player;

    public Coin(float x, float y, int type) {
        super(AssetConstants.gfx_coins, x, y, 16, 16);
        setScreenClipRemove(false);

        getSurface().enableAnimation(null, 4, 8);

        startFrame = type;
        frame = startFrame;
        getSurface().selectAnimationFrame(frame);
        endFrame = type + 5;

        // set a looping event animate us
        gameEngine.getEventHandler().addEventInLoop(this, 0.1f, 0.1f, new Event() {
            public void trigger() {
                incrementAnimation();
            }
        });

        setCollidable(true);
        setCollisionRadius(10);

        player = (Player) gameEngine.getRegisteredGameService("player");

        setInitialRandomVelocity();

        // set a looping event to track the player
        gameEngine.getEventHandler().addEventInLoop(this, 0.2f, 0.1f, new Event() {
            public void trigger() {
                trackPlayer();
            }
        });

    }


    public void setInitialRandomVelocity() {
        velX = RandomUtils.nextInt(500) - 250;
        velY = RandomUtils.nextInt(500) - 250;
    }


    protected void trackPlayer() {

        setVelXY(getVelocityFacing(player.getX(), player.getY(), 330.f));
    }


    public void think() {
        baseDrawableThink();
    }


    public void collision(Collidable otherBody) {
        if (otherBody instanceof Player) {
            removeSelf();
        }

    }
}