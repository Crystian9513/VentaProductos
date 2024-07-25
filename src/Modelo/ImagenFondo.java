/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Peralta
 */
public class ImagenFondo extends JPanel{
    
      private Image imagen;
        
      @Override
        public void paint (Graphics g){
            imagen = new ImageIcon(getClass().getResource("../Imagen/fondoo.png")).getImage();
            g.drawImage(imagen, 0, 0,getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
    
        
}
