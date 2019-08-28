/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maquina.virtual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author matheus
 */
public class FrameTabela {
    private FrameTabela frameTabela = this;
    private File arquivo_obj;
    Gerenciador INSTANCE = Gerenciador.getInstance();
    
    public FrameTabela(File recebe_arquivo_obj) {
        arquivo_obj = recebe_arquivo_obj;
        
        if (INSTANCE.addExecutavel(arquivo_obj) == false) {
            JOptionPane.showMessageDialog(null, "Erro ao abrir arquivo");
        }
    }
    
}
