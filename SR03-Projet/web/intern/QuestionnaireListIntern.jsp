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
    <title>Administrator Panel</title>
    <link rel="stylesheet" type="text/css" href="../css/myStyle.css" />
    <script src="../js/jquery-1.11.0.js" type="text/javascript" charset="utf-8"></script>
    <style>body{background:url("../picture/backgroud_login.jpeg"); background-size:100% auto;}</style>
</head>
<body>
<%String topic = request.getParameter("topic");
    System.out.println(topic);
    String score = "100分";
%>
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
                    <a href="login.action" class="link_class">Log out</a>

                    <br>
                    <a href="internGetTopics.action" class="link_class">Do Questionnaire</a>
                </div>
            </div>



            <div class="tab-buttons ">
                <h3 class="tab-button cur" data-tab="one">History Questionnaires List</h3>
            </div>

            <div class="tabs">
                <div class="tab-item active" id="tab-one">
                    <div class="information-tab">
                        <div class="information-top">

                            <div class="information-top-head-left">
                                <p class="information-top-head-p">Questionnaires</p>
                            </div>
                            <div class="information-top-head-right">
                                <p class="information-top-head-p">Scores</p>
                            </div>



                            <s:iterator value="attempts">
                                <div class="information-top-content-left">
                                    <!-- 这里写action，还没填，要改-->
                                    <a href="http://localhost:8080/SR03_Projet_war_exploded/default/internGetAttempt?attemptId=<s:property value="id"/>" class="information-top-content-p"><s:property value='questionnaireName'/> | Strat time: <s:property value='startTime'/></a>
                                </div>
                                <div class="information-top-content-right">
                                    <!-- 这里填分数-->
                                    <p><s:property value='fullMarks'/></p>
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

