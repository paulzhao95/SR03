<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>Start doing questions</title>
    <%int nbQuestions = (int)session.getAttribute("nbQuestions");%>
</head>
<body>
<div style="text-align:center;">
    <!-- 这里传给action index为1，开始答题，-->
    <a href="actionxxxxxxxx?questionIndex=1&questionnaire.questionnaireID=<s:property value="questionnaireID"/>&questionnaire.topic=<s:property value='topic'/>" style="display:block;margin:40px auto 0 auto; line-height:32px; font-size:20px; font-weight:bold">
        Please complete the test questions carefully. <br/>
        Are you ready?<br/><br/>
        <span><img src="../picture/start.jpg"></span></a>
</div>

</body>
</html>