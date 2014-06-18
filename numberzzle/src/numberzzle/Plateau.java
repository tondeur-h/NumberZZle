package numberzzle;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Plateau extends JPanel implements MouseMotionListener, MouseListener, Runnable {
    
    ArrayList grille=new ArrayList();
    ArrayList score=new ArrayList(10);
    
    boolean jouer=false;
    Thread t;
    
    int temps=120;
    int tempSum=0;
    int dx=50;
    int dy=100;
    int vrecherche=10;
    int maxscore=10;
    boolean good=false;
    Image imgFond,imgScore;
    Numberzzle nbz;
     
    Plateau(Numberzzle nbze){
        nbz=nbze;
        addMouseMotionListener(this);
        addMouseListener(this);
        lire_score();
         try {
        imgFond = ImageIO.read(getClass().getResource("/numberzzle/img/numberzzle.jpg"));
        imgScore = ImageIO.read(getClass().getResource("/numberzzle/img/numberscore.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(Numberzzle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void lire_score(){
        for (int i=0;i<10;i++){
            score.add(i, new Score(0, "_vide_"));
        }
        
        try{
            String line="";
            BufferedReader br=new BufferedReader(new FileReader("score.nzz"));
            int i=0;
            while (br.ready()){
            line=br.readLine();
            Scanner sc=new Scanner(line);
            sc.useDelimiter("#");
            score.add(i,new Score(sc.nextInt(), sc.next()));
            i++;
            sc.close();
            }
            br.close();
        } catch (IOException ioe){}
    }
    
    public void save_score(){
         try{
         PrintWriter pw=new PrintWriter("score.nzz");
        for (int i=0;i<10;i++){
       pw.println(((Score)score.get(i)).getVal()+"#"+((Score)score.get(i)).getNom());
        }
        pw.close();
         } catch (IOException ioe){}
        }
    
    
    public void nouveau(){
        //vider grille
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                grille.add((i*4)+j, new Grille(0,false));
            }
        }
        
        //remplir la grille
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                grille.add((i*4)+j, new Grille((int)((Math.random()*9)+1),false));
            }
        }
        
        jouer=true;
        vrecherche=10;
        tempSum=0;
        good=false;
        nbz.menuOff();
        //demarrer horloge...
        temps=120;
        t=new Thread(this);
                t.start();
  
        repaint();
    }
    
    public String completeZero(int v){
    String r=v+"";
    if (r.length()<2) r="0"+r;
    return r;
    }
    
    public String repTime(int v){
    String r=""+v;
    if (r.length()==2) r="0"+r;
    if (r.length()==1) r="00"+r;
    return r;
    }
    
    @Override
    public void paintComponent(Graphics g){
        //effacer l'écran
        g.drawImage(imgScore, 0, 0, this);
        //lignes de séparations
        g.setColor(Color.white);
        g.drawLine(0, 50, this.getWidth(), 50);
        g.drawLine(200, 0, 200, 50);
        g.drawLine(0, 100, this.getWidth(), 100);
        
        //afficher les scores
        //titre
        g.setFont(new Font("Times New Roman", Font.BOLD, 40));
        g.setColor(Color.black);
        g.drawString("-NumberZZle-",105,88);
        g.drawString("Scoreboard", 220, 40);
        g.setColor(Color.white);
        g.drawString("-NumberZZle-",107,91);
        g.drawString("Scoreboard", 222, 42);
        //dernier score
        g.setColor(Color.black);
        g.drawString(maxscore+"",40,40);
        g.setColor(Color.white);
        g.drawString(maxscore+"", 42, 42);
        
         g.setFont(new Font("Times New Roman", Font.BOLD+Font.ITALIC, 28));
        g.setColor(Color.white);
        g.drawString("Pl.  Nom                 Points",60, 150);
        g.setColor(Color.white);
        g.drawLine(60, 160,450,160);
        for (int i=0;i<10;i++){
        g.setFont(new Font("Times New Roman", Font.BOLD, 28));
        g.setColor(Color.ORANGE);
        g.drawString(completeZero((i+1))+".  "+((Score)score.get(i)).getNom(),60, 200+(i*25));
        g.drawString(""+((Score)score.get(i)).getVal(),400, 200+(i*25));
        }
              
        
        
        //dessiner la grille
        if (jouer){
        //effacer l'écran
        g.drawImage(imgFond, 0, 0, this);
        //lignes de séparations
        g.setColor(Color.white);
        g.drawLine(0, 50, this.getWidth(), 50);
        g.drawLine(200, 0, 200, 50);
        g.drawLine(0, 100, this.getWidth(), 100);
        
      
        
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
       
                //timer
                g.setFont(new Font("Times New Roman", Font.BOLD, 34));
                g.setColor(Color.white);
                g.drawString(repTime(temps)+" sec.",280, 40);
                //valeur recherché
                g.setColor(Color.red);
                g.drawString("Valeur recherchée ["+vrecherche+"]",35,88);
                //somme de la sélection
                g.setColor(Color.yellow);
                g.drawString(tempSum+"",40,40);
                //grille de jeu
                g.setColor(Color.darkGray);
                g.fillRoundRect(dx+12+(i*100), dy+12+(j*100), 80, 80, 10, 10);
                //choix de la couleur
                if (((Grille)grille.get((i*4)+j)).getSelect()==true) g.setColor(Color.red); else g.setColor(Color.gray);
                g.fillRoundRect(dx+10+(i*100), dy+10+(j*100), 80, 80, 10, 10);
                g.setFont(new Font("Times New Roman", Font.BOLD, 60));
                g.setColor(Color.white);
                g.drawString(""+((Grille)grille.get((i*4)+j)).getVal(),dx+35+(i*100), dy+70+(j*100));
            }
        }
        
          if (good){
        g.setFont(new Font("Courrier", Font.BOLD+Font.ITALIC, 72));
        g.setColor(Color.darkGray);
        g.drawString("OK!",174, 324);
        g.setColor(Color.green);
        g.drawString("OK!",170, 320);
        }
        
        }//fin if
        
    }

    public int x2cx(int xc){
    return (int)((xc-(dx+10))/100);
    }
    
    public int y2cy(int yc){
    return (int)((yc-(dy+10))/100);
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {       
            if (e.getModifiers()==16){
            ((Grille)grille.get((x2cx(e.getX())*4)+y2cy(e.getY()))).setSelect(true);    
            CalcSum();
            good=false;
            repaint();
            }
    }

    
    public void CalcSum() {
        tempSum=0;
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
        //faire la somme
        if (((Grille)grille.get((i*4)+j)).getSelect()==true){
            tempSum=tempSum+((Grille)grille.get((i*4)+j)).getVal();
        }
            }
        }
    }
    
    
    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void run() {
        boolean continu=true;
        while (t.isAlive() && continu){
            try {
                Thread.sleep(1000);
                temps--;
                if (temps==0) {
                    jouer=false;
                    continu=false;
                    nbz.menuOn();
                    maxScore();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Plateau.class.getName()).log(Level.SEVERE, null, ex);
            }
            repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    
    @Override
    public void mouseReleased(MouseEvent e) {
    verif_jeu();
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
    public void verif_jeu(){
        
        int somme=0;
        
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
        //faire la somme
        if (((Grille)grille.get((i*4)+j)).getSelect()==true){
            somme=somme+((Grille)grille.get((i*4)+j)).getVal();
        }
                ((Grille)grille.get((i*4)+j)).setSelect(false);
            }
        }
        
        if (somme==vrecherche) {vrecherche++;tempSum=0;good=true;}
        
        repaint();
    }
    
    
    public void maxScore(){
        //garder le meilleur score pour affichage
        if (maxscore<vrecherche-1) maxscore=vrecherche-1;
        int max=0;
        //placer le score max a la bonne place
        for (int i=0;i<10;i++){
            max=((Score)score.get(i)).getVal();
            if ((vrecherche-1)>=max) {
                String nom=JOptionPane.showInputDialog(null,"Votre nom ?","Nouveau Record...",JOptionPane.QUESTION_MESSAGE);
                
                score.add(i, new Score(vrecherche-1, nom.toUpperCase()));
            break;
            }
        }
    }

}
