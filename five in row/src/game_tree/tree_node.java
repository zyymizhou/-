package game_tree;

import main_package.chessboard;

import java.util.ArrayList;
import java.util.Comparator;



public class tree_node {
    private int best_depth=0;//博弈树最优路径的深度
    private long score=0;//搜索得到的分数
    private long now_score=0;//当前分数
    private int whether_get_score=0;
    private int x_now,y_now;//这一步选择的位置
    private int best_x,best_y;//之后最好的选择
    private chessboard now_board;
    private tree_node first_child=null;
    private tree_node next_brother=null;

    public tree_node(){
        ;
    }

    public tree_node(chessboard board, int hardness){
        now_board=board.copy();
        now_score= board.cal_score(hardness);
    }

    public long get_score(){
        return score;
    }

    public int get_best_depth(){
        return best_depth;
    }

    public int get_best_x(){
        return best_x;
    }

    public int get_best_y(){
        return best_y;
    }

    public void cal_score(int player){//player先选择
        tree_node now_node=first_child;
        long best_score;
        if(now_node==null){
            score=now_score;
            whether_get_score=1;
            return;
        }


        if(now_node.whether_get_score==0){
            now_node.cal_score(1-player);
        }
        best_score=now_node.score;
        best_x=now_node.x_now;
        best_y=now_node.y_now;
        best_depth=now_node.best_depth+1;


        /*
        System.out.print("(");
        System.out.print(String.format("%d<%d,%d>,",now_node.score,now_node.x_now+1,now_node.y_now+1));
         */

        now_node=now_node.next_brother;
        while (now_node != null) {

            if (now_node.whether_get_score == 0) {
                now_node.cal_score(1 - player);
            }

            if (player == 1) {//黑方选择
                if (now_node.score > best_score) {
                    best_score = now_node.score;
                    best_x = now_node.x_now;
                    best_y = now_node.y_now;
                    best_depth = now_node.best_depth + 1;
                } else if (now_node.score >= 999999999) {//发现必胜策略，最快取胜
                    if (now_node.best_depth + 1 < best_depth) {
                        best_score = now_node.score;
                        best_x = now_node.x_now;
                        best_y = now_node.y_now;
                        best_depth = now_node.best_depth + 1;
                        //System.out.printf("win:%d,%d(%d)\n",best_x,best_y,best_depth);
                    }
                } else if (now_node.score <= -999999999 && best_score <= -999999999) {//必败，尽量继续下棋
                    if (now_node.best_depth + 1 >= best_depth) {
                        best_score = now_node.score;
                        best_x = now_node.x_now;
                        best_y = now_node.y_now;
                        best_depth = now_node.best_depth + 1;
                    }
                }
            } else {//白方选择
                if (now_node.score < best_score) {
                    best_score = now_node.score;
                    best_x = now_node.x_now;
                    best_y = now_node.y_now;
                    best_depth = now_node.best_depth + 1;
                } else if (now_node.score <= -999999999) {//发现必胜策略，最快取胜
                    if (now_node.best_depth + 1 < best_depth) {
                        best_score = now_node.score;
                        best_x = now_node.x_now;
                        best_y = now_node.y_now;
                        best_depth = now_node.best_depth + 1;
                        //System.out.printf("win:%d,%d(%d)\n",best_x,best_y,best_depth);
                    }
                } else if (now_node.score >= 999999999 && best_score >= 999999999) {//必败，尽量继续下棋
                    if (now_node.best_depth + 1 >= best_depth) {
                        best_score = now_node.score;
                        best_x = now_node.x_now;
                        best_y = now_node.y_now;
                        best_depth = now_node.best_depth + 1;
                    }
                }
            }

            //System.out.print(String.format("%d<%d,%d>,",now_node.score,now_node.x_now+1,now_node.y_now+1));

            now_node = now_node.next_brother;
        }

        //System.out.print(String.format("\nbest:%d)\n",best_score));
        score=best_score;
        whether_get_score=1;
    }

    public void create_tree(int depth, int player, int hardness){
        if(now_score>=999999999 || now_score<=-999999999){
            return;
        }
        ArrayList<point_score> score_list=new ArrayList<point_score>();
        int search_num=5;//找得分靠前的这么多个点
        int i,j;
        int dx,dy;
        int x0,y0;
        int flag;
        tree_node new_tree_node;

        if(depth<=0){
            return;
        }

        //System.out.println(depth);
        for(i=0;i<15;i++){
            for(j=0;j<15;j++){
                if(now_board.get_point(i,j)!=0){
                    continue;
                }//已经有棋子


                score_list.add(new point_score(i,j,point_score.cal_score_of_point(now_board,i,j,player)));//加入元素
            }
        }

        score_list.sort(Comparator.naturalOrder());


        int len;
        len=score_list.size();
        if(search_num>len){
            search_num=len;
        }//避免超上限

        for(i=0;i<search_num;i++){
            if(score_list.get(0).get_score()>=9999999){
                if(score_list.get(i).get_score()<9999999){
                    break;
                }
            }//已经有胜利点
            if(score_list.get(0).get_score()>=99999){
                if(score_list.get(i).get_score()<99999){
                    break;
                }
            }//对方打将
            if(score_list.get(0).get_score()>score_list.get(i).get_score()*10+200){
                break;
            }//分数太低，不值得考虑

            x0=score_list.get(i).get_x();
            y0=score_list.get(i).get_y();

            new_tree_node = new tree_node();
            new_tree_node.next_brother=first_child;
            first_child=new_tree_node;

            new_tree_node.x_now=x0;
            new_tree_node.y_now=y0;

            new_tree_node.now_board=now_board.copy();
            new_tree_node.now_board.add_point(x0,y0);

            new_tree_node.now_score=new_tree_node.now_board.cal_score(hardness);

            if(now_score>=999999999 || now_score<=-999999999){
                ;
            }
            else{
                new_tree_node.create_tree(depth-1,1-player,hardness);
            }
        }

    }
}
