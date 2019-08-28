/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maquina.virtual;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author matheus
 */
public class LinhaComando implements Serializable {
    private String comando;
    
    public LinhaComando(String recebe_linha_comando)
    {
        comando = recebe_linha_comando;
    }
}
