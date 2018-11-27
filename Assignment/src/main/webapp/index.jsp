<html>
<style>
legend {
    display: block;
    padding-left: 2px;
    padding-right: 2px;
    border: none;
}
</style>
<body>
<%
    String path=application.getContextPath();    
    response.sendRedirect(path+"/home");
%>
</body>
</html>
