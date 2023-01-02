package SubModulos;

import SharedObjs.sharedObjSub;

import java.awt.Color;
import java.awt.Font;
import java.util.concurrent.Semaphore;
import javax.swing.*;

public class Rolos extends JFrame implements Runnable {

    sharedObjSub srObj;
    Semaphore roloS;
    Semaphore lavagem;

    JLabel estado = new JLabel("ROLO PARADO");

    public Rolos(sharedObjSub srdObj, Semaphore moedeiroSem, Semaphore s) {
        this.srObj = srdObj;
        this.lavagem = s;
        this.roloS = moedeiroSem;
    }

    @Override
    public void run() {
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        this.setLayout(null);
        this.estado.setBounds(45, 30, 400, 50);
        this.estado.setBackground(Color.red);
        Font font = new Font("Times New Roman", Font.BOLD, 24);
        this.estado.setFont(font);
        this.setTitle("Rolos");
        this.setSize(300, 150);
        this.setLocation(420, 221);
        this.setResizable(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.add(this.estado);

        while (true) {
            try {
                
                this.roloS.acquire();

                if (this.srObj.getRolos() == 1){

                    this.srObj.escreverLogSubModulos("Rolos Ativos");
                    this.srObj.setRolos(2);
                    this.estado.setText("ROLO ATIVO");
                    this.estado.setForeground(Color.green);

                    Thread.sleep(this.srObj.getTempo() * 1000L);
                }

                if (this.srObj.getRolos() == 2) {

                    this.srObj.escreverLogSubModulos("Rolos desativos");
                    this.srObj.setRolos(1);
                    this.estado.setText("ROLO PARADO");
                    this.estado.setForeground(Color.red);

                    this.lavagem.release();
                }

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
