package org.jgameengine.nineteenfourtysomething.logic;

import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy.BaseEnemy;

/**
 * Defines the large steps that happen during the game.
 *
 * Eg, game starts, player dies, game ends.
 *
 * @author Owen Butler
 */
public interface GameFlowLogic {

    /**
     * Called when the game initially loads.
     *
     * This will probably put up the high score/instruction/logo screen, and spawn a mouse listener to
     * listen for the player requesting to start the game.
     */
    void gameLoads();


    /**
     * Called when the player requests to start the game.
     */
    void playerSpawns();


    /**
     * Called when the player dies.
     *
     * Buh bow, game over.
     */
    void playerDies();


    /**
     * When an enemy dies.
     *
     * @param enemy the enemy which died
     */
    void enemyDies(BaseEnemy enemy);


    /**
     * When a powerup spawns.
     *
     * @param x x coordinate where the powerup spawns.
     * @param y y coordinate where the powerup spawns.
     */
    void powerupSpawns(float x, float y);
}
