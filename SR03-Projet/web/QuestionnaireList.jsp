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
    <title>Administrator Panel</title>
    <link rel="stylesheet" type="text/css" href="css/myStyle.css" />
    <script src="js/jquery-1.11.0.js" type="text/javascript" charset="utf-8"></script>
    <style>body{background:url("picture/backgroud_login.jpeg"); background-size:100% auto;}</style>
</head>
<body>
<%String topic = request.getParameter("topic");%>
<div>
    <div class="content">
        <div class="tab-block information-tab">
            <div class="personalnformation">
                <div class="personalnformation-content">
                    <div class="personalnformation-content-left">
                        <img src="picture/default_avatar.png"/>
                    </div>
                    <div class="personalnformation-content-right">
                        <p class="personalnformation-content-right-p">Nom：<%=user.getName()%> </p>
                        <p class="personalnformation-content-right-p">Email：<%=user.getEmail()%> </p>
                        <p class="personalnformation-content-right-p">Create Time：2019</p>
                    </div>
                </div>
                <div style="float: right;margin: 150px;">
                    <br>
                    <a href="administrator/QuestionnaireCreate.jsp?topic=<s:property value='#topic.name'/>" class="link_class">New Questionnaire</a>
                </div>
            </div>



            <div class="tab-buttons ">
                <h3 class="tab-button cur" data-tab="one">Management Questionnaires</h3>
            </div>

            <div class="tabs">
                <div class="tab-item active" id="tab-one">
                    <div class="information-tab">
                        <div class="information-top">

                            <div class="information-top-head-left">
                                <p class="information-top-head-p">Questionnaires</p>
                            </div>
                            <div class="information-top-head-right">
                                <p class="information-top-head-p">Delete</p>
                            </div>



                            <s:iterator value="questionnaires">
                            <div class="information-top-content-left">
                                <!-- 这里写action，还没填，要改-->
                                <a href="http://localhost:8080/SR03_Projet_war_exploded/default/actionxxxxx?questionnaire.name=<s:property value='name'/>&questionnaire.status=<s:property value='status'/>&questionnaire.topic=<s:property value='topic'/>" class="information-top-content-p"><s:property value='name'/></a>
                            </div>
                            <div class="information-top-content-right">
                                <!-- 这里用get方法把删除的id加入url里传输，deleteTopic.servlet?id=i -->
                                <a href="" class="information-top-content-p">Delete</a>
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

