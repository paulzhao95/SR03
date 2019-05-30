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
                    <div class="personalnformation-content-right">
                        <p class="personalnformation-content-right-p">Nom：<%=user.getName()%> </p>
                        <p class="personalnformation-content-right-p">Email：<%=user.getEmail()%> </p>
                        <p class="personalnformation-content-right-p">Create Time：2019</p>
                    </div>
                </div>
                <div style="float: right;margin: 150px;">
                    <!-- 这里需要一个问卷的总的通过时间,我不知道最后传过来的变量叫啥，记得要改 -->
                    <p>Durée du passage: <s:property value='durationInSeconds'/></p>
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
                                <p class="information-top-head-p">Scores</p>
                            </div>


                            <s:iterator value="questions">
                                <div style="border: 1px solid #d8d8d8;width:800px; height: 200px;">
                                        <p><s:property value='Description'/></p>
                                        <br>
                                        <s:iterator value="choices" var="id">
                                            <input type="radio" name="answer1" disabled <s:if test="%{#id.isRight}"> <%=checked%> </s:if>/><s:property value='description'/>
                                            <br>
                                        </s:iterator>
                                    <!-- 这里也需要将value的名字改为对应代表的intern选择的那个选项的变量 -->
                                    <p>Your Answer: <s:property value='choiceAnswer'/></p>
                                </div>
                                <div style="border: 1px solid #d8d8d8;width:200px; height: 200px;">
                                    <!-- 这里用get方法把删除的id加入url里传输，deleteTopic.servlet?id=i -->
                                    <p><%=score%></p>
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

