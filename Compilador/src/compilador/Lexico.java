package compilador;

import java.io.BufferedReader;
import java.io.IOException;

public class Lexico {

    Lexico(BufferedReader reader) throws IOException {
        char caractere;
        int caractere_int;
        int linha_atual = 1;
        while ((caractere_int = reader.read()) != -1) {
            caractere = (char) caractere_int;
            if (caractere == '{') {
                while (((caractere_int = reader.read()) != -1) || ((caractere = (char) caractere_int) == '}')) {
                    //em teoria, esse loop fica lendo comentario
                }
            } else if (caractere == ' ') {

            } else if (caractere == '\n') {
            }

        }
    }

}
