<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib uri='/WEB-INF/tlds/cewolf.tld' prefix='cewolf' %>
<html>
<body>
<h2>Hello cewolf</h2>

<HR>
<jsp:useBean id="timeData" class="com.exam.action.demo.CewolfTwoAction"/>
<cewolf:chart 
	id="timeChart" 
    title="TimeSeries" 
    type="timeseries" 
    xaxislabel="x-values"  
    yaxislabel="y-values">  
    <cewolf:colorpaint color="#EEEEFF"/> 
    <cewolf:data>
        <cewolf:producer id="timeData"/> 
    </cewolf:data>
  </cewolf:chart> 
<p>
<cewolf:img chartid="timeChart" renderer="../cewolf" width="400" height="300"/>
<P>
<HR>

</body>
</html>
