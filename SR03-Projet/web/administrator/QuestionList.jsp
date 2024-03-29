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

        User user = (User)session.getAttribute("user");
    %>
    <base href="<%=basePath%>">
    <title>Administrator Panel</title>
    <link rel="stylesheet" type="text/css" href="../css/myStyle.css" />
    <script src="../js/jquery-1.11.0.js" type="text/javascript" charset="utf-8"></script>
    <style>body{background:url("../picture/backgroud_login.jpeg"); background-size:100% auto;}</style>
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
                    <a href="" class="link_class">Add new Question</a>
                    <br>
                    <a href="QuestionnaireInfoChangeAdmin.jsp?questionnaireID=<S:property value="questionnaireID"/>&name=<s:property value='name'/>&status=<s:property value='status'/>&topic=<s:property value='topic'/>" class="link_class">Update Questionnaire</a>
                </div>
            </div>



            <div class="tab-buttons ">
                <h3 class="tab-button cur" data-tab="one">Management Questions</h3>
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


                            <s:iterator value="questions">
                            <div style="border: 1px solid #d8d8d8;width:800px; height: 200px;">
                                <form>
                                <a href="http://localhost:8080/SR03_Projet_war_exploded/administrator/QuestionInfoChange.jsp?Description=<s:property value='Description'/>&status=<s:property value='status'/>&choices=<s:property value='choices'/>" style="font-size:16px;line-height:50px;font-weight: 200;color: #79aef0"><s:property value='Description'/></a>
                                    <br>
                                <s:iterator value="choices" var="id">
                                    <input type="radio" name="answer1" disabled <s:if test="%{#id.isRight}"> <%=checked%> </s:if>/><s:property value='description'/>
                                    <br>
                                </s:iterator>
                                </form>
                            </div>
                            <div style="border: 1px solid #d8d8d8;width:200px; height: 200px;">
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

