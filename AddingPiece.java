
//@ARANESH
import java.awt.Color;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Toolkit;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import javax.sound.sampled.Clip;

//import com.sun.media.sound.Toolkit;

import java.awt.Graphics;

import javax.swing.SwingConstants;

public class AddingPiece extends JFrame implements ActionListener, MouseListener {

        private JLabel backimage = new JLabel();

        private JLabel logo;

        private JLabel EverydayOutfits = new JLabel("Everday Outifts", SwingConstants.CENTER);
        public JPanel greyback = new JPanel();

        private JLabel Image;
        private JTextField filename = new JTextField("FileName");
        private JButton addImag = new JButton("Add Image");
        private JButton delete = new JButton("delete");
        private JButton add = new JButton("Add");

        private String filepath = null;
        private String fileName = null;
        private String clothe = null;

        private boolean visi = false;

        private JLabel error = new JLabel("Error", SwingConstants.CENTER);

        public File file;

        File theDir = new File("new folder");

        public File Images = null;

        private JButton bottoms = new JButton("Bottoms");
        private JButton shoes = new JButton("Shoes");
        private JButton hoodies = new JButton("Hoodies");
        private JButton shirts = new JButton("Shirts");
        private JButton jackets = new JButton("Jackets");

        private boolean addchecker = false;
        private boolean skip = true;

        private String username;

        private JButton returnHome = new JButton("Return");

        public String imgDir;

        public FileWriter change = null;

        private String text;

        public AddingPiece() {

                setSize(1100, 700);
                setLayout(null);
                setVisible(true);
                setDefaultCloseOperation(EXIT_ON_CLOSE);

                // Centers the JFrame
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                int w = this.getSize().width;
                int h = this.getSize().height;
                int x = (dim.width - w) / 2;
                int y = (dim.height - h) / 2;

                this.setLocation(x, y);
                getContentPane().setBackground(Color.decode("#4484CE"));

                // Pannel setup
                greyback.setVisible(true);
                greyback.setLocation(20, 190);
                greyback.setSize(1045, 455);
                greyback.setLayout(null);
                greyback.setBackground(Color.decode("#d9d9d9"));

                add(greyback);

                setupcomponents();
                setupB();
                errorPop();
                returnHomeButton();
                // Test.getName();

        }

        // Set up for error pop up
        private void errorPop() {

                error.setBounds(420, 150, 200, 100);
                error.setVisible(false);
                error.setForeground(Color.red);
                error.setFont(error.getFont().deriveFont(25.0f));
                greyback.add(error);

        }

        // Set up for return Home button
        private void returnHomeButton() {

                returnHome.setBounds(850, 150, 100, 100);
                returnHome.setVisible(true);
                returnHome.setBackground(Color.decode("#F19F4D"));
                returnHome.addActionListener(this);
                greyback.add(returnHome);

        }

        // Set up for delete Button
        private void setUpDeleteB(boolean visi) {

                delete.setBounds(100, 200, 100, 100);
                delete.setVisible(visi);
                delete.setBackground(Color.decode("#F19F4D"));
                delete.addActionListener(this);
                greyback.add(delete);

        }

        // Adds the buttons on to the JFrame
        private void setupB() {

                int xval = 310;
                int yval = 350;

                bottoms.setBounds(xval, yval, 100, 20);
                bottoms.setBackground(Color.decode("#F19F4D"));
                bottoms.addActionListener(this);
                bottoms.setVisible(true);
                greyback.add(bottoms);

                shoes.setBounds(xval + 110, yval, 100, 20);
                shoes.setBackground(Color.decode("#F19F4D"));
                shoes.addActionListener(this);
                shoes.setVisible(true);
                greyback.add(shoes);

                hoodies.setBounds(xval + 220, yval, 100, 20);
                hoodies.setBackground(Color.decode("#F19F4D"));
                hoodies.addActionListener(this);
                hoodies.setVisible(true);
                greyback.add(hoodies);

                shirts.setBounds(xval + 330, yval, 100, 20);
                shirts.setBackground(Color.decode("#F19F4D"));
                shirts.addActionListener(this);
                shirts.setVisible(true);
                greyback.add(shirts);

                jackets.setBounds(xval + 110, yval + 45, 200, 20);
                jackets.setBackground(Color.decode("#F19F4D"));
                jackets.addActionListener(this);
                jackets.setVisible(true);
                greyback.add(jackets);

                repaint();
        }

        private void callImag(String clothe, String text) {

                final JFileChooser fc = new JFileChooser();
                int returnval = fc.showOpenDialog(fc);

                // https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
                if (returnval == JFileChooser.APPROVE_OPTION) { // Pop up that allows
                                                                                                                // the user to access
                                                                                                                // their files

                        fileName = fc.getSelectedFile().getName(); // Gets the name of the
                                                                                                                // file
                        filepath = fc.getSelectedFile().getAbsolutePath(); // Gets the path
                                                                                                                                // of where the
                                                                                                                                // file was
                        // setFilepath(filepath);

                } else {

                        error.setVisible(true);
                }

                uploadImage(filepath, fileName, clothe, text);
        }

        private void uploadImage(String filepath, String fileName, String clothe, String text) {

                String img = filepath;
                String dir = "Image//" + clothe + "//" + fileName + "";
                imgDir = img;
                setImgDir(imgDir);

                // Adds the image onto the JFrame
                Image = new JLabel();
                Image.setIcon(new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(300, 300, 0)));
                Image.setVisible(true);
                Image.setBounds(430, 100, 200, 200);
                greyback.add(Image);

                repaint();

        }

        // Method that adds the buuton
        private void addButton(boolean visi) {

                add.setBounds(100, 100, 100, 100);
                add.setVisible(visi);
                add.addActionListener(this);
                add.setBackground(Color.decode("#F19F4D"));
                greyback.add(add);

        }

        private void changepath(String filepath, String clothe) {

                // Checks if the file is a gif
                if (fileName.charAt(fileName.length() - 1) == 'f') {

                        error.setVisible(true);
                        error.setText("Can not Add gif");

                }

                else {

                        // Directory of the image on the java project
                        String dir = "files/" + Test.getName() + "/" + clothe + "/" + fileName;

                        Path orignialpath = Paths.get(filepath);

                        // File from the computer
                        File orig = new File(filepath);

                        // Saves the file onto the project
                        orig.renameTo(new File("files\\" + Test.getName() + "\\" + clothe + "\\" + fileName));
                        File f = new File("files/" + Test.getName() + "/" + text);
                        if (!f.exists()) {
                                skip = false;
                        }

                        // https://stackoverflow.com/questions/14503595/write-file-using-bufferedwriter-in-java
                        try (FileWriter fw = new FileWriter("files/" + Test.getName() + "/" + text, true);

                                        BufferedWriter bw = new BufferedWriter(fw); // Writes the
                                                                                                                                // text to the
                                                                                                                                // text file
                                                                                                                                // that is
                                                                                                                                // created by
                                                                                                                                // the file
                                                                                                                                // writer
                                        PrintWriter out = new PrintWriter(bw)) // Printwrriter
                                                                                                                        // allows the text
                                                                                                                        // to be printed to
                                                                                                                        // the nextline
                                                                                                                        // and for formating
                                                                                                                        // purposes
                        {
                                if (skip) {
                                        out.print("\n");
                                        
                                }
                                out.print(dir); // Writes the line
                                

                        } catch (IOException e) {

                                System.out.println("No");
                                error.setVisible(true);
                        }

                }

        }

        private void setupcomponents() {

                // Setup for logo
                logo = new JLabel();
                add(logo);
                logo.setVisible(true);
                logo.setBounds(500, 0, 105, 105);
                add(logo);
                logo.setIcon(new ImageIcon(new ImageIcon("images/logo.png").getImage().getScaledInstance(85, 85, 0)));

                // Setup for everyday outfits title
                EverydayOutfits.setVisible(true);
                add(EverydayOutfits);
                EverydayOutfits.setBounds(389, 100, 300, 50);
                EverydayOutfits.setFont(new Font("Arial", Font.BOLD, 54));
                EverydayOutfits.addMouseListener(this);
                EverydayOutfits.setBounds(315, 100, 500, 75);
                EverydayOutfits.setForeground(Color.decode("#f9cf00"));

                /*
                 * 
                 */

                repaint();

        }

        public void actionPerformed(ActionEvent event) {

                // Adds a clothe image depending on the user

                if (event.getSource() == delete) {

                        Image.setIcon(new ImageIcon());
                        visi = false;
                        setUpDeleteB(visi);
                        addButton(visi);
                        error.setVisible(false);

                }

                // Adds a clothe image depending on the user

                if (event.getSource() == bottoms) {

                        clothe = "Bottoms";
                        text = "BottomsText";
                        callImag(clothe, text);

                        visi = true;
                        setUpDeleteB(visi);
                        addButton(visi);
                        error.setVisible(false);

                }

                // Adds a clothe image depending on the user

                if (event.getSource() == shoes) {

                        clothe = "Shoes";
                        text = "ShoesText";
                        callImag(clothe, text);

                        visi = true;
                        setUpDeleteB(visi);
                        addButton(visi);
                }

                // Adds a clothe image depending on the user

                if (event.getSource() == hoodies) {

                        clothe = "Hoodies";
                        text = "HoodiesText";
                        callImag(clothe, text);

                        visi = true;
                        setUpDeleteB(visi);
                        addButton(visi);

                        error.setVisible(false);

                }

                // Adds a clothe image depending on the user

                if (event.getSource() == shirts) {

                        clothe = "Shirts";
                        text = "ShirtsText";
                        callImag(clothe, text);

                        visi = true;
                        setUpDeleteB(visi);
                        addButton(visi);

                        error.setVisible(false);

                }

                // Adds a clothe image depending on the user
                if (event.getSource() == jackets) {

                        clothe = "Jackets";
                        text = "JacketsText";
                        callImag(clothe, text);

                        visi = true;
                        setUpDeleteB(visi);
                        addButton(visi);

                        error.setVisible(false);

                }

                // When button is clicked adds the image onto the users folder and text
                // file
                // depending on what clothe they chosed
                if (event.getSource() == add) {

                        String file;
                        file = new String(filepath);

                        if (file.equals(null)) {

                                error.setVisible(false);

                        }

                        changepath(file, clothe);

                        Image.setIcon(new ImageIcon());

                        visi = false;
                        setUpDeleteB(visi);
                        addButton(visi);

                }

                if (event.getSource() == returnHome) {

                        home();
                }
        }

        // Returns home
        private void home() {

                this.setVisible(false);
                // New
        }

        public String getImgDir() {
                return imgDir;
        }

        public void setImgDir(String imgDir) {
                this.imgDir = imgDir;
        }

        @Override
        public void mouseClicked(MouseEvent event) {

                if (event.getSource() == logo) {

                        home();
                }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

        }

        @Override
        public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

        }

        @Override
        public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

        }

        @Override
        public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

        }
}
