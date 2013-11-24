package installer;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.ByteChannel;
import java.nio.channels.FileChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;


/**
 * 
 * Beschreibung
 * 
 * @version 2.1 vom 14.04.2013
 * @author Dirk Lippke
 */

public class OP 
{			
	public boolean del(File dir)
	{
		if(dir.exists())
		{
			File f = dir;
			if (f.isDirectory()) 
			{
				File[] files = f.listFiles();
				for (File aktFile : files) 
				{
					del(aktFile);
				}
			}
			return f.delete();
		}
		else
		{
			return false;
		}
	}
	
	public void copy(File quelle, File ziel) throws FileNotFoundException, IOException 
	{
		if(quelle.exists())
		{
			if(quelle.isDirectory())	
			{
				makedirs(ziel);
				copyDir(quelle, ziel);				
			}
			else
			{
				makedirs(ziel.getParentFile());
				copyFile(quelle, ziel);
			}
		}
	}
			  
		
	public static void copyFile(File source, File target) throws IOException
	  {
	    if (!target.exists()) {
	      target.createNewFile();
	    }
	    FileChannel sourceChannel = null;
	    FileChannel targetChannel = null;
	    try
	    {
	      sourceChannel = new FileInputStream(source).getChannel();
	      targetChannel = new FileOutputStream(target).getChannel();
	      targetChannel.transferFrom(sourceChannel, 0L, sourceChannel.size());
	    }
	    finally
	    {
	      if (sourceChannel != null) {
	        sourceChannel.close();
	      }
	      if (targetChannel != null) {
	        targetChannel.close();
	      }
	    }
	  }
	  
	  
	  public static void transfer(FileChannel inputChannel, ByteChannel outputChannel, long lengthInBytes, long chunckSizeInBytes, boolean verbose) throws IOException 
	  {
	    long overallBytesTransfered = 0L;
	    long time = -System.currentTimeMillis();
	    while (overallBytesTransfered < lengthInBytes) {
	      long bytesToTransfer = Math.min(chunckSizeInBytes, lengthInBytes - overallBytesTransfered);
	      long bytesTransfered = inputChannel.transferTo(overallBytesTransfered, bytesToTransfer, outputChannel);
	      
	      overallBytesTransfered += bytesTransfered;
	      
	      if (verbose) {
	        long percentageOfOverallBytesTransfered = Math.round(overallBytesTransfered / ((double) lengthInBytes) * 100.0);
	        System.out.printf("overall bytes transfered: %s progress %s%%\n", overallBytesTransfered, percentageOfOverallBytesTransfered);
	      }
	      
	    }
	    time += System.currentTimeMillis();
	    
	    if (verbose) {
	      double kiloBytesPerSecond = (overallBytesTransfered / 1024.0) / (time / 1000.0);
	      System.out.printf("Transfered: %s bytes in: %s s -> %s kbytes/s", overallBytesTransfered, time / 1000, kiloBytesPerSecond);
	    }
	    
	  }
	
	public void copyDir(File quelle, File ziel) throws FileNotFoundException,IOException 
	{
		File[] files = quelle.listFiles();
		ziel.mkdirs();
		for (File file : files) 
		{
			if (file.isDirectory()) 
			{
				copyDir(file,new File(ziel.getAbsolutePath()+ System.getProperty("file.separator")+ file.getName()));
			} 
			else 
			{
				copyFile(file,new File(ziel.getAbsolutePath()+ System.getProperty("file.separator")	+ file.getName()));
			}
		}
	}
	
	public void rename(File alt, File neu) throws FileNotFoundException,IOException 
	{     
        alt.renameTo(neu);
	}
	
	public void makedirs(File f)
	{
		if(!f.exists())	f.mkdirs();
	}
	
	public String version(String mineord, String Version, String webplace, boolean online, String stamm)
	{
		File file = new File(mineord + "/versions");
		if (file.exists()) 
		{
			File[] l = file.listFiles();
			int laeng =l.length;				
			for (int i=0; i<l.length; i++)
			{
				String[] Versionx1 = l[i].getName().split("\\.");
				if(Versionx1.length!=3)
				{
					laeng-=1;
				}
				else
				{
					String regex = "^\\d+$";
					boolean ok = true;
					for (int i2=0; i2<Versionx1.length; i2++)
					{
						String search = Versionx1[i2];
				        Pattern pattern = Pattern.compile(regex);
				        Matcher matcher = pattern.matcher(search);			
						if(!matcher.find()) ok=false;
					}
					if(ok==false)
					{
						laeng-=1;
					}
				}
			}
			if(laeng<=0)
			{
				JOptionPane.showMessageDialog(null, Read.getTextwith("OP", "error2"), Read.getTextwith("OP", "error2h"), JOptionPane.ERROR_MESSAGE);
				try {
					Desktop.getDesktop().open(new File(mineord + "/versions"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.exit(0);
			}
			String[] ne = new String[laeng];
			int z =0;
			for (int j=0; j<l.length; j++)
			{
				String[] Versionx2 = l[j].getName().split("\\.");				
				if(Versionx2.length==3)
				{
					String regex = "^\\d+$";
					boolean ok2 = true;
					for (int i3=0; i3<Versionx2.length; i3++)
					{
						String search2 = Versionx2[i3];
				        Pattern pattern2 = Pattern.compile(regex);
				        Matcher matcher = pattern2.matcher(search2);			
						if(!matcher.find()) ok2=false;
					}
					if(ok2==true)
					{
						ne[z]= l[j].getName();	
						z++;
					}					
				}
			}
			if(laeng<1)
			{
				Version = null;
			}
			else if(laeng==1)
			{
				Version = ne[0];
			}
			else
			{					
				Object[] options = ne;
				int selected = JOptionPane.showOptionDialog(null, Read.getTextwith("OP", "modver"), Read.getTextwith("OP", "modverh"), JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE, null,	options, options[0]);
				if(selected !=-1)
				{
					Version = ne[selected];
				}
				else
				{
					Version = ne[ne.length-1];
				}											
			}			
		}				
		else 
		{
			JOptionPane.showMessageDialog(null, Read.getTextwith("OP", "error")+":\n\n"+ mineord + "/versions", Read.getTextwith("OP", "errorh"), JOptionPane.ERROR_MESSAGE);
			try 
			{
				Desktop.getDesktop().open(new File(mineord));
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			System.exit(0);
		}		
		return Version;
	}	
}
