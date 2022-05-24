import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;




public class Main {
    public static void main(String[] args) throws InterruptedException {

        Window app = new Window();                                         //создание окна выбора
        app.setLocationRelativeTo(null);
        app.setVisible(true);
        app.pack();
        while (app.n == 3) {                                               //ждем, пока игрок не выберет, с кем играть
            TimeUnit.MILLISECONDS.sleep(100);
        }


            System.out.println("Start");                                   //создание главного окна игры
            JFrame window = new JFrame("Крестики-Нолики");
            window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            window.setSize(500, 500);
            window.setLayout(new BorderLayout());
            window.setLocationRelativeTo(null);
            window.setVisible(true);

            if (app.n >= 1) {                           //игра с компьютером
                Computer game = new Computer();
                window.add(game);
                if (app.n==2){
                    game.h =1;
                }
            } else {                                    //игра с человеком
                TicTacToe game = new TicTacToe();
                window.add(game);
            }
        }
    }
