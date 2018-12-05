package com.stosz.product.imageHashMatch.similarimage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;

/**
 * 图片工具类，主要针对图片水印处理
 *
 * @author XiongChenyang
 * 2017/8/10
 */
public class ImageHelper {

    // 项目根目录路径
    public static final String path = System.getProperty("user.dir");

    private static final Logger logger = LoggerFactory.getLogger(ImageHelper.class);

    /**
     * 生成缩略图 <br/>
     * 保存:ImageIO.write(BufferedImage, imgType[jpg/png/...], File);
     *
     * @param source 原图片
     * @param width  缩略图宽
     * @param height 缩略图高
     * @param b      是否等比缩放
     */
    public static BufferedImage thumb(BufferedImage source, int width,
                                      int height, boolean b) {
        // targetW，targetH分别表示目标长和宽
        int type = source.getType();
        BufferedImage target = null;
        double sx = (double) width / source.getWidth();
        double sy = (double) height / source.getHeight();

        if (b) {
            if (sx > sy) {
                sx = sy;
                width = (int) (sx * source.getWidth());
            } else {
                sy = sx;
                height = (int) (sy * source.getHeight());
            }
        }

        if (type == BufferedImage.TYPE_CUSTOM) { // handmade
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(width,
                    height);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else
            target = new BufferedImage(width, height, type);
        Graphics2D g = target.createGraphics();
        // smoother than exlax:
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }

    /**
     * 图片水印
     *
     * @param imgPath  待处理图片
     * @param markPath 水印图片
     * @param x        水印位于图片左上角的 x 坐标值
     * @param y        水印位于图片左上角的 y 坐标值
     * @param alpha    水印透明度 0.1f ~ 1.0f
     */
    public static void waterMark(String imgPath, String markPath, int x, int y,
                                 float alpha) {
        try (
                FileOutputStream out = new FileOutputStream(imgPath);
                )
        {
            // 加载待处理图片文件
            Image img = ImageIO.read(new File(imgPath));

            BufferedImage image = new BufferedImage(img.getWidth(null),
                    img.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(img, 0, 0, null);

            // 加载水印图片文件
            Image src_biao = ImageIO.read(new File(markPath));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            g.drawImage(src_biao, x, y, null);
            g.dispose();

            // 保存处理后的文件

            String formatName = imgPath.substring(imgPath.lastIndexOf(".") + 1);
            ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(out);
            ImageIO.write(image, formatName, imageOutputStream);
        } catch (Exception e) {
            logger.error("加载待处理图片失败！", e);
            throw new RuntimeException("加载待处理图片失败！", e);
        }
    }

    /**
     * 文字水印
     *
     * @param imgPath 待处理图片
     * @param text    水印文字
     * @param font    水印字体信息
     * @param color   水印字体颜色
     * @param x       水印位于图片左上角的 x 坐标值
     * @param y       水印位于图片左上角的 y 坐标值
     * @param alpha   水印透明度 0.1f ~ 1.0f
     */

    public static void textMark(String imgPath, String text, Font font,
                                Color color, int x, int y, float alpha) {
        try (FileOutputStream out = new FileOutputStream(imgPath);)
        {
            Font Dfont = (font == null) ? new Font("宋体", 20, 13) : font;

            Image img = ImageIO.read(new File(imgPath));

            BufferedImage image = new BufferedImage(img.getWidth(null),
                    img.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();

            g.drawImage(img, 0, 0, null);
            g.setColor(color);
            g.setFont(Dfont);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            g.drawString(text, x, y);
            g.dispose();

            String formatName = imgPath.substring(imgPath.lastIndexOf(".") + 1);
            ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(out);
            ImageIO.write(image, formatName, imageOutputStream);
        } catch (Exception e) {
            logger.error("加载待处理图片失败！", e);
            throw new RuntimeException("处理文字水印失败！", e);
        }
    }

    /**
     * 读取JPEG图片
     *
     * @param filename 文件名
     * @return BufferedImage 图片对象
     */
    public static BufferedImage readJPEGImage(String filename) {
        try (
                InputStream imageIn = new FileInputStream(new File(filename));
                ){

            // 得到输入的编码器，将文件流进行jpg格式编码
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(imageIn);
            BufferedImage sourceImage = ImageIO.read(imageInputStream);
//            JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(imageIn);
//            // 得到编码后的图片对象
//            BufferedImage sourceImage = decoder.decodeAsBufferedImage();

            return sourceImage;
        } catch (FileNotFoundException e) {
            logger.error("找不到该{}图片！", filename, e);
        } catch (IOException e) {
            logger.error("该图片{}读取失败！", filename, e);
        }

        return null;
    }

    /**
     * 读取JPEG图片
     *
     * @param filename 文件名
     * @return BufferedImage 图片对象
     */
    public static BufferedImage readPNGImage(String filename) {
        try {
            File inputFile = new File(filename);
            BufferedImage sourceImage = ImageIO.read(inputFile);
            return sourceImage;
        } catch (FileNotFoundException e) {
            logger.error("找不到该{}图片！", filename, e);
        } catch (IOException e) {
            logger.error("该图片{}读取失败！", filename, e);
        }

        return null;
    }

    /**
     * 灰度值计算
     *
     * @param pixels 像素
     * @return int 灰度值
     */
    public static int rgbToGray(int pixels) {
        // int _alpha = (pixels >> 24) & 0xFF;
        int _red = (pixels >> 16) & 0xFF;
        int _green = (pixels >> 8) & 0xFF;
        int _blue = (pixels) & 0xFF;
        return (int) (0.3 * _red + 0.59 * _green + 0.11 * _blue);
    }

    /**
     * 计算数组的平均值
     *
     * @param pixels 数组
     * @return int 平均值
     */
    public static int average(int[] pixels) {
        float m = 0;
        for (int i = 0; i < pixels.length; ++i) {
            m += pixels[i];
        }
        m = m / pixels.length;
        return (int) m;
    }
}