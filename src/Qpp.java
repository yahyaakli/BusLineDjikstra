import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Qpp {

	private JFrame frame;
	public static int[] X = {350,950,1400,2300,2300,1700,700,350};
	public static int[] Y = {1000,300,300,1300,1800,1800,1400,1000};
	public static int[] X2 = {350,700,1000,1300,1900,2300,2600,2900};
	public static int[] Y2 = {600,600,950,1200,1200,850,1100,1550};
	public static int[] X3 = {700,1100,1100,1600,1900,2600};
	public static int[] Y3 = {2000,1800,1550,700,300,300};
//	public static int[] X = {50,500,600,500,300,20,50};
	//public static int[] Y = {50,50,150,250,250,130,50};
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Qpp window = new Qpp();
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
	public Qpp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame. getContentPane().setBackground(Color.WHITE);
		frame.setSize(1550,780);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			/*	Graphics g = panel.getGraphics();
				g.drawLine(0, 0, 200, 200);
				g.fillOval(200, 200, 10, 10); */
	
			}
		});
		panel.setBounds(55, 143, 1435, 675);
		frame.getContentPane().add(panel);
		//panel.setSize(1200, 900);
		JButton btnAfficher = new JButton("AFFICHER");
		btnAfficher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				draw(X,Y,panel,"C",Color.black);
				draw(X2,Y2,panel,"A",Color.blue);
				draw(X3,Y3,panel,"B",Color.red);
			}
		});
		btnAfficher.setBounds(1118, 90, 137, 29);
		frame.getContentPane().add(btnAfficher);
	}

	public void draw(int[] X,int[] Y,JPanel panel,String str,Color col) {
		Graphics g = panel.getGraphics();
		int a=4;
		for(int i=0;i<X.length;i++) {
			g.fillOval(X[i]/a-5, Y[i]/a-5, 10, 10);
			g.drawString(str + (i+1), X[i]/a+4, Y[i]/a+4);
		}
		for(int i=0;i<X.length-1;i++) {
			g.setColor(col);
			g.drawLine(X[i]/a, Y[i]/a, X[i+1]/a, Y[i+1]/a);
		}
	}
}
