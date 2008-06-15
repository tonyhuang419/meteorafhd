package observer;

import java.util.ArrayList;
import java.util.List;

public class Test{
    public static void main(String[] args) {
        NoticeOfficer officer = new NoticeOfficer("今天下午2：60 有会！");
        List personList = new ArrayList();
        personList.add(new Person("校长"));
        personList.add(new Person("流氓"));
        personList.add(new Person("程序员"));
        personList.add(new Person("疯子"));
        officer.sendNotice(personList);
        for (int i = 0; i < 10000000; i++) {
        }
        officer.content="会议改为3：50";
        officer.sendNotice(personList);
    }
}
