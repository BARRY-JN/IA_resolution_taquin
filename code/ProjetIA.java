import java.util.ArrayList;

public class ProjetIA {


	public static void main(String[] args) {
		Etat etat=null;
		String tmp;

		boolean mode_verbeux=true;


		Euristique.appliquer_euristique_numero(1);
		Ouvert ouvert = null;

		if (args.length==0||args[0].equals("-h")){
			System.out.print("\n");
			System.out.println("Syntaxe : ");
			System.out.println("\tprojetIA \"chemin du fichier .grid\" -option_parcours -autres_options\n");
			System.out.println("Options de parcours : ");
			System.out.println("\t-l parcours en largeur");
			System.out.println("\t-p parcours en profondeur");
			System.out.println("\t-m parcours du meilleurs d'abord\n");
			System.out.println("Autres options: ");
			System.out.println("\t-muet N'afficher que le temps de calcul");
			System.out.println("\t-h=n appliquer l'heuristique numero n\n");
			System.exit(0);
		}

		System.out.print("\n");
		for(int i=0;i<args.length;i++) {

			if (args[i].equals("-l"))
				ouvert = new Ouverture_Largeur();
			if (args[i].equals("-p"))
				ouvert = new Ouverture_Profondeur();
			if (args[i].equals("-m"))
				ouvert = new Ouverture_Meilleurs();
			if (args[i].equals("-muet"))
				mode_verbeux=false;
			if (args[i].contains("-h=")){
				tmp=args[i].replace("-h=","");
				Euristique.appliquer_euristique_numero(Integer.parseInt(tmp));
			}


		}

		if(ouvert==null)
			ouvert = new Ouverture_Largeur();

		InteractionProb.lire(args[0]);

        if(mode_verbeux) {
			System.out.println("  ___/ Grille lue : \\___\n");
			System.out.println("___ Etat initial ___\n");
			InteractionProb.afficher_etats(Initial.e_initial, false);
			System.out.println("\n___ Etat final ___\n");
			InteractionProb.afficher_etats(Final.e_final, false);
			System.out.print("Résolution en cours... \r");
			System.out.flush();
		}

        /* ---------------------------------------------------------------------------------------------------
        ------------------------- A L G O R I T H M E---------------------------------------------------------
        ------------------------------------------------------------------------------------------------------
        */

		//Ouvert ouvert = new Ouverture_Largeur();
		ouvert.InsererEtat(Initial.e_initial);

		//Fermé =  ensemble vide

		//Déclenchement chronomètre
		long start = System.nanoTime();

		//Tant que ouvert est vide et que tete(ouvert) n'est pas un but
		while(!ouvert.EstVide()&&!ouvert.TeteEstUnBut()){

			//On met l'état à la tete de ouvert dans la variable "etat" et on supprime cet état de ouvert
			etat=ouvert.ObtenirTete();
			//On met l'état "etat" dans fermé
			Ferme.Ajouter(etat);

			//On génère tout les voisins possibles de "etat" et on les met dans ouvert si ils ne sont ni dans ouvert, ni dans fermé
			for(Etat etat_genere:etat.generer_sucesseurs()) {
				if (!ouvert.Etat_existe(etat_genere)&&!Ferme.Dans_Ferme(etat_genere)) {
					ouvert.InsererEtat(etat_genere);
					etat.Ajouter_sucesseur(etat_genere);
				}
			}

		}

		long end = System.nanoTime();

		//Si ouvert est vide alors il n'existe pas de but accessibles
		if(ouvert.EstVide()){
			System.out.println("Il n'existe pas de but accessible partant de cette configuration vers cette solution");
		}else{
			//sinon l'élément tête(ouvert) est un but
			if(mode_verbeux) {
				System.out.println("\n  ___/ Résolution : \\___\n");
				ouvert.ObtenirTete().afficher_chemin();

			}else{
				System.out.println("Une solution a été trouvé");
			}

		}
		Double tmp_ms=(end - start) / 1000000d;
		int secondes = (int) (tmp_ms / 1000) % 60 ;
		int minutes = (int) ((tmp_ms / (1000*60)) % 60);
		int heures   = (int) ((tmp_ms / (1000*60*60)) % 24);
		System.out.println("Temps d'execution de l'algo : "+heures+"h "+minutes+"m "+secondes+"s "+tmp_ms+"ms\n");
	}
}
