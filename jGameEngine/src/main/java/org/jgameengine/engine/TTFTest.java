package org.jgameengine.engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glViewport;

public class TTFTest {

    public static void main(String[] args) throws LWJGLException, FontFormatException, IOException {

        Font font;

        TrueTypeFont trueTypeFont;

        initGL();

        font = new Font("Verdana", Font.BOLD, 30);
        trueTypeFont = new TrueTypeFont(font, false);

        while (true) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            trueTypeFont.drawString(20.0f, 20.0f, "not Antialised", Color.white);

            Display.update();

            if (Display.isCloseRequested()) {
                System.exit(0);
            }
        }

    }

    private static void initGL() throws LWJGLException {

        int screenWidth = 640;
        int screenHeight = 480;

        Display.setDisplayMode(new DisplayMode(screenWidth, screenHeight));
        Display.create();

        // enable texture mapping
        glEnable(GL11.GL_TEXTURE_2D);

        // enable smooth shading
        glShadeModel(GL_SMOOTH);

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);

        // set background colour
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClearDepth(1);

        glViewport(0, 0, screenWidth, screenHeight);

        // set ortho 2d screen
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        // gluOrtho2D(0, SCREEN_WIDTH, 0, SCREEN_HEIGHT);
        // set viewpoint
        GL11.glOrtho(0, screenWidth, screenHeight, 0, 1, -1);
//        gluOrtho2D(0, screenWidth, screenHeight, 0);

//        glMatrixMode(GL11.GL_MODELVIEW);

        // enable alpha blending
        glEnable(GL11.GL_BLEND);
        glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

}
