package SubModulos;

import SharedObjs.sharedObj;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.Semaphore;
import javax.swing.*;

public class InterfaceEmoedeiro extends JFrame implements Runnable, ActionListener {

    sharedObj srdObj;
    Semaphore mainSem;

    JButton button1 = new JButton("I");
    JButton button2 = new JButton("C");
    JButton button3 = new JButton("E");
    JButton button4 = new JButton("AF");
    JButton button5 = new JButton("R");

    JLabel welcome = new JLabel("BEM VINDO");
    JLabel welcome2 = new JLabel("ESCOLHA UMA OPÇÃO");
    JLabel cancelar = new JLabel("C - CANCELAR");
    JLabel iniciar = new JLabel("I - INICIAR LAVAGEM");
    JLabel af = new JLabel("A/F - USAR CHAVE");
    JLabel emergencia = new JLabel("E - PARAGEM IMEDIATA");
    JLabel reset = new JLabel("R - RESET DO SISTEMA");

    //Moedeiro
    JFrame moedeiro = new JFrame();
    JButton button6 = new JButton("2$");
    JButton button7 = new JButton("1$");
    JButton button8 = new JButton("0.5$");
    JButton button9 = new JButton("-->");
    JLabel inserir = new JLabel("INSERIR MOEDA");
    JLabel tarifa = new JLabel("TARIFA: ");
    JLabel valor = new JLabel("INSERIDO: ");

    public InterfaceEmoedeiro(sharedObj sObj, Semaphore sem) {
        this.srdObj = sObj;
        this.mainSem = sem;
    }
    
    public void escreverLogInterfaceMoedeiro(String msg) {
        try {
            BufferedWriter buffWrite = new BufferedWriter(new FileWriter("log.txt", true));
            String date = LocalDateTime.now().toString();
            buffWrite.append("InterModeiro: ").append(msg).append("||").append(date).append("\n");
            buffWrite.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.button1) {
            this.srdObj.setButton(1);
            escreverLogInterfaceMoedeiro("Iniciar Lavagem");
            this.mainSem.release();
        }

        if (e.getSource() == this.button2) {
            this.srdObj.setButton(2);
            escreverLogInterfaceMoedeiro("Cancelar");
            this.mainSem.release();
        }

        if (e.getSource() == this.button3) {
            this.srdObj.setButton(3);
            this.mainSem.release();
        }

        if (e.getSource() == this.button4) {
            this.srdObj.setButton(4);
            this.mainSem.release();
        }

        if (e.getSource() == this.button5) {
            this.srdObj.setButton(5);
            escreverLogInterfaceMoedeiro("Reset");
            this.mainSem.release();
        }

        if (e.getSource() == this.button6) {
            this.srdObj.setButton(6);
            escreverLogInterfaceMoedeiro("2 euros");
            this.mainSem.release();
        }

        if (e.getSource() == this.button7) {
            this.srdObj.setButton(7);
            escreverLogInterfaceMoedeiro("1 euro");
            this.mainSem.release();
        }

        if (e.getSource() == this.button8) {
            this.srdObj.setButton(8);
            escreverLogInterfaceMoedeiro("50 centimos");
            this.mainSem.release();
        }

        if (e.getSource() == this.button9) {
            this.srdObj.setButton(9);
            escreverLogInterfaceMoedeiro("Inserir na fila");
            this.mainSem.release();
        }

    }

    @Override
    public void run() {
        this.setLayout(null);
        this.button1.setBounds(100, 100, 50, 50);
        this.button2.setBounds(170, 100, 50, 50);
        this.button3.setBounds(240, 100, 50, 50);
        this.button4.setBounds(310, 100, 50, 50);
        this.button5.setBounds(200, 170, 50, 50);
        this.cancelar.setBounds(25, 200, 200, 200);
        this.iniciar.setBounds(250, 200, 200, 200);
        this.af.setBounds(250, 250, 200, 200);
        this.emergencia.setBounds(25, 250, 200, 200);
        this.reset.setBounds(125, 300, 200, 200);
        this.welcome.setBounds(165, 20, 200, 25);
        this.welcome2.setBounds(95, 45, 400, 26);
        Font font = new Font("Times New Roman", Font.BOLD, 24);
        this.cancelar.setFont(this.cancelar.getFont().deriveFont(16.0F));
        this.iniciar.setFont(this.cancelar.getFont());
        this.af.setFont(this.cancelar.getFont());
        this.emergencia.setFont(this.cancelar.getFont());
        this.reset.setFont(this.cancelar.getFont());
        this.welcome.setFont(font);
        this.welcome2.setFont(this.welcome.getFont());
        this.add(this.button1);
        this.add(this.button2);
        this.add(this.button3);
        this.add(this.button4);
        this.add(this.button5);
        this.add(this.cancelar);
        this.add(this.iniciar);
        this.add(this.af);
        this.add(this.emergencia);
        this.add(this.reset);
        this.add(this.welcome);
        this.add(this.welcome2);
        this.button1.addActionListener(this);
        this.button2.addActionListener(this);
        this.button3.addActionListener(this);
        this.button4.addActionListener(this);
        this.button5.addActionListener(this);
        this.setTitle("Lavagem de Carros");
        this.setSize(500, 600);
        this.setResizable(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Moedeiro
        moedeiro.setLayout(null);
        this.button6.setBounds(40, 200, 60, 50);
        this.button7.setBounds(115, 200, 60, 50);
        this.button8.setBounds(40, 275, 60, 50);
        this.button9.setBounds(115, 275, 60, 50);
        this.inserir.setBounds(50, 20, 200, 25);
        this.tarifa.setBounds(60, 80, 200, 25);
        this.valor.setBounds(50, 110, 200, 25);
        this.inserir.setFont(this.inserir.getFont().deriveFont(16.0F));
        this.tarifa.setFont(this.inserir.getFont());
        this.valor.setFont(this.inserir.getFont());
        this.tarifa.setText("TARIFA: " + this.srdObj.getTarifa());
        moedeiro.add(this.button6);
        moedeiro.add(this.button7);
        moedeiro.add(this.button8);
        moedeiro.add(this.button9);
        moedeiro.add(this.inserir);
        moedeiro.add(this.tarifa);
        moedeiro.add(this.valor);
        this.button7.addActionListener(this);
        this.button8.addActionListener(this);
        this.button9.addActionListener(this);
        this.button6.addActionListener(this);
        moedeiro.setTitle("Moedeiro");
        moedeiro.setVisible(true);
        moedeiro.setResizable(false);
        moedeiro.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        moedeiro.setLocation(1200, 221);
        moedeiro.setSize(250, 400);

        while (true) {
            this.valor.setText("INSERIDO: " + this.srdObj.getMoedas());
            this.tarifa.setText("TARIFA: " + this.srdObj.getTarifa());
        }
    }
}
