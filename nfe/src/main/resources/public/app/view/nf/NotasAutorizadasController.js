Ext.define('nfe.view.nf.NotasAutorizadasController', {
    extend: 'Ext.app.ViewController',

    requires: [
        'Ext.MessageBox'
    ],

    alias: 'controller.notas-autorizadas',

    onClickAtualizar: function (){
        this.getViewModel().getStore('notasAutorizadas').load(
            {"params":{
                "notafiscalid":"03F79B1D8D397592E050007F01005CC8"
            }
        });
        //this.getViewModel().getStore('notasAutorizadas').reload();
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
        return '<a href="notas_fiscais/imprimir_danfe?notafiscalid=' + val + '" target="_blank" >imprimir DANFE</a>';
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
