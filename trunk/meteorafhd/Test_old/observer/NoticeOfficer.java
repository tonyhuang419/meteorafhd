package observer;

import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

class NoticeOfficer extends Observable {
    public NoticeOfficer(String content) {
        this.content = content;
    }
    //重要通知
    String content;

    void sendNotice(List p) {
        Iterator iter = p.iterator();
        while (iter.hasNext()) {
            Object o = (Object) iter.next();
            addObserver((Observer) o);
        }
        setChanged();
        notifyObservers(content);
    } 
}