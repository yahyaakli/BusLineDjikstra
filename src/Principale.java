import utilensemjava.Lecture;
import java.util.*;

public class Principale {
	public static void main(String[] args){

		int V = Lecture.lireEntier("nombre de sommets =? ");
		int	E = Lecture.lireEntier("nombre d'arcs =?");
		GrapheParListe g = new	GrapheParListe(V,E);
		g.afficherGraphe();
		System.out.println("test");
		//g.afficherGraphe2();
		//juste pour vérifier le graphe saisi

		Vector S=g.plusCourtChemin(0);
		for(int	i = 0; i < S.size(); i++){
			System.out.println(((Element)S.elementAt(i)).sommet+","+((Element)S.elementAt(i)).distance);
		}
		//pour tester la méthode arc
		/*
					while(1==1){
					int source = Lecture.lireEntier("source =? ");
					int dest = Lecture.lireEntier("destination =? ");
					System.out.println(g.arc(source, dest));;
					}
		 */
	}
}