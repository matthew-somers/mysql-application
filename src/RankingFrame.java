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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * A frame that allows the user to sort the restaurants
 * and food by amount of reviews or average ratings.
 * 
 * Fall 2013, CS 157A, Group Project
 * @author Kevin Tan
 * @version 1.01 11/16/2013
 */
public class RankingFrame extends JFrame
{
	private static Connection connection;
	private static PreparedStatement preparedStatement = null;
	
	// title of the frame
	private static final String FRAME_TITLE = "Dishaster! - Rankings";
	
	// description that goes above the category selections
	private static final String DESCRIPTION_TEXT = "Select a ranking category:";
	
	// choices of different ways to rank the items
	private String[] rankingOptions = {"Worst Rated Foods", 
									   "Worst Rated Restaurants", 
									   "Most Reviewed Foods", 
									   "Most Reviewed Restaurants"};
	// ranking category option chosen
	private int selectedRanking = 0;
	
	// panel that will contain the table of rankings
	private final JPanel rankList;
	
	/**
	 * Constructs the ranking frame.
	 * @param cn the connection to database
	 * @throws SQLException 
	 */
	public RankingFrame(Connection connection) throws SQLException
	{
		this.connection = connection;
		
		// set the attributes of the frame
        setTitle(FRAME_TITLE);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setSize(new Dimension(500, 500)); // (width, height)
 
        // description label
        final JLabel description = new JLabel(DESCRIPTION_TEXT);
        add(description);
        
        // selector for different rankings
        final JComboBox rankingSelector = new JComboBox(rankingOptions);
        add(rankingSelector);
        
        // list of the rankings
        rankList = new JPanel();
        add(rankList);
        
        // set default ranking as "Worst Rated Foods"	    
        updateTable("SELECT food, AVG(rating) as average " +
				  	"FROM Review " +
				  	"GROUP BY food " +
        			"ORDER BY AVG(rating);");
        
        // changes made whenever a different option is selected
        rankingSelector.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent event)
        	{
        		selectedRanking = rankingSelector.getSelectedIndex(); // get integer option chosen
                
                // Worst Rated Foods
                if (selectedRanking == 0) 
                {
                	try { updateTable("SELECT food, AVG(rating) as average " +
                					  "FROM Review " +
                					  "GROUP BY food " +
                					  "ORDER BY AVG(rating);"); } 
                	catch (SQLException e) {}
                }
                
                // Worst Rated Restaurants
                else if (selectedRanking == 1) 
                {
                	try { updateTable("SELECT name, AVG(rating) as average " +
      					  			  "FROM Serves NATURAL JOIN Review NATURAL JOIN Restaurant " +
      					  			  "GROUP BY restaurant_id " +
      					  			  "ORDER BY AVG(rating), name;");} 
                	catch (SQLException e) {}
                }
                
                // Most Reviewed Foods
                else if (selectedRanking == 2) 
                {
                	try { updateTable("SELECT food, COUNT(food) as reviews " +
                					  "FROM Review " +
                					  "GROUP BY food " +
                					  "ORDER BY COUNT(food) DESC;"); }
                	catch (SQLException e) {}
                }
                
                // Most Reviewed Restaurants
                else if (selectedRanking == 3) 
                {
                	try { updateTable("SELECT name, COUNT(rating) as reviews " +
      					  			  "FROM Review NATURAL JOIN Restaurant " +
      					  			  "GROUP BY name " +
      					  			  "ORDER BY COUNT(rating) DESC;"); }
                	catch (SQLException e) {}
                }
        	} // end actionPerformed
        }); // end ranking selector action listener
        
        // other frame option statements
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	
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
	public void updateTable(String statement) throws SQLException
	{
		rankList.removeAll(); // first clear the existing table
		
		preparedStatement = connection.prepareStatement(statement);
        ResultSet resultSet = preparedStatement.executeQuery();
	    JTable table = new JTable(buildTableModel(resultSet));
	    
	    rankList.add(new JScrollPane(table)); // add the new table to the panel
	    
	    rankList.repaint();
	    rankList.revalidate();
	}
}