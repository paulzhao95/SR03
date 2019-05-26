<%@ page import="java.util.ArrayList" %>
<%@ page import="model.User" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                    <a href="CreateTopic.jsp" class="link_class">Add new Topics</a>
                    <br>
                    <a href="" class="link_class">Add new Users</a>
                </div>
            </div>



            <div class="tab-buttons ">
                <h3 class="tab-button cur" data-tab="one">Management Topics</h3>
                <h3 class="tab-button" data-tab="two">Management Users</h3>
            </div>

            <div class="tabs">
                <div class="tab-item active" id="tab-one">
                    <div class="information-tab">
                        <div class="information-top">

                            <div class="information-top-head-left">
                                <p class="information-top-head-p">Topics</p>
                            </div>
                            <div class="information-top-head-right">
                                <p class="information-top-head-p">Delete</p>
                            </div>




                            <s:iterator value="topics">
                            <div class="information-top-content-left">
                                <!-- 这里要加action-->
                                <a href="http://localhost:8080/SR03_Projet_war_exploded/default/actionxxxxx?topic.name=<s:property value='name'/>" class="information-top-content-p"><s:property value='name'/></a>
                            </div>
                            <div class="information-top-content-right">
                                <a href="" class="information-top-content-p">Delete</a>
                            </div>
                            </s:iterator>






                        </div>
                    </div>
                </div>

                <div class="tab-item" id="tab-two">
                    <div class="information-tab ">
                        <div class="information-record">
                            <div class="information-top-head-left">
                                <p class="information-top-head-p">User Name</p>
                            </div>
                            <div class="information-top-head-right">
                                <p class="information-top-head-p">Delete</p>
                            </div>


                <s:iterator value="users">
                    <div class="information-top-content-left">
                        <a href="http://localhost:8080/SR03_Projet_war_exploded/administrator/UserInfoChangeAdmin.jsp?name=<s:property value='name'/>&password=<s:property value='password'/>&email=<s:property value='email'/>&tel=<s:property value='tel'/>&company=<s:property value='company'/>&type=<s:property value='type'/>&status=<s:property value='status'/>" class="information-top-content-p"><s:property value='name'/></a>
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
<script type="text/javascript">
    $('.tab-button').click(function() {
        var tab = $(this).data('tab')
        $(this).addClass('cur').siblings('.tab-button').removeClass('cur');
        $('#tab-' + tab + '').addClass('active').siblings('.tab-item').removeClass('active');
    });

</script>
</body>
</html>

