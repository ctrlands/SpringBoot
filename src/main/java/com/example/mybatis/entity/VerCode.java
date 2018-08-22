package com.example.mybatis.entity;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 数字验证码生成
 */
public class VerCode {
    public static final String RANDOMCODEKEY= "RANDOMVALIDATECODEKEY";//放到session中的key
    private static Random ran = new Random();
    // 验证码字符集
    //private String randString = "0123456789";//随机产生只有数字的字符串 private String
    private String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";//随机产生数字与字母组合的字符串
    private static final int SIZE = 4; // 验证码数字数量
    private static final int LINES = 5; /// 干扰线数量
    private static final int WIDTH = 95; // 宽度
    private static final int HEIGHT = 40; // 高度
    private static final int FONT_SIZE = 30; // 验证码字体大小



    /**
     * 随机颜色
     *
     */
    public static Color getRandomColor() {
        Color color = new Color(ran.nextInt(256), ran.nextInt(256), ran.nextInt(256));
        return color;
    }


    /**
     * 生成随机验证码及图片
     */
    public void getRandcode(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        // 创建空白图片
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        // 获取图片画笔
        Graphics graphic = image.getGraphics();
        // 设置画笔颜色
        graphic.setColor(Color.GRAY);
        // 绘制矩形背景
        graphic.fillRect(0, 0, WIDTH, HEIGHT);
        String randomString = "";
        // 随机生成字符串

        for (int i = 0; i < SIZE; i++) {
           randomString = drawString(graphic, randomString, i);
        }
        // 绘制干扰线
        for (int i = 0 ; i <= LINES; i++) {
            // 设置随即颜色
            graphic.setColor(getRandomColor());
            // 随机画线
            graphic.drawLine(ran.nextInt(WIDTH), ran.nextInt(HEIGHT), ran.nextInt(WIDTH), ran.nextInt(HEIGHT));
        }
        // 返回验证码和图片
        session.removeAttribute(RANDOMCODEKEY);
        session.setAttribute(RANDOMCODEKEY, randomString);
        graphic.dispose();
        try {
            // 将内存中的图片通过流动性是输出到客户端
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (Exception e) {

        }

    }


    /**
     * 获取随机的字符
     */
    public String getRandomString(int num) {
        return String.valueOf(randString.charAt(num));
    }

    // 绘制字符串
    private String drawString(Graphics g, String randomString, int i) {
        // 设置字体大小
        g.setFont(new Font(null, Font.BOLD + Font.ITALIC, FONT_SIZE));
        // 设置随机颜色
        g.setColor(getRandomColor());
        String rand = String.valueOf(getRandomString(ran.nextInt(randString
                .length())));
        randomString += rand;
        g.translate(ran.nextInt(3), ran.nextInt(3));
        g.drawString(rand, 13 * i, 26);
        return randomString;
    }




}
