/**
 * @private
 * @class Ext.draw.ContainerBase
 */
Ext.define('Ext.draw.ContainerBase', {
    extend: 'Ext.panel.Panel',

    requires: ['Ext.window.Window'],

    layout: 'container',

    // Adds a listener to this draw container's element. If the element does not yet exist
    // addition of the listener will be deferred until onRender.  Useful when listeners
    // need to be attached during initConfig.
    addElementListener: function() {
        var me = this,
            args = arguments;

        if (me.rendered) {
            me.el.on.apply(me.el, args);
        } else {
            me.on('render', function() {
                me.el.on.apply(me.el, args);
            });
        }
    },

    removeElementListener: function() {
        var me = this,
            args = arguments;
        if (me.rendered) {
            me.el.un.apply(me.el, args);
        }
    },

    afterRender: function() {
        this.callParent(arguments);
        this.initAnimator();
    },

    getItems: function() {
        var me = this,
            items = me.items;

        if (!items || !items.isMixedCollection) {
            // getItems may be called before initItems has run and created the items
            // collection, so we have to create it here just in case (this can happen
            // if getItems is called during initConfig)
            me.initItems();
        }

        return me.items;
    },

    onRender: function() {
        this.callParent(arguments);
        this.element = this.el;
        this.innerElement = this.body;
    },

    setItems: function(items) {
        this.items = items;
        return items;
    },

    setSurfaceSize: function(width, height) {
        this.resizeHandler({width:width, height:height});
        this.renderFrame();
    },

    onResize: function () {
        var box = this.body.lastBox;
        this.onBodyResize(box.width, box.height);
    },

    preview: function () {
        var image = this.getImage();
        new Ext.window.Window({
            title: 'Chart Preview',
            closeable: true,
            renderTo: Ext.getBody(),
            autoShow: true,
            maximizeable: true,
            maximized: true,
            border: true,
            layout: {
                type: 'hbox',
                pack: 'center',
                align: 'middle'
            },
            items: {
                xtype: 'container',
                items: {
                    xtype: 'image',
                    mode: 'img',
                    cls: Ext.baseCSSPrefix + 'chart-image',
                    src: image.data,
                    listeners: {
                        afterrender: function () {
                            var me = this,
                                img = me.imgEl.dom,
                                ratio = image.type === 'svg' ? 1 : (window['devicePixelRatio'] || 1),
                                size;

                            if (!img.naturalWidth || !img.naturalHeight) {
                                img.onload = function () {
                                    var width = img.naturalWidth,
                                        height = img.naturalHeight;
                                    me.setWidth(Math.floor(width / ratio));
                                    me.setHeight(Math.floor(height / ratio));
                                }
                            } else {
                                size = me.getSize();
                                me.setWidth(Math.floor(size.width / ratio));
                                me.setHeight(Math.floor(size.height / ratio));
                            }
                        }
                    }
                }
            }
        });
    },

    privates: {
        getTargetEl: function() {
            return this.innerElement;
        }
    }
});

/**
 * @private
 * @class Ext.draw.SurfaceBase
 */
Ext.define('Ext.draw.SurfaceBase', {
    extend: 'Ext.Widget'
});

/**
 * Represents an RGB color and provides helper functions on it e.g. to get
 * color components in HSL color space.
 */
Ext.define('Ext.draw.Color', {
    statics: {
        colorToHexRe: /(.*?)rgb\((\d+),\s*(\d+),\s*(\d+)\)/,
        rgbToHexRe: /\s*rgb\((\d+),\s*(\d+),\s*(\d+)\)/,
        rgbaToHexRe: /\s*rgba\((\d+),\s*(\d+),\s*(\d+),\s*([\.\d]+)\)/,
        hexRe: /\s*#([0-9a-fA-F][0-9a-fA-F]?)([0-9a-fA-F][0-9a-fA-F]?)([0-9a-fA-F][0-9a-fA-F]?)\s*/,

        NONE: 'none',
        RGB_NONE: 'rgb(0, 0, 0)',
        RGBA_NONE: 'rgba(0, 0, 0, 0)'
    },

    isColor: true,
    /**
     * @cfg {Number} lightnessFactor
     *
     * The default factor to compute the lighter or darker color.
     */
    lightnessFactor: 0.2,

    /**
     * @constructor
     * @param {Number} red Red component (0..255)
     * @param {Number} green Green component (0..255)
     * @param {Number} blue Blue component (0..255)
     * @param {Number} [alpha=1] (optional) Alpha component (0..1)
     */
    constructor: function (red, green, blue, alpha) {
        this.setRGB(red, green, blue, alpha);
    },

    setRGB: function (red, green, blue, alpha) {
        var me = this;
        me.r = Math.min(255, Math.max(0, red));
        me.g = Math.min(255, Math.max(0, green));
        me.b = Math.min(255, Math.max(0, blue));
        if (alpha === undefined) {
            me.a = 1;
        } else {
            me.a = Math.min(1, Math.max(0, alpha));
        }
    },

    /**
     * Returns the gray value (0 to 255) of the color.
     *
     * The gray value is calculated using the formula r*0.3 + g*0.59 + b*0.11.
     *
     * @return {Number}
     */
    getGrayscale: function () {
        // http://en.wikipedia.org/wiki/Grayscale#Converting_color_to_grayscale
        return this.r * 0.3 + this.g * 0.59 + this.b * 0.11;
    },

    /**
     * Get the equivalent HSL components of the color.
     * @param {Array} [target] Optional array to receive the values.
     * @return {Array}
     */
    getHSL: function (target) {
        var me = this,
            r = me.r / 255,
            g = me.g / 255,
            b = me.b / 255,
            max = Math.max(r, g, b),
            min = Math.min(r, g, b),
            delta = max - min,
            h,
            s = 0,
            l = 0.5 * (max + min);

        // min==max means achromatic (hue is undefined)
        if (min !== max) {
            s = (l < 0.5) ? delta / (max + min) : delta / (2 - max - min);
            if (r === max) {
                h = 60 * (g - b) / delta;
            } else if (g === max) {
                h = 120 + 60 * (b - r) / delta;
            } else {
                h = 240 + 60 * (r - g) / delta;
            }
            if (h < 0) {
                h += 360;
            }
            if (h >= 360) {
                h -= 360;
            }
        }
        if (target) {
            target[0] = h;
            target[1] = s;
            target[2] = l;
        } else {
            target = [h, s, l];
        }
        return target;
    },

    /**
     * Set current color based on the specified HSL values.
     *
     * @param {Number} h Hue component (0..359)
     * @param {Number} s Saturation component (0..1)
     * @param {Number} l Lightness component (0..1)
     * @return this
     */
    setHSL: function (h, s, l) {
        var c, x, m,
            abs = Math.abs,
            floor = Math.floor;
        h = (h % 360 + 360 ) % 360;
        s = s > 1 ? 1 : s < 0 ? 0 : s;
        l = l > 1 ? 1 : l < 0 ? 0 : l;
        if (s === 0 || h === null) {
            l *= 255;
            this.setRGB(l, l, l);
        }
        else {
            // http://en.wikipedia.org/wiki/HSL_and_HSV#From_HSL
            // C is the chroma
            // X is the second largest component
            // m is the lightness adjustment
            h /= 60;
            c = s * (1 - abs(2 * l - 1));
            x = c * (1 - abs(h - 2 * floor(h / 2) - 1));
            m = l - c / 2;
            m *= 255;
            c *= 255;
            x *= 255;
            switch (floor(h)) {
                case 0:
                    this.setRGB(c + m, x + m, m);
                    break;
                case 1:
                    this.setRGB(x + m, c + m, m);
                    break;
                case 2:
                    this.setRGB(m, c + m, x + m);
                    break;
                case 3:
                    this.setRGB(m, x + m, c + m);
                    break;
                case 4:
                    this.setRGB(x + m, m, c + m);
                    break;
                case 5:
                    this.setRGB(c + m, m, x + m);
                    break;
            }
        }
        return this;
    },

    /**
     * Return a new color that is lighter than this color.
     * @param {Number} [factor=0.2] Lighter factor (0..1).
     * @return {Ext.draw.Color}
     */
    createLighter: function (factor) {
        var hsl = this.getHSL();
        factor = factor || this.lightnessFactor;
        // COMPAT Ext.util.Numbers -> Ext.Number
        hsl[2] = hsl[2] + factor;
        if (hsl[2] > 1) {
            hsl[2] = 1;
        } else if (hsl[2] < 0) {
            hsl[2] = 0;
        }
        return Ext.draw.Color.fromHSL(hsl[0], hsl[1], hsl[2]);
    },

    /**
     * Return a new color that is darker than this color.
     * @param {Number} [factor=0.2] Darker factor (0..1).
     * @return {Ext.draw.Color}
     */
    createDarker: function (factor) {
        factor = factor || this.lightnessFactor;
        return this.createLighter(-factor);
    },

    /**
     * Return the color in the hex format, i.e. '#rrggbb'.
     * @return {String}
     */
    toString: function () {
        var me = this,
            round = Math.round;

        if (me.a === 1) {
            var r = round(me.r).toString(16),
                g = round(me.g).toString(16),
                b = round(me.b).toString(16);
            r = (r.length === 1) ? '0' + r : r;
            g = (g.length === 1) ? '0' + g : g;
            b = (b.length === 1) ? '0' + b : b;
            return ['#', r, g, b].join('');
        } else {
            return 'rgba(' + [
                round(me.r),
                round(me.g),
                round(me.b),
                me.a === 0 ? 0 : me.a.toFixed(15)
            ].join(', ') + ')';
            // Even though things like 'rgba(0,0,0,0)' will probably get converted to
            // 'rgba(0, 0, 0, 0)' when assigned to ctx.fillStyle or ctx.strokeStyle,
            // we can't be sure this is the case for every browser, so for consistency
            // with the Ext.draw.Color.RGBA_NONE (which is used a lot for checks)
            // we join using the ', ' and not ',' here.
        }
    },

    /**
     * Convert a color to hexadecimal format.
     *
     * @param {String/Array} color The color value (i.e 'rgb(255, 255, 255)', 'color: #ffffff').
     * Can also be an Array, in this case the function handles the first member.
     * @return {String} The color in hexadecimal format.
     */
    toHex: function (color) {
        if (Ext.isArray(color)) {
            color = color[0];
        }
        if (!Ext.isString(color)) {
            return '';
        }
        if (color.substr(0, 1) === '#') {
            return color;
        }
        var digits = Ext.draw.Color.colorToHexRe.exec(color);

        if (Ext.isArray(digits)) {
            var red = parseInt(digits[2], 10),
                green = parseInt(digits[3], 10),
                blue = parseInt(digits[4], 10),
                rgb = blue | (green << 8) | (red << 16);
            return digits[1] + '#' + ("000000" + rgb.toString(16)).slice(-6);
        }
        else {
            return '';
        }
    },

    /**
     * Parse the string and set current color.
     *
     * Supported formats: '#rrggbb', '#rgb', and 'rgb(r,g,b)'.
     *
     * If the string is not recognized, an `undefined` will be returned instead.
     *
     * @param {String} str Color in string.
     * @return this
     */
    setFromString: function (str) {
        var values, r, g, b, a = 1,
            parse = parseInt;

        if (str === Ext.draw.Color.NONE) {
            this.r = this.g = this.b = this.a = 0;
            return this;
        }

        if ((str.length === 4 || str.length === 7) && str.substr(0, 1) === '#') {
            values = str.match(Ext.draw.Color.hexRe);
            if (values) {
                r = parse(values[1], 16) >> 0;
                g = parse(values[2], 16) >> 0;
                b = parse(values[3], 16) >> 0;
                if (str.length === 4) {
                    r += (r * 16);
                    g += (g * 16);
                    b += (b * 16);
                }
            }
        }
        else if ((values = str.match(Ext.draw.Color.rgbToHexRe))) {
            r = +values[1];
            g = +values[2];
            b = +values[3];
        } else if ((values = str.match(Ext.draw.Color.rgbaToHexRe))) {
            r = +values[1];
            g = +values[2];
            b = +values[3];
            a = +values[4];
        } else {
            if (Ext.draw.Color.ColorList.hasOwnProperty(str.toLowerCase())) {
                return this.setFromString(Ext.draw.Color.ColorList[str.toLowerCase()]);
            }
        }
        if (typeof r === 'undefined') {
            return this;
        }
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        return this;
    }
}, function () {
    var flyColor = new this();

    this.addStatics({
        /**
         * Returns a flyweight instance of Ext.draw.Color.
         *
         * Can be called with either a CSS color string or with separate
         * arguments for red, green, blue, alpha.
         *
         * @param {Number/String} red Red component (0..255) or CSS color string.
         * @param {Number} [green] Green component (0..255)
         * @param {Number} [blue] Blue component (0..255)
         * @param {Number} [alpha=1] Alpha component (0..1)
         * @return {Ext.draw.Color}
         * @static
         */
        fly: function (r, g, b, a) {
            switch (arguments.length) {
                case 1:
                    flyColor.setFromString(r);
                    break;
                case 3:
                case 4:
                    flyColor.setRGB(r, g, b, a);
                    break;
                default:
                    return null;
            }
            return flyColor;
        },

        ColorList: {
            "aliceblue": "#f0f8ff", "antiquewhite": "#faebd7", "aqua": "#00ffff", "aquamarine": "#7fffd4", "azure": "#f0ffff",
            "beige": "#f5f5dc", "bisque": "#ffe4c4", "black": "#000000", "blanchedalmond": "#ffebcd", "blue": "#0000ff", "blueviolet": "#8a2be2", "brown": "#a52a2a", "burlywood": "#deb887",
            "cadetblue": "#5f9ea0", "chartreuse": "#7fff00", "chocolate": "#d2691e", "coral": "#ff7f50", "cornflowerblue": "#6495ed", "cornsilk": "#fff8dc", "crimson": "#dc143c", "cyan": "#00ffff",
            "darkblue": "#00008b", "darkcyan": "#008b8b", "darkgoldenrod": "#b8860b", "darkgray": "#a9a9a9", "darkgreen": "#006400", "darkkhaki": "#bdb76b", "darkmagenta": "#8b008b", "darkolivegreen": "#556b2f",
            "darkorange": "#ff8c00", "darkorchid": "#9932cc", "darkred": "#8b0000", "darksalmon": "#e9967a", "darkseagreen": "#8fbc8f", "darkslateblue": "#483d8b", "darkslategray": "#2f4f4f", "darkturquoise": "#00ced1",
            "darkviolet": "#9400d3", "deeppink": "#ff1493", "deepskyblue": "#00bfff", "dimgray": "#696969", "dodgerblue": "#1e90ff",
            "firebrick": "#b22222", "floralwhite": "#fffaf0", "forestgreen": "#228b22", "fuchsia": "#ff00ff",
            "gainsboro": "#dcdcdc", "ghostwhite": "#f8f8ff", "gold": "#ffd700", "goldenrod": "#daa520", "gray": "#808080", "green": "#008000", "greenyellow": "#adff2f",
            "honeydew": "#f0fff0", "hotpink": "#ff69b4",
            "indianred ": "#cd5c5c", "indigo ": "#4b0082", "ivory": "#fffff0", "khaki": "#f0e68c",
            "lavender": "#e6e6fa", "lavenderblush": "#fff0f5", "lawngreen": "#7cfc00", "lemonchiffon": "#fffacd", "lightblue": "#add8e6", "lightcoral": "#f08080", "lightcyan": "#e0ffff", "lightgoldenrodyellow": "#fafad2",
            "lightgray": "#d3d3d3", "lightgrey": "#d3d3d3", "lightgreen": "#90ee90", "lightpink": "#ffb6c1", "lightsalmon": "#ffa07a", "lightseagreen": "#20b2aa", "lightskyblue": "#87cefa", "lightslategray": "#778899", "lightsteelblue": "#b0c4de",
            "lightyellow": "#ffffe0", "lime": "#00ff00", "limegreen": "#32cd32", "linen": "#faf0e6",
            "magenta": "#ff00ff", "maroon": "#800000", "mediumaquamarine": "#66cdaa", "mediumblue": "#0000cd", "mediumorchid": "#ba55d3", "mediumpurple": "#9370d8", "mediumseagreen": "#3cb371", "mediumslateblue": "#7b68ee",
            "mediumspringgreen": "#00fa9a", "mediumturquoise": "#48d1cc", "mediumvioletred": "#c71585", "midnightblue": "#191970", "mintcream": "#f5fffa", "mistyrose": "#ffe4e1", "moccasin": "#ffe4b5",
            "navajowhite": "#ffdead", "navy": "#000080",
            "oldlace": "#fdf5e6", "olive": "#808000", "olivedrab": "#6b8e23", "orange": "#ffa500", "orangered": "#ff4500", "orchid": "#da70d6",
            "palegoldenrod": "#eee8aa", "palegreen": "#98fb98", "paleturquoise": "#afeeee", "palevioletred": "#d87093", "papayawhip": "#ffefd5", "peachpuff": "#ffdab9", "peru": "#cd853f", "pink": "#ffc0cb", "plum": "#dda0dd", "powderblue": "#b0e0e6", "purple": "#800080",
            "red": "#ff0000", "rosybrown": "#bc8f8f", "royalblue": "#4169e1",
            "saddlebrown": "#8b4513", "salmon": "#fa8072", "sandybrown": "#f4a460", "seagreen": "#2e8b57", "seashell": "#fff5ee", "sienna": "#a0522d", "silver": "#c0c0c0", "skyblue": "#87ceeb", "slateblue": "#6a5acd", "slategray": "#708090", "snow": "#fffafa", "springgreen": "#00ff7f", "steelblue": "#4682b4",
            "tan": "#d2b48c", "teal": "#008080", "thistle": "#d8bfd8", "tomato": "#ff6347", "turquoise": "#40e0d0",
            "violet": "#ee82ee",
            "wheat": "#f5deb3", "white": "#ffffff", "whitesmoke": "#f5f5f5",
            "yellow": "#ffff00", "yellowgreen": "#9acd32"
        },

        /**
         * Create a new color based on the specified HSL values.
         *
         * @param {Number} h Hue component (0..359)
         * @param {Number} s Saturation component (0..1)
         * @param {Number} l Lightness component (0..1)
         * @return {Ext.draw.Color}
         * @static
         */
        fromHSL: function (h, s, l) {
            return (new this(0, 0, 0, 0)).setHSL(h, s, l);
        },

        /**
         * Parse the string and create a new color.
         *
         * Supported formats: '#rrggbb', '#rgb', and 'rgb(r,g,b)'.
         *
         * If the string is not recognized, an undefined will be returned instead.
         *
         * @param {String} string Color in string.
         * @returns {Ext.draw.Color}
         * @static
         */
        fromString: function (string) {
            return (new this(0, 0, 0, 0)).setFromString(string);
        },

        /**
         * Convenience method for creating a color.
         *
         * Can be called with several different combinations of arguments:
         *
         *     // Ext.draw.Color is returned unchanged.
         *     Ext.draw.Color.create(new Ext.draw.color(255, 0, 0, 0));
         *
         *     // CSS color string.
         *     Ext.draw.Color.create("red");
         *
         *     // Array of red, green, blue, alpha
         *     Ext.draw.Color.create([255, 0, 0, 0]);
         *
         *     // Separate arguments of red, green, blue, alpha
         *     Ext.draw.Color.create(255, 0, 0, 0);
         *
         *     // Returns black when no arguments given.
         *     Ext.draw.Color.create();
         *
         * @param {Ext.draw.Color/String/Number[]/Number} [red] Red component (0..255),
         * CSS color string or array of all components.
         * @param {Number} [green] Green component (0..255)
         * @param {Number} [blue] Blue component (0..255)
         * @param {Number} [alpha=1] Alpha component (0..1)
         * @return {Ext.draw.Color}
         * @static
         */
        create: function (arg) {
            if (arg instanceof this) {
                return arg;
            } else if (Ext.isArray(arg)) {
                return new Ext.draw.Color(arg[0], arg[1], arg[2], arg[3]);
            } else if (Ext.isString(arg)) {
                return Ext.draw.Color.fromString(arg);
            } else if (arguments.length > 2) {
                return new Ext.draw.Color(arguments[0], arguments[1], arguments[2], arguments[3]);
            } else {
                return new Ext.draw.Color(0, 0, 0, 0);
            }
        }
    });
});

/**
 * @private
 * @class Ext.draw.sprite.AnimationParser
 *
 * Computes an intermidiate value between two values of the same type for use in animations.
 * Can have pre- and post- processor functions if the values need to be processed
 * before an intermidiate value can be computed (parseInitial), or the computed value
 * needs to be processed before it can be used as a valid attribute value (serve).
 */
Ext.define('Ext.draw.sprite.AnimationParser', function () {

    function compute(from, to, delta) {
        return from + (to - from) * delta;
    }

    return {
        singleton: true,
        attributeRe: /^url\(#([a-zA-Z\-]+)\)$/,
        requires: ['Ext.draw.Color'],

        color: {
            parseInitial: function (color1, color2) {
                if (Ext.isString(color1)) {
                    color1 = Ext.draw.Color.create(color1);
                }
                if (Ext.isString(color2)) {
                    color2 = Ext.draw.Color.create(color2);

                }
                if ((color1 instanceof Ext.draw.Color) && (color2 instanceof Ext.draw.Color)) {
                    return [
                        [color1.r, color1.g, color1.b, color1.a],
                        [color2.r, color2.g, color2.b, color2.a]
                    ];
                } else {
                    return [color1 || color2, color2 || color1];
                }
            },
            compute: function (from, to, delta) {
                if (!Ext.isArray(from) || !Ext.isArray(to)) {
                    return to || from;
                } else {
                    return [compute(from[0], to[0], delta), compute(from[1], to[1], delta), compute(from[2], to[2], delta), compute(from[3], to[3], delta)];

                }
            },
            serve: function (array) {
                var color = Ext.draw.Color.fly(array[0], array[1], array[2], array[3]);
                return color.toString();
            }
        },

        number: {
            parse: function (n) {
                return n === null ? null : +n;
            },

            compute: function (from, to, delta) {
                if (!Ext.isNumber(from) || !Ext.isNumber(to)) {
                    return to || from;
                } else {
                    return compute(from, to, delta);
                }
            }
        },

        angle: {
            parseInitial: function (from, to) {
                if (to - from > Math.PI) {
                    to -= Math.PI * 2;
                } else if (to - from < -Math.PI) {
                    to += Math.PI * 2;
                }
                return [from, to];
            },

            compute: function (from, to, delta) {
                if (!Ext.isNumber(from) || !Ext.isNumber(to)) {
                    return to || from;
                } else {
                    return compute(from, to, delta);
                }
            }
        },

        path: {
            parseInitial: function (from, to) {
                var fromStripes = from.toStripes(),
                    toStripes = to.toStripes(),
                    i, j,
                    fromLength = fromStripes.length, toLength = toStripes.length,
                    fromStripe, toStripe,
                    length,
                    lastStripe = toStripes[toLength - 1],
                    endPoint = [lastStripe[lastStripe.length - 2], lastStripe[lastStripe.length - 1]];
                for (i = fromLength; i < toLength; i++) {
                    fromStripes.push(fromStripes[fromLength - 1].slice(0));
                }
                for (i = toLength; i < fromLength; i++) {
                    toStripes.push(endPoint.slice(0));
                }
                length = fromStripes.length;

                toStripes.path = to;
                toStripes.temp = new Ext.draw.Path();

                for (i = 0; i < length; i++) {
                    fromStripe = fromStripes[i];
                    toStripe = toStripes[i];
                    fromLength = fromStripe.length;
                    toLength = toStripe.length;
                    toStripes.temp.commands.push('M');
                    for (j = toLength; j < fromLength; j += 6) {
                        toStripe.push(endPoint[0], endPoint[1], endPoint[0], endPoint[1], endPoint[0], endPoint[1]);
                    }

                    lastStripe = toStripes[toStripes.length - 1];
                    endPoint = [lastStripe[lastStripe.length - 2], lastStripe[lastStripe.length - 1]];
                    for (j = fromLength; j < toLength; j += 6) {
                        fromStripe.push(endPoint[0], endPoint[1], endPoint[0], endPoint[1], endPoint[0], endPoint[1]);
                    }
                    for (i = 0; i < toStripe.length; i++) {
                        toStripe[i] -= fromStripe[i];
                    }
                    for (i = 2; i < toStripe.length; i += 6) {
                        toStripes.temp.commands.push('C');
                    }
                }

                return [fromStripes, toStripes];
            },

            compute: function (fromStripes, toStripes, delta) {
                if (delta >= 1) {
                    return toStripes.path;
                }
                var i = 0, ln = fromStripes.length,
                    j = 0, ln2, from, to,
                    temp = toStripes.temp.params, pos = 0;
                for (; i < ln; i++) {
                    from = fromStripes[i];
                    to = toStripes[i];
                    ln2 = from.length;
                    for (j = 0; j < ln2; j++) {
                        temp[pos++] = to[j] * delta + from[j];
                    }
                }
                return toStripes.temp;
            }
        },

        data: {
            compute: function (from, to, delta, target) {
                var lf = from.length - 1,
                    lt = to.length - 1,
                    len = Math.max(lf, lt),
                    f, t, i;
                if (!target || target === from) {
                    target = [];
                }
                target.length = len + 1;
                for (i = 0; i <= len; i++) {
                    f = from[Math.min(i, lf)];
                    t = to[Math.min(i, lt)];
                    if (isNaN(f)) {
                        target[i] = t;
                    } else {
                        target[i] = (t - f) * delta + f;
                    }
                }
                return target;
            }
        },

        text: {
            compute: function (from, to, delta) {
                return from.substr(0, Math.round(from.length * (1 - delta))) + to.substr(Math.round(to.length * (1 - delta)));
            }
        },

        limited: 'number',
        limited01: 'number'
    };
});

(function () {
    if (!Ext.global.Float32Array) {
        // Typed Array polyfill
        var Float32Array = function (array) {
            if (typeof array === 'number') {
                this.length = array;
            } else if ('length' in array) {
                this.length = array.length;
                for (var i = 0, len = array.length; i < len; i++) {
                    this[i] = +array[i];
                }
            }
        };

        Float32Array.prototype = [];
        Ext.global.Float32Array = Float32Array;
    }
})();

/**
 * Utility class providing mathematics functionalities through all the draw package.
 */
Ext.define('Ext.draw.Draw', {
    singleton: true,

    radian: Math.PI / 180,
    pi2: Math.PI * 2,

    /**
     * @deprecated Please use the {@link Ext.identityFn} instead.
     * Function that returns its first element.
     * @param {Mixed} a
     * @return {Mixed}
     */
    reflectFn: function (a) {
        return a;
    },

    /**
     * Converting degrees to radians.
     * @param {Number} degrees
     * @return {Number}
     */
    rad: function (degrees) {
        return (degrees % 360) * this.radian;
    },

    /**
     * Converting radians to degrees.
     * @param {Number} radian
     * @return {Number}
     */
    degrees: function (radian) {
        return (radian / this.radian) % 360;
    },

    /**
     *
     * @param {Object} bbox1
     * @param {Object} bbox2
     * @param {Number} [padding]
     * @return {Boolean}
     */
    isBBoxIntersect: function (bbox1, bbox2, padding) {
        padding = padding || 0;
        return (Math.max(bbox1.x, bbox2.x) - padding > Math.min(bbox1.x + bbox1.width, bbox2.x + bbox2.width)) ||
            (Math.max(bbox1.y, bbox2.y) - padding > Math.min(bbox1.y + bbox1.height, bbox2.y + bbox2.height));
    },

    /**
     * Natural cubic spline interpolation.
     * This algorithm runs in linear time.
     *
     * @param {Array} points Array of numbers.
     */
    spline: function (points) {
        var i, j, ln = points.length,
            nd, d, y, ny,
            r = 0,
            zs = new Float32Array(points.length),
            result = new Float32Array(points.length * 3 - 2);

        zs[0] = 0;
        zs[ln - 1] = 0;

        for (i = 1; i < ln - 1; i++) {
            zs[i] = (points[i + 1] + points[i - 1] - 2 * points[i]) - zs[i - 1];
            r = 1 / (4 - r);
            zs[i] *= r;
        }

        for (i = ln - 2; i > 0; i--) {
            r = 3.732050807568877 + 48.248711305964385 / (-13.928203230275537 + Math.pow(0.07179676972449123, i));
            zs[i] -= zs[i + 1] * r;
        }

        ny = points[0];
        nd = ny - zs[0];
        for (i = 0, j = 0; i < ln - 1; j += 3) {
            y = ny;
            d = nd;
            i++;
            ny = points[i];
            nd = ny - zs[i];
            result[j] = y;
            result[j + 1] = (nd + 2 * d) / 3;
            result[j + 2] = (nd * 2 + d) / 3;
        }
        result[j] = ny;
        return result;
    },

    /**
     * @private
     *
     * Calculates bezier curve control anchor points for a particular point in a path, with a
     * smoothing curve applied. The smoothness of the curve is controlled by the 'value' parameter.
     * Note that this algorithm assumes that the line being smoothed is normalized going from left
     * to right; it makes special adjustments assuming this orientation.
     *
     * @param {Number} prevX X coordinate of the previous point in the path
     * @param {Number} prevY Y coordinate of the previous point in the path
     * @param {Number} curX X coordinate of the current point in the path
     * @param {Number} curY Y coordinate of the current point in the path
     * @param {Number} nextX X coordinate of the next point in the path
     * @param {Number} nextY Y coordinate of the next point in the path
     * @param {Number} value A value to control the smoothness of the curve; this is used to
     *                 divide the distance between points, so a value of 2 corresponds to
     *                 half the distance between points (a very smooth line) while higher values
     *                 result in less smooth curves. Defaults to 4.
     * @return {Object} Object containing x1, y1, x2, y2 bezier control anchor points; x1 and y1
     *                  are the control point for the curve toward the previous path point, and
     *                  x2 and y2 are the control point for the curve toward the next path point.
     */
    getAnchors: function (prevX, prevY, curX, curY, nextX, nextY, value) {
        value = value || 4;
        var PI = Math.PI,
            halfPI = PI / 2,
            abs = Math.abs,
            sin = Math.sin,
            cos = Math.cos,
            atan = Math.atan,
            control1Length, control2Length, control1Angle, control2Angle,
            control1X, control1Y, control2X, control2Y, alpha;

        // Find the length of each control anchor line, by dividing the horizontal distance
        // between points by the value parameter.
        control1Length = (curX - prevX) / value;
        control2Length = (nextX - curX) / value;

        // Determine the angle of each control anchor line. If the middle point is a vertical
        // turnaround then we force it to a flat horizontal angle to prevent the curve from
        // dipping above or below the middle point. Otherwise we use an angle that points
        // toward the previous/next target point.
        if ((curY >= prevY && curY >= nextY) || (curY <= prevY && curY <= nextY)) {
            control1Angle = control2Angle = halfPI;
        } else {
            control1Angle = atan((curX - prevX) / abs(curY - prevY));
            if (prevY < curY) {
                control1Angle = PI - control1Angle;
            }
            control2Angle = atan((nextX - curX) / abs(curY - nextY));
            if (nextY < curY) {
                control2Angle = PI - control2Angle;
            }
        }

        // Adjust the calculated angles so they point away from each other on the same line
        alpha = halfPI - ((control1Angle + control2Angle) % (PI * 2)) / 2;
        if (alpha > halfPI) {
            alpha -= PI;
        }
        control1Angle += alpha;
        control2Angle += alpha;

        // Find the control anchor points from the angles and length
        control1X = curX - control1Length * sin(control1Angle);
        control1Y = curY + control1Length * cos(control1Angle);
        control2X = curX + control2Length * sin(control2Angle);
        control2Y = curY + control2Length * cos(control2Angle);

        // One last adjustment, make sure that no control anchor point extends vertically past
        // its target prev/next point, as that results in curves dipping above or below and
        // bending back strangely. If we find this happening we keep the control angle but
        // reduce the length of the control line so it stays within bounds.
        if ((curY > prevY && control1Y < prevY) || (curY < prevY && control1Y > prevY)) {
            control1X += abs(prevY - control1Y) * (control1X - curX) / (control1Y - curY);
            control1Y = prevY;
        }
        if ((curY > nextY && control2Y < nextY) || (curY < nextY && control2Y > nextY)) {
            control2X -= abs(nextY - control2Y) * (control2X - curX) / (control2Y - curY);
            control2Y = nextY;
        }

        return {
            x1: control1X,
            y1: control1Y,
            x2: control2X,
            y2: control2Y
        };
    },

    /**
     * Given coordinates of the points, calculates coordinates of a Bezier curve that goes through them.
     * @param dataX x-coordinates of the points.
     * @param dataY y-coordinates of the points.
     * @param value A value to control the smoothness of the curve.
     * @return {Object} Object holding two arrays, for x and y coordinates of the curve.
     */
    smooth: function (dataX, dataY, value) {
        var ln = dataX.length,
            prevX, prevY,
            curX, curY,
            nextX, nextY,
            x, y,
            smoothX = [], smoothY = [],
            i, anchors;

        for (i = 0; i < ln - 1; i++) {
            prevX = dataX[i];
            prevY = dataY[i];
            if (i === 0) {
                x = prevX;
                y = prevY;
                smoothX.push(x);
                smoothY.push(y);
                if (ln === 1) {
                    break;
                }
            }
            curX = dataX[i+1];
            curY = dataY[i+1];
            nextX = dataX[i+2];
            nextY = dataY[i+2];
            if (isNaN(nextX) || isNaN(nextY)) {
                smoothX.push(x, curX, curX);
                smoothY.push(y, curY, curY);
                break;
            }
            anchors = this.getAnchors(prevX, prevY, curX, curY, nextX, nextY, value);
            smoothX.push(x, anchors.x1, curX);
            smoothY.push(y, anchors.y1, curY);
            x = anchors.x2;
            y = anchors.y2;
        }
        return {
            smoothX: smoothX,
            smoothY: smoothY
        }
    },

    /**
     * @method
     * @private
     * Work around for iOS.
     * Nested 3d-transforms seems to prevent the redraw inside it until some event is fired.
     */
    updateIOS: Ext.os.is.iOS ? function () {
        var el = Ext.getBody().createChild({
            style: 'position: absolute; top: 0px; bottom: 0px; left: 0px; right: 0px; background: rgba(0,0,0,0.001); z-index: 100000'
        });
        Ext.draw.Animator.schedule(function () {
            el.destroy();
        });
    } : Ext.emptyFn
});

/**
 * @class Ext.draw.gradient.Gradient
 *
 * Creates a gradient.
 */
Ext.define('Ext.draw.gradient.Gradient', {

    requires: ['Ext.draw.Color'],

    isGradient: true,

    config: {
        /**
         * @cfg {Array/Object} Defines the stops of the gradient.
         */
        stops: []
    },

    applyStops: function (newStops) {
        var stops = [],
            ln = newStops.length,
            i, stop, color;

        for (i = 0; i < ln; i++) {
            stop = newStops[i];
            color = Ext.draw.Color.fly(stop.color || Ext.draw.Color.NONE);
            stops.push({
                offset: Math.min(1, Math.max(0, 'offset' in stop ? stop.offset : stop.position || 0)),
                color: color.toString()
            });
        }
        stops.sort(function (a, b) {
            return a.offset - b.offset;
        });
        return stops;
    },

    onClassExtended: function (subClass, member) {
        if (!member.alias && member.type) {
            member.alias = 'gradient.' + member.type;
        }
    },

    constructor: function (config) {
        this.initConfig(config);
    },

    /**
     * @protected
     * Generates the gradient for the given context.
     * @param {Ext.draw.engine.SvgContext} ctx The context.
     * @param {Object} bbox
     * @return {Object}
     */
    generateGradient: Ext.emptyFn

});

/**
 * @class Ext.draw.gradient.GradientDefinition
 *
 * A global map of all gradient configs.
 */
Ext.define('Ext.draw.gradient.GradientDefinition', {
    singleton: true,

    urlStringRe: /^url\(#([\w\-]+)\)$/,
    gradients: {},

    add: function (gradients) {
        var store = this.gradients,
            i, n, gradient;
        for (i = 0, n = gradients.length; i < n; i++) {
            gradient = gradients[i];
            if (Ext.isString(gradient.id)) {
                store[gradient.id] = gradient;
            }
        }
    },

    get: function (str) {
        var store = this.gradients,
            match = str.match(this.urlStringRe),
            gradient;
        if (match && match[1] && (gradient = store[match[1]])) {
            return gradient || str;
        }
        return str;
    }
});

/**
 * @private
 * @class Ext.draw.sprite.AttributeParser
 *
 * Parsers used for sprite attributes if they are {@link Ext.draw.sprite.AttributeDefinition#normalize normalized}
 * (default) when being {@link Ext.draw.sprite.Sprite#setAttributes set}.
 *
 * Methods of the singleton correpond either to the processor functions themselves or processor factories.
 */
Ext.define('Ext.draw.sprite.AttributeParser', {
    singleton: true,
    attributeRe: /^url\(#([a-zA-Z\-]+)\)$/,
    requires: [
        'Ext.draw.Color',
        'Ext.draw.gradient.GradientDefinition'
    ],

    "default": function (n) {
        return n;
    },
    
    string: function (n) {
        return String(n);
    },
    
    number: function (n) {
        if (!isNaN(n)) {
            return n;
        }
    },
    
    angle: function (n) {
        if (!isNaN(n)) {
            n %= Math.PI * 2;
            if (n < -Math.PI) {
                n += Math.PI * 2;
            }
            if (n > Math.PI) {
                n -= Math.PI * 2;
            }
            return n;
        }
    },
    
    data: function (n) {
        if (Ext.isArray(n)) {
            return n.slice();
        } else if (n instanceof Float32Array) {
            return new Float32Array(n);
        }
    },
    
    bool: function (n) {
        return !!n;
    },
    
    color: function (n) {
        if (n instanceof Ext.draw.Color) {
            return n.toString();
        } else if (n instanceof Ext.draw.gradient.Gradient) {
            return n;
        } else if (!n) {
            return Ext.draw.Color.NONE;
        } else if (Ext.isString(n)) {
            if (n.substr(0, 3) === 'url') {
                n = Ext.draw.gradient.GradientDefinition.get(n);
                if (Ext.isString(n)) {
                    return n;
                }
            } else {
                return Ext.draw.Color.fly(n).toString();
            }
        }
        if (n.type === 'linear') {
            return Ext.create('Ext.draw.gradient.Linear', n);
        } else if (n.type === 'radial') {
            return Ext.create('Ext.draw.gradient.Radial', n);
        } else if (n.type === 'pattern') {
            return Ext.create('Ext.draw.gradient.Pattern', n);
        } else {
            return Ext.draw.Color.NONE;
        }
    },

    limited: function (low, hi) {
        return function (n) {
            return isNaN(n) ? undefined : Math.min(Math.max(+n, low), hi);
        };
    },
    
    limited01: function (n) {
        return isNaN(n) ? undefined : Math.min(Math.max(+n, 0), 1);
    },

    /**
     * Generates a function that checks if a value matches
     * one of the given attributes.
     * @returns {Function}
     */
    enums: function () {
        var enums = {},
            args = Array.prototype.slice.call(arguments, 0),
            i, ln;

        for (i = 0, ln = args.length; i < ln; i++) {
            enums[args[i]] = true;
        }
        return function (n) {
            return n in enums ? n : undefined;
        };
    }
});

/**
 * @private
 * Flyweight object to process the attributes of a sprite.
 * A single instance of the AttributeDefinition is created per sprite class.
 * See `onClassCreated` and `onClassExtended` callbacks
 * of the {@link Ext.draw.sprite.Sprite} for more info.
 */
Ext.define('Ext.draw.sprite.AttributeDefinition', {
    requires: [
        'Ext.draw.sprite.AttributeParser',
        'Ext.draw.sprite.AnimationParser'
    ],

    config: {
        /**
         * @cfg {Object} defaults Defines the default values of attributes.
         */
        defaults: {

        },

        /**
         * @cfg {Object} aliases Defines the alternative names for attributes.
         */
        aliases: {

        },

        /**
         * @cfg {Object} animationProcessors Defines the process used to animate between attributes.
         * One doesn't have to define animation processors for sprite attributes that use
         * predefined {@link #processors} from the {@link Ext.draw.AttributeParser} singleton.
         * For such attributes matching animation processors from the {@link Ext.draw.AnimationParser}
         * singleton will be used automatically.
         * However, if you have a custom processor for an attribute that should support
         * animation, you must provide a corresponding animation processor for it here.
         * For more information on animation processors please see {@link Ext.draw.AnimationParser}
         * documentation.
         */
        animationProcessors: {

        },

        /**
         * @cfg {Object} processors Defines the preprocessing used on the attributes.
         * One can define a custom processor function here or use the name of a predefined
         * processor from the {@link Ext.draw.AttributeParser} singleton.
         */
        processors: {

        },

        /**
         * @cfg {Object} dirty Defines what updaters have to be called when an attribute is changed.
         * For example, the config below indicates that the 'size' updater
         * of a {@link Ext.draw.sprite.Square square} sprite has to be called
         * when the 'size' attribute changes.
         *
         *     dirtyTriggers: {
         *         size: 'size'   // Use comma-separated values here if multiple updaters have to be called.
         *     }                  // Note that the order is _not_ guaranteed.
         *
         * If any of the updaters to be called (triggered by the {@link Ext.draw.sprite.Sprite#setAttributes call)
         * set attributes themselves and those attributes have dirty triggers defined for them,
         * then their updaters will be called after all current updaters finish execution.
         *
         * The updater functions themselves are defined in the {@link #updaters} config,
         * aside from the 'canvas' updater, which doesn't have to be defined and acts as a flag,
         * indicating that this attribute should be applied to a Canvas context (or whatever emulates it).
         */
        dirtyTriggers: {

        },

        /**
         * @cfg {Object} updaters Defines the postprocessing used by the attribute.
         * Inside the updater function 'this' refers to the sprite that the attributes belong to.
         * In case of an instancing sprite 'this' will refer to the instancing template.
         * The two parameters passed to the updater function are the attributes themselves
         * and the dirty flags (changed attributes) that triggered this updater call.
         *
         * The example below shows how the 'size' updater changes other attributes
         * of a {@link Ext.draw.sprite.Square square} sprite sprite when its 'size' attribute changes.
         *
         *     updaters: {
         *         size: function (attr) {
         *             var size = attr.size;
         *             this.setAttributes({   // Changes to these attributes will trigger the 'path' updater.
         *                 x: attr.x - size,
         *                 y: attr.y - size,
         *                 height: 2 * size,
         *                 width: 2 * size
         *             });
         *         }
         *     }
         */
        updaters: {

        }
    },

    inheritableStatics: {
        /**
         * @private
         * Processor declaration in the form of 'processorFactory(argument1,argument2,...)'.
         * E.g.: {@link Ext.draw.sprite.AttributeParser#enums enums},
         * {@link Ext.draw.sprite.AttributeParser#limited limited}.
         */
        processorFactoryRe: /^(\w+)\(([\w\-,]*)\)$/
    },

    constructor: function (config) {
        var me = this;
        me.initConfig(config);
    },

    applyDefaults: function (defaults, oldDefaults) {
        oldDefaults = Ext.apply(oldDefaults || {}, this.normalize(defaults));
        return oldDefaults;
    },

    applyAliases: function (aliases, oldAliases) {
        return Ext.apply(oldAliases || {}, aliases);
    },

    applyProcessors: function (processors, oldProcessors) {
        this.getAnimationProcessors(); // Apply custom animation processors first.
        var result = oldProcessors || {},
            defaultProcessor = Ext.draw.sprite.AttributeParser,
            processorFactoryRe = this.self.processorFactoryRe,
            animationProcessors = {},
            anyAnimationProcessors,
            name, match, fn;

        for (name in processors) {
            fn = processors[name];
            if (!Ext.isFunction(fn)) {
                if (Ext.isString(fn)) {
                    match = fn.match(processorFactoryRe);
                    if (match) {
                        fn = defaultProcessor[match[1]].apply(defaultProcessor, match[2].split(','));
                    } else {
                        // Names of animation parsers match the names of attribute parsers.
                        animationProcessors[name] = fn;
                        anyAnimationProcessors = true;
                        fn = defaultProcessor[fn];
                    }
                } else {
                    continue;
                }
            }
            result[name] = fn;
        }

        if (anyAnimationProcessors) {
            this.setAnimationProcessors(animationProcessors);
        }

        return result;
    },

    applyAnimationProcessors: function (animationProcessors, oldAnimationProcessors) {
        var parser = Ext.draw.sprite.AnimationParser,
            name, item;

        if (!oldAnimationProcessors) {
            oldAnimationProcessors = {};
        }

        for (name in animationProcessors) {
            item = animationProcessors[name];
            if (item === 'none') {
                oldAnimationProcessors[name] = null;
            } else if (Ext.isString(item) && !(name in oldAnimationProcessors)) {
                if (item in parser) {
                    // The while loop is used to resolve aliases, e.g. `num: 'number'`,
                    // where `number` maps to a parser object or is an alias too.
                    while (Ext.isString(parser[item])) {
                        item = parser[item];
                    }
                    oldAnimationProcessors[name] = parser[item];
                }
            } else if (Ext.isObject(item)) {
                oldAnimationProcessors[name] = item;
            }
        }
        return oldAnimationProcessors;
    },

    applyDirtyTriggers: function (dirtyTriggers, oldDirtyTrigger) {
        if (!oldDirtyTrigger) {
            oldDirtyTrigger = {};
        }
        for (var name in dirtyTriggers) {
            oldDirtyTrigger[name] = dirtyTriggers[name].split(',');
        }
        return oldDirtyTrigger;
    },

    applyUpdaters: function (updaters, oldUpdaters) {
        return Ext.apply(oldUpdaters || {}, updaters);
    },

    batchedNormalize: function (batchedChanges, keepUnrecognized) {
        if (!batchedChanges) {
            return {};
        }
        var definition = this,
            processors = definition.getProcessors(),
            aliases = definition.getAliases(),
            translation = batchedChanges.translation || batchedChanges.translate,
            normalized = {},
            i, ln, name, val,
            rotation, scaling,
            matrix, subVal, split;

        if ('rotation' in batchedChanges) {
            rotation = batchedChanges.rotation;
        } else {
            rotation = ('rotate' in batchedChanges) ? batchedChanges.rotate : undefined;
        }

        if ('scaling' in batchedChanges) {
            scaling = batchedChanges.scaling;
        } else {
            scaling = ('scale' in batchedChanges) ? batchedChanges.scale : undefined;
        }

        if (typeof scaling !== 'undefined') {
            if (Ext.isNumber(scaling)) {
                normalized.scalingX = scaling;
                normalized.scalingY = scaling;
            } else {
                if ('x' in scaling) {
                    normalized.scalingX = scaling.x;
                }
                if ('y' in scaling) {
                    normalized.scalingY = scaling.y;
                }
                if ('centerX' in scaling) {
                    normalized.scalingCenterX = scaling.centerX;
                }
                if ('centerY' in scaling) {
                    normalized.scalingCenterY = scaling.centerY;
                }
            }
        }

        if (typeof rotation !== 'undefined') {
            if (Ext.isNumber(rotation)) {
                rotation = Ext.draw.Draw.rad(rotation);
                normalized.rotationRads = rotation;
            } else {
                if ('rads' in rotation) {
                    normalized.rotationRads = rotation.rads;
                } else if ('degrees' in rotation) {
                    if (Ext.isArray(rotation.degrees)) {
                        normalized.rotationRads = Ext.Array.map(rotation.degrees, function (deg) {
                            return Ext.draw.Draw.rad(deg);
                        });
                    } else {
                        normalized.rotationRads = Ext.draw.Draw.rad(rotation.degrees);
                    }
                }
                if ('centerX' in rotation) {
                    normalized.rotationCenterX = rotation.centerX;
                }
                if ('centerY' in rotation) {
                    normalized.rotationCenterY = rotation.centerY;
                }
            }
        }
        if (typeof translation !== 'undefined') {
            if ('x' in translation) {
                normalized.translationX = translation.x;
            }
            if ('y' in translation) {
                normalized.translationY = translation.y;
            }
        }

        if ('matrix' in batchedChanges) {
            matrix = Ext.draw.Matrix.create(batchedChanges.matrix);
            split = matrix.split();

            normalized.matrix = matrix;
            normalized.rotationRads = split.rotation;
            normalized.rotationCenterX = 0;
            normalized.rotationCenterY = 0;
            normalized.scalingX = split.scaleX;
            normalized.scalingY = split.scaleY;
            normalized.scalingCenterX = 0;
            normalized.scalingCenterY = 0;
            normalized.translationX = split.translateX;
            normalized.translationY = split.translateY;
        }

        for (name in batchedChanges) {
            val = batchedChanges[name];
            if (typeof val === 'undefined') {
                continue;
            } else if (Ext.isArray(val)) {
                if (name in aliases) {
                    name = aliases[name];
                }
                if (name in processors) {
                    normalized[name] = [];
                    for (i = 0, ln = val.length; i < ln; i++) {
                        subVal = processors[name].call(this, val[i]);
                        if (typeof subVal !== 'undefined') {
                            normalized[name][i] = subVal;
                        }
                    }
                } else if (keepUnrecognized){
                    normalized[name] = val;
                }
            } else {
                if (name in aliases) {
                    name = aliases[name];
                }
                if (name in processors) {
                    val = processors[name].call(this, val);
                    if (typeof val !== 'undefined') {
                        normalized[name] = val;
                    }
                } else if (keepUnrecognized){
                    normalized[name] = val;
                }
            }
        }
        return normalized;
    },

    /**
     * Normalizes the changes given via their processors before they are applied as attributes.
     *
     * @param {Object} changes The changes given.
     * @param {Boolean} keepUnrecognized If 'true', unknown attributes will be passed through as normalized values.
     * @return {Object} The normalized values.
     */
    normalize: function (changes, keepUnrecognized) {
        if (!changes) {
            return {};
        }
        var definition = this,
            processors = definition.getProcessors(),
            aliases = definition.getAliases(),
            translation = changes.translation || changes.translate,
            normalized = {},
            name, val, rotation, scaling, matrix, split;

        if ('rotation' in changes) {
            rotation = changes.rotation;
        } else {
            rotation = ('rotate' in changes) ? changes.rotate : undefined;
        }

        if ('scaling' in changes) {
            scaling = changes.scaling;
        } else {
            scaling = ('scale' in changes) ? changes.scale : undefined;
        }

        if (translation) {
            if ('x' in translation) {
                normalized.translationX = translation.x;
            }
            if ('y' in translation) {
                normalized.translationY = translation.y;
            }
        }

        if (typeof scaling !== 'undefined') {
            if (Ext.isNumber(scaling)) {
                normalized.scalingX = scaling;
                normalized.scalingY = scaling;
            } else {
                if ('x' in scaling) {
                    normalized.scalingX = scaling.x;
                }
                if ('y' in scaling) {
                    normalized.scalingY = scaling.y;
                }
                if ('centerX' in scaling) {
                    normalized.scalingCenterX = scaling.centerX;
                }
                if ('centerY' in scaling) {
                    normalized.scalingCenterY = scaling.centerY;
                }
            }
        }

        if (typeof rotation !== 'undefined') {
            if (Ext.isNumber(rotation)) {
                rotation = Ext.draw.Draw.rad(rotation);
                normalized.rotationRads = rotation;
            } else {
                if ('rads' in rotation) {
                    normalized.rotationRads = rotation.rads;
                } else if ('degrees' in rotation) {
                    normalized.rotationRads = Ext.draw.Draw.rad(rotation.degrees);
                }
                if ('centerX' in rotation) {
                    normalized.rotationCenterX = rotation.centerX;
                }
                if ('centerY' in rotation) {
                    normalized.rotationCenterY = rotation.centerY;
                }
            }
        }

        if ('matrix' in changes) {
            matrix = Ext.draw.Matrix.create(changes.matrix);
            split = matrix.split();

            normalized.matrix = matrix;
            normalized.rotationRads = split.rotation;
            normalized.rotationCenterX = 0;
            normalized.rotationCenterY = 0;
            normalized.scalingX = split.scaleX;
            normalized.scalingY = split.scaleY;
            normalized.scalingCenterX = 0;
            normalized.scalingCenterY = 0;
            normalized.translationX = split.translateX;
            normalized.translationY = split.translateY;
        }

        for (name in changes) {
            val = changes[name];
            if (typeof val === 'undefined') {
                continue;
            }
            if (name in aliases) {
                name = aliases[name];
            }
            if (name in processors) {
                val = processors[name].call(this, val);
                if (typeof val !== 'undefined') {
                    normalized[name] = val;
                }
            } else if (keepUnrecognized){
                normalized[name] = val;
            }
        }
        return normalized;
    },

    setBypassingNormalization: function (attr, modifierStack, changes) {
        return modifierStack.pushDown(attr, changes);
    },

    set: function (attr, modifierStack, changes) {
        changes = this.normalize(changes);
        return this.setBypassingNormalization(attr, modifierStack, changes);
    }
});

/**
 * Utility class to calculate [affine transformation](http://en.wikipedia.org/wiki/Affine_transformation) matrix.
 *
 * This class is compatible with SVGMatrix (http://www.w3.org/TR/SVG11/coords.html#InterfaceSVGMatrix) except:
 *
 *   1. Ext.draw.Matrix is not read only.
 *   2. Using Number as its components rather than floats.
 *
 * Using this class to reduce the severe numeric problem with HTML Canvas and SVG transformation:
 * http://stackoverflow.com/questions/8784405/large-numbers-in-html-canvas-translate-result-in-strange-behavior
 * There's also no way to get current transformation matrix in Canvas:
 * http://stackoverflow.com/questions/7395813/html5-canvas-get-transform-matrix
 *
 */
Ext.define('Ext.draw.Matrix', {

    isMatrix: true,

    statics: {
        /**
         * @static
         * Return the affine matrix that transform two points (x0, y0) and (x1, y1) to (x0p, y0p) and (x1p, y1p)
         * @param {Number} x0
         * @param {Number} y0
         * @param {Number} x1
         * @param {Number} y1
         * @param {Number} x0p
         * @param {Number} y0p
         * @param {Number} x1p
         * @param {Number} y1p
         */
        createAffineMatrixFromTwoPair: function (x0, y0, x1, y1, x0p, y0p, x1p, y1p) {
            var dx = x1 - x0,
                dy = y1 - y0,
                dxp = x1p - x0p,
                dyp = y1p - y0p,
                r = 1 / (dx * dx + dy * dy),
                a = dx * dxp + dy * dyp,
                b = dxp * dy - dx * dyp,
                c = -a * x0 - b * y0,
                f = b * x0 - a * y0;

            return new this(a * r, -b * r, b * r, a * r, c * r + x0p, f * r + y0p);
        },

        /**
         * @static
         * Return the affine matrix that transform two points (x0, y0) and (x1, y1) to (x0p, y0p) and (x1p, y1p)
         * @param {Number} x0
         * @param {Number} y0
         * @param {Number} x1
         * @param {Number} y1
         * @param {Number} x0p
         * @param {Number} y0p
         * @param {Number} x1p
         * @param {Number} y1p
         */
        createPanZoomFromTwoPair: function (x0, y0, x1, y1, x0p, y0p, x1p, y1p) {
            if (arguments.length === 2) {
                return this.createPanZoomFromTwoPair.apply(this, x0.concat(y0));
            }
            var dx = x1 - x0,
                dy = y1 - y0,
                cx = (x0 + x1) * 0.5,
                cy = (y0 + y1) * 0.5,
                dxp = x1p - x0p,
                dyp = y1p - y0p,
                cxp = (x0p + x1p) * 0.5,
                cyp = (y0p + y1p) * 0.5,
                r = dx * dx + dy * dy,
                rp = dxp * dxp + dyp * dyp,
                scale = Math.sqrt(rp / r);

            return new this(scale, 0, 0, scale, cxp - scale * cx, cyp - scale * cy);
        },

        /**
         * @static
         * Create a flyweight to wrap the given array.
         * The flyweight will directly refer the object and the elements can be changed by other methods.
         *
         * Do not hold the instance of flyweight matrix.
         *
         * @param {Array} elements
         * @return {Ext.draw.Matrix}
         */
        fly: (function () {
            var flyMatrix = null,
                simplefly = function (elements) {
                    flyMatrix.elements = elements;
                    return flyMatrix;
                };

            return function (elements) {
                if (!flyMatrix) {
                    flyMatrix = new Ext.draw.Matrix();
                }
                flyMatrix.elements = elements;
                Ext.draw.Matrix.fly = simplefly;
                return flyMatrix;
            };
        })(),

        /**
         * @static
         * Create a matrix from `mat`. If `mat` is already a matrix, returns it.
         * @param {Mixed} mat
         * @return {Ext.draw.Matrix}
         */
        create: function (mat) {
            if (mat instanceof this) {
                return mat;
            }
            return new this(mat);
        }
    },

    /**
     * Create an affine transform matrix.
     *
     * @param {Number} xx Coefficient from x to x
     * @param {Number} xy Coefficient from x to y
     * @param {Number} yx Coefficient from y to x
     * @param {Number} yy Coefficient from y to y
     * @param {Number} dx Offset of x
     * @param {Number} dy Offset of y
     */
    constructor: function (xx, xy, yx, yy, dx, dy) {
        if (xx && xx.length === 6) {
            this.elements = xx.slice();
        } else if (xx !== undefined) {
            this.elements = [xx, xy, yx, yy, dx, dy];
        } else {
            this.elements = [1, 0, 0, 1, 0, 0];
        }
    },

    /**
     * Prepend a matrix onto the current.
     *
     * __Note:__ The given transform will come after the current one.
     *
     * @param {Number} xx Coefficient from x to x.
     * @param {Number} xy Coefficient from x to y.
     * @param {Number} yx Coefficient from y to x.
     * @param {Number} yy Coefficient from y to y.
     * @param {Number} dx Offset of x.
     * @param {Number} dy Offset of y.
     * @return {Ext.draw.Matrix} this
     */
    prepend: function (xx, xy, yx, yy, dx, dy) {
        var elements = this.elements,
            xx0 = elements[0],
            xy0 = elements[1],
            yx0 = elements[2],
            yy0 = elements[3],
            dx0 = elements[4],
            dy0 = elements[5];

        elements[0] = xx * xx0 + yx * xy0;
        elements[1] = xy * xx0 + yy * xy0;
        elements[2] = xx * yx0 + yx * yy0;
        elements[3] = xy * yx0 + yy * yy0;
        elements[4] = xx * dx0 + yx * dy0 + dx;
        elements[5] = xy * dx0 + yy * dy0 + dy;
        return this;
    },

    /**
     * Prepend a matrix onto the current.
     *
     * __Note:__ The given transform will come after the current one.
     * @param {Ext.draw.Matrix} matrix
     * @return {Ext.draw.Matrix} this
     */
    prependMatrix: function (matrix) {
        return this.prepend.apply(this, matrix.elements);
    },

    /**
     * Postpend a matrix onto the current.
     *
     * __Note:__ The given transform will come before the current one.
     *
     * @param {Number} xx Coefficient from x to x.
     * @param {Number} xy Coefficient from x to y.
     * @param {Number} yx Coefficient from y to x.
     * @param {Number} yy Coefficient from y to y.
     * @param {Number} dx Offset of x.
     * @param {Number} dy Offset of y.
     * @return {Ext.draw.Matrix} this
     */
    append: function (xx, xy, yx, yy, dx, dy) {
        var elements = this.elements,
            xx0 = elements[0],
            xy0 = elements[1],
            yx0 = elements[2],
            yy0 = elements[3],
            dx0 = elements[4],
            dy0 = elements[5];

        elements[0] = xx * xx0 + xy * yx0;
        elements[1] = xx * xy0 + xy * yy0;
        elements[2] = yx * xx0 + yy * yx0;
        elements[3] = yx * xy0 + yy * yy0;
        elements[4] = dx * xx0 + dy * yx0 + dx0;
        elements[5] = dx * xy0 + dy * yy0 + dy0;
        return this;
    },

    /**
     * Postpend a matrix onto the current.
     *
     * __Note:__ The given transform will come before the current one.
     *
     * @param {Ext.draw.Matrix} matrix
     * @return {Ext.draw.Matrix} this
     */
    appendMatrix: function (matrix) {
        return this.append.apply(this, matrix.elements);
    },

    /**
     * Set the elements of a Matrix
     * @param {Number} xx
     * @param {Number} xy
     * @param {Number} yx
     * @param {Number} yy
     * @param {Number} dx
     * @param {Number} dy
     * @return {Ext.draw.Matrix} this
     */
    set: function (xx, xy, yx, yy, dx, dy) {
        var elements = this.elements;

        elements[0] = xx;
        elements[1] = xy;
        elements[2] = yx;
        elements[3] = yy;
        elements[4] = dx;
        elements[5] = dy;
        return this;
    },

    /**
     * Return a new matrix represents the opposite transformation of the current one.
     *
     * @param {Ext.draw.Matrix} [target] A target matrix. If present, it will receive
     * the result of inversion to avoid creating a new object.
     *
     * @return {Ext.draw.Matrix}
     */
    inverse: function (target) {
        var elements = this.elements,
            a = elements[0],
            b = elements[1],
            c = elements[2],
            d = elements[3],
            e = elements[4],
            f = elements[5],
            rDim = 1 / (a * d - b * c);

        a *= rDim;
        b *= rDim;
        c *= rDim;
        d *= rDim;
        if (target) {
            target.set(d, -b, -c, a, c * f - d * e, b * e - a * f);
            return target;
        } else {
            return new Ext.draw.Matrix(d, -b, -c, a, c * f - d * e, b * e - a * f);
        }
    },

    /**
     * Translate the matrix.
     *
     * @param {Number} x
     * @param {Number} y
     * @param {Boolean} [prepend] If `true`, this will transformation be prepended to the matrix.
     * @return {Ext.draw.Matrix} this
     */
    translate: function (x, y, prepend) {
        if (prepend) {
            return this.prepend(1, 0, 0, 1, x, y);
        } else {
            return this.append(1, 0, 0, 1, x, y);
        }
    },

    /**
     * Scale the matrix.
     *
     * @param {Number} sx
     * @param {Number} sy
     * @param {Number} scx
     * @param {Number} scy
     * @param {Boolean} [prepend] If `true`, this will transformation be prepended to the matrix.
     * @return {Ext.draw.Matrix} this
     */
    scale: function (sx, sy, scx, scy, prepend) {
        var me = this;

        // null or undefined
        if (sy == null) {
            sy = sx;
        }
        if (scx === undefined) {
            scx = 0;
        }
        if (scy === undefined) {
            scy = 0;
        }

        if (prepend) {
            return me.prepend(sx, 0, 0, sy, scx - scx * sx, scy - scy * sy);
        } else {
            return me.append(sx, 0, 0, sy, scx - scx * sx, scy - scy * sy);
        }
    },

    /**
     * Rotate the matrix.
     *
     * @param {Number} angle Radians to rotate
     * @param {Number|null} rcx Center of rotation.
     * @param {Number|null} rcy Center of rotation.
     * @param {Boolean} [prepend] If `true`, this will transformation be prepended to the matrix.
     * @return {Ext.draw.Matrix} this
     */
    rotate: function (angle, rcx, rcy, prepend) {
        var me = this,
            cos = Math.cos(angle),
            sin = Math.sin(angle);

        rcx = rcx || 0;
        rcy = rcy || 0;

        if (prepend) {
            return me.prepend(
                cos, sin,
                -sin, cos,
                rcx - cos * rcx + rcy * sin,
                rcy - cos * rcy - rcx * sin
            );
        } else {
            return me.append(
                cos, sin,
                -sin, cos,
                rcx - cos * rcx + rcy * sin,
                rcy - cos * rcy - rcx * sin
            );
        }
    },

    /**
     * Rotate the matrix by the angle of a vector.
     *
     * @param {Number} x
     * @param {Number} y
     * @param {Boolean} [prepend] If `true`, this will transformation be prepended to the matrix.
     * @return {Ext.draw.Matrix} this
     */
    rotateFromVector: function (x, y, prepend) {
        var me = this,
            d = Math.sqrt(x * x + y * y),
            cos = x / d,
            sin = y / d;
        if (prepend) {
            return me.prepend(cos, sin, -sin, cos, 0, 0);
        } else {
            return me.append(cos, sin, -sin, cos, 0, 0);
        }
    },

    /**
     * Clone this matrix.
     * @return {Ext.draw.Matrix}
     */
    clone: function () {
        return new Ext.draw.Matrix(this.elements);
    },

    /**
     * Horizontally flip the matrix
     * @return {Ext.draw.Matrix} this
     */
    flipX: function () {
        return this.append(-1, 0, 0, 1, 0, 0);
    },

    /**
     * Vertically flip the matrix
     * @return {Ext.draw.Matrix} this
     */
    flipY: function () {
        return this.append(1, 0, 0, -1, 0, 0);
    },

    /**
     * Skew the matrix
     * @param {Number} angle
     * @return {Ext.draw.Matrix} this
     */
    skewX: function (angle) {
        return this.append(1, Math.tan(angle), 0, -1, 0, 0);
    },

    /**
     * Skew the matrix
     * @param {Number} angle
     * @return {Ext.draw.Matrix} this
     */
    skewY: function (angle) {
        return this.append(1, 0, Math.tan(angle), -1, 0, 0);
    },

    /**
     * Reset the matrix to identical.
     * @return {Ext.draw.Matrix} this
     */
    reset: function () {
        return this.set(1, 0, 0, 1, 0, 0);
    },

    /**
     * @private
     * Split Matrix to `{{devicePixelRatio,c,0},{b,devicePixelRatio,0},{0,0,1}}.{{xx,0,dx},{0,yy,dy},{0,0,1}}`
     * @return {Object} Object with b,c,d=devicePixelRatio,xx,yy,dx,dy
     */
    precisionCompensate: function (devicePixelRatio, comp) {
        var elements = this.elements,
            x2x = elements[0],
            x2y = elements[1],
            y2x = elements[2],
            y2y = elements[3],
            newDx = elements[4],
            newDy = elements[5],
            r = x2y * y2x - x2x * y2y;

        comp.b = devicePixelRatio * x2y / x2x;
        comp.c = devicePixelRatio * y2x / y2y;
        comp.d = devicePixelRatio;
        comp.xx = x2x / devicePixelRatio;
        comp.yy = y2y / devicePixelRatio;
        comp.dx = (newDy * x2x * y2x - newDx * x2x * y2y) / r / devicePixelRatio;
        comp.dy = (newDx * x2y * y2y - newDy * x2x * y2y) / r / devicePixelRatio;
    },

    /**
     * @private
     * Split Matrix to `{{1,c,0},{b,d,0},{0,0,1}}.{{xx,0,dx},{0,xx,dy},{0,0,1}}`
     * @return {Object} Object with b,c,d,xx,yy=xx,dx,dy
     */
    precisionCompensateRect: function (devicePixelRatio, comp) {
        var elements = this.elements,
            x2x = elements[0],
            x2y = elements[1],
            y2x = elements[2],
            y2y = elements[3],
            newDx = elements[4],
            newDy = elements[5],
            yxOnXx = y2x / x2x;

        comp.b = devicePixelRatio * x2y / x2x;
        comp.c = devicePixelRatio * yxOnXx;
        comp.d = devicePixelRatio * y2y / x2x;
        comp.xx = x2x / devicePixelRatio;
        comp.yy = x2x / devicePixelRatio;
        comp.dx = (newDy * y2x - newDx * y2y) / (x2y * yxOnXx - y2y) / devicePixelRatio;
        comp.dy = -(newDy * x2x - newDx * x2y) / (x2y * yxOnXx - y2y) / devicePixelRatio;
    },

    /**
     * Transform point returning the x component of the result.
     * @param {Number} x
     * @param {Number} y
     * @return {Number} x component of the result.
     */
    x: function (x, y) {
        var elements = this.elements;
        return x * elements[0] + y * elements[2] + elements[4];
    },

    /**
     * Transform point returning the y component of the result.
     * @param {Number} x
     * @param {Number} y
     * @return {Number} y component of the result.
     */
    y: function (x, y) {
        var elements = this.elements;

        return x * elements[1] + y * elements[3] + elements[5];
    },

    /**
     * @private
     * @param {Number} i
     * @param {Number} j
     * @return {String}
     */
    get: function (i, j) {
        return +this.elements[i + j * 2].toFixed(4);
    },

    /**
     * Transform a point to a new array.
     * @param {Array} point
     * @return {Array}
     */
    transformPoint: function (point) {
        var elements = this.elements;

        return [
            point[0] * elements[0] + point[1] * elements[2] + elements[4],
            point[0] * elements[1] + point[1] * elements[3] + elements[5]
        ];
    },

    /**
     * @param {Object} bbox Given as `{x: Number, y: Number, width: Number, height: Number}`.
     * @param {Number} [radius]
     * @param {Object} [target] Optional target object to recieve the result.
     * Recommended to use it for better gc.
     *
     * @return {Object} Object with x, y, width and height.
     */
    transformBBox: function (bbox, radius, target) {
        var elements = this.elements,
            l = bbox.x,
            t = bbox.y,
            w0 = bbox.width * 0.5,
            h0 = bbox.height * 0.5,
            xx = elements[0],
            xy = elements[1],
            yx = elements[2],
            yy = elements[3],
            cx = l + w0,
            cy = t + h0,
            w, h, scales;

        if (radius) {
            w0 -= radius;
            h0 -= radius;
            scales = [
                Math.sqrt(elements[0] * elements[0] + elements[2] * elements[2]),
                Math.sqrt(elements[1] * elements[1] + elements[3] * elements[3])
            ];
            w = Math.abs(w0 * xx) + Math.abs(h0 * yx) + Math.abs(scales[0] * radius);
            h = Math.abs(w0 * xy) + Math.abs(h0 * yy) + Math.abs(scales[1] * radius);
        } else {
            w = Math.abs(w0 * xx) + Math.abs(h0 * yx);
            h = Math.abs(w0 * xy) + Math.abs(h0 * yy);
        }

        if (!target) {
            target = {};
        }

        target.x = cx * xx + cy * yx + elements[4] - w;
        target.y = cx * xy + cy * yy + elements[5] - h;
        target.width = w + w;
        target.height = h + h;

        return target;
    },

    /**
     * Transform a list for points.
     *
     * __Note:__ will change the original list but not points inside it.
     * @param {Array} list
     * @return {Array} list
     */
    transformList: function (list) {
        var elements = this.elements,
            xx = elements[0], yx = elements[2], dx = elements[4],
            xy = elements[1], yy = elements[3], dy = elements[5],
            ln = list.length,
            p, i;

        for (i = 0; i < ln; i++) {
            p = list[i];
            list[i] = [
                p[0] * xx + p[1] * yx + dx,
                p[0] * xy + p[1] * yy + dy
            ];
        }
        return list;
    },

    /**
     * Determines whether this matrix is an identity matrix (no transform).
     * @return {Boolean}
     */
    isIdentity: function () {
        var elements = this.elements;

        return elements[0] === 1 &&
            elements[1] === 0 &&
            elements[2] === 0 &&
            elements[3] === 1 &&
            elements[4] === 0 &&
            elements[5] === 0;
    },

    /**
     * Determines if this matrix has the same values as another matrix.
     * @param {Ext.draw.Matrix} matrix
     * @return {Boolean}
     */
    equals: function (matrix) {
        var elements = this.elements,
            elements2 = matrix.elements;

        return elements[0] === elements2[0] &&
            elements[1] === elements2[1] &&
            elements[2] === elements2[2] &&
            elements[3] === elements2[3] &&
            elements[4] === elements2[4] &&
            elements[5] === elements2[5];
    },

    /**
     * Create an array of elements by horizontal order (xx,yx,dx,yx,yy,dy).
     * @return {Array}
     */
    toArray: function () {
        var elements = this.elements;
        return [elements[0], elements[2], elements[4], elements[1], elements[3], elements[5]];
    },

    /**
     * Create an array of elements by vertical order (xx,xy,yx,yy,dx,dy).
     * @return {Array|String}
     */
    toVerticalArray: function () {
        return this.elements.slice();
    },

    /**
     * Get an array of elements.
     * The numbers are rounded to keep only 4 decimals.
     * @return {Array}
     */
    toString: function () {
        var me = this;
        return [me.get(0, 0), me.get(0, 1), me.get(1, 0), me.get(1, 1), me.get(2, 0), me.get(2, 1)].join(',');
    },

    /**
     * Apply the matrix to a drawing context.
     * @param {Object} ctx
     * @return {Ext.draw.Matrix} this
     */
    toContext: function (ctx) {
        ctx.transform.apply(ctx, this.elements);
        return this;
    },

    /**
     * Return a string that can be used as transform attribute in SVG.
     * @return {String}
     */
    toSvg: function () {
        var elements = this.elements;
        // The reason why we cannot use `.join` is the `1e5` form is not accepted in svg.
        return "matrix(" +
            elements[0].toFixed(9) + ',' +
            elements[1].toFixed(9) + ',' +
            elements[2].toFixed(9) + ',' +
            elements[3].toFixed(9) + ',' +
            elements[4].toFixed(9) + ',' +
            elements[5].toFixed(9) +
            ")";
    },

    /**
     * Get the x scale of the matrix.
     * @return {Number}
     */
    getScaleX: function () {
        var elements = this.elements;
        return Math.sqrt(elements[0] * elements[0] + elements[2] * elements[2]);
    },

    /**
     * Get the y scale of the matrix.
     * @return {Number}
     */
    getScaleY: function () {
        var elements = this.elements;
        return Math.sqrt(elements[1] * elements[1] + elements[3] * elements[3]);
    },

    /**
     * Get x-to-x component of the matrix
     * @return {Number}
     */
    getXX: function () {
        return this.elements[0];
    },

    /**
     * Get x-to-y component of the matrix.
     * @return {Number}
     */
    getXY: function () {
        return this.elements[1];
    },

    /**
     * Get y-to-x component of the matrix.
     * @return {Number}
     */
    getYX: function () {
        return this.elements[2];
    },

    /**
     * Get y-to-y component of the matrix.
     * @return {Number}
     */
    getYY: function () {
        return this.elements[3];
    },

    /**
     * Get offset x component of the matrix.
     * @return {Number}
     */
    getDX: function () {
        return this.elements[4];
    },

    /**
     * Get offset y component of the matrix.
     * @return {Number}
     */
    getDY: function () {
        return this.elements[5];
    },

    /**
     * Split a transformation matrix into Scale, Rotate, Translate components.
     * @return {Object}
     */
    split: function () {
        var el = this.elements,
            xx = el[0],
            xy = el[1],
            yx = el[2],
            yy = el[3],
            out = {
                translateX: el[4],
                translateY: el[5]
            };
        out.scaleX = Ext.Number.sign(xx) * Math.sqrt(xx * xx + yx * yx);
        out.scaleY = Ext.Number.sign(yy) * Math.sqrt(xy * xy + yy * yy);
        out.rotation = Math.atan2(xy, yy);

        return out;
    }
}, function () {
    function registerName(properties, name, i) {
        properties[name] = {
            get: function () {
                return this.elements[i];
            },
            set: function (val) {
                this.elements[i] = val;
            }
        };
    }

    // Compatibility with SVGMatrix.
    if (Object.defineProperties) {
        var properties = {};
        /**
         * @property {Number} a Get x-to-x component of the matrix. Avoid using it for performance consideration.
         * Use {@link #getXX} instead.
         */
        registerName(properties, 'a', 0);
        registerName(properties, 'b', 1);
        registerName(properties, 'c', 2);
        registerName(properties, 'd', 3);
        registerName(properties, 'e', 4);
        registerName(properties, 'f', 5);
        Object.defineProperties(this.prototype, properties);
    }

    /**
     * Performs matrix multiplication. This matrix is post-multiplied by another matrix.
     *
     * __Note:__ The given transform will come before the current one.
     *
     * @method
     * @param {Ext.draw.Matrix} matrix
     * @return {Ext.draw.Matrix} this
     */
    this.prototype.multiply = this.prototype.appendMatrix;
});

/**
 * @class Ext.draw.modifier.Modifier
 *
 * Each sprite has a stack of modifiers. The resulting attributes of sprite is
 * the content of the stack top. When setting attributes to a sprite,
 * changes will be pushed-down though the stack of modifiers and pop-back the
 * additive changes; When modifier is triggered to change the attribute of a
 * sprite, it will pop-up the changes to the top.
 */
Ext.define('Ext.draw.modifier.Modifier', {
    mixins: {
        observable: 'Ext.mixin.Observable'
    },
    config: {
        /**
         * @cfg {Ext.draw.modifier.Modifier} previous Previous modifier that receives
         * the push-down changes.
         */
        previous: null,

        /**
         * @cfg {Ext.draw.modifier.Modifier} next Next modifier that receives the
         * pop-up changes.
         */
        next: null,

        /**
         * @cfg {Ext.draw.sprite.Sprite} sprite The sprite to which the modifier belongs.
         */
        sprite: null
    },

    constructor: function (config) {
        this.mixins.observable.constructor.call(this, config);
    },

    updateNext: function (next) {
        if (next) {
            next.setPrevious(this);
        }
    },

    updatePrevious: function (prev) {
        if (prev) {
            prev.setNext(this);
        }
    },

    /**
     * Validate attribute set before use.
     *
     * @param {Object} attr The attribute to be validated. Note that it may be already initialized, so do
     * not override properties that have already been used.
     */
    prepareAttributes: function (attr) {
        if (this._previous) {
            this._previous.prepareAttributes(attr);
        }
    },

    /**
     * Invoked when changes need to be popped up to the top.
     * @param {Object} attributes The source attributes.
     * @param {Object} changes The changes to be popped up.
     */
    popUp: function (attributes, changes) {
        if (this._next) {
            this._next.popUp(attributes, changes);
        } else {
            Ext.apply(attributes, changes);
        }
    },

    /**
     * Invoked when changes need to be pushed down to the sprite.
     * @param {Object} attr The source attributes.
     * @param {Object} changes The changes to make. This object might be changed unexpectedly inside the method.
     * @return {Mixed}
     */
    pushDown: function (attr, changes) {
        if (this._previous) {
            return this._previous.pushDown(attr, changes);
        } else {
            for (var name in changes) {
                if (changes[name] === attr[name]) {
                    delete changes[name];
                }
            }
            return changes;
        }
    }
});

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

(function () {
    var pow = Math.pow,
        sin = Math.sin,
        cos = Math.cos,
        sqrt = Math.sqrt,
        pi = Math.PI,
        easings, addEasing, poly, createPoly, easing, i, l;

    //create polynomial easing equations
    poly = ['quad', 'cubic', 'quart', 'quint'];

    //create other easing equations
    easings = {
        pow: function (p, x) {
            return pow(p, x[0] || 6);
        },

        expo: function (p) {
            return pow(2, 8 * (p - 1));
        },

        circ: function (p) {
            return 1 - sqrt(1 - p * p);
        },

        sine: function (p) {
            return 1 - sin((1 - p) * pi / 2);
        },

        back: function (p, n) {
            n = n || 1.616;
            return p * p * ((n + 1) * p - n);
        },

        bounce: function (p) {
            var value;
            for (var a = 0, b = 1; 1; a += b, b /= 2) {
                if (p >= (7 - 4 * a) / 11) {
                    value = b * b - pow((11 - 6 * a - 11 * p) / 4, 2);
                    break;
                }
            }
            return value;
        },

        elastic: function (p, x) {
            return pow(2, 10 * --p) * cos(20 * p * pi * (x || 1) / 3);
        }
    };

    //Add easeIn, easeOut, easeInOut options to all easing equations.
    addEasing = function (easing, params) {
        params = params && params.length ? params : [ params ];
        return Ext.apply(easing, {

            easeIn: function (pos) {
                return easing(pos, params);
            },

            easeOut: function (pos) {
                return 1 - easing(1 - pos, params);
            },

            easeInOut: function (pos) {
                return (pos <= 0.5) ? easing(2 * pos, params) / 2
                    : (2 - easing(2 * (1 - pos), params)) / 2;
            }
        });
    };

    //Append the polynomial equations with easing support to the EasingPrototype.
    createPoly = function (times) {
        return function (p) {
            return pow(p, times);
        };
    };

    for (i = 0, l = poly.length; i < l; ++i) {
        easings[poly[i]] = createPoly(i + 2);
    }

    //Add linear interpolator
    easings.linear = function (x) {
        return x;
    };

    for (easing in easings) {
        if (easings.hasOwnProperty(easing)) {
            addEasing(easings[easing]);
        }
    }

    /**
     * @class
     * Contains transition equations such as `Quad`, `Cubic`, `Quart`, `Quint`,
     * `Expo`, `Circ`, `Pow`, `Sine`, `Back`, `Bounce`, `Elastic`, etc.
     *
     * Contains transition equations such as `Quad`, `Cubic`, `Quart`, `Quint`, `Expo`, `Circ`, `Pow`, `Sine`, `Back`, `Bounce`, `Elastic`, etc.
     * Each transition also contains methods for applying this function as ease in, ease out or ease in and out accelerations.
     *
     *     var fx = Ext.create('Ext.draw.fx.Sprite', {
     *         sprite: sprite,
     *         duration: 1000,
     *         easing: 'backOut'
     *     });
     */
    Ext.define('Ext.draw.TimingFunctions', {
        singleton: true,
        easingMap: {
            linear: easings.linear,
            easeIn: easings.quad.easeIn,
            easeOut: easings.quad.easeOut,
            easeInOut: easings.quad.easeInOut,
            backIn: easings.back,
            backOut: function (x, n) {
                return 1 - easings.back(1 - x, n);
            },
            backInOut: function (x, n) {
                if (x < 0.5) {
                    return easings.back(x * 2, n) * 0.5;
                } else {
                    return 1 - easings.back((1 - x) * 2, n) * 0.5;
                }
            },
            elasticIn: function (x, n) {
                return 1 - easings.elastic(1 - x, n);
            },
            elasticOut: easings.elastic,
            bounceIn: easings.bounce,
            bounceOut: function (x) {
                return 1 - easings.bounce(1 - x);
            }
        }
    }, function () {
        Ext.apply(this, easings);
    });

})();


/**
 * @class Ext.draw.Animator
 *
 * Singleton class that manages the animation pool.
 */
Ext.define('Ext.draw.Animator', {
    uses: ['Ext.draw.Draw'],
    singleton: true,

    frameCallbacks: {},
    frameCallbackId: 0,
    scheduled: 0,
    frameStartTimeOffset: Ext.now(),
    animations: [],
    running: false,

    /**
     *  Cross platform `animationTime` implementation.
     *  @return {Number}
     */
    animationTime: function () {
        return Ext.AnimationQueue.frameStartTime - this.frameStartTimeOffset;
    },

    /**
     * Adds an animated object to the animation pool.
     *
     * @param {Object} animation The animation descriptor to add to the pool.
     */
    add: function (animation) {
        var me = this;
        if (!me.contains(animation)) {
            me.animations.push(animation);
            me.ignite();
            if ('fireEvent' in animation) {
                animation.fireEvent('animationstart', animation);
            }
        }
    },

    /**
     * Removes an animation from the pool.
     * TODO: This is broken when called within `step` method.
     * @param {Object} animation The animation to remove from the pool.
     */
    remove: function (animation) {
        var me = this,
            animations = me.animations,
            i = 0,
            l = animations.length;

        for (; i < l; ++i) {
            if (animations[i] === animation) {
                animations.splice(i, 1);
                if ('fireEvent' in animation) {
                    animation.fireEvent('animationend', animation);
                }
                return;
            }
        }
    },

    /**
     * Returns `true` or `false` whether it contains the given animation or not.
     *
     * @param {Object} animation The animation to check for.
     * @return {Boolean}
     */
    contains: function (animation) {
        return Ext.Array.indexOf(this.animations, animation) > -1;
    },

    /**
     * Returns `true` or `false` whether the pool is empty or not.
     * @return {Boolean}
     */
    empty: function () {
        return this.animations.length === 0;
    },

    /**
     * Given a frame time it will filter out finished animations from the pool.
     *
     * @param {Number} frameTime The frame's start time, in milliseconds.
     */
    step: function (frameTime) {
        var me = this,
            animations = me.animations,
            animation,
            i = 0,
            ln = animations.length;

        for (; i < ln; i++) {
            animation = animations[i];
            animation.step(frameTime);
            if (!animation.animating) {
                animations.splice(i, 1);
                i--;
                ln--;
                if (animation.fireEvent) {
                    animation.fireEvent('animationend');
                }
            }
        }
    },

    /**
     * Register a one-time callback that will be called at the next frame.
     * @param {Function} callback
     * @param {Object} scope
     * @return {String}
     */
    schedule: function (callback, scope) {
        scope = scope || this;
        var id = 'frameCallback' + (this.frameCallbackId++);

        if (Ext.isString(callback)) {
            callback = scope[callback];
        }
        Ext.draw.Animator.frameCallbacks[id] = {fn: callback, scope: scope, once: true};
        this.scheduled++;
        Ext.draw.Animator.ignite();
        return id;
    },

    /**
     * Cancel a registered one-time callback
     * @param {String} id
     */
    cancel: function (id) {
        if (Ext.draw.Animator.frameCallbacks[id] && Ext.draw.Animator.frameCallbacks[id].once) {
            this.scheduled--;
            delete Ext.draw.Animator.frameCallbacks[id];
        }
    },

    /**
     * Register a recursive callback that will be called at every frame.
     *
     * @param {Function} callback
     * @param {Object} scope
     * @return {String}
     */
    addFrameCallback: function (callback, scope) {
        scope = scope || this;
        if (Ext.isString(callback)) {
            callback = scope[callback];
        }
        var id = 'frameCallback' + (this.frameCallbackId++);

        Ext.draw.Animator.frameCallbacks[id] = {fn: callback, scope: scope};
        return id;
    },

    /**
     * Unregister a recursive callback.
     * @param {String} id
     */
    removeFrameCallback: function (id) {
        delete Ext.draw.Animator.frameCallbacks[id];
    },

    /**
     * @private
     */
    fireFrameCallbacks: function () {
        var callbacks = this.frameCallbacks,
            id, fn, cb;

        for (id in callbacks) {
            cb = callbacks[id];
            fn = cb.fn;
            if (Ext.isString(fn)) {
                fn = cb.scope[fn];
            }

            fn.call(cb.scope);

            if (callbacks[id] && cb.once) {
                this.scheduled--;
                delete callbacks[id];
            }
        }
    },

    handleFrame: function() {
        this.step(this.animationTime());
        this.fireFrameCallbacks();
        if (!this.scheduled && this.empty()) {
            Ext.AnimationQueue.stop(this.handleFrame, this);
            this.running = false;
        }
    },

    ignite: function() {
        if (!this.running) {
            this.running = true;
            Ext.AnimationQueue.start(this.handleFrame, this);
            Ext.draw.Draw.updateIOS();
        }
    }
});

/**
 * The Animation modifier.
 *
 * Sencha Charts allow users to use transitional animation on sprites. Simply set the duration
 * and easing in the animation modifier, then all the changes to the sprites will be animated.
 *
 * Also, you can use different durations and easing functions on different attributes by using
 * {@link #customDurations} and {@link #customEasings}.
 *
 * By default, an animation modifier will be created during the initialization of a sprite.
 * You can get the animation modifier of a sprite via `sprite.fx`.
 *
 */
Ext.define('Ext.draw.modifier.Animation', {
    requires: [
        'Ext.draw.TimingFunctions',
        'Ext.draw.Animator'
    ],
    extend: 'Ext.draw.modifier.Modifier',
    alias: 'modifier.animation',

    config: {
        /**
         * @cfg {Function} easing
         * Default easing function.
         */
        easing: function (x) {
            return x;
        },

        /**
         * @cfg {Number} duration
         * Default duration time (ms).
         */
        duration: 0,

        /**
         * @cfg {Object} customEasings Overrides the default easing function for defined attributes. E.g.:
         *
         *     // Assuming the sprite the modifier is applied to is a 'circle'.
         *     customEasings: {
         *         r: 'easeOut',
         *         'fillStyle,strokeStyle': 'linear',
         *         'cx,cy': function (p, n) {
         *             p = 1 - p;
         *             n = n || 1.616;
         *             return 1 - p * p * ((n + 1) * p - n);
         *         }
         *     }
         */
        customEasings: {},

        /**
         * @cfg {Object} customDurations Overrides the default duration for defined attributes. E.g.:
         *
         *     // Assuming the sprite the modifier is applied to is a 'circle'.
         *     customDurations: {
         *         r: 1000,
         *         'fillStyle,strokeStyle': 2000,
         *         'cx,cy': 1000
         *     }
         */
        customDurations: {},

        /**
         * @deprecated Use {@link #customDurations} instead.
         */
        customDuration: null
    },

    constructor: function () {
        this.anyAnimation = false;
        this.anySpecialAnimations = false;
        this.animating = 0;
        this.animatingPool = [];
        this.callParent(arguments);
    },

    /**
     * @inheritdoc
     */
    prepareAttributes: function (attr) {
        if (!attr.hasOwnProperty('timers')) {
            attr.animating = false;
            attr.timers = {};
            // The animationOriginal object is used to hold the target values for the
            // attributes while they are being animated from source to target values.
            // The animationOriginal is pushed down to the lower level modifiers,
            // instead of the actual attr object, to hide the fact that the
            // attributes are being animated.
            attr.animationOriginal = Ext.Object.chain(attr);
            attr.animationOriginal.prototype = attr;
        }
        if (this._previous) {
            this._previous.prepareAttributes(attr.animationOriginal);
        }
    },

    updateSprite: function (sprite) {
        this.setConfig(sprite.config.fx);
    },

    updateDuration: function (duration) {
        this.anyAnimation = duration > 0;
    },

    applyEasing: function (easing) {
        if (typeof easing === 'string') {
            return Ext.draw.TimingFunctions.easingMap[easing];
        } else {
            return easing;
        }
    },

    applyCustomEasings: function (newEasings, oldEasings) {
        oldEasings = oldEasings || {};
        var any, key, attrs, easing, i, ln;

        for (key in newEasings) {
            any = true;
            easing = newEasings[key];
            attrs = key.split(',');
            if (typeof easing === 'string') {
                easing = Ext.draw.TimingFunctions.easingMap[easing];
            }
            for (i = 0, ln = attrs.length; i < ln; i++) {
                oldEasings[attrs[i]] = easing;
            }
        }
        if (any) {
            this.anySpecialAnimations = any;
        }
        return oldEasings;
    },

    /**
     * Set special easings on the given attributes. E.g.:
     *
     *     circleSprite.fx.setEasingOn('r', 'elasticIn');
     *
     * @param {String/Array} attrs The source attribute(s).
     * @param {String} easing The special easings.
     */
    setEasingOn: function (attrs, easing) {
        attrs = Ext.Array.from(attrs).slice();
        var customEasings = {},
            ln = attrs.length,
            i = 0;

        for (; i < ln; i++) {
            customEasings[attrs[i]] = easing;
        }
        this.setCustomEasings(customEasings);
    },

    /**
     * Remove special easings on the given attributes.
     * @param {String/Array} attrs The source attribute(s).
     */
    clearEasingOn: function (attrs) {
        attrs = Ext.Array.from(attrs, true);
        var i = 0, ln = attrs.length;
        for (; i < ln; i++) {
            delete this._customEasings[attrs[i]];
        }
    },

    applyCustomDurations: function (newDurations, oldDurations) {
        oldDurations = oldDurations || {};
        var any, key, duration, attrs, i, ln;

        for (key in newDurations) {
            any = true;
            duration = newDurations[key];
            attrs = key.split(',');
            for (i = 0, ln = attrs.length; i < ln; i++) {
                oldDurations[attrs[i]] = duration;
            }
        }
        if (any) {
            this.anySpecialAnimations = any;
        }
        return oldDurations;
    },

    /**
     * @private
     * @deprecated
     * @since 5.0.1.
     */
    applyCustomDuration: function (newDuration, oldDuration) {
        if (newDuration) {
            this.getCustomDurations();
            this.setCustomDurations(newDuration);
            Ext.log.warn("'customDuration' config is deprecated. Use 'customDurations' config instead.");
        }
    },

    /**
     * Set special duration on the given attributes. E.g.:
     *
     *     rectSprite.fx.setDurationOn('height', 2000);
     *
     * @param {String/Array} attrs The source attributes.
     * @param {Number} duration The special duration.
     */
    setDurationOn: function (attrs, duration) {
        attrs = Ext.Array.from(attrs).slice();
        var customDurations = {},
            i = 0,
            ln = attrs.length;

        for (; i < ln; i++) {
            customDurations[attrs[i]] = duration;
        }
        this.setCustomDurations(customDurations);
    },

    /**
     * Remove special easings on the given attributes.
     * @param {Object} attrs The source attributes.
     */
    clearDurationOn: function (attrs) {
        attrs = Ext.Array.from(attrs, true);
        var i = 0, ln = attrs.length;

        for (; i < ln; i++) {
            delete this._customDurations[attrs[i]];
        }
    },

    /**
     * @private
     * Initializes Animator for the animation.
     * @param {Object} attr The source attributes.
     * @param {Boolean} animating The animating flag.
     */
    setAnimating: function (attr, animating) {
        var me = this,
            pool = me.animatingPool;

        if (attr.animating !== animating) {
            attr.animating = animating;
            if (animating) {
                pool.push(attr);
                if (me.animating === 0) {
                    Ext.draw.Animator.add(me);
                }
                me.animating++;
            } else {
                for (var i = pool.length; i--;) {
                    if (pool[i] === attr) {
                        pool.splice(i, 1);
                    }
                }
                me.animating = pool.length;
            }
        }
    },

    /**
     * @private
     * Set the attr with given easing and duration.
     * @param {Object} attr The attributes collection.
     * @param {Object} changes The changes that popped up from lower modifier.
     * @return {Object} The changes to pop up.
     */
    setAttrs: function (attr, changes) {
        var timers = attr.timers,
            parsers = this._sprite.self.def._animationProcessors,
            defaultEasing = this._easing,
            defaultDuration = this._duration,
            customDurations = this._customDurations,
            customEasings = this._customEasings,
            anySpecial = this.anySpecialAnimations,
            any = this.anyAnimation || anySpecial,
            animationOriginal = attr.animationOriginal,
            ignite = false,
            timer, name, newValue, startValue, parser, easing, duration;

        if (!any) {
            // If there is no animation enabled
            // When applying changes to attributes, simply stop current animation
            // and set the value.
            for (name in changes) {
                if (attr[name] === changes[name]) {
                    delete changes[name];
                } else {
                    attr[name] = changes[name];
                }
                delete animationOriginal[name];
                delete timers[name];
            }
            return changes;
        } else {
            // If any animation
            for (name in changes) {
                newValue = changes[name];
                startValue = attr[name];
                if (newValue !== startValue && startValue !== undefined && startValue !== null && (parser = parsers[name])) {
                    // If this property is animating.

                    // Figure out the desired duration and easing.
                    easing = defaultEasing;
                    duration = defaultDuration;
                    if (anySpecial) {
                        // Deducing the easing function and duration
                        if (name in customEasings) {
                            easing = customEasings[name];
                        }
                        if (name in customDurations) {
                            duration = customDurations[name];
                        }
                    }

                    // Transitions betweens color and gradient or between gradients are not supported.
                    if (startValue && startValue.isGradient || newValue && newValue.isGradient) {
                        duration = 0;
                    }

                    // If the property is animating
                    if (duration) {
                        if (!timers[name]) {
                            timers[name] = {};
                        }

                        timer = timers[name];
                        timer.start = 0;
                        timer.easing = easing;
                        timer.duration = duration;
                        timer.compute = parser.compute;
                        timer.serve = parser.serve || Ext.identityFn;
                        timer.remove = changes.removeFromInstance && changes.removeFromInstance[name];

                        if (parser.parseInitial) {
                            var initial = parser.parseInitial(startValue, newValue);
                            timer.source = initial[0];
                            timer.target = initial[1];
                        } else if (parser.parse) {
                            timer.source = parser.parse(startValue);
                            timer.target = parser.parse(newValue);
                        } else {
                            timer.source = startValue;
                            timer.target = newValue;
                        }
                        // The animation started. Change to originalVal.
                        animationOriginal[name] = newValue;
                        delete changes[name];
                        ignite = true;
                        continue;
                    } else {
                        delete animationOriginal[name];
                    }
                } else {
                    delete animationOriginal[name];
                }

                // If the property is not animating.
                delete timers[name];
            }
        }

        if (ignite && !attr.animating) {
            this.setAnimating(attr, true);
        }

        return changes;
    },

    /**
     * @private
     *
     * Update attributes to current value according to current animation time.
     * This method will not affect the values of lower layers, but may delete a
     * value from it.
     * @param {Object} attr The source attributes.
     * @return {Object} The changes to pop up.
     */
    updateAttributes: function (attr) {
        if (!attr.animating) {
            return {};
        }
        var changes = {},
            any = false,
            timers = attr.timers,
            animationOriginal = attr.animationOriginal,
            now = Ext.draw.Animator.animationTime(),
            name, timer, delta;

        // If updated in the same frame, return.
        if (attr.lastUpdate === now) {
            return {};
        }

        for (name in timers) {
            timer = timers[name];
            if (!timer.start) {
                timer.start = now;
                delta = 0;
            } else {
                delta = (now - timer.start) / timer.duration;
            }
            if (delta >= 1) {
                changes[name] = animationOriginal[name];
                delete animationOriginal[name];
                if (timers[name].remove) {
                    changes.removeFromInstance = changes.removeFromInstance || {};
                    changes.removeFromInstance[name] = true;
                }
                delete timers[name];
            } else {
                changes[name] = timer.serve(timer.compute(timer.source, timer.target, timer.easing(delta), attr[name]));
                any = true;
            }
        }
        attr.lastUpdate = now;
        this.setAnimating(attr, any);
        return changes;
    },

    /**
     * @inheritdoc
     */
    pushDown: function (attr, changes) {
        changes = this.callParent([attr.animationOriginal, changes]);
        return this.setAttrs(attr, changes);
    },

    /**
     * @inheritdoc
     */
    popUp: function (attr, changes) {
        attr = attr.prototype;
        changes = this.setAttrs(attr, changes);
        if (this._next) {
            return this._next.popUp(attr, changes);
        } else {
            return Ext.apply(attr, changes);
        }
    },

    // This is called as an animated object in `Ext.draw.Animator`.
    step: function (frameTime) {
        var me = this,
            pool = me.animatingPool.slice(),
            attributes,
            i, ln;

        for (i = 0, ln = pool.length; i < ln; i++) {
            attributes = pool[i];
            var changes = this.updateAttributes(attributes),
                name;

            // Looking for anything in changes
            //noinspection LoopStatementThatDoesntLoopJS
            for (name in changes) {
                if (this._next) {
                    this._next.popUp(attributes, changes);
                }
                break;
            }
        }
    },

    /**
     * Stop all animations affected by this modifier.
     */
    stop: function () {
        this.step();

        var me = this,
            pool = me.animatingPool,
            i, ln;

        for (i = 0, ln = pool.length; i < ln; i++) {
            pool[i].animating = false;
        }
        me.animatingPool.length = 0;
        me.animating = 0;
        Ext.draw.Animator.remove(me);
    },

    destroy: function () {
        var me = this;
        me.animatingPool.length = 0;
        me.animating = 0;
    }
});

/**
 * @class Ext.draw.modifier.Highlight
 * @extends Ext.draw.modifier.Modifier
 *
 * Highlight is a modifier that will override sprite attributes
 * with {@link Ext.draw.modifier.Highlight#highlightStyle highlightStyle} attributes
 * when sprite's `highlighted` attribute is true.
 */
Ext.define('Ext.draw.modifier.Highlight', {
    extend: 'Ext.draw.modifier.Modifier',
    alias: 'modifier.highlight',

    config: {

        /**
         * @cfg {Boolean} enabled 'true' if the highlight is applied.
         */
        enabled: false,

        /**
         * @cfg {Object} highlightStyle The style attributes of the highlight modifier.
         */
        highlightStyle: null
    },

    preFx: true,

    applyHighlightStyle: function (style, oldStyle) {
        oldStyle = oldStyle || {};
        if (this.getSprite()) {
            Ext.apply(oldStyle, this.getSprite().self.def.normalize(style));
        } else {
            Ext.apply(oldStyle, style);
        }
        return oldStyle;
    },

    /**
     * @inheritdoc
     */
    prepareAttributes: function (attr) {
        if (!attr.hasOwnProperty('highlightOriginal')) {
            attr.highlighted = false;
            attr.highlightOriginal = Ext.Object.chain(attr);
            attr.highlightOriginal.prototype = attr;
            // A list of attributes that should be removed from a sprite instance
            // when it is unhighlighted.
            attr.highlightOriginal.removeFromInstance = {};
        }
        if (this._previous) {
            this._previous.prepareAttributes(attr.highlightOriginal);
        }
    },

    updateSprite: function (sprite, oldSprite) {
        if (sprite) {
            if (this.getHighlightStyle()) {
                this._highlightStyle = sprite.self.def.normalize(this.getHighlightStyle());
            }
            this.setHighlightStyle(sprite.config.highlight);
        }

        // Add highlight related attributes to sprite's attribute definition.
        // TODO: Unfortunately this will affect all sprites of the same type,
        // TODO: even those without the highlight modifier.
        sprite.self.def.setConfig({
            defaults: {
                highlighted: false
            },
            processors: {
                highlighted: 'bool'
            }
        });
        this.setSprite(sprite);
    },

    /**
     * Filter out modifier changes that override highlightStyle or source attributes.
     * @param {Object} attr The source attributes.
     * @param {Object} changes The modifier changes.
     * @return {*} The filtered changes.
     */
    filterChanges: function (attr, changes) {
        var me = this,
            highlightOriginal = attr.highlightOriginal,
            style = me.getHighlightStyle(),
            name;
        if (attr.highlighted) {
            // TODO: Remove changes that match highlightStyle attribute names.
            // TODO: Backup such changes to highlightOriginal before removing.
            for (name in changes) {
                if (style.hasOwnProperty(name)) {
                    // If sprite is highlighted, then stash the changes
                    // to the `highlightStyle` attributes made by lower level modifiers
                    // to apply them later when sprite is unhighlighted.
                    highlightOriginal[name] = changes[name];
                    delete changes[name];
                }
            }
        }

        // TODO: Remove changes (except the 'highlighted' flag) that match the original values. Why?
        for (name in changes) {
            if (name !== 'highlighted' && highlightOriginal[name] === changes[name]) {
                delete changes[name];
            }
        }

        return changes;
    },

    /**
     * @inheritdoc
     */
    pushDown: function (attr, changes) {
        var highlightStyle = this.getHighlightStyle(),
            highlightOriginal = attr.highlightOriginal,
            removeFromInstance = highlightOriginal.removeFromInstance,
            highlighted, name, tplAttr, timer;

        if (changes.hasOwnProperty('highlighted')) {
            highlighted = changes.highlighted;
            // Hide `highlighted` and `highlightStyle` from underlying modifiers.
            delete changes.highlighted;

            if (this._previous) {
                changes = this._previous.pushDown(highlightOriginal, changes);
            }
            changes = this.filterChanges(attr, changes);

            if (highlighted !== attr.highlighted) {
                if (highlighted) {
                    // Switching ON.
                    // At this time, original should be empty.
                    for (name in highlightStyle) {
                        // Remember the values of attributes to revert back to them on unhighlight.
                        if (name in changes) {
                            // Remember value set by lower level modifiers.
                            highlightOriginal[name] = changes[name];
                        } else {
                            // Remember the original value.

                            // If this is a sprite instance and it doesn't have its own
                            // 'name' attribute, (i.e. inherits template's attribute value)
                            // than we have to get the value for the 'name' attribute from
                            // the template's 'animationOriginal' object instead of its
                            // 'attr' object (which is the prototype of the instance),
                            // because the 'name' attribute of the template may be animating.
                            // Check out the prepareAttributes method of the Animation
                            // modifier for more details on the 'animationOriginal' object.

                            tplAttr = attr.template && attr.template.ownAttr;
                            if (tplAttr && !attr.hasOwnProperty(name)) {
                                removeFromInstance[name] = true;
                                highlightOriginal[name] = tplAttr.animationOriginal[name];
                            } else {

                                // Even if a sprite instance has its own property, it may
                                // still have to be removed from the instance after
                                // unhighlighting is done.
                                // Consider a situation where an instance doesn't originally
                                // have its own attribute (that is used for highlighting and
                                // unhighlighting). It will however have that attribute as
                                // its own when the highlight/unhighlight animation is in
                                // progress, until the attribute is removed from the instance
                                // when the unhighlighting is done.
                                // So in a scenario where the instance is highlighted, then
                                // unhighlighted (i.e. starts animating back to its original
                                // value) and then highlighted again before the unhighlight
                                // animation is done, we should still mark the attribute
                                // for removal from the instance, if it was our original
                                // intention. To tell if it was, we can check the timer
                                // for the attribute and see if the 'remove' flag is set.

                                timer = highlightOriginal.timers[name];
                                if (timer && timer.remove) {
                                    removeFromInstance[name] = true;
                                }
                                highlightOriginal[name] = attr[name];
                            }
                        }
                        if (highlightOriginal[name] !== highlightStyle[name]) {
                            changes[name] = highlightStyle[name];
                        }
                    }
                } else {
                    // Switching OFF.
                    for (name in highlightStyle) {
                        if (!(name in changes)) {
                            changes[name] = highlightOriginal[name];
                        }
                        delete highlightOriginal[name];
                    }
                    changes.removeFromInstance = changes.removeFromInstance || {};
                    // Let the higher lever animation modifier know which attributes
                    // should be removed from instance when the animation is done.
                    Ext.apply(changes.removeFromInstance, removeFromInstance);
                    highlightOriginal.removeFromInstance = {};
                }
                changes.highlighted = highlighted;
            }
        } else {
            if (this._previous) {
                changes = this._previous.pushDown(highlightOriginal, changes);
            }
            changes = this.filterChanges(attr, changes);
        }

        return changes;
    },

    /**
     * @inheritdoc
     */
    popUp: function (attr, changes) {
        changes = this.filterChanges(attr, changes);
        Ext.draw.modifier.Modifier.prototype.popUp.call(this, attr, changes);
    }
});

/**
 * A sprite is an object rendered in a drawing {@link Ext.draw.Surface}.
 * The Sprite class itself is an abstract class and is not meant to be used directly.
 * Every sprite in the Draw and Chart packages is a subclass of the Ext.draw.sprite.Sprite.
 * The standard Sprite subclasses are:
 *
 * * {@link Ext.draw.sprite.Path} - A sprite that represents a path.
 * * {@link Ext.draw.sprite.Rect} - A sprite that represents a rectangle.
 * * {@link Ext.draw.sprite.Circle} - A sprite that represents a circle.
 * * {@link Ext.draw.sprite.Sector} - A sprite representing a pie slice.
 * * {@link Ext.draw.sprite.Arc} - A sprite that represents a circular arc.
 * * {@link Ext.draw.sprite.Ellipse} - A sprite that represents an ellipse.
 * * {@link Ext.draw.sprite.EllipticalArc} - A sprite that represents an elliptical arc.
 * * {@link Ext.draw.sprite.Text} - A sprite that represents text.
 * * {@link Ext.draw.sprite.Image} -  A sprite that represents an image.
 * * {@link Ext.draw.sprite.Instancing} - A sprite that represents multiple instances based on the given template.
 * * {@link Ext.draw.sprite.Composite} - Represents a group of sprites.
 *
 * Sprites can be created with a reference to a {@link Ext.draw.Surface}
 *
 *      var drawContainer = Ext.create('Ext.draw.Container', {
 *          // ...
 *      });
 *
 *      var sprite = Ext.create('Ext.draw.sprite.Sprite', {
 *          type: 'circle',
 *          fill: '#ff0',
 *          surface: drawContainer.getSurface('main'),
 *          radius: 5
 *      });
 *
 * Sprites can also be added to the surface as a configuration object:
 *
 *      var sprite = drawContainer.getSurface('main').add({
 *          type: 'circle',
 *          fill: '#ff0',
 *          radius: 5
 *      });
 */
Ext.define('Ext.draw.sprite.Sprite', {
    alias: 'sprite.sprite',

    mixins: {
        observable: 'Ext.mixin.Observable'
    },

    requires: [
        'Ext.draw.Draw',
        'Ext.draw.gradient.Gradient',
        'Ext.draw.sprite.AttributeDefinition',
        'Ext.draw.modifier.Target',
        'Ext.draw.modifier.Animation',
        'Ext.draw.modifier.Highlight'
    ],

    isSprite: true,

    inheritableStatics: {
        def: {
            processors: {
                /**
                 * @cfg {String} [strokeStyle="none"] The color of the stroke (a CSS color value).
                 */
                strokeStyle: "color",

                /**
                 * @cfg {String} [fillStyle="none"] The color of the shape (a CSS color value).
                 */
                fillStyle: "color",

                /**
                 * @cfg {Number} [strokeOpacity=1] The opacity of the stroke. Limited from 0 to 1.
                 */
                strokeOpacity: "limited01",

                /**
                 * @cfg {Number} [fillOpacity=1] The opacity of the fill. Limited from 0 to 1.
                 */
                fillOpacity: "limited01",

                /**
                 * @cfg {Number} [lineWidth=1] The width of the line stroke.
                 */
                lineWidth: "number",

                /**
                 * @cfg {String} [lineCap="butt"] The style of the line caps.
                 */
                lineCap: "enums(butt,round,square)",

                /**
                 * @cfg {String} [lineJoin="miter"] The style of the line join.
                 */
                lineJoin: "enums(round,bevel,miter)",

                /**
                 * @cfg {Array} [lineDash=[]]
                 * An even number of non-negative numbers specifying a dash/space sequence.
                 * Note that while this is supported in IE8 (VML engine), the behavior is
                 * different from Canvas and SVG. Please refer to this document for details:
                 * http://msdn.microsoft.com/en-us/library/bb264085(v=vs.85).aspx
                 */
                lineDash: "data",

                /**
                 * @cfg {Number} [lineDashOffset=0]
                 * A number specifying how far into the line dash sequence drawing commences.
                 */
                lineDashOffset: "number",

                /**
                 * @cfg {Number} [miterLimit=10]
                 * Sets the distance between the inner corner and the outer corner where two lines meet.
                 */
                miterLimit: "number",

                /**
                 * @cfg {String} [shadowColor="none"] The color of the shadow (a CSS color value).
                 */
                shadowColor: "color",

                /**
                 * @cfg {Number} [shadowOffsetX=0] The offset of the sprite's shadow on the x-axis.
                 */
                shadowOffsetX: "number",

                /**
                 * @cfg {Number} [shadowOffsetY=0] The offset of the sprite's shadow on the y-axis.
                 */
                shadowOffsetY: "number",

                /**
                 * @cfg {Number} [shadowBlur=0] The amount blur used on the shadow.
                 */
                shadowBlur: "number",

                /**
                 * @cfg {Number} [globalAlpha=1] The opacity of the sprite. Limited from 0 to 1.
                 */
                globalAlpha: "limited01",

                /**
                 * @cfg {String} [globalCompositeOperation=source-over]
                 * Indicates how source images are drawn onto a destination image.
                 * globalCompositeOperation attribute is not supported by the SVG and VML (excanvas) engines.
                 */
                globalCompositeOperation: "enums(source-over,destination-over,source-in,destination-in,source-out,destination-out,source-atop,destination-atop,lighter,xor,copy)",

                /**
                 * @cfg {Boolean} [hidden=false] Determines whether or not the sprite is hidden.
                 */
                hidden: "bool",

                /**
                 * @cfg {Boolean} [transformFillStroke=false]
                 * Determines whether the fill and stroke are affected by sprite transformations.
                 */
                transformFillStroke: "bool",

                /**
                 * @cfg {Number} [zIndex=0]
                 * The stacking order of the sprite.
                 */
                zIndex: "number",

                /**
                 * @cfg {Number} [translationX=0]
                 * The translation of the sprite on the x-axis.
                 */
                translationX: "number",

                /**
                 * @cfg {Number} [translationY=0]
                 * The translation of the sprite on the y-axis.
                 */
                translationY: "number",

                /**
                 * @cfg {Number} [rotationRads=0]
                 * The angle of rotation of the sprite in radians.
                 */
                rotationRads: "number",

                /**
                 * @cfg {Number} [rotationCenterX=null]
                 * The central coordinate of the sprite's scale operation on the x-axis.
                 */
                rotationCenterX: "number",

                /**
                 * @cfg {Number} [rotationCenterY=null]
                 * The central coordinate of the sprite's rotate operation on the y-axis.
                 */
                rotationCenterY: "number",

                /**
                 * @cfg {Number} [scalingX=1] The scaling of the sprite on the x-axis.
                 */
                scalingX: "number",

                /**
                 * @cfg {Number} [scalingY=1] The scaling of the sprite on the y-axis.
                 */
                scalingY: "number",

                /**
                 * @cfg {Number} [scalingCenterX=null]
                 * The central coordinate of the sprite's scale operation on the x-axis.
                 */
                scalingCenterX: "number",

                /**
                 * @cfg {Number} [scalingCenterY=null]
                 * The central coordinate of the sprite's scale operation on the y-axis.
                 */
                scalingCenterY: "number",
                
                constrainGradients: "bool"
            },

            aliases: {
                "stroke": "strokeStyle",
                "fill": "fillStyle",
                "color": "fillStyle",
                "stroke-width": "lineWidth",
                "stroke-linecap": "lineCap",
                "stroke-linejoin": "lineJoin",
                "stroke-miterlimit": "miterLimit",
                "text-anchor": "textAlign",
                "opacity": "globalAlpha",

                translateX: "translationX",
                translateY: "translationY",
                rotateRads: "rotationRads",
                rotateCenterX: "rotationCenterX",
                rotateCenterY: "rotationCenterY",
                scaleX: "scalingX",
                scaleY: "scalingY",
                scaleCenterX: "scalingCenterX",
                scaleCenterY: "scalingCenterY"
            },

            defaults: {
                hidden: false,
                zIndex: 0,

                strokeStyle: "none",
                fillStyle: "none",
                lineWidth: 1,
                lineDash: [],
                lineDashOffset: 0,
                lineCap: "butt",
                lineJoin: "miter",
                miterLimit: 10,

                shadowColor: "none",
                shadowOffsetX: 0,
                shadowOffsetY: 0,
                shadowBlur: 0,

                globalAlpha: 1,
                strokeOpacity: 1,
                fillOpacity: 1,
                transformFillStroke: false,

                translationX: 0,
                translationY: 0,
                rotationRads: 0,
                rotationCenterX: null,
                rotationCenterY: null,
                scalingX: 1,
                scalingY: 1,
                scalingCenterX: null,
                scalingCenterY: null,
                
                constrainGradients: false
            },

            dirtyTriggers: {
                hidden: "canvas",
                zIndex: "zIndex",

                globalAlpha: "canvas",
                globalCompositeOperation: "canvas",

                transformFillStroke: "canvas",
                strokeStyle: "canvas",
                fillStyle: "canvas",
                strokeOpacity: "canvas",
                fillOpacity: "canvas",

                lineWidth: "canvas",
                lineCap: "canvas",
                lineJoin: "canvas",
                lineDash: "canvas",
                lineDashOffset: "canvas",
                miterLimit: "canvas",

                shadowColor: "canvas",
                shadowOffsetX: "canvas",
                shadowOffsetY: "canvas",
                shadowBlur: "canvas",

                translationX: "transform",
                translationY: "transform",
                rotationRads: "transform",
                rotationCenterX: "transform",
                rotationCenterY: "transform",
                scalingX: "transform",
                scalingY: "transform",
                scalingCenterX: "transform",
                scalingCenterY: "transform",
                
                constrainGradients: "canvas"
            },

            updaters: {
                "bbox": function (attrs) {
                    var hasRotation = attrs.rotationRads !== 0,
                        hasScaling = attrs.scalingX !== 1 || attrs.scalingY !== 1,
                        noRotationCenter = attrs.rotationCenterX === null || attrs.rotationCenterY === null,
                        noScalingCenter = attrs.scalingCenterX === null || attrs.scalingCenterY === null;

                    attrs.bbox.plain.dirty = true;
                    attrs.bbox.transform.dirty = true;

                    if (hasRotation && noRotationCenter || hasScaling && noScalingCenter) {
                        if (!attrs.dirtyFlags.transform) {
                            attrs.dirtyFlags.transform = [];
                        }
                    }
                },

                "zIndex": function (attrs) {
                    attrs.dirtyZIndex = true;
                },

                "transform": function (attrs) {
                    attrs.dirtyTransform = true;
                    attrs.bbox.transform.dirty = true;
                }
            }
        }
    },

    /**
     * @property {Object} attr
     * The visual attributes of the sprite, e.g. strokeStyle, fillStyle, lineWidth...
     */
    attr: {},

    config: {
        parent: null,
        /**
         * @cfg {Ext.draw.Surface} surface
         * The surface that this sprite is rendered into.
         */
        surface: null
    },

    onClassExtended: function (subClass, data) {
        // The `def` here is no longer a config, but an instance
        // of the AttributeDefinition class created with that config,
        // which can now be retrieved from `initialConfig`.
        var initCfg = subClass.superclass.self.def.initialConfig,
            cfg;

        // If sprite defines attributes of its own, merge that with those of its parent.
        if (data.inheritableStatics && data.inheritableStatics.def) {
            cfg = Ext.merge({}, initCfg, data.inheritableStatics.def);
            subClass.def = Ext.create('Ext.draw.sprite.AttributeDefinition', cfg);
            delete data.inheritableStatics.def;
        } else {
            subClass.def = Ext.create('Ext.draw.sprite.AttributeDefinition', initCfg);
        }
    },

    constructor: function (config) {
        if (Ext.getClassName(this) === 'Ext.draw.sprite.Sprite') {
            throw 'Ext.draw.sprite.Sprite is an abstract class';
        }
        var me = this;

        config = Ext.isObject(config) ? config : {};

        me.id = config.id || Ext.id(null, 'ext-sprite-');
        me.attr = {};
        me.mixins.observable.constructor.apply(me, arguments);
        
        var modifiers = Ext.Array.from(config.modifiers, true);
        me.prepareModifiers(modifiers);
        me.initializeAttributes();
        me.setAttributes(me.self.def.getDefaults(), true);
        var processors = me.self.def.getProcessors();
        for (var name in config) {
            if (name in processors && me['get' + name.charAt(0).toUpperCase() + name.substr(1)]) {
                Ext.Error.raise('The ' + me.$className +
                    ' sprite has both a config and an attribute with the same name: ' + name + '.');
            }
        }
        me.setAttributes(config);
    },

    getDirty: function () {
        return this.attr.dirty;
    },

    setDirty: function (dirty) {
        if ((this.attr.dirty = dirty)) {
            if (this._parent) {
                this._parent.setDirty(true);
            }
        }
    },

    addModifier: function (modifier, reinitializeAttributes) {
        var me = this;
        if (!(modifier instanceof Ext.draw.modifier.Modifier)) {
            modifier = Ext.factory(modifier, null, null, 'modifier');
        }
        modifier.setSprite(this);
        if (modifier.preFx || modifier.config && modifier.config.preFx) {
            if (me.fx.getPrevious()) {
                me.fx.getPrevious().setNext(modifier);
            }
            modifier.setNext(me.fx);
        } else {
            me.topModifier.getPrevious().setNext(modifier);
            modifier.setNext(me.topModifier);
        }
        if (reinitializeAttributes) {
            me.initializeAttributes();
        }
        return modifier;
    },

    prepareModifiers: function (additionalModifiers) {
        // Set defaults
        var me = this,
            i, ln;

        me.topModifier = new Ext.draw.modifier.Target({sprite: me});

        // Link modifiers
        me.fx = new Ext.draw.modifier.Animation({sprite: me});
        me.fx.setNext(me.topModifier);

        for (i = 0, ln = additionalModifiers.length; i < ln; i++) {
            me.addModifier(additionalModifiers[i], false);
        }
    },

    initializeAttributes: function () {
        var me = this;
        me.topModifier.prepareAttributes(me.attr);
    },

    updateDirtyFlags: function (attr) {
        var me = this,
            dirtyFlags = attr.dirtyFlags,
            updaters = me.self.def.getUpdaters(),
            any = false,
            dirty = false,
            flags, updater;

        do {
            any = false;
            for (updater in dirtyFlags) {
                any = true;
                flags = dirtyFlags[updater];
                delete dirtyFlags[updater];
                // To keep things predictable first call all existing updaters from dirtyFlags,
                // then (on the next do/while loop iteration) call updaters for dirtyFlags
                // that may have been added by updaters just called.
                me.updateDirtyFlags = Ext.emptyFn;
                if (updaters[updater]) {
                    updaters[updater].call(me, attr, flags);
                }
                delete me.updateDirtyFlags;
            }
            dirty = dirty || any;
        } while (any);

        if (dirty) {
            me.setDirty(true);
        }
    },

    /**
     * Set attributes of the sprite.
     *
     * @param {Object} changes The content of the change.
     * @param {Boolean} [bypassNormalization] `true` to avoid normalization of the given changes.
     * @param {Boolean} [avoidCopy] `true` to avoid copying the `changes` object.
     * The content of object may be destroyed.
     */
    setAttributes: function (changes, bypassNormalization, avoidCopy) {
        var attributes = this.attr;
        if (bypassNormalization) {
            if (avoidCopy) {
                this.topModifier.pushDown(attributes, changes);
            } else {
                this.topModifier.pushDown(attributes, Ext.apply({}, changes));
            }
        } else {
            this.topModifier.pushDown(attributes, this.self.def.normalize(changes));
        }
    },

    /**
     * Set attributes of the sprite, assuming the names and values have already been
     * normalized.
     *
     * @deprecated Use setAttributes directy with bypassNormalization argument being `true`.
     * @param {Object} changes The content of the change.
     * @param {Boolean} [avoidCopy] `true` to avoid copying the `changes` object.
     * The content of object may be destroyed.
     */
    setAttributesBypassingNormalization: function (changes, avoidCopy) {
        return this.setAttributes(changes, true, avoidCopy);
    },

    /**
     * Returns the bounding box for the given Sprite as calculated with the Canvas engine.
     *
     * @param {Boolean} [isWithoutTransform] Whether to calculate the bounding box with the current transforms or not.
     */
    getBBox: function (isWithoutTransform) {
        var me = this,
            attr = me.attr,
            bbox = attr.bbox,
            plain = bbox.plain,
            transform = bbox.transform;
        if (plain.dirty) {
            me.updatePlainBBox(plain);
            plain.dirty = false;
        }
        if (isWithoutTransform) {
            return plain;
        } else {
            me.applyTransformations();
            if (transform.dirty) {
                me.updateTransformedBBox(transform, plain);
                transform.dirty = false;
            }
            return transform;
        }
    },

    /**
     * @protected
     * Subclass will fill the plain object with `x`, `y`, `width`, `height` information of the plain bounding box of
     * this sprite.
     *
     * @param {Object} plain Target object.
     */
    updatePlainBBox: Ext.emptyFn,

    /**
     * @protected
     * Subclass will fill the plain object with `x`, `y`, `width`, `height` information of the transformed
     * bounding box of this sprite.
     *
     * @param {Object} transform Target object.
     * @param {Object} plain Auxiliary object providing information of plain object.
     */
    updateTransformedBBox: function (transform, plain) {
        this.attr.matrix.transformBBox(plain, 0, transform);
    },

    /**
     * Subclass can rewrite this function to gain better performance.
     * @param {Boolean} isWithoutTransform
     * @return {Array}
     */
    getBBoxCenter: function (isWithoutTransform) {
        var bbox = this.getBBox(isWithoutTransform);
        if (bbox) {
            return [
                bbox.x + bbox.width * 0.5,
                bbox.y + bbox.height * 0.5
            ];
        } else {
            return [0, 0];
        }
    },

    /**
     * Hide the sprite.
     * @return {Ext.draw.sprite.Sprite} this
     * @chainable
     */
    hide: function () {
        this.attr.hidden = true;
        this.setDirty(true);
        return this;
    },

    /**
     * Show the sprite.
     * @return {Ext.draw.sprite.Sprite} this
     * @chainable
     */
    show: function () {
        this.attr.hidden = false;
        this.setDirty(true);
        return this;
    },

    /**
     * Applies sprite's attributes to the given context.
     * @param {Object} ctx Context to apply sprite's attributes to.
     * @param {Array} rect The rect of the context to be affected by gradients.
     */
    useAttributes: function (ctx, rect) {
        this.applyTransformations();
        var attrs = this.attr,
            canvasAttributes = attrs.canvasAttributes,
            strokeStyle = canvasAttributes.strokeStyle,
            fillStyle = canvasAttributes.fillStyle,
            lineDash = canvasAttributes.lineDash,
            lineDashOffset = canvasAttributes.lineDashOffset,
            id;

        if (strokeStyle) {
            if (strokeStyle.isGradient) {
                ctx.strokeStyle = 'black';
                ctx.strokeGradient = strokeStyle;
            } else {
                ctx.strokeGradient = false;
            }
        }

        if (fillStyle) {
            if (fillStyle.isGradient) {
                ctx.fillStyle = 'black';
                ctx.fillGradient = fillStyle;
            } else {
                ctx.fillGradient = false;
            }
        }

        if (lineDash) {
            ctx.setLineDash(lineDash);
        }

        // Only set lineDashOffset to contexts that support the property (excludes VML).
        if (Ext.isNumber(lineDashOffset + ctx.lineDashOffset)) {
            ctx.lineDashOffset = lineDashOffset;
        }

        for (id in canvasAttributes) {
            if (canvasAttributes[id] !== undefined && canvasAttributes[id] !== ctx[id]) {
                ctx[id] = canvasAttributes[id];
            }
        }

        this.setGradientBBox(ctx, rect);
    },

    setGradientBBox: function (ctx, rect) {
        var attr = this.attr;
        if (attr.constrainGradients) {
            ctx.setGradientBBox({x: rect[0], y: rect[1], width: rect[2], height: rect[3]});
        } else {
            ctx.setGradientBBox(this.getBBox(attr.transformFillStroke));
        }
    },

    /**
     * @private
     *
     * Calculates forward and inverse transform matrices.
     * @param {Boolean} force Forces recalculation of transform matrices even when sprite's transform attributes supposedly haven't changed.
     */
    applyTransformations: function (force) {
        if (!force && !this.attr.dirtyTransform) {
            return;
        }
        var me = this,
            attr = me.attr,
            center = me.getBBoxCenter(true),
            centerX = center[0],
            centerY = center[1],

            x = attr.translationX,
            y = attr.translationY,

            sx = attr.scalingX,
            sy = attr.scalingY === null ? attr.scalingX : attr.scalingY,
            scx = attr.scalingCenterX === null ? centerX : attr.scalingCenterX,
            scy = attr.scalingCenterY === null ? centerY : attr.scalingCenterY,

            rad = attr.rotationRads,
            rcx = attr.rotationCenterX === null ? centerX : attr.rotationCenterX,
            rcy = attr.rotationCenterY === null ? centerY : attr.rotationCenterY,

            cos = Math.cos(rad),
            sin = Math.sin(rad);

        if (sx === 1 && sy === 1) {
            scx = 0;
            scy = 0;
        }

        if (rad === 0) {
            rcx = 0;
            rcy = 0;
        }

        attr.matrix.elements = [
            cos * sx, sin * sy,
            -sin * sx, cos * sy,
            scx + (rcx - cos * rcx - scx + rcy * sin) * sx + x,
            scy + (rcy - cos * rcy - scy + rcx * -sin) * sy + y
        ];
        attr.matrix.inverse(attr.inverseMatrix);
        attr.dirtyTransform = false;
        attr.bbox.transform.dirty = true;
    },

    /**
     * Called before rendering.
     */
    preRender: Ext.emptyFn,

    /**
     * Render method.
     * @param {Ext.draw.Surface} surface The surface.
     * @param {Object} ctx A context object compatible with CanvasRenderingContext2D.
     * @param {Array} rect The clip rect (or called dirty rect) of the current rendering. Not to be confused
     * with `surface.getRect()`.
     *
     * @return {*} returns `false` to stop rendering in this frame. All the sprite haven't been rendered
     * will have their dirty flag untouched.
     */
    render: Ext.emptyFn,

    repaint: function () {
        var surface = this.getSurface();
        if (surface) {
            surface.renderFrame();
        }
    },

    /**
     * Removes the sprite and clears all listeners.
     */
    destroy: function () {
        var me = this, modifier = me.topModifier, curr;
        while (modifier) {
            curr = modifier;
            modifier = modifier.getPrevious();
            curr.destroy();
        }
        delete me.attr;

        me.destroy = Ext.emptyFn;
        if (me.fireEvent('beforedestroy', me) !== false) {
            me.fireEvent('destroy', me);
        }
        this.callParent();
    }
}, function () { // onClassCreated
    // Create one AttributeDefinition instance per sprite class when a class is created
    // and replace the `def` config with the instance that was created using that config.
    // Here we only create an AttributeDefinition instance for the base Sprite class,
    // attribute definitions for subclasses are created inside onClassExtended method.
    this.def = Ext.create('Ext.draw.sprite.AttributeDefinition', this.def);
});


/**
 * Class representing a path.
 * Designed to be compatible with [CanvasPathMethods](http://www.whatwg.org/specs/web-apps/current-work/multipage/the-canvas-element.html#canvaspathmethods)
 * and will hopefully be replaced by the browsers' implementation of the Path object.
 */
Ext.define('Ext.draw.Path', {
    requires: ['Ext.draw.Draw'],
    statics: {
        pathRe: /,?([achlmqrstvxz]),?/gi,
        pathRe2: /-/gi,
        pathSplitRe: /\s|,/g
    },
    svgString: '',

    /**
     * Create a path from pathString.
     * @constructor
     * @param {String} pathString
     */
    constructor: function (pathString) {
        var me = this;
        me.commands = []; // Stores command letters from the SVG path data ('d' attribute).
        me.params = [];   // Stores command parameters from the SVG path data.
        // All command parameters are actually point coordinates as the only commands used
        // are the M, L, C, Z. This makes path transformations and hit testing easier.
        // Arcs are approximated using cubic Bezier curves, H and S commands are translated
        // to L commands and relative commands are translated to their absolute versions.
        me.cursor = null;
        me.startX = 0;
        me.startY = 0;
        if (pathString) {
            me.fromSvgString(pathString);
        }
    },

    /**
     * Clear the path.
     */
    clear: function () {
        var me = this;
        me.params.length = 0;
        me.commands.length = 0;
        me.cursor = null;
        me.startX = 0;
        me.startY = 0;
        me.dirt();
    },

    /**
     * @private
     */
    dirt: function () {
        this.svgString = '';
    },

    /**
     * Move to a position.
     * @param {Number} x
     * @param {Number} y
     */
    moveTo: function (x, y) {
        var me = this;
        if (!me.cursor) {
            me.cursor = [x, y];
        }
        me.params.push(x, y);
        me.commands.push('M');
        me.startX = x;
        me.startY = y;
        me.cursor[0] = x;
        me.cursor[1] = y;
        me.dirt();
    },

    /**
     * A straight line to a position.
     * @param {Number} x
     * @param {Number} y
     */
    lineTo: function (x, y) {
        var me = this;
        if (!me.cursor) {
            me.cursor = [x, y];
            me.params.push(x, y);
            me.commands.push('M');
        } else {
            me.params.push(x, y);
            me.commands.push('L');
        }
        me.cursor[0] = x;
        me.cursor[1] = y;
        me.dirt();
    },

    /**
     * A cubic bezier curve to a position.
     * @param {Number} cx1
     * @param {Number} cy1
     * @param {Number} cx2
     * @param {Number} cy2
     * @param {Number} x
     * @param {Number} y
     */
    bezierCurveTo: function (cx1, cy1, cx2, cy2, x, y) {
        var me = this;
        if (!me.cursor) {
            me.moveTo(cx1, cy1);
        }
        me.params.push(cx1, cy1, cx2, cy2, x, y);
        me.commands.push('C');
        me.cursor[0] = x;
        me.cursor[1] = y;
        me.dirt();
    },

    /**
     * A quadratic bezier curve to a position.
     * @param {Number} cx
     * @param {Number} cy
     * @param {Number} x
     * @param {Number} y
     */
    quadraticCurveTo: function (cx, cy, x, y) {
        var me = this;
        if (!me.cursor) {
            me.moveTo(cx, cy);
        }
        me.bezierCurveTo(
            (2 * cx + me.cursor[0]) / 3, (2 * cy + me.cursor[1]) / 3,
            (2 * cx + x) / 3, (2 * cy + y) / 3,
            x, y
        );
    },

    /**
     * Close this path with a straight line.
     */
    closePath: function () {
        var me = this;
        if (me.cursor) {
            me.commands.push('Z');
            me.dirt();
        }
    },

    /**
     * Create a elliptic arc curve compatible with SVG's arc to instruction.
     *
     * The curve start from (`x1`, `y1`) and ends at (`x2`, `y2`). The ellipse
     * has radius `rx` and `ry` and a rotation of `rotation`.
     * @param {Number} x1
     * @param {Number} y1
     * @param {Number} x2
     * @param {Number} y2
     * @param {Number} [rx]
     * @param {Number} [ry]
     * @param {Number} [rotation]
     */
    arcTo: function (x1, y1, x2, y2, rx, ry, rotation) {
        var me = this;
        if (ry === undefined) {
            ry = rx;
        }

        if (rotation === undefined) {
            rotation = 0;
        }

        if (!me.cursor) {
            me.moveTo(x1, y1);
            return;
        }

        if (rx === 0 || ry === 0) {
            me.lineTo(x1, y1);
            return;
        }

        x2 -= x1;
        y2 -= y1;

        var x0 = me.cursor[0] - x1,
            y0 = me.cursor[1] - y1,
            area = x2 * y0 - y2 * x0,
            cos, sin, xx, yx, xy, yy,
            l0 = Math.sqrt(x0 * x0 + y0 * y0),
            l2 = Math.sqrt(x2 * x2 + y2 * y2),
            dist, cx, cy;
        // cos rx, -sin ry , x1 - cos rx x1 + ry sin y1
        // sin rx, cos ry, -rx sin x1 + y1 - cos ry y1
        if (area === 0) {
            me.lineTo(x1, y1);
            return;
        }

        if (ry !== rx) {
            cos = Math.cos(rotation);
            sin = Math.sin(rotation);
            xx = cos / rx;
            yx = sin / ry;
            xy = -sin / rx;
            yy = cos / ry;
            var temp = xx * x0 + yx * y0;
            y0 = xy * x0 + yy * y0;
            x0 = temp;
            temp = xx * x2 + yx * y2;
            y2 = xy * x2 + yy * y2;
            x2 = temp;
        } else {
            x0 /= rx;
            y0 /= ry;
            x2 /= rx;
            y2 /= ry;
        }

        cx = x0 * l2 + x2 * l0;
        cy = y0 * l2 + y2 * l0;
        dist = 1 / (Math.sin(Math.asin(Math.abs(area) / (l0 * l2)) * 0.5) * Math.sqrt(cx * cx + cy * cy));
        cx *= dist;
        cy *= dist;

        var k0 = (cx * x0 + cy * y0) / (x0 * x0 + y0 * y0),
            k2 = (cx * x2 + cy * y2) / (x2 * x2 + y2 * y2);
        var cosStart = x0 * k0 - cx,
            sinStart = y0 * k0 - cy,
            cosEnd = x2 * k2 - cx,
            sinEnd = y2 * k2 - cy,
            startAngle = Math.atan2(sinStart, cosStart),
            endAngle = Math.atan2(sinEnd, cosEnd);
        if (area > 0) {
            if (endAngle < startAngle) {
                endAngle += Math.PI * 2;
            }
        } else {
            if (startAngle < endAngle) {
                startAngle += Math.PI * 2;
            }
        }
        if (ry !== rx) {
            cx = cos * cx * rx - sin * cy * ry + x1;
            cy = sin * cy * ry + cos * cy * ry + y1;
            me.lineTo(cos * rx * cosStart - sin * ry * sinStart + cx,
                sin * rx * cosStart + cos * ry * sinStart + cy);
            me.ellipse(cx, cy, rx, ry, rotation, startAngle, endAngle, area < 0);
        } else {
            cx = cx * rx + x1;
            cy = cy * ry + y1;
            me.lineTo(rx * cosStart + cx, ry * sinStart + cy);
            me.ellipse(cx, cy, rx, ry, rotation, startAngle, endAngle, area < 0);
        }
    },

    /**
     * Create an elliptic arc.
     *
     * See [the whatwg reference of ellipse](http://www.whatwg.org/specs/web-apps/current-work/multipage/the-canvas-element.html#dom-context-2d-ellipse).
     *
     * @param {Number} cx
     * @param {Number} cy
     * @param {Number} radiusX
     * @param {Number} radiusY
     * @param {Number} rotation
     * @param {Number} startAngle
     * @param {Number} endAngle
     * @param {Number} anticlockwise
     */
    ellipse: function (cx, cy, radiusX, radiusY, rotation, startAngle, endAngle, anticlockwise) {
        var me = this,
            params = me.params,
            start = params.length, count,
            i, j;
        if (endAngle - startAngle >= Math.PI * 2) {
            me.ellipse(cx, cy, radiusX, radiusY, rotation, startAngle, startAngle + Math.PI, anticlockwise);
            me.ellipse(cx, cy, radiusX, radiusY, rotation, startAngle + Math.PI, endAngle, anticlockwise);
            return;
        }
        if (!anticlockwise) {
            if (endAngle < startAngle) {
                endAngle += Math.PI * 2;
            }
            count = me.approximateArc(params, cx, cy, radiusX, radiusY, rotation, startAngle, endAngle);
        } else {
            if (startAngle < endAngle) {
                startAngle += Math.PI * 2;
            }
            count = me.approximateArc(params, cx, cy, radiusX, radiusY, rotation, endAngle, startAngle);
            for (i = start, j = params.length - 2; i < j; i += 2, j -= 2) {
                var temp = params[i];
                params[i] = params[j];
                params[j] = temp;
                temp = params[i + 1];
                params[i + 1] = params[j + 1];
                params[j + 1] = temp;
            }
        }

        if (!me.cursor) {
            me.cursor = [params[params.length - 2], params[params.length - 1]];
            me.commands.push('M');
        } else {
            me.cursor[0] = params[params.length - 2];
            me.cursor[1] = params[params.length - 1];
            me.commands.push('L');
        }

        for (i = 2; i < count; i += 6) {
            me.commands.push('C');
        }
        me.dirt();
    },

    /**
     * Create an circular arc.
     *
     * @param {Number} x
     * @param {Number} y
     * @param {Number} radius
     * @param {Number} startAngle
     * @param {Number} endAngle
     * @param {Number} anticlockwise
     */
    arc: function (x, y, radius, startAngle, endAngle, anticlockwise) {
        this.ellipse(x, y, radius, radius, 0, startAngle, endAngle, anticlockwise);
    },

    /**
     * Draw a rectangle and close it.
     *
     * @param {Number} x
     * @param {Number} y
     * @param {Number} width
     * @param {Number} height
     */
    rect: function (x, y, width, height) {
        if (width == 0 || height == 0) {
            return;
        }
        var me = this;
        me.moveTo(x, y);
        me.lineTo(x + width, y);
        me.lineTo(x + width, y + height);
        me.lineTo(x, y + height);
        me.closePath();
    },

    /**
     * @private
     * @param {Array} result
     * @param {Number} cx
     * @param {Number} cy
     * @param {Number} rx
     * @param {Number} ry
     * @param {Number} phi
     * @param {Number} theta1
     * @param {Number} theta2
     * @return {Number}
     */
    approximateArc: function (result, cx, cy, rx, ry, phi, theta1, theta2) {
        var cosPhi = Math.cos(phi),
            sinPhi = Math.sin(phi),
            cosTheta1 = Math.cos(theta1),
            sinTheta1 = Math.sin(theta1),
            xx = cosPhi * cosTheta1 * rx - sinPhi * sinTheta1 * ry,
            yx = -cosPhi * sinTheta1 * rx - sinPhi * cosTheta1 * ry,
            xy = sinPhi * cosTheta1 * rx + cosPhi * sinTheta1 * ry,
            yy = -sinPhi * sinTheta1 * rx + cosPhi * cosTheta1 * ry,
            rightAngle = Math.PI / 2,
            count = 2,
            exx = xx,
            eyx = yx,
            exy = xy,
            eyy = yy,
            rho = 0.547443256150549,
            temp, y1, x3, y3, x2, y2;

        theta2 -= theta1;
        if (theta2 < 0) {
            theta2 += Math.PI * 2;
        }
        result.push(xx + cx, xy + cy);
        while (theta2 >= rightAngle) {
            result.push(
                exx + eyx * rho + cx, exy + eyy * rho + cy,
                exx * rho + eyx + cx, exy * rho + eyy + cy,
                eyx + cx, eyy + cy
            );
            count += 6;
            theta2 -= rightAngle;
            temp = exx;
            exx = eyx;
            eyx = -temp;
            temp = exy;
            exy = eyy;
            eyy = -temp;
        }
        if (theta2) {
            y1 = (0.3294738052815987 + 0.012120855841304373 * theta2) * theta2;
            x3 = Math.cos(theta2);
            y3 = Math.sin(theta2);
            x2 = x3 + y1 * y3;
            y2 = y3 - y1 * x3;
            result.push(
                exx + eyx * y1 + cx, exy + eyy * y1 + cy,
                exx * x2 + eyx * y2 + cx, exy * x2 + eyy * y2 + cy,
                exx * x3 + eyx * y3 + cx, exy * x3 + eyy * y3 + cy
            );
            count += 6;
        }
        return count;
    },

    /**
     * [http://www.w3.org/TR/SVG/implnote.html#ArcImplementationNotes](http://www.w3.org/TR/SVG/implnote.html#ArcImplementationNotes)
     * @param {Number} rx
     * @param {Number} ry
     * @param {Number} rotation Differ from svg spec, this is radian.
     * @param {Number} fA
     * @param {Number} fS
     * @param {Number} x2
     * @param {Number} y2
     */
    arcSvg: function (rx, ry, rotation, fA, fS, x2, y2) {
        if (rx < 0) {
            rx = -rx;
        }
        if (ry < 0) {
            ry = -ry;
        }
        var me = this,
            x1 = me.cursor[0],
            y1 = me.cursor[1],
            hdx = (x1 - x2) / 2,
            hdy = (y1 - y2) / 2,
            cosPhi = Math.cos(rotation),
            sinPhi = Math.sin(rotation),
            xp = hdx * cosPhi + hdy * sinPhi,
            yp = -hdx * sinPhi + hdy * cosPhi,
            ratX = xp / rx,
            ratY = yp / ry,
            lambda = ratX * ratX + ratY * ratY,
            cx = (x1 + x2) * 0.5, cy = (y1 + y2) * 0.5,
            cpx = 0, cpy = 0;
        if (lambda >= 1) {
            lambda = Math.sqrt(lambda);
            rx *= lambda;
            ry *= lambda;
            // me gives lambda == cpx == cpy == 0;
        } else {
            lambda = Math.sqrt(1 / lambda - 1);
            if (fA === fS) {
                lambda = -lambda;
            }
            cpx = lambda * rx * ratY;
            cpy = -lambda * ry * ratX;
            cx += cosPhi * cpx - sinPhi * cpy;
            cy += sinPhi * cpx + cosPhi * cpy;
        }

        var theta1 = Math.atan2((yp - cpy) / ry, (xp - cpx) / rx),
            deltaTheta = Math.atan2((-yp - cpy) / ry, (-xp - cpx) / rx) - theta1;

        if (fS) {
            if (deltaTheta <= 0) {
                deltaTheta += Math.PI * 2;
            }
        } else {
            if (deltaTheta >= 0) {
                deltaTheta -= Math.PI * 2;
            }
        }
        me.ellipse(cx, cy, rx, ry, rotation, theta1, theta1 + deltaTheta, 1 - fS);
    },

    /**
     * Feed the path from svg path string.
     * @param {String} pathString
     */
    fromSvgString: function (pathString) {
        if (!pathString) {
            return;
        }
        var me = this,
            parts,
            paramCounts = {
                a: 7, c: 6, h: 1, l: 2, m: 2, q: 4, s: 4, t: 2, v: 1, z: 0,
                A: 7, C: 6, H: 1, L: 2, M: 2, Q: 4, S: 4, T: 2, V: 1, Z: 0
            },
            lastCommand = '',
            lastControlX, lastControlY,
            lastX = 0, lastY = 0,
            part = false, i, partLength, relative;

        // Split the string to items.
        if (Ext.isString(pathString)) {
            parts = pathString.replace(Ext.draw.Path.pathRe, " $1 ").replace(Ext.draw.Path.pathRe2, " -").split(Ext.draw.Path.pathSplitRe);
        } else if (Ext.isArray(pathString)) {
            parts = pathString.join(',').split(Ext.draw.Path.pathSplitRe);
        }

        // Remove empty entries
        for (i = 0, partLength = 0; i < parts.length; i++) {
            if (parts[i] !== '') {
                parts[partLength++] = parts[i];
            }
        }
        parts.length = partLength;

        me.clear();
        for (i = 0; i < parts.length;) {
            lastCommand = part;
            part = parts[i];
            relative = (part.toUpperCase() !== part);
            i++;
            switch (part) {
                case 'M':
                    me.moveTo(lastX = +parts[i], lastY = +parts[i + 1]);
                    i += 2;
                    while (i < partLength && !paramCounts.hasOwnProperty(parts[i])) {
                        me.lineTo(lastX = +parts[i], lastY = +parts[i + 1]);
                        i += 2;
                    }
                    break;
                case 'L':
                    me.lineTo(lastX = +parts[i], lastY = +parts[i + 1]);
                    i += 2;
                    while (i < partLength && !paramCounts.hasOwnProperty(parts[i])) {
                        me.lineTo(lastX = +parts[i], lastY = +parts[i + 1]);
                        i += 2;
                    }
                    break;
                case 'A':
                    while (i < partLength && !paramCounts.hasOwnProperty(parts[i])) {
                        me.arcSvg(
                            +parts[i], +parts[i + 1],
                            +parts[i + 2] * Math.PI / 180,
                            +parts[i + 3], +parts[i + 4],
                            lastX = +parts[i + 5], lastY = +parts[i + 6]);
                        i += 7;
                    }
                    break;
                case 'C':
                    while (i < partLength && !paramCounts.hasOwnProperty(parts[i])) {
                        me.bezierCurveTo(
                            +parts[i ], +parts[i + 1],
                            lastControlX = +parts[i + 2], lastControlY = +parts[i + 3],
                            lastX = +parts[i + 4], lastY = +parts[i + 5]);
                        i += 6;
                    }
                    break;
                case 'Z':
                    me.closePath();
                    break;
                case 'm':
                    me.moveTo(lastX += +parts[i], lastY += +parts[i + 1]);
                    i += 2;
                    while (i < partLength && !paramCounts.hasOwnProperty(parts[i])) {
                        me.lineTo(lastX += +parts[i], lastY += +parts[i + 1]);
                        i += 2;
                    }
                    break;
                case 'l':
                    me.lineTo(lastX += +parts[i], lastY += +parts[i + 1]);
                    i += 2;
                    while (i < partLength && !paramCounts.hasOwnProperty(parts[i])) {
                        me.lineTo(lastX += +parts[i], lastY += +parts[i + 1]);
                        i += 2;
                    }
                    break;
                case 'a':
                    while (i < partLength && !paramCounts.hasOwnProperty(parts[i])) {
                        me.arcSvg(
                            +parts[i], +parts[i + 1],
                            +parts[i + 2] * Math.PI / 180,
                            +parts[i + 3], +parts[i + 4],
                            lastX += +parts[i + 5], lastY += +parts[i + 6]);
                        i += 7;
                    }
                    break;
                case 'c':
                    while (i < partLength && !paramCounts.hasOwnProperty(parts[i])) {
                        me.bezierCurveTo(lastX + (+parts[i]), lastY + (+parts[i + 1]),
                            lastControlX = lastX + (+parts[i + 2]), lastControlY = lastY + (+parts[i + 3]),
                            lastX += +parts[i + 4], lastY += +parts[i + 5]);
                        i += 6;
                    }
                    break;
                case 'z':
                    me.closePath();
                    break;
                case 's':
                    if (!(lastCommand === 'c' || lastCommand === 'C' || lastCommand === 's' || lastCommand === 'S')) {
                        lastControlX = lastX;
                        lastControlY = lastY;
                    }

                    while (i < partLength && !paramCounts.hasOwnProperty(parts[i])) {
                        me.bezierCurveTo(
                            lastX + lastX - lastControlX, lastY + lastY - lastControlY,
                            lastControlX = lastX + (+parts[i]), lastControlY = lastY + (+parts[i + 1]),
                            lastX += +parts[i + 2], lastY += +parts[i + 3]);
                        i += 4;
                    }
                    break;
                case 'S':
                    if (!(lastCommand === 'c' || lastCommand === 'C' || lastCommand === 's' || lastCommand === 'S')) {
                        lastControlX = lastX;
                        lastControlY = lastY;
                    }
                    while (i < partLength && !paramCounts.hasOwnProperty(parts[i])) {
                        me.bezierCurveTo(
                            lastX + lastX - lastControlX, lastY + lastY - lastControlY,
                            lastControlX = +parts[i], lastControlY = +parts[i + 1],
                            lastX = (+parts[i + 2]), lastY = (+parts[i + 3]));
                        i += 4;
                    }
                    break;
                case 'q':
                    while (i < partLength && !paramCounts.hasOwnProperty(parts[i])) {
                        me.quadraticCurveTo(
                            lastControlX = lastX + (+parts[i]), lastControlY = lastY + (+parts[i + 1]),
                            lastX += +parts[i + 2], lastY += +parts[i + 3]);
                        i += 4;
                    }
                    break;
                case 'Q':
                    while (i < partLength && !paramCounts.hasOwnProperty(parts[i])) {
                        me.quadraticCurveTo(
                            lastControlX = +parts[i], lastControlY = +parts[i + 1],
                            lastX = +parts[i + 2], lastY = +parts[i + 3]);
                        i += 4;
                    }
                    break;
                case 't':
                    if (!(lastCommand === 'q' || lastCommand === 'Q' || lastCommand === 't' || lastCommand === 'T')) {
                        lastControlX = lastX;
                        lastControlY = lastY;
                    }
                    while (i < partLength && !paramCounts.hasOwnProperty(parts[i])) {
                        me.quadraticCurveTo(
                            lastControlX = lastX + lastX - lastControlX, lastControlY = lastY + lastY - lastControlY,
                            lastX += +parts[i + 1], lastY += +parts[i + 2]);
                        i += 2;
                    }
                    break;
                case 'T':
                    if (!(lastCommand === 'q' || lastCommand === 'Q' || lastCommand === 't' || lastCommand === 'T')) {
                        lastControlX = lastX;
                        lastControlY = lastY;
                    }
                    while (i < partLength && !paramCounts.hasOwnProperty(parts[i])) {
                        me.quadraticCurveTo(
                            lastControlX = lastX + lastX - lastControlX, lastControlY = lastY + lastY - lastControlY,
                            lastX = (+parts[i + 1]), lastY = (+parts[i + 2]));
                        i += 2;
                    }
                    break;
                case 'h':
                    while (i < partLength && !paramCounts.hasOwnProperty(parts[i])) {
                        me.lineTo(lastX += +parts[i], lastY);
                        i++;
                    }
                    break;
                case 'H':
                    while (i < partLength && !paramCounts.hasOwnProperty(parts[i])) {
                        me.lineTo(lastX = +parts[i], lastY);
                        i++;
                    }
                    break;
                case 'v':
                    while (i < partLength && !paramCounts.hasOwnProperty(parts[i])) {
                        me.lineTo(lastX, lastY += +parts[i]);
                        i++;
                    }
                    break;
                case 'V':
                    while (i < partLength && !paramCounts.hasOwnProperty(parts[i])) {
                        me.lineTo(lastX, lastY = +parts[i]);
                        i++;
                    }
                    break;
            }
        }
    },

    /**
     * Clone this path.
     * @return {Ext.draw.Path}
     */
    clone: function () {
        var me = this,
            path = new Ext.draw.Path();
        path.params = me.params.slice(0);
        path.commands = me.commands.slice(0);
        path.cursor = me.cursor ? me.cursor.slice(0) : null;
        path.startX = me.startX;
        path.startY = me.startY;
        path.svgString = me.svgString;
        return path;
    },

    /**
     * Transform the current path by a matrix.
     * @param {Ext.draw.Matrix} matrix
     */
    transform: function (matrix) {
        if (matrix.isIdentity()) {
            return;
        }
        var xx = matrix.getXX(), yx = matrix.getYX(), dx = matrix.getDX(),
            xy = matrix.getXY(), yy = matrix.getYY(), dy = matrix.getDY(),
            params = this.params,
            i = 0, ln = params.length,
            x, y;

        for (; i < ln; i += 2) {
            x = params[i];
            y = params[i + 1];
            params[i] = x * xx + y * yx + dx;
            params[i + 1] = x * xy + y * yy + dy;
        }
        this.dirt();
    },

    /**
     * Get the bounding box of this matrix.
     * @param {Object} [target] Optional object to receive the result.
     *
     * @return {Object} Object with x, y, width and height
     */
    getDimension: function (target) {
        if (!target) {
            target = {};
        }

        if (!this.commands || !this.commands.length) {
            target.x = 0;
            target.y = 0;
            target.width = 0;
            target.height = 0;
            return target;
        }

        target.left = Infinity;
        target.top = Infinity;
        target.right = -Infinity;
        target.bottom = -Infinity;
        var i = 0, j = 0,
            commands = this.commands,
            params = this.params,
            ln = commands.length, x, y;
        for (; i < ln; i++) {
            switch (commands[i]) {
                case 'M':
                case 'L':
                    x = params[j];
                    y = params[j + 1];
                    target.left = Math.min(x, target.left);
                    target.top = Math.min(y, target.top);
                    target.right = Math.max(x, target.right);
                    target.bottom = Math.max(y, target.bottom);
                    j += 2;
                    break;
                case 'C':
                    this.expandDimension(target, x, y,
                        params[j], params[j + 1],
                        params[j + 2], params[j + 3],
                        x = params[j + 4], y = params[j + 5]);
                    j += 6;
                    break;
            }
        }

        target.x = target.left;
        target.y = target.top;
        target.width = target.right - target.left;
        target.height = target.bottom - target.top;
        return target;
    },

    /**
     * Get the bounding box as if the path is transformed by a matrix.
     *
     * @param {Ext.draw.Matrix} matrix
     * @param {Object} [target] Optional object to receive the result.
     *
     * @return {Object} An object with x, y, width and height.
     */
    getDimensionWithTransform: function (matrix, target) {
        if (!this.commands || !this.commands.length) {
            if (!target) {
                target = {};
            }
            target.x = 0;
            target.y = 0;
            target.width = 0;
            target.height = 0;
            return target;
        }

        target.left = Infinity;
        target.top = Infinity;
        target.right = -Infinity;
        target.bottom = -Infinity;

        var xx = matrix.getXX(), yx = matrix.getYX(), dx = matrix.getDX(),
            xy = matrix.getXY(), yy = matrix.getYY(), dy = matrix.getDY(),
            i = 0, j = 0,
            commands = this.commands,
            params = this.params,
            ln = commands.length, x, y;
        for (; i < ln; i++) {
            switch (commands[i]) {
                case 'M':
                case 'L':
                    x = params[j] * xx + params[j + 1] * yx + dx;
                    y = params[j] * xy + params[j + 1] * yy + dy;
                    target.left = Math.min(x, target.left);
                    target.top = Math.min(y, target.top);
                    target.right = Math.max(x, target.right);
                    target.bottom = Math.max(y, target.bottom);
                    j += 2;
                    break;
                case 'C':
                    this.expandDimension(target,
                        x, y,
                        params[j] * xx + params[j + 1] * yx + dx,
                        params[j] * xy + params[j + 1] * yy + dy,
                        params[j + 2] * xx + params[j + 3] * yx + dx,
                        params[j + 2] * xy + params[j + 3] * yy + dy,
                        x = params[j + 4] * xx + params[j + 5] * yx + dx,
                        y = params[j + 4] * xy + params[j + 5] * yy + dy);
                    j += 6;
                    break;
            }
        }
        if (!target) {
            target = {};
        }
        target.x = target.left;
        target.y = target.top;
        target.width = target.right - target.left;
        target.height = target.bottom - target.top;
        return target;
    },

    /**
     * @private
     * Expand the rect by the bbox of a bezier curve.
     *
     * @param {Object} target
     * @param {Number} x1
     * @param {Number} y1
     * @param {Number} cx1
     * @param {Number} cy1
     * @param {Number} cx2
     * @param {Number} cy2
     * @param {Number} x2
     * @param {Number} y2
     */
    expandDimension: function (target, x1, y1, cx1, cy1, cx2, cy2, x2, y2) {
        var me = this,
            l = target.left, r = target.right, t = target.top, b = target.bottom,
            dim = me.dim || (me.dim = []);

        me.curveDimension(x1, cx1, cx2, x2, dim);
        l = Math.min(l, dim[0]);
        r = Math.max(r, dim[1]);

        me.curveDimension(y1, cy1, cy2, y2, dim);
        t = Math.min(t, dim[0]);
        b = Math.max(b, dim[1]);

        target.left = l;
        target.right = r;
        target.top = t;
        target.bottom = b;
    },

    /**
     * @private
     * Determine the curve
     * @param {Number} a
     * @param {Number} b
     * @param {Number} c
     * @param {Number} d
     * @param {Number} dim
     */
    curveDimension: function (a, b, c, d, dim) {
        var qa = 3 * (-a + 3 * (b - c) + d),
            qb = 6 * (a - 2 * b + c),
            qc = -3 * (a - b), x, y,
            min = Math.min(a, d),
            max = Math.max(a, d), delta;

        if (qa === 0) {
            if (qb === 0) {
                dim[0] = min;
                dim[1] = max;
                return;
            } else {
                x = -qc / qb;
                if (0 < x && x < 1) {
                    y = this.interpolate(a, b, c, d, x);
                    min = Math.min(min, y);
                    max = Math.max(max, y);
                }
            }
        } else {
            delta = qb * qb - 4 * qa * qc;
            if (delta >= 0) {
                delta = Math.sqrt(delta);
                x = (delta - qb) / 2 / qa;
                if (0 < x && x < 1) {
                    y = this.interpolate(a, b, c, d, x);
                    min = Math.min(min, y);
                    max = Math.max(max, y);
                }
                if (delta > 0) {
                    x -= delta / qa;
                    if (0 < x && x < 1) {
                        y = this.interpolate(a, b, c, d, x);
                        min = Math.min(min, y);
                        max = Math.max(max, y);
                    }
                }
            }
        }
        dim[0] = min;
        dim[1] = max;
    },

    /**
     * @private
     *
     * Returns `a * (1 - t) ^ 3 + 3 * b (1 - t) ^ 2 * t + 3 * c (1 - t) * t ^ 3 + d * t ^ 3`.
     *
     * @param {Number} a
     * @param {Number} b
     * @param {Number} c
     * @param {Number} d
     * @param {Number} t
     * @return {Number}
     */
    interpolate: function (a, b, c, d, t) {
        if (t === 0) {
            return a;
        }
        if (t === 1) {
            return d;
        }
        var rate = (1 - t) / t;
        return t * t * t * (d + rate * (3 * c + rate * (3 * b + rate * a)));
    },

    /**
     * Reconstruct path from cubic bezier curve stripes.
     * @param {Array} stripes
     */
    fromStripes: function (stripes) {
        var me = this,
            i = 0, ln = stripes.length,
            j, ln2, stripe;
        me.clear();
        for (; i < ln; i++) {
            stripe = stripes[i];
            me.params.push.apply(me.params, stripe);
            me.commands.push('M');
            for (j = 2, ln2 = stripe.length; j < ln2; j += 6) {
                me.commands.push('C');
            }
        }
        if (!me.cursor) {
            me.cursor = [];
        }
        me.cursor[0] = me.params[me.params.length - 2];
        me.cursor[1] = me.params[me.params.length - 1];
        me.dirt();
    },

    /**
     * Convert path to bezier curve stripes.
     * @param {Array} [target] The optional array to receive the result.
     * @return {Array}
     */
    toStripes: function (target) {
        var stripes = target || [], curr,
            x, y, lastX, lastY, startX, startY,
            i, j,
            commands = this.commands,
            params = this.params,
            ln = commands.length;
        for (i = 0, j = 0; i < ln; i++) {
            switch (commands[i]) {
                case 'M':
                    curr = [startX = lastX = params[j++], startY = lastY = params[j++]];
                    stripes.push(curr);
                    break;
                case 'L':
                    x = params[j++];
                    y = params[j++];
                    curr.push((lastX + lastX + x) / 3, (lastY + lastY + y) / 3, (lastX + x + x) / 3, (lastY + y + y) / 3, lastX = x, lastY = y);
                    break;
                case 'C':
                    curr.push(params[j++], params[j++], params[j++], params[j++], lastX = params[j++], lastY = params[j++]);
                    break;
                case 'Z':
                    x = startX;
                    y = startY;
                    curr.push((lastX + lastX + x) / 3, (lastY + lastY + y) / 3, (lastX + x + x) / 3, (lastY + y + y) / 3, lastX = x, lastY = y);
                    break;
            }
        }
        return stripes;
    },

    /**
     * @private
     * Update cache for svg string of this path.
     */
    updateSvgString: function () {
        var result = [],
            commands = this.commands,
            params = this.params,
            ln = commands.length,
            i = 0, j = 0;
        for (; i < ln; i++) {
            switch (commands[i]) {
                case 'M':
                    result.push('M' + params[j] + ',' + params[j + 1]);
                    j += 2;
                    break;
                case 'L':
                    result.push('L' + params[j] + ',' + params[j + 1]);
                    j += 2;
                    break;
                case 'C':
                    result.push('C' + params[j] + ',' + params[j + 1] + ' ' +
                        params[j + 2] + ',' + params[j + 3] + ' ' +
                        params[j + 4] + ',' + params[j + 5]);
                    j += 6;
                    break;
                case 'Z':
                    result.push('Z');
                    break;
            }
        }
        this.svgString = result.join('');
    },

    /**
     * Return an svg path string for this path.
     * @return {String}
     */
    toString: function () {
        if (!this.svgString) {
            this.updateSvgString();
        }
        return this.svgString;
    }
});

Ext.define('Ext.draw.overrides.Path', {
    override: 'Ext.draw.Path',

    // An arbitrary point outside the path used for hit testing with ray casting method.
    rayOrigin: {
        x: -10000,
        y: -10000
    },

    /**
     * Tests whether the given point is inside the path.
     * @param {Number} x
     * @param {Number} y
     * @return {Boolean}
     */
    isPointInPath: function (x, y) {
        var me = this,
            commands = me.commands,
            solver = Ext.draw.PathUtil,
            origin = me.rayOrigin,
            params = me.params,
            ln = commands.length,
            firstX = null,
            firstY = null,
            lastX = 0,
            lastY = 0,
            count = 0,
            i, j;

        for (i = 0, j = 0; i < ln; i++) {
            switch (commands[i]) {
                case 'M':
                    if (firstX !== null) {
                        if (solver.linesIntersection(firstX, firstY, lastX, lastY, origin.x, origin.y, x, y)) {
                            count += 1;
                        }
                    }
                    firstX = lastX = params[j];
                    firstY = lastY = params[j + 1];
                    j += 2;
                    break;
                case 'L':
                    if (solver.linesIntersection(lastX, lastY, params[j], params[j + 1], origin.x, origin.y, x, y)) {
                        count += 1;
                    }
                    lastX = params[j];
                    lastY = params[j + 1];
                    j += 2;
                    break;
                case 'C':
                    count += solver.cubicLineIntersections(
                        lastX, params[j], params[j + 2], params[j + 4],
                        lastY, params[j + 1], params[j + 3], params[j + 5],
                        origin.x, origin.y, x, y
                    ).length;
                    lastX = params[j + 4];
                    lastY = params[j + 5];
                    j += 6;
                    break;
                case 'Z':
                    if (firstX !== null) {
                        if (solver.linesIntersection(firstX, firstY, lastX, lastY, origin.x, origin.y, x, y)) {
                            count += 1;
                        }
                    }
                    break;
            }
        }
        return count % 2 === 1;
    },

    /**
     * Tests whether the given point is on the path.
     * @param {Number} x
     * @param {Number} y
     * @returns {Boolean}
     */
    isPointOnPath: function (x, y) {
        var me = this,
            commands = me.commands,
            solver = Ext.draw.PathUtil,
            params = me.params,
            ln = commands.length,
            firstX = null,
            firstY = null,
            lastX = 0,
            lastY = 0,
            i, j;

        for (i = 0, j = 0; i < ln; i++) {
            switch (commands[i]) {
                case 'M':
                    if (firstX !== null) {
                        if (solver.pointOnLine(firstX, firstY, lastX, lastY, x, y)) {
                            return true;
                        }
                    }
                    firstX = lastX = params[j];
                    firstY = lastY = params[j + 1];
                    j += 2;
                    break;
                case 'L':
                    if (solver.pointOnLine(lastX, lastY, params[j], params[j + 1], x, y)) {
                        return true;
                    }
                    lastX = params[j];
                    lastY = params[j + 1];
                    j += 2;
                    break;
                case 'C':
                    if (solver.pointOnCubic(
                        lastX, params[j], params[j + 2], params[j + 4],
                        lastY, params[j + 1], params[j + 3], params[j + 5], x, y)) {
                        return true;
                    }
                    lastX = params[j + 4];
                    lastY = params[j + 5];
                    j += 6;
                    break;
                case 'Z':
                    if (firstX !== null) {
                        if (solver.pointOnLine(firstX, firstY, lastX, lastY, x, y)) {
                            return true;
                        }
                    }
                    break;
            }
        }
        return false;
    },

    /**
     * Calculates the points where the given segment intersects the path.
     * If four parameters are given then the segment is considered to be a line segment,
     * where given parameters are the coordinates of the start and end points.
     * If eight parameters are given then the segment is considered to be
     * a cubic Bezier curve segment, where given parameters are the
     * coordinates of its edge points and control points.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param x3
     * @param y3
     * @param x4
     * @param y4
     * @returns {Array}
     */
    getSegmentIntersections: function (x1, y1, x2, y2, x3, y3, x4, y4) {
        var me = this,
            count = arguments.length,
            solver = Ext.draw.PathUtil,
            commands = me.commands,
            params = me.params,
            ln = commands.length,
            firstX = null,
            firstY = null,
            lastX = 0,
            lastY = 0,
            intersections = [],
            i, j, points;

        for (i = 0, j = 0; i < ln; i++) {
            switch (commands[i]) {
                case 'M':
                    if (firstX !== null) {
                        switch (count) {
                            case 4:
                                points = solver.linesIntersection(firstX, firstY, lastX, lastY, x1, y1, x2, y2);
                                if (points) {
                                    intersections.push(points);
                                }
                                break;
                            case 8:
                                points = solver.cubicLineIntersections(x1, x2, x3, x4, y1, y2, y3, y4,
                                    firstX, firstY, lastX, lastY);
                                intersections.push.apply(intersections, points);
                                break;
                        }
                    }
                    firstX = lastX = params[j];
                    firstY = lastY = params[j + 1];
                    j += 2;
                    break;
                case 'L':
                    switch (count) {
                        case 4:
                            points = solver.linesIntersection(lastX, lastY, params[j], params[j + 1], x1, y1, x2, y2);
                            if (points) {
                                intersections.push(points);
                            }
                            break;
                        case 8:
                            points = solver.cubicLineIntersections(x1, x2, x3, x4, y1, y2, y3, y4,
                                lastX, lastY, params[j], params[j + 1]);
                            intersections.push.apply(intersections, points);
                            break;
                    }
                    lastX = params[j];
                    lastY = params[j + 1];
                    j += 2;
                    break;
                case 'C':
                    switch (count) {
                        case 4:
                            points = solver.cubicLineIntersections(
                                lastX, params[j], params[j + 2], params[j + 4],
                                lastY, params[j + 1], params[j + 3], params[j + 5],
                                x1, y1, x2, y2);
                            intersections.push.apply(intersections, points);
                            break;
                        case 8:
                            points = solver.cubicsIntersections(
                                lastX, params[j], params[j + 2], params[j + 4],
                                lastY, params[j + 1], params[j + 3], params[j + 5],
                                x1, x2, x3, x4, y1, y2, y3, y4);
                            intersections.push.apply(intersections, points);
                            break;
                    }
                    lastX = params[j + 4];
                    lastY = params[j + 5];
                    j += 6;
                    break;
                case 'Z':
                    if (firstX !== null) {
                        switch (count) {
                            case 4:
                                points = solver.linesIntersection(firstX, firstY, lastX, lastY, x1, y1, x2, y2);
                                if (points) {
                                    intersections.push(points);
                                }
                                break;
                            case 8:
                                points = solver.cubicLineIntersections(x1, x2, x3, x4, y1, y2, y3, y4,
                                    firstX, firstY, lastX, lastY);
                                intersections.push.apply(intersections, points);
                                break;
                        }
                    }
                    break;
            }
        }
        return intersections;
    },

    getIntersections: function (path) {
        var me = this,
            commands = me.commands,
            params = me.params,
            ln = commands.length,
            firstX = null,
            firstY = null,
            lastX = 0,
            lastY = 0,
            intersections = [],
            i, j, points;

        for (i = 0, j = 0; i < ln; i++) {
            switch (commands[i]) {
                case 'M':
                    if (firstX !== null) {
                        points = path.getSegmentIntersections.call(path, firstX, firstY, lastX, lastY);
                        intersections.push.apply(intersections, points);
                    }
                    firstX = lastX = params[j];
                    firstY = lastY = params[j + 1];
                    j += 2;
                    break;
                case 'L':
                    points = path.getSegmentIntersections.call(path, lastX, lastY, params[j], params[j + 1]);
                    intersections.push.apply(intersections, points);
                    lastX = params[j];
                    lastY = params[j + 1];
                    j += 2;
                    break;
                case 'C':
                    points = path.getSegmentIntersections.call(path,
                        lastX, lastY, params[j], params[j + 1],
                        params[j + 2], params[j + 3], params[j + 4], params[j + 5]
                    );
                    intersections.push.apply(intersections, points);
                    lastX = params[j + 4];
                    lastY = params[j + 5];
                    j += 6;
                    break;
                case 'Z':
                    if (firstX !== null) {
                        points = path.getSegmentIntersections.call(path, firstX, firstY, lastX, lastY);
                        intersections.push.apply(intersections, points);
                    }
                    break;
            }
        }
        return intersections;
    }
});

/**
 * @class Ext.draw.sprite.Path
 * @extends Ext.draw.sprite.Sprite
 *
 * A sprite that represents a path.
 *
 *     @example
 *     Ext.create('Ext.Container', {
 *         renderTo: Ext.getBody(),
 *         width: 600,
 *         height: 400,
 *         layout: 'fit',
 *         items: {
 *             xtype: 'draw',
 *             sprites: [{
 *                 type: 'path',
 *                 path: 'M75,75 c0,-25 50,25 50,0 c0,-25 -50,25 -50,0',
 *                 fillStyle: 'blue'
 *             }]
 *         }
 *     });
 */
Ext.define('Ext.draw.sprite.Path', {
    extend: 'Ext.draw.sprite.Sprite',
    requires: [
        'Ext.draw.Draw',
        'Ext.draw.Path'
    ],
    alias: [
        'sprite.path',
        'Ext.draw.Sprite'
    ],
    type: 'path',
    isPath: true,
    statics: {
        /**
         * Debug rendering options:
         *
         * debug: {
         *     bbox: true, // renders the bounding box of the path
         *     xray: true  // renders control points of the path
         * }
         *
         */
        debug: false
    },
    inheritableStatics: {
        def: {
            processors: {
                /**
                 * @cfg {String} path The SVG based path string used by the sprite.
                 */
                path: function (n, o) {
                    if (!(n instanceof Ext.draw.Path)) {
                        n = new Ext.draw.Path(n);
                    }
                    return n;
                }
                ,debug: 'default'
            },
            aliases: {
                d: 'path'
            },
            dirtyTriggers: {
                path: 'bbox'
            },
            updaters: {
                path: function (attr) {
                    var path = attr.path;
                    if (!path || path.bindAttr !== attr) {
                        path = new Ext.draw.Path();
                        path.bindAttr = attr;
                        attr.path = path;
                    }
                    path.clear();
                    this.updatePath(path, attr);
                    attr.dirtyFlags.bbox = ['path'];
                }
            }
        }
    },

    updatePlainBBox: function (plain) {
        if (this.attr.path) {
            this.attr.path.getDimension(plain);
        }
    },

    updateTransformedBBox: function (transform) {
        if (this.attr.path) {
            this.attr.path.getDimensionWithTransform(this.attr.matrix, transform);
        }
    },

    render: function (surface, ctx) {
        var mat = this.attr.matrix,
            attr = this.attr;

        if (!attr.path || attr.path.params.length === 0) {
            return;
        }
        mat.toContext(ctx);
        ctx.appendPath(attr.path);
        ctx.fillStroke(attr);

        var debug = this.statics().debug || attr.debug;
        if (debug) {
            debug.bbox && this.renderBBox(surface, ctx);
            debug.xray && this.renderXRay(surface, ctx);
        }
    },

    renderBBox: function (surface, ctx) {
        var bbox = this.getBBox();
        ctx.beginPath();
        ctx.moveTo(bbox.x, bbox.y);
        ctx.lineTo(bbox.x + bbox.width, bbox.y);
        ctx.lineTo(bbox.x + bbox.width, bbox.y + bbox.height);
        ctx.lineTo(bbox.x, bbox.y + bbox.height);
        ctx.closePath();
        ctx.strokeStyle = 'red';
        ctx.strokeOpacity = 1;
        ctx.lineWidth = 0.5;
        ctx.stroke();
    },

    renderXRay: function (surface, ctx) {
        var attr = this.attr,
            mat = attr.matrix,
            imat = attr.inverseMatrix,
            path = attr.path,
            commands = path.commands,
            params = path.params,
            ln = commands.length,
            twoPi = Math.PI * 2,
            size = 2,
            i, j;

        mat.toContext(ctx);
        ctx.beginPath();
        for (i = 0, j = 0; i < ln; i++) {
            switch (commands[i]) {
                case 'M':
                    ctx.moveTo(params[j] - size, params[j + 1] - size);
                    ctx.rect(params[j] - size, params[j + 1] - size, size * 2, size * 2);
                    j += 2;
                    break;
                case 'L':
                    ctx.moveTo(params[j] - size, params[j + 1] - size);
                    ctx.rect(params[j] - size, params[j + 1] - size, size * 2, size * 2);
                    j += 2;
                    break;
                case 'C':
                    ctx.moveTo(params[j] + size, params[j + 1]);
                    ctx.arc(params[j], params[j + 1], size, 0, twoPi, true);
                    j += 2;
                    ctx.moveTo(params[j] + size, params[j + 1]);
                    ctx.arc(params[j], params[j + 1], size, 0, twoPi, true);
                    j += 2;
                    ctx.moveTo(params[j] + size * 2, params[j + 1]);
                    ctx.rect(params[j] - size, params[j + 1] - size, size * 2, size * 2);
                    j += 2;
                    break;
                default:
            }
        }
        imat.toContext(ctx);
        ctx.strokeStyle = 'black';
        ctx.strokeOpacity = 1;
        ctx.lineWidth = 1;
        ctx.stroke();

        mat.toContext(ctx);
        ctx.beginPath();
        for (i = 0, j = 0; i < ln; i++) {
            switch (commands[i]) {
                case 'M':
                    ctx.moveTo(params[j], params[j + 1]);
                    j += 2;
                    break;
                case 'L':
                    ctx.moveTo(params[j], params[j + 1]);
                    j += 2;
                    break;
                case 'C':
                    ctx.lineTo(params[j], params[j + 1]);
                    j += 2;
                    ctx.moveTo(params[j], params[j + 1]);
                    j += 2;
                    ctx.lineTo(params[j], params[j + 1]);
                    j += 2;
                    break;
                default:
            }
        }
        imat.toContext(ctx);
        ctx.lineWidth = 0.5;
        ctx.stroke();
    },

    /**
     * Update the path.
     * @param {Ext.draw.Path} path An empty path to draw on using path API.
     * @param {Object} attr The attribute object. Note: DO NOT use the `sprite.attr` instead of this
     * if you want to work with instancing.
     */
    updatePath: function (path, attr) {}
});

Ext.define('Ext.draw.overrides.sprite.Path', {
    override: 'Ext.draw.sprite.Path',
    requires: ['Ext.draw.Color'],

    /**
     * Tests whether the given point is inside the path.
     * @param x
     * @param y
     * @returns {Boolean}
     */
    isPointInPath: function (x, y) {
        var attr = this.attr;

        if (attr.fillStyle === Ext.draw.Color.RGBA_NONE) {
            return this.isPointOnPath(x, y);
        }

        var path = attr.path,
            matrix = attr.matrix,
            params, result;

        if (!matrix.isIdentity()) {
            params = path.params.slice(0);
            path.transform(attr.matrix);
        }

        result = path.isPointInPath(x, y);

        if (params) {
            path.params = params;
        }
        return result;
    },

    /**
     * Tests whether the given point is on the path.
     * @param x
     * @param y
     * @returns {Boolean}
     */
    isPointOnPath: function (x, y) {
        var attr = this.attr,
            path = attr.path,
            matrix = attr.matrix,
            params, result;

        if (!matrix.isIdentity()) {
            params = path.params.slice(0);
            path.transform(attr.matrix);
        }

        result = path.isPointOnPath(x, y);

        if (params) {
            path.params = params;
        }
        return result;
    },

    /**
     * Returns all points where this sprite intersects the given sprite.
     * The given sprite must be an instance of the {@link Ext.draw.sprite.Path} class
     * or its subclass.
     * @param path
     * @returns {Array}
     */
    getIntersections: function (path) {
        if (!(path.isSprite && path.isPath)) {
            return [];
        }
        var aAttr = this.attr,
            bAttr = path.attr,
            aPath = aAttr.path,
            bPath = bAttr.path,
            aMatrix = aAttr.matrix,
            bMatrix = bAttr.matrix,
            aParams, bParams,
            intersections;

        if (!aMatrix.isIdentity()) {
            aParams = aPath.params.slice(0);
            aPath.transform(aAttr.matrix);
        }
        if (!bMatrix.isIdentity()) {
            bParams = bPath.params.slice(0);
            bPath.transform(bAttr.matrix);
        }

        intersections = aPath.getIntersections(bPath);

        if (aParams) {
            aPath.params = aParams;
        }
        if (bParams) {
            bPath.params = bParams;
        }
        return intersections;
    }
});

/**
 * @class Ext.draw.sprite.Circle
 * @extends Ext.draw.sprite.Path
 *
 * A sprite that represents a circle.
 *
 *     @example
 *     Ext.create('Ext.Container', {
 *         renderTo: Ext.getBody(),
 *         width: 600,
 *         height: 400,
 *         layout: 'fit',
 *         items: {
 *             xtype: 'draw',
 *             sprites: [{
 *                  type: 'circle',
 *                  cx: 100,
 *                  cy: 100,
 *                  r: 25,
 *                  fillStyle: 'blue'
 *              }]
 *         }
 *     });
 */
Ext.define('Ext.draw.sprite.Circle', {
    extend: 'Ext.draw.sprite.Path',
    alias: 'sprite.circle',
    type: 'circle',
    inheritableStatics: {
        def: {
            processors: {
                /**
                 * @cfg {Number} [cx=0] The center coordinate of the sprite on the x-axis.
                 */
                cx: 'number',

                /**
                 * @cfg {Number} [cy=0] The center coordinate of the sprite on the y-axis.
                 */
                cy: 'number',

                /**
                 * @cfg {Number} [r=0] The radius of the sprite.
                 */
                r: 'number'
            },
            aliases: {
                radius: 'r',
                x: 'cx',
                y: 'cy',
                centerX: 'cx',
                centerY: 'cy'
            },
            defaults: {
                cx: 0,
                cy: 0,
                r: 4
            },
            dirtyTriggers: {
                cx: 'path',
                cy: 'path',
                r: 'path'
            }
        }
    },

    updatePlainBBox: function (plain) {
        var attr = this.attr,
            cx = attr.cx,
            cy = attr.cy,
            r = attr.r;
        plain.x = cx - r;
        plain.y = cy - r;
        plain.width = r + r;
        plain.height = r + r;
    },

    updateTransformedBBox: function (transform) {
        var attr = this.attr,
            cx = attr.cx,
            cy = attr.cy,
            r = attr.r,
            matrix = attr.matrix,
            scaleX = matrix.getScaleX(),
            scaleY = matrix.getScaleY(),
            w, h;
        w = scaleX * r;
        h = scaleY * r;
        transform.x = matrix.x(cx, cy) - w;
        transform.y = matrix.y(cx, cy) - h;
        transform.width = w + w;
        transform.height = h + h;
    },

    updatePath: function (path, attr) {
        path.arc(attr.cx, attr.cy, attr.r, 0, Math.PI * 2, false);
    }
});

/**
 * @class Ext.draw.sprite.Arc
 * @extend Ext.draw.sprite.Circle
 * 
 *  A sprite that represents a circular arc.
 *
 *     @example
 *     Ext.create('Ext.Container', {
 *         renderTo: Ext.getBody(),
 *         width: 600,
 *         height: 400,
 *         layout: 'fit',
 *         items: {
 *             xtype: 'draw',
 *             sprites: [{
 *                 type: 'arc',
 *                 cx: 100,
 *                 cy: 100,
 *                 r: 25,
 *                 fillStyle: 'blue',
 *                 startAngle: 0,
 *                 endAngle: Math.PI,
 *                 anticlockwise: true
 *             }]
 *         }
 *     });
 */
Ext.define('Ext.draw.sprite.Arc', {
    extend: 'Ext.draw.sprite.Circle',
    alias: 'sprite.arc',
    type: 'arc',
    inheritableStatics: {
        def: {
            processors: {
                /**
                 * @cfg {Number} [startAngle=0] The beginning angle of the arc.
                 */
                startAngle: 'number',

                /**
                 * @cfg {Number} [endAngle=Math.PI*2] The ending angle of the arc.
                 */
                endAngle: 'number',

                /**
                 * @cfg {Boolean} [anticlockwise=false] Determines whether or not the arc is drawn clockwise.
                 */
                anticlockwise: 'bool'
            },
            aliases: {
                from: 'startAngle',
                to: 'endAngle',
                start: 'startAngle',
                end: 'endAngle'
            },
            defaults: {
                startAngle: 0,
                endAngle: Math.PI * 2,
                anticlockwise: false
            },
            dirtyTriggers: {
                startAngle: 'path',
                endAngle: 'path',
                anticlockwise: 'path'
            }
        }
    },

    updatePath: function (path, attr) {
        path.arc(attr.cx, attr.cy, attr.r, attr.startAngle, attr.endAngle, attr.anticlockwise);
    }
});

Ext.define('Ext.draw.sprite.Arrow', {
    extend: 'Ext.draw.sprite.Path',
    alias: 'sprite.arrow',

    inheritableStatics: {
        def: {
            processors: {
                x: 'number',
                y: 'number',
                /**
                 * @cfg {Number} [size=4] The size of the sprite.
                 * Meant to be comparable to the size of a circle sprite with the same radius.
                 */
                size: 'number'
            },
            defaults: {
                x: 0,
                y: 0,
                size: 4
            },
            dirtyTriggers: {
                x: 'path',
                y: 'path',
                size: 'path'
            }
        }
    },

    updatePath: function (path, attr) {
        var s = attr.size * 1.5,
            x = attr.x - attr.lineWidth / 2,
            y = attr.y;
        path.fromSvgString('M'.concat(x - s * 0.7, ',', y - s * 0.4, 'l', [s * 0.6, 0, 0, -s * 0.4, s, s * 0.8, -s, s * 0.8, 0, -s * 0.4, -s * 0.6, 0], 'z'));
    }

});

/**
 * @class Ext.draw.sprite.Composite
 * @extends Ext.draw.sprite.Sprite
 * 
 * Represents a group of sprites.
 */
Ext.define('Ext.draw.sprite.Composite', {
    extend: 'Ext.draw.sprite.Sprite',
    alias: 'sprite.composite',
    type: 'composite',

    constructor: function () {
        this.callParent(arguments);
        this.sprites = [];
        this.sprites.map = {};
    },

    /**
     * Adds a sprite to the composite.
     * @param {Ext.draw.sprite.Sprite|Object} sprite
     */
    add: function (sprite) {
        if (!sprite.isSprite) {
            sprite = Ext.create('sprite.' + sprite.type, sprite);
            sprite.setParent(this);
            sprite.setSurface(this.getSurface());
        }
        var oldTransformations = sprite.applyTransformations,
            me = this,
            attr = me.attr;

        sprite.applyTransformations = function () {
            if (sprite.attr.dirtyTransform) {
                attr.dirtyTransform = true;
                attr.bbox.plain.dirty = true;
                attr.bbox.transform.dirty = true;
            }
            oldTransformations.call(sprite);
        };
        this.sprites.push(sprite);
        this.sprites.map[sprite.id] = sprite.getId();
        attr.bbox.plain.dirty = true;
        attr.bbox.transform.dirty = true;
        return sprite;
    },

    updateSurface: function (surface) {
        for (var i = 0, ln = this.sprites.length; i < ln; i++) {
            this.sprites[i].setSurface(surface);
        }
    },

    /**
     * Adds a list of sprites to the composite.
     * @param {Ext.draw.sprite.Sprite[]|Object[]|Ext.draw.sprite.Sprite|Object} sprites
     */
    addAll: function (sprites) {
        if (sprites.isSprite || sprites.type) {
            this.add(sprites);
        } else if (Ext.isArray(sprites)) {
            var i = 0;
            while (i < sprites.length) {
                this.add(sprites[i++]);
            }
        }
    },

    /**
     * Updates the bounding box of the composite, which contains the bounding box of all sprites in the composite.
     */
    updatePlainBBox: function (plain) {
        var me = this,
            left = Infinity,
            right = -Infinity,
            top = Infinity,
            bottom = -Infinity,
            sprite, bbox, i, ln;

        for (i = 0, ln = me.sprites.length; i < ln; i++) {
            sprite = me.sprites[i];
            sprite.applyTransformations();
            bbox = sprite.getBBox();
            if (left > bbox.x) {
                left = bbox.x;
            }
            if (right < bbox.x + bbox.width) {
                right = bbox.x + bbox.width;
            }
            if (top > bbox.y) {
                top = bbox.y;
            }
            if (bottom < bbox.y + bbox.height) {
                bottom = bbox.y + bbox.height;
            }
        }
        plain.x = left;
        plain.y = top;
        plain.width = right - left;
        plain.height = bottom - top;
    },

    /**
     * Renders all sprites contained in the composite to the surface.
     */
    render: function (surface, ctx, rect) {
        var mat = this.attr.matrix,
            i, ln;

        mat.toContext(ctx);
        for (i = 0, ln = this.sprites.length; i < ln; i++) {
            surface.renderSprite(this.sprites[i], rect);
        }
    }
});

Ext.define('Ext.draw.sprite.Cross', {
    extend: 'Ext.draw.sprite.Path',
    alias: 'sprite.cross',

    inheritableStatics: {
        def: {
            processors: {
                x: 'number',
                y: 'number',
                /**
                 * @cfg {Number} [size=4] The size of the sprite.
                 * Meant to be comparable to the size of a circle sprite with the same radius.
                 */
                size: 'number'
            },
            defaults: {
                x: 0,
                y: 0,
                size: 4
            },
            dirtyTriggers: {
                x: 'path',
                y: 'path',
                size: 'path'
            }
        }
    },

    updatePath: function (path, attr) {
        var s = attr.size / 1.7,
            x = attr.x - attr.lineWidth / 2,
            y = attr.y;
        path.fromSvgString('M'.concat(x - s, ',', y, 'l', [-s, -s, s, -s, s, s, s, -s, s, s, -s, s, s, s, -s, s, -s, -s, -s, s, -s, -s, 'z']));
    }

});

Ext.define('Ext.draw.sprite.Diamond', {
    extend: 'Ext.draw.sprite.Path',
    alias: 'sprite.diamond',

    inheritableStatics: {
        def: {
            processors: {
                x: 'number',
                y: 'number',
                /**
                 * @cfg {Number} [size=4] The size of the sprite.
                 * Meant to be comparable to the size of a circle sprite with the same radius.
                 */
                size: 'number'
            },
            defaults: {
                x: 0,
                y: 0,
                size: 4
            },
            dirtyTriggers: {
                x: 'path',
                y: 'path',
                size: 'path'
            }
        }
    },

    updatePath: function (path, attr) {
        var s = attr.size * 1.25,
            x = attr.x - attr.lineWidth / 2,
            y = attr.y;
        path.fromSvgString(['M', x, y - s, 'l', s, s, -s, s, -s, -s, s, -s, 'z']);
    }

});

/**
 * @class Ext.draw.sprite.Ellipse
 * @extends Ext.draw.sprite.Path
 * 
 * A sprite that represents an ellipse.
 *
 *     @example
 *     Ext.create('Ext.Container', {
 *         renderTo: Ext.getBody(),
 *         width: 600,
 *         height: 400,
 *         layout: 'fit',
 *         items: {
 *             xtype: 'draw',
 *             sprites: [{
 *                 type: 'ellipse',
 *                 cx: 100,
 *                 cy: 100,
 *                 rx: 40,
 *                 ry: 25,
 *                 fillStyle: 'blue'
 *             }]
 *         }
 *     });
 */
Ext.define("Ext.draw.sprite.Ellipse", {
    extend: "Ext.draw.sprite.Path",
    alias: 'sprite.ellipse',
    type: 'circle',
    inheritableStatics: {
        def: {
            processors: {
                /**
                 * @cfg {Number} [cx=0] The center coordinate of the sprite on the x-axis.
                 */
                cx: "number",
                
                /**
                 * @cfg {Number} [cy=0] The center coordinate of the sprite on the y-axis.
                 */
                cy: "number",
                
                /**
                 * @cfg {Number} [rx=1] The radius of the sprite on the x-axis.
                 */
                rx: "number",

                /**
                 * @cfg {Number} [ry=1] The radius of the sprite on the y-axis.
                 */
                ry: "number",

                /**
                 * @cfg {Number} [axisRotation=0] The rotation of the sprite about its axis.
                 */
                axisRotation: "number"
            },
            aliases: {
                radius: "r",
                x: "cx",
                y: "cy",
                centerX: "cx",
                centerY: "cy",
                radiusX: "rx",
                radiusY: "ry"
            },
            defaults: {
                cx: 0,
                cy: 0,
                rx: 1,
                ry: 1,
                axisRotation: 0
            },
            dirtyTriggers: {
                cx: 'path',
                cy: 'path',
                rx: 'path',
                ry: 'path',
                axisRotation: 'path'
            }
        }
    },

    updatePlainBBox: function (plain) {
        var attr = this.attr,
            cx = attr.cx,
            cy = attr.cy,
            rx = attr.rx,
            ry = attr.ry;
        plain.x = cx - rx;
        plain.y = cy - ry;
        plain.width = rx + rx;
        plain.height = ry + ry;
    },

    updateTransformedBBox: function (transform) {
        var attr = this.attr,
            cx = attr.cx,
            cy = attr.cy,
            rx = attr.rx,
            ry = attr.ry,
            rxy = ry / rx,
            matrix = attr.matrix.clone(),
            xx, xy, yx, yy, dx, dy, w, h;
        matrix.append(1, 0, 0, rxy, 0, cy * (1 - rxy));
        xx = matrix.getXX();
        yx = matrix.getYX();
        dx = matrix.getDX();
        xy = matrix.getXY();
        yy = matrix.getYY();
        dy = matrix.getDY();
        w = Math.sqrt(xx * xx + yx * yx) * rx;
        h = Math.sqrt(xy * xy + yy * yy) * rx;
        transform.x = cx * xx + cy * yx + dx - w;
        transform.y = cx * xy + cy * yy + dy - h;
        transform.width = w + w;
        transform.height = h + h;
    },

    updatePath: function (path, attr) {
        path.ellipse(attr.cx, attr.cy, attr.rx, attr.ry, attr.axisRotation, 0, Math.PI * 2, false);
    }
});

/**
 * @class Ext.draw.sprite.EllipticalArc
 * @extends Ext.draw.sprite.Ellipse
 *
 * A sprite that represents an elliptical arc.
 *
 *     @example
 *     Ext.create('Ext.Container', {
 *         renderTo: Ext.getBody(),
 *         width: 600,
 *         height: 400,
 *         layout: 'fit',
 *         items: {
 *             xtype: 'draw',
 *             sprites: [{
 *                 type: 'ellipticalArc',
 *                 cx: 100,
 *                 cy: 100,
 *                 rx: 40,
 *                 ry: 25,
 *                 fillStyle: 'blue',
 *                 startAngle: 0,
 *                 endAngle: Math.PI,
 *                 anticlockwise: true
 *             }]
 *         }
 *     });
 */
Ext.define("Ext.draw.sprite.EllipticalArc", {
    extend: "Ext.draw.sprite.Ellipse",
    alias: 'sprite.ellipticalArc',
    type: 'ellipticalArc',
    inheritableStatics: {
        def: {
            processors: {
                /**
                 * @cfg {Number} [startAngle=0] The beginning angle of the arc.
                 */
                startAngle: "number",

                /**
                 * @cfg {Number} [endAngle=Math.PI*2] The ending angle of the arc.
                 */
                endAngle: "number",

                /**
                 * @cfg {Boolean} [anticlockwise=false] Determines whether or not the arc is drawn clockwise.
                 */
                anticlockwise: "bool"
            },
            aliases: {
                from: "startAngle",
                to: "endAngle",
                start: "startAngle",
                end: "endAngle"
            },
            defaults: {
                startAngle: 0,
                endAngle: Math.PI * 2,
                anticlockwise: false
            },
            dirtyTriggers: {
                startAngle: 'path',
                endAngle: 'path',
                anticlockwise: 'path'
            }
        }
    },

    updatePath: function (path, attr) {
        path.ellipse(attr.cx, attr.cy, attr.rx, attr.ry, attr.axisRotation, attr.startAngle, attr.endAngle, attr.anticlockwise);
    }
});

/**
 * @class Ext.draw.sprite.Rect
 * @extends Ext.draw.sprite.Path
 *
 * A sprite that represents a rectangle.
 *
 *     @example
 *     Ext.create('Ext.Container', {
 *         renderTo: Ext.getBody(),
 *         width: 600,
 *         height: 400,
 *         layout: 'fit',
 *         items: {
 *             xtype: 'draw',
 *             sprites: [{
 *                 type: 'rect',
 *                 x: 50,
 *                 y: 50,
 *                 width: 50,
 *                 height: 50,
 *                 fillStyle: 'blue'
 *             }]
 *         }
 *     });
 */
Ext.define('Ext.draw.sprite.Rect', {
    extend: 'Ext.draw.sprite.Path',
    alias: 'sprite.rect',
    type: 'rect',
    inheritableStatics: {
        def: {
            processors: {
                /**
                 * @cfg {Number} [x=0] The position of the sprite on the x-axis.
                 */
                x: 'number',

                /**
                 * @cfg {Number} [y=0] The position of the sprite on the y-axis.
                 */
                y: 'number',

                /**
                 * @cfg {Number} [width=1] The width of the sprite.
                 */
                width: 'number',

                /**
                 * @cfg {Number} [height=1] The height of the sprite.
                 */
                height: 'number',

                /**
                 * @cfg {Number} [radius=0] The radius of the rounded corners.
                 */
                radius: 'number'
            },
            aliases: {

            },
            dirtyTriggers: {
                x: 'path',
                y: 'path',
                width: 'path',
                height: 'path',
                radius: 'path'
            },
            defaults: {
                x: 0,
                y: 0,
                width: 1,
                height: 1,
                radius: 0
            }
        }
    },

    updatePlainBBox: function (plain) {
        var attr = this.attr;
        plain.x = attr.x;
        plain.y = attr.y;
        plain.width = attr.width;
        plain.height = attr.height;
    },

    updateTransformedBBox: function (transform, plain) {
        this.attr.matrix.transformBBox(plain, this.attr.radius, transform);
    },

    updatePath: function (path, attr) {
        var x = attr.x,
            y = attr.y,
            width = attr.width,
            height = attr.height,
            radius = Math.min(attr.radius, Math.abs(attr.height) * 0.5, Math.abs(attr.width) * 0.5);
        if (radius === 0) {
            path.rect(x, y, width, height);
        } else {
            path.moveTo(x + radius, y);
            path.arcTo(x + width, y, x + width, y + height, radius);
            path.arcTo(x + width, y + height, x, y + height, radius);
            path.arcTo(x, y + height, x, y, radius);
            path.arcTo(x, y, x + radius, y, radius);
        }
    }
});

/**
 * @class Ext.draw.sprite.Image
 * @extends Ext.draw.sprite.Rect
 * 
 * A sprite that represents an image.
 */
Ext.define("Ext.draw.sprite.Image", {
    extend: "Ext.draw.sprite.Rect",
    alias: 'sprite.image',
    type: 'image',
    statics: {
        imageLoaders: {}
    },

    inheritableStatics: {
        def: {
            processors: {
                /**
                 * @cfg {String} [src=''] The image source of the sprite.
                 */
                src: 'string'
            },
            defaults: {
                src: '',
                /**
                 * @cfg {Number} [width=null] The width of the image.
                 * For consistent image size on all devices the width must be explicitly set.
                 * Otherwise the natural image width devided by the device pixel ratio
                 * (for a crisp looking image) will be used as the width of the sprite.
                 */
                width: null,
                /**
                 * @cfg {Number} [height=null] The height of the image.
                 * For consistent image size on all devices the height must be explicitly set.
                 * Otherwise the natural image height devided by the device pixel ratio
                 * (for a crisp looking image) will be used as the height of the sprite.
                 */
                height: null
            }
        }
    },

    render: function (surface, ctx) {
        var me = this,
            attr = me.attr,
            mat = attr.matrix,
            src = attr.src,
            x = attr.x,
            y = attr.y,
            width = attr.width,
            height = attr.height,
            loadingStub = Ext.draw.sprite.Image.imageLoaders[src],
            imageLoader,
            image,
            i;

        if (loadingStub && loadingStub.done) {
            mat.toContext(ctx);
            image = loadingStub.image;
            ctx.drawImage(image, x, y,
                    width || (image.naturalWidth || image.width) / surface.devicePixelRatio,
                    height || (image.naturalHeight || image.height) / surface.devicePixelRatio);
        } else if (!loadingStub) {
            imageLoader = new Image();
            loadingStub = Ext.draw.sprite.Image.imageLoaders[src] = {
                image: imageLoader,
                done: false,
                pendingSprites: [me],
                pendingSurfaces: [surface]
            };
            imageLoader.width = width;
            imageLoader.height = height;
            imageLoader.onload = function () {
                if (!loadingStub.done) {
                    loadingStub.done = true;
                    for (i = 0; i < loadingStub.pendingSprites.length; i++) {
                        loadingStub.pendingSprites[i].setDirty(true);
                    }
                    for (i in loadingStub.pendingSurfaces) {
                        loadingStub.pendingSurfaces[i].renderFrame();
                    }
                }
            };
            imageLoader.src = src;
        } else {
            Ext.Array.include(loadingStub.pendingSprites, me);
            Ext.Array.include(loadingStub.pendingSurfaces, surface);
        }
    }
});

/**
 * @class Ext.draw.sprite.Instancing
 * @extends Ext.draw.sprite.Sprite
 *
 * Sprite that represents multiple instances based on the given template.
 */
Ext.define('Ext.draw.sprite.Instancing', {
    extend: 'Ext.draw.sprite.Sprite',
    alias: 'sprite.instancing',
    type: 'instancing',
    isInstancing: true,
    config: {
        
        /**
         * @cfg {Object} [template=null] The sprite template used by all instances.
         */
        template: null
    },
    instances: null,

    applyTemplate: function (template) {
        if (!Ext.isObject(template)) {
            Ext.Error.raise("A template of an instancing sprite must either be a sprite instance " +
                " or a valid config object from which a template sprite will be created.");
        } else if (template.isInstancing) {
            Ext.Error.raise("Can't use an instancing sprite as a template for an instancing sprite.");
        }
        if (!template.isSprite) {
            if (!template.xclass && !template.type) {
                // For compatibility with ExtJS
                template.type = 'circle';
            }
            template = Ext.create(template.xclass || 'sprite.' + template.type, template);
        }
        template.setParent(this);
        return template;
    },

    updateTemplate: function (template, oldTemplate) {
        if (oldTemplate) {
            delete oldTemplate.ownAttr;
        }
        template.setSurface(this.getSurface());
        // ownAttr is used to get a reference to the template's attributes
        // when one of the instances is rendering, as at that moment the template's
        // attributes (template.attr) are the instance's attributes.
        template.ownAttr = template.attr;
        template.attr.children = this.instances = [];
        this.position = 0;
    },

    updateSurface: function (surface) {
        var template = this.getTemplate();
        if (template) {
            template.setSurface(surface);
        }
    },

    get: function (index) {
        return this.instances[index];
    },

    getCount: function () {
        return this.instances.length;
    },

    clearAll: function () {
        this.instances.length = 0;
        this.position = 0;
    },

    /**
     * Creates a new sprite instance.
     * 
     * @param {Object} config The configuration of the instance.
     * @param {Boolean} [bypassNormalization] 'true' to bypass attribute normalization.
     * @param {Boolean} [avoidCopy] 'true' to avoid copying the `config` object.
     * @return {Object} The attributes of the instance.
     */
    createInstance: function (config, bypassNormalization, avoidCopy) {
        var template = this.getTemplate(),
            originalAttr = template.attr,
            attr = Ext.Object.chain(originalAttr);
        template.topModifier.prepareAttributes(attr);
        template.attr = attr;
        template.setAttributes(config, bypassNormalization, avoidCopy);
        attr.template = template;
        this.instances.push(attr);
        template.attr = originalAttr;
        this.position++;
        return attr;
    },

    /**
     * Not supported.
     * 
     * @return {null}
     */
    getBBox: function () { return null; },

    /**
     * Returns the bounding box for the instance at the given index.
     *
     * @param {Number} index The index of the instance.
     * @param {Boolean} [isWithoutTransform] 'true' to not apply sprite transforms to the bounding box.
     * @return {Object} The bounding box for the instance.
     */
    getBBoxFor: function (index, isWithoutTransform) {
        var template = this.getTemplate(),
            originalAttr = template.attr,
            bbox;
        template.attr = this.instances[index];
        bbox = template.getBBox(isWithoutTransform);
        template.attr = originalAttr;
        return bbox;
    },

    render: function (surface, ctx, clipRect, rect) {
        if (!this.getTemplate()) {
            Ext.Error.raise('An instancing sprite must have a template.');
        }
        var me = this,
            template = me.getTemplate(),
            mat = me.attr.matrix,
            originalAttr = template.attr,
            instances = me.instances,
            i, ln = me.position;

        mat.toContext(ctx);
        template.preRender(surface, ctx, clipRect, rect);
        template.useAttributes(ctx, rect);
        for (i = 0; i < ln; i++) {
            if (instances[i].dirtyZIndex) {
                break;
            }
        }
        for (i = 0; i < ln; i++) {
            if (instances[i].hidden) {
                continue;
            }
            ctx.save();
            template.attr = instances[i];
            template.useAttributes(ctx, rect);
            template.render(surface, ctx, clipRect, rect);
            ctx.restore();
        }
        template.attr = originalAttr;
    },

    /**
     * Sets the attributes for the instance at the given index.
     * 
     * @param {Number} index the index of the instance
     * @param {Object} changes the attributes to change
     * @param {Boolean} [bypassNormalization] 'true' to avoid attribute normalization
     */
    setAttributesFor: function (index, changes, bypassNormalization) {
        var template = this.getTemplate(),
            originalAttr = template.attr,
            attr = this.instances[index];
        if (!attr) {
            return;
        }
        template.attr = attr;
        if (bypassNormalization) {
            changes = Ext.apply({}, changes);
        } else {
            changes = template.self.def.normalize(changes);
        }
        template.topModifier.pushDown(attr, changes);
        template.updateDirtyFlags(attr);
        template.attr = originalAttr;
    },

    destroy: function () {
        this.callParent();
        this.instances.length = 0;
        this.instances = null;
        if (this.getTemplate()) {
            this.getTemplate().destroy();
        }
    }
});

Ext.define('Ext.draw.sprite.Line', {
    extend: 'Ext.draw.sprite.Sprite',
    alias: 'sprite.line',
    type: 'line',

    inheritableStatics: {
        def: {
            processors: {
                fromX: 'number',
                fromY: 'number',
                toX: 'number',
                toY: 'number'
            },

            defaults: {
                fromX: 0,
                fromY: 0,
                toX: 1,
                toY: 1,
                strokeStyle: 'black'
            }
        }
    },

    updatePlainBBox: function (plain) {
        var attr = this.attr,
            fromX = Math.min(attr.fromX, attr.toX),
            fromY = Math.min(attr.fromY, attr.toY),
            toX = Math.max(attr.fromX, attr.toX),
            toY = Math.max(attr.fromY, attr.toY);
        plain.x = fromX;
        plain.y = fromY;
        plain.width = toX - fromX;
        plain.height = toY - fromY;
    },

    render: function (surface, ctx) {
        var attr = this.attr,
            matrix = this.attr.matrix;

        matrix.toContext(ctx);

        ctx.beginPath();
        ctx.moveTo(attr.fromX, attr.fromY);
        ctx.lineTo(attr.toX, attr.toY);
        ctx.stroke();
    }
});

Ext.define('Ext.draw.sprite.Plus', {
    extend: 'Ext.draw.sprite.Path',
    alias: 'sprite.plus',

    inheritableStatics: {
        def: {
            processors: {
                x: 'number',
                y: 'number',
                /**
                 * @cfg {Number} [size=4] The size of the sprite.
                 * Meant to be comparable to the size of a circle sprite with the same radius.
                 */
                size: 'number'
            },
            defaults: {
                x: 0,
                y: 0,
                size: 4
            },
            dirtyTriggers: {
                x: 'path',
                y: 'path',
                size: 'path'
            }
        }
    },

    updatePath: function (path, attr) {
        var s = attr.size / 1.3,
            x = attr.x - attr.lineWidth / 2,
            y = attr.y;
        path.fromSvgString('M'.concat(x - s / 2, ',', y - s / 2, 'l', [0, -s, s, 0, 0, s, s, 0, 0, s, -s, 0, 0, s, -s, 0, 0, -s, -s, 0, 0, -s, 'z']));
    }

});

/**
 * @class Ext.draw.sprite.Sector
 * @extends Ext.draw.sprite.Path
 * 
 * A sprite representing a pie slice.
 */
Ext.define('Ext.draw.sprite.Sector', {
    extend: 'Ext.draw.sprite.Path',
    alias: 'sprite.sector',
    type: 'sector',
    inheritableStatics: {
        def: {
            processors: {
                /**
                 * @cfg {Number} [centerX=0] The center coordinate of the sprite on the x-axis.
                 */
                centerX: 'number',

                /**
                 * @cfg {Number} [centerY=0] The center coordinate of the sprite on the y-axis.
                 */
                centerY: 'number',

                /**
                 * @cfg {Number} [startAngle=0] The starting angle of the sprite.
                 */
                startAngle: 'number',

                /**
                 * @cfg {Number} [endAngle=0] The ending angle of the sprite.
                 */
                endAngle: 'number',

                /**
                 * @cfg {Number} [startRho=0] The starting point of the radius of the sprite.
                 */
                startRho: 'number',

                /**
                 * @cfg {Number} [endRho=150] The ending point of the radius of the sprite.
                 */
                endRho: 'number',

                /**
                 * @cfg {Number} [margin=0] The margin of the sprite from the center of pie.
                 */
                margin: 'number'
            },
            aliases: {
                rho: 'endRho'
            },
            dirtyTriggers: {
                centerX: 'path,bbox',
                centerY: 'path,bbox',
                startAngle: 'path,bbox',
                endAngle: 'path,bbox',
                startRho: 'path,bbox',
                endRho: 'path,bbox',
                margin: 'path,bbox'
            },
            defaults: {
                centerX: 0,
                centerY: 0,
                startAngle: 0,
                endAngle: 0,
                startRho: 0,
                endRho: 150,
                margin: 0,
                path: 'M 0,0'
            }
        }
    },

    getMidAngle: function () {
        return this.midAngle || 0;
    },

    updatePath: function (path, attr) {
        var startAngle = Math.min(attr.startAngle, attr.endAngle),
            endAngle = Math.max(attr.startAngle, attr.endAngle),
            midAngle = this.midAngle = (startAngle + endAngle) * 0.5,
            margin = attr.margin,
            centerX = attr.centerX,
            centerY = attr.centerY,
            startRho = Math.min(attr.startRho, attr.endRho),
            endRho = Math.max(attr.startRho, attr.endRho);

        if (margin) {
            centerX += margin * Math.cos(midAngle);
            centerY += margin * Math.sin(midAngle);
        }
        path.moveTo(centerX + startRho * Math.cos(startAngle), centerY + startRho * Math.sin(startAngle));
        path.lineTo(centerX + endRho * Math.cos(startAngle), centerY + endRho * Math.sin(startAngle));
        path.arc(centerX, centerY, endRho, startAngle, endAngle, false);
        path.lineTo(centerX + startRho * Math.cos(endAngle), centerY + startRho * Math.sin(endAngle));
        path.arc(centerX, centerY, startRho, endAngle, startAngle, true);
    }
});

Ext.define('Ext.draw.sprite.Square', {
    extend: 'Ext.draw.sprite.Rect',
    alias: 'sprite.square',

    inheritableStatics: {
        def: {
            processors: {
                /**
                 * @cfg {Number} [size=4] The size of the sprite.
                 * Meant to be comparable to the size of a circle sprite with the same radius.
                 */
                size: 'number'
            },
            defaults: {
                size: 4
            },
            dirtyTriggers: {
                size: 'size'
            },
            updaters: {
                size: function (attr) {
                    var size = attr.size,
                        halfLineWidth = attr.lineWidth / 2;
                    this.setAttributes({
                        x: attr.x - size - halfLineWidth,
                        y: attr.y - size,
                        height: 2 * size,
                        width: 2 * size
                    });
                }
            }
        }
    }

});

/**
 * Utility class to provide a way to *approximately* measure the dimension of text
 * without a drawing context.
 */
Ext.define('Ext.draw.TextMeasurer', {
    singleton: true,

    requires: ['Ext.util.TextMetrics'],

    measureDiv: null,
    measureCache: {},

    /**
     * @cfg {Boolean} [precise=false]
     * This singleton tries not to make use of the Ext.util.TextMetrics because it is
     * several times slower than TextMeasurer's own solution. TextMetrics is more precise
     * though, so if you have a case where the error is too big, you may want to set
     * this config to `true` to get perfect results at the expense of performance.
     * Note: defaults to `true` in IE8.
     */
    precise: Ext.isIE8,

    measureDivTpl: {
        tag: 'div',
        overflow: 'hidden',
        position: 'relative',
        // tell the spec runner to ignore this element when checking if the dom is clean
        'data-sticky': true,
        'float': 'left', // 'float' is a reserved word. Don't unquote, or it will break the CMD build.
        width: 0,
        height: 0,
        children: {
            tag: 'div',
            display: 'block',
            position: 'absolute',
            x: -100000,
            y: -100000,
            padding: 0,
            margin: 0,
            'z-index': -100000,
            'white-space': 'nowrap'
        }
    },

    /**
     * @private Measure the size of a text with specific font by using DOM to measure it.
     * Could be very expensive therefore should be used lazily.
     * @param {String} text
     * @param {String} font
     * @return {Object} An object with `width` and `height` properties.
     * @return {Number} return.width
     * @return {Number} return.height
     */
    actualMeasureText: function (text, font) {
        var me = Ext.draw.TextMeasurer,
            measureDiv = me.measureDiv,
            FARAWAY = 100000,
            size;

        if (!measureDiv) {
            var parent = Ext.Element.create({
                style: {
                    "overflow": "hidden",
                    "position": "relative",
                    "float": "left", // DO NOT REMOVE THE QUOTE OR IT WILL BREAK COMPRESSOR
                    "width": 0,
                    "height": 0
                }
            });
            me.measureDiv = measureDiv = Ext.Element.create({});
            measureDiv.setStyle({
                "position": 'absolute',
                "x": FARAWAY,
                "y": FARAWAY,
                "z-index": -FARAWAY,
                "white-space": "nowrap",
                "display": 'block',
                "padding": 0,
                "margin": 0
            });
            Ext.getBody().appendChild(parent);
            parent.appendChild(measureDiv);
        }
        if (font) {
            measureDiv.setStyle({
                font: font,
                lineHeight: 'normal'
            });
        }
        measureDiv.setText('(' + text + ')');
        size = measureDiv.getSize();
        measureDiv.setText('()');
        size.width -= measureDiv.getSize().width;
        return size;
    },

    /**
     * Measure a single-line text with specific font.
     * This will split the text into characters and add up their size.
     * That may *not* be the exact size of the text as it is displayed.
     * @param {String} text
     * @param {String} font
     * @return {Object} An object with `width` and `height` properties.
     * @return {Number} return.width
     * @return {Number} return.height
     */
    measureTextSingleLine: function (text, font) {
        if (this.precise) {
            return this.preciseMeasureTextSingleLine(text, font);
        }
        text = text.toString();
        var cache = this.measureCache,
            chars = text.split(''),
            width = 0,
            height = 0,
            cachedItem, charactor, i, ln, size;

        if (!cache[font]) {
            cache[font] = {};
        }
        cache = cache[font];

        if (cache[text]) {
            return cache[text];
        }

        for (i = 0, ln = chars.length; i < ln; i++) {
            charactor = chars[i];
            if (!(cachedItem = cache[charactor])) {
                size = this.actualMeasureText(charactor, font);
                cachedItem = cache[charactor] = size;
            }
            width += cachedItem.width;
            height = Math.max(height, cachedItem.height);
        }
        return cache[text] = {
            width: width,
            height: height
        };
    },

    // A more precise but slower version of the measureTextSingleLine method.
    preciseMeasureTextSingleLine: function (text, font) {
        text = text.toString();
        var measureDiv = this.measureDiv ||
            (this.measureDiv = Ext.getBody().createChild(this.measureDivTpl).down('div'));
        measureDiv.setStyle({font: font || ''});
        return Ext.util.TextMetrics.measure(measureDiv, text);
    },

    /**
     * Measure a text with specific font.
     * This will split the text to lines and add up their size.
     * That may *not* be the exact size of the text as it is displayed.
     * @param {String} text
     * @param {String} font
     * @return {Object} An object with `width`, `height` and `sizes` properties.
     * @return {Number} return.width
     * @return {Number} return.height
     * @return {Object} return.sizes Results of individual line measurements, in case of multiline text.
     */
    measureText: function (text, font) {
        var lines = text.split('\n'),
            ln = lines.length,
            height = 0,
            width = 0,
            line, i,
            sizes;

        if (ln === 1) {
            return this.measureTextSingleLine(text, font);
        }

        sizes = [];
        for (i = 0; i < ln; i++) {
            line = this.measureTextSingleLine(lines[i], font);
            sizes.push(line);
            height += line.height;
            width = Math.max(width, line.width);
        }

        return {
            width: width,
            height: height,
            sizes: sizes
        };
    }
});

/**
 * @class Ext.draw.sprite.Text
 * @extends Ext.draw.sprite.Sprite
 *
 * A sprite that represents text.
 *
 *     @example
 *     Ext.create('Ext.Container', {
 *         renderTo: Ext.getBody(),
 *         width: 600,
 *         height: 400,
 *         layout: 'fit',
 *         items: {
 *             xtype: 'draw',
 *             sprites: [{
 *                 type: 'text',
 *                 x: 50,
 *                 y: 50,
 *                 text: 'Sencha',
 *                 fontSize: 18,
 *                 fillStyle: 'blue'
 *             }]
 *         }
 *     });
 */
Ext.define('Ext.draw.sprite.Text', {
    extend: 'Ext.draw.sprite.Sprite',
    requires: [
        'Ext.draw.TextMeasurer',
        'Ext.draw.Color'
    ],
    alias: 'sprite.text',
    type: 'text',
    lineBreakRe: /\n/g,
    statics: {
        /**
         * Debug rendering options:
         *
         * debug: {
         *     bbox: true // renders the bounding box of the text sprite
         * }
         *
         */
        debug: false
    },
    inheritableStatics: {
        shortHand1Re: /'(.*)'/g,
        shortHand2Re: / /g,
        shortHand3Re: /\s*,\s*/g,
        shortHand4Re: /\$\$\$\$/g,
        def: {
            animationProcessors: {
                text: 'text'
            },
            processors: {
                /**
                 * @cfg {Number} [x=0] The position of the sprite on the x-axis.
                 */
                x: 'number',

                /**
                 * @cfg {Number} [y=0] The position of the sprite on the y-axis.
                 */
                y: 'number',

                /**
                 * @cfg {String} [text=''] The text represented in the sprite.
                 */
                text: 'string',

                /**
                 * @cfg {String/Number} [fontSize='10px'] The size of the font displayed.
                 */
                fontSize: function (n) {
                    if (!isNaN(n)) {
                        return +n + 'px';
                    } else if (n.match(Ext.dom.Element.unitRe)) {
                        return n;
                    }
                },

                /**
                 * @cfg {String} [fontStyle=''] The style of the font displayed. {normal, italic, oblique}
                 */
                fontStyle: 'enums(,italic,oblique)',

                /**
                 * @cfg {String} [fontVariant=''] The variant of the font displayed. {normal, small-caps}
                 */
                fontVariant: 'enums(,small-caps)',

                /**
                 * @cfg {String} [fontWeight=''] The weight of the font displayed. {normal, bold, bolder, lighter}
                 */
                fontWeight: (function (fontWeights) {
                    return function (n) {
                        if (!n) {
                            return '';
                        } else if (n === 'normal') {
                            return '';
                        } else if (!isNaN(n)) {
                            n = +n;
                            if (100 <= n && n <= 900) {
                                return n;
                            }
                        } else if (n in fontWeights) {
                            return n;
                        }
                    };
                })({
                    normal: true,
                    bold: true,
                    bolder: true,
                    lighter: true
                }),

                /**
                 * @cfg {String} [fontFamily='sans-serif'] The family of the font displayed.
                 */
                fontFamily: 'string',

                /**
                 * @cfg {String} [textAlign='start'] The alignment of the text displayed. {left, right, center, start, end}
                 */
                textAlign: (function (textAligns) {
                    return function (n) {
                        return textAligns[n] || 'center';
                    };
                })({
                    start: 'start',
                    left: 'start',
                    center: 'center',
                    middle: 'center',
                    end: 'end',
                    right: 'end'
                }),

                /**
                 * @cfg {String} [textBaseline="alphabetic"] The baseline of the text displayed. {top, hanging, middle, alphabetic, ideographic, bottom}
                 */
                textBaseline: (function (textBaselines) {
                    return function (n) {
                        return textBaselines[n] || 'alphabetic';
                    };
                })({
                    top: 'top',
                    hanging: 'hanging',
                    middle: 'middle',
                    center: 'middle',
                    alphabetic: 'alphabetic',
                    ideographic: 'ideographic',
                    bottom: 'bottom'
                }),

                /**
                 * @cfg {String} [font='10px sans-serif'] The font displayed.
                 */
                font: "string"
                ,debug: 'default'
            },
            aliases: {
                'font-size': 'fontSize',
                'font-family': 'fontFamily',
                'font-weight': 'fontWeight',
                'font-variant': 'fontVariant',
                'text-anchor': 'textAlign'
            },
            defaults: {
                fontStyle: '',
                fontVariant: '',
                fontWeight: '',
                fontSize: '10px',
                fontFamily: 'sans-serif',
                font: '10px sans-serif',
                textBaseline: 'alphabetic',
                textAlign: 'start',
                strokeStyle: 'rgba(0, 0, 0, 0)',
                fillStyle: '#000',
                x: 0,
                y: 0,
                text: ''
            },
            dirtyTriggers: {
                fontStyle: 'font,bbox',
                fontVariant: 'font,bbox',
                fontWeight: 'font,bbox',
                fontSize: 'font,bbox',
                fontFamily: 'font,bbox',
                font: 'font-short-hand,bbox,canvas',
                textBaseline: 'bbox',
                textAlign: 'bbox',
                x: 'bbox',
                y: 'bbox',
                text: 'bbox'
            },
            updaters: {
                'font-short-hand': (function (dispatcher) {
                    return function (attrs) {
                        // TODO: Do this according to http://www.w3.org/TR/CSS21/fonts.html#font-shorthand
                        var value = attrs.font,
                            parts, part, i, ln, dispKey;
                        value = value.replace(Ext.draw.sprite.Text.shortHand1Re, function (a, arg1) {
                            return arg1.replace(Ext.draw.sprite.Text.shortHand2Re, '$$$$');
                        });
                        value = value.replace(Ext.draw.sprite.Text.shortHand3Re, ',');
                        parts = value.split(' ');

                        attrs = {};
                        for (i = 0, ln = parts.length; i < ln; i++) {
                            part = parts[i];
                            dispKey = dispatcher[part];
                            if (dispKey) {
                                attrs[dispKey] = part;
                            } else if (part.match(Ext.dom.Element.unitRe)) {
                                attrs.fontSize = part;
                            } else {
                                attrs.fontFamily = part.replace(Ext.draw.sprite.Text.shortHand4Re, ' ');
                            }
                        }
                        this.setAttributes(attrs, true);
                    };
                })({
                    'italic': 'fontStyle',
                    'oblique': 'fontStyle',
                    'bold': 'fontWeight',
                    'bolder': 'fontWeight',
                    'lighter': 'fontWeight',
                    '100': 'fontWeight',
                    '200': 'fontWeight',
                    '300': 'fontWeight',
                    '400': 'fontWeight',
                    '500': 'fontWeight',
                    '600': 'fontWeight',
                    '700': 'fontWeight',
                    '800': 'fontWeight',
                    '900': 'fontWeight',
                    'small-caps': 'fontVariant'
                }),
                font: function (attrs) {
                    var font = '';
                    if (attrs.fontWeight) {
                        font += attrs.fontWeight + ' ';
                    }
                    if (attrs.fontStyle) {
                        font += attrs.fontStyle + ' ';
                    }
                    if (attrs.fontVariant) {
                        font += attrs.fontVariant + ' ';
                    }
                    if (attrs.fontSize) {
                        font += attrs.fontSize + ' ';
                    }
                    if (attrs.fontFamily) {
                        font += attrs.fontFamily;
                    }
                    this.setAttributes({
                        font: font
                    }, true);
                }
            }
        }
    },

    constructor: function (config) {
        if (config && config.font) {
            config = Ext.clone(config);
            for (var key in config) {
                if (key !== 'font' && key.indexOf('font') === 0) {
                    delete config[key];
                }
            }
        }
        Ext.draw.sprite.Sprite.prototype.constructor.call(this, config);
    },

    // Overriding the getBBox method of the abstract sprite here to always
    // recalculate the bounding box of the text in flipped RTL mode
    // because in that case the position of the sprite depends not just on
    // the value of its 'x' attribute, but also on the width of the surface
    // the sprite belongs to.
    getBBox: function (isWithoutTransform) {
        var me = this,
            plain = me.attr.bbox.plain,
            surface = me.getSurface();
        if (!surface) {
            Ext.Error.raise("The sprite does not belong to a surface.");
        }
        if (plain.dirty) {
            me.updatePlainBBox(plain);
            plain.dirty = false;
        } if (surface.getInherited().rtl && surface.getFlipRtlText()) {
            // Since sprite's attributes haven't actually changed at this point,
            // and we just want to update the position of its bbox
            // based on surface's width, there's no reason to perform
            // expensive text measurement operation here,
            // so we can use the result of the last measurement instead.
            me.updatePlainBBox(plain, true);
        }
        return me.callParent([isWithoutTransform]);
    },

    rtlAlignments: {
        start: 'end',
        center: 'center',
        end: 'start'
    },

    updatePlainBBox: function (plain, useOldSize) {
        var me = this,
            attr = me.attr,
            x = attr.x,
            y = attr.y,
            dx = [],
            font = attr.font,
            text = attr.text,
            baseline = attr.textBaseline,
            alignment = attr.textAlign,
            size = (useOldSize && me.oldSize) ? me.oldSize : (me.oldSize = Ext.draw.TextMeasurer.measureText(text, font)),
            surface = me.getSurface(),
            isRtl = surface.getInherited().rtl,
            flipRtlText = isRtl && surface.getFlipRtlText(),
            rect = surface.getRect(),
            sizes = size.sizes,
            blockHeight = size.height,
            blockWidth = size.width,
            ln = sizes ? sizes.length : 0,
            lineWidth,
            i = 0;

        // To get consistent results in all browsers we don't apply textAlign and textBaseline
        // attributes of the sprite to context, so text is always left aligned and has an alphabetic baseline.
        // Instead we have to calculate the horizontal offset of each line based on sprite's textAlign,
        // and the vertical offset of the bounding box based on sprite's textBaseline.
        // These offsets are then used by the sprite's 'render' method to position text properly.

        switch (baseline) {
            case 'hanging' :
            case 'top':
                break;
            case 'ideographic' :
            case 'bottom' :
                y -= blockHeight;
                break;
            case 'alphabetic' :
                y -= blockHeight * 0.8;
                break;
            case 'middle' :
                y -= blockHeight * 0.5;
                break;
        }
        if (flipRtlText) {
            x = rect[2] - rect[0] - x;
            alignment = me.rtlAlignments[alignment];
        }

        switch (alignment) {
            case 'start':
                if (isRtl) {
                    for (; i < ln; i++) {
                        lineWidth = sizes[i].width;
                        dx.push(-(blockWidth - lineWidth));
                    }
                }
                break;
            case 'end' :
                x -= blockWidth;
                if (isRtl) {
                    break;
                }
                for (; i < ln; i++) {
                    lineWidth = sizes[i].width;
                    dx.push(blockWidth - lineWidth);
                }
                break;
            case 'center' :
                x -= blockWidth * 0.5;
                for (; i < ln; i++) {
                    lineWidth = sizes[i].width;
                    dx.push((isRtl ? -1 : 1) * (blockWidth - lineWidth) * 0.5);
                }
                break;
        }

        attr.textAlignOffsets = dx;

        plain.x = x;
        plain.y = y;
        plain.width = blockWidth;
        plain.height = blockHeight;
    },

    setText: function (text) {
        this.setAttributes({text: text}, true);
    },

    setElementStyles: function (element, styles) {
        var stylesCache = element.stylesCache || (element.stylesCache = {}),
            style = element.dom.style,
            name;
        for (name in styles) {
            if (stylesCache[name] !== styles[name]) {
                stylesCache[name] = style[name] = styles[name];
            }
        }
    },

    renderBBox: function (surface, ctx) {
        var bbox = this.getBBox(true);
        ctx.beginPath();
        ctx.moveTo(bbox.x, bbox.y);
        ctx.lineTo(bbox.x + bbox.width, bbox.y);
        ctx.lineTo(bbox.x + bbox.width, bbox.y + bbox.height);
        ctx.lineTo(bbox.x, bbox.y + bbox.height);
        ctx.closePath();
        ctx.strokeStyle = 'red';
        ctx.strokeOpacity = 1;
        ctx.lineWidth = 0.5;
        ctx.stroke();
    },

    render: function (surface, ctx, rect) {
        var me = this,
            attr = me.attr,
            mat = Ext.draw.Matrix.fly(attr.matrix.elements.slice(0)),
            bbox = me.getBBox(true),
            dx = attr.textAlignOffsets,
            none = Ext.draw.Color.RGBA_NONE,
            x, y, i, lines, lineHeight;
        if (attr.text.length === 0) {
            return;
        }

        lines = attr.text.split('\n');
        lineHeight = bbox.height / lines.length;
        // Simulate textBaseline and textAlign.
        x = attr.bbox.plain.x;
        // lineHeight * 0.78 is the approximate distance between the top and the alphabetic baselines
        y = attr.bbox.plain.y + lineHeight * 0.78;
        mat.toContext(ctx);
        if (surface.getInherited().rtl) {
            // Canvas element in RTL mode automatically flips text alignment.
            // Here we compensate for that change.
            // So text is still positioned and aligned as in the LTR mode,
            // but the direction of the text is RTL.
            x += attr.bbox.plain.width;
        }

        for (i = 0; i < lines.length; i++) {
            if (ctx.fillStyle !== none) {
                ctx.fillText(lines[i], x + (dx[i] || 0), y + lineHeight * i);
            }
            if (ctx.strokeStyle !== none) {
                ctx.strokeText(lines[i], x + (dx[i] || 0), y + lineHeight * i);
            }
        }
        var debug = me.statics().debug || attr.debug;
        if (debug) {
            debug.bbox && me.renderBBox(surface, ctx);
        }
    }
});

Ext.define('Ext.draw.sprite.Tick', {
    extend: 'Ext.draw.sprite.Line',
    alias: 'sprite.tick',

    inheritableStatics: {
        def: {
            processors: {
                x: 'number',
                y: 'number',
                /**
                 * @cfg {Number} [size=4] The size of the sprite.
                 * Meant to be comparable to the size of a circle sprite with the same radius.
                 */
                size: 'number'
            },
            defaults: {
                x: 0,
                y: 0,
                size: 4
            },
            dirtyTriggers: {
                x: 'tick',
                y: 'tick',
                size: 'tick'
            },
            updaters: {
                tick: function (attr) {
                    var size = attr.size * 1.5,
                        halfLineWidth = attr.lineWidth / 2,
                        x = attr.x,
                        y = attr.y;
                    this.setAttributes({
                        fromX: x - halfLineWidth,
                        fromY: y - size,
                        toX: x - halfLineWidth,
                        toY: y + size
                    });
                }
            }
        }
    }

});

Ext.define('Ext.draw.sprite.Triangle', {
    extend: 'Ext.draw.sprite.Path',
    alias: 'sprite.triangle',

    inheritableStatics: {
        def: {
            processors: {
                x: 'number',
                y: 'number',
                /**
                 * @cfg {Number} [size=4] The size of the sprite.
                 * Meant to be comparable to the size of a circle sprite with the same radius.
                 */
                size: 'number'
            },
            defaults: {
                x: 0,
                y: 0,
                size: 4
            },
            dirtyTriggers: {
                x: 'path',
                y: 'path',
                size: 'path'
            }
        }
    },

    updatePath: function (path, attr) {
        var s = attr.size * 2.2,
            x = attr.x,
            y = attr.y;
        path.fromSvgString('M'.concat(x, ',', y, 'm0-', s * 0.58, 'l', s * 0.5, ',', s * 0.87, '-', s, ',0z'));
    }

});

/**
 * Linear gradient.
 *
 *     @example
 *     Ext.create('Ext.Container', {
 *         renderTo: Ext.getBody(),
 *         width: 600,
 *         height: 400,
 *         layout: 'fit',
 *         items: {
 *             xtype: 'draw',
 *             sprites: [{
 *                 type: 'circle',
 *                 cx: 50,
 *                 cy: 50,
 *                 r: 100,
 *                 fillStyle: {
 *                     type: 'linear',
 *                     degrees: 0,
 *                     stops: [
 *                         {
 *                             offset: 0,
 *                             color: 'white'
 *                         },
 *                         {
 *                             offset: 1,
 *                             color: 'blue'
 *                         }
 *                     ]
 *                 }
 *             }]
 *         }
 *     });
 */

Ext.define('Ext.draw.gradient.Linear', {
    extend: 'Ext.draw.gradient.Gradient',
    requires: ['Ext.draw.Color'],
    type: 'linear',
    config: {
        /**
         * @cfg {Number} The degree of rotation of the gradient.
         */
        degrees: 0
    },

    setAngle: function (angle) {
        this.setDegrees(angle);
    },

    /**
     * @inheritdoc
     */
    generateGradient: function (ctx, bbox) {
        var angle = Ext.draw.Draw.rad(this.getDegrees()),
            cos = Math.cos(angle),
            sin = Math.sin(angle),
            w = bbox.width,
            h = bbox.height,
            cx = bbox.x + w * 0.5,
            cy = bbox.y + h * 0.5,
            stops = this.getStops(),
            ln = stops.length,
            gradient,
            l, i;

        if (!isNaN(cx) && !isNaN(cy) && h > 0 && w > 0) {
            l = (Math.sqrt(h * h + w * w) * Math.abs(Math.cos(angle - Math.atan(h / w)))) / 2;
            gradient = ctx.createLinearGradient(
                cx + cos * l, cy + sin * l,
                cx - cos * l, cy - sin * l
            );

            for (i = 0; i < ln; i++) {
                gradient.addColorStop(stops[i].offset, stops[i].color);
            }
            return gradient;
        }
        return Ext.draw.Color.NONE;
    }
});

/**
 * Radial gradient.
 *
 *     @example
 *     Ext.create('Ext.Container', {
 *         renderTo: Ext.getBody(),
 *         width: 600,
 *         height: 400,
 *         layout: 'fit',
 *         items: {
 *             xtype: 'draw',
 *             sprites: [{
 *                 type: 'circle',
 *                 cx: 100,
 *                 cy: 100,
 *                 r: 100,
 *                 fillStyle: {
 *                     type: 'radial',
 *                     start: {
 *                         x: 0,
 *                         y: 0,
 *                         r: 0
 *                     },
 *                     end: {
 *                         x: 0,
 *                         y: 0,
 *                         r: 1
 *                     },
 *                     stops: [
 *                         {
 *                             offset: 0,
 *                             color: 'white'
 *                         },
 *                         {
 *                             offset: 1,
 *                             color: 'blue'
 *                         }
 *                     ]
 *                 }
 *             }]
 *         }
 *     });
 */
Ext.define('Ext.draw.gradient.Radial', {
    extend: 'Ext.draw.gradient.Gradient',
    type: 'radial',
    config: {
        /**
         * @cfg {Object} start The starting circle of the gradient.
         */
        start: {
            x: 0,
            y: 0,
            r: 0
        },
        /**
         * @cfg {Object} end The ending circle of the gradient.
         */
        end: {
            x: 0,
            y: 0,
            r: 1
        }
    },

    applyStart: function (newStart, oldStart) {
        if (!oldStart) {
            return newStart;
        }
        var circle = {
            x: oldStart.x,
            y: oldStart.y,
            r: oldStart.r
        };

        if ('x' in newStart) {
            circle.x = newStart.x;
        } else if ('centerX' in newStart) {
            circle.x = newStart.centerX;
        }

        if ('y' in newStart) {
            circle.y = newStart.y;
        } else if ('centerY' in newStart) {
            circle.y = newStart.centerY;
        }

        if ('r' in newStart) {
            circle.r = newStart.r;
        } else if ('radius' in newStart) {
            circle.r = newStart.radius;
        }
        return circle;
    },

    applyEnd: function (newEnd, oldEnd) {
        if (!oldEnd) {
            return newEnd;
        }
        var circle = {
            x: oldEnd.x,
            y: oldEnd.y,
            r: oldEnd.r
        };

        if ('x' in newEnd) {
            circle.x = newEnd.x;
        } else if ('centerX' in newEnd) {
            circle.x = newEnd.centerX;
        }

        if ('y' in newEnd) {
            circle.y = newEnd.y;
        } else if ('centerY' in newEnd) {
            circle.y = newEnd.centerY;
        }

        if ('r' in newEnd) {
            circle.r = newEnd.r;
        } else if ('radius' in newEnd) {
            circle.r = newEnd.radius;
        }
        return circle;
    },

    /**
     * @inheritdoc
     */
    generateGradient: function (ctx, bbox) {
        var start = this.getStart(),
            end = this.getEnd(),
            w = bbox.width * 0.5,
            h = bbox.height * 0.5,
            x = bbox.x + w,
            y = bbox.y + h,
            gradient = ctx.createRadialGradient(
                x + start.x * w, y + start.y * h, start.r * Math.max(w, h),
                x + end.x * w, y + end.y * h, end.r * Math.max(w, h)
            ),
            stops = this.getStops(),
            ln = stops.length,
            i;

        for (i = 0; i < ln; i++) {
            gradient.addColorStop(stops[i].offset, stops[i].color);
        }
        return gradient;
    }
});

/**
 * A Surface is an interface to render methods inside a draw {@link Ext.draw.Container}.
 * A Surface contains methods to render sprites, get bounding boxes of sprites, add
 * sprites to the canvas, initialize other graphic components, etc. One of the most used
 * methods for this class is the `add` method, to add Sprites to the surface.
 *
 * Most of the Surface methods are abstract and they have a concrete implementation
 * in Canvas or SVG engines.
 *
 * A Surface instance can be accessed as a property of a draw container. For example:
 *
 *     drawContainer.getSurface('main').add({
 *         type: 'circle',
 *         fill: '#ffc',
 *         radius: 100,
 *         x: 100,
 *         y: 100
 *     });
 *
 * The configuration object passed in the `add` method is the same as described in the {@link Ext.draw.sprite.Sprite}
 * class documentation.
 *
 * ## Example
 *
 *     drawContainer.getSurface('main').add([
 *         {
 *             type: 'circle',
 *             radius: 10,
 *             fill: '#f00',
 *             x: 10,
 *             y: 10
 *         },
 *         {
 *             type: 'circle',
 *             radius: 10,
 *             fill: '#0f0',
 *             x: 50,
 *             y: 50
 *         },
 *         {
 *             type: 'circle',
 *             radius: 10,
 *             fill: '#00f',
 *             x: 100,
 *             y: 100
 *         },
 *         {
 *             type: 'rect',
 *             radius: 10,
 *             x: 10,
 *             y: 10
 *         },
 *         {
 *             type: 'rect',
 *             radius: 10,
 *             x: 50,
 *             y: 50
 *         },
 *         {
 *             type: 'rect',
 *             radius: 10,
 *             x: 100,
 *             y: 100
 *         }
 *     ]);
 *
 */
Ext.define('Ext.draw.Surface', {
    extend: 'Ext.draw.SurfaceBase',
    xtype: 'surface',

    requires: [
        'Ext.draw.sprite.*',
        'Ext.draw.gradient.*',
        'Ext.draw.sprite.AttributeDefinition',
        'Ext.draw.Matrix',
        'Ext.draw.Draw'
    ],

    uses: [
        'Ext.draw.engine.Canvas'
    ],

    /**
     * The reported device pixel density.
     */
    devicePixelRatio: window.devicePixelRatio || 1,

    statics: {
        /**
         * Stably sort the list of sprites by their zIndex.
         * TODO: Improve the performance. Reduce gc impact.
         * @param {Array} list
         */
        stableSort: function (list) {
            if (list.length < 2) {
                return;
            }
            var keys = {}, sortedKeys, result = [], i, ln, zIndex;

            for (i = 0, ln = list.length; i < ln; i++) {
                zIndex = list[i].attr.zIndex;
                if (!keys[zIndex]) {
                    keys[zIndex] = [list[i]];
                } else {
                    keys[zIndex].push(list[i]);
                }
            }
            sortedKeys = Ext.Object.getKeys(keys).sort(function (a, b) {return a - b;});
            for (i = 0, ln = sortedKeys.length; i < ln; i++) {
                result.push.apply(result, keys[sortedKeys[i]]);
            }
            for (i = 0, ln = list.length; i < ln; i++) {
                list[i] = result[i];
            }
        }
    },

    config: {
        cls: Ext.baseCSSPrefix + 'surface',
        /**
         * @cfg {Array}
         * The rect of the surface related to its container.
         */
        rect: null,

        /**
         * @cfg {Object}
         * Background sprite config of the surface.
         */
        background: null,

        /**
         * @cfg {Array}
         * Array of sprite instances.
         */
        items: [],

        /**
         * @cfg {Boolean}
         * Indicates whether the surface needs redraw.
         */
        dirty: false,

        /**
         * @cfg {Boolean} flipRtlText
         * If the surface is in the RTL mode, text will render with the RTL direction,
         * but the alignment and position of the text won't change by default.
         * Setting this config to 'true' will get text alignment and its position
         * within a surface mirrored.
         */
        flipRtlText: false
    },

    isSurface: true,

    dirtyPredecessor: 0,

    constructor: function (config) {
        var me = this;

        me.predecessors = [];
        me.successors = [];
        // The `pendingRenderFrame` flag is used to indicate that `predecessors` (surfaces that should render first)
        // are dirty, and to call `renderFrame` when all `predecessors` have their `renderFrame` called
        // (i.e. not dirty anymore).
        me.pendingRenderFrame = false;
        me.map = {};

        me.callParent([config]);
        me.matrix = new Ext.draw.Matrix();
        me.inverseMatrix = me.matrix.inverse(me.inverseMatrix);
        me.resetTransform();
    },

    /**
     * Round the number to align to the pixels on device.
     * @param {Number} num The number to align.
     * @return {Number} The resultant alignment.
     */
    roundPixel: function (num) {
        return Math.round(this.devicePixelRatio * num) / this.devicePixelRatio;
    },

    /**
     * Mark the surface to render after another surface is updated.
     * @param {Ext.draw.Surface} surface The surface to wait for.
     */
    waitFor: function (surface) {
        var me = this,
            predecessors = me.predecessors;
        if (!Ext.Array.contains(predecessors, surface)) {
            predecessors.push(surface);
            surface.successors.push(me);
            if (surface._dirty) {
                me.dirtyPredecessor++;
            }
        }
    },

    setDirty: function (dirty) {
        if (this._dirty !== dirty) {
            var successors = this.successors, successor,
                i, ln = successors.length;
            for (i = 0; i < ln; i++) {
                successor = successors[i];
                if (dirty) {
                    successor.dirtyPredecessor++;
                    successor.setDirty(true);
                } else {
                    successor.dirtyPredecessor--;
                    if (successor.dirtyPredecessor === 0 && successor.pendingRenderFrame) {
                        successor.renderFrame();
                    }
                }
            }
            this._dirty = dirty;
        }
    },

    applyElement: function (newElement, oldElement) {
        if (oldElement) {
            oldElement.set(newElement);
        } else {
            oldElement = Ext.Element.create(newElement);
        }
        this.setDirty(true);
        return oldElement;
    },

    applyBackground: function (background, oldBackground) {
        this.setDirty(true);
        if (Ext.isString(background)) {
            background = { fillStyle: background };
        }
        return Ext.factory(background, Ext.draw.sprite.Rect, oldBackground);
    },

    applyRect: function (rect, oldRect) {
        if (oldRect && rect[0] === oldRect[0] && rect[1] === oldRect[1] && rect[2] === oldRect[2] && rect[3] === oldRect[3]) {
            return;
        }
        if (Ext.isArray(rect)) {
            return [rect[0], rect[1], rect[2], rect[3]];
        } else if (Ext.isObject(rect)) {
            return [
                rect.x || rect.left,
                rect.y || rect.top,
                rect.width || (rect.right - rect.left),
                rect.height || (rect.bottom - rect.top)
            ];
        }
    },

    updateRect: function (rect) {
        var me = this,
            l = rect[0],
            t = rect[1],
            r = l + rect[2],
            b = t + rect[3],
            background = me.getBackground(),
            element = me.element;

        element.setLocalXY(Math.floor(l), Math.floor(t));
        element.setSize(Math.ceil(r - Math.floor(l)), Math.ceil(b - Math.floor(t)));

        if (background) {
            background.setAttributes({
                x: 0,
                y: 0,
                width: Math.ceil(r - Math.floor(l)),
                height: Math.ceil(b - Math.floor(t))
            });
        }
        me.setDirty(true);
    },

    /**
     * Reset the matrix of the surface.
     */
    resetTransform: function () {
        this.matrix.set(1, 0, 0, 1, 0, 0);
        this.inverseMatrix.set(1, 0, 0, 1, 0, 0);
        this.setDirty(true);
    },

    /**
     * Get the sprite by id or index.
     * It will first try to find a sprite with the given id, otherwise will try to use the id as an index.
     * @param {String|Number} id
     * @returns {Ext.draw.sprite.Sprite}
     */
    get: function (id) {
        return this.map[id] || this.items[id];
    },

    /**
     * Add a Sprite to the surface.
     * You can put any number of object as parameter.
     * See {@link Ext.draw.sprite.Sprite} for the configuration object to be passed into this method.
     *
     * For example:
     *
     *     drawContainer.surface.add({
     *         type: 'circle',
     *         fill: '#ffc',
     *         radius: 100,
     *         x: 100,
     *         y: 100
     *     });
     *
     */
    add: function () {
        var me = this,
            args = Array.prototype.slice.call(arguments),
            argIsArray = Ext.isArray(args[0]),
            results = [],
            sprite, sprites, items, i, ln;

        items = Ext.Array.clean(argIsArray ? args[0] : args);
        if (!items.length) {
            return results;
        }
        sprites = me.prepareItems(items);

        for (i = 0, ln = sprites.length; i < ln; i++) {
            sprite = sprites[i];
            me.map[sprite.getId()] = sprite;
            results.push(sprite);
            sprite.setParent(me);
            sprite.setSurface(me);
            me.onAdd(sprite);
        }

        items = me.getItems();
        if (items) {
            items.push.apply(items, results);
        }

        me.dirtyZIndex = true;
        me.setDirty(true);

        if (!argIsArray && results.length === 1) {
            return results[0];
        } else {
            return results;
        }
    },

    /**
     * @protected
     * Invoked when a sprite is added to the surface.
     * @param {Ext.draw.sprite.Sprite} sprite The sprite to be added.
     */
    onAdd: Ext.emptyFn,

    /**
     * Remove a given sprite from the surface, optionally destroying the sprite in the process.
     * You can also call the sprite own `remove` method.
     *
     * For example:
     *
     *      drawContainer.surface.remove(sprite);
     *      // or...
     *      sprite.remove();
     *
     * @param {Ext.draw.sprite.Sprite} sprite
     * @param {Boolean} [destroySprite=false]
     */
    remove: function (sprite, destroySprite) {
        if (sprite) {
            delete this.map[sprite.getId()];
            if (destroySprite) {
                sprite.destroy();
            } else {
                sprite.setParent(null);
                sprite.setSurface(null);
                Ext.Array.remove(this.getItems(), sprite);
            }
            this.dirtyZIndex = true;
            this.setDirty(true);
        }
    },

    /**
     * Remove all sprites from the surface, optionally destroying the sprites in the process.
     *
     * For example:
     *
     *      drawContainer.getSurface('main').removeAll();
     *
     * @param {Boolean} [destroySprites=false]
     */
    removeAll: function (destroySprites) {
        var items = this.getItems(),
            i = items.length,
            item;
        if (destroySprites) {
            while (i > 0) {
                items[--i].destroy();
            }
        } else {
            while (i > 0) {
                i--;
                item = items[i];
                item.setParent(null);
                item.setSurface(null);
            }
        }
        items.length = 0;
        this.map = {};
        this.dirtyZIndex = true;
    },

    // @private
    applyItems: function (items) {
        if (this.getItems()) {
            this.removeAll(true);
        }
        return Ext.Array.from(this.add(items));
    },

    /**
     * @private
     * Initialize and apply defaults to surface items.
     */
    prepareItems: function (items) {
        items = [].concat(items);
        // Make sure defaults are applied and item is initialized

        var me = this,
            item, i, ln, j,
            removeSprite = function (sprite) {
                this.remove(sprite, false);
            };

        for (i = 0, ln = items.length; i < ln; i++) {
            item = items[i];
            if (!(item instanceof Ext.draw.sprite.Sprite)) {
                // Temporary, just take in configs...
                item = items[i] = me.createItem(item);
            }
            item.on('beforedestroy', removeSprite, me);
        }
        return items;
    },

    /**
     * @private Creates an item and appends it to the surface. Called
     * as an internal method when calling `add`.
     */
    createItem: function (config) {
        return Ext.create(config.xclass || 'sprite.' + config.type, config);
    },

    /**
     * Return the minimal bounding box that contains all the sprites bounding boxes in the given list of sprites.
     * @param {Ext.draw.sprite.Sprite[]|Ext.draw.sprite.Sprite} sprites
     * @param {Boolean} [isWithoutTransform=false]
     * @returns {{x: Number, y: Number, width: number, height: number}}
     */
    getBBox: function (sprites, isWithoutTransform) {
        var sprites = Ext.Array.from(sprites),
            left = Infinity,
            right = -Infinity,
            top = Infinity,
            bottom = -Infinity,
            sprite, bbox, i, ln;

        for (i = 0, ln = sprites.length; i < ln; i++) {
            sprite = sprites[i];
            bbox = sprite.getBBox(isWithoutTransform);
            if (left > bbox.x) {
                left = bbox.x;
            }
            if (right < bbox.x + bbox.width) {
                right = bbox.x + bbox.width;
            }
            if (top > bbox.y) {
                top = bbox.y;
            }
            if (bottom < bbox.y + bbox.height) {
                bottom = bbox.y + bbox.height;
            }
        }
        return {
            x: left,
            y: top,
            width: right - left,
            height: bottom - top
        };
    },

    emptyRect: [0, 0, 0, 0],

    // Converts event's page coordinates into surface coordinates.
    // Note: surface's x-coordinates always go LTR, regardless of RTL mode.
    getEventXY: function (e) {
        var me = this,
            isRtl = me.getInherited().rtl,
            pageXY = e.getXY(), // Event position in page coordinates.
            container = me.el.up(),
            xy = container.getXY(), // Surface container position in page coordinates.
            rect = me.getRect() || me.emptyRect, // Surface position in surface container coordinates (LTR).
            result = [],
            width;

        if (isRtl) {
            width = container.getWidth();
            // The line below is actually a simplified form of
            // rect[2] - (pageXY[0] - xy[0] - (width - (rect[0] + rect[2]))).
            result[0] = xy[0] - pageXY[0] - rect[0] + width;
        } else {
            result[0] = pageXY[0] - xy[0] - rect[0];
        }
        result[1] = pageXY[1] - xy[1] - rect[1];
        return result;
    },

    /**
     * Empty the surface content (without touching the sprites.)
     */
    clear: Ext.emptyFn,

    /**
     * @private
     * Order the items by their z-index if any of that has been changed since last sort.
     */
    orderByZIndex: function () {
        var me = this,
            items = me.getItems(),
            dirtyZIndex = false,
            i, ln;

        if (me.getDirty()) {
            for (i = 0, ln = items.length; i < ln; i++) {
                if (items[i].attr.dirtyZIndex) {
                    dirtyZIndex = true;
                    break;
                }
            }
            if (dirtyZIndex) {
                // sort by zIndex
                Ext.draw.Surface.stableSort(items);
                this.setDirty(true);
            }

            for (i = 0, ln = items.length; i < ln; i++) {
                items[i].attr.dirtyZIndex = false;
            }
        }
    },

    /**
     * Force the element to redraw.
     */
    repaint: function () {
        var me = this;
        me.repaint = Ext.emptyFn;
        Ext.defer(function () {
            delete me.repaint;
            me.element.repaint();
        }, 1);
    },

    /**
     * Triggers the re-rendering of the canvas.
     */
    renderFrame: function () {
        if (!this.element) {
            return;
        }
        if (this.dirtyPredecessor > 0) {
            this.pendingRenderFrame = true;
            return;
        }

        var me = this,
            rect = this.getRect(),
            background = me.getBackground(),
            items = me.getItems(),
            item, i, ln;

        // Cannot render before the surface is placed.
        if (!rect) {
            return;
        }

        // This will also check the dirty flags of the sprites.
        me.orderByZIndex();
        if (me.getDirty()) {
            me.clear();
            me.clearTransform();

            if (background) {
                me.renderSprite(background);
            }

            for (i = 0, ln = items.length; i < ln; i++) {
                item = items[i];
                if (false === me.renderSprite(item)) {
                    return;
                }
                item.attr.textPositionCount = me.textPosition;
            }

            me.setDirty(false);
        }
    },

    /**
     * @private
     * Renders a single sprite into the surface.
     * Do not call it from outside `renderFrame` method.
     *
     * @param {Ext.draw.sprite.Sprite} sprite The Sprite to be rendered.
     * @return {Boolean} returns `false` to stop the rendering to continue.
     */
    renderSprite: Ext.emptyFn,

    /**
     * @method flatten
     * Flattens the given drawing surfaces into a single image
     * and returns an object containing the data (in the DataURL format)
     * and the type (e.g. 'png' or 'svg') of that image.
     * @param {Object} size The size of the final image.
     * @param {Number} size.width
     * @param {Number} size.height
     * @param {Ext.draw.Surface[]} surfaces The surfaces to flatten.
     * @return {Object}
     * @return {String} return.data The DataURL of the flattened image.
     * @return {String} return.type The type of the image.
     *
     */

    /**
     * @private
     * Clears the current transformation state on the surface.
     */
    clearTransform: Ext.emptyFn,

    /**
     * Returns 'true' if the surface is dirty.
     * @return {Boolean} 'true' if the surface is dirty
     */
    getDirty: function () {
        return this._dirty;
    },

    /**
     * Destroys the surface. This is done by removing all components from it and
     * also removing its reference to a DOM element.
     *
     * For example:
     *
     *      drawContainer.surface.destroy();
     */
    destroy: function () {
        var me = this;
        me.removeAll();
        me.setBackground(null);
        me.predecessors = null;
        me.successors = null;
        me.callParent();
    }
});



/**
 * @class Ext.draw.engine.SvgContext
 *
 * A class that imitates a canvas context but generates svg elements instead.
 */
Ext.define('Ext.draw.engine.SvgContext', {

    requires: ['Ext.draw.Color'],

    /**
     * @private
     * Properties to be saved/restored in the `save` and `restore` methods.
     */
    toSave: ["strokeOpacity", "strokeStyle", "fillOpacity", "fillStyle", "globalAlpha", "lineWidth", "lineCap",
             "lineJoin", "lineDash", "lineDashOffset", "miterLimit", "shadowOffsetX", "shadowOffsetY", "shadowBlur",
             "shadowColor", "globalCompositeOperation", "position", "fillGradient", "strokeGradient"],

    "strokeOpacity": 1,
    "strokeStyle": "none",
    "fillOpacity": 1,
    "fillStyle": "none",
    "lineDash": [],
    "lineDashOffset": 0,
    "globalAlpha": 1,
    "lineWidth": 1,
    "lineCap": "butt",
    "lineJoin": "miter",
    "miterLimit": 10,
    "shadowOffsetX": 0,
    "shadowOffsetY": 0,
    "shadowBlur": 0,
    "shadowColor": "none",
    "globalCompositeOperation": "src",

    urlStringRe: /^url\(#([\w\-]+)\)$/,

    constructor: function (SvgSurface) {
        this.surface = SvgSurface;
        this.state = [];
        this.matrix = new Ext.draw.Matrix();
        this.path = null;
        this.clear();
    },

    /**
     * Clears the context.
     */
    clear: function () {
        this.group = this.surface.mainGroup;
        this.position = 0;
        this.path = null;
    },

    /**
     * @private
     * @param {String} tag
     * @return {*}
     */
    getElement: function (tag) {
        return this.surface.getSvgElement(this.group, tag, this.position++);
    },

    /**
     * @private
     *
     * Destroys the DOM element and all associated gradients.
     *
     * @param element {HTMLElement|Ext.dom.Element|String} DOM element.
     */
    removeElement: function (element) {
        var element = Ext.fly(element),
            fill, stroke, fillMatch, strokeMatch,
            gradients, gradient, key;

        if (!element) {
            return;
        }
        if (element.dom.tagName === 'g') {
            gradients = element.dom.gradients;
            for (key in gradients) {
                gradients[key].destroy();
            }
        } else {
            fill = element.getAttribute('fill');
            stroke = element.getAttribute('stroke');
            fillMatch = fill && fill.match(this.urlStringRe);
            strokeMatch = stroke && stroke.match(this.urlStringRe);
            if (fillMatch && fillMatch[1]) {
                gradient = Ext.fly(fillMatch[1]);
                if (gradient) {
                    gradient.destroy();
                }
            }
            if (strokeMatch && strokeMatch[1]) {
                gradient = Ext.fly(strokeMatch[1]);
                if (gradient) {
                    gradient.destroy();
                }
            }
        }
        element.destroy();
    },

    /**
     * Pushes the context state to the state stack.
     */
    save: function () {
        var toSave = this.toSave,
            obj = {},
            group = this.getElement('g'),
            key, i;

        for (i = 0; i < toSave.length; i++) {
            key = toSave[i];
            if (key in this) {
                obj[key] = this[key];
            }
        }
        this.position = 0;
        obj.matrix = this.matrix.clone();
        this.state.push(obj);
        this.group = group;
        return group;
    },

    /**
     * Pops the state stack and restores the state.
     */
    restore: function () {
        var toSave = this.toSave,
            obj = this.state.pop(),
            children = this.group.dom.childNodes,
            key, i;
        
        // Removing extra DOM elements that were not reused.
        while (children.length > this.position) {
            this.removeElement(children[children.length - 1]);
        }
        for (i = 0; i < toSave.length; i++) {
            key = toSave[i];
            if (key in obj) {
                this[key] = obj[key];
            } else {
                delete this[key];
            }
        }

        this.setTransform.apply(this, obj.matrix.elements);
        this.group = this.group.getParent();
    },

    /**
     * Changes the transformation matrix to apply the matrix given by the arguments as described below.
     * @param {Number} xx
     * @param {Number} yx
     * @param {Number} xy
     * @param {Number} yy
     * @param {Number} dx
     * @param {Number} dy
     */
    transform: function (xx, yx, xy, yy, dx, dy) {
        if (this.path) {
            var inv = Ext.draw.Matrix.fly([xx, yx, xy, yy, dx, dy]).inverse();
            this.path.transform(inv);
        }
        this.matrix.append(xx, yx, xy, yy, dx, dy);
    },

    /**
     * Changes the transformation matrix to the matrix given by the arguments as described below.
     * @param {Number} xx
     * @param {Number} yx
     * @param {Number} xy
     * @param {Number} yy
     * @param {Number} dx
     * @param {Number} dy
     */
    setTransform: function (xx, yx, xy, yy, dx, dy) {
        if (this.path) {
            this.path.transform(this.matrix);
        }
        this.matrix.reset();
        this.transform(xx, yx, xy, yy, dx, dy);
    },

    /**
     * Scales the current context by the specified horizontal (x) and vertical (y) factors.
     * @param {Number} x The horizontal scaling factor, where 1 equals unity or 100% scale.
     * @param {Number} y The vertical scaling factor.
     */
    scale: function (x, y) {
        this.transform(x, 0, 0, y, 0, 0);
    },

    /**
     * Rotates the current context coordinates (that is, a transformation matrix).
     * @param {Number} angle The rotation angle, in radians.
     */
    rotate: function (angle) {
        var xx = Math.cos(angle),
            yx = Math.sin(angle),
            xy = -Math.sin(angle),
            yy = Math.cos(angle);
        this.transform(xx, yx, xy, yy, 0, 0);
    },

    /**
     * Specifies values to move the origin point in a canvas.
     * @param {Number} x The value to add to horizontal (or x) coordinates.
     * @param {Number} y The value to add to vertical (or y) coordinates.
     */
    translate: function (x, y) {
        this.transform(1, 0, 0, 1, x, y);
    },

    setGradientBBox: function (bbox) {
        this.bbox = bbox;
    },

    /**
     * Resets the current default path.
     */
    beginPath: function () {
        this.path = new Ext.draw.Path();
    },

    /**
     * Creates a new subpath with the given point.
     * @param {Number} x
     * @param {Number} y
     */
    moveTo: function (x, y) {
        if (!this.path) {
            this.beginPath();
        }
        this.path.moveTo(x, y);
        this.path.element = null;
    },

    /**
     * Adds the given point to the current subpath, connected to the previous one by a straight line.
     * @param {Number} x
     * @param {Number} y
     */
    lineTo: function (x, y) {
        if (!this.path) {
            this.beginPath();
        }
        this.path.lineTo(x, y);
        this.path.element = null;
    },

    /**
     * Adds a new closed subpath to the path, representing the given rectangle.
     * @param {Number} x
     * @param {Number} y
     * @param {Number} width
     * @param {Number} height
     */
    rect: function (x, y, width, height) {
        this.moveTo(x, y);
        this.lineTo(x + width, y);
        this.lineTo(x + width, y + height);
        this.lineTo(x, y + height);
        this.closePath();
    },

    /**
     * Paints the box that outlines the given rectangle onto the canvas, using the current stroke style.
     * @param {Number} x
     * @param {Number} y
     * @param {Number} width
     * @param {Number} height
     */
    strokeRect: function (x, y, width, height) {
        this.beginPath();
        this.rect(x, y, width, height);
        this.stroke();
    },

    /**
     * Paints the given rectangle onto the canvas, using the current fill style.
     * @param {Number} x
     * @param {Number} y
     * @param {Number} width
     * @param {Number} height
     */
    fillRect: function (x, y, width, height) {
        this.beginPath();
        this.rect(x, y, width, height);
        this.fill();
    },

    /**
     * Marks the current subpath as closed, and starts a new subpath with a point the same as the start and end of the newly closed subpath.
     */
    closePath: function () {
        if (!this.path) {
            this.beginPath();
        }
        this.path.closePath();
        this.path.element = null;
    },

    /**
     * Arc command using svg parameters.
     * @param {Number} r1
     * @param {Number} r2
     * @param {Number} rotation
     * @param {Number} large
     * @param {Number} swipe
     * @param {Number} x2
     * @param {Number} y2
     */
    arcSvg: function (r1, r2, rotation, large, swipe, x2, y2) {
        if (!this.path) {
            this.beginPath();
        }
        this.path.arcSvg(r1, r2, rotation, large, swipe, x2, y2);
        this.path.element = null;
    },

    /**
     * Adds points to the subpath such that the arc described by the circumference of the circle described by the arguments, starting at the given start angle and ending at the given end angle, going in the given direction (defaulting to clockwise), is added to the path, connected to the previous point by a straight line.
     * @param {Number} x
     * @param {Number} y
     * @param {Number} radius
     * @param {Number} startAngle
     * @param {Number} endAngle
     * @param {Number} anticlockwise
     */
    arc: function (x, y, radius, startAngle, endAngle, anticlockwise) {
        if (!this.path) {
            this.beginPath();
        }
        this.path.arc(x, y, radius, startAngle, endAngle, anticlockwise);
        this.path.element = null;
    },

    /**
     * Adds points to the subpath such that the arc described by the circumference of the ellipse described by the arguments, starting at the given start angle and ending at the given end angle, going in the given direction (defaulting to clockwise), is added to the path, connected to the previous point by a straight line.
     * @param {Number} x
     * @param {Number} y
     * @param {Number} radiusX
     * @param {Number} radiusY
     * @param {Number} rotation
     * @param {Number} startAngle
     * @param {Number} endAngle
     * @param {Number} anticlockwise
     */
    ellipse: function (x, y, radiusX, radiusY, rotation, startAngle, endAngle, anticlockwise) {
        if (!this.path) {
            this.beginPath();
        }
        this.path.ellipse(x, y, radiusX, radiusY, rotation, startAngle, endAngle, anticlockwise);
        this.path.element = null;
    },

    /**
     * Adds an arc with the given control points and radius to the current subpath, connected to the previous point by a straight line.
     * If two radii are provided, the first controls the width of the arc's ellipse, and the second controls the height. If only one is provided, or if they are the same, the arc is from a circle.
     * In the case of an ellipse, the rotation argument controls the clockwise inclination of the ellipse relative to the x-axis.
     * @param {Number} x1
     * @param {Number} y1
     * @param {Number} x2
     * @param {Number} y2
     * @param {Number} radiusX
     * @param {Number} radiusY
     * @param {Number} rotation
     */
    arcTo: function (x1, y1, x2, y2, radiusX, radiusY, rotation) {
        if (!this.path) {
            this.beginPath();
        }
        this.path.arcTo(x1, y1, x2, y2, radiusX, radiusY, rotation);
        this.path.element = null;
    },

    /**
     * Adds the given point to the current subpath, connected to the previous one by a cubic Bézier curve with the given control points.
     * @param {Number} x1
     * @param {Number} y1
     * @param {Number} x2
     * @param {Number} y2
     * @param {Number} x3
     * @param {Number} y3
     */
    bezierCurveTo: function (x1, y1, x2, y2, x3, y3) {
        if (!this.path) {
            this.beginPath();
        }
        this.path.bezierCurveTo(x1, y1, x2, y2, x3, y3);
        this.path.element = null;
    },

    /**
     * Strokes the given text at the given position. If a maximum width is provided, the text will be scaled to fit that width if necessary.
     * @param {String} text
     * @param {Number} x
     * @param {Number} y
     */
    strokeText: function (text, x, y) {
        text = String(text);
        if (this.strokeStyle) {
            var element = this.getElement('text'),
                tspan = this.surface.getSvgElement(element, 'tspan', 0);
            this.surface.setElementAttributes(element, {
                "x": x,
                "y": y,
                "transform": this.matrix.toSvg(),
                "stroke": this.strokeStyle,
                "fill": "none",
                "opacity": this.globalAlpha,
                "stroke-opacity": this.strokeOpacity,
                "style": "font: " + this.font,
                "stroke-dasharray": this.lineDash.join(','),
                "stroke-dashoffset": this.lineDashOffset
            });
            if (this.lineDash.length) {
                this.surface.setElementAttributes(element, {
                    "stroke-dasharray": this.lineDash.join(','),
                    "stroke-dashoffset": this.lineDashOffset
                });
            }
            if (tspan.dom.firstChild) {
                tspan.dom.removeChild(tspan.dom.firstChild);
            }
            this.surface.setElementAttributes(tspan, {
                "alignment-baseline": "alphabetic"
            });
            tspan.dom.appendChild(document.createTextNode(Ext.String.htmlDecode(text)));
        }
    },

    /**
     * Fills the given text at the given position. If a maximum width is provided, the text will be scaled to fit that width if necessary.
     * @param {String} text
     * @param {Number} x
     * @param {Number} y
     */
    fillText: function (text, x, y) {
        text = String(text);
        if (this.fillStyle) {
            var element = this.getElement('text'),
                tspan = this.surface.getSvgElement(element, 'tspan', 0);
            this.surface.setElementAttributes(element, {
                "x": x,
                "y": y,
                "transform": this.matrix.toSvg(),
                "fill": this.fillStyle,
                "opacity": this.globalAlpha,
                "fill-opacity": this.fillOpacity,
                "style": "font: " + this.font
            });
            if (tspan.dom.firstChild) {
                tspan.dom.removeChild(tspan.dom.firstChild);
            }
            this.surface.setElementAttributes(tspan, {
                "alignment-baseline": "alphabetic"
            });
            tspan.dom.appendChild(document.createTextNode(Ext.String.htmlDecode(text)));
        }
    },

    /**
     * Draws the given image onto the canvas.
     * If the first argument isn't an img, canvas, or video element, throws a TypeMismatchError exception. If the image has no image data, throws an InvalidStateError exception. If the one of the source rectangle dimensions is zero, throws an IndexSizeError exception. If the image isn't yet fully decoded, then nothing is drawn.
     * @param {HTMLElement} image
     * @param {Number} sx
     * @param {Number} sy
     * @param {Number} sw
     * @param {Number} sh
     * @param {Number} dx
     * @param {Number} dy
     * @param {Number} dw
     * @param {Number} dh
     */
    drawImage: function (image, sx, sy, sw, sh, dx, dy, dw, dh) {
        var me = this,
            element = me.getElement('image'),
            x = sx, y = sy,
            width = typeof sw === 'undefined' ? image.width : sw,
            height = typeof sh === 'undefined' ? image.height : sh,
            viewBox = null;
        if (typeof dh !== 'undefined') {
            viewBox = sx + " " + sy + " " + sw + " " + sh;
            x = dx;
            y = dy;
            width = dw;
            height = dh;
        }
        element.dom.setAttributeNS("http:/" + "/www.w3.org/1999/xlink", "href", image.src);
        me.surface.setElementAttributes(element, {
            viewBox: viewBox,
            x: x,
            y: y,
            width: width,
            height: height,
            opacity: me.globalAlpha,
            transform: me.matrix.toSvg()
        });
    },

    /**
     * Fills the subpaths of the current default path or the given path with the current fill style.
     */
    fill: function () {
        if (!this.path) {
            return;
        }
        if (this.fillStyle) {
            var path,
                fillGradient = this.fillGradient,
                bbox = this.bbox,
                element = this.path.element;
            if (!element) {
                path = this.path.toString();
                element = this.path.element = this.getElement('path');
                this.surface.setElementAttributes(element, {
                    "d": path,
                    "transform": this.matrix.toSvg()
                });
            }
            this.surface.setElementAttributes(element, {
                "fill": fillGradient && bbox ? fillGradient.generateGradient(this, bbox) : this.fillStyle,
                "fill-opacity": this.fillOpacity * this.globalAlpha
            });
        }
    },

    /**
     * Strokes the subpaths of the current default path or the given path with the current stroke style.
     */
    stroke: function () {
        if (!this.path) {
            return;
        }
        if (this.strokeStyle) {
            var path,
                strokeGradient = this.strokeGradient,
                bbox = this.bbox,
                element = this.path.element;
            if (!element || !this.path.svgString) {
                path = this.path.toString();
                if (!path) {
                    return;
                }
                element = this.path.element = this.getElement('path');
                this.surface.setElementAttributes(element, {
                    "fill": "none",
                    "d": path,
                    "transform": this.matrix.toSvg()
                });
            }
            this.surface.setElementAttributes(element, {
                "stroke": strokeGradient && bbox ? strokeGradient.generateGradient(this, bbox) : this.strokeStyle,
                "stroke-linecap": this.lineCap,
                "stroke-linejoin": this.lineJoin,
                "stroke-width": this.lineWidth,
                "stroke-opacity": this.strokeOpacity * this.globalAlpha,
                "stroke-dasharray": this.lineDash.join(','),
                "stroke-dashoffset": this.lineDashOffset
            });
            if (this.lineDash.length) {
                this.surface.setElementAttributes(element, {
                    "stroke-dasharray": this.lineDash.join(','),
                    "stroke-dashoffset": this.lineDashOffset
                });
            }
        }
    },

    /**
     * @protected
     *
     * Note: After the method guarantees the transform matrix will be inverted.
     * @param {Object} attr The attribute object
     * @param {Boolean} [transformFillStroke] Indicate whether to transform fill and stroke. If this is not
     *      given, then uses `attr.transformFillStroke` instead.
     */
    fillStroke: function (attr, transformFillStroke) {
        var ctx = this,
            fillStyle = ctx.fillStyle,
            strokeStyle = ctx.strokeStyle,
            fillOpacity = ctx.fillOpacity,
            strokeOpacity = ctx.strokeOpacity;

        if (transformFillStroke === undefined) {
            transformFillStroke = attr.transformFillStroke;
        }

        if (!transformFillStroke) {
            attr.inverseMatrix.toContext(ctx);
        }

        if (fillStyle && fillOpacity !== 0) {
            ctx.fill();
        }

        if (strokeStyle && strokeOpacity !== 0) {
            ctx.stroke();
        }
    },

    appendPath: function (path) {
        this.path = path.clone();
    },

    setLineDash: function (lineDash) {
        this.lineDash = lineDash;
    },

    getLineDash: function () {
        return this.lineDash;
    },

    /**
     * Returns an object that represents a linear gradient that paints along the line given by the coordinates represented by the arguments.
     * @param {Number} x0
     * @param {Number} y0
     * @param {Number} x1
     * @param {Number} y1
     * @return {Ext.draw.engine.SvgContext.Gradient}
     */
    createLinearGradient: function (x0, y0, x1, y1) {
        var me = this,
            element = me.surface.getNextDef('linearGradient'),
            gradients = me.group.dom.gradients || (me.group.dom.gradients = {}),
            gradient;
        me.surface.setElementAttributes(element, {
            "x1": x0,
            "y1": y0,
            "x2": x1,
            "y2": y1,
            "gradientUnits": "userSpaceOnUse"
        });
        gradient = new Ext.draw.engine.SvgContext.Gradient(me, me.surface, element);
        gradients[element.dom.id] = gradient;
        return gradient;
    },

    /**
     * Returns a CanvasGradient object that represents a radial gradient that paints along the cone given by the circles represented by the arguments.
     * If either of the radii are negative, throws an IndexSizeError exception.
     * @param {Number} x0
     * @param {Number} y0
     * @param {Number} r0
     * @param {Number} x1
     * @param {Number} y1
     * @param {Number} r1
     * @return {Ext.draw.engine.SvgContext.Gradient}
     */
    createRadialGradient: function (x0, y0, r0, x1, y1, r1) {
        var me = this,
            element = me.surface.getNextDef('radialGradient'),
            gradients = me.group.dom.gradients || (me.group.dom.gradients = {}),
            gradient;
        me.surface.setElementAttributes(element, {
            "fx": x0,
            "fy": y0,
            "cx": x1,
            "cy": y1,
            "r": r1,
            "gradientUnits": "userSpaceOnUse"
        });
        gradient = new Ext.draw.engine.SvgContext.Gradient(me, me.surface, element, r0 / r1);
        gradients[element.dom.id] = gradient;
        return gradient;
    }
});

/**
 * @class Ext.draw.engine.SvgContext.Gradient
 */
Ext.define("Ext.draw.engine.SvgContext.Gradient", {

    statics: {
        map: {}
    },

    constructor: function (ctx, surface, element, compression) {
        var map = this.statics().map,
            oldInstance;

        // Because of the way Ext.draw.engine.Svg.getNextDef works,
        // there is no guarantee that an existing DOM element from the 'defs' section won't be used
        // for the 'element' param.
        oldInstance = map[element.dom.id];
        if (oldInstance) {
            oldInstance.element = null;
        }
        map[element.dom.id] = this;

        this.ctx = ctx;
        this.surface = surface;
        this.element = element;
        this.position = 0;
        this.compression = compression || 0;
    },

    /**
     * Adds a color stop with the given color to the gradient at the given offset. 0.0 is the offset at one end of the gradient, 1.0 is the offset at the other end.
     * @param {Number} offset
     * @param {String} color
     */
    addColorStop: function (offset, color) {
        var stop = this.surface.getSvgElement(this.element, 'stop', this.position++),
            compression = this.compression;
        this.surface.setElementAttributes(stop, {
            "offset": (((1 - compression) * offset + compression) * 100).toFixed(2) + '%',
            "stop-color": color,
            "stop-opacity": Ext.draw.Color.fly(color).a.toFixed(15)
        });
    },

    toString: function () {
        var children = this.element.dom.childNodes;
        // Removing surplus stops in case existing gradient element with more stops was reused.
        while (children.length > this.position) {
            Ext.fly(children[children.length - 1]).destroy();
        }
        return 'url(#' + this.element.getId() + ')';
    },

    destroy: function () {
        var map = this.statics().map,
            element = this.element;
        if (element) {
            delete map[element.dom.id];
            element.destroy();
        }
        this.callParent();
    }
});

/**
 * @class Ext.draw.engine.Svg
 * @extends Ext.draw.Surface
 *
 * SVG engine.
 */
Ext.define('Ext.draw.engine.Svg', {
    extend: 'Ext.draw.Surface',
    requires: ['Ext.draw.engine.SvgContext'],

    statics: {
        BBoxTextCache: {}
    },

    config: {
        /**
         * Nothing needs to be done in high precision mode.
         */
        highPrecision: false
    },

    getElementConfig: function () {
        //TODO:ps In the Ext world, use renderTpl to create the children
        return {
            reference: 'element',
            style: {
                position: 'absolute'
            },
            children: [
                {
                    reference: 'innerElement',
                    style: {
                        width: '100%',
                        height: '100%',
                        position: 'relative'
                    },
                    children: [
                        {
                            tag: 'svg',
                            reference: 'svgElement',
                            namespace: "http://www.w3.org/2000/svg",
                            width: '100%',
                            height: '100%',
                            version: 1.1
                        }
                    ]
                }
            ]
        };
    },

    constructor: function (config) {
        var me = this;
        me.callParent([config]);
        me.mainGroup = me.createSvgNode("g");
        me.defElement = me.createSvgNode("defs");
        // me.svgElement is assigned in element creation of Ext.Component.
        me.svgElement.appendChild(me.mainGroup);
        me.svgElement.appendChild(me.defElement);
        me.ctx = new Ext.draw.engine.SvgContext(me);
    },

    /**
     * Creates a DOM element under the SVG namespace of the given type.
     * @param {String} type The type of the SVG DOM element.
     * @return {*} The created element.
     */
    createSvgNode: function (type) {
        var node = document.createElementNS("http://www.w3.org/2000/svg", type);
        return Ext.get(node);
    },

    /**
     * @private
     * Returns the SVG DOM element at the given position. If it does not already exist or is a different element tag
     * it will be created and inserted into the DOM.
     * @param {Ext.dom.Element} group The parent DOM element.
     * @param {String} tag The SVG element tag.
     * @param {Number} position The position of the element in the DOM.
     * @return {Ext.dom.Element} The SVG element.
     */
    getSvgElement: function (group, tag, position) {
        var element;
        if (group.dom.childNodes.length > position) {
            element = group.dom.childNodes[position];
            if (element.tagName === tag) {
                return Ext.get(element);
            } else {
                Ext.destroy(element);
            }
        }

        element = Ext.get(this.createSvgNode(tag));
        if (position === 0) {
            group.insertFirst(element);
        } else {
            element.insertAfter(Ext.fly(group.dom.childNodes[position - 1]));
        }
        element.cache = {};
        return element;
    },

    /**
     * @private
     * Applies attributes to the given element.
     * @param {Ext.dom.Element} element The DOM element to be applied.
     * @param {Object} attributes The attributes to apply to the element.
     */
    setElementAttributes: function (element, attributes) {
        var dom = element.dom,
            cache = element.cache,
            name, value;
        for (name in attributes) {
            value = attributes[name];
            if (cache[name] !== value) {
                cache[name] = value;
                dom.setAttribute(name, value);
            }
        }
    },

    /**
     * @private
     * Gets the next reference element under the SVG 'defs' tag.
     * @param {String} tagName The type of reference element.
     * @return {Ext.dom.Element} The reference element.
     */
    getNextDef: function (tagName) {
        return this.getSvgElement(this.defElement, tagName, this.defPosition++);
    },

    /**
     * @inheritdoc
     */
    clearTransform: function () {
        var me = this;
        me.mainGroup.set({transform: me.matrix.toSvg()});
    },

    /**
     * @inheritdoc
     */
    clear: function () {
        this.ctx.clear();
        this.defPosition = 0;
    },

    /**
     * @inheritdoc
     */
    renderSprite: function (sprite) {
        var me = this,
            rect = me.getRect(),
            ctx = me.ctx;
        if (sprite.attr.hidden || sprite.attr.opacity === 0) {
            ctx.save();
            ctx.restore();
            return;
        }
        sprite.element = ctx.save();
        sprite.preRender(this);
        sprite.useAttributes(ctx, rect);
        if (false === sprite.render(this, ctx, [0, 0, rect[2], rect[3]])) {
            return false;
        }
        sprite.setDirty(false);
        ctx.restore();
    },

    flatten: function (size, surfaces) {
        var svg = '<?xml version="1.0" standalone="yes"?>',
            className = Ext.getClassName(this),
            surface, rect, i;

        svg += '<svg version="1.1" baseProfile="full" xmlns="http://www.w3.org/2000/svg"' +
            ' width="' + size.width + '"' +
            ' height="' + size.height + '">';

        for (i = 0; i < surfaces.length; i++) {
            surface = surfaces[i];
            if (Ext.getClassName(surface) !== className) {
                continue;
            }
            rect = surface.getRect();
            svg += '<g transform="translate(' + rect[0] + ',' + rect[1] + ')">';
            svg += this.serializeNode(surface.svgElement.dom);
            svg += '</g>';
        }
        svg += '</svg>';
        return {
            data: 'data:image/svg+xml;utf8,' + encodeURIComponent(svg),
            type: 'svg'
        };
    },

    /**
     * @private
     * Serializes an SVG DOM element and its children recursively into a string.
     * @param {Object} node DOM element to serialize.
     * @returns {String}
     */
    serializeNode: function (node) {
        var result = '',
            i, n, attr, child;
        if (node.nodeType === document.TEXT_NODE) {
            return node.nodeValue;
        }
        result += '<' + node.nodeName;
        if (node.attributes.length) {
            for (i = 0, n = node.attributes.length; i < n; i++) {
                attr = node.attributes[i];
                result += ' ' + attr.name + '="' + attr.value + '"';
            }
        }
        result += '>';
        if (node.childNodes && node.childNodes.length) {
            for (i = 0, n = node.childNodes.length; i < n; i++) {
                child = node.childNodes[i];
                result += this.serializeNode(child);
            }
        }
        result += '</' + node.nodeName + '>';
        return result;
    },

    /**
     * Destroys the Canvas element and prepares it for Garbage Collection.
     */
    destroy: function (path, matrix, band) {
        var me = this;
        me.ctx.destroy();
        me.mainGroup.destroy();
        delete me.mainGroup;
        delete me.ctx;
        me.callParent(arguments);
    },

    remove: function (sprite, destroySprite) {
        if (sprite && sprite.element) {
            //if sprite has an associated svg element remove it from the surface
            if (this.ctx) {
                this.ctx.removeElement(sprite.element);
            } else {
                sprite.element.destroy();
            }
            sprite.element = null;
        }
        this.callParent(arguments);
    }
});

// @define Ext.draw.engine.excanvas
Ext.draw || (Ext.draw = {});
Ext.draw.engine || (Ext.draw.engine = {});
Ext.draw.engine.excanvas = true;

// Copyright 2006 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.


// Known Issues:
//
// * Patterns only support repeat.
// * Radial gradient are not implemented. The VML version of these look very
//   different from the canvas one.
// * Clipping paths are not implemented.
// * Coordsize. The width and height attribute have higher priority than the
//   width and height style values which isn't correct.
// * Painting mode isn't implemented.
// * Canvas width/height should is using content-box by default. IE in
//   Quirks mode will draw the canvas using border-box. Either change your
//   doctype to HTML5
//   (http://www.whatwg.org/specs/web-apps/current-work/#the-doctype)
//   or use Box Sizing Behavior from WebFX
//   (http://webfx.eae.net/dhtml/boxsizing/boxsizing.html)
// * Non uniform scaling does not correctly scale strokes.
// * Optimize. There is always room for speed improvements.

// Only add this code if we do not already have a canvas implementation
if (!document.createElement('canvas').getContext) {

(function() {

  // alias some functions to make (compiled) code shorter
  var m = Math;
  var mr = m.round;
  var ms = m.sin;
  var mc = m.cos;
  var abs = m.abs;
  var sqrt = m.sqrt;

  // this is used for sub pixel precision
  var Z = 10;
  var Z2 = Z / 2;

  var IE_VERSION = +navigator.userAgent.match(/MSIE ([\d.]+)?/)[1];

  /**
   * This funtion is assigned to the <canvas> elements as element.getContext().
   * @this {HTMLElement}
   * @return {CanvasRenderingContext2D_}
   */
  function getContext() {
    return this.context_ ||
        (this.context_ = new CanvasRenderingContext2D_(this));
  }

  var slice = Array.prototype.slice;

  /**
   * Binds a function to an object. The returned function will always use the
   * passed in {@code obj} as {@code this}.
   *
   * Example:
   *
   *   g = bind(f, obj, a, b)
   *   g(c, d) // will do f.call(obj, a, b, c, d)
   *
   * @param {Function} f The function to bind the object to
   * @param {Object} obj The object that should act as this when the function
   *     is called
   * @param {*} var_args Rest arguments that will be used as the initial
   *     arguments when the function is called
   * @return {Function} A new function that has bound this
   */
  function bind(f, obj, var_args) {
    var a = slice.call(arguments, 2);
    return function() {
      return f.apply(obj, a.concat(slice.call(arguments)));
    };
  }

  function encodeHtmlAttribute(s) {
    return String(s).replace(/&/g, '&amp;').replace(/"/g, '&quot;');
  }

  function addNamespace(doc, prefix, urn) {
    Ext.onReady(function() {
      if (!doc.namespaces[prefix]) {
        doc.namespaces.add(prefix, urn, '#default#VML');
      }
    });
  }

  function addNamespacesAndStylesheet(doc) {
    addNamespace(doc, 'g_vml_', 'urn:schemas-microsoft-com:vml');
    addNamespace(doc, 'g_o_', 'urn:schemas-microsoft-com:office:office');

    // Setup default CSS.  Only add one style sheet per document
    if (!doc.styleSheets['ex_canvas_']) {
      var ss = doc.createStyleSheet();
      ss.owningElement.id = 'ex_canvas_';
      ss.cssText = 'canvas{display:inline-block;overflow:hidden;' +
          // default size is 300x150 in Gecko and Opera
          'text-align:left;width:300px;height:150px}';
    }
  }

  // Add namespaces and stylesheet at startup.
  addNamespacesAndStylesheet(document);

  var G_vmlCanvasManager_ = {
    init: function(opt_doc) {
      var doc = opt_doc || document;
      // Create a dummy element so that IE will allow canvas elements to be
      // recognized.
      doc.createElement('canvas');
      doc.attachEvent('onreadystatechange', bind(this.init_, this, doc));
    },

    init_: function(doc) {
      // find all canvas elements
      var els = doc.getElementsByTagName('canvas');
      for (var i = 0; i < els.length; i++) {
        this.initElement(els[i]);
      }
    },

    /**
     * Public initializes a canvas element so that it can be used as canvas
     * element from now on. This is called automatically before the page is
     * loaded but if you are creating elements using createElement you need to
     * make sure this is called on the element.
     * @param {HTMLElement} el The canvas element to initialize.
     * @return {HTMLElement} the element that was created.
     */
    initElement: function(el) {
      if (!el.getContext) {
        el.getContext = getContext;

        // Add namespaces and stylesheet to document of the element.
        addNamespacesAndStylesheet(el.ownerDocument);

        // Remove fallback content. There is no way to hide text nodes so we
        // just remove all childNodes. We could hide all elements and remove
        // text nodes but who really cares about the fallback content.
        el.innerHTML = '';

        // do not use inline function because that will leak memory
        el.attachEvent('onpropertychange', onPropertyChange);
        el.attachEvent('onresize', onResize);

        var attrs = el.attributes;
        if (attrs.width && attrs.width.specified) {
          // TODO: use runtimeStyle and coordsize
          // el.getContext().setWidth_(attrs.width.nodeValue);
          el.style.width = attrs.width.nodeValue + 'px';
        } else {
          el.width = el.clientWidth;
        }
        if (attrs.height && attrs.height.specified) {
          // TODO: use runtimeStyle and coordsize
          // el.getContext().setHeight_(attrs.height.nodeValue);
          el.style.height = attrs.height.nodeValue + 'px';
        } else {
          el.height = el.clientHeight;
        }
        //el.getContext().setCoordsize_()
      }
      return el;
    }
  };

  function onPropertyChange(e) {
    var el = e.srcElement;

    switch (e.propertyName) {
      case 'width':
        el.getContext().clearRect();
        el.style.width = el.attributes.width.nodeValue + 'px';
        // In IE8 this does not trigger onresize.
        el.firstChild.style.width =  el.clientWidth + 'px';
        break;
      case 'height':
        el.getContext().clearRect();
        el.style.height = el.attributes.height.nodeValue + 'px';
        el.firstChild.style.height = el.clientHeight + 'px';
        break;
    }
  }

  function onResize(e) {
    var el = e.srcElement;
    if (el.firstChild) {
      el.firstChild.style.width =  el.clientWidth + 'px';
      el.firstChild.style.height = el.clientHeight + 'px';
    }
  }

  G_vmlCanvasManager_.init();

  // precompute "00" to "FF"
  var decToHex = [];
  for (var i = 0; i < 16; i++) {
    for (var j = 0; j < 16; j++) {
      decToHex[i * 16 + j] = i.toString(16) + j.toString(16);
    }
  }

  function createMatrixIdentity() {
    return [
      [1, 0, 0],
      [0, 1, 0],
      [0, 0, 1]
    ];
  }

  function matrixMultiply(m1, m2) {
    var result = createMatrixIdentity();

    for (var x = 0; x < 3; x++) {
      for (var y = 0; y < 3; y++) {
        var sum = 0;

        for (var z = 0; z < 3; z++) {
          sum += m1[x][z] * m2[z][y];
        }

        result[x][y] = sum;
      }
    }
    return result;
  }

  function copyState(o1, o2) {
    o2.fillStyle     = o1.fillStyle;
    o2.lineCap       = o1.lineCap;
    o2.lineJoin      = o1.lineJoin;
    o2.lineDash      = o1.lineDash;
    o2.lineWidth     = o1.lineWidth;
    o2.miterLimit    = o1.miterLimit;
    o2.shadowBlur    = o1.shadowBlur;
    o2.shadowColor   = o1.shadowColor;
    o2.shadowOffsetX = o1.shadowOffsetX;
    o2.shadowOffsetY = o1.shadowOffsetY;
    o2.strokeStyle   = o1.strokeStyle;
    o2.globalAlpha   = o1.globalAlpha;
    o2.font          = o1.font;
    o2.textAlign     = o1.textAlign;
    o2.textBaseline  = o1.textBaseline;
    o2.arcScaleX_    = o1.arcScaleX_;
    o2.arcScaleY_    = o1.arcScaleY_;
    o2.lineScale_    = o1.lineScale_;
  }

  var colorData = {
    aliceblue: '#F0F8FF',
    antiquewhite: '#FAEBD7',
    aquamarine: '#7FFFD4',
    azure: '#F0FFFF',
    beige: '#F5F5DC',
    bisque: '#FFE4C4',
    black: '#000000',
    blanchedalmond: '#FFEBCD',
    blueviolet: '#8A2BE2',
    brown: '#A52A2A',
    burlywood: '#DEB887',
    cadetblue: '#5F9EA0',
    chartreuse: '#7FFF00',
    chocolate: '#D2691E',
    coral: '#FF7F50',
    cornflowerblue: '#6495ED',
    cornsilk: '#FFF8DC',
    crimson: '#DC143C',
    cyan: '#00FFFF',
    darkblue: '#00008B',
    darkcyan: '#008B8B',
    darkgoldenrod: '#B8860B',
    darkgray: '#A9A9A9',
    darkgreen: '#006400',
    darkgrey: '#A9A9A9',
    darkkhaki: '#BDB76B',
    darkmagenta: '#8B008B',
    darkolivegreen: '#556B2F',
    darkorange: '#FF8C00',
    darkorchid: '#9932CC',
    darkred: '#8B0000',
    darksalmon: '#E9967A',
    darkseagreen: '#8FBC8F',
    darkslateblue: '#483D8B',
    darkslategray: '#2F4F4F',
    darkslategrey: '#2F4F4F',
    darkturquoise: '#00CED1',
    darkviolet: '#9400D3',
    deeppink: '#FF1493',
    deepskyblue: '#00BFFF',
    dimgray: '#696969',
    dimgrey: '#696969',
    dodgerblue: '#1E90FF',
    firebrick: '#B22222',
    floralwhite: '#FFFAF0',
    forestgreen: '#228B22',
    gainsboro: '#DCDCDC',
    ghostwhite: '#F8F8FF',
    gold: '#FFD700',
    goldenrod: '#DAA520',
    grey: '#808080',
    greenyellow: '#ADFF2F',
    honeydew: '#F0FFF0',
    hotpink: '#FF69B4',
    indianred: '#CD5C5C',
    indigo: '#4B0082',
    ivory: '#FFFFF0',
    khaki: '#F0E68C',
    lavender: '#E6E6FA',
    lavenderblush: '#FFF0F5',
    lawngreen: '#7CFC00',
    lemonchiffon: '#FFFACD',
    lightblue: '#ADD8E6',
    lightcoral: '#F08080',
    lightcyan: '#E0FFFF',
    lightgoldenrodyellow: '#FAFAD2',
    lightgreen: '#90EE90',
    lightgrey: '#D3D3D3',
    lightpink: '#FFB6C1',
    lightsalmon: '#FFA07A',
    lightseagreen: '#20B2AA',
    lightskyblue: '#87CEFA',
    lightslategray: '#778899',
    lightslategrey: '#778899',
    lightsteelblue: '#B0C4DE',
    lightyellow: '#FFFFE0',
    limegreen: '#32CD32',
    linen: '#FAF0E6',
    magenta: '#FF00FF',
    mediumaquamarine: '#66CDAA',
    mediumblue: '#0000CD',
    mediumorchid: '#BA55D3',
    mediumpurple: '#9370DB',
    mediumseagreen: '#3CB371',
    mediumslateblue: '#7B68EE',
    mediumspringgreen: '#00FA9A',
    mediumturquoise: '#48D1CC',
    mediumvioletred: '#C71585',
    midnightblue: '#191970',
    mintcream: '#F5FFFA',
    mistyrose: '#FFE4E1',
    moccasin: '#FFE4B5',
    navajowhite: '#FFDEAD',
    oldlace: '#FDF5E6',
    olivedrab: '#6B8E23',
    orange: '#FFA500',
    orangered: '#FF4500',
    orchid: '#DA70D6',
    palegoldenrod: '#EEE8AA',
    palegreen: '#98FB98',
    paleturquoise: '#AFEEEE',
    palevioletred: '#DB7093',
    papayawhip: '#FFEFD5',
    peachpuff: '#FFDAB9',
    peru: '#CD853F',
    pink: '#FFC0CB',
    plum: '#DDA0DD',
    powderblue: '#B0E0E6',
    rosybrown: '#BC8F8F',
    royalblue: '#4169E1',
    saddlebrown: '#8B4513',
    salmon: '#FA8072',
    sandybrown: '#F4A460',
    seagreen: '#2E8B57',
    seashell: '#FFF5EE',
    sienna: '#A0522D',
    skyblue: '#87CEEB',
    slateblue: '#6A5ACD',
    slategray: '#708090',
    slategrey: '#708090',
    snow: '#FFFAFA',
    springgreen: '#00FF7F',
    steelblue: '#4682B4',
    tan: '#D2B48C',
    thistle: '#D8BFD8',
    tomato: '#FF6347',
    turquoise: '#40E0D0',
    violet: '#EE82EE',
    wheat: '#F5DEB3',
    whitesmoke: '#F5F5F5',
    yellowgreen: '#9ACD32'
  };


  function getRgbHslContent(styleString) {
    var start = styleString.indexOf('(', 3);
    var end = styleString.indexOf(')', start + 1);
    var parts = styleString.substring(start + 1, end).split(',');
    // add alpha if needed
    if (parts.length != 4 || styleString.charAt(3) != 'a') {
      parts[3] = 1;
    }
    return parts;
  }

  function percent(s) {
    return parseFloat(s) / 100;
  }

  function clamp(v, min, max) {
    return Math.min(max, Math.max(min, v));
  }

  function hslToRgb(parts){
    var r, g, b, h, s, l;
    h = parseFloat(parts[0]) / 360 % 360;
    if (h < 0)
      h++;
    s = clamp(percent(parts[1]), 0, 1);
    l = clamp(percent(parts[2]), 0, 1);
    if (s == 0) {
      r = g = b = l; // achromatic
    } else {
      var q = l < 0.5 ? l * (1 + s) : l + s - l * s;
      var p = 2 * l - q;
      r = hueToRgb(p, q, h + 1 / 3);
      g = hueToRgb(p, q, h);
      b = hueToRgb(p, q, h - 1 / 3);
    }

    return '#' + decToHex[Math.floor(r * 255)] +
        decToHex[Math.floor(g * 255)] +
        decToHex[Math.floor(b * 255)];
  }

  function hueToRgb(m1, m2, h) {
    if (h < 0)
      h++;
    if (h > 1)
      h--;

    if (6 * h < 1)
      return m1 + (m2 - m1) * 6 * h;
    else if (2 * h < 1)
      return m2;
    else if (3 * h < 2)
      return m1 + (m2 - m1) * (2 / 3 - h) * 6;
    else
      return m1;
  }

  var processStyleCache = {};

  function processStyle(styleString) {
    if (styleString in processStyleCache) {
      return processStyleCache[styleString];
    }

    var str, alpha = 1;

    styleString = String(styleString);
    if (styleString.charAt(0) == '#') {
      str = styleString;
    } else if (/^rgb/.test(styleString)) {
      var parts = getRgbHslContent(styleString);
      var str = '#', n;
      for (var i = 0; i < 3; i++) {
        if (parts[i].indexOf('%') != -1) {
          n = Math.floor(percent(parts[i]) * 255);
        } else {
          n = +parts[i];
        }
        str += decToHex[clamp(n, 0, 255)];
      }
      alpha = +parts[3];
    } else if (/^hsl/.test(styleString)) {
      var parts = getRgbHslContent(styleString);
      str = hslToRgb(parts);
      alpha = parts[3];
    } else {
      str = colorData[styleString] || styleString;
    }
    return processStyleCache[styleString] = {color: str, alpha: alpha};
  }

  var DEFAULT_STYLE = {
    style: 'normal',
    variant: 'normal',
    weight: 'normal',
    size: 10,
    family: 'sans-serif'
  };

  // Internal text style cache
  var fontStyleCache = {};

  function processFontStyle(styleString) {
    if (fontStyleCache[styleString]) {
      return fontStyleCache[styleString];
    }

    var el = document.createElement('div');
    var style = el.style;
    try {
      style.font = styleString;
    } catch (ex) {
      // Ignore failures to set to invalid font.
    }

    return fontStyleCache[styleString] = {
      style: style.fontStyle || DEFAULT_STYLE.style,
      variant: style.fontVariant || DEFAULT_STYLE.variant,
      weight: style.fontWeight || DEFAULT_STYLE.weight,
      size: style.fontSize || DEFAULT_STYLE.size,
      family: style.fontFamily || DEFAULT_STYLE.family
    };
  }

  function getComputedStyle(style, element) {
    var computedStyle = {};

    for (var p in style) {
      computedStyle[p] = style[p];
    }

    // Compute the size
    var canvasFontSize = parseFloat(element.currentStyle.fontSize),
        fontSize = parseFloat(style.size);

    if (typeof style.size == 'number') {
      computedStyle.size = style.size;
    } else if (style.size.indexOf('px') != -1) {
      computedStyle.size = fontSize;
    } else if (style.size.indexOf('em') != -1) {
      computedStyle.size = canvasFontSize * fontSize;
    } else if(style.size.indexOf('%') != -1) {
      computedStyle.size = (canvasFontSize / 100) * fontSize;
    } else if (style.size.indexOf('pt') != -1) {
      computedStyle.size = fontSize / .75;
    } else {
      computedStyle.size = canvasFontSize;
    }

    // Different scaling between normal text and VML text. This was found using
    // trial and error to get the same size as non VML text.
    computedStyle.size *= 0.981;

    return computedStyle;
  }

  function buildStyle(style) {
    return style.style + ' ' + style.variant + ' ' + style.weight + ' ' +
        style.size + 'px ' + style.family;
  }

  var lineCapMap = {
    'butt': 'flat',
    'round': 'round'
  };

  function processLineCap(lineCap) {
    return lineCapMap[lineCap] || 'square';
  }

  /**
   * This class implements CanvasRenderingContext2D interface as described by
   * the WHATWG.
   * @param {HTMLElement} canvasElement The element that the 2D context should
   * be associated with
   */
  function CanvasRenderingContext2D_(canvasElement) {
    this.m_ = createMatrixIdentity();

    this.mStack_ = [];
    this.aStack_ = [];
    this.currentPath_ = [];

    // Canvas context properties
    this.strokeStyle = '#000';
    this.fillStyle = '#000';

    this.lineWidth = 1;
    this.lineJoin = 'miter';
    this.lineDash = [];
    this.lineCap = 'butt';
    this.miterLimit = Z * 1;
    this.globalAlpha = 1;
    this.font = '10px sans-serif';
    this.textAlign = 'left';
    this.textBaseline = 'alphabetic';
    this.canvas = canvasElement;

    var cssText = 'width:' + canvasElement.clientWidth + 'px;height:' +
        canvasElement.clientHeight + 'px;overflow:hidden;position:absolute';
    var el = canvasElement.ownerDocument.createElement('div');
    el.style.cssText = cssText;
    canvasElement.appendChild(el);

    var overlayEl = el.cloneNode(false);
    // Use a non transparent background.
    overlayEl.style.backgroundColor = 'red';
    overlayEl.style.filter = 'alpha(opacity=0)';
    canvasElement.appendChild(overlayEl);

    this.element_ = el;
    this.arcScaleX_ = 1;
    this.arcScaleY_ = 1;
    this.lineScale_ = 1;
  }

  var contextPrototype = CanvasRenderingContext2D_.prototype;
  contextPrototype.clearRect = function() {
    if (this.textMeasureEl_) {
      this.textMeasureEl_.removeNode(true);
      this.textMeasureEl_ = null;
    }
    this.element_.innerHTML = '';
  };

  contextPrototype.beginPath = function() {
    // TODO: Branch current matrix so that save/restore has no effect
    //       as per safari docs.
    this.currentPath_ = [];
  };

  contextPrototype.moveTo = function(aX, aY) {
    var p = getCoords(this, aX, aY);
    this.currentPath_.push({type: 'moveTo', x: p.x, y: p.y});
    this.currentX_ = p.x;
    this.currentY_ = p.y;
  };

  contextPrototype.lineTo = function(aX, aY) {
    var p = getCoords(this, aX, aY);
    this.currentPath_.push({type: 'lineTo', x: p.x, y: p.y});

    this.currentX_ = p.x;
    this.currentY_ = p.y;
  };

  contextPrototype.bezierCurveTo = function(aCP1x, aCP1y,
                                            aCP2x, aCP2y,
                                            aX, aY) {
    var p = getCoords(this, aX, aY);
    var cp1 = getCoords(this, aCP1x, aCP1y);
    var cp2 = getCoords(this, aCP2x, aCP2y);
    bezierCurveTo(this, cp1, cp2, p);
  };

  // Helper function that takes the already fixed cordinates.
  function bezierCurveTo(self, cp1, cp2, p) {
    self.currentPath_.push({
      type: 'bezierCurveTo',
      cp1x: cp1.x,
      cp1y: cp1.y,
      cp2x: cp2.x,
      cp2y: cp2.y,
      x: p.x,
      y: p.y
    });
    self.currentX_ = p.x;
    self.currentY_ = p.y;
  }

  contextPrototype.quadraticCurveTo = function(aCPx, aCPy, aX, aY) {
    // the following is lifted almost directly from
    // http://developer.mozilla.org/en/docs/Canvas_tutorial:Drawing_shapes

    var cp = getCoords(this, aCPx, aCPy);
    var p = getCoords(this, aX, aY);

    var cp1 = {
      x: this.currentX_ + 2.0 / 3.0 * (cp.x - this.currentX_),
      y: this.currentY_ + 2.0 / 3.0 * (cp.y - this.currentY_)
    };
    var cp2 = {
      x: cp1.x + (p.x - this.currentX_) / 3.0,
      y: cp1.y + (p.y - this.currentY_) / 3.0
    };

    bezierCurveTo(this, cp1, cp2, p);
  };

  contextPrototype.arc = function(aX, aY, aRadius,
                                  aStartAngle, aEndAngle, aClockwise) {
    aRadius *= Z;
    var arcType = aClockwise ? 'at' : 'wa';

    var xStart = aX + mc(aStartAngle) * aRadius - Z2;
    var yStart = aY + ms(aStartAngle) * aRadius - Z2;

    var xEnd = aX + mc(aEndAngle) * aRadius - Z2;
    var yEnd = aY + ms(aEndAngle) * aRadius - Z2;

    // IE won't render arches drawn counter clockwise if xStart == xEnd.
    if (xStart == xEnd && !aClockwise) {
      xStart += 0.125; // Offset xStart by 1/80 of a pixel. Use something
                       // that can be represented in binary
    }

    var p = getCoords(this, aX, aY);
    var pStart = getCoords(this, xStart, yStart);
    var pEnd = getCoords(this, xEnd, yEnd);

    this.currentPath_.push({type: arcType,
                           x: p.x,
                           y: p.y,
                           radius: aRadius,
                           xStart: pStart.x,
                           yStart: pStart.y,
                           xEnd: pEnd.x,
                           yEnd: pEnd.y});

  };

  contextPrototype.rect = function(aX, aY, aWidth, aHeight) {
    this.moveTo(aX, aY);
    this.lineTo(aX + aWidth, aY);
    this.lineTo(aX + aWidth, aY + aHeight);
    this.lineTo(aX, aY + aHeight);
    this.closePath();
  };

  contextPrototype.strokeRect = function(aX, aY, aWidth, aHeight) {
    var oldPath = this.currentPath_;
    this.beginPath();

    this.moveTo(aX, aY);
    this.lineTo(aX + aWidth, aY);
    this.lineTo(aX + aWidth, aY + aHeight);
    this.lineTo(aX, aY + aHeight);
    this.closePath();
    this.stroke();

    this.currentPath_ = oldPath;
  };

  contextPrototype.fillRect = function(aX, aY, aWidth, aHeight) {
    var oldPath = this.currentPath_;
    this.beginPath();

    this.moveTo(aX, aY);
    this.lineTo(aX + aWidth, aY);
    this.lineTo(aX + aWidth, aY + aHeight);
    this.lineTo(aX, aY + aHeight);
    this.closePath();
    this.fill();

    this.currentPath_ = oldPath;
  };

  contextPrototype.createLinearGradient = function(aX0, aY0, aX1, aY1) {
    var gradient = new CanvasGradient_('gradient');
    gradient.x0_ = aX0;
    gradient.y0_ = aY0;
    gradient.x1_ = aX1;
    gradient.y1_ = aY1;
    return gradient;
  };

  contextPrototype.createRadialGradient = function(aX0, aY0, aR0,
                                                   aX1, aY1, aR1) {
    var gradient = new CanvasGradient_('gradientradial');
    gradient.x0_ = aX0;
    gradient.y0_ = aY0;
    gradient.r0_ = aR0;
    gradient.x1_ = aX1;
    gradient.y1_ = aY1;
    gradient.r1_ = aR1;
    return gradient;
  };

  contextPrototype.drawImage = function(image, var_args) {
    var dx, dy, dw, dh, sx, sy, sw, sh;

    // to find the original width we overide the width and height
    var oldRuntimeWidth = image.runtimeStyle.width;
    var oldRuntimeHeight = image.runtimeStyle.height;
    image.runtimeStyle.width = 'auto';
    image.runtimeStyle.height = 'auto';

    // get the original size
    var w = image.width;
    var h = image.height;

    // and remove overides
    image.runtimeStyle.width = oldRuntimeWidth;
    image.runtimeStyle.height = oldRuntimeHeight;

    if (arguments.length == 3) {
      dx = arguments[1];
      dy = arguments[2];
      sx = sy = 0;
      sw = dw = w;
      sh = dh = h;
    } else if (arguments.length == 5) {
      dx = arguments[1];
      dy = arguments[2];
      dw = arguments[3];
      dh = arguments[4];
      sx = sy = 0;
      sw = w;
      sh = h;
    } else if (arguments.length == 9) {
      sx = arguments[1];
      sy = arguments[2];
      sw = arguments[3];
      sh = arguments[4];
      dx = arguments[5];
      dy = arguments[6];
      dw = arguments[7];
      dh = arguments[8];
    } else {
      throw Error('Invalid number of arguments');
    }

    var d = getCoords(this, dx, dy);

    var vmlStr = [];

    var W = 10;
    var H = 10;

    var m = this.m_;

    vmlStr.push(' <g_vml_:group',
                ' coordsize="', Z * W, ',', Z * H, '"',
                ' coordorigin="0,0"' ,
                ' style="width:', mr(W*m[0][0]), 'px;height:', mr(H*m[1][1]), 'px;position:absolute;',
                'top:', mr(d.y / Z), 'px;left:', mr(d.x / Z), 'px; rotation:', mr(Math.atan(m[0][1]/m[1][1])*180/Math.PI), ';');

    vmlStr.push('" >' ,
                '<g_vml_:image src="', image.src, '"',
                ' style="width:', Z * dw, 'px;',
                ' height:', Z * dh, 'px"',
                ' cropleft="', sx / w, '"',
                ' croptop="', sy / h, '"',
                ' cropright="', (w - sx - sw) / w, '"',
                ' cropbottom="', (h - sy - sh) / h, '"',
                ' />',
                '</g_vml_:group>');

    this.element_.insertAdjacentHTML('BeforeEnd', vmlStr.join(''));
  };

  contextPrototype.setLineDash = function (lineDash) {
      if (lineDash.length === 1) {
          lineDash = lineDash.slice();
          lineDash[1] = lineDash[0];
      }
      this.lineDash = lineDash;
  };

  contextPrototype.getLineDash = function () {
      return this.lineDash;
  };

  contextPrototype.stroke = function(aFill) {
    var lineStr = [];
    var W = 10;
    var H = 10;

    lineStr.push('<g_vml_:shape',
                 ' filled="', !!aFill, '"',
                 ' style="position:absolute;width:', W, 'px;height:', H, 'px;left:0px;top:0px;"',
                 ' coordorigin="0,0"',
                 ' coordsize="', Z * W, ',', Z * H, '"',
                 ' stroked="', !aFill, '"',
                 ' path="');

    var min = {x: null, y: null};
    var max = {x: null, y: null};

    for (var i = 0; i < this.currentPath_.length; i++) {
      var p = this.currentPath_[i];
      var c;

      switch (p.type) {
        case 'moveTo':
          c = p;
          lineStr.push(' m ', mr(p.x), ',', mr(p.y));
          break;
        case 'lineTo':
          lineStr.push(' l ', mr(p.x), ',', mr(p.y));
          break;
        case 'close':
          lineStr.push(' x ');
          p = null;
          break;
        case 'bezierCurveTo':
          lineStr.push(' c ',
                       mr(p.cp1x), ',', mr(p.cp1y), ',',
                       mr(p.cp2x), ',', mr(p.cp2y), ',',
                       mr(p.x), ',', mr(p.y));
          break;
        case 'at':
        case 'wa':
          lineStr.push(' ', p.type, ' ',
                       mr(p.x - this.arcScaleX_ * p.radius), ',',
                       mr(p.y - this.arcScaleY_ * p.radius), ' ',
                       mr(p.x + this.arcScaleX_ * p.radius), ',',
                       mr(p.y + this.arcScaleY_ * p.radius), ' ',
                       mr(p.xStart), ',', mr(p.yStart), ' ',
                       mr(p.xEnd), ',', mr(p.yEnd));
          break;
      }


      // TODO: Following is broken for curves due to
      //       move to proper paths.

      // Figure out dimensions so we can do gradient fills
      // properly
      if (p) {
        if (min.x == null || p.x < min.x) {
          min.x = p.x;
        }
        if (max.x == null || p.x > max.x) {
          max.x = p.x;
        }
        if (min.y == null || p.y < min.y) {
          min.y = p.y;
        }
        if (max.y == null || p.y > max.y) {
          max.y = p.y;
        }
      }
    }
    lineStr.push(' ">');

    if (!aFill) {
      appendStroke(this, lineStr);
    } else {
      appendFill(this, lineStr, min, max);
    }

    lineStr.push('</g_vml_:shape>');

    this.element_.insertAdjacentHTML('beforeEnd', lineStr.join(''));
  };

  function appendStroke(ctx, lineStr) {
    var a = processStyle(ctx.strokeStyle);
    var color = a.color;
    var opacity = a.alpha * ctx.globalAlpha;
    var lineWidth = ctx.lineScale_ * ctx.lineWidth;

    // VML cannot correctly render a line if the width is less than 1px.
    // In that case, we dilute the color to make the line look thinner.
    if (lineWidth < 1) {
      opacity *= lineWidth;
    }

    lineStr.push(
      '<g_vml_:stroke',
      ' opacity="', opacity, '"',
      ' joinstyle="', ctx.lineJoin, '"',
      ' dashstyle="', ctx.lineDash.join(' '), '"',
      ' miterlimit="', ctx.miterLimit, '"',
      ' endcap="', processLineCap(ctx.lineCap), '"',
      ' weight="', lineWidth, 'px"',
      ' color="', color, '" />'
    );
  }

  function appendFill(ctx, lineStr, min, max) {
    var fillStyle = ctx.fillStyle;
    var arcScaleX = ctx.arcScaleX_;
    var arcScaleY = ctx.arcScaleY_;
    var width = max.x - min.x;
    var height = max.y - min.y;
    if (fillStyle instanceof CanvasGradient_) {
      // TODO: Gradients transformed with the transformation matrix.
      var angle = 0;
      var focus = {x: 0, y: 0};

      // additional offset
      var shift = 0;
      // scale factor for offset
      var expansion = 1;

      if (fillStyle.type_ == 'gradient') {
        var x0 = fillStyle.x0_ / arcScaleX;
        var y0 = fillStyle.y0_ / arcScaleY;
        var x1 = fillStyle.x1_ / arcScaleX;
        var y1 = fillStyle.y1_ / arcScaleY;
        var p0 = getCoords(ctx, x0, y0);
        var p1 = getCoords(ctx, x1, y1);
        var dx = p1.x - p0.x;
        var dy = p1.y - p0.y;
        angle = Math.atan2(dx, dy) * 180 / Math.PI;

        // The angle should be a non-negative number.
        if (angle < 0) {
          angle += 360;
        }

        // Very small angles produce an unexpected result because they are
        // converted to a scientific notation string.
        if (angle < 1e-6) {
          angle = 0;
        }
      } else {
        var p0 = getCoords(ctx, fillStyle.x0_, fillStyle.y0_);
        focus = {
          x: (p0.x - min.x) / width,
          y: (p0.y - min.y) / height
        };

        width  /= arcScaleX * Z;
        height /= arcScaleY * Z;
        var dimension = m.max(width, height);
        shift = 2 * fillStyle.r0_ / dimension;
        expansion = 2 * fillStyle.r1_ / dimension - shift;
      }

      // We need to sort the color stops in ascending order by offset,
      // otherwise IE won't interpret it correctly.
      var stops = fillStyle.colors_;
      stops.sort(function(cs1, cs2) {
        return cs1.offset - cs2.offset;
      });

      var length = stops.length;
      var color1 = stops[0].color;
      var color2 = stops[length - 1].color;
      var opacity1 = stops[0].alpha * ctx.globalAlpha;
      var opacity2 = stops[length - 1].alpha * ctx.globalAlpha;

      var colors = [];
      for (var i = 0; i < length; i++) {
        var stop = stops[i];
        colors.push(stop.offset * expansion + shift + ' ' + stop.color);
      }

      // When colors attribute is used, the meanings of opacity and o:opacity2
      // are reversed.
      lineStr.push('<g_vml_:fill type="', fillStyle.type_, '"',
                   ' method="none" focus="100%"',
                   ' color="', color1, '"',
                   ' color2="', color2, '"',
                   ' colors="', colors.join(','), '"',
                   ' opacity="', opacity2, '"',
                   ' g_o_:opacity2="', opacity1, '"',
                   ' angle="', angle, '"',
                   ' focusposition="', focus.x, ',', focus.y, '" />');
    } else if (fillStyle instanceof CanvasPattern_) {
      if (width && height) {
        var deltaLeft = -min.x;
        var deltaTop = -min.y;
        lineStr.push('<g_vml_:fill',
                     ' position="',
                     deltaLeft / width * arcScaleX * arcScaleX, ',',
                     deltaTop / height * arcScaleY * arcScaleY, '"',
                     ' type="tile"',
                     // TODO: Figure out the correct size to fit the scale.
                     //' size="', w, 'px ', h, 'px"',
                     ' src="', fillStyle.src_, '" />');
       }
    } else {
      var a = processStyle(ctx.fillStyle);
      var color = a.color;
      var opacity = a.alpha * ctx.globalAlpha;
      lineStr.push('<g_vml_:fill color="', color, '" opacity="', opacity,
                   '" />');
    }
  }

  contextPrototype.fill = function() {
    // Calling `$stroke` here because otherwise we'd call not the native ctx.stroke,
    // but our override from Ext.draw.engine.Canvas.statics.contextOverrides.
    this.$stroke(true);
  };

  contextPrototype.closePath = function() {
    this.currentPath_.push({type: 'close'});
  };

  function getCoords(ctx, aX, aY) {
    var m = ctx.m_;
    return {
      x: Z * (aX * m[0][0] + aY * m[1][0] + m[2][0]) - Z2,
      y: Z * (aX * m[0][1] + aY * m[1][1] + m[2][1]) - Z2
    };
  }

  contextPrototype.save = function() {
    var o = {};
    copyState(this, o);
    this.aStack_.push(o);
    this.mStack_.push(this.m_);
    this.m_ = matrixMultiply(createMatrixIdentity(), this.m_);
  };

  contextPrototype.restore = function() {
    if (this.aStack_.length) {
      copyState(this.aStack_.pop(), this);
      this.m_ = this.mStack_.pop();
    }
  };

  function matrixIsFinite(m) {
    return isFinite(m[0][0]) && isFinite(m[0][1]) &&
        isFinite(m[1][0]) && isFinite(m[1][1]) &&
        isFinite(m[2][0]) && isFinite(m[2][1]);
  }

  function setM(ctx, m, updateLineScale) {
    if (!matrixIsFinite(m)) {
      return;
    }
    ctx.m_ = m;

    if (updateLineScale) {
      // Get the line scale.
      // Determinant of this.m_ means how much the area is enlarged by the
      // transformation. So its square root can be used as a scale factor
      // for width.
      var det = m[0][0] * m[1][1] - m[0][1] * m[1][0];
      ctx.lineScale_ = sqrt(abs(det));
    }
  }

  contextPrototype.translate = function(aX, aY) {
    var m1 = [
      [1,  0,  0],
      [0,  1,  0],
      [aX, aY, 1]
    ];

    setM(this, matrixMultiply(m1, this.m_), false);
  };

  contextPrototype.rotate = function(aRot) {
    var c = mc(aRot);
    var s = ms(aRot);

    var m1 = [
      [c,  s, 0],
      [-s, c, 0],
      [0,  0, 1]
    ];

    setM(this, matrixMultiply(m1, this.m_), false);
  };

  contextPrototype.scale = function(aX, aY) {
    this.arcScaleX_ *= aX;
    this.arcScaleY_ *= aY;
    var m1 = [
      [aX, 0,  0],
      [0,  aY, 0],
      [0,  0,  1]
    ];

    setM(this, matrixMultiply(m1, this.m_), true);
  };

  contextPrototype.transform = function(m11, m12, m21, m22, dx, dy) {
    var m1 = [
      [m11, m12, 0],
      [m21, m22, 0],
      [dx,  dy,  1]
    ];

    setM(this, matrixMultiply(m1, this.m_), true);
  };

  contextPrototype.setTransform = function(m11, m12, m21, m22, dx, dy) {
    var m = [
      [m11, m12, 0],
      [m21, m22, 0],
      [dx,  dy,  1]
    ];

    setM(this, m, true);
  };

  /**
   * The text drawing function.
   * The maxWidth argument isn't taken in account, since no browser supports
   * it yet.
   */
  contextPrototype.drawText_ = function(text, x, y, maxWidth, stroke) {
    var m = this.m_,
        delta = 1000,
        left = 0,
        right = delta,
        offset = {x: 0, y: 0},
        lineStr = [];

    var fontStyle = getComputedStyle(processFontStyle(this.font),
                                     this.element_);

    var fontStyleString = buildStyle(fontStyle);

    var elementStyle = this.element_.currentStyle;
    var textAlign = this.textAlign.toLowerCase();
    switch (textAlign) {
      case 'left':
      case 'center':
      case 'right':
        break;
      case 'end':
        textAlign = elementStyle.direction == 'ltr' ? 'right' : 'left';
        break;
      case 'start':
        textAlign = elementStyle.direction == 'rtl' ? 'right' : 'left';
        break;
      default:
        textAlign = 'left';
    }

    // 1.75 is an arbitrary number, as there is no info about the text baseline
    switch (this.textBaseline) {
      case 'hanging':
      case 'top':
        offset.y = fontStyle.size / 1.75;
        break;
      case 'middle':
        break;
      default:
      case null:
      case 'alphabetic':
      case 'ideographic':
      case 'bottom':
        offset.y = -fontStyle.size / 3;
        break;
    }

    switch(textAlign) {
      case 'right':
        left = delta;
        right = 0.05;
        break;
      case 'center':
        left = right = delta / 2;
        break;
    }

    var d = getCoords(this, x + offset.x, y + offset.y);

    lineStr.push('<g_vml_:line from="', -left ,' 0" to="', right ,' 0.05" ',
                 ' coordsize="100 100" coordorigin="0 0"',
                 ' filled="', !stroke, '" stroked="', !!stroke,
                 '" style="position:absolute;width:1px;height:1px;left:0px;top:0px;">');

    if (stroke) {
      appendStroke(this, lineStr);
    } else {
      // TODO: Fix the min and max params.
      appendFill(this, lineStr, {x: -left, y: 0},
                 {x: right, y: fontStyle.size});
    }

    var skewM = m[0][0].toFixed(3) + ',' + m[1][0].toFixed(3) + ',' +
                m[0][1].toFixed(3) + ',' + m[1][1].toFixed(3) + ',0,0';

    var skewOffset = mr(d.x / Z) + ',' + mr(d.y / Z);

    lineStr.push('<g_vml_:skew on="t" matrix="', skewM ,'" ',
                 ' offset="', skewOffset, '" origin="', left ,' 0" />',
                 '<g_vml_:path textpathok="true" />',
                 '<g_vml_:textpath on="true" string="',
                 encodeHtmlAttribute(text),
                 '" style="v-text-align:', textAlign,
                 ';font:', encodeHtmlAttribute(fontStyleString),
                 '" /></g_vml_:line>');

    this.element_.insertAdjacentHTML('beforeEnd', lineStr.join(''));
  };

  contextPrototype.fillText = function(text, x, y, maxWidth) {
    this.drawText_(text, x, y, maxWidth, false);
  };

  contextPrototype.strokeText = function(text, x, y, maxWidth) {
    this.drawText_(text, x, y, maxWidth, true);
  };

  contextPrototype.measureText = function(text) {
    if (!this.textMeasureEl_) {
      var s = '<span style="position:absolute;' +
          'top:-20000px;left:0;padding:0;margin:0;border:none;' +
          'white-space:pre;"></span>';
      this.element_.insertAdjacentHTML('beforeEnd', s);
      this.textMeasureEl_ = this.element_.lastChild;
    }
    var doc = this.element_.ownerDocument;
    this.textMeasureEl_.innerHTML = '';
    this.textMeasureEl_.style.font = this.font;
    // Don't use innerHTML or innerText because they allow markup/whitespace.
    this.textMeasureEl_.appendChild(doc.createTextNode(text));
    return {width: this.textMeasureEl_.offsetWidth};
  };

  /******** STUBS ********/
  contextPrototype.clip = function() {
    // TODO: Implement
  };

  contextPrototype.arcTo = function() {
    // TODO: Implement
  };

  contextPrototype.createPattern = function(image, repetition) {
    return new CanvasPattern_(image, repetition);
  };

  // Gradient / Pattern Stubs
  function CanvasGradient_(aType) {
    this.type_ = aType;
    this.x0_ = 0;
    this.y0_ = 0;
    this.r0_ = 0;
    this.x1_ = 0;
    this.y1_ = 0;
    this.r1_ = 0;
    this.colors_ = [];
  }

  CanvasGradient_.prototype.addColorStop = function(aOffset, aColor) {
    aColor = processStyle(aColor);
    this.colors_.push({offset: aOffset,
                       color: aColor.color,
                       alpha: aColor.alpha});
  };

  function CanvasPattern_(image, repetition) {
    assertImageIsValid(image);
    switch (repetition) {
      case 'repeat':
      case null:
      case '':
        this.repetition_ = 'repeat';
        break;
      case 'repeat-x':
      case 'repeat-y':
      case 'no-repeat':
        this.repetition_ = repetition;
        break;
      default:
        throwException('SYNTAX_ERR');
    }

    this.src_ = image.src;
    this.width_ = image.width;
    this.height_ = image.height;
  }

  function throwException(s) {
    throw new DOMException_(s);
  }

  function assertImageIsValid(img) {
    if (!img || img.nodeType != 1 || img.tagName != 'IMG') {
      throwException('TYPE_MISMATCH_ERR');
    }
    if (img.readyState != 'complete') {
      throwException('INVALID_STATE_ERR');
    }
  }

  function DOMException_(s) {
    this.code = this[s];
    this.message = s +': DOM Exception ' + this.code;
  }
  var p = DOMException_.prototype = new Error;
  p.INDEX_SIZE_ERR = 1;
  p.DOMSTRING_SIZE_ERR = 2;
  p.HIERARCHY_REQUEST_ERR = 3;
  p.WRONG_DOCUMENT_ERR = 4;
  p.INVALID_CHARACTER_ERR = 5;
  p.NO_DATA_ALLOWED_ERR = 6;
  p.NO_MODIFICATION_ALLOWED_ERR = 7;
  p.NOT_FOUND_ERR = 8;
  p.NOT_SUPPORTED_ERR = 9;
  p.INUSE_ATTRIBUTE_ERR = 10;
  p.INVALID_STATE_ERR = 11;
  p.SYNTAX_ERR = 12;
  p.INVALID_MODIFICATION_ERR = 13;
  p.NAMESPACE_ERR = 14;
  p.INVALID_ACCESS_ERR = 15;
  p.VALIDATION_ERR = 16;
  p.TYPE_MISMATCH_ERR = 17;

  // set up externs
  G_vmlCanvasManager = G_vmlCanvasManager_;
  CanvasRenderingContext2D = CanvasRenderingContext2D_;
  CanvasGradient = CanvasGradient_;
  CanvasPattern = CanvasPattern_;
  DOMException = DOMException_;
})();

} // if

/**
 * Provides specific methods to draw with 2D Canvas element.
 */
Ext.define('Ext.draw.engine.Canvas', {
    extend: 'Ext.draw.Surface',
    config: {
        /**
         * @cfg {Boolean} highPrecision
         * True to have the Canvas use JavaScript Number instead of single precision floating point for transforms.
         *
         * For example, when using data with big numbers to plot line series, the transformation
         * matrix of the canvas will have big elements. Due to the implementation of the SVGMatrix,
         * the elements are represented by 32-bits floats, which will work incorrectly.
         * To compensate for that, we enable the canvas context to perform all the transformations
         * in JavaScript.
         *
         * Do not use this if you are not encountering 32-bit floating point errors problem,
         * since this will result in a performance penalty.
         */
        highPrecision: false
    },
    requires: [

        'Ext.draw.engine.excanvas',

        'Ext.draw.Animator',
        'Ext.draw.Color'
    ],

    statics: {
        contextOverrides: {
            /**
             * @ignore
             */
            setGradientBBox: function (bbox) {
                this.bbox = bbox;
            },

            /**
             * Fills the subpaths of the current default path or the given path with the current fill style.
             * @ignore
             */
            fill: function () {
                var fillStyle = this.fillStyle,
                    fillGradient = this.fillGradient,
                    fillOpacity = this.fillOpacity,
                    alpha = this.globalAlpha,
                    bbox = this.bbox;

                if (fillStyle !== Ext.draw.Color.RGBA_NONE && fillOpacity !== 0) {
                    if (fillGradient && bbox) {
                        this.fillStyle = fillGradient.generateGradient(this, bbox);
                    }

                    if (fillOpacity !== 1) {
                        this.globalAlpha = alpha * fillOpacity;
                    }
                    this.$fill();
                    if (fillOpacity !== 1) {
                        this.globalAlpha = alpha;
                    }

                    if (fillGradient && bbox) {
                        this.fillStyle = fillStyle;
                    }
                }
            },

            /**
             * Strokes the subpaths of the current default path or the given path with the current stroke style.
             * @ignore
             */
            stroke: function () {
                var strokeStyle = this.strokeStyle,
                    strokeGradient = this.strokeGradient,
                    strokeOpacity = this.strokeOpacity,
                    alpha = this.globalAlpha,
                    bbox = this.bbox;

                if (strokeStyle !== Ext.draw.Color.RGBA_NONE && strokeOpacity !== 0) {
                    if (strokeGradient && bbox) {
                        this.strokeStyle = strokeGradient.generateGradient(this, bbox);
                    }

                    if (strokeOpacity !== 1) {
                        this.globalAlpha = alpha * strokeOpacity;
                    }
                    this.$stroke();
                    if (strokeOpacity !== 1) {
                        this.globalAlpha = alpha;
                    }

                    if (strokeGradient && bbox) {
                        this.strokeStyle = strokeStyle;
                    }
                }
            },

            /**
             * @ignore
             */
            fillStroke: function (attr, transformFillStroke) {
                var ctx = this,
                    fillStyle = this.fillStyle,
                    fillOpacity = this.fillOpacity,
                    strokeStyle = this.strokeStyle,
                    strokeOpacity = this.strokeOpacity,
                    shadowColor = ctx.shadowColor,
                    shadowBlur = ctx.shadowBlur,
                    none = Ext.draw.Color.RGBA_NONE;

                if (transformFillStroke === undefined) {
                    transformFillStroke = attr.transformFillStroke;
                }

                if (!transformFillStroke) {
                    attr.inverseMatrix.toContext(ctx);
                }
                if (fillStyle !== none && fillOpacity !== 0) {
                    ctx.fill();
                    ctx.shadowColor = none;
                    ctx.shadowBlur = 0;
                }
                if (strokeStyle !== none && strokeOpacity !== 0) {
                    ctx.stroke();
                }
                ctx.shadowColor = shadowColor;
                ctx.shadowBlur = shadowBlur;
            },

            /**
             * 2D Canvas context in IE (up to IE10, inclusive) doesn't support
             * the setLineDash method and the lineDashOffset property.
             * @param dashList An even number of non-negative numbers specifying a dash list.
             */
            setLineDash: function (dashList) {
                if (this.$setLineDash) {
                    this.$setLineDash(dashList);
                }
            },

            /**
             * Adds points to the subpath such that the arc described by the circumference of the
             * ellipse described by the arguments, starting at the given start angle and ending at
             * the given end angle, going in the given direction (defaulting to clockwise), is added
             * to the path, connected to the previous point by a straight line.
             * @ignore
             */
            ellipse: function (cx, cy, rx, ry, rotation, start, end, anticlockwise) {
                var cos = Math.cos(rotation),
                    sin = Math.sin(rotation);
                this.transform(cos * rx, sin * rx, -sin * ry, cos * ry, cx, cy);
                this.arc(0, 0, 1, start, end, anticlockwise);
                this.transform(
                    cos / rx, -sin / ry,
                    sin / rx, cos / ry,
                    -(cos * cx + sin * cy) / rx, (sin * cx - cos * cy) / ry);
            },

            /**
             * Uses the given path commands to begin a new path on the canvas.
             * @ignore
             */
            appendPath: function (path) {
                var me = this,
                    i = 0, j = 0,
                    commands = path.commands,
                    params = path.params,
                    ln = commands.length;
                me.beginPath();
                for (; i < ln; i++) {
                    switch (commands[i]) {
                        case 'M':
                            me.moveTo(params[j], params[j + 1]);
                            j += 2;
                            break;
                        case 'L':
                            me.lineTo(params[j], params[j + 1]);
                            j += 2;
                            break;
                        case 'C':
                            me.bezierCurveTo(
                                params[j], params[j + 1],
                                params[j + 2], params[j + 3],
                                params[j + 4], params[j + 5]
                            );
                            j += 6;
                            break;
                        case 'Z':
                            me.closePath();
                            break;
                    }
                }
            },

            save: function () {
                var toSave = this.toSave,
                    ln = toSave.length,
                    obj = ln && {}, // Don't allocate memory if we don't have to.
                    i = 0,
                    key;
                for (; i < ln; i++) {
                    key = toSave[i];
                    if (key in this) {
                        obj[key] = this[key];
                    }
                }
                this.state.push(obj);
                this.$save();
            },

            restore: function () {
                var obj = this.state.pop(),
                    key;
                if (obj) {
                    for (key in obj) {
                        this[key] = obj[key];
                    }
                }
                this.$restore();
            }
        }
    },

    splitThreshold: 3000,

    /**
     * @private
     * Properties to be saved/restored in the `save` and `restore` methods.
     */
    toSave: ['fillGradient', 'strokeGradient'],

    getElementConfig: function () {
        //TODO:ps In the Ext world, use renderTpl to create the children
        return {
            reference: 'element',
            style: {
                position: 'absolute'
            },
            children: [
                {
                    reference: 'innerElement',
                    style: {
                        width: '100%',
                        height: '100%',
                        position: 'relative'
                    }
                }
            ]
        };
    },

    /**
     * @private
     *
     * Creates the canvas element.
     */
    createCanvas: function () {
        var canvas = Ext.Element.create({
                tag: 'canvas',
                cls: Ext.baseCSSPrefix + 'surface-canvas'
            });

        // Emulate Canvas in IE8 with VML.
        window['G_vmlCanvasManager'] && G_vmlCanvasManager.initElement(canvas.dom);

        var overrides = Ext.draw.engine.Canvas.contextOverrides,
            ctx = canvas.dom.getContext('2d'),
            backingStoreRatio = ctx.webkitBackingStorePixelRatio ||
                ctx.mozBackingStorePixelRatio ||
                ctx.msBackingStorePixelRatio ||
                ctx.oBackingStorePixelRatio ||
                ctx.backingStorePixelRatio || 1,
            name;

        // Windows Phone does not currently support backingStoreRatio
        this.devicePixelRatio /= (Ext.os.is.WindowsPhone) ? window.innerWidth / window.screen.width : backingStoreRatio;

        if (ctx.ellipse) {
            delete overrides.ellipse;
        }

        ctx.state = [];
        ctx.toSave = this.toSave;

        // Saving references to the native Canvas context methods that we'll be overriding.
        for (name in overrides) {
            ctx['$' + name] = ctx[name];
        }
        Ext.apply(ctx, overrides);

        if (this.getHighPrecision()) {
            this.enablePrecisionCompensation(ctx);
        } else {
            this.disablePrecisionCompensation(ctx);
        }

        this.innerElement.appendChild(canvas);
        this.canvases.push(canvas);
        this.contexts.push(ctx);
    },

    initElement: function () {
        this.callParent();
        this.canvases = [];
        this.contexts = [];
        this.activeCanvases = 0;
    },

    // Have to create canvas element here, instead of in the initElement,
    // because otherwise the created canvas will be cached along with the
    // surface's markup and used as a template for future surface
    // instances.
    afterCachedConfig: function () {
        this.callParent();
        this.createCanvas();
    },

    updateHighPrecision: function (highPrecision) {
        var contexts = this.contexts,
            ln = contexts.length,
            i, context;

        for (i = 0; i < ln; i++) {
            context = contexts[i];
            if (highPrecision) {
                this.enablePrecisionCompensation(context);
            } else {
                this.disablePrecisionCompensation(context);
            }
        }
    },

    precisionNames: [
        'rect',
        'fillRect',
        'strokeRect',
        'clearRect',
        'moveTo',
        'lineTo',
        'arc',
        'arcTo',
        'save',
        'restore',
        'updatePrecisionCompensate',
        'setTransform',
        'transform',
        'scale',
        'translate',
        'rotate',
        'quadraticCurveTo',
        'bezierCurveTo',
        'createLinearGradient',
        'createRadialGradient',
        'fillText',
        'strokeText',
        'drawImage'
    ],

    /**
     * @private
     * Clears canvas of compensation for canvas' use of single precision floating point.
     * @param {CanvasRenderingContext2D} ctx The canvas context.
     */
    disablePrecisionCompensation: function (ctx) {
        var regularOverrides = Ext.draw.engine.Canvas.contextOverrides,
            precisionOverrides = this.precisionNames,
            ln = precisionOverrides.length,
            i, name;

        for (i = 0; i < ln; i++) {
            name = precisionOverrides[i];
            if (!(name in regularOverrides)) {
                delete ctx[name];
            }
        }

        this.setDirty(true);
    },

    /**
     * @private
     * Compensate for canvas' use of single precision floating point.
     * @param {CanvasRenderingContext2D} ctx The canvas context.
     */
    enablePrecisionCompensation: function (ctx) {
        var surface = this,
            xx = 1, yy = 1,
            dx = 0, dy = 0,
            matrix = new Ext.draw.Matrix(),
            transStack = [],
            comp = {},
            regularOverrides = Ext.draw.engine.Canvas.contextOverrides,
            originalCtx = ctx.constructor.prototype;

        /**
         * @class CanvasRenderingContext2D
         * @ignore
         */
        var precisionOverrides = {
            toSave: surface.toSave,
            /**
             * Adds a new closed subpath to the path, representing the given rectangle.
             * @return {*}
             * @ignore
             */
            rect: function (x, y, w, h) {
                return originalCtx.rect.call(this, x * xx + dx, y * yy + dy, w * xx, h * yy);
            },

            /**
             * Paints the given rectangle onto the canvas, using the current fill style.
             * @ignore
             */
            fillRect: function (x, y, w, h) {
                this.updatePrecisionCompensateRect();
                originalCtx.fillRect.call(this, x * xx + dx, y * yy + dy, w * xx, h * yy);
                this.updatePrecisionCompensate();
            },

            /**
             * Paints the box that outlines the given rectangle onto the canvas, using the current stroke style.
             * @ignore
             */
            strokeRect: function (x, y, w, h) {
                this.updatePrecisionCompensateRect();
                originalCtx.strokeRect.call(this, x * xx + dx, y * yy + dy, w * xx, h * yy);
                this.updatePrecisionCompensate();
            },

            /**
             * Clears all pixels on the canvas in the given rectangle to transparent black.
             * @ignore
             */
            clearRect: function (x, y, w, h) {
                return originalCtx.clearRect.call(this, x * xx + dx, y * yy + dy, w * xx, h * yy);
            },

            /**
             * Creates a new subpath with the given point.
             * @ignore
             */
            moveTo: function (x, y) {
                return originalCtx.moveTo.call(this, x * xx + dx, y * yy + dy);
            },

            /**
             * Adds the given point to the current subpath, connected to the previous one by a straight line.
             * @ignore
             */
            lineTo: function (x, y) {
                return originalCtx.lineTo.call(this, x * xx + dx, y * yy + dy);
            },

            /**
             * Adds points to the subpath such that the arc described by the circumference of the
             * circle described by the arguments, starting at the given start angle and ending at
             * the given end angle, going in the given direction (defaulting to clockwise), is added
             * to the path, connected to the previous point by a straight line.
             * @ignore
             */
            arc: function (x, y, radius, startAngle, endAngle, anticlockwise) {
                this.updatePrecisionCompensateRect();
                originalCtx.arc.call(this, x * xx + dx, y * xx + dy, radius * xx, startAngle, endAngle, anticlockwise);
                this.updatePrecisionCompensate();
            },

            /**
             * Adds an arc with the given control points and radius to the current subpath,
             * connected to the previous point by a straight line.  If two radii are provided, the
             * first controls the width of the arc's ellipse, and the second controls the height. If
             * only one is provided, or if they are the same, the arc is from a circle.
             *
             * In the case of an ellipse, the rotation argument controls the clockwise inclination
             * of the ellipse relative to the x-axis.
             * @ignore
             */
            arcTo: function (x1, y1, x2, y2, radius) {
                this.updatePrecisionCompensateRect();
                originalCtx.arcTo.call(this, x1 * xx + dx, y1 * yy + dy, x2 * xx + dx, y2 * yy + dy, radius * xx);
                this.updatePrecisionCompensate();
            },

            /**
             * Pushes the context state to the state stack.
             * @ignore
             */
            save: function () {
                transStack.push(matrix);
                matrix = matrix.clone();
                regularOverrides.save.call(this);
                originalCtx.save.call(this);
            },

            /**
             * Pops the state stack and restores the state.
             * @ignore
             */
            restore: function () {
                matrix = transStack.pop();
                regularOverrides.restore.call(this);
                originalCtx.restore.call(this);
                this.updatePrecisionCompensate();
            },

            /**
             * @ignore
             */
            updatePrecisionCompensate: function () {
                matrix.precisionCompensate(surface.devicePixelRatio, comp);
                xx = comp.xx;
                yy = comp.yy;
                dx = comp.dx;
                dy = comp.dy;
                originalCtx.setTransform.call(this, surface.devicePixelRatio, comp.b, comp.c, comp.d, 0, 0);
            },

            /**
             * @ignore
             */
            updatePrecisionCompensateRect: function () {
                matrix.precisionCompensateRect(surface.devicePixelRatio, comp);
                xx = comp.xx;
                yy = comp.yy;
                dx = comp.dx;
                dy = comp.dy;
                originalCtx.setTransform.call(this, surface.devicePixelRatio, comp.b, comp.c, comp.d, 0, 0);
            },

            /**
             * Changes the transformation matrix to the matrix given by the arguments as described below.
             * @ignore
             */
            setTransform: function (x2x, x2y, y2x, y2y, newDx, newDy) {
                matrix.set(x2x, x2y, y2x, y2y, newDx, newDy);
                this.updatePrecisionCompensate();
            },

            /**
             * Changes the transformation matrix to apply the matrix given by the arguments as described below.
             * @ignore
             */
            transform: function (x2x, x2y, y2x, y2y, newDx, newDy) {
                matrix.append(x2x, x2y, y2x, y2y, newDx, newDy);
                this.updatePrecisionCompensate();
            },

            /**
             * Scales the transformation matrix.
             * @return {*}
             * @ignore
             */
            scale: function (sx, sy) {
                this.transform(sx, 0, 0, sy, 0, 0);
            },

            /**
             * Translates the transformation matrix.
             * @return {*}
             * @ignore
             */
            translate: function (dx, dy) {
                this.transform(1, 0, 0, 1, dx, dy);
            },

            /**
             * Rotates the transformation matrix.
             * @return {*}
             * @ignore
             */
            rotate: function (radians) {
                var cos = Math.cos(radians),
                    sin = Math.sin(radians);
                this.transform(cos, sin, -sin, cos, 0, 0);
            },

            /**
             * Adds the given point to the current subpath, connected to the previous one by a
             * quadratic Bézier curve with the given control point.
             * @return {*}
             * @ignore
             */
            quadraticCurveTo: function (cx, cy, x, y) {
                originalCtx.quadraticCurveTo.call(this,
                    cx * xx + dx,
                    cy * yy + dy,
                    x * xx + dx,
                    y * yy + dy
                );
            },

            /**
             * Adds the given point to the current subpath, connected to the previous one by a cubic
             * Bézier curve with the given control points.
             * @return {*}
             * @ignore
             */
            bezierCurveTo: function (c1x, c1y, c2x, c2y, x, y) {
                originalCtx.bezierCurveTo.call(this,
                    c1x * xx + dx,
                    c1y * yy + dy,
                    c2x * xx + dx,
                    c2y * yy + dy,
                    x * xx + dx,
                    y * yy + dy
                );
            },

            /**
             * Returns an object that represents a linear gradient that paints along the line given
             * by the coordinates represented by the arguments.
             * @return {*}
             * @ignore
             */
            createLinearGradient: function (x0, y0, x1, y1) {
                this.updatePrecisionCompensateRect();
                var grad = originalCtx.createLinearGradient.call(this,
                    x0 * xx + dx,
                    y0 * yy + dy,
                    x1 * xx + dx,
                    y1 * yy + dy
                );
                this.updatePrecisionCompensate();
                return grad;
            },

            /**
             * Returns a CanvasGradient object that represents a radial gradient that paints along
             * the cone given by the circles represented by the arguments.  If either of the radii
             * are negative, throws an IndexSizeError exception.
             * @return {*}
             * @ignore
             */
            createRadialGradient: function (x0, y0, r0, x1, y1, r1) {
                this.updatePrecisionCompensateRect();
                var grad = originalCtx.createLinearGradient.call(this,
                    x0 * xx + dx,
                    y0 * xx + dy,
                    r0 * xx,
                    x1 * xx + dx,
                    y1 * xx + dy,
                    r1 * xx
                );
                this.updatePrecisionCompensate();
                return grad;
            },

            /**
             * Fills the given text at the given position. If a maximum width is provided, the text
             * will be scaled to fit that width if necessary.
             * @ignore
             */
            fillText: function (text, x, y, maxWidth) {
                originalCtx.setTransform.apply(this, matrix.elements);
                if (typeof maxWidth === 'undefined') {
                    originalCtx.fillText.call(this, text, x, y);
                } else {
                    originalCtx.fillText.call(this, text, x, y, maxWidth);
                }
                this.updatePrecisionCompensate();
            },

            /**
             * Strokes the given text at the given position. If a
             * maximum width is provided, the text will be scaled to
             * fit that width if necessary.
             * @ignore
             */
            strokeText: function (text, x, y, maxWidth) {
                originalCtx.setTransform.apply(this, matrix.elements);
                if (typeof maxWidth === 'undefined') {
                    originalCtx.strokeText.call(this, text, x, y);
                } else {
                    originalCtx.strokeText.call(this, text, x, y, maxWidth);
                }
                this.updatePrecisionCompensate();
            },

            /**
             * Fills the subpaths of the current default path or the given path with the current fill style.
             * @ignore
             */
            fill: function () {
                var fillGradient = this.fillGradient,
                    bbox = this.bbox;
                this.updatePrecisionCompensateRect();
                if (fillGradient && bbox) {
                    this.fillStyle = fillGradient.generateGradient(this, bbox);
                }
                originalCtx.fill.call(this);
                this.updatePrecisionCompensate();
            },

            /**
             * Strokes the subpaths of the current default path or the given path with the current stroke style.
             * @ignore
             */
            stroke: function () {
                var strokeGradient = this.strokeGradient,
                    bbox = this.bbox;
                this.updatePrecisionCompensateRect();
                if (strokeGradient && bbox) {
                    this.strokeStyle = strokeGradient.generateGradient(this, bbox);
                }
                originalCtx.stroke.call(this);
                this.updatePrecisionCompensate();
            },

            /**
             * Draws the given image onto the canvas.  If the first argument isn't an img, canvas,
             * or video element, throws a TypeMismatchError exception. If the image has no image
             * data, throws an InvalidStateError exception. If the one of the source rectangle
             * dimensions is zero, throws an IndexSizeError exception. If the image isn't yet fully
             * decoded, then nothing is drawn.
             * @return {*}
             * @ignore
             */
            drawImage: function (img_elem, arg1, arg2, arg3, arg4, dst_x, dst_y, dw, dh) {
                switch (arguments.length) {
                    case 3:
                        return originalCtx.drawImage.call(this, img_elem, arg1 * xx + dx, arg2 * yy + dy);
                    case 5:
                        return originalCtx.drawImage.call(this, img_elem, arg1 * xx + dx, arg2 * yy + dy, arg3 * xx, arg4 * yy);
                    case 9:
                        return originalCtx.drawImage.call(this, img_elem, arg1, arg2, arg3, arg4, dst_x * xx + dx, dst_y * yy * dy, dw * xx, dh * yy);
                }
            }
        };
        Ext.apply(ctx, precisionOverrides);
        this.setDirty(true);
    },

    // Continue docs for the Canvas class
    /** @class Ext.draw.engine.Canvas */

    updateRect: function (rect) {
        this.callParent([rect]);

        var me = this,
            l = Math.floor(rect[0]),
            t = Math.floor(rect[1]),
            r = Math.ceil(rect[0] + rect[2]),
            b = Math.ceil(rect[1] + rect[3]),
            devicePixelRatio = me.devicePixelRatio,
            w = r - l,
            h = b - t,
            splitThreshold = Math.round(me.splitThreshold / devicePixelRatio),
            splits = Math.ceil(w / splitThreshold),
            activeCanvases = me.activeCanvases,
            i, offsetX, dom, leftWidth;

        for (i = 0, offsetX = 0; i < splits; i++, offsetX += splitThreshold) {
            if (i >= me.canvases.length) {
                me.createCanvas();
            }
            dom = me.canvases[i].dom;
            dom.style.left = offsetX + 'px';
            if (h * devicePixelRatio !== dom.height) {
                dom.height = h * devicePixelRatio;
                dom.style.height = h + 'px';
            }
            leftWidth = Math.min(splitThreshold, w - offsetX);
            if (leftWidth * devicePixelRatio !== dom.width) {
                dom.width = leftWidth * devicePixelRatio;
                dom.style.width = leftWidth + 'px';
            }
            me.applyDefaults(me.contexts[i]);
        }

        for (; i < activeCanvases; i++) {
            dom = me.canvases[i].dom;
            dom.width = 0;
            dom.height = 0;
        }
        me.activeCanvases = splits;
        me.clear();
    },

    /**
     * @inheritdoc
     */
    clearTransform: function () {
        var me = this,
            activeCanvases = me.activeCanvases,
            i, ctx;

        for (i = 0; i < activeCanvases; i++) {
            ctx = me.contexts[i];
            ctx.translate(-me.splitThreshold * i, 0);
            ctx.scale(me.devicePixelRatio, me.devicePixelRatio);
            me.matrix.toContext(ctx);
        }

    },

    /**
     * @private
     * @inheritdoc
     */
    renderSprite: function (sprite) {
        var me = this,
            rect = me.getRect(),
            surfaceMatrix = me.matrix,
            parent = sprite.getParent(),
            matrix = Ext.draw.Matrix.fly([1, 0, 0, 1, 0, 0]),
            bbox, i, offsetX, ctx, width, left = 0, top, right = rect[2], bottom;

        while (parent && (parent !== me)) {
            matrix.prependMatrix(parent.matrix || parent.attr && parent.attr.matrix);
            parent = parent.getParent();
        }
        matrix.prependMatrix(surfaceMatrix);
        bbox = sprite.getBBox();
        if (bbox) {
            bbox = matrix.transformBBox(bbox);
        }

        sprite.preRender(me);

        if (sprite.attr.hidden || sprite.attr.globalAlpha === 0) {
            sprite.setDirty(false);
            return;
        }

        top = 0;
        bottom = top + rect[3];

        for (i = 0, offsetX = 0; i < me.activeCanvases; i++, offsetX += me.splitThreshold / me.devicePixelRatio) {
            ctx = me.contexts[i];
            width = Math.min(rect[2] - offsetX, me.splitThreshold / me.devicePixelRatio);
            left = offsetX;
            right = left + width;

            if (bbox) {
                if (bbox.x > right ||
                    bbox.x + bbox.width < left ||
                    bbox.y > bottom ||
                    bbox.y + bbox.height < top) {
                    continue;
                }
            }

            ctx.save();
            sprite.useAttributes(ctx, rect);
            if (false === sprite.render(me, ctx, [left, top, width, bottom - top], rect)) {
                return false;
            }
            ctx.restore();
        }
        sprite.setDirty(false);
    },

    flatten: function (size, surfaces) {
        var canvas = document.createElement('canvas'),
            className = Ext.getClassName(this),
            ratio = this.devicePixelRatio,
            ctx = canvas.getContext('2d'),
            surface, rect, i;

        canvas.width = Math.ceil(size.width * ratio);
        canvas.height = Math.ceil(size.height * ratio);

        for (i = 0; i < surfaces.length; i++) {
            surface = surfaces[i];
            if (Ext.getClassName(surface) !== className) {
                continue;
            }
            rect = surface.getRect();
            ctx.drawImage(surface.canvases[0].dom, rect[0] * ratio, rect[1] * ratio);
        }
        return {
            data: canvas.toDataURL(),
            type: 'png'
        };
    },

    applyDefaults: function (ctx) {
        var none = Ext.draw.Color.RGBA_NONE;
        ctx.strokeStyle = none;
        ctx.fillStyle = none;
        ctx.textAlign = 'start';
        ctx.textBaseline = 'alphabetic';
        ctx.miterLimit = 1;
    },

    /**
     * @inheritdoc
     */
    clear: function () {
        var me = this,
            activeCanvases = this.activeCanvases,
            i, canvas, ctx;
        for (i = 0; i < activeCanvases; i++) {
            canvas = me.canvases[i].dom;
            ctx = me.contexts[i];
            ctx.setTransform(1, 0, 0, 1, 0, 0);
            ctx.clearRect(0, 0, canvas.width, canvas.height);
        }
        me.setDirty(true);
    },

    /**
     * Destroys the Canvas element and prepares it for Garbage Collection.
     */
    destroy: function () {
        var me = this,
            i, ln = me.canvases.length;
        for (i = 0; i < ln; i++) {
            me.contexts[i] = null;
            me.canvases[i].destroy();
            me.canvases[i] = null;
        }
        delete me.contexts;
        delete me.canvases;
        me.callParent(arguments);
    }
}, function () {
    if (Ext.os.is.Android4 && Ext.browser.is.Chrome) {
        this.prototype.splitThreshold = 3000;
    } else if (Ext.os.is.Android) {
        this.prototype.splitThreshold = 1e10;
    }
});

/**
 * The Draw Container is a surface in which sprites can be rendered. The Draw Container
 * manages and holds a `Surface` instance: an interface that has
 * an SVG or Canvas implementation depending on the browser capabilities and where
 * Sprites can be appended.
 * One way to create a draw container is:
 *
 *      var drawContainer = Ext.create('Ext.draw.Container', {
 *           renderTo: Ext.getBody(),
 *           width:200,
 *           height:200,
 *           sprites: [{
 *                type: 'circle',
 *                fillStyle: '#79BB3F',
 *                r: 100,
 *                x: 100,
 *                y: 100
 *           }]
 *      });
 *
 * In this case we created a draw container and added a sprite to it.
 * The *type* of the sprite is *circle*, so if you run this code you'll see a green-ish circle.
 *
 * For more information on Sprites, the core elements added to a draw container's surface,
 * refer to the {@link Ext.draw.sprite.Sprite} documentation.
 */
Ext.define('Ext.draw.Container', {
    extend: 'Ext.draw.ContainerBase',
    alternateClassName: 'Ext.draw.Component',
    xtype: 'draw',
    defaultType: 'surface',

    requires: [
        'Ext.draw.Surface',
        'Ext.draw.engine.Svg',
        'Ext.draw.engine.Canvas',
        'Ext.draw.gradient.GradientDefinition'
    ],
    /**
     * @cfg {String} [engine="Ext.draw.engine.Canvas"]
     * Defines the engine (type of surface) used to render draw container contents.  
     * 
     * The render engine is selected automatically depending on the platform used. Priority 
     * is given to the canvas engine due to its performance advantage.
     *
     * You may also set the engine config to be `Ext.draw.engine.Svg` if so desired.
     */    
    engine: 'Ext.draw.engine.Canvas',

    config: {
        cls: Ext.baseCSSPrefix + 'draw-container',

        /**
         * @cfg {Function} [resizeHandler] The resize function that can be configured to have a behavior.
         *
         * __Note:__ since resize events trigger {@link #renderFrame} calls automatically,
         * return `false` from the resize function, if it also calls `renderFrame`, to prevent double rendering.
         */
        resizeHandler: null,

        background: null,

        /**
         * @cfg {Object[]} sprites
         * Defines a set of sprites to be added to the drawContainer surface.
         *
         * For example:
         *
         *      sprites: [{
         *           type: 'circle',
         *           fillStyle: '#79BB3F',
         *           r: 100,
         *           x: 100,
         *           y: 100
         *      }]
         * 
         */
        sprites: null,

        /**
         * @cfg {Object[]} gradients
         * Defines a set of gradients that can be used as color properties
         * (fillStyle and strokeStyle, but not shadowColor) in sprites.
         * The gradients array is an array of objects with the following properties:
         * - **id** - string - The unique name of the gradient.
         * - **type** - string, optional - The type of the gradient. Available types are: 'linear', 'radial'. Defaults to 'linear'.
         * - **angle** - number, optional - The angle of the gradient in degrees.
         * - **stops** - array - An array of objects with 'color' and 'offset' properties, where 'offset' is a real number from 0 to 1.
         *
         * For example:
         *
         *     gradients: [{
         *         id: 'gradientId1',
         *         type: 'linear',
         *         angle: 45,
         *         stops: [{
         *             offset: 0,
         *             color: 'red'
         *         }, {
         *            offset: 1,
         *            color: 'yellow'
         *         }]
         *     }, {
         *        id: 'gradientId2',
         *        type: 'radial',
         *        stops: [{
         *            offset: 0,
         *            color: '#555',
         *        }, {
         *            offset: 1,
         *            color: '#ddd',
         *        }]
         *     }]
         *
         * Then the sprites can use 'gradientId1' and 'gradientId2' by setting the color attributes to those ids, for example:
         *
         *     sprite.setAttributes({
         *         fillStyle: 'url(#gradientId1)',
         *         strokeStyle: 'url(#gradientId2)'
         *     });
         */
        gradients: []
    },

    /**
     * @property {String} [defaultDownloadServerUrl="http://svg.sencha.io"]
     * The default URL used by {@link #download}.
     */
    defaultDownloadServerUrl: 'http://svg.sencha.io',

    /**
     * @property {Array} [supportedFormats=["png", "pdf", "jpeg", "gif"]]
     * A list of export types supported by the server.
     * @private
     */
    supportedFormats: ['png', 'pdf', 'jpeg', 'gif'],

    supportedOptions: {
        version: Ext.isNumber,
        data: Ext.isString,
        format: function (format) {
            return Ext.Array.indexOf(this.supportedFormats, format) >= 0;
        },
        filename: Ext.isString,
        width: Ext.isNumber,
        height: Ext.isNumber,
        scale: Ext.isNumber,
        pdf: Ext.isObject,
        jpeg: Ext.isObject
    },

    initAnimator: function() {
        this.frameCallbackId = Ext.draw.Animator.addFrameCallback('renderFrame', this);
    },

    applyGradients: function (gradients) {
        var result = [],
            i, n, gradient, offset;
        if (!Ext.isArray(gradients)) {
            return result;
        }
        for (i = 0, n = gradients.length; i < n; i++) {
            gradient = gradients[i];
            if (!Ext.isObject(gradient)) {
                continue;
            }
            // ExtJS only supported linear gradients, so we didn't have to specify their type
            if (typeof gradient.type !== 'string') {
                gradient.type = 'linear';
            }
            if (gradient.angle) {
                gradient.degrees = gradient.angle;
                delete gradient.angle;
            }
            // Convert ExtJS stops object to Touch stops array
            if (Ext.isObject(gradient.stops)) {
                gradient.stops = (function (stops) {
                    var result = [], stop;
                    for (offset in stops) {
                        stop = stops[offset];
                        stop.offset = offset / 100;
                        result.push(stop);
                    }
                    return result;
                })(gradient.stops);
            }
            result.push(gradient);
        }
        Ext.draw.gradient.GradientDefinition.add(result);
        return result;
    },

    applySprites: function (sprites) {
        // Never update
        if (!sprites) {
            return;
        }

        sprites = Ext.Array.from(sprites);

        var ln = sprites.length,
            i, surface;

        for (i = 0; i < ln; i++) {
            if (sprites[i].surface instanceof Ext.draw.Surface) {
                surface = sprites[i].surface;
            } else if (Ext.isString(sprites[i].surface)) {
                surface = this.getSurface(sprites[i].surface);
            } else {
                surface = this.getSurface('main');
            }
            surface.add(sprites[i]);
        }
    },

    /**
     * @protected
     * Place watermark after resize.
     * @param {Number} width
     * @param {Number} height
     */
    onPlaceWatermark: Ext.emptyFn,

    onBodyResize: function () {
        var me = this,
            size = me.element.getSize(),
            resizeHandler = me.getResizeHandler() || me.resizeHandler,
            result;
        me.fireEvent('resize', me, size);
        result = resizeHandler.call(me, size);
        if (result !== false) {
            me.renderFrame();
            me.onPlaceWatermark(size.width, size.height);
        }
    },

    resizeHandler: function (size) {
        this.getItems().each(function (surface) {
            surface.setRect([0, 0, size.width, size.height]);
        });
    },

    /**
     * Get a surface by the given id or create one if it doesn't exist.
     * @param {String} [id="main"]
     * @return {Ext.draw.Surface}
     */
    getSurface: function (id) {
        id = this.getId() + '-' + (id || 'main');
        var me = this,
            surfaces = me.getItems(),
            surface = surfaces.get(id);
        if (!surface) {
            surface = me.add({xclass: me.engine, id: id});
            surface.renderFrame();
        }
        return surface;
    },

    /**
     * Render all the surfaces in the container.
     */
    renderFrame: function () {
        var me = this,
            surfaces = me.getItems(),
            i, ln, item;

        for (i = 0, ln = surfaces.length; i < ln; i++) {
            item = surfaces.items[i];
            if (item.isSurface) {
                item.renderFrame();
            }
        }
    },

    /**
     * Produces an image of the chart.
     * @param {String} [format] Possible options are 'image' (the method will return an Image object)
     *                          and 'stream' (the method will return the image as a byte stream).
     *                          If missing, the DataURL of the chart's image will be returned.
     * @return {Object}
     * @return {String} return.data Image element, byte stream or DataURL.
     * @return {String} return.type The type of the data (e.g. 'png' or 'svg').
     */
    getImage: function (format) {
        var size = this.innerElement.getSize(),
            surfaces = Array.prototype.slice.call(this.items.items),
            image, imageElement,
            zIndexes = this.surfaceZIndexes,
            i, j, surface, zIndex;

        // Sort the surfaces by zIndex using insertion sort.
        for (j = 1; j < surfaces.length; j++) {
            surface = surfaces[j];
            zIndex = zIndexes[surface.type];
            i = j - 1;
            while (i >= 0 && zIndexes[surfaces[i].type] > zIndex) {
                surfaces[i + 1] = surfaces[i];
                i--;
            }
            surfaces[i + 1] = surface;
        }

        image = surfaces[0].flatten(size, surfaces);

        if (format === 'image') {
            imageElement = new Image();
            imageElement.src = image.data;
            image.data = imageElement;
            return image;
        }
        if (format === 'stream') {
            image.data = image.data.replace(/^data:image\/[^;]+/, 'data:application/octet-stream');
            return image;
        }
        return image;
    },

    /**
     * Downloads an image or PDF of the chart or opens it in a separate browser tab/window
     * if the download can't be triggered. The exact behavior is platform and browser
     * specific. For more consistent results on mobile devices use the {@link #preview}
     * method instead.
     *
     * @param {Object} [config] The following config options are supported:
     *
     * @param {String} config.url The url to post the data to. Defaults to
     * the {@link #defaultUrl} configuration on the class.
     *
     * @param {String} config.format The format of image to export. See the
     * {@link #supportedFormats}. Defaults to 'png' on the Sencha IO server.
     * Note that you can't export to 'svg' format if the {@link Ext.draw.engine.Canvas Canvas}
     * {@link Ext.draw.Container#engine engine} is used.
     *
     * @param {Number} config.width A width to send to the server for
     * configuring the image width. Defaults to natural image width on
     * the Sencha IO server.
     *
     * @param {Number} config.height A height to send to the server for
     * configuring the image height. Defaults to natural image height on
     * the Sencha IO server.
     *
     * @param {String} config.filename The filename of the downloaded image.
     * Defaults to 'chart' on the Sencha IO server. The config.format is used
     * as a filename extension.
     *
     * @param {Number} config.scale The scaling of the downloaded image.
     * Defaults to 1 on the Sencha IO server. The server will try to determine the natural
     * size of the image unless the width/height configs have been set. If the
     * {@link Ext.draw.engine.Canvas Canvas} {@link Ext.draw.Container#engine engine} is
     * used the natural image size will depend on the value of the window.devicePixelRatio.
     * For example, for devices with devicePixelRatio of 2 the produced image will be
     * two times larger than for devices with devicePixelRatio of 1 for the same drawing.
     * This is done so that the users with devices with HiDPI screens get a downloaded
     * image that looks as crisp on their device as the original drawing.
     * If you want image size to be consistent across devices with different device
     * pixel ratios, you can set the value of this config to 1/devicePixelRatio.
     * This parameter is ignored by the Sencha IO server if config.format is set to 'svg'.
     *
     * @param {Object} config.pdf PDF specific options.
     * This config is only used if config.format is set to 'pdf'.
     * The given object should be in either this format:
     *
     *     {
     *       width: '200px',
     *       height: '300px',
     *       border: '0px'
     *     }
     *
     * or this format:
     *
     *     {
     *       format: 'A4',
     *       orientation: 'portrait',
     *       border: '1cm'
     *     }
     *
     * Supported dimension units are: 'mm', 'cm', 'in', 'px'. No unit means 'px'.
     * Supported formats are: 'A3', 'A4', 'A5', 'Legal', 'Letter', 'Tabloid'.
     * Orientation ('portrait', 'landscape') is optional and defaults to 'portrait'.
     *
     * @param {Object} config.jpeg JPEG specific options.
     * This config is only used if config.format is set to 'jpeg'.
     * The given object should be in this format:
     *
     *     {
     *       quality: 80
     *     }
     *
     * Where quality is an integer between 0 and 100.
     *
     * @return {Boolean} True if request was successfully sent to the server.
     */
    download: function (config) {
        var me = this,
            inputs = [],
            markup, name, value;

        config = Ext.apply({
            version: 2,
            data: me.getImage().data
        }, config);

        for (name in config) {
            if (config.hasOwnProperty(name)) {
                value = config[name];
                if (name in me.supportedOptions) {
                    if (me.supportedOptions[name].call(me, value)) {
                        inputs.push({
                            tag: 'input',
                            type: 'hidden',
                            name: name,
                            value: Ext.isObject(value) ? Ext.JSON.encode(value) : value
                        });
                    }
                    else {
                        Ext.log.error('Invalid value for image download option "' + name + '": ' + value);
                    }
                }
                else {
                    Ext.log.error('Invalid image download option: "' + name + '"');
                }
            }
        }

        markup = Ext.dom.Helper.markup({
            tag: 'html',
            children: [
                {tag: 'head'},
                {
                    tag: 'body',
                    children: [
                        {
                            tag: 'form',
                            method: 'POST',
                            action: config.url || me.defaultDownloadServerUrl,
                            children: inputs
                        },
                        {
                            tag: 'script',
                            type: 'text/javascript',
                            children: 'document.getElementsByTagName("form")[0].submit();'
                        }
                    ]
                }
            ]
        });

        window.open('', 'ImageDownload_' + Date.now()).document.write(markup);
    },

    /**
     * @method preview
     * Displays an image of a Ext.draw.Container on screen.
     * On mobile devices this lets users tap-and-hold to bring up the menu
     * with image saving options.
     * Note: some browsers won't save the preview image if it's SVG based
     * (i.e. generated from a draw container that uses 'Ext.draw.engine.Svg' engine).
     * And some platforms may not have the means of viewing successfully saved SVG images.
     */

    destroy: function () {
        var callbackId = this.frameCallbackId;
        if (callbackId) {
            Ext.draw.Animator.removeFrameCallback(callbackId);
        }
        this.callParent();
    }

}, function () {
    if (location.search.match('svg')) {
        Ext.draw.Container.prototype.engine = 'Ext.draw.engine.Svg';
    } else if ((Ext.os.is.BlackBerry && Ext.os.version.getMajor() === 10) || (Ext.browser.is.AndroidStock4 && (Ext.os.version.getMinor() === 1 || Ext.os.version.getMinor() === 2 || Ext.os.version.getMinor() === 3))) {
        // http://code.google.com/p/android/issues/detail?id=37529
        Ext.draw.Container.prototype.engine = 'Ext.draw.engine.Svg';
    }
});

/**
 * Abstract class that provides default styles for non-specified things.
 * Should be sub-classed when creating new themes.
 * For example:
 *
 *     Ext.define('Ext.chart.theme.Custom', {
 *         extend: 'Ext.chart.theme.Base',
 *         singleton: true,
 *         alias: 'chart.theme.custom',
 *         config: {
 *             baseColor: '#ff9f00'
 *         }
 *     });
 *
 * Theme provided values will not override the values provided in an instance config.
 * Except if a theme provided value is an object, in that case it will be merged with
 * the value from the instance config, unless the theme provided value (object)
 * has a '$default' key set to 'true'.
 *
 * Certain chart theme configs (e.g. 'fontSize') may use the 'default' value to indicate
 * that they should inherit a value from the corresponding CSS style provided by
 * a framework theme. Additionally, one can use basic binary operators like multiplication,
 * addition and subtraction to derive from the default value, e.g. fontSize: 'default*1.3'.
 */
Ext.define('Ext.chart.theme.Base', {

    mixins: {
        factoryable: 'Ext.mixin.Factoryable'
    },

    requires: ['Ext.draw.Color'],

    factoryConfig: {
        type: 'chart.theme'
    },

    isTheme: true,

    config: {
        /**
         * @cfg {String/Ext.draw.Color}
         * The base color used to generate the {@link #colors} of the theme.
         */
        baseColor: null,

        /**
         * @cfg {Array} Array of colors/gradients to be used by the theme.
         * Defaults to {@link #colorDefaults}.
         */
        colors: undefined,

        /**
         * @cfg {Object} The gradient config to be used by series' sprites. E.g.:
         *
         *     {
         *       type: 'linear',
         *       degrees: 90
         *     }
         *
         * Please refer to the documentation for the {@link Ext.draw.gradient.Linear linear}
         * and {@link Ext.draw.gradient.Radial radial} gradients for all possible options.
         * The color {@link Ext.draw.gradient.Gradient#stops stops} for the gradients
         * will be generated by the theme based on the {@link #colors} config.
         */
        gradients: null,

        /**
         * @cfg {Object} chart
         * Theme defaults for the chart.
         * Can apply to all charts or just a specific type of chart.
         * For example:
         *
         *     chart: {
         *       defaults: {
         *         background: 'lightgray'
         *       },
         *       polar: {
         *         background: 'green'
         *       }
         *     }
         *
         * The values from the chart.defaults and chart.*type* configs (where *type* is a valid
         * chart xtype, e.g. '{@link Ext.chart.CartesianChart cartesian}' or '{@link Ext.chart.PolarChart polar}')
         * will be applied to corresponding chart configs.
         * E.g., the chart.defaults.background config will set the {@link Ext.chart.AbstractChart#background}
         * config of all charts, where the chart.cartesian.flipXY config will only set the
         * {@link Ext.chart.CartesianChart#flipXY} config of all cartesian charts.
         */
        chart: {
            defaults: {
                background: 'white'
            }
        },

        /**
         * @cfg {Object} axis
         * Theme defaults for the axes.
         * Can apply to all axes or only axes with a specific position.
         * For example:
         *
         *     axis: {
         *       defaults: {
         *         style: {strokeStyle: 'red'}
         *       },
         *       left: {
         *         title: {fillStyle: 'green'}
         *       }
         *     }
         *
         * The values from the axis.defaults and axis.*position* configs (where *position*
         * is a valid axis {@link Ext.chart.axis.Axis#position}, e.g. 'bottom') will be
         * applied to corresponding {@link Ext.chart.axis.Axis axis} configs.
         * E.g., the axis.defaults.label config will apply to the {@link Ext.chart.axis.Axis#label}
         * config of all axes, where the axis.left.titleMargin config will only apply to the
         * {@link Ext.chart.axis.Axis#titleMargin} config of all axes positioned to the left.
         */
        axis: {
            defaults: {
                label: {
                    x: 0,
                    y: 0,
                    textBaseline: 'middle',
                    textAlign: 'center',
                    fontSize: 'default',
                    fontFamily: 'default',
                    fontWeight: 'default',
                    fillStyle: 'black'
                },
                title: {
                    fontSize: 'default*1.23',
                    fontFamily: 'default',
                    fontWeight: 'default'
                },
                style: {
                    strokeStyle: 'black'
                },
                grid: {
                    strokeStyle: 'rgb(221, 221, 221)'
                }
            },
            top: {
                style: {
                    textPadding: 5
                }
            },
            bottom: {
                style: {
                    textPadding: 5
                }
            }
        },

        /**
         * @cfg {Object} series
         * Theme defaults for the series.
         * Can apply to all series or just a specific type of series.
         * For example:
         *
         *     series: {
         *       defaults: {
         *         style: {
         *           lineWidth: 2
         *         }
         *       },
         *       bar: {
         *         animation: {
         *           easing: 'bounceOut',
         *           duration: 1000
         *         }
         *       }
         *     }
         *
         * The values from the series.defaults and series.*type* configs (where *type*
         * is a valid series {@link Ext.chart.series.Series#type}, e.g. 'line') will be
         * applied to corresponding series configs.
         * E.g., the series.defaults.label config will apply to the {@link Ext.chart.series.Series#label}
         * config of all series, where the series.line.step config will only apply to the
         * {@link Ext.chart.series.Line#step} config of {@link Ext.chart.series.Line line} series.
         */
        series: {
            defaults: {
                label: {
                    fontFamily: 'default',
                    fontWeight: 'default',
                    fontSize: 'default*1.077',
                    textBaseline: 'middle',
                    textAlign: 'center'
                },
                labelOverflowPadding: 5
            }
        },

        /**
         * @cfg {Object} sprites
         * Default style for the custom chart sprites by type.
         * For example:
         *
         *     sprites: {
         *       text: {
         *         fontWeight: 300
         *       }
         *     }
         *
         * These sprite attribute overrides will apply to custom sprites of all charts
         * specified using the {@link Ext.draw.Container#sprites} config.
         * The overrides are specified by sprite type, e.g. sprites.text config
         * tells to apply given attributes to all {@link Ext.draw.sprite.Text text} sprites.
         */
        sprites: {
            text: {
                fontSize: 'default',
                fontWeight: 'default',
                fontFamily: 'default',
                fillStyle: 'black'
            }
        },

        seriesThemes: undefined,

        markerThemes: {
            type: ['circle', 'cross', 'plus', 'square', 'triangle', 'diamond']
        },

        /**
         * @deprecated Use the {@link #gradients} config instead.
         * @since 5.0.1
         */
        useGradients: false,

        /**
         * @deprecated Use the {@link #chart.background} config instead.
         * @since 5.0.1
         */
        background: null
    },

    colorDefaults: [ '#94ae0a', '#115fa6', '#a61120', '#ff8809', '#ffd13e', '#a61187', '#24ad9a', '#7c7474', '#a66111' ],

    constructor: function (config) {
        this.initConfig(config);
        this.resolveDefaults();
    },

    defaultRegEx: /^default([+\-/\*]\d+(?:\.\d+)?)?$/,

    defaultOperators: {
        '*': function (v1, v2) {
            return v1 * v2;
        },
        '+': function (v1, v2) {
            return v1 + v2;
        },
        '-': function (v1, v2) {
            return v1 - v2;
        }
    },

    resolveDefaults: function () {
        var me = this;
        Ext.onReady(function () {
            var sprites = Ext.clone(me.getSprites()),
                axis = Ext.clone(me.getAxis()),
                series = Ext.clone(me.getSeries()),
                div, key, config;

            if (!me.superclass.defaults) {
                div = Ext.getBody().createChild({tag: 'div', cls: 'x-component'});
                me.superclass.defaults = {
                    fontFamily: div.getStyle('fontFamily'),
                    fontWeight: div.getStyle('fontWeight'),
                    fontSize: parseFloat(div.getStyle('fontSize')),
                    fontVariant: div.getStyle('fontVariant'),
                    fontStyle: div.getStyle('fontStyle')
                };
                div.destroy();
            }

            me.replaceDefaults(sprites.text);
            me.setSprites(sprites);

            for (key in axis) {
                config = axis[key];
                me.replaceDefaults(config.label);
                me.replaceDefaults(config.title);
            }
            me.setAxis(axis);

            for (key in series) {
                config = series[key];
                me.replaceDefaults(config.label);
            }
            me.setSeries(series);
        });
    },

    replaceDefaults: function (target) {
        var me = this,
            defaults = me.superclass.defaults,
            defaultRegEx = me.defaultRegEx,
            key, value, match, binaryFn;

        if (Ext.isObject(target)) {
            for (key in defaults) {
                match = defaultRegEx.exec(target[key]);
                if (match) {
                    value = defaults[key];
                    match = match[1];
                    if (match) {
                        binaryFn = me.defaultOperators[match.charAt(0)];
                        value = Math.round(binaryFn(value, parseFloat(match.substr(1))));
                    }
                    target[key] = value;
                }
            }
        }
    },

    applyBaseColor: function (baseColor) {
        var midColor, midL;
        if (baseColor) {
            midColor = baseColor.isColor ? baseColor : Ext.draw.Color.fromString(baseColor);
            midL = midColor.getHSL()[2];
            if (midL < 0.15) {
                midColor = midColor.createLighter(0.3);
            } else if (midL < 0.3) {
                midColor = midColor.createLighter(0.15);
            } else if (midL > 0.85) {
                midColor = midColor.createDarker(0.3);
            } else if (midL > 0.7) {
                midColor = midColor.createDarker(0.15);
            }
            this.setColors([
                midColor.createDarker(0.3).toString(),
                midColor.createDarker(0.15).toString(),
                midColor.toString(),
                midColor.createLighter(0.12).toString(),
                midColor.createLighter(0.24).toString(),
                midColor.createLighter(0.31).toString()
            ]);
        }
        return baseColor;
    },

    applyColors: function (newColors) {
        return newColors || this.colorDefaults;
    },

    updateUseGradients: function (useGradients) {
        if (useGradients) {
            this.updateGradients({
                type: 'linear',
                degrees: 90
            });
        }
    },

    updateBackground: function (background) {
        if (background) {
            var chart = this.getChart();
            chart.defaults.background = background;
            this.setChart(chart);
        }
    },

    updateGradients: function (gradients) {
        var colors = this.getColors(),
            items = [], gradient,
            midColor, color,
            i, ln;

        if (Ext.isObject(gradients)) {
            for (i = 0, ln = colors && colors.length || 0; i < ln; i++) {
                midColor = Ext.draw.Color.fromString(colors[i]);
                if (midColor) {
                    color = midColor.createLighter(0.15).toString();
                    gradient = Ext.apply(Ext.Object.chain(gradients), {
                        stops: [
                            {
                                offset: 1,
                                color: midColor.toString()
                            },
                            {
                                offset: 0,
                                color: color.toString()
                            }
                        ]
                    });
                    items.push(gradient);
                }
            }
            this.setColors(items);
        }
    },

    applySeriesThemes: function (newSeriesThemes) {
        this.getBaseColor();
        this.getUseGradients();
        this.getGradients();
        var colors = this.getColors();
        if (!newSeriesThemes) {
            newSeriesThemes = {
                fillStyle: Ext.Array.clone(colors),
                strokeStyle: Ext.Array.map(colors, function (value) {
                    var color = Ext.draw.Color.fromString(value.stops ? value.stops[0].color : value);
                    return color.createDarker(0.15).toString();
                })
            };
        }
        return newSeriesThemes;
    }

});

Ext.define('Ext.chart.theme.Default', {
    extend: 'Ext.chart.theme.Base',
    singleton: true,
    alias: [
        'chart.theme.default',
        'chart.theme.Base'
    ]
});

/**
 * @class Ext.chart.Markers
 * @extends Ext.draw.sprite.Instancing
 * 
 * Marker sprite. A specialized version of instancing sprite that groups instances.
 * Putting a marker is grouped by its category id. Clearing removes that category.
 */
Ext.define('Ext.chart.Markers', {
    extend: 'Ext.draw.sprite.Instancing',

    defaultCategory: 'default',

    constructor: function () {
        this.callParent(arguments);
        // `categories` maps category names to a map that maps instance index in category to its global index:
        // categoryName: {instanceIndexInCategory: globalInstanceIndex}
        this.categories = {};
        // The `revisions` map keeps revision numbers of instance categories.
        // When a marker (instance) is put (created or updated), it gets the revision
        // of the category. When a category is cleared, its revision is incremented,
        // but its instances are not removed.
        // An instance is only rendered if its revision matches category revision.
        // In other words, a marker has to be put again after its category has been cleared
        // or it won't render.
        this.revisions = {};
    },

    /**
     * Clears the markers in the category.
     * @param {String} category
     */
    clear: function (category) {
        category = category || this.defaultCategory;
        if (!(category in this.revisions)) {
            this.revisions[category] = 1;
        } else {
            this.revisions[category]++;
        }
    },

    /**
     * Puts a marker in the category with additional attributes.
     * @param {String} category
     * @param {Object} attr
     * @param {String|Number} index
     * @param {Boolean} [canonical]
     * @param {Boolean} [keepRevision]
     */
    putMarkerFor: function (category, attr, index, canonical, keepRevision) {
        category = category || this.defaultCategory;

        var me = this,
            categoryInstances = me.categories[category] || (me.categories[category] = {}),
            instance;
        if (index in categoryInstances) {
            me.setAttributesFor(categoryInstances[index], attr, canonical);
        } else {
            categoryInstances[index] = me.getCount(); // the newly created instance will go into me.instances
            me.createInstance(attr, canonical);
        }
        instance = me.get(categoryInstances[index]);
        if (instance) {
            instance.category = category;
            if (!keepRevision) {
                instance.revision = me.revisions[category] || (me.revisions[category] = 1);
            }
        }
    },

    /**
     *
     * @param {String} category
     * @param {Mixed} index
     * @param {Boolean} [isWithoutTransform]
     */
    getMarkerBBoxFor: function (category, index, isWithoutTransform) {
        if (category in this.categories) {
            var categoryInstances = this.categories[category];
            if (index in categoryInstances) {
                return this.getBBoxFor(categoryInstances[index], isWithoutTransform);
            }
        }
    },

    getBBox: function () { return null; },

    render: function (surface, ctx, clipRect) {
        var me = this,
            revisions = me.revisions,
            mat = me.attr.matrix,
            template = me.getTemplate(),
            templateAttr = template.attr,
            instance, i, ln;

        mat.toContext(ctx);
        template.preRender(surface, ctx, clipRect);
        template.useAttributes(ctx, clipRect);

        for (i = 0, ln = me.instances.length; i < ln; i++) {
            instance = me.get(i);
            if (instance.hidden || instance.revision !== revisions[instance.category]) {
                continue;
            }
            ctx.save();
            template.attr = instance;
            template.useAttributes(ctx, clipRect);
            template.render(surface, ctx, clipRect);
            ctx.restore();
        }
        template.attr = templateAttr;
    }
});

/**
 * @class Ext.chart.label.Callout
 * @extends Ext.draw.modifier.Modifier
 *
 * This is a modifier to place labels and callouts by additional attributes.
 */
Ext.define('Ext.chart.label.Callout', {
    extend: 'Ext.draw.modifier.Modifier',

    prepareAttributes: function (attr) {
        if (!attr.hasOwnProperty('calloutOriginal')) {
            attr.calloutOriginal = Ext.Object.chain(attr);
            // No __proto__, nor getPrototypeOf in IE8,
            // so manually saving a reference to 'attr' after chaining.
            attr.calloutOriginal.prototype = attr;
        }
        if (this._previous) {
            this._previous.prepareAttributes(attr.calloutOriginal);
        }
    },

    setAttrs: function (attr, changes) {
        var callout = attr.callout,
            origin = attr.calloutOriginal,
            bbox = attr.bbox.plain,
            width = (bbox.width || 0) + attr.labelOverflowPadding,
            height = (bbox.height || 0) + attr.labelOverflowPadding,
            dx, dy;

        if ('callout' in changes) {
            callout = changes.callout;
        }

        if ('callout' in changes || 'calloutPlaceX' in changes || 'calloutPlaceY' in changes || 'x' in changes || 'y' in changes) {
            var rotationRads = 'rotationRads' in changes ? origin.rotationRads = changes.rotationRads : origin.rotationRads,
                x = 'x' in changes ? (origin.x = changes.x) : origin.x,
                y = 'y' in changes ? (origin.y = changes.y) : origin.y,
                calloutPlaceX = 'calloutPlaceX' in changes ? changes.calloutPlaceX : attr.calloutPlaceX,
                calloutPlaceY = 'calloutPlaceY' in changes ? changes.calloutPlaceY : attr.calloutPlaceY,
                calloutVertical = 'calloutVertical' in changes ? changes.calloutVertical : attr.calloutVertical,
                temp;

            // Normalize Rotations
            rotationRads %= Math.PI * 2;
            if (Math.cos(rotationRads) < 0) {
                rotationRads = (rotationRads + Math.PI) % (Math.PI * 2);
            }

            if (rotationRads > Math.PI) {
                rotationRads -= Math.PI * 2;
            }

            if (calloutVertical) {
                rotationRads = rotationRads * (1 - callout) - Math.PI / 2 * callout;
                temp = width;
                width = height;
                height = temp;
            } else {
                rotationRads = rotationRads * (1 - callout);
            }
            changes.rotationRads = rotationRads;


            // Placing a label in the middle of a pie slice (x/y)
            // if callout doesn't exists (callout=0),
            // or outside the pie slice (calloutPlaceX/Y) if it does (callout=1).
            changes.x = x * (1 - callout) + calloutPlaceX * callout;
            changes.y = y * (1 - callout) + calloutPlaceY * callout;


            dx = calloutPlaceX - x;
            dy = calloutPlaceY - y;
            // Finding where the callout line intersects the bbox of the label
            // if it were to go to the center of the label,
            // and make that intersection point the end of the callout line.
            // Effectively, the end of the callout line traces label's bbox when chart is rotated.
            if (Math.abs(dy * width) > Math.abs(dx * height)) {
                // on top/bottom
                if (dy > 0) {
                    changes.calloutEndX = changes.x - (height / 2) * (dx / dy) * callout;
                    changes.calloutEndY = changes.y - (height / 2) * callout;
                } else {
                    changes.calloutEndX = changes.x + (height / 2) * (dx / dy) * callout;
                    changes.calloutEndY = changes.y + (height / 2) * callout;
                }
            } else {
                // on left/right
                if (dx > 0) {
                    changes.calloutEndX = changes.x - width / 2;
                    changes.calloutEndY = changes.y - (width / 2) * (dy / dx) * callout;
                } else {
                    changes.calloutEndX = changes.x + width / 2;
                    changes.calloutEndY = changes.y + (width / 2) * (dy / dx) * callout;
                }
            }
            // Since the length of the callout line is adjusted depending on the label's position
            // and dimensions, we hide the callout line if the length becomes negative.
            if (changes.calloutStartX && changes.calloutStartY) {
                changes.calloutHasLine =
                    (dx > 0 && changes.calloutStartX < changes.calloutEndX) ||
                    (dx <= 0 && changes.calloutStartX > changes.calloutEndX) ||
                    (dy > 0 && changes.calloutStartY < changes.calloutEndY) ||
                    (dy <= 0 && changes.calloutStartY > changes.calloutEndY);
            } else {
                changes.calloutHasLine = true;
            }
        }

        return changes;
    },

    pushDown: function (attr, changes) {
        changes = Ext.draw.modifier.Modifier.prototype.pushDown.call(this, attr.calloutOriginal, changes);
        return this.setAttrs(attr, changes);
    },

    popUp: function (attr, changes) {
        attr = attr.prototype;
        changes = this.setAttrs(attr, changes);
        if (this._next) {
            return this._next.popUp(attr, changes);
        } else {
            return Ext.apply(attr, changes);
        }
    }
});

/**
 * @class Ext.chart.label.Label
 * @extends Ext.draw.sprite.Text
 *
 * Sprite used to represent labels in series.
 */
Ext.define('Ext.chart.label.Label', {
    extend: 'Ext.draw.sprite.Text',
    requires: ['Ext.chart.label.Callout'],

    inheritableStatics: {
        def: {
            processors: {
                callout: 'limited01',
                calloutHasLine: 'bool',
                calloutPlaceX: 'number',
                calloutPlaceY: 'number',
                calloutStartX: 'number',
                calloutStartY: 'number',
                calloutEndX: 'number',
                calloutEndY: 'number',
                calloutColor: 'color',
                calloutWidth: 'number',
                calloutVertical: 'bool',
                labelOverflowPadding: 'number',
                display: 'enums(none,under,over,rotate,insideStart,insideEnd,inside,outside)',
                orientation: 'enums(horizontal,vertical)',
                renderer: 'default'
            },
            defaults: {
                callout: 0,
                calloutHasLine: true,
                calloutPlaceX: 0,
                calloutPlaceY: 0,
                calloutStartX: 0,
                calloutStartY: 0,
                calloutEndX: 0,
                calloutEndY: 0,
                calloutWidth: 1,
                calloutVertical: false,
                calloutColor: 'black',
                labelOverflowPadding: 5,
                display: 'none',
                orientation: '',
                renderer: null
            },

            dirtyTriggers: {
                callout: 'transform',
                calloutPlaceX: 'transform',
                calloutPlaceY: 'transform',
                labelOverflowPadding: 'transform',
                calloutRotation: 'transform',
                display: 'hidden'
            },

            updaters: {
                hidden: function (attrs) {
                    attrs.hidden = attrs.display === 'none';
                }
            }
        }
    },

    config: {
        /**
         * @cfg {Object} fx Animation configuration.
         */
        fx: {
            customDurations: {
                callout: 200
            }
        },
        field: null,
        /**
         * @cfg {Boolean|Object} calloutLine
         *
         * True to draw a line between the label and the chart with the default settings,
         * or an Object that defines the 'color', 'width' and 'length' properties of the line.
         * This config is only applicable when the label is displayed outside the chart.
         *
         * Default value: false.
         */
        calloutLine: true
    },

    applyCalloutLine: function (calloutLine) {
        if (calloutLine) {
            return Ext.apply({}, calloutLine);
        }
    },

    prepareModifiers: function () {
        this.callParent(arguments);
        this.calloutModifier = new Ext.chart.label.Callout({sprite: this});
        this.fx.setNext(this.calloutModifier);
        this.calloutModifier.setNext(this.topModifier);
    },

    render: function (surface, ctx) {
        var me = this,
            attr = me.attr,
            calloutColor = attr.calloutColor;

        ctx.save();
        ctx.globalAlpha *= attr.callout;
        if (ctx.globalAlpha > 0 && attr.calloutHasLine) {
            if (calloutColor && calloutColor.isGradient) {
                calloutColor = calloutColor.getStops()[0].color;
            }
            ctx.strokeStyle = calloutColor;
            ctx.fillStyle = calloutColor;
            ctx.lineWidth = attr.calloutWidth;
            ctx.beginPath();
            ctx.moveTo(me.attr.calloutStartX, me.attr.calloutStartY);
            ctx.lineTo(me.attr.calloutEndX, me.attr.calloutEndY);
            ctx.stroke();

            ctx.beginPath();
            ctx.arc(me.attr.calloutStartX, me.attr.calloutStartY, 1 * attr.calloutWidth, 0, 2 * Math.PI, true);
            ctx.fill();

            ctx.beginPath();
            ctx.arc(me.attr.calloutEndX, me.attr.calloutEndY, 1 * attr.calloutWidth, 0, 2 * Math.PI, true);
            ctx.fill();
        }
        ctx.restore();

        Ext.draw.sprite.Text.prototype.render.apply(me, arguments);
    }
});

/**
 * Series is the abstract class containing the common logic to all chart series. Series includes
 * methods from Labels, Highlights, and Callouts mixins. This class implements the logic of
 * animating, hiding, showing all elements and returning the color of the series to be used as a legend item.
 *
 * ## Listeners
 *
 * The series class supports listeners via the Observable syntax. Some of these listeners are:
 *
 *  - `itemmouseup` When the user interacts with a marker.
 *  - `itemmousedown` When the user interacts with a marker.
 *  - `itemmousemove` When the user interacts with a marker.
 *  - (similar `item*` events occur for many raw mouse and touch events)
 *  - `afterrender` Will be triggered when the animation ends or when the series has been rendered completely.
 *
 * For example:
 *
 *     series: [{
 *         type: 'bar',
 *         axis: 'left',
 *         listeners: {
 *             'afterrender': function() {
 *                 console('afterrender');
 *             }
 *         },
 *         xField: 'category',
 *         yField: 'data1'
 *     }]
 *
 */
Ext.define('Ext.chart.series.Series', {

    requires: [
        'Ext.chart.Markers',
        'Ext.chart.label.Label',
        'Ext.tip.ToolTip'
    ],

    mixins: {
        observable: 'Ext.mixin.Observable'
    },

    /**
     * @property {String} type
     * The type of series. Set in subclasses.
     * @protected
     */
    type: null,

    /**
     * @property {String} seriesType
     * Default series sprite type.
     */
    seriesType: 'sprite',

    identifiablePrefix: 'ext-line-',

    observableType: 'series',

    darkerStrokeRatio: 0.15,

    /**
     * @event chartattached
     * Fires when the {@link Ext.chart.AbstractChart} has been attached to this series.
     * @param {Ext.chart.AbstractChart} chart
     * @param {Ext.chart.series.Series} series
     */
    /**
     * @event chartdetached
     * Fires when the {@link Ext.chart.AbstractChart} has been detached from this series.
     * @param {Ext.chart.AbstractChart} chart
     * @param {Ext.chart.series.Series} series
     */

    config: {
        /**
         * @private
         * @cfg {Object} chart The chart that the series is bound.
         */
        chart: null,

        /**
         * @cfg {String|String[]} title
         * The human-readable name of the series (displayed in the legend).
         */
        title: null,

        /**
         * @cfg {Function} renderer
         * A function that can be provided to set custom styling properties to each rendered element.
         * It receives `(sprite, config, rendererData, index)` as parameters.
         *
         * @param {Object} sprite The sprite affected by the renderer. The visual attributes are in `sprite.attr`.
         * The data field is available in `sprite.getField()`.
         * @param {Object} config The sprite configuration. It varies with the series and the type of sprite:
         * for instance, a Line chart sprite might have just the `x` and `y` properties while a Bar
         * chart sprite also has `width` and `height`. A `type` might be present too. For instance to
         * draw each marker and each segment of a Line chart, the renderer is called with the
         * `config.type` set to either `marker` or `line`.
         * @param {Object} rendererData A record with different properties depending on the type of chart.
         * The only guaranteed property is `rendererData.store`, the store used by the series.
         * In some cases, a store may not exist: for instance a Gauge chart may read its value directly
         * from its configuration; in this case rendererData.store is null and the value is
         * available in rendererData.value.
         * @param {Number} index The index of the sprite. It is usually the index of the store record associated
         * with the sprite, in which case the record can be obtained with `store.getData().items[index]`.
         * If the chart is not associated with a store, the index represents the index of the sprite within
         * the series. For instance a Gauge chart may have as many sprites as there are sectors in the
         * background of the gauge, plus one for the needle.
         *
         * @return {Object} The attributes that have been changed or added. Note: it is usually possible to
         * add or modify the attributes directly into the `config` parameter and not return anything,
         * but returning an object with only those attributes that have been changed may allow for
         * optimizations in the rendering of some series. Example to draw every other marker in red:
         *
         *      renderer: function (sprite, config, rendererData, index) {
         *          if (config.type === 'marker') {
         *              return { strokeStyle: (index % 2 === 0 ? 'red' : 'black') };
         *          }
         *      }
         */
        renderer: null,

        /**
         * @cfg {Boolean} showInLegend
         * Whether to show this series in the legend.
         */
        showInLegend: true,

        //@private triggerdrawlistener flag
        triggerAfterDraw: false,

        /**
         * @cfg {Object} style Custom style configuration for the sprite used in the series.
         * It overrides the style that is provided by the current theme.
         */
        style: {},

        /**
         * @cfg {Object} subStyle This is the cyclic used if the series has multiple sprites.
         */
        subStyle: {},

        /**
         * @private
         * @cfg {Object} themeStyle Style configuration that is provided by the current theme.
         * It is composed of five objects:
         * @cfg {Object} themeStyle.style Properties common to all the series, for instance the 'lineWidth'.
         * @cfg {Object} themeStyle.subStyle Cyclic used if the series has multiple sprites.
         * @cfg {Object} themeStyle.label Sprite config for the labels, for instance the font and color.
         * @cfg {Object} themeStyle.marker Sprite config for the markers, for instance the size and stroke color.
         * @cfg {Object} themeStyle.markerSubStyle Cyclic used if series have multiple marker sprites.
         */
        themeStyle: {},

        /**
         * @cfg {Array} colors
         * An array of color values which is used, in order of appearance, by the series. Each series
         * can request one or more colors from the array. Radar, Scatter or Line charts require just 
         * one color each. Candlestick and OHLC require two (1 for drops + 1 for rises). Pie charts  
         * and Stacked charts (like Column or Pie charts) require one color for each data category 
         * they represent, so one color for each slice of a Pie chart or each segment of a Column chart.
         * It overrides the colors that are provided by the current theme.
         */
        colors: null,

        /**
         * @cfg {Boolean|Number} useDarkerStrokeColor
         * Colors for the series can be set directly through the 'colors' config, or indirectly
         * with the current theme or the 'colors' config that is set onto the chart. These colors
         * are used as "fill color". Set this config to true, if you want a darker color for the
         * strokes. Set it to false if you want to use the same color as the fill color.
         * Alternatively, you can set it to a number between 0 and 1 to control how much darker
         * the strokes should be.
         */
        useDarkerStrokeColor: true,

        /**
         * @protected
         * @cfg {Object} store The store of values used in the series.
         */
        store: null,

        /**
         * @cfg {Object} label
         * Object with the following properties:
         *
         * @cfg {String} label.display
         *
         * Specifies the presence and position of the labels. The possible values depend on the chart type.
         * For Line charts: 'under' | 'over' | 'rotate'.
         * For Bar charts: 'insideStart' | 'insideEnd' | 'outside'.
         * For Pie charts: 'outside' | 'rotate'.
         * For all charts: 'none' hides the labels.
         *
         * Default value: 'none'.
         *
         * @cfg {String} label.color
         *
         * The color of the label text.
         *
         * Default value: '#000' (black).
         *
         * @cfg {String|String[]} label.field
         *
         * The name(s) of the field(s) to be displayed in the labels. If your chart has 3 series
         * that correspond to the fields 'a', 'b', and 'c' of your model and you only want to
         * display labels for the series 'c', you must still provide an array `[null, null, 'c']`.
         *
         * Default value: null.
         *
         * @cfg {String} label.font
         *
         * The font used for the labels.
         *
         * Default value: '14px Helvetica'.
         *
         * @cfg {String} label.orientation
         *
         * Either 'horizontal' or 'vertical'. If not set (default), the orientation is inferred
         * from the value of the flipXY property of the series.
         *
         * Default value: ''.
         *
         * @cfg {Function} label.renderer
         *
         * Optional function for formatting the label into a displayable value.
         *
         * The arguments to the method are:
         *
         *   - *`text`*, *`sprite`*, *`config`*, *`rendererData`*, *`index`*
         *
         *     Label's renderer is passed the same arguments as {@link #renderer}
         *     plus one extra 'text' argument which comes first.
         *
         * @return {Object|String} The attributes that have been changed or added, or the text for the label.
         * Example to enclose every other label in parentheses:
         *
         *      renderer: function (text) {
         *          if (index % 2 == 0) {
         *              return '(' + text + ')'
         *          }
         *      }
         *
         * Default value: null.
         */
        label: {},

        /**
         * @cfg {Number} labelOverflowPadding
         * Extra distance value for which the labelOverflow listener is triggered.
         */
        labelOverflowPadding: null,

        /**
         * @cfg {String|String[]} labelField
         * @deprecated Use 'field' property of {@link Ext.chart.series.Series#label} instead.
         * The store record field name to be used for the series labels.
         */
        labelField: null,

        /**
         * @cfg {Boolean} showMarkers
         * Whether markers should be displayed at the data points along the line. If true,
         * then the {@link #marker} config item will determine the markers' styling.
         */
        showMarkers: true,

        /**
         * @cfg {Object} marker
         * The sprite template used by marker instances on the series.
         */
        marker: null,

        /**
         * @cfg {Object} markerSubStyle
         * This is cyclic used if series have multiple marker sprites.
         */
        markerSubStyle: null,

        /**
         * @protected
         * @cfg {Object} itemInstancing The sprite template used to create sprite instances in the series.
         */
        itemInstancing: null,

        /**
         * @cfg {Object} background Sets the background of the surface the series is attached.
         */
        background: null,

        /**
         * @cfg {Object} highlightItem The item currently highlighted in the series.
         */
        highlightItem: null,

        /**
         * @protected
         * @cfg {Object} surface The surface that the series is attached.
         */
        surface: null,

        /**
         * @protected
         * @cfg {Object} overlaySurface The surface that series markers are attached.
         */
        overlaySurface: null,

        /**
         * @cfg {Boolean|Array} hidden
         */
        hidden: false,

        /**
         * @cfg {Boolean/Object} highlight
         * The sprite attributes that will be applied to the highlighted items in the series.
         * If set to 'true', the default highlight style from {@link #highlightCfg} will be used.
         * If the value of this config is an object, it will be merged with the {@link #highlightCfg}.
         * In case merging of 'highlight' and 'highlightCfg' configs in not the desired behavior,
         * provide the 'highlightCfg' instead.
         */
        highlight: false,

        /**
         * @protected
         * @cfg {Object} highlightCfg
         * The default style for the highlighted item.
         * Used when {@link #highlight} config was simply set to 'true' instead of specifying a style.
         */
        highlightCfg: {
            // Make custom highlightCfg's in subclasses replace this one.
            merge: function (value) {
                return value;
            },
            $value: {
                fillStyle: 'yellow',
                strokeStyle: 'red'
            }
        },

        /**
         * @cfg {Object} animation The series animation configuration.
         */
        animation: null,

        /**
         * @cfg {Object} tooltip
         * Add tooltips to the visualization's markers. The options for the tooltip are the
         * same configuration used with {@link Ext.tip.ToolTip}. For example:
         *
         *     tooltip: {
         *       trackMouse: true,
         *       width: 140,
         *       height: 28,
         *       renderer: function (storeItem, item) {
         *           this.setHtml(storeItem.get('name') + ': ' + storeItem.get('data1') + ' views');
         *       }
         *     }
         */
        tooltip: null
    },

    directions: [],

    sprites: null,

    /**
     * @private
     * Returns the number of colors this series needs.
     * A Pie chart needs one color per slice while a Stacked Bar chart needs one per segment.
     * An OHLC chart needs 2 colors (one for drops, one for rises), and most other charts need just 1 color.
     */
    themeColorCount: function() {
        return 1;
    },

    /**
     * @private
     * Returns the number of markers this series needs.
     * Currently, only the Line, Scatter and Radar series use markers - and they need just one each.
     */
    themeMarkerCount: function() {
        return 0;
    },

    getFields: function (fieldCategory) {
        var me = this,
            fields = [], fieldsItem,
            i, ln;
        for (i = 0, ln = fieldCategory.length; i < ln; i++) {
            fieldsItem = me['get' + fieldCategory[i] + 'Field']();
            if (Ext.isArray(fieldsItem)) {
                fields.push.apply(fields, fieldsItem);
            } else {
                fields.push(fieldsItem);
            }
        }
        return fields;
    },

    applyAnimation: function (newAnimation, oldAnimation) {
        if (!newAnimation) {
            newAnimation = {
                duration: 0
            };
        } else if (newAnimation === true) {
            newAnimation = {
                easing: 'easeInOut',
                duration: 500
            };
        }
        return oldAnimation ? Ext.apply({}, newAnimation, oldAnimation) : newAnimation;
    },

    updateTitle: function (newTitle) {
        var me = this,
            chart = me.getChart();
        if (!chart || chart.isInitializing) {
            return;
        }
        var newTitle = Ext.Array.from(newTitle),
            series = chart.getSeries(),
            seriesIndex = Ext.Array.indexOf(series, me),
            legendStore = chart.getLegendStore(),
            ln = Math.min(newTitle.length, me.getYField().length),
            i, item, title;

        if (seriesIndex !== -1) {
            for (i = 0; i < ln; i++) {
                title = newTitle[i];
                if (title) {
                    item = legendStore.getAt(seriesIndex + i);
                    item.set('name', title);
                }
            }
        }
    },

    applyHighlight: function (highlight, oldHighlight) {
        if (Ext.isObject(highlight)) {
            highlight = Ext.merge({}, this.config.highlightCfg, highlight);
        } else if (highlight === true) {
            highlight = this.config.highlightCfg;
        }
        return Ext.apply(oldHighlight || {}, highlight);
    },

    applyItemInstancing: function (instancing, oldInstancing) {
        return Ext.merge(oldInstancing || {}, instancing);
    },

    setAttributesForItem: function (item, change) {
        if (item && item.sprite) {
            if (item.sprite.itemsMarker && item.category === 'items') {
                item.sprite.putMarker(item.category, change, item.index, false, true);
            }
            if (item.sprite.isMarkerHolder && item.category === 'markers') {
                item.sprite.putMarker(item.category, change, item.index, false, true);
            } else if (item.sprite instanceof Ext.draw.sprite.Instancing) {
                item.sprite.setAttributesFor(item.index, change);
            } else {
                item.sprite.setAttributes(change);
            }
        }
    },

    getBBoxForItem: function (item) {
        if (item && item.sprite) {
            if (item.sprite.itemsMarker && item.category === 'items') {
                return item.sprite.getMarkerBBox(item.category, item.index);
            } else if (item.sprite instanceof Ext.draw.sprite.Instancing) {
                return item.sprite.getBBoxFor(item.index);
            } else {
                return item.sprite.getBBox();
            }
        }
        return null;
    },

    applyHighlightItem: function (newHighlightItem, oldHighlightItem) {
        if (newHighlightItem === oldHighlightItem) {
            return;
        }
        if (Ext.isObject(newHighlightItem) && Ext.isObject(oldHighlightItem)) {
            if (newHighlightItem.sprite === oldHighlightItem.sprite &&
                newHighlightItem.index === oldHighlightItem.index
                ) {
                return;
            }
        }
        return newHighlightItem;
    },

    updateHighlightItem: function (newHighlightItem, oldHighlightItem) {
        this.setAttributesForItem(oldHighlightItem, {highlighted: false});
        this.setAttributesForItem(newHighlightItem, {highlighted: true});
    },

    constructor: function (config) {
        var me = this;
        me.getId();
        me.sprites = [];
        me.dataRange = [];
        Ext.ComponentManager.register(me);

        if (config) {
            // Backward compatibility with Ext.
            if (config.tips) {
                config = Ext.apply({
                    tooltip: config.tips
                }, config);
            }
            // Backward compatibility with Touch.
            if (config.highlightCfg) {
                config = Ext.apply({
                    highlight: config.highlightCfg
                }, config);
            }
        }

        me.mixins.observable.constructor.call(me, config);
    },

    applyTooltip: function (tooltip, oldTooltip) {
        var chart = this.getChart(),
            interactions = chart.getInteractions(),
            i, hasItemHighlight;
        var config = Ext.apply({}, tooltip, {
            renderer: Ext.emptyFn,
            constrainPosition: true,
            shrinkWrapDock: true,
            autoHide: true,
            offsetX: 10,
            offsetY: 10
        });
        for (i = 0; i < interactions.length; i++) {
            if (interactions[i].type === 'itemhightlight') {
                hasItemHighlight = true;
                break;
            }
        }
        if (!hasItemHighlight) {
            interactions.push('itemhighlight');
            chart.setInteractions(interactions);
        }
        return new Ext.tip.ToolTip(config);
    },

    showTip: function (item, xy) {
        var me = this,
            tooltip = me.getTooltip(),
            sprite, surface, surfaceEl,
            pos, point,
            bbox, x, y,
            config,
            isRtl;

        if (!tooltip) {
            return;
        }
        clearTimeout(me.tooltipTimeout);
        config = tooltip.config;
        if (tooltip.trackMouse) {
            xy[0] += config.offsetX;
            xy[1] += config.offsetY;
        } else {
            sprite = item.sprite;
            surface = sprite.getSurface();
            surfaceEl = Ext.get(surface.getId());
            if (surfaceEl) {
                bbox = item.series.getBBoxForItem(item);
                x = bbox.x + bbox.width / 2;
                y = bbox.y + bbox.height / 2;
                point = surface.matrix.transformPoint([x, y]);
                pos = surfaceEl.getXY();
                isRtl = surface.getInherited().rtl;
                x = isRtl ? pos[0] + surfaceEl.getWidth() - point[0] : pos[0] + point[0];
                y = pos[1] + point[1];
                xy = [x, y];
            }
        }
        tooltip.config.renderer.call(tooltip, item.record, item);
        tooltip.show(xy);
    },

    hideTip: function (item) {
        var me = this,
            tooltip = me.getTooltip();
        if (!tooltip) {
            return;
        }
        clearTimeout(me.tooltipTimeout);
        me.tooltipTimeout = Ext.defer(function () {
            tooltip.hide();
        }, 1);
    },

    applyStore: function (store) {
        return store && Ext.StoreManager.lookup(store);
    },

    getStore: function () {
        return this._store || this.getChart() && this.getChart().getStore();
    },

    updateStore: function (newStore, oldStore) {
        var me = this,
            chartStore = this.getChart() && this.getChart().getStore(),
            sprites = me.getSprites(),
            ln = sprites.length,
            i, sprite;
        newStore = newStore || chartStore;
        oldStore = oldStore || chartStore;

        if (oldStore) {
            oldStore.un({
                datachanged: 'onDataChanged',
                update: 'onDataChanged',
                scope: me
            });
        }
        if (newStore) {
            newStore.on({
                datachanged: 'onDataChanged',
                update: 'onDataChanged',
                scope: me
            });
            for (i = 0; i < ln; i++) {
                sprite = sprites[i];
                if (sprite.setStore) {
                    sprite.setStore(newStore);
                }
            }
            me.onDataChanged();
        }
    },

    onStoreChange: function (store, oldStore) {
        if (!this._store) {
            this.updateStore(store, oldStore);
        }
    },

    coordinateStacked: function (direction, directionOffset, directionCount) {
        var me = this,
            store = me.getStore(),
            items = store.getData().items,
            axis = me['get' + direction + 'Axis'](),
            hidden = me.getHidden(),
            range = {min: 0, max: 0},
            directions = me['fieldCategory' + direction],
            fieldCategoriesItem,
            i, j, k, fields, field, data, style = {},
            dataStart = [], dataEnd, posDataStart = [], negDataStart = [],
            stacked = me.getStacked(),
            sprites = me.getSprites();

        if (sprites.length > 0) {
            for (i = 0; i < directions.length; i++) {
                fieldCategoriesItem = directions[i];
                fields = me.getFields([fieldCategoriesItem]);
                for (j = 0; j < items.length; j++) {
                    dataStart[j] = 0;
                    posDataStart[j] = 0;
                    negDataStart[j] = 0;
                }
                for (j = 0; j < fields.length; j++) {
                    style = {};
                    field = fields[j];
                    if (hidden[j]) {
                        style['dataStart' + fieldCategoriesItem] = dataStart;
                        style['data' + fieldCategoriesItem] = dataStart;
                        sprites[j].setAttributes(style);
                        continue;
                    }
                    data = me.coordinateData(items, field, axis);
                    if (stacked) {
                        dataEnd = [];
                        for (k = 0; k < items.length; k++) {
                            if (!data[k]) {
                                data[k] = 0;
                            }
                            if (data[k] >= 0) {
                                dataStart[k] = posDataStart[k];
                                posDataStart[k] += data[k];
                                dataEnd[k] = posDataStart[k];
                            } else {
                                dataStart[k] = negDataStart[k];
                                negDataStart[k] += data[k];
                                dataEnd[k] = negDataStart[k];
                            }
                        }
                        style['dataStart' + fieldCategoriesItem] = dataStart;
                        style['data' + fieldCategoriesItem] = dataEnd;
                        me.getRangeOfData(dataStart, range);
                        me.getRangeOfData(dataEnd, range);
                    } else {
                        style['dataStart' + fieldCategoriesItem] = dataStart;
                        style['data' + fieldCategoriesItem] = data;
                        me.getRangeOfData(data, range);
                    }
                    sprites[j].setAttributes(style);
                }
            }
            me.dataRange[directionOffset] = range.min;
            me.dataRange[directionOffset + directionCount] = range.max;
            style = {};
            style['dataMin' + direction] = range.min;
            style['dataMax' + direction] = range.max;
            for (i = 0; i < sprites.length; i++) {
                sprites[i].setAttributes(style);
            }
        }
    },

    coordinate: function (direction, directionOffset, directionCount) {
        var me = this,
            store = me.getStore(),
            hidden = me.getHidden(),
            items = store.getData().items,
        // TODO: in this.processData we check if we have the getX(Y)Axis method,
        // TODO: if we don't, we call coordinateX(Y) instead, which calls this method,
        // TODO: but here we just call getX(Y)Axis even though it doesn't exist
        // TODO: (check cartesian charts without axes)
            axis = me['get' + direction + 'Axis'](),
            range = {min: Infinity, max: -Infinity},
            fieldCategory = me['fieldCategory' + direction] || [direction],
            fields = me.getFields(fieldCategory),
            i, field, data, style = {},
            sprites = me.getSprites();
        if (sprites.length > 0) {
            if (!Ext.isBoolean(hidden) || !hidden) {
                for (i = 0; i < fieldCategory.length; i++) {
                    field = fields[i];
                    data = me.coordinateData(items, field, axis);
                    me.getRangeOfData(data, range);
                    style['data' + fieldCategory[i]] = data;
                }
            }
            me.dataRange[directionOffset] = range.min;
            me.dataRange[directionOffset + directionCount] = range.max;
            style['dataMin' + direction] = range.min;
            style['dataMax' + direction] = range.max;
            if (axis) {
                axis.range = null;
                style['range' + direction] = axis.getRange();
            }
            for (i = 0; i < sprites.length; i++) {
                sprites[i].setAttributes(style);
            }
        }
    },

    /**
     * @private
     * This method will return an array containing data coordinated by a specific axis.
     * @param {Array} items
     * @param {String} field
     * @param {Ext.chart.axis.Axis} axis
     * @return {Array}
     */
    coordinateData: function (items, field, axis) {
        var data = [],
            length = items.length,
            layout = axis && axis.getLayout(),
            coord = axis ? function (x, field, idx, items) {
                return layout.getCoordFor(x, field, idx, items);
            } : function (x) { return +x; },
            i, x;
        for (i = 0; i < length; i++) {
            x = items[i].data[field];
            data[i] = !Ext.isEmpty(x) ? coord(x, field, i, items) : x;
        }
        return data;
    },

    getRangeOfData: function (data, range) {
        var i, length = data.length,
            value, min = range.min, max = range.max;
        for (i = 0; i < length; i++) {
            value = data[i];
            if (value < min) {
                min = value;
            }
            if (value > max) {
                max = value;
            }
        }
        range.min = min;
        range.max = max;
    },

    updateLabelData: function () {
        var me = this,
            store = me.getStore(),
            items = store.getData().items,
            sprites = me.getSprites(),
            labelTpl = me.getLabel().getTemplate(),
            labelFields = Ext.Array.from(labelTpl.getField() || me.getLabelField()),
            i, j, ln, labels,
            sprite, field;

        if (!sprites.length || !labelFields.length) {
            return;
        }

        for (i = 0; i < sprites.length; i++) {
            labels = [];
            sprite = sprites[i];
            field = sprite.getField();
            if (Ext.Array.indexOf(labelFields, field) < 0) {
                field = labelFields[i];
            }
            for (j = 0, ln = items.length; j < ln; j++) {
                labels.push(items[j].get(field));
            }
            sprite.setAttributes({labels: labels});
        }
    },

    updateLabelField: function (labelField) {
        var labelTpl = this.getLabel().getTemplate();
        if (!labelTpl.config.field) {
            labelTpl.setField(labelField);
        }
    },

    processData: function () {
        if (!this.getStore()) {
            return;
        }

        var me = this,
            directions = this.directions,
            i, ln = directions.length,
            direction, axis;

        for (i = 0; i < ln; i++) {
            direction = directions[i];
            if (me['get' + direction + 'Axis']) {
                axis = me['get' + direction + 'Axis']();
                if (axis) {
                    axis.processData(me);
                    continue;
                }
            }
            if (me['coordinate' + direction]) {
                me['coordinate' + direction]();
            }
        }
        me.updateLabelData();
    },

    applyBackground: function (background) {
        if (this.getChart()) {
            this.getSurface().setBackground(background);
            return this.getSurface().getBackground();
        } else {
            return background;
        }
    },

    updateChart: function (newChart, oldChart) {
        var me = this;
        if (oldChart) {
            oldChart.un('axeschange', 'onAxesChange', me);
            // TODO: destroy them
            me.sprites = [];
            me.setSurface(null);
            me.setOverlaySurface(null);
            me.onChartDetached(oldChart);
        }
        if (newChart) {
            me.setSurface(newChart.getSurface('series'));
            me.setOverlaySurface(newChart.getSurface('overlay'));

            newChart.on('axeschange', 'onAxesChange', me);
            // TODO: Gauge series should render correctly when chart's store is missing.
            // TODO: When store is initially missing the getAxes will return null here,
            // TODO: since applyAxes has actually triggered this series.updateChart call
            // TODO: indirectly.
            // TODO: Figure out why it doesn't go this route when a store is present.
            if (newChart.getAxes()) {
                me.onAxesChange(newChart);
            }
            me.onChartAttached(newChart);
        }

        me.updateStore(me._store, null);
    },

    onAxesChange: function (chart) {
        var me = this,
            axes = chart.getAxes(), axis,
            directionToAxesMap = {},
            directionToFieldsMap = {},
            needHighPrecision = false,
            directions = this.directions, direction,
            i, ln;

        for (i = 0, ln = directions.length; i < ln; i++) {
            direction = directions[i];
            directionToFieldsMap[direction] = me.getFields(me['fieldCategory' + direction]);
        }

        for (i = 0, ln = axes.length; i < ln; i++) {
            axis = axes[i];
            if (!directionToAxesMap[axis.getDirection()]) {
                directionToAxesMap[axis.getDirection()] = [axis];
            } else {
                directionToAxesMap[axis.getDirection()].push(axis);
            }
        }

        for (i = 0, ln = directions.length; i < ln; i++) {
            direction = directions[i];
            if (me['get' + direction + 'Axis']()) {
                continue;
            }
            if (directionToAxesMap[direction]) {
                axis = me.findMatchingAxis(directionToAxesMap[direction], directionToFieldsMap[direction]);
                if (axis) {
                    me['set' + direction + 'Axis'](axis);
                    if (axis.getNeedHighPrecision()) {
                        needHighPrecision = true;
                    }
                }
            }
        }
        this.getSurface().setHighPrecision(needHighPrecision);
    },

    /**
     * @private
     * Given the list of axes in a certain direction and a list of series fields in that direction
     * returns the first matching axis for the series in that direction,
     * or undefined if a match wasn't found.
     */
    findMatchingAxis: function (directionAxes, directionFields) {
        var axis, axisFields,
            i, j;

        for (i = 0; i < directionAxes.length; i++) {
            axis = directionAxes[i];
            axisFields = axis.getFields();
            if (!axisFields.length) {
                return axis;
            } else {
                if (directionFields) {
                    for (j = 0; j < directionFields.length; j++) {
                        if ( Ext.Array.indexOf(axisFields, directionFields[j]) >= 0 ) {
                            return axis;
                        }
                    }
                }
            }
        }
    },

    onChartDetached: function (oldChart) {
        var me = this;
        me.fireEvent('chartdetached', oldChart, me);
        oldChart.un('storechange', 'onStoreChange', me);
    },

    onChartAttached: function (chart) {
        var me = this;
        me.setBackground(me.getBackground());
        me.fireEvent('chartattached', chart, me);
        chart.on('storechange', 'onStoreChange', me);
        me.processData();
    },

    updateOverlaySurface: function (overlaySurface) {
        var me = this;
        if (overlaySurface) {
            if (me.getLabel()) {
                me.getOverlaySurface().add(me.getLabel());
            }
        }
    },

    applyLabel: function (newLabel, oldLabel) {
        if (!oldLabel) {
            oldLabel = new Ext.chart.Markers({zIndex: 10});
            oldLabel.setTemplate(new Ext.chart.label.Label(newLabel));
        } else {
            oldLabel.getTemplate().setAttributes(newLabel);
        }
        return oldLabel;
    },

    createItemInstancingSprite: function (sprite, itemInstancing) {
        var me = this,
            template,
            markers = new Ext.chart.Markers();

        markers.setAttributes({zIndex: Number.MAX_VALUE});
        var config = Ext.apply({}, itemInstancing);
        if (me.getHighlight()) {
            config.highlight = me.getHighlight();
            config.modifiers = ['highlight'];
        }
        markers.setTemplate(config);
        template = markers.getTemplate();
        template.setAttributes(me.getStyle());
        template.fx.on('animationstart', 'onSpriteAnimationStart', this);
        template.fx.on('animationend', 'onSpriteAnimationEnd', this);
        sprite.bindMarker('items', markers);

        me.getSurface().add(markers);
        return markers;
    },

    getDefaultSpriteConfig: function () {
        return {
            type: this.seriesType,
            renderer: this.getRenderer()
        };
    },

    updateRenderer: function (renderer) {
        var me = this,
            chart = me.getChart(),
            sprites;
        if (chart && chart.isInitializing) {
            return;
        }
        sprites = me.getSprites();
        // TODO: Removing the renderer won't revert series markers to its original
        // TODO: style, if the render modified their attributes.
        if (sprites.length) {
            sprites[0].setAttributes({renderer: renderer || null});
            if (chart && !chart.isInitializing) {
                chart.redraw();
            }
        }
    },

    createSprite: function () {
        var me = this,
            surface = me.getSurface(),
            itemInstancing = me.getItemInstancing(),
            marker, config,
            sprite = surface.add(me.getDefaultSpriteConfig());

        sprite.setAttributes(this.getStyle());

        if (itemInstancing) {
            sprite.itemsMarker = me.createItemInstancingSprite(sprite, itemInstancing);
        }

        if (sprite.bindMarker) {
            if (me.getShowMarkers() && me.getMarker()) {
                marker = new Ext.chart.Markers();
                config = Ext.Object.chain(me.getMarker());
                if (me.getHighlight()) {
                    config.highlight = me.getHighlight();
                    config.modifiers = ['highlight'];
                }
                marker.setTemplate(config);
                marker.getTemplate().fx.setCustomDurations({
                    translationX: 0,
                    translationY: 0
                });
                sprite.dataMarker = marker;
                sprite.bindMarker('markers', marker);
                me.getOverlaySurface().add(marker);
            }
            if (me.getLabel().getTemplate().getField() || me.getLabelField()) {
                sprite.bindMarker('labels', me.getLabel());
            }
        }

        if (sprite.setStore) {
            sprite.setStore(me.getStore());
        }

        sprite.fx.on('animationstart', 'onSpriteAnimationStart', me);
        sprite.fx.on('animationend', 'onSpriteAnimationEnd', me);

        me.sprites.push(sprite);

        return sprite;
    },

    /**
     * Returns sprites the are used to draw this series.
     */
    getSprites: Ext.emptyFn,

    onDataChanged: function () {
        var me = this,
            chart = me.getChart(),
            chartStore = chart && chart.getStore(),
            seriesStore = me.getStore();

        if (seriesStore !== chartStore) {
            me.processData();
        }
    },

    isXType: function (xtype) {
        return xtype === 'series';
    },

    getItemId: function () {
        return this.getId();
    },

    applyThemeStyle: function (theme, oldTheme) {
        var me = this,
            fill, stroke;

        fill = theme && theme.subStyle && theme.subStyle.fillStyle;
        stroke = fill && theme.subStyle.strokeStyle;
        if (fill && !stroke) {
            theme.subStyle.strokeStyle = me.getStrokeColorsFromFillColors(fill);
        }

        fill = theme && theme.markerSubStyle && theme.markerSubStyle.fillStyle;
        stroke = fill && theme.markerSubStyle.strokeStyle;
        if (fill && !stroke) {
            theme.markerSubStyle.strokeStyle = me.getStrokeColorsFromFillColors(fill);
        }
        return Ext.apply(oldTheme || {}, theme);
    },

    applyStyle: function (style, oldStyle) {
        // TODO: Incremental setter
        var cls = Ext.ClassManager.get(Ext.ClassManager.getNameByAlias('sprite.' + this.seriesType));
        if (cls && cls.def) {
            style = cls.def.normalize(style);
        }
        return Ext.apply(oldStyle || {}, style);
    },

    applySubStyle: function (subStyle, oldSubStyle) {
        var cls = Ext.ClassManager.get(Ext.ClassManager.getNameByAlias('sprite.' + this.seriesType));
        if (cls && cls.def) {
            subStyle = cls.def.batchedNormalize(subStyle, true);
        }
        return Ext.merge(oldSubStyle || {}, subStyle);
    },

    applyMarker: function (marker, oldMarker) {
        var type = (marker && marker.type) || (oldMarker && oldMarker.type) || 'circle',
            cls = Ext.ClassManager.get(Ext.ClassManager.getNameByAlias('sprite.' + type));
        if (cls && cls.def) {
            marker = cls.def.normalize(marker, true);
            marker.type = type;
        }
        return Ext.merge(oldMarker || {}, marker);
    },

    applyMarkerSubStyle: function (marker, oldMarker) {
        var type = (marker && marker.type) || (oldMarker && oldMarker.type) || 'circle',   // TODO:ps Should use marker theme instead of 'circle'
            cls = Ext.ClassManager.get(Ext.ClassManager.getNameByAlias('sprite.' + type));
        if (cls && cls.def) {
            marker = cls.def.batchedNormalize(marker, true);
        }
        return Ext.merge(oldMarker || {}, marker);
    },

    updateHidden: function (hidden) {
        // TODO: remove this when jacky fix the problem.
        this.getColors();
        this.getSubStyle();
        this.setSubStyle({hidden: hidden});
        this.processData();
        this.doUpdateStyles();
    },

    /**
     *
     * @param {Number} index
     * @param {Boolean} value
     */
    setHiddenByIndex: function (index, value) {
        if (Ext.isArray(this.getHidden())) {
            this.getHidden()[index] = value;
            this.updateHidden(this.getHidden());
        } else {
            this.setHidden(value);
        }
    },

    getStrokeColorsFromFillColors: function (colors) {
        var me = this,
            darker = me.getUseDarkerStrokeColor(),
            darkerRatio = (Ext.isNumber(darker) ? darker : me.darkerStrokeRatio),
            strokeColors;
        if (darker) {
            strokeColors = Ext.Array.map(colors, function (color) {
                color = Ext.isString(color) ? color : color.stops[0].color;
                color = Ext.draw.Color.fromString(color);
                return color.createDarker(darkerRatio).toString();
            });
        } else {
            strokeColors = Ext.Array.clone(colors);
        }
        return strokeColors;
    },

    updateThemeColors: function (colors) {
        var me = this,
            theme = me.getThemeStyle(),
            fillColors = Ext.Array.clone(colors),
            strokeColors = me.getStrokeColorsFromFillColors(colors),
            newSubStyle = { fillStyle: fillColors, strokeStyle: strokeColors };

        theme.subStyle = Ext.apply(theme.subStyle || {}, newSubStyle);
        theme.markerSubStyle = Ext.apply(theme.markerSubStyle || {}, newSubStyle);

        me.doUpdateStyles();
    },

    themeOnlyIfConfigured: {
    },

    updateTheme: function (theme) {
        var me = this,
            seriesTheme = theme.getSeries(),
            initialConfig = me.getInitialConfig(),
            defaultConfig = me.defaultConfig,
            configs = me.getConfigurator().configs,
            genericSeriesTheme = seriesTheme.defaults,
            specificSeriesTheme = seriesTheme[me.type],
            themeOnlyIfConfigured = me.themeOnlyIfConfigured,
            key, value, isObjValue, isUnusedConfig, initialValue, cfg;

        seriesTheme = Ext.merge({}, genericSeriesTheme, specificSeriesTheme);
        for (key in seriesTheme) {
            value = seriesTheme[key];
            cfg = configs[key];
            if (value !== null && value !== undefined && cfg) {
                initialValue = initialConfig[key];
                isObjValue = Ext.isObject(value);
                isUnusedConfig = initialValue === defaultConfig[key];
                if (isObjValue) {
                    if (isUnusedConfig && themeOnlyIfConfigured[key]) {
                        continue;
                    }
                    value = Ext.merge({}, value, initialValue);
                }
                if (isUnusedConfig || isObjValue) {
                    me[cfg.names.set](value);
                }
            }
        }
    },

    /**
     * @private
     * When the chart's "colors" config changes, these colors are passed onto the series
     * where they are used with the same priority as theme colors, i.e. they do not override
     * the series' "colors" config, nor the series' "style" config, but they do override
     * the colors from the theme's "seriesThemes" config.
     */
    updateChartColors: function (colors) {
        var me = this;

        if (!me.getColors()) {
            me.updateThemeColors(colors);
        }
    },

    updateColors: function (colors) {
        this.updateThemeColors(colors);
    },

    updateStyle: function () {
        this.doUpdateStyles();
    },

    updateSubStyle: function () {
        this.doUpdateStyles();
    },

    updateThemeStyle: function () {
        this.doUpdateStyles();
    },

    doUpdateStyles: function () {
        var me = this,
            sprites = me.sprites,
            itemInstancing = me.getItemInstancing(),
            i = 0, ln = sprites && sprites.length,
            markerCfg = me.getMarker(),
            style;

        // TODO: make sure all series work nicely with the below change
//        me.setAnimation(me.getChart().getAnimation());
        for (; i < ln; i++) {
            style = me.getStyleByIndex(i);
            if (itemInstancing) {
                sprites[i].itemsMarker.getTemplate().setAttributes(style);
            }
            sprites[i].setAttributes(style);
            if (markerCfg && sprites[i].dataMarker) {
                sprites[i].dataMarker.getTemplate().setAttributes(me.getMarkerStyleByIndex(i));
            }
        }
    },

    getStyleWithTheme: function() {
        var me = this,
            theme = me.getThemeStyle(),
            seriesThemeStyle = (theme && theme.style) || {},
            style = Ext.applyIf(Ext.apply({}, me.getStyle()), seriesThemeStyle);
        return style;
    },

    getSubStyleWithTheme: function() {
        var me = this,
            theme = me.getThemeStyle(),
            seriesThemeSubStyle = (theme && theme.subStyle) || {},
            subStyle = Ext.applyIf(Ext.apply({}, me.getSubStyle()), seriesThemeSubStyle);
        return subStyle;
    },

    // getMarkerStyleWithTheme: function() {
    //     var me = this,
    //         theme = me.getThemeStyle(),
    //         seriesThemeStyle = (theme && theme.style) || {},
    //         style = Ext.applyIf(Ext.apply({}, me.getMarker()), seriesThemeStyle);
    //     return style;
    // },

    // getMarkerSubStyleWithTheme: function() {
    //     var me = this,
    //         theme = me.getThemeStyle(),
    //         seriesThemeStyle = (theme && theme.style) || {},
    //         style = Ext.applyIf(Ext.apply({}, me.getMarkerSubStyle()), seriesThemeStyle);
    //     return style;
    // },

    getStyleByIndex: function (i) {
        var me = this,
            theme = me.getThemeStyle(),
            style, themeStyle, subStyle, themeSubStyle,
            result = {};

        style = me.getStyle();
        themeStyle = (theme && theme.style) || {};

        subStyle = me.styleDataForIndex(me.getSubStyle(), i);
        themeSubStyle = me.styleDataForIndex((theme && theme.subStyle), i);

        Ext.apply(result, themeStyle);
        Ext.apply(result, themeSubStyle);

        Ext.apply(result, style);
        Ext.apply(result, subStyle);

        return result;
    },

    getMarkerStyleByIndex: function (i) {
        var me = this,
            theme = me.getThemeStyle(),
            style, themeStyle, subStyle, themeSubStyle,
            markerStyle, themeMarkerStyle, markerSubStyle, themeMarkerSubStyle,
            result = {};

        style = me.getStyle();
        themeStyle = (theme && theme.style) || {};

        subStyle = me.styleDataForIndex(me.getSubStyle(), i);
        themeSubStyle = me.styleDataForIndex((theme && theme.subStyle), i);

        markerStyle = me.getMarker();
        themeMarkerStyle = (theme && theme.marker) || {};

        markerSubStyle = me.getMarkerSubStyle();
        themeMarkerSubStyle = me.styleDataForIndex((theme && theme.markerSubStyle), i);

        Ext.apply(result, themeStyle);
        Ext.apply(result, themeSubStyle);
        Ext.apply(result, themeMarkerStyle);
        Ext.apply(result, themeMarkerSubStyle);

        Ext.apply(result, style);
        Ext.apply(result, subStyle);
        Ext.apply(result, markerStyle);
        Ext.apply(result, markerSubStyle);

        return result;
    },

    styleDataForIndex: function (style, i) {
        var value, name, result = {};

        if (style) {
            for (name in style) {
                value = style[name];
                if (Ext.isArray(value)) {
                    result[name] = value[i % value.length];
                } else {
                    result[name] = value;
                }
            }
        }
        return result;
    },

    /**
     * For a given x/y point relative to the main rect, find a corresponding item from this
     * series, if any.
     * @param {Number} x
     * @param {Number} y
     * @param {Object} [target] optional target to receive the result
     * @return {Object} An object describing the item, or null if there is no matching item. The exact contents of
     * this object will vary by series type, but should always contain at least the following:
     *
     * @return {Ext.data.Model} return.record the record of the item.
     * @return {Array} return.point the x/y coordinates relative to the chart box of a single point
     * for this data item, which can be used as e.g. a tooltip anchor point.
     * @return {Ext.draw.sprite.Sprite} return.sprite the item's rendering Sprite.
     * @return {Number} return.subSprite the index if sprite is an instancing sprite.
     */
    getItemForPoint: Ext.emptyFn,

    getItemByIndex: function (index) {
        if (this.getSprites()) {
            var me = this,
                sprite = me.getSprites()[0],
                store = me.getStore(),
                item;

            if (sprite) {
                item = {
                    series: this,
                    category: this.getItemInstancing() ? 'items' : 'markers',
                    index: index,
                    record: store.getData().items[index],
                    field: this.getYField(),
                    sprite: sprite
                };
                return item;
            }
        }
    },

    onSpriteAnimationStart: function (sprite) {
        this.fireEvent('animationstart', sprite);
    },

    onSpriteAnimationEnd: function (sprite) {
        this.fireEvent('animationend', sprite);
    },

    /**
     * Provide legend information to target array.
     *
     * @param {Array} target
     *
     * The information consists:
     * @param {String} target.name
     * @param {String} target.markColor
     * @param {Boolean} target.disabled
     * @param {String} target.series
     * @param {Number} target.index
     */
    provideLegendInfo: function (target) {
        target.push({
            name: this.getTitle() || this.getId(),
            mark: 'black',
            disabled: this.getHidden(),
            series: this.getId(),
            index: 0
        });
    },

    destroy: function () {
        var me = this,
            store = me.getStore(),
            // Peek at the config so we don't create one just to destroy it
            tooltip = me.getConfig('tooltip', true);

        me.clearListeners();
        Ext.ComponentManager.unregister(me);
        if (store && store.getAutoDestroy()) {
            Ext.destroy(store);
        }
        me.setStore(null);
        if (tooltip) {
            Ext.destroy(tooltip);
            clearTimeout(me.tooltipTimeout);
        }
        me.callParent();
    }
});

/**
 * @class Ext.chart.interactions.Abstract
 *
 * Defines a common abstract parent class for all interactions.
 *
 */
Ext.define('Ext.chart.interactions.Abstract', {

    xtype: 'interaction',

    mixins: {
        observable: 'Ext.mixin.Observable'
    },

    config: {

        /**
         * @cfg {Object} gesture
         * Maps gestures that should be used for starting/maintaining/ending the interaction
         * to corresponding class methods.
         * @private
         */

        gestures: {
            tap: 'onGesture'
        },

        /**
         * @cfg {Ext.chart.AbstractChart} chart The chart that the interaction is bound.
         */
        chart: null,

        /**
         * @cfg {Boolean} enabled 'true' if the interaction is enabled.
         */
        enabled: true
    },

    /**
     * Android device is emerging too many events so if we re-render every frame it will take forever to finish a frame.
     * This throttle technique will limit the timespan between two frames.
     */
    throttleGap: 0,

    stopAnimationBeforeSync: false,

    constructor: function (config) {
        var me = this;
        me.mixins.observable.constructor.call(me, config);
        me.getId();
        Ext.ComponentManager.register(me);
    },

    /**
     * @protected
     * A method to be implemented by subclasses where all event attachment should occur.
     */
    initialize: Ext.emptyFn,

    updateChart: function (newChart, oldChart) {
        var me = this;
        if (oldChart === newChart) {
            return;
        }
        if (oldChart) {
            me.removeChartListener(oldChart);
        }
        if (newChart) {
            me.addChartListener();
        }
    },

    updateEnabled: function (enabled) {
        var me = this,
            chart = me.getChart();
        if (chart) {
            if (enabled) {
                me.addChartListener();
            } else {
                me.removeChartListener(chart);
            }
        }
    },

    /**
     * @protected
     * Placeholder method.
     */
    onGesture: Ext.emptyFn,

    /**
     * @protected Find and return a single series item corresponding to the given event,
     * or null if no matching item is found.
     * @param {Event} e
     * @return {Object} the item object or null if none found.
     */
    getItemForEvent: function (e) {
        var me = this,
            chart = me.getChart(),
            chartXY = chart.getEventXY(e);
        return chart.getItemForPoint(chartXY[0], chartXY[1]);
    },

    /**
     * @protected Find and return all series items corresponding to the given event.
     * @param {Event} e
     * @return {Array} array of matching item objects
     */
    getItemsForEvent: function (e) {
        var me = this,
            chart = me.getChart(),
            chartXY = chart.getEventXY(e);
        return chart.getItemsForPoint(chartXY[0], chartXY[1]);
    },

    /**
     * @private
     */
    addChartListener: function () {
        var me = this,
            chart = me.getChart(),
            gestures = me.getGestures(),
            gesture;

        if (!me.getEnabled()) {
            return;
        }

        function insertGesture(name, fn) {
            chart.addElementListener(
                name,
                // wrap the handler so it does not fire if the event is locked by another interaction
                me.listeners[name] = function (e) {
                    var locks = me.getLocks(), result;
                    if (me.getEnabled() && (!(name in locks) || locks[name] === me)) {
                        result = (Ext.isFunction(fn) ? fn : me[fn]).apply(this, arguments);
                        if (result === false && e && e.stopPropagation) {
                            e.stopPropagation();
                        }
                        return result;
                    }
                },
                me
            );
        }

        me.listeners = me.listeners || {};
        for (gesture in gestures) {
            insertGesture(gesture, gestures[gesture]);
        }
    },

    removeChartListener: function (chart) {
        var me = this,
            gestures = me.getGestures(),
            gesture;

        function removeGesture(name) {
            chart.removeElementListener(name, me.listeners[name]);
            delete me.listeners[name];
        }

        if (me.listeners) {
            for (gesture in gestures) {
                removeGesture(gesture);
            }
        }
    },

    lockEvents: function () {
        var me = this,
            locks = me.getLocks(),
            args = Array.prototype.slice.call(arguments),
            i = args.length;
        while (i--) {
            locks[args[i]] = me;
        }
    },

    unlockEvents: function () {
        var locks = this.getLocks(),
            args = Array.prototype.slice.call(arguments),
            i = args.length;
        while (i--) {
            delete locks[args[i]];
        }
    },

    getLocks: function () {
        var chart = this.getChart();
        return chart.lockedEvents || (chart.lockedEvents = {});
    },

    isMultiTouch: function () {
        if (Ext.browser.is.IE10) {
            return true;
        }
        return !(Ext.browser.is.AndroidStock2 || Ext.os.is.Desktop);
    },

    initializeDefaults: Ext.emptyFn,

    doSync: function () {
        var me = this,
            chart = me.getChart();
        if (me.syncTimer) {
            clearTimeout(me.syncTimer);
            me.syncTimer = null;
        }
        if (me.stopAnimationBeforeSync) {
            chart.resizing = true;
        }
        chart.redraw();
        if (me.stopAnimationBeforeSync) {
            chart.resizing = false;
        }
        me.syncThrottle = Date.now() + me.throttleGap;
    },

    sync: function () {
        var me = this;
        if (me.throttleGap && Ext.frameStartTime < me.syncThrottle) {
            if (me.syncTimer) {
                return;
            }
            me.syncTimer = Ext.defer(function () {
                me.doSync();
            }, me.throttleGap);
        } else {
            me.doSync();
        }
    },

    getItemId: function () {
        return this.getId();
    },

    isXType: function (xtype) {
        return xtype === 'interaction';
    },

    destroy: function () {
        var me = this,
            chart = me.getChart();
        me.removeChartListener(chart);
        Ext.ComponentManager.unregister(me);
        delete me.listeners;
        me.callParent();
    }
}, function () {
    if (Ext.browser.is.AndroidStock2) {
        this.prototype.throttleGap = 20;
    } else if (Ext.os.is.Android4) {
        this.prototype.throttleGap = 40;
    }
});

/**
 * Mixin that provides the functionality to place markers.
 */
Ext.define('Ext.chart.MarkerHolder', {
    extend: 'Ext.Mixin',

    mixinConfig: {
        id: 'markerHolder',
        after: {
            constructor: 'constructor',
            preRender: 'preRender'
        }
    },

    isMarkerHolder: true,

    constructor: function () {
        this.boundMarkers = {};
        this.cleanRedraw = false;
    },

    /**
     *
     * @param {String} name
     * @param {Ext.chart.Markers} marker
     */
    bindMarker: function (name, marker) {
        if (marker) {
            if (!this.boundMarkers[name]) {
                this.boundMarkers[name] = [];
            }
            Ext.Array.include(this.boundMarkers[name], marker);
        }
    },

    getBoundMarker: function (name) {
        return this.boundMarkers[name];
    },

    preRender: function () {
        var me = this,
            id = me.getId(),
            boundMarkers = me.boundMarkers,
            parent = me.getParent(),
            boundMarkersItem,
            name, i, ln,
            matrix;

        if (me.surfaceMatrix) {
            matrix = me.surfaceMatrix.set(1, 0, 0, 1, 0, 0);
        } else {
            matrix = me.surfaceMatrix = new Ext.draw.Matrix();
        }

        me.cleanRedraw = !this.attr.dirty;
        if (!me.cleanRedraw) {
            for (name in me.boundMarkers) {
                if (boundMarkers[name]) {
                    for (boundMarkersItem = boundMarkers[name], i = 0, ln = boundMarkersItem.length; i < ln; i++) {
                        boundMarkersItem[i].clear(id);
                    }
                }
            }
        }

        while (parent && parent.attr && parent.attr.matrix) {
            matrix.prependMatrix(parent.attr.matrix);
            parent = parent.getParent();
        }
        matrix.prependMatrix(parent.matrix);
        me.surfaceMatrix = matrix;
        me.inverseSurfaceMatrix = matrix.inverse(me.inverseSurfaceMatrix);
    },

    putMarker: function (name, attr, index, canonical, keepRevision) {
        var boundMarkersItem, i, ln, id = this.getId();
        if (this.boundMarkers[name]) {
            for (boundMarkersItem = this.boundMarkers[name], i = 0, ln = boundMarkersItem.length; i < ln; i++) {
                boundMarkersItem[i].putMarkerFor(id, attr, index, canonical, keepRevision);
            }
        }
    },

    getMarkerBBox: function (name, index, isWithoutTransform) {
        var boundMarker = this.boundMarkers[name],
            id = this.getId();
        if (boundMarker) {
            return boundMarker[0].getMarkerBBoxFor(id, index, isWithoutTransform);
        }
    }

});

/**
 * @private
 * @class Ext.chart.axis.sprite.Axis
 * @extends Ext.draw.sprite.Sprite
 *
 * The axis sprite. Currently all types of the axis will be rendered with this sprite.
 */

Ext.define('Ext.chart.axis.sprite.Axis', {
    extend: 'Ext.draw.sprite.Sprite',
    type: 'axis',
    mixins: {
        markerHolder: 'Ext.chart.MarkerHolder'
    },

    requires: ['Ext.draw.sprite.Text'],

    inheritableStatics: {
        def: {
            processors: {
                /**
                 * @cfg {Boolean} grid 'true' if the axis has a grid.
                 */
                grid: 'bool',

                /**
                 * @cfg {Boolean} axisLine 'true' if the main line of the axis is drawn.
                 */
                axisLine: 'bool',

                /**
                 * @cfg {Boolean} minorTricks 'true' if the axis has sub ticks.
                 */
                minorTicks: 'bool',

                /**
                 * @cfg {Number} minorTickSize The length of the minor ticks.
                 */
                minorTickSize: 'number',

                /**
                 * @cfg {Boolean} majorTicks 'true' if the axis has major ticks.
                 */
                majorTicks: 'bool',

                /**
                 * @cfg {Number} majorTickSize The length of the major ticks.
                 */
                majorTickSize: 'number',

                /**
                 * @cfg {Number} length The total length of the axis.
                 */
                length: 'number',

                /**
                 * @private
                 * @cfg {Number} startGap Axis start determined by the chart inset padding.
                 */
                startGap: 'number',

                /**
                 * @private
                 * @cfg {Number} endGap Axis end determined by the chart inset padding.
                 */
                endGap: 'number',

                /**
                 * @cfg {Number} dataMin The minimum value of the axis data.
                 */
                dataMin: 'number',

                /**
                 * @cfg {Number} dataMax The maximum value of the axis data.
                 */
                dataMax: 'number',

                /**
                 * @cfg {Number} visibleMin The minimum value that is displayed.
                 */
                visibleMin: 'number',

                /**
                 * @cfg {Number} visibleMax The maximum value that is displayed.
                 */
                visibleMax: 'number',

                /**
                 * @cfg {String} position The position of the axis on the chart.
                 */
                position: 'enums(left,right,top,bottom,angular,radial,gauge)',

                /**
                 * @cfg {Number} minStepSize The minimum step size between ticks.
                 */
                minStepSize: 'number',

                /**
                 * @private
                 * @cfg {Number} estStepSize The estimated step size between ticks.
                 */
                estStepSize: 'number',

                /**
                 * @private
                 * Unused.
                 */
                titleOffset: 'number',

                /**
                 * @cfg {Number} [textPadding=0]
                 * The padding around axis labels to determine collision.
                 * The default is 0 for all axes except horizontal axes of cartesian charts,
                 * where the default is 5 to prevent axis labels from blending one into another.
                 * This default is defined in the {@link Ext.chart.theme.Base#axis axis} config
                 * of the {@link Ext.chart.theme.Base Base} theme.
                 * You may want to change this default to a smaller number or 0, if you have
                 * horizontal axis labels rotated, which allows for more text to fit in.
                 */
                textPadding: 'number',

                /**
                 * @cfg {Number} min The minimum value of the axis.
                 */
                min: 'number',

                /**
                 * @cfg {Number} max The maximum value of the axis.
                 */
                max: 'number',

                /**
                 * @cfg {Number} centerX The central point of the angular axis on the x-axis.
                 */
                centerX: 'number',

                /**
                 * @cfg {Number} centerY The central point of the angular axis on the y-axis.
                 */
                centerY: 'number',

                /**
                 * @private
                 * @cfg {Number} radius
                 * Unused.
                 */
                radius: 'number',

                /**
                 * @private
                 */
                totalAngle: 'number',

                /**
                 * @cfg {Number} The starting rotation of the angular axis.
                 */
                baseRotation: 'number',

                /**
                 * @private
                 * Unused.
                 */
                data: 'default',

                /**
                 * @cfg {Boolean} 'true' if the estimated step size is adjusted by text size.
                 */
                enlargeEstStepSizeByText: 'bool'
            },

            defaults: {
                grid: false,
                axisLine: true,
                minorTicks: false,
                minorTickSize: 3,
                majorTicks: true,
                majorTickSize: 5,
                length: 0,
                startGap: 0,
                endGap: 0,
                visibleMin: 0,
                visibleMax: 1,
                dataMin: 0,
                dataMax: 1,
                position: '',
                minStepSize: 0,
                estStepSize: 20,
                min: 0,
                max: 1,
                centerX: 0,
                centerY: 0,
                radius: 1,
                baseRotation: 0,
                data: null,
                titleOffset: 0,
                textPadding: 0,
                scalingCenterY: 0,
                scalingCenterX: 0,
                // Override default
                strokeStyle: 'black',
                enlargeEstStepSizeByText: false
            },

            dirtyTriggers: {
                minorTickSize: 'bbox',
                majorTickSize: 'bbox',
                position: 'bbox,layout',
                axisLine: 'bbox,layout',
                min: 'layout',
                max: 'layout',
                length: 'layout',
                minStepSize: 'layout',
                estStepSize: 'layout',
                data: 'layout',
                dataMin: 'layout',
                dataMax: 'layout',
                visibleMin: 'layout',
                visibleMax: 'layout',
                enlargeEstStepSizeByText: 'layout'
            },
            updaters: {
                layout: function () {
                    this.doLayout();
                }
            }
        }
    },

    config: {

        /**
         * @cfg {Object} label
         *
         * The label configuration object for the Axis. This object may include style attributes
         * like `spacing`, `padding`, `font` that receives a string or number and
         * returns a new string with the modified values.
         */
        label: null,

        /**
         * @cfg {Object|Ext.chart.axis.layout.Layout} layout The layout configuration used by the axis.
         */
        layout: null,

        /**
         * @cfg {Object|Ext.chart.axis.segmenter.Segmenter} segmenter The method of segmenter used by the axis.
         */
        segmenter: null,

        /**
         * @cfg {Function} renderer Allows direct customisation of rendered axis sprites.
         */
        renderer: null,

        /**
         * @private
         * @cfg {Object} layoutContext Stores the context after calculating layout.
         */
        layoutContext: null,

        /**
         * @cfg {Ext.chart.axis.Axis} axis The axis represented by this sprite.
         */
        axis: null
    },

    thickness: 0,

    stepSize: 0,

    getBBox: function () { return null; },

    doDefaultRender: function (v) {
        // 'this' pointer in this case is a layoutContext
        return this.segmenter.renderer(v, this);
    },

    doLayout: function () {
        var me = this,
            chart = me.getAxis().getChart();
        if (chart.isInitializing) {
            return;
        }
        var attr = me.attr,
            layout = me.getLayout(),
            isRtl = chart.getInherited().rtl,
            min = attr.dataMin + (attr.dataMax - attr.dataMin) * attr.visibleMin,
            max = attr.dataMin + (attr.dataMax - attr.dataMin) * attr.visibleMax,
            context = {
                attr: attr,
                segmenter: me.getSegmenter(),
                renderer: me.doDefaultRender
            };

        if (attr.position === 'left' || attr.position === 'right') {
            attr.translationX = 0;
            attr.translationY = max * attr.length / (max - min);
            attr.scalingX = 1;
            attr.scalingY = -attr.length / (max - min);
            attr.scalingCenterY = 0;
            attr.scalingCenterX = 0;
            me.applyTransformations(true);
        } else if (attr.position === 'top' || attr.position === 'bottom') {
            if (isRtl) {
                attr.translationX = attr.length + min * attr.length / (max - min) + 1;
            } else {
                attr.translationX = -min * attr.length / (max - min);
            }
            attr.translationY = 0;
            attr.scalingX = (isRtl ? -1 : 1) * attr.length / (max - min);
            attr.scalingY = 1;
            attr.scalingCenterY = 0;
            attr.scalingCenterX = 0;
            me.applyTransformations(true);
        }

        if (layout) {
            layout.calculateLayout(context);
            me.setLayoutContext(context);
        }
    },

    iterate: function (snaps, fn) {
        var i, position,
            id, axis, floatingAxes, floatingValues,
            some = Ext.Array.some,
            abs = Math.abs,
            threshold;
        if (snaps.getLabel) {
            if (snaps.min < snaps.from) {
                fn.call(this, snaps.min, snaps.getLabel(snaps.min), -1, snaps);
            }
            for (i = 0; i <= snaps.steps; i++) {
                fn.call(this, snaps.get(i), snaps.getLabel(i), i, snaps);
            }
            if (snaps.max > snaps.to) {
                fn.call(this, snaps.max, snaps.getLabel(snaps.max), snaps.steps + 1, snaps);
            }
        } else {
            axis = this.getAxis();
            floatingAxes = axis.floatingAxes;
            floatingValues = [];
            threshold = (snaps.to - snaps.from) / (snaps.steps + 1);
            if (axis.getFloating()) {
                for (id in floatingAxes) {
                    floatingValues.push(floatingAxes[id]);
                }
            }
            // Don't render ticks in axes intersection points.
            function isTickVisible(position) {
                return !floatingValues.length || some(floatingValues, function (value) {
                    return abs(value - position) > threshold;
                });
            }
            if (snaps.min < snaps.from && isTickVisible(snaps.min)) {
                fn.call(this, snaps.min, snaps.min, -1, snaps);
            }
            for (i = 0; i <= snaps.steps; i++) {
                position = snaps.get(i);
                if (isTickVisible(position)) {
                    fn.call(this, position, position, i, snaps);
                }
            }
            if (snaps.max > snaps.to && isTickVisible(snaps.max)) {
                fn.call(this, snaps.max, snaps.max, snaps.steps + 1, snaps);
            }
        }
    },

    renderTicks: function (surface, ctx, layout, clipRect) {
        var me = this,
            attr = me.attr,
            docked = attr.position,
            matrix = attr.matrix,
            halfLineWidth = 0.5 * attr.lineWidth,
            xx = matrix.getXX(),
            dx = matrix.getDX(),
            yy = matrix.getYY(),
            dy = matrix.getDY(),
            majorTicks = layout.majorTicks,
            majorTickSize = attr.majorTickSize,
            minorTicks = layout.minorTicks,
            minorTickSize = attr.minorTickSize;

        if (majorTicks) {
            switch (docked) {
                case 'right':
                    function getRightTickFn(size) {
                        return function (position, labelText, i) {
                            position = surface.roundPixel(position * yy + dy) + halfLineWidth;
                            ctx.moveTo(0, position);
                            ctx.lineTo(size, position);
                        };
                    }
                    me.iterate(majorTicks, getRightTickFn(majorTickSize));
                    minorTicks && me.iterate(minorTicks, getRightTickFn(minorTickSize));
                    break;
                case 'left':
                    function getLeftTickFn(size) {
                        return function (position, labelText, i) {
                            position = surface.roundPixel(position * yy + dy) + halfLineWidth;
                            ctx.moveTo(clipRect[2] - size, position);
                            ctx.lineTo(clipRect[2], position);
                        };
                    }
                    me.iterate(majorTicks, getLeftTickFn(majorTickSize));
                    minorTicks && me.iterate(minorTicks, getLeftTickFn(minorTickSize));
                    break;
                case 'bottom':
                    function getBottomTickFn(size) {
                        return function (position, labelText, i) {
                            position = surface.roundPixel(position * xx + dx) - halfLineWidth;
                            ctx.moveTo(position, 0);
                            ctx.lineTo(position, size);
                        };
                    }
                    me.iterate(majorTicks, getBottomTickFn(majorTickSize));
                    minorTicks && me.iterate(minorTicks, getBottomTickFn(minorTickSize));
                    break;
                case 'top':
                    function getTopTickFn(size) {
                        return function (position, labelText, i) {
                            position = surface.roundPixel(position * xx + dx) - halfLineWidth;
                            ctx.moveTo(position, clipRect[3]);
                            ctx.lineTo(position, clipRect[3] - size);
                        };
                    }
                    me.iterate(majorTicks, getTopTickFn(majorTickSize));
                    minorTicks && me.iterate(minorTicks, getTopTickFn(minorTickSize));
                    break;
                case 'angular':
                    me.iterate(majorTicks, function (position, labelText, i) {
                        position = position / (attr.max + 1) * Math.PI * 2 + attr.baseRotation;
                        ctx.moveTo(
                            attr.centerX + (attr.length) * Math.cos(position),
                            attr.centerY + (attr.length) * Math.sin(position)
                        );
                        ctx.lineTo(
                            attr.centerX + (attr.length + majorTickSize) * Math.cos(position),
                            attr.centerY + (attr.length + majorTickSize) * Math.sin(position)
                        );
                    });
                    break;
                case 'gauge':
                    var gaugeAngles = me.getGaugeAngles();
                    me.iterate(majorTicks, function (position, labelText, i) {
                        position = (position - attr.min) / (attr.max - attr.min + 1) * attr.totalAngle - attr.totalAngle + gaugeAngles.start;
                        ctx.moveTo(
                            attr.centerX + (attr.length) * Math.cos(position),
                            attr.centerY + (attr.length) * Math.sin(position)
                        );
                        ctx.lineTo(
                            attr.centerX + (attr.length + majorTickSize) * Math.cos(position),
                            attr.centerY + (attr.length + majorTickSize) * Math.sin(position)
                        );
                    });
                    break;
            }
        }
    },

    renderLabels: function (surface, ctx, layout, clipRect) {
        var me = this,
            attr = me.attr,
            halfLineWidth = 0.5 * attr.lineWidth,
            docked = attr.position,
            matrix = attr.matrix,
            textPadding = attr.textPadding,
            xx = matrix.getXX(),
            dx = matrix.getDX(),
            yy = matrix.getYY(),
            dy = matrix.getDY(),
            thickness = 0,
            majorTicks = layout.majorTicks,
            tickPadding = Math.max(attr.majorTickSize, attr.minorTickSize) + attr.lineWidth,
            label = me.getLabel(), font, labelOffset,
            lastLabelText = null,
            textSize = 0, textCount = 0,
            segmenter = layout.segmenter,
            renderer = me.getRenderer(),
            title = me.getAxis().getTitle(),
            titleBBox = title && title.attr.text !== '' && title.getBBox(),
            labelInverseMatrix, lastBBox = null, bbox, fly, text, titlePadding;
        if (majorTicks && label && !label.attr.hidden) {
            font = label.attr.font;
            if (ctx.font !== font) {
                ctx.font = font;
            } // This can profoundly improve performance.
            label.setAttributes({translationX: 0, translationY: 0}, true, true);
            label.applyTransformations();
            labelInverseMatrix = label.attr.inverseMatrix.elements.slice(0);
            switch (docked) {
                case 'left':
                    titlePadding = titleBBox ? titleBBox.x + titleBBox.width : 0;
                    label.setAttributes({
                        translationX: surface.roundPixel(titlePadding + (clipRect[2] - titlePadding - tickPadding) / 2 + dx) - halfLineWidth
                    }, true, true);
                    break;
                case 'right':
                    titlePadding = titleBBox ? clipRect[2] - titleBBox.x : 0;
                    label.setAttributes({
                        translationX: surface.roundPixel(tickPadding + (clipRect[2] - tickPadding - titlePadding) / 2 + dx) + halfLineWidth
                    }, true, true);
                    break;
                case 'top':
                    titlePadding = titleBBox ? titleBBox.y + titleBBox.height: 0;
                    label.setAttributes({
                        translationY: surface.roundPixel(titlePadding + (clipRect[3] - titlePadding - tickPadding) / 2) - halfLineWidth
                    }, true, true);
                    break;
                case 'bottom':
                    titlePadding = titleBBox ? clipRect[3] - titleBBox.y : 0;
                    label.setAttributes({
                        translationY: surface.roundPixel(tickPadding + (clipRect[3] - tickPadding - titlePadding) / 2) + halfLineWidth
                    }, true, true);
                    break;
                case 'radial' :
                    label.setAttributes({
                        translationX: attr.centerX
                    }, true, true);
                    break;
                case 'angular':
                    label.setAttributes({
                        translationY: attr.centerY
                    }, true, true);
                    break;
                case 'gauge':
                    label.setAttributes({
                        translationY: attr.centerY
                    }, true, true);
                    break;
            }

            // TODO: there are better ways to detect collision.
            if (docked === 'left' || docked === 'right') {
                me.iterate(majorTicks, function (position, labelText, i) {
                    if (labelText === undefined) {
                        return;
                    }
                    text = renderer ? renderer.call(me, labelText, layout, lastLabelText) : segmenter.renderer(labelText, layout, lastLabelText);
                    lastLabelText = labelText;
                    label.setAttributes({
                        text: String(text),
                        translationY: surface.roundPixel(position * yy + dy)
                    }, true, true);
                    label.applyTransformations();
                    thickness = Math.max(thickness, label.getBBox().width + tickPadding);
                    if (thickness <= me.thickness) {
                        fly = Ext.draw.Matrix.fly(label.attr.matrix.elements.slice(0));
                        bbox = fly.prepend.apply(fly, labelInverseMatrix).transformBBox(label.getBBox(true));
                        if (lastBBox && !Ext.draw.Draw.isBBoxIntersect(bbox, lastBBox, textPadding)) {
                            return;
                        }
                        surface.renderSprite(label);
                        lastBBox = bbox;
                        textSize += bbox.height;
                        textCount++;
                    }
                });
            } else if (docked === 'top' || docked === 'bottom') {
                me.iterate(majorTicks, function (position, labelText, i) {
                    if (labelText === undefined) {
                        return;
                    }
                    // TODO: When a custom renderer is used, the labels don't go through the segmenter.renderer
                    // TODO: and so if they are numbers they don't get rounded, we have to take care of it in our
                    // TODO: custom renderer. It may appear that they have the precision we want already, so
                    // TODO: for our custom renderer we could just use function (v) { return v + '%'; }, but this is
                    // TODO: deceiving, as the min/max values are not aligned by the Numeric (or Time) segmenter
                    // TODO: (see layout.Continuous#snapEnds) since segmenter.from simply returns the passed value.
                    // TODO: So what we get is sever jittering during axis range animation because the min/max
                    // TODO: labels (typically max, since min is often 0) will have varying mantissa during animation.
                    // TODO: The min/max should always be aligned as well as intermidiate ticks, because who really
                    // TODO: wants to have axis with values like this [-13.5233, -10, -5, 0, 5, 10, 12.2352]?
                    // TODO: Granted, it will only look this way if we defined our own custom render and don't do
                    // TODO: the rounding there, like in the example above. But even without a custom renderer
                    // TODO: the labels will look like this [-13, -10, -5, 0, 5, 10, 12]. The spacing between the
                    // TODO: ticks and the grid lines is still inconsistent, which doesn't look right to most everybody.
                    // TODO: Alternatively, alignMaximum/MinimumByMajorUnit configs should be implemented
                    // TODO: and set to 'true' by default.
                    // TODO: For now taking care of rounding in custom renderers is the way to go.
                    text = renderer ? renderer.call(this, labelText, layout, lastLabelText) : segmenter.renderer(labelText, layout, lastLabelText);
                    lastLabelText = labelText;
                    label.setAttributes({
                        text: String(text),
                        translationX: surface.roundPixel(position * xx + dx)
                    }, true, true);
                    label.applyTransformations();
                    thickness = Math.max(thickness, label.getBBox().height + tickPadding);
                    if (thickness <= me.thickness) {
                        fly = Ext.draw.Matrix.fly(label.attr.matrix.elements.slice(0));
                        bbox = fly.prepend.apply(fly, labelInverseMatrix).transformBBox(label.getBBox(true));
                        if (lastBBox && !Ext.draw.Draw.isBBoxIntersect(bbox, lastBBox, textPadding)) {
                            return;
                        }
                        surface.renderSprite(label);
                        lastBBox = bbox;
                        textSize += bbox.width;
                        textCount++;
                    }
                });
            } else if (docked === 'radial') {
                me.iterate(majorTicks, function (position, labelText, i) {
                    if (labelText === undefined) {
                        return;
                    }
                    text = renderer ? renderer.call(me, labelText, layout, lastLabelText) : segmenter.renderer(labelText, layout, lastLabelText);
                    lastLabelText = labelText;
                    if (typeof text !== 'undefined') {
                        label.setAttributes({
                            text: String(text),
                            translationX: attr.centerX - surface.roundPixel(position) / attr.max * attr.length * Math.cos(attr.baseRotation + Math.PI / 2),
                            translationY: attr.centerY - surface.roundPixel(position) / attr.max * attr.length * Math.sin(attr.baseRotation + Math.PI / 2)
                        }, true, true);
                        label.applyTransformations();
                        bbox = label.attr.matrix.transformBBox(label.getBBox(true));
                        if (lastBBox && !Ext.draw.Draw.isBBoxIntersect(bbox, lastBBox)) {
                            return;
                        }
                        surface.renderSprite(label);
                        lastBBox = bbox;
                        textSize += bbox.width;
                        textCount++;
                    }
                });
            } else if (docked === 'angular') {
                labelOffset = attr.majorTickSize + attr.lineWidth * 0.5 + (parseInt(label.attr.fontSize, 10) || 10) / 2;
                me.iterate(majorTicks, function (position, labelText, i) {
                    if (labelText === undefined) {
                        return;
                    }
                    text = renderer ? renderer.call(me, labelText, layout, lastLabelText) : segmenter.renderer(labelText, layout, lastLabelText);
                    lastLabelText = labelText;
                    thickness = Math.max(thickness, Math.max(attr.majorTickSize, attr.minorTickSize) + (attr.lineCap !== 'butt' ? attr.lineWidth * 0.5 : 0));
                    if (typeof text !== 'undefined') {
                        var angle = position / (attr.max + 1) * Math.PI * 2 + attr.baseRotation;
                        label.setAttributes({
                            text: String(text),
                            translationX: attr.centerX + (attr.length + labelOffset) * Math.cos(angle),
                            translationY: attr.centerY + (attr.length + labelOffset) * Math.sin(angle)
                        }, true, true);
                        label.applyTransformations();
                        bbox = label.attr.matrix.transformBBox(label.getBBox(true));
                        if (lastBBox && !Ext.draw.Draw.isBBoxIntersect(bbox, lastBBox)) {
                            return;
                        }
                        surface.renderSprite(label);
                        lastBBox = bbox;
                        textSize += bbox.width;
                        textCount++;
                    }
                });
            } else if (docked === 'gauge') {
                var gaugeAngles = me.getGaugeAngles();
                me.iterate(majorTicks, function (position, labelText, i) {
                    if (labelText === undefined) {
                        return;
                    }
                    text = renderer ? renderer.call(me, labelText, layout, lastLabelText) : segmenter.renderer(labelText, layout, lastLabelText);
                    lastLabelText = labelText;

                    if (typeof text !== 'undefined') {
                        var angle = (position - attr.min) / (attr.max - attr.min + 1) * attr.totalAngle - attr.totalAngle + gaugeAngles.start;
                        label.setAttributes({
                            text: String(text),
                            translationX: attr.centerX + (attr.length + 10) * Math.cos(angle),
                            translationY: attr.centerY + (attr.length + 10) * Math.sin(angle)
                        }, true, true);
                        label.applyTransformations();
                        bbox = label.attr.matrix.transformBBox(label.getBBox(true));
                        if (lastBBox && !Ext.draw.Draw.isBBoxIntersect(bbox, lastBBox)) {
                            return;
                        }
                        surface.renderSprite(label);
                        lastBBox = bbox;
                        textSize += bbox.width;
                        textCount++;
                    }
                });
            }

            if (attr.enlargeEstStepSizeByText && textCount) {
                textSize /= textCount;
                textSize += tickPadding;
                textSize *= 2;
                if (attr.estStepSize < textSize) {
                    attr.estStepSize = textSize;
                }
            }

            if (Math.abs(me.thickness - (thickness)) > 1) {
                me.thickness = thickness;
                attr.bbox.plain.dirty = true;
                attr.bbox.transform.dirty = true;
                me.doThicknessChanged();
                return false;
            }
        }
    },

    renderAxisLine: function (surface, ctx, layout, clipRect) {
        var me = this,
            attr = me.attr,
            halfLineWidth = attr.lineWidth * 0.5,
            docked = attr.position,
            position, gaugeAngles;
        if (attr.axisLine && attr.length) {
            switch (docked) {
                case 'left':
                    position = surface.roundPixel(clipRect[2]) - halfLineWidth;
                    ctx.moveTo(position, -attr.endGap);
                    ctx.lineTo(position, attr.length + attr.startGap);
                    break;
                case 'right':
                    ctx.moveTo(halfLineWidth, -attr.endGap);
                    ctx.lineTo(halfLineWidth, attr.length + attr.startGap);
                    break;
                case 'bottom':
                    ctx.moveTo(-attr.startGap, halfLineWidth);
                    ctx.lineTo(attr.length + attr.endGap, halfLineWidth);
                    break;
                case 'top':
                    position = surface.roundPixel(clipRect[3]) - halfLineWidth;
                    ctx.moveTo(-attr.startGap, position);
                    ctx.lineTo(attr.length + attr.endGap, position);
                    break;
                case 'angular':
                    ctx.moveTo(attr.centerX + attr.length, attr.centerY);
                    ctx.arc(attr.centerX, attr.centerY, attr.length, 0, Math.PI * 2, true);
                    break;
                case 'gauge':
                    gaugeAngles = me.getGaugeAngles();
                    ctx.moveTo(attr.centerX + Math.cos(gaugeAngles.start) * attr.length,
                        attr.centerY + Math.sin(gaugeAngles.start) * attr.length);
                    ctx.arc(attr.centerX, attr.centerY, attr.length, gaugeAngles.start, gaugeAngles.end, true);
                    break;
            }
        }
    },

    getGaugeAngles: function () {
        var me = this,
            angle = me.attr.totalAngle,
            offset;
        if (angle <= Math.PI) {
            offset = (Math.PI - angle) * 0.5;
        } else {
            offset = -(Math.PI * 2 - angle) * 0.5;
        }
        offset = Math.PI * 2 - offset;
        return {
            start: offset,
            end: offset - angle
        };
    },

    renderGridLines: function (surface, ctx, layout, clipRect) {
        var me = this,
            attr = me.attr,
            matrix = attr.matrix,
            startGap = attr.startGap,
            endGap = attr.endGap,
            xx = matrix.getXX(),
            yy = matrix.getYY(),
            dx = matrix.getDX(),
            dy = matrix.getDY(),
            position = attr.position,
            majorTicks = layout.majorTicks,
            anchor, j, lastAnchor;
        if (attr.grid) {
            if (majorTicks) {
                if (position === 'left' || position === 'right') {
                    lastAnchor = attr.min * yy + dy + endGap + startGap;
                    me.iterate(majorTicks, function (position, labelText, i) {
                        anchor = position * yy + dy + endGap;
                        me.putMarker('horizontal-' + (i % 2 ? 'odd' : 'even'), {
                            y: anchor,
                            height: lastAnchor - anchor
                        }, j = i, true);
                        lastAnchor = anchor;
                    });
                    j++;
                    anchor = 0;
                    me.putMarker('horizontal-' + (j % 2 ? 'odd' : 'even'), {
                        y: anchor,
                        height: lastAnchor - anchor
                    }, j, true);
                } else if (position === 'top' || position === 'bottom') {
                    lastAnchor = attr.min * xx + dx + startGap;
                    if (startGap) {
                        me.putMarker('vertical-even', {
                            x: 0,
                            width: lastAnchor
                        }, -1, true);
                    }
                    me.iterate(majorTicks, function (position, labelText, i) {
                        anchor = position * xx + dx + startGap;
                        me.putMarker('vertical-' + (i % 2 ? 'odd' : 'even'), {
                            x: anchor,
                            width: lastAnchor - anchor
                        }, j = i, true);
                        lastAnchor = anchor;
                    });
                    j++;
                    anchor = attr.length + attr.startGap + attr.endGap;
                    me.putMarker('vertical-' + (j % 2 ? 'odd' : 'even'), {
                        x: anchor,
                        width: lastAnchor - anchor
                    }, j, true);
                } else if (position === 'radial') {
                    me.iterate(majorTicks, function (position, labelText, i) {
                        if (!position) {
                            return;
                        }
                        anchor = position / attr.max * attr.length;
                        me.putMarker('circular-' + (i % 2 ? 'odd' : 'even'), {
                            scalingX: anchor,
                            scalingY: anchor
                        }, i, true);
                        lastAnchor = anchor;
                    });
                } else if (position === 'angular') {
                    me.iterate(majorTicks, function (position, labelText, i) {
                        if (!attr.length) {
                            return;
                        }
                        anchor = position / (attr.max + 1) * Math.PI * 2 + attr.baseRotation;
                        me.putMarker('radial-' + (i % 2 ? 'odd' : 'even'), {
                            rotationRads: anchor,
                            rotationCenterX: 0,
                            rotationCenterY: 0,
                            scalingX: attr.length,
                            scalingY: attr.length
                        }, i, true);
                        lastAnchor = anchor;
                    });
                }
            }
        }
    },

    renderLimits: function () {
        var me = this,
            axis = me.getAxis(),
            limits = Ext.Array.from(axis.getLimits());

        if (!limits.length) {
            return;
        }

        var limitsRect = axis.limits.surface.getRect(),
            attr = me.attr,
            matrix = attr.matrix,
            position = attr.position,
            chain = Ext.Object.chain,
            titles = axis.limits.titles,
            titleBBox, titlePosition, titleFlip,
            limit, value,
            i, ln, x, y;

        titles.instances = [];
        titles.position = 0;

        if (position === 'left' || position === 'right') {
            for (i = 0, ln = limits.length; i < ln; i++) {
                limit = chain(limits[i]);
                !limit.line && (limit.line = {});
                value = Ext.isString(limit.value) ? axis.getCoordFor(limit.value) : limit.value;
                value = value * matrix.getYY() + matrix.getDY();
                limit.line.y = value;
                limit.line.strokeStyle = limit.line.strokeStyle || attr.strokeStyle;
                me.putMarker('horizontal-limit-lines', limit.line, i, true);
                if (limit.line.title) {
                    titles.createInstance(limit.line.title);
                    titleBBox = titles.getBBoxFor(titles.position - 1);
                    titlePosition = limit.line.title.position || (position === 'left' ? 'start' : 'end');
                    switch (titlePosition) {
                        case 'start':
                            x = 10;
                            break;
                        case 'end':
                            x = limitsRect[2] - 10;
                            break;
                        case 'middle':
                            x = limitsRect[2] / 2;
                            break;
                    }
                    titles.setAttributesFor(titles.position - 1, {
                        x: x,
                        y: value - titleBBox.height / 2,
                        textAlign: titlePosition,
                        fillStyle: limit.line.title.fillStyle || limit.line.strokeStyle
                    });
                }
            }
        } else if (position === 'top' || position === 'bottom') {
            for (i = 0, ln = limits.length; i < ln; i++) {
                limit = chain(limits[i]);
                !limit.line && (limit.line = {});
                value = Ext.isString(limit.value) ? axis.getCoordFor(limit.value) : limit.value;
                value = value * matrix.getXX() + matrix.getDX();
                limit.line.x = value;
                limit.line.strokeStyle = limit.line.strokeStyle || attr.strokeStyle;
                me.putMarker('vertical-limit-lines', limit.line, i, true);
                if (limit.line.title) {
                    titles.createInstance(limit.line.title);
                    titleBBox = titles.getBBoxFor(titles.position - 1);
                    titlePosition = limit.line.title.position || (position === 'top' ? 'end' : 'start');
                    switch (titlePosition) {
                        case 'start':
                            y = limitsRect[3] - titleBBox.width / 2 - 10;
                            break;
                        case 'end':
                            y = titleBBox.width / 2 + 10;
                            break;
                        case 'middle':
                            y = limitsRect[3] / 2;
                            break;
                    }
                    titles.setAttributesFor(titles.position - 1, {
                        x: value + titleBBox.height / 2,
                        y: y,
                        fillStyle: limit.line.title.fillStyle || limit.line.strokeStyle,
                        rotationRads: Math.PI / 2
                    });
                }
            }
        } else if (position === 'radial') {
            for (i = 0, ln = limits.length; i < ln; i++) {
                limit = chain(limits[i]);
                !limit.line && (limit.line = {});
                value = Ext.isString(limit.value) ? axis.getCoordFor(limit.value) : limit.value;
                if (value > attr.max) {
                    continue;
                }
                value = value / attr.max * attr.length;
                limit.line.cx = attr.centerX;
                limit.line.cy = attr.centerY;
                limit.line.scalingX = value;
                limit.line.scalingY = value;
                limit.line.strokeStyle = limit.line.strokeStyle || attr.strokeStyle;
                me.putMarker('circular-limit-lines', limit.line, i, true);
                if (limit.line.title) {
                    titles.createInstance(limit.line.title);
                    titleBBox = titles.getBBoxFor(titles.position - 1);
                    titles.setAttributesFor(titles.position - 1, {
                        x: attr.centerX,
                        y: attr.centerY - value - titleBBox.height / 2,
                        fillStyle: limit.line.title.fillStyle || limit.line.strokeStyle
                    });
                }
            }
        } else if (position === 'angular') {
            for (i = 0, ln = limits.length; i < ln; i++) {
                limit = chain(limits[i]);
                !limit.line && (limit.line = {});
                value = Ext.isString(limit.value) ? axis.getCoordFor(limit.value) : limit.value;
                value = value / (attr.max + 1) * Math.PI * 2 + attr.baseRotation;
                limit.line.translationX = attr.centerX;
                limit.line.translationY = attr.centerY;
                limit.line.rotationRads = value;
                limit.line.rotationCenterX = 0;
                limit.line.rotationCenterY = 0;
                limit.line.scalingX = attr.length;
                limit.line.scalingY = attr.length;
                limit.line.strokeStyle = limit.line.strokeStyle || attr.strokeStyle;
                me.putMarker('radial-limit-lines', limit.line, i, true);
                if (limit.line.title) {
                    titles.createInstance(limit.line.title);
                    titleBBox = titles.getBBoxFor(titles.position - 1);
                    titleFlip = ((value > -0.5 * Math.PI && value < 0.5 * Math.PI) || (value > 1.5 * Math.PI && value < 2 * Math.PI)) ? 1 : -1;
                    titles.setAttributesFor(titles.position - 1, {
                        x: attr.centerX + 0.5 * attr.length * Math.cos(value) + titleFlip * titleBBox.height / 2 * Math.sin(value),
                        y: attr.centerY + 0.5 * attr.length * Math.sin(value) - titleFlip * titleBBox.height / 2 * Math.cos(value),
                        rotationRads: titleFlip === 1 ? value : value - Math.PI,
                        fillStyle: limit.line.title.fillStyle || limit.line.strokeStyle
                    });
                }
            }
        } else if (position === 'gauge') {

        }
    },

    doThicknessChanged: function () {
        var axis = this.getAxis();
        if (axis) {
            axis.onThicknessChanged();
        }
    },

    render: function (surface, ctx, clipRect) {
        var me = this,
            layout = me.getLayoutContext();
        if (layout) {
            if (false === me.renderLabels(surface, ctx, layout, clipRect)) {
                return false;
            }
            ctx.beginPath();
            me.renderTicks(surface, ctx, layout, clipRect);
            me.renderAxisLine(surface, ctx, layout, clipRect);
            me.renderGridLines(surface, ctx, layout, clipRect);
            me.renderLimits();
            ctx.stroke();
        }
    }
});

/*
    Moved TODO comments to bottom
    TODO(touch-2.2): Split different types of axis into different sprite classes.
*/

/**
 * @abstract
 * @class Ext.chart.axis.segmenter.Segmenter
 * 
 * Interface for a segmenter in an Axis. A segmenter defines the operations you can do to a specific
 * data type.
 * 
 * See {@link Ext.chart.axis.Axis}.
 * 
 */
Ext.define('Ext.chart.axis.segmenter.Segmenter', {

    config: {
        /**
         * @cfg {Ext.chart.axis.Axis} axis The axis that the Segmenter is bound.
         */
        axis: null
    },

    constructor: function (config) {
        this.initConfig(config);
    },

    /**
     * This method formats the value.
     * 
     * @param {*} value The value to format.
     * @param {Object} context Axis layout context.
     * @return {String}
     */
    renderer: function (value, context) {
        return String(value);
    },
    
    /**
     * Convert from any data into the target type.
     * @param {*} value The value to convert from
     * @return {*} The converted value. 
     */
    from: function (value) {
        return value;
    },

    /**
     * Returns the difference between the min and max value based on the given unit scale.
     * 
     * @param {*} min The smaller value.
     * @param {*} max The larger value.
     * @param {*} unit The unit scale. Unit can be any type.
     * @return {Number} The number of `unit`s between min and max. It is the minimum n that min + n * unit >= max.
     */
    diff: Ext.emptyFn,

    /**
     * Align value with step of units.
     * For example, for the date segmenter, if the unit is "Month" and step is 3, the value will be aligned by
     * seasons.
     * 
     * @param {*} value The value to be aligned.
     * @param {Number} step The step of units.
     * @param {*} unit The unit.
     * @return {*} Aligned value.
     */
    align: Ext.emptyFn,

    /**
     * Add `step` `unit`s to the value. 
     * @param {*} value  The value to be added.
     * @param {Number} step The step of units. Negative value are allowed.
     * @param {*} unit The unit.
     */
    add: Ext.emptyFn,

    /**
     * Given a start point and estimated step size of a range, determine the preferred step size.
     * 
     * @param {*} start The start point of range.
     * @param {*} estStepSize The estimated step size.
     * @return {Object} Return the step size by an object of step x unit.
     * @return {Number} return.step The step count of units.
     * @return {Number|Object} return.unit The unit.
     */
    preferredStep: Ext.emptyFn
});

/**
 * @class Ext.chart.axis.segmenter.Names
 * @extends Ext.chart.axis.segmenter.Segmenter
 * 
 * Names data type. Names will be calculated as their indices in the methods in this class.
 * The `preferredStep` always return `{ unit: 1, step: 1 }` to indicate "show every item".
 * 
 */
Ext.define('Ext.chart.axis.segmenter.Names', {
    extend: 'Ext.chart.axis.segmenter.Segmenter',
    alias: 'segmenter.names',

    renderer: function (value, context) {
        return value;
    },

    diff: function (min, max, unit) {
        return Math.floor(max - min);
    },

    align: function (value, step, unit) {
        return Math.floor(value);
    },


    add: function (value, step, unit) {
        return value + step;
    },

    preferredStep: function (min, estStepSize, minIdx, data) {
        return {
            unit: 1,
            step: 1
        };
    }
});

/**
 * @class Ext.chart.axis.segmenter.Numeric
 * @extends Ext.chart.axis.segmenter.Segmenter
 * 
 * Numeric data type.
 */
Ext.define('Ext.chart.axis.segmenter.Numeric', {
    extend: 'Ext.chart.axis.segmenter.Segmenter',
    alias: 'segmenter.numeric',
    isNumeric: true,

    renderer: function (value, context) {
        return value.toFixed(Math.max(0, context.majorTicks.unit.fixes));
    },

    diff: function (min, max, unit) {
        return Math.floor((max - min) / unit.scale);
    },

    align: function (value, step, unit) {
        return Math.floor(value / (unit.scale * step)) * unit.scale * step;
    },

    add: function (value, step, unit) {
        return value + step * unit.scale;
    },

    preferredStep: function (min, estStepSize) {
        var logs = Math.floor(Math.log(estStepSize) * Math.LOG10E), // common logarithm of estStepSize
            scale = Math.pow(10, logs);
        estStepSize /= scale;
        if (estStepSize < 2) {
            estStepSize = 2;
        } else if (estStepSize < 5) {
            estStepSize = 5;
        } else if (estStepSize < 10) {
            estStepSize = 10;
            logs++;
        }
        return {
            unit: {
                // when estStepSize < 1, rounded down log10(estStepSize) is equal to -number_of_leading_zeros in estStepSize
                fixes: -logs, // number of fractional digits
                scale: scale
            },
            step: estStepSize
        };
    },

    /**
     * Wraps the provided estimated step size of a range without altering it into a step size object.
     *
     * @param {*} min The start point of range.
     * @param {*} estStepSize The estimated step size.
     * @return {Object} Return the step size by an object of step x unit.
     * @return {Number} return.step The step count of units.
     * @return {Object} return.unit The unit.
     */

    exactStep: function (min, estStepSize) {
        var logs = Math.floor(Math.log(estStepSize) * Math.LOG10E),
            scale = Math.pow(10, logs);
        return {
            unit: {
                // add one decimal point if estStepSize is not a multiple of scale
                fixes: -logs + (estStepSize % scale === 0 ? 0 : 1),
                scale: 1
            },
            step: estStepSize
        }
    },

    adjustByMajorUnit: function (step, scale, range) {
        var min = range[0],
            max = range[1],
            increment = step * scale,
            remainder = min % increment;
        if (remainder !== 0) {
            range[0] = min - remainder + (min < 0 ? -increment : 0);
        }
        remainder = max % increment;
        if (remainder !== 0) {
            range[1] = max - remainder + (max > 0 ? increment : 0);
        }
    }
});

/**
 * @class Ext.chart.axis.segmenter.Time
 * @extends Ext.chart.axis.segmenter.Segmenter
 * 
 * Time data type.
 */
Ext.define('Ext.chart.axis.segmenter.Time', {
    extend: 'Ext.chart.axis.segmenter.Segmenter',
    alias: 'segmenter.time',

    config: {
        /**
         * @cfg {Object} step
         * If specified, the will override the result of {@link #preferredStep}.
         */
        step: null
    },

    renderer: function (value, context) {
        var ExtDate = Ext.Date;
        switch (context.majorTicks.unit) {
            case 'y':
                return ExtDate.format(value, 'Y');
            case 'mo':
                return ExtDate.format(value, 'Y-m');
            case 'd':
                return ExtDate.format(value, 'Y-m-d');
        }
        return ExtDate.format(value, 'Y-m-d\nH:i:s');
    },

    from: function (value) {
        return new Date(value);
    },

    diff: function (min, max, unit) {
        var ExtDate = Ext.Date;
        if (isFinite(min)) {
            min = new Date(min);
        }
        if (isFinite(max)) {
            max = new Date(max);
        }
        return ExtDate.diff(min, max, unit);
    },

    align: function (date, step, unit) {
        if (unit === 'd' && step >= 7) {
            date = Ext.Date.align(date, 'd', step);
            date.setDate(date.getDate() - date.getDay() + 1);
            return date;
        } else {
            return Ext.Date.align(date, unit, step);
        }
    },

    add: function (value, step, unit) {
        return Ext.Date.add(new Date(value), unit, step);
    },

    preferredStep: function (min, estStepSize) {
        if (this.getStep()) {
            return this.getStep();
        }
        var from = new Date(+min),
            to = new Date(+min + Math.ceil(estStepSize)),
            ExtDate = Ext.Date,
            units = [
                [ExtDate.YEAR, 1, 2, 5, 10, 20, 50, 100, 200, 500],
                [ExtDate.MONTH, 1, 3, 6],
                [ExtDate.DAY, 1, 7, 14],
                [ExtDate.HOUR, 1, 6, 12],
                [ExtDate.MINUTE, 1, 5, 15, 30],
                [ExtDate.SECOND, 1, 5, 15, 30],
                [ExtDate.MILLI, 1, 2, 5, 10, 20, 50, 100, 200, 500]
            ],
            result;

        for (var i = 0; i < units.length; i++) {
            var unit = units[i][0],
                diff = this.diff(from, to, unit);
            if (diff > 0) {
                for (var j = 1; j < units[i].length; j++) {
                    if (diff <= units[i][j]) {
                        result = {
                            unit: unit,
                            step: units[i][j]
                        };
                        break;
                    }
                }
                if (!result) {
                    i--;
                    result = {
                        unit: units[i][0],
                        step: 1
                    };
                }
                break;
            }
        }
        if (!result) {
            result = {unit: ExtDate.DAY, step: 1}; // Default step is one Day.
        }
        return result;
    }
});

/**
 * @abstract
 * @class Ext.chart.axis.layout.Layout
 *
 * Interface used by Axis to process its data into a meaningful layout.
 */
Ext.define('Ext.chart.axis.layout.Layout', {
    mixins: {
        observable: 'Ext.mixin.Observable'
    },
    config: {
        /**
         * @cfg {Ext.chart.axis.Axis} axis The axis that the Layout is bound.
         */
        axis: null
    },

    constructor: function (config) {
        this.mixins.observable.constructor.call(this, config);
    },

    /**
     * Processes the data of the series bound to the axis.
     * @param {Ext.chart.series.Series} series The bound series.
     */
    processData: function (series) {
        var me = this,
            axis = me.getAxis(),
            direction = axis.getDirection(),
            boundSeries = axis.boundSeries,
            i, ln;
        if (series) {
            series['coordinate' + direction]();
        } else {
            for (i = 0, ln = boundSeries.length; i < ln; i++) {
                boundSeries[i]['coordinate' + direction]()
            }
        }
    },

    /**
     * Calculates the position of major ticks for the axis.
     * @param {Object} context
     */
    calculateMajorTicks: function (context) {
        var me = this,
            attr = context.attr,
            range = attr.max - attr.min,
            zoom = range / Math.max(1, attr.length) * (attr.visibleMax - attr.visibleMin),
            viewMin = attr.min + range * attr.visibleMin,
            viewMax = attr.min + range * attr.visibleMax,
            estStepSize = attr.estStepSize * zoom,
            out = me.snapEnds(context, attr.min, attr.max, estStepSize);
        if (out) {
            me.trimByRange(context, out, viewMin, viewMax);
            context.majorTicks = out;
        }
    },

    /**
     * Calculates the position of sub ticks for the axis.
     * @param {Object} context
     */
    calculateMinorTicks: function (context) {
        if (this.snapMinorEnds) {
            context.minorTicks = this.snapMinorEnds(context);
        }
    },

    /**
     * Calculates the position of tick marks for the axis.
     * @param {Object} context
     * @return {*}
     */
    calculateLayout: function (context) {
        var me = this,
            attr = context.attr;

        if (attr.length === 0) {
            return null;
        }

        if (attr.majorTicks) {
            me.calculateMajorTicks(context);
            if (attr.minorTicks) {
                me.calculateMinorTicks(context);
            }
        }
    },

    /**
     * Snaps the data bound to the axis to meaningful tick marks.
     * @param {Object} context
     * @param {Number} min
     * @param {Number} max
     * @param {Number} estStepSize
     */
    snapEnds: Ext.emptyFn,

    /**
     * Trims the layout of the axis by the defined minimum and maximum.
     * @param {Object} context
     * @param {Object} out
     * @param {Number} trimMin
     * @param {Number} trimMax
     */
    trimByRange: function (context, out, trimMin, trimMax) {
        var segmenter = context.segmenter,
            unit = out.unit,
            beginIdx = segmenter.diff(out.from, trimMin, unit),
            endIdx = segmenter.diff(out.from, trimMax, unit),
            begin = Math.max(0, Math.ceil(beginIdx / out.step)),
            end = Math.min(out.steps, Math.floor(endIdx / out.step));

        if (end < out.steps) {
            out.to = segmenter.add(out.from, end * out.step, unit);
        }

        if (out.max > trimMax) {
            out.max = out.to;
        }

        if (out.from < trimMin) {
            out.from = segmenter.add(out.from, begin * out.step, unit);
            while (out.from < trimMin) {
                begin++;
                out.from = segmenter.add(out.from, out.step, unit);
            }
        }

        if (out.min < trimMin) {
            out.min = out.from;
        }

        out.steps = end - begin;
    }
});

/**
 * @class Ext.chart.axis.layout.Discrete
 * @extends Ext.chart.axis.layout.Layout
 *
 * Simple processor for data that cannot be interpolated.
 */
Ext.define('Ext.chart.axis.layout.Discrete', {
    extend: 'Ext.chart.axis.layout.Layout',
    alias: 'axisLayout.discrete',

    processData: function () {
        var me = this,
            axis = me.getAxis(),
            seriesList = axis.boundSeries,
            direction = axis.getDirection(),
            i, ln, series;
        this.labels = [];
        this.labelMap = {};
        for (i = 0, ln = seriesList.length; i < ln; i++) {
            series = seriesList[i];
            if (series['get' + direction + 'Axis']() === axis) {
                series['coordinate' + direction]();
            }
        }
        // About the labels on Category axes (aka. axes with a Discrete layout)...
        //
        // When the data set from the store changes, series.processData() is called, which does its thing
        // at the series level and then calls series.updateLabelData() to update the labels in the sprites
        // that belong to the series. At the same time, series.processData() calls axis.processData(), which
        // also does its thing but at the axis level, and also needs to update the labels for the sprite(s)
        // that belong to the axis. This is not that simple, however. So how are the axis labels rendered?
        // First, axis.sprite.Axis.render() calls renderLabels() which obtains the majorTicks from the 
        // axis.layout and iterate() through them. The majorTicks are an object returned by snapEnds() below
        // which provides a getLabel() function that returns the label from the axis.layoutContext.data array.
        // So now the question is: how are the labels transferred from the axis.layout to the axis.layoutContext?
        // The easy response is: it's in calculateLayout() below. The issue is to call calculateLayout() because
        // it takes in an axis.layoutContext that can only be created in axis.sprite.Axis.doLayout(), which is 
        // a private "updater" function that is called by all the sprite's "dirtyTriggers". Of course, we don't 
        // want to call doLayout() directly from here, so instead we update the sprite's data attribute, which 
        // sets the dirtyTrigger which calls doLayout() which calls calculateLayout() etc...
        // Note that the sprite's data attribute could be set to any value and it would still result in the  
        // dirtyTrigger we need. For consistency, however, it is set to the labels.
        axis.getSprites()[0].setAttributes({data: this.labels});
        this.fireEvent('datachange', this.labels);
    },

    // @inheritdoc
    calculateLayout: function (context) {
        context.data = this.labels;
        this.callParent([context]);
    },

    //@inheritdoc
    calculateMajorTicks: function (context) {
        var me = this,
            attr = context.attr,
            data = context.data,
            range = attr.max - attr.min,
            zoom = range / Math.max(1, attr.length) * (attr.visibleMax - attr.visibleMin),
            viewMin = attr.min + range * attr.visibleMin,
            viewMax = attr.min + range * attr.visibleMax,
            estStepSize = attr.estStepSize * zoom;

        var out = me.snapEnds(context, Math.max(0, attr.min), Math.min(attr.max, data.length - 1), estStepSize);
        if (out) {
            me.trimByRange(context, out, viewMin, viewMax);
            context.majorTicks = out;
        }
    },

    // @inheritdoc
    snapEnds: function (context, min, max, estStepSize) {
        estStepSize = Math.ceil(estStepSize);
        var steps = Math.floor((max - min) / estStepSize),
            data = context.data;
        return {
            min: min,
            max: max,
            from: min,
            to: steps * estStepSize + min,
            step: estStepSize,
            steps: steps,
            unit: 1,
            getLabel: function (current) {
                return data[this.from + this.step * current];
            },
            get: function (current) {
                return this.from + this.step * current;
            }
        };
    },

    // @inheritdoc
    trimByRange: function (context, out, trimMin, trimMax) {
        var unit = out.unit,
            beginIdx = Math.ceil((trimMin - out.from) / unit) * unit,
            endIdx = Math.floor((trimMax - out.from) / unit) * unit,
            begin = Math.max(0, Math.ceil(beginIdx / out.step)),
            end = Math.min(out.steps, Math.floor(endIdx / out.step));

        if (end < out.steps) {
            out.to = end;
        }

        if (out.max > trimMax) {
            out.max = out.to;
        }

        if (out.from < trimMin && out.step > 0) {
            out.from = out.from + begin * out.step * unit;
            while (out.from < trimMin) {
                begin++;
                out.from += out.step * unit;
            }
        }

        if (out.min < trimMin) {
            out.min = out.from;
        }

        out.steps = end - begin;
    },

    getCoordFor: function (value, field, idx, items) {
        this.labels.push(value);
        return this.labels.length - 1;
    }
});

/**
 * @class Ext.chart.axis.layout.CombineDuplicate
 * @extends Ext.chart.axis.layout.Discrete
 * 
 * Discrete processor that combines duplicate data points.
 */
Ext.define('Ext.chart.axis.layout.CombineDuplicate', {
    extend: 'Ext.chart.axis.layout.Discrete',
    alias: 'axisLayout.combineDuplicate',

    getCoordFor: function (value, field, idx, items) {
        if (!(value in this.labelMap)) {
            var result = this.labelMap[value] = this.labels.length;
            this.labels.push(value);
            return result;
        }
        return this.labelMap[value];
    }

});

/**
 * @class Ext.chart.axis.layout.Continuous
 * @extends Ext.chart.axis.layout.Layout
 * 
 * Processor for axis data that can be interpolated.
 */
Ext.define('Ext.chart.axis.layout.Continuous', {
    extend: 'Ext.chart.axis.layout.Layout',
    alias: 'axisLayout.continuous',
    config: {
        adjustMinimumByMajorUnit: false,
        adjustMaximumByMajorUnit: false
    },
    
    getCoordFor: function (value, field, idx, items) {
        return +value;
    },

    //@inheritdoc
    snapEnds: function (context, min, max, estStepSize) {
        var segmenter = context.segmenter,
            axis = this.getAxis(),
            majorTickSteps = axis.getMajorTickSteps(),
            // if specific number of steps requested and the segmenter can do such segmentation
            out = majorTickSteps && segmenter.exactStep ?
                segmenter.exactStep(min, (max - min) / majorTickSteps) :
                segmenter.preferredStep(min, estStepSize),
            unit = out.unit,
            step = out.step,
            from = segmenter.align(min, step, unit),
            steps = segmenter.diff(min, max, unit) + 1;
        return {
            min: segmenter.from(min),
            max: segmenter.from(max),
            from: from,
            to: segmenter.add(from, steps * step, unit),
            step: step,
            steps: steps,
            unit: unit,
            get: function (current) {
                return segmenter.add(this.from, this.step * current, unit);
            }
        };
    },

    snapMinorEnds: function (context) {
        var majorTicks = context.majorTicks,
            minorTickSteps = this.getAxis().getMinorTickSteps(),
            segmenter = context.segmenter,
            min = majorTicks.min,
            max = majorTicks.max,
            from = majorTicks.from,
            unit = majorTicks.unit,
            step = majorTicks.step / minorTickSteps,
            scaledStep = step * unit.scale,
            fromMargin = from - min,
            offset = Math.floor(fromMargin / scaledStep),
            extraSteps = offset + Math.floor((max - majorTicks.to) / scaledStep) + 1,
            steps = majorTicks.steps * minorTickSteps + extraSteps;
        return {
            min: min,
            max: max,
            from: min + fromMargin % scaledStep,
            to: segmenter.add(from, steps * step, unit),
            step: step,
            steps: steps,
            unit: unit,
            get: function (current) {
                return (current % minorTickSteps + offset + 1 !== 0) ? // don't render minor tick in major tick position
                    segmenter.add(this.from, this.step * current, unit) :
                    null;
            }
        }
    }
});

/**
 * @class Ext.chart.axis.Axis
 *
 * Defines axis for charts.
 *
 * Using the current model, the type of axis can be easily extended. By default, Sencha Charts provide three different
 * types of axis:
 *
 *  * **numeric** - the data attached to this axis is numeric and continuous.
 *  * **time** - the data attached to this axis is (or gets converted into) a date/time value; it is continuous.
 *  * **category** - the data attached to this axis belongs to a finite set. The data points are evenly placed along the axis.
 *
 * The behavior of an axis can be easily changed by setting different types of axis layout and axis segmenter to the axis.
 *
 * Axis layout defines how the data points are placed. Using continuous layout, the data points will be distributed by
 * the numeric value. Using discrete layout the data points will be spaced evenly. Furthermore, if you want to combine
 * the data points with the duplicate values in a discrete layout, you should use combineDuplicate layout.
 *
 * Segmenter defines the way to segment data range. For example, if you have a Date-type data range from Jan 1, 1997 to
 * Jan 1, 2017, the segmenter will segement the data range into years, months or days based on the current zooming
 * level.
 *
 * It is possible to write custom axis layouts and segmenters to extends this behavior by simply implementing interfaces
 * {@link Ext.chart.axis.layout.Layout} and {@link Ext.chart.axis.segmenter.Segmenter}.
 *
 * Here's an example for the axes part of a chart definition:
 * An example of axis for a series (in this case for an area chart that has multiple layers of yFields) could be:
 *
 *     axes: [{
 *         type: 'numeric',
 *         position: 'left',
 *         title: 'Number of Hits',
 *         grid: {
 *             odd: {
 *                 opacity: 1,
 *                 fill: '#ddd',
 *                 stroke: '#bbb',
 *                 lineWidth: 1
 *             }
 *         },
 *         minimum: 0
 *     }, {
 *         type: 'category',
 *         position: 'bottom',
 *         title: 'Month of the Year',
 *         grid: true,
 *         label: {
 *             rotate: {
 *                 degrees: 315
 *             }
 *         }
 *     }]
 *
 * In this case we use a `numeric` axis for displaying the values of the Area series and a `category` axis for displaying the names of
 * the store elements. The numeric axis is placed on the left of the screen, while the category axis is placed at the bottom of the chart.
 * Both the category and numeric axes have `grid` set, which means that horizontal and vertical lines will cover the chart background. In the
 * category axis the labels will be rotated so they can fit the space better.
 */
Ext.define('Ext.chart.axis.Axis', {
    xtype: 'axis',

    mixins: {
        observable: 'Ext.mixin.Observable'
    },

    requires: [
        'Ext.chart.axis.sprite.Axis',
        'Ext.chart.axis.segmenter.*',
        'Ext.chart.axis.layout.*'
    ],

    config: {
        /**
         * @cfg {String} position
         * Where to set the axis. Available options are `left`, `bottom`, `right`, `top`, `radial` and `angular`.
         */
        position: 'bottom',

        /**
         * @cfg {Array} fields
         * An array containing the names of the record fields which should be mapped along the axis.
         * This is optional if the binding between series and fields is clear.
         */
        fields: [],

        /**
         * @cfg {Object} label
         *
         * The label configuration object for the Axis. This object may include style attributes
         * like `spacing`, `padding`, `font` that receives a string or number and
         * returns a new string with the modified values.
         *
         * For more supported values, see the configurations for {@link Ext.chart.label.Label}.
         */
        label: undefined,

        /**
         * @cfg {Object} grid
         * The grid configuration object for the Axis style. Can contain `stroke` or `fill` attributes.
         * Also may contain an `odd` or `even` property in which you only style things on odd or even rows.
         * For example:
         *
         *
         *     grid {
         *         odd: {
         *             stroke: '#555'
         *         },
         *         even: {
         *             stroke: '#ccc'
         *         }
         *     }
         */
        grid: false,

        /**
         * @cfg {Array|Object} limits
         * The limit lines configuration for the axis.
         * For example:
         *
         *     limits: [{
         *         value: 50,
         *         line: {
         *             strokeStyle: 'red',
         *             lineDash: [6, 3],
         *             title: {
         *                 text: 'Monthly minimum',
         *                 fontSize: 14
         *             }
         *         }
         *     }]
         */
        limits: null,

        /**
         * @cfg {Function} renderer Allows direct customisation of rendered axis sprites.
         * @param {String} label The label.
         * @param {Object|Ext.chart.axis.layout.Layout} layout The layout configuration used by the axis.
         * @param {String} lastLabel The last label.
         * @return {String} The label to display.
         */
        renderer: null,

        /**
         * @protected
         * @cfg {Ext.chart.AbstractChart} chart The Chart that the Axis is bound.
         */
        chart: null,

        /**
         * @cfg {Object} style
         * The style for the axis line and ticks.
         * Refer to the {@link Ext.chart.axis.sprite.Axis}
         */
        style: null,

        /**
         * @cfg {Number} margin
         * The margin of the axis. Used to control the spacing between axes in charts with multiple axes.
         * Unlike CSS where the margin is added on all 4 sides of an element, the `margin` is the total space
         * that is added horizontally for a vertical axis, vertically for a horizontal axis,
         * and radially for an angular axis.
         */
        margin: 0,

        /**
         * @cfg {Number} [titleMargin=4]
         * The margin around the axis title. Unlike CSS where the margin is added on all 4
         * sides of an element, the `titleMargin` is the total space that is added horizontally
         * for a vertical title and vertically for an horizontal title, with half the `titleMargin`
         * being added on either side.
         */
        titleMargin: 4,

        /**
         * @cfg {Object} background
         * The background config for the axis surface.
         */
        background: null,

        /**
         * @cfg {Number} minimum
         * The minimum value drawn by the axis. If not set explicitly, the axis
         * minimum will be calculated automatically.
         */
        minimum: NaN,

        /**
         * @cfg {Number} maximum
         * The maximum value drawn by the axis. If not set explicitly, the axis
         * maximum will be calculated automatically.
         */
        maximum: NaN,

        /**
         * @cfg {Boolean} reconcileRange
         * If 'true' the range of the axis will be a union of ranges
         * of all the axes with the same direction. Defaults to 'false'.
         */
        reconcileRange: false,

        /**
         * @cfg {Number} minZoom
         * The minimum zooming level for axis.
         */
        minZoom: 1,

        /**
         * @cfg {Number} maxZoom
         * The maximum zooming level for axis
         */
        maxZoom: 10000,

        /**
         * @cfg {Object|Ext.chart.axis.layout.Layout} layout
         * The axis layout config. See {@link Ext.chart.axis.layout.Layout}
         */
        layout: 'continuous',

        /**
         * @cfg {Object|Ext.chart.axis.segmenter.Segmenter} segmenter
         * The segmenter config. See {@link Ext.chart.axis.segmenter.Segmenter}
         */
        segmenter: 'numeric',

        /**
         * @cfg {Boolean} hidden
         * Indicate whether to hide the axis.
         * If the axis is hidden, one of the axis line, ticks, labels or the title will be shown and
         * no margin will be taken.
         * The coordination mechanism works fine no matter if the axis is hidden.
         */
        hidden: false,

        /**
         * @cfg {Number} [majorTickSteps=0]
         * Forces the number of major ticks to the specified value.
         */
        majorTickSteps: 0,

        /**
         * @cfg {Number} [minorTickSteps=0]
         * The number of small ticks between two major ticks.
         */
        minorTickSteps: 0,

        /**
         * @cfg {Boolean} adjustByMajorUnit
         * Whether to make the auto-calculated minimum and maximum of the axis
         * a multiple of the interval between the major ticks of the axis.
         * If {@link #majorTickSteps}, {@link #minimum} or {@link #maximum}
         * configs have been set, this config will be ignored.
         * Defaults to 'true'.
         */
        adjustByMajorUnit: true,

        /**
         * @cfg {String|Object} title
         * The title for the Axis.
         * If given a String, the 'text' attribute of the title sprite will be set,
         * otherwise the style will be set.
         */
        title: null,

        /**
         * @cfg {Number} increment
         * Given a minimum and maximum bound for the series to be rendered (that can be obtained
         * automatically or by manually setting `minimum` and `maximum`) tick marks will be added
         * on each `increment` from the minimum value to the maximum one.
         */
        increment: 0.5,

        /**
         * @private
         * @cfg {Number} length
         * Length of the axis position. Equals to the size of inner rect on the docking side of this axis.
         * WARNING: Meant to be set automatically by chart. Do not set it manually.
         */
        length: 0,

        /**
         * @private
         * @cfg {Array} center
         * Center of the polar axis.
         * WARNING: Meant to be set automatically by chart. Do not set it manually.
         */
        center: null,

        /**
         * @private
         * @cfg {Number} radius
         * Radius of the polar axis.
         * WARNING: Meant to be set automatically by chart. Do not set it manually.
         */
        radius: null,

        /**
         * @private
         */
        totalAngle: Math.PI,

        /**
         * @private
         * @cfg {Number} rotation
         * Rotation of the polar axis.
         * WARNING: Meant to be set automatically by chart. Do not set it manually.
         */
        rotation: null,

        /**
         * @cfg {Boolean} [labelInSpan]
         * Draws the labels in the middle of the spans.
         */
        labelInSpan: null,

        /**
         * @cfg {Array} visibleRange
         * Specify the proportion of the axis to be rendered. The series bound to
         * this axis will be synchronized and transformed.
         */
        visibleRange: [0, 1],

        /**
         * @cfg {Boolean} needHighPrecision
         * Indicates that the axis needs high precision surface implementation.
         * See {@link Ext.draw.engine.Canvas#highPrecision}
         */
        needHighPrecision: false,

        /**
         * @cfg {Ext.chart.axis.Axis|String|Number} linkedTo
         * Axis (itself, its ID or index) that this axis is linked to.
         * When an axis is linked to a master axis, it will use the same data as the master axis.
         * It can be used to show additional info, or to ease reading the chart by duplicating the scales.
         */
        linkedTo: null,

        /**
         * @cfg {Number|Object}
         * If `floating` is a number, then it's a percentage displacement of the axis from its initial {@link #position}
         * in the direction opposite to the axis' direction. For instance, '{position:"left", floating:75}' displays a vertical 
         * axis at 3/4 of the chart, starting from the left. It is equivalent to '{position:"right", floating:25}'.
         * If `floating` is an object, then `floating.value` is the position of this axis along another axis,
         * defined by `floating.alongAxis`, where `alongAxis` is an ID, an {@link Ext.chart.AbstractChart#axes} config index,
         * or the other axis itself. `alongAxis` must have an opposite {@link Ext.chart.axis.Axis#getAlignment alignment}.
         * For example:
         *
         *
         *      axes: [
         *          {
         *              title: 'Average Temperature (F)',
         *              type: 'numeric',
         *              position: 'left',
         *              id: 'temperature-vertical-axis',
         *              minimum: -30,
         *              maximum: 130
         *          },
         *          {
         *              title: 'Month (2013)',
         *              type: 'category',
         *              position: 'bottom',
         *              floating: {
         *                  value: 32,
         *                  alongAxis: 'temperature-vertical-axis'
         *              }
         *          }
         *      ]
         */
        floating: null
    },

    titleOffset: 0,

    animating: 0,

    prevMin: 0,

    prevMax: 1,

    boundSeries: [],

    sprites: null,

    surface: null,

    /**
     * @private
     * @property {Array} The full data range of the axis. Should not be set directly, clear it to `null` and use
     * `getRange` to update.
     */
    range: null,

    xValues: [],

    yValues: [],

    masterAxis: null,

    applyRotation: function (rotation) {
        var twoPie = Math.PI * 2;
        return (rotation % twoPie + Math.PI) % twoPie - Math.PI;
    },

    updateRotation: function (rotation) {
        var sprites = this.getSprites(),
            position = this.getPosition();
        if (!this.getHidden() && position === 'angular' && sprites[0]) {
            sprites[0].setAttributes({
                baseRotation: rotation
            });
        }
    },

    applyTitle: function (title, oldTitle) {
        var surface;

        if (Ext.isString(title)) {
            title = { text: title };
        }

        if (!oldTitle) {
            oldTitle = Ext.create('sprite.text', title);
            if ((surface = this.getSurface())) {
                surface.add(oldTitle);
            }
        } else {
            oldTitle.setAttributes(title);
        }
        return oldTitle;
    },

    applyFloating: function (floating, oldFloating) {
        if (floating === null) {
            floating = {
                value: null,
                alongAxis: null
            };
        } else if (Ext.isNumber(floating)) {
            floating = {
                value: floating,
                alongAxis: null
            };
        }
        if (Ext.isObject(floating)) {
            if (oldFloating && oldFloating.alongAxis) {
                delete this.getChart().getAxis(oldFloating.alongAxis).floatingAxes[this.getId()];
            }
            return floating;
        }
        return oldFloating;
    },

    constructor: function (config) {
        var me = this,
            id;

        me.sprites = [];
        me.labels = [];
        // Maps IDs of the axes that float along this axis to their floating values.
        me.floatingAxes = {};

        if (config.position === 'angular') {
            config.style = config.style || {};
            // TODO: try to get rid of the estStepSize
            config.style.estStepSize = 1;
        }
        if ('id' in config) {
            id = config.id;
        } else if ('id' in me.config) {
            id = me.config.id;
        } else {
            id = me.getId();
        }
        me.id = id;
        me.setId(id);
        me.mixins.observable.constructor.apply(me, arguments);
        Ext.ComponentManager.register(me);
        this.initConfig(config);
    },

    /**
     * @private
     * @return {String}
     */
    getAlignment: function () {
        switch (this.getPosition()) {
            case 'left':
            case 'right':
                return 'vertical';
            case 'top':
            case 'bottom':
                return 'horizontal';
            case 'radial':
                return 'radial';
            case 'angular':
                return 'angular';
        }
    },

    /**
     * @private
     * @return {String}
     */
    getGridAlignment: function () {
        switch (this.getPosition()) {
            case 'left':
            case 'right':
                return 'horizontal';
            case 'top':
            case 'bottom':
                return 'vertical';
            case 'radial':
                return 'circular';
            case 'angular':
                return 'radial';
        }
    },

    /**
     * @private
     * Get the surface for drawing the series sprites
     */
    getSurface: function () {
        var me = this,
            chart = me.getChart();
        if (chart && !me.surface) {
            var surface = me.surface = chart.getSurface(me.getId(), 'axis'),
                gridSurface = me.gridSurface = chart.getSurface('main'),
                axisSprite = me.getSprites()[0],
                gridAlignment = me.getGridAlignment();

            gridSurface.waitFor(surface);
            me.getGrid();

            if (me.getLimits()) {
                me.limits = {
                    surface: chart.getSurface('overlay'),
                    lines: new Ext.chart.Markers(),
                    titles: new Ext.draw.sprite.Instancing()
                };
                me.limits.lines.setTemplate({xclass: 'grid.' + gridAlignment});
                me.limits.lines.getTemplate().setAttributes({strokeStyle: 'black'});
                me.limits.surface.add(me.limits.lines);
                axisSprite.bindMarker(gridAlignment + '-limit-lines', me.limits.lines);

                me.limitTitleTpl = new Ext.draw.sprite.Text();
                me.limits.titles.setTemplate(me.limitTitleTpl);
                me.limits.surface.add(me.limits.titles);

                chart.on('redraw', me.renderLimits, me);
            }
        }
        return me.surface;
    },

    applyGrid: function (grid) {
        // Returning an empty object here if grid was set to 'true' so that
        // config merging in the theme works properly.
        if (grid === true) {
            return {};
        }
        return grid;
    },

    updateGrid: function (grid) {
        var me = this,
            chart = me.getChart();

        if (!chart) {
            me.on({
                chartattached: Ext.bind(me.updateGrid, me, [grid]),
                single: true
            });
            return;
        }

        var gridSurface = me.gridSurface,
            axisSprite = me.getSprites()[0],
            gridAlignment = me.getGridAlignment(),
            gridSprite;

        if (grid) {
            gridSprite = me.gridSpriteEven;
            if (!gridSprite) {
                gridSprite = me.gridSpriteEven = new Ext.chart.Markers();
                gridSprite.setTemplate({xclass: 'grid.' + gridAlignment});
                gridSurface.add(gridSprite);
                axisSprite.bindMarker(gridAlignment + '-even', gridSprite);
            }
            if (Ext.isObject(grid)) {
                gridSprite.getTemplate().setAttributes(grid);
                if (Ext.isObject(grid.even)) {
                    gridSprite.getTemplate().setAttributes(grid.even);
                }
            }

            gridSprite = me.gridSpriteOdd;
            if (!gridSprite) {
                gridSprite = me.gridSpriteOdd = new Ext.chart.Markers();
                gridSprite.setTemplate({xclass: 'grid.' + gridAlignment});
                gridSurface.add(gridSprite);
                axisSprite.bindMarker(gridAlignment + '-odd', gridSprite);
            }
            if (Ext.isObject(grid)) {
                gridSprite.getTemplate().setAttributes(grid);
                if (Ext.isObject(grid.odd)) {
                    gridSprite.getTemplate().setAttributes(grid.odd);
                }
            }
        }
    },

    /**
     * @private
     */
    renderLimits: function () {
        this.getSprites()[0].renderLimits();
    },

    /**
     *
     * Mapping data value into coordinate.
     *
     * @param {*} value
     * @param {String} field
     * @param {Number} [idx]
     * @param {Ext.util.MixedCollection} [items]
     * @return {Number}
     */
    getCoordFor: function (value, field, idx, items) {
        return this.getLayout().getCoordFor(value, field, idx, items);
    },

    applyPosition: function (pos) {
        return pos.toLowerCase();
    },

    applyLength: function (length, oldLength) {
        return length > 0 ? length : oldLength;
    },

    applyLabel: function (newText, oldText) {
        if (!oldText) {
            oldText = new Ext.draw.sprite.Text({});
        }
        if (this.limitTitleTpl) {
            this.limitTitleTpl.setAttributes(newText);
        }
        oldText.setAttributes(newText);
        return oldText;
    },

    applyLayout: function (layout, oldLayout) {
        // TODO: finish this
        layout = Ext.factory(layout, null, oldLayout, 'axisLayout');
        layout.setAxis(this);
        return layout;
    },

    applySegmenter: function (segmenter, oldSegmenter) {
        // TODO: finish this
        segmenter = Ext.factory(segmenter, null, oldSegmenter, 'segmenter');
        segmenter.setAxis(this);
        return segmenter;
    },

    updateMinimum: function () {
        this.range = null;
    },

    updateMaximum: function () {
        this.range = null;
    },

    hideLabels: function () {
        this.getSprites()[0].setDirty(true);
        this.setLabel({hidden: true});
    },

    showLabels: function () {
        this.getSprites()[0].setDirty(true);
        this.setLabel({hidden: false});
    },

    /**
     * Invokes renderFrame on this axis's surface(s)
     */
    renderFrame: function () {
        this.getSurface().renderFrame();
    },

    updateChart: function (newChart, oldChart) {
        var me = this, surface;
        if (oldChart) {
            oldChart.un('serieschange', me.onSeriesChange, me);
            me.linkAxis();
            me.fireEvent('chartdetached', oldChart, me);
        }
        if (newChart) {
            newChart.on('serieschange', me.onSeriesChange, me);
            me.surface = null;
            surface = me.getSurface();
            me.getLabel().setSurface(surface);
            surface.add(me.getSprites());
            surface.add(me.getTitle());
            me.fireEvent('chartattached', newChart, me);
        }
    },

    applyBackground: function (background) {
        var rect = Ext.ClassManager.getByAlias('sprite.rect');
        return rect.def.normalize(background);
    },

    /**
     * @protected
     * Invoked when data has changed.
     */
    processData: function () {
        this.getLayout().processData();
        this.range = null;
    },

    getDirection: function () {
        return this.getChart().getDirectionForAxis(this.getPosition());
    },

    isSide: function () {
        var position = this.getPosition();
        return position === 'left' || position === 'right';
    },

    applyFields: function (fields) {
        return Ext.Array.from(fields);
    },

    applyVisibleRange: function (visibleRange, oldVisibleRange) {
        // If it is in reversed order swap them
        if (visibleRange[0] > visibleRange[1]) {
            var temp = visibleRange[0];
            visibleRange[0] = visibleRange[1];
            visibleRange[0] = temp;
        }
        if (visibleRange[1] === visibleRange[0]) {
            visibleRange[1] += 1 / this.getMaxZoom();
        }
        if (visibleRange[1] > visibleRange[0] + 1) {
            visibleRange[0] = 0;
            visibleRange[1] = 1;
        } else if (visibleRange[0] < 0) {
            visibleRange[1] -= visibleRange[0];
            visibleRange[0] = 0;
        } else if (visibleRange[1] > 1) {
            visibleRange[0] -= visibleRange[1] - 1;
            visibleRange[1] = 1;
        }

        if (oldVisibleRange && visibleRange[0] === oldVisibleRange[0] && visibleRange[1] === oldVisibleRange[1]) {
            return undefined;
        }

        return visibleRange;
    },

    updateVisibleRange: function (visibleRange) {
        this.fireEvent('transformed', this, visibleRange);
    },

    onSeriesChange: function (chart) {
        var me = this,
            series = chart.getSeries(),
            getAxisMethod = 'get' + me.getDirection() + 'Axis',
            boundSeries = [], i, ln = series.length,
            linkedTo, masterAxis;

        for (i = 0; i < ln; i++) {
            if (this === series[i][getAxisMethod]()) {
                boundSeries.push(series[i]);
            }
        }

        me.boundSeries = boundSeries;

        linkedTo = me.getLinkedTo();
        masterAxis = linkedTo && chart.getAxis(linkedTo);
        if (masterAxis) {
            me.linkAxis(masterAxis);
        } else {
            me.getLayout().processData();
        }
    },

    linkAxis: function (masterAxis) {
        var me = this;
        function link(action, slave, master) {
            master.getLayout()[action]('datachange', 'onDataChange', slave);
            master[action]('rangechange', 'onRangeChange', slave);
        }
        if (me.masterAxis) {
            link('un', me, me.masterAxis);
            me.masterAxis = null;
        }
        if (masterAxis) {
            if (masterAxis.type !== this.type) {
                throw "Linked axes must be of the same type.";
            }
            link('on', me, masterAxis);
            me.onDataChange(masterAxis.getLayout().labels);
            me.onRangeChange(masterAxis.range);
            me.setStyle(Ext.apply({}, me.config.style, masterAxis.config.style));
            me.setTitle(Ext.apply({}, me.config.title, masterAxis.config.title));
            me.setLabel(Ext.apply({}, me.config.label, masterAxis.config.label));
            me.masterAxis = masterAxis;
        }
    },

    onDataChange: function (data) {
        this.getLayout().labels = data;
    },

    onRangeChange: function (range) {
        this.range = range;
    },

    applyRange: function (newRange) {
        if (!newRange) {
            return this.dataRange.slice(0);
        } else {
            return [
                newRange[0] === null ? this.dataRange[0] : newRange[0],
                newRange[1] === null ? this.dataRange[1] : newRange[1]
            ];
        }
    },

    /**
     * Get the range derived from all the bound series.
     * @return {Array}
     */
    getRange: function () {
        var me = this,
            getRangeMethod = 'get' + me.getDirection() + 'Range';

        if (me.range) {
            return me.range;
        }
        if (!isNaN(me.getMinimum()) && !isNaN(me.getMaximum())) {
            return me.range = [me.getMinimum(), me.getMaximum()];
        }
        var min = Infinity,
            max = -Infinity,
            boundSeries = me.boundSeries,
            layout = me.getLayout(),
            segmenter = me.getSegmenter(),
            visibleRange = me.getVisibleRange(),
            context, attr, majorTicks,
            series, i, ln;

        // For each series bound to this axis, ask the series for its min/max values
        // and use them to find the overall min/max.
        for (i = 0, ln = boundSeries.length; i < ln; i++) {
            series = boundSeries[i];
            var minMax = series[getRangeMethod]();

            if (minMax) {
                if (minMax[0] < min) {
                    min = minMax[0];
                }
                if (minMax[1] > max) {
                    max = minMax[1];
                }
            }
        }
        if (!isFinite(max)) {
            max = me.prevMax;
        }

        if (!isFinite(min)) {
            min = me.prevMin;
        }

        if (me.getLabelInSpan() || min === max) {
            max += me.getIncrement();
            min -= me.getIncrement();
        }

        if (!isNaN(me.getMinimum())) {
            min = me.getMinimum();
        } else {
            me.prevMin = min;
        }

        if (!isNaN(me.getMaximum())) {
            max = me.getMaximum();
        } else {
            me.prevMax = max;
        }

        me.range = [min, max];

        // It's important to call 'me.reconcileRange' after me.range
        // has been assigned to avoid circular calls.
        if (me.getReconcileRange()) {
            me.reconcileRange();
        }
        // TODO: Find a better way to do this.
        // TODO: The original design didn't take into account that the range of an axis
        // TODO: will depend not just on the range of the data of the bound series in the
        // TODO: direction of the axis, but also on the range of other axes with the
        // TODO: same direction and on the segmentation of the axis (interval between
        // TODO: major ticks).
        // TODO: While the fist omission was possible to retrofit rather gracefully
        // TODO: by adding the axis.reconcileRange method, the second one is harder to deal with.
        // TODO: The issue is that the resulting axis segmentation, which is a part of
        // TODO: the axis sprite layout has to be known before layout has begun.
        // TODO: Example for the logic below:
        // TODO: If we have a range of data of 0..34.5 the step will be 2 and we
        // TODO: will round up the max to 36 based on that step, but when the range is 0..36,
        // TODO: the step becomes 5, so we have to reconcile the range once again where max
        // TODO: becomes 40.
        if (me.getAdjustByMajorUnit() && segmenter.adjustByMajorUnit && !me.getMajorTickSteps()) {
            attr = Ext.Object.chain(me.getSprites()[0].attr);
            attr.min = me.range[0];
            attr.max = me.range[1];
            attr.visibleMin = visibleRange[0];
            attr.visibleMax = visibleRange[1];
            context = {
                attr: attr,
                segmenter: segmenter
            };
            layout.calculateLayout(context);
            majorTicks = context.majorTicks;
            if (majorTicks) {
                segmenter.adjustByMajorUnit(majorTicks.step, majorTicks.unit.scale, me.range);

                attr.min = me.range[0];
                attr.max = me.range[1];
                delete context.majorTicks;
                layout.calculateLayout(context);
                majorTicks = context.majorTicks;
                segmenter.adjustByMajorUnit(majorTicks.step, majorTicks.unit.scale, me.range);
            } else if (!me.hasClearRangePending) {
                // Axis hasn't been rendered yet.
                me.hasClearRangePending = true;
                me.getChart().on('layout', 'clearRange', me);
            }
        }

        me.fireEvent('rangechange', me.range);
        return me.range;
    },

    // @private
    clearRange: function () {
        delete this.hasClearRangePending;
        this.range = null;
    },

    /**
     * Expands the range of the axis
     * based on the range of other axes with the same direction (if any).
     */
    reconcileRange: function () {
        var me = this,
            axes = me.getChart().getAxes(),
            direction = me.getDirection(),
            i, ln, axis, range;

        if (!axes) {
            return;
        }
        for (i = 0, ln = axes.length; i < ln; i++) {
            axis = axes[i];
            range = axis.getRange();
            if (axis === me || axis.getDirection() !== direction || !range || !axis.getReconcileRange()) {
                continue;
            }
            if (range[0] < me.range[0]) {
                me.range[0] = range[0];
            }
            if (range[1] > me.range[1]) {
                me.range[1] = range[1];
            }
        }
    },

    applyStyle: function (style, oldStyle) {
        var cls = Ext.ClassManager.getByAlias('sprite.' + this.seriesType);
        if (cls && cls.def) {
            style = cls.def.normalize(style);
        }
        oldStyle = Ext.apply(oldStyle || {}, style);
        return oldStyle;
    },

    themeOnlyIfConfigured: {
        grid: true
    },

    updateTheme: function (theme) {
        var me = this,
            axisTheme = theme.getAxis(),
            position = me.getPosition(),
            initialConfig = me.getInitialConfig(),
            defaultConfig = me.defaultConfig,
            configs = me.getConfigurator().configs,
            genericAxisTheme = axisTheme.defaults,
            specificAxisTheme = axisTheme[position],
            themeOnlyIfConfigured = me.themeOnlyIfConfigured,
            key, value, isObjValue, isUnusedConfig, initialValue, cfg;

        axisTheme = Ext.merge({}, genericAxisTheme, specificAxisTheme);
        for (key in axisTheme) {
            value = axisTheme[key];
            cfg = configs[key];
            if (value !== null && value !== undefined && cfg) {
                initialValue = initialConfig[key];
                isObjValue = Ext.isObject(value);
                isUnusedConfig = initialValue === defaultConfig[key];
                if (isObjValue) {
                    if (isUnusedConfig && themeOnlyIfConfigured[key]) {
                        continue;
                    }
                    value = Ext.merge({}, value, initialValue);
                }
                if (isUnusedConfig || isObjValue) {
                    me[cfg.names.set](value);
                }
            }
        }
    },

    updateCenter: function (center) {
        var sprites = this.getSprites(),
            axisSprite = sprites[0],
            centerX = center[0],
            centerY = center[1];
        if (axisSprite) {
            axisSprite.setAttributes({
                centerX: centerX,
                centerY: centerY
            });
        }
        if (this.gridSpriteEven) {
            this.gridSpriteEven.getTemplate().setAttributes({
                translationX: centerX,
                translationY: centerY,
                rotationCenterX: centerX,
                rotationCenterY: centerY
            });
        }
        if (this.gridSpriteOdd) {
            this.gridSpriteOdd.getTemplate().setAttributes({
                translationX: centerX,
                translationY: centerY,
                rotationCenterX: centerX,
                rotationCenterY: centerY
            });
        }
    },

    getSprites: function () {
        if (!this.getChart()) {
            return;
        }
        var me = this,
            range = me.masterAxis ? me.masterAxis.range : me.getRange(),
            position = me.getPosition(),
            chart = me.getChart(),
            animation = chart.getAnimation(),
            baseSprite, style,
            length = me.getLength();

        // If animation is false, then stop animation.
        if (animation === false) {
            animation = {
                duration: 0
            };
        }
        if (range) {
            style = Ext.applyIf({
                position: position,
                axis: me,
                min: range[0],
                max: range[1],
                length: length,
                grid: me.getGrid(),
                hidden: me.getHidden(),
                titleOffset: me.titleOffset,
                layout: me.getLayout(),
                segmenter: me.getSegmenter(),
                totalAngle: me.getTotalAngle(),
                label: me.getLabel()
            }, me.getStyle());

            // If the sprites are not created.
            if (!me.sprites.length) {
                baseSprite = new Ext.chart.axis.sprite.Axis(style);
                baseSprite.fx.setCustomDurations({
                    baseRotation: 0
                });
                baseSprite.fx.on('animationstart', 'onAnimationStart', me);
                baseSprite.fx.on('animationend', 'onAnimationEnd', me);
                baseSprite.setLayout(me.getLayout());
                baseSprite.setSegmenter(me.getSegmenter());
                baseSprite.setLabel(me.getLabel());
                me.sprites.push(baseSprite);
                me.updateTitleSprite();
            } else {
                baseSprite = me.sprites[0];
                baseSprite.fx.setConfig(animation);
                baseSprite.setAttributes(style);
            }

            if (me.getRenderer()) {
                baseSprite.setRenderer(me.getRenderer());
            }
        }

        return me.sprites;
    },

    updateTitleSprite: function () {
        if ( !this.sprites[0] || isNaN(this.getLength()) ) {
            return;
        }
        var me = this,
            thickness = this.sprites[0].thickness,
            surface = me.getSurface(),
            title = me.getTitle(),
            position = me.getPosition(),
            margin = me.getMargin(),
            titleMargin = me.getTitleMargin(),
            length = me.getLength(),
            anchor = surface.roundPixel(length / 2);

        if (title) {
            switch (position) {
                case 'top':
                    title.setAttributes({
                        x: anchor,
                        y: margin + titleMargin / 2,
                        textBaseline: 'top',
                        textAlign: 'center'
                    }, true, true);
                    title.applyTransformations();
                    me.titleOffset = title.getBBox().height + titleMargin;
                    break;
                case 'bottom':
                    title.setAttributes({
                        x: anchor,
                        y: thickness + titleMargin / 2,
                        textBaseline: 'top',
                        textAlign: 'center'
                    }, true, true);
                    title.applyTransformations();
                    me.titleOffset = title.getBBox().height + titleMargin;
                    break;
                case 'left':
                    title.setAttributes({
                        x: margin + titleMargin / 2,
                        y: anchor,
                        textBaseline: 'top',
                        textAlign: 'center',
                        rotationCenterX: margin + titleMargin / 2,
                        rotationCenterY: anchor,
                        rotationRads: -Math.PI / 2
                    }, true, true);
                    title.applyTransformations();
                    me.titleOffset = title.getBBox().width + titleMargin;
                    break;
                case 'right':
                    title.setAttributes({
                        x: thickness - margin + titleMargin / 2,
                        y: anchor,
                        textBaseline: 'bottom',
                        textAlign: 'center',
                        rotationCenterX: thickness + titleMargin / 2,
                        rotationCenterY: anchor,
                        rotationRads: Math.PI / 2
                    }, true, true);
                    title.applyTransformations();
                    me.titleOffset = title.getBBox().width + titleMargin;
                    break;
            }
        }
    },

    onThicknessChanged: function () {
        this.getChart().onThicknessChanged();
    },

    getThickness: function () {
        if (this.getHidden()) {
            return 0;
        }
        return (this.sprites[0] && this.sprites[0].thickness || 1) + this.titleOffset + this.getMargin();
    },

    onAnimationStart: function () {
        this.animating++;
        if (this.animating === 1) {
            this.fireEvent('animationstart');
        }
    },

    onAnimationEnd: function () {
        this.animating--;
        if (this.animating === 0) {
            this.fireEvent('animationend');
        }
    },

    // Methods used in ComponentQuery and controller
    getItemId: function () {
        return this.getId();
    },

    getAncestorIds: function () {
        return [this.getChart().getId()];
    },

    isXType: function (xtype) {
        return xtype === 'axis';
    },

    destroy: function () {
        var chart = this.getChart();
        if (chart) {
            chart.un('redraw', this.renderLimits, this);
        }
        this.linkAxis();
        Ext.ComponentManager.unregister(this);
        this.callParent();
    }
});


/**
 * @class Ext.chart.LegendBase
 */
Ext.define('Ext.chart.LegendBase', {
    extend: 'Ext.view.View',
    config: {
        tpl: [
            '<div class="', Ext.baseCSSPrefix, 'legend-container">',
                '<tpl for=".">',
                    '<div class="', Ext.baseCSSPrefix, 'legend-item">',
                        '<span ',
                            'class="', Ext.baseCSSPrefix, 'legend-item-marker {[ values.disabled ? Ext.baseCSSPrefix + \'legend-inactive\' : \'\' ]}" ',
                            'style="background:{mark};">',
                        '</span>{name}',
                    '</div>',
                '</tpl>',
            '</div>'
        ],
        nodeContainerSelector: 'div.' + Ext.baseCSSPrefix + 'legend-container',
        itemSelector: 'div.' + Ext.baseCSSPrefix + 'legend-item',
        docked: 'bottom'
    },

    setDocked: function (docked) {
        var panel = this.ownerCt,
            layout;

        this.docked = docked;

        switch (docked) {
            case 'top':
            case 'bottom':
                this.addCls('x-horizontal');
                layout = 'hbox';
                break;
            case 'left':
            case 'right':
                this.removeCls('x-horizontal');
                layout = 'vbox';
                break;
        }

        if (panel) {
            panel.setDocked(docked);
        }
    },

    setStore: function (store) {
        this.bindStore(store);
    },

    clearViewEl: function () {
        this.callParent(arguments);
        // The legend-container div is not removed automatically.
        Ext.removeNode(this.getNodeContainer());
    },

    onItemClick: function (record, item, index, e) {
        this.callParent(arguments);
        this.toggleItem(index);
    }
});

/**
 * @class Ext.chart.Legend
 */
Ext.define('Ext.chart.Legend', {
    xtype: 'legend',
    extend: 'Ext.chart.LegendBase',
    config: {
        baseCls: 'x-legend',
        padding: 5,

        /**
         * @cfg {Array}
         * The rect of the legend related to its container.
         */
        rect: null,

        disableSelection: true,

        toggleable: true
    },

    toggleItem: function (index) {
        if (this.getToggleable()) {
            var store = this.getStore(),
                record = store && store.getAt(index);
            if (record) {
                record.set('disabled', !record.get('disabled'));
            }
        }
    }

});

/**
 * The Ext.chart package provides the capability to visualize data.
 * Each chart binds directly to a {@link Ext.data.Store store} enabling automatic updates of the chart.
 * A chart configuration object has some overall styling options as well as an array of axes
 * and series. A chart instance example could look like this:
 *
 *     new Ext.chart.CartesianChart({
 *         width: 800,
 *         height: 600,
 *         animation: true,
 *         store: store1,
 *         legend: {
 *             position: 'right'
 *         },
 *         axes: [
 *             // ...some axes options...
 *         ],
 *         series: [
 *             // ...some series options...
 *         ]
 *     });
 *
 * In this example we set the `width` and `height` of a chart; We decide whether our series are
 * animated or not and we select a store to be bound to the chart; We also set the legend to the right part of the
 * chart.
 *
 * You can register certain interactions such as {@link Ext.chart.interactions.PanZoom} on the chart by specify an
 * array of names or more specific config objects. All the events will be wired automatically.
 *
 * You can also listen to `itemXXX` events directly on charts. That case all the contained series will relay this event to the
 * chart.
 *
 * For more information about the axes and series configurations please check the documentation of
 * each series (Line, Bar, Pie, etc).
 *
 */

Ext.define('Ext.chart.AbstractChart', {

    extend: 'Ext.draw.Container',

    requires: [
        'Ext.chart.theme.Default',
        'Ext.chart.series.Series',
        'Ext.chart.interactions.Abstract',
        'Ext.chart.axis.Axis',
        'Ext.data.StoreManager',
        'Ext.chart.Legend',
        'Ext.data.Store'
    ],
    
    defaultBindProperty: 'store',

    /**
     * @event beforerefresh
     * Fires before a refresh to the chart data is called.  If the `beforerefresh` handler returns
     * `false` the {@link #refresh} action will be canceled.
     * @param {Ext.chart.AbstractChart} this
     */

    /**
     * @event refresh
     * Fires after the chart data has been refreshed.
     * @param {Ext.chart.AbstractChart} this
     */

    /**
     * @event redraw
     * Fires after the chart is redrawn.
     * @param {Ext.chart.AbstractChart} this
     */

    /**
     * @event itemmousemove
     * Fires when the mouse is moved on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
    /**
     * @event itemmouseup
     * Fires when a mouseup event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
    /**
     * @event itemmousedown
     * Fires when a mousedown event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
    /**
     * @event itemmouseover
     * Fires when the mouse enters a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
    /**
     * @event itemmouseout
     * Fires when the mouse exits a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
    /**
     * @event itemclick
     * Fires when a click event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
    /**
     * @event itemdoubleclick
     * Fires when a doubleclick event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
    /**
     * @event itemtap
     * Fires when a tap event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
    /**
     * @event itemtapstart
     * Fires when a tapstart event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
    /**
     * @event itemtapend
     * Fires when a tapend event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
    /**
     * @event itemtapcancel
     * Fires when a tapcancel event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
    /**
     * @event itemtaphold
     * Fires when a taphold event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
    /**
     * @event itemdoubletap
     * Fires when a doubletap event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
    /**
     * @event itemsingletap
     * Fires when a singletap event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
    /**
     * @event itemtouchstart
     * Fires when a touchstart event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
    /**
     * @event itemtouchmove
     * Fires when a touchmove event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
    /**
     * @event itemtouchend
     * Fires when a touchend event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
    /**
     * @event itemdragstart
     * Fires when a dragstart event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
    /**
     * @event itemdrag
     * Fires when a drag event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
    /**
     * @event itemdragend
     * Fires when a dragend event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
    /**
     * @event itempinchstart
     * Fires when a pinchstart event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
    /**
     * @event itempinch
     * Fires when a pinch event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
    /**
     * @event itempinchend
     * Fires when a pinchend event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
    /**
     * @event itemswipe
     * Fires when a swipe event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */

    /**
     * @property version Current version of Sencha Charts.
     * @type {String}
     */
    version: '2.5.0',

    delegationRegex: /^item([a-z]+)$/i,

    domEvents: new RegExp("click|focus|blur|paste|input|mousemove|mousedown|mouseup|mouseover|mouseout|keyup|keydown|keypress|submit|"+
                          "pinch|pinchstart|pinchend|touchmove|touchstart|touchend|rotate|rotatestart|rotateend|drag|dragstart|dragend|tap|doubletap|singletap"),

    config: {

        /**
         * @cfg {Ext.data.Store} store
         * The store that supplies data to this chart.
         */
        store: 'ext-empty-store',

        /**
         * @cfg {String} [theme="default"]
         * The name of the theme to be used. A theme defines the colors and styles
         * used by the series, axes, markers and other chart components.
         * Please see the documentation for the {@link Ext.chart.theme.Base} class for more information.
         * Possible theme values are:
         *   - 'green', 'sky', 'red', 'purple', 'blue', 'yellow'
         *   - 'category1' to 'category6'
         *   - and the above theme names with the '-gradients' suffix, e.g. 'green-gradients'
         */
        theme: 'default',

        /**
         * @cfg {Object} style
         * The style for the chart component.
         */
        style: null,

        /**
         * @cfg {Boolean/Object} shadow (optional) `true` for the default shadow configuration 
         * `{shadowOffsetX: 2, shadowOffsetY: 2, shadowBlur: 3, shadowColor: '#444'}`
         * or a standard shadow config object to be used for default chart shadows.
         */
        shadow: false,

        /**
         * @cfg {Boolean/Object} animation (optional) `true` for the default animation (easing: 'ease' and duration: 500)
         * or a standard animation config object to be used for default chart animations.
         */
        animation: !Ext.isIE8,

        /**
         * @cfg {Ext.chart.series.Series/Array} series
         * Array of {@link Ext.chart.series.Series Series} instances or config objects. For example:
         *
         *     series: [{
         *         type: 'column',
         *         axis: 'left',
         *         listeners: {
         *             'afterrender': function() {
         *                 console.log('afterrender');
         *             }
         *         },
         *         xField: 'category',
         *         yField: 'data1'
         *     }]
         */
        series: [],

        /**
         * @cfg {Ext.chart.axis.Axis/Array/Object} axes
         * Array of {@link Ext.chart.axis.Axis Axis} instances or config objects. For example:
         *
         *     axes: [{
         *         type: 'numeric',
         *         position: 'left',
         *         title: 'Number of Hits',
         *         minimum: 0
         *     }, {
         *         type: 'category',
         *         position: 'bottom',
         *         title: 'Month of the Year'
         *     }]
         */
        axes: [],

        /**
         * @cfg {Ext.chart.Legend/Object} legend
         */
        legend: null,

        /**
         * @cfg {Array} colors Array of colors/gradients to override the color of items and legends.
         */
        colors: null,

        /**
         * @cfg {Object|Number|String} insetPadding The amount of inset padding in pixels for the chart.
         * Inset padding is the padding from the boundary of the chart to any of its contents.
         */
        insetPadding: {
            top: 10,
            left: 10,
            right: 10,
            bottom: 10
        },

        /**
         * @cfg {Object} background Set the chart background. This can be a gradient object, image, or color.
         *
         * For example, if `background` were to be a color we could set the object as
         *
         *     background: '#ccc'
         *
         * You can specify an image by using:
         *
         *     background: {
         *         type: 'image',
         *         src: 'http://path.to.image/'
         *     }
         *
         * Also you can specify a gradient by using the gradient object syntax:
         *
         *     background: {
         *         type: 'linear',
         *         degrees: 0,
         *         stops: [
         *             {
         *                 offset: 0,
         *                 color: 'white'
         *             },
         *             {
         *                 offset: 1,
         *                 color: 'blue'
         *             }
         *         ]
         *     }
         */
        background: null,

        /**
         * @cfg {Array} interactions
         * Interactions are optional modules that can be plugged in to a chart to allow the user to interact
         * with the chart and its data in special ways. The `interactions` config takes an Array of Object
         * configurations, each one corresponding to a particular interaction class identified by a `type` property:
         *
         *     new Ext.chart.AbstractChart({
         *         renderTo: Ext.getBody(),
         *         width: 800,
         *         height: 600,
         *         store: store1,
         *         axes: [
         *             // ...some axes options...
         *         ],
         *         series: [
         *             // ...some series options...
         *         ],
         *         interactions: [{
         *             type: 'interactiontype'
         *             // ...additional configs for the interaction...
         *         }]
         *     });
         *
         * When adding an interaction which uses only its default configuration (no extra properties other than `type`),
         * you can alternately specify only the type as a String rather than the full Object:
         *
         *     interactions: ['reset', 'rotate']
         *
         * The current supported interaction types include:
         *
         * - {@link Ext.chart.interactions.PanZoom panzoom} - allows pan and zoom of axes
         * - {@link Ext.chart.interactions.ItemHighlight itemhighlight} - allows highlighting of series data points
         * - {@link Ext.chart.interactions.ItemInfo iteminfo} - allows displaying details of a data point in a popup panel
         * - {@link Ext.chart.interactions.Rotate rotate} - allows rotation of pie and radar series
         *
         * See the documentation for each of those interaction classes to see how they can be configured.
         *
         * Additional custom interactions can be registered using `'interactions.'` alias prefix.
         */
        interactions: [],

        /**
         * @private
         * The main area of the chart where grid and series are drawn.
         */
        mainRect: null,

        /**
         * @private
         * Override value.
         */
        resizeHandler: null,

        /**
         * @readonly
         * @cfg {Object} highlightItem
         * The current highlight item in the chart.
         * The object must be the one that you get from item events.
         *
         * Note that series can also own highlight items.
         * This notion is separate from this one and should not be used at the same time.
         */
        highlightItem: null
    },

    /**
     * @private
     */
    resizing: 0,

    /**
     * Toggle for chart interactions that require animation to be suspended.
     * @private
     */
    animationSuspended: 0,

    /**
     * @private The z-indexes to use for the various surfaces
     */
    surfaceZIndexes: {
        background: 0,
        main: 1,
        grid: 2,
        series: 3,
        axis: 4,
        chart: 5,
        overlay: 6,
        events: 7
    },

    animating: 0,

    layoutSuspended: 0,

    applyAnimation: function (newAnimation, oldAnimation) {
        if (!newAnimation) {
            newAnimation = {
                duration: 0
            };
        } else if (newAnimation === true) {
            newAnimation = {
                easing: 'easeInOut',
                duration: 500
            };
        }
        return oldAnimation ? Ext.apply({}, newAnimation, oldAnimation) : newAnimation;
    },

    applyInsetPadding: function (padding, oldPadding) {
        if (!Ext.isObject(padding)) {
            return Ext.util.Format.parseBox(padding);
        } else if (!oldPadding) {
            return padding;
        } else {
            return Ext.apply(oldPadding, padding);
        }
    },

    suspendAnimation: function () {
        this.animationSuspended++;
        if (this.animationSuspended === 1) {
            var series = this.getSeries(), i = -1, n = series.length;
            while (++i < n) {
                //update animation config to not animate
                series[i].setAnimation(this.getAnimation());
            }
        }
    },

    resumeAnimation: function () {
        this.animationSuspended--;
        if (this.animationSuspended === 0) {
            var series = this.getSeries(), i = -1, n = series.length;
            while (++i < n) {
                //update animation config to animate
                series[i].setAnimation(this.getAnimation());
            }
        }
    },

    suspendChartLayout: function () {
        this.layoutSuspended++;
        if (this.layoutSuspended === 1) {
            if (this.scheduledLayoutId) {
                this.layoutInSuspension = true;
                this.cancelLayout();
            } else {
                this.layoutInSuspension = false;
            }
        }
    },

    resumeChartLayout: function () {
        this.layoutSuspended--;
        if (this.layoutSuspended === 0) {
            if (this.layoutInSuspension) {
                this.scheduleLayout();
            }
        }
    },

    /**
     * Cancel a scheduled layout.
     */
    cancelLayout: function () {
        if (this.scheduledLayoutId) {
            Ext.draw.Animator.cancel(this.scheduledLayoutId);
            this.scheduledLayoutId = null;
        }
    },

    /**
     * Schedule a layout at next frame.
     */
    scheduleLayout: function () {
        var me = this;

        if (me.rendered && !me.scheduledLayoutId) {
            me.scheduledLayoutId = Ext.draw.Animator.schedule('doScheduleLayout', me);
        }
    },

    doScheduleLayout: function () {
        if (this.layoutSuspended) {
            this.layoutInSuspension = true;
        } else {
            this.performLayout();
        }
    },

    getAnimation: function () {
        if (this.resizing || this.animationSuspended) {
            return {
                duration: 0
            };
        } else {
            return this.callParent();
        }
    },

    constructor: function (config) {
        var me = this;

        me.itemListeners = {};
        me.surfaceMap = {};

        me.isInitializing = true;
        me.suspendChartLayout();
        me.callParent(arguments);
        delete me.isInitializing;

        me.getSurface('main');
        me.getSurface('chart').setFlipRtlText(me.getInherited().rtl);
        me.getSurface('overlay').waitFor(me.getSurface('series'));
        me.resumeChartLayout();
    },

    applySprites: function (sprites) {
        var surface = this.getSurface('chart');

        sprites = Ext.Array.from(sprites);
        surface.removeAll(true);
        surface.add(sprites);
    },

    initItems: function () {
        var items = this.items,
            i, ln, item;
        if (items && !items.isMixedCollection) {
            this.items = [];
            items = Ext.Array.from(items);
            for (i = 0, ln = items.length; i < ln; i++) {
                item = items[i];
                if (item.type) {
                    Ext.Error.raise("To add custom sprites to the chart use the 'sprites' config.");
                } else {
                    this.items.push(item);
                }
            }
        }
        this.callParent();
    },

    applyBackground: function (newBackground, oldBackground) {
        var surface = this.getSurface('background'),
            width, height, isUpdateOld;
        if (newBackground) {
            if (oldBackground) {
                width = oldBackground.attr.width;
                height = oldBackground.attr.height;
                isUpdateOld = oldBackground.type === (newBackground.type || 'rect');
            }
            if (newBackground.isSprite) {
                oldBackground = newBackground;
            } else if (newBackground.type === 'image' && Ext.isString(newBackground.src)) {
                if (isUpdateOld) {
                    oldBackground.setAttributes({
                        src: newBackground.src
                    });
                } else {
                    surface.remove(oldBackground, true);
                    oldBackground = surface.add(newBackground);
                }
            } else {
                if (isUpdateOld) {
                    oldBackground.setAttributes({
                        fillStyle: newBackground
                    });
                } else {
                    surface.remove(oldBackground, true);
                    oldBackground = surface.add({
                        type: 'rect',
                        fillStyle: newBackground,
                        fx: {
                            customDurations: {
                                x: 0,
                                y: 0,
                                width: 0,
                                height: 0
                            }
                        }
                    });
                }
            }
        }
        if (width && height) {
            oldBackground.setAttributes({
                width: width,
                height: height
            });
        }
        oldBackground.fx.setConfig(this.getAnimation());
        return oldBackground;
    },

    /**
     * Return the legend store that contains all the legend information.
     * This information is collected from all the series.
     * @return {Ext.data.Store}
     */
    getLegendStore: function () {
        return this.legendStore;
    },

    refreshLegendStore: function () {
        if (this.getLegendStore()) {
            var i, ln,
                series = this.getSeries(), seriesItem,
                legendData = [];
            if (series) {
                for (i = 0, ln = series.length; i < ln; i++) {
                    seriesItem = series[i];
                    if (seriesItem.getShowInLegend()) {
                        seriesItem.provideLegendInfo(legendData);
                    }
                }
            }
            this.getLegendStore().setData(legendData);
        }
    },

    resetLegendStore: function () {
        if (this.getLegendStore()) {
            var data = this.getLegendStore().getData().items,
                i, ln = data.length,
                record;
            for (i = 0; i < ln; i++) {
                record = data[i];
                record.beginEdit();
                record.set('disabled', false);
                record.commit();
            }
        }
    },

    onUpdateLegendStore: function (store, record) {
        var series = this.getSeries(), seriesItem;
        if (record && series) {
            seriesItem = series.map[record.get('series')];
            if (seriesItem) {
                seriesItem.setHiddenByIndex(record.get('index'), record.get('disabled'));
                this.redraw();
            }
        }
    },

    resizeHandler: function (size) {
        var me = this;
        me.scheduleLayout();
        return false;
    },

    applyMainRect: function (newRect, rect) {
        if (!rect) {
            return newRect;
        }
        this.getSeries();
        this.getAxes();
        if (newRect[0] === rect[0] &&
            newRect[1] === rect[1] &&
            newRect[2] === rect[2] &&
            newRect[3] === rect[3]) {
            return rect;
        } else {
            return newRect;
        }
    },

    getAxis: function (axis) {
        if (axis instanceof Ext.chart.axis.Axis) {
            return axis;
        } else if (Ext.isNumber(axis)) {
            return this.getAxes()[axis];
        } else if (Ext.isString(axis)) {
            return Ext.ComponentMgr.get(axis);
        } else {
            return null;
        }
    },

    getSurface: function (name, type) {
        name = name || 'main';
        type = type || name;
        var me = this,
            surface = this.callParent([name]),
            zIndexes = me.surfaceZIndexes;
        if (type in zIndexes) {
            surface.element.setStyle('zIndex', zIndexes[type]);
        }
        if (!me.surfaceMap[type]) {
            me.surfaceMap[type] = [];
        }
        if (Ext.Array.indexOf(me.surfaceMap[type], (surface)) < 0) {
            surface.type = type;
            me.surfaceMap[type].push(surface);
        }
        return surface;
    },

    applyAxes: function (newAxes, oldAxes) {
        this.resizing++;

        this.getStore();
        if (!oldAxes) {
            oldAxes = [];
            oldAxes.map = {};
        }
        var result = [], i, ln, axis, oldAxis, linkedTo, id,
            positions = {left: 'right', right: 'left'},
            oldMap = oldAxes.map;
        result.map = {};
        newAxes = Ext.Array.from(newAxes, true);
        for (i = 0, ln = newAxes.length; i < ln; i++) {
            axis = Ext.Object.chain(newAxes[i]);
            if (!axis) {
                continue;
            }

            linkedTo = axis.linkedTo;
            id = axis.id;
            if (Ext.isNumber(linkedTo)) {
                axis = Ext.merge({}, newAxes[linkedTo], axis);
            } else if (Ext.isString(linkedTo)) {
                Ext.Array.each(newAxes, function (item) {
                    if (item.id === axis.linkedTo) {
                        axis = Ext.merge({}, item, axis);
                        return false;
                    }
                });
            }
            axis.id = id;

            if (this.getInherited().rtl) {
                axis.position = positions[axis.position] || axis.position;
            }
            axis = Ext.factory(axis, null, oldAxis = oldMap[axis.getId && axis.getId() || axis.id], 'axis');
            if (axis) {
                axis.setChart(this);
                result.push(axis);
                result.map[axis.getId()] = axis;
                if (!oldAxis) {
                    axis.on('animationstart', 'onAnimationStart', this);
                    axis.on('animationend', 'onAnimationEnd', this);
                }
            }
        }

        for (i in oldMap) {
            if (!result.map[i]) {
                oldMap[i].destroy();
            }
        }

        this.resizing--;

        return result;
    },

    updateAxes: function (newAxes) {
        this.scheduleLayout();
    },

    circularCopyArray: function(inArray, startIndex, count) {
        var outArray = [],
            i, len = inArray && inArray.length;
        if (len) {
            for (i = 0; i < count; i++) {
                outArray.push(inArray[(startIndex + i) % len]);
            }
        }
        return outArray;
    },

    circularCopyObject: function(inObject, startIndex, count) {
        var me = this,
            name, value, outObject = {};
        if (count) {
            for (name in inObject) {
                if (inObject.hasOwnProperty(name)) {
                    value = inObject[name];
                    if (Ext.isArray(value)) {
                        outObject[name] = me.circularCopyArray(value, startIndex, count);
                    } else {
                        outObject[name] = value;
                    }
                }
            }
        }
        return outObject;
    },

    getColors: function () {
        var me = this,
            configColors = me.config.colors,
            theme = me.getTheme();
        if (Ext.isArray(configColors) && configColors.length > 0) {
            configColors = me.applyColors(configColors);
        }
        return configColors || (theme && theme.getColors());
    },

    applyColors: function (newColors) {
        newColors = Ext.Array.map(newColors, function(color) {
            if (Ext.isString(color)) {
                return color;
            } else {
                return color.toString();
            }
        });
        return newColors;
    },

    updateColors: function (newColors) {
        var me = this,
            theme = me.getTheme(),
            colors = newColors || (theme && theme.getColors()),
            colorCount = colors.length,
            colorIndex = 0,
            series = me.getSeries(),
            seriesCount = series && series.length,
            i, seriesItem, seriesColors, seriesColorCount;

        if (colorCount) {
            for (i = 0; i < seriesCount; i++) {
                seriesItem = series[i];
                seriesColorCount = seriesItem.themeColorCount();
                seriesColors = me.circularCopyArray(colors, colorIndex, seriesColorCount);
                colorIndex += seriesColorCount;
                seriesItem.updateChartColors(seriesColors);
            }
        }
        me.refreshLegendStore();
    },

    applyTheme: function (theme) {
        if (theme && theme.isTheme) {
            return theme;
        }
        return Ext.Factory.chartTheme(theme);
    },

    updateTheme: function (theme) {
        var me = this,
            axes = me.getAxes(),
            series = me.getSeries(),
            colors = me.getColors(),
            seriesItem, seriesTheme,
            colorIndex = 0,
            markerIndex = 0,
            markerCount,
            colorCount,
            i;

        me.updateChartTheme(theme);

        for (i = 0; i < axes.length; i++) {
            axes[i].updateTheme(theme);
        }

        for (i = 0; i < series.length; i++) {
            series[i].updateTheme(theme);

            seriesItem = series[i];
            seriesTheme = {};

            if (theme.getSeriesThemes) {
                colorCount = seriesItem.themeColorCount();
                seriesTheme.subStyle = me.circularCopyObject(theme.getSeriesThemes(), colorIndex, colorCount);
                colorIndex += colorCount;
            } else {
                seriesTheme.subStyle = {};
            }

            if (theme.getMarkerThemes) {
                markerCount = seriesItem.themeMarkerCount();
                seriesTheme.markerSubStyle = me.circularCopyObject(theme.getMarkerThemes(), markerIndex, markerCount);
                markerIndex += markerCount;
            } else {
                seriesTheme.markerSubStyle = {};
            }
        }

        me.updateSpriteTheme(theme);

        me.updateColors(colors);
    },

    themeOnlyIfConfigured: {
    },

    updateChartTheme: function (theme) {
        var me = this,
            chartTheme = theme.getChart(),
            initialConfig = me.getInitialConfig(),
            defaultConfig = me.defaultConfig,
            configs = me.getConfigurator().configs,
            genericChartTheme = chartTheme.defaults,
            specificChartTheme = chartTheme[me.xtype],
            themeOnlyIfConfigured = me.themeOnlyIfConfigured,
            key, value, isObjValue, isUnusedConfig, initialValue, cfg;

        chartTheme = Ext.merge({}, genericChartTheme, specificChartTheme);
        for (key in chartTheme) {
            value = chartTheme[key];
            cfg = configs[key];
            if (value !== null && value !== undefined && cfg) {
                initialValue = initialConfig[key];
                isObjValue = Ext.isObject(value);
                isUnusedConfig = initialValue === defaultConfig[key];
                if (isObjValue) {
                    if (isUnusedConfig && themeOnlyIfConfigured[key]) {
                        continue;
                    }
                    value = Ext.merge({}, value, initialValue);
                }
                if (isUnusedConfig || isObjValue) {
                    me[cfg.names.set](value);
                }
            }
        }
    },

    updateSpriteTheme: function (theme) {
        var me = this,
            chartSurface = me.getSurface('chart'),
            sprites = chartSurface.getItems(),
            styles = theme.getSprites(),
            sprite, style,
            key, attr,
            isText,
            i, ln;

        for (i = 0, ln = sprites.length; i < ln; i++) {
            sprite = sprites[i];
            style = styles[sprite.type];
            if (style) {
                attr = {};
                isText = sprite.type === 'text';
                for (key in style) {
                    if (!(key in sprite.config)) {
                        // Setting individual font attributes will take over the 'font' shorthand
                        // attribute, but this behavior is undesireable for theming.
                        if (!(isText && key.indexOf('font') === 0 && sprite.config.font)) {
                            attr[key] = style[key];
                        }
                    }
                }
                sprite.setAttributes(attr);
            }
        }
    },

    applySeries: function (newSeries, oldSeries) {
        var me = this,
            result = [],
            oldMap, oldSeriesItem,
            i, ln, series;

        me.resizing++;

        me.getAxes();
        if (!oldSeries) {
            oldSeries = [];
            oldMap = oldSeries.map = {};
        }
        result.map = {};
        newSeries = Ext.Array.from(newSeries, true);
        for (i = 0, ln = newSeries.length; i < ln; i++) {
            series = newSeries[i];
            if (!series) {
                continue;
            }
            oldSeriesItem = oldSeries.map[series.getId && series.getId() || series.id];
            if (series instanceof Ext.chart.series.Series) {
                if (oldSeriesItem !== series) {
                    // Replacing
                    if (oldSeriesItem) {
                        oldSeriesItem.destroy();
                    }
                    me.addItemListenersToSeries(series);
                }
                series.setChart(me);
            } else if (Ext.isObject(series)) {
                if (oldSeriesItem) {
                    // Update
                    oldSeriesItem.setConfig(series);
                    series = oldSeriesItem;
                } else {
                    // Create a series.
                    if (Ext.isString(series)) {
                        series = Ext.create(series.xclass || ('series.' + series), {chart: me});
                    } else {
                        series.chart = me;
                        series = Ext.create(series.xclass || ('series.' + series.type), series);
                    }
                    series.on('animationstart', 'onAnimationStart', me);
                    series.on('animationend', 'onAnimationEnd', me);
                    me.addItemListenersToSeries(series);
                }
            }

            result.push(series);
            result.map[series.getId()] = series;
        }

        for (i in oldMap) {
            if (!result.map[oldMap[i].getId()]) {
                oldMap[i].destroy();
            }
        }

        me.resizing--;

        return result;
    },

    applyLegend: function (newLegend, oldLegend) {
        return Ext.factory(newLegend, Ext.chart.Legend, oldLegend);
    },

    updateLegend: function (legend, oldLegend) {
        if (oldLegend) {
            oldLegend.destroy();
        }
        if (legend) {
            this.getItems();
            this.legendStore = new Ext.data.Store({
                autoDestroy: true,
                fields: [
                    'id', 'name', 'mark', 'disabled', 'series', 'index'
                ]
            });
            legend.setStore(this.legendStore);
            this.refreshLegendStore();
            this.legendStore.on('update', 'onUpdateLegendStore', this);
        }
    },

    setParent: function (parent) {
        this.callParent(arguments);
        if (parent && this.getLegend()) {
            parent.add(this.getLegend());
        }
    },

    updateSeries: function (newSeries, oldSeries) {
        this.resizing++;

        this.fireEvent('serieschange', this, newSeries, oldSeries);
        this.refreshLegendStore();
        this.scheduleLayout();

        this.resizing--;
    },

    applyInteractions: function (interactions, oldInteractions) {
        if (!oldInteractions) {
            oldInteractions = [];
            oldInteractions.map = {};
        }
        var me = this,
            result = [], oldMap = oldInteractions.map,
            i, ln, interaction;
        result.map = {};
        interactions = Ext.Array.from(interactions, true);
        for (i = 0, ln = interactions.length; i < ln; i++) {
            interaction = interactions[i];
            if (!interaction) {
                continue;
            }
            interaction = Ext.factory(interaction, null, oldMap[interaction.getId && interaction.getId() || interaction.id], 'interaction');
            if (interaction) {
                interaction.setChart(me);
                result.push(interaction);
                result.map[interaction.getId()] = interaction;
            }
        }

        for (i in oldMap) {
            if (!result.map[oldMap[i]]) {
                oldMap[i].destroy();
            }
        }
        return result;
    },

    applyStore: function (store) {
        return store && Ext.StoreManager.lookup(store);
    },

    updateStore: function (newStore, oldStore) {
        var me = this;
        if (oldStore) {
            oldStore.unAfter({
                datachanged: 'onDataChanged',
                update: 'onDataChanged',
                scope: me
            });
            if (oldStore.autoDestroy) {
                oldStore.destroy();
            }
        }
        if (newStore) {
            newStore.onAfter({
                datachanged: 'onDataChanged',
                update: 'onDataChanged',
                scope: me
            });
        }

        me.fireEvent('storechange', newStore, oldStore);
        me.onDataChanged();
    },

    /**
     * Redraw the chart. If animations are set this will animate the chart too.
     */
    redraw: function () {
        this.fireEvent('redraw', this);
    },

    performLayout: function () {
        var me = this,
            size = me.innerElement.getSize(),
            chartRect = [0, 0, size.width, size.height],
            background = me.getBackground();

        me.hasFirstLayout = true;
        me.fireEvent('layout');
        me.cancelLayout();
        me.getSurface('background').setRect(chartRect);
        me.getSurface('chart').setRect(chartRect);
        background.setAttributes({
            width: size.width,
            height: size.height
        });
    },

    // Converts page coordinates into chart's 'main' surface coordinates.
    getEventXY: function (e) {
        return this.getSurface().getEventXY(e);
    },

    /**
     * Given an x/y point relative to the chart, find and return the first series item that
     * matches that point.
     * @param {Number} x
     * @param {Number} y
     * @return {Object} An object with `series` and `item` properties, or `false` if no item found.
     */
    getItemForPoint: function (x, y) {
        var me = this,
            items = me.getSeries(),
            l = items.length,
            // If we haven't drawn yet, don't attempt to find any items.
            i = me.hasFirstLayout ? l - 1 : -1,
            series, item;

        // Iterate from the end so that the series that are drawn later get hit tested first.
        while (i >= 0) {
            series = items[i];
            item = series.getItemForPoint(x, y);
            if (item) {
                return item;
            }
            i--;
        }

        return null;
    },

    /**
     * Given an x/y point relative to the chart, find and return all series items that match that point.
     * @param {Number} x
     * @param {Number} y
     * @return {Array} An array of objects with `series` and `item` properties.
     */
    getItemsForPoint: function (x, y) {
        var me = this,
            series = me.getSeries(),
            seriesItem,
            items = [];

        for (var i = 0; i < series.length; i++) {
            seriesItem = series[i];
            var item = seriesItem.getItemForPoint(x, y);
            if (item) {
                items.push(item);
            }
        }

        return items;
    },

    /**
     * @private
     */
    delayThicknessChanged: 0,

    /**
     * @private
     */
    thicknessChanged: false,

    /**
     * Suspend the layout initialized by thickness change
     */
    suspendThicknessChanged: function () {
        this.delayThicknessChanged++;
    },

    /**
     * Resume the layout initialized by thickness change
     */
    resumeThicknessChanged: function () {
        if (this.delayThicknessChanged > 0) {
            this.delayThicknessChanged--;
            if (this.delayThicknessChanged === 0 && this.thicknessChanged) {
                this.onThicknessChanged();
            }
        }
    },

    onAnimationStart: function () {
        this.fireEvent('animationstart', this);
    },

    onAnimationEnd: function () {
        this.fireEvent('animationend', this);
    },

    onThicknessChanged: function () {
        if (this.delayThicknessChanged === 0) {
            this.thicknessChanged = false;
            this.performLayout();
        } else {
            this.thicknessChanged = true;
        }
    },

    /**
     * @private
     */
    onDataChanged: function () {
        var me = this;
        if (me.isInitializing) {
            return;
        }
        var rect = me.getMainRect(),
            store = me.getStore(),
            series = me.getSeries(),
            axes = me.getAxes(),
            colors = me.getColors(),
            i, ln;

        if (!store || !axes || !series) {
            return;
        }
        if (!rect) { // The chart hasn't been rendered yet.
            me.on({
                redraw: me.onDataChanged,
                scope: me,
                single: true
            });
            return;
        }
        for (i = 0, ln = series.length; i < ln; i++) {
            series[i].processData();
        }
        me.updateColors(colors);
        me.redraw();
    },

    /**
     * Changes the data store bound to this chart and refreshes it.
     * @param {Ext.data.Store} store The store to bind to this chart.
     */
    bindStore: function (store) {
        this.setStore(store);
    },

    applyHighlightItem: function (newHighlightItem, oldHighlightItem) {
        if (newHighlightItem === oldHighlightItem) {
            return;
        }
        if (Ext.isObject(newHighlightItem) && Ext.isObject(oldHighlightItem)) {
            if (newHighlightItem.sprite === oldHighlightItem.sprite &&
                newHighlightItem.index === oldHighlightItem.index
                ) {
                return;
            }
        }
        return newHighlightItem;
    },

    updateHighlightItem: function (newHighlightItem, oldHighlightItem) {
        if (oldHighlightItem) {
            oldHighlightItem.series.setAttributesForItem(oldHighlightItem, {highlighted: false});
        }
        if (newHighlightItem) {
            newHighlightItem.series.setAttributesForItem(newHighlightItem, {highlighted: true});
            this.fireEvent('itemhighlight', newHighlightItem);
        }
    },

    addItemListenersToSeries: function (series) {
        for (var name in this.itemListeners) {
            var listenerMap = this.itemListeners[name], i, ln;
            for (i = 0, ln = listenerMap.length; i < ln; i++) {
                series.addListener.apply(series, listenerMap[i]);
            }
        }
    },

    addItemListener: function (name, fn, scope, options, order) {
        var listenerMap = this.itemListeners[name] || (this.itemListeners[name] = []),
            series = this.getSeries(), seriesItem,
            i, ln;
        listenerMap.push([name, fn, scope, options, order]);
        if (series) {
            for (i = 0, ln = series.length; i < ln; i++) {
                seriesItem = series[i];
                seriesItem.addListener(name, fn, scope, options, order);
            }
        }
    },

    remoteItemListener: function (name, fn, scope, options, order) {
        var listenerMap = this.itemListeners[name],
            series = this.getSeries(), seriesItem,
            i, ln;
        if (listenerMap) {
            for (i = 0, ln = listenerMap.length; i < ln; i++) {
                if (listenerMap[i].fn === fn) {
                    listenerMap.splice(i, 1);
                    if (series) {
                        for (i = 0, ln = series.length; i < ln; i++) {
                            seriesItem = series[i];
                            seriesItem.removeListener(name, fn, scope, options, order);
                        }
                    }
                    break;
                }
            }
        }
    },

    doAddListener: function (name, fn, scope, options, order) {
        if (name.match(this.delegationRegex)) {
            return this.addItemListener(name, fn, scope || this, options, order);
        } else if (name.match(this.domEvents)) {
            return this.element.doAddListener.apply(this.element, arguments);
        } else {
            return this.callParent(arguments);
        }
    },

    doRemoveListener: function (name, fn, scope, options, order) {
        if (name.match(this.delegationRegex)) {
            return this.remoteItemListener(name, fn, scope || this, options, order);
        } else if (name.match(this.domEvents)) {
            return this.element.doRemoveListener.apply(this.element, arguments);
        } else {
            return this.callParent(arguments);
        }
    },

    onItemRemove: function (item) {
        this.callParent(arguments);
        if (this.surfaceMap) {
            Ext.Array.remove(this.surfaceMap[item.type], item);
            if (this.surfaceMap[item.type].length === 0) {
                delete this.surfaceMap[item.type];
            }
        }
    },

    // @private remove gently.
    destroy: function () {
        var me = this,
            emptyArray = [],
            legend = me.getLegend();
        me.surfaceMap = null;
        me.setHighlightItem(null);
        me.setSeries(emptyArray);
        me.setAxes(emptyArray);
        me.setInteractions(emptyArray);
        if (legend) {
            legend.destroy();
            me.setLegend(null);
        }
        me.legendStore = null;
        me.setStore(null);
        me.cancelLayout();
        this.callParent(arguments);
    },

    /* ---------------------------------
     Methods needed for ComponentQuery
     ----------------------------------*/

    /**
     * @private
     * @param {Boolean} deep
     * @return {Array}
     */
    getRefItems: function (deep) {
        var me = this,
            series = me.getSeries(),
            axes = me.getAxes(),
            interaction = me.getInteractions(),
            ans = [], i, ln;

        for (i = 0, ln = series.length; i < ln; i++) {
            ans.push(series[i]);
            if (series[i].getRefItems) {
                ans.push.apply(ans, series[i].getRefItems(deep));
            }
        }

        for (i = 0, ln = axes.length; i < ln; i++) {
            ans.push(axes[i]);
            if (axes[i].getRefItems) {
                ans.push.apply(ans, axes[i].getRefItems(deep));
            }
        }

        for (i = 0, ln = interaction.length; i < ln; i++) {
            ans.push(interaction[i]);
            if (interaction[i].getRefItems) {
                ans.push.apply(ans, interaction[i].getRefItems(deep));
            }
        }

        return ans;
    }

});

/**
 * @class Ext.chart.overrides.AbstractChart
 */
Ext.define('Ext.chart.overrides.AbstractChart', {
    override: 'Ext.chart.AbstractChart',

    updateLegend: function (legend, oldLegend) {
        var dock;
        this.callParent(arguments);
        if (legend) {
            dock = legend.docked;
            this.addDocked({
                dock: dock,
                xtype: 'panel',
                shrinkWrap: true,
                autoScroll: true,
                layout: {
                    type: dock === 'top' || dock === 'bottom' ? 'hbox' : 'vbox',
                    pack: 'center'
                },
                items: legend,
                cls: Ext.baseCSSPrefix + 'legend-panel'
            });
        }
    }
});

/**
 * @class Ext.chart.grid.HorizontalGrid
 * @extends Ext.draw.sprite.Sprite
 * 
 * Horizontal Grid sprite. Used in Cartesian Charts.
 */
Ext.define('Ext.chart.grid.HorizontalGrid', {
    extend: 'Ext.draw.sprite.Sprite',
    alias: 'grid.horizontal',

    inheritableStatics: {
        def: {
            processors: {
                x: 'number',
                y: 'number',
                width: 'number',
                height: 'number'
            },

            defaults: {
                x: 0,
                y: 0,
                width: 1,
                height: 1,
                strokeStyle: '#DDD'
            }
        }
    },

    render: function (surface, ctx, clipRect) {
        var attr = this.attr,
            y = surface.roundPixel(attr.y),
            halfLineWidth = ctx.lineWidth * 0.5;
        ctx.beginPath();
        ctx.rect(clipRect[0] - surface.matrix.getDX(), y + halfLineWidth, +clipRect[2], attr.height);
        ctx.fill();

        ctx.beginPath();
        ctx.moveTo(clipRect[0] - surface.matrix.getDX(), y + halfLineWidth);
        ctx.lineTo(clipRect[0] + clipRect[2] - surface.matrix.getDX(), y + halfLineWidth);
        ctx.stroke();
    }
});

/**
 * @class Ext.chart.grid.VerticalGrid
 * @extends Ext.draw.sprite.Sprite
 * 
 * Vertical Grid sprite. Used in Cartesian Charts.
 */
Ext.define('Ext.chart.grid.VerticalGrid', {
    extend: 'Ext.draw.sprite.Sprite',
    alias: 'grid.vertical',

    inheritableStatics: {
        def: {
            processors: {
                x: 'number',
                y: 'number',
                width: 'number',
                height: 'number'
            },

            defaults: {
                x: 0,
                y: 0,
                width: 1,
                height: 1,
                strokeStyle: '#DDD'
            }
        }
    },

    render: function (surface, ctx, clipRect) {
        var attr = this.attr,
            x = surface.roundPixel(attr.x),
            halfLineWidth = ctx.lineWidth * 0.5;
        ctx.beginPath();
        ctx.rect(x - halfLineWidth, clipRect[1] - surface.matrix.getDY(), attr.width, clipRect[3]);
        ctx.fill();

        ctx.beginPath();
        ctx.moveTo(x - halfLineWidth, clipRect[1] - surface.matrix.getDY());
        ctx.lineTo(x - halfLineWidth, clipRect[1] + clipRect[3] - surface.matrix.getDY());
        ctx.stroke();
    }
});

/**
 * @class Ext.chart.CartesianChart
 * @extends Ext.chart.AbstractChart
 * @xtype cartesian
 *
 * Represents a chart that uses cartesian coordinates.
 * A cartesian chart has two directions, X direction and Y direction.
 * The series and axes are coordinated along these directions.
 * By default the x direction is horizontal and y direction is vertical,
 * You can swap the direction by setting the {@link #flipXY} config to `true`.
 *
 * Cartesian series often treats x direction an y direction differently.
 * In most cases, data on x direction are assumed to be monotonically increasing.
 * Based on this property, cartesian series can be trimmed and summarized properly
 * to gain a better performance.
 *
 */

Ext.define('Ext.chart.CartesianChart', {
    extend: 'Ext.chart.AbstractChart',
    alternateClassName: 'Ext.chart.Chart',
    requires: ['Ext.chart.grid.HorizontalGrid', 'Ext.chart.grid.VerticalGrid'],
    config: {
        /**
         * @cfg {Boolean} flipXY Flip the direction of X and Y axis.
         * If flipXY is `true`, the X axes will be vertical and Y axes will be horizontal.
         * Note that {@link Ext.chart.axis.Axis#position positions} of chart axes have
         * to be updated accordingly: axes positioned to the `top` and `bottom` should
         * be positioned to the `left` or `right` and vice versa.
         */
        flipXY: false,
        /*

         While it may seem tedious to change the position config of all axes every time
         when the value of the flipXY config is changed, it's hard to predict the
         expectaction of the user here, as illustrated below.

         The 'num' and 'cat' here stand for the numeric and the category axis, respectively.
         And the right column shows the expected (subjective) result of setting the flipXY
         config of the chart to 'true'.

         As one can see, there's no single rule (e.g. position swapping, clockwise 90° chart
         rotation) that will produce a universally accepted result.
         So we are letting the user decide, instead of doing it for them.

         ---------------------------------------------
         |   flipXY: false       |    flipXY: true   |
         ---------------------------------------------
         |        ^              |      ^            |
         |        |     *        |      | * * *      |
         |   num1 |   * *        |  cat | * *        |
         |        | * * *        |      | *          |
         |        -------->      |      -------->    |
         |           cat         |         num1      |
         ---------------------------------------------
         |                       |         num1      |
         |       ^       ^       |      ^------->    |
         |       |     * |       |      | * * *      |
         |  num1 |   * * | num2  |  cat | * *        |
         |       | * * * |       |      | *          |
         |       -------->       |      -------->    |
         |          cat          |         num2      |
         ---------------------------------------------

         */

        innerRect: [0, 0, 1, 1],

        /**
         * @cfg {Object} innerPadding The amount of inner padding in pixels.
         * Inner padding is the padding from the innermost axes to the series.
         */
        innerPadding: {
            top: 0,
            left: 0,
            right: 0,
            bottom: 0
        }
    },
    xtype: [ 'cartesian', 'chart' ],

    applyInnerPadding: function (padding, oldPadding) {
        if (!Ext.isObject(padding)) {
            return Ext.util.Format.parseBox(padding);
        } else if (!oldPadding) {
            return padding;
        } else {
            return Ext.apply(oldPadding, padding);
        }
    },

    getDirectionForAxis: function (position) {
        var flipXY = this.getFlipXY();
        if (position === 'left' || position === 'right') {
            if (flipXY) {
                return 'X';
            } else {
                return 'Y';
            }
        } else {
            if (flipXY) {
                return 'Y';
            } else {
                return 'X';
            }
        }
    },

    /**
     * Layout the axes and series.
     */
    performLayout: function () {
        this.resizing++;

        this.callParent();
        this.suspendThicknessChanged();
        var me = this,
            chartRect = me.getSurface('chart').getRect(),
            width = chartRect[2],
            height = chartRect[3],
            axes = me.getAxes(), axis,
            seriesList = me.getSeries(), series,
            axisSurface, thickness,
            insetPadding = me.getInsetPadding(),
            innerPadding = me.getInnerPadding(),
            surface, gridSurface,
            shrinkBox = Ext.apply({}, insetPadding),
            mainRect, innerWidth, innerHeight,
            elements, floating, floatingValue, matrix, i, ln,
            isRtl = me.getInherited().rtl,
            flipXY = me.getFlipXY();

        if (width <= 0 || height <= 0) {
            return;
        }

        for (i = 0; i < axes.length; i++) {
            axis = axes[i];
            axisSurface = axis.getSurface();
            floating = axis.getFloating();
            floatingValue = floating ? floating.value : null;
            thickness = axis.getThickness();
            switch (axis.getPosition()) {
                case 'top':
                    axisSurface.setRect([0, shrinkBox.top + 1, width, thickness]);
                    break;
                case 'bottom':
                    axisSurface.setRect([0, height - (shrinkBox.bottom + thickness), width, thickness]);
                    break;
                case 'left':
                    axisSurface.setRect([shrinkBox.left, 0, thickness, height]);
                    break;
                case 'right':
                    axisSurface.setRect([width - (shrinkBox.right + thickness), 0, thickness, height]);
                    break;
            }
            if (floatingValue === null) {
                shrinkBox[axis.getPosition()] += thickness;
            }
        }

        width -= shrinkBox.left + shrinkBox.right;
        height -= shrinkBox.top + shrinkBox.bottom;

        mainRect = [shrinkBox.left, shrinkBox.top, width, height];

        shrinkBox.left += innerPadding.left;
        shrinkBox.top += innerPadding.top;
        shrinkBox.right += innerPadding.right;
        shrinkBox.bottom += innerPadding.bottom;

        innerWidth = width - innerPadding.left - innerPadding.right;
        innerHeight = height - innerPadding.top - innerPadding.bottom;

        me.setInnerRect([shrinkBox.left, shrinkBox.top, innerWidth, innerHeight]);

        if (innerWidth <= 0 || innerHeight <= 0) {
            return;
        }

        me.setMainRect(mainRect);
        me.getSurface().setRect(mainRect);

        for (i = 0, ln = me.surfaceMap.grid && me.surfaceMap.grid.length; i < ln; i++) {
            gridSurface = me.surfaceMap.grid[i];
            gridSurface.setRect(mainRect);
            gridSurface.matrix.set(1, 0, 0, 1, innerPadding.left, innerPadding.top);
            gridSurface.matrix.inverse(gridSurface.inverseMatrix);
        }

        for (i = 0; i < axes.length; i++) {
            axis = axes[i];
            axisSurface = axis.getSurface();
            matrix = axisSurface.matrix;
            elements = matrix.elements;
            switch (axis.getPosition()) {
                case 'top':
                case 'bottom':
                    elements[4] = shrinkBox.left;
                    axis.setLength(innerWidth);
                    break;
                case 'left':
                case 'right':
                    elements[5] = shrinkBox.top;
                    axis.setLength(innerHeight);
                    break;
            }
            axis.updateTitleSprite();
            matrix.inverse(axisSurface.inverseMatrix);
        }

        for (i = 0, ln = seriesList.length; i < ln; i++) {
            series = seriesList[i];
            surface = series.getSurface();
            surface.setRect(mainRect);
            if (flipXY) {
                if (isRtl) {
                    surface.matrix.set(0, -1, -1, 0,
                            innerPadding.left + innerWidth,
                            innerPadding.top + innerHeight);
                } else {
                    surface.matrix.set(0, -1, 1, 0,
                        innerPadding.left,
                        innerPadding.top + innerHeight);
                }
            } else {
                surface.matrix.set(1, 0, 0, -1,
                    innerPadding.left,
                    innerPadding.top + innerHeight);
            }
            surface.matrix.inverse(surface.inverseMatrix);
            series.getOverlaySurface().setRect(mainRect);
        }
        me.redraw();
        me.onPlaceWatermark(chartRect[2], chartRect[3]);

        this.resizing--;
        this.resumeThicknessChanged();
    },

    refloatAxes: function () {
        var me = this,
            axes = me.getAxes(),
            axis, axisSurface, axisRect,
            floating, value, alongAxis, matrix,
            size = me.innerElement.getSize(),
            inset = me.getInsetPadding(),
            inner = me.getInnerPadding(),
            width = size.width - inset.left - inset.right,
            height = size.height - inset.top - inset.bottom,
            isHorizontal;

        for (var i = 0; i < axes.length; i++) {
            axis = axes[i];
            floating = axis.getFloating();
            value = floating ? floating.value : null;
            if (value === null) {
                delete axis.floatingAtCoord;
                continue;
            }
            axisSurface = axis.getSurface();
            axisRect = axisSurface.getRect();
            if (!axisRect) {
                continue;
            }
            axisRect = axisRect.slice();
            alongAxis = me.getAxis(floating.alongAxis);
            if (alongAxis) {
                isHorizontal = alongAxis.getAlignment() === 'horizontal';
                if (Ext.isString(value)) {
                    value = alongAxis.getCoordFor(value);
                }
                alongAxis.floatingAxes[axis.getId()] = value;
                matrix = alongAxis.getSprites()[0].attr.matrix;
                if (isHorizontal) {
                    value = value * matrix.getXX() + matrix.getDX();
                    axis.floatingAtCoord = value + inner.left + inner.right;
                } else {
                    value = value * matrix.getYY() + matrix.getDY();
                    axis.floatingAtCoord = value + inner.top + inner.bottom;
                }
            } else {
                isHorizontal = axis.getAlignment() === 'horizontal';
                if (isHorizontal) {
                    axis.floatingAtCoord = value + inner.top + inner.bottom;
                } else {
                    axis.floatingAtCoord = value + inner.left + inner.right;
                }
                value = axisSurface.roundPixel(0.01 * value * (isHorizontal ? height : width));
            }
            switch (axis.getPosition()) {
                case 'top':
                    axisRect[1] = inset.top + inner.top + value - axisRect[3] + 1;
                    break;
                case 'bottom':
                    axisRect[1] = inset.top + inner.top + (alongAxis ? value : height - value);
                    break;
                case 'left':
                    axisRect[0] = inset.left + inner.left + value - axisRect[2] + 1;
                    break;
                case 'right':
                    axisRect[0] = inset.left + inner.left + (alongAxis ? value : width - value) - 1;
                    break;
            }
            axisSurface.setRect(axisRect);
        }
    },

    redraw: function () {
        var me = this,
            series = me.getSeries(),
            axes = me.getAxes(),
            rect = me.getMainRect(),
            innerWidth, innerHeight,
            innerPadding = me.getInnerPadding(),
            sprites, xRange, yRange, isSide, attr, i, j,
            axis, axisX, axisY, range, visibleRange,
            flipXY = me.getFlipXY(),
            sprite, zIndex, zBase = 1000,
            markers, markerCount, markerIndex, markerSprite, markerZIndex;

        if (!rect) {
            return;
        }

        innerWidth = rect[2] - innerPadding.left - innerPadding.right;
        innerHeight = rect[3] - innerPadding.top - innerPadding.bottom;
        for (i = 0; i < series.length; i++) {
            if ((axisX = series[i].getXAxis())) {
                visibleRange = axisX.getVisibleRange();
                xRange = axisX.getRange();
                xRange = [
                    xRange[0] + (xRange[1] - xRange[0]) * visibleRange[0],
                    xRange[0] + (xRange[1] - xRange[0]) * visibleRange[1]
                ];
            } else {
                xRange = series[i].getXRange();
            }

            if ((axisY = series[i].getYAxis())) {
                visibleRange = axisY.getVisibleRange();
                yRange = axisY.getRange();
                yRange = [
                    yRange[0] + (yRange[1] - yRange[0]) * visibleRange[0],
                    yRange[0] + (yRange[1] - yRange[0]) * visibleRange[1]
                ];
            } else {
                yRange = series[i].getYRange();
            }

            attr = {
                visibleMinX: xRange[0],
                visibleMaxX: xRange[1],
                visibleMinY: yRange[0],
                visibleMaxY: yRange[1],
                innerWidth: innerWidth,
                innerHeight: innerHeight,
                flipXY: flipXY
            };

            sprites = series[i].getSprites();
            for (j = 0; j < sprites.length; j++) {

                // All the series now share the same surface, so we must assign
                // the sprites a zIndex that depends on the index of their series.
                sprite = sprites[j];
                zIndex = (sprite.attr.zIndex || 0);
                if (zIndex < zBase) {
                    // Set the sprite's zIndex
                    zIndex += (i+1) * 100 + zBase;
                    sprite.attr.zIndex = zIndex;
                    // Iterate through its marker sprites to do the same.
                    markers = sprite.boundMarkers;
                    if (markers) {
                        markerCount = (markers.items ? markers.items.length : 0);
                        if (markerCount) {
                            for (markerIndex = 0; markerIndex < markerCount; markerIndex++) {
                                markerSprite = markers.items[markerIndex];
                                markerZIndex = (markerSprite.attr.zIndex || 0);
                                if (markerZIndex == Number.MAX_VALUE) {
                                    markerSprite.attr.zIndex = zIndex;
                                } else {
                                    if (markerZIndex < zBase) {
                                        markerSprite.attr.zIndex = zIndex + markerZIndex;
                                    }
                                }
                            }
                        }
                    }
                }

                sprite.setAttributes(attr, true);
            }
        }

        for (i = 0; i < axes.length; i++) {
            axis = axes[i];
            isSide = axis.isSide();
            sprites = axis.getSprites();
            range = axis.getRange();
            visibleRange = axis.getVisibleRange();
            attr = {
                dataMin: range[0],
                dataMax: range[1],
                visibleMin: visibleRange[0],
                visibleMax: visibleRange[1]
            };
            if (isSide) {
                attr.length = innerHeight;
                attr.startGap = innerPadding.bottom;
                attr.endGap = innerPadding.top;
            } else {
                attr.length = innerWidth;
                attr.startGap = innerPadding.left;
                attr.endGap = innerPadding.right;
            }
            for (j = 0; j < sprites.length; j++) {
                sprites[j].setAttributes(attr, true);
            }
        }
        me.renderFrame();
        me.callParent(arguments);
    },

    renderFrame: function () {
        this.refloatAxes();
        this.callParent(arguments);
    },

    onPlaceWatermark: function (width, height) {
        var me = this,
            watermarkElement = me.watermarkElement,
            rect = watermarkElement &&
                  (me.getSurface ? me.getSurface('main').getRect()
                                 : me.getItems().get(0).getRect());

        if (rect) {
            watermarkElement.setStyle({
                right: Math.round(width - (rect[2] + rect[0])) + 'px',
                bottom: Math.round(height - (rect[3] + rect[1])) + 'px'
            });
        }
    }
});

/**
 * @class Ext.chart.grid.CircularGrid
 * @extends Ext.draw.sprite.Circle
 * 
 * Circular Grid sprite. Used by Radar chart to render a series of concentric circles.
 */
Ext.define('Ext.chart.grid.CircularGrid', {
    extend: 'Ext.draw.sprite.Circle',
    alias: 'grid.circular',
    
    inheritableStatics: {
        def: {
            defaults: {
                r: 1,
                strokeStyle: '#DDD'
            }
        }
    }
});

/**
 * @class Ext.chart.grid.RadialGrid
 * @extends Ext.draw.sprite.Path
 * 
 * Radial Grid sprite. Used by Radar chart to render a series of radial lines.
 * Represents the scale of the radar chart on the yField.
 */
Ext.define('Ext.chart.grid.RadialGrid', {
    extend: 'Ext.draw.sprite.Path',
    alias: 'grid.radial',

    inheritableStatics: {
        def: {
            processors: {
                startRadius: 'number',
                endRadius: 'number'
            },

            defaults: {
                startRadius: 0,
                endRadius: 1,
                scalingCenterX: 0,
                scalingCenterY: 0,
                strokeStyle: '#DDD'
            },

            dirtyTriggers: {
                startRadius: 'path,bbox',
                endRadius: 'path,bbox'
            }
        }
    },

    render: function () {
        this.callParent(arguments);
    },

    updatePath: function (path, attr) {
        var startRadius = attr.startRadius,
            endRadius = attr.endRadius;
        path.moveTo(startRadius, 0);
        path.lineTo(endRadius, 0);
    }
});

/**
 * @class Ext.chart.PolarChart
 * @extends Ext.chart.AbstractChart
 * @xtype polar
 *
 * Represent a chart that uses polar coordinates.
 * A polar chart has two axes: an angular axis (which is a circle) and
 * a radial axis (a straight line from the center to the edge of the circle).
 * The angular axis is usually a Category axis while the radial axis is
 * typically numerical. 
 *
 * Pie charts and Radar charts are common examples of Polar charts.
 *
 */
Ext.define('Ext.chart.PolarChart', {

    requires: [
        'Ext.chart.grid.CircularGrid',
        'Ext.chart.grid.RadialGrid'
    ],

    extend: 'Ext.chart.AbstractChart',
    xtype: 'polar',

    config: {
        /**
         * @cfg {Array} center Determines the center of the polar chart.
         * Updated when the chart performs layout.
         */
        center: [0, 0],
        /**
         * @cfg {Number} radius Determines the radius of the polar chart.
         * Updated when the chart performs layout.
         */
        radius: 0,

        /**
         * @cfg {Number} innerPadding The amount of inner padding in pixels.
         * Inner padding is the padding from the outermost angular axis to the series.
         */
        innerPadding: 0
    },

    getDirectionForAxis: function (position) {
        if (position === 'radial') {
            return 'Y';
        } else {
            return 'X';
        }
    },

    applyCenter: function (center, oldCenter) {
        if (oldCenter && center[0] === oldCenter[0] && center[1] === oldCenter[1]) {
            return;
        }
        return [+center[0], +center[1]];
    },

    updateCenter: function (center) {
        var me = this,
            axes = me.getAxes(), axis,
            series = me.getSeries(), seriesItem,
            i, ln;
        for (i = 0, ln = axes.length; i < ln; i++) {
            axis = axes[i];
            axis.setCenter(center);
        }

        for (i = 0, ln = series.length; i < ln; i++) {
            seriesItem = series[i];
            seriesItem.setCenter(center);
        }
    },

    applyInnerPadding: function (padding, oldPadding) {
        return Ext.isNumber(padding) ? padding : oldPadding;
    },

    doSetSurfaceRect: function (surface, rect) {
        var mainRect = this.getMainRect();
        surface.setRect(rect);
        surface.matrix.set(1, 0, 0, 1, mainRect[0] - rect[0], mainRect[1] - rect[1]);
        surface.inverseMatrix.set(1, 0, 0, 1, rect[0] - mainRect[0], rect[1] - mainRect[1]);
    },

    applyAxes: function (newAxes, oldAxes) {
        var me = this,
            firstSeries = Ext.Array.from(me.config.series)[0],
            i, ln, axis, foundAngular;

        if (firstSeries.type === 'radar' && newAxes && newAxes.length) {
            // For compatibility with ExtJS: add a default angular axis if it's missing
            for (i = 0, ln = newAxes.length; i < ln; i++) {
                axis = newAxes[i];
                if (axis.position === 'angular') {
                    foundAngular = true;
                    break;
                }
            }
            if (!foundAngular) {
                newAxes.push({
                    type: 'category',
                    position: 'angular',
                    fields: firstSeries.xField || firstSeries.angleField,
                    style: {
                        estStepSize: 1
                    },
                    grid: true
                });
            }
        }
        return this.callParent(arguments);
    },

    performLayout: function () {
        try {
            this.resizing++;
            this.callParent();
            this.suspendThicknessChanged();
            var me = this,
                chartRect = me.getSurface('chart').getRect(),
                inset = me.getInsetPadding(),
                inner = me.getInnerPadding(),
                shrinkBox = Ext.apply({}, inset), side,
                width = chartRect[2] - inset.left - inset.right,
                height = chartRect[3] - inset.top - inset.bottom,
                mainRect = [inset.left, inset.top, width, height],
                seriesList = me.getSeries(), series,
                innerWidth = width - inner * 2,
                innerHeight = height - inner * 2,
                center = [innerWidth * 0.5 + inner, innerHeight * 0.5 + inner],
                radius = Math.min(innerWidth, innerHeight) * 0.5,
                axes = me.getAxes(), axis, thickness, halfLineWidth,
                angularAxes = [], radialAxes = [],
                seriesRadius = radius - inner,
                i, ln, shrinkRadius, floating, floatingValue,
                gaugeSeries, gaugeRadius;

            me.setMainRect(mainRect);

            me.doSetSurfaceRect(me.getSurface(), mainRect);
            for (i = 0, ln = me.surfaceMap.grid && me.surfaceMap.grid.length; i < ln; i++) {
                me.doSetSurfaceRect(me.surfaceMap.grid[i], chartRect);
            }

            for (i = 0, ln = axes.length; i < ln; i++) {
                axis = axes[i];
                switch (axis.getPosition()) {
                    case 'angular':
                        angularAxes.push(axis);
                        break;
                    case 'radial':
                        radialAxes.push(axis);
                        break;
                }
            }

            for (i = 0, ln = angularAxes.length; i < ln; i++) {
                axis = angularAxes[i];
                floating = axis.getFloating();
                floatingValue = floating ? floating.value : null;
                me.doSetSurfaceRect(axis.getSurface(), chartRect);
                thickness = axis.getThickness();
                for (side in shrinkBox) {
                    shrinkBox[side] += thickness;
                }
                width = chartRect[2] - shrinkBox.left - shrinkBox.right;
                height = chartRect[3] - shrinkBox.top - shrinkBox.bottom;
                shrinkRadius = Math.min(width, height) * 0.5;
                if (i === 0) {
                    seriesRadius = shrinkRadius - inner;
                }
                axis.setMinimum(0);
                axis.setLength(shrinkRadius);
                axis.getSprites();
                halfLineWidth = axis.sprites[0].attr.lineWidth * 0.5;
                for (side in shrinkBox) {
                    shrinkBox[side] += halfLineWidth;
                }
            }

            for (i = 0, ln = radialAxes.length; i < ln; i++) {
                axis = radialAxes[i];
                me.doSetSurfaceRect(axis.getSurface(), mainRect);
                axis.setMinimum(0);
                axis.setLength(seriesRadius);
                axis.getSprites();
            }

            for (i = 0, ln = seriesList.length; i < ln; i++) {
                series = seriesList[i];
                if (series.type === 'gauge' && !gaugeSeries) {
                    gaugeSeries = series;
                } else {
                    series.setRadius(seriesRadius);
                }
                me.doSetSurfaceRect(series.getSurface(), mainRect);
            }

            me.doSetSurfaceRect(me.getSurface('overlay'), chartRect);
            if (gaugeSeries) {
                gaugeSeries.setRect(mainRect);
                gaugeRadius = gaugeSeries.getRadius() - inner;
                me.setRadius(gaugeRadius);
                me.setCenter(gaugeSeries.getCenter());
                gaugeSeries.setRadius(gaugeRadius);
                if (axes.length && axes[0].getPosition() === 'gauge') {
                    axis = axes[0];
                    me.doSetSurfaceRect(axis.getSurface(), chartRect);
                    axis.setTotalAngle(gaugeSeries.getTotalAngle());
                    axis.setLength(gaugeRadius);
                }
            } else {
                me.setRadius(radius);
                me.setCenter(center);
            }
            me.redraw();
        } catch (e) { // catch is required in IE8 (try/finally not supported)
            Ext.log.error(this.$className + ': Unhandled Exception: ', e.description || e.message);
            throw e;
        }
        finally {
            this.resizing--;
            this.resumeThicknessChanged();
        }
    },

    refloatAxes: function () {
        var me = this,
            axes = me.getAxes(),
            mainRect = me.getMainRect(),
            floating, value, alongAxis,
            i, n, axis, radius;

        if (!mainRect) {
            return;
        }
        radius = 0.5 * Math.min(mainRect[2], mainRect[3]);
        for (i = 0, n = axes.length; i < n; i++) {
            axis = axes[i];
            floating = axis.getFloating();
            value = floating ? floating.value : null;
            if (value !== null) {
                alongAxis = me.getAxis(floating.alongAxis);
                if (axis.getPosition() === 'angular') {
                    if (alongAxis) {
                        value = alongAxis.getLength() * value / alongAxis.getRange()[1];
                    } else {
                        value = 0.01 * value * radius;
                    }
                    axis.sprites[0].setAttributes({length: value}, true);
                } else {
                    if (alongAxis) {
                        if (Ext.isString(value)) {
                            value = alongAxis.getCoordFor(value);
                        }
                        value = value / (alongAxis.getRange()[1] + 1) * Math.PI * 2 - Math.PI * 1.5 +
                            axis.getRotation();
                    } else {
                        value = Ext.draw.Draw.rad(value);
                    }
                    axis.sprites[0].setAttributes({baseRotation: value}, true);
                }
            }
        }
    },

    redraw: function () {
        var me = this,
            axes = me.getAxes(), axis,
            series = me.getSeries(), seriesItem,
            i, ln;

        for (i = 0, ln = axes.length; i < ln; i++) {
            axis = axes[i];
            axis.getSprites();
        }

        for (i = 0, ln = series.length; i < ln; i++) {
            seriesItem = series[i];
            seriesItem.getSprites();
        }

        me.renderFrame();
        me.callParent(arguments);
    },

    renderFrame: function () {
        this.refloatAxes();
        this.callParent(arguments);
    }
});

/**
 * @class Ext.chart.SpaceFillingChart
 * @extends Ext.chart.AbstractChart
 *
 * Creates a chart that fills the entire area of the chart.
 * e.g. Gauge, Treemap
 */
Ext.define('Ext.chart.SpaceFillingChart', {

    extend: 'Ext.chart.AbstractChart',
    xtype: 'spacefilling',

    config: {

    },

    performLayout: function () {
        try {
            this.resizing++;
            this.callParent();
            var me = this,
                chartRect = me.getSurface('chart').getRect(),
                padding = me.getInsetPadding(),
                width = chartRect[2] - padding.left - padding.right,
                height = chartRect[3] - padding.top - padding.bottom,
                mainRect = [padding.left, padding.top, width, height],
                seriesList = me.getSeries(), series,
                i, ln;
            me.getSurface().setRect(mainRect);
            me.setMainRect(mainRect);
            for (i = 0, ln = seriesList.length; i < ln; i++) {
                series = seriesList[i];
                series.getSurface().setRect(mainRect);
                if (series.setRect) {
                    series.setRect(mainRect);
                }
                series.getOverlaySurface().setRect(chartRect);
            }
            me.redraw();
        } catch (e) { // catch is required in IE8 (try/finally not supported)
            Ext.log.error(this.$className + ': Unhandled Exception: ', e.description || e.message);
            throw e;
        }
        finally {
            this.resizing--;
        }
    },

    redraw: function () {
        var me = this,
            seriesList = me.getSeries(), series,
            i, ln;

        for (i = 0, ln = seriesList.length; i < ln; i++) {
            series = seriesList[i];
            series.getSprites();
        }

        me.renderFrame();
        me.callParent(arguments);
    }
});

/**
 * @class Ext.chart.axis.Category
 * @extends Ext.chart.axis.Axis
 *
 * A type of axis that displays items in categories. This axis is generally used to
 * display categorical information like names of items, month names, quarters, etc.
 * but no quantitative values. For that other type of information {@link Ext.chart.axis.Numeric Numeric}
 * axis are more suitable.
 *
 * As with other axis you can set the position of the axis and its title. For example:
 *
 *     @example
 *     Ext.create('Ext.Container', {
 *         renderTo: Ext.getBody(),
 *         width: 600,
 *         height: 400,
 *         layout: 'fit',
 *         items: {
 *             xtype: 'cartesian',
 *             innerPadding: '0 40 0 40',
 *             store: {
 *                 fields: ['name', 'data1', 'data2', 'data3', 'data4', 'data5'],
 *                 data: [
 *                     {'name': 'metric one', 'data1': 10, 'data2': 12, 'data3': 14, 'data4': 8, 'data5': 13},
 *                     {'name': 'metric two', 'data1': 7, 'data2': 8, 'data3': 16, 'data4': 10, 'data5': 3},
 *                     {'name': 'metric three', 'data1': 5, 'data2': 2, 'data3': 14, 'data4': 12, 'data5': 7},
 *                     {'name': 'metric four', 'data1': 2, 'data2': 14, 'data3': 6, 'data4': 1, 'data5': 23},
 *                     {'name': 'metric five', 'data1': 27, 'data2': 38, 'data3': 36, 'data4': 13, 'data5': 33}
 *                 ]
 *             },
 *             axes: {
 *                 type: 'category',
 *                 position: 'bottom',
 *                 fields: ['name'],
 *                 title: {
 *                     text: 'Sample Values',
 *                     fontSize: 15
 *                 }
 *             },
 *             series: {
 *                 type: 'area',
 *                 subStyle: {
 *                     fill: ['blue', 'green', 'red']
 *                 },
 *                 xField: 'name',
 *                 yField: ['data1', 'data2', 'data3']
 *             }
 *         }
 *     });
 *
 * In this example with set the category axis to the bottom of the surface, bound the axis to
 * the `name` property and set as title "Sample Values".
 */

Ext.define('Ext.chart.axis.Category', {
    requires: [
        'Ext.chart.axis.layout.CombineDuplicate',
        'Ext.chart.axis.segmenter.Names'
    ],
    extend: 'Ext.chart.axis.Axis',
    alias: 'axis.category',
    type: 'category',

    config: {
        layout: 'combineDuplicate',

        segmenter: 'names'
    }
});

/**
 * @class Ext.chart.axis.Numeric
 * @extends Ext.chart.axis.Axis
 *
 * An axis to handle numeric values. This axis is used for quantitative data as
 * opposed to the category axis. You can set minimum and maximum values to the
 * axis so that the values are bound to that. If no values are set, then the
 * scale will auto-adjust to the values.
 *
 *     @example
 *     Ext.create('Ext.Container', {
 *         renderTo: Ext.getBody(),
 *         width: 600,
 *         height: 400,
 *         layout: 'fit',
 *         items: {
 *             xtype: 'cartesian',
 *             store: {
 *               fields: ['name', 'data1', 'data2', 'data3', 'data4', 'data5'],
 *               data: [
 *                   {'name': 1, 'data1': 10, 'data2': 12, 'data3': 14, 'data4': 8, 'data5': 13},
 *                   {'name': 2, 'data1': 7, 'data2': 8, 'data3': 16, 'data4': 10, 'data5': 3},
 *                   {'name': 3, 'data1': 5, 'data2': 2, 'data3': 14, 'data4': 12, 'data5': 7},
 *                   {'name': 4, 'data1': 2, 'data2': 14, 'data3': 6, 'data4': 1, 'data5': 23},
 *                   {'name': 5, 'data1': 27, 'data2': 38, 'data3': 36, 'data4': 13, 'data5': 33}
 *               ]
 *             },
 *             axes: {
 *                 type: 'numeric',
 *                 position: 'left',
 *                 minimum: 0,
 *                 fields: ['data1', 'data2', 'data3', 'data4', 'data5'],
 *                 title: 'Sample Values',
 *                 grid: {
 *                     odd: {
 *                         opacity: 1,
 *                         fill: '#ddd',
 *                         stroke: '#bbb',
 *                         'lineWidth': 1
 *                     }
 *                 }
 *             },
 *             series: {
 *                 type: 'area',
 *                 subStyle: {
 *                     fill: ['blue', 'green', 'red']
 *                 },
 *                 xField: 'name',
 *                 yField: ['data1', 'data2', 'data3']
 *             }
 *         }
 *     });
 *
 * In this example we create an axis of Numeric type. We set a minimum value so that
 * even if all series have values greater than zero, the grid starts at zero. We bind
 * the axis onto the left part of the surface by setting _position_ to _left_.
 * We bind three different store fields to this axis by setting _fields_ to an array.
 * We set the title of the axis to _Number of Hits_ by using the _title_ property.
 * We use a _grid_ configuration to set odd background rows to a certain style and even rows
 * to be transparent/ignored.
 *
 */
Ext.define('Ext.chart.axis.Numeric', {
    extend: 'Ext.chart.axis.Axis',
    alias: ['axis.numeric', 'axis.radial'],		// For compatibility with ExtJS: add radial
    type: 'numeric',
    requires: ['Ext.chart.axis.layout.Continuous', 'Ext.chart.axis.segmenter.Numeric'],
    config: {
        layout: 'continuous',

        segmenter: 'numeric',

        aggregator: 'double'
    }
});

/**
 * @class Ext.chart.axis.Time
 * @extends Ext.chart.axis.Numeric
 *
 * A type of axis whose units are measured in time values. Use this axis
 * for listing dates that you will want to group or dynamically change.
 * If you just want to display dates as categories then use the
 * Category class for axis instead.
 *
 *     @example
 *     Ext.create('Ext.Container', {
 *         renderTo: Ext.getBody(),
 *         width: 600,
 *         height: 400,
 *         layout: 'fit',
 *         items: {
 *             xtype: 'cartesian',
 *             store: {
 *                 fields: ['time', 'open', 'high', 'low', 'close'],
 *                 data: [
 *                     {'time': new Date('Jan 1 2010').getTime(), 'open': 600, 'high': 614, 'low': 578, 'close': 590},
 *                     {'time': new Date('Jan 2 2010').getTime(), 'open': 590, 'high': 609, 'low': 580, 'close': 580},
 *                     {'time': new Date('Jan 3 2010').getTime(), 'open': 580, 'high': 602, 'low': 578, 'close': 602},
 *                     {'time': new Date('Jan 4 2010').getTime(), 'open': 602, 'high': 614, 'low': 586, 'close': 586}
 *                 ]
 *             },
 *             axes: [{
 *                 type: 'numeric',
 *                 position: 'left',
 *                 fields: ['open', 'high', 'low', 'close'],
 *                 title: {
 *                     text: 'Sample Values',
 *                     fontSize: 15
 *                 },
 *                 grid: true,
 *                 minimum: 560,
 *                 maximum: 640
 *             }, {
 *                 type: 'time',
 *                 position: 'bottom',
 *                 fields: ['time'],
 *                 fromDate: new Date('Dec 31 2009'),
 *                 toDate: new Date('Jan 5 2010'),
 *                 title: {
 *                     text: 'Sample Values',
 *                     fontSize: 15
 *                 },
 *                 style: {
 *                     axisLine: false
 *                 }
 *             }],
 *             series: {
 *                 type: 'candlestick',
 *                 xField: 'time',
 *                 openField: 'open',
 *                 highField: 'high',
 *                 lowField: 'low',
 *                 closeField: 'close',
 *                 style: {
 *                 ohlcType: 'ohlc',
 *                     dropStyle: {
 *                         fill: 'rgb(237, 123, 43)',
 *                         stroke: 'rgb(237, 123, 43)'
 *                     },
 *                     raiseStyle: {
 *                         fill: 'rgb(55, 153, 19)',
 *                         stroke: 'rgb(55, 153, 19)'
 *                     }
 *                 }
 *             }
 *         }
 *     });
 */
Ext.define('Ext.chart.axis.Time', {
    extend: 'Ext.chart.axis.Numeric',
    alias: 'axis.time',
    type: 'time',
    requires: [
        'Ext.chart.axis.layout.Continuous',
        'Ext.chart.axis.segmenter.Time'
    ],
    config: {
        /**
         * @cfg {Boolean} calculateByLabelSize
         * The minimum value drawn by the axis. If not set explicitly, the axis
         * minimum will be calculated automatically.
         */
        calculateByLabelSize: true,

        /**
         * @cfg {String/Boolean} dateFormat
         * Indicates the format the date will be rendered on.
         * For example: 'M d' will render the dates as 'Jan 30', etc.
         */
        dateFormat: null,

        /**
         * @cfg {Date} fromDate The starting date for the time axis.
         */
        fromDate: null,

        /**
         * @cfg {Date} toDate The ending date for the time axis.
         */
        toDate: null,

        /**
         * @cfg {Array} [step=[Ext.Date.DAY, 1]] An array with two components:
         *
         * - The unit of the step (Ext.Date.DAY, Ext.Date.MONTH, etc).
         * - The number of units for the step (1, 2, etc).
         *
         */
        step: [Ext.Date.DAY, 1],

        layout: 'continuous',

        segmenter: 'time',

        aggregator: 'time'
    },

    updateDateFormat: function (format) {
        this.setRenderer(function (date) {
            return Ext.Date.format(new Date(date), format);
        });
    },

    updateFromDate: function (date) {
        this.setMinimum(+date);
    },

    updateToDate: function (date) {
        this.setMaximum(+date);
    },

    getCoordFor: function (value) {
        if (Ext.isString(value)) {
            value = new Date(value);
        }
        return +value;
    }
});

/**
 * @class Ext.chart.interactions.CrossZoom
 * @extends Ext.chart.interactions.Abstract
 *
 * The CrossZoom interaction allows the user to zoom in on a selected area of the chart.
 *
 *     @example
 *     Ext.create('Ext.Container', {
 *         renderTo: Ext.getBody(),
 *         width: 600,
 *         height: 400,
 *         layout: 'fit',
 *         items: {
 *             xtype: 'cartesian',
 *             interactions: 'crosszoom',
 *             store: {
 *               fields: ['name', 'data1', 'data2', 'data3', 'data4', 'data5'],
 *               data: [
 *                   {'name':'metric one', 'data1':10, 'data2':12, 'data3':14, 'data4':8, 'data5':13},
 *                   {'name':'metric two', 'data1':7, 'data2':8, 'data3':16, 'data4':10, 'data5':3},
 *                   {'name':'metric three', 'data1':5, 'data2':2, 'data3':14, 'data4':12, 'data5':7},
 *                   {'name':'metric four', 'data1':2, 'data2':14, 'data3':6, 'data4':1, 'data5':23},
 *                   {'name':'metric five', 'data1':27, 'data2':38, 'data3':36, 'data4':13, 'data5':33}
 *               ]
 *             },
 *             axes: [{
 *                 type: 'numeric',
 *                 position: 'left',
 *                 fields: ['data1'],
 *                 title: {
 *                     text: 'Sample Values',
 *                     fontSize: 15
 *                 },
 *                 grid: true,
 *                 minimum: 0
 *             }, {
 *                 type: 'category',
 *                 position: 'bottom',
 *                 fields: ['name'],
 *                 title: {
 *                     text: 'Sample Values',
 *                     fontSize: 15
 *                 }
 *             }],
 *             series: [{
 *                 type: 'line',
 *                 highlight: {
 *                     size: 7,
 *                     radius: 7
 *                 },
 *                 style: {
 *                     stroke: 'rgb(143,203,203)'
 *                 },
 *                 xField: 'name',
 *                 yField: 'data1',
 *                 marker: {
 *                     type: 'path',
 *                     path: ['M', -2, 0, 0, 2, 2, 0, 0, -2, 'Z'],
 *                     stroke: 'blue',
 *                     lineWidth: 0
 *                 }
 *             }, {
 *                 type: 'line',
 *                 highlight: {
 *                     size: 7,
 *                     radius: 7
 *                 },
 *                 fill: true,
 *                 xField: 'name',
 *                 yField: 'data3',
 *                 marker: {
 *                     type: 'circle',
 *                     radius: 4,
 *                     lineWidth: 0
 *                 }
 *             }]
 *         }
 *     });
 */
Ext.define('Ext.chart.interactions.CrossZoom', {

    extend: 'Ext.chart.interactions.Abstract',

    type: 'crosszoom',
    alias: 'interaction.crosszoom',

    config: {
        /**
         * @cfg {Object/Array} axes
         * Specifies which axes should be made navigable. The config value can take the following formats:
         *
         * - An Object whose keys correspond to the {@link Ext.chart.axis.Axis#position position} of each
         *   axis that should be made navigable. Each key's value can either be an Object with further
         *   configuration options for each axis or simply `true` for a default set of options.
         *       {
         *           type: 'crosszoom',
         *           axes: {
         *               left: {
         *                   maxZoom: 5,
         *                   allowPan: false
         *               },
         *               bottom: true
         *           }
         *       }
         *
         *   If using the full Object form, the following options can be specified for each axis:
         *
         *   - minZoom (Number) A minimum zoom level for the axis. Defaults to `1` which is its natural size.
         *   - maxZoom (Number) A maximum zoom level for the axis. Defaults to `10`.
         *   - startZoom (Number) A starting zoom level for the axis. Defaults to `1`.
         *   - allowZoom (Boolean) Whether zooming is allowed for the axis. Defaults to `true`.
         *   - allowPan (Boolean) Whether panning is allowed for the axis. Defaults to `true`.
         *   - startPan (Boolean) A starting panning offset for the axis. Defaults to `0`.
         *
         * - An Array of strings, each one corresponding to the {@link Ext.chart.axis.Axis#position position}
         *   of an axis that should be made navigable. The default options will be used for each named axis.
         *
         *       {
         *           type: 'crosszoom',
         *           axes: ['left', 'bottom']
         *       }
         *
         * If the `axes` config is not specified, it will default to making all axes navigable with the
         * default axis options.
         */
        axes: true,

        //@inheritdoc
        gestures: {
            dragstart: 'onGestureStart',
            drag: 'onGesture',
            dragend: 'onGestureEnd',
            dblclick: 'onDoubleTap'
        },

        undoButton: {}
    },

    stopAnimationBeforeSync: false,

    zoomAnimationInProgress: false,

    constructor: function () {
        this.callParent(arguments);
        this.zoomHistory = [];
    },

    applyAxes: function (axesConfig) {
        var result = {};
        if (axesConfig === true) {
            return {
                top: {},
                right: {},
                bottom: {},
                left: {}
            };
        } else if (Ext.isArray(axesConfig)) {
            // array of axis names - translate to full object form
            result = {};
            Ext.each(axesConfig, function (axis) {
                result[axis] = {};
            });
        } else if (Ext.isObject(axesConfig)) {
            Ext.iterate(axesConfig, function (key, val) {
                // axis name with `true` value -> translate to object
                if (val === true) {
                    result[key] = {};
                } else if (val !== false) {
                    result[key] = val;
                }
            });
        }
        return result;
    },

    applyUndoButton: function (button, oldButton) {
        var me = this;
        if (button) {
            if (oldButton) {
                oldButton.destroy();
            }
            return Ext.create('Ext.Button', Ext.apply({
                cls: [],
                text: 'Undo Zoom',
                disabled: true,
                handler: function () {
                    me.undoZoom();
                }
            }, button));
        } else if (oldButton) {
            oldButton.destroy();
        }
    },

    getSurface: function () {
        return this.getChart() && this.getChart().getSurface('main');
    },
    
    setSeriesOpacity: function (opacity) {
        var surface = this.getChart() && this.getChart().getSurface('series');
        if (surface) {
            surface.element.setStyle('opacity', opacity);
        }
    },

    onGestureStart: function (e) {
        var me = this,
            chart = me.getChart(),
            surface = me.getSurface(),
            rect = chart.getInnerRect(),
            innerPadding = chart.getInnerPadding(),
            minX = innerPadding.left,
            maxX = minX + rect[2],
            minY = innerPadding.top,
            maxY = minY + rect[3],
            xy = chart.getEventXY(e),
            x = xy[0],
            y = xy[1];

        if (me.zoomAnimationInProgress) {
            return;
        }
        if (x > minX && x < maxX && y > minY && y < maxY) {
            me.gestureEvent = 'drag';
            me.lockEvents(me.gestureEvent);
            me.startX = x;
            me.startY = y;
            me.selectionRect = surface.add({
                type: 'rect',
                globalAlpha: 0.5,
                fillStyle: 'rgba(80,80,140,0.5)',
                strokeStyle: 'rgba(80,80,140,1)',
                lineWidth: 2,
                x: x,
                y: y,
                width: 0,
                height: 0,
                zIndex: 10000
            });
            me.setSeriesOpacity(0.8);
            return false;
        }
    },

    onGesture: function (e) {
        var me = this;
        if (me.zoomAnimationInProgress) {
            return;
        }
        if (me.getLocks()[me.gestureEvent] === me) {
            var chart = me.getChart(),
                surface = me.getSurface(),
                rect = chart.getInnerRect(),
                innerPadding = chart.getInnerPadding(),
                minX = innerPadding.left,
                maxX = minX + rect[2],
                minY = innerPadding.top,
                maxY = minY + rect[3],
                xy = chart.getEventXY(e),
                x = xy[0],
                y = xy[1];

            if (x < minX) {
                x = minX;
            } else if (x > maxX) {
                x = maxX;
            }
            if (y < minY) {
                y = minY;
            } else if (y > maxY) {
                y = maxY;
            }
            me.selectionRect.setAttributes({
                width: x - me.startX,
                height: y - me.startY
            });
            if (Math.abs(me.startX - x) < 11 || Math.abs(me.startY - y) < 11) {
                me.selectionRect.setAttributes({globalAlpha: 0.5});
            } else {
                me.selectionRect.setAttributes({globalAlpha: 1});
            }
            surface.renderFrame();
            return false;
        }
    },

    onGestureEnd: function (e) {
        var me = this;
        if (me.zoomAnimationInProgress) {
            return;
        }
        if (me.getLocks()[me.gestureEvent] === me) {
            var chart = me.getChart(),
                surface = me.getSurface(),
                rect = chart.getInnerRect(),
                innerPadding = chart.getInnerPadding(),
                minX = innerPadding.left,
                maxX = minX + rect[2],
                minY = innerPadding.top,
                maxY = minY + rect[3],
                rectWidth = rect[2],
                rectHeight = rect[3],
                xy = chart.getEventXY(e),
                x = xy[0],
                y = xy[1];

            if (x < minX) {
                x = minX;
            } else if (x > maxX) {
                x = maxX;
            }
            if (y < minY) {
                y = minY;
            } else if (y > maxY) {
                y = maxY;
            }
            if (Math.abs(me.startX - x) < 11 || Math.abs(me.startY - y) < 11) {
                surface.remove(me.selectionRect);
            } else {
                me.zoomBy([
                    Math.min(me.startX, x) / rectWidth,
                    1 - Math.max(me.startY, y) / rectHeight,
                    Math.max(me.startX, x) / rectWidth,
                    1 - Math.min(me.startY, y) / rectHeight
                ]);

                me.selectionRect.setAttributes({
                    x: Math.min(me.startX, x),
                    y: Math.min(me.startY, y),
                    width: Math.abs(me.startX - x),
                    height: Math.abs(me.startY - y)
                });

                me.selectionRect.fx.setConfig(chart.getAnimation() || {duration: 0});
                me.selectionRect.setAttributes({
                    globalAlpha: 0,
                    x: 0,
                    y: 0,
                    width: rectWidth,
                    height: rectHeight
                });

                me.zoomAnimationInProgress = true;

                chart.suspendThicknessChanged();
                me.selectionRect.fx.on('animationend', function () {
                    chart.resumeThicknessChanged();

                    surface.remove(me.selectionRect);
                    me.selectionRect = null;

                    me.zoomAnimationInProgress = false;
                });
            }

            surface.renderFrame();
            me.sync();
            me.unlockEvents(me.gestureEvent);
            me.setSeriesOpacity(1.0);

            if (!me.zoomAnimationInProgress) {
                surface.remove(me.selectionRect);
                me.selectionRect = null;
            }
        }
    },

    zoomBy: function (rect) {
        var me = this,
            axisConfigs = me.getAxes(),
            chart = me.getChart(),
            axes = chart.getAxes(),
            isRtl = chart.getInherited().rtl,
            config,
            zoomMap = {},
            x1, x2;

        if (isRtl) {
            rect = rect.slice();
            x1 =  1 - rect[0];
            x2 =  1 - rect[2];
            rect[0] = Math.min(x1, x2);
            rect[2] = Math.max(x1, x2);
        }
        for (var i = 0; i < axes.length; i++) {
            var axis = axes[i];
            config = axisConfigs[axis.getPosition()];
            if (config && config.allowZoom !== false) {
                var isSide = axis.isSide(),
                    oldRange = axis.getVisibleRange();
                zoomMap[axis.getId()] = oldRange.slice(0);
                if (!isSide) {
                    axis.setVisibleRange([
                        (oldRange[1] - oldRange[0]) * rect[0] + oldRange[0],
                        (oldRange[1] - oldRange[0]) * rect[2] + oldRange[0]
                    ]);
                } else {
                    axis.setVisibleRange([
                        (oldRange[1] - oldRange[0]) * rect[1] + oldRange[0],
                        (oldRange[1] - oldRange[0]) * rect[3] + oldRange[0]
                    ]);
                }
            }
        }

        me.zoomHistory.push(zoomMap);
        me.getUndoButton().setDisabled(false);
    },

    undoZoom: function () {
        var zoomMap = this.zoomHistory.pop(),
            axes = this.getChart().getAxes();
        if (zoomMap) {
            for (var i = 0; i < axes.length; i++) {
                var axis = axes[i];
                if (zoomMap[axis.getId()]) {
                    axis.setVisibleRange(zoomMap[axis.getId()]);
                }
            }
        }
        this.getUndoButton().setDisabled(this.zoomHistory.length === 0);
        this.sync();
    },

    onDoubleTap: function (e) {
        this.undoZoom();
    }
});

/**
 * The Crosshair interaction allows the user to get precise values for a specific point on the chart.
 * The values are obtained by single-touch dragging on the chart.
 *
 *     @example
 *     Ext.create('Ext.Container', {
 *         renderTo: Ext.getBody(),
 *         width: 600,
 *         height: 400,
 *         layout: 'fit',
 *         items: {
 *             xtype: 'cartesian',
 *             innerPadding: 20,
 *             interactions: {
 *                 type: 'crosshair',
 *                 axes: {
 *                     left: {
 *                         label: {
 *                             fillStyle: 'white'
 *                         },
 *                         rect: {
 *                             fillStyle: 'brown',
 *                             radius: 6
 *                         }
 *                     },
 *                     bottom: {
 *                         label: {
 *                             fontSize: '14px',
 *                             fontWeight: 'bold'
 *                         }
 *                     }
 *                 },
 *                 lines: {
 *                     horizontal: {
 *                         strokeStyle: 'brown',
 *                         lineWidth: 2,
 *                         lineDash: [20, 2, 2, 2, 2, 2, 2, 2]
 *                     }
 *                 }
 *             },
 *             store: {
 *                 fields: ['name', 'data'],
 *                 data: [
 *                     {name: 'apple', data: 300},
 *                     {name: 'orange', data: 900},
 *                     {name: 'banana', data: 800},
 *                     {name: 'pear', data: 400},
 *                     {name: 'grape', data: 500}
 *                 ]
 *             },
 *             axes: [{
 *                 type: 'numeric',
 *                 position: 'left',
 *                 fields: ['data'],
 *                 title: {
 *                     text: 'Value',
 *                     fontSize: 15
 *                 },
 *                 grid: true,
 *                 label: {
 *                     rotationRads: -Math.PI / 4
 *                 }
 *             }, {
 *                 type: 'category',
 *                 position: 'bottom',
 *                 fields: ['name'],
 *                 title: {
 *                     text: 'Category',
 *                     fontSize: 15
 *                 }
 *             }],
 *             series: {
 *                 type: 'line',
 *                 style: {
 *                     strokeStyle: 'black'
 *                 },
 *                 xField: 'name',
 *                 yField: 'data',
 *                 marker: {
 *                     type: 'circle',
 *                     radius: 5,
 *                     fillStyle: 'lightblue'
 *                 }
 *             }
 *         }
 *     });
 */

Ext.define('Ext.chart.interactions.Crosshair', {

    extend: 'Ext.chart.interactions.Abstract',
    requires: [
        'Ext.chart.grid.HorizontalGrid',
        'Ext.chart.grid.VerticalGrid',
        'Ext.chart.CartesianChart',
        'Ext.chart.axis.layout.Discrete'
    ],

    type: 'crosshair',
    alias: 'interaction.crosshair',

    config: {
        /**
         * @cfg {Object} axes
         * Specifies label text and label rect configs on per axis basis or as a single config for all axes.
         *
         *     {
         *         type: 'crosshair',
         *         axes: {
         *             label: { fillStyle: 'white' },
         *             rect: { fillStyle: 'maroon'}
         *         }
         *     }
         *
         * In case per axis configuration is used, an object with keys corresponding
         * to the {@link Ext.chart.axis.Axis#position position} must be provided.
         *
         *     {
         *         type: 'crosshair',
         *         axes: {
         *             left: {
         *                 label: { fillStyle: 'white' },
         *                 rect: {
         *                     fillStyle: 'maroon',
         *                     radius: 4
         *                 }
         *             },
         *             bottom: {
         *                 label: {
         *                     fontSize: '14px',
         *                     fontWeight: 'bold'
         *                 },
         *                 rect: { fillStyle: 'white' }
         *             }
         *         }
         *
         * If the `axes` config is not specified, the following defaults will be used:
         * - `label` will use values from the {@link Ext.chart.axis.Axis#label label} config.
         * - `rect` will use the 'white' fillStyle.
         */
        axes: {
            top: {label: {}, rect: {}},
            right: {label: {}, rect: {}},
            bottom: {label: {}, rect: {}},
            left: {label: {}, rect: {}}
        },

        /**
         * @cfg {Object} lines
         * Specifies attributes of horizontal and vertical lines that make up the crosshair.
         * If this config is missing, black dashed lines will be used.
         *
         *     {
         *         horizontal: {
         *             strokeStyle: 'red',
         *             lineDash: [] // solid line
         *         },
         *         vertical: {
         *             lineWidth: 2,
         *             lineDash: [15, 5, 5, 5]
         *         }
         *     }
         */
        lines: {
            horizontal: {
                strokeStyle: 'black',
                lineDash: [5, 5]
            },
            vertical: {
                strokeStyle: 'black',
                lineDash: [5, 5]
            }
        },

        /**
         * @cfg {String} gesture
         * Specifies which gesture should be used for starting/maintaining/ending the interaction.
         */
        gesture: 'drag'
    },

    applyAxes: function (axesConfig, oldAxesConfig) {
        return Ext.merge(oldAxesConfig || {}, axesConfig);
    },

    applyLines: function (linesConfig, oldLinesConfig) {
        return Ext.merge(oldLinesConfig || {}, linesConfig);
    },

    updateChart: function (chart) {
        if (!(chart instanceof Ext.chart.CartesianChart)) {
            throw 'Crosshair interaction can only be used on cartesian charts.';
        }
        this.callParent(arguments);
    },

    getGestures: function () {
        var me = this,
            gestures = {};
        gestures[me.getGesture()] = 'onGesture';
        gestures[me.getGesture() + 'start'] = 'onGestureStart';
        gestures[me.getGesture() + 'end'] = 'onGestureEnd';
        return gestures;
    },

    onGestureStart: function (e) {
        var me = this,
            chart = me.getChart(),
            axesTheme = chart.getTheme().getAxis(), axisTheme,
            surface = chart.getSurface('overlay'),
            rect = chart.getInnerRect(),
            chartWidth = rect[2],
            chartHeight = rect[3],
            xy = chart.getEventXY(e),
            x = xy[0],
            y = xy[1],
            axes = chart.getAxes(),
            axesConfig = me.getAxes(),
            linesConfig = me.getLines(),
            axis, axisSurface, axisRect, axisWidth, axisHeight, axisPosition, axisAlignment,
            axisLabel, axisLabelConfig, crosshairLabelConfig, tickPadding,
            axisSprite, attr, axisThickness, lineWidth, halfLineWidth,
            title, titleBBox, titlePadding,
            horizontalLineCfg, verticalLineCfg,
            i;

        if (x > 0 && x < chartWidth && y > 0 && y < chartHeight) {
            me.lockEvents(me.getGesture());
            horizontalLineCfg = Ext.apply({
                xclass: 'Ext.chart.grid.HorizontalGrid',
                x: 0,
                y: y,
                width: chartWidth
            }, linesConfig.horizontal);
            verticalLineCfg = Ext.apply({
                xclass: 'Ext.chart.grid.VerticalGrid',
                x: x,
                y: 0,
                height: chartHeight
            }, linesConfig.vertical);
            me.axesLabels = me.axesLabels || {};
            for (i = 0; i < axes.length; i++) {
                axis = axes[i];
                axisSurface = axis.getSurface();
                axisRect = axisSurface.getRect();
                axisSprite = axis.getSprites()[0];
                axisWidth = axisRect[2];
                axisHeight = axisRect[3];
                axisPosition = axis.getPosition();
                axisAlignment = axis.getAlignment();
                title = axis.getTitle(),
                titleBBox = title && title.attr.text !== '' && title.getBBox(),
                attr = axisSprite.attr;
                axisThickness = axisSprite.thickness;
                lineWidth = attr.axisLine ? attr.lineWidth : 0;
                halfLineWidth = lineWidth / 2;
                tickPadding = Math.max(attr.majorTickSize, attr.minorTickSize) + lineWidth;

                axisLabel = me.axesLabels[axisPosition] = axisSurface.add({type: 'composite'});

                axisLabel.labelRect = axisLabel.add(Ext.apply({
                    type: 'rect',
                    fillStyle: 'white',
                    x: axisPosition === 'right' ? lineWidth : 0,
                    y: axisPosition === 'bottom' ? lineWidth : 0,
                    width: axisWidth - lineWidth - (axisAlignment === 'vertical' && titleBBox ? titleBBox.width : 0),
                    height: axisHeight - lineWidth - (axisAlignment === 'horizontal' && titleBBox ? titleBBox.height : 0),
                    translationX: axisPosition === 'left' && titleBBox ? titleBBox.width : 0,
                    translationY: axisPosition === 'top' && titleBBox ? titleBBox.height : 0
                }, axesConfig.rect || axesConfig[axisPosition].rect));

                if (axisAlignment === 'vertical' && !verticalLineCfg.strokeStyle) {
                    verticalLineCfg.strokeStyle = attr.strokeStyle;
                }
                if (axisAlignment === 'horizontal' && !horizontalLineCfg.strokeStyle) {
                    horizontalLineCfg.strokeStyle = attr.strokeStyle;
                }

                axisTheme = Ext.merge({}, axesTheme.defaults, axesTheme[axisPosition]);
                axisLabelConfig = Ext.apply({}, axis.config.label, axisTheme.label);
                crosshairLabelConfig = axesConfig.label || axesConfig[axisPosition].label;
                axisLabel.labelText = axisLabel.add(Ext.apply(axisLabelConfig, crosshairLabelConfig, {
                    type: 'text',
                    x: (function () {
                        switch (axisPosition) {
                            case 'left':
                                titlePadding = titleBBox ? titleBBox.x + titleBBox.width : 0;
                                return titlePadding + (axisWidth - titlePadding - tickPadding) / 2 - halfLineWidth;
                            case 'right':
                                titlePadding = titleBBox ? axisWidth - titleBBox.x : 0;
                                return tickPadding + (axisWidth - tickPadding - titlePadding) / 2 + halfLineWidth;
                            default:
                                return 0;
                        }
                    })(),
                    y: (function () {
                        switch (axisPosition) {
                            case 'top':
                                titlePadding = titleBBox ? titleBBox.y + titleBBox.height: 0;
                                return titlePadding + (axisHeight - titlePadding - tickPadding) / 2 - halfLineWidth;
                            case 'bottom':
                                titlePadding = titleBBox ? axisHeight - titleBBox.y : 0;
                                return tickPadding + (axisHeight - tickPadding - titlePadding) / 2 + halfLineWidth;
                            default:
                                return 0;
                        }
                    })()
                }));
            }
            me.horizontalLine = surface.add(horizontalLineCfg);
            me.verticalLine = surface.add(verticalLineCfg);
            return false;
        }

    },

    onGesture: function (e) {
        var me = this;
        if (me.getLocks()[me.getGesture()] !== me) {
            return;
        }
        var chart = me.getChart(),
            surface = chart.getSurface('overlay'),
            rect = Ext.Array.slice(chart.getInnerRect()),
            padding = chart.getInnerPadding(),
            px = padding.left,
            py = padding.top,
            chartWidth = rect[2],
            chartHeight = rect[3],
            xy = chart.getEventXY(e),
            x = xy[0],
            y = xy[1],
            axes = chart.getAxes(),
            axis, axisPosition, axisAlignment, axisSurface, axisSprite, axisMatrix,
            axisLayoutContext, axisSegmenter,
            axisLabel, labelBBox, textPadding,
            xx, yy, dx, dy,
            xValue, yValue,
            text,
            i;

        if (x < 0) {
            x = 0;
        } else if (x > chartWidth) {
            x = chartWidth;
        }
        if (y < 0) {
            y = 0;
        } else if (y > chartHeight) {
            y = chartHeight;
        }
        x += px;
        y += py;

        for (i = 0; i < axes.length; i++) {
            axis = axes[i];
            axisPosition = axis.getPosition();
            axisAlignment = axis.getAlignment();
            axisSurface = axis.getSurface();
            axisSprite = axis.getSprites()[0];
            axisMatrix = axisSprite.attr.matrix;
            textPadding = axisSprite.attr.textPadding * 2;
            axisLabel = me.axesLabels[axisPosition];
            axisLayoutContext = axisSprite.getLayoutContext();
            axisSegmenter = axis.getSegmenter();

            if (axisLabel) {
                if (axisAlignment === 'vertical') {
                    yy = axisMatrix.getYY();
                    dy = axisMatrix.getDY();
                    yValue = (y - dy - py) / yy;
                    if (axis.getLayout() instanceof Ext.chart.axis.layout.Discrete) {
                        y = Math.round(yValue) * yy + dy + py;
                        yValue = axisSegmenter.from(Math.round(yValue));
                        yValue = axisSprite.attr.data[yValue];
                    } else {
                        yValue = axisSegmenter.from(yValue);
                    }
                    text = axisSegmenter.renderer(yValue, axisLayoutContext);

                    axisLabel.setAttributes({translationY: y - py});
                    axisLabel.labelText.setAttributes({text: text});
                    labelBBox = axisLabel.labelText.getBBox();
                    axisLabel.labelRect.setAttributes({
                        height: labelBBox.height + textPadding,
                        y: -(labelBBox.height + textPadding) / 2
                    });
                    axisSurface.renderFrame();
                } else {
                    xx = axisMatrix.getXX();
                    dx = axisMatrix.getDX();
                    xValue = (x - dx - px) / xx;
                    if (axis.getLayout() instanceof Ext.chart.axis.layout.Discrete) {
                        x = Math.round(xValue) * xx + dx + px;
                        xValue = axisSegmenter.from(Math.round(xValue));
                        xValue = axisSprite.attr.data[xValue];
                    } else {
                        xValue = axisSegmenter.from(xValue);
                    }
                    text = axisSegmenter.renderer(xValue, axisLayoutContext);

                    axisLabel.setAttributes({translationX: x - px});
                    axisLabel.labelText.setAttributes({text: text});
                    labelBBox = axisLabel.labelText.getBBox();
                    axisLabel.labelRect.setAttributes({
                        width: labelBBox.width + textPadding,
                        x: -(labelBBox.width + textPadding) / 2
                    });
                    axisSurface.renderFrame();
                }
            }
        }
        me.horizontalLine.setAttributes({y: y, strokeStyle: axisSprite.attr.strokeStyle});
        me.verticalLine.setAttributes({x: x, strokeStyle: axisSprite.attr.strokeStyle});
        surface.renderFrame();
        return false;
    },

    onGestureEnd: function (e) {
        var me = this,
            chart = me.getChart(),
            surface =  chart.getSurface('overlay'),
            axes = chart.getAxes(),
            axis, axisPosition, axisSurface, axisLabel,
            i;

        surface.remove(me.verticalLine);
        surface.remove(me.horizontalLine);

        for (i = 0; i < axes.length; i++) {
            axis = axes[i];
            axisPosition = axis.getPosition();
            axisSurface = axis.getSurface();
            axisLabel = me.axesLabels[axisPosition];
            if (axisLabel) {
                delete me.axesLabels[axisPosition];
                axisSurface.remove(axisLabel);
            }
            axisSurface.renderFrame();
        }

        surface.renderFrame();
        me.unlockEvents(me.getGesture());
    }

});

/**
 * @class Ext.chart.interactions.ItemHighlight
 * @extends Ext.chart.interactions.Abstract
 *
 * The ItemHighlight interaction allows the user to highlight series items in the chart.
 */
Ext.define('Ext.chart.interactions.ItemHighlight', {

    extend: 'Ext.chart.interactions.Abstract',

    type: 'itemhighlight',
    alias: 'interaction.itemhighlight',

    config: {
        //@inheritdoc
        gestures: {
            tap: 'onHighlightGesture',
            mousemove: 'onMouseMoveGesture',
            mouseenter: 'onMouseEnterGesture',
            mouseleave: 'onMouseLeaveGesture',
            mousedown: 'onMouseDownGesture',
            mouseup: 'onMouseUpGesture'
        }
    },

    highlightItem: null,

    onMouseMoveGesture: function (e) {
        var me = this,
            item, tooltip, chart;

        if (me.isDragging) {
            if (me.tipItem) {
                me.tipItem.series.hideTip(me.tipItem);
                me.tipItem = null;
            }
        } else if (!me.highlightItem) {
            item = me.getItemForEvent(e);
            chart = me.getChart();
            if (item !== chart.getHighlightItem()) {
                chart.setHighlightItem(item);
                me.sync();
            }

            if (this.isMousePointer) {
                if ( me.tipItem && (!item || me.tipItem.field !== item.field || me.tipItem.record !== item.record) ) {
                    me.tipItem.series.hideTip(me.tipItem);
                    me.tipItem = null;
                }
                if (item && (tooltip = item.series.getTooltip())) {
                    if (tooltip.trackMouse || !me.tipItem) {
                        item.series.showTip(item, e.getXY());
                    }
                    me.tipItem = item;
                }
            }
            return false;
        }
    },

    showTip: function (e, item) {
        item.series.showTip(item, e.getXY());
        this.tipItem = item;
    },

    onMouseEnterGesture: function () {
        this.isMousePointer = true;
    },

    onMouseLeaveGesture: function () {
        this.isMousePointer = false;
    },

    onMouseDownGesture: function () {
        this.isDragging = true;
    },

    onMouseUpGesture: function () {
        this.isDragging = false;
    },

    onHighlightGesture: function (e) {
        // A click/tap on an item makes its highlight sticky. It requires another click/tap to unhighlight.
        if (this.isMousePointer) {
            return;
        }
        var me = this,
            item = me.getItemForEvent(e);
        if (me.highlightItem && item && (me.highlightItem.index === item.index)) {
            item = null;
        }
        me.highlightItem = item;
        me.getChart().setHighlightItem(item);
    }
});

/**
 * The PanZoom interaction allows the user to navigate the data for one or more chart
 * axes by panning and/or zooming. Navigation can be limited to particular axes. Zooming is
 * performed by pinching on the chart or axis area; panning is performed by single-touch dragging.
 *
 * For devices which do not support multiple-touch events, zooming can not be done via pinch gestures; in this case the
 * interaction will allow the user to perform both zooming and panning using the same single-touch drag gesture.
 * {@link #modeToggleButton} provides a button to indicate and toggle between two modes.
 *
 *     @example
 *     Ext.create('Ext.Container', {
 *         renderTo: Ext.getBody(),
 *         width: 600,
 *         height: 400,
 *         layout: 'fit',
 *         items: {
 *             xtype: 'cartesian',
 *             interactions: [{
 *                 type: 'panzoom',
 *                 zoomOnPanGesture: true
 *             }],
 *             store: {
 *               fields: ['name', 'data1', 'data2', 'data3', 'data4', 'data5'],
 *               data: [
 *                   {'name':'metric one', 'data1':10, 'data2':12, 'data3':14, 'data4':8, 'data5':13},
 *                   {'name':'metric two', 'data1':7, 'data2':8, 'data3':16, 'data4':10, 'data5':3},
 *                   {'name':'metric three', 'data1':5, 'data2':2, 'data3':14, 'data4':12, 'data5':7},
 *                   {'name':'metric four', 'data1':2, 'data2':14, 'data3':6, 'data4':1, 'data5':23},
 *                   {'name':'metric five', 'data1':27, 'data2':38, 'data3':36, 'data4':13, 'data5':33}
 *               ]
 *             },
 *             axes: [{
 *                 type: 'numeric',
 *                 position: 'left',
 *                 fields: ['data1'],
 *                 title: {
 *                     text: 'Sample Values',
 *                     fontSize: 15
 *                 },
 *                 grid: true,
 *                 minimum: 0
 *             }, {
 *                 type: 'category',
 *                 position: 'bottom',
 *                 fields: ['name'],
 *                 title: {
 *                     text: 'Sample Values',
 *                     fontSize: 15
 *                 }
 *             }],
 *             series: [{
 *                 type: 'line',
 *                 highlight: {
 *                     size: 7,
 *                     radius: 7
 *                 },
 *                 style: {
 *                     stroke: 'rgb(143,203,203)'
 *                 },
 *                 xField: 'name',
 *                 yField: 'data1',
 *                 marker: {
 *                     type: 'path',
 *                     path: ['M', -2, 0, 0, 2, 2, 0, 0, -2, 'Z'],
 *                     stroke: 'blue',
 *                     lineWidth: 0
 *                 }
 *             }, {
 *                 type: 'line',
 *                 highlight: {
 *                     size: 7,
 *                     radius: 7
 *                 },
 *                 fill: true,
 *                 xField: 'name',
 *                 yField: 'data3',
 *                 marker: {
 *                     type: 'circle',
 *                     radius: 4,
 *                     lineWidth: 0
 *                 }
 *             }]
 *         }
 *     });
 *
 * The configuration object for the `panzoom` interaction type should specify which axes
 * will be made navigable via the `axes` config. See the {@link #axes} config documentation
 * for details on the allowed formats. If the `axes` config is not specified, it will default
 * to making all axes navigable with the default axis options.
 *
 */
Ext.define('Ext.chart.interactions.PanZoom', {

    extend: 'Ext.chart.interactions.Abstract',

    type: 'panzoom',
    alias: 'interaction.panzoom',
    requires: [
        'Ext.draw.Animator'
    ],

    config: {

        /**
         * @cfg {Object/Array} axes
         * Specifies which axes should be made navigable. The config value can take the following formats:
         *
         * - An Object with keys corresponding to the {@link Ext.chart.axis.Axis#position position} of each
         *   axis that should be made navigable. Each key's value can either be an Object with further
         *   configuration options for each axis or simply `true` for a default set of options.
         *
         *       {
         *           type: 'panzoom',
         *           axes: {
         *               left: {
         *                   maxZoom: 5,
         *                   allowPan: false
         *               },
         *               bottom: true
         *           }
         *       }
         *
         *   If using the full Object form, the following options can be specified for each axis:
         *
         *   - minZoom (Number) A minimum zoom level for the axis. Defaults to `1` which is its natural size.
         *   - maxZoom (Number) A maximum zoom level for the axis. Defaults to `10`.
         *   - startZoom (Number) A starting zoom level for the axis. Defaults to `1`.
         *   - allowZoom (Boolean) Whether zooming is allowed for the axis. Defaults to `true`.
         *   - allowPan (Boolean) Whether panning is allowed for the axis. Defaults to `true`.
         *   - startPan (Boolean) A starting panning offset for the axis. Defaults to `0`.
         *
         * - An Array of strings, each one corresponding to the {@link Ext.chart.axis.Axis#position position}
         *   of an axis that should be made navigable. The default options will be used for each named axis.
         *
         *       {
         *           type: 'panzoom',
         *           axes: ['left', 'bottom']
         *       }
         *
         * If the `axes` config is not specified, it will default to making all axes navigable with the
         * default axis options.
         */
        axes: {
            top: {},
            right: {},
            bottom: {},
            left: {}
        },

        minZoom: null,

        maxZoom: null,

        /**
         * @cfg {Boolean} showOverflowArrows
         * If `true`, arrows will be conditionally shown at either end of each axis to indicate that the
         * axis is overflowing and can therefore be panned in that direction. Set this to `false` to
         * prevent the arrows from being displayed.
         */
        showOverflowArrows: true,

        /**
         * @cfg {Object} overflowArrowOptions
         * A set of optional overrides for the overflow arrow sprites' options. Only relevant when
         * {@link #showOverflowArrows} is `true`.
         */

        /**
         * @cfg {String} panGesture
         * Defines the gesture that initiates panning.
         * @private
         */
        panGesture: 'drag',

        /**
         * @cfg {String} zoomGesture
         * Defines the gesture that initiates zooming.
         * @private
         */
        zoomGesture: 'pinch',

        /**
         * @cfg {Boolean} zoomOnPanGesture
         * If `true`, the pan gesture will zoom the chart. Ignored on touch devices.
         */
        zoomOnPanGesture: false,

        modeToggleButton: {
            xtype: 'segmentedbutton',
            width: 200,
            defaults: { ui: 'default-toolbar' },
            items: [
                {
                    text: 'Pan'
                },
                {
                    text: 'Zoom'
                }
            ],
            cls: Ext.baseCSSPrefix + 'panzoom-toggle'
        },

        hideLabelInGesture: false // Ext.os.is.Android
    },

    stopAnimationBeforeSync: true,

    applyAxes: function (axesConfig, oldAxesConfig) {
        return Ext.merge(oldAxesConfig || {}, axesConfig);
    },

    applyZoomOnPanGesture: function (zoomOnPanGesture) {
        this.getChart();
        if (this.isMultiTouch()) {
            return false;
        }
        return zoomOnPanGesture;
    },

    updateZoomOnPanGesture: function (zoomOnPanGesture) {
        var button = this.getModeToggleButton();
        if (!this.isMultiTouch()) {
            button.show();
            if (zoomOnPanGesture) {
                button.setValue(1);
            } else {
                button.setValue(0);
            }
        } else {
            button.hide();
        }
    },

    toggleMode: function () {
        var me = this;
        if (!me.isMultiTouch()) {
            me.setZoomOnPanGesture(!me.getZoomOnPanGesture());
        }
    },

    applyModeToggleButton: function (button, oldButton) {
        var me = this,
            result = Ext.factory(button, 'Ext.button.Segmented', oldButton);
        if (result && !oldButton) {
            result.addListener('toggle', function (segmentedButton) {
                me.setZoomOnPanGesture(segmentedButton.getValue() === 1);
            });
        }
        return result;
    },

    getGestures: function () {
        var me = this,
            gestures = {},
            pan = me.getPanGesture(),
            zoom = me.getZoomGesture(),
            isTouch = Ext.supports.Touch;

        gestures[zoom] = 'onZoomGestureMove';
        gestures[zoom + 'start'] = 'onZoomGestureStart';
        gestures[zoom + 'end'] = 'onZoomGestureEnd';
        gestures[pan] = 'onPanGestureMove';
        gestures[pan + 'start'] = 'onPanGestureStart';
        gestures[pan + 'end'] = 'onPanGestureEnd';
        gestures.doubletap = 'onDoubleTap';
        return gestures;
    },

    onDoubleTap: function (e) {
        var me = this,
            chart = me.getChart(),
            axes = chart.getAxes(),
            axis, i, ln;

        for (i = 0, ln = axes.length; i < ln; i++) {
            axis = axes[i];
            axis.setVisibleRange([0, 1]);
        }
        chart.redraw();
    },

    onPanGestureStart: function (e) {
        if (!e || !e.touches || e.touches.length < 2) { //Limit drags to single touch
            var me = this,
                rect = me.getChart().getInnerRect(),
                xy = me.getChart().element.getXY();
            me.startX = e.getX() - xy[0] - rect[0];
            me.startY = e.getY() - xy[1] - rect[1];
            me.oldVisibleRanges = null;
            me.hideLabels();
            me.getChart().suspendThicknessChanged();
            me.lockEvents(me.getPanGesture());
            return false;
        }
    },

    onPanGestureMove: function (e) {
        var me = this;
        if (me.getLocks()[me.getPanGesture()] === me) { // Limit drags to single touch.
            var rect = me.getChart().getInnerRect(),
                xy = me.getChart().element.getXY();
            if (me.getZoomOnPanGesture()) {
                me.transformAxesBy(me.getZoomableAxes(e), 0, 0, (e.getX() - xy[0] - rect[0]) / me.startX, me.startY / (e.getY() - xy[1] - rect[1]));
            } else {
                me.transformAxesBy(me.getPannableAxes(e), e.getX() - xy[0] - rect[0] - me.startX, e.getY() - xy[1] - rect[1] - me.startY, 1, 1);
            }
            me.sync();
            return false;
        }
    },

    onPanGestureEnd: function (e) {
        var me = this,
            pan = me.getPanGesture();

        if (me.getLocks()[pan] === me) {
            me.getChart().resumeThicknessChanged();
            me.showLabels();
            me.sync();
            me.unlockEvents(pan);
            return false;
        }
    },

    onZoomGestureStart: function (e) {
        if (e.touches && e.touches.length === 2) {
            var me = this,
                xy = me.getChart().element.getXY(),
                rect = me.getChart().getInnerRect(),
                x = xy[0] + rect[0],
                y = xy[1] + rect[1],
                newPoints = [e.touches[0].point.x - x, e.touches[0].point.y - y, e.touches[1].point.x - x, e.touches[1].point.y - y],
                xDistance = Math.max(44, Math.abs(newPoints[2] - newPoints[0])),
                yDistance = Math.max(44, Math.abs(newPoints[3] - newPoints[1]));
            me.getChart().suspendThicknessChanged();
            me.lastZoomDistances = [xDistance, yDistance];
            me.lastPoints = newPoints;
            me.oldVisibleRanges = null;
            me.hideLabels();
            me.lockEvents(me.getZoomGesture());
            return false;
        }
    },

    onZoomGestureMove: function (e) {
        var me = this;
        if (me.getLocks()[me.getZoomGesture()] === me) {
            var rect = me.getChart().getInnerRect(),
                xy = me.getChart().element.getXY(),
                x = xy[0] + rect[0],
                y = xy[1] + rect[1],
                abs = Math.abs,
                lastPoints = me.lastPoints,
                newPoints = [e.touches[0].point.x - x, e.touches[0].point.y - y, e.touches[1].point.x - x, e.touches[1].point.y - y],
                xDistance = Math.max(44, abs(newPoints[2] - newPoints[0])),
                yDistance = Math.max(44, abs(newPoints[3] - newPoints[1])),
                lastDistances = this.lastZoomDistances || [xDistance, yDistance],
                zoomX = xDistance / lastDistances[0],
                zoomY = yDistance / lastDistances[1];

            me.transformAxesBy(me.getZoomableAxes(e),
                rect[2] * (zoomX - 1) / 2 + newPoints[2] - lastPoints[2] * zoomX,
                rect[3] * (zoomY - 1) / 2 + newPoints[3] - lastPoints[3] * zoomY,
                zoomX,
                zoomY);
            me.sync();
            return false;
        }
    },

    onZoomGestureEnd: function (e) {
        var me = this,
            zoom = me.getZoomGesture();

        if (me.getLocks()[zoom] === me) {
            me.getChart().resumeThicknessChanged();
            me.showLabels();
            me.sync();
            me.unlockEvents(zoom);
            return false;
        }
    },

    hideLabels: function () {
        if (this.getHideLabelInGesture()) {
            this.eachInteractiveAxes(function (axis) {
                axis.hideLabels();
            });
        }
    },

    showLabels: function () {
        if (this.getHideLabelInGesture()) {
            this.eachInteractiveAxes(function (axis) {
                axis.showLabels();
            });
        }
    },

    isEventOnAxis: function (e, axis) {
        // TODO: right now this uses the current event position but really we want to only
        // use the gesture's start event. Pinch does not give that to us though.
        var rect = axis.getSurface().getRect();
        return rect[0] <= e.getX() && e.getX() <= rect[0] + rect[2] && rect[1] <= e.getY() && e.getY() <= rect[1] + rect[3];
    },

    getPannableAxes: function (e) {
        var me = this,
            axisConfigs = me.getAxes(),
            axes = me.getChart().getAxes(),
            i, ln = axes.length,
            result = [], isEventOnAxis = false,
            config;

        if (e) {
            for (i = 0; i < ln; i++) {
                if (this.isEventOnAxis(e, axes[i])) {
                    isEventOnAxis = true;
                    break;
                }
            }
        }

        for (i = 0; i < ln; i++) {
            config = axisConfigs[axes[i].getPosition()];
            if (config && config.allowPan !== false && (!isEventOnAxis || this.isEventOnAxis(e, axes[i]))) {
                result.push(axes[i]);
            }
        }
        return result;
    },

    getZoomableAxes: function (e) {
        var me = this,
            axisConfigs = me.getAxes(),
            axes = me.getChart().getAxes(),
            result = [],
            i, ln = axes.length, axis,
            isEventOnAxis = false, config;

        if (e) {
            for (i = 0; i < ln; i++) {
                if (this.isEventOnAxis(e, axes[i])) {
                    isEventOnAxis = true;
                    break;
                }
            }
        }

        for (i = 0; i < ln; i++) {
            axis = axes[i];
            config = axisConfigs[axis.getPosition()];
            if (config && config.allowZoom !== false && (!isEventOnAxis || this.isEventOnAxis(e, axis))) {
                result.push(axis);
            }
        }
        return result;
    },

    eachInteractiveAxes: function (fn) {
        var me = this,
            axisConfigs = me.getAxes(),
            axes = me.getChart().getAxes();
        for (var i = 0; i < axes.length; i++) {
            if (axisConfigs[axes[i].getPosition()]) {
                if (false === fn.call(this, axes[i])) {
                    return;
                }
            }
        }
    },

    transformAxesBy: function (axes, panX, panY, sx, sy) {
        var rect = this.getChart().getInnerRect(),
            axesCfg = this.getAxes(), axisCfg,
            oldVisibleRanges = this.oldVisibleRanges,
            result = false;

        if (!oldVisibleRanges) {
            this.oldVisibleRanges = oldVisibleRanges = {};
            this.eachInteractiveAxes(function (axis) {
                oldVisibleRanges[axis.getId()] = axis.getVisibleRange();
            });
        }

        if (!rect) {
            return;
        }

        for (var i = 0; i < axes.length; i++) {
            axisCfg = axesCfg[axes[i].getPosition()];
            result = this.transformAxisBy(axes[i], oldVisibleRanges[axes[i].getId()], panX, panY, sx, sy, this.minZoom || axisCfg.minZoom, this.maxZoom || axisCfg.maxZoom) || result;
        }
        return result;
    },

    transformAxisBy: function (axis, oldVisibleRange, panX, panY, sx, sy, minZoom, maxZoom) {
        var me = this,
            visibleLength = oldVisibleRange[1] - oldVisibleRange[0],
            visibleRange = axis.getVisibleRange(),
            actualMinZoom =  minZoom || me.getMinZoom() || axis.config.minZoom,
            actualMaxZoom =  maxZoom || me.getMaxZoom() || axis.config.maxZoom,
            rect = me.getChart().getInnerRect(),
            left, right;
        if (!rect) {
            return;
        }

        var isSide = axis.isSide(),
            length = isSide ? rect[3] : rect[2],
            pan = isSide ? -panY : panX;
        visibleLength /= isSide ? sy : sx;
        if (visibleLength < 0) {
            visibleLength = -visibleLength;
        }

        if (visibleLength * actualMinZoom > 1) {
            visibleLength = 1;
        }

        if (visibleLength * actualMaxZoom < 1) {
            visibleLength = 1 / actualMaxZoom;
        }
        left = oldVisibleRange[0];
        right = oldVisibleRange[1];

        visibleRange = visibleRange[1] - visibleRange[0];
        if (visibleLength === visibleRange && visibleRange === 1) {
            return;
        }
        axis.setVisibleRange([
            (oldVisibleRange[0] + oldVisibleRange[1] - visibleLength) * 0.5 - pan / length * visibleLength,
            (oldVisibleRange[0] + oldVisibleRange[1] + visibleLength) * 0.5 - pan / length * visibleLength
        ]);
        return (Math.abs(left - axis.getVisibleRange()[0]) > 1e-10 || Math.abs(right - axis.getVisibleRange()[1]) > 1e-10);
    },

    destroy: function () {
        this.setModeToggleButton(null);
        this.callParent();
    }
});

/**
 * @class Ext.chart.interactions.Rotate
 * @extends Ext.chart.interactions.Abstract
 *
 * The Rotate interaction allows the user to rotate a polar chart about its central point.
 *
 *     @example
 *     Ext.create('Ext.Container', {
 *         renderTo: Ext.getBody(),
 *         width: 600,
 *         height: 400,
 *         layout: 'fit',
 *         items: {
 *             xtype: 'polar',
 *             interactions: 'rotate',
 *             colors: ["#115fa6", "#94ae0a", "#a61120", "#ff8809", "#ffd13e"],
 *             store: {
 *                 fields: ['name', 'data1', 'data2', 'data3', 'data4', 'data5'],
 *                 data: [
 *                     {'name':'metric one', 'data1':10, 'data2':12, 'data3':14, 'data4':8, 'data5':13},
 *                     {'name':'metric two', 'data1':7, 'data2':8, 'data3':16, 'data4':10, 'data5':3},
 *                     {'name':'metric three', 'data1':5, 'data2':2, 'data3':14, 'data4':12, 'data5':7},
 *                     {'name':'metric four', 'data1':2, 'data2':14, 'data3':6, 'data4':1, 'data5':23},
 *                     {'name':'metric five', 'data1':27, 'data2':38, 'data3':36, 'data4':13, 'data5':33}
 *                 ]
 *             },
 *             series: {
 *                 type: 'pie',
 *                 label: {
 *                     field: 'name',
 *                     display: 'rotate'
 *                 },
 *                 xField: 'data3',
 *                 donut: 30
 *             }
 *         }
 *     });
 */
Ext.define('Ext.chart.interactions.Rotate', {
    extend: 'Ext.chart.interactions.Abstract',

    type: 'rotate',

    alias: 'interaction.rotate',

    /**
     * @event rotate
     * Fires on every tick of the rotation
     * @param {Ext.chart.interactions.Rotate} this This interaction.
     * @param {Number} angle The new current rotation angle.
     */

    /**
     * @event rotationEnd
     * Fires after a user finishes the rotation
     * @param {Ext.chart.interactions.Rotate} this This interaction.
     * @param {Number} angle The new current rotation angle.
     */

    config: {
        /**
         * @cfg {String} gesture
         * Defines the gesture type that will be used to rotate the chart. Currently only
         * supports `pinch` for two-finger rotation and `drag` for single-finger rotation.
         * @private
         */
        gesture: 'rotate',

        //@inheritdoc
        gestures: {
            rotate: 'onRotate',
            rotateend: 'onRotate',
            dragstart: 'onGestureStart',
            drag: 'onGesture',
            dragend: 'onGestureEnd'
        },

        /**
         * @cfg {Number} rotation
         * Saves the current rotation of the series. Accepts negative values and values > 360 ( / 180 * Math.PI)
         * @private
         */
        rotation: 0
    },

    oldRotations: null,

    getAngle: function(e) {
        var me = this,
            chart = me.getChart(),
            xy = chart.getEventXY(e),
            center = chart.getCenter();

        return Math.atan2(
            xy[1] - center[1],
            xy[0] - center[0]
        );
    },

    getEventRadius: function(e) {
        var me = this,
            chart = me.getChart(),
            xy = chart.getEventXY(e),
            center = chart.getCenter(),
            dx = xy[0] - center[0],
            dy = xy[1] - center[1];

        return Math.sqrt(dx * dx + dy * dy);
    },

    onGestureStart: function(e) {
        var me = this,
            chart = me.getChart(),
            radius = chart.getRadius(),
            eventRadius = me.getEventRadius(e);

        if (radius >= eventRadius) {
            me.lockEvents('drag');
            me.angle = me.getAngle(e);
            me.oldRotations = {};
            return false;
        }
    },

    onGesture: function(e) {
        var me = this,
            angle = me.getAngle(e) - me.angle;

        if (me.getLocks().drag === me) {
            me.doRotateTo(angle, true);
            return false;
        }
    },

    /**
     * @private
     */
    doRotateTo: function(angle, relative, animate) {
        var me = this,
            chart = me.getChart(),
            axes = chart.getAxes(),
            series = chart.getSeries(),
            oldRotations = me.oldRotations,
            axis, seriesItem, oldRotation,
            i, ln;

        if (!animate) {
            chart.suspendAnimation();
        }

        for (i = 0, ln = axes.length; i < ln; i++) {
            axis = axes[i];
            oldRotation = oldRotations[axis.getId()] || (oldRotations[axis.getId()] = axis.getRotation());
            axis.setRotation( angle + (relative ? oldRotation : 0) );
        }

        for (i = 0, ln = series.length; i < ln; i++) {
            seriesItem = series[i];
            oldRotation = oldRotations[seriesItem.getId()] || (oldRotations[seriesItem.getId()] = seriesItem.getRotation());
            seriesItem.setRotation( angle + (relative ? oldRotation : 0) );
        }

        me.setRotation(angle + (relative ? oldRotation : 0));

        me.fireEvent('rotate', me, me.getRotation());

        me.sync();
        if (!animate) {
            chart.resumeAnimation();
        }
    },

    /**
     * Rotates a polar chart about its center point to the specified angle.
     * @param {Number} angle The angle to rotate to.
     * @param {Boolean} [relative=false] Whether the rotation is relative to the current angle or not.
     * @param {Boolean} [animate=false] Whether to animate the rotation or not.
     */
    rotateTo: function (angle, relative, animate) {
        this.doRotateTo(angle, relative, animate);
        this.oldRotations = {};
    },

    onGestureEnd: function(e) {
        var me = this;
        if (me.getLocks().drag === me) {
            me.onGesture(e);
            me.unlockEvents('drag');

            me.fireEvent('rotationEnd', me, me.getRotation());

            return false;
        }
    },

    onRotate: function(e) {

    }
});

/**
 * @class Ext.chart.interactions.RotatePie3D
 * @extends Ext.chart.interactions.Rotate
 *
 * A special version of the Rotate interaction used by Pie3D Chart.
 */
Ext.define('Ext.chart.interactions.RotatePie3D', {

    extend: 'Ext.chart.interactions.Rotate',

    type: 'rotatePie3d',

    alias: 'interaction.rotatePie3d',

    getAngle: function (e) {
        var chart = this.getChart(),
            rtl = chart.getInherited().rtl,
            direction = rtl ? -1 : 1,
            pageXY = e.getXY(),
            xy = chart.element.getXY(),
            rect = chart.getMainRect();
        return direction * Math.atan2(
            pageXY[1] - xy[1] - rect[3] * 0.5,
            pageXY[0] - xy[0] - rect[2] * 0.5
        );
    }
});

/**
 * @abstract
 * @class Ext.chart.series.Cartesian
 * @extends Ext.chart.series.Series
 *
 * Common base class for series implementations that plot values using cartesian coordinates.
 *
 * @constructor
 */
Ext.define('Ext.chart.series.Cartesian', {
    extend: 'Ext.chart.series.Series',
    config: {
        /**
         * @cfg {String} xField
         * The field used to access the x axis value from the items from the data source.
         */
        xField: null,

        /**
         * @cfg {String|String[]} yField
         * The field(s) used to access the y-axis value(s) of the items from the data source.
         */
        yField: null,

        /**
         * @cfg {Ext.chart.axis.Axis|Number|String}
         * xAxis The chart axis the series is bound to in the 'X' direction.
         * Normally, this would be set automatically by the series.
         * For charts with multiple x-axes, this defines which x-axis is used by the series.
         * It refers to either axis' ID or the (zero-based) index of the axis
         * in the chart's {@link Ext.chart.AbstractChart#axes axes} config.
         */
        xAxis: null,

        /**
         * @cfg {Ext.chart.axis.Axis|Number|String}
         * yAxis The chart axis the series is bound to in the 'Y' direction.
         * Normally, this would be set automatically by the series.
         * For charts with multiple y-axes, this defines which y-axis is used by the series.
         * It refers to either axis' ID or the (zero-based) index of the axis
         * in the chart's {@link Ext.chart.AbstractChart#axes axes} config.
         */
        yAxis: null
    },

    directions: ['X', 'Y'],

    /**
     * @private
     *
     * Tells which store record fields should be used for a specific axis direction. E.g. for
     *
     *     fieldCategory<direction>: ['<fieldConfig1>', '<fieldConfig2>', ...]
     *
     * the field names from the following configs will be used:
     *
     *     series.<fieldConfig1>Field, series.<fieldConfig2>Field, ...
     *
     */
    fieldCategoryX: ['X'],
    fieldCategoryY: ['Y'],

    applyXAxis: function (newAxis, oldAxis) {
        return this.getChart().getAxis(newAxis) || oldAxis;
    },

    applyYAxis: function (newAxis, oldAxis) {
        return this.getChart().getAxis(newAxis) || oldAxis;
    },

    updateXAxis: function (axis) {
        axis.processData(this);
    },

    updateYAxis: function (axis) {
        axis.processData(this);
    },

    coordinateX: function () {
        return this.coordinate('X', 0, 2);
    },

    coordinateY: function () {
        return this.coordinate('Y', 1, 2);
    },

    getItemForPoint: function (x, y) {
        if (this.getSprites()) {
            var me = this,
                sprite = me.getSprites()[0],
                store = me.getStore(),
                item, index;

            if (me.getHidden()) {
                return null;
            }
            if (sprite) {
                index = sprite.getIndexNearPoint(x, y);
                if (index !== -1) {
                    item = {
                        series: me,
                        category: me.getItemInstancing() ? 'items' : 'markers',
                        index: index,
                        record: store.getData().items[index],
                        field: me.getYField(),
                        sprite: sprite
                    };
                    return item;
                }
            }
        }
    },

    createSprite: function () {
        var me = this,
            sprite = me.callParent(),
            chart = me.getChart(),
            xAxis = me.getXAxis();

        sprite.setAttributes({
            flipXY: chart.getFlipXY(),
            xAxis: xAxis
        });
        if (sprite.setAggregator && xAxis && xAxis.getAggregator) {
            if (xAxis.getAggregator) {
                sprite.setAggregator({strategy: xAxis.getAggregator()});
            } else {
                sprite.setAggregator({});
            }
        }
        return sprite;
    },

    getSprites: function () {
        var me = this,
            chart = this.getChart(),
            animation = me.getAnimation() || chart && chart.getAnimation(),
            itemInstancing = me.getItemInstancing(),
            sprites = me.sprites, sprite;

        if (!chart) {
            return [];
        }

        if (!sprites.length) {
            sprite = me.createSprite();
        } else {
            sprite = sprites[0];
        }

        if (animation) {
            me.getLabel().getTemplate().fx.setConfig(animation);
            if (itemInstancing) {
                sprite.itemsMarker.getTemplate().fx.setConfig(animation);
            }
            sprite.fx.setConfig(animation);
        }
        return sprites;
    },

    provideLegendInfo: function (target) {
        var me = this,
            style = me.getSubStyleWithTheme(),
            fill = style.fillStyle;

        if (Ext.isArray(fill)) {
            fill = fill[0];
        }
        target.push({
            name: me.getTitle() || me.getYField() || me.getId(),
            mark: (Ext.isObject(fill) ? fill.stops && fill.stops[0].color : fill) || style.strokeStyle || 'black',
            disabled: me.getHidden(),
            series: me.getId(),
            index: 0
        });
    },

    getXRange: function () {
        return [this.dataRange[0], this.dataRange[2]];
    },

    getYRange: function () {
        return [this.dataRange[1], this.dataRange[3]];
    }
})
;

/**
 * @abstract
 * @extends Ext.chart.series.Cartesian
 * Abstract class for all the stacked cartesian series including area series
 * and bar series.
 */
Ext.define('Ext.chart.series.StackedCartesian', {

    extend: 'Ext.chart.series.Cartesian',

    config: {
        /**
         * @cfg {Boolean}
         * 'true' to display the series in its stacked configuration.
         */
        stacked: true,

        /**
         * @cfg {Array} hidden
         */
        hidden: []
    },

    animatingSprites: 0,

    themeColorCount: function() {
        var me = this,
            yField = me.getYField();
        return Ext.isArray(yField) ? yField.length : 1;
    },

    updateStacked: function () {
        this.processData();
    },

    coordinateY: function () {
        return this.coordinateStacked('Y', 1, 2);
    },

    getFields: function (fieldCategory) {
        var me = this,
            fields = [], fieldsItem,
            i, ln;
        for (i = 0, ln = fieldCategory.length; i < ln; i++) {
            fieldsItem = me['get' + fieldCategory[i] + 'Field']();
            if (Ext.isArray(fieldsItem)) {
                fields.push.apply(fields, fieldsItem);
            } else {
                fields.push(fieldsItem);
            }
        }
        return fields;
    },

    updateLabelOverflowPadding: function (labelOverflowPadding) {
        this.getLabel().setAttributes({labelOverflowPadding: labelOverflowPadding});
    },

    getSprites: function () {
        var me = this,
            chart = me.getChart(),
            animation = me.getAnimation() || chart && chart.getAnimation(),
            fields = me.getFields(me.fieldCategoryY),
            itemInstancing = me.getItemInstancing(),
            sprites = me.sprites, sprite,
            hidden = me.getHidden(),
            spritesCreated = false,
            i, length = fields.length;

        if (!chart) {
            return [];
        }

        for (i = 0; i < length; i++) {
            sprite = sprites[i];
            if (!sprite) {
                sprite = me.createSprite();
                sprite.setAttributes({zIndex: -i});

                sprite.setField(fields[i]);
                spritesCreated = true;
                hidden.push(false);
                if (itemInstancing) {
                    sprite.itemsMarker.getTemplate().setAttributes(me.getStyleByIndex(i));
                } else {
                    sprite.setAttributes(me.getStyleByIndex(i));
                }
            }
            if (animation) {
                if (itemInstancing) {
                    sprite.itemsMarker.getTemplate().fx.setConfig(animation);
                }
                sprite.fx.setConfig(animation);
            }
        }

        if (spritesCreated) {
            me.updateHidden(hidden);
        }
        return sprites;
    },

    getItemForPoint: function (x, y) {
        if (this.getSprites()) {
            var me = this,
                i, ln, sprite,
                itemInstancing = me.getItemInstancing(),
                sprites = me.getSprites(),
                store = me.getStore(),
                hidden = me.getHidden(),
                item, index, yField;

            for (i = 0, ln = sprites.length; i < ln; i++) {
                if (!hidden[i]) {
                    sprite = sprites[i];
                    index = sprite.getIndexNearPoint(x, y);
                    if (index !== -1) {
                        yField = me.getYField();
                        item = {
                            series: me,
                            index: index,
                            category: itemInstancing ? 'items' : 'markers',
                            record: store.getData().items[index],
                            // Handle the case where we're stacked but a single segment
                            field: typeof yField === 'string' ? yField : yField[i],
                            sprite: sprite
                        };
                        return item;
                    }
                }
            }
            return null;
        }
    },

    provideLegendInfo: function (target) {
        var me = this,
            sprites = me.getSprites(),
            title = me.getTitle(),
            field = me.getYField(),
            hidden = me.getHidden(),
            single = sprites.length === 1,
            style, fill,
            i, name;

        for (i = 0; i < sprites.length; i++) {
            style = me.getStyleByIndex(i);
            fill = style.fillStyle;
            if (Ext.isArray(title)) {
                name = title[i];
            } else if (single) {
                name = title;
            } else if (Ext.isArray(field)) {
                name = field[i];
            } else {
                name = me.getId();
            }
            target.push({
                name: name,
                mark: (Ext.isObject(fill) ? fill.stops && fill.stops[0].color : fill) || style.strokeStyle || 'black',
                disabled: hidden[i],
                series: me.getId(),
                index: i
            });
        }
    },

    onSpriteAnimationStart: function (sprite) {
        this.animatingSprites++;
        if (this.animatingSprites === 1) {
            this.fireEvent('animationstart');
        }
    },

    onSpriteAnimationEnd: function (sprite) {
        this.animatingSprites--;
        if (this.animatingSprites === 0) {
            this.fireEvent('animationend');
        }
    }
});

/**
 * @class Ext.chart.series.sprite.Cartesian
 * @extends Ext.draw.sprite.Sprite
 *
 * Cartesian sprite.
 */
Ext.define('Ext.chart.series.sprite.Cartesian', {
    extend: 'Ext.draw.sprite.Sprite',
    mixins: {
        markerHolder: 'Ext.chart.MarkerHolder'
    },
    inheritableStatics: {
        def: {
            processors: {
                /**
                 * @cfg {Number} [dataMinX=0] Data minimum on the x-axis.
                 */
                dataMinX: 'number',

                /**
                 * @cfg {Number} [dataMaxX=1] Data maximum on the x-axis.
                 */
                dataMaxX: 'number',

                /**
                 * @cfg {Number} [dataMinY=0] Data minimum on the y-axis.
                 */
                dataMinY: 'number',

                /**
                 * @cfg {Number} [dataMaxY=2] Data maximum on the y-axis.
                 */
                dataMaxY: 'number',

                /**
                 * @cfg {Array} [rangeX='data'] Data range derived from all the series bound to the x-axis.
                 */
                rangeX: 'data',
                /**
                 * @cfg {Array} [rangeY='data'] Data range derived from all the series bound to the y-axis.
                 */
                rangeY: 'data',

                /**
                 * @cfg {Object} [dataY=null] Data items on the y-axis.
                 */
                dataY: 'data',

                /**
                 * @cfg {Object} [dataX=null] Data items on the x-axis.
                 */
                dataX: 'data',

                /**
                 * @cfg {Object} [labels=null] Labels used in the series.
                 */
                labels: 'default',

                /**
                 * @cfg {Number} [labelOverflowPadding=10] Padding around labels to determine overlap.
                 */
                labelOverflowPadding: 'number',

                /**
                 * @cfg {Number} [selectionTolerance=20]
                 * The distance from the event position to the sprite's data points to trigger interactions (used for 'iteminfo', etc).
                 */
                selectionTolerance: 'number',

                /**
                 * @cfg {Boolean} If flipXY is 'true', the series is flipped.
                 */
                flipXY: 'bool',

                renderer: 'default',

                // PanZoom information
                visibleMinX: 'number',
                visibleMinY: 'number',
                visibleMaxX: 'number',
                visibleMaxY: 'number',
                innerWidth: 'number',
                innerHeight: 'number'
            },
            defaults: {
                dataY: null,
                dataX: null,
                dataMinX: 0,
                dataMaxX: 1,
                dataMinY: 0,
                dataMaxY: 1,
                labels: null,
                labelOverflowPadding: 10,
                selectionTolerance: 20,
                flipXY: false,
                renderer: null,
                transformFillStroke: false,

                visibleMinX: 0,
                visibleMinY: 0,
                visibleMaxX: 1,
                visibleMaxY: 1,
                innerWidth: 1,
                innerHeight: 1
            },
            dirtyTriggers: {
                dataX: 'dataX,bbox',
                dataY: 'dataY,bbox',
                dataMinX: 'bbox',
                dataMaxX: 'bbox',
                dataMinY: 'bbox',
                dataMaxY: 'bbox',
                visibleMinX: 'panzoom',
                visibleMinY: 'panzoom',
                visibleMaxX: 'panzoom',
                visibleMaxY: 'panzoom',
                innerWidth: 'panzoom',
                innerHeight: 'panzoom'
            },
            updaters: {
                dataX: function (attrs) {
                    this.processDataX();
                    // TODO: the lines below basically schedule the 'dataY'
                    // TODO: updater, must be a better way to do it.
                    if (!attrs.dirtyFlags.dataY) {
                        attrs.dirtyFlags.dataY = [];
                    }
                    attrs.dirtyFlags.dataY.push('dataY');
                },

                dataY: function () {
                    this.processDataY();
                },

                panzoom: function (attrs) {
                    var dx = attrs.visibleMaxX - attrs.visibleMinX,
                        dy = attrs.visibleMaxY - attrs.visibleMinY,
                        innerWidth = attrs.flipXY ? attrs.innerHeight : attrs.innerWidth,
                        innerHeight = !attrs.flipXY ? attrs.innerHeight : attrs.innerWidth,
                        surface = this.getSurface(),
                        isRtl = surface ? surface.getInherited().rtl : false;

                    if (isRtl && !attrs.flipXY) {
                        attrs.translationX = innerWidth + attrs.visibleMinX * innerWidth / dx;
                    } else {
                        attrs.translationX = -attrs.visibleMinX * innerWidth / dx;
                    }
                    attrs.translationY = -attrs.visibleMinY * innerHeight / dy;
                    attrs.scalingX = (isRtl && !attrs.flipXY ? -1 : 1) * innerWidth / dx;
                    attrs.scalingY = innerHeight / dy;
                    attrs.scalingCenterX = 0;
                    attrs.scalingCenterY = 0;
                    this.applyTransformations(true);
                }
            }
        }
    },

    config: {
        /**
         * @private
         * @cfg {Object} store The store that is passed to the renderer.
         */
        store: null,

        /**
         * @cfg {String} field The store field used by the series.
         */
        field: null
    },

    processDataY: Ext.emptyFn,

    processDataX: Ext.emptyFn,

    updatePlainBBox: function (plain) {
        var attr = this.attr;
        plain.x = attr.dataMinX;
        plain.y = attr.dataMinY;
        plain.width = attr.dataMaxX - attr.dataMinX;
        plain.height = attr.dataMaxY - attr.dataMinY;
    },

    /**
     * Does a binary search of the data on the x-axis using the given key.
     * @param {String} key
     * @return {*}
     */
    binarySearch: function (key) {
        var dx = this.attr.dataX,
            start = 0,
            end = dx.length;
        if (key <= dx[0]) {
            return start;
        }
        if (key >= dx[end - 1]) {
            return end - 1;
        }
        while (start + 1 < end) {
            var mid = (start + end) >> 1,
                val = dx[mid];
            if (val === key) {
                return mid;
            } else if (val < key) {
                start = mid;
            } else {
                end = mid;
            }
        }
        return start;
    },

    render: function (surface, ctx, rect) {
        var me = this,
            attr = me.attr,
            inverseMatrix = attr.inverseMatrix.clone();

        inverseMatrix.appendMatrix(surface.inverseMatrix);

        if (attr.dataX === null || attr.dataX === undefined) {
            return;
        }
        if (attr.dataY === null || attr.dataY === undefined) {
            return;
        }

        if (inverseMatrix.getXX() * inverseMatrix.getYX() || inverseMatrix.getXY() * inverseMatrix.getYY()) {
            console.log('Cartesian Series sprite does not support rotation/sheering');
            return;
        }

        var clip = inverseMatrix.transformList([
            [rect[0] - 1, rect[3] + 1],
            [rect[0] + rect[2] + 1, -1]
        ]);

        clip = clip[0].concat(clip[1]);

        me.renderClipped(surface, ctx, clip, rect);
    },

    /**
     * Render the given visible clip range.
     * @param {Ext.draw.Surface} surface
     * @param {Ext.draw.engine.Canvas/Ext.draw.engine.SvgContext} ctx
     * @param {Array} clip
     * @param {Array} rect
     */
    renderClipped: Ext.emptyFn,

    /**
     * Get the nearest item index from point (x, y). -1 as not found.
     * @param {Number} x
     * @param {Number} y
     * @return {Number} The index
     */
    getIndexNearPoint: function (x, y) {
        var me = this,
            mat = me.attr.matrix,
            dataX = me.attr.dataX,
            dataY = me.attr.dataY,
            selectionTolerance = me.attr.selectionTolerance,
            minX, minY, index = -1,
            imat = mat.clone().prependMatrix(me.surfaceMatrix).inverse(),
            center = imat.transformPoint([x, y]),
            positionLB = imat.transformPoint([x - selectionTolerance, y - selectionTolerance]),
            positionTR = imat.transformPoint([x + selectionTolerance, y + selectionTolerance]),
            left = Math.min(positionLB[0], positionTR[0]),
            right = Math.max(positionLB[0], positionTR[0]),
            top = Math.min(positionLB[1], positionTR[1]),
            bottom = Math.max(positionLB[1], positionTR[1]),
            xi, yi, i, len;

        for (i = 0, len = dataX.length; i < len; i++) {
            xi = dataX[i];
            yi = dataY[i];
            if (xi > left && xi < right && yi > top && yi < bottom) {
                if (index === -1 || (Math.abs(xi - center[0]) < minX) && (Math.abs(yi - center[1]) < minY)) {
                    minX = Math.abs(xi - center[0]);
                    minY = Math.abs(yi - center[1]);
                    index = i;
                }
            }
        }

        return index;
    }
});

/**
 * @class Ext.chart.series.sprite.StackedCartesian
 * @extends Ext.chart.series.sprite.Cartesian
 *
 * Stacked cartesian sprite.
 */
Ext.define('Ext.chart.series.sprite.StackedCartesian', {
    extend: 'Ext.chart.series.sprite.Cartesian',
    inheritableStatics: {
        def: {
            processors: {
                /**
                 * @private
                 * @cfg {Number} [groupCount=1] The number of items (e.g. bars) in a group.
                 */
                groupCount: 'number',

                /**
                 * @private
                 * @cfg {Number} [groupOffset=0] The group index of the series sprite.
                 */
                groupOffset: 'number',

                /**
                 * @private
                 * @cfg {Object} [dataStartY=null] The starting point of the data used in the series.
                 */
                dataStartY: 'data'
            },
            defaults: {
                selectionTolerance: 20,
                groupCount: 1,
                groupOffset: 0,
                dataStartY: null
            },
            dirtyTriggers: {
                dataStartY: 'dataY,bbox'
            }
        }
    },

    //@inheritdoc
    getIndexNearPoint: function (x, y) {
        var sprite = this,
            mat = sprite.attr.matrix,
            dataX = sprite.attr.dataX,
            dataY = sprite.attr.dataY,
            dataStartY = sprite.attr.dataStartY,
            selectionTolerance = sprite.attr.selectionTolerance,
            minX = 0.5, minY = Infinity, index = -1,
            imat = mat.clone().prependMatrix(this.surfaceMatrix).inverse(),
            center = imat.transformPoint([x, y]),
            positionLB = imat.transformPoint([x - selectionTolerance, y - selectionTolerance]),
            positionTR = imat.transformPoint([x + selectionTolerance, y + selectionTolerance]),
            dx, dy,
            top = Math.min(positionLB[1], positionTR[1]),
            bottom = Math.max(positionLB[1], positionTR[1]);

        for (var i = 0; i < dataX.length; i++) {
            if (Math.min(dataStartY[i], dataY[i]) <= bottom && top <= Math.max(dataStartY[i], dataY[i])) {
                dx = Math.abs(dataX[i] - center[0]);
                dy = Math.max(-Math.min(dataY[i] - center[1], center[1] - dataStartY[i]), 0);
                if (dx < minX && dy <= minY) {
                    minX = dx;
                    minY = dy;
                    index = i;
                }
            }
        }

        return index;
    }
});

/**
 * @class Ext.chart.series.sprite.Area
 * @extends Ext.chart.series.sprite.StackedCartesian
 *
 * Area series sprite.
 */
Ext.define('Ext.chart.series.sprite.Area', {
    alias: 'sprite.areaSeries',
    extend: 'Ext.chart.series.sprite.StackedCartesian',

    inheritableStatics: {
        def: {
            processors: {
                /**
                 * @cfg {Boolean} [step=false] 'true' if the area is represented with steps instead of lines.
                 */
                step: 'bool'
            },
            defaults: {
                step: false
            }
        }
    },

    renderClipped: function (surface, ctx, clip) {
        var me = this,
            attr = me.attr,
            dataX = attr.dataX,
            dataY = attr.dataY,
            dataStartY = attr.dataStartY,
            matrix = attr.matrix,
            x, y, i, lastX, lastY, startX, startY,
            xx = matrix.elements[0],
            dx = matrix.elements[4],
            yy = matrix.elements[3],
            dy = matrix.elements[5],
            surfaceMatrix = me.surfaceMatrix,
            markerCfg = {},
            min = Math.min(clip[0], clip[2]),
            max = Math.max(clip[0], clip[2]),
            start = Math.max(0, this.binarySearch(min)),
            end = Math.min(dataX.length - 1, this.binarySearch(max) + 1);

        ctx.beginPath();
        startX = dataX[start] * xx + dx;
        startY = dataY[start] * yy + dy;
        ctx.moveTo(startX, startY);
        if (attr.step) {
            lastY = startY;
            for (i = start; i <= end; i++) {
                x = dataX[i] * xx + dx;
                y = dataY[i] * yy + dy;
                ctx.lineTo(x, lastY);
                ctx.lineTo(x, lastY = y);
            }
        } else {
            for (i = start; i <= end; i++) {
                x = dataX[i] * xx + dx;
                y = dataY[i] * yy + dy;
                ctx.lineTo(x, y);
            }
        }

        if (dataStartY) {
            if (attr.step) {
                lastX = dataX[end] * xx + dx;
                for (i = end; i >= start; i--) {
                    x = dataX[i] * xx + dx;
                    y = dataStartY[i] * yy + dy;
                    ctx.lineTo(lastX, y);
                    ctx.lineTo(lastX = x, y);
                }
            } else {
                for (i = end; i >= start; i--) {
                    x = dataX[i] * xx + dx;
                    y = dataStartY[i] * yy + dy;
                    ctx.lineTo(x, y);
                }
            }
        } else {
            ctx.lineTo(dataX[end] * xx + dx, y);
            ctx.lineTo(dataX[end] * xx + dx, dy);
            ctx.lineTo(startX, dy);
            ctx.lineTo(startX, dataY[i] * yy + dy);
        }
        if (attr.transformFillStroke) {
            attr.matrix.toContext(ctx);
        }
        ctx.fill();

        if (attr.transformFillStroke) {
            attr.inverseMatrix.toContext(ctx);
        }
        ctx.beginPath();
        ctx.moveTo(startX, startY);
        if (attr.step) {
            for (i = start; i <= end; i++) {
                x = dataX[i] * xx + dx;
                y = dataY[i] * yy + dy;
                ctx.lineTo(x, lastY);
                ctx.lineTo(x, lastY = y);
                markerCfg.translationX = surfaceMatrix.x(x, y);
                markerCfg.translationY = surfaceMatrix.y(x, y);
                me.putMarker('markers', markerCfg, i, !attr.renderer);
            }
        } else {
            for (i = start; i <= end; i++) {
                x = dataX[i] * xx + dx;
                y = dataY[i] * yy + dy;
                ctx.lineTo(x, y);
                markerCfg.translationX = surfaceMatrix.x(x, y);
                markerCfg.translationY = surfaceMatrix.y(x, y);
                me.putMarker('markers', markerCfg, i, !attr.renderer);
            }
        }

        if (attr.transformFillStroke) {
            attr.matrix.toContext(ctx);
        }
        ctx.stroke();
    }
});

/**
 * @class Ext.chart.series.Area
 * @extends Ext.chart.series.StackedCartesian
 *
 * Creates an Area Chart.
 * 
 *     @example
 *     Ext.create('Ext.Container', {
 *         renderTo: Ext.getBody(),
 *         width: 600,
 *         height: 400,
 *         layout: 'fit',
 *         items: {
 *             xtype: 'cartesian',
 *             store: {
 *               fields: ['name', 'data1', 'data2', 'data3', 'data4', 'data5'],
 *               data: [
 *                   {'name': 'metric one', 'data1': 10, 'data2': 12, 'data3': 14, 'data4': 8, 'data5': 13},
 *                   {'name': 'metric two', 'data1': 7, 'data2': 8, 'data3': 16, 'data4': 10, 'data5': 3},
 *                   {'name': 'metric three', 'data1': 5, 'data2': 2, 'data3': 14, 'data4': 12, 'data5': 7},
 *                   {'name': 'metric four', 'data1': 2, 'data2': 14, 'data3': 6, 'data4': 1, 'data5': 23},
 *                   {'name': 'metric five', 'data1': 27, 'data2': 38, 'data3': 36, 'data4': 13, 'data5': 33}
 *               ]
 *             },
 *             axes: [{
 *                 type: 'numeric',
 *                 position: 'left',
 *                 fields: ['data1'],
 *                 title: {
 *                     text: 'Sample Values',
 *                     fontSize: 15
 *                 },
 *                 grid: true,
 *                 minimum: 0
 *             }, {
 *                 type: 'category',
 *                 position: 'bottom',
 *                 fields: ['name'],
 *                 title: {
 *                     text: 'Sample Values',
 *                     fontSize: 15
 *                 }
 *             }],
 *             series: {
 *                 type: 'area',
 *                 subStyle: {
 *                     fill: ['blue', 'green', 'red']
 *                 },
 *                 xField: 'name',
 *                 yField: ['data1', 'data2', 'data3']
 *             }
 *         }
 *     });
 */
Ext.define('Ext.chart.series.Area', {

    extend: 'Ext.chart.series.StackedCartesian',

    alias: 'series.area',
    type: 'area',
    seriesType: 'areaSeries',

    requires: ['Ext.chart.series.sprite.Area']
});

/**
 * @class Ext.chart.series.sprite.Bar
 * @extends Ext.chart.series.sprite.StackedCartesian
 *
 * Draws a sprite used in the bar series.
 */
Ext.define('Ext.chart.series.sprite.Bar', {
    alias: 'sprite.barSeries',
    extend: 'Ext.chart.series.sprite.StackedCartesian',

    inheritableStatics: {
        def: {
            processors: {
                /**
                 * @cfg {Number} [minBarWidth=2] The minimum bar width.
                 */
                minBarWidth: 'number',

                /**
                 * @cfg {Number} [maxBarWidth=100] The maximum bar width.
                 */
                maxBarWidth: 'number',

                /**
                 * @cfg {Number} [minGapWidth=5] The minimum gap between bars.
                 */
                minGapWidth: 'number',

                /**
                 * @cfg {Number} [radius=0] The degree of rounding for rounded bars.
                 */
                radius: 'number',

                /**
                 * @cfg {Number} [inGroupGapWidth=3] The gap between grouped bars.
                 */
                inGroupGapWidth: 'number'
            },
            defaults: {
                minBarWidth: 2,
                maxBarWidth: 100,
                minGapWidth: 5,
                inGroupGapWidth: 3,
                radius: 0
            }
        }
    },

    drawLabel: function (text, dataX, dataStartY, dataY, labelId) {
        var me = this,
            attr = me.attr,
            label = me.getBoundMarker('labels')[0],
            labelTpl = label.getTemplate(),
            labelCfg = me.labelCfg || (me.labelCfg = {}),
            surfaceMatrix = me.surfaceMatrix,
            labelOverflowPadding = attr.labelOverflowPadding,
            labelDisplay = labelTpl.attr.display,
            labelOrientation = labelTpl.attr.orientation,
            labelY, halfWidth, labelBBox,
            changes, hasPendingChanges;

        // The coordinates below (data point converted to surface coordinates)
        // are just for the renderer to give it a notion of where the label will be positioned.
        // The actual position of the label will be different
        // (unless the renderer returns x/y coordinates in the changes object)
        // and depend on several things including the size of the text,
        // which has to be measured after the renderer call,
        // since text can be modified by the renderer.
        labelCfg.x = surfaceMatrix.x(dataX, dataY);
        labelCfg.y = surfaceMatrix.y(dataX, dataY);

        // Set defaults
        if (!attr.flipXY) {
            labelCfg.rotationRads = -Math.PI * 0.5;
        } else {
            labelCfg.rotationRads = 0;
        }
        labelCfg.calloutVertical = !attr.flipXY;

        // Check if we have a specific orientation specified, if so, set
        // the appropriate values.
        switch (labelOrientation) {
            case 'horizontal': 
                labelCfg.rotationRads = 0;              
                labelCfg.calloutVertical = false;
                break;
            case 'vertical': 
                labelCfg.rotationRads = -Math.PI * 0.5; 
                labelCfg.calloutVertical = true;
                break;
        }

        labelCfg.text = text;

        if (labelTpl.attr.renderer) {
            changes = labelTpl.attr.renderer.call(this, text, label, labelCfg, {store: this.getStore()}, labelId);
            if (typeof changes === 'string') {
                labelCfg.text = changes;
            } else if (typeof changes === 'object') {
                if ('text' in changes) {
                    labelCfg.text = changes.text;
                }
                hasPendingChanges = true;
            }
        }

        labelBBox = me.getMarkerBBox('labels', labelId, true);
        if (!labelBBox) {
            me.putMarker('labels', labelCfg, labelId);
            labelBBox = me.getMarkerBBox('labels', labelId, true);
        }

        halfWidth = (labelBBox.width / 2 + labelOverflowPadding);
        if (dataStartY > dataY) {
            halfWidth = -halfWidth;
        }

        if ((labelOrientation === 'horizontal' && attr.flipXY) || (labelOrientation === 'vertical' && !attr.flipXY) || !labelOrientation) {
            labelY = (labelDisplay === 'insideStart') ? dataStartY + halfWidth : dataY - halfWidth;
        } else {
            labelY = (labelDisplay === 'insideStart') ? dataStartY + labelOverflowPadding * 2 : dataY - labelOverflowPadding * 2;
        }
        labelCfg.x = surfaceMatrix.x(dataX, labelY);
        labelCfg.y = surfaceMatrix.y(dataX, labelY);

        labelY = (labelDisplay === 'insideStart') ? dataStartY - halfWidth : dataY + halfWidth;
        labelCfg.calloutPlaceX = surfaceMatrix.x(dataX, labelY);
        labelCfg.calloutPlaceY = surfaceMatrix.y(dataX, labelY);

        labelY = (labelDisplay === 'insideStart') ? dataStartY : dataY;
        labelCfg.calloutStartX = surfaceMatrix.x(dataX, labelY);
        labelCfg.calloutStartY = surfaceMatrix.y(dataX, labelY);
        if (dataStartY > dataY) {
            halfWidth = -halfWidth;
        }
        if (Math.abs(dataY - dataStartY) <= halfWidth * 2 || labelDisplay === 'outside') {
            labelCfg.callout = 1;
        } else {
            labelCfg.callout = 0;
        }

        if (hasPendingChanges) {
            Ext.apply(labelCfg, changes);
        }

        me.putMarker('labels', labelCfg, labelId);
    },

    drawBar: function (ctx, surface, clip, left, top, right, bottom, index) {
        var itemCfg = this.itemCfg || (this.itemCfg = {}),
            changes;

        itemCfg.x = left;
        itemCfg.y = top;
        itemCfg.width = right - left;
        itemCfg.height = bottom - top;
        itemCfg.radius = this.attr.radius;

        if (this.attr.renderer) {
            changes = this.attr.renderer.call(this, this, itemCfg, {store:this.getStore()}, index);
            Ext.apply(itemCfg, changes);
        }
        this.putMarker('items', itemCfg, index, !this.attr.renderer);
    },

    //@inheritdoc
    renderClipped: function (surface, ctx, clip, rect) {
        if (this.cleanRedraw) {
            return;
        }
        var me = this,
            attr = me.attr,
            dataX = attr.dataX,
            dataY = attr.dataY,
            dataText = attr.labels,
            dataStartY = attr.dataStartY,
            groupCount = attr.groupCount,
            groupOffset = attr.groupOffset - (groupCount - 1) * 0.5,
            inGroupGapWidth = attr.inGroupGapWidth,
            lineWidth = ctx.lineWidth,
            matrix = attr.matrix,
            xx = matrix.elements[0],
            yy = matrix.elements[3],
            dx = matrix.elements[4],
            dy = surface.roundPixel(matrix.elements[5]) - 1,
            maxBarWidth = (xx < 0 ? -1 : 1) * xx - attr.minGapWidth,
            minBarWidth = ( Math.min(maxBarWidth, attr.maxBarWidth) - inGroupGapWidth * (groupCount - 1) ) / groupCount,
            barWidth = surface.roundPixel( Math.max(attr.minBarWidth, minBarWidth) ),
            surfaceMatrix = me.surfaceMatrix,
            left, right, bottom, top, i, center,
            halfLineWidth = 0.5 * attr.lineWidth,
            min = Math.min(clip[0], clip[2]),
            max = Math.max(clip[0], clip[2]),
            start = Math.max(0, Math.floor(min)),
            end = Math.min(dataX.length - 1, Math.ceil(max)),
            drawMarkers = dataText && me.getBoundMarker('labels'),
            yLow, yHi;

        for (i = start; i <= end; i++) {
            yLow = dataStartY ? dataStartY[i] : 0;
            yHi = dataY[i];
            center = dataX[i] * xx + dx + groupOffset * (barWidth + inGroupGapWidth);
            left = surface.roundPixel(center - barWidth / 2) + halfLineWidth;
            top = surface.roundPixel(yHi * yy + dy + lineWidth);
            right = surface.roundPixel(center + barWidth / 2) - halfLineWidth;
            bottom = surface.roundPixel(yLow * yy + dy + lineWidth);

            me.drawBar(ctx, surface, clip, left, top - halfLineWidth, right, bottom - halfLineWidth, i);

            if (drawMarkers && dataText[i]) {
                me.drawLabel(dataText[i], center, bottom, top, i);
            }
            me.putMarker('markers', {
                translationX: surfaceMatrix.x(center, top),
                translationY: surfaceMatrix.y(center, top)
            }, i, true);
        }
    },

    //@inheritdoc
    getIndexNearPoint: function (x, y) {
        var sprite = this,
            attr = sprite.attr,
            dataX = attr.dataX,
            surface = sprite.getSurface(),
            surfaceRect = surface.getRect() || [0,0,0,0],
            surfaceHeight = surfaceRect[3],
            hitX, hitY,
            i, bbox,
            index = -1;

        // The "items" sprites that draw the bars work in a reverse vertical coordinate system
        // starting with 0 at the bottom and increasing the Y coordinate toward the top.
        // See also Ext.chart.series.Bar.getItemForPoint(x,y) regarding the chart's innerPadding.
        if (attr.flipXY) {
            hitX = surfaceHeight - y;
            if (surface.getInherited().rtl) {
                hitY = surfaceRect[2] - x;
            } else {
                hitY = x;
            }
        } else {
            hitX = x;
            hitY = surfaceHeight - y;
        }

        for (i = 0; i < dataX.length; i++) {
            bbox = sprite.getMarkerBBox('items', i);
            if (bbox && hitX >= bbox.x && hitX <= (bbox.x + bbox.width) && hitY >= bbox.y && hitY <= (bbox.y + bbox.height)) {
                index = i;
            }
        }
        return index;
    }

});

/**
 * @class Ext.chart.series.Bar
 * @extends Ext.chart.series.StackedCartesian
 * 
 * Creates a Bar Chart.
 * 
 *     @example
 *     Ext.create('Ext.Container', {
 *         renderTo: Ext.getBody(),
 *         width: 600,
 *         height: 400,
 *         layout: 'fit',
 *         items: {
 *             xtype: 'cartesian',
 *             store: {
 *                 fields: ['name', 'value'],
 *                 data: [
 *                     {name: 'metric one', value: 10},
 *                     {name: 'metric two', value: 7},
 *                     {name: 'metric three', value: 5},
 *                     {name: 'metric four', value: 2},
 *                     {name: 'metric five', value: 27}
 *                 ]
 *             },
 *             axes: [{
 *                 type: 'numeric',
 *                 position: 'left',
 *                 title: {
 *                     text: 'Sample Values',
 *                     fontSize: 15
 *                 },
 *                 fields: 'value'
 *             }, {
 *                 type: 'category',
 *                 position: 'bottom',
 *                 title: {
 *                     text: 'Sample Values',
 *                     fontSize: 15
 *                 },
 *                 fields: 'name'
 *             }],
 *             series: {
 *                 type: 'bar',
 *                 xField: 'name',
 *                 yField: 'value',
 *                 style: {
 *                     fill: 'blue'
 *                 }
 *             }
 *         }
 *     });
 */
Ext.define('Ext.chart.series.Bar', {

    extend: 'Ext.chart.series.StackedCartesian',

    alias: 'series.bar',
    type: 'bar',
    seriesType: 'barSeries',

    requires: [
        'Ext.chart.series.sprite.Bar',
        'Ext.draw.sprite.Rect'
    ],

    config: {
        /**
         * @private
         * @cfg {Object} itemInstancing Sprite template used for series.
         */
        itemInstancing: {
            type: 'rect',
            fx: {
                customDurations: {
                    x: 0,
                    y: 0,
                    width: 0,
                    height: 0,
                    radius: 0
                }
            }
        }
    },

    getItemForPoint: function (x, y) {
        if (this.getSprites()) {
            var me = this,
                chart = me.getChart(),
                padding = chart.getInnerPadding(),
                isRtl = chart.getInherited().rtl;

            // Convert the coordinates because the "items" sprites that draw the bars ignore the chart's InnerPadding.
            // See also Ext.chart.series.sprite.Bar.getIndexNearPoint(x,y) regarding the series's vertical coordinate system.
            arguments[0] = x + (isRtl ? padding.right : -padding.left);
            arguments[1] = y + padding.bottom;
            return me.callParent(arguments);
        }
    },

    updateXAxis: function (axis) {
        axis.setLabelInSpan(true);
        this.callParent(arguments);
    },

    updateHidden: function (hidden) {
        this.callParent(arguments);
        this.updateStacked();
    },

    updateStacked: function (stacked) {
        var sprites = this.getSprites(),
            ln = sprites.length,
            visible = [],
            attrs = {}, i;

        for (i = 0; i < ln; i++) {
            if (!sprites[i].attr.hidden) {
                visible.push(sprites[i]);
            }
        }
        ln = visible.length;

        if (this.getStacked()) {
            attrs.groupCount = 1;
            attrs.groupOffset = 0;
            for (i = 0; i < ln; i++) {
                visible[i].setAttributes(attrs);
            }
        } else {
            attrs.groupCount = visible.length;
            for (i = 0; i < ln; i++) {
                attrs.groupOffset = i;
                visible[i].setAttributes(attrs);
            }
        }
        this.callParent(arguments);
    }
});

/**
 * Limited cache is a size limited cache container that stores limited number of objects.
 * 
 * When {@link #get} is called, the container will try to find the object in the list.
 * If failed it will call the {@link #feeder} to create that object. If there are too many
 * objects in the container, the old ones are removed.
 * 
 * __Note:__ This is not using a Least Recently Used policy due to simplicity and performance consideration.
 */
Ext.define("Ext.draw.LimitedCache", {
    config: {
        /**
         * @cfg {Number}
         * The amount limit of the cache.
         */
        limit: 40,

        /**
         * @cfg {Function}
         * Function that generates the object when look-up failed.
         * @return {Number}
         */
        feeder: function () {
            return 0;
        },

        /**
         * @cfg {Object}
         * The scope for {@link #feeder}
         */
        scope: null
    },
    cache: null,

    constructor: function (config) {
        this.cache = {};
        this.cache.list = [];
        this.cache.tail = 0;
        this.initConfig(config);
    },

    /**
     * Get a cached object.
     * @param {String} id
     * @param {Mixed...} args Arguments appended to feeder.
     * @return {Object}
     */
    get: function (id) {
        // TODO: Implement cache hit optimization
        var cache = this.cache,
            limit = this.getLimit(),
            feeder = this.getFeeder(),
            scope = this.getScope() || this;

        if (cache[id]) {
            return cache[id].value;
        }
        if (cache.list[cache.tail]) {
            delete cache[cache.list[cache.tail].cacheId];
        }
        cache[id] = cache.list[cache.tail] = {
            value: feeder.apply(scope, Array.prototype.slice.call(arguments, 1)),
            cacheId: id
        };
        cache.tail++;
        if (cache.tail === limit) {
            cache.tail = 0;
        }
        return cache[id].value;
    },

    /**
     * Clear all the objects.
     */
    clear: function () {
        this.cache = {};
        this.cache.list = [];
        this.cache.tail = 0;
    }
});

/**
 * This class we summarize the data and returns it when required.
 */
Ext.define("Ext.draw.SegmentTree", {

    config: {
        strategy: "double"
    },

    /**
     * @private
     * @param {Object} result
     * @param {Number} last
     * @param {Number} dataX
     * @param {Number} dataOpen
     * @param {Number} dataHigh
     * @param {Number} dataLow
     * @param {Number} dataClose
     */
    time: function (result, last, dataX, dataOpen, dataHigh, dataLow, dataClose) {
        var start = 0, lastOffset, lastOffsetEnd,
            minimum = new Date(dataX[result.startIdx[0]]),
            maximum = new Date(dataX[result.endIdx[last - 1]]),
            extDate = Ext.Date,
            units = [
                [extDate.MILLI, 1, 'ms1', null],
                [extDate.MILLI, 2, 'ms2', 'ms1'],
                [extDate.MILLI, 5, 'ms5', 'ms1'],
                [extDate.MILLI, 10, 'ms10', 'ms5'],
                [extDate.MILLI, 50, 'ms50', 'ms10'],
                [extDate.MILLI, 100, 'ms100', 'ms50'],
                [extDate.MILLI, 500, 'ms500', 'ms100'],
                [extDate.SECOND, 1, 's1', 'ms500'],
                [extDate.SECOND, 10, 's10', 's1'],
                [extDate.SECOND, 30, 's30', 's10'],
                [extDate.MINUTE, 1, 'mi1', 's10'],
                [extDate.MINUTE, 5, 'mi5', 'mi1'],
                [extDate.MINUTE, 10, 'mi10', 'mi5'],
                [extDate.MINUTE, 30, 'mi30', 'mi10'],
                [extDate.HOUR, 1, 'h1', 'mi30'],
                [extDate.HOUR, 6, 'h6', 'h1'],
                [extDate.HOUR, 12, 'h12', 'h6'],
                [extDate.DAY, 1, 'd1', 'h12'],
                [extDate.DAY, 7, 'd7', 'd1'],
                [extDate.MONTH, 1, 'mo1', 'd1'],
                [extDate.MONTH, 3, 'mo3', 'mo1'],
                [extDate.MONTH, 6, 'mo6', 'mo3'],
                [extDate.YEAR, 1, 'y1', 'mo3'],
                [extDate.YEAR, 5, 'y5', 'y1'],
                [extDate.YEAR, 10, 'y10', 'y5'],
                [extDate.YEAR, 100, 'y100', 'y10']
            ], unitIdx, currentUnit,
            plainStart = start,
            plainEnd = last,
            first = false,
            startIdxs = result.startIdx,
            endIdxs = result.endIdx,
            minIdxs = result.minIdx,
            maxIdxs = result.maxIdx,
            opens = result.open,
            closes = result.close,
            minXs = result.minX,
            minYs = result.minY,
            maxXs = result.maxX,
            maxYs = result.maxY,
            i, current;

        for (unitIdx = 0; last > start + 1 && unitIdx < units.length; unitIdx++) {
            minimum = new Date(dataX[startIdxs[0]]);
            currentUnit = units[unitIdx];
            minimum = extDate.align(minimum, currentUnit[0], currentUnit[1]);
            if (extDate.diff(minimum, maximum, currentUnit[0]) > dataX.length * 2 * currentUnit[1]) {
                continue;
            }
            if (currentUnit[3] && result.map['time_' + currentUnit[3]]) {
                lastOffset = result.map['time_' + currentUnit[3]][0];
                lastOffsetEnd = result.map['time_' + currentUnit[3]][1];
            } else {
                lastOffset = plainStart;
                lastOffsetEnd = plainEnd;
            }

            start = last;
            current = minimum;
            first = true;

            startIdxs[last] = startIdxs[lastOffset];
            endIdxs[last] = endIdxs[lastOffset];
            minIdxs[last] = minIdxs[lastOffset];
            maxIdxs[last] = maxIdxs[lastOffset];
            opens[last] = opens[lastOffset];
            closes[last] = closes[lastOffset];
            minXs[last] = minXs[lastOffset];
            minYs[last] = minYs[lastOffset];
            maxXs[last] = maxXs[lastOffset];
            maxYs[last] = maxYs[lastOffset];
            current = Ext.Date.add(current, currentUnit[0], currentUnit[1]);

            for (i = lastOffset + 1; i < lastOffsetEnd; i++) {
                if (dataX[endIdxs[i]] < +current) {
                    endIdxs[last] = endIdxs[i];
                    closes[last] = closes[i];
                    if (maxYs[i] > maxYs[last]) {
                        maxYs[last] = maxYs[i];
                        maxXs[last] = maxXs[i];
                        maxIdxs[last] = maxIdxs[i];
                    }
                    if (minYs[i] < minYs[last]) {
                        minYs[last] = minYs[i];
                        minXs[last] = minXs[i];
                        minIdxs[last] = minIdxs[i];
                    }
                } else {
                    last++;
                    startIdxs[last] = startIdxs[i];
                    endIdxs[last] = endIdxs[i];
                    minIdxs[last] = minIdxs[i];
                    maxIdxs[last] = maxIdxs[i];
                    opens[last] = opens[i];
                    closes[last] = closes[i];
                    minXs[last] = minXs[i];
                    minYs[last] = minYs[i];
                    maxXs[last] = maxXs[i];
                    maxYs[last] = maxYs[i];
                    current = Ext.Date.add(current, currentUnit[0], currentUnit[1]);
                }
            }
            if (last > start) {
                result.map['time_' + currentUnit[2]] = [start, last];
            }
        }
    },

    /**
     * @private
     * @param {Object} result
     * @param {Number} position
     * @param {Number} dataX
     * @param {Number} dataOpen
     * @param {Number} dataHigh
     * @param {Number} dataLow
     * @param {Number} dataClose
     */
    "double": function (result, position, dataX, dataOpen, dataHigh, dataLow, dataClose) {
        var offset = 0, lastOffset, step = 1,
            i,
            startIdx,
            endIdx,
            minIdx,
            maxIdx,
            open,
            close,
            minX,
            minY,
            maxX,
            maxY;
        while (position > offset + 1) {
            lastOffset = offset;
            offset = position;
            step += step;
            for (i = lastOffset; i < offset; i += 2) {
                if (i === offset - 1) {
                    startIdx = result.startIdx[i];
                    endIdx = result.endIdx[i];
                    minIdx = result.minIdx[i];
                    maxIdx = result.maxIdx[i];
                    open = result.open[i];
                    close = result.close[i];
                    minX = result.minX[i];
                    minY = result.minY[i];
                    maxX = result.maxX[i];
                    maxY = result.maxY[i];
                } else {

                    startIdx = result.startIdx[i];
                    endIdx = result.endIdx[i + 1];
                    open = result.open[i];
                    close = result.close[i];
                    if (result.minY[i] <= result.minY[i + 1]) {
                        minIdx = result.minIdx[i];
                        minX = result.minX[i];
                        minY = result.minY[i];
                    } else {
                        minIdx = result.minIdx[i + 1];
                        minX = result.minX[i + 1];
                        minY = result.minY[i + 1];
                    }
                    if (result.maxY[i] >= result.maxY[i + 1]) {
                        maxIdx = result.maxIdx[i];
                        maxX = result.maxX[i];
                        maxY = result.maxY[i];
                    } else {
                        maxIdx = result.maxIdx[i + 1];
                        maxX = result.maxX[i + 1];
                        maxY = result.maxY[i + 1];
                    }
                }
                result.startIdx[position] = startIdx;
                result.endIdx[position] = endIdx;
                result.minIdx[position] = minIdx;
                result.maxIdx[position] = maxIdx;
                result.open[position] = open;
                result.close[position] = close;
                result.minX[position] = minX;
                result.minY[position] = minY;
                result.maxX[position] = maxX;
                result.maxY[position] = maxY;
                position++;
            }
            result.map['double_' + step] = [offset, position];
        }
    },

    /**
     * @private
     */
    none: Ext.emptyFn,

    /**
     * @private
     *
     * @param {Number} dataX
     * @param {Number} dataOpen
     * @param {Number} dataHigh
     * @param {Number} dataLow
     * @param {Number} dataClose
     * @return {Object}
     */
    aggregateData: function (dataX, dataOpen, dataHigh, dataLow, dataClose) {
        var length = dataX.length,
            startIdx = [],
            endIdx = [],
            minIdx = [],
            maxIdx = [],
            open = [],
            minX = [],
            minY = [],
            maxX = [],
            maxY = [],
            close = [],
            result = {
                startIdx: startIdx,
                endIdx: endIdx,
                minIdx: minIdx,
                maxIdx: maxIdx,
                open: open,
                minX: minX,
                minY: minY,
                maxX: maxX,
                maxY: maxY,
                close: close
            },
            i;

        for (i = 0; i < length; i++) {
            startIdx[i] = i;
            endIdx[i] = i;
            minIdx[i] = i;
            maxIdx[i] = i;
            open[i] = dataOpen[i];
            minX[i] = dataX[i];
            minY[i] = dataLow[i];
            maxX[i] = dataX[i];
            maxY[i] = dataHigh[i];
            close[i] = dataClose[i];
        }

        result.map = {
            original: [0, length]
        };
        if (length) {
            this[this.getStrategy()](result, length, dataX, dataOpen, dataHigh, dataLow, dataClose);
        }
        return result;
    },

    /**
     * @private
     * @param {Object} items
     * @param {Number} start
     * @param {Number} end
     * @param {Number} key
     * @return {*}
     */
    binarySearchMin: function (items, start, end, key) {
        var dx = this.dataX;
        if (key <= dx[items.startIdx[0]]) {
            return start;
        }
        if (key >= dx[items.startIdx[end - 1]]) {
            return end - 1;
        }
        while (start + 1 < end) {
            var mid = (start + end) >> 1,
                val = dx[items.startIdx[mid]];
            if (val === key) {
                return mid;
            } else if (val < key) {
                start = mid;
            } else {
                end = mid;
            }
        }
        return start;
    },

    /**
     * @private
     * @param {Object} items
     * @param {Number} start
     * @param {Number} end
     * @param {Number} key
     * @return {*}
     */
    binarySearchMax: function (items, start, end, key) {
        var dx = this.dataX;
        if (key <= dx[items.endIdx[0]]) {
            return start;
        }
        if (key >= dx[items.endIdx[end - 1]]) {
            return end - 1;
        }
        while (start + 1 < end) {
            var mid = (start + end) >> 1,
                val = dx[items.endIdx[mid]];
            if (val === key) {
                return mid;
            } else if (val < key) {
                start = mid;
            } else {
                end = mid;
            }
        }
        return end;
    },

    constructor: function (config) {
        this.initConfig(config);
    },

    /**
     * Sets the data of the segment tree.
     * @param {Number} dataX
     * @param {Number} dataOpen
     * @param {Number} dataHigh
     * @param {Number} dataLow
     * @param {Number} dataClose
     */
    setData: function (dataX, dataOpen, dataHigh, dataLow, dataClose) {
        if (!dataHigh) {
            dataClose = dataLow = dataHigh = dataOpen;
        }
        this.dataX = dataX;
        this.dataOpen = dataOpen;
        this.dataHigh = dataHigh;
        this.dataLow = dataLow;
        this.dataClose = dataClose;
        if (dataX.length === dataHigh.length &&
            dataX.length === dataLow.length) {
            this.cache = this.aggregateData(dataX, dataOpen, dataHigh, dataLow, dataClose);
        }
    },

    /**
     * Returns the minimum range of data that fits the given range and step size.
     *
     * @param {Number} min
     * @param {Number} max
     * @param {Number} estStep
     * @return {Object} The aggregation information.
     * @return {Number} return.start
     * @return {Number} return.end
     * @return {Object} return.data The aggregated data
     */
    getAggregation: function (min, max, estStep) {
        if (!this.cache) {
            return null;
        }
        var minStep = Infinity,
            range = this.dataX[this.dataX.length - 1] - this.dataX[0],
            cacheMap = this.cache.map,
            result = cacheMap.original,
            name, positions, ln, step, minIdx, maxIdx;

        for (name in cacheMap) {
            positions = cacheMap[name];
            ln = positions[1] - positions[0] - 1;
            step = range / ln;
            if (estStep <= step && step < minStep) {
                result = positions;
                minStep = step;
            }
        }
        minIdx = Math.max(this.binarySearchMin(this.cache, result[0], result[1], min), result[0]);
        maxIdx = Math.min(this.binarySearchMax(this.cache, result[0], result[1], max) + 1, result[1]);
        return {
            data: this.cache,
            start: minIdx,
            end: maxIdx
        };
    }
});

/**
 *
 */
Ext.define('Ext.chart.series.sprite.Aggregative', {
    extend: 'Ext.chart.series.sprite.Cartesian',
    requires: ['Ext.draw.LimitedCache', 'Ext.draw.SegmentTree'],
    inheritableStatics: {
        def: {
            processors: {
                /**
                 * @cfg {Object} [dataHigh=null] Data items representing the high values of the aggregated data.
                 */
                dataHigh: 'data',

                /**
                 * @cfg {Object} [dataLow=null] Data items representing the low values of the aggregated data.
                 */
                dataLow: 'data',

                /**
                 * @cfg {Object} [dataClose=null] Data items representing the closing values of the aggregated data.
                 */
                dataClose: 'data'
            },
            aliases: {
                /**
                 * @cfg {Object} [dataOpen=null] Data items representing the opening values of the aggregated data.
                 */
                dataOpen: 'dataY'
            },
            defaults: {
                dataHigh: null,
                dataLow: null,
                dataClose: null
            }
        }
    },

    config: {
        aggregator: {}
    },

    applyAggregator: function (aggregator, oldAggr) {
        return Ext.factory(aggregator, Ext.draw.SegmentTree, oldAggr);
    },

    constructor: function () {
        this.callParent(arguments);
    },

    processDataY: function () {
        var me = this,
            attr = me.attr,
            high = attr.dataHigh,
            low = attr.dataLow,
            close = attr.dataClose,
            open = attr.dataY;
        me.callParent(arguments);
        if (attr.dataX && open && open.length > 0) {
            if (high) {
                me.getAggregator().setData(attr.dataX, attr.dataY, high, low, close);
            } else {
                me.getAggregator().setData(attr.dataX, attr.dataY);
            }
        }
    },

    getGapWidth: function () {
        return 1;
    },

    renderClipped: function (surface, ctx, clip, rect) {
        var me = this,
            min = Math.min(clip[0], clip[2]),
            max = Math.max(clip[0], clip[2]),
            aggregates = me.getAggregator() && me.getAggregator().getAggregation(
                min, max, (max - min) / rect[2] * me.getGapWidth()
            );
        if (aggregates) {
            me.dataStart = aggregates.data.startIdx[aggregates.start];
            me.dataEnd = aggregates.data.endIdx[aggregates.end - 1];

            me.renderAggregates(aggregates.data, aggregates.start, aggregates.end, surface, ctx, clip, rect);
        }
    }
});

/**
 * @class Ext.chart.series.sprite.CandleStick
 * @extends Ext.chart.series.sprite.Aggregative
 * 
 * CandleStick series sprite.
 */
Ext.define('Ext.chart.series.sprite.CandleStick', {
    alias: 'sprite.candlestickSeries',
    extend: 'Ext.chart.series.sprite.Aggregative',
    inheritableStatics: {
        def: {
            processors: {
                raiseStyle: function (n, o) {
                    return Ext.merge({}, o || {}, n);
                },
                dropStyle: function (n, o) {
                    return Ext.merge({}, o || {}, n);
                },

                /**
                 * @cfg {Number} [barWidth=15] The bar width of the candles.
                 */
                barWidth: 'number',

                /**
                 * @cfg {Number} [padding=3] The amount of padding between candles.
                 */
                padding: 'number',

                /**
                 * @cfg {String} [ohlcType='candlestick'] Determines whether candlestick or ohlc is used.
                 */
                ohlcType: 'enums(candlestick,ohlc)'
            },
            defaults: {
                raiseStyle: {
                    strokeStyle: 'green',
                    fillStyle: 'green'
                },
                dropStyle: {
                    strokeStyle: 'red',
                    fillStyle: 'red'
                },
                planar: false,
                barWidth: 15,
                padding: 3,
                lineJoin: 'miter',
                miterLimit: 5,
                ohlcType: 'candlestick'
            },

            dirtyTriggers: {
                raiseStyle: 'raiseStyle',
                dropStyle: 'dropStyle'
            },

            updaters: {
                raiseStyle: function () {
                    this.raiseTemplate && this.raiseTemplate.setAttributes(this.attr.raiseStyle);
                },
                dropStyle: function () {
                    this.dropTemplate && this.dropTemplate.setAttributes(this.attr.dropStyle);
                }
            }
        }
    },

    candlestick: function (ctx, open, high, low, close, mid, halfWidth) {
        var minOC = Math.min(open, close),
            maxOC = Math.max(open, close);
        ctx.moveTo(mid, low);
        ctx.lineTo(mid, maxOC);

        ctx.moveTo(mid + halfWidth, maxOC);
        ctx.lineTo(mid + halfWidth, minOC);
        ctx.lineTo(mid - halfWidth, minOC);
        ctx.lineTo(mid - halfWidth, maxOC);
        ctx.closePath();

        ctx.moveTo(mid, high);
        ctx.lineTo(mid, minOC);
    },

    ohlc: function (ctx, open, high, low, close, mid, halfWidth) {
        ctx.moveTo(mid, high);
        ctx.lineTo(mid, low);
        ctx.moveTo(mid, open);
        ctx.lineTo(mid - halfWidth, open);
        ctx.moveTo(mid, close);
        ctx.lineTo(mid + halfWidth, close);
    },

    constructor: function () {
        this.callParent(arguments);
        this.raiseTemplate = new Ext.draw.sprite.Rect({parent: this});
        this.dropTemplate = new Ext.draw.sprite.Rect({parent: this});
    },

    getGapWidth: function () {
        var attr = this.attr,
            barWidth = attr.barWidth,
            padding = attr.padding;
        return barWidth + padding;
    },

    renderAggregates: function (aggregates, start, end, surface, ctx, clip) {
        var me = this,
            attr = this.attr,
            dataX = attr.dataX,
            matrix = attr.matrix,
            xx = matrix.getXX(),
            yy = matrix.getYY(),
            dx = matrix.getDX(),
            dy = matrix.getDY(),
            barWidth = attr.barWidth / xx,
            template,
            ohlcType = attr.ohlcType,
            halfWidth = Math.round(barWidth * 0.5 * xx),
            opens = aggregates.open,
            closes = aggregates.close,
            maxYs = aggregates.maxY,
            minYs = aggregates.minY,
            startIdxs = aggregates.startIdx,
            open, high, low, close, mid,
            i,
            pixelAdjust = attr.lineWidth * surface.devicePixelRatio / 2;

        pixelAdjust -= Math.floor(pixelAdjust);
        ctx.save();
        template = this.raiseTemplate;
        template.useAttributes(ctx, clip);
        ctx.beginPath();
        for (i = start; i < end; i++) {
            if (opens[i] <= closes[i]) {
                open = Math.round(opens[i] * yy + dy) + pixelAdjust;
                high = Math.round(maxYs[i] * yy + dy) + pixelAdjust;
                low = Math.round(minYs[i] * yy + dy) + pixelAdjust;
                close = Math.round(closes[i] * yy + dy) + pixelAdjust;
                mid = Math.round(dataX[startIdxs[i]] * xx + dx) + pixelAdjust;
                me[ohlcType](ctx, open, high, low, close, mid, halfWidth);
            }
        }
        ctx.fillStroke(template.attr);
        ctx.restore();

        ctx.save();
        template = this.dropTemplate;
        template.useAttributes(ctx, clip);
        ctx.beginPath();
        for (i = start; i < end; i++) {
            if (opens[i] > closes[i]) {
                open = Math.round(opens[i] * yy + dy) + pixelAdjust;
                high = Math.round(maxYs[i] * yy + dy) + pixelAdjust;
                low = Math.round(minYs[i] * yy + dy) + pixelAdjust;
                close = Math.round(closes[i] * yy + dy) + pixelAdjust;
                mid = Math.round(dataX[startIdxs[i]] * xx + dx) + pixelAdjust;
                me[ohlcType](ctx, open, high, low, close, mid, halfWidth);
            }
        }
        ctx.fillStroke(template.attr);
        ctx.restore();
    }
});

/**
 * @class Ext.chart.series.CandleStick
 * @extends Ext.chart.series.Cartesian
 * 
 * Creates a candlestick or OHLC Chart.
 *
 *     @example
 *     Ext.create('Ext.Container', {
 *         renderTo: Ext.getBody(),
 *         width: 600,
 *         height: 400,
 *         layout: 'fit',
 *         items: {
 *             xtype: 'cartesian',
 *             store: {
 *                 fields: ['time', 'open', 'high', 'low', 'close'],
 *                 data: [
 *                     {'time':new Date('Jan 1 2010').getTime(), 'open':600, 'high':614, 'low':578, 'close':590},
 *                     {'time':new Date('Jan 2 2010').getTime(), 'open':590, 'high':609, 'low':580, 'close':580},
 *                     {'time':new Date('Jan 3 2010').getTime(), 'open':580, 'high':602, 'low':578, 'close':602},
 *                     {'time':new Date('Jan 4 2010').getTime(), 'open':602, 'high':614, 'low':586, 'close':586},
 *                     {'time':new Date('Jan 5 2010').getTime(), 'open':586, 'high':602, 'low':565, 'close':565}
 *                 ]
 *             },
 *             axes: [{
 *                 type: 'numeric',
 *                 position: 'left',
 *                 fields: ['open', 'high', 'low', 'close'],
 *                 title: {
 *                     text: 'Sample Values',
 *                     fontSize: 15
 *                 },
 *                 grid: true,
 *                 minimum: 560,
 *                 maximum: 640
 *             }, {
 *                 type: 'time',
 *                 position: 'bottom',
 *                 fields: ['time'],
 *                 fromDate: new Date('Dec 31 2009'),
 *                 toDate: new Date('Jan 6 2010'),
 *                 title: {
 *                     text: 'Sample Values',
 *                     fontSize: 15
 *                 },
 *                 style: {
 *                     axisLine: false
 *                 }
 *             }],
 *             series: {
 *                 type: 'candlestick',
 *                 xField: 'time',
 *                 openField: 'open',
 *                 highField: 'high',
 *                 lowField: 'low',
 *                 closeField: 'close',
 *                 style: {
 *                     dropStyle: {
 *                         fill: 'rgb(237, 123, 43)',
 *                         stroke: 'rgb(237, 123, 43)'
 *                     },
 *                     raiseStyle: {
 *                         fill: 'rgb(55, 153, 19)',
 *                         stroke: 'rgb(55, 153, 19)'
 *                     }
 *                 }
 *             }
 *         }
 *     });
 */
Ext.define('Ext.chart.series.CandleStick', {
    extend: 'Ext.chart.series.Cartesian',
    requires: ['Ext.chart.series.sprite.CandleStick'],
    alias: 'series.candlestick',
    type: 'candlestick',
    seriesType: 'candlestickSeries',
    config: {
        /**
         * @cfg {String} openField
         * The store record field name that represents the opening value of the given period.
         */
        openField: null,
        /**
         * @cfg {String} highField
         * The store record field name that represents the highest value of the time interval represented.
         */
        highField: null,
        /**
         * @cfg {String} lowField
         * The store record field name that represents the lowest value of the time interval represented.
         */
        lowField: null,
        /**
         * @cfg {String} closeField
         * The store record field name that represents the closing value of the given period.
         */
        closeField: null
    },

    fieldCategoryY: ['Open', 'High', 'Low', 'Close'],

    themeColorCount: function() {
        return 2;
    }

});

/**
 * @abstract
 * @class Ext.chart.series.Polar
 * @extends Ext.chart.series.Series
 *
 * Common base class for series implementations that plot values using polar coordinates.
 */
Ext.define('Ext.chart.series.Polar', {

    extend: 'Ext.chart.series.Series',

    config: {
        /**
         * @cfg {Number} rotation
         * The angle in degrees at which the first polar series item should start.
         */
        rotation: 0,

        /**
         * @cfg {Number} radius
         * The radius of the polar series. Set to `null` will fit the polar series to the boundary.
         */
        radius: null,

        /**
         * @cfg {Array} center for the polar series.
         */
        center: [0, 0],

        /**
         * @cfg {Number} offsetX
         * The x-offset of center of the polar series related to the center of the boundary.
         */
        offsetX: 0,

        /**
         * @cfg {Number} offsetY
         * The y-offset of center of the polar series related to the center of the boundary.
         */
        offsetY: 0,

        /**
         * @cfg {Boolean} showInLegend
         * Whether to add the series elements as legend items.
         */
        showInLegend: true,

        /**
         * @cfg {String} xField
         * The store record field name for the labels used in the radar series.
         */
        xField: null,

        /**
         * @cfg {String} angleField
         * Alias for {@link #xField}. For compatibility with ExtJS.
         */
        angleField: null,

        /**
         * @cfg {String} yField
         * The store record field name for the deflection of the graph in the radar series,
         * or the length of the slices in the pie series.
         */
        yField: null,

        /**
         * @cfg {String} lengthField
         * Alias for {@link #yField}. For compatibility with ExtJS.
         */
        lengthField: null,

        xAxis: null,

        yAxis: null
    },

    directions: ['X', 'Y'],
    fieldCategoryX: ['X'],
    fieldCategoryY: ['Y'],

    getAngleField: function () {
        return this.getXField();
    },

    setAngleField: function (f) {
        return this.setXField(f);
    },

    getLengthField: function () {
        return this.getYField();
    },

    setLengthField: function (f) {
        return this.setYField(f);
    },

    applyXAxis: function (newAxis, oldAxis) {
        return this.getChart().getAxis(newAxis) || oldAxis;
    },

    applyYAxis: function (newAxis, oldAxis) {
        return this.getChart().getAxis(newAxis) || oldAxis;
    },

    getXRange: function () {
        return [this.dataRange[0], this.dataRange[2]];
    },

    getYRange: function () {
        return [this.dataRange[1], this.dataRange[3]];
    },

    themeColorCount: function() {
        var me = this,
            store = me.getStore(),
            count = store && store.getCount() || 0;
        return count;

    },

    getDefaultSpriteConfig: function () {
        return {
            type: this.seriesType,
            renderer: this.getRenderer(),
            centerX: 0,
            centerY: 0,
            rotationCenterX: 0,
            rotationCenterY: 0
        };
    },

    applyRotation: function (rotation) {
        var twoPie = Math.PI * 2;
        return (rotation % twoPie + Math.PI) % twoPie - Math.PI;
    },

    updateRotation: function (rotation) {
        var sprites = this.getSprites();
        if (sprites && sprites[0]) {
            sprites[0].setAttributes({
                baseRotation: rotation
            });
        }
    }
});

/**
 * @class Ext.chart.series.Gauge
 * @extends Ext.chart.series.Series
 * 
 * Creates a Gauge Chart.
 *
 *     @example
 *     Ext.create('Ext.Container', {
 *         renderTo: Ext.getBody(),
 *         width: 600,
 *         height: 400,
 *         layout: 'fit',
 *         items: {
 *             xtype: 'polar',
 *             series: {
 *                 type: 'gauge',
 *                 minimum: 100,
 *                 maximum: 800,
 *                 value: 400,
 *                 donut: 30
 *             }
 *         }
 *     });
 */
Ext.define('Ext.chart.series.Gauge', {
    alias: 'series.gauge',
    extend: 'Ext.chart.series.Polar',
    type: 'gauge',
    seriesType: 'pieslice',

    requires: [
        'Ext.draw.sprite.Sector'
    ],

    config: {
        /**
         * @cfg {String} angleField
         * @deprecated Use `field` directly
         * The store record field name to be used for the gauge angles.
         * The values bound to this field name must be positive real numbers.
         */
        angleField: null,

        /**
         * @cfg {String} field
         * The store record field name to be used for the gauge value.
         * The values bound to this field name must be positive real numbers.
         */
        field: null,

        /**
         * @cfg {Boolean} needle
         * If true, display the gauge as a needle, otherwise as a sector.
         */
        needle: false,

        /**
         * @cfg {Number} needleLengthRatio
         * @deprecated Use `needleLength` directly
         * Ratio of the length of needle compared to the radius of the entire disk.
         */
        needleLengthRatio: undefined,

        /**
         * @cfg {Number} needleLength
         * Percentage of the length of needle compared to the radius of the entire disk.
         */
        needleLength: 90,

        /**
         * @cfg {Number} needleWidth
         * Width of the needle in pixels.
         */
        needleWidth: 4,

        /**
         * @cfg {Number} donut
         * Percentage of the radius of the donut hole compared to the entire disk.
         */
        donut: 30,

        /**
         * @cfg {Boolean} showInLegend
         * Whether to add the gauge chart elements as legend items.
         */
        showInLegend: false,

        /**
         * @cfg {Number} value
         * Directly sets the displayed value of the gauge.
         * It is ignored if {@link #field} is provided.
         */
        value: null,

        /**
         * @cfg {Array} colors (required)
         * An array of color values which is used for the needle and the `sectors`.
         */
        colors: null,

        /**
         * @cfg {Array} sectors
         * Allows to paint sectors of different colors in the background of the gauge,
         * with optional labels.
         *
         * It can be an array of numbers (each between `minimum` and `maximum`) that
         * define the highest value of each sector. For N sectors, only (N-1) values are
         * needed because it is assumed that the first sector starts at `minimum` and the
         * last sector ends at `maximum`. Example: a water temperature gauge that is blue 
         * below 20C, red above 80C, gray in-between, and with an orange needle...
         *
         *      minimum: 0,
         *      maximum: 100,
         *      sectors: [20, 80],
         *      colors: ['orange', 'blue', 'lightgray', 'red']
         *
         * It can be also an array of objects, each with the following properties:
         * 
         * @cfg {Number} sectors.start The starting value of the sector. If omitted, it
         * uses the previous sector's `end` value or the chart's `minimum`.
         * @cfg {Number} sectors.end The ending value of the sector. If omitted, it uses
         * the `maximum` defined for the chart.
         * @cfg {String} sectors.label The label for this sector. Labels are styled using
         * the series' {@link Ext.chart.series.Series#label label} config.
         * @cfg {String} sectors.color The color of the sector. If omitted, it uses one
         * of the `colors` defined for the series or for the chart.
         * @cfg {Object} sectors.style An additional style object for the sector (for
         * instance to set the opacity or to draw a line of a different color around the
         * sector).
         *
         *      minimum: 0,
         *      maximum: 100,
         *      sectors: [{
         *              end: 20,
         *              label: 'Cold',
         *              color: 'aqua'
         *          },
         *          {
         *              end: 80,
         *              label: 'Temp.',
         *              color: 'lightgray',
         *              style: { strokeStyle:'black', strokeOpacity:1, lineWidth:1 }
         *          },
         *          {
         *              label: 'Hot',
         *              color: 'tomato'
         *          }]
         */
        sectors: null,

        /**
         * @cfg {Number} minimum
         * The minimum value of the gauge.
         */
        minimum: 0,

        /**
         * @cfg {Number} maximum
         * The maximum value of the gauge.
         */
        maximum: 100,

        rotation: 0,

        /**
        * @cfg {Number} totalAngle
        * The size of the sector that the series will occupy.
        */
        totalAngle: Math.PI / 2,

        rect: [0, 0, 1, 1],

        center: [0.5, 0.75],

        radius: 0.5,

        /**
         * @cfg {Boolean} wholeDisk Indicates whether to show the whole disk or only the marked part.
         */
        wholeDisk: false
    },

    coordinateX: function () {
        return this.coordinate('X', 0, 2);
    },

    coordinateY: function () {
        return this.coordinate('Y', 1, 2);
    },

    updateNeedle: function(needle) {
        var me = this,
            sprites = me.getSprites(),
            angle = me.valueToAngle(me.getValue());

        if (sprites && sprites.length) {
            sprites[0].setAttributes({
                startAngle: (needle ? angle : 0),
                endAngle: angle,
                strokeOpacity: (needle ? 1 : 0),
                lineWidth: (needle ? me.getNeedleWidth() : 0)
            });
            me.doUpdateStyles();
        }
    },

    themeColorCount: function() {
        var me = this,
            store = me.getStore(),
            count = store && store.getCount() || 0;
        return count + (me.getNeedle() ? 0 : 1);

    },

    updateColors: function (colors, oldColors) {
        var me = this,
            sectors = me.getSectors(),
            sectorCount = sectors && sectors.length,
            sprites = me.getSprites(),
            newColors = Ext.Array.clone(colors),
            colorCount = colors && colors.length,
            i;

        if (!colorCount || !colors[0]) {
            return;
        }

        // Make sure the 'sectors' colors are not overridden.
        for (i = 0; i < sectorCount; i++) {
            newColors[i+1] = sectors[i].color || newColors[i+1] || colors[i%colorCount];
        }

//        if (sprites.length) {
            sprites[0].setAttributes({
                strokeStyle: newColors[0]
            });
//        }

        this.setSubStyle({
            fillStyle: newColors,
            strokeStyle: newColors
        });
        this.doUpdateStyles();
    },
    
    updateAngleField: function (angleField) {
        this.setField(angleField);
    },
    
    updateNeedleLengthRatio: function (needleLengthRatio) {
        this.setNeedleLength(needleLengthRatio * 100);
    },

    updateRect: function (rect) {
        var wholeDisk = this.getWholeDisk(),
            halfTotalAngle = wholeDisk ? Math.PI : this.getTotalAngle() / 2,
            donut = this.getDonut() / 100,
            width, height, radius;
        if (halfTotalAngle <= Math.PI / 2) {
            width = 2 * Math.sin(halfTotalAngle);
            height = 1 - donut * Math.cos(halfTotalAngle);
        } else {
            width = 2;
            height = 1 - Math.cos(halfTotalAngle);
        }

        radius = Math.min(rect[2] / width, rect[3] / height);
        this.setRadius(radius);
        this.setCenter([rect[2] / 2, radius + (rect[3] - height * radius) / 2]);
    },

    updateCenter: function (center) {
        this.setStyle({
            centerX: center[0],
            centerY: center[1],
            rotationCenterX: center[0],
            rotationCenterY: center[1]
        });
        this.doUpdateStyles();
    },

    updateRotation: function (rotation) {
        this.setStyle({
            rotationRads: rotation - (this.getTotalAngle() + Math.PI) / 2
        });
        this.doUpdateStyles();
    },

    doUpdateShape: function (radius, donut) {
        var endRhoArray,
            sectors = this.getSectors(),
            sectorCount = (sectors && sectors.length) || 0,
            needleLength = this.getNeedleLength() / 100;

        // Initialize an array that contains the endRho for each sprite.
        // The first sprite is for the needle, the others for the gauge background sectors. 
        // Note: SubStyle arrays are handled in series.getStyleByIndex().
        endRhoArray = [radius * needleLength, radius];
        while (sectorCount --) {
            endRhoArray.push(radius);
        }

        this.setSubStyle({
            endRho: endRhoArray,
            startRho: radius / 100 * donut
        });
        this.doUpdateStyles();
    },

    updateRadius: function (radius) {
        var donut = this.getDonut();
        this.doUpdateShape(radius, donut);
    },

    updateDonut: function (donut) {
        var radius = this.getRadius();
        this.doUpdateShape(radius, donut);
    },

    valueToAngle: function(value) {
        value = this.applyValue(value);
        return this.getTotalAngle() * (value - this.getMinimum()) / (this.getMaximum() - this.getMinimum());
    },

    applyValue: function (value) {
        return Math.min(this.getMaximum(), Math.max(value, this.getMinimum()));
    },

    updateValue: function (value) {
        var me = this,
            needle = me.getNeedle(),
            angle = me.valueToAngle(value),
            sprites = me.getSprites();

        sprites[0].rendererData.value = value;
        sprites[0].setAttributes({
            startAngle: (needle ? angle : 0),
            endAngle: angle
        });
        me.doUpdateStyles();
    },

    processData: function () {
        var me = this,
            store = me.getStore(),
            axis, min, max,
            fx, fxDuration,
            record = store && store.first(),
            field, value;

        if (record) {
            field = me.getField();
            if (field) {
                value = record.get(field);
            }
        }

        if (axis = me.getXAxis()) {
            min = axis.getMinimum();
            max = axis.getMaximum();
            // Animating the axis here can lead to weird looking results.
            fx = axis.getSprites()[0].fx;
            fxDuration = fx.getDuration();
            fx.setDuration(0);
            if (Ext.isNumber(min)) {
                me.setMinimum(min);
            } else {
                axis.setMinimum(me.getMinimum());
            }
            if (Ext.isNumber(max)) {
                me.setMaximum(max);
            } else {
                axis.setMaximum(me.getMaximum());
            }
            fx.setDuration(fxDuration);
        }
        if (!Ext.isNumber(value)) {
            value = me.getMinimum();
        }
        me.setValue(value);
    },

    getDefaultSpriteConfig: function () {
        return {
            type: this.seriesType,
            renderer: this.getRenderer(),
            fx: {
                customDurations: {
                    translationX: 0,
                    translationY: 0,
                    rotationCenterX: 0,
                    rotationCenterY: 0,
                    centerX: 0,
                    centerY: 0,
                    startRho: 0,
                    endRho: 0,
                    baseRotation: 0
                }
            }
        };
    },

    normalizeSectors: function(sectors) {
        // Make sure all the sectors in the array have a legit start and end.
        // Note: the array is modified in-place.
        var me = this,
            sectorCount = (sectors && sectors.length) || 0,
            i, value, start, end;
    
        if (sectorCount) {
            for (i = 0; i < sectorCount; i++) {
                value = sectors[i];
                if (typeof value === 'number') {
                    sectors[i] = {
                        start: (i > 0 ? sectors[i-1].end : me.getMinimum()),
                        end: Math.min(value, me.getMaximum())
                    };
                    if (i == (sectorCount - 1) && sectors[i].end < me.getMaximum()) {
                        sectors[i+1] = {
                            start: sectors[i].end,
                            end: me.getMaximum()
                        };
                    }
                } else {
                    if (typeof value.start === 'number') {
                        start = Math.max(value.start, me.getMinimum());
                    } else {
                        start = (i > 0 ? sectors[i-1].end : me.getMinimum());
                    }
                    if (typeof value.end === 'number') {
                        end = Math.min(value.end, me.getMaximum());
                    } else {
                        end = me.getMaximum();
                    }
                    sectors[i].start = start;
                    sectors[i].end = end;
                }
            }
        } else {
            sectors = [{
                start: me.getMinimum(),
                end: me.getMaximum()
            }];
        }
        return sectors;
    },

    getSprites: function () {
        var me = this,
            store = me.getStore(),
            value = me.getValue(),
            i, ln;

        // The store must be initialized, or the value must be set
        if (!store && !Ext.isNumber(value)) {
            return [];
        }

        // Return cached sprites
        var chart = me.getChart(),
            animation = me.getAnimation() || chart && chart.getAnimation(),
            sprites = me.sprites,
            spriteIndex = 0,
            sprite, sectors, attr, rendererData,
            lineWidths = [];    // Hack to avoid having the lineWidths overwritten by the one specified in the theme.
                                // In fact, all the style properties from the needle and sectors should go to the series subStyle.

        if (sprites && sprites.length) {
            sprites[0].fx.setConfig(animation);
            return sprites;
        }

        rendererData = {
            store: store,
            field: me.getField(),
            value: value,
            series: me
        };

        // Create needle sprite
        sprite = me.createSprite();
        sprite.setAttributes({
            zIndex: 10
        }, true);
        sprite.rendererData = rendererData;
        sprite.rendererIndex = spriteIndex++;
        lineWidths.push(me.getNeedleWidth());

        // Create background sprite(s)
        me.getLabel().getTemplate().setField(true); // Enable labels
        sectors = me.normalizeSectors(me.getSectors());
        for (i = 0, ln = sectors.length; i < ln; i++) {
            attr = {
                startAngle: me.valueToAngle(sectors[i].start),
                endAngle: me.valueToAngle(sectors[i].end),
                label: sectors[i].label,
                fillStyle: sectors[i].color,
                strokeOpacity: 0,
                rotateLabels: false,
                doCallout: false,           // Show labels inside sectors.
                labelOverflowPadding: -1    // Allow labels to overlap.
            };
            Ext.apply(attr, sectors[i].style);
            sprite = me.createSprite();
            sprite.rendererData = rendererData;
            sprite.rendererIndex = spriteIndex++;
            sprite.setAttributes(attr, true);
            lineWidths.push(attr.lineWidth);
        }
        me.setSubStyle({ lineWidth: lineWidths });

        me.doUpdateStyles();
        return sprites;
    }
});


/**
 * @private
 */
Ext.define('Ext.chart.series.ItemPublisher', {
    extend: 'Ext.event.publisher.Publisher',

    targetType: 'series',

    handledEvents: [
    /**
     * @event itemmousemove
     * Fires when the mouse is moved on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
        'itemmousemove',
    /**
     * @event itemmouseup
     * Fires when a mouseup event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
        'itemmouseup',
    /**
     * @event itemmousedown
     * Fires when a mousedown event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
        'itemmousedown',
    /**
     * @event itemmouseover
     * Fires when the mouse enters a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
        'itemmouseover',
    /**
     * @event itemmouseout
     * Fires when the mouse exits a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
        'itemmouseout',
    /**
     * @event itemclick
     * Fires when a click event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
        'itemclick',
    /**
     * @event itemdoubleclick
     * Fires when a doubleclick event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
        'itemdoubleclick',
    /**
     * @event itemtap
     * Fires when a tap event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
        'itemtap',
    /**
     * @event itemtapstart
     * Fires when a tapstart event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
        'itemtapstart',
    /**
     * @event itemtapend
     * Fires when a tapend event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
        'itemtapend',
    /**
     * @event itemtapcancel
     * Fires when a tapcancel event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
        'itemtapcancel',
    /**
     * @event itemtaphold
     * Fires when a taphold event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
        'itemtaphold',
    /**
     * @event itemdoubletap
     * Fires when a doubletap event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
        'itemdoubletap',
    /**
     * @event itemsingletap
     * Fires when a singletap event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
        'itemsingletap',
    /**
     * @event itemtouchstart
     * Fires when a touchstart event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
        'itemtouchstart',
    /**
     * @event itemtouchmove
     * Fires when a touchmove event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
        'itemtouchmove',
    /**
     * @event itemtouchend
     * Fires when a touchend event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
        'itemtouchend',
    /**
     * @event itemdragstart
     * Fires when a dragstart event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
        'itemdragstart',
    /**
     * @event itemdrag
     * Fires when a drag event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
        'itemdrag',
    /**
     * @event itemdragend
     * Fires when a dragend event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
        'itemdragend',
    /**
     * @event itempinchstart
     * Fires when a pinchstart event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
        'itempinchstart',
    /**
     * @event itempinch
     * Fires when a pinch event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
        'itempinch',
    /**
     * @event itempinchend
     * Fires when a pinchend event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
        'itempinchend',
    /**
     * @event itemswipe
     * Fires when a swipe event occurs on a series item.
     * @param {Ext.chart.series.Series} series
     * @param {Object} item
     * @param {Event} event
     */
        'itemswipe'
    ],

    delegationRegex: /^item([a-z]+)$/i,

    getSubscribers: function (chartId) {
        var subscribers = this.subscribers;

        if (!subscribers.hasOwnProperty(chartId)) {
            subscribers[chartId] = {};
        }

        return subscribers[chartId];
    },

    subscribe: function (target, eventName) {
        var match = target.match(this.idSelectorRegex),
            dispatcher = this.dispatcher,
            targetType = this.targetType,
            series, id;

        if (!match) {
            return false;
        }

        id = match[1];
        series = Ext.ComponentManager.get(id);
        if (!series) {
            return false;
        }

        if (!series.getChart()) {
            dispatcher.addListener(targetType, target, 'chartattached', 'attachChart', this, [series, eventName], 'before');
        } else {
            this.attachChart(series.getChart(), [series, eventName]);
        }

        return true;
    },

    attachChart: function (chart, args) {
        var dispatcher = this.dispatcher,
            targetType = this.targetType,
            series = args[0],
            eventName = args[1],
            subscribers = this.getSubscribers(chart.getId()),
            match = eventName.match(this.delegationRegex);
        if (match) {
            var chartEventName = match[1];
            if (!subscribers.hasOwnProperty(eventName)) {
                subscribers[eventName] = [];
                dispatcher.addListener(targetType, '#' + series.getId(), 'chartdetached', 'detachChart', this, [series, eventName, subscribers], 'after');
                chart.element.on(chartEventName, "relayMethod", this, [chart, eventName]);
            }
            subscribers[eventName].push(series);
            return true;
        } else {
            return false;
        }
    },

    unsubscribe: function (target, eventName) {
        var match = target.match(this.idSelectorRegex),
            dispatcher = this.dispatcher,
            targetType = this.targetType,
            series, id;

        if (!match) {
            return false;
        }

        id = match[1];
        series = Ext.ComponentManager.get(id);
        if (!series) {
            return false;
        }

        dispatcher.removeListener(targetType, target, 'chartattached', 'attachChart', this, 'before');
        if (series.getChart()) {
            this.detachChart(series.getChart(), [series, eventName]);
        }
        return true;
    },

    detachChart: function (chart, args) {
        var dispatcher = this.dispatcher,
            targetType = this.targetType,
            series = args[0],
            eventName = args[1],
            subscribers = this.getSubscribers(chart.getId()),
            match = eventName.match(this.delegationRegex),
            index, seriesArray;
        if (match) {
            var chartEventName = match[1];
            if (subscribers.hasOwnProperty(eventName)) {
                seriesArray = subscribers[eventName];
                index = Ext.Array.indexOf(seriesArray, series);
                if (index > -1) {
                    seriesArray.splice(index, 1);
                }
                if (seriesArray.length === 0) {
                    chart.element.un(chartEventName, "relayMethod", this, [chart, eventName]);
                    dispatcher.removeListener(targetType, '#' + series.getId(), 'chartdetached', 'detachChart', this, 'after');
                    delete subscribers[eventName];
                }
            }
        }
    },

    relayMethod: function (e, sender, args) {
        var chart = args[0],
            eventName = args[1],
            dispatcher = this.dispatcher,
            targetType = this.targetType,
            chartXY = chart.getEventXY(e),
            x = chartXY[0],
            y = chartXY[1],
            subscriber = this.getSubscribers(chart.getId())[eventName],
            i, ln;
        if (subscriber) {
            for (i = 0, ln = subscriber.length; i < ln; i++) {
                var series = subscriber[i],
                    item = series.getItemForPoint(x, y);
                if (item) {
                    // TODO: Don't stop at the first item.
                    // Depending on the selectionTolerance, there might be an item in another
                    // series that's closer to the event location. See test case 3943c.
                    dispatcher.doDispatchEvent(targetType, '#' + series.getId(), eventName, [series, item, e]);
                    return;
                }
            }
        }
    }

}, function () {

});

/**
 * @class Ext.chart.series.sprite.Line
 * @extends Ext.chart.series.sprite.Aggregative
 *
 * Line series sprite.
 */
Ext.define('Ext.chart.series.sprite.Line', {
    alias: 'sprite.lineSeries',
    extend: 'Ext.chart.series.sprite.Aggregative',

    inheritableStatics: {
        def: {
            processors: {
                /**
                 * @cfg {Boolean} [smooth=false]
                 * `true` if the sprite uses line smoothing.
                 * Don't enable this if your data has gaps: NaN, undefined, etc.
                 */
                smooth: 'bool',
                /**
                 * @cfg {Boolean} [fillArea=false]
                 * `true` if the sprite paints the area underneath the line.
                 */
                fillArea: 'bool',
                /**
                 * @cfg {Boolean} [step=false]
                 * `true` if the line uses steps instead of straight lines to connect the dots.
                 * It is ignored if `smooth` is `true`.
                 */
                step: 'bool',
                /**
                 * @cfg {Boolean} [preciseStroke=true]
                 * `true` if the line uses precise stroke.
                 */
                preciseStroke: 'bool',
                /**
                 * @private
                 * The x-axis associated with the Line series.
                 * We need to know the position of the x-axis to fill the area underneath
                 * the stroke properly.
                 */
                xAxis: 'default',
                /**
                 * @cfg {Number} [yCap=Math.pow(2, 20)]
                 * Absolute maximum y-value.
                 * Larger values will be capped to avoid rendering issues.
                 */
                yCap: 'default' // The 'default' processor is used here as we don't want this attribute to animate.
            },

            defaults: {
                smooth: false,
                fillArea: false,
                step: false,
                preciseStroke: true,
                xAxis: null,
                yCap: Math.pow(2, 20),
                yJump: 50
            },

            dirtyTriggers: {
                dataX: 'dataX,bbox,smooth',
                dataY: 'dataY,bbox,smooth',
                smooth: 'smooth'
            },

            updaters: {
                smooth: function (attr) {
                    var dataX = attr.dataX,
                        dataY = attr.dataY;
                    if (attr.smooth && dataX && dataY && dataX.length > 2 && dataY.length > 2) {
                        this.smoothX = Ext.draw.Draw.spline(dataX);
                        this.smoothY = Ext.draw.Draw.spline(dataY);
                    } else {
                        delete this.smoothX;
                        delete this.smoothY;
                    }
                }
            }
        }
    },

    list: null,

    updatePlainBBox: function (plain) {
        var attr = this.attr,
            ymin = Math.min(0, attr.dataMinY),
            ymax = Math.max(0, attr.dataMaxY);
        plain.x = attr.dataMinX;
        plain.y = ymin;
        plain.width = attr.dataMaxX - attr.dataMinX;
        plain.height = ymax - ymin;
    },

    drawStrip: function (ctx, strip) {
        ctx.moveTo(strip[0], strip[1]);
        for (var i = 2, ln = strip.length; i < ln; i += 2) {
            ctx.lineTo(strip[i], strip[i + 1]);
        }
    },

    drawStraightStroke: function (surface, ctx, start, end, list, xAxis) {
        var me = this,
            attr = me.attr,
            renderer = attr.renderer,
            step = attr.step,
            needMoveTo = true,
            abs = Math.abs,
            lineConfig = {
                type: 'line',
                smooth: false,
                step: step
            },
            strip = [], // Stores last continuous segment of the stroke.
            lineConfig, changes, stripStartX,
            x, y, x0, y0, x1, y1, i;

        for (i = 3; i < list.length; i += 3) {
            x0 = list[i - 3];
            y0 = list[i - 2];
            x = list[i];
            y = list[i + 1];
            x1 = list[i + 3];
            y1 = list[i + 4];

            if (renderer) {
                lineConfig.x = x;
                lineConfig.y = y;
                lineConfig.x0 = x0;
                lineConfig.y0 = y0;
                changes = renderer.call(me, me, lineConfig, me.rendererData, start + i/3);
            }

            if (Ext.isNumber(x + y + x0 + y0)) {
                if (needMoveTo) {
                    ctx.beginPath();
                    ctx.moveTo(x0, y0);
                    strip.push(x0, y0);
                    stripStartX = x0;
                    needMoveTo = false;
                }
            } else {
                continue;
            }

            if (step) {
                ctx.lineTo(x, y0);
                strip.push(x, y0);
            }
            ctx.lineTo(x, y);
            strip.push(x, y);

            if ( changes || !(Ext.isNumber(x1 + y1)) ) {
                ctx.save();
                    Ext.apply(ctx, changes);

                    if (attr.fillArea) {
                        ctx.lineTo(x, xAxis);
                        ctx.lineTo(stripStartX, xAxis);
                        ctx.closePath();
                        ctx.fill();
                    }

                    // Draw the line on top of the filled area.
                    ctx.beginPath();
                    me.drawStrip(ctx, strip);
                    strip = [];
                    ctx.stroke();
                ctx.restore();

                ctx.beginPath();
                needMoveTo = true;
            }
        }
    },

    calculateScale: function (count, end) {
        var power = 0,
            n = count;
        while (n < end && count > 0) {
            power++;
            n += count >> power;
        }
        return Math.pow(2, power > 0 ? power - 1 : power);
    },

    drawSmoothStroke: function (surface, ctx, start, end, list, xAxis) {
        var me = this,
            attr = me.attr,
            step = attr.step,
            matrix = attr.matrix,
            xx = matrix.getXX(),
            yy = matrix.getYY(),
            dx = matrix.getDX(),
            dy = matrix.getDY(),
            smoothX = me.smoothX,
            smoothY = me.smoothY,
            scale = me.calculateScale(attr.dataX.length, end),
            cx1, cy1, cx2, cy2, x, y, x0, y0,
            i, j, changes,
            lineConfig = {
                type: 'line',
                smooth: true,
                step: step
            };

        ctx.beginPath();
        ctx.moveTo(smoothX[start * 3] * xx + dx, smoothY[start * 3] * yy + dy);
        for (i = 0, j = start * 3 + 1; i < list.length - 3; i += 3, j += 3 * scale) {
            cx1 = smoothX[j] * xx + dx;
            cy1 = smoothY[j] * yy + dy;
            cx2 = smoothX[j + 1] * xx + dx;
            cy2 = smoothY[j + 1] * yy + dy;
            x = list[i + 3];
            y = list[i + 4];
            x0 = list[i];
            y0 = list[i + 1];
            if (attr.renderer) {
                lineConfig.x0 = x0;
                lineConfig.y0 = y0;
                lineConfig.cx1 = cx1;
                lineConfig.cy1 = cy1;
                lineConfig.cx2 = cx2;
                lineConfig.cy2 = cy2;
                lineConfig.x = x;
                lineConfig.y = y;
                changes = attr.renderer.call(me, me, lineConfig, me.rendererData, start + i/3 + 1);
                ctx.save();
                Ext.apply(ctx, changes);
                if (attr.fillArea) {
                    ctx.moveTo(x0, y0);
                    ctx.bezierCurveTo(cx1, cy1, cx2, cy2, x, y);
                    ctx.lineTo(x, xAxis);
                    ctx.lineTo(x0, xAxis);
                    ctx.lineTo(x0, y0);
                    ctx.closePath();
                    ctx.fill();
                    ctx.beginPath();
                }
                // Draw the line on top of the filled area.
                ctx.moveTo(x0, y0);
                ctx.bezierCurveTo(cx1, cy1, cx2, cy2, x, y);
                ctx.stroke();
                ctx.moveTo(x0, y0);
                ctx.closePath();
                ctx.restore();
                ctx.beginPath();
                ctx.moveTo(x, y);
            } else {
                ctx.bezierCurveTo(cx1, cy1, cx2, cy2, x, y);
            }
        }
    },

    drawLabel: function (text, dataX, dataY, labelId, rect) {
        var me = this,
            attr = me.attr,
            label = me.getBoundMarker('labels')[0],
            labelTpl = label.getTemplate(),
            labelCfg = me.labelCfg || (me.labelCfg = {}),
            surfaceMatrix = me.surfaceMatrix,
            labelX, labelY,
            labelOverflowPadding = attr.labelOverflowPadding,
            flipXY = attr.flipXY,
            halfHeight, labelBBox,
            changes, hasPendingChanges;

        // The coordinates below (data point converted to surface coordinates)
        // are just for the renderer to give it a notion of where the label will be positioned.
        // The actual position of the label will be different
        // (unless the renderer returns x/y coordinates in the changes object)
        // and depend on several things including the size of the text,
        // which has to be measured after the renderer call,
        // since text can be modified by the renderer.
        labelCfg.x = surfaceMatrix.x(dataX, dataY);
        labelCfg.y = surfaceMatrix.y(dataX, dataY);

        if (flipXY) {
            labelCfg.rotationRads = Math.PI * 0.5;
        } else {
            labelCfg.rotationRads = 0;
        }

        labelCfg.text = text;

        if (labelTpl.attr.renderer) {
            changes = labelTpl.attr.renderer.call(me, text, label, labelCfg, me.rendererData, labelId);
            if (typeof changes === 'string') {
                labelCfg.text = changes;
            } else if (typeof changes === 'object') {
                if ('text' in changes) {
                    labelCfg.text = changes.text;
                }
                hasPendingChanges = true;
            }
        }

        labelBBox = me.getMarkerBBox('labels', labelId, true);
        if (!labelBBox) {
            me.putMarker('labels', labelCfg, labelId);
            labelBBox = me.getMarkerBBox('labels', labelId, true);
        }

        halfHeight = labelBBox.height / 2;
        labelX = dataX;

        switch (labelTpl.attr.display) {
            case 'under':
                labelY = dataY - halfHeight - labelOverflowPadding;
                break;
            case 'rotate':
                labelX += labelOverflowPadding;
                labelY = dataY - labelOverflowPadding;
                labelCfg.rotationRads = -Math.PI / 4;
                break;
            default: // 'over'
                labelY = dataY + halfHeight + labelOverflowPadding;
        }

        labelCfg.x = surfaceMatrix.x(labelX, labelY);
        labelCfg.y = surfaceMatrix.y(labelX, labelY);

        if (hasPendingChanges) {
            Ext.apply(labelCfg, changes);
        }

        me.putMarker('labels', labelCfg, labelId);
    },

    drawMarker: function (x, y, index) {
        var me = this,
            attr = me.attr,
            renderer = attr.renderer,
            surfaceMatrix = me.surfaceMatrix,
            markerCfg = {};

        if (renderer && me.boundMarkers.markers) {
            markerCfg.type = 'marker';
            markerCfg.x = x;
            markerCfg.y = y;
            markerCfg = renderer.call(me, me, markerCfg, me.rendererData, index) || {};
        }
        markerCfg.translationX = surfaceMatrix.x(x, y);
        markerCfg.translationY = surfaceMatrix.y(x, y);
        me.putMarker('markers', markerCfg, index, !renderer);
    },

    drawStroke: function (surface, ctx, start, end, list, xAxis) {
        var me = this,
            isSmooth = me.attr.smooth && me.smoothX && me.smoothY;

        if (isSmooth) {
            me.drawSmoothStroke(surface, ctx, start, end, list, xAxis);
        } else {
            me.drawStraightStroke(surface, ctx, start, end, list, xAxis);
        }
    },

    renderAggregates: function (aggregates, start, end, surface, ctx, clip, rect) {
        var me = this,
            attr = me.attr,
            dataX = attr.dataX,
            dataY = attr.dataY,
            labels = attr.labels,
            xAxis = attr.xAxis,
            yCap = attr.yCap,
            isSmooth = attr.smooth && me.smoothX && me.smoothY,
            drawLabels = labels && me.getBoundMarker('labels'),
            drawMarkers = me.getBoundMarker('markers'),
            matrix = attr.matrix,
            pixel = surface.devicePixelRatio,
            xx = matrix.getXX(),
            yy = matrix.getYY(),
            dx = matrix.getDX(),
            dy = matrix.getDY(),
            list = me.list || (me.list = []),
            minXs = aggregates.minX,
            maxXs = aggregates.maxX,
            minYs = aggregates.minY,
            maxYs = aggregates.maxY,
            idx = aggregates.startIdx,
            isContinuousLine = true,
            xAxisOrigin, isVerticalX,
            x, y, i, index;

        me.rendererData = {store: me.getStore()};
        list.length = 0;

        // Say we have 7 y-items (attr.dataY): [20, 19, 17, 15, 11, 10, 14]
        //         and 7 x-items (attr.dataX): [0,   1,  2,  3,  4,  5,  6].
        // Then aggregates.startIdx is an aggregated index,
        // where every other item is skipped on each aggregation level:
        // [0, 1, 2, 3, 4, 5, 6,
        //  0, 2, 4, 6,
        //  0, 4,
        //  0]
        // aggregates.minY
        // [20, 19, 17, 15, 11, 10, 14,
        //  19, 15, 10, 14,
        //  15, 10,
        //  10]
        // aggregates.maxY
        // [20, 19, 17, 15, 11, 10, 14,
        //  20, 17, 11, 14,
        //  20, 14,
        //  20]
        // aggregates.minX is
        // [0, 1, 2, 3, 4, 5, 6,
        //  1, 3, 5, 6, // TODO: why this order for min?
        //  3, 5,       // TODO: why this inconsistency?
        //  5]
        // aggregates.maxX is
        // [0, 1, 2, 3, 4, 5, 6,
        //  0, 2, 4, 6,
        //  0, 6,
        //  0]

        // Create a list of the form [x0, y0, idx0, x1, y1, idx1, ...],
        // where each x,y pair is a coordinate representing original data point
        // at the idx position.
        for (i = start; i < end; i++) {
            var minX = minXs[i],
                maxX = maxXs[i],
                minY = minYs[i],
                maxY = maxYs[i];

            if (minX < maxX) {
                list.push(minX * xx + dx, minY * yy + dy, idx[i]);
                list.push(maxX * xx + dx, maxY * yy + dy, idx[i]);
            } else if (minX > maxX) {
                list.push(maxX * xx + dx, maxY * yy + dy, idx[i]);
                list.push(minX * xx + dx, minY * yy + dy, idx[i]);
            } else {
                list.push(maxX * xx + dx, maxY * yy + dy, idx[i]);
            }
        }

        if (list.length) {
            for (i = 0; i < list.length; i += 3) {
                x = list[i];
                y = list[i + 1];
                if (Ext.isNumber(x + y)) {
                    if (y > yCap) {
                        y = yCap;
                    } else if (y < -yCap) {
                        y = -yCap;
                    }
                    list[i + 1] = y;
                } else {
                    isContinuousLine = false;
                    continue;
                }
                index = list[i + 2];
                if (drawMarkers) {
                    me.drawMarker(x, y, index);
                }
                if (drawLabels && labels[index]) {
                    me.drawLabel(labels[index], x, y, index, rect);
                }
            }

            me.isContinuousLine = isContinuousLine;
            if (isSmooth && !isContinuousLine) {
                Ext.Error.raise("Line smoothing in only supported for gapless data, " +
                    "where all data points are finite numbers.");
            }

            if (xAxis) {
                isVerticalX = xAxis.getAlignment() === 'vertical';
                if (Ext.isNumber(xAxis.floatingAtCoord)) {
                    xAxisOrigin = (isVerticalX ? rect[2] : rect[3]) - xAxis.floatingAtCoord;
                } else {
                    xAxisOrigin = isVerticalX ? rect[0] : rect[1];
                }
            } else {
                Ext.Error.raise("A chart must have two ortogonal axes configured " +
                    "in order to use the Line series.");
            }

            if (attr.preciseStroke) {
                if (attr.fillArea) {
                    ctx.fill();
                }
                if (attr.transformFillStroke) {
                    attr.inverseMatrix.toContext(ctx);
                }
                me.drawStroke(surface, ctx, start, end, list, xAxisOrigin);
                if (attr.transformFillStroke) {
                    attr.matrix.toContext(ctx);
                }
                ctx.stroke();
            } else {
                me.drawStroke(surface, ctx, start, end, list, xAxisOrigin);

                if (isContinuousLine && isSmooth && attr.fillArea && !attr.renderer) {
                    var lastPointX = dataX[dataX.length - 1] * xx + dx + pixel,
                        lastPointY = dataY[dataY.length - 1] * yy + dy,
                        firstPointX = dataX[0] * xx + dx - pixel,
                        firstPointY = dataY[0] * yy + dy;
                    ctx.lineTo(lastPointX, lastPointY);
                    ctx.lineTo(lastPointX, xAxisOrigin - attr.lineWidth);
                    ctx.lineTo(firstPointX, xAxisOrigin - attr.lineWidth);
                    ctx.lineTo(firstPointX, firstPointY);
                }

                if (attr.transformFillStroke) {
                    attr.matrix.toContext(ctx);
                }
                // Prevent the reverse transform to fix floating point error.
                if (attr.fillArea) {
                    ctx.fillStroke(attr, true);
                } else {
                    ctx.stroke(true);
                }
            }
        }
    }
});

/**
 * @class Ext.chart.series.Line
 * @extends Ext.chart.series.Cartesian
 *
 * Creates a Line Chart. A Line Chart is a useful visualization technique to display quantitative information for different
 * categories or other real values (as opposed to the bar chart), that can show some progression (or regression) in the dataset.
 * As with all other series, the Line Series must be appended in the *series* Chart array configuration. See the Chart
 * documentation for more information. A typical configuration object for the line series could be:
 *
 *     @example
 *     Ext.create('Ext.Container', {
 *         renderTo: Ext.getBody(),
 *         width: 600,
 *         height: 400,
 *         layout: 'fit',
 *         items: {
 *             xtype: 'cartesian',
 *             store: {
 *               fields: ['name', 'data1', 'data2', 'data3', 'data4', 'data5'],
 *               data: [
 *                   {'name':'metric one', 'data1':10, 'data2':12, 'data3':14, 'data4':8, 'data5':13},
 *                   {'name':'metric two', 'data1':7, 'data2':8, 'data3':16, 'data4':10, 'data5':3},
 *                   {'name':'metric three', 'data1':5, 'data2':2, 'data3':14, 'data4':12, 'data5':7},
 *                   {'name':'metric four', 'data1':2, 'data2':14, 'data3':6, 'data4':1, 'data5':23},
 *                   {'name':'metric five', 'data1':27, 'data2':38, 'data3':36, 'data4':13, 'data5':33}
 *               ]
 *             },
 *             axes: [{
 *                 type: 'numeric',
 *                 position: 'left',
 *                 fields: ['data1'],
 *                 title: {
 *                     text: 'Sample Values',
 *                     fontSize: 15
 *                 },
 *                 grid: true,
 *                 minimum: 0
 *             }, {
 *                 type: 'category',
 *                 position: 'bottom',
 *                 fields: ['name'],
 *                 title: {
 *                     text: 'Sample Values',
 *                     fontSize: 15
 *                 }
 *             }],
 *             series: [{
 *                 type: 'line',
 *                 highlight: {
 *                     size: 7,
 *                     radius: 7
 *                 },
 *                 style: {
 *                     stroke: 'rgb(143,203,203)'
 *                 },
 *                 xField: 'name',
 *                 yField: 'data1',
 *                 marker: {
 *                     type: 'path',
 *                     path: ['M', -2, 0, 0, 2, 2, 0, 0, -2, 'Z'],
 *                     stroke: 'blue',
 *                     lineWidth: 0
 *                 }
 *             }, {
 *                 type: 'line',
 *                 highlight: {
 *                     size: 7,
 *                     radius: 7
 *                 },
 *                 fill: true,
 *                 xField: 'name',
 *                 yField: 'data3',
 *                 marker: {
 *                     type: 'circle',
 *                     radius: 4,
 *                     lineWidth: 0
 *                 }
 *             }]
 *         }
 *     });
 *
 * In this configuration we're adding two series (or lines), one bound to the `data1`
 * property of the store and the other to `data3`. The type for both configurations is
 * `line`. The `xField` for both series is the same, the `name` property of the store.
 * Both line series share the same axis, the left axis. You can set particular marker
 * configuration by adding properties onto the marker object. Both series have
 * an object as highlight so that markers animate smoothly to the properties in highlight
 * when hovered. The second series has `fill = true` which means that the line will also
 * have an area below it of the same color.
 *
 * **Note:** In the series definition remember to explicitly set the axis to bind the
 * values of the line series to. This can be done by using the `axis` configuration property.
 */
Ext.define('Ext.chart.series.Line', {
    extend: 'Ext.chart.series.Cartesian',
    alias: 'series.line',
    type: 'line',
    seriesType: 'lineSeries',

    requires: [
        'Ext.chart.series.sprite.Line'
    ],

    config: {
        /**
         * @cfg {Number} selectionTolerance
         * The offset distance from the cursor position to the line series to trigger events (then used for highlighting series, etc).
         */
        selectionTolerance: 20,

        /**
         * @cfg {Object} style
         * An object containing styles for the visualization lines. These styles will override the theme styles.
         * Some options contained within the style object will are described next.
         */

        /**
         * @cfg {Boolean/Number} smooth
         * If set to `true` or a non-zero number, the line will be smoothed/rounded around its points; otherwise
         * straight line segments will be drawn.
         *
         * A numeric value is interpreted as a divisor of the horizontal distance between consecutive points in
         * the line; larger numbers result in sharper curves while smaller numbers result in smoother curves.
         *
         * If set to `true` then a default numeric value of 3 will be used.
         */
        smooth: false,

        /**
         * @cfg {Boolean} step
         * If set to `true`, the line uses steps instead of straight lines to connect the dots.
         * It is ignored if `smooth` is true.
         */
        step: false,

        /**
         * @cfg {Boolean} fill
         * If set to `true`, the area underneath the line is filled with the color defined as follows, listed by priority:
         * - The color that is configured for this series ({@link Ext.chart.series.Series#colors}).
         * - The color that is configured for this chart ({@link Ext.chart.AbstractChart#colors}).
         * - The fill color that is set in the {@link #style} config.
         * - The stroke color that is set in the {@link #style} config, or the same color as the line.
         *
         * Note: Do not confuse `series.config.fill` (which is a boolean) with `series.style.fill' (which is an alias
         * for the `fillStyle` property and contains a color). For compatibility with previous versions of the API,
         * if `config.fill` is undefined but a `style.fill' color is provided, `config.fill` is considered true.
         * So the default value below must be undefined, not false.
         */
         fill: undefined,

        aggregator: { strategy: 'double' }
    },

    /**
     * @private Default numeric smoothing value to be used when `{@link #smooth} = true`.
     */
    defaultSmoothness: 3,

    /**
     * @private Size of the buffer area on either side of the viewport to provide seamless zoom/pan
     * transforms. Expressed as a multiple of the viewport length, e.g. 1 will make the buffer on
     * each side equal to the length of the visible axis viewport.
     */
    overflowBuffer: 1,

    themeMarkerCount: function() {
        return 1;
    },

    /**
     * @private Override {@link Ext.chart.series.Series#getDefaultSpriteConfig}
     */
    getDefaultSpriteConfig: function () {
        var me = this,
            parentConfig = me.callParent(arguments),
            style = Ext.apply({}, me.getStyle()),
            styleWithTheme,
            fillArea = false;

        if (typeof me.config.fill != 'undefined') {
            // If config.fill is present but there is no fillStyle, then use the
            // strokeStyle to fill (and paint the area the same color as the line).
            if (me.config.fill) {
                fillArea = true;
                if (typeof style.fillStyle == 'undefined') {
                    if (typeof style.strokeStyle == 'undefined') {
                        styleWithTheme = me.getStyleWithTheme();
                        style.fillStyle = styleWithTheme.fillStyle;
                        style.strokeStyle = styleWithTheme.strokeStyle;
                    } else {
                        style.fillStyle = style.strokeStyle;
                    }
                }
            }
        } else {
            // For compatibility with previous versions of the API, if config.fill
            // is undefined but style.fillStyle is provided, we fill the area.
            if (style.fillStyle) {
                fillArea = true;
            }
        }

        // If we don't fill, then delete the fillStyle because that's what is used by
        // the Line sprite to fill below the line.
        if (!fillArea) {
            delete style.fillStyle;
        }

        style = Ext.apply(parentConfig || {}, style);

        return Ext.apply(style, {
            fillArea: fillArea,
            step: me.config.step,
            smooth: me.config.smooth,
            selectionTolerance: me.config.selectionTolerance
        });
    },

    updateStep: function (step) {
        var sprite = this.getSprites()[0];
        if (sprite && sprite.attr.step !== step) {
            sprite.setAttributes({step: step});
        }
    },

    updateFill: function (fill) {
        var sprite = this.getSprites()[0];
        if (sprite && sprite.attr.fillArea !== fill) {
            sprite.setAttributes({fillArea: fill});
        }
    },

    updateSmooth: function (smooth) {
        var sprite = this.getSprites()[0];
        if (sprite && sprite.attr.smooth !== smooth) {
            sprite.setAttributes({smooth: smooth});
        }
    }

});

/**
 * @class Ext.chart.series.sprite.PieSlice
 *
 * Pie slice sprite.
 */
Ext.define('Ext.chart.series.sprite.PieSlice', {
    alias: 'sprite.pieslice',
    mixins: {
        markerHolder: 'Ext.chart.MarkerHolder'
    },
    extend: 'Ext.draw.sprite.Sector',

    inheritableStatics: {
        def: {
            processors: {
                /**
                 * @cfg {Boolean} [doCallout=true]
                 * 'true' if the pie series uses label callouts.
                 */
                doCallout: 'bool',

                /**
                 * @cfg {String} [label='']
                 * Label associated with the Pie sprite.
                 */
                label: 'string',

                // @deprecated Use series.label.orientation config instead.
                // @since 5.0.1
                rotateLabels: 'bool',

                /**
                 * @cfg {Number} [labelOverflowPadding=10]
                 * Padding around labels to determine overlap.
                 * Any negative number allows the labels to overlap.
                 */
                labelOverflowPadding: 'number',

                renderer: 'default'
            },
            defaults: {
                doCallout: true,
                rotateLabels: true,
                label: '',
                labelOverflowPadding: 10,
                renderer: null
            }
        }
    },

    config: {
        /**
         * @private
         * @cfg {Object} rendererData The object that is passed to the renderer.
         *
         * For instance when the PieSlice sprite is used in a Gauge chart, the object
         * contains the 'store' and 'field' properties, and the 'value' as well
         * for that one PieSlice that is used to draw the needle of the Gauge.
         */
        rendererData: null,
        rendererIndex: 0
    },

    setGradientBBox: function (ctx, rect) {
        var me = this,
            attr = me.attr;
        if (attr.fillStyle.isGradient || attr.strokeStyle.isGradient) {
            if (attr.constrainGradients) {
                ctx.setGradientBBox({x: rect[0], y: rect[1], width: rect[2], height: rect[3]});
            } else {
                var midAngle = me.getMidAngle(),
                    margin = attr.margin,
                    cx = attr.centerX,
                    cy = attr.centerY,
                    r = attr.endRho,
                    matrix = attr.matrix,
                    scaleX = matrix.getScaleX(),
                    scaleY = matrix.getScaleY(),
                    w = scaleX * r,
                    h = scaleY * r,
                    bbox = {
                        width: w + w,
                        height: h + h
                    };
                if (margin) {
                    cx += margin * Math.cos(midAngle);
                    cy += margin * Math.sin(midAngle);
                }
                bbox.x = matrix.x(cx, cy) - w;
                bbox.y = matrix.y(cx, cy) - h;
                ctx.setGradientBBox(bbox);
            }
        }
    },

    render: function (surface, ctx, clip, rect) {
        var me = this,
            attr = me.attr,
            itemCfg = {},
            changes;

        if (attr.renderer) {
            itemCfg = {
                type: 'sector',
                text: attr.text,
                centerX: attr.centerX,
                centerY: attr.centerY,
                margin: attr.margin,
                startAngle: Math.min(attr.startAngle, attr.endAngle),
                endAngle: Math.max(attr.startAngle, attr.endAngle),
                startRho: Math.min(attr.startRho, attr.endRho),
                endRho: Math.max(attr.startRho, attr.endRho)
            };
            changes = attr.renderer.call(me, me, itemCfg, me.rendererData, me.rendererIndex);
            me.setAttributes(changes);
            me.useAttributes(ctx, clip);
        }

        // Draw the sector
        me.callParent([surface, ctx, clip, rect]);

        // Draw the labels
        if (attr.label && me.getBoundMarker('labels')) {
            me.placeLabel();
        }
    },

    placeLabel: function () {
        var me = this,
            attr = me.attr,
            attributeId = me.attr.attributeId,
            startAngle = Math.min(attr.startAngle, attr.endAngle),
            endAngle = Math.max(attr.startAngle, attr.endAngle),
            midAngle = (startAngle + endAngle) * 0.5,
            margin = attr.margin,
            centerX = attr.centerX,
            centerY = attr.centerY,
            sinMidAngle = Math.sin(midAngle),
            cosMidAngle = Math.cos(midAngle),
            startRho = Math.min(attr.startRho, attr.endRho) + margin,
            endRho = Math.max(attr.startRho, attr.endRho) + margin,
            midRho = (startRho + endRho) * 0.5,
            surfaceMatrix = me.surfaceMatrix,
            labelCfg = me.labelCfg || (me.labelCfg = {}),
            label = me.getBoundMarker('labels')[0],
            labelTpl = label.getTemplate(),
            calloutLine = labelTpl.getCalloutLine(),
            calloutLineLength = calloutLine && calloutLine.length || 40,
            labelBox, x, y, changes;

        surfaceMatrix.appendMatrix(attr.matrix);

        labelCfg.text = attr.label;

        x = centerX + cosMidAngle * midRho;
        y = centerY + sinMidAngle * midRho;
        labelCfg.x = surfaceMatrix.x(x, y);
        labelCfg.y = surfaceMatrix.y(x, y);

        x = centerX + cosMidAngle * endRho;
        y = centerY + sinMidAngle * endRho;
        labelCfg.calloutStartX = surfaceMatrix.x(x, y);
        labelCfg.calloutStartY = surfaceMatrix.y(x, y);

        x = centerX + cosMidAngle * (endRho + calloutLineLength);
        y = centerY + sinMidAngle * (endRho + calloutLineLength);
        labelCfg.calloutPlaceX = surfaceMatrix.x(x, y);
        labelCfg.calloutPlaceY = surfaceMatrix.y(x, y);

        if (!attr.rotateLabels) {
            labelCfg.rotationRads = 0;
            Ext.log.warn("'series.style.rotateLabels' config is deprecated. " +
                "Use 'series.label.orientation' config instead.");
        } else {
            switch (labelTpl.attr.orientation) {
                case 'horizontal':
                    labelCfg.rotationRads = midAngle + Math.atan2(
                            surfaceMatrix.y(1, 0) - surfaceMatrix.y(0, 0),
                            surfaceMatrix.x(1, 0) - surfaceMatrix.x(0, 0)
                    ) + Math.PI/2;
                    break;
                case 'vertical':
                    labelCfg.rotationRads = midAngle + Math.atan2(
                            surfaceMatrix.y(1, 0) - surfaceMatrix.y(0, 0),
                            surfaceMatrix.x(1, 0) - surfaceMatrix.x(0, 0)
                    );
                    break;
            }
        }
        labelCfg.calloutColor = (calloutLine && calloutLine.color) || me.attr.fillStyle;
        if (calloutLine) {
            if (calloutLine.width) {
                labelCfg.calloutWidth = calloutLine.width;
            }
        } else {
            labelCfg.calloutHasLine = false;
        }
        labelCfg.globalAlpha = attr.globalAlpha * attr.fillOpacity;

        // If a slice is empty, don't display the label.
        // This behavior can be overridden by a renderer.
        labelCfg.hidden = (attr.startAngle == attr.endAngle);

        if (labelTpl.attr.renderer) {
            changes = labelTpl.attr.renderer.call(me, me.attr.label, label, labelCfg, me.rendererData, me.rendererIndex);
            if (typeof changes === 'string') {
                labelCfg.text = changes;
            } else {
                Ext.apply(labelCfg, changes);
            }
        }
        me.putMarker('labels', labelCfg, attributeId);

        labelBox = me.getMarkerBBox('labels', attributeId, true);
        if (labelBox) {
            if (attr.doCallout) {
                if (labelTpl.attr.display === 'outside') {
                    me.putMarker('labels', {
                        callout: 1
                    }, attributeId);
                } else if (labelTpl.attr.display === 'inside') {
                    me.putMarker('labels', {
                        callout: 0
                    }, attributeId);
                } else {
                    me.putMarker('labels', {
                        callout: 1 - me.sliceContainsLabel(attr, labelBox)
                    }, attributeId);
                }
            } else {
                me.putMarker('labels', {
                    globalAlpha: me.sliceContainsLabel(attr, labelBox)
                }, attributeId);
            }
        }
    },

    sliceContainsLabel: function (attr, bbox) {
        var padding = attr.labelOverflowPadding,
            middle = (attr.endRho + attr.startRho) / 2,
            outer = middle + (bbox.width + padding) / 2,
            inner = middle - (bbox.width + padding) / 2,
            sliceAngle, l1, l2, l3;

        if (padding < 0) {
            return 1;
        }
        if (bbox.width + padding * 2 > (attr.endRho - attr.startRho)) {
            return 0;
        }
        l1 = Math.sqrt(attr.endRho * attr.endRho - outer * outer);
        l2 = Math.sqrt(attr.endRho * attr.endRho - inner * inner);
        sliceAngle = Math.abs(attr.endAngle - attr.startAngle);
        l3 = (sliceAngle > Math.PI/2 ? inner : Math.abs(Math.tan(sliceAngle / 2)) * inner);
        if (bbox.height + padding * 2 > Math.min(l1, l2, l3) * 2) {
            return 0;
        }
        return 1;
    }
});

/**
 * @class Ext.chart.series.Pie
 * @extends Ext.chart.series.Polar
 *
 * Creates a Pie Chart. A Pie Chart is a useful visualization technique to display quantitative information for different
 * categories that also have a meaning as a whole.
 * As with all other series, the Pie Series must be appended in the *series* Chart array configuration. See the Chart
 * documentation for more information. A typical configuration object for the pie series could be:
 *
 *     @example
 *     Ext.create('Ext.Container', {
 *         renderTo: Ext.getBody(),
 *         width: 600,
 *         height: 400,
 *         layout: 'fit',
 *         items: {
 *             xtype: 'polar',
 *             interactions: 'rotate',
 *             store: {
 *               fields: ['name', 'data1', 'data2', 'data3', 'data4', 'data5'],
 *               data: [
 *                   {name: 'metric one',   data1: 10, data2: 12, data3: 14, data4: 8,  data5: 13},
 *                   {name: 'metric two',   data1: 7,  data2: 8,  data3: 16, data4: 10, data5: 3},
 *                   {name: 'metric three', data1: 5,  data2: 2,  data3: 14, data4: 12, data5: 7},
 *                   {name: 'metric four',  data1: 2,  data2: 14, data3: 6,  data4: 1,  data5: 23},
 *                   {name: 'metric five',  data1: 27, data2: 38, data3: 36, data4: 13, data5: 33}
 *               ]
 *             },
 *             series: {
 *                 type: 'pie',
 *                 label: {
 *                     field: 'name',
 *                     display: 'rotate'
 *                 },
 *                 xField: 'data3',
 *                 donut: 30
 *             }
 *         }
 *     });
 *
 * In this configuration we set `pie` as the type for the series, set an object with specific style properties for highlighting options
 * (triggered when hovering elements). We also set true to `showInLegend` so all the pie slices can be represented by a legend item.
 * We set `data1` as the value of the field to determine the angle span for each pie slice. We also set a label configuration object
 * where we set the field name of the store field to be rendered as text for the label. The labels will also be displayed rotated.
 *
 */
Ext.define('Ext.chart.series.Pie', {
    extend: 'Ext.chart.series.Polar',
    requires: [
        'Ext.chart.series.sprite.PieSlice'
    ],
    type: 'pie',
    alias: 'series.pie',
    seriesType: 'pieslice',

    config: {
        /**
         * @cfg {String} labelField
         * @deprecated Use {@link Ext.chart.series.Pie#label} instead.
         * The store record field name to be used for the pie slice labels.
         */
        labelField: false,

        /**
         * @cfg {String} lengthField
         * The store record field name to be used for the pie slice lengths.
         * The values bound to this field name must be positive real numbers.
         */
        lengthField: false,

        /**
         * @cfg {Number} donut Specifies the radius of the donut hole, as a percentage of the chart's radius.
         * Defaults to 0 (no donut hole).
         */
        donut: 0,

        /**
         * @cfg {String} field
         * @deprecated Use xField directly
         */
        field: null,

        /**
         * @cfg {Number} rotation The starting angle of the pie slices.
         */
        rotation: 0,

        /**
         * @cfg {Boolean} clockwise
         * Whether the pie slices are displayed clockwise. Default's true.
         */
        clockwise: true,

        /**
         * @cfg {Number} [totalAngle=2*PI] The total angle of the pie series.
         */
        totalAngle: 2 * Math.PI,

        /**
         * @cfg {Array} hidden Determines which pie slices are hidden.
         */
        hidden: [],

        /**
         * @cfg {Number} Allows adjustment of the radius by a specific percentage.
         */
        radiusFactor: 100,

        /**
         * @cfg {Object} Default highlight config for the pie series.
         * Slides highlighted pie sector outward.
         */
        highlightCfg: {
            margin: 20
        },

        style: {}
    },

    directions: ['X'],

    setField: function (f) {
        return this.setXField(f);
    },

    getField: function () {
        return this.getXField();
    },

    applyLabel: function (newLabel, oldLabel) {
        if (Ext.isObject(newLabel) && !Ext.isString(newLabel.orientation)) {
            // Override default label orientation from '' to 'vertical'.
            Ext.apply(newLabel = Ext.Object.chain(newLabel), {orientation: 'vertical'});
        }
        if (!oldLabel) {
            oldLabel = new Ext.chart.Markers({zIndex: 10});
            oldLabel.setTemplate(new Ext.chart.label.Label(newLabel));
        } else {
            oldLabel.getTemplate().setAttributes(newLabel);
        }
        return oldLabel;
    },

    updateLabelData: function () {
        var me = this,
            store = me.getStore(),
            items = store.getData().items,
            sprites = me.getSprites(),
            labelField = me.getLabel().getTemplate().getField(),
            hidden = me.getHidden(),
            i, ln, labels, sprite;
        if (sprites.length > 0 && labelField) {
            labels = [];
            for (i = 0, ln = items.length; i < ln; i++) {
                labels.push(items[i].get(labelField));
            }
            for (i = 0, ln = sprites.length; i < ln; i++) {
                sprite = sprites[i];
                sprite.setAttributes({label: labels[i]});
                sprite.putMarker('labels', {hidden: hidden[i]}, sprite.attr.attributeId);
            }
        }
    },

    coordinateX: function () {
        var me = this,
            store = me.getStore(),
            items = store.getData().items,
            itemCount = items.length,
            field = me.getXField(),
            lengthField = me.getLengthField(),
            value, sum = 0,
            length, maxLength = 0,
            hidden = me.getHidden(),
            summation = [], i,
            lastAngle = 0,
            totalAngle = me.getTotalAngle(),
            clockwise = me.getClockwise() ? 1 : -1,
            sprites = me.getSprites();

        if (!sprites) {
            return;
        }

        for (i = 0; i < itemCount; i++) {
            value = Math.abs(Number(items[i].get(field))) || 0;
            length = lengthField && Math.abs(Number(items[i].get(lengthField))) || 0;
            if (!hidden[i]) {
                sum += value;
                if (length > maxLength) {
                    maxLength = length;
                }
            }
            summation[i] = sum;
            if (i >= hidden.length) {
                hidden[i] = false;
            }
        }
        me.maxLength = maxLength;

        if (sum !== 0) {
            sum = totalAngle / sum;
        }
        for (i = 0; i < itemCount; i++) {
            sprites[i].setAttributes({
                startAngle: lastAngle,
                endAngle: lastAngle = (sum ? clockwise * summation[i] * sum : 0),
                globalAlpha: 1
            });
        }
        for (; i < me.sprites.length; i++) {
            sprites[i].setAttributes({
                startAngle: totalAngle,
                endAngle: totalAngle,
                globalAlpha: 0
            });
        }
        me.getChart().refreshLegendStore();
    },

    updateCenter: function (center) {
        this.setStyle({
            translationX: center[0] + this.getOffsetX(),
            translationY: center[1] + this.getOffsetY()
        });
        this.doUpdateStyles();
    },

    updateRadius: function (radius) {
        this.setStyle({
            startRho: radius * this.getDonut() * 0.01,
            endRho: radius * this.getRadiusFactor() * 0.01
        });
        this.doUpdateStyles();
    },

    getStyleByIndex: function (i) {
        var me = this,
            items = me.getStore().getData().items,
            lengthField = me.getLengthField(),
            radius = me.getRadius(),
            style, length, startRho, endRho;
        length = lengthField && Math.abs(Number(items[i].get(lengthField))) || 0;
        startRho = radius * me.getDonut() * 0.01;
        endRho = radius * me.getRadiusFactor() * 0.01;
        style = this.callParent([i]);
        style.startRho = startRho;
        style.endRho = me.maxLength ? (startRho + (endRho - startRho) * length / me.maxLength) : endRho;
        return style;
    },

    updateDonut: function (donut) {
        var radius = this.getRadius();
        this.setStyle({
            startRho: radius * donut * 0.01,
            endRho: radius * this.getRadiusFactor() * 0.01
        });
        this.doUpdateStyles();
    },

    rotationOffset: -0.5 * Math.PI,

    updateRotation: function (rotation) {
        this.setStyle({
            // Subtract 90 degrees from rotation, so that `rotation` config's default
            // zero value makes first pie sector start at noon, rather than 3 o'clock.
            rotationRads: rotation + this.rotationOffset
        });
        this.doUpdateStyles();
    },

    updateTotalAngle: function (totalAngle) {
        this.processData();
    },

    getSprites: function () {
        var me = this,
            chart = me.getChart(),
            store = me.getStore();
        if (!chart || !store) {
            return [];
        }
        me.getColors();
        me.getSubStyle();
        var items = store.getData().items,
            length = items.length,
            animation = me.getAnimation() || chart && chart.getAnimation(),
            sprites = me.sprites, sprite,
            spriteIndex = 0, rendererData,
            i, spriteCreated = false,
            label = me.getLabel(),
            labelTpl = label.getTemplate();

        rendererData = {
            store: store,
            field: me.getField(),
            series: me
        };

        for (i = 0; i < length; i++) {
            sprite = sprites[i];
            if (!sprite) {
                sprite = me.createSprite();
                if (me.getHighlight()) {
                    sprite.config.highlight = me.getHighlight();
                    sprite.addModifier('highlight', true);
                }
                if (labelTpl.getField()) {
                    labelTpl.setAttributes({
                        labelOverflowPadding: me.getLabelOverflowPadding()
                    });
                    labelTpl.fx.setCustomDurations({'callout': 200});
                    sprite.bindMarker('labels', label);
                }
                sprite.setAttributes(me.getStyleByIndex(i));
                sprite.rendererData = rendererData;
                sprite.rendererIndex = spriteIndex++;
                spriteCreated = true;
            }
            sprite.fx.setConfig(animation);
        }
        if (spriteCreated) {
            me.doUpdateStyles();
        }
        return me.sprites;
    },

    betweenAngle: function (x, a, b) {
        var pp = Math.PI * 2,
            offset = this.rotationOffset;
        if (!this.getClockwise()) {
            x *= -1;
            a *= -1;
            b *= -1;
            a -= offset;
            b -= offset;
        } else {
            a += offset;
            b += offset;
        }
        b -= a;
        x -= a;
        x %= pp;
        b %= pp;
        x += pp;
        b += pp;
        x %= pp;
        b %= pp;
        return x < b;
    },

    /**
     * Returns the pie slice for a given angle
     * @param {Number} angle The angle to search for the slice
     * @return {Object} An object containing the reocord, sprite, scope etc.
     */
    getItemForAngle: function (angle) {
        var me = this,
            sprites = me.getSprites(),
            attr;

        angle %= Math.PI * 2;

        while (angle < 0) {
            angle += Math.PI * 2;
        }

        if (sprites) {
            var store  = me.getStore(),
                items  = store.getData().items,
                hidden = me.getHidden(),
                i      = 0,
                ln     = store.getCount();

            for (; i < ln; i++) {
                if(!hidden[i]) {
                    // Fortunately, item's id equals its index in the instances list.
                    attr = sprites[i].attr;

                    if (attr.startAngle <= angle &&  attr.endAngle >= angle) {
                        return {
                            series: me,
                            sprite: sprites[i],
                            index: i,
                            record: items[i],
                            field: me.getXField()
                        };
                    }
                }
            }
        }

        return null;
    },

    getItemForPoint: function (x, y) {
        var me = this,
            sprites = me.getSprites();
        if (sprites) {
            var center = me.getCenter(),
                offsetX = me.getOffsetX(),
                offsetY = me.getOffsetY(),
                originalX = x - center[0] + offsetX,
                originalY = y - center[1] + offsetY,
                store = me.getStore(),
                donut = me.getDonut(),
                records = store.getData().items,
                direction = Math.atan2(originalY, originalX) - me.getRotation(),
                radius = Math.sqrt(originalX * originalX + originalY * originalY),
                startRadius = me.getRadius() * donut * 0.01,
                hidden = me.getHidden(),
                i, ln, attr;

            for (i = 0, ln = records.length; i < ln; i++) {
                if (!hidden[i]) {
                    // Fortunately, item's id equals its index in the instances list.
                    attr = sprites[i].attr;
                    if (radius >= startRadius + attr.margin && radius <= attr.endRho + attr.margin) {
                        if (me.betweenAngle(direction, attr.startAngle, attr.endAngle)) {
                            return {
                                series: me,
                                sprite: sprites[i],
                                index: i,
                                record: records[i],
                                field: me.getXField()
                            };
                        }
                    }
                }
            }
            return null;
        }
    },

    provideLegendInfo: function (target) {
        var me = this,
            store = me.getStore();
        if (store) {
            var items = store.getData().items,
                labelField = me.getLabel().getTemplate().getField(),
                field = me.getField(),
                hidden = me.getHidden(),
                i, style, fill;

            for (i = 0; i < items.length; i++) {
                style = me.getStyleByIndex(i);
                fill = style.fillStyle;
                if (Ext.isObject(fill)) {
                    fill = fill.stops && fill.stops[0].color;
                }
                target.push({
                    name: labelField ? String(items[i].get(labelField))  : field + ' ' + i,
                    mark: fill || style.strokeStyle || 'black',
                    disabled: hidden[i],
                    series: me.getId(),
                    index: i
                });
            }
        }
    }
});

/*
    Moved TODO comments to bottom:
    TODO: `contrast` is not supported. Should be in the series.label config.
    TODO: We set `contrast` to `true` to flip the color of the label if it is to similar 
    to the background color. Finally, we set the font family
    TODO: and size through the `font` parameter.    
*/

/**
 * @class Ext.chart.series.sprite.Pie3DPart
 * @extends Ext.draw.sprite.Path
 * 
 * Pie3D series sprite.
 */
Ext.define("Ext.chart.series.sprite.Pie3DPart", {
    extend: 'Ext.draw.sprite.Path',
    mixins: {
        markerHolder: "Ext.chart.MarkerHolder"
    },
    alias: 'sprite.pie3dPart',
    type: 'pie3dPart',
    inheritableStatics: {
        def: {
            processors: {
                /**
                 * @cfg {Number} [centerX=0] The central point of the series on the x-axis.
                 */
                centerX: "number",

                /**
                 * @cfg {Number} [centerY=0] The central point of the series on the x-axis.
                 */
                centerY: "number",

                /**
                 * @cfg {Number} [startAngle=0] The starting angle of the polar series.
                 */
                startAngle: "number",

                /**
                 * @cfg {Number} [endAngle=Math.PI] The ending angle of the polar series.
                 */
                endAngle: "number",

                /**
                 * @cfg {Number} [startRho=0] The starting radius of the polar series.
                 */
                startRho: "number",

                /**
                 * @cfg {Number} [endRho=150] The ending radius of the polar series.
                 */
                endRho: "number",

                /**
                 * @cfg {Number} [margin=0] Margin from the center of the pie. Used for donut.
                 */
                margin: "number",

                /**
                 * @cfg {Number} [thickness=0] The thickness of the 3D pie part.
                 */
                thickness: "number",

                /**
                 * @cfg {Number} [distortion=0] The distortion of the 3D pie part.
                 */
                distortion: "number",

                /**
                 * @cfg {Object} [baseColor='white'] The color of the 3D pie part before adding the 3D effect.
                 */
                baseColor: "color",

                /**
                 * @cfg {Number} [baseRotation=0] The starting rotation of the polar series.
                 */
                baseRotation: "number",

                /**
                 * @cfg {String} [part=0] The part of the 3D Pie represented by the sprite.
                 */
                part: "enums(top,start,end,inner,outer)"
            },
            aliases: {
                rho: 'endRho'
            },
            dirtyTriggers: {
                centerX: "path,bbox",
                centerY: "path,bbox",
                startAngle: "path,partZIndex",
                endAngle: "path,partZIndex",
                startRho: "path",
                endRho: "path,bbox",
                margin: "path,bbox",
                thickness: "path",
                baseRotation: "path,partZIndex,partColor",
                baseColor: 'partZIndex,partColor',
                part: "path,partZIndex"
            },
            defaults: {
                centerX: 0,
                centerY: 0,
                startAngle: 0,
                endAngle: 0,
                startRho: 0,
                endRho: 150,
                margin: 0,
                distortion: 1,
                baseRotation: 0,
                baseColor: 'white',
                part: "top"
            },
            updaters: {
                "partColor": function (attrs) {
                    var color = Ext.draw.Color.fly(attrs.baseColor),
                        fillStyle;
                    switch (attrs.part) {
                        case 'top':
                            fillStyle = color.toString();
                            break;
                        case 'outer':
                            fillStyle = Ext.create("Ext.draw.gradient.Linear", {
                                type: 'linear',
                                stops: [
                                    {
                                        offset: 0,
                                        color: color.createDarker(0.3).toString()
                                    },
                                    {
                                        offset: 0.3,
                                        color: color.toString()
                                    },
                                    {
                                        offset: 0.8,
                                        color: color.createLighter(0.2).toString()
                                    },
                                    {
                                        offset: 1,
                                        color: color.createDarker(0.4).toString()
                                    }
                                ]
                            });
                            break;
                        case 'start':
                            fillStyle = color.createDarker(0.3).toString();
                            break;
                        case 'end':
                            fillStyle = color.createDarker(0.3).toString();
                            break;
                        case 'inner':
                            fillStyle = Ext.create("Ext.draw.gradient.Linear", {
                                type: 'linear',
                                stops: [
                                    {
                                        offset: 0,
                                        color: color.createDarker(0.4).toString()
                                    },
                                    {
                                        offset: 0.2,
                                        color: color.createLighter(0.2).toString()
                                    },
                                    {
                                        offset: 0.7,
                                        color: color.toString()
                                    },
                                    {
                                        offset: 1,
                                        color: color.createDarker(0.3).toString()
                                    }
                                ]
                            });
                            break;
                    }

                    attrs.fillStyle = fillStyle;
                    attrs.canvasAttributes.fillStyle = fillStyle;
                },
                "partZIndex": function (attrs) {
                    var rotation = attrs.baseRotation;
                    switch (attrs.part) {
                        case 'top':
                            attrs.zIndex = 5;
                            break;
                        case 'outer':
                            attrs.zIndex = 4;
                            break;
                        case 'start':
                            attrs.zIndex = 1 + Math.sin(attrs.startAngle + rotation);
                            break;
                        case 'end':
                            attrs.zIndex = 1 + Math.sin(attrs.endAngle + rotation);
                            break;
                        case 'inner':
                            attrs.zIndex = 1;
                            break;
                    }
                    attrs.dirtyZIndex = true;
                }
            }
        }
    },

    updatePlainBBox: function (plain) {
        var attr = this.attr,
            rho = attr.part === 'inner' ? attr.startRho : attr.endRho;
        plain.width = rho * 2;
        plain.height = rho * attr.distortion * 2 + attr.thickness;
        plain.x = attr.centerX - rho;
        plain.y = attr.centerY - rho * attr.distortion;
    },
    
    updateTransformedBBox: function (transform) {
        return this.updatePlainBBox(transform);
    },
    
    updatePath: function (path) {
        if (this.attr.endAngle < this.attr.startAngle) {
            return;
        }
        this[this.attr.part + 'Renderer'](path);
    },

    topRenderer: function (path) {
        var attr = this.attr,
            margin = attr.margin,
            distortion = attr.distortion,
            centerX = attr.centerX,
            centerY = attr.centerY,
            baseRotation = attr.baseRotation,
            startAngle = attr.startAngle + baseRotation,
            endAngle = attr.endAngle + baseRotation,
            midAngle = (startAngle + endAngle) * 0.5,
            startRho = attr.startRho,
            endRho = attr.endRho,
            sinEnd = Math.sin(endAngle),
            cosEnd = Math.cos(endAngle);

        centerX += Math.cos(midAngle) * margin;
        centerY += Math.sin(midAngle) * margin * distortion;
        path.ellipse(centerX, centerY, startRho, startRho * distortion, 0, startAngle, endAngle, false);
        path.lineTo(centerX + cosEnd * endRho, centerY + sinEnd * endRho * distortion);
        path.ellipse(centerX, centerY, endRho, endRho * distortion, 0, endAngle, startAngle, true);
        path.closePath();
    },

    startRenderer: function (path) {
        var attr = this.attr,
            margin = attr.margin,
            centerX = attr.centerX,
            centerY = attr.centerY,
            distortion = attr.distortion,
            baseRotation = attr.baseRotation,
            startAngle = attr.startAngle + baseRotation ,
            endAngle = attr.endAngle + baseRotation,
            thickness = attr.thickness,
            startRho = attr.startRho,
            endRho = attr.endRho,
            sinStart = Math.sin(startAngle),
            cosStart = Math.cos(startAngle),
            midAngle;

        if (cosStart < 0) {
            midAngle = (startAngle + endAngle) * 0.5;
            centerX += Math.cos(midAngle) * margin;
            centerY += Math.sin(midAngle) * margin * distortion;
            path.moveTo(centerX + cosStart * startRho, centerY + sinStart * startRho * distortion);
            path.lineTo(centerX + cosStart * endRho, centerY + sinStart * endRho * distortion);
            path.lineTo(centerX + cosStart * endRho, centerY + sinStart * endRho * distortion + thickness);
            path.lineTo(centerX + cosStart * startRho, centerY + sinStart * startRho * distortion + thickness);
            path.closePath();
        }
    },

    endRenderer: function (path) {
        var attr = this.attr,
            margin = attr.margin,
            centerX = attr.centerX,
            centerY = attr.centerY,
            distortion = attr.distortion,
            baseRotation = attr.baseRotation,
            startAngle = attr.startAngle + baseRotation ,
            endAngle = attr.endAngle + baseRotation,
            thickness = attr.thickness,
            startRho = attr.startRho,
            endRho = attr.endRho,
            sin = Math.sin(endAngle),
            cos = Math.cos(endAngle),
            midAngle;

        if (cos > 0) {
            midAngle = (startAngle + endAngle) * 0.5;
            centerX += Math.cos(midAngle) * margin;
            centerY += Math.sin(midAngle) * margin * distortion;
            path.moveTo(centerX + cos * startRho, centerY + sin * startRho * distortion);
            path.lineTo(centerX + cos * endRho, centerY + sin * endRho * distortion);
            path.lineTo(centerX + cos * endRho, centerY + sin * endRho * distortion + thickness);
            path.lineTo(centerX + cos * startRho, centerY + sin * startRho * distortion + thickness);
            path.closePath();
        }
    },

    innerRenderer: function (path) {
        var attr = this.attr,
            margin = attr.margin,
            centerX = attr.centerX,
            centerY = attr.centerY,
            distortion = attr.distortion,
            baseRotation = attr.baseRotation,
            startAngle = attr.startAngle + baseRotation ,
            endAngle = attr.endAngle + baseRotation,
            midAngle = (startAngle + endAngle) * 0.5,
            thickness = attr.thickness,
            startRho = attr.startRho,
            isTranslucent = attr.globalAlpha < 1,
            sinEnd, cosEnd,
            tempStart, tempEnd;

        centerX += Math.cos(midAngle) * margin;
        centerY += Math.sin(midAngle) * margin * distortion;
        if (startAngle >= Math.PI * 2 || isTranslucent) {
            startAngle -= Math.PI * 2;
            endAngle -= Math.PI * 2;
        }
        if (endAngle > Math.PI && endAngle < Math.PI * 3 || isTranslucent) {
            tempStart = startAngle;
            tempEnd = Math.min(endAngle, Math.PI * 2);
            sinEnd = Math.sin(tempEnd);
            cosEnd = Math.cos(tempEnd);
            path.ellipse(centerX, centerY, startRho, startRho * distortion, 0, tempStart, tempEnd, false);
            path.lineTo(centerX + cosEnd * startRho, centerY + sinEnd * startRho * distortion + thickness);
            path.ellipse(centerX, centerY + thickness, startRho, startRho * distortion, 0, tempEnd, tempStart, true);
            path.closePath();
        }
        if (endAngle > Math.PI * 3) {
            tempStart = Math.PI;
            tempEnd = endAngle;
            sinEnd = Math.sin(tempEnd);
            cosEnd = Math.cos(tempEnd);
            path.ellipse(centerX, centerY, startRho, startRho * distortion, 0, tempStart, tempEnd, false);
            path.lineTo(centerX + cosEnd * startRho, centerY + sinEnd * startRho * distortion + thickness);
            path.ellipse(centerX, centerY + thickness, startRho, startRho * distortion, 0, tempEnd, tempStart, true);
            path.closePath();
        }
    },

    outerRenderer: function (path) {
        var attr = this.attr,
            margin = attr.margin,
            centerX = attr.centerX,
            centerY = attr.centerY,
            distortion = attr.distortion,
            baseRotation = attr.baseRotation,
            startAngle = attr.startAngle + baseRotation ,
            endAngle = attr.endAngle + baseRotation,
            midAngle = (startAngle + endAngle) * 0.5,
            thickness = attr.thickness,
            endRho = attr.endRho,
            isTranslucent = attr.globalAlpha < 1,
            sinEnd, cosEnd,
            tempStart, tempEnd;

        centerX += Math.cos(midAngle) * margin;
        centerY += Math.sin(midAngle) * margin * distortion;

        if (startAngle >= Math.PI * 2 || isTranslucent) {
            startAngle -= Math.PI * 4;
            endAngle -= Math.PI * 4;
        }

        if (startAngle < Math.PI || isTranslucent) {
            tempStart = startAngle;
            tempEnd = Math.min(endAngle, Math.PI);
            sinEnd = Math.sin(tempEnd);
            cosEnd = Math.cos(tempEnd);
            path.ellipse(centerX, centerY, endRho, endRho * distortion, 0, tempStart, tempEnd, false);
            path.lineTo(centerX + cosEnd * endRho, centerY + sinEnd * endRho * distortion + thickness);
            path.ellipse(centerX, centerY + thickness, endRho, endRho * distortion, 0, tempEnd, tempStart, true);
            path.closePath();
        }
        if (endAngle > Math.PI * 2) {
            tempStart = Math.max(startAngle, Math.PI * 2);
            tempEnd = endAngle;
            sinEnd = Math.sin(tempEnd);
            cosEnd = Math.cos(tempEnd);
            path.ellipse(centerX, centerY, endRho, endRho * distortion, 0, tempStart, tempEnd, false);
            path.lineTo(centerX + cosEnd * endRho, centerY + sinEnd * endRho * distortion + thickness);
            path.ellipse(centerX, centerY + thickness, endRho, endRho * distortion, 0, tempEnd, tempStart, true);
            path.closePath();
        }
    }
});

/**
 * @class Ext.chart.series.Pie3D
 * @extends Ext.chart.series.Polar
 * 
 * Creates a 3D Pie Chart.
 *
 *     @example
 *     Ext.create('Ext.Container', {
 *         renderTo: Ext.getBody(),
 *         width: 600,
 *         height: 400,
 *         layout: 'fit',
 *         items: {
 *             xtype: 'polar',
 *             interactions: 'rotate',
 *             store: {
 *               fields: ['name', 'data1', 'data2', 'data3', 'data4', 'data5'],
 *               data: [
 *                   {'name':'metric one', 'data1':10, 'data2':12, 'data3':14, 'data4':8, 'data5':13},
 *                   {'name':'metric two', 'data1':7, 'data2':8, 'data3':16, 'data4':10, 'data5':3},
 *                   {'name':'metric three', 'data1':5, 'data2':2, 'data3':14, 'data4':12, 'data5':7},
 *                   {'name':'metric four', 'data1':2, 'data2':14, 'data3':6, 'data4':1, 'data5':23},
 *                   {'name':'metric five', 'data1':27, 'data2':38, 'data3':36, 'data4':13, 'data5':33}
 *               ]
 *             },
 *             series: {
 *                 type: 'pie3d',
 *                 field: 'data3',
 *                 donut: 30
 *             }
 *         }
 *     });
 */
Ext.define('Ext.chart.series.Pie3D', {
    requires: ['Ext.chart.series.sprite.Pie3DPart'],
    extend: 'Ext.chart.series.Polar',
    type: 'pie3d',
    seriesType: 'pie3d',
    alias: 'series.pie3d',
    config: {
        rect: [0, 0, 0, 0],
        thickness: 35,
        distortion: 0.5,

        /**
         * @cfg {String} field (required)
         * @deprecated Use xField instead
         * The store record field name to be used for the pie angles.
         * The values bound to this field name must be positive real numbers.
         */
        field: null,

        /**
         * @private
         * @cfg {String} lengthField
         * Not supported.
         */
        lengthField: false,

        /**
         * @cfg {Boolean/Number} donut
         * Whether to set the pie chart as donut chart.
         * Can be set to a particular percentage to set the radius
         * of the donut chart.
         */
        donut: false,

        rotation: 0
    },

    itemOffset: 5,

    setField: function (f) {
        return this.setXField(f);
    },

    getField: function () {
        return this.getXField();
    },

    applyRotation: function (rotation) {
        var twoPie = Math.PI * 2;
        return (rotation % twoPie + twoPie) % twoPie;
    },

    updateRotation: function (rotation) {
        var sprites = this.getSprites(),
            i, ln;
        for (i = 0, ln = sprites.length; i < ln; i++) {
            sprites[i].setAttributes({
                baseRotation: rotation
            });
        }
    },

    updateColors: function (colorSet) {
        this.setSubStyle({baseColor: colorSet});
    },
    
    doUpdateStyles: function () {
        var sprites = this.getSprites(),
            itemOffset = this.itemOffset,
            i = 0, j = 0, ln = sprites && sprites.length;

        for (; i < ln; i += itemOffset, j++) {
            sprites[i].setAttributes(this.getStyleByIndex(j));
            sprites[i + 1].setAttributes(this.getStyleByIndex(j));
            sprites[i + 2].setAttributes(this.getStyleByIndex(j));
            sprites[i + 3].setAttributes(this.getStyleByIndex(j));
            sprites[i + 4].setAttributes(this.getStyleByIndex(j));
        }
    },

    processData: function () {
        var me = this,
            chart = me.getChart(),
            animation = chart && chart.getAnimation(),
            store = me.getStore(),
            items = store.getData().items,
            length = items.length,
            field = me.getField(),
            value, sum = 0, ratio,
            summation = [],
            sprites = this.getSprites(),
            itemOffset = me.itemOffset,
            commonAttributes, lastAngle, i;

        for (i = 0; i < length; i++) {
            value = items[i].get(field);
            sum += value;
            summation[i] = sum;
        }
        if (sum === 0) {
            return;
        }
        ratio = 2 * Math.PI / sum;
        for (i = 0; i < length; i++) {
            summation[i] *= ratio;
        }

        for (i = 0; i < sprites.length; i++) {
            sprites[i].fx.setConfig(animation);
        }

        for (i = 0, lastAngle = 0; i < length; i++) {
            commonAttributes = {opacity: 1, startAngle: lastAngle, endAngle: summation[i]};
            sprites[i * itemOffset].setAttributes(commonAttributes);
            sprites[i * itemOffset + 1].setAttributes(commonAttributes);
            sprites[i * itemOffset + 2].setAttributes(commonAttributes);
            sprites[i * itemOffset + 3].setAttributes(commonAttributes);
            sprites[i * itemOffset + 4].setAttributes(commonAttributes);
            lastAngle = summation[i];
        }
    },

    getSprites: function () {
        var me = this,
            chart = this.getChart(),
            surface = me.getSurface(),
            store = me.getStore();
        if (!store) {
            return [];
        }
        var items = store.getData().items,
            itemOffset = me.itemOffset,
            length = items.length,
            animation = me.getAnimation() || chart && chart.getAnimation(),
            rect = chart.getMainRect() || [0, 0, 1, 1],
            rotation = me.getRotation(),
            center = me.getCenter(),
            offsetX = me.getOffsetX(),
            offsetY = me.getOffsetY(),
            radius = Math.min((rect[3] - me.getThickness() * 2) / me.getDistortion(), rect[2]) / 2,
            commonAttributes = {
                centerX: center[0] + offsetX,
                centerY: center[1] + offsetY - me.getThickness() / 2,
                endRho: radius,
                startRho: radius * me.getDonut() / 100,
                thickness: me.getThickness(),
                distortion: me.getDistortion()
            }, sliceAttributes, twoPie = Math.PI * 2,
            sprites = me.sprites,
            topSprite, startSprite, endSprite, innerSideSprite, outerSideSprite,
            i;

        for (i = 0; i < length; i++) {
            sliceAttributes = Ext.apply({}, this.getStyleByIndex(i), commonAttributes);
            topSprite = sprites[i * itemOffset];
            if (!topSprite) {
                topSprite = surface.add({
                    type: 'pie3dPart',
                    part: 'top',
                    startAngle: twoPie,
                    endAngle: twoPie
                });
                startSprite = surface.add({
                    type: 'pie3dPart',
                    part: 'start',
                    startAngle: twoPie,
                    endAngle: twoPie
                });
                endSprite = surface.add({
                    type: 'pie3dPart',
                    part: 'end',
                    startAngle: twoPie,
                    endAngle: twoPie
                });
                innerSideSprite = surface.add({
                    type: 'pie3dPart',
                    part: 'inner',
                    startAngle: twoPie,
                    endAngle: twoPie,
                    thickness: 0
                });
                outerSideSprite = surface.add({
                    type: 'pie3dPart',
                    part: 'outer',
                    startAngle: twoPie,
                    endAngle: twoPie,
                    thickness: 0
                });
                topSprite.fx.setDurationOn('baseRotation', 0);
                startSprite.fx.setDurationOn('baseRotation', 0);
                endSprite.fx.setDurationOn('baseRotation', 0);
                innerSideSprite.fx.setDurationOn('baseRotation', 0);
                outerSideSprite.fx.setDurationOn('baseRotation', 0);
                sprites.push(topSprite, startSprite, endSprite, innerSideSprite, outerSideSprite);
            } else {
                startSprite = sprites[i * itemOffset + 1];
                endSprite = sprites[i * itemOffset + 2];
                innerSideSprite = sprites[i * itemOffset + 3];
                outerSideSprite = sprites[i * itemOffset + 4];
                if (animation) {
                    topSprite.fx.setConfig(animation);
                    startSprite.fx.setConfig(animation);
                    endSprite.fx.setConfig(animation);
                    innerSideSprite.fx.setConfig(animation);
                    outerSideSprite.fx.setConfig(animation);
                }
            }
            topSprite.setAttributes(sliceAttributes);
            startSprite.setAttributes(sliceAttributes);
            endSprite.setAttributes(sliceAttributes);
            innerSideSprite.setAttributes(sliceAttributes);
            outerSideSprite.setAttributes(sliceAttributes);
        }

        for (i *= itemOffset, ln = sprites.length; i < ln; i++) {
            sprites[i].fx.setConfig(animation);
            sprites[i].setAttributes({
                opacity: 0,
                startAngle: twoPie,
                endAngle: twoPie,
                baseRotation: rotation
            });
        }

        return sprites;
    }
});

/**
 * @class Ext.chart.series.sprite.Polar
 * @extends Ext.draw.sprite.Sprite
 * 
 * Polar sprite.
 */
Ext.define('Ext.chart.series.sprite.Polar', {
    mixins: {
        markerHolder: 'Ext.chart.MarkerHolder'
    },
    extend: 'Ext.draw.sprite.Sprite',
    inheritableStatics: {
        def: {
            processors: {
                /**
                 * @cfg {Number} [dataMinX=0] Data minimum on the x-axis.
                 */
                dataMinX: 'number',

                /**
                 * @cfg {Number} [dataMaxX=1] Data maximum on the x-axis.
                 */
                dataMaxX: 'number',

                /**
                 * @cfg {Number} [dataMinY=0] Data minimum on the y-axis.
                 */
                dataMinY: 'number',

                /**
                 * @cfg {Number} [dataMaxY=2] Data maximum on the y-axis.
                 */
                dataMaxY: 'number',

                /**
                 * @cfg {Array} [rangeX='data'] Data range derived from all the series bound to the x-axis.
                 */
                rangeX: 'data',
                /**
                 * @cfg {Array} [rangeY='data'] Data range derived from all the series bound to the y-axis.
                 */
                rangeY: 'data',

                /**
                 * @cfg {Object} [dataY=null] Data items on the y-axis.
                 */
                dataY: 'data',

                /**
                 * @cfg {Object} [dataX=null] Data items on the x-axis.
                 */
                dataX: 'data',

                /**
                 * @cfg {Number} [centerX=0] The central point of the series on the x-axis.
                 */
                centerX: 'number',

                /**
                 * @cfg {Number} [centerY=0] The central point of the series on the y-axis.
                 */
                centerY: 'number',

                /**
                 * @cfg {Number} [startAngle=0] The starting angle of the polar series.
                 */
                startAngle: 'number',

                /**
                 * @cfg {Number} [endAngle=Math.PI] The ending angle of the polar series.
                 */
                endAngle: 'number',

                /**
                 * @cfg {Number} [startRho=0] The starting radius of the polar series.
                 */
                startRho: 'number',

                /**
                 * @cfg {Number} [endRho=150] The ending radius of the polar series.
                 */
                endRho: 'number',

                /**
                 * @cfg {Number} [baseRotation=0] The starting rotation of the polar series.
                 */
                baseRotation: 'number',

                /**
                 * @cfg {Object} [labels=null] Labels used in the series.
                 */
                labels: 'default',

                /**
                 * @cfg {Number} [labelOverflowPadding=10] Padding around labels to determine overlap.
                 */
                labelOverflowPadding: 'number'
            },
            defaults: {
                dataY: null,
                dataX: null,
                dataMinX: 0,
                dataMaxX: 1,
                dataMinY: 0,
                dataMaxY: 1,
                centerX: 0,
                centerY: 0,
                startAngle: 0,
                endAngle: Math.PI,
                startRho: 0,
                endRho: 150,
                baseRotation: 0,
                labels: null,
                labelOverflowPadding: 10
            },
            dirtyTriggers: {
                dataX: 'bbox',
                dataY: 'bbox',
                dataMinX: 'bbox',
                dataMaxX: 'bbox',
                dataMinY: 'bbox',
                dataMaxY: 'bbox',
                centerX: 'bbox',
                centerY: 'bbox',
                startAngle: 'bbox',
                endAngle: 'bbox',
                startRho: 'bbox',
                endRho: 'bbox',
                baseRotation: 'bbox'
            }
        }
    },

    config: {
        /**
         * @private
         * @cfg {Object} store The store that is passed to the renderer.
         */
        store: null,
        field: null
    },
    
    updatePlainBBox: function (plain) {
        var attr = this.attr;
        plain.x = attr.centerX - attr.endRho;
        plain.y = attr.centerY + attr.endRho;
        plain.width = attr.endRho * 2;
        plain.height = attr.endRho * 2;
    }
});

/**
 * @class Ext.chart.series.sprite.Radar
 * @extends Ext.chart.series.sprite.Polar
 * 
 * Radar series sprite.
 */
Ext.define('Ext.chart.series.sprite.Radar', {
    alias: 'sprite.radar',
    extend: 'Ext.chart.series.sprite.Polar',

    render: function (surface, ctx) {
        var me = this,
            attr = me.attr,
            centerX = attr.centerX,
            centerY = attr.centerY,
            matrix = attr.matrix,
            minX = attr.dataMinX,
            maxX = attr.dataMaxX,
            maxY = attr.dataMaxY,
            dataX = attr.dataX,
            dataY = attr.dataY,
            rangeY = attr.rangeY,
            endRho = attr.endRho,
            startRho = attr.startRho,
            baseRotation = attr.baseRotation,
            i, length = dataX.length,
            markerCfg = {},
            surfaceMatrix = me.surfaceMatrix,
            x, y, r, th;
        ctx.beginPath();
        for (i = 0; i < length; i++) {
            th = (dataX[i] - minX) / (maxX - minX + 1) * 2 * Math.PI + baseRotation;
            r = dataY[i] / (rangeY ? rangeY[1] : maxY) * (endRho - startRho) + startRho;
            x = matrix.x(centerX + Math.cos(th) * r, centerY + Math.sin(th) * r);
            y = matrix.y(centerX + Math.cos(th) * r, centerY + Math.sin(th) * r);
            if (i === 0) {
                ctx.moveTo(x, y);
            }
            ctx.lineTo(x, y);
            markerCfg.translationX = surfaceMatrix.x(x, y);
            markerCfg.translationY = surfaceMatrix.y(x, y);
            me.putMarker('markers', markerCfg, i, true);
        }
        ctx.closePath();
        ctx.fillStroke(attr);
    }
});

/**
 * @class Ext.chart.series.Radar
 * @extends Ext.chart.series.Polar
 *
 * Creates a Radar Chart. A Radar Chart is a useful visualization technique for comparing different quantitative values for
 * a constrained number of categories.
 * As with all other series, the Radar series must be appended in the *series* Chart array configuration. See the Chart
 * documentation for more information. A typical configuration object for the radar series could be:
 *
 *     @example
 *     Ext.create('Ext.Container', {
 *         renderTo: Ext.getBody(),
 *         width: 600,
 *         height: 400,
 *         layout: 'fit',
 *         items: {
 *             xtype: 'polar',
 *             interactions: 'rotate',
 *             store: {
 *                 fields: ['name', 'data1', 'data2', 'data3', 'data4', 'data5'],
 *                 data: [
 *                     {'name':'metric one', 'data1':10, 'data2':12, 'data3':14, 'data4':8, 'data5':13},
 *                     {'name':'metric two', 'data1':7, 'data2':8, 'data3':16, 'data4':10, 'data5':3},
 *                     {'name':'metric three', 'data1':5, 'data2':2, 'data3':14, 'data4':12, 'data5':7},
 *                     {'name':'metric four', 'data1':2, 'data2':14, 'data3':6, 'data4':1, 'data5':23},
 *                     {'name':'metric five', 'data1':27, 'data2':38, 'data3':36, 'data4':13, 'data5':33}
 *                 ]
 *             },
 *             series: {
 *                 type: 'radar',
 *                 xField: 'name',
 *                 yField: 'data4',
 *                 style: {
 *                     fillStyle: 'rgba(0, 0, 255, 0.1)',
 *                     strokeStyle: 'rgba(0, 0, 0, 0.8)',
 *                     lineWidth: 1
 *                 }
 *             },
 *             axes: [{
 *                 type: 'numeric',
 *                 position: 'radial',
 *                 fields: 'data4',
 *                 style: {
 *                     estStepSize: 10
 *                 },
 *                 grid: true
 *             }, {
 *                 type: 'category',
 *                 position: 'angular',
 *                 fields: 'name',
 *                 style: {
 *                     estStepSize: 1
 *                 },
 *                 grid: true
 *             }]
 *         }
 *     });
 *
 */
Ext.define('Ext.chart.series.Radar', {
    extend: 'Ext.chart.series.Polar',
    type: 'radar',
    seriesType: 'radar',
    alias: 'series.radar',
    requires: ['Ext.chart.series.sprite.Radar'],
    /**
     * @cfg {Object} style
     * An object containing styles for overriding series styles from theming.
     */

    config: {

    },

    themeColorCount: function() {
        return 1;
    },

    themeMarkerCount: function() {
        return 1;
    },

    updateAngularAxis: function (axis) {
        axis.processData(this);
    },

    updateRadialAxis: function (axis) {
        axis.processData(this);
    },

    coordinateX: function () {
        return this.coordinate('X', 0, 2);
    },

    coordinateY: function () {
        return this.coordinate('Y', 1, 2);
    },

    updateCenter: function (center) {
        this.setStyle({
            translationX: center[0] + this.getOffsetX(),
            translationY: center[1] + this.getOffsetY()
        });
        this.doUpdateStyles();
    },

    updateRadius: function (radius) {
        this.setStyle({
            endRho: radius
        });
        this.doUpdateStyles();
    },

    updateRotation: function (rotation) {
        this.setStyle({
            rotationRads: rotation
        });
        this.doUpdateStyles();
    },

    updateTotalAngle: function (totalAngle) {
        this.processData();
    },

    getItemForPoint: function (x, y) {
        var me = this,
            sprite = me.sprites && me.sprites[0],
            attr = sprite.attr,
            dataX = attr.dataX,
            dataY = attr.dataY,
            centerX = attr.centerX,
            centerY = attr.centerY,
            minX = attr.dataMinX,
            maxX = attr.dataMaxX,
            maxY = attr.dataMaxY,
            endRho = attr.endRho,
            startRho = attr.startRho,
            baseRotation = attr.baseRotation,
            i, length = dataX.length,
            store = me.getStore(),
            marker = me.getMarker(),
            threshhold = 22,
            item, th, r;

        if (me.getHidden()) {
            return null;
        }
        if (sprite && marker) {
            for (i = 0; i < length; i++) {
                th = (dataX[i] - minX) / (maxX - minX + 1) * 2 * Math.PI + baseRotation;
                r = dataY[i] / maxY * (endRho - startRho) + startRho;
                if (Math.abs(centerX + Math.cos(th) * r - x) < threshhold && Math.abs(centerY + Math.sin(th) * r - y) < threshhold) {
                    item = {
                        series: me,
                        sprite: sprite,
                        index: i,
                        record: store.getData().items[i],
                        field: me.getYField()
                    };
                    return item;
                }
            }
        }
        return me.callParent(arguments);
    },

    getSprites: function () {
        if (!this.getChart()) {
            return [];
        }
        if (!this.sprites.length) {
            this.createSprite();
        }
        return this.sprites;
    },

    provideLegendInfo: function (target) {
        var me = this,
            style = me.getSubStyleWithTheme(),
            fill = style.fillStyle;

        if (Ext.isArray(fill)) {
            fill = fill[0];
        }
        target.push({
            name: me.getTitle() || me.getYField() || me.getId(),
            mark: (Ext.isObject(fill) ? fill.stops && fill.stops[0].color : fill) || style.strokeStyle || 'black',
            disabled: me.getHidden(),
            series: me.getId(),
            index: 0
        });
    }

});


/**
 * @class Ext.chart.series.sprite.Scatter
 * @extends Ext.chart.series.sprite.Cartesian
 * 
 * Scatter series sprite.
 */
Ext.define('Ext.chart.series.sprite.Scatter', {
    alias: 'sprite.scatterSeries',
    extend: 'Ext.chart.series.sprite.Cartesian',

    renderClipped: function (surface, ctx, clip, clipRect) {
        if (this.cleanRedraw) {
            return;
        }
        var me = this,
            attr = me.attr,
            dataX = attr.dataX,
            dataY = attr.dataY,
            labels = attr.labels,
            drawLabels = labels && me.getBoundMarker('labels'),
            matrix = me.attr.matrix,
            xx = matrix.getXX(),
            yy = matrix.getYY(),
            dx = matrix.getDX(),
            dy = matrix.getDY(),
            markerCfg = {}, changes,
            left = clipRect[0] - xx,
            right = clipRect[0] + clipRect[2] + xx,
            top = clipRect[1] - yy,
            bottom = clipRect[1] + clipRect[3] + yy,
            x, y;
        for (var i = 0; i < dataX.length; i++) {
            x = dataX[i];
            y = dataY[i];
            x = x * xx + dx;
            y = y * yy + dy;
            if (left <= x && x <= right && top <= y && y <= bottom) {
                if (attr.renderer) {
                    markerCfg = {
                        type: 'items',
                        translationX: x,
                        translationY: y
                    };
                    changes = attr.renderer.call(me, me, markerCfg, {store: me.getStore()}, i);
                    markerCfg = Ext.apply(markerCfg, changes);
                } else {
                    markerCfg.translationX = x;
                    markerCfg.translationY = y;
                }
                me.putMarker('items', markerCfg, i, !attr.renderer);
                if (drawLabels && labels[i]) {
                    me.drawLabel(labels[i], x, y, i, clipRect);
                }
            }
        }
    },

    drawLabel: function (text, dataX, dataY, labelId, rect) {
        var me = this,
            attr = me.attr,
            label = me.getBoundMarker('labels')[0],
            labelTpl = label.getTemplate(),
            labelCfg = me.labelCfg || (me.labelCfg = {}),
            surfaceMatrix = me.surfaceMatrix,
            labelX, labelY,
            labelOverflowPadding = attr.labelOverflowPadding,
            flipXY = attr.flipXY,
            halfHeight, labelBox,
            changes;

        labelCfg.text = text;

        labelBox = me.getMarkerBBox('labels', labelId, true);
        if (!labelBox) {
            me.putMarker('labels', labelCfg, labelId);
            labelBox = me.getMarkerBBox('labels', labelId, true);
        }

        if (flipXY) {
            labelCfg.rotationRads = Math.PI * 0.5;
        } else {
            labelCfg.rotationRads = 0;
        }

        halfHeight = labelBox.height / 2;
        labelX = dataX;

        switch (labelTpl.attr.display) {
            case 'under':
                labelY = dataY - halfHeight - labelOverflowPadding;
                break;
            case 'rotate':
                labelX += labelOverflowPadding;
                labelY = dataY - labelOverflowPadding;
                labelCfg.rotationRads = -Math.PI / 4;
                break;
            default: // 'over'
                labelY = dataY + halfHeight + labelOverflowPadding;
        }

        labelCfg.x = surfaceMatrix.x(labelX, labelY);
        labelCfg.y = surfaceMatrix.y(labelX, labelY);

        if (labelTpl.attr.renderer) {
            changes = labelTpl.attr.renderer.call(me, text, label, labelCfg, {store: me.getStore()}, labelId);
            if (typeof changes === 'string') {
                labelCfg.text = changes;
            } else {
                Ext.apply(labelCfg, changes);
            }
        }

        me.putMarker('labels', labelCfg, labelId);
    }
});

/**
 * @class Ext.chart.series.Scatter
 * @extends Ext.chart.series.Cartesian
 *
 * Creates a Scatter Chart. The scatter plot is useful when trying to display more than two variables in the same visualization.
 * These variables can be mapped into x, y coordinates and also to an element's radius/size, color, etc.
 * As with all other series, the Scatter Series must be appended in the *series* Chart array configuration. See the Chart
 * documentation for more information on creating charts. A typical configuration object for the scatter could be:
 *
 *     @example
 *     Ext.create('Ext.Container', {
 *         renderTo: Ext.getBody(),
 *         width: 600,
 *         height: 400,
 *         layout: 'fit',
 *         items: {
 *             xtype: 'cartesian',
 *             store: {
 *               fields: ['name', 'data1', 'data2', 'data3', 'data4', 'data5'],
 *               data: [
 *                   {'name':'metric one', 'data1':10, 'data2':12, 'data3':14, 'data4':8, 'data5':13},
 *                   {'name':'metric two', 'data1':7, 'data2':8, 'data3':16, 'data4':10, 'data5':3},
 *                   {'name':'metric three', 'data1':5, 'data2':2, 'data3':14, 'data4':12, 'data5':7},
 *                   {'name':'metric four', 'data1':2, 'data2':14, 'data3':6, 'data4':1, 'data5':23},
 *                   {'name':'metric five', 'data1':27, 'data2':38, 'data3':36, 'data4':13, 'data5':33}
 *               ]
 *             },
 *             axes: [{
 *                 type: 'numeric',
 *                 position: 'left',
 *                 fields: ['data1'],
 *                 title: {
 *                     text: 'Sample Values',
 *                     fontSize: 15
 *                 },
 *                 grid: true,
 *                 minimum: 0
 *             }, {
 *                 type: 'category',
 *                 position: 'bottom',
 *                 fields: ['name'],
 *                 title: {
 *                     text: 'Sample Values',
 *                     fontSize: 15
 *                 }
 *             }],
 *             series: {
 *                 type: 'scatter',
 *                 highlight: {
 *                     size: 7,
 *                     radius: 7
 *                 },
 *                 fill: true,
 *                 xField: 'name',
 *                 yField: 'data3',
 *                 marker: {
 *                     type: 'circle',
 *                     fillStyle: 'blue',
 *                     radius: 10,
 *                     lineWidth: 0
 *                 }
 *             }
 *         }
 *     });
 *
 * In this configuration we add three different categories of scatter series. Each of them is bound to a different field of the same data store,
 * `data1`, `data2` and `data3` respectively. All x-fields for the series must be the same field, in this case `name`.
 * Each scatter series has a different styling configuration for markers, specified by the `marker` object. Finally we set the left axis as
 * axis to show the current values of the elements.
 *
 */
Ext.define('Ext.chart.series.Scatter', {

    extend: 'Ext.chart.series.Cartesian',

    alias: 'series.scatter',

    type: 'scatter',
    seriesType: 'scatterSeries',

    requires: [
        'Ext.chart.series.sprite.Scatter'
    ],

    config: {
        itemInstancing: {
            fx: {
                customDurations: {
                    translationX: 0,
                    translationY: 0
                }
            }
        }
    },

    themeMarkerCount: function() {
        return 1;
    },

    applyMarker: function (marker, oldMarker) {
        this.getItemInstancing();
        this.setItemInstancing(marker);
        return this.callParent(arguments);
    },

    provideLegendInfo: function (target) {
        var me = this,
            style = me.getMarkerStyleByIndex(0),
            fill = style.fillStyle;

        target.push({
            name: me.getTitle() || me.getYField() || me.getId(),
            mark: (Ext.isObject(fill) ? fill.stops && fill.stops[0].color : fill) || style.strokeStyle || 'black',
            disabled: me.getHidden(),
            series: me.getId(),
            index: 0
        });
    }
});


Ext.define('Ext.chart.theme.Blue', {
    extend: 'Ext.chart.theme.Base',
    singleton: true,
    alias: [
        'chart.theme.blue',
        'chart.theme.Blue'
    ],
    config: {
        baseColor: '#4d7fe6'
    }
});

Ext.define('Ext.chart.theme.BlueGradients', {
    extend: 'Ext.chart.theme.Base',
    singleton: true,
    alias: [
        'chart.theme.blue-gradients',
        'chart.theme.Blue:gradients'
    ],
    config: {
        baseColor: '#4d7fe6',
        gradients: {
            type: 'linear',
            degrees: 90
        }
    }
});

Ext.define('Ext.chart.theme.Category1', {
    extend: 'Ext.chart.theme.Base',
    singleton: true,
    alias: [
        'chart.theme.category1',
        'chart.theme.Category1'
    ],
    config: {
        colors: ['#f0a50a', '#c20024', '#2044ba', '#810065', '#7eae29']
    }
});

Ext.define('Ext.chart.theme.Category1Gradients', {
    extend: 'Ext.chart.theme.Base',
    singleton: true,
    alias: [
        'chart.theme.category1-gradients',
        'chart.theme.Category1:gradients'
    ],
    config: {
        colors: ['#f0a50a', '#c20024', '#2044ba', '#810065', '#7eae29'],
        gradients: {
            type: 'linear',
            degrees: 90
        }
    }
});

Ext.define('Ext.chart.theme.Category2', {
    extend: 'Ext.chart.theme.Base',
    singleton: true,
    alias: [
        'chart.theme.category2',
        'chart.theme.Category2'
    ],
    config: {
        colors: ['#6d9824', '#87146e', '#2a9196', '#d39006', '#1e40ac']
    }
});

Ext.define('Ext.chart.theme.Category2Gradients', {
    extend: 'Ext.chart.theme.Base',
    singleton: true,
    alias: [
        'chart.theme.category2-gradients',
        'chart.theme.Category2:gradients'
    ],
    config: {
        colors: ['#6d9824', '#87146e', '#2a9196', '#d39006', '#1e40ac'],
        gradients: {
            type: 'linear',
            degrees: 90
        }
    }
});

Ext.define('Ext.chart.theme.Category3', {
    extend: 'Ext.chart.theme.Base',
    singleton: true,
    alias: [
        'chart.theme.category3',
        'chart.theme.Category3'
    ],
    config: {
        colors: ['#fbbc29', '#ce2e4e', '#7e0062', '#158b90', '#57880e']
    }
});

Ext.define('Ext.chart.theme.Category3Gradients', {
    extend: 'Ext.chart.theme.Base',
    singleton: true,
    alias: [
        'chart.theme.category3-gradients',
        'chart.theme.Category3:gradients'
    ],
    config: {
        colors: ['#fbbc29', '#ce2e4e', '#7e0062', '#158b90', '#57880e'],
        gradients: {
            type: 'linear',
            degrees: 90
        }
    }
});

Ext.define('Ext.chart.theme.Category4', {
    extend: 'Ext.chart.theme.Base',
    singleton: true,
    alias: [
        'chart.theme.category4',
        'chart.theme.Category4'
    ],
    config: {
        colors: ['#ef5773', '#fcbd2a', '#4f770d', '#1d3eaa', '#9b001f']
    }
});

Ext.define('Ext.chart.theme.Category4Gradients', {
    extend: 'Ext.chart.theme.Base',
    singleton: true,
    alias: [
        'chart.theme.category4-gradients',
        'chart.theme.Category4:gradients'
    ],
    config: {
        colors: ['#ef5773', '#fcbd2a', '#4f770d', '#1d3eaa', '#9b001f'],
        gradients: {
            type: 'linear',
            degrees: 90
        }
    }
});

Ext.define('Ext.chart.theme.Category5', {
    extend: 'Ext.chart.theme.Base',
    singleton: true,
    alias: [
        'chart.theme.category5',
        'chart.theme.Category5'
    ],
    config: {
        colors: ['#7eae29', '#fdbe2a', '#910019', '#27b4bc', '#d74dbc']
    }
});

Ext.define('Ext.chart.theme.Category5Gradients', {
    extend: 'Ext.chart.theme.Base',
    singleton: true,
    alias: [
        'chart.theme.category5-gradients',
        'chart.theme.Category5:gradients'
    ],
    config: {
        colors: ['#7eae29', '#fdbe2a', '#910019', '#27b4bc', '#d74dbc'],
        gradients: {
            type: 'linear',
            degrees: 90
        }
    }
});

Ext.define('Ext.chart.theme.Category6', {
    extend: 'Ext.chart.theme.Base',
    singleton: true,
    alias: [
        'chart.theme.category6',
        'chart.theme.Category6'
    ],
    config: {
        colors: ['#44dce1', '#0b2592', '#996e05', '#7fb325', '#b821a1']
    }
});

Ext.define('Ext.chart.theme.Category6Gradients', {
    extend: 'Ext.chart.theme.Base',
    singleton: true,
    alias: [
        'chart.theme.category6-gradients',
        'chart.theme.Category6:gradients'
    ],
    config: {
        colors: ['#44dce1', '#0b2592', '#996e05', '#7fb325', '#b821a1'],
        gradients: {
            type: 'linear',
            degrees: 90
        }
    }
});

Ext.define('Ext.chart.theme.DefaultGradients', {
    extend: 'Ext.chart.theme.Base',
    singleton: true,
    alias: [
        'chart.theme.default-gradients',
        'chart.theme.Base:gradients'
    ],
    config: {
        gradients: {
            type: 'linear',
            degrees: 90
        }
    }
});

Ext.define('Ext.chart.theme.Green', {
    extend: 'Ext.chart.theme.Base',
    singleton: true,
    alias: [
        'chart.theme.green',
        'chart.theme.Green'
    ],
    config: {
        baseColor: '#b1da5a'
    }
});

Ext.define('Ext.chart.theme.GreenGradients', {
    extend: 'Ext.chart.theme.Base',
    singleton: true,
    alias: [
        'chart.theme.green-gradients',
        'chart.theme.Green:gradients'
    ],
    config: {
        baseColor: '#b1da5a',
        gradients: {
            type: 'linear',
            degrees: 90
        }
    }
});

Ext.define('Ext.chart.theme.Midnight', {
    extend: 'Ext.chart.theme.Base',
    singleton: true,
    alias: [
        'chart.theme.midnight',
        'chart.theme.Midnight'
    ],
    config: {
        colors: [
            '#A837FF',
            '#4AC0F2',
            '#FF4D35',
            '#FF8809',
            '#61C102',
            '#FF37EA'
        ],

        chart: {
            defaults: {
                background: 'rgb(52, 52, 53)'
            }
        },

        axis: {
            defaults: {
                style: {
                    strokeStyle: 'rgb(224, 224, 227)'
                },
                label: {
                    fillStyle: 'rgb(224, 224, 227)'
                },
                title: {
                    fillStyle: 'rgb(224, 224, 227)'
                },
                grid: {
                    strokeStyle: 'rgb(112, 112, 115)'
                }
            }
        },

        series: {
            defaults: {
                label: {
                    fillStyle: 'rgb(224, 224, 227)'
                }
            }
        },

        sprites: {
            text: {
                fillStyle: 'rgb(224, 224, 227)'
            }
        }
    }
});

Ext.define('Ext.chart.theme.Purple', {
    extend: 'Ext.chart.theme.Base',
    singleton: true,
    alias: [
        'chart.theme.purple',
        'chart.theme.Purple'
    ],
    config: {
        baseColor: '#da5abd'
    }
});

Ext.define('Ext.chart.theme.PurpleGradients', {
    extend: 'Ext.chart.theme.Base',
    singleton: true,
    alias: [
        'chart.theme.purple-gradients',
        'chart.theme.Purple:gradients'
    ],
    config: {
        baseColor: '#da5abd',
        gradients: {
            type: 'linear',
            degrees: 90
        }
    }
});

Ext.define('Ext.chart.theme.Red', {
    extend: 'Ext.chart.theme.Base',
    singleton: true,
    alias: [
        'chart.theme.red',
        'chart.theme.Red'
    ],
    config: {
        baseColor: '#e84b67'
    }
});

Ext.define('Ext.chart.theme.RedGradients', {
    extend: 'Ext.chart.theme.Base',
    singleton: true,
    alias: [
        'chart.theme.red-gradients',
        'chart.theme.Red:gradients'
    ],
    config: {
        baseColor: '#e84b67',
        gradients: {
            type: 'linear',
            degrees: 90
        }
    }
});

Ext.define('Ext.chart.theme.Sky', {
    extend: 'Ext.chart.theme.Base',
    singleton: true,
    alias: [
        'chart.theme.sky',
        'chart.theme.Sky'
    ],
    config: {
        baseColor: '#4ce0e7'
    }
});

Ext.define('Ext.chart.theme.SkyGradients', {
    extend: 'Ext.chart.theme.Base',
    singleton: true,
    alias: [
        'chart.theme.sky-gradients',
        'chart.theme.Sky:gradients'
    ],
    config: {
        baseColor: '#4ce0e7',
        gradients: {
            type: 'linear',
            degrees: 90
        }
    }
});

Ext.define('Ext.chart.theme.Yellow', {
    extend: 'Ext.chart.theme.Base',
    singleton: true,
    alias: [
        'chart.theme.yellow',
        'chart.theme.Yellow'
    ],
    config: {
        baseColor: '#fec935'
    }
});

Ext.define('Ext.chart.theme.YellowGradients', {
    extend: 'Ext.chart.theme.Base',
    singleton: true,
    alias: [
        'chart.theme.yellow-gradients',
        'chart.theme.Yellow:gradients'
    ],
    config: {
        baseColor: '#fec935',
        gradients: {
            type: 'linear',
            degrees: 90
        }
    }
});

/**
 * @private
 * Singleton that provides methods used by the Ext.draw.Path
 * for hit testing and finding path intersection points.
 */
Ext.define('Ext.draw.PathUtil', function () {
    var abs = Math.abs,
        pow = Math.pow,
        cos = Math.cos,
        acos = Math.acos,
        sqrt = Math.sqrt,
        PI = Math.PI;

    // For extra info see: http://pomax.github.io/bezierinfo/

    return {
        singleton: true,

        requires: [
            'Ext.draw.overrides.Path',
            'Ext.draw.overrides.sprite.Path'
        ],

        /**
         * @private
         * Sign function.
         * http://en.wikipedia.org/wiki/Sign_function
         * @param x {Number}
         * @returns {Number}
         */
        sign: function (x) {
            if (x > 0) {
                return 1;
            } else if (x < 0) {
                return -1;
            } else if (x === 0) {
                return 0;
            } else {
                Ext.Error.raise('The argument is not a valid number.');
            }
        },

        /**
         * @private
         * Finds roots of a cubic equation in t, where t lies in the interval of [0,1].
         * Based on http://www.particleincell.com/blog/2013/cubic-line-intersection/
         * @param P {Number[]} Cubic equation coefficients.
         * @returns {Number[]} Returns an array of parametric intersection locations along the cubic,
         *                  with -1 indicating an out-of-bounds intersection
         *                  (before or after the end point or in the imaginary plane).
         */
        cubicRoots: function (P) {
            var a = P[0],
                b = P[1],
                c = P[2],
                d = P[3];

            if (a === 0) {
                return this.quadraticRoots(b, c, d);
            }

            var A = b / a,
                B = c / a,
                C = d / a,

                Q = (3*B - pow(A, 2)) / 9,
                R = (9*A*B - 27*C - 2*pow(A, 3)) / 54,
                D = pow(Q, 3) + pow(R, 2), // Polynomial discriminant.
                t = [],
                S, T, Im, th, i,

                sign = this.sign;

            if (D >= 0) { // Complex or duplicate roots.
                S = sign(R + sqrt(D)) * pow(abs(R + sqrt(D)), 1/3);
                T = sign(R - sqrt(D)) * pow(abs(R - sqrt(D)), 1/3);

                t[0] = -A/3 + (S + T);          // Real root.
                t[1] = -A/3 - (S + T)/2;        // Real part of complex root.
                t[2] = t[1];                    // Real part of complex root.
                Im = abs(sqrt(3) * (S - T)/2);  // Complex part of root pair.

                // Discard complex roots.
                if (Im !== 0) {
                    t[1] =- 1;
                    t[2] =- 1;
                }

            } else { // Distinct real roots.
                th = acos(R / sqrt(-pow(Q, 3)));

                t[0] = 2*sqrt(-Q)*cos(th/3) - A/3;
                t[1] = 2*sqrt(-Q)*cos((th + 2*PI)/3) - A/3;
                t[2] = 2*sqrt(-Q)*cos((th + 4*PI)/3) - A/3;
            }

            // Discard out of spec roots.
            for (i = 0; i < 3; i++) {
                if (t[i] < 0 || t[i] > 1) {
                    t[i] = -1;
                }
            }

            return t;
        },

        /**
         * @private
         * Finds roots of a quadratic equation in t, where t lies in the interval of [0,1].
         * Takes three quadratic equation coefficients as parameters.
         * @param a {Number}
         * @param b {Number}
         * @param c {Number}
         * @returns {Array}
         */
        quadraticRoots: function (a, b, c) {
            var D, rD, t, i;
            if (a === 0) {
                return this.linearRoot(b, c);
            }
            D = b*b - 4*a*c;
            if (D === 0) { // One real root.
                t = [-b/(2*a)];
            } else if (D > 0) { // Distinct real roots.
                rD = sqrt(D);
                t = [(-b - rD) / (2*a), (-b + rD) / (2*a)];
            } else { // Complex roots.
                return [];
            }
            for (i = 0; i < t.length; i++) {
                if (t[i] < 0 || t[i] > 1) {
                    t[i] = -1;
                }
            }
            return t;
        },

        /**
         * @private
         * Finds roots of a linear equation in t, where t lies in the interval of [0,1].
         * Takes two linear equation coefficients as parameters.
         * @param a {Number}
         * @param b {Number}
         * @returns {Array}
         */
        linearRoot: function (a, b) {
            var t = -b/a;
            if (a === 0 || t < 0 || t > 1) {
                return [];
            }
            return [t];
        },

        /**
         * @private
         * Calculates the coefficients of a cubic function for the given coordinates.
         * @param P0 {Number}
         * @param P1 {Number}
         * @param P2 {Number}
         * @param P3 {Number}
         * @returns {Array}
         */
        bezierCoeffs: function (P0, P1, P2, P3) {
            var Z = [];
            Z[0] = -P0 + 3*P1 - 3*P2 + P3;
            Z[1] = 3*P0 - 6*P1 + 3*P2;
            Z[2] = -3*P0 + 3*P1;
            Z[3] = P0;
            return Z;
        },

        /**
         * @private
         * Computes intersection points between a cubic spline and a line segment.
         * Takes in x/y components of cubic control points and line segment start/end points
         * as parameters.
         * @param px1 {Number}
         * @param px2 {Number}
         * @param px3 {Number}
         * @param px4 {Number}
         * @param py1 {Number}
         * @param py2 {Number}
         * @param py3 {Number}
         * @param py4 {Number}
         * @param x1 {Number}
         * @param y1 {Number}
         * @param x2 {Number}
         * @param y2 {Number}
         * @returns {Array} Array of intersection points, where each intersection point
         *                  is itself a two-item array [x,y].
         */
        cubicLineIntersections: function (px1, px2, px3, px4, py1, py2, py3, py4,
                                          x1, y1, x2, y2) {
            var P = [],
                intersections = [],

                // Finding line equation coefficients.
                A = y1 - y2,
                B = x2 - x1,
                C = x1 * (y2 - y1) - y1 * (x2 - x1),

                // Finding cubic Bezier curve equation coefficients.
                bx = this.bezierCoeffs(px1, px2, px3, px4),
                by = this.bezierCoeffs(py1, py2, py3, py4),

                i, r, s,
                t, tt, ttt,
                cx, cy;

            P[0] = A*bx[0] + B*by[0];		// t^3
            P[1] = A*bx[1] + B*by[1];		// t^2
            P[2] = A*bx[2] + B*by[2];		// t
            P[3] = A*bx[3] + B*by[3] + C;	// 1

            r = this.cubicRoots(P);

            // Verify the roots are in bounds of the linear segment.
            for (i = 0; i < r.length; i++) {
                t = r[i];

                if (t < 0 || t > 1) {
                    continue;
                }

                tt = t*t;
                ttt = tt*t;

                cx = bx[0]*ttt + bx[1]*tt + bx[2]*t + bx[3];
                cy = by[0]*ttt + by[1]*tt + by[2]*t + by[3];

                // Above is intersection point assuming infinitely long line segment,
                // make sure we are also in bounds of the line.
                if ((x2 - x1) !== 0) { // If not vertical line
                    s = (cx - x1) / (x2 - x1);
                } else {
                    s = (cy - y1) / (y2 - y1);
                }
                // In bounds?
                if (!(s < 0 || s > 1)) {
                    intersections.push([cx, cy]);
                }
            }
            return intersections;
        },

        /**
         * @private
         * Splits cubic Bezier curve into two cubic Bezier curves at point z,
         * where z belongs to a range of [0, 1].
         * Accepts cubic coefficients and point z as parameters.
         * @param P1 {Number}
         * @param P2 {Number}
         * @param P3 {Number}
         * @param P4 {Number}
         * @param z Point to split the given curve at.
         * @returns {Array} Two-item array, where each item is itself an array
         *                  of cubic coefficients.
         */
        splitCubic: function (P1, P2, P3, P4, z) {
            var zz = z * z,
                zzz = z * zz,
                iz = z - 1,
                izz = iz * iz,
                izzz = iz * izz,
                // Common point for both curves.
                P = zzz * P4 - 3 * zz * iz * P3 + 3 * z * izz * P2 - izzz * P1;

            return [
                [
                    P1,
                    z * P2 - iz * P1,
                    zz * P3 - 2 * z * iz * P2 + izz * P1,
                    P
                ],
                [
                    P,
                    zz * P4 - 2 * z * iz * P3 + izz * P2,
                    z * P4 - iz * P3,
                    P4
                ]
            ]
        },

        /**
         * @private
         * Returns the dimension of a cubic Bezier curve in a single direction.
         * @param a {Number}
         * @param b {Number}
         * @param c {Number}
         * @param d {Number}
         * @returns {Array} Two-item array representing cubic's range in the given direction.
         */
        cubicDimension: function (a, b, c, d) {
            var qa = 3 * (-a + 3 * (b - c) + d),
                qb = 6 * (a - 2 * b + c),
                qc = -3 * (a - b), x, y,
                min = Math.min(a, d),
                max = Math.max(a, d), delta;

            if (qa === 0) {
                if (qb === 0) {
                    return [min, max];
                } else {
                    x = -qc / qb;
                    if (0 < x && x < 1) {
                        y = this.interpolateCubic(a, b, c, d, x);
                        min = Math.min(min, y);
                        max = Math.max(max, y);
                    }
                }
            } else {
                delta = qb * qb - 4 * qa * qc;
                if (delta >= 0) {
                    delta = sqrt(delta);
                    x = (delta - qb) / 2 / qa;
                    if (0 < x && x < 1) {
                        y = this.interpolateCubic(a, b, c, d, x);
                        min = Math.min(min, y);
                        max = Math.max(max, y);
                    }
                    if (delta > 0) {
                        x -= delta / qa;
                        if (0 < x && x < 1) {
                            y = this.interpolateCubic(a, b, c, d, x);
                            min = Math.min(min, y);
                            max = Math.max(max, y);
                        }
                    }
                }
            }
            return [min, max];
        },

        /**
         * @private
         * Calculates a value of a cubic function at the given point t. In other words
         * returns a * (1 - t) ^ 3 + 3 * b (1 - t) ^ 2 * t + 3 * c (1 - t) * t ^ 3 + d * t ^ 3
         * for given a, b, c, d and t, where t belongs to an interval of [0, 1].
         * @param a {Number}
         * @param b {Number}
         * @param c {Number}
         * @param d {Number}
         * @param t {Number}
         * @returns {Number}
         */
        interpolateCubic: function (a, b, c, d, t) {
            if (t === 0) {
                return a;
            }
            if (t === 1) {
                return d;
            }
            var rate = (1 - t) / t;
            return t * t * t * (d + rate * (3 * c + rate * (3 * b + rate * a)));
        },

        /**
         * @private
         * Computes intersection points between two cubic Bezier curve segments.
         * Takes x/y components of control points for two Bezier curve segments.
         * @param ax1 {Number}
         * @param ax2 {Number}
         * @param ax3 {Number}
         * @param ax4 {Number}
         * @param ay1 {Number}
         * @param ay2 {Number}
         * @param ay3 {Number}
         * @param ay4 {Number}
         * @param bx1 {Number}
         * @param bx2 {Number}
         * @param bx3 {Number}
         * @param bx4 {Number}
         * @param by1 {Number}
         * @param by2 {Number}
         * @param by3 {Number}
         * @param by4 {Number}
         * @returns {Array} Array of intersection points, where each intersection point
         *                  is itself a two-item array [x,y].
         */
        cubicsIntersections: function (ax1, ax2, ax3, ax4, ay1, ay2, ay3, ay4,
                                       bx1, bx2, bx3, bx4, by1, by2, by3, by4) {
            var me = this,
                axDim = me.cubicDimension(ax1, ax2, ax3, ax4),
                ayDim = me.cubicDimension(ay1, ay2, ay3, ay4),
                bxDim = me.cubicDimension(bx1, bx2, bx3, bx4),
                byDim = me.cubicDimension(by1, by2, by3, by4),
                splitAx, splitAy, splitBx, splitBy,
                points = [];

            // Curves' bounding boxes don't intersect.
            if (axDim[0] > bxDim[1] || axDim[1] < bxDim[0] || ayDim[0] > byDim[1] || ayDim[1] < byDim[0]) {
                return [];
            }
            // Both curves occupy sub-pixel areas which is effectively their intersection point.
            if (abs(ay1 - ay2) < 1 && abs(ay3 - ay4) < 1 && abs(ax1 - ax4) < 1 && abs(ax2 - ax3) < 1 &&
                abs(by1 - by2) < 1 && abs(by3 - by4) < 1 && abs(bx1 - bx4) < 1 && abs(bx2 - bx3) < 1) {
                return [[ (ax1 + ax4) * 0.5, (ay1 + ay2) * 0.5 ]];
            }

            splitAx = me.splitCubic(ax1, ax2, ax3, ax4, 0.5);
            splitAy = me.splitCubic(ay1, ay2, ay3, ay4, 0.5);
            splitBx = me.splitCubic(bx1, bx2, bx3, bx4, 0.5);
            splitBy = me.splitCubic(by1, by2, by3, by4, 0.5);

            points.push.apply(points, me.cubicsIntersections.apply(me, splitAx[0].concat(splitAy[0], splitBx[0], splitBy[0])));
            points.push.apply(points, me.cubicsIntersections.apply(me, splitAx[0].concat(splitAy[0], splitBx[1], splitBy[1])));
            points.push.apply(points, me.cubicsIntersections.apply(me, splitAx[1].concat(splitAy[1], splitBx[0], splitBy[0])));
            points.push.apply(points, me.cubicsIntersections.apply(me, splitAx[1].concat(splitAy[1], splitBx[1], splitBy[1])));

            return points;
        },

        /**
         * @private
         * Returns the point [x,y] where two line segments intersect or null.
         * Takes x/y components of the start and end point of the segments as parameters.
         * Based on Paul Bourke's explanation:
         * http://paulbourke.net/geometry/pointlineplane/
         * @param x1 {Number}
         * @param y1 {Number}
         * @param x2 {Number}
         * @param y2 {Number}
         * @param x3 {Number}
         * @param y3 {Number}
         * @param x4 {Number}
         * @param y4 {Number}
         * @returns {Number[]|null}
         */
        linesIntersection: function (x1, y1, x2, y2, x3, y3, x4, y4) {
            var d = (x2 - x1) * (y4 - y3) - (y2 - y1) * (x4 - x3),
                ua, ub;
            if (d === 0) { // Lines are parallel.
                return null;
            }
            ua = ( (x4 - x3) * (y1 - y3) - (x1 - x3) * (y4 - y3) ) / d;
            ub = ( (x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3) ) / d;
            if (ua >= 0 && ua <= 1 && ub >= 0 && ub <= 1) {
                return [
                    x1 + ua * (x2 - x1), // x
                    y1 + ua * (y2 - y1)  // y
                ];
            }
            return null; // The intersection point is outside one or both segments.
        },

        /**
         * @private
         * Checks if a point belongs to a line segment.
         * Takes x/y components of the start and end points of the segment and the point's
         * coordinates as parameters.
         * @param x1 {Number}
         * @param y1 {Number}
         * @param x2 {Number}
         * @param y2 {Number}
         * @param x {Number}
         * @param y {Number}
         * @returns {Boolean}
         */
        pointOnLine: function (x1, y1, x2, y2, x, y) {
            var t, _;
            if (abs(x2 - x1) < abs(y2 - y1)) {
                _ = x1;
                x1 = y1;
                y1 = _;
                _ = x2;
                x2 = y2;
                y2 = _;
                _ = x;
                x = y;
                y = _;
            }
            t = (x - x1) / (x2 - x1);
            if (t < 0 || t > 1) {
                return false;
            }
            return abs(y1 + t * (y2 - y1) - y) < 4;
        },

        /**
         * @private
         * Checks if a point belongs to a cubic Bezier curve segment.
         * Takes x/y components of the control points of the segment and the point's
         * coordinates as parameters.
         * @param px1 {Number}
         * @param px2 {Number}
         * @param px3 {Number}
         * @param px4 {Number}
         * @param py1 {Number}
         * @param py2 {Number}
         * @param py3 {Number}
         * @param py4 {Number}
         * @param x {Number}
         * @param y {Number}
         * @returns {Boolean}
         */
        pointOnCubic: function (px1, px2, px3, px4, py1, py2, py3, py4, x, y) {
            // Finding cubic Bezier curve equation coefficients.
            var me = this,
                bx = me.bezierCoeffs(px1, px2, px3, px4),
                by = me.bezierCoeffs(py1, py2, py3, py4),
                i, j, rx, ry, t;

            bx[3] -= x;
            by[3] -= y;

            rx = me.cubicRoots(bx);
            ry = me.cubicRoots(by);

            for (i = 0; i < rx.length; i++) {
                t = rx[i];
                for (j = 0; j < ry.length; j++) {
                    // TODO: for more accurate results tolerance should be dynamic
                    // TODO: based on the length and shape of the segment.
                    if (t >= 0 && t <= 1 && abs(t - ry[j]) < 0.05) {
                        return true;
                    }
                }
            }

            return false;
        }

    };
});

/**
 * @private
 */
Ext.define('Ext.chart.TipSurface', {

    /* Begin Definitions */

    extend: 'Ext.draw.Container',

    /* End Definitions */

    spriteArray: false,
    renderFirst: true,

    constructor: function(config) {
        this.callParent([config]);
        if (config.sprites) {
            this.spriteArray = [].concat(config.sprites);
            delete config.sprites;
        }
    },

    onRender: function() {
        var me = this,
            i = 0,
            l = 0,
            sp,
            sprites;
            this.callParent(arguments);
        sprites = me.spriteArray;
        if (me.renderFirst && sprites) {
            me.renderFirst = false;
            for (l = sprites.length; i < l; i++) {
                sp = me.surface.add(sprites[i]);
                sp.setAttributes({
                    hidden: false
                },
                true);
            }
        }
    }
});

/**
 * The ItemInfo interaction allows displaying detailed information about a series data
 * point in a popup panel.
 *
 * To attach this interaction to a chart, include an entry in the chart's
 * {@link Ext.chart.AbstractChart#interactions interactions} config with the `iteminfo` type:
 *
 *     new Ext.chart.AbstractChart({
 *         renderTo: Ext.getBody(),
 *         width: 800,
 *         height: 600,
 *         store: store1,
 *         axes: [ ...some axes options... ],
 *         series: [ ...some series options... ],
 *         interactions: [{
 *             type: 'iteminfo',
 *             listeners: {
 *                 show: function(me, item, panel) {
 *                     panel.setHtml('Stock Price: $' + item.record.get('price'));
 *                 }
 *             }
 *         }]
 *     });
 */
Ext.define('Ext.chart.interactions.ItemInfo', {

    extend: 'Ext.chart.interactions.Abstract',

    type: 'iteminfo',
    alias: 'interaction.iteminfo',

    /**
     * @event show
     * Fires when the info panel is shown.
     * @param {Ext.chart.interactions.ItemInfo} this The interaction instance
     * @param {Object} item The item whose info is being displayed
     * @param {Ext.Panel} panel The panel for displaying the info
     */

    config: {
        /**
         * @cfg {Object} extjsGestures
         * Defines the gestures that should trigger the item info panel to be displayed in ExtJS.
         */
        extjsGestures: {
            'start' : { event: 'click',      handler: 'onInfoGesture'},
            'move'  : { event: 'mousemove',  handler: 'onInfoGesture'},
            'end'   : { event: 'mouseleave', handler: 'onInfoGesture'}
        }

        // TODO:ps The trigger above should be 'itemclick', not 'click'.
    },

    item: null,

    onInfoGesture: function (e, element) {
        var me = this,
            item = me.getItemForEvent(e),
            tooltip = item && item.series.tooltip;

        if (tooltip) {
            tooltip.onMouseMove.call(tooltip, e);            
        }
        if (item !== me.item) {
            if (item) {
                item.series.showTip(item);
            } else {
                me.item.series.hideTip(me.item);
            }
            me.item = item;
        }
        return false;
    }

});

// @require Ext.chart.series.ItemPublisher

if(Ext.defaultSetupConfig && Ext.defaultSetupConfig.eventPublishers) {
    Ext.defaultSetupConfig.eventPublishers.seriesItemEvents = {
        xclass: 'Ext.chart.series.ItemPublisher'
    };
}

