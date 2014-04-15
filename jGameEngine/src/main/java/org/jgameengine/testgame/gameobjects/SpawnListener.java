package org.jgameengine.testgame.gameobjects;

import org.apache.log4j.Logger;
import org.jgameengine.engine.Engine;
import org.jgameengine.testgame.network.packets.RequestSpawnPacket;
import org.jgameengine.input.MouseListener;

/**
 * Listens for mouse events to spawn the player in the gameworld.
 *
 * User: Owen Butler
 * Date: 4/08/2005
 * Time: 23:22:16
 */
public class SpawnListener implements MouseListener {

    private static final Logger logger = Logger.getLogger(SpawnListener.class.getName());

    private boolean listening = true;

    private Engine gameEngine;


    public SpawnListener(Engine gameEngine) {
        this.gameEngine = gameEngine;
    }


    public void mouseEvent(int x, int y) {
    }


    public void button0Down() {
        if (listening) {
            // spawn a player!
            listening = false;
            gameEngine.getNetworkDriver().sendPacketToAll(new RequestSpawnPacket());

        }
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
}
