function addMJ(){
	if(confirm("���Ҫ����")){
		var mjName = document.getElementById("addName").value;
		var mjPrice = document.getElementById("addPrice").value;
		var checkNo = document.getElementById("checkNo").value;
		var mjDeal = document.getElementById("addDeal");
		var mjDealValue;
		if(checkNum(mjPrice)==false){
			showMessage("�۸������Ի����ı�ʾ��лл����");
		}
		else if( mjName.length==0 ){
			showMessage("ɶ��û��������үˣ��İ���NND����");
		}
		else if( mjName.length<1 ){
			showMessage("����̫�̣���׼��������");
		}
		else if( parseInt(mjDeal)<100 ) {
			showMessage("��������û��������ô���˵ã��ǵ�ɡ����ô�Ҳ�Ե����Ը�����");
		}
		else if( checkNo.length<3 ) {
			showMessage("��֤��ߴ粻��");
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
     var re = /^[0-9]+.?[0-9]*$/;   //�ж��ַ����Ƿ�Ϊ����     //�ж������� /^[1-9]+[0-9]*]*$/  
     if (!re.test(num)) {     
        return false;
     }
     else{
     	return true;
     }
} 

function callback(data){
	//alert(data);
	showMessage(data); //�˺�����guest.js
	//location.reload();
}