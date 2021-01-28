public class Euristique {
	private static int heuristique_actuelle;

	public static void appliquer_euristique_numero(int num){
		heuristique_actuelle=num;
	}

	public static int obtenir_heuristique(Etat etat){
		switch(heuristique_actuelle){
			case 1:
				return heuristique_un(etat);
			case 2:
				return heuristique_deux(etat);
			case 3:
				return heuristique_trois(etat);
			case 4:
				return heuristique_quatre(etat);
			default:
				return heuristique_un(etat);
		}
	}

	public static int heuristique_un(Etat etat){
		// PREMIERE HEURISTIQUE :
		// On va calculer le nombre de déplacement minimum pour chaque symbole pour être à la bonne position
		// et on va aditionner ce nombre de déplacement.
		int y, x, x2,y2, result=0;
		char actual_symbol;
		boolean continu=true;

		if(etat==null)
			return 0;

		for(y=0;y<Final.e_final.configuration.length;y++){
			for(x=0;x<Final.e_final.configuration[y].length();x++){
				actual_symbol=Final.e_final.configuration[y].charAt(x);

				if(actual_symbol!=' ') {

					for (y2=0;y2<etat.configuration.length&&continu;y2++) {
						for (x2=0;x2<etat.configuration[y2].length()&&continu;x2++) {
							if (etat.configuration[y2].charAt(x2) == actual_symbol) {
								result += Math.abs(x - x2) + Math.abs(y - y2);
								continu=false;
							}
						}
					}
					continu=true;
				}
			}
		}

		return result;
	}

	public static int heuristique_deux(Etat etat){
		//DEUXIEME HEURISTIQUE :
		//On lit une ligne de l'état actuel et on la compare symbole par symbole avec la même ligne de l'état final
		//Et on fait la somme du nombre de différences pour l'ensemble des lignes

		if(etat==null)
			return 0;

		int x,y,difference=0;
		for(y=0;y<etat.configuration.length;y++){
			for(x=0;x<etat.configuration[y].length();x++){
				if(etat.configuration[y].charAt(x)!=Final.e_final.configuration[y].charAt(x)){
					difference++;
				}
			}
		}
		return difference;
	}

	public static int heuristique_trois(Etat etat) {
		//TROISIEME HEURISTIQUE :
		//On prend un caractere de l'etat actuel et on verifie si il existe dans une zone de taille 2*2 par
		//rapport aux memes coordonnes dans l'etat final.
		//si c'est le cas on va calculer le nombre de déplacement minimal pour qu'il soit a la bonne place

		int x,y,x_min,x_max,y_min,y_max;
		char caractere_recherche;
		int difference=0,pas=1;
		for(y=0;y<etat.configuration.length;y++){
			for(x=0;x<etat.configuration[y].length();x++){
				caractere_recherche=etat.configuration[y].charAt(x);
				if(x<etat.configuration[y].length()-pas){
					x_min=x;
					x_max=x+pas;
				}else{
					x_min=x-pas;
					x_max=x;
				}
				if(y<etat.configuration.length-pas){
					y_min=y;
					y_max=y+pas;
				}else{
					y_min=y-pas;
					y_max=y;
				}

				if(Final.e_final.configuration[y_min].charAt(x_min)==caractere_recherche) {
					//le caractere est sur la diagonale donc il faut au minimum 4 déplacement (si l'une des cases du carré 2X2 est vide)
					if (Math.abs(x_min - x) == 1 && Math.abs(y_min - y) == 1)
						difference += 4;
					//si le caractere est en haut/bas/droite/gauche, il faut au minimum 1 déplacement (si l'une des cases du carré 2X2 est vide)
					else if(Math.abs(x_min - x) == 1 || Math.abs(y_min - y) == 1) {
						difference += 1;
					}
				}else if(Final.e_final.configuration[y_min].charAt(x_max)==caractere_recherche) {
					if (Math.abs(x_max - x) == 1 && Math.abs(y_min - y) == 1)
						difference += 4;
					else if (Math.abs(x_max - x) == 1 || Math.abs(y_min - y) == 1) {
						difference += 1;
					}
				}else if(Final.e_final.configuration[y_max].charAt(x_min)==caractere_recherche) {
					if (Math.abs(x_min - x) == 1 && Math.abs(y_max - y) == 1)
						difference += 4;
					else if (Math.abs(x_min - x) == 1 || Math.abs(y_max - y) == 1) {
						difference += 1;
					}
				}else if(Final.e_final.configuration[y_max].charAt(x_max)==caractere_recherche) {
					if (Math.abs(x_max - x) == 1 && Math.abs(y_max - y) == 1)
						difference += 4;
					else if (Math.abs(x_max - x) == 1 || Math.abs(y_max - y) == 1) {
						difference += 1;
					}
				}else {
					//on doit déplacer une case de diagonale en diagonale sur toute la grille
					//(pire des cas)
					difference+= etat.getM()*etat.getN()*4;
				}

			}
		}
		return difference;
	}

	public static int heuristique_quatre(Etat etat) {
		//QUATRIEME HEURISTIQUE :
		//on associe un nombre à chaque caractère
		//on fait la soustraction des nombres sur une ligne pour l'état actuel et l'état final
		//et on fait la soustraction de la totalité des lignes des 2 états
		int compte=0,compte2=0;
		if(etat==null)
			return 0;

		int x,y,difference=0;
		for(y=0;y<etat.configuration.length;y++){
			for(x=0;x<etat.configuration[y].length();x++){
				if(etat.configuration[y].charAt(x)!=' '&&Final.e_final.configuration[y].charAt(x)!=' ') {
					compte -= (int) etat.configuration[y].charAt(x);
					compte2 -= (int) Final.e_final.configuration[y].charAt(x);
					difference += Math.abs(compte - compte2);
				}
			}
		}

		return difference;
	}
}
