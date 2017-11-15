import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.io.*;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.ListIterator;
import java.util.Map;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.text.JTextComponent;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import com.sun.glass.events.KeyEvent;
import com.sun.org.apache.xalan.internal.xsltc.CollatorFactory;
import com.sun.scenario.effect.light.Light;

import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import sun.rmi.transport.LiveRef;


/*
 * Program creates a Windows application that can be used to track and store sports scores for my
 * two children.  It has text box display and input, as well as increment and decrement
 * buttons.  Scores, entered either in the text box or with the increment/decrement buttons, are 
 * validated for numerical, positive integer input.  Scoring can be loaded from a text file to 
 * continue a previous score (File->Open).  New scores can be saved back to the file (File->Save).  
 * Program demonstrates the use of layouts, spacers, menus, background colors, file streams, 
 * images, action listers, fonts, mnemonics, and accelerators.        
 */

public class KidsCounter
{	
    //class-level variables

    //labels
    private JLabel samLabel;
    private JLabel bekahLabel;

    //Text Boxes
    private JTextField samText;
    private JTextField bekahText;

    //Buttons
    private JButton samPlusBtn;
    private JButton samMinusBtn;
    private JButton bekahPlusBtn;
    private JButton bekahMinusBtn;
	
    //image icon 
    ImageIcon picKids;
    JLabel lblKidsPic;
    
    //background color
    Color contentColor = new Color(120, 135, 171);
	
    //window frame
    private JFrame frame;
    private JPanel contentPane;
    
    //numbers
    private Integer samNum;
    private Integer bekahNum;
    
    //file-related
    JFileChooser fc;
    File kidsFile;
    
    //instantiate a KidsCounter object and run the start function
    public static void main (String[] args) 
    {
       KidsCounter listGUI = new KidsCounter();
       listGUI.start();
    }

    //creates the window that calls the functions to make the menus and content
    public void start() 
    {
    	frame = new JFrame("Kids Counter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = (JPanel)frame.getContentPane();
               
        contentPane.setBackground(contentColor);
        
        makeMenus();
        makeContent();
        
        frame.setSize(320, 400);
        
        frame.setVisible(true);
    }
    
    //create menubar and call functions to make the File and Help menus
    private void makeMenus()
    {
        JMenuBar menuBar;
        
        menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
                
        menuBar.add(makeFileMenu());        
        menuBar.add(makeHelpMenu());
    }
    
    //create the File menu, setting mnemonics, accelerators, and action listeners
    private JMenu makeFileMenu()
    {
        JMenu menu;
        JMenuItem menuItem;

        //File menu
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        
        //Open menu item
        menuItem = new JMenuItem("Open");
        menuItem.setMnemonic(KeyEvent.VK_O);
        menuItem.addActionListener(new OpenMenuItemListener());
        menuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_O,
                                       Event.CTRL_MASK));
        menu.add(menuItem);
        
        //Save menu item
        menuItem = new JMenuItem("Save");
        menuItem.setMnemonic(KeyEvent.VK_S);
        menuItem.addActionListener(new SaveMenuItemListener());
        menuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_S,
                                       Event.CTRL_MASK));
        menu.add(menuItem);
          
        //Exit menu item
        menu.addSeparator();
        menuItem = new JMenuItem("Exit");
        menuItem.setMnemonic(KeyEvent.VK_X);
        menuItem.addActionListener(new ExitMenuItemListener());
        menuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_Q,
                                       Event.CTRL_MASK));
        menu.add(menuItem);
          
        return menu;
    }
    
    //create the Help menu, setting mnemonics and action listeners
    private JMenu makeHelpMenu()
    {
        JMenu menu;
        JMenuItem menuItem;

        //Help menu
        menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);
        
        //About menu item
        menuItem = new JMenuItem("About Kids Counter");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.addActionListener(new AboutMenuItemListener());
        menu.add(menuItem);
          
        return menu;
    }
    
    //build content pane: add content pane layout, spacing "glue"/"strut", panels (for image and kids counting areas) 
    private void makeContent()
    {    	
    	JPanel panel = new JPanel();
    	
    	//creates content pane layout and border spacing with an empty border  	
    	contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        
        //picture panel
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(new Color(6, 21, 57));
        
        picKids = new ImageIcon("kids.jpg");        
        lblKidsPic = new JLabel(picKids);        
        lblKidsPic.setAlignmentX(Container.LEFT_ALIGNMENT);        
        panel.add(lblKidsPic);
                
        //add vertical spacing to picture panel
        panel.add(Box.createVerticalGlue());
        
        contentPane.add(panel);
        
        //spacing panel
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(contentColor);
        panel.add(Box.createVerticalStrut(25));
                
        contentPane.add(panel);
                
        //*Bekah's scoring section*
        
        //panel for name label
        panel = new JPanel();        
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(new Color(212, 177, 106));
      
        bekahLabel = new JLabel("Bekah's Token Count:");
        bekahLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
                
        panel.add(bekahLabel);        
        panel.add(Box.createHorizontalGlue());
        contentPane.add(panel);
     
        //panel for text box and buttons
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 15, 2));
        panel.setBackground(new Color(38, 23, 88));
        
        bekahText = new JTextField("0");
        bekahText.setFont(new Font("Trebuchet MS", Font.BOLD + Font.ITALIC, 14));
        bekahText.setColumns(10);
        bekahText.setMaximumSize(new Dimension(80,24));
        panel.add(bekahText);
        
        bekahPlusBtn = new JButton("  +  ");
        bekahPlusBtn.addActionListener(new bekahPlusListener());
        panel.add(bekahPlusBtn);
        
        bekahMinusBtn = new JButton("  -  ");
        bekahMinusBtn.addActionListener(new bekahMinusListener());
        panel.add(bekahMinusBtn);
        
        panel.add(Box.createHorizontalGlue());
        
        contentPane.add(panel);
                
        //*Sam's scoring section*
        
        //panel for name label
        panel = new JPanel();        
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(new Color(212, 177, 106));
        
        samLabel = new JLabel("Sam's Token Count:");
        samLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
                
        panel.add(samLabel);        
        panel.add(Box.createHorizontalGlue());
        
        contentPane.add(panel);
        
        //panel for text box and buttons
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 20, 2));
        panel.setBackground(new Color(38, 23, 88));
        
        samText = new JTextField("0");
        samText.setFont(new Font("Trebuchet MS", Font.BOLD + Font.ITALIC, 14));
        samText.setColumns(10);
        samText.setMaximumSize(new Dimension(80,24));
        panel.add(samText);
        
        samPlusBtn = new JButton("  +  ");
        samPlusBtn.addActionListener(new samPlusListener());
        panel.add(samPlusBtn);
        
        samMinusBtn = new JButton("  -  ");
        samMinusBtn.addActionListener(new samMinusListener());
        panel.add(samMinusBtn);
        
        panel.add(Box.createHorizontalGlue());
        
        contentPane.add(panel);

    }
    
    /*Action Listeners*/
    
    //File->Save clicked: after file validation, print scores to file     
    private class SaveMenuItemListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
        	if(kidsFile==null)
        	{
        		JOptionPane.showMessageDialog(frame, 
                        "File was never loaded.  Go to File->Open.", 
                        "File Load Warning", 
                        JOptionPane.WARNING_MESSAGE);
        		
        		return;
        	}
        	
        	try         	
        	{
        		PrintStream oFile = new PrintStream(kidsFile);
        		oFile.println(bekahText.getText() + " " + samText.getText());
            	oFile.close();
        	}
        	catch(FileNotFoundException ex)
        	{
        		JOptionPane.showMessageDialog(frame, 
                        "File was never found.  Go to File->Open to load another file.", 
                        "File Output Warning", 
                        JOptionPane.WARNING_MESSAGE);
        	}
        }
    }

    //Help->About Kids Counter clicked: display message about program
    private class AboutMenuItemListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            JOptionPane.showMessageDialog(frame, 
                    "KidsCounter\n\nVersion 1.0\n\n" +
                    "(c) Copyright Andrew Davison 2017\n" + "All rights reserved\n\n", 
                    "About KidsCounter", 
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    //File->Open clicked: use open file dialog to scan scores in from file 
    private class OpenMenuItemListener implements ActionListener
    {
        public void actionPerformed(ActionEvent ae)
        {        	        	
        	fc = new JFileChooser();
        	fc.showOpenDialog(frame);
            
            kidsFile = fc.getSelectedFile();
            
            if(kidsFile==null)
            	return;  
            
            //scan first two integers from file and place them into text boxes on the form 
            try 
            {
            	Scanner scan = new Scanner(kidsFile);
            	int itemNum = 0;
            	            	
            	while(scan.hasNext())
            	{            		            	
            		if(itemNum==0)
            		{
            			bekahNum = scan.nextInt();
            			bekahText.setText(bekahNum.toString());
            			itemNum++;
            		}
            		else if(itemNum==1)
            		{
            			samNum = scan.nextInt();
            			samText.setText(samNum.toString());
            			itemNum++;
            		}        			
            	}
            	
            	scan.close();
            }
            catch (IOException e) 
            {
            	JOptionPane.showMessageDialog(frame, 
                        "I/O error in file\n\n     " + kidsFile.getName() +
                        "\n\nThis program will close", 
                        "I/O Error", 
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1);
			}
        }
    }
    
    //increment Bekah's score, validate positive integer input
    private class bekahPlusListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Integer ctr = 0;
            
            try
            {
            	ctr = Integer.parseInt(bekahText.getText());
            	
            	if(ctr >= 0)
            	{
            		ctr++;
                    bekahText.setText(ctr.toString());
            	}
            	else 
            	{
            		JOptionPane.showMessageDialog(frame, 
                            "Entry must be a positive number",
                            "Input Error", 
                            JOptionPane.ERROR_MESSAGE);
            	}
            	            	
            }
            catch (NumberFormatException nfe) 
            {
            	JOptionPane.showMessageDialog(frame, 
                        "Entry is not an integer number\n\n" + nfe.toString(),
                        "Input Error", 
                        JOptionPane.ERROR_MESSAGE);                
			}            
        }
    }
    
    //decrement Bekah's score, validate positive integer input
    private class bekahMinusListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Integer ctr = 0;
            
            try
            {
            	ctr = Integer.parseInt(bekahText.getText());
            	            	
                if(ctr>0)
                {
                	ctr--;
                    bekahText.setText(ctr.toString());
                }
                else
            	{
            		JOptionPane.showMessageDialog(frame, 
                            "Entry must be a positive number",
                            "Input Error", 
                            JOptionPane.ERROR_MESSAGE);
            	}
            }
            catch (NumberFormatException nfe) 
            {
            	JOptionPane.showMessageDialog(frame, 
                        "Entry is not an integer number\n\n" + nfe.toString(),
                        "Input Error", 
                        JOptionPane.ERROR_MESSAGE);                
			}            
        }
    }
    
    //increment Sam's score, validate positive integer input
    private class samPlusListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Integer ctr = 0;
            
            try
            {
            	ctr = Integer.parseInt(samText.getText());
            	
            	if(ctr >= 0)
            	{
            		ctr++;
                    samText.setText(ctr.toString());
            	}
            	else 
            	{
            		JOptionPane.showMessageDialog(frame, 
                            "Entry must be a positive number",
                            "Input Error", 
                            JOptionPane.ERROR_MESSAGE);
            	}
            }
            catch (NumberFormatException nfe) 
            {
            	JOptionPane.showMessageDialog(frame, 
                        "Entry is not an integer number\n\n" + nfe.toString(),
                        "Input Error", 
                        JOptionPane.ERROR_MESSAGE);                
			}            
        }
    }
    
    //decrement Sam's score, validate positive integer input
    private class samMinusListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Integer ctr = 0;
            
            try
            {
            	ctr = Integer.parseInt(samText.getText());
            	
            	if(ctr>0)
                {
                	ctr--;
                    samText.setText(ctr.toString());
                }
            	else 
            	{
            		JOptionPane.showMessageDialog(frame, 
                            "Entry must be a positive number",
                            "Input Error", 
                            JOptionPane.ERROR_MESSAGE);
            	}
            }
            catch (NumberFormatException nfe) 
            {
            	JOptionPane.showMessageDialog(frame, 
                        "Entry is not an integer number\n\n" + nfe.toString(),
                        "Input Error", 
                        JOptionPane.ERROR_MESSAGE);                
			}                        
        }
    }
    
    //exit form
    private class ExitMenuItemListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            System.exit(0);
        }
    }    
}
 
