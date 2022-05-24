import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class TicTacToe extends JComponent {
    public static final int F_ = 0;                       //константы для удобного определения выигрыша по сумме
    public static final int F_X = 10;
    public static final int F_0 = 200;
    int[][] arr = new int[3][3];
    boolean xturn; // чей сйчас ход

    public TicTacToe(){                                    // основной конструктор
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        restart();
    }

    public void restart(){                                 // очистка поля
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                arr[i][j]= F_;

            }

        }

    }


    //основной метод
    @Override
    public void processMouseEvent(MouseEvent mouseEvent) {
        super.processMouseEvent(mouseEvent);
        if (mouseEvent.getButton() == MouseEvent.BUTTON1){             //координаты клика
            int x = mouseEvent.getX();
            int y = mouseEvent.getY();

            int i =(int) ((double) x / getWidth() * 3);
            int j =(int) ((double) y / getHeight() * 3);
            if (arr[i][j] == F_){                                      /*крестик или нолик в зависимости от
                                                                         очередности (соотв. константа в массиве)*/
                arr[i][j] = xturn?F_X:F_0;
                xturn = !xturn;
                repaint();
            }
            int result = check();                                      //проверка на выигрыш
            if (result != 0){
                if (result == 3*F_0){
                    JOptionPane.showMessageDialog(this, "нолики выиграли!", "Победа!", JOptionPane.INFORMATION_MESSAGE);
                }else if (result == 3*F_X){
                    JOptionPane.showMessageDialog(this, "крестики выиграли!", "Победа!", JOptionPane.INFORMATION_MESSAGE);
                }else JOptionPane.showMessageDialog(this, "Ничья!", "", JOptionPane.INFORMATION_MESSAGE);
            restart();
            repaint();
            }

        }
    }


    //Рисовка текущего состояния поля
    @Override
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        graphics.clearRect(0,0,getWidth(),getHeight());
        drawGrid(graphics);
        drawXO(graphics);
    }


    //Прорисовка сетки
    void drawGrid(Graphics graphics){
        int w = getWidth();  int wi = w/3;
        int h = getHeight();  int hi = h/3;
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < 3; i++) {
            graphics.drawLine(0,hi*i,w, hi*i);
            graphics.drawLine(wi*i,0,wi*i,h );
        }
    }


    //Прорисовка нолика
    void drawO(int i, int j, Graphics graphics){
        int wi = getWidth()/3;
        int hi = getHeight()/3;
        int x = i*wi;
        int y = j*hi;
        graphics.setColor(Color.BLUE);
        graphics.drawOval(x + wi/8, y+8, wi*7/10, hi*9/10);
    }


    //Прорисовка крестика
    void drawX(int i, int j, Graphics graphics){
        int wi = getWidth()/3;
        int hi = getHeight()/3;
        int x = i*wi;
        int y = j*hi;
        graphics.setColor(Color.RED);
        graphics.drawLine(x+10, y+10, x+wi-10, y+hi-10);
        graphics.drawLine(x+wi-10,y+10,x+10,y+hi-10);
    }

    //Вызов соотв. метода в зависимости от содержимого ячейки массива
    void drawXO(Graphics graphics){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (arr[i][j] == F_0) drawO(i,j,graphics);
                else
                if (arr[i][j] == F_X) drawX(i,j,graphics);
            }
        }
    }

    //проверка на выигрыш
    int check(){
        int d1=0; int d2 =0;                       //проверка диагоналей
        for (int i = 0; i < 3; i++) {
            d1+= arr[i][i];
            d2+= arr[i][2-i];
        }
        if (d1 == F_X*3 || d1 == F_0*3 ){
            return d1;
        }
        if (d2 == F_X*3 || d2 == F_0*3 ){
            return d2;
        }

        int ri ; int rj;                          //проверка рядов и столбцов
        boolean e = false;
        for (int i = 0; i < 3; i++) {
            ri =0; rj=0;
            for (int j = 0; j < 3; j++) {
                if(arr[i][j] == F_){
                    e = true;
                }
                ri += arr[i][j];
                rj += arr[j][i];
            }
            if (ri == F_0*3 || ri == F_X*3) return ri;
            if (rj == F_0*3 || rj == F_X*3) return rj;

        }
        if(e) return 0; else return -1;
    }
}
