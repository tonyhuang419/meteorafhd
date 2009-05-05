package hf.view.main.task
{
    import flash.display.*;
    import hf.view.*;
    import hf.view.common.*;

    public class LevelAndExperience extends Task
    {

        public function LevelAndExperience()
        {
            this.width = 455;
            this.height = 300;
            title = Language.L["等级和经验"];
            operationAction = closeWindow;
            operationButtonLabel = Language.L["确定"];
            addCancelButton = false;
            var _loc_1:* = MaterialLib.getInstance().getMaterial("LevelAndExperienss") as Sprite;
            if (_loc_1 != null)
            {
                _loc_1.y = 40;
                _loc_1.x = 20;
                _loc_1.mouseEnabled = false;
                taskAddChild(_loc_1);
            }// end if
            return;
        }// end function

    }
}
