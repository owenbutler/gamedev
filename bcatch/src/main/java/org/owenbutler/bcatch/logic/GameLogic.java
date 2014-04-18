package org.owenbutler.bcatch.logic;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.WebStartDataStore;
import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.GameObject;
import org.jgameengine.engine.Engine;
import org.jgameengine.input.MouseListener;
import org.owenbutler.bcatch.constants.GameConstants;
import org.owenbutler.bcatch.renderables.Background;
import org.owenbutler.bcatch.renderables.Bullet;
import org.owenbutler.bcatch.renderables.BulletCatcher;
import org.owenbutler.bcatch.renderables.Enemy;
import org.owenbutler.bcatch.renderables.Player;
import org.owenbutler.bcatch.renderables.ReboundBullet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class GameLogic implements MouseListener {

    protected Engine engine;

    protected HudManager hudManager;

    protected boolean listeningForPlayerSpawn;

    protected boolean gameStarted;

    protected Player player;

    protected float enemySpawnTime;

    protected int totalScore;

    protected MouseDrivenRoutePlotter plotter;

    protected float initialEnemyWaveTime = GameConstants.ENEMY_WAVE_TIME;

    protected WayPoint[] currentRoute;

    protected List<Integer> highScores;

    protected WebStartDataStore dataStore;

    private static String contentsUrl = "http://www.owenbutler.org/bcatch/";

    protected Background background;

    public GameLogic(Engine engine) {
        this.engine = engine;

        engine.getInputManager().addMouseListener(this);

        dataStore = new WebStartDataStore(contentsUrl);

        if (dataStore.loadObject(false) == null) {
            highScores = new ArrayList<>();
            highScores.add(1000);
            highScores.add(10000);
            highScores.add(100000);
            highScores.add(1000000);

        } else {
            //noinspection unchecked
            highScores = (List<Integer>) dataStore.loadObject(false);
        }
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

        background = new Background();
        engine.addGameObject(background);

        engine.getEventHandler().addEventInLoop(this, 10, 10, new Event() {
            public void trigger() {
                updateBackgroundSpeed();
            }
        });
    }

    private void showIntroScreen() {

        if (hudManager == null) {
            hudManager = (HudManager) engine.getHudManager();
            updateHighScores();

        }

        hudManager.showIntroScreen();

    }

    private void playerSpawns() {

        gameStarted = true;

        hudManager.hideIntroScreen();

//        plotter = new MouseDrivenRoutePlotter(engine);
//
//        engine.getInputManager().addMouseListener(plotter);

        if (player == null) {
            player = new Player(400, 580);
            engine.getInputManager().addMovementListener(player);
            engine.getInputManager().addMouseListener(player);
            engine.getInputManager().addPauseListener(player);
            engine.addGameObject(player);

            engine.registerGameService("player", player);
        }

        player.setDead(false);
        player.setBlRender(true);
        player.setXY(400, 580);

        totalScore = 0;
        hudManager.updateScore(totalScore);

        engine.getEventHandler().addEventIn(this, initialEnemyWaveTime, new Event() {
            public void trigger() {
                spawnEnemyWave();
            }
        });

        engine.getEventHandler().addEventIn(this, 10.0f, new Event() {
            public void trigger() {
                reduceWaveSpawnTime();
            }
        });

        // spawn a few enemies
//        for (int i = 0; i < 10; i++) {
//            Enemy enemy = new Enemy();
//            enemy.setX(RandomUtils.nextInt(800));
//            enemy.setY(RandomUtils.nextInt(600));
//            engine.addGameObject(enemy);
//
//        }

    }

    private void reduceWaveSpawnTime() {
        if (!gameStarted) {
            return;
        }

        initialEnemyWaveTime -= 0.1f;
        if (initialEnemyWaveTime < 0) {
            initialEnemyWaveTime = 0;
        }

        numEnemiesSpawn += 1;

        engine.getEventHandler().addEventIn(this, 10.0f, new Event() {
            public void trigger() {
                reduceWaveSpawnTime();
            }
        });
    }

    protected int numEnemiesSpawn = 4;

    private void spawnEnemyWave() {

        if (!gameStarted) {
            return;
        }

        // choose a route
        currentRoute = waypoints[RandomUtils.nextInt(waypoints.length)];

        // set an event to spawn enemies on that route
        engine.getEventHandler().addEventInRepeat(this,
                GameConstants.ENEMY_SPAWN_IN_WAVE_TIME,
                GameConstants.ENEMY_SPAWN_IN_WAVE_TIME,
                numEnemiesSpawn,
                new Event() {
                    public void trigger() {
                        spawnEnemy();
                    }
                });

        // set an event to spawn another wave
        engine.getEventHandler().addEventIn(this, GameConstants.ENEMY_WAVE_TIME, new Event() {
            public void trigger() {
                spawnEnemyWave();
            }
        });

    }

    private void spawnEnemy() {

        if (!gameStarted) {
            return;
        }

        Enemy newEnemy = new Enemy();
        newEnemy.setRoute(currentRoute);

        engine.addGameObject(newEnemy);

    }

    public void playerDies() {

        player.setBlRender(false);
        player.setDead(true);
        gameStarted = false;
        hudManager.setShowGameOver(true);

        engine.getEventHandler().addEventIn(this, 2.0f, new Event() {
            public void trigger() {
                hudManager.showIntroScreen();
                hudManager.setShowGameOver(false);

                listeningForPlayerSpawn = true;

            }
        });

        // remove enemies and bullets
        Set<GameObject> gameObjects = engine.getGameObjects();
        for (GameObject gameObject : gameObjects) {
            boolean remove = false;
            if (gameObject instanceof Enemy) {
                remove = true;
            } else if (gameObject instanceof Bullet) {
                remove = true;
            } else if (gameObject instanceof ReboundBullet) {
                remove = true;
            } else if (gameObject instanceof BulletCatcher) {
                remove = true;
            }

            if (remove) {
                engine.removeGameObject(gameObject);
            }
        }

        highScores.add(totalScore);

        updateHighScores();

        background.setYTexMod(background.defaultYTexSpeed);

    }

    private void updateHighScores() {

        Collections.sort(highScores);
        Collections.reverse(highScores);

        if (highScores.size() == 11) {
            highScores.remove(10);
        }

        dataStore.saveObject(highScores);

        hudManager.setHighScores(highScores);

    }

    public void scoreHit(int score) {
        totalScore += score;

        hudManager.updateScore(totalScore);
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

    private void updateBackgroundSpeed() {

        if (!gameStarted) {
            return;
        }

        float currentSpeed = background.getYTexMod();

        background.setYTexMod(currentSpeed + 0.05f);
    }

    WayPoint[][] waypoints = {

            {
                    new WayPoint(17, 32),
                    new WayPoint(24, 64),
                    new WayPoint(31, 100),
                    new WayPoint(38, 132),
                    new WayPoint(45, 155),
                    new WayPoint(56, 186),
                    new WayPoint(71, 227),
                    new WayPoint(87, 265),
                    new WayPoint(108, 308),
                    new WayPoint(131, 335),
                    new WayPoint(143, 343),
                    new WayPoint(162, 346),
                    new WayPoint(193, 346),
                    new WayPoint(219, 340),
                    new WayPoint(257, 319),
                    new WayPoint(283, 292),
                    new WayPoint(306, 265),
                    new WayPoint(324, 236),
                    new WayPoint(337, 204),
                    new WayPoint(341, 190),
                    new WayPoint(341, 178),
                    new WayPoint(355, 215),
                    new WayPoint(370, 269),
                    new WayPoint(377, 298),
                    new WayPoint(386, 330),
                    new WayPoint(400, 349),
                    new WayPoint(424, 373),
                    new WayPoint(469, 394),
                    new WayPoint(516, 385),
                    new WayPoint(570, 360),
                    new WayPoint(631, 317),
                    new WayPoint(681, 270),
                    new WayPoint(718, 221),
                    new WayPoint(743, 157),
                    new WayPoint(755, 116),
                    new WayPoint(764, 87),
                    new WayPoint(776, 54),
                    new WayPoint(783, 33),
                    new WayPoint(787, 16)
            },
            {
                    new WayPoint(12, 392),
                    new WayPoint(99, 390),
                    new WayPoint(208, 399),
                    new WayPoint(286, 391),
                    new WayPoint(338, 390),
                    new WayPoint(387, 388),
                    new WayPoint(429, 389),
                    new WayPoint(482, 387),
                    new WayPoint(525, 391),
                    new WayPoint(566, 391),
                    new WayPoint(618, 401),
                    new WayPoint(692, 402),
                    new WayPoint(716, 400),
                    new WayPoint(768, 373),
                    new WayPoint(782, 356),
                    new WayPoint(775, 293),
                    new WayPoint(744, 259),
                    new WayPoint(696, 207),
                    new WayPoint(677, 192),
                    new WayPoint(650, 178),
                    new WayPoint(604, 169),
                    new WayPoint(536, 151),
                    new WayPoint(461, 138),
                    new WayPoint(402, 122),
                    new WayPoint(340, 111),
                    new WayPoint(271, 88),
                    new WayPoint(213, 73),
                    new WayPoint(162, 51),
                    new WayPoint(100, 42),
                    new WayPoint(85, 39),
                    new WayPoint(70, 38),
                    new WayPoint(49, 37)
            },
            {
                    new WayPoint(774, 398),
                    new WayPoint(738, 395),
                    new WayPoint(674, 386),
                    new WayPoint(577, 386),
                    new WayPoint(531, 386),
                    new WayPoint(423, 386),
                    new WayPoint(368, 386),
                    new WayPoint(294, 389),
                    new WayPoint(253, 389),
                    new WayPoint(193, 388),
                    new WayPoint(128, 388),
                    new WayPoint(66, 388),
                    new WayPoint(32, 385),
                    new WayPoint(28, 338),
                    new WayPoint(60, 296),
                    new WayPoint(114, 258),
                    new WayPoint(190, 228),
                    new WayPoint(262, 210),
                    new WayPoint(348, 185),
                    new WayPoint(431, 157),
                    new WayPoint(534, 125),
                    new WayPoint(615, 103),
                    new WayPoint(675, 87),
                    new WayPoint(709, 75),
                    new WayPoint(743, 63),
                    new WayPoint(760, 55),
                    new WayPoint(782, 39)
            },
            {
                    new WayPoint(14, 390),
                    new WayPoint(43, 363),
                    new WayPoint(65, 330),
                    new WayPoint(85, 297),
                    new WayPoint(108, 267),
                    new WayPoint(135, 242),
                    new WayPoint(175, 212),
                    new WayPoint(226, 178),
                    new WayPoint(282, 145),
                    new WayPoint(335, 127),
                    new WayPoint(394, 112),
                    new WayPoint(455, 106),
                    new WayPoint(488, 108),
                    new WayPoint(583, 139),
                    new WayPoint(630, 165),
                    new WayPoint(642, 191),
                    new WayPoint(646, 230),
                    new WayPoint(635, 265),
                    new WayPoint(608, 300),
                    new WayPoint(566, 326),
                    new WayPoint(510, 336),
                    new WayPoint(466, 329),
                    new WayPoint(416, 294),
                    new WayPoint(402, 257),
                    new WayPoint(408, 215),
                    new WayPoint(432, 178),
                    new WayPoint(473, 141),
                    new WayPoint(506, 125),
                    new WayPoint(588, 95),
                    new WayPoint(650, 79),
                    new WayPoint(716, 63),
                    new WayPoint(755, 54),
                    new WayPoint(795, 41)
            },
            {
                    new WayPoint(768, 415),
                    new WayPoint(732, 378),
                    new WayPoint(675, 318),
                    new WayPoint(633, 277),
                    new WayPoint(593, 243),
                    new WayPoint(569, 226),
                    new WayPoint(539, 206),
                    new WayPoint(511, 193),
                    new WayPoint(464, 173),
                    new WayPoint(428, 162),
                    new WayPoint(368, 148),
                    new WayPoint(309, 142),
                    new WayPoint(269, 152),
                    new WayPoint(234, 171),
                    new WayPoint(204, 205),
                    new WayPoint(197, 230),
                    new WayPoint(197, 247),
                    new WayPoint(206, 274),
                    new WayPoint(231, 302),
                    new WayPoint(263, 321),
                    new WayPoint(295, 319),
                    new WayPoint(317, 302),
                    new WayPoint(332, 269),
                    new WayPoint(334, 235),
                    new WayPoint(324, 188),
                    new WayPoint(307, 158),
                    new WayPoint(280, 126),
                    new WayPoint(247, 103),
                    new WayPoint(202, 79),
                    new WayPoint(164, 64),
                    new WayPoint(121, 52),
                    new WayPoint(74, 38),
                    new WayPoint(53, 33),
                    new WayPoint(19, 23)
            },
            {
                    new WayPoint(12, 236),
                    new WayPoint(95, 189),
                    new WayPoint(156, 153),
                    new WayPoint(203, 127),
                    new WayPoint(232, 113),
                    new WayPoint(253, 104),
                    new WayPoint(269, 100),
                    new WayPoint(301, 91),
                    new WayPoint(336, 83),
                    new WayPoint(363, 78),
                    new WayPoint(408, 74),
                    new WayPoint(447, 76),
                    new WayPoint(470, 77),
                    new WayPoint(494, 83),
                    new WayPoint(531, 98),
                    new WayPoint(571, 117),
                    new WayPoint(618, 142),
                    new WayPoint(701, 183),
                    new WayPoint(738, 203),
                    new WayPoint(749, 209),
                    new WayPoint(785, 234),
                    new WayPoint(788, 248),
                    new WayPoint(774, 271),
                    new WayPoint(752, 290),
                    new WayPoint(705, 312),
                    new WayPoint(666, 323),
                    new WayPoint(615, 337),
                    new WayPoint(575, 350),
                    new WayPoint(533, 357),
                    new WayPoint(483, 364),
                    new WayPoint(423, 365),
                    new WayPoint(366, 368),
                    new WayPoint(307, 368),
                    new WayPoint(238, 353),
                    new WayPoint(181, 332),
                    new WayPoint(147, 319),
                    new WayPoint(96, 291),
                    new WayPoint(56, 269),
                    new WayPoint(34, 257)
            },
            {
                    new WayPoint(786, 240),
                    new WayPoint(737, 207),
                    new WayPoint(653, 161),
                    new WayPoint(589, 135),
                    new WayPoint(536, 112),
                    new WayPoint(503, 101),
                    new WayPoint(479, 95),
                    new WayPoint(429, 86),
                    new WayPoint(375, 81),
                    new WayPoint(322, 83),
                    new WayPoint(252, 101),
                    new WayPoint(186, 125),
                    new WayPoint(121, 157),
                    new WayPoint(81, 182),
                    new WayPoint(51, 202),
                    new WayPoint(29, 218),
                    new WayPoint(6, 235),
                    new WayPoint(45, 268),
                    new WayPoint(66, 282),
                    new WayPoint(110, 308),
                    new WayPoint(172, 336),
                    new WayPoint(221, 352),
                    new WayPoint(297, 357),
                    new WayPoint(366, 354),
                    new WayPoint(457, 344),
                    new WayPoint(537, 322),
                    new WayPoint(577, 307),
                    new WayPoint(637, 291),
                    new WayPoint(696, 277),
                    new WayPoint(742, 264),
                    new WayPoint(763, 255),
                    new WayPoint(782, 242)
            },
            {
                    new WayPoint(14, 20),
                    new WayPoint(76, 181),
                    new WayPoint(118, 295),
                    new WayPoint(136, 329),
                    new WayPoint(148, 373),
                    new WayPoint(153, 335),
                    new WayPoint(186, 220),
                    new WayPoint(205, 156),
                    new WayPoint(215, 121),
                    new WayPoint(225, 94),
                    new WayPoint(235, 70),
                    new WayPoint(244, 49),
                    new WayPoint(265, 98),
                    new WayPoint(321, 234),
                    new WayPoint(350, 300),
                    new WayPoint(364, 323),
                    new WayPoint(374, 343),
                    new WayPoint(384, 364),
                    new WayPoint(394, 275),
                    new WayPoint(426, 155),
                    new WayPoint(441, 114),
                    new WayPoint(465, 84),
                    new WayPoint(484, 63),
                    new WayPoint(516, 148),
                    new WayPoint(571, 290),
                    new WayPoint(583, 314),
                    new WayPoint(603, 368),
                    new WayPoint(608, 387),
                    new WayPoint(675, 269),
                    new WayPoint(724, 174),
                    new WayPoint(738, 143),
                    new WayPoint(745, 130),
                    new WayPoint(760, 96),
                    new WayPoint(771, 71),
                    new WayPoint(775, 58)
            },
            {
                    new WayPoint(12, 191),
                    new WayPoint(22, 149),
                    new WayPoint(42, 103),
                    new WayPoint(87, 70),
                    new WayPoint(165, 68),
                    new WayPoint(221, 105),
                    new WayPoint(257, 174),
                    new WayPoint(259, 265),
                    new WayPoint(219, 322),
                    new WayPoint(189, 336),
                    new WayPoint(145, 282),
                    new WayPoint(132, 199),
                    new WayPoint(147, 147),
                    new WayPoint(204, 95),
                    new WayPoint(291, 72),
                    new WayPoint(362, 76),
                    new WayPoint(414, 111),
                    new WayPoint(454, 189),
                    new WayPoint(457, 264),
                    new WayPoint(438, 318),
                    new WayPoint(401, 300),
                    new WayPoint(365, 233),
                    new WayPoint(365, 151),
                    new WayPoint(391, 95),
                    new WayPoint(434, 65),
                    new WayPoint(490, 62),
                    new WayPoint(580, 107),
                    new WayPoint(632, 172),
                    new WayPoint(651, 272),
                    new WayPoint(633, 341),
                    new WayPoint(601, 352),
                    new WayPoint(575, 332),
                    new WayPoint(544, 246),
                    new WayPoint(557, 176),
                    new WayPoint(605, 122),
                    new WayPoint(685, 84),
                    new WayPoint(722, 77),
                    new WayPoint(760, 76)
            },
            {
                    new WayPoint(797, 66),
                    new WayPoint(779, 68),
                    new WayPoint(731, 79),
                    new WayPoint(684, 104),
                    new WayPoint(645, 144),
                    new WayPoint(596, 222),
                    new WayPoint(563, 303),
                    new WayPoint(567, 342),
                    new WayPoint(580, 368),
                    new WayPoint(616, 380),
                    new WayPoint(657, 384),
                    new WayPoint(691, 366),
                    new WayPoint(704, 289),
                    new WayPoint(677, 195),
                    new WayPoint(653, 149),
                    new WayPoint(634, 129),
                    new WayPoint(596, 103),
                    new WayPoint(568, 96),
                    new WayPoint(534, 97),
                    new WayPoint(475, 123),
                    new WayPoint(422, 174),
                    new WayPoint(387, 246),
                    new WayPoint(390, 314),
                    new WayPoint(417, 361),
                    new WayPoint(458, 399),
                    new WayPoint(505, 368),
                    new WayPoint(533, 277),
                    new WayPoint(512, 188),
                    new WayPoint(478, 134),
                    new WayPoint(428, 85),
                    new WayPoint(404, 74),
                    new WayPoint(338, 98),
                    new WayPoint(256, 148),
                    new WayPoint(204, 201),
                    new WayPoint(204, 268),
                    new WayPoint(232, 327),
                    new WayPoint(251, 338),
                    new WayPoint(307, 363),
                    new WayPoint(385, 333),
                    new WayPoint(399, 255),
                    new WayPoint(359, 161),
                    new WayPoint(314, 109),
                    new WayPoint(257, 71),
                    new WayPoint(214, 65),
                    new WayPoint(159, 80),
                    new WayPoint(94, 130),
                    new WayPoint(52, 196),
                    new WayPoint(29, 279),
                    new WayPoint(41, 330),
                    new WayPoint(61, 361),
                    new WayPoint(82, 371),
                    new WayPoint(119, 375),
                    new WayPoint(170, 349),
                    new WayPoint(162, 262),
                    new WayPoint(115, 169),
                    new WayPoint(84, 126),
                    new WayPoint(56, 100),
                    new WayPoint(40, 90),
                    new WayPoint(17, 84)
            },
            {
                    new WayPoint(21, 8),
                    new WayPoint(22, 35),
                    new WayPoint(33, 46),
                    new WayPoint(80, 47),
                    new WayPoint(197, 47),
                    new WayPoint(280, 47),
                    new WayPoint(391, 40),
                    new WayPoint(463, 42),
                    new WayPoint(572, 40),
                    new WayPoint(583, 38),
                    new WayPoint(644, 40),
                    new WayPoint(681, 39),
                    new WayPoint(711, 44),
                    new WayPoint(730, 50),
                    new WayPoint(738, 67),
                    new WayPoint(722, 81),
                    new WayPoint(699, 85),
                    new WayPoint(626, 88),
                    new WayPoint(565, 88),
                    new WayPoint(484, 88),
                    new WayPoint(386, 88),
                    new WayPoint(335, 92),
                    new WayPoint(260, 93),
                    new WayPoint(211, 93),
                    new WayPoint(160, 93),
                    new WayPoint(116, 93),
                    new WayPoint(82, 93),
                    new WayPoint(30, 93),
                    new WayPoint(61, 123),
                    new WayPoint(147, 182),
                    new WayPoint(262, 242),
                    new WayPoint(424, 309),
                    new WayPoint(517, 341),
                    new WayPoint(584, 365),
                    new WayPoint(631, 377),
                    new WayPoint(677, 388),
                    new WayPoint(708, 394),
                    new WayPoint(731, 399),
                    new WayPoint(761, 406)
            },
            {
                    new WayPoint(782, 14),
                    new WayPoint(781, 25),
                    new WayPoint(778, 36),
                    new WayPoint(756, 56),
                    new WayPoint(720, 68),
                    new WayPoint(630, 72),
                    new WayPoint(517, 63),
                    new WayPoint(425, 63),
                    new WayPoint(324, 63),
                    new WayPoint(218, 63),
                    new WayPoint(155, 63),
                    new WayPoint(107, 63),
                    new WayPoint(62, 63),
                    new WayPoint(42, 63),
                    new WayPoint(31, 68),
                    new WayPoint(36, 88),
                    new WayPoint(66, 101),
                    new WayPoint(183, 119),
                    new WayPoint(289, 119),
                    new WayPoint(397, 119),
                    new WayPoint(451, 117),
                    new WayPoint(540, 115),
                    new WayPoint(596, 115),
                    new WayPoint(627, 115),
                    new WayPoint(683, 115),
                    new WayPoint(705, 115),
                    new WayPoint(729, 168),
                    new WayPoint(739, 303),
                    new WayPoint(742, 350),
                    new WayPoint(746, 376),
                    new WayPoint(746, 392),
                    new WayPoint(656, 405),
                    new WayPoint(526, 381),
                    new WayPoint(346, 352),
                    new WayPoint(226, 360),
                    new WayPoint(113, 376),
                    new WayPoint(62, 377),
                    new WayPoint(80, 332),
                    new WayPoint(233, 292),
                    new WayPoint(350, 260),
                    new WayPoint(461, 214),
                    new WayPoint(560, 159),
                    new WayPoint(643, 103),
                    new WayPoint(687, 78),
                    new WayPoint(742, 50)
            },
            {
                    new WayPoint(17, 223),
                    new WayPoint(29, 223),
                    new WayPoint(90, 231),
                    new WayPoint(194, 235),
                    new WayPoint(252, 239),
                    new WayPoint(309, 239),
                    new WayPoint(348, 240),
                    new WayPoint(465, 238),
                    new WayPoint(501, 228),
                    new WayPoint(516, 194),
                    new WayPoint(498, 144),
                    new WayPoint(446, 110),
                    new WayPoint(375, 94),
                    new WayPoint(326, 116),
                    new WayPoint(294, 170),
                    new WayPoint(298, 204),
                    new WayPoint(324, 236),
                    new WayPoint(368, 255),
                    new WayPoint(462, 262),
                    new WayPoint(560, 253),
                    new WayPoint(640, 243),
                    new WayPoint(706, 235),
                    new WayPoint(736, 232),
                    new WayPoint(762, 228),
                    new WayPoint(780, 224)
            },
            {
                    new WayPoint(787, 235),
                    new WayPoint(667, 238),
                    new WayPoint(555, 238),
                    new WayPoint(455, 238),
                    new WayPoint(358, 238),
                    new WayPoint(309, 232),
                    new WayPoint(265, 206),
                    new WayPoint(266, 160),
                    new WayPoint(294, 103),
                    new WayPoint(380, 75),
                    new WayPoint(430, 88),
                    new WayPoint(483, 131),
                    new WayPoint(488, 170),
                    new WayPoint(453, 224),
                    new WayPoint(380, 245),
                    new WayPoint(284, 246),
                    new WayPoint(207, 238),
                    new WayPoint(135, 237),
                    new WayPoint(61, 237),
                    new WayPoint(25, 237),
                    new WayPoint(7, 237)
            },
            {
                    new WayPoint(11, 478),
                    new WayPoint(27, 479),
                    new WayPoint(56, 478),
                    new WayPoint(89, 477),
                    new WayPoint(142, 477),
                    new WayPoint(180, 476),
                    new WayPoint(207, 460),
                    new WayPoint(237, 422),
                    new WayPoint(258, 378),
                    new WayPoint(274, 335),
                    new WayPoint(287, 270),
                    new WayPoint(296, 186),
                    new WayPoint(305, 137),
                    new WayPoint(317, 88),
                    new WayPoint(324, 57),
                    new WayPoint(330, 45),
                    new WayPoint(361, 30),
                    new WayPoint(393, 32),
                    new WayPoint(421, 44),
                    new WayPoint(432, 67),
                    new WayPoint(438, 135),
                    new WayPoint(438, 198),
                    new WayPoint(435, 227),
                    new WayPoint(434, 275),
                    new WayPoint(434, 310),
                    new WayPoint(434, 353),
                    new WayPoint(441, 396),
                    new WayPoint(458, 430),
                    new WayPoint(476, 444),
                    new WayPoint(566, 471),
                    new WayPoint(631, 472),
                    new WayPoint(701, 476),
                    new WayPoint(745, 478),
                    new WayPoint(759, 478)
            },
            {
                    new WayPoint(790, 476),
                    new WayPoint(744, 474),
                    new WayPoint(685, 473),
                    new WayPoint(638, 473),
                    new WayPoint(590, 473),
                    new WayPoint(537, 473),
                    new WayPoint(507, 466),
                    new WayPoint(439, 443),
                    new WayPoint(417, 425),
                    new WayPoint(407, 377),
                    new WayPoint(399, 319),
                    new WayPoint(388, 251),
                    new WayPoint(387, 187),
                    new WayPoint(387, 140),
                    new WayPoint(384, 122),
                    new WayPoint(372, 92),
                    new WayPoint(360, 78),
                    new WayPoint(333, 88),
                    new WayPoint(295, 131),
                    new WayPoint(271, 194),
                    new WayPoint(258, 272),
                    new WayPoint(255, 328),
                    new WayPoint(250, 379),
                    new WayPoint(242, 404),
                    new WayPoint(231, 428),
                    new WayPoint(215, 455),
                    new WayPoint(181, 482),
                    new WayPoint(112, 484),
                    new WayPoint(59, 484),
                    new WayPoint(14, 478)
            },
            {
                    new WayPoint(327, 18),
                    new WayPoint(330, 97),
                    new WayPoint(330, 155),
                    new WayPoint(330, 227),
                    new WayPoint(333, 278),
                    new WayPoint(333, 315),
                    new WayPoint(335, 331),
                    new WayPoint(338, 365),
                    new WayPoint(338, 393),
                    new WayPoint(318, 420),
                    new WayPoint(297, 431),
                    new WayPoint(242, 438),
                    new WayPoint(207, 439),
                    new WayPoint(166, 439),
                    new WayPoint(121, 439),
                    new WayPoint(80, 439),
                    new WayPoint(56, 439),
                    new WayPoint(35, 439),
                    new WayPoint(19, 437)
            },
            {
                    new WayPoint(372, 26),
                    new WayPoint(371, 46),
                    new WayPoint(371, 121),
                    new WayPoint(371, 186),
                    new WayPoint(372, 233),
                    new WayPoint(371, 275),
                    new WayPoint(371, 310),
                    new WayPoint(371, 340),
                    new WayPoint(380, 384),
                    new WayPoint(384, 404),
                    new WayPoint(394, 420),
                    new WayPoint(420, 439),
                    new WayPoint(439, 445),
                    new WayPoint(473, 454),
                    new WayPoint(509, 460),
                    new WayPoint(530, 463),
                    new WayPoint(577, 463),
                    new WayPoint(639, 469),
                    new WayPoint(690, 477),
                    new WayPoint(706, 477),
                    new WayPoint(725, 477),
                    new WayPoint(752, 477),
                    new WayPoint(765, 473),
                    new WayPoint(776, 470)
            },
            {
                    new WayPoint(14, 20),
                    new WayPoint(125, 25),
                    new WayPoint(218, 29),
                    new WayPoint(283, 30),
                    new WayPoint(397, 38),
                    new WayPoint(488, 38),
                    new WayPoint(591, 38),
                    new WayPoint(680, 38),
                    new WayPoint(722, 38),
                    new WayPoint(753, 38),
                    new WayPoint(708, 86),
                    new WayPoint(609, 158),
                    new WayPoint(548, 200),
                    new WayPoint(457, 254),
                    new WayPoint(383, 291),
                    new WayPoint(308, 328),
                    new WayPoint(218, 370),
                    new WayPoint(152, 401),
                    new WayPoint(90, 433),
                    new WayPoint(69, 447),
                    new WayPoint(57, 450),
                    new WayPoint(12, 460),
                    new WayPoint(0, 462),
                    new WayPoint(177, 458),
                    new WayPoint(259, 455),
                    new WayPoint(344, 454),
                    new WayPoint(465, 454),
                    new WayPoint(555, 454),
                    new WayPoint(616, 454),
                    new WayPoint(648, 454),
                    new WayPoint(693, 456),
                    new WayPoint(752, 462),
                    new WayPoint(766, 462),
                    new WayPoint(778, 462)
            },
            {
                    new WayPoint(29, 455),
                    new WayPoint(136, 458),
                    new WayPoint(295, 458),
                    new WayPoint(395, 458),
                    new WayPoint(543, 458),
                    new WayPoint(653, 458),
                    new WayPoint(708, 458),
                    new WayPoint(733, 458),
                    new WayPoint(744, 458),
                    new WayPoint(759, 458),
                    new WayPoint(658, 415),
                    new WayPoint(530, 355),
                    new WayPoint(407, 290),
                    new WayPoint(311, 238),
                    new WayPoint(231, 194),
                    new WayPoint(157, 149),
                    new WayPoint(96, 106),
                    new WayPoint(85, 96),
                    new WayPoint(57, 71),
                    new WayPoint(28, 42),
                    new WayPoint(189, 40),
                    new WayPoint(364, 46),
                    new WayPoint(487, 46),
                    new WayPoint(588, 50),
                    new WayPoint(651, 50),
                    new WayPoint(694, 50),
                    new WayPoint(721, 50),
                    new WayPoint(746, 50),
                    new WayPoint(766, 50),
                    new WayPoint(791, 50)
            },
            {
                    new WayPoint(652, 14),
                    new WayPoint(627, 18),
                    new WayPoint(566, 20),
                    new WayPoint(502, 30),
                    new WayPoint(471, 46),
                    new WayPoint(466, 75),
                    new WayPoint(471, 110),
                    new WayPoint(491, 132),
                    new WayPoint(555, 168),
                    new WayPoint(627, 192),
                    new WayPoint(687, 210),
                    new WayPoint(713, 225),
                    new WayPoint(718, 238),
                    new WayPoint(699, 259),
                    new WayPoint(658, 279),
                    new WayPoint(602, 292),
                    new WayPoint(536, 305),
                    new WayPoint(478, 319),
                    new WayPoint(427, 341),
                    new WayPoint(416, 357),
                    new WayPoint(414, 380),
                    new WayPoint(415, 400),
                    new WayPoint(437, 425),
                    new WayPoint(509, 457),
                    new WayPoint(601, 476),
                    new WayPoint(688, 480),
                    new WayPoint(729, 472),
                    new WayPoint(746, 472),
                    new WayPoint(768, 473)
            },
            {
                    new WayPoint(342, 11),
                    new WayPoint(296, 23),
                    new WayPoint(239, 25),
                    new WayPoint(176, 25),
                    new WayPoint(131, 31),
                    new WayPoint(90, 48),
                    new WayPoint(58, 71),
                    new WayPoint(36, 94),
                    new WayPoint(27, 118),
                    new WayPoint(41, 148),
                    new WayPoint(84, 178),
                    new WayPoint(154, 187),
                    new WayPoint(215, 189),
                    new WayPoint(269, 194),
                    new WayPoint(315, 215),
                    new WayPoint(344, 231),
                    new WayPoint(357, 249),
                    new WayPoint(319, 280),
                    new WayPoint(241, 286),
                    new WayPoint(195, 286),
                    new WayPoint(137, 287),
                    new WayPoint(109, 294),
                    new WayPoint(68, 326),
                    new WayPoint(41, 364),
                    new WayPoint(22, 405),
                    new WayPoint(21, 418),
                    new WayPoint(60, 448),
                    new WayPoint(92, 463),
                    new WayPoint(163, 490),
                    new WayPoint(225, 495),
                    new WayPoint(283, 494),
                    new WayPoint(328, 492),
                    new WayPoint(408, 495),
                    new WayPoint(531, 497),
                    new WayPoint(637, 501),
                    new WayPoint(706, 500),
                    new WayPoint(739, 500),
                    new WayPoint(754, 497),
                    new WayPoint(780, 497),
                    new WayPoint(791, 496)
            },
            {
                    new WayPoint(21, 474),
                    new WayPoint(116, 472),
                    new WayPoint(203, 463),
                    new WayPoint(242, 446),
                    new WayPoint(266, 412),
                    new WayPoint(274, 392),
                    new WayPoint(262, 362),
                    new WayPoint(196, 293),
                    new WayPoint(111, 258),
                    new WayPoint(46, 230),
                    new WayPoint(14, 213),
                    new WayPoint(7, 200),
                    new WayPoint(10, 183),
                    new WayPoint(46, 149),
                    new WayPoint(89, 134),
                    new WayPoint(183, 120),
                    new WayPoint(261, 120),
                    new WayPoint(306, 108),
                    new WayPoint(323, 94),
                    new WayPoint(326, 80),
                    new WayPoint(321, 63),
                    new WayPoint(301, 38),
                    new WayPoint(252, 11),
                    new WayPoint(218, 7),
                    new WayPoint(124, 6),
                    new WayPoint(64, 10),
                    new WayPoint(42, 11),
                    new WayPoint(7, 11)
            },
            {
                    new WayPoint(13, 27),
                    new WayPoint(12, 60),
                    new WayPoint(12, 92),
                    new WayPoint(22, 141),
                    new WayPoint(43, 170),
                    new WayPoint(57, 178),
                    new WayPoint(95, 179),
                    new WayPoint(136, 165),
                    new WayPoint(176, 133),
                    new WayPoint(186, 83),
                    new WayPoint(185, 69),
                    new WayPoint(185, 42),
                    new WayPoint(185, 27),
                    new WayPoint(192, 77),
                    new WayPoint(203, 145),
                    new WayPoint(210, 168),
                    new WayPoint(235, 198),
                    new WayPoint(254, 206),
                    new WayPoint(319, 214),
                    new WayPoint(355, 176),
                    new WayPoint(377, 136),
                    new WayPoint(378, 114),
                    new WayPoint(381, 83),
                    new WayPoint(383, 67),
                    new WayPoint(383, 56),
                    new WayPoint(397, 105),
                    new WayPoint(405, 143),
                    new WayPoint(418, 170),
                    new WayPoint(429, 184),
                    new WayPoint(451, 200),
                    new WayPoint(476, 203),
                    new WayPoint(508, 195),
                    new WayPoint(533, 138),
                    new WayPoint(538, 103),
                    new WayPoint(540, 82),
                    new WayPoint(540, 67),
                    new WayPoint(540, 55),
                    new WayPoint(545, 72),
                    new WayPoint(563, 151),
                    new WayPoint(576, 172),
                    new WayPoint(583, 185),
                    new WayPoint(594, 195),
                    new WayPoint(619, 206),
                    new WayPoint(660, 200),
                    new WayPoint(714, 159),
                    new WayPoint(737, 131),
                    new WayPoint(747, 115),
                    new WayPoint(778, 60),
                    new WayPoint(786, 34),
                    new WayPoint(787, 17)
            },
            {
                    new WayPoint(779, 103),
                    new WayPoint(766, 139),
                    new WayPoint(711, 166),
                    new WayPoint(590, 178),
                    new WayPoint(545, 173),
                    new WayPoint(518, 124),
                    new WayPoint(515, 81),
                    new WayPoint(515, 63),
                    new WayPoint(514, 50),
                    new WayPoint(518, 76),
                    new WayPoint(497, 174),
                    new WayPoint(487, 190),
                    new WayPoint(439, 186),
                    new WayPoint(381, 106),
                    new WayPoint(377, 57),
                    new WayPoint(377, 35),
                    new WayPoint(375, 65),
                    new WayPoint(359, 144),
                    new WayPoint(322, 200),
                    new WayPoint(273, 210),
                    new WayPoint(214, 167),
                    new WayPoint(178, 96),
                    new WayPoint(175, 54),
                    new WayPoint(172, 129),
                    new WayPoint(159, 156),
                    new WayPoint(123, 173),
                    new WayPoint(102, 178),
                    new WayPoint(76, 176),
                    new WayPoint(34, 119),
                    new WayPoint(21, 88),
                    new WayPoint(12, 66),
                    new WayPoint(11, 41),
                    new WayPoint(11, 23)
            },
            {
                    new WayPoint(6, 261),
                    new WayPoint(141, 254),
                    new WayPoint(267, 233),
                    new WayPoint(342, 213),
                    new WayPoint(442, 178),
                    new WayPoint(491, 155),
                    new WayPoint(544, 127),
                    new WayPoint(598, 99),
                    new WayPoint(656, 75),
                    new WayPoint(695, 56),
                    new WayPoint(730, 40),
                    new WayPoint(754, 28),
                    new WayPoint(771, 18)
            },
            {
                    new WayPoint(799, 248),
                    new WayPoint(767, 256),
                    new WayPoint(660, 258),
                    new WayPoint(528, 236),
                    new WayPoint(433, 209),
                    new WayPoint(338, 165),
                    new WayPoint(252, 123),
                    new WayPoint(185, 93),
                    new WayPoint(131, 66),
                    new WayPoint(89, 43),
                    new WayPoint(75, 37),
                    new WayPoint(58, 31),
                    new WayPoint(42, 26),
                    new WayPoint(27, 20)
            },
            {
                    new WayPoint(10, 477),
                    new WayPoint(27, 440),
                    new WayPoint(89, 326),
                    new WayPoint(144, 239),
                    new WayPoint(207, 180),
                    new WayPoint(282, 145),
                    new WayPoint(391, 143),
                    new WayPoint(584, 200),
                    new WayPoint(613, 222),
                    new WayPoint(677, 307),
                    new WayPoint(702, 354),
                    new WayPoint(742, 415),
                    new WayPoint(758, 434),
                    new WayPoint(799, 484),
                    new WayPoint(799, 495)
            },
            {
                    new WayPoint(768, 450),
                    new WayPoint(673, 325),
                    new WayPoint(573, 211),
                    new WayPoint(501, 143),
                    new WayPoint(477, 130),
                    new WayPoint(410, 127),
                    new WayPoint(321, 160),
                    new WayPoint(242, 216),
                    new WayPoint(182, 283),
                    new WayPoint(135, 348),
                    new WayPoint(99, 393),
                    new WayPoint(75, 424),
                    new WayPoint(62, 442),
                    new WayPoint(44, 460)
            },
            {
                    new WayPoint(793, 13),
                    new WayPoint(765, 26),
                    new WayPoint(699, 59),
                    new WayPoint(627, 123),
                    new WayPoint(593, 208),
                    new WayPoint(584, 270),
                    new WayPoint(597, 353),
                    new WayPoint(634, 424),
                    new WayPoint(665, 445),
                    new WayPoint(749, 475),
                    new WayPoint(774, 481)
            },
            {
                    new WayPoint(7, 21),
                    new WayPoint(42, 38),
                    new WayPoint(136, 117),
                    new WayPoint(193, 205),
                    new WayPoint(222, 289),
                    new WayPoint(223, 349),
                    new WayPoint(189, 418),
                    new WayPoint(136, 453),
                    new WayPoint(93, 468),
                    new WayPoint(31, 480),
                    new WayPoint(6, 484)
            },
            {
                    new WayPoint(13, 299),
                    new WayPoint(90, 302),
                    new WayPoint(319, 305),
                    new WayPoint(499, 302),
                    new WayPoint(597, 302),
                    new WayPoint(644, 303),
                    new WayPoint(710, 294),
                    new WayPoint(737, 289),
                    new WayPoint(753, 283),
                    new WayPoint(709, 247),
                    new WayPoint(613, 226),
                    new WayPoint(487, 224),
                    new WayPoint(346, 224),
                    new WayPoint(218, 224),
                    new WayPoint(135, 224),
                    new WayPoint(73, 224),
                    new WayPoint(52, 224),
                    new WayPoint(36, 226),
                    new WayPoint(24, 228),
                    new WayPoint(13, 229)
            },
            {
                    new WayPoint(724, 195),
                    new WayPoint(619, 194),
                    new WayPoint(487, 187),
                    new WayPoint(370, 189),
                    new WayPoint(255, 189),
                    new WayPoint(140, 190),
                    new WayPoint(116, 198),
                    new WayPoint(80, 214),
                    new WayPoint(66, 218),
                    new WayPoint(57, 239),
                    new WayPoint(107, 257),
                    new WayPoint(354, 296),
                    new WayPoint(496, 283),
                    new WayPoint(591, 278),
                    new WayPoint(634, 278),
                    new WayPoint(692, 280),
                    new WayPoint(713, 280),
                    new WayPoint(731, 280),
                    new WayPoint(756, 280),
                    new WayPoint(770, 281)
            }

    };

}
