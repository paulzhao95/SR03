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

<div>
    <div class="content">
        <div class="tab-block information-tab">
            <div class="personalnformation">
                <div class="personalnformation-content">
                    <div class="personalnformation-content-left">
                        <img src="../picture/default_avatar.png"/>
                    </div>
                    <div class="personalnformation-content-right">
<%--                        <p class="personalnformation-content-right-p">Nom：<%=user.getName()%> </p>--%>
                        <p class="personalnformation-content-right-p">Nom：<s:property value="user.name"/> </p>
                        <p class="personalnformation-content-right-p">Email：<%=user.getEmail()%> </p>
                        <p class="personalnformation-content-right-p">Create Time：2019</p>
                    </div>
            </div>
                <div style="float: right;margin: 150px;">
                    <a href="logout.action" class="link_class">Log out</a>
                    <br>
                    <a href="CreateTopic.jsp" class="link_class">Add new Topics</a>
                    <br>
                    <a href="AddUser.jsp" class="link_class">Add new Users</a>
                </div>
            </div>



            <div class="tab-buttons ">
                <h3 class="tab-button cur" data-tab="one">Management Topics</h3>
                <h3 class="tab-button" data-tab="two">Management Users</h3>
            </div>

            <div class="tabs">
                <div class="tab-item active" id="tab-one">








                    <div>
                        <div style="text-align:center;">
                            <div class="search bar6">
                                <form action="getUsers.action">
                                    <input type="text" style="height: 40px;width: 400px;display: inline-block;" name = "topicName" placeholder="Search...">


                                    <button type="submit" value="Validation"></button>
                                </form>
                            </div>
                        </div>
                    </div>
                    <br>






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
                                <a href="http://localhost:8080/SR03_Projet_war_exploded/getQuestionnaires.action?topic=<s:property value='name'/>" class="information-top-content-p"><s:property value='name'/></a>
                            </div>
                            <div class="information-top-content-right">
                                <a href="deleteTopic.action?topic.name=<s:property value='name'/>" class="information-top-content-p">Delete</a>
                            </div>
                            </s:iterator>

                        </div>
                    </div>






                    <div style="text-align:center">
                        <br>
                        <a href="<s:url action="getUsers"><s:param name="pageNumberTopic" value="1"></s:param></s:url>">First Page</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                        <s:if test="%{pageNumberTopice != 1}">
                            <a href="<s:url action="getUsers"><s:param name="pageNumberTopic" value="pageNumberTopic-1"></s:param></s:url>">Previous page</a>&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;
                        </s:if>
                        <s:bean name= "org.apache.struts2.util.Counter"  var= "counter">
                            <s:param name="first"  value= "1"  />
                            <s:param name="last"  value= "%{topicNumber/5+1}"/>
                            <s:iterator status="status">
                                <a href="<s:url action="getUsers"><s:param name="pageNumberTopic" value="%{#status.index+1}"></s:param></s:url>"><s:property value="%{#status.index+1}"/></a>&nbsp;
                            </s:iterator>
                        </s:bean>
                        &nbsp;&nbsp;&nbsp;&nbsp;|
                        <s:if test="%{pageNumberTopice != topicNumber/5+1}">
                            <a href="<s:url action="getUsers"><s:param name="pageNumberTopic" value="pageNumberTopic+1"></s:param></s:url>">Next Page</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                        </s:if>
                        <a href="<s:url action="getUsers"><s:param name="pageNumberTopic" value="%{topicNumber/5+1}"></s:param></s:url>">Last Page</a>
                    </div>





                </div>

                <div class="tab-item" id="tab-two">








                    <div>
                        <div style="text-align:center;">
                            <div class="search bar6">
                                <form action="getUsers.action">
                                    <input type="text" style="height: 40px;width: 400px;display: inline-block;" name = "userName" placeholder="Search...">

                                    <button type="submit" value="Validation"></button>
                                </form>
                            </div>
                        </div>
                    </div>
                    <br>







                    <div class="information-tab ">
                        <div class="information-record">
                            <div class="information-top-head-left">
                                <p class="information-top-head-p">User Name</p>
                            </div>
                            <div class="information-top-head-right">
                                <p class="information-top-head-p">Delete | Check</p>
                            </div>


                <s:iterator value="users">
                    <div class="information-top-content-left">
                        <a href="getUser.action?email=<s:property value='email'/>" class="information-top-content-p"><s:property value='name'/></a>


                    </div>
                    <div class="information-top-content-right">
                        <!-- 这里用get方法把删除的id加入url里传输，deleteTopic.servlet?id=i -->
                        <a href="<s:url value="deleteUser.action" ><s:param name="user.email" value="email"></s:param></s:url>" class="information-top-content-p">Delete</a> |
                        <a href="getAttemptsByUser.action?email=<s:property value='email'/>&pageNumber_Topice=<s:property value="1"/> " class="information-top-content-p">Check attempts</a>

                    </div>
                </s:iterator>
                        </div>
                    </div>


                    <div style="text-align:center">
                        <br>
                        <a href="<s:url action="getUsers"><s:param name="pageNumberUser" value="1"></s:param></s:url>">First Page</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                        <s:if test="%{pageNumberUser != 1}">
                            <a href="<s:url action="getUsers"><s:param name="pageNumberUser" value="pageNumberUser-1"></s:param></s:url>">Previous page</a>&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;
                        </s:if>
                        <s:bean name= "org.apache.struts2.util.Counter"  var= "counter">
                            <s:param name="first"  value= "1"  />
                            <s:param name="last"  value= "%{userNumber/5+1}"/>
                            <s:iterator status="status">
                                <a href="<s:url action="getUsers"><s:param name="pageNumberUser" value="%{#status.index+1}"></s:param></s:url>"><s:property value="%{#status.index+1}"/></a>&nbsp;
                            </s:iterator>
                        </s:bean>
                        &nbsp;&nbsp;&nbsp;&nbsp;|
                        <s:if test="%{pageNumberUser != userNumber/5+1}">
                            <a href="<s:url action="getUsers"><s:param name="pageNumberUser" value="pageNumberUser+1"></s:param></s:url>">Next Page</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                        </s:if>
                        <a href="<s:url action="getUsers"><s:param name="pageNumberUser" value="%{userNumber/5+1}"></s:param></s:url>">Last Page</a>
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

