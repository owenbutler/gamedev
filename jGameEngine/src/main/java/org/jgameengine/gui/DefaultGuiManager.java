package org.jgameengine.gui;

import java.util.HashSet;
import java.util.Set;

public class DefaultGuiManager implements GuiManager {

    Set<GuiElement> guiElements = new HashSet<GuiElement>();

    public void showGui() {
        for (GuiElement guiElement : guiElements) {
            guiElement.enable();
        }
    }

    public void hideGui() {
        for (GuiElement guiElement : guiElements) {
            guiElement.disable();
        }
    }

    public void registerGuiElement(GuiElement element) {
        guiElements.add(element);
    }

    public void deRegisterGuiElement(GuiElement element) {
        guiElements.remove(element);
    }

    public void removeAllGuiElements() {
    }
}
