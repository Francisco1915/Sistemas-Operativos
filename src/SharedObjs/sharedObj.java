package SharedObjs;

import Queue.Carro;
import Queue.EmptyCollectionException;
import Queue.LinkedQueue;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class sharedObj extends JFrame {
    private double moedas;
    private double tarifa;
    private final LinkedQueue<Carro> listaCarros;
    private int nCarrosLavados;
    // 0 - Livre
    // 1 - Ocupado
    private int isOcupate = 0;
    // 0 - Aberto
    // 1 - Ocupado
    private int isOpen = 0;
    private int button;
    private boolean block = false;

    public sharedObj() {
        this.listaCarros = new LinkedQueue<>();
    }
    
    public void resetObj() {
        this.block = false;
        this.isOcupate = 0;
        this.moedas = 0;
        while (this.listaCarros.size() != 0) {
            try {
                this.listaCarros.dequeue();
            } catch (EmptyCollectionException e) {
                e.printStackTrace();
            }
        }
        this.nCarrosLavados = 0;
    }

    public synchronized void escreverLogMAIN(String msg) {
        try {
            BufferedWriter buffWrite = new BufferedWriter(new FileWriter("log.txt", true));
            String date = LocalDateTime.now().toString();
            buffWrite.append("MAIN: ").append(msg).append("||").append(date).append("\n");
            buffWrite.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public synchronized int getnCarros() {
        return this.listaCarros.size();
    }

    public synchronized void add1Carro(int i) {
        this.listaCarros.enqueue(new Carro(i));
    }
    
    public synchronized void remove1Carro() throws EmptyCollectionException {this.listaCarros.dequeue();}

    public int getIsOcupate() {return isOcupate;}

    public void setIsOcupate(int isOcupate) {this.isOcupate = isOcupate;}

    public synchronized int getnCarrosLavados() {
        return nCarrosLavados;
    }

    public synchronized void add1CarroLavado() {
        this.nCarrosLavados += 1;
    }    

    public synchronized int getIsOpen() {
        return this.isOpen;
    }

    public synchronized void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }
    
    public synchronized double getMoedas() {
        return this.moedas;
    }
    
    public synchronized void set0Moedas() {
        this.moedas = 0;
    }
    
    public synchronized void setMoedas(double moedas) {
        this.moedas += moedas;
    }

    public synchronized double getTarifa() {
        return tarifa;
    }

    public synchronized void setTarifa(double tarifa) {
        this.tarifa = tarifa;
    }

    public synchronized int getButton() {
        return this.button;
    }

    public synchronized void setButton(int button) {
        this.button = button;
    }

    public synchronized boolean isBlock() {
        return this.block;
    }

    public synchronized void setBlock(boolean block) {this.block = block;}
}