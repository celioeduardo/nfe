/**
 * @class Ext.draw.modifier.Target
 * @extends Ext.draw.modifier.Modifier
 *
 * This is the destination (top) modifier that has to be put at
 * the top of the modifier stack.
 *
 * The Target modifier figures out which updaters have to be called
 * for the changed set of attributes and makes the sprite and its instances (if any)
 * call them.
 *
 */
Ext.define('Ext.draw.modifier.Target', {
    requires: ['Ext.draw.Matrix'],
    extend: 'Ext.draw.modifier.Modifier',
    alias: 'modifier.target',
    statics: {
        uniqueId: 0
    },

    /**
     * @inheritdoc
     */
    prepareAttributes: function (attr) {
        if (this._previous) {
            this._previous.prepareAttributes(attr);
        }
        attr.attributeId = 'attribute-' + Ext.draw.modifier.Target.uniqueId++;
        if (!attr.hasOwnProperty('canvasAttributes')) {
            attr.bbox = {
                plain: {dirty: true},
                transform: {dirty: true}
            };
            attr.dirty = true;
            /*
            Maps updaters that have to be called to the attributes that triggered the update.
            It is basically a reversed dirtyTriggers map (see Ext.draw.sprite.AttributeDefinition),
            but only for those attributes that have changed.
            dirtyFlags updaters are called by the sprite.updateDirtyFlags method.

            The 'canvas' updater is a special kind of updater that is not actually a function
            but a flag indicating that the attribute should be applied directly to a canvas
            context.
            */
            attr.dirtyFlags = {};
            /*
            Holds the attributes that triggered the canvas update (attr.dirtyFlags.canvas).
            Canvas attributes are applied directly to a canvas context
            by the sprite.useAttributes method.
            */
            attr.canvasAttributes = {};
            attr.matrix = new Ext.draw.Matrix();
            attr.inverseMatrix = new Ext.draw.Matrix();
        }
    },

    /**
     * @private
     * Applies the appropriate dirty flags from the modifier changes.
     * @param {Object} attr The source attributes.
     * @param {Object} changes The modifier changes.
     */
    setDirtyFlags: function (attr, changes) {
        Ext.apply(attr, changes);
        var sprite = this.getSprite(),
            dirtyFlags = attr.dirtyFlags,
            dirtyTriggers = sprite.self.def.getDirtyTriggers(),
            triggers, trigger, instances, instance,
            name, flags, hasChanges, canvasAttributes,
            i, j, ln;

        for (name in changes) {
            hasChanges = true;
            if ((triggers = dirtyTriggers[name])) {
                i = 0;
                while ((trigger = triggers[i++])) {
                    if (!(flags = dirtyFlags[trigger])) {
                        flags = dirtyFlags[trigger] = [];
                    }
                    flags.push(name);
                }
            }
            if (attr.template && changes.removeFromInstance && changes.removeFromInstance[name]) {
                delete attr[name];
            }
        }

        if (!hasChanges) {
            return;
        }

        // This can prevent sub objects to set duplicated attributes to context.
        if (dirtyFlags.canvas) {
            canvasAttributes = dirtyFlags.canvas;
            delete dirtyFlags.canvas;
            for (i = 0, ln = canvasAttributes.length; i < ln; i++) {
                name = canvasAttributes[i];
                attr.canvasAttributes[name] = attr[name];
            }
        }

        // If the attributes of an instancing sprite template are being modified here,
        // then spread the dirty flags to the instances (template's children).
        if (attr.hasOwnProperty('children')) {
            instances = attr.children;
            for (i = 0, ln = instances.length; i < ln; i++) {
                instance = instances[i];
                Ext.apply(instance.dirtyFlags, dirtyFlags);
                if (canvasAttributes) {
                    for (j = 0; j < canvasAttributes.length; j++) {
                        name = canvasAttributes[j];
                        instance.canvasAttributes[name] = instance[name];
                    }
                }
                sprite.updateDirtyFlags(instance);
            }
        }

        sprite.setDirty(true);
    },

    /**
     * @inheritdoc
     */
    popUp: function (attributes, changes) {
        this.setDirtyFlags(attributes, changes);
        this._sprite.updateDirtyFlags(attributes);
    },

    /**
     * @inheritdoc
     */
    pushDown: function (attr, changes) {
        if (this._previous) {
            changes = this._previous.pushDown(attr, changes);
        }
        this.setDirtyFlags(attr, changes);
        this._sprite.updateDirtyFlags(attr);
        return changes;
    }
});
