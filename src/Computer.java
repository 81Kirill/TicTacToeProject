import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.concurrent.TimeUnit;
public class Computer extends JComponent {

    int h=0;
    int F_ = 0;
    int F_X = 10;
    int F_0 = 200;
    int[][] arr = new int[3][3];
    boolean xturn = false;
    int v=0;

    public Computer(){                                       //создание объекта - поля для игры с компьютером
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        restart();

    }

    public void restart(){                                   //очистка поля
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                arr[i][j]= F_;

            }

        }

    }


    //Основной метод игры: ход человека и компьютера
    @Override
    public void processMouseEvent(MouseEvent mouseEvent) {
        super.processMouseEvent(mouseEvent);
        if (mouseEvent.getButton() == MouseEvent.BUTTON1){      //координаты нажатия
            int x = mouseEvent.getX();
            int y = mouseEvent.getY();

            int i =(int) ((double) x / getWidth() * 3);
            int j =(int) ((double) y / getHeight() * 3);
            if (arr[i][j] == F_){
                arr[i][j] = F_0;                              //ход игрока
                v=0;

                repaint();   //Здесь и далее: Вызов метода paintComponent
            }
            try {
                TimeUnit.MILLISECONDS.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (v==0 && arr[ComputerLogic()/10][ComputerLogic()%10]==F_){       //ход компьютера
            arr[ComputerLogic()/10][ComputerLogic()%10]=F_X;
            repaint();
            v++;
            }
            int result = check();                    //Кто выиграл?; Конец партии
            if (result != 0){
                if (result == 3*F_0){
                    xturn = false;  //(ходит первым тот, кто выиграл)
                    JOptionPane.showMessageDialog(this, "нолики выиграли!", "Победа!", JOptionPane.INFORMATION_MESSAGE);
                }else if (result == 3*F_X){
                    xturn = true;
                    JOptionPane.showMessageDialog(this, "крестики выиграли!", "Победа!", JOptionPane.INFORMATION_MESSAGE);

                }else{
                    JOptionPane.showMessageDialog(this, "Ничья!", "", JOptionPane.INFORMATION_MESSAGE);
                    xturn=!xturn;
                }
                restart();
                repaint();
                if (xturn) FCturn();                            //первый ход совершает компьютер

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

    //Прорисовка поля
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


    //Определяем объект (Х/0), в зависимости от содержимого ячеек массива
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
        int d1=0; int d2 =0;                            //проверка диагоналей
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

        int ri ; int rj;                              //проверка столбцов и рядов
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

    //поиск оптимального хода для комьютера
    int ComputerLogic(){
        int p;
        int px=0; int py=0;
        int priority =0;
        for (int i = 0; i < 3; i++) {                  //проверка, может ли выиграть компьютер
            for (int j = 0; j < 3; j++) {

                if (arr[i][j]==F_){
                    arr[i][j] = F_X;
                    if (check()==F_X*3){
                        priority =3;
                        px = i;
                        py = j;
                    }
                    arr[i][j] = F_;
                    }
                }
            }
        if (priority<3){                             //проверка, может ли выиграть игрок в следующем своем ходу
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if (arr[i][j]==F_){
                    arr[i][j] = F_0;
                    if (check()==F_0*3){
                        priority =2;
                        px = i;
                        py = j;


                    }
                    arr[i][j] = F_;
                }
            }
        }}
        if (h == 1){
        if (priority<2){
            if (arr[1][1]==F_){                       //проверка наиболее перспективной центральной клетки
                px=1;
                py=1;
                priority =1;
            }else {
                int t=4; int r=4;                     //проверка угловых клеток
                while (t!=px || r!=py) {
                    t =(int) ((Math.random()*100)%3);
                    r =(int) ((Math.random()*100)%3);
                    if ((arr[t][r] == F_) && (r*t)%4==0 && r+t!=1){
                        px=t;;
                        py=r;
                        priority =1;
                        break;
                    }
                }


            }
        }}

        if (priority<1) {                                  //выбор случайной свободной клетки
            int t=4; int r=4;
            while (t!=px || r!=py) {
                t =(int) ((Math.random()*100)%3);
                r =(int) ((Math.random()*100)%3);
                if (arr[t][r] == F_){
                    px=t;
                    py=r;
                    break;
                }
            }

        }
        p= px*10 + py;
        return p;
    }
    void FCturn(){                                            //первый ход комьютера
        arr[ComputerLogic()/10][ComputerLogic()%10]=F_X;
        repaint();
    }
}