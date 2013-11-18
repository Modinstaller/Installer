package installer;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
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

public class lizenz extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JButton zur = new JButton();
	private JButton wei = new JButton();
	private JCheckBox check = new JCheckBox();
	private JLabel head = new JLabel();
	private String mineord, Version, webplace;
	private Method shapeMethod, transparencyMethod;
	private Class<?> utils;
	private Cursor c = new Cursor(Cursor.HAND_CURSOR);
	private String Lizenztext = "", stamm;
	private JTextPane tp = new JTextPane();
	private Scanner scan;
	private boolean online;

	public lizenz(final String mineord, final String webplace, final boolean online, final String Version, final String stamm) 
	{
		this.online = online;
		this.mineord = mineord;
		this.webplace = webplace;
		this.Version = Version;
		this.stamm = stamm;
		setUndecorated(true);

		try 
		{
			utils = Class.forName("com.sun.awt.AWTUtilities");
			shapeMethod = utils.getMethod("setWindowShape", Window.class,Shape.class);
			shapeMethod.invoke(null, this, new RoundRectangle2D.Double(0, 0,550, 336, 20, 20));
			transparencyMethod = utils.getMethod("setWindowOpacity",Window.class, float.class);
			transparencyMethod.invoke(null, this, .95f);
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
		setTitle(Read.getTextwith("installer", "name"));
		int frameWidth = 550;
		int frameHeight = 336;
		setSize(frameWidth, frameHeight);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (d.width - getSize().width) / 2;
		int y = (d.height - getSize().height) / 2;
		setLocation(x, y);

		setIconImage(new ImageIcon(this.getClass().getResource("src/icon.png"))	.getImage());

		JPanel cp = new GraphicsPanel(false);
		cp.setLayout(null);
		add(cp);

		scan = new Scanner(getClass().getResourceAsStream("src/lizenz.txt"));

		while (scan.hasNextLine()) 
		{
			Lizenztext += scan.nextLine();
		}

		final StringBuilder sb = new StringBuilder(Lizenztext.length() * 2 + 18);
		sb.append(Lizenztext);

		tp.setContentType("text/plain");
		tp.setEditorKit(new HTMLEditorKit());
		tp.setPreferredSize(new Dimension(500, 200));

		final MutableAttributeSet a = new SimpleAttributeSet();
		final StyledDocument doc = (StyledDocument) tp.getDocument();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String fontNames[] = ge.getAvailableFontFamilyNames();
		try 
		{
			boolean ja = false;
			for (String string : fontNames) 
			{
				String s = String.valueOf(string);
				if (s.equals("Calibri")) 
				{
					ja = true;
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
			tp.setText("<html><body>" + doc.getText(0, Lizenztext.length())	+ "</body></html>");
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
		tp.setEditable(false);
		JScrollPane ScrollPane2 = new JScrollPane(tp);
		ScrollPane2.setBounds(25, 50, 500, 200);
		cp.add(ScrollPane2);

		tp.setCaretPosition(0);

		zur.setCursor(c);
		zur.setBackground(null);
		zur.setBounds(10, 295, 90, 30);
		zur.setText(Read.getTextwith("lizenz", "text1"));
		zur.setMargin(new Insets(2, 2, 2, 2));
		zur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				System.exit(0);
			}
		});
		cp.add(zur);
		wei.setCursor(c);
		wei.setBackground(null);
		wei.setBounds(430, 295, 110, 30);
		wei.setText(Read.getTextwith("lizenz", "text2"));
		wei.setMargin(new Insets(2, 2, 2, 2));
		wei.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				wei_ActionPerformed(evt);
			}
		});
		wei.setEnabled(false);
		cp.add(wei);
		check.setBounds(55, 260, 400, 25);
		check.setText(Read.getTextwith("lizenz", "text3"));
		check.setOpaque(false);
		check.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				check_ItemStateChanged(evt);
			}
		});
		cp.add(check);
		head.setBounds(24, 15, 243, 25);
		head.setFont(new Font("Dialog", Font.BOLD, 14));
		head.setText(Read.getTextwith("lizenz", "text4"));
		cp.add(head);

		setVisible(true);
	}

	// Anfang Methoden
	

	public void wei_ActionPerformed(ActionEvent evt) 
	{
		try 
		{
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(stamm +"/Modinstaller/lizenz.txt", true)));
			Date zeitstempel = new Date();
			out.write(String.valueOf(zeitstempel));
			out.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		setVisible(false);
		new seite2(webplace, mineord, online, Version, stamm);
	}

	public void check_ItemStateChanged(ItemEvent evt) 
	{
		if (evt.getStateChange() == 1) 
		{
			wei.setEnabled(true);
		} 
		else 
		{
			wei.setEnabled(false);
		}
	}
}