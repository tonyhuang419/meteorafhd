package 
{
    import com.minutes.ui.core.*;
    import flash.display.*;

    private class ThumbBar extends LipiUIComponent
    {
        private var iconButton:Sprite;

        private function ThumbBar()
        {
            addEventListener(UIEvent.RESIZE, thumbBarResize);
            return;
        }// end function

        private function thumbBarResize(param1:UIEvent) : void
        {
            if (iconButton != null)
            {
                iconButton.width = this.width;
                iconButton.height = this.width;
                iconButton.x = 0;
                iconButton.y = (this.height - iconButton.height) / 2;
                if (this.height < this.width)
                {
                    iconButton.visible = false;
                }// end if
            }// end if
            return;
        }// end function

        public function set iconSkin(param1:Class) : void
        {
            if (param1 == null && iconButton != null)
            {
                removeChild(iconButton);
                iconButton = null;
            }
            else
            {
                if (iconButton == null)
                {
                    iconButton = new param1;
                }// end if
                iconButton.width = this.width;
                iconButton.height = this.width;
                iconButton.x = 0;
                iconButton.y = (this.height - iconButton.height) / 2;
                trace(iconButton.x, iconButton.y);
                addChild(iconButton);
            }// end else if
            return;
        }// end function

    }
}
