import java.util.ArrayList;

public interface Ouvert {
    Etat ObtenirTete();
    boolean TeteEstUnBut();
    boolean EstVide();
    boolean Etat_existe(Etat etat);
    void InsererEtat(ArrayList<Etat> etats);
    void InsererEtat(Etat etat);
    int count();
    void Afficher();
}
