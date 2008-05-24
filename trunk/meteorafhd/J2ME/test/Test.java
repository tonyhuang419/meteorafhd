package test;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextBox;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class Test extends MIDlet implements CommandListener {
	private TextBox textbox; // Textbox ��ʾһ����Ϣ
	private Display disp; // ����MIDlet��Display ����
	private Command cmdExit; // �趨��ť�����˳�MIDlet
	private Command cmdOK; // ȷ����ť
	private Alert alt; // ��Ϣ��ʾ����
	public Test() {
		super();
		disp = Display.getDisplay(this); // ��õ�ǰMIDlet��Display����
		cmdExit = new Command("�˳�", Command.SCREEN, 1); // �½��������ư�ť
		cmdOK = new Command("�Ķ�", Command.OK, 1);
		textbox = new TextBox("�����������Ŀ���:", "", 40, 0); // �½��ı���
		textbox.addCommand(cmdExit); // ��ӿ��ư�ť
		textbox.addCommand(cmdOK);
		textbox.setCommandListener(this); // ��ʼ��������
	}
	protected void startApp() throws MIDletStateChangeException {
		alt = new Alert("����ר�� V1.0");// ��ʼ����ʱ��ʾ��Ȩ��Ϣ���½���Ϣ�����
		// ������ʾ�ַ���
		alt.setString("==== ����ר�� V1.0 ====����2004������Ȩ����(C) 2004-2005");
		alt.setType(AlertType.INFO); // ����Ϊ��ͨ�Ķ���Ϣ��
		alt.setTimeout(Alert.FOREVER); // ��Ϣ�����ڰ���DONE������ܽ�����һҳ��
		disp.setCurrent(alt, textbox); // ��ʾ��Ϣ����
	}
	protected void pauseApp() {}
	protected void destroyApp(boolean arg0){}
	public void commandAction(Command arg0, Displayable arg1)
	{
		if (arg0 == cmdExit) { // �����˳���ʱֹͣ����
			destroyApp(false);
			notifyDestroyed();
		}
		if (arg0 == cmdOK) { // �����Ķ������Ķ���Ӧ��Ϣ
			TextBox textbox = (TextBox)arg1; // �õ��û����������
			String sInfo = textbox.getString();
			if (sInfo.equals("1") || sInfo.equals("2") || sInfo.equals("3") || sInfo.equals("4") || sInfo.equals("5")) { // ��ʾ��Ŀ1������
				// ������ѡ��Ŀѡ��Ҫ��ʾ������
				if (sInfo.equals("1")) alt.setString("һ�ֵ�Ƣ��������");
				if (sInfo.equals("2")) alt.setString("����Ĵ�Ը������");
				if (sInfo.equals("3")) alt.setString("������ٻ��¡���");
				if (sInfo.equals("4")) alt.setString("Ϊʲô����");
				if (sInfo.equals("5")) alt.setString("��ҹ���ˡ���");
				alt.setTitle("�������Ķ�����" + sInfo); // ���ñ���
			}else{
				alt.setString("�ܱ�Ǹ����ʱ��û����ѡ�����Ŀ�����������룡");
				alt.setTitle("���󾯸�"); // ���ñ���
			}
			alt.setType(AlertType.INFO); // ����Ϊ��ͨ�Ķ���Ϣ��
			alt.setTimeout(Alert.FOREVER); // ��Ϣ�����ڰ���DONE���������һҳ��
			disp.setCurrent(alt, textbox); // ��ʾ��Ϣ����
		}
	}
}