import java.io.File;
import java.io.IOException;

public class App {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File stat = new File("kelkonk_bus_stations.txt");
		File lig = new File("kelkonk_bus_lignes.txt");
		GrapheParListe graphe = new GrapheParListe(stat,lig);
		graphe.CourtCheminSommet_station("A3","C5");
		
	}

}
