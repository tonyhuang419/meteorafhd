package hf.view.main.OutSwf
{
    import flash.display.*;
    import flash.events.*;
    import flash.net.*;
    import hf.control.*;
    import hf.view.*;
    import hf.view.common.*;
    import hf.view.farm.land.*;

    public class HelpWindow extends Sprite
    {
        private var _helpLoader:Loader;

        public function HelpWindow()
        {
            addEventListener(Event.ADDED_TO_STAGE, onAdded);
            return;
        }// end function

        private function onAdded(param1:Event) : void
        {
            var _loc_2:Sprite;
            removeEventListener(Event.ADDED_TO_STAGE, onAdded);
            _helpLoader = new Loader();
            _helpLoader.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, ioerror);
            _helpLoader.contentLoaderInfo.addEventListener(Event.COMPLETE, onComplete);
            if (Language.lang == Language.EN)
            {
                _helpLoader.load(new URLRequest(GetCropID.getSwfUrl() + "main/outswf/help_en.swf"));
            }
            else
            {
                _helpLoader.load(new URLRequest(GetCropID.getSwfUrl() + "main/outswf/help.swf"));
            }// end else if
            addChild(_helpLoader);
            if (stage != null)
            {
                _loc_2 = new Sprite();
                _loc_2.graphics.beginFill(16777215, 0.3);
                _loc_2.graphics.drawRect(0, 0, stage.stageWidth, stage.stageHeight);
                _loc_2.graphics.endFill();
                addChildAt(_loc_2, 0);
            }// end if
            return;
        }// end function

        private function ioerror(param1:IOErrorEvent) : void
        {
            trace("加载新手帮助错误");
            return;
        }// end function

        private function onComplete(param1:Event) : void
        {
            var _loc_2:* = param1.currentTarget.content;
            _loc_2.x = (stage.stageWidth - _loc_2.width) / 2;
            _loc_2.y = (stage.stageHeight - _loc_2.height) / 2;
            _loc_2.addEventListener("closeEvent", helpCloseHandler, false, 0, true);
            return;
        }// end function

        private function helpCloseHandler(param1:Event) : void
        {
            if (parent != null)
            {
                Command.getInstance().mainCommand.welcomeEnd();
                Command.getInstance().mainCommand.getPackage();
                ViewControl.getInstance().dispatchEvent(new ViewEvent(ViewEvent.TASK_START));
                parent.removeChild(this);
            }// end if
            return;
        }// end function

    }
}
