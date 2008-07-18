function   Chinese(num)     //将阿拉伯数字翻译成中文的大写数字   
  	{   
          if(!/^\d*(\.\d*)?$/.test(num)){alert("Number   is   wrong!");   return   "Number   is   wrong!";}   
    
          var   AA   =   new   Array("零","壹","贰","叁","肆","伍","陆","柒","捌","玖");   
          var   BB   =   new   Array("","拾","百","千","万","亿","点","");   
            
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
    
          if(a.length>1)   //加上小数部分(如果有小数部分)   
          {   
                  re   +=   BB[6];   
                  for(var   i=0;   i<a[1].length;   i++)   re   +=   AA[a[1].charAt(i)];   
          }   
          re+="圆整";
          return   re;   
  	}  
  	
  //让输入框只接受数字、小数
function JHshNumberText(){
  if ( !(((window.event.keyCode >= 48) && (window.event.keyCode <= 57)) || !(window.event.keyCode == 188)  || (window.event.keyCode == 46)  || (window.event.keyCode == 45)))
  {
  window.event.keyCode = 0 ;
  }
}   