<!DOCTYPE html>
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
            if(!user.getType().toString().equals("Administrator")) {
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
    <meta charset="utf-8" />
    <title>Changement du Topic</title>
    <link rel="stylesheet" type="text/css" href="../css/myStyle2.css" />
</head>

<body>
<%String topic = request.getParameter("topic");%>
<img class="bgone" src="../picture/1.jpg" />
<img class="pic" src="../picture/a.png" />

<div class="table">
    <%String hint = "Name of Topic";%>
    <div class="wel">Cr&eacute;er un nouveau topic</div>
    <form action="addTopic.action">
        <div class="password">
            <div id="yonghu"><img src="../picture/yhm.png" /></div>
            <input type="text" name="topic.name" placeholder=<%=hint%>/>
        </div>
        <input class="btn" type="submit" name="Validation" value="Validation" />
    </form>
</div>

</body>
</html>
