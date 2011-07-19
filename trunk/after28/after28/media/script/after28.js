function getSelectedValue(selectId){
	var s = document.getElementById(selectId);
	var index = s.selectedIndex;
	return s.options[index].value;
}

function validateEmail(elementValue){  
   var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;  
   return emailPattern.test(elementValue);  
 } 

function getById(_id){
	return document.getElementById(_id);
}

function calcNextTime(){
	//last happen date
	var lastDate = getById("lastDate").value;
	//cycle
	var cycle = getSelectedValue("cycle");
	//predays
	var predays = getSelectedValue("predays");
	if(lastDate!=''){
		var d = new Date(Date.parse(lastDate.replace(/-/g,"/")));
		d.setDate(d.getDate()+parseInt(cycle));
		getById("nexthappen").innerHTML = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
		d.setDate(d.getDate()-parseInt(predays));
		getById("sendDate").value = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
	}
	lightOnSendBtn();
}

function lightOnSendBtn(){
	getById("send_info").disabled=true;
	if(getById("lastDate").value=='')
		return;
	var mail = getById("email").value;
	if( !validateEmail(mail) )
		return;
	getById("send_info").disabled=false;
}
