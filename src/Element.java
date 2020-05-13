
public class Element {
	int sommet;
	int distance;
	int predecesseur;
	Station Sommet;
	Station Predecesseur;
	public Element(int s, int d) {
		this.sommet = s;
		this.distance = d;
	}
	public Element(int s, int d, int p) {
		this.sommet = s;
		this.distance = d;
		this.predecesseur= p;
	}
	public Element(Station s, int d, Station p) {
		this.Sommet = s;
		this.distance = d;
		this.Predecesseur= p;
	}
}
