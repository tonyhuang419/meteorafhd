	
	function clearOptions(selObj){
		selObj.options.length = 0;
	}
	
	function addOption(selObj,value,text){
		var option = document.createElement("OPTION");
		option.value=value;
		option.text=text;
		selObj.options.add(option);
	}
	
	//value是不是在数组里
	 function isInArray(valueArray,value){
	 	for(var i=0;i<valueArray.length;i++){
			if(value == valueArray[i]){
				return true;
			}
		}
		return false;
	 }
	 //删除指定的选项
	 function removeSelectOption(selObj,opValues){
	 	for(var i=0;i<selObj.options.length;i++){
	 		//从列表框中删除
	 		if(isInArray(opValues,selObj.options[i].value)){
	 			// 将这个选项删除，i减一，因为选项删除后，后面的选项会自动前移一个
	 			selObj.options[i]=null;
	 			i--;
	 		}
	 	} 	
	 }
	 
	 /**
	  * 通过url，用ajax获得ajson对象填充select
	  * 依赖mootools-1.2-core-jm.js
	  * jsonObjGetUrl 获得json对像的url，数据放在json根对象的jsonData属性了，jsonData是一个对象数组
	  * selectForm 下拉表单对象
	  * listKey 做为value值的对象属性名称
	  * listValue 作为text值的对象属性名称
	  * option 列表的选项
	  */
	 function ajaxSetSelectOptions(jsonObjGetUrl,selectForm,listKey,listValue,option){
		var jsonRequest = new Request.JSON({url:jsonObjGetUrl, onComplete: function(jsonObj){
			if(jsonObj!=null && jsonObj.jsonData !=null ){
				 clearOptions(selectForm);
				 if(option != null){
				 	if(option.value!=null && option.text!=null){
				 		addOption(selectForm,option.value,option.text);
				 	}
				 }
				 for(var i=0;i<jsonObj.jsonData.length;i++){
				 	addOption(selectForm,eval("jsonObj.jsonData["+i+"]."+listKey),eval("jsonObj.jsonData["+i+"]."+listValue));
				 }
				 if(option != null && option.defaultValue != null){
					selectForm.value = option.defaultValue;
				 }
			 }
		}}).get({randerNum:Math.random()});	 	
	 	
	 }