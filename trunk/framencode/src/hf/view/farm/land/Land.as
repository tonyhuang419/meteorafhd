package hf.view.farm.land
{
    import flash.display.*;
    import hf.view.*;

    public class Land extends Sprite
    {
        private var aFarmlandS2:DisplayObject;
        private var lib:MaterialLib;
        private var aFarmlandG:DisplayObject;
        private var aFarmlandG2:DisplayObject;
        private var _status:String;
        private var aFarmlandS:DisplayObject;
        public static const FERTILE:String = "fertile";
        public static const ARID_FERTILE:String = "aridFertile";
        public static const SUITABLE:String = "suitable";
        public static const ARID:String = "arid";

        public function Land()
        {
            lib = MaterialLib.getInstance();
            aFarmlandS = lib.getMaterial("FarmlandS") as DisplayObject;
            addChild(aFarmlandS);
            _status = SUITABLE;
            return;
        }// end function

        public function set status(param1:String) : void
        {
            if (_status == param1)
            {
                return;
            }// end if
            if (param1 == SUITABLE)
            {
                aFarmlandS.visible = true;
                if (aFarmlandG != null)
                {
                    removeChild(aFarmlandG);
                    aFarmlandG = null;
                }// end if
                if (aFarmlandS2 != null)
                {
                    removeChild(aFarmlandS2);
                    aFarmlandS2 = null;
                }// end if
                if (aFarmlandG2 != null)
                {
                    removeChild(aFarmlandG2);
                    aFarmlandG2 = null;
                }// end if
            }
            else if (param1 == ARID)
            {
                aFarmlandS.visible = false;
                aFarmlandG = lib.getMaterial("FarmlandG") as DisplayObject;
                addChild(aFarmlandG);
                if (aFarmlandS2 != null)
                {
                    removeChild(aFarmlandS2);
                    aFarmlandS2 = null;
                }// end if
                if (aFarmlandG2 != null)
                {
                    removeChild(aFarmlandG2);
                    aFarmlandG2 = null;
                }// end if
            }
            else if (param1 == FERTILE)
            {
                aFarmlandS.visible = false;
                if (aFarmlandG != null)
                {
                    removeChild(aFarmlandG);
                    aFarmlandG = null;
                }// end if
                aFarmlandS2 = lib.getMaterial("FarmlandS2") as DisplayObject;
                addChild(aFarmlandS2);
                if (aFarmlandG2 != null)
                {
                    removeChild(aFarmlandG2);
                    aFarmlandG2 = null;
                }// end if
            }
            else if (param1 == ARID_FERTILE)
            {
                aFarmlandS.visible = false;
                if (aFarmlandG != null)
                {
                    removeChild(aFarmlandG);
                    aFarmlandG = null;
                }// end if
                if (aFarmlandS2 != null)
                {
                    removeChild(aFarmlandS2);
                    aFarmlandS2 = null;
                }// end if
                aFarmlandG2 = lib.getMaterial("FarmlandG2") as DisplayObject;
                addChild(aFarmlandG2);
            }// end else if
            _status = param1;
            return;
        }// end function

        public function get status() : String
        {
            return _status;
        }// end function

    }
}
