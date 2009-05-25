package com.minutes.ui.control
{
    import com.minutes.ui.core.*;

    public class ToggleButton extends LipiButton
    {
        private var _selectedSkin:LipiSkin;
        private var _selected:Boolean = false;
        private var _defaultSkin:LipiSkin;

        public function ToggleButton()
        {
            return;
        }// end function

        public function get defaultSkin() : LipiSkin
        {
            return _defaultSkin;
        }// end function

        public function set selectedSkin(param1:LipiSkin) : void
        {
            _selectedSkin = param1;
            if (selected)
            {
                bgSkin = selectedSkin;
            }
            else
            {
                bgSkin = defaultSkin;
            }// end else if
            return;
        }// end function

        public function set selected(param1:Boolean) : void
        {
            _selected = param1;
            if (param1)
            {
                bgSkin = selectedSkin;
            }
            else
            {
                bgSkin = defaultSkin;
            }// end else if
            return;
        }// end function

        public function get selectedSkin() : LipiSkin
        {
            return _selectedSkin;
        }// end function

        public function get selected() : Boolean
        {
            return _selected;
        }// end function

        public function set defaultSkin(param1:LipiSkin) : void
        {
            _defaultSkin = param1;
            if (selected)
            {
                bgSkin = selectedSkin;
            }
            else
            {
                bgSkin = defaultSkin;
            }// end else if
            return;
        }// end function

    }
}
