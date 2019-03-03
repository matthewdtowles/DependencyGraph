package dependencygraph;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import static javax.swing.BorderFactory.createTitledBorder;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;


/**
 * GUI
 * 
 * This is the GUI class for the Dependency Graph
 * 
 * The application builds a dependency graph from
 * an input file with class dependency info
 * Each line of file has ClassName and class names
 * that depend on it
 * If no classes depend on a class, then it should
 * not exist on a line by itself
 * Each line represent a vertex and its adjacency list
 * e.g.:
 *   File format (input):
 *      ClassA ClassC ClassE
 *      ClassB ClassD ClassG
 *      ClassE ClassB ClassF ClassH
 *      ClassI ClassC
 *   If 'ClassA' chosen to recompile
 *   Recompilation order (output):
 *      ClassA ClassE ClassH ClassF ClassB ClassG ClassD ClassC
 * 
 * @author matthew.towles
 * @date Mar 1, 2019
 */
public class GUI extends JFrame {

    private JPanel topPanel;
    private JLabel fileLabel;
    private JTextField fileTextField;
    private JButton buildButton;
    private JLabel classLabel;
    private JTextField classTextField;
    private JButton orderButton;
    private JPanel bottomPanel;
    private JScrollPane bottomScroll;   
    private JTextArea outputTextArea;
    private static final String APP_TITLE = "Class Dependency Graph";
    private DirectedGraph graph;
    
    
    /**
     * Constructor
     */
    public GUI() {
        super(APP_TITLE);
        init();
    }
    
    
    /**
     * Initialize the GUI
     */
    private void init(){
        topPanel = new JPanel();
        fileLabel = new JLabel("Input file name:");
        fileTextField = new JTextField();
        buildButton = new JButton("Build Directed Graph");
        classLabel = new JLabel("Class to recompile:");
        classTextField = new JTextField();
        orderButton = new JButton("Topological Order");
        bottomPanel = new JPanel();
        bottomScroll = new JScrollPane();
        outputTextArea = new JTextArea();
        graph = null;
        
        // top panel layout
        topPanel.setBorder(createTitledBorder(""));
        GroupLayout topPanelLayout = new GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(
                topPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(
                    topPanelLayout.createParallelGroup(
                        GroupLayout.Alignment.LEADING
                    )
                    .addComponent(classLabel)
                    .addComponent(fileLabel))
                .addGap(12, 12, 12)
                .addGroup(
                    topPanelLayout.createParallelGroup(
                        GroupLayout.Alignment.LEADING
                    )
                    .addComponent(
                        classTextField, 
                        GroupLayout.DEFAULT_SIZE, 
                        201, 
                        Short.MAX_VALUE
                    )
                    .addComponent(fileTextField)
                )
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(
                    topPanelLayout.createParallelGroup(
                        GroupLayout.Alignment.LEADING
                    )
                    .addComponent(buildButton, GroupLayout.Alignment.TRAILING)
                    .addComponent(orderButton, GroupLayout.Alignment.TRAILING)
                )
                .addContainerGap()
            )
        );
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(
                topPanelLayout.createSequentialGroup()
                .addGroup(
                    topPanelLayout.createParallelGroup(
                        GroupLayout.Alignment.BASELINE
                    )
                    .addComponent(buildButton)
                    .addComponent(
                        fileTextField, 
                        GroupLayout.PREFERRED_SIZE, 
                        GroupLayout.DEFAULT_SIZE, 
                        GroupLayout.PREFERRED_SIZE
                    )
                    .addComponent(fileLabel)
                )
                .addGap(18, 18, 18)
                .addGroup(
                    topPanelLayout.createParallelGroup(
                        GroupLayout.Alignment.BASELINE
                    )
                    .addComponent(orderButton)
                    .addComponent(
                        classTextField, 
                        GroupLayout.PREFERRED_SIZE, 
                        GroupLayout.DEFAULT_SIZE, 
                        GroupLayout.PREFERRED_SIZE
                    )
                    .addComponent(classLabel)
                )
            )
        );
        
        // bottom panel layout:
        bottomPanel.setBorder(createTitledBorder("Recompilation Order"));
        GroupLayout bottomPanelLayout = new GroupLayout(bottomPanel);
        bottomPanel.setLayout(bottomPanelLayout);
        bottomPanelLayout.setHorizontalGroup(
            bottomPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(bottomScroll)
        );
        bottomPanelLayout.setVerticalGroup(
            bottomPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(
                bottomScroll, 
                GroupLayout.DEFAULT_SIZE, 
                142, 
                Short.MAX_VALUE
            )
        );
        
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(
                layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(
                        topPanel, 
                        GroupLayout.DEFAULT_SIZE, 
                        GroupLayout.DEFAULT_SIZE, 
                        Short.MAX_VALUE
                    )
                    .addComponent(
                        bottomPanel, 
                        GroupLayout.Alignment.TRAILING, 
                        GroupLayout.DEFAULT_SIZE, 
                        GroupLayout.DEFAULT_SIZE, 
                        Short.MAX_VALUE
                    )
                )
                .addContainerGap()
            )
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(
                layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(
                    topPanel, 
                    GroupLayout.PREFERRED_SIZE, 
                    GroupLayout.DEFAULT_SIZE, 
                    GroupLayout.PREFERRED_SIZE
                )
                .addPreferredGap(
                    LayoutStyle.ComponentPlacement.UNRELATED
                )
                .addComponent(
                    bottomPanel, 
                    GroupLayout.DEFAULT_SIZE, 
                    GroupLayout.DEFAULT_SIZE, 
                    Short.MAX_VALUE
                )
                .addContainerGap()
            )
        );

        outputTextArea.setEditable(false);
        outputTextArea.setColumns(20);
        outputTextArea.setRows(5);
        bottomScroll.setViewportView(outputTextArea);
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        buildButton.addActionListener(this::buildButtonActionPerformed);

        orderButton.addActionListener(this::orderButtonActionPerformed);
        
        setVisible(true);
        
        pack();
    }
    
    /**
     * Builds a DirectedGraph
     * Takes user input as a file name
     * File should contain class dependency info
     * Reads this info and creates a DirectedGraph
     * from those dependencies
     * 
     * Generates JOptionPane with a success/error message
     * 
     * @param evt 
     */
    private void buildButtonActionPerformed(ActionEvent evt) {
        // not sure if try/catch is way to go yet
        // at least temporary placeholder
        try {
            graph = new DirectedGraph(new File(fileTextField.getText()));

            // if graph is built successfully
            JOptionPane.showMessageDialog(
                bottomPanel, 
                "Graph built successfully"
            );
        } catch (HeadlessException | FileNotFoundException e) {
            // if graph not built successfully
            JOptionPane.showMessageDialog(
                bottomPanel,
                "File did not open"
            );
        }
    }                                           

    
    /**
     * Prints out a list of classes that need to be
     * recompiled if a valid class name is given
     * and if a directed graph has been created
     * 
     * This button cannot work if the first button has not
     * already executed successfully
     * 
     * @param evt 
     */
    private void orderButtonActionPerformed(ActionEvent evt) {
        // only run if a DirectedGraph has been built
        try {
            graph.topologicalSort(classTextField.getText());
            String recompOrder = graph.getRecompilationOrder();
            outputTextArea.setText(recompOrder);
        } catch (CycleException e) {
            JOptionPane.showMessageDialog(
                    bottomPanel, 
                    e.getMessage(), 
                    e.toString(), 
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (InvalidClassException e) {
            JOptionPane.showMessageDialog(
                    bottomPanel, 
                    e.getMessage(), 
                    e.toString(), 
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    bottomPanel, 
                    "No graph to sort.", 
                    "Topological Ordering Failed", 
                    JOptionPane.ERROR_MESSAGE
            );
        }
        
        // if an invalid class name given, InvalidClassException thrown
    }      
    
    
    /**
     * Main method
     * @param args 
     */
    public static void main(String args[]) {
        new GUI();
    }
}
