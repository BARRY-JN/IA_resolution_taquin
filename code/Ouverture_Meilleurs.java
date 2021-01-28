import java.util.ArrayList;
import java.util.PriorityQueue;

public class Ouverture_Meilleurs implements Ouvert {
	private PriorityQueue<Etat> ouvert; //liste

	public Ouverture_Meilleurs(){
		ouvert=new PriorityQueue<>();
	}

	//les etats sont dépilés par ordre croissant d'heuristique
	@Override
	public Etat ObtenirTete() {
		return ouvert.poll();
	}

	@Override
	public boolean TeteEstUnBut() {
		if (!ouvert.isEmpty()) {
			return ouvert.peek().Est_final();
		}else{
			return false;
		}
	}

	@Override
	public boolean EstVide() {
		return ouvert.isEmpty();
	}

	@Override
	public boolean Etat_existe(Etat etat) {
		for(Etat et:ouvert){
			if(et.equals(etat))
				return true;
		}
		return false;
	}

	@Override
	public void InsererEtat(ArrayList<Etat> etats) {

		for(Etat etat:etats) {
			InsererEtat(etat);
		}
	}

	@Override
	public void InsererEtat(Etat etat) {
		if(etat!=null)
			ouvert.add(etat);
	}

	@Override
	public int count(){
		return ouvert.size();
	}

	@Override
	public void Afficher() {
		for(Etat et:ouvert) {
			InteractionProb.afficher_etats(et, true);
		}
	}
}
