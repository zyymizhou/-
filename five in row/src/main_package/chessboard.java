package main_package;

import entrance.select_window;

import javax.swing.*;
import java.util.Random;
import java.util.Vector;

public class chessboard {
    private int[][] board=new int[15][15];//棋盘（步数，奇黑偶白）
    private int steps=0;//步数

    public void show_steps(){
        int i,j;
        for(i=0;i<15;i++){
            for(j=0;j<15;j++){
                System.out.print(this.get_point(i,j));
                System.out.print(" ");
            }
            System.out.print("\n");
        }
    }

    public chessboard copy(){
        chessboard new_chessboard=new chessboard();
        int i,j;
        for(i=0;i<15;i++){
            for(j=0;j<15;j++){
                new_chessboard.board[i][j]=board[i][j];
            }
        }
        new_chessboard.steps=steps;
        return new_chessboard;
    }

    public int get_point(int x0,int y0){
        if(x0<0 || y0<0 || x0>14 || y0>14){
            return -1;
        }
        else{
            return board[x0][y0];
        }
    }
    public int get_steps(){
        return steps;
    }

    public void add_point(int x,int y){
        if(x>14 || y>14 || x<0 || y<0){
            //System.out.println("you can not do this");
            return;
        }
        if(board[x][y]!=0){
            //System.out.println("you can not do this");
            return;
        }
        steps++;
        board[x][y]=steps;
    }

    public void turn_back(int times){//回退times步
        int i,j,k;
        int n=0;
        int end=steps;//最后一个
        int begin=steps-times+1;//第一个删的
        if(begin<1){
            //System.out.println("You can not turn back any more.");
            return;
        }
        for(i=0;i<15;i++){
            for(j=0;j<15;j++){
                if(n>=times){
                    steps=begin-1;
                    return;
                }
                if(board[i][j]<=end && board[i][j]>=begin){
                    board[i][j]=0;
                    n++;
                }
            }
        }

    }

    public void show_chessboard(){
        int i,j,k;
        System.out.print("   ");
        for(i=0;i<15;i++){
            if(i<9){
                System.out.printf("%d  ",i+1);
            }
            else{
                System.out.printf("%d ",i+1);
            }
        }
        System.out.print("\n");
        for(i=0;i<15;i++){
            if(i<9){
                System.out.printf("%d  ",i+1);
            }
            else{
                System.out.printf("%d ",i+1);
            }
            for(j=0;j<15;j++){
                if(board[i][j]==0){
                    System.out.print(".  ");
                }
                else if(board[i][j]%2==1){
                    System.out.print("@  ");
                }
                else{
                    System.out.print("O  ");
                }
            }
            System.out.print("\n");
        }
    }

    public long cal_score(int hardness){//计算局势，黑方优势为正
        int i,j,k;
        int dx,dy;
        int n;
        int x0,y0;
        int color,now_color;
        int num;
        int score;
        int[] each_score=new int[5];
        int[][] row_num=new int[6][2];

        each_score[0]=0;
        each_score[1]=1;
        each_score[2]=8;
        each_score[3]=30;
        each_score[4]=100;

        Vector<Thread> thread_list=new Vector<Thread>();

        for(k=0;k<4;k++){
            if(k==0){
                dx=1;
                dy=0;
            }//行
            else if(k==1){
                dx=0;
                dy=1;
            }//列
            else if(k==2){
                dx=1;
                dy=1;
            }//右上
            else{
                dx=1;
                dy=-1;
            }//右下
            for(i=0;i<15;i++){
                for(j=0;j<15;j++){
                    color=-1;
                    num=0;
                    for(n=0;n<5;n++){
                        x0=i+n*dx;
                        y0=j+n*dy;
                        if(x0>14 || y0>14 || x0<0 || y0<0){
                            break;//出界
                        }
                        if(board[x0][y0]==0){
                            now_color=-1;
                        }
                        else{
                            now_color=board[x0][y0]%2;
                        }


                        if(color==-1){
                            if(now_color!=-1){
                                color=now_color;
                                num=1;
                            }
                        }
                        else if(color!=now_color){
                            if(now_color!=-1){
                                color=-1;
                                break;
                            }

                        }
                        else{
                            num++;
                        }


                        if(num==5){
                            if(color==1){
                                return 999999999;
                            }
                            else{
                                return -999999999;
                            }
                        }
                    }
                    if(color!=-1){
                        row_num[num][color]++;
                    }
                }
            }

        }



        score=0;
        for(i=0;i<5;i++){
            score+=(row_num[i][1]-row_num[i][0])*each_score[i];
            //8 8System.out.printf("%d:%d,%d\n",i,row_num[i][1],row_num[i][0]);
        }
        Random r=new Random();
        if(hardness==3){
            score+=(r.nextInt(30)-15);
        }
        else if(hardness==2){
            score+=(r.nextInt(60)-30);
        }
        else{
            score+=(r.nextInt(100)-50);
        }
        return score;
    }


}
