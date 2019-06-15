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
    <script type="text/javascript" src="../js/jquery-1.7.1.min.js" ></script>
    <script type="text/javascript" src="../js/Investigation.js" ></script>
    <link rel="stylesheet" type="text/css" href="../css/Investigation.css">
    <link rel="stylesheet" type="text/css" href="../css/myStyle3.css">
    <link rel="stylesheet" type="text/css" href="../css/myStyle.css">
    <style>body{background:url("../picture/create_question.jpg"); background-size:100% auto;}
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

    .button {
        background-color: #79aef0; /* Green */
        border: none;
        color: white;
        padding: 10px 22px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 13px;
    }
    </style>
</head>
<body>
<div class="Content" style="margin:0 auto;">
    <div id='Investigation'><div class="BackGrey" style="display: none;position: absolute;left: 0px;top: 0px;z-index:10001;opacity: 0.6;width: 100%;background-color: rgb(0, 0, 0);"></div>
        <div class='InvestigationView' style='margin:0 auto;width:1000px;'>
            <div class='TopTool' style='float:left;width:100%;padding-left:20px;padding-top:4px;padding-bottom:4px;'>
                <ul>
                    <li><input type="submit" id="AddMoreFileBox" name="single" value="ADD New Question"/></li>
                </ul>
            </div>
            <form action="addQuestionnaire.action">
                <input type="hidden" name="questionnaire.topic" value=<%=request.getParameter("topic")%>>
                <div class='Content' style='clear:both;height:auto;text-align:left;padding: 15px 24px 15px 25px;margin-left:auto;margin-right:auto;'>
                    <div class='InvestigationHead' onclick='Investigation.InvestigationHeadClick();'><h1 id='investigation_title' style='font-size:24px;font-weight:bold;color:steelblue;padding:15px 0;line-height:24px;'>Le titre de Questionnaire</h1><div id='investigation_desc' class='investigationdescription'>Enter the title of this questionnaire</div></div>
                    <div class='InvestigationTitle' style='display:none;margin:0 auto;width:530px;height:300px;padding-left:10px;padding-top:20px;z-index:10005'>
                        <table style='margin:0 auto;'>
                            <tr><td><p>The Title：</p></td>
                                <td><textarea rows="2" cols="20" style="height:150px;display:inline-block;" name="questionnaire.name">Enter the title of this questionnaire</textarea></td>
                            </tr>
                        </table>
                        <div style='margin-top:10px;text-align:center;'>
                            <input type='button' class='investigation_title_save' value='SAVE' Onclick='Investigation.Investigation_title_save_Click();' /></div>
                    </div>
                </div>

                <div id="InputsWrapper">
                    <div class="test_content_nr">
                        <ul>
                            <li>
                                <div class="test_content_nr_tt">
                                    <i>*</i><font>
                                    <input class = "input_control" type="text" name="questionnaire.questions[0].Description" placeholder="Question0"/></font>
                                </div>
                                <div class="test_content_nr_main">
                                    <ul>
                                        <ul>
                                            <li class="option">
                                                <input type="text" style="width:800px;" name="questionnaire.questions[0].choices[0].description" placeholder="Choice1"/>
                                            </li>
                                        </ul>
                                        <ul>
                                            <li class="option">
                                                <input type="text" style="width:800px;" name="questionnaire.questions[0].choices[1].description" placeholder="Choice2"/>
                                            </li>
                                        </ul>
                                        <ul>
                                            <li class="option">
                                                <input type="text" style="width:800px;" name="questionnaire.questions[0].choices[2].description" placeholder="Choice3"/>
                                            </li>
                                        </ul>
                                        <ul>
                                            <li class="option">
                                                <input type="text" style="width:800px;" name="questionnaire.questions[0].choices[3].description" placeholder="Choice4"/>
                                            </li>
                                        </ul>
                                    </ul>
                                </div>
                            </li>
                            <div>
                                <div align="right">
                                    Answer:   A<input style = "font-size:1em;height:1.5em;width:12%;margin-right: 10px" name = "questionnaire.questions[0].choices[0].isRight" type = "checkbox" value="true">
                                    B<input style = "font-size:1em;height:1.5em;width:12%;margin-right: 10px" name = "questionnaire.questions[0].choices[1].isRight" type = "checkbox" value="true">
                                    C<input style = "font-size:1em;height:1.5em;width:12%;margin-right: 10px" name = "questionnaire.questions[0].choices[2].isRight" type = "checkbox" value="true">
                                    D<input style = "font-size:1em;height:1.5em;width:12%;margin-right: 10px" name = "questionnaire.questions[0].choices[3].isRight" type = "checkbox" value="true">
                                </div>
                                <a href="#" class="removeclass"><input class="button" type="button" value='Delete'></a>
                            </div>
                            </ul>
                    </div>
                </div>
                <div align="center">
        <input class="btn" type="submit" name="Validation" value="Validation" />
                </div>
        </form>
    </div></div>
</div>
</body>
</html>


