package org.jgameengine.renderer.lwjgl;

import org.jgameengine.renderer.Surface2d;
import org.jgameengine.renderer.animation.AnimationBufferCache;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;

public class LWJGLSurface2d
        implements Surface2d {

    private float width;

    private float height;

    private float x;

    private float y;

    private int textureId;

    private float[] color = {1.0f, 1.0f, 1.0f, 1.0f};

    private static int m_displayList;

    private float rotate;

    private static FloatBuffer interleavedVertexAndTextureBuffer;

    static {
        interleavedVertexAndTextureBuffer = BufferUtils.createFloatBuffer(20);
        interleavedVertexAndTextureBuffer.put(new float[]{
                1.0f, 1.0f,
                0.5f, 0.5f, 0.0f,
                0.0f, 1.0f,
                -0.5f, 0.5f, 0.0f,
                1.0f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.0f, 0.0f,
                -0.5f, -0.5f, 0.0f
        }).flip();
    }

    private boolean animated = false;

    private int animationTileIndex;

    public FloatBuffer[] animatedInterleavedArrays;

    static {
        m_displayList = glGenLists(1);

        glNewList(m_displayList, GL11.GL_COMPILE);
        glBegin(GL11.GL_TRIANGLE_STRIP);
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(0.5f, 0.5f, 0.0f);
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(-0.5f, 0.5f, 0.0f);
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(0.5f, -0.5f, 0.0f);
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(-0.5f, -0.5f, 0.0f);
        glEnd();
        glEndList();
    }

    public void initSurface(String textureName, float x, float y, float width, float height) {
        textureId = LWJGLTextureCache.getTextureId(textureName);
        setSize(width, height);
        setXY(x, y);
    }

    public void render() {
        LWJGLCachingTextureBinder.bindTexture(GL_TEXTURE_2D, textureId);
        glTranslatef(x, y, 0.0f);
        glRotatef(rotate, 0.0f, 0.0f, 1.0f);
        glScalef(width, height, 1.0f);
        GL11.glColor4f(color[0], color[1], color[2], color[3]);

        if (animated) {
            GL11.glInterleavedArrays(GL11.GL_T2F_V3F, 0, animatedInterleavedArrays[animationTileIndex]);
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
        } else {
            glCallList(m_displayList);
        }

    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void lookAt(int x, int y) {
        int adjacent = x - (int) this.x;
        int opposite = (int) this.y - y;

        if (opposite == 0) {
            opposite = 1;
        }

        double angleDeg = Math.toDegrees(Math.atan((float) Math.abs(adjacent) / (float) Math.abs(opposite)));

        if (adjacent > 0) {
            if (opposite < 0) {
                angleDeg = 180 - angleDeg;
            }
        } else {
            if (opposite < 0) {
                angleDeg = 180 + angleDeg;
            } else {
                angleDeg = 360 - angleDeg;
            }
        }

        rotate = (int) angleDeg;
    }

    public void setRotation(float degrees) {
        rotate = degrees;
    }

    public float getRotation() {
        return rotate;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setAlpha(float alpha) {
        color[3] = alpha;
    }

    public void enableAnimation(String animationDescriptor, int tilesHorizontal, int tilesVertical) {

        animated = true;
        animationTileIndex = 0;

        animatedInterleavedArrays = AnimationBufferCache.getAnimationFrames(tilesHorizontal, tilesVertical);
    }

    public void selectAnimationFrame(int index) {
        this.animationTileIndex = index;
    }

    public void setXY(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float[] getColor() {
        return color;
    }

    public void setColor(float[] color) {
        this.color = color;
    }

}
