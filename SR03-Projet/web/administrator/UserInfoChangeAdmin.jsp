<%@ page import="model.User" %>
<!doctype html>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html lang="zh">
<head>
    <%
        String path = request.getRequestURI();
        String basePath = request.getScheme() + "://"
                +request.getServerName() + ":" + request.getServerPort()
                + path;
        User currentUser = new User();
        if (session.getAttribute("user") != null){
            currentUser  = (User)session.getAttribute("user");
            if(!currentUser.getType().toString().equals("Administrator")) {
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
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
    <link type="text/css" rel="stylesheet" href="../css/myStyle.css">
    <title>Créer votre compte</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.1/css/materialize.min.css">
    <style type="text/css">
        html,
        body {
            height: 100%;
        }
        html {
            display: table;
            margin: auto;
        }
        body {
            background-image: url("../picture/backgroud_login.jpeg");
        }
        body {
            display: table-cell;
            vertical-align: middle;
            color:#404d5b;
        }
        .margin {
            margin: 0 !important;
        }
        .card-panel{ min-width:350px;}
    </style>
</head>
<body class="blue">

<%--<%String user = request.getParameter("name");--%>
<%--    String email = request.getParameter("email");--%>
<%--    String password = request.getParameter("password");--%>
<%--    String telephone = request.getParameter("tel");--%>
<%--    String company = request.getParameter("company");--%>
<%--    String creating_time = "2019";--%>
<%--    Boolean status = request.getParameter("status").equals("true");--%>
<%--    String type = request.getParameter("type");--%>
<% String check = "checked";%>
<%
//    User user = (User) request.getAttribute("user");
%>

<div id="login-page" class="row">
    <div class="col s12 z-depth-6 card-panel">
        <form action="updateUser.action" class="login-form">
            <div class="row">
                <div class="input-field col s12 center">
                    <img src="../picture/UTC_logo.png" alt="" class="responsive-img valign profile-image-login">
                    <p class="center login-form-text">Changez l'information de ce compte</p>
                </div>
            </div>
            <div class="row margin">
                <div class="input-field col s12">
                    <i class="mdi-social-person-outline prefix"></i>
                    <input id="username" name = "user.name" type="text" class="validate" onchange="changeValue()" value = "<s:property value="user.name"/>">
                    <label for="username" class="center-align"></label>
                </div>
            </div>
            <div class="row margin">
                <div class="input-field col s12">
                    <i class="mdi-communication-email prefix"></i>
                    <input id="email" name = "user.email" type="email" class="validate" onchange="changeValue()" value = "<s:property value="user.email"/>">
                    <label for="email" class="center-align"></label>
                </div>
            </div>
            <div class="row margin">
                <div class="input-field col s12">
                    <i class="mdi-action-lock-outline prefix"></i>
                    <input id="password" name = "user.password" type="text" onchange="changeValue()" class="validate" value = "<s:property value="user.password"/>">
                    <label for="password"></label>
                </div>
            </div>
            <div class="row margin">
                <div class="input-field col s12">
                    <i class="mdi-social-person-outline prefix"></i>
                    <input id="telephone" name = "user.tel" type="text" class="validate" onchange="changeValue()" value = "<s:property value="user.tel"/>">
                    <label for="telephone"></label>
                </div>
            </div>
            <div class="row margin">
                <div class="input-field col s12">
                    <i class="mdi-social-person-outline prefix"></i>
                    <input id="company" name = "user.company" type="text" onchange="changeValue()" class="validate" value = "<s:property value="user.company"/>">
                    <label for="company"></label>
                </div>
            </div>
            <div class="row margin">
                <div class="input-field col s12">
                    <i class="mdi-social-person-outline prefix"></i>
                    <input type="radio" id="Amdin" name="user.type" value="Administrator"
                            <%if (user.getType().toString().equals("Administrator")){%> <%=check%> <%}%>/>
                    <label for="Amdin">Amdin</label>
                    <input type="radio" id="Intern" name="user.type" value="Intern"
                            <%if (user.getType().toString().equals("Intern")){%> <%=check%> <%}%>/>
                    <label for="Intern">Intern</label>
                </div>
            </div>
            <div class="row margin">
                <div class="input-field col s12">
                    <i class="mdi-social-person-outline prefix"></i>
                    <input type="radio" id="Status_true" name="user.status" value="true"
                            <%if (user.getStatus()){%> <%=check%> <%}%>/>
                    <label for="Status_true">Active</label>
                    <input type="radio" id="Status_false" name="user.status" value="false"
                            <%if (!user.getStatus()){%> <%=check%> <%}%>/>
                    <label for="Status_false">Inactive</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s12">
                    <input type="submit" value = "Validation" class = "btn waves-effect waves-light col s12">
                </div>
            </div>
        </form>
    </div>
</div>
<script>
    function changeValue(obj){
        $(obj).attr("value",$(obj).val());
    }
</script>
</body>
</html>