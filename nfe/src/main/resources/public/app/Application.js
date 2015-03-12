/**
 * The main application class. An instance of this class is created by app.js when it calls
 * Ext.application(). This is the ideal place to handle application launch and initialization
 * details.
 */
Ext.define('nfe.Application', {
    extend: 'Ext.app.Application',
    
    name: 'nfe',

    views: [
        // TODO: add views here
    ],

    controllers: [
        'Root'
        // TODO: add controllers here
    ],

    stores: [
    ],
    
    launch: function () {
        Ext.Ajax.on("requestexception", this.tratarErroGlobal, this);
    },

    tratarErroGlobal: function(conn, response, opt, opt2) {
    	
        if (response.timedout)
            Ext.Msg.show({
                title: 'Atenção',
                msg: 'Tempo de espera esgotado',
                icon: Ext.Msg.ERROR,
                buttons: Ext.Msg.OK
            });
        else {
            this.tratarErro(response);
        }
    },
    
    tratarErro: function(response){
    	var resTxt = Ext.decode(response.responseText);
        if (response.status == 404)
        	Ext.Msg.show({
	            title: 'Recurso não encontado. (404)',
	            msg:resTxt.path,
	            icon: Ext.Msg.ERROR,
	            buttons: Ext.Msg.OK
	        });
        else if (response.status == 403)
    		Ext.Msg.show({
    			title: 'Acesso Negado',
    			msg:'Acesso Negado em '
    				+resTxt.path
    				+'. ['
    				+resTxt.message+']',
    			icon: Ext.Msg.ERROR,
    			buttons: Ext.Msg.OK
    		});
        else if (response.status == 401)
        	Ext.Msg.show({
        		title: 'Não autenticado',
        		msg:'Não autenticado.',
    			icon: Ext.Msg.ERROR,
    			buttons: Ext.Msg.OK
        	});
        else {
	        Ext.Msg.show({
	            title: 'Atenção',
	            msg:resTxt.erro,
	            icon: Ext.Msg.WARNING,
	            buttons: Ext.Msg.OK
	        });
        }
    },
    
    glyphFontFamily: 'font-awesome'
});
