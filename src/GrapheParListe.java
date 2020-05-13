//import utilensemjava.Lecture;

import java.io.*;
import java.util.*;


public class GrapheParListe 
{
	public Liste adj[]; //Liste d'ajacence, un tableau de listes chaines
	public Vector <Station> ListeStations;
	public Vector <LiasStation> Liaison;
	//Constructeur on saisie les arcs la main
/*	GrapheParListe(int nb_sommets, int nb_arcs)
	{
		adj=new Liste[nb_sommets];
		for(int i=0 ; i<nb_sommets ; i++) 
		{	adj[i]=null;	}


		for(int k=0 ; k<nb_arcs ; k++)
		{
			int i=Lecture.lireEntier("Sommet sortant du "+(k+1)+"eme arc (0-"+(nb_sommets-1)+") = ");
			int j=Lecture.lireEntier("Sommet d'arrive du "+(k+1)+"eme arc (0-"+(nb_sommets-1)+") = ");
			int val=Lecture.lireEntier("Valeur du "+(k+1)+"eme arc = ");

			adj[i]=new Liste(j,adj[i],val);
		}
	}
*/
	//Constructeur avec le graphe fixe du cours, pour un exemple
	GrapheParListe()
	{
		adj=new Liste[6];
		for(int i=0 ; i<adj.length ; i++) 
		{	adj[i]=null;	}

		//On saisie les arcs
		adj[0]=new Liste(3,adj[0],10);
		adj[0]=new Liste(1,adj[0],1);
		adj[1]=new Liste(4,adj[1],1);
		adj[1]=new Liste(3,adj[1],8);
		adj[1]=new Liste(2,adj[1],7);
		adj[4]=new Liste(3,adj[4],4);
		adj[4]=new Liste(2,adj[4],2);
		adj[5]=new Liste(3,adj[5],2);
	}

	//Constructeur pour lire le graphe partir d'un fichier texte
	GrapheParListe(int nb_sommets) throws IOException
	{
		//On cree la liste d'adjacence vide avec le nombre de noeuds donne par nb_sommets
		adj=new Liste[nb_sommets];
		for(int i=0 ; i<nb_sommets ; i++) adj[i]=null;

		//On lit ligne par ligne le graphe partir d'un fichier
		File f = new File("inputG.csv");
		BufferedReader fR = new BufferedReader(new FileReader(f));
		String chaine ="";
		String [] noeud;
		do {
			chaine = fR.readLine();
			//pour visualiser la ligne lue:
			if (chaine != null) {
				System.out.println(chaine);
				noeud=chaine.split(",");
				int lg_liste=(noeud.length-1)/2; // nombre de noeuds dans une liste chaine
				for (int i=0;i<lg_liste;i++) {
					adj[Integer.parseInt(noeud[0])]=new Liste(Integer.parseInt(noeud[2*i+1]),
							adj[Integer.parseInt(noeud[0])],Integer.parseInt(noeud[2*i+2]));
				}
			}
		} while (chaine != null);
		fR.close();
	}
	// REMPLIR LE GRAPHE PAR LES DONNEES DES FICHIERS
	public GrapheParListe(File station,File lignes) throws IOException {
		BufferedReader Lg = new BufferedReader(new FileReader(lignes));
		BufferedReader St = new BufferedReader(new FileReader(station));
		ListeStations= new Vector<Station>();
		String Ligne = "";
		int x=0;
		int y=0;
		// REMPLIR LA LISTE DES STATION A PARTIR DU FICHIER LIGNES
		do {
			String [] L;
			Ligne = St.readLine(); 
			if(Ligne!=null && !Ligne.substring(0,1).equals("#")) {
				L=Ligne.split("	");
				if(L[0]!="#") {
					Station m = new Station(L[0],Integer.valueOf(L[1]),Integer.valueOf(L[2]));
					ListeStations.add(m);
				} 
			}
		}while(Ligne!= null);
		// TROUVER LE GRAPHE
		// REMPLIS UNE LISTE DE LIAISON ENTRE LES STATION 
		Ligne = "";
		String [] Lsplit;
		Boolean FirstRead = false;
		Liaison = new Vector<LiasStation>(); // represente les liaison entre les stations

		int indice =0;
		// trouver tout les liaison entre les stations
		do {
			while(!FirstRead) {
				Ligne = Lg.readLine();
				if(Ligne != null && Ligne.length()>1 && !Ligne.substring(0,1).equals("#")) {
					Lsplit = Ligne.split(":");
					LiasStation m;
					for(int i=0;i<ListeStations.size();i++) {
						String Comp = ListeStations.elementAt(i).nomStation;
						if(Lsplit[1].equals(Comp)) {
							m = new LiasStation(ListeStations.elementAt(i),ListeStations.elementAt(i),0.0);
							Liaison.add(m);
						}
					}
					FirstRead = true;
				}
			}
			Ligne = Lg.readLine();
			if(Ligne != null && Ligne.length()>1 && !Ligne.substring(0,1).equals("#")) {
				Lsplit = Ligne.split(":");
				LiasStation m;
				Station pre = Liaison.elementAt(indice).station;
				for(int i=0;i<ListeStations.size();i++) {
					String Comp = ListeStations.elementAt(i).nomStation;
					if(Lsplit[1].equals(Comp) && !Lsplit[1].equals(pre.nomStation)) {
						m = new LiasStation(ListeStations.elementAt(i),pre,ListeStations.elementAt(i).Distance(pre));
						Liaison.add(m);
						indice++;
					}
				}
			}
			if(Ligne != null && Ligne.length()<2 ) {
				FirstRead = false;
				indice++;
			}
		}while(Ligne!=null);
		// remplir le graphe
		int nb_sommets = ListeStations.size();
		int nb_arcs = Liaison.size();
		adj=new Liste[nb_sommets];

		for(int i=0 ; i<nb_sommets ; i++) adj[i]=null;
		for(int k=1 ; k<nb_arcs ; k++)
		{
			Station stat = Liaison.elementAt(k).precedente; 
			Station suiv = Liaison.elementAt(k).station;
			int index = ListeStations.indexOf(stat);
			adj[index]=new Liste(suiv,adj[index],(int) Liaison.elementAt(k).Distance/20 + 60);
		}

	}

	//Affichage du graphe : liste de noeuds connecte partir d'un sommet sortant
	public void afficherGraphe()
	{
		for(int i=0 ; i<adj.length ; i++)
		{
			System.out.print("Sommet "+i+" : ");
			if (adj[i]!=null) 
			{
				Liste a = adj[i];
				while(a!=null)
				{
					System.out.print(a.num_noeud+" "+a.valeur+" ");
					if (a.suivant != null)
						System.out.print(" -> ");
					a=a.suivant;
				}
			}
			System.out.println("-> null");
		}
	}
	// meme methode d'affichage applique sur un graphe avec des objet de type station
	public void afficherGraphe_station()
	{
		for(int i=0 ; i<adj.length ; i++)
		{
			System.out.print("Sommet "+ListeStations.elementAt(i)+" : ");
			if (adj[i]!=null) 
			{
				Liste a = adj[i];
				while(a!=null)
				{
					System.out.print(a.num_station+" "+a.valeur+" ");
					if (a.suivant != null)
						System.out.print(" -> ");
					a=a.suivant;
				}
			}
			System.out.println("-> null");
		}
	}

	//Tester s'il existe un arc entre deux sommets
	public boolean arc (int source, int dest)
	{
		boolean arcExiste=false;

		if (adj[source]!=null) 
		{
			Liste a=adj[source];
			while (a!= null)
			{
				if (a.num_noeud==dest) 
				{	arcExiste=true;	}
				if (arcExiste) 
				{	a=null;	}
				else 
				{	a=a.suivant;	}
			}
		}
		return arcExiste;
	}
	// meme methode que la precedent applique sur un graphe avec des objets de type station
	public boolean arc_station (Station source, Station dest)
	{
		boolean arcExiste=false;
		int index = ListeStations.indexOf(source);
		if (adj[index]!=null) 
		{
			Liste a=adj[index];
			while (a!= null)
			{
				if (a.num_station==dest) 
				{	arcExiste=true;	}
				if (arcExiste) 
				{	a=null;	}
				else 
				{	a=a.suivant;	}
			}
		}
		return arcExiste;
	}
	public boolean arc_station (String S, String D)
	{
		Station source = new Station();
		Station dest = new Station();
		boolean sourceFound = false;
		boolean DestFound = false;
		for(int i=0;i<ListeStations.size();i++) {
			if(ListeStations.elementAt(i).nomStation.equals(S)) {
				source = ListeStations.elementAt(i);
				sourceFound = true;
			}
			if(ListeStations.elementAt(i).nomStation.equals(D)) {
				dest = ListeStations.elementAt(i);
				DestFound = true;
			}
		}
		boolean arcExiste=false;
		if(sourceFound && DestFound){

			int index = ListeStations.indexOf(source);
			if (adj[index]!=null) 
			{
				Liste a=adj[index];
				while (a!= null)
				{
					if (a.num_station==dest) {	
						arcExiste=true;
					}
					if (arcExiste) 
					{	a=null;	}
					else 
					{	a=a.suivant;}
				}
			}
		}
		return arcExiste;
	}
	//Retourne la valeur de l'arc entre deux sommets
	public int valeurArc(int source, int dest)
	{
		int val=Integer.MAX_VALUE;
		boolean arcExiste=false;
		Liste a=adj[source];

		while (a!=null)
		{
			if (a.num_noeud==dest) 
			{	val = a.valeur; arcExiste=true;	}
			if (arcExiste) 
			{	a=null;	}
			else 
			{	a=a.suivant;	}
		}
		return val;
	}
	// meme methode que la precedent applique sur un graphe avec des objets de type station
	public int valeurArc_station(Station source, Station dest)
	{
		boolean arcExiste=false;
		int index = ListeStations.indexOf(source);
		int val=Integer.MAX_VALUE;
		Liste a=adj[index];

		while (a!=null)
		{
			if (a.num_station==dest) 
			{	val = a.valeur; arcExiste=true;	}
			if (arcExiste) 
			{	a=null;	}
			else 
			{	a=a.suivant;	}
		}
		return val;
	}
	public int valeurArc_station(String S, String D)
	{
		Station source = new Station();
		Station dest = new Station();
		boolean sourceFound = false;
		boolean DestFound = false;
		for(int i=0;i<ListeStations.size();i++) {
			if(ListeStations.elementAt(i).nomStation.equals(S)) {
				source = ListeStations.elementAt(i);
				sourceFound = true;
			}
			if(ListeStations.elementAt(i).nomStation.equals(D)) {
				dest = ListeStations.elementAt(i);
				DestFound = true;
			}
		}
		if(sourceFound && DestFound) return valeurArc_station(source,dest);
		else return Integer.MAX_VALUE;

	}
	//Recherche le plus court chemin d'un sommet source vers tous les autres noeuds
	public Vector plusCourtChemin(int num_sommet)
	{
		final int INFINI=999999999;
		Vector <Element> S=new  Vector<Element>() ;	//Vector de solution
		Vector D=new Vector(); 	//Vector du départ

		//Initialise l'ensemble de départ D
		for(int i=0 ; i<adj.length ; i++)
		{
			if (i!=num_sommet)
			{	D.addElement(new Element(i,INFINI,num_sommet));	}
			else
			{	D.addElement(new Element(i,0,num_sommet));	}
		}

		//Construit l'ensemble des solutions S selon l'algorithme de Dijkstra
		while(D.size()!=0)
		{
			//On cherche l'element qui a la plus petite distance par rapport au sommet de depart
			int indice_min=0;
			int dm=INFINI;
			int sm=((Element)D.elementAt(0)).sommet;
			int pm=((Element)D.elementAt(0)).predecesseur;

			for(int i=0 ; i<D.size() ; i++)
			{
				if(dm>((Element)D.elementAt(i)).distance)
				{
					dm=((Element)D.elementAt(i)).distance;
					sm=((Element)D.elementAt(i)).sommet;
					pm=((Element)D.elementAt(i)).predecesseur;
					indice_min=i;
				}
			}
			Element m=new Element(sm,dm,pm);

			//On ajoute l'élément trouvé dans S puis on le supprime de D
			S.addElement(m);
			D.removeElementAt(indice_min);

			//On recalcule les distances pour tout sommet x de D qui possède un arc avec le sommet m
			for(int i=0 ; i<D.size() ; i++)
			{
				Element x=(Element)D.elementAt(i);
				if(arc(m.sommet,x.sommet))
				{
					int d=m.distance+valeurArc(m.sommet,x.sommet);
					if(d<x.distance)
					{
						x.distance=d;
						x.predecesseur=m.sommet;
						D.setElementAt(x,i);
					}
				}
			}
		}
		System.out.println("");
		System.out.println("Vecteur solution : ");
		for(int i=0 ; i<S.size() ; i++)
		{	System.out.println(((Element)S.elementAt(i)).predecesseur+","+((Element)S.elementAt(i)).sommet+","+((Element)S.elementAt(i)).distance);	}
		System.out.println("");
		return S;
	}
	public Vector plusCourtChemin2(int num_sommet)
	{
		final int INFINI=999999999;
		Vector <Element> S=new  Vector<Element>() ;	//Vector de solution
		Vector<Element> D=new Vector<Element>(); 	//Vector du départ

		//Initialise l'ensemble de départ D
		for(int i=0 ; i<adj.length ; i++)
		{
			if (i!=num_sommet)
			{	D.addElement(new Element(i,INFINI,num_sommet));	}
			else
			{	D.addElement(new Element(i,0,num_sommet));	}
		}

		//Construit l'ensemble des solutions S selon l'algorithme de Dijkstra
		while(D.size()!=0)
		{
			//On cherche l'element qui a la plus petite distance par rapport au sommet de depart
			int indice_min=0;
			int dm=INFINI;
			int sm=((Element)D.elementAt(0)).sommet;
			int pm=((Element)D.elementAt(0)).predecesseur;

			for(int i=0 ; i<D.size() ; i++)
			{
				if(dm>((Element)D.elementAt(i)).distance)
				{
					dm=((Element)D.elementAt(i)).distance;
					sm=((Element)D.elementAt(i)).sommet;
					pm=((Element)D.elementAt(i)).predecesseur;
					indice_min=i;
				}
			}
			Element m=new Element(sm,dm,pm);

			//On ajoute l'élément trouvé dans S puis on le supprime de D
			S.addElement(m);
			D.removeElementAt(indice_min);

			//On recalcule les distances pour tout sommet x de D qui possède un arc avec le sommet m
			for(int i=0 ; i<D.size() ; i++)
			{
				Element x=(Element)D.elementAt(i);
				if(arc(m.sommet,x.sommet))
				{
					int d=m.distance+valeurArc(m.sommet,x.sommet);
					if(d<x.distance)
					{
						x.distance=d;
						x.predecesseur=m.sommet;
						D.setElementAt(x,i);
					}
				}
			}
		}
		return S;
	}
	// meme methode que la precedent applique sur un graphe avec des objets de type station
	public Vector plusCourtChemin_station(Station num_sommet)
	{
		final int INFINI=999999999;
		Vector <Element> S=new  Vector<Element>() ;	//Vector de solution
		Vector<Element> D=new Vector<Element>(); 	//Vector du départ

		//Initialise l'ensemble de départ D
		for(int i=0 ; i<ListeStations.size() ; i++)
		{
			if (ListeStations.elementAt(i)!=num_sommet)
			{	D.addElement(new Element(ListeStations.elementAt(i),INFINI,num_sommet));	}
			else
			{	D.addElement(new Element(ListeStations.elementAt(i),0,num_sommet));	}
		}

		//Construit l'ensemble des solutions S selon l'algorithme de Dijkstra
		while(D.size()!=0)
		{
			//On cherche l'element qui a la plus petite distance par rapport au sommet de depart
			int indice_min=0;
			int dm=INFINI;
			Station sm=((Element)D.elementAt(0)).Sommet;
			Station pm=((Element)D.elementAt(0)).Predecesseur;

			for(int i=0 ; i<D.size() ; i++)
			{
				if(dm>((Element)D.elementAt(i)).distance)
				{
					dm=((Element)D.elementAt(i)).distance;
					sm=((Element)D.elementAt(i)).Sommet;
					pm=((Element)D.elementAt(i)).Predecesseur;
					indice_min=i;
				}
			}
			Element m=new Element(sm,dm,pm);

			//On ajoute l'élément trouvé dans S puis on le supprime de D
			S.addElement(m);
			D.removeElementAt(indice_min);

			//On recalcule les distances pour tout sommet x de D qui possède un arc avec le sommet m
			for(int i=0 ; i<D.size() ; i++)
			{
				Element x=(Element)D.elementAt(i);
				if(arc_station(m.Sommet,x.Sommet))
				{
					int d=m.distance+valeurArc_station(m.Sommet,x.Sommet);
					if(d<x.distance)
					{
						x.distance=d;
						x.Predecesseur=m.Sommet;
						D.setElementAt(x,i);
					}
				}
			}
		}
	/*	System.out.println("");
		System.out.println("Vecteur solution : ");
		for(int i=0 ; i<S.size() ; i++)
		{	System.out.println(((Element)S.elementAt(i)).Predecesseur+","+((Element)S.elementAt(i)).Sommet+","+((Element)S.elementAt(i)).distance);	}
		System.out.println("");*/
		return S;
	}
	public Vector plusCourtChemin_station(String num) {
		Station source = new Station();
		for(int i=0;i<ListeStations.size();i++) {
			if(ListeStations.elementAt(i).nomStation.equals(num)) {
				source = ListeStations.elementAt(i);
			}
		}
		return plusCourtChemin_station(source);
	}
	public Vector CourtCheminSommet(int sommetDepart, int sommetArrive) {
		Vector <Element> S= plusCourtChemin2(sommetDepart);
		Vector <Element> Res = new Vector<Element>(); 
		if(arc(sommetDepart,sommetArrive)) {
			Element Start = S.elementAt(0);
			Element Finish = S.elementAt(1);
			for(Element el:S) {
				if(el.sommet==sommetArrive) {
					Start = el;
				}
				if(el.sommet==sommetDepart) {
					Finish = el;
				}
			}
			Res.add(Start);
			while(Start!=Finish) {
				int Pred = Start.predecesseur;
				for(Element el:S) {
					if(el.sommet==Pred) Start = el;
				}
				Res.add(Start);
			}
			System.out.println("le chemin le plus court du sommet "+sommetDepart+" au sommet "+sommetArrive);
			for(int i=Res.size()-1; i>=0;i--) {
				System.out.print("S"+Res.elementAt(i).sommet+" ");
			}
		}	
		return Res;	
	}
	// meme methode que la precedent applique sur un graphe avec des objets de type station
	public Vector CourtCheminSommet_station(Station sommetDepart, Station sommetArrive) {
		Vector <Element> S= plusCourtChemin_station(sommetDepart);
		Vector <Element> Res = new Vector<Element>(); 
			Element Start = S.elementAt(0);
			Element Finish = S.elementAt(1);
			for(Element el:S) {
				if(el.Sommet==sommetArrive) {
					Start = el;
				}
				if(el.Sommet==sommetDepart) {
					Finish = el;
				}
			}
			Res.add(Start);
			while(Start!=Finish) {
				Station Pred = Start.Predecesseur;
				for(Element el:S) {
					if(el.Sommet==Pred) {
						Start = el;
					}
				}
				Res.add(Start);
			}
		/*	System.out.println("le chemin le plus court du sommet "+sommetDepart+" au sommet "+sommetArrive);
			for(int i=Res.size()-1; i>=0;i--) {
				System.out.print(Res.elementAt(i).Sommet+" ");
			}*/
		
		return Res;	
	}
	public Vector CourtCheminSommet_station(String depart,String Arrive) {
		Station source = new Station();
		Station arriv = new Station();
		for(int i=0;i<ListeStations.size();i++) {
			if(ListeStations.elementAt(i).nomStation.equals(depart)) {
				source = ListeStations.elementAt(i);
			}
			if(ListeStations.elementAt(i).nomStation.equals(Arrive)) {
				arriv = ListeStations.elementAt(i);
			}
		}
		return CourtCheminSommet_station(source,arriv);
	}
}
