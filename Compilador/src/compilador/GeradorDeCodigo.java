package compilador;

import java.util.ArrayList;

public class GeradorDeCodigo {

    ArrayList<String> codigo = new ArrayList<String>();

    public void geraLDC(int k) {
        String operacao = "LDC " + k;
        codigo.add(operacao);
    }

    public void geraLDV(int n) {
        String operacao = "LDV " + n;
        codigo.add(operacao);
    }

    public void geraADD() {
        String operacao = "ADD";
        codigo.add(operacao);
    }

    public void geraSUB() {
        String operacao = "SUB";
        codigo.add(operacao);
    }

    public void geraMULT() {
        String operacao = "MULT";
        codigo.add(operacao);
    }

    public void geraDIVI() {
        String operacao = "DIVI";
        codigo.add(operacao);
    }

    public void geraINV() {
        String operacao = "INV";
        codigo.add(operacao);
    }

    public void geraAND() {
        String operacao = "AND";
        codigo.add(operacao);
    }

    public void geraOR() {
        String operacao = "OR";
        codigo.add(operacao);
    }

    public void geraNEG() {
        String operacao = "NEG";
        codigo.add(operacao);
    }

    public void geraCME() {
        String operacao = "CME";
        codigo.add(operacao);
    }

    public void geraCMA() {
        String operacao = "CMA";
        codigo.add(operacao);
    }

    public void geraCEQ() {
        String operacao = "CEQ";
        codigo.add(operacao);
    }

    public void geraCDIF() {
        String operacao = "CDIF";
        codigo.add(operacao);
    }

    public void geraCMEQ() {
        String operacao = "CMEQ";
        codigo.add(operacao);
    }

    public void geraCMAQ() {
        String operacao = "CMAQ";
        codigo.add(operacao);
    }

    public void geraSTART() {
        String operacao = "START";
        codigo.add(operacao);
    }

    public void geraHLT() {
        String operacao = "HLT";
        codigo.add(operacao);
    }

    public void geraSTR(int n) {
        String operacao = "STR " + n;
        codigo.add(operacao);
    }

    public void geraJMP(int label) {
        String operacao = "JMP L" + label;
        codigo.add(operacao);
    }

    public void geraJMPF(int label) {
        String operacao = "JMPF L" + label;
        codigo.add(operacao);
    }

    public void geraNULL(int label) {
        String operacao = "L" + label + " NULL";
        codigo.add(operacao);
    }

    public void geraRD() {
        String operacao = "RD";
        codigo.add(operacao);
    }

    public void geraPRN() {
        String operacao = "PRN";
        codigo.add(operacao);
    }

    public void geraALLOC(int m, int n) {
        String operacao = "ALLOC " + m + "," + n;
        codigo.add(operacao);
    }

    public void geraDALLOC(int m, int n) {
        String operacao = "DALLOC " + m + "," + n;
        codigo.add(operacao);
    }

    public void geraCALL(int label) {
        String operacao = "CALL L" + label;
        codigo.add(operacao);
    }

    public void geraRETURN() {
        String operacao = "RETURN";
        codigo.add(operacao);
    }

    public void printaCodigo() {
        for (String n : codigo) {
            System.out.println(n);
        }
    }

    public void geraPOSFIXA(ArrayList<Elemento> posFixa) {
        for (Elemento e : posFixa) {
            if (e instanceof ElementoOperando) {
                try {
                    Integer.parseInt(e.getNome());
                    geraLDC(Integer.parseInt(e.getNome()));
                } catch (NumberFormatException err) {
                    geraLDV(((ElementoOperando) e).getMemoria());
                }

            } else {
                switch (e.getNome()) {
                    case "+":
                        geraADD();
                        break;
                    case "-":
                        geraSUB();
                        break;
                    case "*":
                        geraMULT();
                        break;
                    case "div":
                        geraDIVI();
                        break;
                    case "<":
                        geraCME();
                        break;
                    case "<=":
                        geraCMEQ();
                        break;
                    case ">":
                        geraCMA();
                        break;
                    case ">=":
                        geraCMAQ();
                        break;
                    case "=":
                        geraCEQ();
                        break;
                    case "!=":
                        geraCDIF();
                        break;
                    case "e":
                        geraAND();
                        break;
                    case "ou":
                        geraOR();
                        break;

                }
            }
        }
    }
}
