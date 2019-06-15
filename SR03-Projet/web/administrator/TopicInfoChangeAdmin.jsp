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

<img class="bgone" src="../picture/1.jpg" />
<img class="pic" src="../picture/a.png" />

<div class="table">
    <%String topic = "exemple1";%>
    <div class="wel">Changez le nom de ce topic</div>
    <form action="">
    <div class="password">
        <div id="yonghu"><img src="../picture/yhm.png" /></div>
        <input type="text" name="topic" value="<%=topic%>" onchange="changeValue()" />
    </div>
    <input class="btn" type="button" name="Validation" value="Validation" />
    </form>
</div>
<script>
    function changeValue(obj){
        $(obj).attr("value",$(obj).val());
    }
</script>
</body>
</html>
