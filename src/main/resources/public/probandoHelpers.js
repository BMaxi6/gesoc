    $(function () {
  Handlebars.registerHelper('capitalize', function(str){
    str = str || '';
    return str.slice(0,1).toUpperCase() + str.slice(1);
  });
  var theTemplateScript = $("#built-in-helpers-template").html();
  var theTemplate = Handlebars.compile(theTemplateScript);
  var context = {
    egresos:[
      {
            numeroOp: "1",
      },
      {
            numeroOp: "2",
      }
    ]
  };
  var theCompiledHtml = theTemplate(context);
  $('.content-placeholder').html(theCompiledHtml);
});