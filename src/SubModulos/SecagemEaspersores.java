package SubModulos;

import SharedObjs.sharedObjSub;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Semaphore;

public class SecagemEaspersores extends JFrame implements Runnable {

    sharedObjSub srObj;
    Semaphore secagemEaspersoresS;
    Semaphore lavagem;

    JLabel estado = new JLabel("SECA ASPER PARADO");

    public SecagemEaspersores(sharedObjSub srdObj, Semaphore secagemEaspersoresS, Semaphore s) {
        this.srObj = srdObj;
        this.lavagem = s;
        this.secagemEaspersoresS = secagemEaspersoresS;
    }

    @Override
    public void run() {
        
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        this.setLayout(null);
        this.estado.setBounds(45, 30, 400, 50);
        this.estado.setBackground(Color.red);
        Font font = new Font("Times New Roman", Font.BOLD, 24);
        this.estado.setFont(font);
        this.setTitle("Secador e Aspersores");
        this.setSize(400, 150);
        this.setLocation(120, 371);
        this.setResizable(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.add(this.estado);

        while (true) {
            try {
                this.secagemEaspersoresS.acquire();

                if (this.srObj.getSecaAsper() == 1) {

                    this.srObj.escreverLogSubModulos("Aspersores ativos");
                    this.srObj.setSecaAsper(2);
                    this.estado.setText("ASPERSORES ATIVOS");
                    this.estado.setForeground(Color.green);

                    Thread.sleep(this.srObj.getTempoAS() * 1000L);

                    this.srObj.escreverLogSubModulos("Aspersores Desativos");
                    this.estado.setForeground(Color.red);
                    this.estado.setText("ASPERSORES DESATIVADOS");

                    Thread.sleep(1000);

                    this.srObj.escreverLogSubModulos("Secador Ativo");
                    this.estado.setText("SECADOR ATIVO");
                    this.estado.setForeground(Color.green);

                    Thread.sleep(this.srObj.getTempoSec() * 1000L);

                }

                if (this.srObj.getSecaAsper() == 2) {

                    this.srObj.escreverLogSubModulos("Secador Desativo");
                    this.srObj.setSecaAsper(1);
                    this.estado.setText("SECA E ASPER PARADO");
                    this.estado.setForeground(Color.red);
                }
                
                this.lavagem.release();

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
