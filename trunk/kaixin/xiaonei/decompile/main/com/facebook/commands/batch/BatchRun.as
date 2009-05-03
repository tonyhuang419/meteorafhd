package com.facebook.commands.batch
{
    import com.adobe.serialization.json.*;
    import com.facebook.data.*;
    import com.facebook.data.batch.*;
    import com.facebook.delegates.*;
    import com.facebook.net.*;

    public class BatchRun extends FacebookCall
    {
        public var serial_only:Boolean;
        public var method_feed:BatchCollection;
        public static var SCHEMA:Array = ["method_feed", "serial_only"];
        public static var METHOD_NAME:String = "batch.run";

        public function BatchRun(param1:BatchCollection, param2:Boolean = false)
        {
            super(METHOD_NAME);
            if (param1.length > 20)
            {
                throw new RangeError(InternalErrorMessages.BATCH_RUN_RANGE_ERROR);
            }// end if
            this.method_feed = param1;
            this.serial_only = param2;
            return;
        }// end function

        override function initialize() : void
        {
            var _loc_1:Array;
            var _loc_2:uint;
            var _loc_3:uint;
            var _loc_4:String;
            var _loc_5:FacebookCall;
            var _loc_6:URLVariables;
            _loc_1 = [];
            _loc_2 = method_feed.length;
            _loc_3 = 0;
            while (_loc_3++ < _loc_2)
            {
                // label
                _loc_5 = method_feed.getItemAt(_loc_3) as FacebookCall;
                _loc_5.session = session;
                _loc_5.initialize();
                RequestHelper.formatRequest(_loc_5);
                _loc_6 = _loc_5.args;
                _loc_1.push(_loc_6.toString());
            }// end while
            _loc_4 = JSON.encode(_loc_1);
            applySchema(SCHEMA, _loc_4, serial_only);
            super.initialize();
            super.initialize();
            return;
        }// end function

    }
}
