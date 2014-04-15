package org.owenbutler.theta.logic.levels;

import org.jgameengine.common.events.Event;
import org.owenbutler.theta.logic.LevelSupport;

public class LevelThree extends LevelSupport {

    public void start() {

        // let the hud manager know the level is starting
        showLevelIntro("level three");

        // setup the enemy spawns
        float time = 0;

        for (int i = 0; i < 25; ++i) {
            in((float) i, new Event() {
                public void trigger() {
                    spawnSmallSeeker();
                }
            });
        }

        // 2
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy2();
            }
        });

        // 3
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy3();
            }
        });

        // 2 2
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy2();
                spawnEnemy2();
            }
        });

        // 1 1 1
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy1();
                spawnEnemy1();
                spawnEnemy1();
                spawnEnemy1();
            }
        });

        // 2 3
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy2();
                spawnEnemy2();
                spawnEnemy3();
            }
        });

        // 3 3
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy3();
                spawnEnemy3();
            }
        });

        // 3 1 3
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy3();
                spawnEnemy1();
                spawnEnemy1();
                spawnEnemy3();
            }
        });

        // 3b
        time += 4.0f;
        in(time, new Event() {
            public void trigger() {
                showLevelText("warning!");

                spawnEnemy3(true);
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