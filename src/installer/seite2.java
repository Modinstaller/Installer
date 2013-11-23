package installer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLEditorKit;

/**
 * 
 * Beschreibung
 * 
 * @version 2.1 vom 14.04.2013
 * @author Dirk Lippke
 */

public class seite2 extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JList jList1 = new JList();
	private DefaultListModel jList1Model = new DefaultListModel();
	private JScrollPane jList1ScrollPane = new JScrollPane(jList1);
	private JButton pfeilrechts = new JButton();
	private JButton pfeillinks = new JButton();
	private JButton importbutton = new JButton();
	private JButton einstellung = new JButton();
	private JButton restore = new JButton();
	private JButton hilfe = new JButton();
	private JButton link = new JButton();
	private JRadioButton modl = new JRadioButton("Modloader");
	private JRadioButton forg = new JRadioButton("Forge");
	private JTextPane pane;  
	private JList jList2 = new JList();
	public static DefaultListModel jList2Model = new DefaultListModel();
	private JScrollPane jList2ScrollPane = new JScrollPane(jList2);
	private JLabel uberschrift = new JLabel();
	private JLabel versionstext = new JLabel();
	private JLabel modtext = new JLabel();
	private JLabel strichoben = new JLabel();
	private JLabel strichunten = new JLabel();
	private JLabel banner = new JLabel();
	public static JButton weiter = new JButton();
	private JButton beenden = new JButton();
	private BufferedReader in, in2, in3;
	private String Version, webplace, mineord, hyperlink ="http://www.minecraft-installer.de", stamm;
	private Cursor c = new Cursor(Cursor.HAND_CURSOR);
	private Method shapeMethod, transparencyMethod;
	private Class<?> utils;
	private boolean online, Modloader=true;
	private JCheckBox check = new JCheckBox();
	private boolean aktual = true;
	private JScrollPane scroller;
	


	public seite2(final String webplace, final String mineord, final boolean online, final String Version, final String stamm) 
	{
		this.webplace = webplace;
		this.mineord = mineord;
		this.Version = Version;
		this.online = online;
		this.stamm = stamm;
		setUndecorated(true);
		try 
		{
			utils = Class.forName("com.sun.awt.AWTUtilities");
			shapeMethod = utils.getMethod("setWindowShape", Window.class,Shape.class);
			shapeMethod.invoke(null, this, new RoundRectangle2D.Double(0, 0,700, 600, 20, 20));
			transparencyMethod = utils.getMethod("setWindowOpacity",Window.class, float.class);
			transparencyMethod.invoke(null, this, .95f);
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
		setTitle(Read.getTextwith("installer", "name"));
		setSize(700, 600);
		setLocationRelativeTo(null);
		JPanel cp = new GraphicsPanel(false);
		cp.setLayout(null);
		add(cp);

		setIconImage(new ImageIcon(this.getClass().getResource("src/icon.png")).getImage());

		uberschrift.setBounds(25, 16, 638, 28);
		uberschrift.setText(Read.getTextwith("seite2", "headline"));
		uberschrift.setFont(new Font("Minecraft", Font.BOLD, 14));
		cp.add(uberschrift);
		
		versionstext.setBounds(572, 5, 110, 20);
		versionstext.setText("Minecraft " + Version);
		versionstext.setFont(new Font("Dialog", Font.BOLD, 11));
		versionstext.setHorizontalAlignment(SwingConstants.RIGHT);
		cp.add(versionstext);
		
		strichoben.setBackground(new Color(0x404040));
		strichoben.setBounds(0, 45, 700, 2);
		strichoben.setOpaque(true);
		cp.add(strichoben);
		
		

		jList1.setModel(jList1Model);
		jList1.setCellRenderer(new CellRenderer());  
		jList1ScrollPane.setBounds(30, 60, 180, 260);		
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

			public void mousePressed(MouseEvent e) {
				try 
				{	
					if (jList1Model.getSize()>0 && jList1.isEnabled() && online==true) 
					{
					    String Auswahl = (String) jList1Model.getElementAt(jList1.getSelectedIndex());
					    if(!modtext.getText().equals(Auswahl))
					    {
				            pane.setText(getInfoText(Auswahl));
				            pane.setCaretPosition(0);
					    }
					}
				}
				catch (Exception ex)
				{
					pane.setText(ex + "\n\nErrorcode: S2x01");
				}
			}
		});
		cp.add(jList1ScrollPane);

		jList2.setModel(jList2Model); // Liste2
		jList2.setCellRenderer(new CellRenderer());  
		jList2.addMouseListener(new MouseListener() 
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
				try 
				{	
					if (jList2Model.getSize()>0 && online==true) 
					{
					    String Auswahl = (String) jList2Model.getElementAt(jList2.getSelectedIndex());     
											
						if(((String) jList2Model.getElementAt(jList2.getSelectedIndex())).substring(0, 1).equals("+"))
						{
							if(e.getClickCount()==2)
							{
								new modimport(Auswahl.substring(2), mineord, Modloader, stamm);
							}
							if(!modtext.getText().equals("Import"))
							 {
								pane.setText(getInfoText("Import"));
								pane.setCaretPosition(0);
							 }							
						}
						else
						{
							 if(!modtext.getText().equals(Auswahl))
							 {
					            pane.setText(getInfoText(Auswahl));
					            pane.setCaretPosition(0);
							 }
						}
					}
				} 
				catch (Exception ex) 
				{
					pane.setText(ex + "\n\nErrorcode: S2x02");
				}
			}
		});
		jList2ScrollPane.setBounds(340, 60, 180, 260);
		cp.add(jList2ScrollPane);
		
		//JTabbedPane tabbedPane = new JTabbedPane();
		//tabbedPane.addTab( "Modloader", jList1ScrollPane);
		//tabbedPane.addTab( "Forge", new Button("j"));
		
		//tabbedPane.setBounds(30, 60, 180, 260);
		//cp.add(tabbedPane);

		pfeilrechts.setBounds(223, 90, 100, 83); // Pfeil rechts
		pfeilrechts.setBackground(new Color(0, 0, 0, 0));
		pfeilrechts.setIcon(new ImageIcon(this.getClass().getResource("src/pfeil_rechts.png")));
		pfeilrechts.setMargin(new Insets(2, 2, 2, 2));
		pfeilrechts.setToolTipText(Read.getTextwith("seite2", "text1"));
		pfeilrechts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				pfeilrechts_ActionPerformed(evt);
			}
		});
		pfeilrechts.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
				pfeilrechts.setIcon(new ImageIcon(this.getClass().getResource("src/pfeil_rechts.png")));
			}

			public void mouseEntered(MouseEvent e) {
				pfeilrechts.setIcon(new ImageIcon(this.getClass().getResource("src/pfeil_rechts_over.png")));
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}
		});
		pfeilrechts.setCursor(c);
		cp.add(pfeilrechts);

		pfeillinks.setBounds(223, 180, 100, 83); // Pfeil links
		pfeillinks.setBackground(new Color(0, 0, 0, 0));
		pfeillinks.setIcon(new ImageIcon(this.getClass().getResource("src/pfeil_links.png")));
		pfeillinks.setMargin(new Insets(2, 2, 2, 2));
		pfeillinks.setToolTipText(Read.getTextwith("seite2", "text2"));
		pfeillinks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				pfeillinks_ActionPerformed(evt);
			}
		});
		pfeillinks.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
				pfeillinks.setIcon(new ImageIcon(this.getClass().getResource("src/pfeil_links.png")));
			}

			public void mouseEntered(MouseEvent e) {
				pfeillinks.setIcon(new ImageIcon(this.getClass().getResource("src/pfeil_links_over.png")));
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}
		});
		pfeillinks.setCursor(c);
		cp.add(pfeillinks);
		
		

		importbutton.setBounds(520, 90, 180, 40); // Mods importieren
		importbutton.setBackground(new Color(0, 0, 0, 0));
		importbutton.setHorizontalAlignment(SwingConstants.LEFT);
		importbutton.setText(Read.getTextwith("seite2", "text3"));
		importbutton.setMargin(new Insets(2, 2, 2, 2));
		importbutton.setFont(importbutton.getFont().deriveFont(Font.BOLD));
		importbutton.setIcon(new ImageIcon(this.getClass().getResource("src/import_n.png")));
		importbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if(online==true)
				{
					pane.setText(getInfoText("Import"));
					pane.setCaretPosition(0);
				}
				importbutton_ActionPerformed(evt);
			}
		});
		importbutton.setCursor(c);
		cp.add(importbutton);
		
		einstellung.setBounds(520, 150, 180, 40); // Mods importieren
		einstellung.setBackground(new Color(0, 0, 0, 0));
		einstellung.setText(Read.getTextwith("seite2", "text4"));
		einstellung.setMargin(new Insets(2, 2, 2, 2));
		einstellung.setFont(einstellung.getFont().deriveFont(Font.BOLD));
		einstellung.setHorizontalAlignment(SwingConstants.LEFT);
	    einstellung.setIcon(new ImageIcon(this.getClass().getResource("src/einstellung_n.png")));
		einstellung.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				einstellung_ActionPerformed(evt);
			}
		});
		einstellung.setCursor(c);
		cp.add(einstellung);
		
		restore.setBounds(520, 210, 180, 40); // Mods importieren
		restore.setBackground(new Color(0, 0, 0, 0));
		restore.setText(Read.getTextwith("seite2", "text5"));
		restore.setMargin(new Insets(2, 2, 2, 2));
		restore.setFont(restore.getFont().deriveFont(Font.BOLD));
		restore.setIcon(new ImageIcon(this.getClass().getResource("src/restore.png")));
		restore.setHorizontalAlignment(SwingConstants.LEFT);
		restore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				restore_ActionPerformed(evt);
			}
		});
		restore.setCursor(c);
		cp.add(restore);
		
		hilfe.setBounds(520, 270, 180, 40); // Mods importieren
		hilfe.setBackground(new Color(0, 0, 0, 0));
		hilfe.setText(Read.getTextwith("seite2", "text6"));
		hilfe.setMargin(new Insets(2, 2, 2, 2));
		hilfe.setFont(hilfe.getFont().deriveFont(Font.BOLD));
		hilfe.setHorizontalAlignment(SwingConstants.LEFT);
		hilfe.setIcon(new ImageIcon(this.getClass().getResource("src/hilfe_n.png")));
		hilfe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				new browser("http://www.minecraft-installer.de/#faq");
			}
		});
		hilfe.setCursor(c);
		cp.add(hilfe);
		
		modtext.setBounds(522, 365, 180, 40);
		modtext.setText(Read.getTextwith("seite2", "text7"));
		modtext.setHorizontalAlignment(SwingConstants.CENTER);
		modtext.setFont(new Font("Dialog", Font.BOLD, 15));
		cp.add(modtext);
		
		link.setBounds(520, 400, 160, 40); // Mods importieren
		link.setBackground(new Color(0, 0, 0, 0));
		link.setHorizontalAlignment(SwingConstants.LEFT);
		link.setFont(link.getFont().deriveFont(Font.BOLD));
		link.setIcon(new ImageIcon(this.getClass().getResource("src/link_n.png")));
		link.setText(Read.getTextwith("seite2", "text8"));
		link.setMargin(new Insets(2, 2, 2, 2));
		link.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				link_ActionPerformed(evt);
			}
		});
		link.setCursor(c);
		cp.add(link);

		strichunten.setBounds(0, 545, 700, 2); // Strich unten
		strichunten.setBackground(new Color(0x404040));
	    strichunten.setOpaque(true);
	    cp.add(strichunten);

		banner.setBounds(535, 455, 140, 40);
	    banner.setIcon(new ImageIcon(this.getClass().getResource("src/banner_klein.png")));
	    banner.setCursor(c);
	    banner.addMouseListener(new MouseListener()
	    {                                                                             // Link zu Minecraft-Mods.de
	      public void mouseClicked(MouseEvent e)
	      {
	        new browser("http://www.minecraft-mods.de");
	      } 
	      public void mouseExited(MouseEvent e)
	      {
	      }
	      public void mouseEntered(MouseEvent e)
	      {
	      }
	      public void mouseReleased(MouseEvent e)
	      {
	      } 
	      public void mousePressed(MouseEvent e)
	      {
	      }     
	    }); 
	    cp.add(banner);
	    
	    check.setBounds(535, 510, 200, 25);
		check.setText(Read.getTextwith("seite2", "text11"));
		check.setOpaque(false);
		check.setSelected(true);
		check.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				check_ItemStateChanged(evt);
			}
		});
		cp.add(check);
	     
		
		beenden.setBounds(-5, 555, 150, 40);
		beenden.setBackground(new Color(0, 0, 0, 0));
		beenden.setText(Read.getTextwith("seite2", "text9"));
		beenden.setHorizontalAlignment(SwingConstants.LEFT);
		beenden.setIcon(new ImageIcon(this.getClass().getResource("src/beenden.png")));
		beenden.setMargin(new Insets(2, 2, 2, 2));
		beenden.setForeground(Color.DARK_GRAY);
		beenden.setFont(beenden.getFont().deriveFont(Font.BOLD));
		beenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				beenden_ActionPerformed(evt);
			}
		});
		beenden.setCursor(c);
		cp.add(beenden);

		weiter.setBounds(505, 555, 180, 40); // Installieren
		weiter.setBackground(null);
		weiter.setText(Read.getTextwith("seite2", "text10"));
		weiter.setFont(weiter.getFont().deriveFont((float) 15));
		weiter.setMargin(new Insets(2, 2, 2, 2));
		weiter.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent evt) 
			{
				weiter_ActionPerformed(evt);
			}
		});
		weiter.setCursor(c);
		weiter.setEnabled(false);
		cp.add(weiter);
		
		if(online==true)
		{
			modl.setText("Modloader"); // Auswahl Modloder
			modl.setSelected(true);
			modl.setBackground(new Color(0xC0C0C0));
			modl.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent evt) 
				{
					modl_ActionPerformed(evt);
				}
			});
			modl.setBounds(230, 275, 100, 20);
			cp.add(modl);
	
			 // Auswahl Forge
			forg.setText("Forge");
			forg.setBounds(230, 295, 100, 20);
			forg.setBackground(new Color(0xC0C0C0));
			forg.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent evt) 
				{
					forg_ActionPerformed();
				}
			});
			cp.add(forg);
			// Zur Gruppe hinzufügen
			ButtonGroup group = new ButtonGroup();
			group.add(modl);
			group.add(forg);
			
			updatelists();
		}
		
		pane = new JTextPane();
		pane.setEditable(false);
	    pane.setContentType("text/html");
	    pane.setEditorKit(new HTMLEditorKit());  
	    if(online==true)
	    {
	    	if(jList1Model.isEmpty())
	    	{
		    	if(!jList2Model.isEmpty())
		    	{
		    		jList2.setSelectedIndex(0);
		    		pane.setText(getInfoText((String)jList2Model.get(jList2.getSelectedIndex())));	    
		    	}
	    	}
	    	else
	    	{
	    		pane.setText(getInfoText((String)jList1Model.get(jList1.getSelectedIndex())));	    
	    	}	    	
	    }
	    else
	    {
	    	pane.setText(Read.getTextwith("seite2", "offline"));
	    }
	    pane.setCaretPosition(0);	   
	 
	    scroller = new JScrollPane(pane);
	    scroller.setBounds(30, 350, 490, 180);
	    cp.add(scroller);
	    
	    File file1 = new File(stamm +"/Modinstaller/Backup/");
		if (!file1.exists()) // Überprüfen ob Restore möglich ist		
		{
			restore.setEnabled(false);
		}
		setVisible(true);	
	}
	
	public void modl_ActionPerformed(ActionEvent evt) // Auswählen von Mods
	{
		Modloader=true;
		if(online==true)
		{
			try 
			{
				new download().downloadFile(webplace + Version + "/quellen.txt",new FileOutputStream(new File(stamm +"/Modinstaller/modlist.txt")));
				updatelists();							
				pfeillinks.setEnabled(true);
				pfeilrechts.setEnabled(true);				
				importbutton.setEnabled(true);
			} 
			catch (Exception ex) 
			{
				new Error(Read.getTextwith("seite2", "error1")+ String.valueOf(ex)+ "\n\nErrorcode: S2x03", Version);
				new browser("http://www.minecraft-installer.de/verbindung.htm");
			}			
		}
	}

	public void forg_ActionPerformed() // Auswählen von Mods
	{
		Modloader=false;
		if(online==true)
		{
			try 
			{
				new download().downloadFile(webplace + Version + "/Forge_Mods/quellen.txt",new FileOutputStream(new File(stamm +"/Modinstaller/modlist.txt")));
				updatelists();
				if(new File(stamm +"/Modinstaller/modlist.txt").length()==0)
				{					
					pfeillinks.setEnabled(false);
					pfeilrechts.setEnabled(false);
					importbutton.setEnabled(false);
				}
				else
				{					
					pfeillinks.setEnabled(true);
					pfeilrechts.setEnabled(true);
					importbutton.setEnabled(true);
				}	
			} 
			catch (Exception ex) 
			{
				new Error(Read.getTextwith("seite2", "error1")+ String.valueOf(ex)+ "\n\nErrorcode: S2x03", Version);
				new browser("http://www.minecraft-installer.de/verbindung.htm");
			}			
		}
	}

	public void updatelists() // Listen aktualisieren
	{
		jList1Model.removeAllElements();
		jList2Model.removeAllElements();
		if (Modloader==true) 
		{
			if (new File(stamm +"/Modinstaller/log.log").exists()) 
			{
				try 
				{
					in = new BufferedReader(new FileReader(stamm +"/Modinstaller/log.log")); // Alle Mods einlesen
					String zeile = null;
					while ((zeile = in.readLine()) != null) 
					{
						jList2Model.addElement(zeile); // zur Liste1 hinzufügen
					}
					in.close();
				} 
				catch (Exception ex) 
				{
					new Error(Read.getTextwith("seite2", "error2") + String.valueOf(ex)+ "\n\nErrorcode: S2x05", Version);
				}
			}
		} 
		else 
		{
			if (new File(stamm +"/Modinstaller/log2.log").exists()) 
			{
				try 
				{
					in2 = new BufferedReader(new FileReader(stamm +"/Modinstaller/log2.log")); // Alle Mods einlesen
					String zeile2 = null;
					while ((zeile2 = in2.readLine()) != null) 
					{
						jList2Model.addElement(zeile2); // zur Liste1 hinzufügen
					}
					in2.close();
				} 
				catch (Exception ex) 
				{
					new Error(Read.getTextwith("seite2", "error2") + String.valueOf(ex)+ "\n\nErrorcode: S2x06", Version);		
				}
			}
		}
		try 
		{
			if(new File(stamm +"/Modinstaller/modlist.txt").exists())
			{
				in = new BufferedReader(new FileReader(stamm +"/Modinstaller/modlist.txt")); // Alle Mods einlesen
				String zeile = null;
				while ((zeile = in.readLine()) != null) 
				{
					String[] spl = zeile.split(";"); // in String[] spl speichern
					boolean ent1 = false;
					for (int j = 0; j < jList2Model.getSize(); j++) 
					{
						if (spl[0].equals(jList2Model.getElementAt(j).toString())) // Ausgewählte Stelle suchen
						{
							ent1 = true;
						}
					}
					if (ent1 == false) 
					{
						jList1Model.addElement(spl[0]);
					}                       				// zur Liste1 hinzufügen
				}
				in.close();
				jList1.setSelectedIndex(0);
			}
			else
			{
				jList1Model.removeAllElements();
				jList2Model.removeAllElements();
			}
		} 
		catch (Exception ex) 
		{
			new Error(Read.getTextwith("seite2", "error3") + String.valueOf(ex)+ "\n\nS2x07", Version);	
		}
		new OP().del(new File(stamm +"/Modinstaller/Import"));
		new OP().del(new File(stamm +"/Modinstaller/zusatz.txt"));
	}

	public void pfeilrechts_ActionPerformed(ActionEvent evt) // Auswählen von Mods
	{		
		String modname = (String)jList1.getSelectedValue();
		if (searchentry(jList1Model, modname)) 
		{
			jList2Model.add(jList2Model.getSize(),modname);
			jList1Model.removeElement(modname);
			
			String comp = getModinfo(modname, "comp");
			if(!comp.equals("error"))
			{
				String[] spl = comp.split(";");
				for (int j=0; j<spl.length; j++)
				{
					if (searchentry(jList1Model, spl[j])) 
					{
						jList2Model.add(jList2Model.getSize(),spl[j]);
						jList1Model.removeElement(spl[j]);
					}
				}
			}
			if(getModinfo(modname, "inco").equals("allother")) //Alle verbieten mit allother
			{
				jList1.setEnabled(false);
			}
			
			weiter.setEnabled(true); // Installieren Knopf freischalten					
		}			
	}
	
	public boolean searchentry(DefaultListModel model, String modname)
	{
		boolean gefunden = false;
		for (int i=0; i < model.getSize(); i++) 
		{
			if (model.getElementAt(i).equals(modname)) // überprüfen ob schon in Liste vorhanden
			{
				gefunden = true;
			}
		}
		return gefunden;
	}

	public void pfeillinks_ActionPerformed(ActionEvent evt) // Entfernen von Mods
	{
		weiter.setEnabled(true);
		for (int i = 0; i < jList2Model.getSize(); i++) 
		{
			if (jList2.getSelectedIndex() == i) // Ausgewählte Stelle suchen
			{
				if (((String) jList2Model.getElementAt(jList2.getSelectedIndex())).substring(0, 1).equals("+")) // wenn ausgewähltes mod,dann zusatz.txt löschen
				{
					try 
					{
						BufferedReader in = new BufferedReader(new FileReader(stamm +"/Modinstaller/zusatz.txt"));
						BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(stamm +"/Modinstaller/zusatz2.txt")));
						String zeile = null;
						boolean test = false;
						while ((zeile = in.readLine()) != null) 
						{
							File zz = new File(zeile);
							if (!zz.getName().equals(((String) jList2Model.getElementAt(jList2.getSelectedIndex())).substring(2))) 
							{
								out.write(String.valueOf(zeile)	+ System.getProperty("line.separator"));
								test = true;
							} 
						}
						in.close();
						out.close();
						new OP().del(new File(stamm +"/Modinstaller/zusatz.txt"));
				        new OP().rename(new File(stamm +"/Modinstaller/zusatz2.txt"), new File(stamm +"/Modinstaller/zusatz.txt"));
						if (test == false) 
						{
							new OP().del(new File(stamm +"/Modinstaller/zusatz.txt"));
							new OP().del(new File(stamm +"/Modinstaller/Import"));
						} // end of if
					} 
					catch (Exception ex) 
					{
						new Error(String.valueOf(ex) + "\n\nErrorcode: S2x08", Version);						
					}
				} // sonst nach Liste1 kopieren
				else 
				{
					boolean ent = false;
					for (int j = 0; j < jList1Model.getSize(); j++) 
					{
						if (jList2Model.getElementAt(jList2.getSelectedIndex()).toString().equals(jList1Model.getElementAt(j).toString())) // Ausgewählte Stelle suchen
						{
							ent = true;
						}
					}
					if (ent == false) 
					{
						jList1.setEnabled(true);
						jList1Model.addElement(jList2Model.getElementAt(jList2.getSelectedIndex()));
					}
				}
				jList2Model.remove(jList2.getSelectedIndex()); // Mod vom Liste2 löschen
				if (jList2Model.getSize() == 0) 
				{
					weiter.setEnabled(false); // Wenn keine Mods in Liste2 vorhanden Installieren deaktivieren
				}
			}
		}
	}

	public void weiter_ActionPerformed(ActionEvent evt) // Installieren Knopf
	{		
		try {
			new OP().copy(new File(stamm +"/Modinstaller/log.log"), new File(stamm +"/Modinstaller/log_old.log"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			new OP().copy(new File(stamm +"/Modinstaller/log2.log"), new File(stamm +"/Modinstaller/log2_old.log"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] zeilen = new String[jList2Model.getSize()];
		String[] namen = new String[jList2Model.getSize()];	
		int[] anzahl = new int[jList2Model.getSize()];
		File file;
		try 
		{
			if (Modloader==true) 
			{
				file = new File(stamm +"/Modinstaller/log.log");
			} 
			else 
			{
				file = new File(stamm +"/Modinstaller/log2.log");
			}
			FileWriter fw1 = null;
			fw1 = new FileWriter(file.getPath());
			PrintWriter pw = new PrintWriter(fw1);

			BufferedReader in = new BufferedReader(new FileReader(stamm +"/Modinstaller/modlist.txt")); // Modliste durchkämmen
			String zeile = null;
			while ((zeile = in.readLine()) != null) 
			{
				String[] spl = zeile.split(";");
				for (int j = 0; j < jList2Model.getSize(); j++) {
					
					if (spl[0].equals(jList2Model.getElementAt(j))) // Wenn Name des Mods in Liste2 identisch
					{
						namen[j] = spl[0];
						zeilen[j] = spl[1];                           // Speicherort in zeilen speichern
						anzahl[j] = Integer.parseInt(spl[2]);
						pw.println(spl[0]);						// Anzahl der Dateien pro Mod in auswahlzahl speichern
					}
				}
			}
			in.close();
			fw1.flush();
			fw1.close();
			pw.flush();
			pw.close();
			dispose();
			new seite3(namen, zeilen, anzahl, Modloader, webplace, mineord, online, Version, stamm);
		} 
		catch (Exception ex) 
		{	
			new Error(Read.getTextwith("seite2", "error3")+ ex.toString() + " "+Thread.currentThread().getStackTrace()+ "\n\nErrorcode: S2x09", Version);	
		}		
	}

	public void importbutton_ActionPerformed(ActionEvent evt)
	{
		new modimport(mineord, Modloader, stamm);			
	}
	private String getInfoText(String modname)
	{	   
		modtext.setText(modname);
		String inh = "";  
	    
        inh="<html><body>"+getModinfo(modname, "desc")+"</body></html>";
        hyperlink = getModinfo(modname, "hyper");
	   	    
	    try		//Einfügen
	    {
	        StringBuilder sb = new StringBuilder(inh.length() * 2 + 18);
	        sb.append(inh);
	        
	        MutableAttributeSet a = new SimpleAttributeSet();
		    StyledDocument doc = (StyledDocument) pane.getDocument();
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    String fontNames[] = ge.getAvailableFontFamilyNames();     
		    try 
		    {
		      boolean ja=false;
		      for (String string : fontNames) 
		      {
		        String s = String.valueOf(string);
		        if (s.equals("Calibri")) 
		        {
		          ja=true;  
		        } 
		      }
		      if (ja) 
		      {
		        StyleConstants.setFontFamily(a, "Calibri");  
		      } 
		      else
		      {
		        StyleConstants.setFontFamily(a, "Arial"); 
		      }      
		      StyleConstants.setFontSize(a, 13);       
		      doc.setParagraphAttributes(0, doc.getLength(), a, true);
		      doc.insertString(0, sb.toString(), null);
		      return doc.getText(0, inh.length());
		    }
		    catch(Exception ex)
		    { 
		    	return String.valueOf(ex)+ "<br><br>Errorcode: INx01"; 
		    }		       		        
		}         
		catch (Exception ex)
		{
		    return String.valueOf(ex)+"<br><br>Errorcode: INx04";
		}		  
	}
	
	public void einstellung_ActionPerformed(ActionEvent evt) 
	{
		versionerfragen();
	}
	
	public void link_ActionPerformed(ActionEvent evt) 
	{
		new browser(hyperlink);
	}
	
	public void restore_ActionPerformed(ActionEvent evt)
	{
		new OP().del(new File(mineord+"/versions/Modinstaller"));
		try {
			new OP().copy(new File(stamm+"/Modinstaller/Backup"), new File(mineord+"/versions/Modinstaller"));
			JOptionPane.showMessageDialog(null,	Read.getTextwith("seite2", "restore"), Read.getTextwith("seite2", "restoreh"), JOptionPane.INFORMATION_MESSAGE);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new OP().del(new File(stamm+"/Modinstaller/log.log"));
		new OP().del(new File(stamm+"/Modinstaller/log2.log"));
		try {
			new OP().rename(new File(stamm+"/Modinstaller/log_old.log"), new File(stamm+"/Modinstaller/log.log"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			new OP().rename(new File(stamm+"/Modinstaller/log2_old.log"), new File(stamm+"/Modinstaller/log2.log"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		restore.setEnabled(false);	
	}
	
	public void beenden_ActionPerformed(ActionEvent evt) 
	{
		System.exit(0);
	}
	
	public void check_ItemStateChanged(ItemEvent evt)
	{
		if (evt.getStateChange() == 1) 
		{
			aktual = true;
			link.setEnabled(true);
			pane.setText(getInfoText(modtext.getText()));
			pane.setCaretPosition(0);
		} 
		else 
		{
			aktual = false;
			link.setEnabled(false);
		}
		
	}
	
	public void versionerfragen()
	{
		Version = new OP().version(mineord, Version, webplace, online, stamm);
		versionstext.setText("Minecraft "+Version);
		Modloader = true;
		modl.setSelected(true);
					
			try 
			{
				String[] vers = Version.split("\\.");
				boolean vorhanden=false;
				if(Integer.parseInt(vers[0])>0)
				{	
					if(Integer.parseInt(vers[0])==1&&Integer.parseInt(vers[1])>3)
					{
						vorhanden=true;
					}
					if(Integer.parseInt(vers[0])>1)
					{
						vorhanden=true;
					}
				}
				if(vorhanden)
				{
					new OP().makedirs(new File(stamm + "/Modinstaller"));
					new download().downloadFile(webplace + Version + "/quellen.txt",new FileOutputStream(new File(stamm + "/Modinstaller/modlist.txt")));
					online = true;
				}
				else
				{
					JOptionPane.showMessageDialog(null, Read.getTextwith("seite1", "inco"), Read.getTextwith("seite1", "incoh"), JOptionPane.INFORMATION_MESSAGE); //ändern					
					online = false;	
					new OP().del(new File(stamm + "/Modinstaller/modlist.txt"));					
				}
				
			} 
			catch (Exception ex) 
			{
				new Error(Read.getTextwith("seite2", "error1")+ String.valueOf(ex)+ "\n\nErrorcode: S2x03", Version);	
				new browser("http://www.minecraft-installer.de/verbindung.htm");
			}			
		
		updatelists();
	}
	
	private String getModinfo(String mod, String typ)
	{
		String ergebnis="error", inhalt="";
		
		if(aktual==true)
		{
			File speicher = new File(stamm +"/Modinstaller/Texte/"+mod+".txt");
					
			try		//Herunterladen
		    {
		    	new download().downloadFile(Read.getTextwith("seite2", "texts")+mod+".txt", new FileOutputStream(speicher));
		    }
		    catch (Exception ex)
		    {     
		    	ergebnis = "Error: "+ex;
		    }	
		
			try 
			{
				in3 = new BufferedReader(new FileReader(stamm +"/Modinstaller/Texte/"+mod+".txt"));
		        String zeile = null;
		        while ((zeile = in3.readLine()) != null) 
		        {
		          inhalt+=zeile;  
		        } 
		        if (inhalt.length()>1) 
		        {
			        String[] spl = inhalt.split(";;");
			        if(typ.equals("desc"))
			        {
			        	ergebnis = spl[0];
			        }
			        else if(typ.equals("hyper"))
			        {
			        	ergebnis = spl[1];
			        }
			        else if(typ.equals("comp"))
			        {
			        	ergebnis = spl[2];
			        }
			        else if(typ.equals("inco"))
			        {
			        	ergebnis = spl[3];
			        }		
		        }
			} 
			catch (Exception ex) 
			{	
				ergebnis = "Error: "+ex;
			}		
			return ergebnis;
		}
		return  Read.getTextwith("seite2", "text12");
	}
	
	private static class CellRenderer extends DefaultListCellRenderer 
	{  
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Component getListCellRendererComponent( JList list, Object value, int index, boolean isSelected, boolean cellHasFocus ) {  
            Component c = super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );  
            if(cellHasFocus==true) 
            	{
            	c.setBackground(new Color(0xe0e0e0));   
            	c.setForeground(Color.black);
            	}
            return c;  
        }  
    }  
}
