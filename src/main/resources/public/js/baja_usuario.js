$(document).ready(function(){
  $("#inputUsuarioBaja").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#bajaUsuarios tbody tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});