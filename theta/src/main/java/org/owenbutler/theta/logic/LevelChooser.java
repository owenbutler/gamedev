package org.owenbutler.theta.logic;

import org.jgameengine.engine.Engine;
import org.jgameengine.input.MouseListener;
import org.jgameengine.renderer.HudCallback;

public class LevelChooser implements MouseListener, HudCallback {

    private Engine engine;

    private HudManager hudManager;

    private int mouseX;
    private int mouseY;

    private boolean enabled;

    private GameLogic gameLogic;

    int selectedLevel;

    public LevelChooser(Engine engine) {
        this.engine = engine;

        hudManager = (HudManager) engine.getHudManager();

        gameLogic = (GameLogic) engine.getRegisteredGameService("game");

    }

    public void mouseEvent(int x, int y) {
        mouseX = x;
        mouseY = y;

        selectedLevel = findSelectedLevel();
    }

    public void button0Down() {
    }

    public void button0Up() {
        if (enabled) {

            gameLogic.levelStartRequested(selectedLevel);
        }
    }

    private int findSelectedLevel() {

        if (mouseY > 130 && mouseY < 165) {

            if (mouseX > 100 && mouseX < 500) {
                int x = mouseX - 100;

                return 1 + x / 40;
            }
        }

        return 1;
    }

    public void button1Down() {
    }

    public void button1Up() {
    }

    public void button2Down() {
    }

    public void button2Up() {
    }

    public void initShowLevelChoices() {

        // register us with the hud manager
        hudManager.registerHudCallback(this);

        // add us as a mouse listener
        engine.getInputManager().addMouseListener(this);

        enabled = true;
    }

    public void hideLevelChoices() {

        hudManager.deRegisterHudCallback(this);

        engine.getInputManager().removeMouseListener(this);

        enabled = false;
    }

    public void render() {
        hudManager.drawTextAt(100, 100, "select your level");

        for (int i = 1; i <= 10; ++i) {
            hudManager.drawTextAt(60 + (i * 40), 130, selectedLevel == i ? ">" + i : "" + i);
        }
    }
}
