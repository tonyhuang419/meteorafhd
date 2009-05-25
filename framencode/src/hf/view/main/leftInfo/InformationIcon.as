package hf.view.main.leftInfo
{
    import flash.display.*;
    import hf.view.*;
    import hf.view.farm.toolBar.*;

    public class InformationIcon extends ToolBase
    {
        private var _icon:MovieClip;

        public function InformationIcon()
        {
            _icon = MaterialLib.getInstance().getMaterial("InformationIcon") as MovieClip;
            _icon.gotoAndStop(1);
            addChild(_icon);
            buttonMode = true;
            useHandCursor = true;
            return;
        }// end function

        public function stopIcon() : void
        {
            _icon.gotoAndStop(1);
            return;
        }// end function

        public function playIcon() : void
        {
            _icon.play();
            return;
        }// end function

    }
}
