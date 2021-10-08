/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mccoy.algo;

import com.mccoy.algo.ui.FranciseOptimizerVisualizer;
import com.mccoy.algo.ui.UIContainer;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

/**
 *
 * @author rmccoy
 */
public class App extends Canvas implements Runnable {
    
    public static final long serialVersionID = -274921492884053948L;

    public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
    
    private Thread thread;
    private boolean running = false;
    private final UIContainer uiContainer = UIContainer.getInstance();
 
    public App() {
        JFrame frame = new JFrame();
        
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(this);
        frame.setVisible(true);
        this.start();

        
    }

    public synchronized void start() {
 
        thread = new Thread(this);
        thread.start();
        running = true;
        System.out.println("Starting app on " + thread.getName());
    }
    
    public synchronized void stop() {

        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run(){
        //Main Game Loop
        
        this.requestFocus();
        long lastTime = System.nanoTime();
        
        //Frame per second
        double amountOfTicks = 20.0;
        
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running) {
                render();
                frames++;
            }
            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
            }
            
        }
        stop();
    }
    
    private void tick() {
        //every frame do something
        uiContainer.tick();
    }
    
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        //draw stuff here
        uiContainer.render(g);
        
        g.dispose();
        bs.show();

    }

    public static int positionClamp(int position, int min, int max) {
        if(position >= max) 
            return max;
        if(position <= min)
            return min;
        else
            return position;
    }
    
    public static void main(String[] args) {
        new App();
    }
    
}
