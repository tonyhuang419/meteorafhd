function   Chinese(num)     //将阿拉伯数字翻译成中文的大写数字   
  	{   
          if(!/^\d*(\.\d*)?$/.test(num)){alert("Number   is   wrong!");   return   "Number   is   wrong!";}   
    
          var   AA   =   new   Array("零","壹","贰","叁","肆","伍","陆","柒","捌","玖");   
          var   BB   =   new   Array("","拾","佰","仟","万","亿","点","");   
          var CC = new Array("角", "分", "厘");
            
          var   a   =   (""+   num).replace(/(^0*)/g,   "").split("."),   k   =   0,   re   =   "";   
    
          for(var   i=a[0].length-1;   i>=0;   i--)   
          {   
                  switch(k)   
                  {   
                          case   0   :   re   =   BB[7]   +   re;   break;   
                          case   4   :   if(!new   RegExp("0{4}\\d{"+   (a[0].length-i-1)   +"}$").test(a[0]))   
                                            re   =   BB[4]   +   re;   break;   
                          case   8   :   re   =   BB[5]   +   re;   BB[7]   =   BB[5];   k   =   0;   break;   
                  }   
                  if(k%4   ==   2   &&   a[0].charAt(i+2)   !=   0   &&   a[0].charAt(i+1)   ==   0)   re   =   AA[0]   +   re;   
                  if(a[0].charAt(i)   !=   0)   re   =   AA[a[0].charAt(i)]   +   BB[k%4]   +   re;   k++;   
          }   
          re+="元";
    
         if(a.length>1) //加上小数部分(如果有小数部分)
   		 {
       		 for(var i=0; i<a[1].length; i++)
        		{
         		 re += AA[a[1].charAt(i)] + CC[i];
         		 if(i==2) break;
        		}
    	}  
          re+="整";
          return   re;   
  	}  
  	
  //让输入框只接受数字、小数
function JHshNumberText(){
  if ( !(
((window.event.keyCode > 47) && (window.event.keyCode <= 58)) 
  	|| (window.event.keyCode == 44)  
	|| (window.event.keyCode == 46) ))
  	{
  		window.event.keyCode = 0 ;
 	}
}   