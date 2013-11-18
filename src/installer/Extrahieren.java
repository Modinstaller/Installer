package installer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.JLabel;

public class Extrahieren 
{
	private BufferedInputStream buffinstr = null;
	private BufferedOutputStream buffoutstr = null;
	private JarFile jarFile = null;
	
public Extrahieren(File ort, File ziel, JLabel stat, String Fehler, String mineord, String stamm)
{
	byte[] buffer = null;
	try 
	{
		jarFile = new JarFile(String.valueOf(ort));
		Enumeration<JarEntry> enu = jarFile.entries();
		double proz = 0.000;
		double zz = 0;
		while (enu.hasMoreElements()) 
		{ 															// Jar Datei auslesen
			zz++;
			proz = (zz / 2448) * 100;
			stat.setText(Read.getTextwith("seite3", "extra") + String.valueOf(Math.round(proz)) + "%)"); // Prozentsatz abschätzen

			JarEntry jarEntry = enu.nextElement(); // Jedes Element einzeln kopieren
			try 
			{
				buffinstr = new BufferedInputStream(jarFile.getInputStream(jarEntry)); // Datei der zip im Byte-Buffer speichern
				int avail = buffinstr.available();
				if (avail > 0) 
				{
					buffer = new byte[avail];
					buffinstr.read(buffer, 0, avail);
				}
			} 
			catch (Exception ex) 
			{
				stat.setText("Errorcode: EXx01: " + String.valueOf(ex));
				Fehler += String.valueOf(ex) + " Errorcode: EXx01\n";
			} 
			finally 
			{
				try 
				{
					if (buffinstr != null)	buffinstr.close(); // Inputstream beenden
				} 
				catch (Exception ex) 
				{
					stat.setText("Errorcode: EXx02: " + String.valueOf(ex));
					Fehler += String.valueOf(ex) + " Errorcode: EXx02\n";
				}
			}
			try 
			{
				File ord = new File(jarEntry.getName());
				new OP().makedirs(new File(ziel + "/" + ord.getParent())); // Schrägstricke!!!!!!!		
				String name=jarEntry.getName();
				if (ord.getName().equals("aux.class")) 
				{
					name = "_aux.class";
				} // end of if
				buffoutstr = new BufferedOutputStream(new FileOutputStream(ziel + "/"	+ name)); // Datei der zip im korrekten Ordner abspeichern
				buffoutstr.write(buffer, 0, buffer.length);
			} 
			catch (Exception ex) 
			{
				stat.setText("Errorcode: EXx03: " + String.valueOf(ex));
				Fehler += String.valueOf(ex) + " Errorcode: EXx03\n";
			} 
			finally 
			{
				try 
				{
					if (buffoutstr != null) buffoutstr.close(); // Outputstream beenden
				} 
				catch (Exception ex) 
				{
					stat.setText("Errorcode: EXx04: " + String.valueOf(ex));
					Fehler += String.valueOf(ex) + " Errorcode: EXx04\n";
				}
			}
		}
		jarFile.close();
	} 
	catch (Exception ex) 
	{
		stat.setText("Errorcode: EXx05: " + String.valueOf(ex));
		Fehler += String.valueOf(ex) + " Errorcode: EXx05\n";
	}
	new OP().del(new File(stamm + "/Modinstaller/Original/META-INF"));	
}

public Extrahieren() {
	// TODO Auto-generated constructor stub
}

public void exit()
{
	try
	{
		buffinstr.close();
		buffoutstr.close();
		jarFile.close();	
	}
	catch (Exception ex)
	{			
	}	
}
}
