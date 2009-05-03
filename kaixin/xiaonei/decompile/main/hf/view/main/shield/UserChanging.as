package hf.view.main.shield
{
    import flash.display.*;
    import flash.events.*;
    import flash.text.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.common.*;

    public class UserChanging extends Sprite
    {
        private var dataLoading:DataLoading;
        private var modeSprite:Sprite;
        private var _textField:TextField;

        public function UserChanging()
        {
            addEventListener(Event.ADDED_TO_STAGE, toStage);
            return;
        }// end function

        private function linkClick(param1:TextEvent) : void
        {
            Command.getInstance().mainCommand.userReload();
            _textField.visible = false;
            dataLoading.visible = true;
            return;
        }// end function

        private function toStage(param1:Event) : void
        {
            removeEventListener(Event.ADDED_TO_STAGE, toStage);
            modeSprite = new Sprite();
            modeSprite.graphics.beginFill(16777215, 0.6);
            modeSprite.graphics.drawRect(0, 0, stage.stageWidth, stage.stageHeight);
            modeSprite.graphics.endFill();
            modeSprite.visible = false;
            addChild(modeSprite);
            dataLoading = new DataLoading();
            dataLoading.x = stage.stageWidth / 2;
            dataLoading.y = stage.stageHeight / 2;
            dataLoading.visible = false;
            addChild(dataLoading);
            _textField = new TextField();
            _textField.addEventListener(TextEvent.LINK, linkClick);
            _textField.defaultTextFormat = new TextFormat("Tahoma", 14, 3355443);
            _textField.width = 200;
            _textField.height = 25;
            _textField.x = stage.stageWidth / 2 - 50;
            _textField.y = stage.stageHeight / 2;
            _textField.text = "";
            _textField.selectable = false;
            _textField.visible = false;
            addChild(_textField);
            MData.getInstance().mainData.addEventListener(MainData.USER_CHANGING, userChanging);
            MData.getInstance().mainData.addEventListener(MainData.SET_USER_ERROR, setUserError);
            return;
        }// end function

        private function setUserError(param1:Event) : void
        {
            _textField.htmlText = MData.getInstance().mainData.setUserError;
            _textField.visible = true;
            dataLoading.visible = false;
            return;
        }// end function

        private function userChanging(param1:Event) : void
        {
            var _loc_2:* = MData.getInstance().mainData.userChanging;
            modeSprite.visible = MData.getInstance().mainData.userChanging;
            dataLoading.visible = _loc_2;
            return;
        }// end function

    }
}
