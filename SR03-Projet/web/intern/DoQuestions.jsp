<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Administrator Panel</title>
    <link rel="stylesheet" type="text/css" href="../css/myStyle3.css" />
    <link rel="stylesheet" type="text/css" href="../css/myStyle.css" />
    <script src="../js/jquery-1.11.0.js" type="text/javascript" charset="utf-8"></script>
    <style>body{background:url("../picture/backgroud_login.jpeg"); background-size:100% auto;}</style>
</head>
<body>
<%String checked = "checked";%>
<div>
    <div class="content">
        <div class="tab-block information-tab">

            <form action="">
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
                                <i><s:property value="question.questionId"/></i><font><s:property value="description"/></font>
                            </div>
                            <div class="test_content_nr_main">
                                <ul>
                                    <s:iterator value="question.choices" status="id" var="item">
                                    <li class="option">
                                        <!--这里每个选项都要有一个chosen的属性变量，来看他是否已经被选择了，item表示每一个循环变量的对象个体-->
                                        <input id = "answer<s:property value="#status.index"/>" class = "radioOrCheck" name = "question.choices[<s:property value="#status.index"/>].description" type="checkbox" value = <s:property value="choiceID"/>
                                                <s:if test="%{item.chosen.equals(true)}"> <%=checked%> </s:if>>

                                        <label for="answer<s:property value="#status.index"/>">
                                            <p class="ue" style="display: inline;"><s:property value="description"/></p>
                                        </label>
                                    </li>
                                    </s:iterator>
                                </ul>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <div>
            <input type="hidden" name="question.questionId" value="questionId">

                <!-- 这里有三个不同的submit提交标签,分别有不同的名字和值，对应不同的页面跳转-->
                <s:if test="question.questionId!=0">
            <div align="left">
                <input type="submit" name="changePagePrevious" value = "Previous">
            </div>
                </s:if>
                <!--这里要用到session里面存到的当前问卷里面有几个问题的变量nbQuestions -->
                <s:if test="question.questionId.toString()!=#session.nbQuestions">
            <div align="right">
                <input type="submit" name="changePageNext" value = "Next">
            </div>
                </s:if>
                <s:else>
                    <div align="right">
                        <input type="submit" name="changePageFinish" value = "Finish">
                    </div>
                </s:else>
        </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
