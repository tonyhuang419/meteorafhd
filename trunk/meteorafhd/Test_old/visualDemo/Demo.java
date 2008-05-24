package visualDemo;
import jdbc.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class Demo {

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private Table table = null;
	private Text text = null;


	public static void main(String[] args) {

		Display display = Display.getDefault();
		Demo thisClass = new Demo();
		thisClass.createSShell();
		thisClass.sShell.open();

		while (!thisClass.sShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	private void createSShell() {
		sShell = new Shell();
		sShell.setText("Demo");
		sShell.setSize(new Point(660, 480));
		sShell.setLayout(null);
		table = new Table(sShell, SWT.SINGLE | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);

		String[] titles = { "Col 1", "Col 2", "Col 3", "Col 4" };

		for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
			TableColumn column = new TableColumn(table, SWT.NULL);
			column.setText(titles[loopIndex]);
		}
		for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
			table.getColumn(loopIndex).pack();
		}
		table.setLinesVisible(true);
		table.setBounds(new Rectangle(50, 15, 300, 250));
		text = new Text(sShell, SWT.BORDER);
		text.setBounds(new Rectangle(50, 280, 300,30));
		text.addTraverseListener(new org.eclipse.swt.events.TraverseListener() {
			public void keyTraversed(org.eclipse.swt.events.TraverseEvent e) {
				String sqltext = text.getText();
				sqle(sqltext);
			}
		});
	}
	
	private void sqle(String _sqlText){
		ResultSet rs;
		try{	
			DAO.setConnection();
			rs = DAO.getResultSet(_sqlText);
			//int loopIndex = 0;
			while(rs.next()){
				TableItem item = new TableItem(table, SWT.NULL);
				item.setText(0, rs.getString("people"));
				item.setText(1, rs.getString("book"));
				item.setText(2, rs.getString("city"));
				item.setText(3, rs.getString("thing"));
			}		
			rs.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}		
	}
}




