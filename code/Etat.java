import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Etat implements Comparable<Etat> {
     private static int m;
     private static int n;
     String[] configuration;
     private Point coord_case_vide;
     private Etat precedent;

	public enum direction {
		HAUT, BAS, DROITE, GAUCHE
	}

    @Override
    public int compareTo(Etat etat) {
        return Integer.compare(Euristique.obtenir_heuristique(this),Euristique.obtenir_heuristique(etat));
    }

    @Override
    public boolean equals(Object etat){
        Etat temp=(Etat) etat;
        return Arrays.equals(this.configuration,temp.configuration);
    }

    int getM(){
        return m;
    }

    int getN(){
        return n;
    }

    Point getCoord_case_vide(){
        return coord_case_vide;
    }

    static void Definir_taille(int largeur, int hauteur){
        m=largeur;
        n=hauteur;
    }

    public Etat(String[] configuration){
        this(configuration,null);
        int y,x;
        for(y=0;y<configuration.length;y++) {
            for (x = 0; x < configuration[y].length(); x++) {
                if (configuration[y].charAt(x) == ' ')
                    coord_case_vide = new Point(x, y);
            }
        }


    }

     public Etat(String[] configuration, Point coord){
         this.configuration=configuration;
         this.coord_case_vide=coord;
     }

    public Etat(String[] configuration, Object coord){
        this(configuration,(Point) coord);
    }

     boolean Est_final(){
        return Final.Est_Final(this);
     }

     void Ajouter_sucesseur(Etat etat){
	    if(etat.precedent==null)
            etat.precedent=this;
     }

     //générer état en fonction des règles de production

    ArrayList<Etat> generer_sucesseurs(){
        Etat droite,gauche, haut, bas;
        ArrayList<Etat> etats=new ArrayList<>();

        //DEPLACEMENTS (DE LA CASE VIDE)
        //DROITE
        if(coord_case_vide.x+1<m){
            droite=new Etat(configuration.clone(),coord_case_vide.clone());
            deplacer_bloc(direction.DROITE,droite);
            etats.add(droite);
        }

        //GAUCHE
        if(coord_case_vide.x-1>=0){
            gauche=new Etat(configuration.clone(),coord_case_vide.clone());
            deplacer_bloc(direction.GAUCHE,gauche);
            etats.add(gauche);
        }


        //BAS
        if(coord_case_vide.y+1<n){
            bas=new Etat(configuration.clone(),coord_case_vide.clone());
            deplacer_bloc(direction.BAS,bas);
            etats.add(bas);
        }

        //HAUT
        if(coord_case_vide.y-1>=0){
            haut=new Etat(configuration.clone(),coord_case_vide.clone());
            deplacer_bloc(direction.HAUT,haut);
            etats.add(haut);
        }
        return etats;
    }

    private void deplacer_bloc(direction direction, Etat etat){
        char precedent;
        StringBuilder precedent2;
        StringBuilder suivant;

        switch(direction){
            case HAUT:
                //On recopie les 2 lignes qui vont subir des modification dans des String builders
                precedent2=new StringBuilder(etat.configuration[etat.coord_case_vide.y-1]);
                suivant = new StringBuilder(etat.configuration[etat.coord_case_vide.y]);

                //On effectue les modifications (la ligne du haut recoit l'espace, celle du bas le caractere
                //qui a bouge
                precedent=precedent2.charAt(etat.coord_case_vide.x);
                precedent2.setCharAt(etat.coord_case_vide.x,' ');
                suivant.setCharAt(etat.coord_case_vide.x,precedent);

                //On remplace les lignes modifiés dans la configuration de l'état
                etat.configuration[etat.coord_case_vide.y-1]=precedent2.toString();
                etat.configuration[etat.coord_case_vide.y]=suivant.toString();

                etat.coord_case_vide.setLocation(etat.coord_case_vide.x,etat.coord_case_vide.y-1);
                break;

            case BAS:
                precedent2 = new StringBuilder(etat.configuration[etat.coord_case_vide.y+1]);
                suivant=new StringBuilder(etat.configuration[etat.coord_case_vide.y]);

                precedent=precedent2.charAt(etat.coord_case_vide.x);
                precedent2.setCharAt(etat.coord_case_vide.x,' ');
                suivant.setCharAt(etat.coord_case_vide.x,precedent);

                etat.configuration[etat.coord_case_vide.y+1]=precedent2.toString();
                etat.configuration[etat.coord_case_vide.y]=suivant.toString();
                etat.coord_case_vide.setLocation(etat.coord_case_vide.x,etat.coord_case_vide.y+1);
                break;

            case DROITE:
                suivant=new StringBuilder(etat.configuration[etat.coord_case_vide.y]);
                precedent=suivant.charAt(etat.coord_case_vide.x+1);

                suivant.setCharAt(etat.coord_case_vide.x,precedent);
                suivant.setCharAt(etat.coord_case_vide.x+1,' ');

                etat.configuration[etat.coord_case_vide.y]=suivant.toString();
                etat.coord_case_vide.setLocation(etat.coord_case_vide.x+1,etat.coord_case_vide.y);
                break;

            case GAUCHE:

                suivant=new StringBuilder(etat.configuration[etat.coord_case_vide.y]);
                precedent=suivant.charAt(etat.coord_case_vide.x-1);

                suivant.setCharAt(etat.coord_case_vide.x,precedent);
                suivant.setCharAt(etat.coord_case_vide.x-1,' ');

                etat.configuration[etat.coord_case_vide.y]=suivant.toString();
                etat.coord_case_vide.setLocation(etat.coord_case_vide.x-1,etat.coord_case_vide.y);
                break;

            default:
                break;
        }
    }

    void afficher_chemin(){
        Etat etat_actuel;
        Stack<Etat> pile = new Stack<>();
        int compteur=1;

        etat_actuel=this;

        while(etat_actuel!=null){
            pile.push(etat_actuel);
            etat_actuel=etat_actuel.precedent;
        }

        while(!pile.empty()){
            InteractionProb.afficher_etats(pile.pop(),true);
            if(pile.size()>0)
                System.out.println("\t    \\/ ("+compteur+")");
            compteur++;
        }
    }

}
