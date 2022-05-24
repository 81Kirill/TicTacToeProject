import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

    public class Window extends JFrame {
        int n=3;
        private JButton c;
        private JButton hc;
        private JButton p;

        public Window() {
            super("Выберите режим игры");
            c = new JButton("Computer");
            p = new JButton("Person");
            hc = new JButton("Computer.hard");


            JPanel buttonsPanel = new JPanel(new FlowLayout());


            buttonsPanel.add(c);
            buttonsPanel.add(p);
            buttonsPanel.add(hc);

            add(buttonsPanel, BorderLayout.SOUTH);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            initListeners();
            n=initListeners();
        }



        //куда нажал игрок?
        private int initListeners() {
            c.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    n=1;
                }
            });
            p.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    n=0;
                }
            });
            hc.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    n=2;
                }
            });
            return n;
        }
    }
