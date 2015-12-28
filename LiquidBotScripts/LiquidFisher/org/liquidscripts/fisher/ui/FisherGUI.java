package org.liquidscripts.fisher.ui;

import org.liquidscripts.fisher.Storage;
import org.liquidscripts.fisher.wrapper.Fish;
import org.liquidscripts.fisher.wrapper.Location;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
/*
 * Created by JFormDesigner on Sun May 04 19:33:35 AST 2014
 */



/**
 * @author Magorium ShadowBot
 */
public class FisherGUI extends JFrame {
    public FisherGUI() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Magorium ShadowBot
		Title = new JLabel();
		tabbedPane = new JTabbedPane();
		MainPanel = new JPanel();
		LocationLabel = new JLabel();
		LocationBox = new JComboBox<String>();
		FishBox = new JComboBox<String>();
		FishLabel = new JLabel();
		ModeBox = new JComboBox<String>();
		ModeLabel = new JLabel();
		scrollPane = new JScrollPane();
		textPane = new JTextPane();
		StartButton = new JButton();

		//======== this ========
		setTitle("LiquidFisher");
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		//---- Title ----
		Title.setText("LiquidFisher");
		Title.setHorizontalAlignment(SwingConstants.CENTER);
		Title.setForeground(new Color(3, 5, 148));
		Title.setFont(new Font("28 Days Later", Font.PLAIN, 36));
		Title.setIcon(new ImageIcon("C:\\Users\\Magorium\\PhpstormProjects\\ShadowBot\\repository\\img\\icons\\8.png"));
		Title.setBorder(LineBorder.createBlackLineBorder());
		contentPane.add(Title);
		Title.setBounds(0, 0, 383, 90);

		//======== tabbedPane ========
		{

			//======== MainPanel ========
			{

				MainPanel.setLayout(null);

				//---- LocationLabel ----
				LocationLabel.setText("Location:");
				LocationLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
				MainPanel.add(LocationLabel);
				LocationLabel.setBounds(5, 220, 65, 20);

				//---- LocationBox ----
				LocationBox.setModel(new DefaultComboBoxModel(locations()));
                LocationBox.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        FishBox.setModel(new DefaultComboBoxModel(fishes(getFishSpot())));
                    }
                });
				MainPanel.add(LocationBox);
				LocationBox.setBounds(70, 220, 305, LocationBox.getPreferredSize().height);

				//---- FishBox ----
                FishBox.setModel(new DefaultComboBoxModel(fishes(getFishSpot())));
				MainPanel.add(FishBox);
				FishBox.setBounds(70, 250, 305, 20);

				//---- FishLabel ----
				FishLabel.setText("Fish:");
				FishLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
				MainPanel.add(FishLabel);
				FishLabel.setBounds(5, 250, 65, 20);

				//---- ModeBox ----
				ModeBox.setModel(new DefaultComboBoxModel<String>(new String[] {
					"Banking",
					"Powerfish"
				}));
				MainPanel.add(ModeBox);
				ModeBox.setBounds(70, 280, 305, 20);

				//---- ModeLabel ----
				ModeLabel.setText("Mode:");
				ModeLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
				MainPanel.add(ModeLabel);
				ModeLabel.setBounds(5, 280, 65, 20);

				//======== scrollPane ========
				{

					//---- textPane ----
                    textPane.setEditable(false);
					textPane.setText("Welcome to LiquidFisher , Have you ever want 99 Fishing? Rs take alot of your time ? This script will get you 99 Fishing , All you have to do is to Fill Options in this GUI \n\nThank you for Using LiquidScripts \nEnjoy.");
					scrollPane.setViewportView(textPane);
				}
				MainPanel.add(scrollPane);
				scrollPane.setBounds(5, 5, 370, 210);

				{ // compute preferred size
					Dimension preferredSize = new Dimension();
					for(int i = 0; i < MainPanel.getComponentCount(); i++) {
						Rectangle bounds = MainPanel.getComponent(i).getBounds();
						preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
						preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
					}
					Insets insets = MainPanel.getInsets();
					preferredSize.width += insets.right;
					preferredSize.height += insets.bottom;
					MainPanel.setMinimumSize(preferredSize);
					MainPanel.setPreferredSize(preferredSize);
				}
			}
			tabbedPane.addTab("Main", MainPanel);
		}
		contentPane.add(tabbedPane);
		tabbedPane.setBounds(0, 90, 385, 335);

		//---- StartButton ----
		StartButton.setText("Start");
		StartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                if (ModeBox.getSelectedItem().toString().equalsIgnoreCase("powerfish")) {
                    Storage.chosenMode = Storage.MODE.POWERFISH;
                } else {
                    Storage.chosenMode = Storage.MODE.BANKING;
                }
                String fish = FishBox.getSelectedItem().toString();
                for (Fish f : Storage.FISHES) {
                    if (f.getName().equalsIgnoreCase(fish)) {
                        Storage.fishToCaught = new Fish[]{f};
                        break;
                    }
                }
                Storage.chosenLocation = getFishSpot();

                System.out.println("Current Location " + Storage.chosenLocation.getName());
                System.out.println("Current Location " + Storage.fishToCaught[0].getName());

                Storage.started = true;
                dispose();
			}
		});
		contentPane.add(StartButton);
		StartButton.setBounds(0, 425, 385, 35);

		{ // compute preferred size
			Dimension preferredSize = new Dimension();
			for(int i = 0; i < contentPane.getComponentCount(); i++) {
				Rectangle bounds = contentPane.getComponent(i).getBounds();
				preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
				preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
			}
			Insets insets = contentPane.getInsets();
			preferredSize.width += insets.right;
			preferredSize.height += insets.bottom;
			contentPane.setMinimumSize(preferredSize);
			contentPane.setPreferredSize(preferredSize);
		}
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Magorium ShadowBot
	private JLabel Title;
	private JTabbedPane tabbedPane;
	private JPanel MainPanel;
	private JLabel LocationLabel;
	private JComboBox<String> LocationBox;
	private JComboBox<String> FishBox;
	private JLabel FishLabel;
	private JComboBox<String> ModeBox;
	private JLabel ModeLabel;
	private JScrollPane scrollPane;
	private JTextPane textPane;
	private JButton StartButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables


    private String[] locations() {
        ArrayList<String> list = new ArrayList<String>();
        for (Location l : Storage.LOCATIONS) {
            list.add(l.getName());
        }
        return list.toArray(new String[list.size()]);
    }

    private String[] fishes(Location location) {
        ArrayList<String> list = new ArrayList<String>();
        for (Fish l : location.getFishes()) {
            list.add(l.getName());
        }
        return list.toArray(new String[list.size()]);
    }

    private Location getFishSpot() {
        String location = LocationBox.getSelectedItem().toString();
        for (Location f : Storage.LOCATIONS) {
            if (f.getName().equalsIgnoreCase(location))
                return f;

        }
        return null;
    }
}
