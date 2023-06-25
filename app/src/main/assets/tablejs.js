function javacalljs(){
     document.getElementById("editor").innerHTML = "<br\>JAVA调用了JS的无参函数";
}
function javacalljswith(arg){
     document.getElementById("editor").innerHTML = ("<br\>"+arg);
}
$(document).ready(function(){
 /*$("#table").click(function(){
      $("#editor").innerHTML = "<br\>JAVA调用了JS的无参函数";
     window.TableJS.test();
  });*/
  // window.TableJS.test();
});

function getTableInfo(obj){
   // window.TableJS.test();
    var html = $(obj).html();
    var tableId = $(obj).attr("id");
    window.TableJS.setCurrentHtml(html,tableId);
}
/*
 * 表格删除
 */
function deleteTable(tableId){
   // window.TableJS.test();
  //  window.TableJS.test(tableId);
   if(null != tableId && 'undifend' != tableId && '' != tableId && null != $("#"+tableId)){
         $("#"+tableId).remove();
       //  window.TableJS.test("succss");
   }
}
/*
 * 表格替换
 */
function replaceTable(tableId,htmlString){
    //window.TableJS.test(tableId);
    //window.TableJS.test(htmlString);
   if(null != tableId && 'undifend' != tableId && '' != tableId && null != $("#"+tableId)){
         $("#"+tableId).html(htmlString);
   }
}
/*
 * 新增行
 */
function addRow(){
   var rows =  $("tbody >tr").length;
   var column =  $("tbody >tr").first().find("td").length;
   if(null == column || column == 0){
      column = 1;
   }
   var tdString = '<td class="table_cell_body" ></td>';
   var string = '<tr class="table_row_body">';
   for(var i= 0; i < column; i++){
        string = string+tdString
   }
   string = string+"</tr>";
   var tbody = $("tbody").append(string);
    updateTableWidth();
 }
 /*
  * 删除行
  */
 function delRow(){
    var rows =  $("tbody >tr").length;
    if(rows > 0){
        $("tbody >tr").last().remove();
    }
     updateTableWidth();
 }
 /*
  * 新增列
  */
 function addColumn(){
     var string  = '<td class="table_cell_body"></td>';
     $("tbody >tr").each(function(){
        $(this).append(string);
     });
      updateTableWidth();
  }
  /*
   * 删除列
   */
 function delColumn(){
        $("tbody >tr").each(function(){
               $(this).find("td").last().remove();
        });
        updateTableWidth();
  }


function updateTableWidth(){
     var tdLength = $("tbody >tr").first().find("td").length;
     if(tdLength == 0){

     }else if(tdLength == 1){
        $("tbody >tr").find("td").each(function (){
            $(this).attr("style","width:100%")
        })
     }else if(tdLength == 2){
        $("tbody >tr").each(function (){
            $(this).find("td").first().attr("style","width:33%");
            $(this).find("td").last().attr("style","width:67%");
         })
     }else if(tdLength == 3){
            $("tbody >tr").each(function (){
                 $(this).find("td").first().attr("style","width:30%");
                 $(this).find("td").eq(1).attr("style","width:35%");
                 $(this).find("td").last().attr("style","width:35%");
            })
     }else{
             $("tbody >tr").each(function (){
               var  width = parseFloat(100/tdLength).toFixed(2);
                $(this).find("td").each(function(){
                     $(this).attr("style","width:"+width+"%");
                })
             });
     }
     getTableHtml();
}

function getTableHtml(){
    var table = $("table");
    var html = "";
    if(null != table && 'undifend' != table && '' != table){
      var html =  $(table).eq(0).parent().html();
      window.TableJS.setCurrentHtml(html,"");
    }
}