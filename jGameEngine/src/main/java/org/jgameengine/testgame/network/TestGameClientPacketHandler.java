package org.jgameengine.testgame.network;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.jgameengine.engine.Engine;
import org.jgameengine.common.events.Event;
import org.jgameengine.network.NetworkPacketHandler;
import org.jgameengine.testgame.gameobjects.renderables.ControllablePlayerShip;
import org.jgameengine.testgame.gameobjects.renderables.PlayerBullet;
import org.jgameengine.testgame.gameobjects.renderables.ClientDronePlayerShip;
import org.jgameengine.testgame.network.packets.JoinResponsePacket;
import org.jgameengine.testgame.network.packets.PlayerLocationUpdatePacket;
import org.jgameengine.testgame.network.packets.PlayerSpawnPacket;
import org.jgameengine.testgame.network.packets.WorldStatePacket;
import org.jgameengine.testgame.network.packets.WorldStatePlayerObject;
import org.jgameengine.testgame.network.packets.MissilePacket;
import org.jgameengine.testgame.network.packets.ClientLeftGamePacket;
import org.jgameengine.testgame.network.packets.PlayerDamagePacket;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

/**
 * Handles client packets.
 * <p/>
 * Triggers game logic when appropriate.
 * <p/>
 * User: Owen Butler
 * Date: 31/05/2005
 * Time: 17:28:45
 */
public class TestGameClientPacketHandler
        implements NetworkPacketHandler {

    private static final Logger logger = Logger.getLogger(TestGameClientPacketHandler.class.getName());

    /**
     * game engine.
     */
    private Engine engine;

    private ControllablePlayerShip ship;

    private int connectionId;

    /**
     * How often this client updates the server with it's position.
     * <p/>
     * eg.  0.02f is equal to 50 times per second
     * eg.  0.1f  is equal to 10 times per second
     */
    private float serverUpdateRate;

    /**
     * map used to associate connection id to "ship".
     */
    private Map<Integer, ClientDronePlayerShip> connectionIdToShip = new HashMap<Integer, ClientDronePlayerShip>();


    /**
     * initialise the packet handler.
     *
     * @param engine game engine we are running in
     */
    public void initPacketHandler(Engine engine) {
        this.engine = engine;
    }


    /**
     * handle a packet.
     *
     * @param connectionId connection id the packet came from
     * @param packet       the packet
     */
    public void handlePacket(Integer connectionId, Object packet) {

        if (logger.isTraceEnabled()) {
            logger.trace(ToStringBuilder.reflectionToString(packet));
        }

        if (packet instanceof WorldStatePacket) {
            handleWorldState((WorldStatePacket) packet);
        } else if (packet instanceof PlayerSpawnPacket) {
            handlePlayerSpawn((PlayerSpawnPacket) packet);
        } else if (packet instanceof JoinResponsePacket) {
            handleJoinResponsePacket((JoinResponsePacket) packet);
        } else if (packet instanceof MissilePacket) {
            handleMissile((MissilePacket) packet);
        } else if (packet instanceof ClientLeftGamePacket) {
            handleClientLeftGame((ClientLeftGamePacket) packet);
        } else if (packet instanceof PlayerDamagePacket) {
            handlePlayerDamage((PlayerDamagePacket) packet);
        }
//		else if (packet instanceof PlayerFirePacket)
//		{
//			handlePlayerFire(connectionId, (PlayerFirePacket) packet);
//		}
//		else if (packet instanceof JoinPacket)
//		{
//			handleJoin(connectionId, (JoinPacket) packet);
//		}
//		else if (packet instanceof RequestSpawnPacket)
//		{
//			handleRequestSpawn(connectionId, (RequestSpawnPacket) packet);
//		}

    }


    /**
     * The server has notified us that another client has left the game.
     *
     * @param clientLeftGamePacket the packet sent from the server to indicate a client has left the game
     */
    private void handleClientLeftGame(ClientLeftGamePacket clientLeftGamePacket) {

        // try to find the drone render that corresponds to this client and remove it from
        // the game world
        ClientDronePlayerShip removeDrone = connectionIdToShip.get(clientLeftGamePacket.getId());
        if (removeDrone != null) {
            engine.removeGameObject(removeDrone);
        }
    }


    /**
     * Handle a disconnection by a client.
     */
    public void handleDisconnection(Integer connectionId) {
        throw new RuntimeException("not implemented");
    }


    private void handleJoinResponsePacket(JoinResponsePacket packet) {
        connectionId = packet.getConnectionId();

        // the join packet has the basic world state, process it
        List worldState = packet.getObjects();
        for (Object aWorldState : worldState) {
            WorldStatePlayerObject playerObject = (WorldStatePlayerObject) aWorldState;

            // if it's us, ignore
            if (playerObject.getId() == connectionId) {
                continue;
            }

            ClientDronePlayerShip drone = new ClientDronePlayerShip();
            connectionIdToShip.put(playerObject.getId(), drone);
            drone.setXY(playerObject.getPositionX(), playerObject.getPositionY());
            drone.lookat(playerObject.getDirectionX(), playerObject.getDirectionY());

            engine.addGameObject(drone);
        }
    }


    private void handleWorldState(WorldStatePacket worldStatePacket) {
        // iterate over the items in the world state
        List worldState = worldStatePacket.getWorldState();
        for (Object aWorldState : worldState) {
            WorldStatePlayerObject playerObject = (WorldStatePlayerObject) aWorldState;

            // if it's us, ignore
            if (playerObject.getId() == connectionId) {
                continue;
            }

            ClientDronePlayerShip drone = connectionIdToShip.get(playerObject.getId());
            if (drone == null) {
                logger.warn("got worldState for a connection id that doesn't exist : " + playerObject.getId());
                continue;
            }
            drone.setXY(playerObject.getPositionX(), playerObject.getPositionY());
            drone.lookat(playerObject.getDirectionX(), playerObject.getDirectionY());
        }
    }


    /**
     * A missile has been fired.
     *
     * @param missilePacket the missile
     */
    private void handleMissile(MissilePacket missilePacket) {
        // check whether the missile is ours, and ignore if so
        if (missilePacket.getId() == connectionId) {
            return;
        }

        // the missile is someone elses, lets insert it into the world and start rendering it
        PlayerBullet bullet = new PlayerBullet(missilePacket.getPositionX(), missilePacket.getPositionY(), missilePacket.getVelocityX(), missilePacket.getVelocityY());
        bullet.lookat((int) missilePacket.getDirectionX(), (int) missilePacket.getDirectionY());
        engine.addGameObject(bullet);

        // set the owner of the missile so collisions work properly
        ClientDronePlayerShip drone = connectionIdToShip.get(missilePacket.getId());
        bullet.setOwner(drone);
    }


    private void handlePlayerSpawn(PlayerSpawnPacket playerSpawnPacket) {
        if (playerSpawnPacket.getId() == connectionId) {
            ship = new ControllablePlayerShip();
            ship.setXY(playerSpawnPacket.getPositionX(), playerSpawnPacket.getPositionY());
            engine.addGameObject(ship);
            engine.getInputManager().addMouseListener(ship);
            engine.getInputManager().addMovementListener(ship);

            // add a trigger to update the server with the clients position
            engine.getEventHandler().addEventInLoop(this, serverUpdateRate, serverUpdateRate, new Event() {
                public void trigger() {
                    updateServerWithClientPosition();
                }
            });
        } else {//  someone else spawned?

            ClientDronePlayerShip otherShip = new ClientDronePlayerShip();
            otherShip.setXY(playerSpawnPacket.getPositionX(), playerSpawnPacket.getPositionY());
            otherShip.lookat((int) playerSpawnPacket.getDirectionX(), (int) playerSpawnPacket.getDirectionY());

            connectionIdToShip.put(playerSpawnPacket.getId(), otherShip);
            engine.addGameObject(otherShip);
        }
    }

    private void handlePlayerDamage(PlayerDamagePacket playerDamagePacket) {
        if (playerDamagePacket.getId() == connectionId) {
            logger.debug("WE GOT HIT!!");
        }
    }



    private void updateServerWithClientPosition() {

        if (logger.isTraceEnabled()) {
            logger.trace("updating server with client position");
        }

        engine.getNetworkDriver().sendPacketToAll(
                new PlayerLocationUpdatePacket(connectionId, ship.getX(), ship.getY(),
                        ship.getFacingX(), ship.getFacingY()));
    }


    public float getServerUpdateRate() {
        return serverUpdateRate;
    }


    public void setServerUpdateRate(float serverUpdateRate) {
        this.serverUpdateRate = serverUpdateRate;
    }
}