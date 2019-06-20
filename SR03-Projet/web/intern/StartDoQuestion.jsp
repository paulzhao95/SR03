<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="model.User" %>
<html>
<head>
    <%
        String path = request.getRequestURI();
        String basePath = request.getScheme() + "://"
                +request.getServerName() + ":" + request.getServerPort()
                + path;
        User user = new User();
        if (session.getAttribute("user") != null){
            user = (User)session.getAttribute("user");
            if(!user.getType().toString().equals("Intern")) {
    %>
    <script type="text/javascript" language="javascript">
        alert("Warning: You are not allowed to visit this page !!!!!!!");
        window.document.location.href="http://localhost:8080/SR03_Projet_war_exploded/index.jsp";
    </script>
    <%
        }}else{%>
    <script type="text/javascript" language="javascript">
        alert("Warning: You are not allowed to visit this page !");
        window.document.location.href="http://localhost:8080/SR03_Projet_war_exploded/index.jsp";
    </script>
    <%}
    %>
    <base href="<%=basePath%>">
    <title>Start doing questions</title>
</head>
<body>
<div style="text-align:center;">
    <a href="http://localhost:8080/SR03_Projet_war_exploded/internStartAttempt.action?questionnaireId=<s:property value='questionnaireId'/>&topic=<s:property value='topic'/>" style="display:block;margin:40px auto 0 auto; line-height:32px; font-size:20px; font-weight:bold">
        Please complete the test questions carefully. <br/>
        Are you ready?<br/><br/>
        <span><img src="../picture/start.jpg"></span></a>
</div>

</body>
</html>