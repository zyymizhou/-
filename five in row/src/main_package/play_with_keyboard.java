package main_package;

import game_tree.*;
import entrance.*;

import javax.swing.*;

import java.awt.*;

public class play_with_keyboard extends JPanel{
    private chessboard my_chessboard=new chessboard();
    private int choose_x=7;
    private int choose_y=7;
    private int human_player;
    private int mod;
    game_window gw;
    private Thread computer_play_thread;

    public int get_human_player(){
        return human_player;
    }

    public int get_steps(){
        return my_chessboard.get_steps();
    }

    public play_with_keyboard(int color,int hardness,game_window my_game_window){
        human_player=color;
        mod=hardness;
        gw=my_game_window;
        run();
    }

    public void turn_back(){
        my_chessboard.turn_back(2);
        repaint();
    }

    public void move_focus(int x,int y){//移动光标
        int x1=choose_x+x;
        int y1=choose_y+y;
        if(x1<=14 && x1>=0 && y1<=14 && y1>=0){
            choose_x=x1;
            choose_y=y1;
        }
        repaint();
    }

    public void human_choose(){//人类落子后的操作
        long now_score;
        if(my_chessboard.get_point(choose_x,choose_y)!=0){
            return;
        }//不可落子
        my_chessboard.add_point(choose_x,choose_y);
        repaint();
        now_score=my_chessboard.cal_score(mod);
        if(now_score>=999999999 || now_score<=-999999999){
            JOptionPane.showMessageDialog(null,"You win!");
            gw.dispose();
            select_window.open_select_window();
        }
        else {
            computer_play_thread = new Thread(new Runnable() {
                public void run() {
                    //System.out.println(1);
                    if (human_player == my_chessboard.get_steps() % 2) {//电脑回合
                        computer_play();

                        repaint();
                        long now_score;
                        now_score = my_chessboard.cal_score(mod);
                        //System.out.println(now_score);
                        if (now_score >= 999999999 || now_score <= -999999999) {
                            JOptionPane.showMessageDialog(null, "You lose!");
                            gw.dispose();
                            select_window.open_select_window();
                        }

                    }
                }
            });
            computer_play_thread.start();
        }
    }

    public void computer_play(){
        tree_node root_node=new tree_node(my_chessboard,mod);
        int x0;
        int y0;
        if(mod==3){//困难
            if(my_chessboard.get_steps()>35){
                root_node.create_tree(11,1-human_player,mod);
            }
            else if(my_chessboard.get_steps()>15){
                root_node.create_tree(9,1-human_player,mod);
            }
            else if(my_chessboard.get_steps()>5){
                root_node.create_tree(7,1-human_player,mod);
            }
            else {
                root_node.create_tree(5,1-human_player,mod);
            }
        }

        else if(mod==2){//中等
            if(my_chessboard.get_steps()>10){
                root_node.create_tree(6,1-human_player,mod);
            }
            else {
                root_node.create_tree(4,1-human_player,mod);
            }
        }

        else{
            root_node.create_tree(4,1-human_player,mod);
        }


        root_node.cal_score(1-human_player);
        x0=root_node.get_best_x();
        y0=root_node.get_best_y();
        my_chessboard.add_point(x0,y0);
        choose_x=x0;
        choose_y=y0;
    }

    public void run() {
        if(human_player==0){//电脑黑棋第一步
            my_chessboard.add_point(7,7);
            //my_chessboard.show_chessboard();
            repaint();
        }

    }

    public void paint(Graphics g){
        int i,j;

        super.paint(g);
        g.clearRect(0,0,this.getWidth(),this.getHeight());

        //棋盘底色
        g.setColor(new Color(205,165,29));
        g.fillRect(0,0,700,700);
        //g.fillRect(35,35,600,600);

        //横线
        for(i=0;i<15;i++){
            g.setColor(Color.black);
            g.fillRect(54+40*i,54,3,560);
        }

        //竖线
        for(i=0;i<15;i++){
            g.setColor(Color.black);
            g.fillRect(54,54+40*i,563,3);
        }


        //绘制棋子
        for(i=0;i<15;i++){
            for(j=0;j<15;j++){
                if(my_chessboard.get_point(i,j)!=0){

                    if(my_chessboard.get_point(i,j)%2==1){
                        g.setColor(Color.black);
                    }
                    else{
                        g.setColor(Color.white);
                    }
                    g.fillOval(37+40*j,37+40*i,36,36);

                    if(my_chessboard.get_point(i,j)==my_chessboard.get_steps()){
                        g.setColor(Color.gray);
                        g.fillRect(40+40*j,53+40*i,30,5);
                        g.setColor(Color.gray);
                        g.fillRect(53+40*j,40+40*i,5,30);
                    }//上一步标记
                }


            }
        }

        if(my_chessboard.get_point(choose_x,choose_y)==0){
            if(my_chessboard.get_steps()%2==0){
                g.setColor(Color.black);
            }
            else {
                g.setColor(Color.white);
            }
            g.fillOval(37+40*choose_y,37+40*choose_x,36,36);
        }
        //选择点标记
        g.setColor(Color.red);
        g.fillRect(40+40*choose_y,53+40*choose_x,30,5);
        g.setColor(Color.red);
        g.fillRect(53+40*choose_y,40+40*choose_x,5,30);

    }





}
