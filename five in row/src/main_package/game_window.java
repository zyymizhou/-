package main_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class game_window extends JFrame implements Runnable{
    static play_with_keyboard pwk;

    public static void game_begin(int color,int hardness){
        game_window DB=new game_window(color,hardness);
        DB.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void run(){
        ;
    }

    public game_window(int color,int hardness){
        pwk=new play_with_keyboard(color,hardness,this);
        this.setTitle("五子棋");
        Container c=this.getContentPane();//面板
        c.add(pwk);//添加

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocation(100,50);
        this.setSize(700,700);

        //this.setLocationRelativeTo(null);

        this.setResizable(false);
        this.setVisible(true);

        this.getContentPane().setVisible(true);//显示窗口

        this.addKeyListener(new game_window.KeyListener());
    }

    static class KeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e){
            int code=e.getKeyCode();
            int can_play=0;
            if(pwk.get_human_player()!=pwk.get_steps()%2){//玩家的回合
                can_play=1;
            }
            if(code==KeyEvent.VK_DOWN){
                if(can_play==1){
                    pwk.move_focus(1,0);
                }
            }
            else if(code==KeyEvent.VK_UP){
                if(can_play==1) {
                    pwk.move_focus(-1, 0);
                }
            }
            else if(code==KeyEvent.VK_RIGHT){
                if(can_play==1) {
                    pwk.move_focus(0, 1);
                }
            }
            else if(code==KeyEvent.VK_LEFT){
                if(can_play==1) {
                    pwk.move_focus(0, -1);
                }
            }
            else if(code==KeyEvent.VK_BACK_SPACE){
                if(can_play==1) {
                    pwk.turn_back();
                }
            }
            else if(code==KeyEvent.VK_ENTER){
                if(can_play==1) {
                    pwk.human_choose();
                }
            }
        }
    }
}
