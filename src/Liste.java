
public class Liste {
	int num_noeud;
	int valeur;
	Liste suivant;
	Station num_station;
	public Liste(int num,Liste suiv,int c) {
		this.num_noeud = num;
		this.valeur = c;
		this.suivant = suiv;
	}
	public Liste(Station num,Liste suiv,int c) {
		this.num_station = num;
		this.valeur = c;
		this.suivant = suiv;
	}
}
