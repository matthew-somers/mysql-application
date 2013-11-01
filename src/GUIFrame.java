import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUIFrame extends JFrame
{

	public GUIFrame()
	{
        setTitle("Dishaster!");
        setLayout(new FlowLayout());
        setSize(new Dimension(800, 400));
        
        final JTextArea textarea = new JTextArea(10, 50);
        textarea.setEditable(false);
        textarea.setLineWrap(true);
        GUIModel model = new GUIModel();
        final JTextField searchbox = new JTextField(30);
        searchbox.setText("name");
        
        JButton searchbutton = new JButton("Search");
        searchbutton.setPreferredSize(new Dimension(150, 50));
        searchbutton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
            	try {
            	textarea.setText(GUIModel.readDataBase(searchbox.getText()));
            	}
            	catch(Exception e) {}
            }
        });
        
        
        this.add(searchbox);
        this.add(model);
        this.add(textarea);
        this.add(searchbutton);
        //setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
