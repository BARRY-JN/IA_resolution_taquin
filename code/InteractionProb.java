

import java.io.*;


public class InteractionProb {
    public static void lire(String cheminFichier) {
        int nbr_ligne=0,i=0,j=0;
        String prb[],sol[];

        try {

            File f = new File(cheminFichier);
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine;

            //on lit le nbr de lignes du prb
            nbr_ligne = Integer.parseInt(b.readLine());
            prb = new String[nbr_ligne];
            sol = new String[nbr_ligne];

            while ((readLine = b.readLine()) != null) {
                if(i<nbr_ligne) {
                    //on lit l'état initial
                    prb[i] = readLine;
                }else {
                    //on lit l'état final
                    sol[j] = readLine;
                    j++;
                }
                i++;
            }
            //on crée nos 2 état: l'état initial et l'état final

            Etat.Definir_taille(prb[0].length(),nbr_ligne);
            Initial.Definir_initial(new Etat(prb));
            Final.Definir_final(new Etat(sol));

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }

    public static void afficher_grille(Etat etat){
        int i,j;
        System.out.print("\t┌");
        for(j=0;j<etat.configuration[0].length()-1;j++) {
            System.out.print("───┬");
        }
        System.out.print("───┐");
        for(i=0;i<etat.configuration.length;i++) {

            System.out.print("\n\t│");
            for(j=0;j<etat.configuration[i].length();j++) {
                System.out.print(" "+etat.configuration[i].charAt(j)+" │");
            }
            if(i!=etat.configuration.length-1) {
                System.out.print("\n\t├");
                for (j = 0; j < etat.configuration[0].length()-1; j++) {
                    System.out.print("───┼");
                }
                System.out.print("───┤");
            }
        }
        System.out.print("\n\t└");
        for(j=0;j<etat.configuration[0].length()-1;j++) {
            System.out.print("───┴");
        }
        System.out.print("───┘");

    }

    public static void afficher_grille_basique(Etat etat){
        int i,j;
        for(i=0;i<etat.configuration.length;i++) {
            for(j=0;j<etat.configuration[i].length();j++) {
                System.out.print("\t"+etat.configuration[i].charAt(j));
                if(j!=etat.configuration[i].length()-1){
                    System.out.print(" - ");
                }
            }
            System.out.print("\n");
        }

    }

    public static void afficher_etats(Etat[] etats, boolean config_only){
        System.out.println("> m: " + etats[0].getM()+" n: " + etats[0].getN());
        for(Etat t:etats){
            afficher_etats(t, config_only);
        }
    }

    public static void afficher_etats(Etat etat, boolean config_only){
        if(etat!=null) {
            if (!config_only) {
                System.out.println("> configuration : ");
            }else{

            }
            InteractionProb.afficher_grille(etat);
            //InteractionProb.afficher_grille_basique(etat);
            if (!config_only) {
                System.out.println("\n> coord case vide : (x=" + etat.getCoord_case_vide().x+", y="+etat.getCoord_case_vide().y + ")\n");
            }
            System.out.println();
        }
    }
}
