<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Administrator Panel</title>
    <link rel="stylesheet" type="text/css" href="../css/myStyle3.css"/>
    <link rel="stylesheet" type="text/css" href="../css/myStyle.css"/>
    <script src="../js/jquery-1.11.0.js" type="text/javascript" charset="utf-8"></script>
    <style>body {
        background: url("../picture/backgroud_login.jpeg");
        background-size: 100% auto;
    }

    .input_control {
        margin: 0px;
    }

    input[type="text"] {
        margin: 0px;
        box-sizing: border-box;
        text-align: center;
        font-size: 1.2em;
        height: 2.5em;
        border-radius: 4px;
        border: 1px solid #c8cccf;
        color: #6a6f77;
        -web-kit-appearance: none;
        -moz-appearance: none;
        display: block;
        outline: 0;
        padding: 0.1em;
        text-decoration: none;
        width: 100%;
    }

    input[type="submit"] {
        -moz-user-select: none;
        background-color: #F5F5F5;
        background-image: -moz-linear-gradient(#F5F5F5, #F1F1F1);
        border: 1px solid rgba(0, 0, 0, 0.1);
        border-radius: 2px 2px 2px 2px;
        color: #666666;
        cursor: default;
        font-family: arial, sans-serif;
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

<%String questionnaireID = request.getParameter("questionnaireID");%>

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
                    <form action="">
                        <ul>
                            <li id="qu_0_0">
                                <div class="test_content_nr_tt">
                                    <i>*</i><font>
                                    <input id="question" class="input_control" name="question.Description" type="text">
                                    <label for="question"></label></font>
                                </div>
                                <div class="test_content_nr_main">
                                    <ul>
                                            <li class="option">
                                                <input style="font-size:1.2em;height:1.5em;width:80%;" name="question.choices[0].description" type="text">
                                            </li>
                                        <li class="option">
                                            <input style="font-size:1.2em;height:1.5em;width:80%;" name="question.choices[1].description" type="text">
                                        </li>
                                        <li class="option">
                                            <input style="font-size:1.2em;height:1.5em;width:80%;" name="question.choices[2].description" type="text">
                                        </li>
                                        <li class="option">
                                            <input style="font-size:1.2em;height:1.5em;width:80%;" name="question.choices[3].description" type="text">
                                        </li>
                                    </ul>
                                </div>
                            </li>
                                    <input style = "font-size:1em;height:1.5em;width:12%;margin-right: 10px" name = "questions.choices[0].isRight" type = "checkbox" value="true">
                                    <input style = "font-size:1em;height:1.5em;width:12%;margin-right: 10px" name = "questions.choices[1].isRight" type = "checkbox" value="true">
                                    <input style = "font-size:1em;height:1.5em;width:12%;margin-right: 10px" name = "questions.choices[2].isRight" type = "checkbox" value="true">
                                    <input style = "font-size:1em;height:1.5em;width:12%;margin-right: 10px" name = "questions.choices[3].isRight" type = "checkbox" value="true">
                                <input name = "question.questionnaireId" type="hidden" value = "<%=questionnaireID%>" >
                                <input type="submit" value="Validation">
                        </ul>
                    </form>
                </div>

            </div>

        </div>
    </div>
</div>
<script>
    function changeValue(obj) {
        $(obj).attr("value", $(obj).val());
    }
</script>
</body>
</html>

