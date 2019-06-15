<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
<%String checked = "checked";
    String score = "100分";%>
<div>
    <div class="content">
        <div class="tab-block information-tab">
            <div class="personalnformation">
                <div class="personalnformation-content">
                    <div class="personalnformation-content-left">
                        <img src="../picture/default_avatar.png"/>
                    </div>
                </div>
                <div style="float: right;margin: 100px;width: 700px">
                    <p>Socre: <s:property value='attempt.fullMarks'/> </p>
<br>

                    <p>Durée du passage: <s:property value='attempt.durationInSeconds'/> s</p>
                </div>
                <div style="float: right;margin: 100px;">
                    <a href="login.action?email=<%=user.getEmail()%>&password=<%=user.getPassword()%>&type=<%=user.getType()%>" class="link_class">Main interface</a>
                    <br>
                </div>
            </div>



            <div class="tab-buttons ">
                <h3 class="tab-button cur" data-tab="one">History Questions List</h3>
            </div>

            <div class="tabs">
                <div class="tab-item active" id="tab-one">
                    <div class="information-tab">
                        <div class="information-top">

                            <div class="information-top-head-left">
                                <p class="information-top-head-p">Questions</p>
                            </div>
                            <div class="information-top-head-right">
                                <p class="information-top-head-p">User's Answer</p>
                            </div>


                            <s:iterator value="questionnaire.questions" status="question_status">
                                <div style="border: 1px solid #d8d8d8;width:800px; height: 200px;">
                                    <p><s:property value='description'/></p>
                                    <br>
                                    <s:iterator value="choices" var="id" status="status">
                                        <s:if test="%{#id.description.length != 0}">
                                            <input type="radio" name="answer<s:property value="#question_status.index"/><s:property value="#status.index"/>" disabled <s:if test="%{#id.isRight == true}"> checked = "<%=checked%>" </s:if>/><s:property value='description'/>
                                            <br>
                                        </s:if>
                                    </s:iterator>
                                </div>
                                <div style="border: 1px solid #d8d8d8;width:200px; height: 200px;">
                                    <p>Number&nbsp;&nbsp; <s:property value='attempt.userChoices[#question_status.index].choiceId'/></p>
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

