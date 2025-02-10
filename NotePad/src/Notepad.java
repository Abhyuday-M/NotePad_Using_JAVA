import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Notepad extends JFrame implements ActionListener {
    JTextArea area;

    Notepad() {
        setTitle("Notes");

        // Icons
        ImageIcon notepadIcon = new ImageIcon(ClassLoader.getSystemResource("Icons/icons8-notepad-64.png"));
        Image icon = notepadIcon.getImage();
        setIconImage(icon);

        // Menubar
        JMenuBar menubar = new JMenuBar();
        menubar.setBackground(Color.GRAY);

        // File Menu
        JMenu file = new JMenu("File");
        file.setFont(new Font("AERIAL", Font.PLAIN, 15));

        JMenuItem newDocument = new JMenuItem("New");
        newDocument.addActionListener(this);
        newDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));

        JMenuItem openDocument = new JMenuItem("Open");
        openDocument.addActionListener(this);
        openDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));

        JMenuItem saveDocument = new JMenuItem("Save");
        saveDocument.addActionListener(this);
        saveDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

        JMenuItem printDocument = new JMenuItem("Print");
        printDocument.addActionListener(this);
        printDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));

        JMenuItem exitDocument = new JMenuItem("Exit");
        exitDocument.addActionListener(this);
        exitDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, ActionEvent.CTRL_MASK));

        file.add(newDocument);
        file.add(openDocument);
        file.add(saveDocument);
        file.add(printDocument);
        file.add(exitDocument);

        menubar.add(file);

        // Edit Menu
        JMenu edit = new JMenu("Edit");
        edit.setFont(new Font("AERIAL", Font.PLAIN, 15));

        JMenuItem undoButton = new JMenuItem("Undo");
        undoButton.addActionListener(this);
        undoButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));

        JMenuItem copy = new JMenuItem("Copy");
        copy.addActionListener(this);
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));

        JMenuItem cut = new JMenuItem("Cut");
        cut.addActionListener(this);
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

        JMenuItem paste = new JMenuItem("Paste");
        paste.addActionListener(this);
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));

        JMenuItem selectAll = new JMenuItem("Select All");
        selectAll.addActionListener(this);
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));

        JMenuItem delete = new JMenuItem("Delete");
        delete.addActionListener(this);
        delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, ActionEvent.CTRL_MASK));

        edit.add(undoButton);
        edit.add(copy);
        edit.add(cut);
        edit.add(paste);
        edit.add(selectAll);
        edit.add(delete);

        menubar.add(edit);

        // Help Menu
        JMenu help = new JMenu("Help");
        help.setFont(new Font("AERIAL", Font.PLAIN, 15));

        JMenuItem viewhelp = new JMenuItem("View help");
        viewhelp.addActionListener(this);
        viewhelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));

        JMenuItem sendFeedback = new JMenuItem("Send Feedback");
        sendFeedback.addActionListener(this);
        sendFeedback.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));

        JMenuItem aboutthisNote = new JMenuItem("About Notes");
        aboutthisNote.addActionListener(this);
        aboutthisNote.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));

        help.add(viewhelp);
        help.add(sendFeedback);
        help.add(aboutthisNote);

        menubar.add(help);

        setJMenuBar(menubar);

        area = new JTextArea();
        area.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        JScrollPane pane = new JScrollPane(area);
        pane.setBorder(BorderFactory.createEmptyBorder());
        add(pane);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();

        switch (command) {
            case "New":
                area.setText("");
                break;
            case "Open":
                openFile();
                break;
            case "Save":
                saveFile();
                break;
            case "Print":
                printDocument();
                break;
            case "Exit":
                System.exit(0);
                break;
            case "Undo":
                area.setText("");
                break;
            case "Copy":
                area.copy();
                break;
            case "Cut":
                area.cut();
                break;
            case "Paste":
                area.paste();
                break;
            case "Select All":
                area.selectAll();
                break;
            case "Delete":
                area.replaceSelection("");
                break;
            case "View help":
                JOptionPane.showMessageDialog(this, "Notepad Help", "Help", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "Send Feedback":
                JOptionPane.showMessageDialog(this, "Send feedback to developer", "Feedback", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "About Notes":
                JOptionPane.showMessageDialog(this, "Simple Notepad Application", "About", JOptionPane.INFORMATION_MESSAGE);
                break;
        }
    }

    private void openFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt");
        chooser.addChoosableFileFilter(restrict);

        int action = chooser.showOpenDialog(this);
        if (action == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                area.read(reader, null);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error reading file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFile() {
        JFileChooser saveAs = new JFileChooser();
        saveAs.setApproveButtonText("Save");

        int action = saveAs.showSaveDialog(this);
        if (action == JFileChooser.APPROVE_OPTION) {
            File fileName = new File(saveAs.getSelectedFile() + ".txt");
            try (BufferedWriter outFile = new BufferedWriter(new FileWriter(fileName))) {
                area.write(outFile);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void printDocument() {
        try {
            area.print();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Printing failed", "Print Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Notepad());
    }
}