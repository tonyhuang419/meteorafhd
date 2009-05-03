package hf.view.main.window.profile
{
    import com.minutes.ui.collections.*;
    import com.minutes.ui.constant.*;
    import flash.display.*;
    import flash.events.*;
    import flash.text.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.common.*;
    import hf.view.main.WindowControl.*;

    public class ProfileLog extends Sprite
    {
        private var aLipiList:LipiList;
        private var delLogLabel:TextField;

        public function ProfileLog()
        {
            aLipiList = new LipiList(LogItem);
            aLipiList.bgAlpha = 0;
            aLipiList.horizontalScrollPolicy = ScrollPolicy.OFF;
            aLipiList.width = 460;
            aLipiList.height = 190;
            aLipiList.x = 30;
            aLipiList.y = 3;
            addChild(aLipiList);
            var _loc_1:* = new Shape();
            _loc_1.graphics.lineStyle(1, 16777215);
            _loc_1.graphics.moveTo(0, 0);
            _loc_1.graphics.lineTo(490, 0);
            _loc_1.y = 195;
            _loc_1.x = 3;
            addChild(_loc_1);
            delLogLabel = new TextField();
            delLogLabel.autoSize = TextFieldAutoSize.LEFT;
            delLogLabel.selectable = false;
            delLogLabel.defaultTextFormat = new TextFormat("Tahoma", null, 39423);
            delLogLabel.htmlText = "<a href=\'event:del\'>" + Language.L["清空消息记录"] + "</a>";
            delLogLabel.addEventListener(TextEvent.LINK, delLog);
            delLogLabel.x = 30;
            delLogLabel.y = 180 + 18;
            addChild(delLogLabel);
            return;
        }// end function

        public function set dataList(param1:Array) : void
        {
            delLogLabel.visible = MData.getInstance().mainData.me;
            aLipiList.dataList = param1;
            return;
        }// end function

        private function delLogConfirm() : void
        {
            Command.getInstance().mainCommand.clearLog();
            return;
        }// end function

        public function get dataList() : Array
        {
            return aLipiList.dataList;
        }// end function

        private function delLog(param1:TextEvent) : void
        {
            var _loc_2:* = new ConfirmWindow();
            _loc_2.title = Language.L["清空记录"];
            _loc_2.data = {text:Language.L["确认清空消息记录？"]};
            _loc_2.confirmFn = delLogConfirm;
            WControl.open(_loc_2);
            return;
        }// end function

    }
}
