import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/*
 * Add to Wishlist Frame.
 */
public class WishlistFrame extends JFrame
{
	private static Connection connection;
	private static PreparedStatement preparedStatement = null;
	
	// title of the frame
	private static final String FRAME_TITLE = "Dishaster! - Your Wishlist";
	
	// panel that will contain the table of reviews
	final static JPanel reviewList = new JPanel();
	
	public WishlistFrame(final Connection connection, final int userid) throws SQLException
	{
		this.connection = connection;
		
		// set the attributes of the frame
	    setTitle(FRAME_TITLE);
	    setLayout(new FlowLayout(FlowLayout.CENTER));
	    setSize(new Dimension(500, 500)); // (width, height)
	    
	    // button to INSERT a new review
	    JButton insertButton = new JButton("Add to Wishlist");
	    insertButton.setPreferredSize(new Dimension(150, 30));
	    insertButton.addActionListener(new ActionListener() 
	    {
	        @Override
	        public void actionPerformed(ActionEvent event) 
	        {
	        	try { new WishlistAdd(connection, userid); } // opens new frame
	        	catch(Exception e) {}
	        }
	    });
	    add(insertButton); // add button to the frame
	    
	    
	    // button to UPDATE a review
	    JButton updateButton = new JButton("Update Wishlist");
	    updateButton.setPreferredSize(new Dimension(150, 30));
	    updateButton.addActionListener(new ActionListener() 
	    {
	        @Override
	        public void actionPerformed(ActionEvent event) 
	        {
	        	try { new UpdateReviewFrame(connection, userid); } // opens new frame
	        	catch(Exception e) {}
	        }
	    });
	    add(updateButton); // add button to the frame
	    
	    
	    // button to DELETE a review
	    JButton deleteButton = new JButton("Delete from Wishlist");
	    deleteButton.setPreferredSize(new Dimension(150, 30));
	    deleteButton.addActionListener(new ActionListener() 
	    {
	        @Override
	        public void actionPerformed(ActionEvent event) 
	        {
	        	try 
	        	{
	        		new DeleteWishlistFrame(connection, userid);
	        	} // opens new frame
	        	catch(Exception e) {}
	        }
	    });
	    add(deleteButton); // add button to the frame
	    
	    // add list of reviews
	    add(reviewList);
	    
	    // get the list of reviews that match the user id	    
	    updateTable("SELECT food, name " +
				  	"FROM Wishlist NATURAL JOIN Restaurant " +
				  	"WHERE id = " + userid + " " +
	    			"");
	    
	    
	    // other frame option statements
	    setVisible(true);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    
	} // end ReviewFrame constructor



/**
 * Builds a table model
 * @author Paul Vargas on http://stackoverflow.com/questions/10620448/most-simple-code-to-populate-jtable-from-resultset
 * @param rs the result set
 * @return a table model with the data and column names
 * @throws SQLException
 */
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
 * Updates the table with a new ranking category
 * @param statement the SQL query to be executed
 * @throws SQLException
 */
public static void updateTable(String statement) throws SQLException
{
	reviewList.removeAll(); // first clear the existing table
	System.out.println(statement);
	
	preparedStatement = connection.prepareStatement(statement);
    ResultSet resultSet = preparedStatement.executeQuery();
    JTable table = new JTable(buildTableModel(resultSet));
    
    reviewList.add(new JScrollPane(table)); // add the new table to the panel
    
    reviewList.repaint();
    reviewList.revalidate();
}
	
}
