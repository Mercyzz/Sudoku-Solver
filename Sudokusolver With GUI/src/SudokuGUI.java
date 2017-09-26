import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class SudokuGUI extends Solver {

	private JFrame SudokuGUI;
	private JPanel ButtonsPanel;
	private JPanel SudokuPanel;
	private JButton SolveButton;
	private JButton ClearButton;
	private JButton OpenButton;
	private JButton SaveButton;
	private JButton ExitButton;
	private JButton CreditsButton;
	private JTextField[][] textField = new JTextField[9][9];
	private JPanel panel[][] = new JPanel[3][3];

	public SudokuGUI() {

		PanelsAndFrame();
		SudokuBoard();
		Buttons();
		ButtonsPlacement();
		fillSudokuPanel(field.getModel());

	}

	public static void main(String[] args) {
		new SudokuGUI();
	}

	// PANELS AND FRAME
	private void PanelsAndFrame() {
		SudokuGUI = new JFrame("Sudoku Solver");
    
		SudokuGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ButtonsPanel = new JPanel(new GridBagLayout());
		ButtonsPanel.setBackground(new Color(57, 135, 232));
		SudokuPanel = new JPanel(new GridBagLayout());
		SudokuPanel.setBackground(new Color(57, 135, 232));
		SudokuPanel.setLayout(new GridLayout(3, 3, 5, 5));

		SudokuGUI.add(ButtonsPanel, BorderLayout.EAST);
		SudokuGUI.add(SudokuPanel, BorderLayout.CENTER);
        
        SudokuGUI.setSize(600, 600);
	}

	// CREATE SUDOKU BOARD
	
	private void SudokuBoard() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				textField[i][j] = new JTextField(1);
				Font font = new Font("Arial", Font.PLAIN, 30);
				textField[i][j].setFont(font);
				textField[i][j].setHorizontalAlignment(JTextField.CENTER);
				textField[i][j].setEnabled(false);
			}
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				panel[i][j] = new JPanel(new GridLayout(3, 3));

			}
		}
		for (int x = 0; x < 3; x++) {
            for (int a = 0; a < 3; a++) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        panel[x][a].add(textField[i + x * 3][j + a * 3]);
                    }
                }
				SudokuPanel.add(panel[x][a]);
			}
		}
	}

	// FILL SUDOKU METHODE
	private void fillSudokuPanel(int[][] model) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				textField[i][j].setText(String.valueOf(model[i][j]));
			}
		}
	}

	// RELOAD SUDOK FIELD METHODE
	private void reloadSudoku() {
		 fillSudokuPanel(field.getModel());
	}

	// CLEAR SUDOKU METHODE
	private void ClearSudoku() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				textField[i][j].setText(" ");
			}
		}
	}

	// SAVE SUDOKU BOARD
	private void save() throws IOException {
		JFileChooser save = new JFileChooser();
		int trytosave = save.showDialog(SudokuGUI, "Save");

		BufferedWriter saveit = null;
		File file1;
		if (trytosave != JFileChooser.APPROVE_OPTION) {
			return;
		}
		file1 = save.getSelectedFile();

		saveit = new BufferedWriter(new FileWriter(file1));
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				saveit.write(textField[i][j].getText());
			}
			saveit.write("\n");
		}
		saveit.close();
		JOptionPane.showMessageDialog(null, "File Saved Successfully !");
	}

	// OPEN SUDOKU BOARD
	private void OpenSudoku() throws IOException {
		JFileChooser open = new JFileChooser();

		FileNameExtensionFilter docFilter = new FileNameExtensionFilter("Text File", "txt");
		open.setFileFilter(docFilter);
		int trytoopen = open.showDialog(SudokuGUI, "Open");
		if (trytoopen == JFileChooser.APPROVE_OPTION) {
			
			File selectedFile1 = open.getSelectedFile();
			ModelForFile(selectedFile1); // create new field in solver class
		}
		if (trytoopen == JFileChooser.CANCEL_OPTION) {
			   return;
			   }
		reloadSudoku(); // Reload sudoku with new input from file ( no necessary to open window again to open new file) 
		JOptionPane.showMessageDialog(null, "File Opened Successfully !");
	}

	private void Buttons() {
		SolveButton();
		ClearButton();
		OpenButton();
		SaveButton();
		ExitButton();
		CreditsButton();
	}

	// CREAT BUTTONS AND ACTIONS

	// SOLVE BUTTON
	private void SolveButton() {
		SolveButton = new JButton("Solve");
		SolveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sudokusolve();
				fillSudokuPanel(field.getModel());
			}

		});

	}
				
	// CLEAR BUTTON
	private void ClearButton() {
		ClearButton = new JButton("Clear");
		ClearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ClearSudoku();

			}
		});
	}

	// OPEN BUTTON
	private void OpenButton() {
		OpenButton = new JButton("Open");
		OpenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Only files with X's as 0 and numbers are available");
				try {
					OpenSudoku();
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null, "Sorry, could not open the file !");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Sorry, could not open the file !");
					e1.printStackTrace();
				}

			}

		});
	}

	// SAVE BUTTON
	private void SaveButton() {
		SaveButton = new JButton("Save");
		SaveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Save file as .txt :-)");
				try {
					save();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Sorry, could not save the file !");
					e1.printStackTrace();
				}

			}

		});
	}

	// EXIT BUTTON
	private void ExitButton() {
		ExitButton = new JButton("Exit");
		ExitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SudokuGUI.dispose();
			}

		});
	}

	// CREDITS BUTTON
	private void CreditsButton(){
		CreditsButton = new JButton("Credits");
		CreditsButton.addActionListener(new ActionListener(){
			
		
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Sudoku Solver created and designed by Karol Zdunek");
			}
			});
			
	}
	// BUTTONS PLACEMENT
	private void ButtonsPlacement() {
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10);

		c.gridx = 0;
		c.gridy = 1;
		ButtonsPanel.add(SolveButton, c);

		c.gridx = 0;
		c.gridy = 2;
		ButtonsPanel.add(ClearButton, c);

		c.gridx = 0;
		c.gridy = 3;
		ButtonsPanel.add(OpenButton, c);

		c.gridx = 0;
		c.gridy = 4;
		ButtonsPanel.add(SaveButton, c);

		c.gridx = 0;
		c.gridy = 5;
		ButtonsPanel.add(ExitButton, c);
		
		c.gridx = 0;
		c.gridy= 9;
		ButtonsPanel.add(CreditsButton, c);
		
	}

}
