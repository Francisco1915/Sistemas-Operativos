package Main;

import SharedObjs.sharedObj;
import SharedObjs.sharedObjSub;
import SubModulos.InterfaceEmoedeiro;
import SubModulos.Rolos;
import SubModulos.SecagemEaspersores;
import SubModulos.Tapete;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Semaphore;
import javax.swing.*;

public class Main implements Runnable {

    sharedObj srdObj;
    sharedObjSub srdObjLavagem = new sharedObjSub();
    Semaphore InterfaceS;
    Semaphore tapeteS = new Semaphore(0);
    Semaphore roloS = new Semaphore(0);
    Semaphore secagemEaspersoresS = new Semaphore(0);
    Semaphore lavagem = new Semaphore(0);
    int count = 0;
    Thread InterfaceEmoedeiro;
    Thread tapete;
    Thread rolo;
    Thread secagemEaspersores;
    Thread Tlavagem;

    //INFO
    JFrame mainInfo = new JFrame();
    JLabel nCarros = new JLabel("Carros em fila: ");
    JLabel nCarrosLavados = new JLabel("Carros Lavados: ");
    JLabel infoOcupate = new JLabel("Livre");
    JLabel infoOpen = new JLabel("Aberto");

    public Main(sharedObj srObj, Semaphore s) {
        this.srdObj = srObj;
        this.InterfaceS = s;
        this.InterfaceEmoedeiro = new Thread(new InterfaceEmoedeiro(this.srdObj, this.InterfaceS));
        this.tapete = new Thread(new Tapete(this.srdObjLavagem, this.srdObj, this.tapeteS, this.lavagem));
        this.rolo = new Thread(new Rolos(this.srdObjLavagem, this.roloS, this.lavagem));
        this.secagemEaspersores = new Thread(new SecagemEaspersores(this.srdObjLavagem, this.secagemEaspersoresS, this.lavagem));
        this.Tlavagem = new Thread(new Lavagem(this.mainInfo, this.nCarros, this.nCarrosLavados, this.infoOcupate, this.infoOpen, this.srdObj, this.lavagem, this.tapeteS, this.roloS, this.secagemEaspersoresS));
    }

    public void lerLinhas() {

        try {
            BufferedReader readFile = new BufferedReader(new FileReader("LavagemCarrosInfo.txt"));
            this.srdObj.setTarifa(Integer.parseInt(readFile.readLine()));
            this.srdObjLavagem.setTempo(Integer.parseInt(readFile.readLine()));
            this.srdObjLavagem.setTempoAS(Integer.parseInt(readFile.readLine()));
            this.srdObjLavagem.setTempoSec(Integer.parseInt(readFile.readLine()));
            readFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized void iniciarInfo() {
        //Info
        this.mainInfo.setLayout(null);
        this.nCarros.setBounds(25, 13, 200, 100);
        this.nCarrosLavados.setBounds(275, 13, 200, 100);
        this.infoOcupate.setBounds(300, 0, 200, 50);
        this.infoOpen.setBounds(100, 0, 200, 50);
        this.nCarros.setFont(this.nCarros.getFont().deriveFont(16.0F));
        this.nCarrosLavados.setFont(this.nCarros.getFont());
        this.infoOcupate.setFont(this.nCarros.getFont());
        this.infoOpen.setFont(this.nCarros.getFont());
        this.nCarros.setText("Carros em fila: " + this.srdObj.getnCarros());
        this.nCarrosLavados.setText("Carros Lavados: " + this.srdObj.getnCarrosLavados());
        this.mainInfo.add(this.nCarros);
        this.mainInfo.add(this.nCarrosLavados);
        this.mainInfo.add(this.infoOcupate);
        this.mainInfo.add(this.infoOpen);
        this.mainInfo.setTitle("Lavagem de Carros INFO");
        this.mainInfo.setLocation(120, 521);
        this.mainInfo.setSize(500, 150);
        this.mainInfo.setResizable(false);
        this.mainInfo.setVisible(true);
        this.mainInfo.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    public synchronized void atualizarInfo() {
        this.nCarros.setText("Carros em fila: " + this.srdObj.getnCarros());
        this.nCarrosLavados.setText("Carros Lavados: " + this.srdObj.getnCarrosLavados());

        if (this.srdObj.getIsOcupate() == 0) {
            this.infoOcupate.setText("Livre");
        } else if (this.srdObj.getIsOcupate() == 1) {
            this.infoOcupate.setText("Ocupado");
        }

        if (this.srdObj.getIsOpen() == 0) {
            this.infoOpen.setText("Sistema:   Aberto        e");
        } else if (this.srdObj.getIsOpen() == 1) {
            this.infoOpen.setText("Sistema:   Fechado        e");
        }
    }

    @Override
    public void run() {

        //Ler o ficheiro de configuração
        this.lerLinhas();
        this.srdObj.escreverLogMAIN("Ler ficheiro de info");

        this.InterfaceEmoedeiro.start();
        this.tapete.start();
        this.rolo.start();
        this.secagemEaspersores.start();
        this.Tlavagem.start();
        this.iniciarInfo();

        while (true) {

            this.atualizarInfo();

            try {
                this.InterfaceS.acquire();
                switch (this.srdObj.getButton()) {
                    case 1:
                        if (this.srdObj.isBlock()) {
                            JOptionPane.showMessageDialog(null, "SYSTEM IS OFF!", "Lavagem de carro Info", JOptionPane.ERROR_MESSAGE);
                        } else {
                            if (this.srdObj.getIsOcupate() == 0 && this.srdObj.getIsOpen() == 0) {
                                if (this.srdObj.getnCarros() != 0) {
                                    this.lavagem.release();
                                } else {
                                    JOptionPane.showMessageDialog(null, "Nenhum carro em fila \nIntroduzir moedas", "Moedeiro Info", JOptionPane.INFORMATION_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Sistema Fechado", "Lavagem de carro Info", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        break;
                    case 2:
                        if (this.srdObj.isBlock()) {
                            JOptionPane.showMessageDialog(null, "SYSTEM IS OFF!", "Lavagem de carro Info", JOptionPane.ERROR_MESSAGE);
                        } else {
                            if (this.srdObj.getIsOpen() == 0) {

                                if (this.srdObj.getMoedas() > 0) {
                                    JOptionPane.showMessageDialog(null, "Transação cancelada \nReceber Troco: " + this.srdObj.getMoedas(), "Moedeiro Info", JOptionPane.INFORMATION_MESSAGE);
                                    this.srdObj.set0Moedas();
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Sistema Fechado", "Lavagem de carro Info", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        break;
                    case 3:
                        this.srdObj.setBlock(true);
                        break;
                    case 4:
                        if (this.srdObj.isBlock()) {
                            JOptionPane.showMessageDialog(null, "SYSTEM IS OFF!", "Lavagem de carro Info", JOptionPane.ERROR_MESSAGE);
                        } else {
                            if (this.srdObj.getIsOpen() == 0) {
                                this.srdObj.setIsOpen(1);
                                this.srdObj.escreverLogMAIN("Lavagem de carros fechada");
                                break;
                            }

                            this.srdObj.setIsOpen(0);
                            this.srdObj.escreverLogMAIN("Lavagem de carros aberta");
                        }
                        break;
                    case 5:
                        if (this.srdObj.isBlock()) {
                            JOptionPane.showMessageDialog(null, "SYSTEM IS OFF!", "Lavagem de carro Info", JOptionPane.ERROR_MESSAGE);
                        } else {
                            if (this.srdObj.getIsOpen() == 0) {
                                if (this.srdObj.getIsOcupate() == 0) {

                                    this.srdObj.resetObj();
                                    this.srdObjLavagem.restObjt();
                                    this.lerLinhas();
                                    this.srdObj.escreverLogMAIN("Ler ficheiro de info");
                                    JOptionPane.showMessageDialog(null, "Reset ao Sistema", "Lavagem de carro Info", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Sistema Ocupado", "Lavagem de carro Info", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Sistema Fechado", "Lavagem de carro Info", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        break;
                    case 6:
                        if (this.srdObj.isBlock()) {
                            JOptionPane.showMessageDialog(null, "SYSTEM IS OFF!", "Lavagem de carro Info", JOptionPane.ERROR_MESSAGE);
                        } else {
                            if (this.srdObj.getIsOpen() == 0) {

                                this.srdObj.setMoedas(2);
                            } else {
                                JOptionPane.showMessageDialog(null, "Sistema Fechado", "Lavagem de carro Info", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        break;
                    case 7:
                        if (this.srdObj.isBlock()) {
                            JOptionPane.showMessageDialog(null, "SYSTEM IS OFF!", "Lavagem de carro Info", JOptionPane.ERROR_MESSAGE);
                        } else {
                            if (this.srdObj.getIsOpen() == 0) {
                                this.srdObj.setMoedas(1);
                            } else {
                                JOptionPane.showMessageDialog(null, "Sistema Fechado", "Lavagem de carro Info", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        break;
                    case 8:
                        if (this.srdObj.isBlock()) {
                            JOptionPane.showMessageDialog(null, "SYSTEM IS OFF!", "Lavagem de carro Info", JOptionPane.ERROR_MESSAGE);
                        } else {
                            if (this.srdObj.getIsOpen() == 0) {
                                this.srdObj.setMoedas(0.5);
                            } else {
                                JOptionPane.showMessageDialog(null, "Sistema Fechado", "Lavagem de carro Info", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        break;
                    case 9:
                        if (this.srdObj.isBlock()) {
                            JOptionPane.showMessageDialog(null, "SYSTEM IS OFF!", "Lavagem de carro Info", JOptionPane.ERROR_MESSAGE);
                        } else {
                            if (this.srdObj.getIsOpen() == 0) {
                                if (this.srdObj.getMoedas() == 0) {
                                    JOptionPane.showMessageDialog(null, "Introduzir moedas", "Moedeiro Info", JOptionPane.INFORMATION_MESSAGE);
                                } else if (this.srdObj.getTarifa() > this.srdObj.getMoedas()) {
                                    JOptionPane.showMessageDialog(null, "Devolver dinheiro: " + this.srdObj.getMoedas(), "Moedeiro Info", JOptionPane.INFORMATION_MESSAGE);
                                    this.srdObj.set0Moedas();
                                } else {
                                    this.srdObj.add1Carro(count++);
                                    double troco = this.srdObj.getMoedas() - this.srdObj.getTarifa();
                                    JOptionPane.showMessageDialog(null, "Receber Troco: " + troco + "\n Inserido na fila", "Moedeiro Info", JOptionPane.INFORMATION_MESSAGE);
                                    this.srdObj.set0Moedas();
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Sistema Fechado", "Lavagem de carro Info", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        break;
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

    }
}
