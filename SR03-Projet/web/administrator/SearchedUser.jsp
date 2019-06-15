<%@ page import="java.util.ArrayList" %>
<%@ page import="model.User" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
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
    <base href="<%=basePath%>">
    <title>Administrator Panel</title>
    <link rel="stylesheet" type="text/css" href="../css/myStyle.css" />
    <script src="../js/jquery-1.11.0.js" type="text/javascript" charset="utf-8"></script>
    <style>body{background:url("../picture/backgroud_login.jpeg"); background-size:100% auto;}</style>

</head>
<body>

<div>
    <div class="content">
        <div class="tab-block information-tab">
            <div class="personalnformation">
                <div class="personalnformation-content">
                    <div class="personalnformation-content-left">
                        <img src="../picture/default_avatar.png"/>
                    </div>
                    <div class="personalnformation-content-right">
                        <%--                        <p class="personalnformation-content-right-p">Nom：<%=user.getName()%> </p>--%>
                        <p class="personalnformation-content-right-p">Nom：<s:property value="user.name"/> </p>
                        <p class="personalnformation-content-right-p">Email：<%=user.getEmail()%> </p>
                        <p class="personalnformation-content-right-p">Create Time：2019</p>
                    </div>
                </div>
                <div style="float: right;margin: 150px;">
                    <a href="logout.action" class="link_class">Log out</a>
                    <br>
                    <a href="AddUser.jsp" class="link_class">Add new Users</a>
                </div>
            </div>



            <div class="tab-buttons ">
                <h3 class="tab-button" data-tab="two">Management Users</h3>
            </div>

            <div class="tabs">
                <div class="tab-item active" id="tab-one">


                    <div class="information-tab ">
                        <div class="information-record">
                            <div class="information-top-head-left">
                                <p class="information-top-head-p">User Name</p>
                            </div>
                            <div class="information-top-head-right">
                                <p class="information-top-head-p">Delete | Check</p>
                            </div>


                            <s:iterator value="users">
                                <div class="information-top-content-left">
                                    <a href="getUser.action?email=<s:property value='email'/>" class="information-top-content-p"><s:property value='name'/></a>


                                </div>
                                <div class="information-top-content-right">
                                    <a href="<s:url value="deleteUser.action" ><s:param name="user.email" value="email"></s:param></s:url>" class="information-top-content-p">Delete</a> |
                                    <a href="getAttemptsByUser.action?email=<s:property value='email'/>&pageNumber_Topice=<s:property value="1"/> " class="information-top-content-p">Check attempts</a>

                                </div>
                            </s:iterator>
                        </div>
                    </div>

                </div>
            </div>



        </div>
    </div>
</div>
</div>

</body>
</html>

