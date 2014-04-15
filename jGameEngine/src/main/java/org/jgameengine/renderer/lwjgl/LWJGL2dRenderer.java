package org.jgameengine.renderer.lwjgl;

import org.apache.log4j.Logger;
import org.jgameengine.common.comparators.ZSortedRenderableComparator;
import org.jgameengine.engine.Engine;
import org.jgameengine.engine.EngineLocator;
import org.jgameengine.renderer.Renderable;
import org.jgameengine.renderer.Renderer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.lwjgl.opengl.EXTFramebufferObject.glBindFramebufferEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glFramebufferTexture2DEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glGenFramebuffersEXT;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetShader;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUseProgram;

public class LWJGL2dRenderer
        implements Renderer {

    private static final Logger logger = Logger.getLogger(LWJGL2dRenderer.class.getName());

    private List<Renderable> renderables = new LinkedList<Renderable>();

    private int screenWidth;
    private int screenHeight;

    private int fps;
    private float lastFpsTime;

    private Engine engine;

    private String gameName = "Game";

    private boolean showFramerate = false;

    private boolean useShaders;

    private boolean shaderCapable = false;

    private int vertexShaderID;
    private int fragmentShaderID;

    private int shaderProgramID;

    private String vertexShaderFile;
    private String fragmentShaderFile;

    private int fboID;
    private int fboTexID;

    private boolean fboSupported = false;

    protected boolean sync;
    protected int syncRate = 60;

    private float clearColorR = 0.0f;
    private float clearColorG = 0.0f;
    private float clearColorB = 0.0f;
    private float clearColorA = 0.0f;

    private int peturbLocation;
    private FloatBuffer peturbBuf;
    private int currentTimeLocation;

    private ZSortedRenderableComparator zSorter = new ZSortedRenderableComparator();

    public void init() {
        try {
            logger.debug("Setting display mode to " + screenWidth + ", " + screenHeight);
            Display.setDisplayMode(new DisplayMode(screenWidth, screenHeight));
            logger.debug("Created display.");
            Display.setTitle(gameName);
        } catch (Exception e) {
            logger.error("Failed to create display due to " + e);
        }

        try {
            Display.create();
            logger.debug("Created OpenGL.");

        } catch (Exception e) {
            logger.error("Failed to create OpenGL due to " + e);
            System.exit(1);
        }

        engine = EngineLocator.getEngine();

        initGl();
    }

    public void shutdown() {

        Display.destroy();
    }

    private void initGl() {
        glEnable(GL11.GL_TEXTURE_2D);

        glShadeModel(GL_SMOOTH);

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);

        glClearColor(clearColorR, clearColorG, clearColorB, clearColorA);
        glClearDepth(1);

        glViewport(0, 0, screenWidth, screenHeight);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        GL11.glOrtho(0, screenWidth, screenHeight, 0, 1, -1);

        glEnable(GL11.GL_BLEND);
        glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        initShaders();

        initFBO();
    }

    private void initFBO() {

        if (!shaderCapable) {
            logger.debug("shaders not supported, so won't even try FBO");
            return;
        }

        fboSupported = GLContext.getCapabilities().GL_EXT_framebuffer_object;
        if (!fboSupported) {
            logger.debug("FBO not supported");
            return;
        }
        logger.debug("FBO supported, initializing");

        IntBuffer fboCreateBuf = BufferUtils.createIntBuffer(1);
        glGenFramebuffersEXT(fboCreateBuf);
        fboID = fboCreateBuf.get(0);

        IntBuffer texCreateBuf = BufferUtils.createIntBuffer(1);
        glGenTextures(texCreateBuf);
        fboTexID = texCreateBuf.get(0);

        glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, fboID);

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, fboTexID);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB8, getScreenWidth(), getScreenHeight(), 0,
                GL11.GL_RGB, GL11.GL_INT, (IntBuffer) null);
        glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,
                EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT,
                GL11.GL_TEXTURE_2D, fboTexID, 0);

        try {
            checkFBO();
        } catch (Exception e) {
            logger.error("error setting up FBO", e);
            fboSupported = false;
            return;
        }

        glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
    }

    private void checkFBO() throws Exception {
        int frameBuffer = EXTFramebufferObject.glCheckFramebufferStatusEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT);
        switch (frameBuffer) {
            case EXTFramebufferObject.GL_FRAMEBUFFER_COMPLETE_EXT:
                break;
            case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT:
                throw new Exception("FrameBuffer: " + fboID
                        + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT exception");
            case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT:
                throw new Exception("FrameBuffer: " + fboID
                        + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT exception");
            case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT:
                throw new Exception("FrameBuffer: " + fboID
                        + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT exception");
            case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT:
                throw new Exception("FrameBuffer: " + fboID
                        + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT exception");
            case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT:
                throw new Exception("FrameBuffer: " + fboID
                        + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT exception");
            case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT:
                throw new Exception("FrameBuffer: " + fboID
                        + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT exception");
            default:
                throw new Exception("Unexpected reply from glCheckFramebufferStatusEXT: " + frameBuffer);
        }
    }

    private void initShaders() {

        if (!isUseShaders()) {
            return;
        }

        IntBuffer pBuf = BufferUtils.createIntBuffer(1);

        if (GLContext.getCapabilities().OpenGL20) {
            logger.debug("card supports GL20");
            shaderCapable = true;
        } else {
            logger.debug("card does not support GL20");
            return;
        }

        vertexShaderID = glCreateShader(GL20.GL_VERTEX_SHADER);
        logger.debug("vertexShaderID = " + vertexShaderID);
        fragmentShaderID = glCreateShader(GL20.GL_FRAGMENT_SHADER);
        logger.debug("fragmentShaderID = " + fragmentShaderID);

        try {

            glShaderSource(vertexShaderID, getProgramCode(getVertexShaderFile()));
            glCompileShader(vertexShaderID);
            printLogInfo(vertexShaderID);
            glGetShader(vertexShaderID, GL20.GL_COMPILE_STATUS, pBuf);
            logger.debug("vertex shader compile status : " + pBuf.get(0));

            glShaderSource(fragmentShaderID, getProgramCode(getFragmentShaderFile()));
            glCompileShader(fragmentShaderID);
            printLogInfo(fragmentShaderID);
            glGetShader(fragmentShaderID, GL20.GL_COMPILE_STATUS, pBuf);
            logger.debug("fragment shader compile status : " + pBuf.get(0));

            shaderProgramID = glCreateProgram();
            glAttachShader(shaderProgramID, vertexShaderID);
            glAttachShader(shaderProgramID, fragmentShaderID);

            glLinkProgram(shaderProgramID);

            initShaderLocations();

        } catch (IOException e) {
            logger.error("error while initializing shaders, disabling", e);
            shaderCapable = false;
        }
    }

    private void initShaderLocations() {
        peturbLocation = getUniformLocation(shaderProgramID, "peturb");
        peturbBuf = BufferUtils.createFloatBuffer(3);
        peturbBuf.put(1).put(2).put(3);
        peturbBuf.flip();

        currentTimeLocation = getUniformLocation(shaderProgramID, "currentTime");
    }

    private int getUniformLocation(int shaderProgramID, String uniformName) {
        ByteBuffer fBuf = BufferUtils.createByteBuffer(uniformName.length() + 1);

        int length = uniformName.length();

        char[] charArray = new char[length];
        uniformName.getChars(0, length, charArray, 0);

        for (int i = 0; i < length; i++) {
            fBuf.put((byte) charArray[i]);
        }
        fBuf.put((byte) 0);
        fBuf.flip();
        GL20.glGetUniformLocation(shaderProgramID, fBuf);

        int location = GL20.glGetUniformLocation(shaderProgramID, fBuf);

        if (location == -1) {
            throw new IllegalArgumentException("The uniform \"" + uniformName + "\" does not exist in the Shader Program.");
        }

        return location;
    }

    private void printLogInfo(int obj) {
        IntBuffer iVal = BufferUtils.createIntBuffer(1);
        GL20.glGetShader(obj, GL20.GL_INFO_LOG_LENGTH, iVal);

        int length = iVal.get();
        System.out.println("Info log length:" + length);
        if (length > 0) {
            ByteBuffer infoLog = BufferUtils.createByteBuffer(length);
            iVal.flip();
            GL20.glGetShaderInfoLog(obj, iVal, infoLog);
            byte[] infoBytes = new byte[length];
            infoLog.get(infoBytes);
            String out = new String(infoBytes);

            System.out.println("Info log:\n" + out);
        }
    }

    public void renderFrame() {

        frameInitFBO();

        glClear(GL_COLOR_BUFFER_BIT);

        for (Renderable renderable : renderables) {
            if (renderable.enabled()) {
                glPushMatrix();
                renderable.render();
                glPopMatrix();
            }
        }

        frameFinishFBO();

        updateFpsCounter();

        LWJGLCachingTextureBinder.reset();
    }

    private void updateFpsCounter() {
        lastFpsTime += engine.getTimeDelta();
        fps++;

        if (lastFpsTime > 1) {
            if (showFramerate) {
                Display.setTitle(gameName + " (FPS: " + fps + ")");
            }
            lastFpsTime = 0;
            fps = 0;
        }
    }

    private void frameInitFBO() {

        if (!fboSupported) {
            return;
        }

        glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, fboID);

        GL11.glPushAttrib(GL11.GL_VIEWPORT_BIT);
        GL11.glViewport(0, 0, screenWidth, screenHeight);
    }

    private void frameFinishFBO() {

        if (fboSupported && shaderCapable) {

            glUseProgram(shaderProgramID);
            updateUniformValues();

            glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
            GL11.glPopAttrib();

            glClear(GL_COLOR_BUFFER_BIT);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, fboTexID);

            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex2f(0, 0);
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex2f(0, getScreenHeight());
            GL11.glTexCoord2f(1, 0);
            GL11.glVertex2f(getScreenWidth(), getScreenHeight());
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex2f(getScreenWidth(), 0);
            GL11.glEnd();

            glUseProgram(0);
        }
    }

    float[] currentPeturb = new float[]{0.0f, 0.0f, -1.0f};

    private void updateUniformValues() {

        if (shaderCapable) {
            glUniform3f(this.peturbLocation, currentPeturb[0], currentPeturb[1], currentPeturb[2]);
            glUniform1f(currentTimeLocation, engine.getCurrentTime());
        }
    }

    public void addWarp(int x, int y) {
        currentPeturb[0] = (1.0f / 640.0f) * x;
        currentPeturb[1] = 1 - ((1.0f / 480.0f) * y);
        currentPeturb[2] = engine.getCurrentTime();
    }

    public void removeWarp() {
        currentPeturb[2] = -1.0f;
    }

    public void finishFrame() {

        Display.update();

        if (sync) {
            Display.sync(syncRate);
        }
    }

    public void addRenderable(Renderable newRenderable) {
        renderables.add(newRenderable);
        Collections.sort(renderables, zSorter);
    }

    public void removeRenderable(Renderable oldRenderable) {
        renderables.remove(oldRenderable);
    }

    public void removeRenderables(Set oldRenderables) {
        renderables.removeAll(oldRenderables);
    }

    private static ByteBuffer getProgramCode(String filename) throws IOException {
        ClassLoader fileLoader = LWJGL2dRenderer.class.getClassLoader();
        InputStream fileInputStream = fileLoader.getResourceAsStream(filename);
        byte[] shaderCode;

        if (fileInputStream == null) {
            fileInputStream = new FileInputStream(filename);
        }
        DataInputStream dataStream = new DataInputStream(fileInputStream);
        dataStream.readFully(shaderCode = new byte[fileInputStream.available()]);
        fileInputStream.close();
        dataStream.close();

        ByteBuffer shaderPro = BufferUtils.createByteBuffer(shaderCode.length);

        shaderPro.put(shaderCode);
        shaderPro.flip();

        return shaderPro;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public boolean isShowFramerate() {
        return showFramerate;
    }

    public void setShowFramerate(boolean showFramerate) {
        this.showFramerate = showFramerate;
    }

    public boolean isShaderCapable() {
        return shaderCapable;
    }

    public void setShaderCapable(boolean shaderCapable) {
        this.shaderCapable = shaderCapable;
    }

    public boolean isUseShaders() {
        return useShaders;
    }

    public void setUseShaders(boolean useShaders) {
        this.useShaders = useShaders;
    }

    public String getVertexShaderFile() {
        return vertexShaderFile;
    }

    public void setVertexShaderFile(String vertexShaderFile) {
        this.vertexShaderFile = vertexShaderFile;
    }

    public String getFragmentShaderFile() {
        return fragmentShaderFile;
    }

    public void setFragmentShaderFile(String fragmentShaderFile) {
        this.fragmentShaderFile = fragmentShaderFile;
    }

    public boolean isSync() {
        return sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }

    public int getSyncRate() {
        return syncRate;
    }

    public void setSyncRate(int syncRate) {
        this.syncRate = syncRate;
    }

    public float getClearColorR() {
        return clearColorR;
    }

    public void setClearColorR(float clearColorR) {
        this.clearColorR = clearColorR;
    }

    public float getClearColorG() {
        return clearColorG;
    }

    public void setClearColorG(float clearColorG) {
        this.clearColorG = clearColorG;
    }

    public float getClearColorB() {
        return clearColorB;
    }

    public void setClearColorB(float clearColorB) {
        this.clearColorB = clearColorB;
    }

    public float getClearColorA() {
        return clearColorA;
    }

    public void setClearColorA(float clearColorA) {
        this.clearColorA = clearColorA;
    }
}
