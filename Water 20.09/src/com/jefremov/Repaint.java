package com.jefremov;

import com.sun.management.GarbageCollectionNotificationInfo;

import java.awt.*;

public class Repaint {
    public static void onRepaint(Graphics g) {
        long current_time = System.nanoTime();
        float delta_time = (current_time - GameWindow.last_frame_time) * 0.000000001f;
        GameWindow.last_frame_time = current_time;
        GameWindow.drop_top = GameWindow.drop_top + GameWindow.drop_v * delta_time;
        GameWindow.drop_left = GameWindow.drop_left + (GameWindow.direction * GameWindow.drop_v) * delta_time;
        GameWindow.drop_top1 = GameWindow.drop_top1 + GameWindow.drop_v1 * delta_time;

        g.drawImage(GameWindow.background, 0, 0, null);
        g.drawImage(GameWindow.drop_2, (int) GameWindow.drop_left, (int) GameWindow.drop_top, null);
        if (GameWindow.score >= 6) {
            g.drawImage(GameWindow.drop_1, (int) GameWindow.drop_left1, (int) GameWindow.drop_top1, null);
        }
        if (GameWindow.drop_top > GameWindow.game_window.getHeight()) {

            g.drawImage(GameWindow.game_over, 280, 120, null);
            g.drawImage(GameWindow.restart, 175, 300, null);
            GameWindow.end = true;
        }
        if (GameWindow.drop_left <= 0.0 || GameWindow.drop_left + GameWindow.drop_width > GameWindow.game_window.getWidth()) {
            if (GameWindow.direction == -1) GameWindow.direction = 1;
            else GameWindow.direction = -1;
        }
        if (GameWindow.drawRecords) {
            for (int i = 0; i < GameWindow.recordsLast.size(); i++) {
                g.drawString(GameWindow.recordsLast.get(i), 200, 25 + 25 * i);


            }
        }
        GameWindow.nameEntry.isActive = GameWindow.end;
        GameWindow.nameEntry.update(g);
    }
}
