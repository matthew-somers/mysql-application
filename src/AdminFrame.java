import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;


public class AdminFrame extends JFrame
{
	private static Connection connection;
	private static PreparedStatement preparedStatement = null;
	
	// title of the frame
	private static final String FRAME_TITLE = "Admin Options";
	
	// panel that will contain the table of reviews
	final static JPanel list = new JPanel();
	static JTable table;
	
	public AdminFrame(Connection cn)
	{
		connection = cn;
        setTitle(FRAME_TITLE);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setSize(new Dimension(600, 500)); // (width, height)
        
        String views[] = {"View all food with restaurants", "View Restaurant", 
        		"View Serves", "View Review", "View ReviewArchive", "View User", "View Wishlist"};
        final JComboBox viewbox = new JComboBox(views);
        viewbox.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
            	try 
            	{
            		if (viewbox.getSelectedIndex() == 0)
            			updateTable("select * from restaurant join serves using(restaurant_id)");
            		else if (viewbox.getSelectedIndex() == 1)
            			updateTable("select * from restaurant");
            		else if (viewbox.getSelectedIndex() == 2)
            			updateTable("select * from serves");
            		else if (viewbox.getSelectedIndex() == 3)
            			updateTable("select * from review");
            		else if (viewbox.getSelectedIndex() == 4)
            			updateTable("select * from reviewarchive");
            		else if (viewbox.getSelectedIndex() == 5)
            			updateTable("select * from user");
            		else if (viewbox.getSelectedIndex() == 6)
            			updateTable("select * from wishlist");
            		
            	}
            	catch(Exception e) {}
            }
        });
        
        add(viewbox);
        add(list);
        
        try {
        	updateTable("select * from restaurant join serves using(restaurant_id)");
        }
        catch(Exception e) {}
        
        // other frame option statements
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	
	public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException 
	{
        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);
    }
	
	
	/**
	 * Updates the table with a new review category
	 * @param statement the SQL query to be executed
	 * @throws SQLException
	 */
	public static void updateTable(String statement) throws SQLException
	{
		list.removeAll(); // first clear the existing table
		
		preparedStatement = connection.prepareStatement(statement);
        ResultSet resultSet = preparedStatement.executeQuery();
	    table = new JTable(buildTableModel(resultSet));
	    list.add(new JScrollPane(table)); // add the new table to the panel
	    
	    list.repaint();
	    list.revalidate();
	}
}
