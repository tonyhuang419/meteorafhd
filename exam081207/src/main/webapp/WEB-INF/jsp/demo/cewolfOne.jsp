<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib uri='/WEB-INF/tlds/cewolf.tld' prefix='cewolf' %>
<html>
<body>
<h2>Hello cewolf</h2>

<HR>
<jsp:useBean id="pageViews" class="com.exam.action.demo.CewolfOneAction"/>
<cewolf:chart 
    id="line" 
    title="标题" 
    type="line" 
    xaxislabel="Page" 
    yaxislabel="Y坐标">
    <cewolf:data>
        <cewolf:producer id="pageViews"/>
    </cewolf:data>
</cewolf:chart>
<p>
<cewolf:img chartid="line" renderer="../cewolf" width="400" height="300"/>
<P>
<HR>

</body>
</html>
