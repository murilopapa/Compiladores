package compilador;

public class ElementoOperando extends ElementoOperador{
    private String tipo;

    public ElementoOperando(String nome, String tipo) {
        super(nome);
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
}
