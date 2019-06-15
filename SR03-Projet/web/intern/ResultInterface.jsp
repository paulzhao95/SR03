<%@ page import="model.Attempt" %>
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
    <%
        Attempt attempt = (Attempt)request.getSession().getAttribute("attempt");
        int attemptId = attempt.getId();
        int fullMarks = attempt.getFullMarks();
        int score = attempt.getScore();
        int duration = attempt.getDurationInSeconds();
        String useremail = attempt.getUserEmail();
        String topicID = attempt.getTopicName();
        int questionnaireId = attempt.getQuestionnaireId();

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
                        <p class="personalnformation-content-right-p">Your score：<%=score%> </p>
                        <p class="personalnformation-content-right-p">Duration：<%=duration%> Seconds</p>
                    </div>
                </div>
                <div style="float: right;margin: 150px;">
                    <br>
                    <!-- 这里我的想法是做一个连接，可以查看这次答题的每一道题的对错情况。可以直接调用QuestionListInter.jsp这个jsp作为显示 -->
                    <a href="internGetAttempt.action?attemptId=<%=attemptId%>&topic=<%=topicID%>&questionnaireId=<%=questionnaireId%>" class="link_class">Details</a>
                </div>
            </div>

        </div>
    </div>
</div>
</body>
</html>