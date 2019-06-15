<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>Administrator Panel</title>
    <link rel="stylesheet" type="text/css" href="../css/myStyle3.css" />
    <link rel="stylesheet" type="text/css" href="../css/myStyle.css" />
    <script src="../js/jquery-1.11.0.js" type="text/javascript" charset="utf-8"></script>
    <style>body{background:url("../picture/backgroud_login.jpeg"); background-size:100% auto;}
    .input_control{
        margin:0px;
    }
    input[type="text"]{
        margin:0px;
        box-sizing: border-box;
        text-align:center;
        font-size:1.2em;
        height:2.5em;
        border-radius:4px;
        border:1px solid #c8cccf;
        color:#6a6f77;
        -web-kit-appearance:none;
        -moz-appearance: none;
        display:block;
        outline:0;
        padding:0.1em;
        text-decoration:none;
        width:100%;
    }
    input[type="submit"] {
        -moz-user-select: none;
        background-color: #F5F5F5;
        background-image: -moz-linear-gradient(#F5F5F5, #F1F1F1);
        border: 1px solid rgba(0, 0, 0, 0.1);
        border-radius: 2px 2px 2px 2px;
        color: #666666;
        cursor: default;
        font-family: arial,sans-serif;
        font-size: 16px;
        font-weight: bold;
        height: 29px;
        line-height: 27px;
        margin: 11px 6px;
        min-width: 54px;
        padding: 0 8px;
        text-align: center;
    }


    </style>
</head>
<body>

<%  int number = 3;
    String checked = "checked";%>


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
                    <form action = "updateQuestion.action">
                        <ul>
                            <li id="qu_0_0">
                                <div class="test_content_nr_tt">
                                    <i>*</i><font>
                                    <input id="question" class = "input_control" name = "question.Description" type="text" onchange="changeValue()" value = <s:property value="question.Description"/>>
                                    <input type="hidden" name="question.topic" value=<s:property value="topic"/>>
                                    <input type="hidden" name="question.questionnaireId" value=<s:property value="questionnaireId"/> >
                                    <input type="hidden" name="question.questionId" value=<s:property value="questionId"/> >

                                    </font>
                                </div>


                                <div class="test_content_nr_main">
                                    <ul>
                                        <s:iterator value="question.choices" status="id">
                                        <li class="option">
                                            <input id="answer" style = "font-size:1.2em;height:1.5em;width:80%;" name = "question.choices[<s:property value="#id.index"/>].description" type="text" onchange="changeValue()" value = <s:property value="description"/>>
                                            <div align="right">*</div>
                                        </li>
                                        </s:iterator>

                                    </ul>
                                </div>
                                <div class="test_content_nr_main">
                                    <ul>
                                        Change the Order of choicces:
                                        <input type="text" style="height:1.5em;width:5em;display:inline-block;" name="choice1" />
                                        ~~~
                                        <input type="text" style = "height:1.5em;width:5em;display:inline-block;" name="choice2" />
                                    </ul>
                                </div>
                            </li>
                                    <s:iterator value="question.choices" status="id" var="var">
                                        <s:property value='#id.index'/><input style = "font-size:1em;height:1.5em;width:12%;margin-right: 10px" name = "question.choices[<s:property value="#id.index"/>].isRight" type = "checkbox" <s:if test="%{#var.isRight.equals(true)}"> <%=checked%> </s:if> value="true">
                                    </s:iterator>
                                <input type="submit" value = "Validation">
                        </ul>
                    </form>
                </div>

            </div>

        </div>
    </div>
</div>
<script>
    function changeValue(obj){
        $(obj).attr("value",$(obj).val());
    }
</script>
</body>
</html>

