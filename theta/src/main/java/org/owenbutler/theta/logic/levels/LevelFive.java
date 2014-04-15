package org.owenbutler.theta.logic.levels;

import org.jgameengine.common.events.Event;
import org.owenbutler.theta.logic.LevelSupport;

public class LevelFive extends LevelSupport {

    public void start() {

        // let the hud manager know the level is starting
        showLevelIntro("level five");

        // setup the enemy spawns
        float time = 0;

        for (int i = 0; i < 25; ++i) {
            in((float) i, new Event() {
                public void trigger() {
                    spawnSmallSeeker();
                }
            });
        }
        in(10.0f, new Event() {
            public void trigger() {
                showLevelText("warning!");
                spawnSmallSeeker();
                spawnSmallSeeker();
                spawnSmallSeeker();
                spawnSmallSeeker();
                spawnSmallSeeker();
                spawnSmallSeeker();
                spawnSmallSeeker();
                spawnSmallSeeker();
                spawnSmallSeeker();
                spawnSmallSeeker();
            }
        });

        // 1 2 3
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy1();
                spawnEnemy2();
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

        // 3 3 3
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy3();
                spawnEnemy3();
                spawnEnemy3();
                spawnEnemy3();
            }
        });

        // 2 2
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy2();
                spawnEnemy2();
                spawnEnemy2();
            }
        });

        // 4 4
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy4();
                spawnEnemy4();
                spawnEnemy4();
            }
        });

        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy3();
                spawnEnemy3();
                spawnEnemy3();
                spawnEnemy3();
            }
        });

        // 2b 2b
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                showLevelText("warning!");

                spawnEnemy2(true);
                spawnEnemy2(true);
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