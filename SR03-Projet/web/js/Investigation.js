$(document).ready(function() {

    var MaxInputs       = 8; //maximum input boxes allowed
    var InputsWrapper   = $("#InputsWrapper"); //Input boxes wrapper ID
    var AddButton       = $("#AddMoreFileBox"); //Add button ID

    var x = InputsWrapper.length; //initlal text box count
    var QuestionCount=0; //to keep track of text box added

    $(AddButton).click(function (e)  //on add input button click
    {
        if(x <= MaxInputs) //max input box allowed
        {
            QuestionCount++; //text box added increment
            //add input box
            $(InputsWrapper).append('<div class="test_content_nr"><ul><li><div class="test_content_nr_tt"><i>*</i><font><input class = "input_control" type="text" name="questionnaire.questions['+QuestionCount+'].Description" placeholder="Question '+ QuestionCount +'"/></font></div><div class="test_content_nr_main"><ul><ul><li class="option"><input type="text" style="width:800px;" name="questionnaire.questions['+QuestionCount+'].choices[0].description" placeholder="Choice1"/></li></ul><ul><li class="option"><input type="text" style="width:800px;" name="questionnaire.questions['+QuestionCount+'].choices[1].description" placeholder="Choice2"/></li></ul><ul><li class="option"><input type="text" style="width:800px;" name="questionnaire.questions['+QuestionCount+'].choices[2].description" placeholder="Choice3"/></li></ul><ul><li class="option"><input type="text" style="width:800px;" name="questionnaire.questions['+QuestionCount+'].choices[3].description" placeholder="Choice4"/></li></ul></ul></div></li><div><div align="right">Answer:   A<input style = "font-size:1em;height:1.5em;width:12%;margin-right: 10px" name = "questionnaire.questions['+QuestionCount+'].choices[0].isRight" type = "checkbox" value="true">B<input style = "font-size:1em;height:1.5em;width:12%;margin-right: 10px" name = "questionnaire.questions['+QuestionCount+'].choices[1].isRight" type = "checkbox" value="true">C<input style = "font-size:1em;height:1.5em;width:12%;margin-right: 10px" name = "questionnaire.questions['+QuestionCount+'].choices[2].isRight" type = "checkbox" value="true">D<input style = "font-size:1em;height:1.5em;width:12%;margin-right: 10px" name = "questionnaire.questions['+QuestionCount+'].choices[3].isRight" type = "checkbox" value="true"></div><a href="#" class="removeclass"><input style="background-color: #79aef0;border: none;color: white;padding: 10px 22px;text-align: center;text-decoration: none;display: inline-block;font-size: 13px;" type="button" value="Delete"></a></div></div></li></ul></div>');
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

    };
})(jQuery);

