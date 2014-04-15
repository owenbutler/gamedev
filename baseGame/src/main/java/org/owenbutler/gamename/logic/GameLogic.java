package org.owenbutler.JGE_ARTIFACT_ID.logic;

import org.jgameengine.common.events.Event;
import org.jgameengine.engine.Engine;
import org.jgameengine.input.MouseListener;
import org.jgameengine.input.PauseListener;
import org.owenbutler.JGE_ARTIFACT_ID.renderables.Player;

public class GameLogic implements MouseListener, PauseListener {

    protected Engine engine;

    protected HudManager hudManager;

    protected boolean listeningForPlayerSpawn;

    protected boolean gameStarted;

    protected Player player;

    protected float enemySpawnTime;

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

    /**
     * Pause requested.
     */
    public void pause() {
        engine.pause();
    }

    /**
     * The opposite of pause.
     */
    public void unPause() {
        engine.unpause();
    }

}
