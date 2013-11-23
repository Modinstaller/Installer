package installer;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.net.URLEncoder;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * 
 * Beschreibung
 * 
 * @version 2.1 vom 14.04.2013
 * @author Dirk Lippke
 */

public class seite1 extends JFrame 
{
	private static final long serialVersionUID = 6893761562923644768L;
	private JLabel uberschrift = new JLabel();
	private JLabel banner = new JLabel();
	private JLabel grafik = new JLabel();
	private JLabel uberschrift2 = new JLabel();
	private JLabel programmtext = new JLabel();
	private JLabel prog = new JLabel();
	private JButton beenden = new JButton();
	private String Version, Programmnummer = Read.getTextwith("installer", "version"), Zusatz = Read.getTextwith("installer", "zusatz"), webplace = Read.getTextwith("installer", "webplace"), mineord, stamm;
	private boolean online = false;
	private Cursor c = new Cursor(Cursor.HAND_CURSOR);
	private Method shapeMethod, transparencyMethod;
	private Class<?> utils;
	private boolean update = true;
	
	
	public seite1()
	{
		setUndecorated(true);
		try 
		{
		    utils = Class.forName("com.sun.awt.AWTUtilities");
			shapeMethod = utils.getMethod("setWindowShape", Window.class,Shape.class);
			shapeMethod.invoke(null, this, new RoundRectangle2D.Double(0, 0,400, 200, 20, 20));
			transparencyMethod = utils.getMethod("setWindowOpacity",Window.class, float.class);
			transparencyMethod.invoke(null, this, .95f);
		} 
		catch (Exception ex) 
		{
			System.out.println(ex);
		}	
		
		setTitle(Read.getTextwith("installer", "name"));
		setSize(400, 200);
		setLocationRelativeTo(null);
		
		
		JPanel cp = new GraphicsPanel(false);
		cp.setLayout(null);
		add(cp);
		setIconImage(new ImageIcon(this.getClass().getResource("src/icon.png")).getImage());

		uberschrift.setBounds(0, 30, 400, 40);
		uberschrift.setText(Read.getTextwith("installer", "name"));
		uberschrift.setFont(new Font("Minecraft", Font.PLAIN, 20));
		uberschrift.setHorizontalAlignment(SwingConstants.CENTER);
		cp.add(uberschrift);

		uberschrift2.setBounds(0, 70, 400, 25);
		uberschrift2.setHorizontalAlignment(SwingConstants.CENTER);
		uberschrift2.setText(Read.getTextwith("installer", "desc"));
		cp.add(uberschrift2);

		programmtext.setBounds(10, 0, 150, 20);
		programmtext.setText("Version " + Programmnummer + " " + Zusatz);
		programmtext.setFont(new Font("Dialog", Font.BOLD, 9));
		cp.add(programmtext);
		
		prog.setBounds(80, 160, 350, 20);
		prog.setText(Read.getTextwith("seite1", "prog1"));
		prog.setFont(new Font("Dialog", Font.ITALIC, 12));
		cp.add(prog);

		banner.setBounds(0, 110, 400, 40);
		banner.setHorizontalAlignment(SwingConstants.CENTER);
		banner.setIcon(new ImageIcon(this.getClass().getResource("src/banner_klein.png")));
		cp.add(banner);
		
		grafik.setBounds(425, 60, 90, 80);
		grafik.setIcon(null);
		cp.add(grafik);

		beenden.setBounds(10, 150, 40, 40);
		beenden.setBackground(new Color(0, 0, 0, 0));
		beenden.setIcon(new ImageIcon(this.getClass().getResource("src/beenden.png")));
		beenden.setMargin(new Insets(2, 2, 2, 2));
		beenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				beenden_ActionPerformed(evt);
			}
		});
		beenden.setCursor(c);
		cp.add(beenden);		
		
		setVisible(true);
		
		new Thread() 
		{
			@Override
			public void run() 
			{		
				prog.setText(Read.getTextwith("seite1", "prog2"));
				
				try {
					 Thread.sleep(300);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
				String str = System.getProperty("os.name").toLowerCase(); // Ordner Appdata den Betriebssystemen anpassen
				
				 if (str.contains("win"))
				 {
					 mineord = System.getenv("APPDATA").replace("\\", "/") + "/.minecraft";
					 stamm = System.getenv("APPDATA").replace("\\", "/");
				 }
				 else if (str.contains("mac")) 
				 {
					 mineord = System.getProperty("user.home").replace("\\", "/") + "/Library/Application Support/minecraft";
					 stamm =  System.getProperty("user.home").replace("\\", "/") + "/Library/Application Support";
				 }
				 else if (str.contains("solaris")) 
				 {
					 mineord = System.getProperty("user.home").replace("\\", "/") + "/.minecraft";
					 stamm = System.getProperty("user.home").replace("\\", "/");
				 }
				 else if (str.contains("sunos")) 
				 {
					 mineord = System.getProperty("user.home").replace("\\", "/") + "/.minecraft";
					 stamm = System.getProperty("user.home").replace("\\", "/");
				 }
				 else if (str.contains("linux"))
				 {
					 mineord = System.getProperty("user.home").replace("\\", "/") + "/.minecraft";
					 stamm = System.getProperty("user.home").replace("\\", "/");
				 }
				 else if (str.contains("unix")) 
				 {
					 mineord = System.getProperty("user.home").replace("\\", "/") + "/.minecraft";
					 stamm = System.getProperty("user.home").replace("\\", "/");
				 }
				 else 
				 {
					mineord = System.getProperty("user.home").replace("\\", "/") + "/.minecraft";
				    stamm = System.getProperty("user.home").replace("\\", "/");
				 }		
					
				if(!new File(mineord).exists())
				{
					 JOptionPane.showMessageDialog(null, Read.getTextwith("seite1", "error4"), Read.getTextwith("seite1", "error4h"), JOptionPane.ERROR_MESSAGE);
					 JFileChooser fc = new JFileChooser(); 
					 
					 fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
					 int returnVal = fc.showOpenDialog(null);
					 if (returnVal != JFileChooser.APPROVE_OPTION) 
					 { 
						 System.exit(0);
					 } 
					 else 
					 { 
						 mineord = String.valueOf(fc.getSelectedFile()).replace("\\", "/");
					 }
				}		
				
				prog.setText(Read.getTextwith("seite1", "prog3"));
				try {
					Thread.sleep(300);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				 // Installation von Mincraft überprüfen
				
				Version = new OP().version(mineord, Version, webplace, online, stamm);		
				
				new OP().makedirs(new File(stamm + "/Modinstaller"));
				  
				aktualisieren();
				
				if(online==false)
				{
					new OP().del(new File(stamm + "/Modinstaller/modlist.txt"));				
				}
		    
			    if(online==true&&update==true)
				{    	  
			    	prog.setText(Read.getTextwith("seite1", "prog4"));
			    	try {
						Thread.sleep(300);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try // Update testen
					{
						new download().downloadFile("http://www.minecraft-installer.de/request.php?target=update&lang="+Read.getTextwith("installer", "lang"), new FileOutputStream(new File(stamm + "/Modinstaller/update.txt"))); // update_de.txt herunterladen
						BufferedReader in2 = new BufferedReader(new FileReader(stamm + "/Modinstaller/update.txt")); // Datei einlesen
						String zeile3 = null;
						int zahl = 0;
						String meld = "";
						String textz = "";
						boolean antw = false;
						while ((zeile3 = in2.readLine()) != null) // Datei durchkämmen
						{
							zahl++;
							if (zahl == 1) 
							{					
								String[] Nrneu = zeile3.split("\\.");
								String[] Nralt = Programmnummer.split("\\.");	
			
								if (Integer.parseInt(Nrneu[0])>Integer.parseInt(Nralt[0])) 
								{						
									antw = true;	
								}
								else if(Integer.parseInt(Nrneu[0])==Integer.parseInt(Nralt[0]))
								{
									if (Integer.parseInt(Nrneu[1])>Integer.parseInt(Nralt[1])) 
									{
										antw = true;						
									}
									else if(Integer.parseInt(Nrneu[1])==Integer.parseInt(Nralt[1]))
									{
										if(Nrneu.length==3)
										{
											if(Nralt.length==3)
											{
												if (Integer.parseInt(Nrneu[2])>Integer.parseInt(Nralt[2])) 
												{
													antw = true;					
												}	
											}
											else
											{	
												if (Integer.parseInt(Nrneu[2])>0) 
												{
													antw = true;						
												}
											}								
										}	
									}
								}
								meld = zeile3;
							} 
							else // alle anderen Zeilen in text speichern
							{
								textz += zeile3;
							}
						}
						in2.close();
						if (antw == true) // Wenn Programmnummer nicht identisch ist
						{
							prog.setText(Read.getTextwith("seite1", "prog5"));
							int eingabe = JOptionPane.showConfirmDialog(null,"<html><body><span style=\"font-weight:bold\">"+Read.getTextwith("seite1", "update1")+ meld+ Read.getTextwith("seite1", "update2")+ textz+ Read.getTextwith("seite1", "update3"), Read.getTextwith("seite1", "update1") + meld, JOptionPane.YES_NO_OPTION);
							if (eingabe == 0) 
							{
								new browser("http://www.minecraft-installer.de");
							} // end of if
						} // end of if
						else
						{
							prog.setText(Read.getTextwith("seite1", "prog6"));
					    	try {
								Thread.sleep(300);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						}		 
						catch (Exception ex) 
						{
							return;
						}
					} 
		    
			
				    new OP().makedirs(new File(stamm+"/Modinstaller/Texte"));    
					new OP().del(new File(stamm+"/Modinstaller/zusatz.txt"));
					new OP().del(new File(stamm+"/Modinstaller/Import"));
					
					 prog.setText(Read.getTextwith("seite1", "prog12"));
					 
				    try 
				    {
						Thread.sleep(500);
						dispose();
					} 
				    catch (InterruptedException e1) 
				    {				
						e1.printStackTrace();
					}   
					File filex = new File(stamm+"/Modinstaller/lizenz.txt"); // Überprüfen ob lizenz.txt vorhanden
					if (filex.exists()) 
					{
						new seite2(webplace, mineord, online, Version, stamm);
					} 
					else 
					{
						new lizenz(mineord, webplace, online, Version, stamm);
					}
					}
				}.start();
			}
		
			// Anfang Methoden
			
	
	public void aktualisieren()
	{
		try 												// Wenn Minecraft aktueller
		{ 
			prog.setText(Read.getTextwith("seite1", "prog7"));
			try {
				Thread.sleep(300);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			File or = new File(stamm + "/Modinstaller/original.txt");
			if(or.exists())
			{
				BufferedReader in3 = new BufferedReader(new FileReader(or)); // Minecraft Version ermitteln
				String zeile2;
				boolean de = false;
				while ((zeile2 = in3.readLine()) != null) 
				{
					if (!zeile2.equals(Version)) 
					{
						de =true;						
					}
				}
				in3.close();
				if (de==true)
				{
					prog.setText(Read.getTextwith("seite1", "prog8"));
					try {
						Thread.sleep(300);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					new OP().del(new File(stamm + "/Modinstaller/Mods"));
					new OP().del(new File(stamm + "/Modinstaller/Original"));
					new OP().del(new File(stamm + "/Modinstaller/log.log"));
					new OP().del(new File(stamm + "/Modinstaller/log2.log"));
					new OP().del(new File(stamm + "/Modinstaller/original.txt"));
					new OP().del(new File(stamm + "/Modinstaller/Mods/forge.zip"));	
					new OP().del(new File(stamm + "/Modinstaller/Mods/Forge"));	
				}
				else
				{
					prog.setText(Read.getTextwith("seite1", "prog9"));
					try {
						Thread.sleep(300);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
				}
			}				
		} 
		catch (Exception ex) 
		{
			new Error(String.valueOf(ex) +"\n\nErrorcode: S1x03", Version);
		}
		
		if (update == true)
		{			
			prog.setText(Read.getTextwith("seite1", "prog10"));
			try {
				Thread.sleep(300);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
					Zusatz = "Offline";
				}
			} 
			catch (Exception ex) 
			{				
				try 
				{
					String body = "Text=" + URLEncoder.encode(String.valueOf(ex) + "; Errorcode: S1x04", "UTF-8" ) + "&" + "MCVers=" + URLEncoder.encode(Version, "UTF-8" ) + "&" + "InstallerVers=" + URLEncoder.encode( Read.getTextwith("installer", "version"), "UTF-8" ) + "&" + "OP=" + URLEncoder.encode(System.getProperty("os.name").toString() + "; " + System.getProperty("os.version").toString() + "; " + System.getProperty("os.arch").toString(), "UTF-8" )+ "&" + "EMail=" + URLEncoder.encode(" ", "UTF-8" );
					new download().post("http://www.minecraft-installer.de/error.php", body);
				} 
				catch (Exception e) 
				{					
					e.printStackTrace();
				}				
				
				prog.setText(Read.getTextwith("seite1", "prog11"));
				Object[] option = {Read.getTextwith("seite1", "inter1"), Read.getTextwith("seite1", "inter2"), Read.getTextwith("seite1", "inter3")};
				int select = JOptionPane.showOptionDialog(null, Read.getTextwith("seite1", "inter4")+ String.valueOf(ex)+ "\n\nErrorcode: S1x04", Read.getTextwith("seite1", "inter4h"), JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE, null, option, option[0]);
				switch (select)
				{
				case 0:
				{
					new browser("http://www.minecraft-installer.de/verbindung.htm");	
					online = false;
					Zusatz = "Offline";
				}
					break;
				case 1: 
					{
						online = false;
						Zusatz = "Offline";
					}
					break;
				case 2:System.exit(0);
					break;
				default: break;				
				}	
				programmtext.setText("Version " + Programmnummer + " " + Zusatz);
			}
		}
		else
		{
			online = true;	
		}		
	}
	
	public void beenden_ActionPerformed(ActionEvent evt) 
	{
		System.exit(0);
	}
	

	public static void main(String[] args) 
	{	
		System.setProperty("java.net.preferIPv4Stack", "true");
		  try 
		    {
		      UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		    } 
		    catch (Exception e) 
		    {
		      e.printStackTrace();
		    }
		new seite1();			
	}	
}
