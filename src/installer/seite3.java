package installer;

import static argo.jdom.JsonNodeFactories.field;
import static argo.jdom.JsonNodeFactories.object;
import static argo.jdom.JsonNodeFactories.string;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import argo.format.PrettyJsonFormatter;
import argo.jdom.JdomParser;
import argo.jdom.JsonField;
import argo.jdom.JsonNode;
import argo.jdom.JsonRootNode;
import argo.jdom.JsonStringNode;
/**
 * 
 * Beschreibung
 * 
 * @version 2.1 vom 14.04.2013
 * @author Dirk Lippke
 */

public class seite3 extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JButton b1 = new JButton();
	private JButton b2 = new JButton();
	private JLabel feld = new JLabel();
	private JProgressBar bar = new JProgressBar();
	private JLabel stat = new JLabel();
	private float downloadzeit = 0, downloadgroesse=0;
	private double value = 0.00;
	private String Version, mineord, webplace, quelle, Fehler = "", stamm;
	private BufferedReader buffread;
	private Cursor c = new Cursor(Cursor.HAND_CURSOR);
	private Method shapeMethod, transparencyMethod;
	private Class<?> utils;
	private boolean online, arbeiten =true;
	private String Speicherort = "";
	private String zeilex = null, text ="";
	private int auswahlzahl;
	
	public seite3(final String[] namen, final String[] downloadlist, final int[] anzahl, final boolean Modloader, final String webplace, final String mineord, final boolean online, final String Version, final String stamm) 
	{
		this.mineord = mineord;
		this.webplace = webplace;
		this.Version = Version;
		this.online = online;
		this.stamm = stamm;
		
		for (int u=0; u<anzahl.length; u++)
		{
			auswahlzahl += anzahl[u];
		}
		
		setUndecorated(true);

		try 
		{
			utils = Class.forName("com.sun.awt.AWTUtilities");
			shapeMethod = utils.getMethod("setWindowShape", Window.class,Shape.class); // Rundes Design
			shapeMethod.invoke(null, this, new RoundRectangle2D.Double(0, 0,550, 230, 20, 20));
			transparencyMethod = utils.getMethod("setWindowOpacity",Window.class, float.class);
			transparencyMethod.invoke(null, this, .95f); // 5% transparent
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}

		setTitle(Read.getTextwith("installer", "name"));
		setSize(550, 230);
		setLocationRelativeTo(null);
		JPanel cp = new GraphicsPanel(false);
		cp.setLayout(null);
		add(cp);
		
		// Anfang Komponenten

		setIconImage(new ImageIcon(this.getClass().getResource("src/icon.png")).getImage());

		feld.setBackground(null);
		feld.setForeground(null);
		feld.setIcon(new ImageIcon(this.getClass().getResource("src/banner_gross.png")));
		feld.setBounds(41, 20, 468, 60);
		feld.setCursor(c);
		feld.addMouseListener(new MouseListener() { // Internetlink
			public void mouseClicked(MouseEvent e) 
			{
				new browser("http://server.nitrado.net/deu/gameserver-mieten?pk_campaign=MinecraftInstaller");
			}

			public void mouseExited(MouseEvent e) {
				Border thickBorder = new LineBorder(Color.WHITE, 0);
				feld.setBorder(thickBorder);
				feld.setBounds(41, 20, 468, 60);
			}

			public void mouseEntered(MouseEvent e) {
				Border thickBorder = new LineBorder(Color.WHITE, 1);
				feld.setBorder(thickBorder);
				feld.setBounds(40, 19, 470, 62);
			}

			public void mouseReleased(MouseEvent e) {
				feld.setBorder(new LineBorder(Color.magenta, 1));
			}

			public void mousePressed(MouseEvent e) {
				feld.setBorder(new LineBorder(Color.blue, 1));
			}
		});
		cp.add(feld);

		bar.setBounds(16, 140, 518, 33);
		cp.add(bar);
		stat.setBounds(25, 120, 510, 17);
		cp.add(stat);
		b1.setBounds(10, 190, 100, 30);
		b1.setBackground(null);
		b1.setText(Read.getTextwith("seite3", "text1"));
		b1.setMargin(new Insets(2, 2, 2, 2));
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				b1_ActionPerformed(evt);
			}
		});
		b1.setCursor(c);
		cp.add(b1);
		b2.setBounds(430, 190, 110, 30);
		b2.setBackground(null);
		b2.setText(Read.getTextwith("seite3", "text2"));
		b2.setMargin(new Insets(2, 2, 2, 2));
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				b2_ActionPerformed(evt);
			}
		});
		b2.setEnabled(false);
		b2.setCursor(c);
		cp.add(b2);
		setVisible(true);		

		new Thread() {
			private BufferedReader buffread22;
			private BufferedReader reader;
			private JsonRootNode versionData;
			@Override
			public void run() 
			{
				try 
				{
					status(value += 1);
					
					stat.setText(Read.getTextwith("seite3", "prog1") + " (Backup)");					
					new OP().del(new File(stamm + "/Modinstaller/Backup"));
					stat.setText(Read.getTextwith("seite3", "prog1") + " (Result)");
					new OP().del(new File(stamm + "/Modinstaller/Result"));		
					new OP().del(new File(mineord +"/mods"));
					new OP().del(new File(mineord +"/coremods"));
					new OP().del(new File(mineord +"/config"));

					status(value += 1);
					
					stat.setText(Read.getTextwith("seite3", "prog2"));
					new OP().makedirs(new File(stamm + "/Modinstaller/Result"));
					new OP().makedirs(new File(stamm + "/Modinstaller/Backup"));					
					new OP().makedirs(new File(stamm + "/Modinstaller/Original"));
					
					status(value += 1);
					
					stat.setText(Read.getTextwith("seite3", "prog3"));
					new OP().copy(new File(mineord + "/versions/Modinstaller"), new File(stamm + "/Modinstaller/Backup"));
					new OP().del(new File(mineord + "/versions/Modinstaller"));
					new OP().copy(new File(mineord + "/versions/"+Version), new File(mineord + "/versions/Modinstaller")); //von Versions Ordner in Modinstaller Ordner kopieren
					new OP().rename(new File(mineord + "/versions/Modinstaller/"+Version+".jar"), new File(mineord + "/versions/Modinstaller/Modinstaller.jar")); //Umbenennen in Modinstaller
					new OP().rename(new File(mineord + "/versions/Modinstaller/"+Version+".json"), new File(mineord + "/versions/Modinstaller/Modinstaller.json"));
					
					status(value += 1);
					
					String content = "";
					String xyz="";
				      try {
				        reader = new BufferedReader(new FileReader(mineord + "/versions/Modinstaller/Modinstaller.json"));
				        while ((xyz = reader.readLine()) != null) // i.txt auslesen
						{			          
				          content += xyz+"\n";  				          
				        }
				      } 
				      catch (Exception e) 
				      {				     
				      }
				      content = content.replaceAll(Version, "Modinstaller");
				      
				      status(value += 1);
				      
				      new OP().del(new File(mineord + "/versions/Modinstaller/Modinstaller.json"));
				      try 
						{
							FileWriter fw12 = new FileWriter(new File(mineord + "/versions/Modinstaller/Modinstaller.json").getPath()); //in Textdatei anstatt der Version Modinstaller schreiben
							PrintWriter pw3 = new PrintWriter(fw12);
							pw3.println(content);
							fw12.flush();
							fw12.close();
							pw3.flush();
							pw3.close();
						} 
						catch (Exception ex) 
						{
							
						}
				    
					    
					status(value += 1);					
								

					if (!new File(stamm + "/Modinstaller/original.txt").exists()&&Modloader==true) 
					{
						new OP().copy(new File(mineord + "/versions/"+Version+"/"+Version+".jar"), new File(stamm + "/Modinstaller/minecraft.jar"));
						new Extrahieren(new File(stamm + "/Modinstaller/minecraft.jar"),new File(stamm + "/Modinstaller/Original/"), stat, Fehler, mineord, stamm);    // Entpacken
					}
					if(Modloader==false)
					{
						new OP().copy(new File(mineord + "/versions/"+Version+"/"+Version+".jar"), new File(stamm + "/Modinstaller/Modinstaller.jar"));
					}

					new OP().copy(new File(stamm + "/Modinstaller/Original"),new File(stamm + "/Modinstaller/Result")); // Orginal in Ergebnisordner
					
					status(value += 1);
				} 
				catch (Exception ex) 
				{
					stat.setText("Errorcode: S3x01: " + String.valueOf(ex));
					Fehler += String.valueOf(ex) + " Errorcode: S3x01\n";
				}
				status(value += 1);
				
				if (online==true)																//Dateien herunterladen
				{			
				try 
				{
					int zz = 0;
					new OP().makedirs(new File(stamm + "/Modinstaller/Mods")); // Ordner anlegen
					for (int k = 0; k < downloadlist.length; k++) 
					{
						double promodgroesse = 85.0 / (double) anzahl.length;
						quelle = String.valueOf(downloadlist[k]); // Downloadpfad für genauere Downloadliste speichern
						if (!quelle.equals("null")) 
						{
							stat.setText(Read.getTextwith("seite3", "prog4a") + namen[k] + Read.getTextwith("seite3", "prog4b"));
							
							float[] ges = new download().downloadFile(quelle + "quellen.txt",new FileOutputStream(new File(stamm + "/Modinstaller/Mods/"+ namen[k] + ".txt")));
							buffread = new BufferedReader(new FileReader(stamm + "/Modinstaller/Mods/"+ namen[k] + ".txt")); // Quellen Datei von einem Mod herunterladen und in i.txt in Ordner speichern
							zeilex = "";  
							while ((zeilex = buffread.readLine()) != null) // Moddownladliste auslesen
							{
								Speicherort ="";
								try 
								{
									zz++;									
									
									int verbleibend = auswahlzahl - zz;
									String luegen = Read.getTextwith("seite3", "prog5a") + String.valueOf(verbleibend)+ Read.getTextwith("seite3", "prog5b");
									if (verbleibend < 1) 
									{
										luegen =  Read.getTextwith("seite3", "prog6");
									} // Prozent berechnen
								
									
									String[] ordn = zeilex.split("/");						
									if (Modloader == true) // Unterscheide Modloader Forge
									{
										Speicherort = stamm +"/Modinstaller/Mods/"	+ namen[k] + "/";
									} 
									else 
									{
										Speicherort = mineord+"/";
									}
									new OP().makedirs(new File(Speicherort+ zeilex.substring(0, zeilex.length()	- ordn[ordn.length - 1].length()))); // Ordner anlegen
																		
								
									downloadgroesse += ges[0];
									downloadzeit += ges[1]; 									
									
									String geswin="";
									try
									{
										float geschw = downloadgroesse/(downloadzeit / 1000000000);		//Bytes pro Sekunde
										if (geschw < 1000) 
										{
											geswin = String.valueOf(Math.round(geschw)) + "B/s";
										}
										else if (geschw < 1000000) 
										{
											geswin = String.valueOf(Math.round(geschw / 1024))+ "kB/s";
										}
										else 
										{
											geswin = String.valueOf(Math.round(geschw / 1024 / 1024*10.0)/10.0)+ "MB/s";											
										}
									}
									catch (Exception ex)
									{	
									}
									
									text =namen[k] + Read.getTextwith("seite3", "prog7a") + geswin + Read.getTextwith("seite3", "prog7b") +" (" + luegen + ")";
									stat.setText(text); // Status anzeigen 
									
									double groesse = promodgroesse / (double) anzahl[k];
									
									arbeiten=true;	
									if(anzahl[k]<50)
									new Thread()		//Downloadstatus pro Datei
									{
										public void run()
										{	
											int ist =0;
											int soll=0;
											
											try 
											{
												soll = new download().size(quelle + zeilex);
											} 
											catch (Exception e) 
											{
												System.out.println(e);
											}									
											
											if(soll>0)
											{
												while(arbeiten)		
												{													
													
													try 
													{
														ist = new download().groesse(new File(Speicherort+ zeilex));
													} 
													catch (Exception e) 
													{														
														e.printStackTrace();
													}					
													
													if(ist>0&&ist<=soll)
													{
														int proz = (int) Math.round(((double)ist/(double)soll)*100);					
														
														if(proz<100&&proz>0)
														{														
															//value = vor + ((double)ist/(double)soll)*groesse;													
															stat.setText(text + " - "+String.valueOf(proz) + " %");			
														}
													}
												}													
											}											
										}
									}.start();																
									
									ges = new download().downloadFile(quelle + zeilex,new FileOutputStream(new File(Speicherort+ zeilex)));// Datei aus i.txt herunterladen
									
									status(value+=groesse);
									
									arbeiten=false;										
								} 
								catch (Exception ex) 
								{
									stat.setText("Errorcode: S3x03: "	+ String.valueOf(ex));
									Fehler += String.valueOf(ex) + " at \""+ quelle + zeilex+ "\" Errorcode: S3x03\n";								
								}
							}
							buffread.close();
							if (Modloader==true) 
							{
								new OP().del(new File(stamm + "/Modinstaller/log2.log"));
								new OP().copy(new File(stamm + "/Modinstaller/Mods/"+ namen[k] + "/"), new File(stamm + "/Modinstaller/Result/")); // Mods in den Endordner kopieren	
							}
							
							stat.setText(Read.getTextwith("seite3", "prog8"));
							
							try		//nicht für Modloader oder Forge vorgesehene Dateien kopieren
							{
								new download().downloadFile(quelle + "extra.txt",new FileOutputStream(new File(stamm + "/Modinstaller/Mods/"+ namen[k] + "_extra.txt")));
								buffread22 = new BufferedReader(new FileReader(stamm + "/Modinstaller/Mods/"+ namen[k] + "_extra.txt")); 
								String zeilex2 = null;  
								while ((zeilex2 = buffread22.readLine()) != null) 
								{	
									String[]spl3 = zeilex2.split(";;");
									File g = new File(stamm+"/"+ spl3[1]);									
									new OP().makedirs(g.getParentFile());
									new download().downloadFile(quelle+spl3[0],new FileOutputStream(g));
								}									
							}
							catch(Exception ex)
							{
								stat.setText(Read.getTextwith("seite3", "prog9"));
							}
						}
					}
					if (Modloader==false) 
					{
						new OP().del(new File(stamm + "/Modinstaller/log.log"));						
						stat.setText(Read.getTextwith("seite3", "prog10"));
						new download().downloadFile(webplace + Version +"/"+ "forge.json", new FileOutputStream(new File(mineord + "/versions/Modinstaller/Modinstaller.json")));
						new OP().makedirs(new File(mineord + "/libraries/net/minecraftforge/minecraftforge/"+Version));
						if(new download().ident(webplace + Version +"/"+ "forge.jar", new File(mineord + "/libraries/net/minecraftforge/minecraftforge/"+Version+"/minecraftforge-"+Version+".jar")))
						{
							new download().downloadFile(webplace + Version +"/"+ "forge.jar", new FileOutputStream(new File(mineord + "/libraries/net/minecraftforge/minecraftforge/"+Version+"/minecraftforge-"+Version+".jar")));
						}
						
						stat.setText(Read.getTextwith("seite3", "prog10b"));
						File lib1 = new File(mineord+"/libraries/org/scala-lang/scala-compiler/2.10.2/scala-compiler-2.10.2.jar");
						File lib2 = new File(mineord+"/libraries/org/scala-lang/scala-library/2.10.2/scala-library-2.10.2.jar");
						new OP().makedirs(lib1.getParentFile());
						new OP().makedirs(lib2.getParentFile());
						if(new download().ident(webplace +"/scala-compiler-2.10.2.jar", lib1))						
						{
							stat.setText(Read.getTextwith("seite3", "prog10b") + "  - scala-compiler");						
							new download().downloadFile(webplace +"/scala-compiler-2.10.2.jar", new FileOutputStream(lib1));
						}
						if(new download().ident(webplace +"/scala-library-2.10.2.jar", lib2))			
						{		
							stat.setText(Read.getTextwith("seite3", "prog10b") + "  - scala-library");	
							new download().downloadFile(webplace +"/scala-library-2.10.2.jar", new FileOutputStream(lib2));
						}
					}
					else
					{
						new OP().del(new File(mineord + "/versions/Modinstaller/Modinstaller.json"));	
						new OP().del(new File(mineord + "/libraries/net/minecraftforge"));	
					}
				}
				 
				catch (Exception ex) 
				{
					stat.setText("Errorocde: S3x04: " + String.valueOf(ex));
					Fehler += String.valueOf(ex) + " Errorcode: S3x04\n";
				}
			    }
				
				File zusatz = new File(stamm + "/Modinstaller/zusatz.txt");
				if (zusatz.exists())  // Zusatzdateien kopieren
				{
					try 
					{
						BufferedReader buffread2 = new BufferedReader(new FileReader(zusatz.toString().replace("\\", "/")));
						String zeile2 = "";
						while ((zeile2 = buffread2.readLine()) != null) 
						{							
							stat.setText(Read.getTextwith("seite3", "prog11a") + zeile2 + Read.getTextwith("seite3", "prog11b"));
							File neu = new File(stamm + "/Modinstaller/Import/"+zeile2+".txt");
							if(neu.exists())
							{
								BufferedReader buffread3 = new BufferedReader(new FileReader(neu.toString().replace("\\", "/")));
								String zeile3 = "";
								while ((zeile3 = buffread3.readLine()) != null) 
								{	
									String[] spl = zeile3.split(";;");
									if(spl.length==1)
									{	
										if(new File(spl[0]).isDirectory())
										{
											new OP().copy(new File(spl[0]), new File(stamm + "/Modinstaller/Result/"));
										}
										else
										{
											String name = new File(spl[0]).getName().toString().replace("\\", "/");
											new OP().copy(new File(spl[0]), new File(stamm + "/Modinstaller/Result/"+name));
										}									
									}
									else
									{									
										String von = spl[0];
										String nach = spl[1]+new File(spl[0]).getName().toString().replace("\\", "/");
										
										new OP().copy(new File(von), new File(nach));
									}
								}
								buffread3.close();
							}
						}
						buffread2.close();
					} 
					catch (Exception ex) 
					{
						stat.setText("Errorcode: S3x05" + String.valueOf(ex));
						Fehler += String.valueOf(ex) + " Errorcode: S3x05\n";
					}
				}
				
				if(Modloader==true)
				{
					stat.setText(Read.getTextwith("seite3", "prog12"));				
					new Komprimieren(new File(stamm + "/Modinstaller/Result/"), new File(mineord + "/versions/Modinstaller/Modinstaller.jar"), Fehler);
					
					try 
					{
						FileWriter fw12 = new FileWriter(new File(stamm + "/Modinstaller/original.txt").getPath());
						PrintWriter pw3 = new PrintWriter(fw12);
						pw3.println(Version);
						fw12.flush();
						fw12.close();
						pw3.flush();
						pw3.close();
					} 
					catch (Exception ex) 
					{
						stat.setText("Errorcode: S3x06" + String.valueOf(ex));
						Fehler += String.valueOf(ex) + " Errorcode: S3x06\n";
					}
				}
				
				File profiles = new File(mineord + "/launcher_profiles.json");
				
				if(profiles.exists())
				{				
					FileInputStream installProfile = null;
					try 
					{
						installProfile = new FileInputStream(profiles);
					} 
					catch (FileNotFoundException e1) 
					{						
						
					}
				    JdomParser parser = new JdomParser();
				    try
				    {
				    	versionData = parser.parse(new InputStreamReader(installProfile));
				    }
				    catch (Exception e)
				    {
				      
				    }				 
				    try 
			    	{					    					    	
				        JsonField[] fields = {field("name", string("Modinstaller")), field("lastVersionId", string("Modinstaller")) };
				       

				        Map<JsonStringNode, JsonNode> profileCopy = new HashMap<JsonStringNode, JsonNode>(versionData.getNode(new Object[] { "profiles" }).getFields());
				        profileCopy.remove(string("Modinstaller"));
				        profileCopy.put(string("Modinstaller"), object(fields));				       
				        JsonRootNode profileJsonCopy = object(profileCopy);
				        
				        Map<JsonStringNode, JsonNode> rootCopy = new HashMap<JsonStringNode, JsonNode>(versionData.getFields());	    
				        rootCopy.put(string("profiles"), profileJsonCopy);
				        rootCopy.put(string("selectedProfile"), string("Modinstaller"));
				     //   rootCopy.remove(string("authenticationDatabase"));			        

				        JsonRootNode jsonProfileData = object(rootCopy); 
				    	
				        BufferedWriter newWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(profiles)));;
				        PrettyJsonFormatter.fieldOrderPreservingPrettyJsonFormatter().format(jsonProfileData, newWriter);
				        newWriter.close();				        									
					}			    	
			    	catch (Exception e) 
			    	{						
						
					}	
				}

				b2.setEnabled(true);
				b1.setEnabled(false);
				bar.setValue(100);
				

				if (!Fehler.equals("")) // alle Fehler anzeigen
				{
					new Error(Fehler, Version);
					stat.setText(Read.getTextwith("seite3", "error2"));				
				} 
				else 
				{
					stat.setText(Read.getTextwith("seite3", "prog13"));	
					int eingabe = JOptionPane.showConfirmDialog(null, Read.getTextwith("seite3", "prog14"), Read.getTextwith("seite3", "prog14h"), JOptionPane.YES_NO_OPTION);
					if(eingabe==0)
					{
						new startLauncher(webplace, mineord, online, stamm);
					}
					else
					{
						System.exit(0);
					}
				}
			}
		}.start();
	}

	public void status(double zahl) // Statusbar einstellen
	{
		bar.setValue((int) zahl);
	}

	public void b1_ActionPerformed(ActionEvent evt) // Vorgang abbrechen
	{
		if (JOptionPane.showConfirmDialog(this,	Read.getTextwith("seite3", "cancel"), Read.getTextwith("seite3", "cancelh"), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) 
		{
			try 
			{			
				new Extrahieren().exit();
				new download().exit();
				buffread.close();				
				new Komprimieren().exit();				
			} 
			catch (Exception ex) 
			{
			}
			try
			{
				new OP().del(new File(mineord+"/versions/Modinstaller"));
				try 
				{
					new OP().copy(new File(stamm+"/Modinstaller/Backup"), new File(mineord+"/versions/Modinstaller"));
					JOptionPane.showMessageDialog(null,	Read.getTextwith("seite2", "restore"), Read.getTextwith("seite2", "restoreh"), JOptionPane.INFORMATION_MESSAGE);
				} 
				catch (FileNotFoundException e) 
				{
					e.printStackTrace();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}		
				
				new OP().del(new File(stamm+"/Modinstaller/Backup"));
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
			}
			catch (Exception ex)
			{				
			}
			dispose();
			new seite2(webplace, mineord, online, Version, stamm); // beenden
		}
	}

	public void b2_ActionPerformed(ActionEvent evt) 
	{
		System.exit(0);
	}
}