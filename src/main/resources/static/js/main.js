(function ($) {

        $.fontSelector = function () {
            $("#font_select").find("option").on('click', function (e) {
                var font_id = $(e.target).attr("id")
                $(".font_table").hide();
                $("#font_table_" + font_id).show();
                $("#font_name").html($(e.target).val());
                $("#btn_viewfont").attr("href", "/font/"+font_id+"/show");
            });


            $("#font_select option:first").attr('selected','selected');
            $("#font_select option:first").prop('selected', true);
            $("#font_select option:first").click();
        }


        $.filterProjects = function () {
            var filter = $("#projectListFilter");
            filter.change(function() {
                var selected = filter.find(":selected").text();
                $("#projects-table").find("tr").show();
                var statusCells = $("#projects-table").find("td.status");
                statusCells.each(function() {
                    var text = $(this).html();
                    if(selected=='Changed') {
                        if(text=='OK') {
                            $(this).parent().hide();
                        }
                    } else if(selected=='Ok') {
                        if(text=='CHANGED') {
                            $(this).parent().hide();
                        }
                    }
                });
            });
        }
        $.tripletSelector = function () {
            var filter = $("#triplet_selector");
            filter.on('click', function(e) {
                filter.find("input").prop('checked', false);
                var tripletId = $(e.target).attr("id");
                $(e.target).prop('checked', true);
                $(".triplet_info").hide();
                $("#info" + tripletId).show();

                $("#btn_dlttriplet").attr("href", "/triplet/"+tripletId+"/delete");
                $("#btn_edittriplet").attr("href", "/triplet/"+tripletId+"/edit");
            });
            $("#triplet_selector input:first").click();
        }

    }(jQuery)
);


$(document).ready(function () {
    $.fontSelector();
    $.filterProjects();
    $.tripletSelector();
});