package hf.view.main.leftInfo
{
    import flash.display.*;
    import hf.view.*;
    import hf.view.farm.toolBar.*;

    public class CameraIcon extends ToolBase
    {
        private var _icon:MovieClip;

        public function CameraIcon()
        {
            _icon = MaterialLib.getInstance().getMaterial("Camera") as MovieClip;
            addChild(_icon);
            var _loc_1:* = new Sprite();
            _loc_1.graphics.beginFill(0, 0);
            _loc_1.graphics.drawRect(0, 0, _icon.width, _icon.height);
            _loc_1.graphics.endFill();
            addChild(_loc_1);
            buttonMode = true;
            useHandCursor = true;
            return;
        }// end function

    }
}
