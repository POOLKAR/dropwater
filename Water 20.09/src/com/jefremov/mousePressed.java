package com.jefremov;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.io.IOException;

public class mousePressed extends MouseAdapter {


    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            if (GameWindow.pause) {
                GameWindow.pause = false;
                GameWindow.drop_v = GameWindow.drop_speed_saved;
                GameWindow.drop_v1 = GameWindow.drop_speed_saved;

                try {
                    Robot r = new Robot();
                    r.mouseMove((int) GameWindow.mousecordX, (int) GameWindow.mousecordY);
                } catch (AWTException ee) {

                }
            } else {
                GameWindow.drop_speed_saved = GameWindow.drop_v;
                GameWindow.drop_speed_saved = GameWindow.drop_v1;
                GameWindow.drop_v = 0;
                GameWindow.drop_v1 = 0;
                GameWindow.mousecordX = MouseInfo.getPointerInfo().getLocation().getX();
                GameWindow.mousecordY = MouseInfo.getPointerInfo().getLocation().getY();
                GameWindow.pause = true;
            }
        }

        if (e.getButton() == MouseEvent.BUTTON1) {
            if (GameWindow.pause) return;

            int x = e.getX();
            int y = e.getY();


            float drop_right = GameWindow.drop_left + GameWindow.drop_2.getWidth(null);
            float drop_bottom = GameWindow.drop_left + GameWindow.drop_2.getHeight(null);
            boolean is_drop = x >= GameWindow.drop_left && x <= drop_right && y >= GameWindow.drop_top && y <= drop_bottom;
            float drop_right1 = GameWindow.drop_left1 + GameWindow.drop_1.getWidth(null);
            float drop_bottom1 = GameWindow.drop_left1 + GameWindow.drop_1.getHeight(null);
            boolean is_drop1 = x >= GameWindow.drop_left1 && x <= drop_right1 && y >= GameWindow.drop_top1 && y <= drop_bottom1;

            if (is_drop) {
                if (GameWindow.drop_height > 25 && GameWindow.drop_width > 50) {
                    GameWindow.drop_width = GameWindow.drop_width - 1;
                    GameWindow.drop_height = GameWindow.drop_height - 2;
                    try {
                        GameWindow.dropResize();
                    } catch (IOException ioe) {

                    }

                }




                GameWindow.drop_top = -100;
                GameWindow.drop_left = (int) (Math.random() * (GameWindow.game_field.getWidth() - GameWindow.drop_2.getWidth(null)));
                GameWindow.drop_v = GameWindow.drop_v + 20;
                GameWindow.drop_v1 = GameWindow.drop_v;
                GameWindow.score++;
                GameWindow.onDirection();
                GameWindow.game_window.setTitle("Score: " + GameWindow.score);

            }

            if (is_drop1) {
                GameWindow.drop_top1 = -100;
                GameWindow.drop_left1 = (int) (Math.random() * (GameWindow.game_field.getWidth() - GameWindow.drop_1.getWidth(null)));
                GameWindow.drop_v1 = GameWindow.drop_v;
                GameWindow.score++;
                GameWindow.game_window.setTitle("Score: " + GameWindow.score);
            }


            if (GameWindow.end) {
                boolean isRestart = x >= 175 && x <= 175 + GameWindow.restart.getWidth(null) && y >= 300 && y <= 300 + GameWindow.restart.getHeight(null);


                if (isRestart) {
                    GameWindow.end = false;
                    GameWindow.score = 0;
                    GameWindow.game_window.setTitle("Score: " + GameWindow.score);
                    GameWindow. drop_top = -100;
                    GameWindow. drop_top1 = -200;
                    GameWindow. drop_left = (int) (Math.random() * (GameWindow.game_field.getWidth() -GameWindow. drop_2.getWidth(null)));
                    GameWindow. drop_left1 = (int) (Math.random() * (GameWindow.game_field.getWidth() - GameWindow.drop_1.getWidth(null)));
                    GameWindow.drop_v = 200;
                    GameWindow.drop_v1 = 200;

                    GameWindow.drop_width = 100;
                    GameWindow.drop_height = 150;
                }
            }
        }

    }
}
