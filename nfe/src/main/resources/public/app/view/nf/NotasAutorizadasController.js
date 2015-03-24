Ext.define('nfe.view.nf.NotasAutorizadasController', {
    extend: 'Ext.app.ViewController',

    requires: [
        'Ext.window.MessageBox',
        'Ext.window.Toast'
    ],

    alias: 'controller.notas-autorizadas',

    onClickAtualizar: function (){
        var store = this.getViewModel().getStore('notasAutorizadas');
        var vm = this.getViewModel();        
        var filtroNumero = this.lookupReference('filtronf').getValue();
        
        store.getProxy().setExtraParams({
        	ambiente:vm.get('ambiente'),
        	filial:vm.get('filial'),
            numero:filtroNumero
        });
        
        store.load();
        
    },
    onClickEnviar: function () {

        if (this.getView().getSelection().length==0){
            Ext.Msg.alert('Selecionar','Nenhum item selecionado!');
        } else {
            Ext.Msg.confirm('Confirmar', 'Enviar Emails?', 'enviarEmails', this);
        }
    },

    enviarEmails: function (choice) {
        if (choice === 'yes') {
            var s = this.getView().getSelection();

            var ids = [];
            for (var i = 0; i < s.length; i++) {
                ids.push(s[i].get('notaFiscalId'));
            }
            
            var model = new nfe.model.NotaFiscal();
            var grid = this.getView();
            grid.getView().mask('Enviando...');
            var me = this;
            model.enviarEmails('HOMOLOGACAO',ids,function(){
            	me.getViewModel().getStore('notasAutorizadas').reload();
            },
            null,
            function(){
            	grid.getView().unmask();
            });

        }
    },
    
    rendererNumero: function(numero, metadata, rec){
        var nf = rec.get('serie') != null ? numero+'/'+rec.get('serie') : numero,
            es = rec.get('tipo') == 'ENTRADA' ? 'entrada' : 'saída';

        return Ext.String.format(
            '<div style="font-size:x-large;font-weight: bold;">{0}</div>'+
            '<div style="padding: 2px 0px 0px 0px; font-size: 12px;font-color=gray;color: gray;font-style: italic;">{4}</div>'+
            '<div style="padding: 2px 0px 0px 0px; font-weight: 400;font-size: 18px;line-height: 22px; font-family:Arial">{1}</div>'+
            '<hr style="margin: 2px">'+
            '<div style="font-size: small;font-color=gray;color: gray;font-style: italic;">{2} - emitida em {3} - ({5})</div>',
            nf,
            rec.get('publicoNome'),
            es,
            Ext.util.Format.date(rec.get('emissao'),'d/m/Y'),
            rec.get('chave'),
            nfe.model.NotaFiscal.getTipoEmissao(rec.get('tipoEmissao')));
    },

    rendererValor: function(valor, metadata, rec){
        return Ext.String.format(
            '<div style="font-size: large;font-weight: bold;font-style: italic;margin-top: 20px;">{0}</div>',
            "R$ " + Ext.util.Format.number(valor,'0,000.00'));
    },
    rendererAutorizacao: function(valor, metadata, rec){
    	return Ext.String.format(
    			'<div style="font-size: 12px;font-style: normal;margin-top: 20px;">{0}</div>',
    			"autorização<br>" + Ext.Date.format(valor,'d/m/Y H:i:s') +
    			"<br>"+rec.get('numeroProtocoloAutorizacao'));
    },

    rendererDanfe: function(column, widget, record) {

    	if (record.get('tipoEmissao')=='FS_DA')
    		widget.setText('FS-DA');
    	else
    		widget.setText('danfe');
    },
    rendererCceCorrecao: function(correcao, metadata, rec){
        return Ext.String.format('<a href="notas_fiscais/imprimir_cce?notaFiscalId={0}&sequencia={1}" target="_blank">{2}</a>',
        	rec.get('notaFiscalId'),
        	rec.get('cceSequencia'),
        	Ext.util.Format.ellipsis(correcao,100));
    },
    rendererObservacao: function(valor, metadata, rec){
        if (rec.get('msgDescricao') == null) 
            return '';
        
        var descricao = rec.get('msgDescricao');
        descricao = descricao.replace(/[\n\r]/g,"<br>");
        
        return Ext.String.format(
            '<div style="font-style: italic;font-color=red;margin-top: 20px;">{0} - {1}</div>',
            rec.get('msgCodigo'), descricao);
    },
    enviarEmail: function(btn) {
       var rec = btn.getWidgetRecord();
        Ext.toast({
            title: 'Enviando e-mail...',
            html: 'Nota Fiscal '+rec.get('numero') + '/' + rec.get('serie'),
            align: 't',
            bodyPadding: 10,
            width:350
        });
        nfe.model.NotaFiscal.enviarEmail(rec.get('notaFiscalId'));
    },
    
    imprimirDanfe: function(btn) {
    	var me = this,rec = btn.getWidgetRecord();    	
    
    	window.open('notas_fiscais/imprimir_danfe?notaFiscalId=' + rec.get('notaFiscalId'));
    	
    	this.getViewModel().getStore('notasAutorizadas').load();
    	
    },
    
    cancelar: function(btn) {
    	var rec = btn.getWidgetRecord(),
    		me = this;
    	Ext.Msg.prompt('Cancelar Nota Fiscal '+rec.get('numero'),
    			'Informe o motivo do cancelamento',
    			function(result,justificativa) {
    		var me = this;
    		if (result === 'ok') {
    			
    			var tamanhoJustificativa = justificativa.length;
    			
    			if (tamanhoJustificativa < 15 || tamanhoJustificativa > 255){
    				Ext.Msg.show({
    					title: 'Motivo inválido',
    					message: 'O Motivo do cancelamento tem que ter no mínimo 15 '+
    					'e no máximo 255 caracteres. Tamanho informado: '+tamanhoJustificativa,
    					buttons: Ext.Msg.OK,
    					icon: Ext.window.MessageBox.INFO
    				});
    			} else {
    				me.getView().mask('Cancelando...');
                	nfe.model.NotaFiscal.cancelar(rec.get('notaFiscalId'),justificativa,
	                	function(){
	                		me.getView().focus(); // Bug de focus no ExtJS - precisa ter essa chamada..
	                		Ext.toast({
	    		                title: 'Nota Fiscal Cancelada com Sucesso',
	    		                html: 'Nota Fiscal '+rec.get('numero') + '/' + rec.get('serie') + 
	    		                	' foi cancelada com sucesso.',
	    		                align: 't',
	    		                bodyPadding: 10,
	    		                width:350
	    		            });
	            	    	me.getViewModel().getStore('notasAutorizadas').reload();
	                    },null,
	                    function(){
	                    	me.getView().unmask();
	                    });
    			}
    		} 
    	},this);
    },
    onAbrirCartaCorrecao: function(btn) {
    	var rec = btn.getWidgetRecord(),
			me = this;
    	
		this.windowCce = Ext.widget('cce',{
				modal:true,
				viewModel:{
					data:{
						notaFiscalId: rec.get('notaFiscalId'),
						nf: rec
					}
				},
				listeners:{
					registrarCartaCorrecao:'registrarCartaCorrecao'
				}
			});
    	this.getView().add(this.windowCce);
    	this.windowCce.show();
    },
    registrarCartaCorrecao:function(notaFiscalId,correcao){
    	var me = this;
    	nfe.model.NotaFiscal.registrarCce(notaFiscalId,correcao,
    		function(){
    			me.windowCce.close();
    			me.windowCce = null;
    			Ext.toast({
    	            title: 'Carta de Correção',
    	            html: 'Carta de Correção registrada com Sucesso para NotaFiscal.',
    	            align: 't',
    	            bodyPadding: 10,
    	            width:350
    	        });
    		});
    }
});
