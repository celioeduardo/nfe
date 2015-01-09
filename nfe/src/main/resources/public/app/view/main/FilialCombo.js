Ext.define('nfe.view.main.FilialCombo', {
    extend: 'Ext.ux.TreePicker',
    xtype: 'filialcombo',
    displayField: 'nome',    
    initComponent: function() {
        var me = this;

        me.callParent(arguments);
        
    },
    
    createPicker: function() {
        var me = this,
            picker = new Ext.tree.Panel({
                shrinkWrapDock: 2,
                store: me.store,
                floating: true,
                displayField: me.displayField,
                rootVisible: false,
                columns: me.columns,
                minHeight: me.minPickerHeight,
                maxHeight: me.maxPickerHeight,
                manageHeight: false,
                shadow: false,
                listeners: {
                    scope: me,
                    itemclick: me.onItemClick
                },
                viewConfig: {
                    listeners: {
                        scope: me,
                        render: me.onViewRender
                    }
                }
            }),
            view = picker.getView();

        if (Ext.isIE9 && Ext.isStrict) {
            // In IE9 strict mode, the tree view grows by the height of the horizontal scroll bar when the items are highlighted or unhighlighted.
            // Also when items are collapsed or expanded the height of the view is off. Forcing a repaint fixes the problem.
            view.on({
                scope: me,
                highlightitem: me.repaintPickerView,
                unhighlightitem: me.repaintPickerView,
                afteritemexpand: me.repaintPickerView,
                afteritemcollapse: me.repaintPickerView
            });
        }
        return picker;
    },
    
    onItemClick: function(view, record, node, rowIndex, e){
    	if(record.get('leaf') == false) return false;
    	this.selectItem(record);
    }
});
