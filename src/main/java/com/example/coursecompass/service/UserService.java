package com.example.coursecompass.service;

import com.example.coursecompass.dao.UserDao;
import com.example.coursecompass.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    private final Random random = new Random();

    @Transactional
    public void saveUser(User user) {
        userDao.save(user);
    }

    @Transactional
    public User findUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public String generateCaptchaText() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            char c = (char) (random.nextInt(26) + 'a');
            sb.append(c);
        }
        return sb.toString();
    }

    public String createCaptchaImage(String text) {
        int width = 150;
        int height = 50;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        // Gradient Background
        Color color1 = new Color(84, 50, 215); // Light color
        Color color2 = new Color(255, 183, 128); // Darker color
        GradientPaint gradientPaint = new GradientPaint(0, 0, color1, width, height, color2);
        g2d.setPaint(gradientPaint);
        g2d.fillRect(0, 0, width, height);

        // Custom Text
        Font font = new Font("Arial", Font.BOLD, 40);
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);

        // Draw Text with Shadow
        g2d.setColor(Color.GRAY);
        g2d.drawString(text, 12, 42); // Shadow
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, 10, 40); // Main text

        // Noise lines
        g2d.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < 5; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            g2d.drawLine(x1, y1, x2, y2);
        }

        g2d.dispose();

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
