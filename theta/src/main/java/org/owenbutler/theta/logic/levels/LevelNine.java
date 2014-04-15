package org.owenbutler.theta.logic.levels;

import org.jgameengine.common.events.Event;
import org.owenbutler.theta.logic.LevelSupport;

public class LevelNine extends LevelSupport {

    public void start() {

        // let the hud manager know the level is starting
        showLevelIntro("level nine");

        // setup the enemy spawns
        float time = 0;

        for (float i = 2.0f; i < 25; i += 0.5f) {
            in(i, new Event() {
                public void trigger() {
                    spawnSmallSeeker();
                }
            });
        }

        // 1 2 3 4
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy1();
                spawnEnemy2();
                spawnEnemy3();
                spawnEnemy4();
            }
        });

        // 1 2 3 4
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy1();
                spawnEnemy2();
                spawnEnemy3();
                spawnEnemy4();
            }
        });

        // 1 2 3 4
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy1();
                spawnEnemy2();
                spawnEnemy3();
                spawnEnemy4();
            }
        });

        // 1 1 2 2 3 3 4 4
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy1();
                spawnEnemy1();
                spawnEnemy2();
                spawnEnemy2();
                spawnEnemy3();
                spawnEnemy3();
                spawnEnemy4();
                spawnEnemy4();
            }
        });

        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy1();
                spawnEnemy1();
                spawnEnemy2();
                spawnEnemy3();
                spawnEnemy4();
            }
        });

        // 1 2 3 4
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy1();
                spawnEnemy1();
                spawnEnemy2();
                spawnEnemy3();
                spawnEnemy4();
            }
        });

        // 1 2 3 4
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy1();
                spawnEnemy1();
                spawnEnemy2();
                spawnEnemy3();
                spawnEnemy4();
            }
        });

        // 1 1 2 2 3 3 4 4
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy1();
                spawnEnemy1();
                spawnEnemy1();
                spawnEnemy1();
                spawnEnemy2();
                spawnEnemy2();
                spawnEnemy3();
                spawnEnemy4();
            }
        });

        // 4b 4b
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                showLevelText("warning!");

                spawnEnemy4(true);
                spawnEnemy4(true);
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