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
        //这里从session里面接收总页数
        //int pageTotal = (int)request.getSession().getAttribute("pageTotal");
        User user = (User)session.getAttribute("user");
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
<%String checked = "checked";%>
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
<%--                    <a href="<AddQuestion.jsp?topic=<s:property value="questionnaire.topic"/>&questionnaireId=<s:property value="questionnaire.questionnaireId"/>" class="link_class">Add new Question</a>--%>
                    <a href="<s:url value="AddQuestion.jsp"><s:param name="topic" value="topic"></s:param><s:param name="questionnaireId" value="questionnaireId"></s:param> </s:url> " class="link_class">Add new Question</a>
<%--                    <a href="<s:url value="AddQuestion.jsp"><s:param name="questionnaire" value="${questionnaire}"></s:param></s:url> " class="link_class">Add new Question</a>--%>
                    <br>

                    <div class="test_content_nr_main">
                        <ul>
                            <form action="actionxxxx.action?">
                            Change the Order of questions:
                            <input type="text" style="height:1.5em;width:3em;display:inline-block;" name="change1" />
                            ~~~
                            <input type="text" style = "height:1.5em;width:3em;display:inline-block;" name="change2" />
                                <!-- 这里两个hidden标好了topic和questionnaireID-->
                            <input type="hidden" name="questionnaire.topic" value="<s:property value="questionnaire.topic"/>">
                            <input type="hidden" name="questionnaire.questionnaireId" value="<s:property value="questionnaire.questionnaireId"/>">
                            <input type="submit" style="display:inline-block;" value="validation">
                            </form>
                        </ul>
                    </div>
                </div>
            </div>



            <div class="tab-buttons ">
                <h3 class="tab-button cur" data-tab="one">Management Questions</h3>
            </div>
            <br>
            <div>
                <div style="text-align:center;">
                    <div class="search bar6">
                    <form action="actionxxxx.action">
                        <input type="text" style="height: 40px;width: 400px;display: inline-block;" placeholder="Search...">
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
                                <p class="information-top-head-p">Questions</p>
                            </div>
                            <div class="information-top-head-right">
                                <p class="information-top-head-p">Delete</p>
                            </div>


                            <s:iterator value="questions" status="question_status">
                            <div style="border: 1px solid #d8d8d8;width:800px; height: 200px;">
                                <form>
<%--                                <a href="QuestionInfoChange.jsp?question.questionId=<s:property value='questionId'/>" style="font-size:16px;line-height:50px;font-weight: 200;color: #79aef0"><s:property value='Description'/></a>--%>
                                    <s:property value="#question_status.index"/>.<a href="<s:url action="getQuestion" > <s:param name="questionId" value="questionId"></s:param><s:param name="questionnaireId" value="questionnaireId"></s:param><s:param name="topic" value="topic"></s:param></s:url>" style="font-size:16px;line-height:50px;font-weight: 200;color: #79aef0"><s:property value='Description'/></a>
                                    <br>
                                <s:iterator value="choices" var="id" status="status">
                                    <s:if test="%{#id.description.length != 0}">
                                    <input type="radio" name="answer<s:property value="#question_status.index"/><s:property value="#status.index"/>" disabled <s:if test="%{#id.isRight.equals(true)}"> <%=checked%> </s:if>/><s:property value='description'/>
                                    <br>
                                    </s:if>
                                </s:iterator>
                                </form>
                            </div>
                            <div style="border: 1px solid #d8d8d8;width:200px; height: 200px;">
                                <!-- 这里用get方法把删除的id加入url里传输，deleteTopic.servlet?id=i -->
                                <a href="http://localhost:8080/SR03_Projet_war_exploded/default/deleteQuestion.action?question.topic=<s:property value="topic"/>&question.questionnaireId=<s:property value="questionnaireId"/>&question.questionId=<s:property value="questionId"/>" class="information-top-content-p">Delete</a>
                            </div>
                            </s:iterator>
                        </div>
                    </div>
                </div>
            </div>
            <div style="text-align:center">
                <br>
                <a href="getQuestionnaire.action?PageNumber=1">First Page</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                <a href="getQuestionnaire.action?PageNumber=<s:property value="%{pageNumber-1}"/>">Previous page</a>&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;
                <s:bean name= "org.apache.struts2.util.Counter"  var= "counter">
                    <s:param name="first"  value= "1"  />
                    <s:param name="last"  value= "%{questionNumber/5+1}"/>
                    <s:iterator status="status">
                <a href="getQuestionnaire.action?PageNumber=<s:property value="%{#status.index+1}"/>"><s:property value="%{#status.index+1}"/></a>&nbsp;
                    </s:iterator>
                </s:bean>
                    &nbsp;&nbsp;&nbsp;&nbsp;|
                <a href="getQuestion.action?pageNumber=<s:property value="%{pageNumber+1}"/>">Next Page</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                <a href="getQuestion.action?pageNumber=<s:property value="%{questionNumber/5+1}"/>">Last Page</a>
                <a href="<s:url action="getQuestions"><s:param name="pageNumber" value="pageNumber+1"></s:param><s:param name="topic" value="topic"></s:param><s:param name="questionnaireId" value="questionnaireId"></s:param></s:url>">Next Page</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                </form>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>

