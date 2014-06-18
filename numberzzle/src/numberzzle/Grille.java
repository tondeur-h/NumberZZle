package numberzzle;

class Grille {

    int valeur;
    boolean select;

    Grille(int v,boolean b){
        setVal(v);
        setSelect(b);
    }
    
    public int getVal(){return valeur;}
    public void setVal(int v){valeur=v;}
    public boolean getSelect(){return select;}
    public void setSelect(boolean b){select=b;}
}

class Score {
    int valeur;
    String nom;

    Score(int v,String n){
        setVal(v);
        setNom(n);
    }
    
    public void setVal(int v){valeur=v;}
    public void setNom(String n){nom=n;}
    public int getVal(){return valeur;}
    public String getNom(){return nom;}
}
