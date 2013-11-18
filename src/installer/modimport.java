package installer;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/**
 * 
 * Beschreibung
 * 
 * @version 2.1 vom 14.04.2013
 * @author Dirk Lippke
 */

public class modimport extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private String mineord, eingabe, Ordner, stamm;
	private JList jList1 = new JList();
	private DefaultListModel jList1Model = new DefaultListModel();
	private JScrollPane jList1ScrollPane = new JScrollPane(jList1);
	private JButton hinzu = new JButton();
	private JButton entf = new JButton();
	private JButton bend = new JButton();
	private Cursor c = new Cursor(Cursor.HAND_CURSOR);
	private boolean erstellen = false;
	private boolean Modloader;
	
	public modimport(final String mineord, final boolean Modloader, final String stamm)
	{
		this.mineord = mineord;
		this.Modloader = Modloader;
		this.stamm = stamm;
		this.eingabe = JOptionPane.showInputDialog(null, Read.getTextwith("modimport", "text1"), Read.getTextwith("modimport", "text1"), JOptionPane.PLAIN_MESSAGE);
		if(eingabe!=null)
		{
			boolean um=true;
			for(int i = 0; i<seite2.jList2Model.getSize();i++)
			{
				if(seite2.jList2Model.getElementAt(i).toString().substring(2).equals(eingabe))
				{
					um=false;
				}
			}
			if(um==true)
			{
				try
				{
					BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(stamm+"/Modinstaller/zusatz.txt", true)));
					out.write(eingabe+ System.getProperty("line.separator"));
					out.close();
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(null, ex + "\n\nErrorcode: MOx01");
				}
				if(Modloader==true)
				{
					JOptionPane.showMessageDialog(null, Read.getTextwith("modimport", "text2"), Read.getTextwith("modimport", "text2h"), JOptionPane.INFORMATION_MESSAGE);
				}
				erstellen =true;
				make();
			}
			else
			{
				JOptionPane.showMessageDialog(null, Read.getTextwith("modimport", "text3"), Read.getTextwith("modimport", "text3h"), JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	public modimport (final String ein, final String mineord, final boolean Modloader, final String stamm)
	{
		this.mineord = mineord;
		this.eingabe=ein;
		this.Modloader = Modloader;
		this.stamm = stamm;
		make();
	}

	public void make()
	{
		setTitle(Read.getTextwith("modimport", "text4") + " \""+eingabe+"\"");
		int frameWidth = 310;
		int frameHeight = 290;
		setSize(frameWidth, frameHeight);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (d.width - getSize().width) / 2;
		int y = (d.height - getSize().height) / 2;
		setLocation(x, y);
		setResizable(false);
	
		setIconImage(new ImageIcon(this.getClass().getResource("src/icon.png"))	.getImage());
	
		JPanel cp = new GraphicsPanel(false);
		cp.setLayout(null);
		add(cp);
	
		hinzu.setBounds(0, 30, 130, 45); 
		hinzu.setBackground(new Color(0, 0, 0, 0));
		hinzu.setFont(hinzu.getFont().deriveFont(Font.BOLD));
		hinzu.setHorizontalAlignment(SwingConstants.LEFT);
		hinzu.setText(Read.getTextwith("modimport", "text5"));
		hinzu.setMargin(new Insets(2, 2, 2, 2));
		hinzu.setIcon(new ImageIcon(this.getClass().getResource("src/add.png")));
		hinzu.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			hinzu_ActionPerformed(evt);
		}
		});
		hinzu.setCursor(c);
		cp.add(hinzu);
	
		entf.setBounds(0, 75, 130, 45); 
		entf.setBackground(new Color(0, 0, 0, 0));
		entf.setHorizontalAlignment(SwingConstants.LEFT);
		entf.setFont(entf.getFont().deriveFont(Font.BOLD));
		entf.setText(Read.getTextwith("modimport", "text6"));
		entf.setIcon(new ImageIcon(this.getClass().getResource("src/delete.png")));
		entf.setMargin(new Insets(2, 2, 2, 2));
		entf.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			entf_ActionPerformed(evt);
		}
		});
		entf.setCursor(c);
		cp.add(entf);
		
		bend.setBounds(10, 220, 120, 30); 
		bend.setBackground(null);
		bend.setText(Read.getTextwith("modimport", "text7"));
		bend.setMargin(new Insets(2, 2, 2, 2));
		bend.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			bend_ActionPerformed(evt);
		}
		});
		bend.setCursor(c);
		cp.add(bend);
	
		jList1.setModel(jList1Model); // Liste1
		jList1ScrollPane.setBounds(135, 10, 160, 240);
		cp.add(jList1ScrollPane);
	
		File b = new File(stamm+"/Modinstaller/Import/"+eingabe+".txt");
		if(b.exists())
		{
			try
			{
				BufferedReader in = new BufferedReader(new FileReader(stamm+"/Modinstaller/Import/"+eingabe+".txt")); // zusatz.txt durchkämmen
				String zeile = null;
				while ((zeile = in.readLine()) != null) 
				{
					String[] spl = zeile.split(";;");
					File zz	= new File(spl[0]);						
					jList1Model.addElement(zz.getName());
				}
				in.close();
			}
			catch (Exception ex)
			{	
				JOptionPane.showMessageDialog(null, ex + "\n\nErrorcode: MOx02");
			}
		}	
		jList1.addMouseListener(new MouseListener() 
		{
			public void mouseClicked(MouseEvent e) {
			}
	
			public void mouseExited(MouseEvent e) {
			}
	
			public void mouseEntered(MouseEvent e) {
			} 
	
			public void mouseReleased(MouseEvent e) {
			}
	
			public void mousePressed(MouseEvent e) 
			{
				if(jList1Model.getSize()>0&&e.getClickCount() == 2)
				{
					try 
					{
						BufferedReader in = new BufferedReader(new FileReader(stamm+"/Modinstaller/Import/"+eingabe+".txt")); // zusatz.txt durchkämmen
						String zeile = null;
						while ((zeile = in.readLine()) != null) 
						{
							String[] spl = zeile.split(";;");
							zeile = spl[0];
							if (new File(zeile).getName().equals(((String) jList1Model.getElementAt(jList1.getSelectedIndex())))) 
							{
								try 
								{
									Desktop.getDesktop().open(new File(zeile));
								} 
								catch (IOException ex) 
								{
									ex.printStackTrace();
								} 	
							} // end of if						
						}
						in.close();		
					} 
					catch (Exception ex) 
					{
						JOptionPane.showMessageDialog(null, ex + "\n\nErrorcode: MOx03");
					}			
				}
			}
		});
		setVisible(true);	
	}

	public void hinzu_ActionPerformed(ActionEvent evt) // Auswählen von Mods
	{
		File i = new File(stamm+"/Modinstaller/Import/");
		i.mkdirs();
		if(Modloader==false)
		{
			Object[] options = {".minecraft", ".minecraft/mods", ".minecraft/coremods", ".minecraft/databases"};
			int selected = JOptionPane.showOptionDialog(null, Read.getTextwith("modimport", "text8"), Read.getTextwith("modimport", "text8h"), JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE, null,	options, options[0]);
			switch (selected) 
			{                                  // Nach Version fragen
				case 0:
					Ordner = "";			
					break;
				case 1:
					Ordner = "mods/";
					break;
				case 2:
					Ordner = "coremods/";
					break;
				case 3:
					Ordner = "database/";
					break;
				default:
					Ordner = "";
			} // end of switch	
		}
		JFileChooser FC = new JFileChooser();
		FC.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		FC.setMultiSelectionEnabled(true);
		FC.setDialogTitle(Read.getTextwith("modimport", "text9"));
		if (FC.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) // Ordner öffnen
		{
			File[] files = FC.getSelectedFiles();
			for (int j =0; j<files.length; j++)
			{
				jList1Model.addElement(files[j].getName());
				try 
				{
					BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(stamm+"/Modinstaller/Import/"+eingabe+".txt", true))); // Ausgewählte Dateien in zusatz.txt abspeichern
					if(Modloader==true)
					{
						out.write(String.valueOf(files[j]).replace("\\", "/")+ System.getProperty("line.separator"));
					}
					else
					{
						out.write(String.valueOf(files[j]).replace("\\", "/")+";;"+mineord+"/"+Ordner+ System.getProperty("line.separator"));
					}
					out.close();
				} 
				catch (Exception e) 
				{
					JOptionPane.showMessageDialog(null, Read.getTextwith("modimport", "error1")	+ String.valueOf(e)	+ "\n\nErrorcode: MOx04", Read.getTextwith("modimport", "error1h"), JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	public void entf_ActionPerformed(ActionEvent evt) // Auswählen von Mods
	{
		if(jList1Model.getSize()>0&&jList1.isSelectionEmpty()==false)
		{
			try 
			{
				BufferedReader in = new BufferedReader(new FileReader(stamm+"/Modinstaller/Import/"+eingabe+".txt")); // zusatz.txt durchkämmen
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(stamm+"/Modinstaller/Import/"+eingabe+"2.txt")));
				String zeile = null;
				boolean test = false;
				while ((zeile = in.readLine()) != null) 
				{
					File zz = new File(zeile);
					if (!zz.getName().equals(((String) jList1Model.getElementAt(jList1.getSelectedIndex())))) 
					{
						out.write(String.valueOf(zeile)	+ System.getProperty("line.separator"));
						test = true;
					} // end of if
			}
			in.close();
			out.close();
			new OP().del(new File(stamm+"/Modinstaller/Import/"+eingabe+".txt"));
	        new OP().rename(new File(stamm+"/Modinstaller/Import/"+eingabe+"2.txt"), new File(stamm+"/Modinstaller/Import/"+eingabe+".txt"));
			if (test == false) 
			{
				new OP().del(new File(stamm+"/Modinstaller/Import/"+eingabe+".txt"));
			} 
			} 
			catch (Exception ex) 
			{
				JOptionPane.showMessageDialog(null, ex + "\n\nErrorcode: MOx05");
			}
			try
			{
				jList1Model.removeElementAt(jList1.getSelectedIndex());
			}
			catch (Exception ex)
			{		
				JOptionPane.showMessageDialog(null, ex + "\n\nErrorcode: MOx06");
			}
		}
	}
	
	public void bend_ActionPerformed(ActionEvent evt) // Auswählen von Mods
	{
		if(jList1Model.getSize()>0)
		{
			seite2.weiter.setEnabled(true);	
			if(erstellen==true)
			{
				seite2.jList2Model.addElement("+ " + eingabe);
			}
		}
		else
		{
			for(int i = 0; i<seite2.jList2Model.getSize();i++)
			{
				if(seite2.jList2Model.getElementAt(i).toString().substring(2).equals(eingabe))
				{
					seite2.jList2Model.removeElementAt(i);
				}
			}
			try 
			{
				BufferedReader in = new BufferedReader(new FileReader(stamm+"/Modinstaller/zusatz.txt"));
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(stamm+"/Modinstaller/zusatz2.txt")));
				String zeile = null;
				boolean test = false;
				while ((zeile = in.readLine()) != null) 
				{
					File zz = new File(zeile);
					if (!zz.getName().equals(eingabe)) 
					{
						JOptionPane.showMessageDialog(null, zeile);
						out.write(String.valueOf(zeile)	+ System.getProperty("line.separator"));
						test = true;
					} 
				}
				in.close();
				out.close();
				new OP().del(new File(stamm+"/Modinstaller/zusatz.txt"));
		        new OP().rename(new File(stamm+"/Modinstaller/zusatz2.txt"), new File(stamm+"/Modinstaller/zusatz.txt"));
				if (test == false) 
				{
					new OP().del(new File(stamm+"/Modinstaller/zusatz.txt"));
					new OP().del(new File(stamm+"/Modinstaller/Import"));
				} // end of if
			} 
			catch (Exception ex) 
			{
				JOptionPane.showMessageDialog(null, ex + "\n\nErrorcode: MOx07");
			}
		}
		dispose();
	}
}
