package com.jefremov;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class GameWindow extends JFrame {


    public static GameWindow game_window;
    public static long last_frame_time;
    public static Image background;
    public static Image game_over;
    public static Image drop_2;
    public static Image drop_1;
    public static Image restart;
    public static float drop_left= 300;
    public static float drop_top = -100;
    public static float drop_left1= 300;
    public static float drop_top1 = -100;
    public static float drop_v=1;
    public static float drop_v1=1;
    public static int score=1;
    public static boolean end;
    public static float drop_width=100;
    public static float drop_height=150;
    public static boolean pause = false;
    public static float drop_speed_saved;
    public static double mousecordX=0;
    public static double mousecordY=0;
    public static int direction= -1;
    public static GameField game_field;


    public static Entry nameEntry;
    public static Database db;

    public static boolean isRecorded = false;
    public static boolean drawRecords = false;
    public static ArrayList<String> recordsLast = new ArrayList<String>();

    public static void main(String[] args) throws IOException {

        /*try{  СОЗДАНИЕ ТАБЛИЦЫ РЕКОРДОВ
            String url = "jdbc:mysql://localhost/store?useLegacyDatetimeCode=false&serverTimezone=Europe/Helsinki";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            //String sqlCommand = "CREATE TABLE GameScore (idPlayer INT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(45), Score INT, Date DATETIME)";

            try (Connection conn = DriverManager.getConnection(url, username, password)){

                //Statement statement = conn.createStatement();
                //statement.executeUpdate(sqlCommand);

                System.out.println("Connection to Store DB succesfull!");
            }
        }
        catch(Exception ex){
            System.out.println("Connection failed...");

            System.out.println(ex);
        }*/

        db = new Database("jdbc:mysql://localhost/waterdrop?useLegacyDatetimeCode=false&serverTimezone=Europe/Helsinki", "root", "");
        db.init();




        // вывод картинок
        background = ImageIO.read(GameWindow.class.getResourceAsStream("background.jpg"));
        game_over = ImageIO.read(GameWindow.class.getResourceAsStream("game_over.png"));
        drop_1 = ImageIO.read(GameWindow.class.getResourceAsStream("drop_1.png")).getScaledInstance((int) drop_width, (int) drop_height, Image.SCALE_DEFAULT);
        drop_2 = ImageIO.read(GameWindow.class.getResourceAsStream("drop.png")).getScaledInstance((int) drop_width, (int) drop_height, Image.SCALE_DEFAULT);
        restart = ImageIO.read(GameWindow.class.getResourceAsStream("restart.png")).getScaledInstance(100,100, Image.SCALE_DEFAULT);
        game_window = new GameWindow();//Создание нового окна
        game_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Закрытие программы
        game_window.setLocation(200, 100);//расположение мыши
        game_window.setSize(906, 478);//размер окна
        last_frame_time = System.nanoTime();
        game_field = new GameField();
        onDirection();
        game_field.addMouseListener(new mousePressed() {

        });
        game_window.add(game_field);
        game_window.setResizable(false);
        game_window.setVisible(true);

        nameEntry = new Entry();
        game_window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                nameEntry.keyPress(e);
                if (nameEntry.isActive && !isRecorded)
                {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    {
                        db.addRecord(nameEntry.text, score);
                        isRecorded = true;
                        recordsLast = db.getRecords();
                        drawRecords = true;
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        game_window.setVisible(true);

    }


    public static void dropResize() throws IOException
    {
        drop_2 = ImageIO.read(GameWindow.class.getResourceAsStream("drop.png")).getScaledInstance((int) drop_width, (int) drop_height, Image.SCALE_DEFAULT);
        drop_1 = ImageIO.read(GameWindow.class.getResourceAsStream("drop_1.png")).getScaledInstance((int) drop_width, (int) drop_height, Image.SCALE_DEFAULT);
    }

    public static int onDirection(){
        int rand = (int)(Math.random()*2+1);
        if(rand == 2) direction = 1;
        else direction = -1;

        return direction;
    }


    public static class GameField extends JPanel{
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Repaint.onRepaint(g);
            repaint();
        }
    }
}