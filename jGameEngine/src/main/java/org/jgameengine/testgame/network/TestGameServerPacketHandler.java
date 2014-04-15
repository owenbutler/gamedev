package org.jgameengine.testgame.network;

import org.jgameengine.engine.Engine;
import org.jgameengine.common.events.Event;
import org.jgameengine.network.NetworkPacketHandler;
import org.jgameengine.testgame.gameobjects.renderables.ServerDronePlayerShip;
import org.jgameengine.testgame.gameobjects.renderables.ServerPlayerBullet;
import org.jgameengine.testgame.network.packets.JoinPacket;
import org.jgameengine.testgame.network.packets.JoinResponsePacket;
import org.jgameengine.testgame.network.packets.PlayerFirePacket;
import org.jgameengine.testgame.network.packets.PlayerLocationUpdatePacket;
import org.jgameengine.testgame.network.packets.RequestSpawnPacket;
import org.jgameengine.testgame.network.packets.PlayerSpawnPacket;
import org.jgameengine.testgame.network.packets.MissilePacket;
import org.jgameengine.testgame.network.packets.WorldStatePacket;
import org.jgameengine.testgame.network.packets.WorldStatePlayerObject;
import org.jgameengine.testgame.network.packets.ClientLeftGamePacket;
import org.jgameengine.testgame.network.packets.PlayerDamagePacket;
import org.apache.log4j.Logger;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Server side packet handler for the testgame game.
 *
 * The class routes logic steps to a "game" logic controller.
 *
 * User: Owen Butler
 * Date: 1/05/2005
 * Time: 23:14:14
 */
public class TestGameServerPacketHandler
        implements NetworkPacketHandler {

    private static final Logger logger = Logger.getLogger(TestGameServerPacketHandler.class.getName());

    /**
     * game engine.
     */
    private Engine engine;

    /**
     * map used to associated connection id to name.
     */
    private Map<Integer, String> connectionIdToName = new HashMap<Integer, String>();

    /**
     * map used to associate connection id to "ship".
     */
    private Map<Integer, ServerDronePlayerShip> connectionIdToShip = new HashMap<Integer, ServerDronePlayerShip>();

    /**
     * map used to associate a "ship" to a connection id.
     */
    private Map<ServerDronePlayerShip, Integer> shipToConnectionId = new HashMap<ServerDronePlayerShip, Integer>();

    /**
     * How often clients are updated of world state expressed as floating point seconds.
     *
     * eg.  0.02f is equal to 50 times per second
     * eg.  0.1f  is equal to 10 times per second
     */
    private float clientUpdateRate;

    /**
     * Damage per missile.
     */
    private static final float MISSILE_DAMAGE = 1.0f;


    /**
     * initialise the packet handler.
     *
     * @param engine game engine we are running in
     */
    public void initPacketHandler(Engine engine) {
        this.engine = engine;

        // add a looping event which sends the client state to all clients every xx ms
        this.engine.getEventHandler().addEventInLoop(this, getClientUpdateRate(), getClientUpdateRate(), new Event() {
            public void trigger() {
                updateClientsWithWorldState();
            }
        });
    }


    /**
     * called every xx ms to update clients with the state of the world.
     * <p/>
     * Loops through all the drones in the world, creating a WorldStatePacket.
     */
    private void updateClientsWithWorldState() {
        // if we have no clients connected, don't bother sending the world state
        if (connectionIdToName.size() == 0) {
            return;
        }

        List<WorldStatePlayerObject> worldList = new ArrayList<WorldStatePlayerObject>();

        // create the list of player objects in the world
        for (Map.Entry<Integer, ServerDronePlayerShip> entry : connectionIdToShip.entrySet()) {
            ServerDronePlayerShip droneShip = entry.getValue();
            Integer connectionId = entry.getKey();

            WorldStatePlayerObject playerObject = new WorldStatePlayerObject(connectionId,
                    droneShip.getX(), droneShip.getY(),
                    droneShip.getFacingX(), droneShip.getFacingY());

            worldList.add(playerObject);
        }

        // create a world state packet with the list of objects in the world
        WorldStatePacket worldState = new WorldStatePacket(worldList);

        // send the world state packet to all clients
        engine.getNetworkDriver().sendPacketToAll(worldState);
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

        if (packet instanceof PlayerLocationUpdatePacket) {
            handlePlayerLocationUpdate(connectionId, (PlayerLocationUpdatePacket) packet);
        } else if (packet instanceof PlayerFirePacket) {
            handlePlayerFire(connectionId, (PlayerFirePacket) packet);
        } else if (packet instanceof JoinPacket) {
            handleJoin(connectionId, (JoinPacket) packet);
        } else if (packet instanceof RequestSpawnPacket) {
            handleRequestSpawn(connectionId, (RequestSpawnPacket) packet);
        }
    }


    /**
     * Handle a disconnection by a client.
     */
    public void handleDisconnection(Integer connectionId) {

        // A client disconnected so we should remove it from the server representation of the world
        // this stops it rendering on the server, and stops it from getting sent out
        ServerDronePlayerShip ship = connectionIdToShip.remove(connectionId);
        if (ship != null) {
            shipToConnectionId.remove(ship);
            engine.removeGameObject(ship);
        }

        // send a packet to all clients letting them know that a client has been removed
        engine.getNetworkDriver().sendPacketToAll(new ClientLeftGamePacket(connectionId));
    }


    private void handlePlayerLocationUpdate(Integer connectionId, PlayerLocationUpdatePacket playerLocationUpdatePacket) {
        // find the player by id
        ServerDronePlayerShip ship = connectionIdToShip.get(connectionId);

        assert ship != null;

        // set the location of the player
        ship.setXY(playerLocationUpdatePacket.getPositionX(), playerLocationUpdatePacket.getPositionY());

        // set the direction of the player
        ship.setFacingX(playerLocationUpdatePacket.getDirectionX());
        ship.setFacingY(playerLocationUpdatePacket.getDirectionY());
//        ship.lookat(playerLocationUpdatePacket.getDirectionX(), playerLocationUpdatePacket.getDirectionY());
    }


    private void handleJoin(Integer connectionId, JoinPacket joinPacket) {
        // save the name
        connectionIdToName.put(connectionId, joinPacket.getName());

        // create a "you are joined" packet and send it to the client
        List<WorldStatePlayerObject> worldList = new ArrayList<WorldStatePlayerObject>();

        // create the list of player objects in the world
        // these generics are almost as ugly as STL, and not that handy really...
        for (Map.Entry<Integer, ServerDronePlayerShip> entry : connectionIdToShip.entrySet()) {
            ServerDronePlayerShip droneShip = entry.getValue();
            Integer shipConnectionId = entry.getKey();

            WorldStatePlayerObject playerObject = new WorldStatePlayerObject(shipConnectionId,
                    droneShip.getX(), droneShip.getY(),
                    droneShip.getFacingX(), droneShip.getFacingY());

            worldList.add(playerObject);
        }

        JoinResponsePacket joinResponsePacket = new JoinResponsePacket(worldList, connectionId);

        engine.getNetworkDriver().sendPacket(connectionId, joinResponsePacket);
    }


    private void handleRequestSpawn(Integer connectionId, RequestSpawnPacket requestSpawnPacket) {

        // create a player object
        ServerDronePlayerShip droneShip = new ServerDronePlayerShip();

        // set the owner
        droneShip.setConnectionOwner(connectionId);

        // inject that player object into the world
        engine.addGameObject(droneShip);

        // save the player object in a map against the id
        connectionIdToShip.put(connectionId, droneShip);

        // save the id in a map against the player
        shipToConnectionId.put(droneShip, connectionId);

        // send a spawn player event to all the clients
        // todo have some sort of spawn manager or something to get the actual spawn point?
        float spawnX = 320.0f, spawnY = 240.0f;
        droneShip.setXY(spawnX, spawnY);
        String name = connectionIdToName.get(connectionId);
        logger.debug("sending spawn packet with name : " + name);
        PlayerSpawnPacket spawnPacket = new PlayerSpawnPacket(connectionId,
                connectionIdToName.get(connectionId),
                spawnX, spawnY, 320.0f, 250.0f);

        engine.getNetworkDriver().sendPacketToAll(spawnPacket);
    }


    private void handlePlayerFire(Integer connectionId, PlayerFirePacket playerFirePacket) {

        // create a missile object
        float x, y, c;
        float maxRadius = 300.0f;
        float velX, velY;
        x = playerFirePacket.getDirectionX() - playerFirePacket.getPositionX();
        y = playerFirePacket.getDirectionY() - playerFirePacket.getPositionY();

        c = (float) Math.sqrt((Math.abs(x) * Math.abs(x)) + (Math.abs(y) * Math.abs(y)));

        // assume that the radius is positive
        // find the difference
        float fScale = maxRadius / c;
        velX = x * fScale;
        velY = y * fScale;

        ServerPlayerBullet bullet = new ServerPlayerBullet(playerFirePacket.getPositionX(), playerFirePacket.getPositionY(), velX, velY);

        bullet.setOwner(connectionId);

        engine.addGameObject(bullet);

        engine.getNetworkDriver().sendPacketToAll(new MissilePacket(connectionId,
                playerFirePacket.getPositionX(),
                playerFirePacket.getPositionY(),
                playerFirePacket.getDirectionX(),
                playerFirePacket.getDirectionY(),
                velX,
                velY));
    }


    /**
     * Called when a missile has collided with a player.
     *
     * @param missile the missile that hit
     * @param player the player that the missile hit
     */
    public void missileHitPlayer(ServerPlayerBullet missile, ServerDronePlayerShip player) {

        String missileOwnerName = connectionIdToName.get(missile.getConnectionOwner());
        String playerName = connectionIdToName.get(player.getConnectionOwner());

        logger.debug(missileOwnerName + " hit " + playerName + " with a missile");
        
        engine.getNetworkDriver().sendPacketToAll(new PlayerDamagePacket(player.getConnectionOwner(), MISSILE_DAMAGE));
    }

    public float getClientUpdateRate() {
        return clientUpdateRate;
    }


    public void setClientUpdateRate(float clientUpdateRate) {
        this.clientUpdateRate = clientUpdateRate;
    }
}
