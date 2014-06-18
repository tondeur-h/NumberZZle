package numberzzle;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author tondeur-h
 */
public class Numberzzle extends JFrame implements ActionListener, WindowListener {

    Plateau plateau;
    JMenuItem jouer;
    Image imgIco;
    
    public Numberzzle() {
    //creer la fenêtre
        setResizable(false);
        setTitle("numberzzle");
         try {
        imgIco = ImageIO.read(getClass().getResource("/numberzzle/img/numberzzle.png"));
        } catch (IOException ex) {
            Logger.getLogger(Numberzzle.class.getName()).log(Level.SEVERE, null, ex);
        }
        setIconImage(imgIco);
        setBounds(getToolkit().getScreenSize().width/2-250,getToolkit().getScreenSize().height/2-250, 500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(monMenu());
        addWindowListener(this);
        plateau=new Plateau(this);
        getContentPane().add(plateau);
        setVisible(true);
    }

    
    public JMenuBar monMenu() {
    JMenuBar menubar=new JMenuBar();
    JMenu fichier=new JMenu("Jeu");
    fichier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/numberzzle/img/numberzzle16.png")));
    JMenu aide=new JMenu("Aide");
    aide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/numberzzle/img/help16.png")));
    
    jouer=new JMenuItem("Jouer");
    jouer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/numberzzle/img/numberzzle16.png")));
    JMenuItem quitter=new JMenuItem("Quitter");
    quitter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/numberzzle/img/quit16.png")));
    fichier.add(jouer);
    fichier.addSeparator();
    fichier.add(quitter);
    
    jouer.addActionListener(this);
    jouer.setActionCommand("JOUER");
    
    quitter.addActionListener(this);
    quitter.setActionCommand("QUITTER");
    
    JMenuItem about=new JMenuItem("A propos de");
    about.addActionListener(this);
    about.setActionCommand("APROPOS");
    about.setIcon(new javax.swing.ImageIcon(getClass().getResource("/numberzzle/img/about16.png")));
    
    JMenuItem licence=new JMenuItem("Licence");
    licence.addActionListener(this);
    licence.setActionCommand("LICENCE");
    licence.setIcon(new javax.swing.ImageIcon(getClass().getResource("/numberzzle/img/about16.png")));
    
    
    aide.add(licence);
    aide.add(about);
    
    menubar.add(fichier);
    menubar.add(aide);
    
    return menubar;
    }
    
    public void menuOff(){
    jouer.setEnabled(false);
    }
    
    public void menuOn(){
    jouer.setEnabled(true);
    }
    
    
    public static void main(String[] args) {
      new Numberzzle();
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().compareToIgnoreCase("QUITTER")==0){
            plateau.save_score();
            System.exit(0);
    }
        
        if (e.getActionCommand().compareToIgnoreCase("JOUER")==0){
            plateau.nouveau();
        }
    
        if (e.getActionCommand().compareToIgnoreCase("LICENCE")==0){
            new AboutBox();
        }
        
        if (e.getActionCommand().compareToIgnoreCase("APROPOS")==0){
        JOptionPane.showMessageDialog(this,"NumberZZLE Version 1.0\n Tondeur H.\nlicence GPL v3", "numberzzle v.1", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
    plateau.save_score();
    }

    @Override
    public void windowClosed(WindowEvent e) {}
    @Override
    public void windowIconified(WindowEvent e) {
    }
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e) {}
}
