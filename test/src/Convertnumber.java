
import java.util.ArrayList;

public class Convertnumber { //extends JRDefaultScriptlet
//	���캯��
	public Convertnumber() {
	}

//	��������Сд�����ش�д����
	private static String convert(String number) {
		int num = 0;
		String numStr = null;
		ArrayList<String> aList = new ArrayList<String>();
		ArrayList<String> bList = new ArrayList<String>();

		int longNum = number.length();
		//System.out.println("***number***:" + number);
		bList.add("��");
		bList.add("Ҽ");
		bList.add("��");
		bList.add("��");
		bList.add("��");
		bList.add("��");
		bList.add("½");
		bList.add("��");
		bList.add("��");
		bList.add("��");
		bList.add("��");
		bList.add("Ǫ");
		bList.add("��");
		bList.add("ʰ");
		bList.add("Ԫ");
		bList.add("��");
		for (int i = 0; i < longNum; i++) {
			numStr = number.substring(i, i + 1);
			num = Integer.parseInt(numStr);
			numStr = (String) bList.get(num);
			aList.add(numStr);
			if (num != 0) {
				num = number.substring(i).length();
				if (num == 5) {
					aList.add(bList.get(10));
				}
				if (num == 4) {
					aList.add(bList.get(11));
				}
				if (num == 3) {
					aList.add(bList.get(12));
				}
				if (num == 2) {
					aList.add(bList.get(13));
				}
			}
		}

		longNum = aList.size();
		for (int i = longNum - 1; i >= 0; i--) {
			if (aList.get(i).equals(bList.get(0))) {
				aList.remove(i);
			}
			else {
				break;
			}
		}

		longNum = aList.size();
		for (int i = 0; i < longNum-2; i++) {
			if (aList.get(i).equals(bList.get(0)) && aList.get(i+1).equals(bList.get(0)) && aList.get(i+2).equals(bList.get(0))) {
				aList.remove(i);
				aList.remove(i+1);
				i++;
			}
		}


		longNum = aList.size();
		for (int i = 0; i < longNum-1; i++) {
			if (aList.get(i).equals(bList.get(0)) && aList.get(i+1).equals(bList.get(0))) {
				aList.remove(i);
			}
		}

		longNum = aList.size();
		numStr = "";
		for (int i = 0; i < longNum; i++) {
			numStr += (String) aList.get(i);
		}

		return numStr;
	}

	//ת��С���
	public String convertSmlnumber(String number){
		ArrayList<String> cList = new ArrayList<String>();
		ArrayList<String> bList = new ArrayList<String>();
		bList.add("��");
		bList.add("Ҽ");
		bList.add("��");
		bList.add("��");
		bList.add("��");
		bList.add("��");
		bList.add("½");
		bList.add("��");
		bList.add("��");
		bList.add("��");

		cList.add("Ԫ");
		cList.add("��");
		cList.add("��");
		cList.add("��");

		String strnum = "";
		String rtnstr = "";
		int num = 0;
		if(number.length() > 3){
			strnum = number.substring(0,3);
		}else{
			strnum = number;
		}
		for(int i=0;i<strnum.length();i++){
			if(i ==0){
				rtnstr +=(String)cList.get(i);
			}
			num = Integer.parseInt(strnum.substring(i,i+1));
			rtnstr +=(String)bList.get(num);
			if(num != 0){
				rtnstr +=(String)cList.get(i+1);
			}
		}
		//ȥ��ĩβjϵ��"��"
		while(rtnstr.substring(rtnstr.length()-1,rtnstr.length()).equals(bList.get(0))){
			rtnstr = rtnstr.substring(0,rtnstr.length()-1);
		}
		//����м�}��jϵΪ��,��ȥ��һ��
		for(int m=0;m<rtnstr.length()-1;m++){
			if(rtnstr.substring(m,m+1).equals(rtnstr.substring(m+1,m+2))){
				rtnstr = rtnstr.substring(0,m)+rtnstr.substring(m+1,rtnstr.length());
			}
		}
		return rtnstr;
	}

	public String convertNumber(String number) {
		String numberStr = "";
		boolean flag = false;
		ArrayList<String> bList = new ArrayList<String>();
		bList.add("Ԫ��");
		bList.add("��");
		bList.add("��");

		//�ж��Ƿ�Ϊ������������򡰼�ǽ�һ��
		int num = number.indexOf(".");
		String substr = "";
		if (num != -1) {
			//if (Integer.parseInt(number.substring(num + 1, number.length())) > 0) {
			//number = "" + (Integer.parseInt(number.substring(0, num)) + 1);
			//}
			//else {
			substr = number.substring(num+1, number.length());
			flag = true;
			number = "" + Integer.parseInt(number.substring(0, num));

			//}
		}

		num = number.length();
		ArrayList<String> aList = new ArrayList<String>();
		while (num > 0) {
			if (num >= 4) {
				aList.add(number.substring(num - 4, num));
			}
			if (num < 4 && num > 0) {
				aList.add(number.substring(0, num));
			}
			num = num - 4;
		}
		num = aList.size();
		//System.out.println("num::" + num);
		while (num > 0) {
			numberStr += Convertnumber.convert(aList.get(num - 1).toString()) +
			bList.get(num - 1);
			num--;
		}
		//���?����
		if(flag == true && Integer.parseInt(substr) != 0){
			numberStr = numberStr.substring(0,numberStr.length()-2)+convertSmlnumber(substr);
		}
		return numberStr;
	}

//	public static void main(String[] args) {
//	Convertnumber cnum = new Convertnumber();
//	String strnum = "2350090.12";
//	String str = cnum.convertNumber(strnum);
//	System.out.println(str);
//	}
}