function checkLength(minLength,maxlength, fieldID){
    if(fieldID.length<minLength||fieldID.length>maxlength){
     return "";
    }
  }  
 function checkTime(DateOne,DateTwo)   
{    
    var OneMonth = DateOne.substring(5,DateOne.lastIndexOf ('-'));   
    var OneDay = DateOne.substring(DateOne.length,DateOne.lastIndexOf ('-')+1);   
    var OneYear = DateOne.substring(0,DateOne.indexOf ('-'));   
   
    var TwoMonth = DateTwo.substring(5,DateTwo.lastIndexOf ('-'));   
    var TwoDay = DateTwo.substring(DateTwo.length,DateTwo.lastIndexOf ('-')+1);   
    var TwoYear = DateTwo.substring(0,DateTwo.indexOf ('-'));   
   
    var cha=((Date.parse(OneMonth+'/'+OneDay+'/'+OneYear)- Date.parse(TwoMonth+'/'+TwoDay+'/'+TwoYear))/86400000);    
   if(cha<=0){
       return "time is right";
   }  
   else
   {
       if(cha>0){
         return "";
       }
       else{
         return "time is right";
       }
      
   }
   
} 

function compareData(value1,value2){
    if(value1-value2<=0){
     return "data is right";
    }
    else{
     return "";
    }
  }  



