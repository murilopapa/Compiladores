package maquina.virtual;

import java.util.Scanner;

public class MaquinaVirtual {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Memoria memoria = new Memoria();
        Pilha pilha = new Pilha();
        //carrega o programa na Memoria (instruções)
        //intrucoes.start()
        memoria.addFuncao("START", 0, 0);
        memoria.addFuncao("LDC", 2, 0);
        memoria.addFuncao("LDC", 5, 0);
        memoria.addFuncao("LDC", 6, 0);
        memoria.addFuncao("LDC", 20, 0);
        memoria.addFuncao("LDC", 7, 0);
        memoria.addFuncao("LDC", 9, 0);
        memoria.addFuncao("LDC", 1, 0);
        memoria.addFuncao("LDC", 0, 0);
        memoria.addFuncao("LDC", 45, 0);
        memoria.addFuncao("LDC", 87, 0);
        memoria.addFuncao("LDC", 99, 0);
        memoria.addFuncao("LDV", 0, 0);
        memoria.addFuncao("ADD", 0, 0);
        memoria.addFuncao("HLT", 0, 0);

        boolean execute = true;

        while (execute) {
            int aux, arg1, arg2;
            Funcoes funcao_atual = memoria.getFuncByIndex(memoria.getI());
            switch (funcao_atual.getFuncao()) {
                case "LDC":     //coloca uma constante no topo da pilha
                    pilha.setTopoPilha(funcao_atual.getArg1());
                    memoria.setI(memoria.getI() + 1);
                    break;

                case "LDV":     //pega um valor ja existente na pilha, mantem ele e copia pro topo
                    aux = pilha.getIndexPilha(funcao_atual.getArg1());
                    pilha.setTopoPilha(aux);
                    memoria.setI(memoria.getI() + 1);
                    break;

                case "ADD":     //soma o antepenultimo com o ultimo elemento da pilha, remove os dois e salva o novo
                    arg1 = pilha.getTopoPilha();
                    arg2 = pilha.getTopoPilha();
                    aux = arg2 + arg1;
                    System.out.println("ADD: " + aux);
                    pilha.setTopoPilha(aux);
                    memoria.setI(memoria.getI() + 1);
                    break;

                case "SUB":     //subtrai o antepenultimo com o ultimo elemento da pilha, remove os dois e salva o novo
                    arg1 = pilha.getTopoPilha();
                    arg2 = pilha.getTopoPilha();
                    aux = arg2 - arg1;
                    System.out.println("SUB: " + aux);
                    pilha.setTopoPilha(aux);
                    memoria.setI(memoria.getI() + 1);
                    break;

                case "MULT":    //multiplica o antepenultimo com o ultimo elemento da pilha, remove os dois e salva o novo
                    arg1 = pilha.getTopoPilha();
                    arg2 = pilha.getTopoPilha();
                    aux = arg2 * arg1;
                    System.out.println("MULT: " + aux);
                    pilha.setTopoPilha(aux);
                    memoria.setI(memoria.getI() + 1);
                    break;

                case "DIVI":    //divide o antepenultimo com o ultimo elemento da pilha, remove os dois e salva o novo
                    arg1 = pilha.getTopoPilha();
                    arg2 = pilha.getTopoPilha();
                    aux = arg2 / arg1;
                    System.out.println("DIVI: " + aux);
                    pilha.setTopoPilha(aux);
                    memoria.setI(memoria.getI() + 1);
                    break;

                case "INV":     //inverte o sinal do topo
                    arg1 = pilha.getTopoPilha();
                    aux = arg1 * -1;
                    pilha.setTopoPilha(aux);
                    memoria.setI(memoria.getI() + 1);
                    break;

                case "AND":     //penultimo AND ultimo, remove os dois e coloca 0 se for 0 e 1 se for 1 (é apenas pra binario)
                    arg1 = pilha.getTopoPilha();
                    arg2 = pilha.getTopoPilha();
                    if (arg1 == 1 && arg2 == 1) {
                        aux = 1;
                    } else {
                        aux = 0;
                    }
                    System.out.println("AND: " + aux);
                    pilha.setTopoPilha(aux);
                    memoria.setI(memoria.getI() + 1);
                    break;

                case "OR":      //penultimo OR ultimo, remove os dois e coloca 0 se for 0 e 1 se for 1 (é apenas pra binario)
                    arg1 = pilha.getTopoPilha();
                    arg2 = pilha.getTopoPilha();
                    if (arg1 == 0 && arg2 == 0) {
                        aux = 0;
                    } else {
                        aux = 1;
                    }
                    System.out.println("OR: " + aux);
                    pilha.setTopoPilha(aux);
                    memoria.setI(memoria.getI() + 1);
                    break;

                case "NEG":     //negacao, 1-topo, remove e coloca de novo
                    arg1 = pilha.getTopoPilha();
                    aux = 1 - arg1;
                    pilha.setTopoPilha(aux);
                    memoria.setI(memoria.getI() + 1);
                    break;

                case "CME":     // se antepenultimo menor que topo, remove os dois e coloca 1 no topo
                    arg1 = pilha.getTopoPilha();
                    arg2 = pilha.getTopoPilha();
                    if (arg2 < arg1) {
                        aux = 1;
                    } else {
                        aux = 0;
                    }
                    pilha.setTopoPilha(aux);
                    memoria.setI(memoria.getI() + 1);
                    break;

                case "CMA":     // se antepenultimo maior que topo, remove os dois e coloca 1 no topo
                    arg1 = pilha.getTopoPilha();
                    arg2 = pilha.getTopoPilha();
                    if (arg2 > arg1) {
                        aux = 1;
                    } else {
                        aux = 0;
                    }
                    pilha.setTopoPilha(aux);
                    memoria.setI(memoria.getI() + 1);
                    break;

                case "CEQ":     // se antepenultimo igual ao topo, remove os dois e coloca 1 no topo
                    arg1 = pilha.getTopoPilha();
                    arg2 = pilha.getTopoPilha();
                    if (arg2 == arg1) {
                        aux = 1;
                    } else {
                        aux = 0;
                    }
                    pilha.setTopoPilha(aux);
                    memoria.setI(memoria.getI() + 1);
                    break;

                case "CDIF":    // se antepenultimo diferente que topo, remove os dois e coloca 1 no topo
                    arg1 = pilha.getTopoPilha();
                    arg2 = pilha.getTopoPilha();
                    if (arg2 != arg1) {
                        aux = 1;
                    } else {
                        aux = 0;
                    }
                    pilha.setTopoPilha(aux);
                    memoria.setI(memoria.getI() + 1);
                    break;

                case "CMEQ":    // se antepenultimo menor ou igual ao topo, remove os dois e coloca 1 no topo
                    arg1 = pilha.getTopoPilha();
                    arg2 = pilha.getTopoPilha();
                    if (arg2 <= arg1) {
                        aux = 1;
                    } else {
                        aux = 0;
                    }
                    pilha.setTopoPilha(aux);
                    memoria.setI(memoria.getI() + 1);
                    break;

                case "CMAQ":     // se antepenultimo maior ou igual ao topo, remove os dois e coloca 1 no topo
                    arg1 = pilha.getTopoPilha();
                    arg2 = pilha.getTopoPilha();
                    if (arg2 >= arg1) {
                        aux = 1;
                    } else {
                        aux = 0;
                    }
                    pilha.setTopoPilha(aux);
                    memoria.setI(memoria.getI() + 1);
                    break;

                case "START":       //inicia o codigo
                    System.out.println("Iniciado!");
                    memoria.setI(memoria.getI() + 1);
                    break;

                case "HLT":         //termina o codigo
                    System.out.println("Finalizado!");
                    execute = false;
                    printaPilha(pilha);     //funcao extra para printar a pilha
                    break;

                case "STR":         //pega o valor do topo, remove, e salva em uma posiçao ja existente
                    arg1 = pilha.getTopoPilha();
                    pilha.setIndexPilha(funcao_atual.getArg1(), arg1);
                    memoria.setI(memoria.getI() + 1);
                    break;
                case "JMP":         //pula pra linha X

                    break;
                case "JMPF":        //pula pra linha X se o topo for 0 (ou seja, alguma comparaçao deu falso)

                    break;
                case "NULL":        //serve so para colocar a LABEL da linha

                    break;
                case "RD":          //le um inteiro do terminal
                    arg1 = scanner.nextInt();
                    pilha.setTopoPilha(arg1);
                    memoria.setI(memoria.getI() + 1);
                    break;
                case "PRN":         //printa o valor do topo e remove ele
                    arg1 = pilha.getTopoPilha();
                    System.out.println("SAIDA: " + arg1);
                    break;
                case "ALLOC":

                    break;
                case "DALLOC":

                    break;
                case "CALL":

                    break;
                case "RETURN":

                    break;
            }
        }

    }

    public static void printaPilha(Pilha pilha) {
        System.out.println("----------");
        System.out.println("PILHA:");
        for (int i = pilha.getDadosSize()-1; i >= 0; i--) {
            System.out.println("[" + i + "]" + " " + pilha.getIndexPilha(i));
        }
        System.out.println("----------");
    }

}
