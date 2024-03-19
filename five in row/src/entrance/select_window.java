package entrance;

import main_package.game_window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class select_window extends JFrame {
    public static void main(String[] args) {
        open_select_window();
    }

    public static void open_select_window(){
        select_window DB=new select_window();
        DB.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public select_window(){
        this.setTitle("五子棋");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(700,700);
        this.setLocation(100,50);
        this.setVisible(true);
        this.setResizable(false);
        this.setLayout(null);

        this.getContentPane().setBackground(new Color(205,165,29));

        Font button_font=new Font("华文行楷", Font.BOLD,30);

        JButton infer_button=new JButton("说明");
        infer_button.addActionListener(e -> JOptionPane.showMessageDialog(null,"键盘按前后左右键移动光标\nenter键落子\nbackspace键悔棋\n在困难难度下计算时间较长，请耐心等待。"));
        infer_button.setBounds(600,600,60,30);
        infer_button.setBackground(new Color(133, 184, 245));
        infer_button.setFocusPainted(false);
        this.add(infer_button);

        JButton black_easy=new JButton("黑棋   简单");
        black_easy.addActionListener(e -> {
            game_window.game_begin(1,1);
            dispose();
        });
        black_easy.setBounds(75,75,200,100);
        black_easy.setFont(button_font);
        black_easy.setForeground(Color.white);
        black_easy.setBackground(Color.black);
        black_easy.setFocusPainted(false);
        this.add(black_easy);

        JButton black_normal=new JButton("黑棋   中等");
        black_normal.addActionListener(e -> {
            game_window.game_begin(1,2);
            dispose();
        });
        black_normal.setBounds(75,275,200,100);
        black_normal.setFont(button_font);
        black_normal.setForeground(Color.white);
        black_normal.setBackground(Color.black);
        black_normal.setFocusPainted(false);
        this.add(black_normal);

        JButton black_hard=new JButton("黑棋   困难");
        black_hard.addActionListener(e -> {
            game_window.game_begin(1,3);
            dispose();
        });
        black_hard.setBounds(75,475,200,100);
        black_hard.setFont(button_font);
        black_hard.setForeground(Color.white);
        black_hard.setBackground(Color.black);
        black_hard.setFocusPainted(false);
        this.add(black_hard);

        JButton white_easy=new JButton("白棋   简单");
        white_easy.addActionListener(e -> {
            game_window.game_begin(0,1);
            dispose();
        });
        white_easy.setBounds(375,75,200,100);
        white_easy.setFont(button_font);
        white_easy.setBackground(Color.white);
        white_easy.setFocusPainted(false);
        this.add(white_easy);

        JButton white_normal=new JButton("白棋   中等");
        white_normal.addActionListener(e -> {
            game_window.game_begin(0,2);
            dispose();
        });
        white_normal.setBounds(375,275,200,100);
        white_normal.setFont(button_font);
        white_normal.setBackground(Color.white);
        white_normal.setFocusPainted(false);
        this.add(white_normal);

        JButton white_hard=new JButton("白棋   困难");
        white_hard.addActionListener(e -> {
            game_window.game_begin(0,3);
            dispose();
        });
        white_hard.setBounds(375,475,200,100);
        white_hard.setFont(button_font);
        white_hard.setBackground(Color.white);
        white_hard.setFocusPainted(false);
        this.add(white_hard);

        infer_button.repaint();
        black_easy.repaint();
        black_normal.repaint();
        black_hard.repaint();
        white_easy.repaint();
        white_normal.repaint();
        white_hard.repaint();
    }

}
