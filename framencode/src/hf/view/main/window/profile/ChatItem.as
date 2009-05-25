package hf.view.main.window.profile
{
    import com.lipi.*;
    import com.minutes.ui.collections.*;
    import flash.events.*;
    import flash.text.*;
    import hf.model.*;
    import hf.view.common.*;

    public class ChatItem extends LipiListChild
    {
        private var timeText:TextField;
        private var msgText:TextField;
        private var replyText:TextField;

        public function ChatItem()
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
            replyText = new TextField();
            replyText.defaultTextFormat = new TextFormat("Tahoma");
            replyText.visible = false;
            replyText.htmlText = "<a href=\'event:reply\'><font color=\'#0099FF\'>" + Language.L["回复"] + "</font></a>";
            replyText.y = 0;
            replyText.x = 400;
            replyText.height = 20;
            replyText.width = 40;
            replyText.addEventListener(TextEvent.LINK, replyHandler);
            addChild(replyText);
            return;
        }// end function

        private function replyHandler(param1:TextEvent) : void
        {
            var _loc_2:* = new LipiEvent("replyClick", true);
            _loc_2.data = data;
            dispatchEvent(_loc_2);
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

        override public function set data(param1:Object) : void
        {
            var _loc_7:String;
            var _loc_9:String;
            super.data = param1;
            var _loc_2:String;
            if (MData.getInstance().mainData.me)
            {
                _loc_2 = MData.getInstance().mainData.currentUserId;
            }// end if
            if (_loc_2 == "0" || _loc_2 == param1["fromId"])
            {
                replyText.visible = false;
            }
            else
            {
                replyText.visible = true;
            }// end else if
            var _loc_3:* = unescape(param1["msg"]);
            var _loc_4:String;
            _loc_3 = _loc_3.replace(/</g, "&lt;");
            _loc_3 = _loc_3.replace(/>/g, "&gt;");
            if (!param1.hasOwnProperty("isReply") || param1["isReply"] - 0 == 0)
            {
                _loc_4 = "<font color=\'#009900\'><b>" + String(param1["fromName"]) + ":</b></font> " + _loc_3;
            }
            else
            {
                _loc_4 = "<font color=\'#009900\'><b>" + String(param1["fromName"]) + "</b></font>回复<font color=\'#009900\'><b>" + param1["toName"] + ":</b></font> " + _loc_3;
            }// end else if
            var _loc_5:* = MData.getInstance().mainData.friendList;
            var _loc_6:String;
            _loc_7 = _loc_4;
            msgTextValue = _loc_7;
            var _loc_8:* = new Date(Number(param1["time"]) * 1000);
            var _loc_10:* = twoData(_loc_8.getHours());
            var _loc_11:* = twoData(_loc_8.getMinutes());
            if (Language.lang == Language.CN)
            {
                if (compareDate(_loc_8, new Date()) == 0)
                {
                    _loc_9 = Language.L["今天"] + _loc_10 + ":" + _loc_11;
                }
                else if (compareDate(_loc_8, new Date()) == 1)
                {
                    _loc_9 = Language.L["昨天"] + _loc_10 + ":" + _loc_11;
                }
                else if (compareDate(_loc_8, new Date()) == 2)
                {
                    _loc_9 = Language.L["前天"] + _loc_10 + ":" + _loc_11;
                }
                else
                {
                    _loc_9 = twoData(_loc_8.getMonth() + 1) + "-" + twoData(_loc_8.getDate()) + " " + _loc_10 + ":" + _loc_11;
                }// end else if
            }
            else
            {
                _loc_9 = twoData(_loc_8.getMonth() + 1) + "-" + twoData(_loc_8.getDate()) + " " + _loc_10 + ":" + _loc_11;
            }// end else if
            timeTextValue = "<b>" + _loc_9 + "</b>";
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

        private function compareDate(param1:Date, param2:Date) : int
        {
            param1 = new Date(param1.getFullYear(), param1.getMonth(), param1.getDate());
            param2 = new Date(param2.getFullYear(), param2.getMonth(), param2.getDate());
            var _loc_3:* = Math.abs(param2.getTime() - param1.getTime());
            return _loc_3 / 3600 / 24 / 1000;
        }// end function

    }
}
