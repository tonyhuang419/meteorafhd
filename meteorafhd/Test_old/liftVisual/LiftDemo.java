/**
 * 
 */
package liftVisual;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class LiftDemo {

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="79,28"
	private Composite composite3 = null;
	private Composite composite1 = null;
	private Composite composite2 = null;
	static  Label label = null;
	static  Label label1 = null;
	static  Label label2 = null;
	static private Button button16 = null;
	static private Button button15 = null;
	static private Button button14 = null;
	static private Button button13 = null;
	static private Button button12 = null;
	static private Button button11 = null;
	static private Button button26 = null;
	static private Button button25 = null;
	static private Button button24 = null;
	static private Button button23 = null;
	static private Button button22 = null;
	static private Button button21 = null;
	static private Button button36 = null;
	static private Button button35 = null;
	static private Button button34 = null;
	static private Button button33 = null;
	static private Button button32 = null;
	static private Button button31 = null;
	private Composite composite = null;
	static private Button button962 = null;
	static private Button button951 = null;
	static private Button button952 = null;
	static private Button button941 = null;
	static private Button button942 = null;
	static private Button button931 = null;
	static private Button button932 = null;
	static private Button button921 = null;
	static private Button button922 = null;
	static private Button button911 = null;
	private Label label3 = null;
	private Label label31 = null;
	
	private Building buildx = new Building();  //  @jve:decl-index=0:

	private void createComposite() {
		composite = new Composite(sShell, SWT.NONE);
		composite.setLayout(null);
		composite.setBounds(new Rectangle(242, 36, 132, 234));
		button962 = new Button(composite, SWT.NONE);
		button962.setBounds(new Rectangle(61, 1, 38, 37));
		button962.setText("6");
		button962.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
				buildx.setAskBuilding(5, 1);
				button962.setEnabled(false);
			}
		});
		button951 = new Button(composite, SWT.NONE);
		button951.setBounds(new Rectangle(13, 39, 38, 37));
		button951.setText("5");
		button951.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
				buildx.setAskBuilding(4, 0);
				button951.setEnabled(false);
			}
		});
		button952 = new Button(composite, SWT.NONE);
		button952.setBounds(new Rectangle(61, 39, 38, 37));
		button952.setText("5");
		button952.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
				buildx.setAskBuilding(4, 1);
				button952.setEnabled(false);
			}
		});
		button941 = new Button(composite, SWT.NONE);
		button941.setBounds(new Rectangle(13, 77, 38, 37));
		button941.setText("4");
		button941.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
				buildx.setAskBuilding(3, 0);
				button941.setEnabled(false);
			}
		});
		button942 = new Button(composite, SWT.NONE);
		button942.setBounds(new Rectangle(61, 77, 38, 37));
		button942.setText("4");
		button942.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
				buildx.setAskBuilding(3, 1);
				button942.setEnabled(false);
			}
		});
		button931 = new Button(composite, SWT.NONE);
		button931.setBounds(new Rectangle(13, 115, 38, 37));
		button931.setText("3");
		button931.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
				buildx.setAskBuilding(2, 0);
				button931.setEnabled(false);
			}
		});
		button932 = new Button(composite, SWT.NONE);
		button932.setBounds(new Rectangle(61, 115, 38, 37));
		button932.setText("3");
		button932.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
				buildx.setAskBuilding(2, 1);
				button932.setEnabled(false);
			}
		});
		button921 = new Button(composite, SWT.NONE);
		button921.setBounds(new Rectangle(13, 153, 38, 37));
		button921.setText("2");
		button921.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
				buildx.setAskBuilding(1, 0);
				button921.setEnabled(false);
			}
		});
		button922 = new Button(composite, SWT.NONE);
		button922.setBounds(new Rectangle(61, 153, 38, 37));
		button922.setText("2");
		button922.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
				buildx.setAskBuilding(1, 1);
				button922.setEnabled(false);
			}
		});
		button911 = new Button(composite, SWT.NONE);
		button911.setBounds(new Rectangle(13, 191, 38, 37));
		button911.setText("1");
		button911.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
				buildx.setAskBuilding(0, 0);
				button911.setEnabled(false);
			}
		});
		label3 = new Label(composite, SWT.CENTER);
		label3.setBounds(new Rectangle(61, 209, 38, 37));
		label3.setText("Down");
		label31 = new Label(composite, SWT.CENTER);
		label31.setBounds(new Rectangle(13, 1, 38, 37));
		label31.setText("Up");
	}
	
	
	
	private void createComposite3() {
		composite3 = new Composite(sShell, SWT.NONE);
		composite3.setLayout(null);
		composite3.setBounds(new Rectangle(155, 36, 50, 234));
		button36 = new Button(composite3, SWT.NONE);
		button36.setBounds(new Rectangle(8, 6, 32, 31));
		button36.setText("6");
		button36.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
				buildx.liftThree.askFloor(6);
				button36.setEnabled(false);
			}
		});
		button35 = new Button(composite3, SWT.NONE);
		button35.setBounds(new Rectangle(8, 43, 32, 31));
		button35.setText("5");
		button35.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
				buildx.liftThree.askFloor(5);
				button35.setEnabled(false);
			}
		});
		button34 = new Button(composite3, SWT.NONE);
		button34.setBounds(new Rectangle(8, 80, 32, 31));
		button34.setText("4");
		button34.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {				
				buildx.liftThree.askFloor(4);
				button34.setEnabled(false);
			}
		});
		button33 = new Button(composite3, SWT.NONE);
		button33.setBounds(new Rectangle(8, 117, 32, 31));
		button33.setText("3");
		button33.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {			
				buildx.liftThree.askFloor(3);
				button33.setEnabled(false);
				button33.setEnabled(false);
			}
		});
		button32 = new Button(composite3, SWT.NONE);
		button32.setBounds(new Rectangle(8, 154, 32, 31));
		button32.setText("2");
		button32.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {				
				buildx.liftThree.askFloor(2);
				button32.setEnabled(false);
			}
		});
		button31 = new Button(composite3, SWT.NONE);
		button31.setBounds(new Rectangle(8, 191, 32, 31));
		button31.setText("1");
		button31.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {				
				buildx.liftThree.askFloor(1);
				button31.setEnabled(false);
			}
		});
	}
	
	private void createComposite2() {
		composite2 = new Composite(sShell, SWT.NONE);
		composite2.setLayout(null);
		composite2.setBounds(new Rectangle(82, 36, 50, 234));
		button26 = new Button(composite2, SWT.NONE);
		button26.setBounds(new Rectangle(8, 6, 34, 32));
		button26.setText("6");
		button26.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {				
				buildx.liftTwo.askFloor(6);
				button26.setEnabled(false);
			}
		});
		button25 = new Button(composite2, SWT.NONE);
		button25.setBounds(new Rectangle(7, 44, 34, 32));
		button25.setText("5");
		button25.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {			
				buildx.liftTwo.askFloor(5);
				button25.setEnabled(false);
			}
		});
		button24 = new Button(composite2, SWT.NONE);
		button24.setBounds(new Rectangle(7, 82, 34, 32));
		button24.setText("4");
		button24.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {				
				buildx.liftTwo.askFloor(4);
				button24.setEnabled(false);
			}
		});
		button23 = new Button(composite2, SWT.NONE);
		button23.setBounds(new Rectangle(7, 120, 34, 32));
		button23.setText("3");
		button23.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {			
				buildx.liftTwo.askFloor(3);
				button23.setEnabled(false);
			}
		});
		button22 = new Button(composite2, SWT.NONE);
		button22.setBounds(new Rectangle(7, 158, 34, 32));
		button22.setText("2");
		button22.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {		
				buildx.liftTwo.askFloor(2);
				button22.setEnabled(false);
			}
		});
		button21 = new Button(composite2, SWT.NONE);
		button21.setBounds(new Rectangle(7, 196, 34, 32));
		button21.setText("1");
		button21.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {			
				buildx.liftTwo.askFloor(1);
				button21.setEnabled(false);
			}
		});
	}

	private void createComposite1() {		
		buildx.liftOne.start();
		buildx.liftTwo.start();
		buildx.liftThree.start();
		
		composite1 = new Composite(sShell, SWT.NONE);
		composite1.setLayout(null);
		composite1.setBounds(new Rectangle(12, 36, 50, 234));
		button16 = new Button(composite1, SWT.NONE);
		button16.setBounds(new Rectangle(7, 6, 35, 31));
		button16.setText("6");
		button16.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {			
				buildx.liftOne.askFloor(6);
				button16.setEnabled(false);
			}
		});
		button15 = new Button(composite1, SWT.NONE);
		button15.setBounds(new Rectangle(7, 43, 35, 31));
		button15.setText("5");
		button15.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
				buildx.liftOne.askFloor(5);
				button15.setEnabled(false);
			}
		});
		button14 = new Button(composite1, SWT.NONE);
		button14.setBounds(new Rectangle(7, 80, 35, 31));
		button14.setText("4");
		button14.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {				
				buildx.liftOne.askFloor(4);
				button14.setEnabled(false);
			}
		});
		button13 = new Button(composite1, SWT.NONE);
		button13.setBounds(new Rectangle(7, 117, 35, 31));
		button13.setText("3");
		button13.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {				
				buildx.liftOne.askFloor(3);
				button13.setEnabled(false);
			}
		});
		button12 = new Button(composite1, SWT.NONE);
		button12.setBounds(new Rectangle(7, 154, 35, 31));
		button12.setText("2");
		button12.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {				
				buildx.liftOne.askFloor(2);
				button12.setEnabled(false);
			}
		});
		button11 = new Button(composite1, SWT.NONE);
		button11.setBounds(new Rectangle(7, 191, 35, 31));
		button11.setText("1");
		button11.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {			
				buildx.liftOne.askFloor(1);
				button11.setEnabled(false);
			}
		});
	}
	
//	private void setLiftTop(int top){
//		
//	}
	
	static String test;  //  @jve:decl-index=0:
	static void setLabelText(String _test){
		test = _test;
		Display display = Display.getDefault();
		display.asyncExec(new Runnable(){
			public void   run(){
				label.setText(test);
			}
		});
	}
	static void setLabel1Text(String _test){
		test = _test;
		Display display = Display.getDefault();
		display.asyncExec(new Runnable(){
			public void   run(){
				label1.setText(test);
			}
		});
	}
	static void setLabel2Text(String _test){
		test = _test;
		Display display = Display.getDefault();
		display.asyncExec(new Runnable(){
			public void   run(){
				label2.setText(test);
			}
		});
	}
	
	static int xx;
	static public void setLiftButtonEnable(int _i){
		xx = _i;
		Display display = Display.getDefault();
		display.asyncExec(new Runnable(){
			public void   run(){
				switch(xx){
				case 11:
					button11.setEnabled(true);
					break;
				case 12:
					button12.setEnabled(true);
					break;
				case 13:
					button13.setEnabled(true);
					break;
				case 14:
					button14.setEnabled(true);
					break;
				case 15:
					button15.setEnabled(true);
					break;
				case 16:
					button16.setEnabled(true);
					break;
				case 21:
					button21.setEnabled(true);
					break;
				case 22:
					button22.setEnabled(true);
					break;
				case 23:
					button23.setEnabled(true);
					break;
				case 24:
					button24.setEnabled(true);
					break;
				case 25:
					button25.setEnabled(true);
					break;
				case 26:
					button26.setEnabled(true);
					break;
				case 31:
					button31.setEnabled(true);
					break;
				case 32:
					button32.setEnabled(true);
					break;
				case 33:
					button33.setEnabled(true);
					break;
				case 34:
					button34.setEnabled(true);
					break;
				case 35:
					button35.setEnabled(true);
					break;
				case 36:
					button36.setEnabled(true);
					break;		
				}
			}
		});	
	}
	
	static public void setBuildingButtonEnable(int _i){
		xx = _i;
		Display display = Display.getDefault();
		display.asyncExec(new Runnable(){
			public void  run(){
				switch(xx){
				case 911:
					button911.setEnabled(true);
					break;
				case 921:
					button921.setEnabled(true);
					break;
				case 931:
					button931.setEnabled(true);
					break;
				case 941:
					button941.setEnabled(true);
					break;
				case 951:
					button951.setEnabled(true);
					break;
				case 922:
					button922.setEnabled(true);
					break;
				case 932:
					button932.setEnabled(true);
					break;
				case 942:
					button942.setEnabled(true);
					break;
				case 952:
					button952.setEnabled(true);
					break;	
				case 962:
					button962.setEnabled(true);
					break;	
				}
			}
		});	
	}
	
	
	private void createSShell() {
		sShell = new Shell();
		sShell.setText("LiftDemo");
		createComposite3();
		createComposite2();
		createComposite1();
		sShell.setSize(new Point(447, 371));
		sShell.setLayout(null);
		label = new Label(sShell, SWT.CENTER);
		label.setBounds(new Rectangle(16, 283, 35, 27));
		label.setText("1");
		label1 = new Label(sShell, SWT.CENTER);
		label1.setBounds(new Rectangle(91, 283, 35, 27));
		label1.setText("1");
		label2 = new Label(sShell, SWT.CENTER);
		label2.setBounds(new Rectangle(160, 284, 35, 27));
		label2.setText("1");
		createComposite();
		
		buildx.liftOne.setName("a");
		buildx.liftTwo.setName("b");
		buildx.liftThree.setName("c");
	}
	
	public static void main(String[] args) {
		Display display = Display.getDefault();
		LiftDemo thisClass = new LiftDemo();
		thisClass.createSShell();
		thisClass.sShell.open();

		while (!thisClass.sShell.isDisposed()) {
			if (!display.readAndDispatch()){
				display.sleep();
			}
		}
		display.dispose();
	}
}
