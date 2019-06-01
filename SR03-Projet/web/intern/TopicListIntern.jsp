<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="model.User" %>
<html>
<head>
    <meta charset="UTF-8">
    <%
        String path = request.getRequestURI();
        String basePath = request.getScheme() + "://"
                +request.getServerName() + ":" + request.getServerPort()
                + path;

        User user = (User)session.getAttribute("user");
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
                        <p class="personalnformation-content-right-p">Nom：<%=user.getName()%> </p>
                        <p class="personalnformation-content-right-p">Email：<%=user.getEmail()%> </p>
                        <p class="personalnformation-content-right-p">Create Time：2019</p>
                    </div>
                </div>
                <div style="float: right;margin: 150px;">
                    <br>
                    <a href="internGetTopics.action" class="link_class">Do Questionnaire</a>
                </div>
            </div>



            <div class="tab-buttons ">
                <h3 class="tab-button cur" data-tab="one">History Topic List</h3>
            </div>

            <div class="tabs">
                <div class="tab-item active" id="tab-one">
                    <div class="information-tab">
                        <div class="information-top">

                            <div class="information-top-head-left">
                                <p class="information-top-head-p">Topics</p>
                            </div>



                            <s:iterator value="topics">
                                <div class="information-top-content-left">
                                    <a href="http://localhost:8080/SR03_Projet_war_exploded/getQuestionnaires.action?topic=<s:property value='name'/>" class="information-top-content-p"><s:property value='name'/></a>
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

