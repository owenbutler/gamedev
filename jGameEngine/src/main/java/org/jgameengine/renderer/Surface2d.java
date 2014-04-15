package org.jgameengine.renderer;

public interface Surface2d {

    void initSurface(String textureName, float x, float y, float width, float height);

    void render();

    void setSize(float width, float height);

    void setWidth(float width);

    void setHeight(float height);

    void setXY(float x, float y);

    void setX(float x);

    void setY(float y);

    void lookAt(int x, int y);

    void setRotation(float degrees);

    float getRotation();

    float getWidth();

    float getHeight();

    void setAlpha(float alpha);

    void enableAnimation(String animationDescriptor, int width, int height);

    void selectAnimationFrame(int index);
}
