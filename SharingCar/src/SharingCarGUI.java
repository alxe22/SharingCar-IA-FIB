package sharingcar;

import IA.Comparticion.Usuarios;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

import static java.awt.Color.*;

public class SharingCarGUI extends JPanel {

    private static Usuarios us;
    private static int[] colorUs;
    private static final double COEFF = 0.0450;

    @Override
    public void paint(Graphics gr) {
        Graphics2D g2d = (Graphics2D) gr;
        Graphics2D g2d2 = (Graphics2D) gr.create();

        Random r = new Random(342);
        for (int i = 0; i < us.size(); ++i){

            int cox = us.get(i).getCoordOrigenX();
            int coy = us.get(i).getCoordOrigenY();

            if (us.get(i).isConductor()) {
                g2d.setColor(BLACK);
            } else {
                g2d.setColor(RED);
            }

            g2d.drawRect(cox*3, coy*3, (int) (5/(us.size()*COEFF)), (int) (5/(us.size()*COEFF)));
            g2d.fillRect(cox*3, coy*3,(int) (5/(us.size()*COEFF)), (int) (5/(us.size()*COEFF)));
        }

    }

    /*public static void main(String[] args) {
        Random al = new Random(System.currentTimeMillis());
        us = new Usuarios(10, 5, al.nextInt());
        colorUs = new int[us.size()];

        for (int i = 0; i < us.size(); ++i){
            colorUs[i] = (int)(Math.random() * 15777215)+1000000;
        }
        JFrame frame = new JFrame("Sharing Car");
        frame.add(new SharingCarGUI());
        frame.setSize(400, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }*/



}