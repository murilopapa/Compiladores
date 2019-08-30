package maquina.virtual;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Gerenciador {

    private static Gerenciador INSTANCE;

    private static Scanner scanner = new Scanner(System.in);
    private static Memoria memoria = new Memoria();
    private static Pilha pilha = new Pilha();
    private static ArrayList<Label> labels = new ArrayList<Label>();      //pego todas as labels que registrei

    public static Gerenciador getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Gerenciador();

        }
        return INSTANCE;
    }

    public boolean LerArquivo(File objLido) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(objLido));
            String text;
            int count_linha = 0;
            while ((text = reader.readLine()) != null) {
                System.out.println(text);
                String func = null, arg1 = null, arg2 = null;
                String[] splited = text.split(" ");
                for (int i = 0; i < splited.length; i++) {
                    switch (i) {
                        case 0:
                            func = splited[i];
                            break;
                        case 1:
                            arg1 = splited[i];
                            if (arg1.equals("NULL")) {

                                String aux;
                                aux = func;
                                func = arg1;
                                arg1 = aux;

                                Label newLabel = new Label();
                                newLabel.setLabel(arg1);
                                newLabel.setLinha(count_linha);
                                labels.add(newLabel);
                            }
                            break;
                        case 2:
                            arg2 = splited[i];
                            break;
                    }
                }
                memoria.addFuncao(func, arg1, arg2);
                count_linha++;
            }
        } catch (FileNotFoundException e) {
            return false;
            //e.printStackTrace();
        } catch (IOException e) {
            return false;
            //e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }
        return true;
    }

    public boolean ExecutaLinha() {
        boolean finished = false;
        int aux;
        String arg1s, arg2s;
        int arg1i, arg2i;
        Funcoes funcao_atual = memoria.getFuncByIndex(memoria.getI());
        switch (funcao_atual.getFuncao()) {
            case "LDC":     //coloca uma constante no topo da pilha
                arg1s = funcao_atual.getArg1();
                arg1i = Integer.parseInt(arg1s);
                pilha.setTopoPilha(arg1i);
                memoria.setI(memoria.getI() + 1);
                printaPilha(pilha, funcao_atual.getFuncao());
                break;  //OK

            case "LDV":     //pega um valor ja existente na pilha, mantem ele e copia pro topo
                arg1s = funcao_atual.getArg1();
                arg1i = Integer.parseInt(arg1s);
                aux = pilha.getIndexPilha(arg1i);
                pilha.setTopoPilha(aux);
                memoria.setI(memoria.getI() + 1);
                printaPilha(pilha, funcao_atual.getFuncao());
                break;  //OK

            case "ADD":     //soma o antepenultimo com o ultimo elemento da pilha, remove os dois e salva o novo
                arg1i = pilha.getTopoPilha();
                arg2i = pilha.getTopoPilha();
                aux = arg2i + arg1i;
                System.out.println("ADD: " + aux);
                pilha.setTopoPilha(aux);
                memoria.setI(memoria.getI() + 1);
                printaPilha(pilha, funcao_atual.getFuncao());
                break;  //OK

            case "SUB":     //subtrai o antepenultimo com o ultimo elemento da pilha, remove os dois e salva o novo
                arg1i = pilha.getTopoPilha();
                arg2i = pilha.getTopoPilha();
                aux = arg2i - arg1i;
                System.out.println("SUB: " + aux);
                pilha.setTopoPilha(aux);
                memoria.setI(memoria.getI() + 1);
                printaPilha(pilha, funcao_atual.getFuncao());
                break;  //OK

            case "MULT":    //multiplica o antepenultimo com o ultimo elemento da pilha, remove os dois e salva o novo
                arg1i = pilha.getTopoPilha();
                arg2i = pilha.getTopoPilha();
                aux = arg2i * arg1i;
                System.out.println("MULT: " + aux);
                pilha.setTopoPilha(aux);
                memoria.setI(memoria.getI() + 1);
                printaPilha(pilha, funcao_atual.getFuncao());
                break;  //OK

            case "DIVI":    //divide o antepenultimo com o ultimo elemento da pilha, remove os dois e salva o novo
                arg1i = pilha.getTopoPilha();
                arg2i = pilha.getTopoPilha();
                aux = arg2i / arg1i;
                System.out.println("DIVI: " + aux);
                pilha.setTopoPilha(aux);
                memoria.setI(memoria.getI() + 1);
                printaPilha(pilha, funcao_atual.getFuncao());
                break;  //OK

            case "INV":     //inverte o sinal do topo
                arg1i = pilha.getTopoPilha();
                aux = arg1i * -1;
                pilha.setTopoPilha(aux);
                memoria.setI(memoria.getI() + 1);
                printaPilha(pilha, funcao_atual.getFuncao());
                break;  //OK

            case "AND":     //penultimo AND ultimo, remove os dois e coloca 0 se for 0 e 1 se for 1 (é apenas pra binario)
                arg1i = pilha.getTopoPilha();
                arg2i = pilha.getTopoPilha();
                if (arg1i == 1 && arg2i == 1) {
                    aux = 1;
                } else {
                    aux = 0;
                }
                System.out.println("AND: " + aux);
                pilha.setTopoPilha(aux);
                memoria.setI(memoria.getI() + 1);
                printaPilha(pilha, funcao_atual.getFuncao());
                break;  //OK

            case "OR":      //penultimo OR ultimo, remove os dois e coloca 0 se for 0 e 1 se for 1 (é apenas pra binario)
                arg1i = pilha.getTopoPilha();
                arg2i = pilha.getTopoPilha();
                if (arg1i == 0 && arg2i == 0) {
                    aux = 0;
                } else {
                    aux = 1;
                }
                System.out.println("OR: " + aux);
                pilha.setTopoPilha(aux);
                memoria.setI(memoria.getI() + 1);
                printaPilha(pilha, funcao_atual.getFuncao());
                break;  //OK

            case "NEG":     //negacao, 1-topo, remove e coloca de novo
                arg1i = pilha.getTopoPilha();
                aux = 1 - arg1i;
                pilha.setTopoPilha(aux);
                memoria.setI(memoria.getI() + 1);
                printaPilha(pilha, funcao_atual.getFuncao());
                break;  //OK

            case "CME":     // se antepenultimo menor que topo, remove os dois e coloca 1 no topo
                arg1i = pilha.getTopoPilha();
                arg2i = pilha.getTopoPilha();
                if (arg2i < arg1i) {
                    aux = 1;
                } else {
                    aux = 0;
                }
                pilha.setTopoPilha(aux);
                memoria.setI(memoria.getI() + 1);
                printaPilha(pilha, funcao_atual.getFuncao());
                break;  //OK

            case "CMA":     // se antepenultimo maior que topo, remove os dois e coloca 1 no topo
                arg1i = pilha.getTopoPilha();
                arg2i = pilha.getTopoPilha();
                if (arg2i > arg1i) {
                    aux = 1;
                } else {
                    aux = 0;
                }
                pilha.setTopoPilha(aux);
                memoria.setI(memoria.getI() + 1);
                printaPilha(pilha, funcao_atual.getFuncao());
                break;  //OK

            case "CEQ":     // se antepenultimo igual ao topo, remove os dois e coloca 1 no topo
                arg1i = pilha.getTopoPilha();
                arg2i = pilha.getTopoPilha();
                if (arg2i == arg1i) {
                    aux = 1;
                } else {
                    aux = 0;
                }
                pilha.setTopoPilha(aux);
                memoria.setI(memoria.getI() + 1);
                printaPilha(pilha, funcao_atual.getFuncao());
                break;  //OK

            case "CDIF":    // se antepenultimo diferente que topo, remove os dois e coloca 1 no topo
                arg1i = pilha.getTopoPilha();
                arg2i = pilha.getTopoPilha();
                if (arg2i != arg1i) {
                    aux = 1;
                } else {
                    aux = 0;
                }
                pilha.setTopoPilha(aux);
                memoria.setI(memoria.getI() + 1);
                printaPilha(pilha, funcao_atual.getFuncao());
                break;  //OK

            case "CMEQ":    // se antepenultimo menor ou igual ao topo, remove os dois e coloca 1 no topo
                arg1i = pilha.getTopoPilha();
                arg2i = pilha.getTopoPilha();
                if (arg2i <= arg1i) {
                    aux = 1;
                } else {
                    aux = 0;
                }
                pilha.setTopoPilha(aux);
                memoria.setI(memoria.getI() + 1);
                printaPilha(pilha, funcao_atual.getFuncao());
                break;  //OK

            case "CMAQ":     // se antepenultimo maior ou igual ao topo, remove os dois e coloca 1 no topo
                arg1i = pilha.getTopoPilha();
                arg2i = pilha.getTopoPilha();
                if (arg2i >= arg1i) {
                    aux = 1;
                } else {
                    aux = 0;
                }
                pilha.setTopoPilha(aux);
                memoria.setI(memoria.getI() + 1);
                printaPilha(pilha, funcao_atual.getFuncao());
                break;  //OK

            case "START":       //inicia o codigo
                System.out.println("Iniciado!");
                memoria.setI(memoria.getI() + 1);
                printaPilha(pilha, funcao_atual.getFuncao());
                break;  //OK

            case "HLT":         //termina o codigo
                System.out.println("Finalizado!");
                finished = true;
                memoria.setI(0);
                printaPilha(pilha, funcao_atual.getFuncao());     //funcao extra para printar a pilha
                break;  //OK

            case "STR":         //pega o valor do topo, remove, e salva em uma posiçao ja existente
                arg1s = funcao_atual.getArg1();
                arg1i = Integer.parseInt(arg1s);

                arg2i = pilha.getTopoPilha();
                pilha.setIndexPilha(arg1i, arg2i);

                memoria.setI(memoria.getI() + 1);
                printaPilha(pilha, funcao_atual.getFuncao());
                break;
            case "JMP":         //pula pra linha X
                arg1s = funcao_atual.getArg1();
                for (int i = 0; i < labels.size(); i++) {           //percorro
                    Label aux_label = labels.get(i);
                    if (aux_label.getLabel().equals(arg1s)) {       //se achar a label que eu quero dar o jump
                        memoria.setI(aux_label.getLinha());         //seto o "i" para o indice dessa label
                    }
                }
                memoria.setI(memoria.getI() + 1);
                printaPilha(pilha, funcao_atual.getFuncao());
                break;
            case "JMPF":        //pula pra linha X se o topo for 0 (ou seja, alguma comparaçao deu falso)
                arg1s = funcao_atual.getArg1();
                arg2i = pilha.getTopoPilha();
                if (arg2i == 0) {
                    for (int i = 0; i < labels.size(); i++) {           //percorro
                        Label aux_label = labels.get(i);
                        if (aux_label.getLabel().equals(arg1s)) {       //se achar a label que eu quero dar o jump
                            memoria.setI(aux_label.getLinha());         //seto o "i" para o indice dessa label
                        }
                    }
                } else {
                    memoria.setI(memoria.getI() + 1);
                }
                printaPilha(pilha, funcao_atual.getFuncao());
                break;
            case "NULL":        //serve so para colocar a LABEL da linha
                memoria.setI(memoria.getI() + 1);
                printaPilha(pilha, funcao_atual.getFuncao());
                break;  //OK
            case "RD":          //le um inteiro do terminal
                System.out.println("ENTRADA:");
                arg1i = scanner.nextInt();
                pilha.setTopoPilha(arg1i);
                memoria.setI(memoria.getI() + 1);
                printaPilha(pilha, funcao_atual.getFuncao());
                break;  //OK
            case "PRN":         //printa o valor do topo e remove ele
                arg1i = pilha.getTopoPilha();
                System.out.println("SAIDA: " + arg1i);
                memoria.setI(memoria.getI() + 1);
                printaPilha(pilha, funcao_atual.getFuncao());
                break;  //OK
            case "ALLOC":
                arg1s = funcao_atual.getArg1();
                arg2s = funcao_atual.getArg2();
                arg1i = Integer.parseInt(arg1s);
                arg2i = Integer.parseInt(arg2s);

                for (int i = 0; i <= arg2i - 1; i++) {
                    if ((arg1i + i) < pilha.getDadosSize()) {
                        pilha.setTopoPilha(pilha.getIndexPilha(arg1i + i));
                    } else {
                        pilha.setTopoPilha(0);
                    }

                }
                memoria.setI(memoria.getI() + 1);
                printaPilha(pilha, funcao_atual.getFuncao());
                break;
            case "DALLOC":
                arg1s = funcao_atual.getArg1();
                arg2s = funcao_atual.getArg2();
                arg1i = Integer.parseInt(arg1s);
                arg2i = Integer.parseInt(arg2s);
                // O ERRO DO EXEMPLO 2 ESTA NA CONDIÇÃO DO IF, ARRUMAR
                for (int i = arg2i - 1; i >= 0; i--) {
                    if ((arg1i + i) >= pilha.getDadosSize() - 1) {
                        pilha.getTopoPilha();
                    } else {
                        pilha.setIndexPilha(arg1i + i, pilha.getTopoPilha());
                    }

                }
                memoria.setI(memoria.getI() + 1);
                printaPilha(pilha, funcao_atual.getFuncao());
                break;
            case "CALL":
                arg1s = funcao_atual.getArg1();

                for (int i = 0; i < labels.size(); i++) {           //percorro
                    Label aux_label = labels.get(i);
                    if (aux_label.getLabel().equals(arg1s)) {       //se achar a label que eu quero dar o jump
                        pilha.setTopoPilha(memoria.getI() + 1);
                        memoria.setI(aux_label.getLinha());         //seto o "i" para o indice dessa label
                    }
                }

                break;
            case "RETURN":
                memoria.setI(pilha.getTopoPilha());
                break;
        }
        if (finished) {
            return true;
        }
        else{
            return false;
        }
    }

    public void printaPilha(Pilha pilha, String comando) {
        System.out.println("----------");
        System.out.println("COMANDO: " + comando);
        System.out.println("PILHA:");
        for (int i = pilha.getDadosSize() - 1; i >= 0; i--) {
            System.out.println("[" + i + "]" + " " + pilha.getIndexPilha(i));
        }
        System.out.println("----------");
    }

    public int TamanhoMemoria() {
        return memoria.getTotalLinhas();
    }

    public Memoria getMemoria() {
        return memoria;
    }

    public Pilha getPilha() {
        return pilha;
    }
    
}
