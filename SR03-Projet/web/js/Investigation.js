$(document).ready(function() {

    var MaxInputs       = 8; //maximum input boxes allowed
    var InputsWrapper   = $("#InputsWrapper"); //Input boxes wrapper ID
    var AddButton       = $("#AddMoreFileBox"); //Add button ID

    var x = InputsWrapper.length; //initlal text box count
    var QuestionCount=1; //to keep track of text box added

    $(AddButton).click(function (e)  //on add input button click
    {
        if(x <= MaxInputs) //max input box allowed
        {
            QuestionCount++; //text box added increment
            //add input box
            $(InputsWrapper).append('<div class="test_content_nr"><ul><li><div class="test_content_nr_tt"><i>*</i><font><input class = "input_control" type="text" name="Question'+QuestionCount+'" placeholder="Question '+ QuestionCount +'"/></font></div><div class="test_content_nr_main"><ul><ul><li class="option"><input type="text" style="width:800px;" name="Question'+QuestionCount+'.Choice1" placeholder="Question'+QuestionCount+'.Choice1"/></li></ul><ul><li class="option"><input type="text" style="width:800px;" name="Question'+QuestionCount+'.Choice2" placeholder="Question'+QuestionCount+'.Choice2"/></li></ul><ul><li class="option"><input type="text" style="width:800px;" name="Question'+QuestionCount+'.Choice3" placeholder="Question'+QuestionCount+'.Choice3"/></li></ul><ul><li class="option"><input type="text" style="width:800px;" name="Question'+QuestionCount+'.Choice4" placeholder="Question'+QuestionCount+'.Choice4"/></li></ul></ul></div></li><div><div align="right"><input id = "right_answer" style = "font-size:1em;height:1.5em;width:12%;margin-right: 10px" name = "right_answer" type = "text" placeholder="Answer"></div><a href="#" class="removeclass"><input style="        background-color: #79aef0; /* Green */\n' +
                '        border: none;\n' +
                '        color: white;\n' +
                '        padding: 10px 22px;\n' +
                '        text-align: center;\n' +
                '        text-decoration: none;\n' +
                '        display: inline-block;\n' +
                '        font-size: 13px;" type="button" value="Delete"></a></div></div></li></ul></div>');
            x++; //text box increment
        }
        return false;
    });

    $("body").on("click",".removeclass", function(e){ //user click on remove text
        if( x > 1 ) {
            $(this).parent().parent().parent().remove(); //remove text box
            QuestionCount--
            x--; //decrement textbox
        }
        return false;
    })

});

(function ($) {
    var Investigation_Json_data = [];
    var Investigation_Question_num = 0;
    var Investigation_Question_opnum = 1;
    window.Investigation = {
        cleanSpelChar: function (th) {
            if (/[\"\'<>%;)(&+]/.test(th.val())) {
                $(th).val(th.val().replace(/[\"\'<>%;)(&+]/g, ""));
                alert("Invalid characters！");
            }
        },
        ChangeValue: function () {
            Investigation.cleanSpelChar($(this));
            var str = $(this).val();
            var index = $(this).parents("tr").index();
            var optionobj = $(this).parents(".div_question").children(".div_preview").children(".div_table_question").children("ul").children("li").eq(index).children("span");
            var html = optionobj.html();
            html = str;
            optionobj.html(html);
        },
        DeleteOption: function () {
            if ($(this).parents("table").children("tbody").children("tr").length > 2) {
                var index = $(this).parents("tr").index();
                var optionobj = $(this).parents(".div_question").children(".div_preview").children(".div_table_question").children("ul").children("li").eq(index);
                $(this).parents("tr").remove();
                optionobj.remove();
                Investigation_Question_opnum = Investigation_Question_opnum - 1;
            }
            else
                alert("选择题不能少于2个选项");
        },
        LoadToolClick: function () {
            $(".stuff a").unbind('click');
            $(".stuff").each(function () {
                $(this).children("li").eq(0).children("a").click(function () {
                    var question = $(this).parents(".div_question");
                    var edit = $(this).parents(".div_preview").next();
                    var display = edit.css('display');
                    (display == 'block') ? (edit.css('display', 'none'), question.css('border', '2px solid white'), question.hover(function () {
                        $(this).css('border', '2px solid #fdb553');
                    }, function () {
                        $(this).css('border', '2px solid white');
                    })) : ($(".div_preview").next().css('display', 'none'), $(".div_question").css('border', '2px solid white'), $(".div_question").hover(function () {
                        $(this).css('border', '2px solid #fdb553');
                    }, function () {
                        $(this).css('border', '2px solid white');
                    }), edit.css('display', 'block'), question.css('border', '2px solid #66CAFF'), question.unbind("mouseenter").unbind("mouseleave"));
                });
                $(this).children("li").eq(1).children("a").click(function () {
                    if (confirm("确定要删除题目吗?")) {
                        $(this).parents(".div_question").remove();
                    }
                });
                $(this).children("li").eq(2).children("a").click(function () {
                    var index = $(this).parents(".Content").children(".div_question").eq(0).index();
                    if (($(this).parents(".div_question").index() - index) != 0) {
                        var PreQuestion = $(this).parents(".div_question").prev();
                        $(this).parents(".div_question").after(PreQuestion);
                    }
                    else {
                        alert('不能再往上移了');
                    }
                });
                $(this).children("li").eq(3).children("a").click(function () {
                    var index = $(this).parents(".Content").children(".div_question").eq(0).index();
                    if (($(this).parents(".Content").children(".div_question").length + index) > $(this).parents(".div_question").index() + 1) {
                        var NextQuestion = $(this).parents(".div_question").next();
                        $(this).parents(".div_question").before(NextQuestion);
                    }
                    else {
                        alert('不能再往下移了');
                    }
                });
            });
        },
        CreateSingleOption: function () {
            var index = $(this).parents("tr").index();
            $(this).parents("tr").after('<tr><td><input class="inputtext choicetxt" type="text" value="选项' + Investigation_Question_opnum + '"></td><td><span title="在此选项下面插入一个新的选项" class="choiceimg design-icon design-add" style="cursor: pointer;"></span></td><td><span title="删除当前选项（最少保留2个选项）" class="choiceimg design-icon design-minus" style="cursor: pointer;"></span></td></tr>');
            $(this).parents("tr").next().children("td").eq(0).children(".inputtext").change(Investigation.ChangeValue);
            $(this).parents("tr").next().children("td").eq(1).children().click(Investigation.CreateSingleOption);
            $(this).parents("tr").next().children("td").eq(2).children().click(Investigation.DeleteOption);

            var optionobj = $(this).parents(".div_question").children(".div_preview").children(".div_table_question").children("ul").children("li").eq(index);



            optionobj.after('<li style="width:15%"><input type="radio" name="choice" value="'+Investigation_Question_opnum+'"/><span>'+Investigation_Question_opnum + '</span></li>');






            Investigation_Question_opnum = Investigation_Question_opnum + 1;
        },
        CreateMultiOption: function () {
            var index = $(this).parents("tr").index();
            $(this).parents("tr").after('<tr><td><input class="inputtext choicetxt" type="text" value="选项' + Investigation_Question_opnum + '"></td><td><span title="在此选项下面插入一个新的选项" class="choiceimg design-icon design-add" style="cursor: pointer;"></span></td><td><span title="删除当前选项（最少保留2个选项）" class="choiceimg design-icon design-minus" style="cursor: pointer;"></span></td></tr>');
            $(this).parents("tr").next().children("td").eq(0).children(".inputtext").change(Investigation.ChangeValue);
            $(this).parents("tr").next().children("td").eq(1).children().click(Investigation.CreateMultiOption);
            $(this).parents("tr").next().children("td").eq(2).children().click(Investigation.DeleteOption);

            var optionobj = $(this).parents(".div_question").children(".div_preview").children(".div_table_question").children("ul").children("li").eq(index);
            optionobj.after('<li style="width:15%"><input type="checkbox"/><span>选项' + Investigation_Question_opnum + '</span></li>');
            Investigation_Question_opnum = Investigation_Question_opnum + 1;
        },









        CreateSingle: function () {
            $(".div_preview").next().css('display', 'none');
            $(".div_question").css('border', '2px solid white'), $(".div_question").hover(function () {
                $(this).css('border', '2px solid #fdb553');
            }, function () {
                $(this).css('border', '2px solid white');
            });
            var div_question = $("<div class='div_question'></div>");
            var div_preview = $('<div class="div_preview"></div>');
            var div = $('<div style="display:block;margin-bottom:5px;"></div>');
            var div_title_question_all = $('<div class="div_title_question_all"></div>');
            var div_table_question = $('<div class="div_table_question" style="overflow:auto;"></div>');
            var div_title_question = $('<div class="div_title_question"></div>');
            var span = $('<span>' + (Investigation_Question_num + 1) + '、您的年龄</span><span style="color:red">&nbsp;*</span>');




            var radioul = $('<ul><li style="width:15%"><input type="radio" name="choice"/><span>Choice1</span></li><li style="width:15%"><input type="radio" name="choice"/><span>Choice2</span></li></ul>');






            var spanRight = $('<div class="spanRight"></div>');
            var StuffClearDiv = $('<div style="clear:both;"></div>')
            var stuff = $('<ul class="stuff"><li><a href="javascript:void(0);"><span class="design-icon design-edit"></span><span>编辑</span></a></li><li><a href="javascript:void(0);"><span class="design-icon design-delete"></span><span>删除</span></a></li><li><a href="javascript:void(0);"><span class="design-icon design-up"></span><span>上移</span></a></li><li><a href="javascript:void(0);"><span class="design-icon design-down"></span><span>下移</span></a></li><div style="clear:both;"></div></ul>');
            var div_title_attr_question = $('<div class="div_title_attr_question" style="overflow:auto;"></div>');
            var TitleSpanLeft = $('<div class="spanLeft"><div style="height: 27px; line-height: 27px; width: 400px; background: rgb(240, 240, 238);"><span style="float:left;"><b style="font-size:14px;">题目标题</b></span></div><div style="width:395px;"><textarea wrap="soft" class="inputtext" id="tcquestion_' + Investigation_Question_num + '" style="style="width: 395px; height: 70px;"></textarea></div></div>');
            var optionedit = $('<div style="margin-left:20px;clear:both;"></div></div>');
            var table = $('<table></table>');
            var option1 = $('<tr><td><input type="text" class="inputtext choicetxt" value="Choice1" /></td><td><span title="在此选项下面插入一个新的选项" class="choiceimg design-icon design-add" style="cursor: pointer;"></span></td><td><span title="删除当前选项（最少保留2个选项）" class="choiceimg design-icon design-minus" style="cursor: pointer;"></span></td></tr>');
            var option2 = $('<tr><td><input type="text" class="inputtext choicetxt" value="Choice2" /></td><td><span title="在此选项下面插入一个新的选项" class="choiceimg design-icon design-add" style="cursor: pointer;"></span></td><td><span title="删除当前选项（最少保留2个选项）" class="choiceimg design-icon design-minus" style="cursor: pointer;"></span></td></tr>');
            div_question.appendTo("#Investigation .Content");
            div_preview.appendTo(div_question);
            div.appendTo(div_question);
            div_title_question_all.appendTo(div_preview);
            div_table_question.appendTo(div_preview);
            div_title_question.appendTo(div_title_question_all);
            span.appendTo(div_title_question);
            radioul.appendTo(div_table_question);
            spanRight.appendTo(div_preview);
            StuffClearDiv.appendTo(div_preview);
            stuff.appendTo(spanRight);
            div_title_attr_question.appendTo(div);
            TitleSpanLeft.appendTo(div_title_attr_question);
            optionedit.appendTo(div_title_attr_question);
            table.appendTo(optionedit);
            option1.appendTo(table); option2.appendTo(table);
            //加载工具栏点击事件
            Investigation.LoadToolClick();
            $("#tcquestion_" + Investigation_Question_num).val((Investigation_Question_num + 1) + "、您的年龄");
            var NewQuestion = $("#Investigation .Content").children(".div_question:last-child");

            NewQuestion.find(".choicetxt").change(Investigation.ChangeValue);

            NewQuestion.find(".choicetxt").parent().next().children().click(Investigation.CreateSingleOption);
            NewQuestion.find(".choicetxt").parent().next().next().children().click(Investigation.DeleteOption);
            $("#tcquestion_" + Investigation_Question_num).change(function () {
                Investigation.cleanSpelChar($(this));
                var str = $(this).val();
                $(this).parents(".div_question").children(".div_preview").children(".div_title_question_all").children(".div_title_question").children("span").eq(0).html(str);
            });
            Investigation_Question_num = Investigation_Question_num + 1;
            Investigation_Question_opnum = Investigation_Question_opnum + 2;
        },








        SaveJson: function (JsonTextName) {
            Investigation_Json_data = [];
            $("#Investigation .Content .div_preview").each(function (i) {
                var opnum = 1;
                var question = {};
                var items = [];
                question.ItemOrdinal = i;

                var type = $(this).children(".div_table_question").children("ul").children("li").eq(0).children("input").attr("type");
                ;
                if (type == "checkbox")
                    question.ItemTypeName = 'MultiItem';
                else if (type == "radio")
                    question.ItemTypeName = 'SingleItem';
                else
                    question.ItemTypeName = 'BlankItem';
                question.Title = $(this).children(".div_title_question_all").children(".div_title_question").children("span").eq(0).html();

                $(this).children(".div_table_question").children("ul").children("li").each(function (j) {
                    var html = $(this).children("span").html();

                    items.push(html);
                    opnum = opnum + 1;
                });
                question.Items = items;
                Investigation_Json_data.push(question);

            });
            alert(JSON.stringify(Investigation_Json_data));
            $("[name='" + JsonTextName + "']").val(JSON.stringify(Investigation_Json_data));
        },






        InvestigationHeadClick: function () {
            $(".InvestigationTitle").css('display', 'block');
            $(".InvestigationTitle").css('top', $(document).scrollTop() + 150);
            $(".BackGrey").css('display', 'block');
            var backHeight = ($(document.body).height() > document.body.clientHeight ? $(document.body).height() : document.body.clientHeight);
            $(".BackGrey").css('height', backHeight);
        },
        Investigation_title_save_Click: function () {
            Investigation.cleanSpelChar($(".InvestigationTitle input[type='text']"));
            Investigation.cleanSpelChar($(".InvestigationTitle textarea"));
            $(".InvestigationHead h1").html($(".InvestigationTitle input[type='text']").val());
            $(".investigationdescription").html($(".InvestigationTitle textarea").val().replace(/\n/g, "<br/>"));
            $(".InvestigationTitle").css('display', 'none');
            $(".BackGrey").css('display', 'none');
        },
        Investigation_title_changebr: function () {
            $(".investigationdescription").html($(".investigationdescription").html().replace(/\n/g, "<br/>"));
        },
        CreateInvestigationEditorView: function (name) {
            $("#Investigation .Content .div_question").remove();
            var data = $.parseJSON($("[name='" + name + "']").val());
            Investigation_Question_num = 0;
            for (var o in data) {
                var div_question = $("<div class='div_question'></div>");
                var div_preview = $('<div class="div_preview"></div>');
                var div = $('<div style="display:block;margin-bottom:5px;"></div>');
                var div_title_question_all = $('<div class="div_title_question_all"></div>');
                var div_table_question = $('<div class="div_table_question" style="overflow:auto;"></div>');
                var div_title_question = $('<div class="div_title_question"></div>');
                var span = $('<span>' + data[o].Title + '</span><span style="color:red">&nbsp;*</span>');
                var spanRight = $('<div class="spanRight"></div>');
                var StuffClearDiv = $('<div style="clear:both;"></div>')
                var stuff = $('<ul class="stuff"><li><a href="javascript:void(0);"><span class="design-icon design-edit"></span><span>编辑</span></a></li><li><a href="javascript:void(0);"><span class="design-icon design-delete"></span><span>删除</span></a></li><li><a href="javascript:void(0);"><span class="design-icon design-up"></span><span>上移</span></a></li><li><a href="javascript:void(0);"><span class="design-icon design-down"></span><span>下移</span></a></li><div style="clear:both;"></div></ul>');
                div_question.appendTo(".Content");
                var div_title_attr_question = $('<div class="div_title_attr_question" style="overflow:auto;"></div>');
                var optionedit = $('<div style="margin-left:20px;clear:both;"></div></div>');
                var TitleSpanLeft = $('<div class="spanLeft"><div style="height: 27px; line-height: 27px; width: 400px; background: rgb(240, 240, 238);"><span style="float:left;"><b style="font-size:14px;">题目标题</b></span></div><div style="width:395px;"><textarea wrap="soft" class="inputtext" id="tcquestion_' + data[o].ItemOrdinal + '" style="style="width: 395px; height: 70px;"></textarea></div></div>');
                var table = $('<table></table>');

                div_preview.appendTo(div_question);
                div.appendTo(div_question);
                div_title_question_all.appendTo(div_preview);
                div_table_question.appendTo(div_preview);
                div_title_question.appendTo(div_title_question_all);
                span.appendTo(div_title_question);
                spanRight.appendTo(div_preview);
                StuffClearDiv.appendTo(div_preview);
                stuff.appendTo(spanRight);
                div_title_attr_question.appendTo(div);
                TitleSpanLeft.appendTo(div_title_attr_question);
                optionedit.appendTo(div_title_attr_question);
                table.appendTo(optionedit);


                var NewQuestion = $("#Investigation .Content").children(".div_question:last-child");
                if (data[o].ItemTypeName == "SingleItem") {
                    var ul = $('<ul></ul>');
                    $.each(data[o].Items, function (n, value) {
                        var li = $('<li style="width:15%"><input type="radio" /><span>' + value + '</span></li>');
                        var option = $('<tr><td><input type="text" class="inputtext choicetxt" value="' + value + '" /></td><td><span title="在此选项下面插入一个新的选项" class="choiceimg design-icon design-add" style="cursor: pointer;"></span></td><td><span title="删除当前选项（最少保留2个选项）" class="choiceimg design-icon design-minus" style="cursor: pointer;"></span></td></tr>');
                        li.appendTo(ul);
                        option.appendTo(table);
                    });
                    ul.appendTo(div_table_question);
                    NewQuestion.find(".choicetxt").parent().next().children().click(Investigation.CreateSingleOption);
                    NewQuestion.find(".choicetxt").parent().next().next().children().click(Investigation.DeleteOption);
                }
                else if (data[o].ItemTypeName == "MultiItem") {
                    var ul = $('<ul></ul>');
                    $.each(data[o].Items, function (n, value) {
                        var li = $('<li style="width:15%"><input type="checkbox" /><span>' + value + '</span></li>');
                        var option = $('<tr><td><input type="text" class="inputtext choicetxt" value="' + value + '" /></td><td><span title="在此选项下面插入一个新的选项" class="choiceimg design-icon design-add" style="cursor: pointer;"></span></td><td><span title="删除当前选项（最少保留2个选项）" class="choiceimg design-icon design-minus" style="cursor: pointer;"></span></td></tr>');
                        li.appendTo(ul);
                        option.appendTo(table);
                    });
                    ul.appendTo(div_table_question);
                    NewQuestion.find(".choicetxt").parent().next().children().click(Investigation.CreateMultiOption);
                    NewQuestion.find(".choicetxt").parent().next().next().children().click(Investigation.DeleteOption);
                }
                else if (data[o].ItemTypeName == "BlankItem") {
                    var ul = $('<ul></ul>');
                    var li = $('<li><textarea readonly="readonly" style="width:165px;height:50px;"></textarea></li>');
                    li.appendTo(ul);
                    ul.appendTo(div_table_question);
                }

                $("#tcquestion_" + data[o].ItemOrdinal).val(data[o].Title);

                NewQuestion.find(".choicetxt").change(Investigation.ChangeValue);

                $("#tcquestion_" + data[o].ItemOrdinal).change(function () {
                    Investigation.cleanSpelChar($(this));
                    var str = $(this).val();
                    $(this).parents(".div_question").children(".div_preview").children(".div_title_question_all").children(".div_title_question").children("span").eq(0).html(str);
                });
                Investigation_Question_num = data[o].ItemOrdinal + 1;
            }
            //加载工具栏点击事件
            Investigation.LoadToolClick();
        },







        SaveEditJson: function (JsonTextName) {
            Investigation_Json_data = [];
            $(".Content .div_preview").each(function (i) {
                var opnum = 1;
                var question = {};
                var items = [];

                var array = $(this).parent().find(".div_title_attr_question textarea").attr("id").split("_");
                question.ItemOrdinal = array[1];

                var type = $(this).children(".div_table_question").children("ul").children("li").eq(0).children("input").attr("type");
                ;
                if (type == "checkbox")
                    question.ItemTypeName = 'MultiItem';
                else if (type == "radio")
                    question.ItemTypeName = 'SingleItem';
                else
                    question.ItemTypeName = 'BlankItem';
                question.Title = $(this).children(".div_title_question_all").children(".div_title_question").children("span").eq(0).html();

                $(this).children(".div_table_question").children("ul").children("li").each(function (j) {
                    var html = $(this).children("span").html();

                    items.push(html);
                    opnum = opnum + 1;
                });
                question.Items = items;
                Investigation_Json_data.push(question);

            });
            $("[name='" + JsonTextName + "']").val(JSON.stringify(Investigation_Json_data));
        },
        TopTool: function () {
            var height = $('#Investigation .TopTool').offset().top;
            $(window).scroll(function(){
                var t = $(window).scrollTop();
                if(t >= height-10){
                    $('#Investigation .TopTool').addClass('fixed');
                }else{
                    $('#Investigation .TopTool').removeClass('fixed');
                }
            });
        },
    };
})(jQuery);

