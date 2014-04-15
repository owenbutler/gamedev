package org.jgameengine.testgame.gameobjects.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.jgameengine.testgame.network.TestGameServerPacketHandler;
import org.apache.log4j.Logger;

/**
 * User: Owen Butler
 * Date: 11/04/2005
 * Time: 17:12:46
 */
public class ServerPlayerBullet
        extends BaseDrawableGameObject {

    private static final Logger logger = Logger.getLogger(ServerPlayerBullet.class);

    private Integer owner;

    private static final float COLLISION_RADIUS = 8.0f;
    private static final float SIZE = COLLISION_RADIUS * 2.0f;
    
    /**
     * create a new server player bullet.
     *
     * @param x    x position
     * @param y    y position
     * @param xVel x velocity
     * @param yVel y velocity
     */
    public ServerPlayerBullet(float x, float y, float xVel, float yVel) {
        super("sys/boundingCircle.png", x, y, SIZE, SIZE);

        setVelX(xVel);
        setVelY(yVel);

        setScreenClipRemove(true);

        setCollidable(true);
        setCollisionRadius(COLLISION_RADIUS);
    }


    /**
     * Run a frame of think time.
     */
    public void think() {
        baseDrawableThink();
    }

    /**
     * Bullet collided with something.
     *
     * If we collided into a player, then take appropriate action.
     *
     * @param otherBody the object we collided into
     */
    public void collision(Collidable otherBody) {

        if (otherBody instanceof ServerDronePlayerShip) {
            ServerDronePlayerShip ship = (ServerDronePlayerShip) otherBody;
            if (ship.getConnectionOwner().equals(getConnectionOwner())) {
                return;
            }
            
            // we collided with another player!  Do something!
            TestGameServerPacketHandler gameLogic = (TestGameServerPacketHandler) gameEngine.getGameLogicHandler();
            gameLogic.missileHitPlayer(this, ship);
            
            // remove ourselves, our job here is done
            removeSelf();
        }
    }


    public Integer getConnectionOwner() {
        return owner;
    }


    public void setOwner(Integer owner) {
        this.owner = owner;
    }
}
