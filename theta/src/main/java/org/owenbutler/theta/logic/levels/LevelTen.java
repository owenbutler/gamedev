package org.owenbutler.theta.logic.levels;

import org.jgameengine.common.events.Event;
import org.owenbutler.theta.logic.LevelSupport;

public class LevelTen extends LevelSupport {

    public void start() {

        // let the hud manager know the level is starting
        showLevelIntro("level ten");

        // setup the enemy spawns
        float time = 0;

        for (float i = 2.0f; i < 25; i += 0.2f) {
            in(i, new Event() {
                public void trigger() {
                    spawnSmallSeeker();
                }
            });
        }

        // 1b
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy1(true);
            }
        });

        // 2b
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy2(true);
            }
        });

        // 3b
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy3(true);
            }
        });

        // 4b
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                spawnEnemy4(true);
            }
        });

        // 4b 1b
        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                showLevelText("warning!");

                spawnEnemy4(true);
                spawnEnemy1(true);
            }
        });

        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                showLevelText("warning!");

                spawnEnemy4(true);
                spawnEnemy2(true);
            }
        });

        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                showLevelText("warning!");

                spawnEnemy4(true);
                spawnEnemy3(true);
            }
        });

        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                showLevelText("warning!");

                spawnEnemy4(true);
                spawnEnemy4(true);
            }
        });

        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                showLevelText("warning!");

                spawnEnemy4(true);
                spawnEnemy4(true);
                spawnEnemy4(true);
            }
        });

        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                showLevelText("warning!");

                spawnEnemy4(true);
                spawnEnemy4(true);
                spawnEnemy4(true);
                spawnEnemy4(true);
            }
        });

        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                showLevelText("warning!");

                spawnEnemy4(true);
                spawnEnemy4(true);
                spawnEnemy4(true);
                spawnEnemy4(true);
                spawnEnemy4(true);
            }
        });

        time += 3.0f;
        in(time, new Event() {
            public void trigger() {
                showLevelText("warning!");

                spawnEnemy4(true);
                spawnEnemy4(true);
                spawnEnemy4(true);
                spawnEnemy4(true);
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