package org.jgameengine.common.gameobjects;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.EngineConstants;
import org.jgameengine.common.events.Event;
import org.jgameengine.engine.Engine;
import org.jgameengine.engine.EngineLocator;
import org.jgameengine.renderer.Renderable;
import org.jgameengine.renderer.Surface2d;
import org.jgameengine.renderer.Surface2dFactory;

public abstract class BaseDrawableGameObject
        implements GameObject, Renderable, Collidable {

    protected Surface2d surface;

    protected float velX;
    protected float velY;

    protected float x;
    protected float y;

    protected float width;
    protected float height;

    protected float moveVelX;
    protected float moveVelY;

    protected float sortZ;

    private boolean blRender = true;

    protected float collisionWidth;
    protected float collisionHeight;

    protected float collisionRadius;

    protected float collisionRadiusSquared;

    protected Engine gameEngine = EngineLocator.getEngine();

    protected Object owner;

    protected boolean screenClipRemove = true;

    protected boolean clippedToScreen = false;

    protected boolean screenWrap = false;

    protected boolean collidable = false;

    protected boolean rotate;

    protected float rotationAccumulated;

    protected int autoRotateAmount;

    private float lastScaleTime;

    protected int autoScaleAmountX;

    protected int autoScaleAmountY;

    protected float fadeEndTime;

    protected float fadeStartTime;

    protected int startFrame;

    protected int endFrame;

    protected int frame;

    private int screenWidth, screenHeight;

    protected BaseDrawableGameObject(String surfaceName, float x, float y, float width, float height) {
        this(x, y);

        surface = Surface2dFactory.getSurface2d();
        surface.initSurface(surfaceName, x, y, width, height);
    }

    protected void initSurface(String surfaceName, float width, float height) {
        this.width = width;
        this.height = height;

        surface = Surface2dFactory.getSurface2d();
        surface.initSurface(surfaceName, x, y, width, height);
    }

    protected BaseDrawableGameObject(float x, float y) {
        this.x = x;
        this.y = y;

        screenWidth = gameEngine.getRenderer().getScreenWidth();
        screenHeight = gameEngine.getRenderer().getScreenHeight();
    }

    protected BaseDrawableGameObject() {
    }

    public abstract void think();

    public void baseDrawableThink() {
        move();

        if (isScreenClipRemove()) {
            doScreenClipRemove();
        }

        if (rotate) {
            doRotate();
        }

        if (screenWrap) {
            doScreenWrap();
        }
    }

    private void doScreenWrap() {

        int screenWidth = gameEngine.getRenderer().getScreenWidth();
        int screenHeight = gameEngine.getRenderer().getScreenHeight();
        if (x > screenWidth + width) {
            x = -width;
        } else if (x < -width) {
            x = screenWidth + width;
        } else if (y < -height) {
            y = screenHeight + height;
        } else if (y > screenHeight + height) {
            y = -height;
        }
    }

    private void doScreenClipRemove() {

        if ((x < -width) || (x > screenWidth + width) || (y < -height) || (y > screenHeight + height)) {
            gameEngine.removeGameObject(this);
        }
    }

    private void doRotate() {

        rotationAccumulated += ((float) autoRotateAmount) * (gameEngine.getTimeDelta());
        surface.setRotation(rotationAccumulated);
    }

    public void setScale(int scaleX, int scaleY) {
        autoScaleAmountX = scaleX;
        autoScaleAmountY = scaleY;
        lastScaleTime = gameEngine.getCurrentTime();

        gameEngine.getEventHandler().addEventInLoop(this, 0.0f, EngineConstants.SCALE_GRANULARITY, new Event() {
            public void trigger() {
                doScale();
            }
        });
    }

    private void doScale() {

        float timeDelta = gameEngine.getCurrentTime() - lastScaleTime;
        lastScaleTime = gameEngine.getCurrentTime();
        float scaleX = this.autoScaleAmountX * timeDelta;
        float scaleY = this.autoScaleAmountY * timeDelta;

        float w = surface.getWidth() + scaleX;
        float h = surface.getHeight() + scaleY;
        surface.setWidth(w < 0 ? 0 : w);
        surface.setHeight(h < 0 ? 0 : h);
    }

    public void setFadeAndRemove(float startTime, float fadeTime) {
        fadeStartTime = gameEngine.getCurrentTime() + startTime;
        fadeEndTime = fadeStartTime + fadeTime;

        gameEngine.getEventHandler().addEventIn(this, startTime, new Event() {
            public void trigger() {
                doFadeAndRemove();
            }
        });
    }

    public void setFadeAndRemove(float fadeTime) {
        setFadeAndRemove(0.0f, fadeTime);
    }

    private void doFadeAndRemove() {

        if (gameEngine.getCurrentTime() > fadeEndTime) {
            removeSelf();
            return;
        }

        gameEngine.getEventHandler().addEventIn(this, EngineConstants.FADE_GRANULARITY, new Event() {
            public void trigger() {
                doFadeAndRemove();
            }
        });

        // set our alpha so that it approaches zero (totally faded out) as our fadetime is elapsed
        float totalFadeTime = fadeEndTime - fadeStartTime;
        float timeThroughFade = gameEngine.getCurrentTime() - fadeStartTime;

        surface.setAlpha(1.0f - ((1.0f / totalFadeTime) * timeThroughFade));
    }

    public void removeSelf() {
        gameEngine.removeGameObject(this);
    }

    public void removeSelfIn(float seconds) {
        gameEngine.getEventHandler().addEventIn(this, seconds, new Event() {
            public void trigger() {
                removeSelf();
            }
        });
    }

    public boolean enabled() {
        return blRender;
    }

    public void render() {
        surface.render();
    }

    public void setCollisionBox(float width, float height) {
        this.collisionWidth = width;
        this.collisionHeight = height;
    }

    public float getMoveVelX() {
        return moveVelX;
    }

    public void setMoveVelX(float moveVelX) {
        this.moveVelX = moveVelX;
    }

    public float getMoveVelY() {
        return moveVelY;
    }

    public Surface2d getSurface() {
        return surface;
    }

    public void setSurface(Surface2d surface) {
        this.surface = surface;
    }

    public void setMoveVelY(float moveVelY) {
        this.moveVelY = moveVelY;
    }

    public boolean isBlRender() {
        return blRender;
    }

    public void setBlRender(boolean blRender) {
        this.blRender = blRender;
    }

    public boolean collidable() {
        return collidable;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
        surface.setX(this.x);
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
        surface.setY(this.y);
    }

    public void setXY(float x, float y) {
        this.x = x;
        this.y = y;
        surface.setXY(this.x, this.y);
    }

    public void incrementX(float amount) {
        x += amount;
        surface.setX(x);
    }

    public void incrementY(float amount) {
        y += amount;
        surface.setY(y);
    }

    public float getCollisionWidth() {
        return collisionWidth;
    }

    public void setWidth(float width) {
        this.width = width;
        surface.setWidth(this.width);
    }

    public float getCollisionHeight() {
        return collisionHeight;
    }

    public void setHeight(float height) {
        this.height = height;
        surface.setHeight(this.height);
    }

    public void incrementWidth(float amount) {
        width += amount;
        surface.setWidth(width);
    }

    public void incrementHeight(float amount) {
        height += amount;
        surface.setHeight(height);
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public void setVelXY(float velX, float velY) {
        this.velX = velX;
        this.velY = velY;
    }

    public void setVelXY(float[] velxy) {
        velX = velxy[0];
        velY = velxy[1];
    }

    public void setVelXYWithRandomMod(float[] velxy, int randomFactor) {
        int randomFactorBase = randomFactor * 2;

        velX = velxy[0] + RandomUtils.nextInt(randomFactorBase) - randomFactor;
        velY = velxy[1] + RandomUtils.nextInt(randomFactorBase) - randomFactor;
    }

    public void setVelXYRandom(int randomFactor) {
        int randomFactorBase = randomFactor * 2;

        velX = RandomUtils.nextInt(randomFactorBase) - randomFactor;
        if (velX == 0) {
            velX = 1;
        }
        velY = RandomUtils.nextInt(randomFactorBase) - randomFactor;
        if (velY == 0) {
            velY = 1;
        }
    }

    public void move() {
        float timeDelta = gameEngine.getTimeDelta();

        x += timeDelta * velX;
        y += timeDelta * velY;

        if (clippedToScreen) {
            clipToScreen();
        }
        surface.setXY(x, y);
    }

    protected void clipToScreen() {
        float heightCheck = height / 2;
        float widthCheck = width / 2;
        if (y < heightCheck) {
            y = heightCheck;
        } else {
            float bottomHeightCheck = gameEngine.getRenderer().getScreenHeight() - heightCheck;
            if (y > bottomHeightCheck) {
                y = bottomHeightCheck;
            }
        }

        if (x < widthCheck) {
            x = widthCheck;
        } else {
            float rightWidthCheck = gameEngine.getRenderer().getScreenWidth() - widthCheck;
            if (x > rightWidthCheck) {
                x = rightWidthCheck;
            }
        }
    }

    public boolean isClippedToScreen() {
        return clippedToScreen;
    }

    public void setClippedToScreen(boolean clippedToScreen) {
        this.clippedToScreen = clippedToScreen;
    }

    public void lookat(int x, int y) {
        surface.lookAt(x, y);
    }

    public void setRotation(float degrees) {
        surface.setRotation(degrees);
        this.rotationAccumulated = degrees;
    }

    public boolean isCollidable() {
        return collidable;
    }

    public void setCollidable(boolean collidable) {
        this.collidable = collidable;
    }

    public void collision(Collidable otherBody) {
    }

    public boolean isScreenClipRemove() {
        return screenClipRemove;
    }

    public void setScreenClipRemove(boolean screenClipRemove) {
        this.screenClipRemove = screenClipRemove;
    }

    public boolean isRotate() {
        return rotate;
    }

    public void setRotate(boolean rotate) {
        this.rotate = rotate;
    }

    public void setRotate(int amount) {
        this.rotate = true;
        this.autoRotateAmount = amount;
    }

    public Object getConnectionOwner() {
        return owner;
    }

    public void setOwner(Object owner) {
        this.owner = owner;
    }

    public float getCollisionRadius() {
        return collisionRadius;
    }

    public float getCollisionRadiusSquared() {
        return collisionRadiusSquared;
    }

    public void setCollisionRadius(float collisionRadius) {
        this.collisionRadius = collisionRadius;
        collisionRadiusSquared = collisionRadius * collisionRadius;
    }

    public boolean isOwner(Object other) {
        return other == getConnectionOwner();
    }

    public float getSortZ() {
        return sortZ;
    }

    public void setSortZ(float sortZ) {
        this.sortZ = sortZ;
    }

    protected void incrementAnimation() {
        if (frame == endFrame) {
            frame = startFrame;
        } else {
            ++frame;
        }
        getSurface().selectAnimationFrame(frame);
    }

    protected void incrementAnimationAndRemove() {
        if (frame >= endFrame) {
            removeSelf();
        }
        getSurface().selectAnimationFrame(++frame);
    }

    public void setRandomRotation() {
        setRotation((float) RandomUtils.nextInt(360));
    }

    public boolean offScreen() {

        int width, height;

        width = gameEngine.getRenderer().getScreenWidth();
        height = gameEngine.getRenderer().getScreenHeight();

        return x > width || x < 0 || y > height || y < 0;

    }

    public float[] getVelocityFacing(float facingX, float facingY, float maxSpeed) {
        float[] xy = new float[2];

        float x, y, c;
        x = facingX - this.x;
        y = facingY - this.y;

        c = (float) Math.sqrt((Math.abs(x) * Math.abs(x)) + (Math.abs(y) * Math.abs(y)));

        float fScale = maxSpeed / c;
        xy[0] = x * fScale;
        xy[1] = y * fScale;

        return xy;
    }

    public boolean isScreenWrap() {
        return screenWrap;
    }

    public void setScreenWrap(boolean screenWrap) {
        this.screenWrap = screenWrap;
    }

    protected float widthIncrement;
    protected float heightIncrement;

    public void setShrinkAndRemove(float time) {

        int num = (int) (30 * time);

        widthIncrement = -(width / (float) num);
        heightIncrement = -(height / (float) num);

        gameEngine.getEventHandler().addEventInLoop(this, time / (float) num, time / (float) num, new Event() {
            public void trigger() {

                incrementWidth(widthIncrement);
                incrementHeight(heightIncrement);

                if (height < 1 || width < 1) {
                    removeSelf();
                }
            }
        });
    }

    public boolean ignore(Collidable otherBody) {
        return false;
    }

    public void disable() {
    }

    public void enable() {
    }
}
