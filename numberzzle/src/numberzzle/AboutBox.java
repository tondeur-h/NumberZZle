/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package numberzzle;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author tondeur-h
 */
public class AboutBox extends JDialog implements ActionListener {
    
    public final int largeur=450;
    public final int hauteur=350;
    JTextArea txtLic;
    JScrollPane jsp;
    Image imgIco;
    
    public AboutBox() {
    
    setBounds((getToolkit().getScreenSize().width/2)-(largeur/2), (getToolkit().getScreenSize().height/2)-(hauteur/2),largeur,hauteur);
    
    setLayout(new BorderLayout());
    
    setTitle("Licence...");
    
     try {
        imgIco = ImageIO.read(getClass().getResource("/numberzzle/img/numberzzle.png"));
        } catch (IOException ex) {
            Logger.getLogger(Numberzzle.class.getName()).log(Level.SEVERE, null, ex);
        }
        setIconImage(imgIco);
    
    txtLic=new JTextArea();
    txtLic.setText(lireRes());
    txtLic.setEditable(false);
    txtLic.setCaretPosition(0);
    
    jsp=new JScrollPane(txtLic);
    
   
    //bas
    JPanel bas=new JPanel();
    bas.setLayout(new GridBagLayout());
    
    JButton btn1=new JButton("Fermer");
    btn1.addActionListener(this);
    
    bas.add(btn1);
    
    
    add(jsp, BorderLayout.CENTER);
    add(bas,BorderLayout.SOUTH);
    
    setModal(true);
    setVisible(true);
    
    }

    
    public String lire(){
    String fichierStr="";
        BufferedReader bf;
        try {
            bf = new BufferedReader(new FileReader("gpl-3.0.txt"));
        try {
            while (bf.ready()){
            fichierStr=fichierStr+bf.readLine()+"\n";
            }
        } catch (IOException ex) {
            Logger.getLogger(AboutBox.class.getName()).log(Level.SEVERE, null, ex);
        }
        
          } catch (FileNotFoundException ex) {
            Logger.getLogger(AboutBox.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    return fichierStr;
    }
    
    public String lireRes(){
    String fichierStr="";
    byte [] b;
    BufferedInputStream bf = new BufferedInputStream(getClass().getResourceAsStream("/numberzzle/img/gpl-3.0.txt"));
            try {  
            b=new byte[bf.available()];
            bf.read(b,0, bf.available());
            fichierStr=new String(b);
        } catch (IOException ex) {
            Logger.getLogger(AboutBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    return fichierStr;
    }
    
    
    public void fermer(){
        this.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    fermer();
    }
            
    
}
