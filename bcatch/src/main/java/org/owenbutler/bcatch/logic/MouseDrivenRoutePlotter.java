package org.owenbutler.bcatch.logic;

import org.jgameengine.common.events.Event;
import org.jgameengine.engine.Engine;
import org.jgameengine.input.MouseListener;
import org.jgameengine.renderables.Particle;

import java.util.ArrayList;
import java.util.List;

public class MouseDrivenRoutePlotter implements MouseListener {

    protected int mouseX;
    protected int mouseY;

    protected Engine gameEngine;
    protected boolean plotting;

    //    int[] lastPoint = new int[2];
    WayPoint lastPoint = new WayPoint(0, 0);

    List<WayPoint> points = new ArrayList<>();

    public MouseDrivenRoutePlotter(Engine gameEngine) {
        this.gameEngine = gameEngine;

        gameEngine.getEventHandler().addEventInLoop(this, 0.1f, 0.1f, new Event() {
            public void trigger() {
                checkPosition();
            }
        });
    }

    public void mouseEvent(int x, int y) {

        mouseX = x;
        mouseY = y;

    }

    private void checkPosition() {

        if (plotting) {

            if (farEnoughAway()) {
                plotNextPoint();
            }
        }
    }

    private void plotNextPoint() {

        lastPoint = new WayPoint(mouseX, mouseY);

        points.add(lastPoint);

        Particle particle = new Particle(mouseX, mouseY, 400.0f);
        particle.setWidth(4.0f);
        particle.setHeight(4.0f);
        gameEngine.addGameObject(particle);
    }

    private boolean farEnoughAway() {
        return Math.abs(mouseX - lastPoint.x) > 10 || Math.abs(mouseY - lastPoint.y) > 10;
    }

    private void printPlot() {

        System.out.println("{");
        for (WayPoint point : points) {
            System.out.println("new WayPoint(" + point.x + ", " + point.y + "),");
        }
        System.out.println("},");

    }

    public void button0Down() {

        plotting = true;

    }

    public void button0Up() {
        plotting = false;

        printPlot();

        points.clear();
    }

    public void button1Down() {

    }

    public void button1Up() {

    }

    public void button2Down() {

    }

    public void button2Up() {

    }
}
