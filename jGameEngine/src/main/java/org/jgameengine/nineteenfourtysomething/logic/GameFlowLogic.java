package org.jgameengine.nineteenfourtysomething.logic;

import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy.BaseEnemy;

public interface GameFlowLogic {

    void gameLoads();

    void playerSpawns();

    void playerDies();

    void enemyDies(BaseEnemy enemy);

    void powerupSpawns(float x, float y);
}
