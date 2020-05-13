import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Panel;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.JPanel;

public class Itineraire {

	private JFrame frame;
	private JTextField textFieldLigne;
	private JTextField textFieldStation;
	private JTextField textField_Depart;
	private JTextField textField_Arrivee;
	private JTextField textFieldTemps;

	private final JFileChooser lignefile;
	private final JFileChooser stationfile;

	private File stationOpened;
	private File LigneOpened;
	private GrapheParListe graphe;
	
	private Graphics g;

	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Itineraire window = new Itineraire();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Itineraire() {
		initialize();
		lignefile = new JFileChooser(); 
		stationfile = new JFileChooser(); 
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 1288, 701);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textFieldLigne = new JTextField();
		textFieldLigne.setBounds(36, 31, 174, 31);
		frame.getContentPane().add(textFieldLigne);
		textFieldLigne.setColumns(10);

		textFieldStation = new JTextField();
		textFieldStation.setColumns(10);
		textFieldStation.setBounds(36, 77, 174, 31);
		frame.getContentPane().add(textFieldStation);

		JButton btnLignesBus = new JButton("Lignes Bus");
		btnLignesBus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int retval = lignefile.showOpenDialog(btnLignesBus);
				if(retval==JFileChooser.APPROVE_OPTION) {
					LigneOpened=lignefile.getSelectedFile();
					textFieldLigne.setText(LigneOpened.getName());
				}
			}
		});
		btnLignesBus.setBounds(220, 31, 131, 30);
		frame.getContentPane().add(btnLignesBus);

		JButton button = new JButton("Station Bus");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int retval = stationfile.showOpenDialog(btnLignesBus);
				if(retval==JFileChooser.APPROVE_OPTION) {
					stationOpened=stationfile.getSelectedFile();
					textFieldStation.setText(stationOpened.getName());
				}
			}
		});
		button.setBounds(220, 77, 131, 30);
		frame.getContentPane().add(button);

		JLabel lblRecherche = new JLabel("Recherche");
		lblRecherche.setBounds(566, 0, 88, 33);
		frame.getContentPane().add(lblRecherche);

		JLabel lblDepart = new JLabel("Depart");
		lblDepart.setBounds(519, 40, 46, 13);
		frame.getContentPane().add(lblDepart);

		JLabel lblArrivee = new JLabel("Arrivee");
		lblArrivee.setBounds(634, 40, 46, 13);
		frame.getContentPane().add(lblArrivee);
		
		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("Button.background"));
		panel.setBounds(36, 118, 858, 520);
		frame.getContentPane().add(panel);

		textField_Depart = new JTextField();
		textField_Depart.setBackground(SystemColor.control);
		textField_Depart.setBounds(496, 56, 88, 52);
		frame.getContentPane().add(textField_Depart);
		textField_Depart.setColumns(10);

		textField_Arrivee = new JTextField();
		textField_Arrivee.setBackground(SystemColor.control);
		textField_Arrivee.setColumns(10);
		textField_Arrivee.setBounds(609, 56, 88, 52);
		frame.getContentPane().add(textField_Arrivee);
		
		JLabel affichageChemin = new JLabel("");
		affichageChemin.setBounds(934, 165, 161, 173);
		frame.getContentPane().add(affichageChemin);

		JButton btnRecherche = new JButton("Recherche");
		btnRecherche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector <Element> res = graphe.CourtCheminSommet_station(textField_Depart.getText(),textField_Arrivee.getText());
				String chemin ="";
				int temps = 0;
				for(int i=res.size()-1;i>=0;i--) {
					chemin = chemin + res.elementAt(i).Sommet + " ";
					temps = temps + res.elementAt(i).distance;
				}
				affichageChemin.setText(chemin);
				textFieldTemps.setText(Integer.toString(temps));
				for(int i=0;i<graphe.Liaison.size();i++) {
					if(chemin.contains(graphe.Liaison.elementAt(i).station.nomStation) && chemin.contains(graphe.Liaison.elementAt(i).precedente.nomStation) ) {
						draw(graphe.Liaison.elementAt(i).precedente,graphe.Liaison.elementAt(i).station,panel,Color.RED);
					}
					else {
						draw(graphe.Liaison.elementAt(i).precedente,graphe.Liaison.elementAt(i).station,panel,Color.GRAY);
					}
				}
				
			}
		});
		btnRecherche.setForeground(Color.BLACK);
		btnRecherche.setBackground(Color.WHITE);
		btnRecherche.setBounds(707, 55, 125, 52);
		frame.getContentPane().add(btnRecherche);


		
		
		JButton btnLancer = new JButton("Lancer");
		btnLancer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					graphe = new GrapheParListe(stationOpened,LigneOpened);
					for(int i=0;i<graphe.Liaison.size();i++) {
						if(graphe.Liaison.elementAt(i).station.nomStation.contains("A") && graphe.Liaison.elementAt(i).precedente.nomStation.contains("A")) {
							draw(graphe.Liaison.elementAt(i).precedente,graphe.Liaison.elementAt(i).station,panel,Color.blue);
						}else if((graphe.Liaison.elementAt(i).station.nomStation.contains("B") || graphe.Liaison.elementAt(i).station.nomStation.contains("A"))&& graphe.Liaison.elementAt(i).precedente.nomStation.contains("B") ){
							draw(graphe.Liaison.elementAt(i).precedente,graphe.Liaison.elementAt(i).station,panel,Color.red);
						}
						else {
							draw(graphe.Liaison.elementAt(i).precedente,graphe.Liaison.elementAt(i).station,panel,Color.black);
						}
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnLancer.setBackground(new Color(220, 220, 220));
		btnLancer.setBounds(361, 56, 80, 52);
		frame.getContentPane().add(btnLancer);

		textFieldTemps = new JTextField();
		textFieldTemps.setBounds(982, 62, 161, 40);
		frame.getContentPane().add(textFieldTemps);
		textFieldTemps.setColumns(10);

		JLabel lblTempsEstimee = new JLabel("Temps estimee");
		lblTempsEstimee.setBounds(982, 40, 102, 13);
		frame.getContentPane().add(lblTempsEstimee);
		
		JLabel lblLeCheminLe = new JLabel("Le chemin le plus court:");
		lblLeCheminLe.setBounds(930, 127, 202, 13);
		frame.getContentPane().add(lblLeCheminLe);
		


	}
	public void draw(Station X,Station Y,JPanel panel,Color col) {
		g = panel.getGraphics();
		int a=4;
		g.fillOval(X.x/a-5, X.y/a-5, 10, 10);
		g.drawString(X.nomStation, X.x/a+4, X.y/a+4);
		g.fillOval(Y.x/a-5, Y.y/a-5, 10, 10);
		g.drawString(Y.nomStation, Y.x/a+4, Y.y/a+4);
		g.setColor(col);
		g.drawLine(X.x/a, X.y/a, Y.x/a, Y.y/a);
	}

}
