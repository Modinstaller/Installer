package installer;

import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * 
 * Beschreibung
 * 
 * @version 2.1 vom 14.04.2013
 * @author Dirk Lippke
 */

public class startLauncher 
{	
	public startLauncher(final String webplace, final String mineord, final boolean online, final String stamm)
	{
		new OP().makedirs(new File(stamm +"/Modinstaller/"));
		boolean ausf = true;
		File start = new File(stamm +"/Modinstaller/Launcher2.jar");	
		if(!start.exists())
		{
			try 
			{	
				new download().downloadFile(webplace + "Launcher2.jar",	new FileOutputStream(start));		
				ausf =true;		
			} 
			catch (Exception ex) 
			{
				ausf =false;
			}
		}
		if(ausf ==false)
		{
			int eingabe = JOptionPane.showConfirmDialog(null, Read.getTextwith("startLauncher", "prog1"), Read.getTextwith("startLauncher", "prog1h"), JOptionPane.YES_NO_OPTION);
			if(eingabe == 0)
			{
				JFileChooser FC = new JFileChooser();
				FC.setDialogTitle(Read.getTextwith("startLauncher", "prog2"));
				FC.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FC.addChoosableFileFilter(new SimpleGraphicFileFilter());
				FC.setMultiSelectionEnabled(false);
				if (FC.showOpenDialog(FC) == JFileChooser.APPROVE_OPTION) // Ordner öffnen
				{
					try
					{
						new OP().copy(FC.getSelectedFile(), start);		
						ausf=true;
					}
					catch (Exception ex)
					{	
						JOptionPane.showMessageDialog(null, ex + "\n\nErrorcode: STx02");
					}
				}        
			}
		}
	 
		if(ausf==true)
		{
			try 
			{				
				Runtime.getRuntime().exec("java -jar " + start.toString());								
			} 
			catch (Exception ex) 
			{
				JOptionPane.showMessageDialog(null, ex + "\n\nErrorcode: STx03");
			}
			System.exit(0);
		}	
	}
}