package maquina.virtual;

import java.util.ArrayList;
import java.util.Scanner;

public class MaquinaVirtual {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Memoria memoria = new Memoria();
        Pilha pilha = new Pilha();
        //carrega o programa na Memoria (instruções)

        memoria.addFuncao("START", "0", "0");
        memoria.addFuncao("ALLOC", "0", "3");
        memoria.addFuncao("RD", "0", "0");
        memoria.addFuncao("STR", "1", "0");
        memoria.addFuncao("LDV", "1", "0");
        memoria.addFuncao("LDC", "1", "0");
        memoria.addFuncao("ADD", "0", "0");
        memoria.addFuncao("STR", "1", "0");
        memoria.addFuncao("LDV", "1", "0");
        memoria.addFuncao("LDC", "10", "0");
        memoria.addFuncao("CMA", "0", "0");
        memoria.addFuncao("JMPF", "L1", "0");
        memoria.addFuncao("LDC", "1", "0");
        memoria.addFuncao("STR", "3", "0");
        memoria.addFuncao("JMP", "L2", "0");
        memoria.addFuncao("NULL", "L1", "0");
        memoria.addFuncao("LDC", "2", "0");
        memoria.addFuncao("STR", "3", "0");
        memoria.addFuncao("NULL", "L2", "0");
        memoria.addFuncao("LDV", "1", "0");
        memoria.addFuncao("LDV", "3", "0");
        memoria.addFuncao("ADD", "0", "0");
        memoria.addFuncao("STR", "2", "0");
        memoria.addFuncao("LDV", "2", "0");
        memoria.addFuncao("PRN", "0", "0");
        memoria.addFuncao("DALLOC", "0", "3");
        memoria.addFuncao("HLT", "0", "0");

        boolean execute = true;

        while (execute) {
            int aux;
            String arg1s, arg2s;
            int arg1i, arg2i;
            Funcoes funcao_atual = memoria.getFuncByIndex(memoria.getI());
            ArrayList<Label> labels = memoria.getLabels();      //pego todas as labels que registrei
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
                    execute = false;
                    printaPilha(pilha, funcao_atual.getFuncao());     //funcao extra para printar a pilha
                    break;  //OK

                case "STR":         //pega o valor do topo, remove, e salva em uma posiçao ja existente
                    arg1s = funcao_atual.getArg1();
                    arg1i = Integer.parseInt(arg1s);
                    if (pilha.getDadosSize() - 1 == arg1i) {
                        pilha.getTopoPilha();
                    } else {
                        if (pilha.getDadosSize() - 1 < arg1i) {

                        } else {
                            arg2i = pilha.getTopoPilha();
                            pilha.setIndexPilha(arg1i, arg2i);
                        }
                    }
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
                            pilha.setTopoPilha(-999999);
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

                    for (int i = arg2i - 1; i >= 0; i--) {
                        if (i == (pilha.getDadosSize() - 1)) {
                            pilha.getTopoPilha();
                        } else {
                            pilha.setIndexPilha(arg1i + i, pilha.getTopoPilha());
                        }
                    }
                    memoria.setI(memoria.getI() + 1);
                    printaPilha(pilha, funcao_atual.getFuncao());
                    break;
                case "CALL":

                    break;
                case "RETURN":

                    break;
            }
        }

    }

    public static void printaPilha(Pilha pilha, String comando) {
        System.out.println("----------");
        System.out.println("COMANDO: " + comando);
        System.out.println("PILHA:");
        for (int i = pilha.getDadosSize() - 1; i >= 0; i--) {
            System.out.println("[" + i + "]" + " " + pilha.getIndexPilha(i));
        }
        System.out.println("----------");
    }

}
