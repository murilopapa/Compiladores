package compilador;

public class ElementoOperando extends Elemento {

    private String tipo;
    private int memoria;

    public ElementoOperando(String nome, String tipo, int memoria) {
        super(nome);
        this.memoria = memoria;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getMemoria() {
        return memoria;
    }

    public void setMemoria(int memoria) {
        this.memoria = memoria;
    }

}
