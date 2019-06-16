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
    <style>
        .bar6 button {
            background: #c5464a;
            border-radius: 0 5px 5px 0;
            width: 80px;
            height: 30px;
            top: 0;
            right: 0;
            display: inline-block;
        }
        .bar6 button:before {
            content: "Search";
            font-size: 13px;
            color: #F9F0DA;
        }</style>
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
                    <a href="login.action?email=<%=user.getEmail()%>&password=<%=user.getPassword()%>&type=<%=user.getType()%>" class="link_class">Main interface</a>
                </div>
            </div>



                <div class="tab-buttons ">
                <h3 class="tab-button cur" data-tab="one">History Questionnaires List</h3>
            </div>






            <br>
            <div>
                <div style="text-align:center;">
                    <div class="search bar6">
                        <form action="getAttemptsByQuestionnaire.action">
                            <input type="text" style="height: 40px;width: 400px;display: inline-block;" name = "userEmailSearched" placeholder="Search...">

                            <input type="hidden" name="topic" value="<s:property value="topic"/>">
                            <input type="hidden" name="questionnaireId" value="<s:property value="questionnaireId"/>">

                            <button type="submit" value="Validation"></button>
                        </form>
                    </div>
                </div>
            </div>










            <div class="tabs">
                <div class="tab-item active" id="tab-one">
                    <div class="information-tab">
                        <div class="information-top">

                            <div class="information-top-head-left">
                                <p class="information-top-head-p">Users</p>
                            </div>
                            <div class="information-top-head-right">
                                <p class="information-top-head-p">Scores</p>
                            </div>



                            <s:iterator value="attempts" var = "id">
                                <div class="information-top-content-left">
                                    <!-- 这里写action，还没填，要改-->
                                    <a href="getAttempt.action?attemptId=<s:property value="id"/>" class="information-top-content-p"><s:property value='#id.userEmail'/> | Strat time: <s:property value='#id.startTime'/></a>
                                </div>
                                <div class="information-top-content-right">
                                    <!-- 这里填分数-->
                                    <p><s:property value='#id.fullMarks'/></p>
                                </div>
                            </s:iterator>


                        </div>
                    </div>
                </div>
            </div>




            <div style="text-align:center">
                <br>
                <a href="<s:url action="getAttemptsByQuestionnaire"><s:param name="pageNumber" value="1"></s:param><s:param name="topic" value="topic"></s:param><s:param name="questionnaireId" value="questionnaireId"></s:param></s:url>">First Page</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                <s:if test="%{pageNumber != 1}">
                    <a href="<s:url action="getAttemptsByQuestionnaire"><s:param name="pageNumber" value="pageNumber-1"></s:param><s:param name="topic" value="topic"></s:param><s:param name="questionnaireId" value="questionnaireId"></s:param></s:url>">Previous page</a>&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;
                </s:if>
                <s:bean name= "org.apache.struts2.util.Counter"  var= "counter">
                    <s:param name="first"  value= "1"  />
                    <s:param name="last"  value= "%{attemptNumber/5+1}"/>
                    <s:iterator status="status">
                        <a href="<s:url action="getAttemptsByQuestionnaire"><s:param name="pageNumber" value="%{#status.index+1}"></s:param><s:param name="topic" value="topic"></s:param><s:param name="questionnaireId" value="questionnaireId"></s:param></s:url>"><s:property value="%{#status.index+1}"/></a>&nbsp;
                    </s:iterator>
                </s:bean>
                &nbsp;&nbsp;&nbsp;&nbsp;|
                <s:if test="%{pageNumber != attemptNumber/5+1}">
                    <a href="<s:url action="getAttemptsByQuestionnaire"><s:param name="pageNumber" value="pageNumber+1"></s:param><s:param name="topic" value="topic"></s:param><s:param name="questionnaireId" value="questionnaireId"></s:param></s:url>">Next Page</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                </s:if>
                <a href="<s:url action="getAttemptsByQuestionnaire"><s:param name="pageNumber" value="%{attemptNumber/5+1}"></s:param><s:param name="topic" value="topic"></s:param><s:param name="questionnaireId" value="questionnaireId"></s:param></s:url>">Last Page</a>
            </div>







        </div>
    </div>
</div>
</div>
</body>
</html>

