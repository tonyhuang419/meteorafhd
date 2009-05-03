package hf.model
{

    public class OpenControl extends Object
    {

        public function OpenControl()
        {
            return;
        }// end function

        public static function open(param1:String) : Boolean
        {
            var name:* = param1;
            var openBoolean:Boolean;
            var xmlData:* = INI.getInstance().data;
            if (xmlData != null)
            {
                var _loc_4:int;
                var _loc_5:* = xmlData.openControl.item;
                var _loc_3:* = new XMLList("");
                for each (_loc_6 in _loc_5)
                {
                    // label
                    var _loc_7:* = _loc_5[_loc_4];
                    with (_loc_5[_loc_4])
                    {
                        if (@name == name)
                        {
                            _loc_3[_loc_4] = _loc_6;
                        }// end if
                    }// end with
                }// end of for each ... in
                if (_loc_3.@open == "true")
                {
                    openBoolean;
                }
                else
                {
                    openBoolean;
                }// end if
            }// end else if
            return openBoolean;
        }// end function

    }
}
