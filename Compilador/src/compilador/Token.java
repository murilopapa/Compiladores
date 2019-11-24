package compilador;

public class Token {

    private String simbolo;
    private String lexema;
    private int linha;
    private int indexStart, indexEnd;

    Token(){
        
    }
    Token(Token token){
        simbolo= token.getSimbolo();
        lexema= token.getLexema();
        linha= token.getLinha();
        indexStart= token.getIndexStart();
        indexEnd= token.getIndexEnd();
        
    }
    
    public String getSimbolo() {
        return simbolo;
    }

    public int getIndexStart() {
        return indexStart;
    }

    public void setIndexStart(int indexStart) {
        this.indexStart = indexStart;
    }

    public int getIndexEnd() {
        return indexEnd;
    }

    public void setIndexEnd(int indexEnd) {
        this.indexEnd = indexEnd;
    }
    
    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }
    

}
