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
	private TextBox textbox; // Textbox 显示一条消息
	private Display disp; // 引用MIDlet的Display 对象
	private Command cmdExit; // 设定按钮用于退出MIDlet
	private Command cmdOK; // 确定按钮
	private Alert alt; // 信息提示对象
	public Test() {
		super();
		disp = Display.getDisplay(this); // 获得当前MIDlet的Display对象
		cmdExit = new Command("退出", Command.SCREEN, 1); // 新建两个控制按钮
		cmdOK = new Command("阅读", Command.OK, 1);
		textbox = new TextBox("请输入待阅项目序号:", "", 40, 0); // 新建文本框
		textbox.addCommand(cmdExit); // 添加控制按钮
		textbox.addCommand(cmdOK);
		textbox.setCommandListener(this); // 开始侦听命令
	}
	protected void startApp() throws MIDletStateChangeException {
		alt = new Alert("整蛊专家 V1.0");// 开始运行时显示版权信息，新建信息框对象
		// 设置显示字符串
		alt.setString("==== 整蛊专家 V1.0 ====郎锐2004年作版权所有(C) 2004-2005");
		alt.setType(AlertType.INFO); // 设置为普通阅读信息框
		alt.setTimeout(Alert.FOREVER); // 信息窗口在按下DONE键后才能进入下一页面
		disp.setCurrent(alt, textbox); // 显示信息窗口
	}
	protected void pauseApp() {}
	protected void destroyApp(boolean arg0){}
	public void commandAction(Command arg0, Displayable arg1)
	{
		if (arg0 == cmdExit) { // 按下退出键时停止运行
			destroyApp(false);
			notifyDestroyed();
		}
		if (arg0 == cmdOK) { // 按下阅读键后阅读对应信息
			TextBox textbox = (TextBox)arg1; // 得到用户输入的内容
			String sInfo = textbox.getString();
			if (sInfo.equals("1") || sInfo.equals("2") || sInfo.equals("3") || sInfo.equals("4") || sInfo.equals("5")) { // 显示项目1的内容
				// 根据所选项目选择要显示的内容
				if (sInfo.equals("1")) alt.setString("一兄弟脾气甚……");
				if (sInfo.equals("2")) alt.setString("猪的四大愿望……");
				if (sInfo.equals("3")) alt.setString("春风里，百花下……");
				if (sInfo.equals("4")) alt.setString("为什么……");
				if (sInfo.equals("5")) alt.setString("昨夜做了……");
				alt.setTitle("您正在阅读短信" + sInfo); // 设置标题
			}else{
				alt.setString("很抱歉，暂时还没有您选择的项目，请重新输入！");
				alt.setTitle("错误警告"); // 设置标题
			}
			alt.setType(AlertType.INFO); // 设置为普通阅读信息框
			alt.setTimeout(Alert.FOREVER); // 信息窗口在按下DONE键后进入下一页面
			disp.setCurrent(alt, textbox); // 显示信息窗口
		}
	}
}