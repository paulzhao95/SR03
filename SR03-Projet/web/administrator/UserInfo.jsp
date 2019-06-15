<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <meta charset="UTF-8">
    <title>Administrator Panel</title>
    <link rel="stylesheet" type="text/css" href="../css/myStyle.css" />
    <script src="../js/jquery-1.11.0.js" type="text/javascript" charset="utf-8"></script>
    <style>body{background:url("../picture/backgroud_login.jpeg"); background-size:100% auto;}</style>
</head>
<body>
<%String user = request.getParameter("name");
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    String telephone = request.getParameter("tel");
    String company = request.getParameter("company");
    String creating_time = "2019";
    Boolean status = request.getParameter("status").equals("true") ;
    String type = request.getParameter("type");%>
<div>
    <div class="content">
        <div class="tab-block information-tab">
            <div class="personalnformation">
                <div class="personalnformation-content">
                    <div class="personalnformation-content-left">
                        <img src="../picture/default_avatar.png"/>
                    </div>
                    <div>
                        <p>Nom: <%=user%> </p>
                        <p>Email: <br><%=email%> </p>
                        <p>Password: <%=password%> </p>
                        <p>Telephone: <%=telephone%> </p>
                        <p>Company: <%=company%> </p>
                        <p>Status: <%=status%> </p>
                        <p>Type: <%=type%></p>
                    </div>
                </div>
                <div style="float: right;margin: 150px;">
                    <a href="http://localhost:8080/SR03_Projet_war_exploded/default/updateUser?user.name=<%=user%>&user.password=<%=password%>&user.email=<%=email%>&user.tel=<%=telephone%>&user.company=<%=company%>&user.type=<%=type%>&user.status=<%=status%>" class="link_class">Change Status</a>
                    <br>
                    <a href="User-Topic_List.jsp" class="link_class">RETURN</a>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>

