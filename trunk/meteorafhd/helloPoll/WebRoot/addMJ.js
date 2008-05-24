function addMJ(){
	if(confirm("真的要卖？")){
		var mjName = document.getElementById("addName").value;
		var mjPrice = document.getElementById("addPrice").value;
		var checkNo = document.getElementById("checkNo").value;
		var mjDeal = document.getElementById("addDeal");
		var mjDealValue;
		if(checkNum(mjPrice)==false){
			showMessage("价格请勿以火星文表示，谢谢合作");
		}
		else if( mjName.length==0 ){
			showMessage("啥！没名儿，当爷耍猴的啊，NND……");
		}
		else if( mjName.length<1 ){
			showMessage("名儿太短，不准卖！！！");
		}
		else if( parseInt(mjDeal)<100 ) {
			showMessage("出来卖！没见过有这么便宜得，涨点吧……好歹也对得起自个儿啊");
		}
		else if( checkNo.length<3 ) {
			showMessage("验证码尺寸不对");
		}
		else{
			for(var i=0;i<mjDeal.options.length;i++){
				if(mjDeal.options[i].selected)
					mjDealValue = mjDeal.options[i].value;
			}
			if(parseInt(mjDealValue)==0){
				mjDealValue = "false";
			}
			else{
				mjDealValue = "true";
			}
			CreatePoll.create(mjName,mjPrice,mjDealValue,checkNo,callback);		
		}
	}
	else{}
}

function checkNum(num)
{
     var re = /^[0-9]+.?[0-9]*$/;   //判断字符串是否为数字     //判断正整数 /^[1-9]+[0-9]*]*$/  
     if (!re.test(num)) {     
        return false;
     }
     else{
     	return true;
     }
} 

function callback(data){
	//alert(data);
	showMessage(data); //此函数在guest.js
	//location.reload();
}