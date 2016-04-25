/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notepad;

import javax.swing.*; //jframe design
import java.awt.*; //more gui
import java.awt.event.*; //event handeling
import java.util.Scanner; //read from file
import java.io.*; //write to file

/**
 *
 * @author f
 */
public class Notepad extends JFrame implements ActionListener {
    //globals
    private TextArea ta = new TextArea("erase me",0,0,TextArea.SCROLLBARS_VERTICAL_ONLY); //writing area
    private MenuBar mb = new MenuBar(); //menubar to contain menu items
    private Menu file = new Menu(); //file menu item
    private MenuItem open = new MenuItem(); //open existing file
    private MenuItem save = new MenuItem(); //save file
    private MenuItem close = new MenuItem(); //close file
    
    public Notepad(){
        this.setSize(500, 500); //initial window size
        this.setTitle("Basic Notepad");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.ta.setFont(new Font("Consolas", Font.BOLD, 12)); //set default font for text area
        //makes the textarea fill content pane automatically
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(ta);
        //adds menu bar into gui
        this.setMenuBar(this.mb);
        this.mb.add(file);
        
        this.file.setLabel("File");
        
        this.open.setLabel("Open");
        this.open.addActionListener(this); //tells us when its being clicked
        this.open.setShortcut(new MenuShortcut(KeyEvent.VK_O, false)); //add keyboard shortcut
        this.file.add(this.open); //add to file menu
        
        this.save.setLabel("Save");
        this.save.addActionListener(this);
        this.save.setShortcut(new MenuShortcut(KeyEvent.VK_S, false));
        this.file.add(this.save);
        
        this.close.setLabel("Close");
        this.close.addActionListener(this);
        this.close.setShortcut(new MenuShortcut(KeyEvent.VK_F4, false));
        this.file.add(close);
    }
    
    public void actionPerformed(ActionEvent ev) {
        // if close option clicked
        if(ev.getSource() == this.close){
            this.dispose(); //close app
        // if open option clicked
        }else if(ev.getSource() == this.open){
            JFileChooser openFile = new JFileChooser(); //open file chooser
            int choice = openFile.showOpenDialog(this); //either approve or cancel
            
            switch(choice){
                case JFileChooser.APPROVE_OPTION: //if approve
                    this.ta.setText(""); //clear text field
                    try{ //scan file in line by line , appending each line to textarea
                        Scanner in = new Scanner(new FileReader(openFile.getSelectedFile().getPath()));
                        while(in.hasNext()){
                            this.ta.append(in.nextLine() + "\n");
                        }
                    }catch (Exception e){ //catch exception and post debug console
                        System.out.println(e.getMessage());
                    }
            }
        // if save option is clicked
        }else if(ev.getSource() == this.save){
            JFileChooser saveFile = new JFileChooser();
            int choice = saveFile.showSaveDialog(this); //now its show save dialog (because were saving)
            
            switch(choice){
                case JFileChooser.APPROVE_OPTION:
                    try{//buffered writer writes to a file
                        BufferedWriter bw = new BufferedWriter(new FileWriter(saveFile.getSelectedFile().getPath()));
                        bw.write(this.ta.getText()); //write whats in text area
                        bw.close(); //close 
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
            }
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Notepad np = new Notepad();
        np.setVisible(true);
    }
    
}
