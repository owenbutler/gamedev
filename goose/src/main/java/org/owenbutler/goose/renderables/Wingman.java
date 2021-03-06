package org.owenbutler.goose.renderables;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.owenbutler.goose.constants.AssetConstants;
import org.owenbutler.goose.constants.GameConstants;
import org.owenbutler.goose.logic.GameLogic;

import java.util.HashSet;
import java.util.Set;

public class Wingman extends BaseDrawableGameObject {

    // whether we are dead
    protected boolean dead;

    // whether we are firing gun
    protected boolean firing;

    // reference to the game logic object
    protected GameLogic game;

    // whether we should correct for 8 way movement, velocity etc.
    protected boolean eightWayMoveAdjust;

    // our current waypoint location
    float wayPointX, wayPointY;

    // the wingmen in the game
    Set<BaseDrawableGameObject> wingMen;

    // whether the ship is currenting tracking to a point, on x and y.
    protected boolean headingToWayPointX;
    protected boolean headingToWayPointY;

    // how close a ship has to be to it's waypoint to be considered there
    protected int waypointErrorVar = 15;

    // the idle movements that a ship goes through
    private float[][] idleMovements = new float[][]{
            {5, 5},
            {-5, 5},
            {-5, -5},
            {5, -5}
    };
    int idleMovementIndexX = 0;
    int idleMovementIndexY = 0;

    // the ships idle point, the place where it goes when theres nothing else to do
    float idlePointX, idlePointY;

    // the sensor we use to detect other wingmen in proximity
    protected WingmanSensor wingmanSensor;

    // set of objects that are have been sensed by the wingman sensor
    protected Set<BaseDrawableGameObject> wingmenWithinRadius = new HashSet<>(6);

    // whether we are taking evasive action or not
    protected boolean evasiveAction;

    // is this guy idling
    protected boolean idling;

    public Wingman() {
        super(AssetConstants.gfx_player, 0, 0, 32, 32);

        setCollidable(true);
        setCollisionRadius(8);
        setScreenClipRemove(false);

        gameEngine.getEventHandler().addEventInLoop(this, GameConstants.PLAYER_FIRE_INTERVAL, GameConstants.PLAYER_FIRE_INTERVAL, new Event() {
            public void trigger() {
                checkPlayerFire();
            }
        });

        gameEngine.getEventHandler().addEventInLoop(this, GameConstants.WINGMAN_MOVE_CHECK_INTERVAL, GameConstants.WINGMAN_MOVE_CHECK_INTERVAL, new Event() {
            public void trigger() {
                checkMovement();
            }
        });

        gameEngine.getEventHandler().addEventInLoop(this, GameConstants.WINGMAN_AI_CHECK_INTERVAL, GameConstants.WINGMAN_AI_CHECK_INTERVAL, new Event() {
            public void trigger() {
                checkSituation();
            }
        });

        game = (GameLogic) gameEngine.getRegisteredGameService("game");

        setSortZ(AssetConstants.Z_PLAYER);

        eightWayMoveAdjust = true;

        wingmanSensor = new WingmanSensor(this);
        gameEngine.addGameObject(wingmanSensor);
    }

    protected void checkSituation() {

        // check whether there are bullets in our close proximity
        // if so, plan a dodge
        if (bulletsInProximity()) {
            if (dodgePossible()) {
                dodgeBullets();
            } else {

                // if we can't dodge, move back
                moveBackToCreateBulletDodge();
            }

        } else if (wingmenTooClose()) {

            // check whether there are wingmen near us
            // if so, move away and up/down to create space
            evasiveAction();

        } else {
            if (!idling) {
                trackWayPoint();
            }
        }

        // check whether there are wingmen above
        // if not,
        // check whether there are enemies above
        // if so, shoot
        firing = noFriendliesAbove() && bogiesAbove();

        resetSensorsAndStuff();
    }

    private void resetSensorsAndStuff() {

        wingmenWithinRadius.clear();
    }

    private boolean noFriendliesAbove() {

        int xRange = GameConstants.WINGMAN_AI_X_RANGE_ABOVE_CHECK;

        // iterate through the list of friendlies, checking whether they are within a certain range of our x
        for (BaseDrawableGameObject goose : wingMen) {
            if (goose == this) {
                continue;
            }

            float leftBound = goose.getX() - xRange;
            float rightBound = goose.getX() + xRange;
            if (x > leftBound && x < rightBound) {

                // something is within our x range, check if they are above us
                if (y > goose.getY()) {

                    // they are above us
                    return false;
                }
            }

        }

        return true;
    }

    private boolean bogiesAbove() {

        return game.bogiesAbovePosition(x);
    }

    private void evasiveAction() {
        evasiveAction = true;

        BaseDrawableGameObject[] wings = new BaseDrawableGameObject[0];
        wings = wingmenWithinRadius.toArray(wings);
        int moveAwayFrom = RandomUtils.nextInt(wings.length);

        BaseDrawableGameObject other = wings[moveAwayFrom];
        if (other.getX() > x) {
            velX = -GameConstants.PLAYER_VELOCITY;
        } else {
            velX = GameConstants.PLAYER_VELOCITY;
        }

        if (other.getY() > y) {
            velY = -GameConstants.PLAYER_VELOCITY;
        } else {
            velY = GameConstants.PLAYER_VELOCITY;
        }
    }

    private boolean wingmenTooClose() {
        return wingmenWithinRadius.size() != 0;
    }

    private void moveBackToCreateBulletDodge() {

    }

    private void dodgeBullets() {

    }

    private boolean dodgePossible() {
        return false;
    }

    private boolean bulletsInProximity() {
        return false;
    }

    private void checkMovement() {

        if (evasiveAction) {
            return;
        }

        if (headingToWayPointX) {

            if (closeToX()) {
                headingToWayPointX = false;
            } else {

                if (x < wayPointX) {
                    velX = GameConstants.PLAYER_VELOCITY;
                } else if (x > wayPointX) {
                    velX = -GameConstants.PLAYER_VELOCITY;
                }
            }

        } else {

            idleMovementX();
        }

        if (headingToWayPointY) {

            if (closeToY()) {
                headingToWayPointY = false;
            } else {

                if (y < wayPointY) {
                    velY = GameConstants.PLAYER_VELOCITY;
                } else if (y > wayPointY) {
                    velY = -GameConstants.PLAYER_VELOCITY;
                }
            }

        } else {

            idleMovementY();
        }

        idling = !headingToWayPointX || !headingToWayPointY;
    }

    private boolean closeToX() {
        float xLeftBound = wayPointX - waypointErrorVar;
        float xRightBound = wayPointX + waypointErrorVar;

        return x > xLeftBound && x < xRightBound;
    }

    private boolean closeToY() {
        float yUpBound = wayPointY - waypointErrorVar;
        float yDownBound = wayPointY + waypointErrorVar;

        return y > yUpBound && y < yDownBound;
    }

    private void idleMovementX() {

        velX = idleMovements[idleMovementIndexX++][0];

        idleMovementIndexX %= idleMovements.length;

    }

    private void idleMovementY() {

        velY = idleMovements[idleMovementIndexY++][1];

        idleMovementIndexY %= idleMovements.length;

    }

    private void setSpawnPoint() {

        float spawnY;

        spawnY = 740;

        x = wayPointX;
        y = spawnY;
    }

    private void checkPlayerFire() {

        if (dead) {
            return;
        }

        if (firing) {
            fire();
        }
    }

    private void fire() {

        float bulletX = x + (RandomUtils.nextInt(20) - 10);

        PlayerBullet bullet = new PlayerBullet(bulletX, y - 10);
        gameEngine.addGameObject(bullet);
    }

    public void think() {
        baseDrawableThink();
    }

    public void move() {

        float tVelX = velX;
        float tVelY = velY;

        if (eightWayMoveAdjust) {
            if (Math.abs(velX) == Math.abs(velY)) {
                tVelX = velX * 0.80f;
                tVelY = velY * 0.80f;
            }
        }

        x += gameEngine.getTimeDelta() * tVelX;
        y += gameEngine.getTimeDelta() * tVelY;

        if (clippedToScreen) {
            clipToScreen();
        }
        surface.setXY(x, y);
    }

    public Set<BaseDrawableGameObject> getWingMen() {
        return wingMen;
    }

    public void setWingMen(Set<BaseDrawableGameObject> wingMen) {
        this.wingMen = wingMen;
    }

    public void setIdlePoint(int num) {

        int modifier = 1;

        if (num > 1) {
            modifier = 2;
        }

        idlePointX = (num + modifier) * 130;
        idlePointY = 500 + RandomUtils.nextInt(50);

        wayPointX = idlePointX;
        wayPointY = idlePointY;

        trackWayPoint();

        setSpawnPoint();
    }

    public void trackWayPoint() {
        headingToWayPointX = true;
        headingToWayPointY = true;
        evasiveAction = false;
        idling = false;
    }

    public void wingManMovementWithinRadius(BaseDrawableGameObject wingman) {

        wingmenWithinRadius.add(wingman);
    }
}
