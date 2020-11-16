


           Handlebars.registerHelper("saludar", function()
                                    {
             return "hola";  
           });
	
Handlebars.registerHelper('json', function (content) {
    return JSON.stringify(content);
});