import java.util.ArrayList;
import java.util.Stack;

public class Ouverture_Profondeur implements Ouvert {

	private Stack<Etat> ouvert; //pile

	public Ouverture_Profondeur(){
		ouvert = new Stack<>();
	}

	@Override
	public Etat ObtenirTete() {
		return ouvert.pop();
	}

	@Override
	public boolean TeteEstUnBut() {
			return ouvert.peek().Est_final();
	}

	@Override
	public boolean EstVide() {
		return ouvert.isEmpty();
	}

	@Override
	public boolean Etat_existe(Etat etat) {
		int i;
		Etat et;
		for(i=0;i<ouvert.size();i++){
			et=ouvert.elementAt(i);
			if(et.equals(etat))
				return true;
		}
		return false;
	}

	@Override
	public void InsererEtat(ArrayList<Etat> etats) {
		Etat tmp;
		for(int i=0;i<etats.size();i++) {
			tmp=etats.get(i);
			this.InsererEtat(tmp);
		}
	}

	@Override
	public void InsererEtat(Etat etat) {
		ouvert.push(etat);
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
