Ext.define('nfe.view.nf.NotasAutorizadasController', {
    extend: 'Ext.app.ViewController',

    requires: [
        'Ext.window.MessageBox'
    ],

    alias: 'controller.notas-autorizadas',

    onClickAtualizar: function (){
        this.getViewModel().getStore('notasAutorizadas').load();
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
            	//me.fireViewEvent('notasPendentesEnviadas');
            	console.log('sucesso!');
            },
            null,
            function(){
            	grid.getView().unmask();
            });

        }
    },
    

    rendererNumero: function(numero, metadata, rec){
        var nf = rec.get('serie') != null ? numero+'/'+rec.get('serie') : numero,
            es = rec.get('tipo') == 'E' ? 'entrada' : 'saída';

        return Ext.String.format(
            '<div style="font-size:x-large;font-weight: bold;">{0}</div>'+
            '<div style="padding: 2px 0px 0px 0px; font-size: 12px;font-color=gray;color: gray;font-style: italic;">{4}</div>'+
            '<div style="padding: 2px 0px 0px 0px; font-weight: 400;font-size: 18px;line-height: 22px; font-family:Arial">{1}</div>'+
            '<hr style="margin: 2px">'+
            '<div style="font-size: small;font-color=gray;color: gray;font-style: italic;">{2} - emitida em {3}</div>',
            nf,
            rec.get('publicoNome'),
            es,
            Ext.util.Format.date(rec.get('emissao'),'d/m/Y'),
            rec.get('chave'));
    },

    rendererValor: function(valor, metadata, rec){
        return Ext.String.format(
            '<div style="font-size: large;font-weight: bold;font-style: italic;margin-top: 20px;">{0}</div>',
            "R$ " + Ext.util.Format.number(valor,'0,000.00'));
    },

    rendererDanfe: function (val, meta, record) {
    	var me = this.getViewModel();
    	if (!me.get('nao_impressa'))
    		return '<div style="padding: 0px 0px 0px 0px; text-align: center"><a href="notas_fiscais/imprimir_danfe?notafiscalid=' + val + '" target="_blank"><img src="resources/images/danfe.jpg" height="100%" width="100%"/></a></div>'+
    			        '<div style="padding: 0px 0px 0px 0px; text-align: center"><a href="notas_fiscais/imprimir_danfe?notafiscalid=' + val + '" target="_blank"><img src="resources/images/email.jpg" height="100%" width="100%"/></a></div>';
    	
    	return '<div style="padding: 0px 0px 0px 0px; font-size:x-large;line-height: 22px; text-align: center"><a href="notas_fiscais/imprimir_danfe?notafiscalid=' + val + '" target="_blank"><img src="resources/images/danfe.jpg" height="100%" width="100%"/></a></div>';
    },
    
    rendererObservacao: function(valor, metadata, rec){
        if (rec.get('msgDescricao') == null) 
            return '';
        
        var descricao = rec.get('msgDescricao');
        descricao = descricao.replace(/[\n\r]/g,"<br>");
        
        return Ext.String.format(
            '<div style="font-style: italic;font-color=red;margin-top: 20px;">{0} - {1}</div>',
            rec.get('msgCodigo'), descricao);
    }    
});
