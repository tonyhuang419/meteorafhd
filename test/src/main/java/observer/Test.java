package observer;

import java.util.ArrayList;
import java.util.List;

public class Test{
    public static void main(String[] args) {
        NoticeOfficer officer = new NoticeOfficer("��������2��60 �лᣡ");
        List personList = new ArrayList();
        personList.add(new Person("У��"));
        personList.add(new Person("��å"));
        personList.add(new Person("����Ա"));
        personList.add(new Person("����"));
        officer.sendNotice(personList);
        for (int i = 0; i < 10000000; i++) {
        }
        officer.content="�����Ϊ3��50";
        officer.sendNotice(personList);
    }
}
