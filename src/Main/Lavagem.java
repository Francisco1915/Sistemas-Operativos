package Main;

import Queue.EmptyCollectionException;
import SharedObjs.sharedObj;

import javax.swing.*;
import java.util.concurrent.Semaphore;

public class Lavagem extends JFrame implements Runnable {

    sharedObj srobj;
    Semaphore lavagem;
    Semaphore tapete;
    Semaphore rolos;
    Semaphore secagemAspersores;

    JFrame mainInfo;
    JLabel ncarros;
    JLabel ncarrosLavados;
    JLabel infoOcupate;
    JLabel infoOpen;

    public Lavagem(JFrame mainInfo, JLabel ncarros, JLabel ncarrosLavados, JLabel infoOcupate, JLabel infoOpen, sharedObj srobj, Semaphore s, Semaphore s1, Semaphore s2, Semaphore s3) {
        this.srobj = srobj;
        this.lavagem = s;
        this.tapete = s1;
        this.rolos = s2;
        this.secagemAspersores = s3;
        this.mainInfo = mainInfo;
        this.ncarros = ncarros;
        this.ncarrosLavados = ncarrosLavados;
        this.infoOcupate = infoOcupate;
        this.infoOpen = infoOpen;
    }

    public synchronized void atualizarInfo() {
        this.ncarros.setText("Carros em fila: " + this.srobj.getnCarros());
        this.ncarrosLavados.setText("Carros Lavados: " + this.srobj.getnCarrosLavados());

        if (this.srobj.getIsOcupate() == 0) {
            this.infoOcupate.setText("Livre");
        } else if (this.srobj.getIsOcupate() == 1) {
            this.infoOcupate.setText("Ocupado");
        }

        if (this.srobj.getIsOpen() == 0) {
            this.infoOpen.setText("Sistema:   Aberto        e");
        } else if (this.srobj.getIsOpen() == 1) {
            this.infoOpen.setText("Sistema:   Fechado        e");
        }
    }

    @Override
    public void run() {

        while(true) {
            try {
                this.lavagem.acquire();
                while (this.srobj.getnCarros() != 0) {

                    this.srobj.remove1Carro();

                    this.srobj.setIsOcupate(1);
                    this.srobj.escreverLogMAIN("Sistema Ocupado");
                    this.atualizarInfo();

                    this.tapete.release();
                    this.lavagem.acquire();
                    this.rolos.release();
                    this.lavagem.acquire();
                    this.secagemAspersores.release();
                    this.lavagem.acquire();
                    this.tapete.release();

                    this.srobj.add1CarroLavado();

                    this.srobj.setIsOcupate(0);
                    this.srobj.escreverLogMAIN("Sistema Livre");
                    this.atualizarInfo();

                    Thread.sleep(5000);
                }
            } catch (InterruptedException | EmptyCollectionException ex) {
                ex.printStackTrace();
            }
        }
    }

}
