<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<html>
<head>
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
    <meta charset="UTF-8">
    <title>Administrator Panel</title>
    <link rel="stylesheet" type="text/css" href="../css/myStyle3.css" />
    <link rel="stylesheet" type="text/css" href="../css/myStyle.css" />
    <script src="../js/jquery-1.11.0.js" type="text/javascript" charset="utf-8"></script>
    <style>body{background:url("../picture/backgroud_login.jpeg"); background-size:100% auto;}</style>
</head>
<body>

<%int number = 3;
  String s = "checked";%>


<div>
    <div class="content">
        <div class="tab-block information-tab">
            <div class="personalnformation">
                <div class="personalnformation-content">
                    <div class="personalnformation-content-left">
                        <img src="../picture/default_avatar.png"/>
                    </div>
                </div>


                    <div class="test_content_nr">
                        <ul>
                            <li id="qu_0_0">
                                <div class="test_content_nr_tt">
                                    <i>*</i><font>在生产管理信息系统中，下列操作步骤能正确将工单推进流程的是（  ）</font><a href="" class="icon iconfont">Edit Question</a>
                                </div>

                                <div class="test_content_nr_main">
                                    <ul>

                                        <li class="option">

                                            <input type="radio" class="radioOrCheck" name="answer1"
                                                   id="0_answer_1_option_1" disabled <%if (number == 1){%> <%=s%> <%}%>/>


                                            <label for="0_answer_1_option_1">
                                                A.
                                                <p class="ue" style="display: inline;">在工具栏中点击“workflow”标签</p>
                                            </label>
                                        </li>

                                        <li class="option">

                                            <input type="radio" class="radioOrCheck" name="answer1"
                                                   id="0_answer_1_option_2" disabled <%if (number == 2){%> <%=s%> <%}%>
                                            />


                                            <label for="0_answer_1_option_2">
                                                B.
                                                <p class="ue" style="display: inline;">在缺陷单界面中点击“推进流程”按钮</p>
                                            </label>
                                        </li>

                                        <li class="option">

                                            <input type="radio" class="radioOrCheck" name="answer1"
                                                   id="0_answer_1_option_3" disabled <%if (number == 3){%> <%=s%> <%}%>
                                            />


                                            <label for="0_answer_1_option_3">
                                                C.
                                                <p class="ue" style="display: inline;">在缺陷单界面中点击“提交”按钮</p>
                                            </label>
                                        </li>
                                    </ul>
                                </div>
                            </li>
                        </ul>
                    </div>

            </div>

</div>
</div>
</div>
</body>
</html>

