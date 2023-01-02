package SharedObjs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class sharedObjSub {
    
    private int tempo;
    private int rolos = 1;
    private int tempoAS;
    private int tempoSec;
    private int SecaAsper = 1;
    private int tapete = 1;

    public sharedObjSub() {
    }
    
    public void restObjt() {
        this.tapete = 1;
        this.rolos = 1;
        this.SecaAsper = 1;
    }
    
    public void escreverLogSubModulos(String msg) {
        try {
            BufferedWriter buffWrite = new BufferedWriter(new FileWriter("log.txt", true));
            String date = LocalDateTime.now().toString();
            buffWrite.append("SUB-MODULO: ").append(msg).append("||").append(date).append("\n");
            buffWrite.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public synchronized int getTempo() {
        return tempo;
    }

    public synchronized void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public synchronized int getRolos() {return rolos;}

    public synchronized void setRolos(int rolos) {this.rolos = rolos;}

    public synchronized int getTempoAS() {
        return tempoAS;
    }

    public synchronized void setTempoAS(int tempoAS) {
        this.tempoAS = tempoAS;
    }

    public synchronized int getTempoSec() {
        return tempoSec;
    }

    public synchronized void setTempoSec(int tempoSec) {
        this.tempoSec = tempoSec;
    }

    public synchronized int getSecaAsper() {return SecaAsper;}

    public synchronized void setSecaAsper(int SecaAsper) {
        this.SecaAsper = SecaAsper;
    }

    public synchronized int getTapete() {
        return tapete;
    }

    public synchronized void setTapete(int tapete) {
        this.tapete = tapete;
    }


}
