package maquina.virtual;

public class MaquinaVirtual {

    public static void main(String[] args) {
        Memoria memoria = new Memoria();
        Pilha pilha = new Pilha();
        //carrega o programa na Memoria (instruções)
        //intrucoes.start()
        memoria.addFuncao("START", 0, 0);
        memoria.addFuncao("LDV", 2, 0);
        memoria.addFuncao("LDV", 5, 0);
        memoria.addFuncao("LDV", 7, 0);
        memoria.addFuncao("ADD", 0, 0);
        memoria.addFuncao("END", 0, 0);
        
        boolean execute = true;

        while (execute) {
            int index = memoria.getI();
            Funcoes funcao_atual = memoria.getFuncByIndex(index);
            switch (funcao_atual.getFuncao()) {
                case "START":
                    System.out.println("Iniciado!");
                    memoria.setI(index+1);
                    break;
                case "LDV":
                    pilha.setTopoPilha(funcao_atual.getArg1());
                    memoria.setI(index+1);
                    break;
                case "ADD":
                    int soma = pilha.getTopoPilha() + pilha.getTopoPilha();
                    System.out.println(soma);
                    memoria.setI(index+1);
                    break;
                case "END":
                    System.out.println("Finalizado!");
                    execute = false;
                    break;
            }
        }

    }

}
