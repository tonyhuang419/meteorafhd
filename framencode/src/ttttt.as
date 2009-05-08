package {
	import com.adobe.crypto.MD5;
	
	import flash.display.Sprite;
	import flash.text.TextField;
	
	import hf.model.MData;
	import hf.security.SecurityKey;

	public class ttttt extends Sprite
	{
		public function ttttt()
		{
			var _timeValue:* = MData.getInstance().mainData.serverTime;
            var farmKey:* = MD5.hash(_timeValue + SecurityKey.encodeKey);
			var text:TextField = new TextField();
			trace(SecurityKey.encodeKey+"....."+farmKey);
			text.text = farmKey;
			this.addChild(text);
		}
	}
}