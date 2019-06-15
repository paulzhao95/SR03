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
<%String topic = request.getParameter("topic");
    System.out.println(topic);
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


                    <a href="login.action?email=<%=user.getEmail()%>&password=<%=user.getPassword()%>&type=<%=user.getType()%>" class="link_class">Main interface</a>


                    <br>
                    <a href="http://localhost:8080/SR03_Projet_war_exploded/administrator/QuestionnaireCreate.jsp?topic=<s:property value="topic"/>" class="link_class">New Questionnaire</a>
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
<%--                                <a href="http://localhost:8080/SR03_Projet_war_exploded/default/getQuestions?questionnaireId=<s:property value='questionnaireId'/>&status=<s:property value='status'/>&topic=<s:property value='topic'/>&name=<s:property value='name'/>" class="information-top-content-p"><s:property value='name'/></a>--%>
                                <a href="<s:url action="getQuestions"><s:param name="topic" value="topic"></s:param><s:param name="questionnaireId" value="questionnaireId"></s:param><s:param name="pageNumber" value="1"></s:param> </s:url>" class="information-top-content-p"><s:property value='name'/></a> |
                                <a href="getAttemptsByQuestionnaire.action?topic=<s:property value='topic'/>&questionnaireId=<s:property value="questionnaireId"/>&pageNumber=<s:property value="1"/> " class="information-top-content-p">Check attempts</a>


                            </div>
                            <div class="information-top-content-right">
                                <!-- 这里用get方法把删除的id加入url里传输，deleteTopic.servlet?id=i -->
                                <a href="http://localhost:8080/SR03_Projet_war_exploded/default/deleteQuestionnaire?questionnaire.questionnaireId=<s:property value='questionnaireId'/>&questionnaire.topic=<s:property value='topic'/>" class="information-top-content-p">Delete</a> |
                                <a href="<s:url value=" QuestionnaireInfoChangeAdmin.jsp"><s:param name="questionnaireId" value="questionnaireId"></s:param><s:param name="status" value="status"></s:param><s:param name="name" value="name"></s:param><s:param name="topic" value="topic"></s:param></s:url>">Update</a>
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

