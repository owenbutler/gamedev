package org.owenbutler.grazier.logic;

import org.jgameengine.engine.Engine;
import org.owenbutler.grazier.renderables.Enemy1;

public class Level {

    protected Engine engine;

    protected int level;

    public Level(Engine engine) {
        this.engine = engine;
    }

    public void startLevel() {

        // set an event to spawn enemies

//        engine.getEventHandler().addEventInLoop(this, 2, 2, new Event() {
//            public void trigger() {
//                spawnEnemy();
//            }
//        });

        Enemy1 enemy;

//        enemy = new Enemy1();
//        enemy.setX(100);
//        enemy.setY(100);
//        engine.addGameObject(enemy);
//
//        enemy = new Enemy1();
//        enemy.setX(200);
//        enemy.setY(100);
//        engine.addGameObject(enemy);
//
//        enemy = new Enemy1();
//        enemy.setX(300);
//        enemy.setY(100);
//        engine.addGameObject(enemy);
//
        enemy = new Enemy1();
        enemy.setX(400);
        enemy.setY(100);
        engine.addGameObject(enemy);

//        enemy = new Enemy1();
//        enemy.setX(500);
//        enemy.setY(100);
//        engine.addGameObject(enemy);
//
//        enemy = new Enemy1();
//        enemy.setX(600);
//        enemy.setY(100);
//        engine.addGameObject(enemy);
//
//        enemy = new Enemy1();
//        enemy.setX(700);
//        enemy.setY(100);
//        engine.addGameObject(enemy);
//
    }

    private void spawnEnemy() {

        Enemy1 enemy = new Enemy1();

        engine.addGameObject(enemy);

    }

    public void endLevel() {

        engine.getEventHandler().removeEventsForOwner(this);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
