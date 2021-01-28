import java.util.Hashtable;

public class Ferme {

	private static Hashtable<String, Etat> Etats_fermes= new Hashtable<>();


	public static boolean Dans_Ferme(Etat etat){
		if(Etats_fermes.size()==0)
			return false;
		return Etats_fermes.containsKey(String.join("", etat.configuration));
	}

	public static void Ajouter(Etat etat){
		Etats_fermes.put(String.join("", etat.configuration),etat);
	}

}
