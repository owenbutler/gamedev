package org.jgameengine.testgame.gameobjects.renderables;

import org.jgameengine.common.gameobjects.BaseControllableDrawableGameObject;
import org.jgameengine.input.MouseListener;
import org.jgameengine.testgame.network.packets.PlayerFirePacket;

/**
 * User: Owen Butler
 * Date: 5/04/2005
 * Time: 21:26:56
 */
public class ControllablePlayerShip
        extends BaseControllableDrawableGameObject
        implements MouseListener {

//    private static final Logger logger = Logger.getLogger(ControllablePlayerShip.class.getName());

    private static final float SHIPVELOCITY = 200.0f;

    private static final float WIDTH = 80.0f;
    private static final float HEIGHT = 80.0f;

    private int facingX;

    private int facingY;


    public ControllablePlayerShip() {
        super("ship1.png", 0.0f, 0.0f, WIDTH, HEIGHT);

        setXY(512, 384);

        setMoveVelX(SHIPVELOCITY);
        setMoveVelY(SHIPVELOCITY);

        setScreenClipRemove(false);

        setCollidable(true);
        setCollisionRadius(10.0f);        
    }


    /**
     * Run a frame of think time.
     */
    public void think() {
        baseDrawableThink();

        lookat(facingX, facingY);
    }


    public void mouseEvent(int x, int y) {
        facingX = x;
        facingY = y;
    }


    /**
     * Fire!
     */
    private void fire() {
        float[] bulletVel = getVelocityFacing(facingX, facingY, 300.0f);

        PlayerBullet bullet = new PlayerBullet(this.x, this.y, bulletVel[0], bulletVel[1]);
        bullet.setRotation(surface.getRotation());
        bullet.setOwner(this);
        gameEngine.addGameObject(bullet);

        gameEngine.getNetworkDriver().sendPacketToAll(new PlayerFirePacket(0, this.x, this.y, facingX, facingY));

        // play a sound
//        gameEngine.getAudioManager().loadOrGetSample("audio/sfx/ding.wav").playSample();

        gameEngine.addGameObject(new TestAnimation(this.x, this.y));
    }


    public void button0Down() {
        fire();
    }


    public void button0Up() {
    }


    public void button1Down() {
    }


    public void button1Up() {
    }


    public void button2Down() {
    }


    public void button2Up() {
    }


    public int getFacingX() {
        return facingX;
    }


    public void setFacingX(int facingX) {
        this.facingX = facingX;
    }


    public int getFacingY() {
        return facingY;
    }


    public void setFacingY(int facingY) {
        this.facingY = facingY;
    }
}
