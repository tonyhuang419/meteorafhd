<%@page contentType="text/html"%>
<%@taglib uri='/WEB-INF/tlds/cewolf.tld' prefix='cewolf' %>
<html>
<body>
<h2>Hello cewolf</h2>

<HR>
<jsp:useBean id="pageViews" class="com.exam.action.demo.CewolfAction"/>
<cewolf:chart 
    id="line" 
    title="Page View Statistics" 
    type="line" 
    xaxislabel="Page" 
    yaxislabel="Views">
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
