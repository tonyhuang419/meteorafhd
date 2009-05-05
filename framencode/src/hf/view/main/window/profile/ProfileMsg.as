package hf.view.main.window.profile
{
    import com.adobe.utils.*;
    import com.lipi.*;
    import com.minutes.ui.collections.*;
    import com.minutes.ui.constant.*;
    import com.minutes.ui.control.*;
    import com.minutes.ui.core.*;
    import flash.display.*;
    import flash.events.*;
    import flash.text.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.common.*;
    import hf.view.main.WindowControl.*;

    public class ProfileMsg extends Sprite
    {
        private var replyLabel:TextField;
        private var chatLipiList:LipiList;
        private var delChatLabel:TextField;
        private var chatInput:TextField;
        private var _me:Boolean = false;
        private var sendToId:String = "0";

        public function ProfileMsg()
        {
            chatLipiList = new LipiList(ChatItem);
            chatLipiList.addEventListener("replyClick", replyClickHandler);
            chatLipiList.bgAlpha = 0;
            chatLipiList.horizontalScrollPolicy = ScrollPolicy.OFF;
            chatLipiList.width = 470;
            chatLipiList.height = 150;
            chatLipiList.x = 20;
            chatLipiList.y = 2;
            addChild(chatLipiList);
            chatInput = new TextField();
            chatInput.defaultTextFormat = new TextFormat("Tahoma");
            if (Language.lang == Language.CN)
            {
                chatInput.maxChars = 50;
            }
            else
            {
                chatInput.maxChars = 150;
            }// end else if
            chatInput.wordWrap = true;
            chatInput.addEventListener(KeyboardEvent.KEY_DOWN, chatInputKeyDown);
            chatInput.type = TextFieldType.INPUT;
            chatInput.background = true;
            chatInput.backgroundColor = 16777215;
            chatInput.border = true;
            chatInput.borderColor = 39423;
            chatInput.htmlText = "";
            chatInput.width = 450;
            chatInput.height = 35;
            chatInput.x = 20;
            chatInput.y = 160;
            addChild(chatInput);
            var _loc_1:* = new TextField();
            _loc_1.autoSize = TextFieldAutoSize.LEFT;
            _loc_1.selectable = false;
            _loc_1.defaultTextFormat = new TextFormat("Tahoma", null, 6710886);
            _loc_1.text = Language.L["(每条最多 50 字)"];
            _loc_1.y = 200;
            _loc_1.x = 410 - _loc_1.width;
            addChild(_loc_1);
            var _loc_2:* = new LipiButton();
            _loc_2.width = 43;
            _loc_2.height = 23;
            _loc_2.bgAlpha = 0;
            _loc_2.bgSkin = new LipiSkin(MaterialLib.getInstance().getClass("ButtonGreen"));
            _loc_2.addEventListener(MouseEvent.CLICK, sendMsg);
            _loc_2.y = 198;
            _loc_2.x = 420;
            _loc_2.label = Language.L["发送留言"];
            _loc_2.textColor = 16777215;
            addChild(_loc_2);
            delChatLabel = new TextField();
            delChatLabel.autoSize = TextFieldAutoSize.LEFT;
            delChatLabel.selectable = false;
            delChatLabel.defaultTextFormat = new TextFormat("Tahoma", null, 39423);
            delChatLabel.htmlText = "<a href=\'event:del\'>" + Language.L["清空留言记录"] + "</a>";
            delChatLabel.addEventListener(TextEvent.LINK, delChat);
            delChatLabel.y = 200;
            delChatLabel.x = 20;
            addChild(delChatLabel);
            replyLabel = new TextField();
            replyLabel.selectable = false;
            replyLabel.autoSize = TextFieldAutoSize.LEFT;
            replyLabel.background = true;
            replyLabel.backgroundColor = 16777215;
            replyLabel.defaultTextFormat = new TextFormat("Tahoma", null, 39168, true);
            replyLabel.text = "";
            replyLabel.height = 22;
            replyLabel.y = 140;
            replyLabel.x = 20;
            addChild(replyLabel);
            return;
        }// end function

        public function get dataList() : Array
        {
            return chatLipiList.dataList;
        }// end function

        private function delChat(param1:TextEvent) : void
        {
            var e:* = param1;
            var cw:* = new ConfirmWindow();
            cw.title = Language.L["清空记录"];
            cw.data = {text:Language.L["确认删除你的留言记录？"]};
            cw.confirmFn = 
function () : void
{
    Command.getInstance().mainCommand.clearChat();
    return;
}// end function
;
            WControl.open(cw);
            return;
        }// end function

        public function hideReply() : void
        {
            if (replyLabel != null)
            {
                replyLabel.visible = false;
            }// end if
            sendToId = "0";
            return;
        }// end function

        private function chatInputKeyDown(param1:KeyboardEvent) : void
        {
            param1.stopPropagation();
            if (param1.keyCode == 13)
            {
                sendMsg();
            }// end if
            return;
        }// end function

        public function set me(param1:Boolean) : void
        {
            _me = param1;
            return;
        }// end function

        private function sendMsg(param1:Event = null) : void
        {
            if (StringUtil.trim(chatInput.text) == "")
            {
                chatInput.text = "";
                return;
            }// end if
            var _loc_2:int;
            var _loc_3:String;
            var _loc_4:String;
            if (me)
            {
                _loc_4 = MData.getInstance().mainData.host["uId"];
            }
            else
            {
                _loc_4 = MData.getInstance().mainData.currentUserId;
            }// end else if
            if (sendToId != "0")
            {
                _loc_3 = sendToId;
                _loc_2 = 1;
            }
            else
            {
                _loc_3 = _loc_4;
            }// end else if
            var _loc_5:* = chatInput.text;
            _loc_5 = chatInput.text.replace("\r", " ");
            Command.getInstance().mainCommand.sendChat(_loc_3, _loc_4, _loc_2, _loc_5);
            chatInput.text = "";
            replyLabel.visible = false;
            sendToId = "0";
            return;
        }// end function

        private function replyClickHandler(param1:LipiEvent) : void
        {
            replyLabel.text = Language.L["回复"] + param1.data["fromName"] + ": ";
            replyLabel.visible = true;
            sendToId = param1.data["fromId"];
            stage.focus = chatInput;
            return;
        }// end function

        public function set dataList(param1:Array) : void
        {
            chatLipiList.dataList = param1;
            if (MData.getInstance().mainData.me)
            {
                delChatLabel.visible = true;
            }
            else
            {
                delChatLabel.visible = false;
            }// end else if
            return;
        }// end function

        public function get me() : Boolean
        {
            return _me;
        }// end function

    }
}
