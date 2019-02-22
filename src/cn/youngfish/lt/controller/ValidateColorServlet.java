package cn.youngfish.lt.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/validateImg")
public class ValidateColorServlet extends HttpServlet {

    public static final String CHECK_CODE_KEY = "CHECK_CODE_KEY";

    private static final long serialVersionUID = 1L;

    private int width = 152;
    private int height = 25;
    private int codeCount = 5;

    private int fontHeight = 4;

    private int codeX = 0;
    private int codeY = 0;

    char[] codeSequence = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz23456789".toCharArray();

    public void init() {
        fontHeight = height - 2;
        codeX = width / (codeCount + 2);
        codeY = height - 4;
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        BufferedImage buffImg = null;
        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

        Graphics2D graphics = null;
        graphics = buffImg.createGraphics();

        graphics.setColor(Color.WHITE);

        graphics.fillRect(0, 0, width, height);

        Font font = null;
        font = new Font("", Font.BOLD, fontHeight);
        graphics.setFont(font);

        graphics.setColor(Color.BLACK);

        graphics.drawRect(0, 0, width - 1, height - 1);

        Random random = null;
        random = new Random();
        graphics.setColor(Color.GREEN);
        for (int i = 0; i < 60; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(20);
            int y1 = random.nextInt(20);
            graphics.drawLine(x, y, x + x1, y + y1);
        }

        StringBuffer randomCode;
        randomCode = new StringBuffer();

        for (int i = 0; i < codeCount; i++) {
            String strRand = null;
            strRand = String.valueOf(codeSequence[random.nextInt(36)]);

            randomCode.append(strRand);

            graphics.setColor(Color.BLUE);
            graphics.drawString(strRand, (i + 1) * codeX, codeY);
        }
        System.out.println(randomCode);

        request.getSession().setAttribute(CHECK_CODE_KEY, randomCode.toString());
        response.setContentType("image;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        ServletOutputStream sos = response.getOutputStream();
        ImageIO.write(buffImg, "jpeg", sos);
        sos.close();
    }
}
