package installer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import javax.swing.JOptionPane;

public class Komprimieren 
{
	private File ortkop;
	private BufferedInputStream buffinstr;
	private JarOutputStream zipOut;
	private String Fehler;
	
	public Komprimieren(File ort2, File ziel, String Fehler)
	{
		ortkop = ort2;
		this.Fehler = Fehler;
		try 
		{
			zipOut = new JarOutputStream(new FileOutputStream(String.valueOf(ziel)));
		} 
		catch (Exception ex) 
		{
			JOptionPane.showMessageDialog(null,	Read.getTextwith("seite3", "komp1") + String.valueOf(ex)+"\n\nErrorcode: KOx01", Read.getTextwith("seite3", "komph"), JOptionPane.ERROR_MESSAGE);
		}
		Fehler += zip(ort2);
		try 
		{
			zipOut.close(); // Jar Datei schlieﬂen
		} 
		catch (Exception ex) 
		{
			JOptionPane.showMessageDialog(null,	Read.getTextwith("seite3", "komp2") + String.valueOf(ex)+"\n\nErrorcode: KOx02", Read.getTextwith("seite3", "komph"), JOptionPane.ERROR_MESSAGE);
		}
		
	}
	public Komprimieren() 
	{	
	}
	
	public String zip(File ort)
	{
		try 
		{
			File[] files = ort.listFiles();
			if (files != null) 
			{
				for (int i = 0; i < files.length; i++) // Alle Dateien und Unterordner auflisten
				{
					if (files[i].isDirectory()) 
					{
						zip(files[i]); // Wenn Unterordner vorhanden diese erst durchsuchen
					} 
					else 
					{
						buffinstr = new BufferedInputStream(new FileInputStream(files[i])); // Datei einlesen
						int avail = buffinstr.available();
						byte[] buffer = new byte[avail];
						if (avail > 0) 
						{
							buffinstr.read(buffer, 0, avail);
						}
						String eintragname = files[i].getAbsolutePath().substring(ortkop.getAbsolutePath().length()).replace("\\", "/").substring(1);
						if (eintragname.equals("_aux.class")) 
						{
							eintragname = "aux.class";
						}
						JarEntry ze = new JarEntry(eintragname); // Datei in Jar Datei schreiben
						zipOut.putNextEntry(ze);
						zipOut.write(buffer, 0, buffer.length); // Byte buffer speichern
						zipOut.closeEntry();
					}
				}
			}
		} 
		catch (Exception ex) 
		{
			Fehler += String.valueOf(ex) + " Errorcode: KOx03\n";
		} 
		finally 
		{
			try 
			{
				if (buffinstr != null)	buffinstr.close(); // Inputstream beenden
			} 
			catch (Exception ex) 
			{
				Fehler += String.valueOf(ex) + " Errorcode: KOx04\n";
			}
		}
		return Fehler;
	}
	
	public void exit()
	{
		try
		{
			buffinstr.close();
			zipOut.closeEntry();
			zipOut.close();			
		}
		catch (Exception ex)
		{			
		}	
	}
}
