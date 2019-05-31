<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8" />
    <title>Changement du Topic</title>
    <link rel="stylesheet" type="text/css" href="../css/myStyle2.css" />
</head>

<body>
<%
    String name = request.getParameter("name");
    Boolean status = request.getParameter("status").equals("true");
    String check = "checked";
    String topic = request.getParameter("topic");
    int questionnaireId = Integer.parseInt(request.getParameter("questionnaireId"));%>

<img class="bgone" src="../picture/1.jpg" />
<img class="pic" src="../picture/a.png" />

<div class="table">
    <div class="wel">Changez l'information de cette questionnaire</div>
    <form action="updateQuestionnaire.action">
        <div class="user">
            <div id="yonghu"><img src="../picture/yhm.png" /></div>
            <input type="text" name="questionnaire.name" value="<%=name%>" onchange="changeValue()"/>
            <input type="hidden" name="questionnaire.topic" value="<%=topic%>" />
            <input type="hidden" name="questionnaire.questionnaireId" value="<%=questionnaireId%>" />
        </div>
        <div class = "password">
            Active
            <input type="radio" style="width:140px;height:25px" name="questionnaire.status" value="true"
                    <%if (status){%> <%=check%> <%}%>/>
            Inactive
            <input type="radio" style="width:140px;height:25px" name="questionnaire.status" value="false"
                    <%if (!status){%> <%=check%> <%}%>/>
        </div>
        <input class="btn" type="submit" name="Validation" value="Validation" />
    </form>
</div>
<script>
    function changeValue(obj){
        $(obj).attr("value",$(obj).val());
    }
</script>
</body>
</html>
