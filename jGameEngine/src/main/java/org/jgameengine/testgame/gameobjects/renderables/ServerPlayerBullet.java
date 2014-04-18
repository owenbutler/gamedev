package org.jgameengine.testgame.gameobjects.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.jgameengine.testgame.network.TestGameServerPacketHandler;

public class ServerPlayerBullet
        extends BaseDrawableGameObject {

    private Integer owner;

    private static final float COLLISION_RADIUS = 8.0f;
    private static final float SIZE = COLLISION_RADIUS * 2.0f;

    public ServerPlayerBullet(float x, float y, float xVel, float yVel) {
        super("sys/boundingCircle.png", x, y, SIZE, SIZE);

        setVelX(xVel);
        setVelY(yVel);

        setScreenClipRemove(true);

        setCollidable(true);
        setCollisionRadius(COLLISION_RADIUS);
    }


    public void think() {
        baseDrawableThink();
    }

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
