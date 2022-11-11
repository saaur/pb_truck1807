package pb_truck;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollBar;

public class Edit extends JFrame {

	private JPanel contentPane;
	private static ArrayList<String> listItems;
	private static Connection connect;

	/**
	 * Launch the application.
	 */
	public static void main(Connection connection, ArrayList<String> list) {
		listItems = list;
		connect = connection;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Edit frame = new Edit();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Edit() {
		setBounds(100, 100, 651, 461);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel title = new JLabel("Edit Menu");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(248, 11, 122, 16);
		contentPane.add(title);
		
		JComboBox list = new JComboBox();
		list.setMaximumRowCount(16);
		list.setModel(new DefaultComboBoxModel<String>(listItems.toArray(new String[0])));
		list.setBounds(20, 42, 308, 27);
		contentPane.add(list);
		
		JComboBox quantity = new JComboBox();
		quantity.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4"}));
		quantity.setToolTipText("");
		quantity.setBounds(437, 42, 61, 27);
		contentPane.add(quantity);
		JLabel selectTitle = new JLabel("Select Item");
		selectTitle.setBounds(20, 26, 77, 16);
		contentPane.add(selectTitle);
		
		JLabel quantityTitle = new JLabel("Quantity");
		quantityTitle.setBounds(437, 26, 61, 16);
		contentPane.add(quantityTitle);
		
		JButton confirmButton = new JButton("OK");
		confirmButton.setBounds(510, 41, 117, 29);
		contentPane.add(confirmButton);
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					connect.createStatement().executeUpdate("UPDATE data SET QUANTITY =" 
							+ quantity.getSelectedItem() + " WHERE ITEM_NAME ='" + list.getSelectedItem() + "'");
					System.out.println("Your truck order has been updated to include " 
							+ quantity.getSelectedItem() + " weeks worth of " + list.getSelectedItem() + "\n");
				} catch (SQLException e1) { }
			}
		});
	}
}
