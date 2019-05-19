<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Administrator Panel</title>
    <link rel="stylesheet" type="text/css" href="../css/myStyle.css" />
    <script src="../js/jquery-1.11.0.js" type="text/javascript" charset="utf-8"></script>
    <style>body{background:url("../picture/backgroud_login.jpeg"); background-size:100% auto;}</style>
</head>
<body>

<%String s = "abababa"; %>


<div>
    <div class="content">
        <div class="tab-block information-tab">
            <div class="personalnformation">
                <div class="personalnformation-content">
                    <div class="personalnformation-content-left">
                        <img src="../picture/default_avatar.png"/>
                    </div>
                    <div class="personalnformation-content-right">
                        <p class="personalnformation-content-right-p">Nom：<%=s%> </p>
                        <p class="personalnformation-content-right-p">Email：<%=s%> </p>
                        <p class="personalnformation-content-right-p">Create Time：13519494861</p>
                    </div>
            </div>
                <div style="float: right;margin: 150px;">
                    <a href="" class="link_class">Add new Topics</a>
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


                            <%
                                for(int i=0;i<10;i++){
                            %>

                            <div class="information-top-content-left">

                                <a href="" class="information-top-content-p"><%=i%></a>
                            </div>
                            <div class="information-top-content-right">
                                <!-- 这里用get方法把删除的id加入url里传输，deleteTopic.servlet?id=i -->
                                <a href="" class="information-top-content-p">Delete</a>
                            </div>
                            <%
                                }
                            %>


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


                            <%
                                for(int i=0;i<100;i++){
                            %>

                            <div class="information-top-content-left">
                                <a href="" class="information-top-content-p"><%=i%></a>
                            </div>
                            <div class="information-top-content-right">
                                <!-- 这里用get方法把删除的id加入url里传输，deleteTopic.servlet?id=i -->
                                <a href="" class="information-top-content-p">Delete</a>
                            </div>
                            <%
                                }
                            %>

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

