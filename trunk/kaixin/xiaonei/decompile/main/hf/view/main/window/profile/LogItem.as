package hf.view.main.window.profile
{
    import com.lipi.*;
    import com.minutes.ui.collections.*;
    import flash.events.*;
    import flash.text.*;
    import hf.model.*;
    import hf.view.common.*;

    public class LogItem extends LipiListChild
    {
        private var msgText:TextField;
        private var timeText:TextField;

        public function LogItem()
        {
            timeText = new TextField();
            timeText.defaultTextFormat = new TextFormat("Tahoma");
            timeText.x = 0;
            timeText.y = 0;
            timeText.width = 80;
            timeText.height = 24;
            addChild(timeText);
            msgText = new TextField();
            msgText.defaultTextFormat = new TextFormat("Tahoma");
            msgText.addEventListener(TextEvent.LINK, onLink);
            msgText.x = 90;
            msgText.y = 0;
            msgText.width = 295;
            msgText.height = 24;
            msgText.wordWrap = true;
            addChild(msgText);
            return;
        }// end function

        private function set typeTextValue(param1:String) : void
        {
            if (param1 == null)
            {
                param1 = "";
            }// end if
            return;
        }// end function

        private function set msgTextValue(param1:String) : void
        {
            msgText.htmlText = param1;
            msgText.height = 9 + msgText.textHeight;
            return;
        }// end function

        override public function get data() : Object
        {
            return super.data;
        }// end function

        private function set timeTextValue(param1:String) : void
        {
            timeText.htmlText = param1;
            return;
        }// end function

        private function compareDate(param1:Date, param2:Date) : int
        {
            param1 = new Date(param1.getFullYear(), param1.getMonth(), param1.getDate());
            param2 = new Date(param2.getFullYear(), param2.getMonth(), param2.getDate());
            var _loc_3:* = Math.abs(param2.getTime() - param1.getTime());
            return _loc_3 / 3600 / 24 / 1000;
        }// end function

        override public function set data(param1:Object) : void
        {
            var _loc_6:String;
            var _loc_8:String;
            var _loc_11:int;
            super.data = param1;
            var _loc_2:* = String(param1["msg"]);
            var _loc_3:* = _loc_2.replace(/(.*?)\|(.*?)\|(.*)/, "$2");
            var _loc_4:* = MData.getInstance().mainData.friendList;
            var _loc_5:String;
            if (_loc_3.length < 12)
            {
                if (_loc_4 != null)
                {
                    _loc_11 = 0;
                    while (_loc_11 < _loc_4.length)
                    {
                        // label
                        if (_loc_4[_loc_11]["userId"].toString() == _loc_3.toString())
                        {
                            _loc_5 = _loc_4[_loc_11]["userName"];
                            break;
                        }// end if
                        _loc_11++;
                    }// end while
                }
                else
                {
                    _loc_5 = _loc_3;
                }// end else if
                _loc_6 = _loc_2.replace(/(.*?)\|(.*?)\|(.*?)/, "$1<a href=\'event:" + _loc_3 + "\'><font color=\'#009900\'><b>" + _loc_5 + "</b></font></a>$3");
            }
            else
            {
                _loc_6 = _loc_2;
            }// end else if
            msgTextValue = _loc_6;
            var _loc_7:* = new Date(Number(param1["time"]) * 1000);
            var _loc_9:* = twoData(_loc_7.getHours());
            var _loc_10:* = twoData(_loc_7.getMinutes());
            if (Language.lang == Language.CN)
            {
                if (compareDate(_loc_7, new Date()) == 0)
                {
                    _loc_8 = Language.L["今天"] + _loc_9 + ":" + _loc_10;
                }
                else if (compareDate(_loc_7, new Date()) == 1)
                {
                    _loc_8 = Language.L["昨天"] + _loc_9 + ":" + _loc_10;
                }
                else if (compareDate(_loc_7, new Date()) == 2)
                {
                    _loc_8 = Language.L["前天"] + _loc_9 + ":" + _loc_10;
                }
                else
                {
                    _loc_8 = twoData(_loc_7.getMonth() + 1) + "-" + twoData(_loc_7.getDate()) + " " + _loc_9 + ":" + _loc_10;
                }// end else if
            }
            else
            {
                _loc_8 = twoData(_loc_7.getMonth() + 1) + "-" + twoData(_loc_7.getDate()) + " " + _loc_9 + ":" + _loc_10;
            }// end else if
            timeTextValue = "<b>" + _loc_8 + "</b>";
            typeTextValue = "<font color=\'#FF0000\'><b>[" + param1["type"] + "]</b></a>";
            return;
        }// end function

        private function twoData(param1:int) : String
        {
            if (param1 < 10)
            {
                return "0" + String(param1);
            }// end if
            return String(param1);
        }// end function

        private function onLink(param1:TextEvent) : void
        {
            var _loc_2:* = new LipiEvent(LipiList.CHILD_LINK, true);
            _loc_2.data = param1.text;
            dispatchEvent(_loc_2);
            return;
        }// end function

    }
}
