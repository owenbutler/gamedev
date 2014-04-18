package org.jgameengine.renderer.lwjgl;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class LWJGLTextureCache {

    private static Map<String, Integer> textures = new HashMap<>();

    private static ColorModel glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
            new int[]{8, 8, 8, 8},
            true,
            false,
            ComponentColorModel.TRANSLUCENT,
            DataBuffer.TYPE_BYTE);

    private static ColorModel glColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
            new int[]{8, 8, 8, 0},
            false,
            false,
            ComponentColorModel.OPAQUE,
            DataBuffer.TYPE_BYTE);

    private static IntBuffer textureIDBuffer = BufferUtils.createIntBuffer(1);

    private static String textureNotFoundResourceName = "gfx/textureNotFound.png";

    public static int getTextureId(String path) {
        Integer textureId = textures.get(path);

        if (textureId == null) {
            try {
                textureId = getTexture(path);
            } catch (IOException e) {
                throw new RuntimeException("couldn't load texture :" + path, e);
            }

            textures.put(path, textureId);
        }

        return textureId;
    }

    public static int getTexture(String resourceName,
                                 int target,
                                 int dstPixelFormat,
                                 int minFilter,
                                 int magFilter) throws IOException {
        int srcPixelFormat;

        int textureID = createTextureID();

        GL11.glBindTexture(target, textureID);

        BufferedImage bufferedImage = loadImage(resourceName);

        if (bufferedImage.getColorModel().hasAlpha()) {
            srcPixelFormat = GL11.GL_RGBA;
        } else {
            srcPixelFormat = GL11.GL_RGB;
        }

        ByteBuffer textureBuffer = convertImageData(bufferedImage);

        if (target == GL11.GL_TEXTURE_2D) {
            GL11.glTexParameteri(target, GL11.GL_TEXTURE_MIN_FILTER, minFilter);
            GL11.glTexParameteri(target, GL11.GL_TEXTURE_MAG_FILTER, magFilter);
        }

        GL11.glTexImage2D(target,
                0,
                dstPixelFormat,
                get2Fold(bufferedImage.getWidth()),
                get2Fold(bufferedImage.getHeight()),
                0,
                srcPixelFormat,
                GL11.GL_UNSIGNED_BYTE,
                textureBuffer);

        return textureID;
    }

    public static int getTexture(String resourceName) throws IOException {
        int id;
        try {
            id = getTexture(resourceName, GL11.GL_TEXTURE_2D, GL11.GL_RGBA, GL11.GL_LINEAR, GL11.GL_LINEAR);
        } catch (IOException e) {
            try {
                id = getTexture(textureNotFoundResourceName, GL11.GL_TEXTURE_2D, GL11.GL_RGBA, GL11.GL_LINEAR, GL11.GL_LINEAR);
            } catch (IOException e1) {
                throw e;
            }
        }

        return id;
    }

    private static int get2Fold(int fold) {
        int ret = 2;
        while (ret < fold) {
            ret *= 2;
        }
        return ret;
    }

    private static ByteBuffer convertImageData(BufferedImage bufferedImage) {
        ByteBuffer imageBuffer;
        WritableRaster raster;
        BufferedImage texImage;

        int texWidth = 2;
        int texHeight = 2;

        while (texWidth < bufferedImage.getWidth()) {
            texWidth *= 2;
        }
        while (texHeight < bufferedImage.getHeight()) {
            texHeight *= 2;
        }

        if (bufferedImage.getColorModel().hasAlpha()) {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 4, null);
            texImage = new BufferedImage(glAlphaColorModel, raster, false, new Hashtable());
        } else {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 3, null);
            texImage = new BufferedImage(glColorModel, raster, false, new Hashtable());
        }

        Graphics g = texImage.getGraphics();
        g.setColor(new Color(0f, 0f, 0f, 0f));
        g.fillRect(0, 0, texWidth, texHeight);
        g.drawImage(bufferedImage, 0, 0, null);

        byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData();

        imageBuffer = ByteBuffer.allocateDirect(data.length);
        imageBuffer.order(ByteOrder.nativeOrder());
        imageBuffer.put(data, 0, data.length);
        imageBuffer.flip();

        return imageBuffer;
    }

    private static BufferedImage loadImage(String ref) throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource(ref);

        if (url == null) {
            throw new IOException("Cannot find: " + ref);
        }

        return ImageIO.read(new BufferedInputStream(LWJGLTextureCache.class.getClassLoader().getResourceAsStream(ref)));
    }

    private static int createTextureID() {
        GL11.glGenTextures(textureIDBuffer);
        return textureIDBuffer.get(0);
    }
}
