package org.owenbutler.theta.logic.levels;

import org.jgameengine.common.events.Event;
import org.owenbutler.theta.logic.LevelSupport;

public class LevelFour extends LevelSupport {

    public void start() {

        // let the hud manager know the level is starting
        showLevelIntro("level four");

        // setup the enemy spawns
        float time = 0;

        for (int i = 0; i < 21; ++i) {
            in((float) i, new Event() {
                public void trigger() {
                    spawnSmallSeeker();
                }
            });
        }

        // 1 1 1
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy1();
                spawnEnemy1();
                spawnEnemy1();
            }
        });

        // 2 2 2
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy2();
                spawnEnemy2();
                spawnEnemy2();
            }
        });

        // 3 3 3
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy3();
                spawnEnemy3();
                spawnEnemy3();
            }
        });

        // 4
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy4();
            }
        });

        // 4 4
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy4();
                spawnEnemy4();
                spawnEnemy1();
            }
        });

        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy1();
                spawnEnemy1();
                spawnEnemy1();
                spawnEnemy1();
            }
        });

        // 1b 1b
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                showLevelText("warning!");

                spawnEnemy1(true);
                spawnEnemy1(true);
            }
        });

        time += 4.0f;

        in(time, new Event() {
            public void trigger() {
                endLevelWhenAllEnemiesDead();
            }
        });

    }

}