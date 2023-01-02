package SubModulos;

import SharedObjs.sharedObj;
import SharedObjs.sharedObjSub;

import java.awt.*;
import java.util.concurrent.Semaphore;
import javax.swing.*;

public class Tapete extends JFrame implements Runnable {

    sharedObjSub srObj;
    sharedObj srObj2;
    Semaphore tapeteS;
    Semaphore lavagem;

    JLabel estado = new JLabel("TAPETE PARADO");

    public Tapete(sharedObjSub srdObj, sharedObj srObjt2, Semaphore moedeiroSem, Semaphore s) {
        this.srObj = srdObj;
        this.srObj2 = srObjt2;
        this.lavagem = s;
        this.tapeteS = moedeiroSem;
    }

    @Override
    public void run() {
        this.setLayout(null);
        this.estado.setBounds(45, 30, 400, 50);
        this.estado.setBackground(Color.red);
        Font font = new Font("Times New Roman", Font.BOLD, 24);
        this.estado.setFont(font);
        this.setTitle("Tapete");
        this.setSize(300, 150);
        this.setLocation(120, 221);
        this.setResizable(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.add(this.estado);

        while (true) {

            try {
                this.tapeteS.acquire();
                switch (this.srObj.getTapete()) {
                    case 1:
                        this.srObj.escreverLogSubModulos("Tapete Ativo");
                        this.srObj.setTapete(2);
                        this.estado.setText("TAPETE ATIVO");
                        this.estado.setForeground(Color.green);
                        Thread.sleep(2000);
                        this.lavagem.release();
                    break;
                case 2:
                        Thread.sleep(3000);
                        this.srObj.setTapete(1);
                        this.estado.setText("TAPETE PARADO");
                        this.srObj.escreverLogSubModulos("Tapete Desativo");
                        this.estado.setForeground(Color.red);
                    break;
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
