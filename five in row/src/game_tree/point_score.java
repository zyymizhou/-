package game_tree;

import main_package.chessboard;

public class point_score implements Comparable<point_score>{
    private int x=-1;
    private int y=-1;
    private long score_of_point=0;

    public point_score(int x0,int y0,long p){
        x=x0;
        y=y0;
        score_of_point=p;
    }

    public int compareTo(point_score other){
        return Long.compare(other.score_of_point, score_of_point);
    }

    public int get_x(){
        return x;
    }
    public int get_y(){
        return y;
    }
    public long get_score(){
        return score_of_point;
    }

    public static long cal_score_of_point(chessboard board,int x0,int y0,int player){//计算当前情况每个点的得分（与row_list的不同）
        int enemy=1-player;

        long score=0;
        int i,j,k;
        int dx,dy;
        int[][] num=new int[5][2];
        int num_of_row;
        int color;
        int now_step;
        int x_now,y_now;
        if(board.get_point(x0,y0)!=0){
            System.out.println("error");
            return 0;
        }

        for(k=0;k<4;k++){//k表明方向
            if(k==0){//行
                dx=1;
                dy=0;
            }
            else if(k==1){//列
                dx=0;
                dy=1;
            }
            else if(k==2){//右下
                dx=1;
                dy=1;
            }
            else{
                dx=1;
                dy=-1;
            }
            for(i=-4;i<=0;i++){//i表明起始位置
                color=-1;
                num_of_row=0;
                for(j=0;j<=4;j++){//j表明离起始位置距离
                    x_now=x0+i*dx+j*dx;
                    y_now=y0+i*dy+j*dy;
                    if(x_now>14 || x_now<0 || y_now>14 || y_now<0){
                        num_of_row=0;
                        break;
                    }//超出棋盘
                    now_step=board.get_point(x_now,y_now);

                    if(now_step!=0){
                        if(color==-1){//之前没有棋子
                            color=now_step%2;
                        }
                        else if(color!=now_step%2){
                            num_of_row=0;
                            break;
                        }

                        num_of_row++;
                    }
                }
                if(color!=-1){
                    num[num_of_row][color]++;
                }

            }
        }



        //计算分数
        score+=num[1][player]* 2L;
        score+=num[2][player]* 50L;
        score+=num[3][player]* 300L;
        score+=num[4][player]* 9999999L;


        score+=num[1][enemy]* 4L;
        score+=num[2][enemy]* 20L;
        score+=num[3][enemy]* 120L;
        score+=num[4][enemy]* 99999L;


        return score;
    }
}
