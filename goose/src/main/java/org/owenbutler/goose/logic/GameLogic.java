package org.owenbutler.goose.logic;

import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.engine.Engine;
import org.jgameengine.input.MouseListener;
import org.jgameengine.input.PauseListener;
import org.owenbutler.goose.constants.GameConstants;
import org.owenbutler.goose.renderables.Enemy1;
import org.owenbutler.goose.renderables.Player;
import org.owenbutler.goose.renderables.Wingman;

import java.util.HashSet;
import java.util.Set;

public class GameLogic implements MouseListener, PauseListener {

    protected Engine engine;

    protected HudManager hudManager;

    protected boolean listeningForPlayerSpawn;

    protected boolean gameStarted;

    protected Player player;

    protected float enemySpawnTime;

    Set<BaseDrawableGameObject> wingmen = new HashSet<>();

    public GameLogic(Engine engine) {
        this.engine = engine;

        engine.getInputManager().addMouseListener(this);

    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void gameLoads() {

        showIntroScreen();

        // listen for mouse button to start the game
        listeningForPlayerSpawn = true;

        engine.getInputManager().addPauseListener(this);

        for (int i = 0; i < GameConstants.WINGMEN; ++i) {

            Wingman wingman = new Wingman();
            engine.addGameObject(wingman);

            wingman.setWingMen(wingmen);
            wingman.setIdlePoint(i);

            wingmen.add(wingman);
        }

        Enemy1 enemy1 = new Enemy1(50, 100);
        engine.addGameObject(enemy1);

        engine.getEventHandler().addEventInLoop(this, GameConstants.GAME_ENEMY_MOVEMENT_FADE_CHECK, GameConstants.GAME_ENEMY_MOVEMENT_FADE_CHECK, new Event() {
            public void trigger() {
                fadeEnemyMovement();
            }
        });

    }

    private void showIntroScreen() {

        if (hudManager == null) {
            hudManager = (HudManager) engine.getHudManager();
        }

        hudManager.showIntroScreen();
    }

    private void playerSpawns() {

        gameStarted = true;

        hudManager.hideIntroScreen();

        if (player == null) {
            player = new Player(400, 580);
            engine.getInputManager().addMovementListener(player);
            engine.getInputManager().addMouseListener(player);
            engine.addGameObject(player);
            engine.registerGameService("player", player);

            wingmen.add(player);
        }

        player.setBlRender(true);
        player.setXY(400, 580);
/*        player.setDead(false);*/

    }

    public void playerDies() {

        player.setBlRender(false);
/*        player.dead();*/
        gameStarted = false;
        hudManager.setShowGameOver(true);

        engine.getEventHandler().addEventIn(this, 2.0f, new Event() {
            public void trigger() {
                hudManager.showIntroScreen();
                hudManager.setShowGameOver(false);

                listeningForPlayerSpawn = true;

            }
        });

    }

    public void mouseEvent(int x, int y) {

    }

    public void button0Down() {

    }

    public void button0Up() {
        if (listeningForPlayerSpawn) {
            playerSpawns();
            listeningForPlayerSpawn = false;
        }
    }

    public void button1Down() {

    }

    public void button1Up() {

    }

    public void button2Down() {

    }

    public void button2Up() {

    }

    public void pause() {
        engine.pause();
    }

    public void unPause() {
        engine.unPause();
    }

    public void updateEnemyPosition(float x) {

        int index = ((int) x) / numQuadrants;

        enemyPositionTimes[index] = engine.getCurrentTime();
    }

    final int numQuadrants = 100;
    float[] enemyPositionTimes = new float[numQuadrants];

    private void fadeEnemyMovement() {

        float currentTime = engine.getCurrentTime();

        for (int index = 0; index < enemyPositionTimes.length; ++index) {
            float enemyPositionTime = enemyPositionTimes[index];

            if (enemyPositionTime != 0) {
                if (enemyPositionTime + GameConstants.GAME_ENEMY_MOVEMENT_FADE_THRESHOLD < currentTime) {
                    enemyPositionTimes[index] = 0;
                }
            }
        }
    }

    public boolean bogiesAbovePosition(float x) {
        int index = ((int) x) / numQuadrants;

        return enemyPositionTimes[index] != 0;
    }
}
