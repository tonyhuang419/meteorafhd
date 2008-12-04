<%@page contentType="text/html"%>
<%@taglib uri='/WEB-INF/cewolf.tld' prefix='cewolf' %>
<html>
<body>
<h2>Hello World!</h2>

<HR>
<jsp:useBean id="pageViews" class="jFreeChart.PageViewCountData"/>
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

</body>
</html>
