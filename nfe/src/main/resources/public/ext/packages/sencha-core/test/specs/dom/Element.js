describe("Ext.dom.Element", function() {
    describe("instantiation", function() {
        var element, domEl;

        beforeEach(function() {
            domEl = document.createElement('div');
            document.body.appendChild(domEl);
        });

        afterEach(function() {
            var el = Ext.cache[domEl.id];

            if (el) {
                el.destroy();
            } else {
                document.body.removeChild(domEl);
            }
        });

        it("should set dom element id if it hasn't already one", function() {
            element = new Ext.dom.Element(domEl);

            expect(domEl.id).toBeDefined();
        });

        it("should not set dom element id if it has already one", function() {
            var id = Ext.id();

            domEl.id = id;
            element = new Ext.dom.Element(domEl);

            expect(domEl.id).toEqual(id);
        });

        it("should set dom property to dom element", function() {
            element = new Ext.dom.Element(domEl);

            expect(element.dom).toBe(domEl);
        });

        it("should set id property to dom id", function() {
            var id = Ext.id();

            domEl.id = id;
            element = new Ext.dom.Element(domEl);

            expect(element.id).toEqual(id);
        });

        it("should find a dom element if a string corresponding to it's id is passed as first argument", function() {
            var id = Ext.id();

            domEl.id = id;

            element = new Ext.dom.Element(id);

            expect(element.dom).toBe(domEl);
        });

        it("should throw error if the Element has an invalid id", function() {
            function expectError(id) {
                var dom = document.createElement('div');
                dom.id = id;
                document.body.appendChild(dom);
                expect(function() {
                    new Ext.Element(dom);
                }).toThrow('Invalid Element "id": "' + id + '"');
                document.body.removeChild(dom);
            }
            expectError('.abcdef');
            expectError('0a...');
            expectError('12345');
            expectError('.abc-def');
            expectError('<12345/>');
            expectError('1<>234.567');
        });
    });

    function describeMethods(fly) {
        describe('methods (using ' + (fly ? 'Ext.fly()' : 'new Ext.dom.Element()') + ')', function(){
            var domEl, element;

            function addElement(tag) {
                domEl = document.createElement(tag);
                document.body.appendChild(domEl);
                return fly ? Ext.fly(domEl) : Ext.get(domEl);
            }
            
            afterEach(function() {
                if (element) {
                    // Prevent console warnings
                    spyOn(Ext.Logger, 'warn');
                    element.destroy();
                    element = null;
                }
            });

            describe("set", function() {
                beforeEach(function() {
                    element = addElement('div');
                });

                it("should call Ext.core.DomHelper.applyStyles if object passed as first argument has style property", function() {
                    var style = {width:'100px'};

                    spyOn(element, "applyStyles");

                    element.set({style: style});

                    expect(element.applyStyles).toHaveBeenCalledWith(style);
                });

                it("should set dom element className if object passed as first argument has cls property", function() {
                    var cls = "x-test-class";

                    element.set({cls: cls});

                    expect(element.dom.className).toEqual(cls);
                });

                it("should use setAttribute by default", function() {
                    spyOn(element.dom, "setAttribute");

                    element.set({align: "center"});

                    expect(element.dom.setAttribute).toHaveBeenCalledWith("align", "center");
                });

                it("should be able to use expandos", function() {
                    spyOn(element.dom, "setAttribute");

                    element.set({align: "center"}, false);


                    expect(element.dom.align).toEqual("center");
                });

            });

            describe("is", function() {
                beforeEach(function() {
                    element = addElement('div');
                });

                it("Returns true if this element matches the passed simple selector", function() {
                    element.set({cls: "x-test-class"});

                    expect(element.is("div.x-test-class")).toBe(true);
                });
            });

            describe("focus", function() {
                beforeEach(function() {
                    element = addElement('div');
                });

                it("should focus dom element", function() {
                    spyOn(element.dom, "focus");

                    element.focus();

                    expect(element.dom.focus).toHaveBeenCalled();
                });

                it("should be able to defer dom element focus", function() {
                    spyOn(element.dom, "focus");
                    element.focus(1);

                    waitsFor(function(){
                        return element.dom.focus.calls.length === 1;
                    }, "element.dom.focus was never called");

                    runs(function() {
                        expect(element.dom.focus).toHaveBeenCalled();
                    });
                });

                it("should ignore any exception", function() {
                    element.dom.focus = function() {
                        throw "error";
                    };

                    expect(element.focus.bind(element)).not.toThrow("error");
                });
            });

            describe("blur", function() {
                beforeEach(function() {
                    element = addElement('div');
                });

                it("should blur dom element", function() {
                    spyOn(element.dom, "blur");

                    element.blur();

                    expect(element.dom.blur).toHaveBeenCalled();
                });


                it("should ignore any exception", function() {
                    element.dom.blur = function() {
                        throw "error";
                    };

                    expect(element.blur.bind(element)).not.toThrow("error");
                });
            });

            describe("getValue", function() {
                beforeEach(function() {
                    element = addElement('div');
                    element.dom.value = "10";
                });

                it("should return the dom value", function() {
                    expect(element.getValue()).toEqual("10");
                });

                it("should return the dom value as Number", function() {
                    expect(element.getValue(true)).toEqual(10);
                });
            });

            describe("listeners", function() {
                var options;

                beforeEach(function() {
                    options = {delay: 10};
                });

                xdescribe('deprecated (EventManager)', function() {
                    describe("addListener", function() {
                        beforeEach(function() {
                            element = addElement('div');
                        });

                        it("should call Ext.EventManager.on", function() {
                            spyOn(Ext.EventManager, "on");

                            element.addListener("click", Ext.emptyFn, fakeScope, options);

                            expect(Ext.EventManager.on).toHaveBeenCalledWith(element, "click", Ext.emptyFn, fakeScope, options);
                        });
                    });

                    describe("removeListener", function() {
                        beforeEach(function() {
                            element = addElement('div');
                        });

                        it("should call Ext.EventManager.un", function() {
                            spyOn(Ext.EventManager, "un");

                            element.removeListener("click", Ext.emptyFn, fakeScope);

                            expect(Ext.EventManager.un).toHaveBeenCalledWith(element, "click", Ext.emptyFn, fakeScope);
                        });
                    });

                    describe("removeAllListener", function() {
                        beforeEach(function() {
                            element = addElement('div');
                        });

                        it("should call Ext.EventManager.removeAll", function() {
                            spyOn(Ext.EventManager, "removeAll");

                            element.removeAllListeners();

                            expect(Ext.EventManager.removeAll).toHaveBeenCalledWith(element.dom);
                        });
                    });

                    describe("purgeAllListener", function() {
                        it("should call Ext.EventManager.purgeElement", function() {
                            element = addElement('div');
                            spyOn(Ext.EventManager, "purgeElement");

                            element.purgeAllListeners();

                            expect(Ext.EventManager.purgeElement).toHaveBeenCalledWith(element);
                        });
                        
                        // https://sencha.jira.com/browse/EXTJSIV-6713
                        it("should work with images", function() {
                            element = addElement('img');
                            
                            expect(function() { element.purgeAllListeners(); }).not.toThrow();
                            element.destroy();
                        });
                    });
                });
            });

            describe("addUnits", function() {
                beforeEach(function() {
                    element = addElement('div');
                });

                it("should return an empty string if size passed is an empty string", function() {
                    expect(element.addUnits("")).toEqual("");
                });

                it("should return auto if size passed is 'auto' string", function() {
                    expect(element.addUnits("auto")).toEqual("auto");
                });

                it("should return an empty string if size passed is undefined", function() {
                    expect(element.addUnits(undefined)).toEqual("");
                });

                it("should return an empty string if size passed is null", function() {
                    expect(element.addUnits(null)).toEqual("");
                });
            });

            describe("destroy", function() {
                var id, dom;
                
                beforeEach(function() {
                    element = addElement('div');
                    id = element.id;
                    dom = element.dom;
                });

                beforeEach(function() {
                    element.destroy();
                });

                it("should remove dom property", function() {
                    expect(element.dom).toBe(null);
                });

                it("should should remove the cache entry", function() {
                    expect(id in Ext.cache).toBe(false);
                });

                it("should remove the element from the dom", function() {
                    expect(dom.parentNode).toBeNull();
                });
            });

            describe("hover", function() {
                var overFn, outFn, options;
                beforeEach(function() {
                    element = addElement('div');
                    overFn = function() {
                        return 1;
                    };

                    outFn = function() {
                        return 2;
                    };

                    options = {
                        foo: true
                    };

                    spyOn(element, "on");
                });

                describe("mouseenter event", function() {
                    it("should add a listener on mouseenter", function() {
                        element.hover(overFn, outFn, fakeScope, options);

                        expect(element.on).toHaveBeenCalledWith("mouseenter", overFn, fakeScope, options);
                    });

                    it("should set scope to element.dom if it is not passed in arguments", function() {
                        element.hover(overFn, outFn, null, options);

                        expect(element.on).toHaveBeenCalledWith("mouseenter", overFn, element.dom, options);
                    });
                });

                describe("mouseleave event", function() {
                    it("should add a listener on mouseleave", function() {
                        element.hover(overFn, outFn, fakeScope, options);

                        expect(element.on).toHaveBeenCalledWith("mouseleave", outFn, fakeScope, options);
                    });

                    it("should set scope to element.dom if it is not passed in arguments", function() {
                        element.hover(overFn, outFn, null, options);

                        expect(element.on).toHaveBeenCalledWith("mouseleave", outFn, element.dom, options);
                    });
                });
            });

            describe("contains", function() {
                /**
                 * TODO: Removed tests for now, need to reinstate once the refactoring is done.
                 */
            });

            describe("mask", function() {
                // Note the following specs have been disabled for IE 6 because of failures in the eye
                // run that could not be reproduced.  They always passed locally in the test runner.
                // The comments have been left to show the unique differences needed to get these to
                // run in IE 6.
                describe("masking the body el", function () {
                    var cmp, maskEl, dom, scrollHeight, scrollWidth;

                    function createCmp(height) {
                        cmp = new Ext.Component({
                            height: height || 200,
                            renderTo: Ext.getBody()
                        });

                        maskEl = Ext.getBody().mask({msg: "Tom Sawyer"});

                        dom = document.body;
                        scrollHeight = dom.scrollHeight;
                        scrollWidth = dom.scrollWidth;
                    }

                    afterEach(function () {
                        Ext.removeNode(maskEl.dom.nextSibling);
                        Ext.removeNode(maskEl.dom);
                        Ext.destroy(cmp, maskEl);

                        cmp = maskEl = dom = scrollHeight = scrollWidth = null;
                    });
                });

            });

            xdescribe("deprecated 5.0", function() {
                describe("getAttributeNS", function() {
                    beforeEach(function() {
                        element = addElement('div');
                    });

                    it("should call element getAttribute", function() {
                        spyOn(element, "getAttribute");

                        element.getAttributeNS("ns1", "align");

                        expect(element.getAttribute).toHaveBeenCalledWith("align", "ns1");
                    });
                });
            });

            describe("getAttribute", function() {
                var element2, element3;
                beforeEach(function() {
                    element = addElement('div');
                    element2 = Ext.getBody().createChild({tag: "div"});


                    if (element.dom.setAttribute) {
                        element.dom.setAttribute("qtip", "bar");
                        element2.dom.setAttribute("ext:qtip", "foo");
                    } else {
                        element.dom["qtip"] = "bar";
                        element2.dom["ext:qtip"] = "foo";
                    }

                    if (element.dom.setAttributeNS) {
                        element3 = Ext.getBody().createChild({tag: "div"});
                        element3.dom.setAttributeNS("ext", "qtip", "foobar");
                    }
                });

                afterEach(function() {
                    element2.destroy();
                    if (element3) {
                        element3.destroy();
                    }
                });

                describe("without namespace", function() {
                    it("should return the attribute value if it exists", function() {
                        expect(element.getAttribute("qtip")).toEqual("bar");
                    });

                    it("should return null if the attribute does not exist", function() {
                        expect(element.getAttribute("nothing")).toBeNull();
                    });
                });

                describe("with namespace", function() {
                    it("should return null on a non-namespaced attribute", function() {
                        expect(element.getAttribute("qtip", "ext")).toBeNull();
                    });

                    it("should return null if the attribute belong to another namespace", function() {
                        expect(element2.getAttribute("qtip", "nothing")).toBeNull();
                    });

                    it("should return the attribute value if it belongs to the namespace", function() {
                        if (element3) {
                            expect(element3.getAttribute("qtip", "ext")).toEqual("foobar");
                        }
                    });

                    it("should handle xml namespace", function() {
                        expect(element2.getAttribute("qtip", "ext")).toEqual("foo");
                    });
                });
            });

            describe("update", function() {
                beforeEach(function() {
                    element = addElement('div');
                    element.dom.innerHTML = "hello world";
                });

                it("should update dom element innerHTML", function() {
                    element.update("foobar");

                    expect(element.dom).hasHTML("foobar");
                });

                it("should return element", function() {
                    expect(element.update("foobar")).toBe(element);
                });
            });

            describe("prototype aliases", function() {
                beforeEach(function() {
                    element = addElement('div');
                });

                it("should aliases addListener with on", function() {
                    expect(typeof(element.on)).toEqual('function');
                });

                it("should aliases removeListener with un", function() {
                    expect(typeof(element.un)).toEqual('function');
                });

                it("should aliases removeAllListeners with clearListeners", function() {
                    expect(typeof(element.clearListeners)).toEqual('function');
                });
            });

            describe("visibilityMode", function(){
                beforeEach(function() {
                    element = addElement('div');
                });

                it('must be able to setVisibilityMode and getVisibilityMode', function(){
                    element.setVisibilityMode(Ext.dom.Element.DISPLAY);
                    expect(element.getVisibilityMode()).toBe(Ext.dom.Element.DISPLAY);
                    
                    element.setVisibilityMode(Ext.dom.Element.VISIBILITY);
                    expect(element.getVisibilityMode()).toBe(Ext.dom.Element.VISIBILITY);
                });
                
                it("should retain visibilityMode on flyweights", function(){
                    Ext.fly(element.dom).setVisibilityMode(Ext.dom.Element.DISPLAY);
                    expect(Ext.fly(element.dom).getVisibilityMode()).toBe(Ext.dom.Element.DISPLAY);    
                });
            });

            describe("visibility", function(){
                var child, grandChild,
                    modes = [Ext.dom.Element.DISPLAY, Ext.dom.Element.VISIBILITY];

                beforeEach(function() {
                    element = addElement('div');
                    child = element.createChild({tag: "div"});
                    if (child) {
                        child.setVisible(true);
                        grandChild = child.createChild({tag: "div"});
                        if (grandChild) {
                            grandChild.setVisible(true);
                        }
                    }
                });

                afterEach(function() {
                    if (grandChild) {
                        grandChild.destroy();
                    }
                    if (child) {
                        child.destroy();
                    }
                });

                it("should toggle the visibility of the element itself", function(){
                    for (var i in modes) {
                        element.setVisibilityMode(modes[i]);

                        element.setVisible(false);
                        expect(element.isVisible(false)).toBe(false);

                        element.setVisible(true);
                        expect(element.isVisible(false)).toBe(true);                    
                    }
                });

                it("should toggle the 'deep' visibility of the grand-child", function(){
                    for (var i in modes) {
                        element.setVisibilityMode(modes[i]);

                        element.setVisible(false);
                        expect(grandChild.isVisible(true)).toBe(false);

                        element.setVisible(true);
                        expect(grandChild.isVisible(true)).toBe(true);
                    }
                });
            });

            describe("getMargin", function() {
                beforeEach(function() {
                    element = addElement('div');
                    element.setStyle({
                        marginTop: '1px',
                        marginRight: '11px',
                        marginBottom: '21px',
                        marginLeft: '31px'
                    });
                });

                function clearMargins() {
                    element.dom.style.marginTop = '';
                    element.dom.style.marginRight = '';
                    element.dom.style.marginBottom = '';
                    element.dom.style.marginLeft = '';
                }

                describe("with sides", function() {
                    it("should return the top", function() {
                        expect(element.getMargin('t')).toBe(1);
                    });

                    it("should return the right", function() {
                        expect(element.getMargin('r')).toBe(11);
                    });

                    it("should return the bottom", function() {
                        expect(element.getMargin('b')).toBe(21);
                    });

                    it("should return the left", function() {
                        expect(element.getMargin('l')).toBe(31);
                    });

                    it("should return the sum of the vertical margins", function() {
                        expect(element.getMargin('tb')).toBe(22);
                    });

                    it("should return the sum of the horizontal margins", function() {
                        expect(element.getMargin('lr')).toBe(42);
                    });

                    it("should return the sum of all margins", function() {
                        expect(element.getMargin('trbl')).toBe(64);
                    });

                    it("should coerce missing margins to 0", function() {
                        clearMargins();
                        expect(element.getMargin('t')).toBe(0);
                        expect(element.getMargin('r')).toBe(0);
                        expect(element.getMargin('b')).toBe(0);
                        expect(element.getMargin('l')).toBe(0);
                        expect(element.getMargin('tb')).toBe(0);
                        expect(element.getMargin('lr')).toBe(0);
                    });
                });

                describe("with no sides", function() {
                    it("should return the margins with each margin name & shortcut", function() {
                        expect(element.getMargin()).toEqual({
                            t: 1,
                            top: 1,
                            r: 11,
                            right: 11,
                            b: 21,
                            bottom: 21,
                            l: 31,
                            left: 31
                        });
                    });

                    it("should coerce missing margins to 0", function() {
                        clearMargins();
                        expect(element.getMargin()).toEqual({
                            t: 0,
                            top: 0,
                            r: 0,
                            right: 0,
                            b: 0,
                            bottom: 0,
                            l: 0,
                            left: 0
                        });
                    });
                });
            });

            if (!fly) {
                describe("setVertical", function() {
                    beforeEach(function() {
                        var styleSheet = document.styleSheets[0],
                            selector = '.vert',
                            props = [
                                    '-webkit-transform: rotate(90deg);',
                                    '-moz-transform: rotate(90deg);',
                                    '-o-transform: rotate(90deg);',	
                                    'transform: rotate(90deg);',
                                    'filter: progid:DXImageTransform.Microsoft.BasicImage(rotation=1);',
                            ].join('');
                        if (styleSheet.insertRule) {
                            styleSheet.insertRule(selector + '{' + props + '}', 1);
                        } else {
                            // IE8
                            styleSheet.addRule(selector, props);
                        }
                        element = addElement('div');
                        element.setWidth(100);
                        element.setHeight(30);
                        element.setVertical(90, 'vert');
                    });

                    afterEach(function() {
                        var styleSheet = document.styleSheets[0];
                        if (styleSheet.deleteRule) {
                  
                            styleSheet.deleteRule(1);
                        } else {
                            // IE8
                            styleSheet.removeRule(styleSheet.rules.length - 1); 
                        }
                    });

                    it("should add the css class", function() {
                        expect(element.hasCls('vert')).toBe(true);
                    });

                    it("should get the width using getWidth()", function() {
                        expect(element.getWidth()).toBe(30);
                    });

                    it("should get the width using getStyle('width')", function() {
                        expect(element.getStyle('width')).toBe('30px');
                    });

                    it("should get the height using getHeight", function() {
                        expect(element.getHeight()).toBe(100);
                    });

                    it("should get the height using getStyle('height')", function() {
                        expect(element.getStyle('height')).toBe('100px');
                    });

                    it("should set the width using setWidth()", function() {
                        element.setWidth(200);
                        expect(element.getWidth()).toBe(200);
                    });

                    it("should set the width using setStyle('width')", function() {
                        element.setStyle('width', '200px');
                        expect(element.getWidth()).toBe(200);
                    });

                    it("should set the height using setHeight()", function() {
                        element.setHeight(200);
                        expect(element.getHeight()).toBe(200);
                    });

                    it("should set the height using setStyle('height')", function() {
                        element.setStyle('height', '200px');
                        expect(element.getHeight()).toBe(200);
                    });

                    describe("setHorizontal", function() {
                        beforeEach(function() {
                            element.setHorizontal();
                        });

                        it("should remove the css class", function() {
                            expect(element.hasCls('vert')).toBe(false);
                        });

                        it("should get the width using getWidth()", function() {
                            expect(element.getWidth()).toBe(100);
                        });

                        it("should get the width using getStyle('width')", function() {
                            expect(element.getStyle('width')).toBe('100px');
                        });

                        it("should get the height using getHeight", function() {
                            expect(element.getHeight()).toBe(30);
                        });

                        it("should get the height using getStyle('height')", function() {
                            expect(element.getStyle('height')).toBe('30px');
                        });

                        it("should set the width using setWidth()", function() {
                            element.setWidth(200);
                            expect(element.getWidth()).toBe(200);
                        });

                        it("should set the width using setStyle('width')", function() {
                            element.setStyle('width', '200px');
                            expect(element.getWidth()).toBe(200);
                        });

                        it("should set the height using setHeight()", function() {
                            element.setHeight(200);
                            expect(element.getHeight()).toBe(200);
                        });

                        it("should set the height using setStyle('height')", function() {
                            element.setStyle('height', '200px');
                            expect(element.getHeight()).toBe(200);
                        });
                    });
                });
            }
        });
    }

    describeMethods();
    describeMethods(true);

    describe("class methods", function() {
        var element, element2, domEl, domEl2, id;

        beforeEach(function() {
            element = Ext.getBody().createChild({tag: "div"});
            domEl = element.dom;

            id = Ext.id();
            domEl2 = document.createElement("div");
            domEl2.id = id;
            document.body.appendChild(domEl2);
        });

        afterEach(function() {
            // Prevent console warnings
            spyOn(Ext.Logger, 'warn');
            element.destroy();
            if (element2) {
                element2.destroy();
            }
            if (domEl2 && domEl2.parentNode === document.body) {
                document.body.removeChild(domEl2);
            }
        });

        describe("get", function() {
            describe("alias", function() {
                it("should alias Ext.dom.Element.get with Ext.get", function() {
                    spyOn(Ext.dom.Element, 'get');
                    Ext.get();
                    expect(Ext.dom.Element.get).toHaveBeenCalled();
                });
            });

            describe("passing string id as first argument", function() {
                describe("with a dom element which is not already encapsulated", function() {
                    it("should return a new Ext.dom.Element", function() {
                        element2 = Ext.get(id);

                        expect(element2 instanceof Ext.dom.Element).toBe(true);
                    });

                    it("should encapsulate the dom element in the Ext.dom.Element", function() {
                        element2 = Ext.get(id);

                        expect(element2.dom).toBe(domEl2);
                    });

                    it("should add element to Ext.cache", function() {
                        element2 = Ext.get(id);
                        expect(Ext.cache[id] === element2);
                    });
                });

                describe("with a dom element which is already encapsulated", function() {
                    it("should return the corresponding Ext.Element", function() {
                        expect(Ext.get(domEl)).toBe(element);
                    });
                });
            });

            describe("passing dom element as first argument", function() {
                describe("with a dom element which is not already encapsulated", function() {
                    it("should return a new Ext.dom.Element", function() {
                        element2 = Ext.get(domEl2);

                        expect(element2 instanceof Ext.dom.Element).toBe(true);
                    });

                    it("should encapsulate the dom element in the Ext.dom.Element", function() {
                        element2 = Ext.get(domEl2);

                        expect(element2.dom).toBe(domEl2);
                    });

                    it("should add element to Ext.cache", function() {
                        expect(Ext.cache[domEl2.id] === domEl2);
                    });
                });

                describe("with a dom element which is already encapsulated", function() {
                    it("should return the corresponding Ext.Element", function() {
                        expect(Ext.get(domEl.id)).toBe(element);
                    });
                });
            });

            describe("passing an Ext.dom.Element as first argument", function() {
                it("should return Ext.dom.Element", function() {
                    expect(Ext.get(element)).toBe(element);
                });
            });
            
            describe("passing a Ext.dom.FlyWeight as first argument", function() {
                it("should return Ext.dom.Element", function() {
                    var result = Ext.get(Ext.fly(domEl));
                    expect(result).toBe(element);
                    expect(result.isFly).toBeUndefined();
                    
                });
            });

            describe("passing a CompositeElement as first argument", function() {
                var compositeElement;

                beforeEach(function() {
                    compositeElement = Ext.select("div");
                });

                it("should return Ext.dom.Element", function() {
                    expect(Ext.get(compositeElement)).toBe(compositeElement);
                });
            });

            describe("passing an array as first argument", function() {
                it("should call Ext.dom.Element.select", function() {
                    var arr = [domEl, domEl2];
                    spyOn(Ext.dom.Element, "select");

                    Ext.get(arr);

                    expect(Ext.dom.Element.select).toHaveBeenCalledWith(arr);
                });
            });

            describe("passing document as first argument", function() {
                it("should return an Ext.dom.Element", function() {
                    expect(Ext.get(document) instanceof Ext.dom.Element).toBe(true);
                });

                xit("should return a bogus Ext.dom.Element", function() {
                    expect(Ext.get(document).id).not.toBeDefined();
                });

                it("should return an Ext.dom.Element that encapsulate document", function() {
                    expect(Ext.get(document).dom).toBe(document);
                });
            });

            it("should not wrap a documentFragment", function() {
                var dom = document.createDocumentFragment(),
                    el = Ext.get(dom);

                expect(el).toBe(null);
            });

            it("should wrap the window object", function() {
                var dom = window,
                    el = Ext.get(dom);

                expect(el instanceof Ext.dom.Element).toBe(true);
                expect(el.dom).toBe(dom);
            });

            it("should wrap the document object", function() {
                var dom = document,
                    el = Ext.get(dom);

                expect(el instanceof Ext.dom.Element).toBe(true);
                expect(el.dom).toBe(dom);
            });

            describe("document and window within iframe", function() {
                var iframe, el;

                beforeEach(function() {
                    iframe = document.createElement('iframe');
                    document.body.appendChild(iframe);

                    waitsFor(function() {
                        return !!iframe.contentWindow.document.body;
                    });
                });

                afterEach(function() {
                    document.body.removeChild(iframe);
                    el.destroy();
                });

                it("should wrap an iframe's window object", function() {
                    var dom = iframe.contentWindow;

                    el = Ext.get(dom);

                    expect(el instanceof Ext.dom.Element).toBe(true);
                    expect(el.dom).toBe(dom);
                });

                it("should wrap an iframe's document object", function() {
                    var dom = iframe.contentWindow.document;

                    el = Ext.get(dom);

                    expect(el instanceof Ext.dom.Element).toBe(true);
                    expect(el.dom).toBe(dom);
                });
            });

            it("should not wrap a text node", function() {
                expect(Ext.get(document.createTextNode(('foo')))).toBe(null);
            });
        });

        xdescribe("garbageCollector", function() {

        });

        describe("fly", function() {
            var flyWeight;

            beforeEach(function() {
                spyOn(Ext, "getDom").andCallThrough();

            });

            describe('use strict', function () {
                var backup;

                //TODO - See if there is a cheap enough way to avoid this replacement
                //TODO - Oddly enough even if we wrap Ext.fly it throws an error trying
                //TODO - to use Ext.fly.caller but the caller is the wrapper not the
                //TODO - strict mode function (perhaps the JIT has removed the "useless"
                //TODO - wrapper function).
                beforeEach(function () {
                    Ext.fly = (function (oldFly) {
                        backup = oldFly;
                        return function (dom, named) {
                            return oldFly(dom, named || '_global');
                        }
                    }(Ext.fly));
                });

                afterEach(function () {
                    Ext.fly = backup;
                });

                it('should work when called by strict mode function', function () {
                    'use strict';
                    var f = Ext.fly(domEl2);
                });
            });

            describe("global flyweight", function() {
                beforeEach(function() {
                    flyWeight = Ext.fly(domEl2);
                });

                it("should return an Ext.dom.Element.Fly", function() {
                    expect(flyWeight instanceof Ext.dom.Fly).toBe(true);
                });

                it("should not cache a dom element", function() {
                    expect(Ext.cache[domEl2.id]).toBeUndefined();
                });

                it("should call Ext.getDom", function() {
                    expect(Ext.getDom).toHaveBeenCalledWith(domEl2);
                });
            });

            describe("named reusable flyweight", function() {
                beforeEach(function() {
                    flyWeight = Ext.fly(domEl2, "myflyweight");
                });

                it("should return an Ext.dom.Element.Flyweight", function() {
                    expect(flyWeight instanceof Ext.dom.Fly).toBe(true);
                });

                it("should not cache a dom element", function() {
                    expect(Ext.cache[domEl2.id]).toBeUndefined();
                });

                it("should call Ext.getDom", function() {
                    expect(Ext.getDom).toHaveBeenCalledWith(domEl2);
                });
            });

            it("should wrap a documentFragment", function() {
                var dom = document.createDocumentFragment(),
                    el = Ext.fly(dom);

                expect(el instanceof Ext.dom.Fly).toBe(true);
                expect(el.dom).toBe(dom);
            });

            it("should wrap the window object", function() {
                var dom = window,
                    el = Ext.fly(dom);

                expect(el instanceof Ext.dom.Fly).toBe(true);
                expect(el.dom).toBe(dom);
            });

            it("should wrap the document object", function() {
                var dom = document,
                    el = Ext.fly(dom);

                expect(el instanceof Ext.dom.Fly).toBe(true);
                expect(el.dom).toBe(dom);
            });

            describe("document and window within iframe", function() {
                var iframe;

                beforeEach(function() {
                    iframe = document.createElement('iframe');
                    document.body.appendChild(iframe);
                });

                afterEach(function() {
                    document.body.removeChild(iframe);
                });

                it("should wrap an iframe's window object", function() {
                    var dom = iframe.contentWindow,
                        el = Ext.fly(dom);

                    expect(el instanceof Ext.dom.Fly).toBe(true);
                    expect(el.dom).toBe(dom);
                });

                it("should wrap an iframe's document object", function() {
                    var dom = iframe.contentWindow.document,
                        el = Ext.fly(dom);

                    expect(el instanceof Ext.dom.Fly).toBe(true);
                    expect(el.dom).toBe(dom);

                });
            });

            it("should not wrap a text node", function() {
                expect(Ext.fly(document.createTextNode(('foo')))).toBe(null);
            });
        });

        describe("aliases", function() {
            it("should aliases Ext.dom.Element.get with Ext.get", function() {
                spyOn(Ext.dom.Element, 'get');
                Ext.get();
                expect(Ext.dom.Element.get).toHaveBeenCalled();
            });

            it("should aliases Ext.fly with Ext.Element.fly", function() {
                spyOn(Ext, 'fly');
                Ext.Element.fly();
                expect(Ext.fly).toHaveBeenCalled();
            });
        });
    });
    
    describe("getXY", function(){
        var unAttached;
        beforeEach(function(){
            unAttached = document.createElement('div');
        });
        it("should not throw when reading unattached element", function(){
            Ext.fly(unAttached).getXY();
        });
    });

    describe("Ext", function() {
        // these specs have to live here instead of in sencha-core, because they test
        // the result of passing a Ext.Element instance to Ext.isElement() or Ext.isTextNode.
        it("should return false when an Ext.Element instance is passed to Ext.isElement", function() {
           expect(Ext.isElement(Ext.getBody())).toBe(false);
        });

        it("should return false when an Ext.Element instance is passed to Ext.isTextNode", function() {
           expect(Ext.isTextNode(Ext.getBody())).toBe(false);
        });
    });

    describe("Ext.isGarbage", function() {
        it("should return false if the element is the window", function() {
            expect(Ext.isGarbage(window)).toBe(false);
        });

        it("should return false if the element is the document", function() {
            expect(Ext.isGarbage(document)).toBe(false);
        });

        it("should return false if the element is the documentElement", function() {
            expect(Ext.isGarbage(document.documentElement)).toBe(false);
        });

        it("should return false if the element is in the DOM", function() {
            var el = Ext.getBody().createChild();
            expect(Ext.isGarbage(el.dom)).toBe(false);
            el.destroy();
        });

        it("should return true if the element is not in the DOM", function() {
            expect(Ext.isGarbage(document.createElement('div'))).toBe(true);
        });

        it("should return false if the element is in the detached body", function() {
            var el = Ext.getDetachedBody().createChild();
            expect(Ext.isGarbage(el.dom)).toBe(false);
            el.destroy();
        });

        it("should return false if the element is in the dom and the cache contains an element with the same id", function() {
            // EXTJS-13702
            var el = Ext.getBody().createChild({
                id: 'foo'
            });

            document.body.removeChild(el.dom);

            el = Ext.getBody().createChild({
                id: 'foo'
            });

            expect(Ext.isGarbage(el.dom)).toBe(false);

            el.destroy();
        });

    });

    describe("shim", function() {
        var element;

        beforeEach(function() {
            element = Ext.getBody().createChild({
                style: 'position:absolute;left:250px;top:150px;width:200px;height:100px;'
            });
        });

        afterEach(function() {
            if (!element.isDestroyed) {
                element.destroy();
            }
        });

        function expectBox(x, y, w, h) {
            var box = element.shim.el.getBox();

            expect(box.x).toBe(x);
            expect(box.y).toBe(y);
            expect(box.width).toBe(w);
            expect(box.height).toBe(h);
        }

        it("should not have a shim by default", function() {
            expect(element.shim).toBeUndefined();
        });

        it("should create a shim", function() {
            element.enableShim();

            expect(element.shim instanceof Ext.dom.Shim).toBe(true);
        });

        it("should show the shim upon creation", function() {
            element.enableShim();

            expect(element.shim.el.isVisible()).toBe(true);
        });

        it("should insert the shim as the previousSibling of its target", function() {
            element.enableShim();

            expect(element.dom.previousSibling).toBe(element.shim.el.dom);
        });

        it("should be an iframe", function() {
            element.enableShim();

            expect(element.shim.el.dom.tagName).toBe('IFRAME');
        });

        it("should have a CSS class of 'x-shim'", function() {
            element.enableShim();

            expect(element.shim.el).toHaveCls('x-shim');
        });

        it("should have a role of 'presentation'", function() {
            element.enableShim();

            expect(element.shim.el.dom.getAttribute('role')).toBe('presentation');
        });

        it("should have frameBorder: '0' on the iframe", function() {
            element.enableShim();

            expect(element.shim.el.dom.getAttribute('frameBorder')).toBe('0');
        });

        it("should set the iframe's src to Ext.SSL_SECURE_URL", function() {
            element.enableShim();

            expect(element.shim.el.dom.getAttribute('src')).toBe(Ext.SSL_SECURE_URL);
        });

        it("should have a tabindex of '-1'", function() {
            // tabIndex of -1 ensures that the iframe is not focusable by the user
            element.enableShim();

            expect(element.shim.el.dom.getAttribute('tabindex')).toBe('-1');
        });

        it("should not show the shim if the element is not visible", function() {
            element.hide();

            element.enableShim();

            expect(element.shim.el).toBeUndefined();
        });

        it("should not show the shim upon creation if ths isVisible parameter is false", function() {
            // private api used by components to avoid an isVisible check
            element.enableShim(null, false);

            expect(element.shim.el).toBeUndefined();
        });

        it("should hide the shim when the element is destroyed", function() {
            element.enableShim();

            var shim = element.shim,
                shimEl = shim.el;

            element.destroy();

            expect(shim.el).toBeNull();
            expect(shimEl.isVisible()).toBe(false);
            // should NOT destroy the shim - it is returned to the pool
            expect(shimEl.isDestroyed).toBeFalsy();
        });

        it("should use DISPLAY visibilityMode for the shim", function() {
            element.enableShim();

            expect(element.shim.el.getVisibilityMode()).toBe(Ext.Element.DISPLAY);
        });

        it("should allow fixed positioning to be configured", function() {
            element.enableShim({
                fixed: true
            });

            expect(element.shim.el.getStyle('position')).toBe('fixed');
        });

        it("should set the z-index of the shim when the z-index of the element is set", function() {
            element.enableShim();

            element.setZIndex(174);

            expect(parseInt(element.shim.el.getStyle('z-index'), 10)).toBe(174);
        });

        it("should align the shim to the target element", function() {
            element.enableShim();

            expectBox(250, 150, 200, 100);
        });

        it("should realign the shim using passed coordinates", function() {
            element.enableShim();

            element.shim.realign(25, 50, 75, 85);

            expectBox(25, 50, 75, 85);
        });

        it("should realign the shim in response to setLeft()", function() {
            element.enableShim();

            element.setLeft(300);

            expectBox(300, 150, 200, 100);
        });

        it("should realign the shim in response to setTop()", function() {
            element.enableShim();

            element.setTop(300);

            expectBox(250, 300, 200, 100);
        });

        it("should realign the shim in response to setLocalX()", function() {
            element.enableShim();

            element.setLocalX(300);

            expectBox(300, 150, 200, 100);
        });

        it("should realign the shim in response to setLocalY()", function() {
            element.enableShim();

            element.setLocalY(300);

            expectBox(250, 300, 200, 100);
        });

        it("should realign the shim in response to setLocalXY()", function() {
            element.enableShim();

            element.setLocalXY(300, 400);

            expectBox(300, 400, 200, 100);
        });

        it("should realign the shim in response to setX()", function() {
            element.enableShim();

            element.setX(300);

            expectBox(300, 150, 200, 100);
        });

        it("should realign the shim in response to setY()", function() {
            element.enableShim();

            element.setY(300);

            expectBox(250, 300, 200, 100);
        });

        it("should realign the shim in response to setXY()", function() {
            element.enableShim();

            element.setXY([300, 400]);

            expectBox(300, 400, 200, 100);
        });

        it("should realign the shim in response to setWidth()", function() {
            element.enableShim();

            element.setWidth(400);

            expectBox(250, 150, 400, 100);
        });

        it("should realign the shim in response to setHeight()", function() {
            element.enableShim();

            element.setHeight(400);

            expectBox(250, 150, 200, 400);
        });

        it("should realign the shim in response to setSize()", function() {
            element.enableShim();

            element.setSize(450, 550);

            expectBox(250, 150, 450, 550);
        });

        it("should realign the shim in response to setBox()", function() {
            element.enableShim();

            element.setBox({
                x: 300,
                y: 400,
                width: 500,
                height: 600
            });

            expectBox(300, 400, 500, 600);
        });

        it("should disable the shim", function() {
            element.enableShim();

            var shimEl = element.shim.el;

            element.disableShim();

            expect(element.shim.el).toBeNull();
            expect(shimEl.isVisible()).toBe(false);
        });

        it("should re-enable the shim after disabling", function() {
            element.enableShim();

            element.disableShim();

            element.enableShim();

            expect(element.shim.el.isVisible()).toBe(true);
            expectBox(250, 150, 200, 100);
        });

        it("should realign the shim when re-enabling if the target el was moved", function() {
            element.enableShim();

            element.disableShim();

            element.setXY([500, 450]);
            element.setSize(50, 60);

            element.enableShim();

            expectBox(500, 450, 50, 60);
        });

        it("should hide the shim when the target element is hidden", function() {
            element.enableShim();

            var shim = element.shim,
                shimEl = shim.el;

            element.hide();

            expect(shim.el).toBeNull();
            expect(shimEl.isVisible()).toBe(false);
        });

        it("should show and realign the shim when the target element is shown", function() {
            element.hide();

            element.enableShim();

            element.show();

            expect(element.shim.el.isVisible()).toBe(true);
            expectBox(250, 150, 200, 100);
        });

        it("should hide the shim when the target element is hidden using setDisplayed", function() {
            element.enableShim();

            var shim = element.shim,
                shimEl = shim.el;

            element.setDisplayed(false);

            expect(shim.el).toBeNull();
            expect(shimEl.isVisible()).toBe(false);
        });

        it("should show and realign the shim when the target element is shown using setDisplayed", function() {
            element.setDisplayed(false);

            element.enableShim();

            element.setDisplayed(true);

            expect(element.shim.el.isVisible()).toBe(true);
            expectBox(250, 150, 200, 100);
        });

        it("should disable the shim when disableShim is called", function() {
            element.enableShim();

            var shimEl = element.shim.el;

            expect(element.shim.disabled).toBe(false);
            expect(element.shim.el.isVisible()).toBe(true);

            element.disableShim();

            expect(element.shim.disabled).toBe(true);
            expect(shimEl.isVisible()).toBe(false);
            expect(element.shim.el).toBeNull();
        });

        it("should enable a disabled shim when enableShim is called", function() {
            element.enableShim();
            element.disableShim();
            element.enableShim();

            expect(element.shim.disabled).toBe(false);
            expect(element.shim.el.isVisible()).toBe(true);
        });

        it("should realign a disabled shim when it is re-enabled", function() {
            element.enableShim();
            element.disableShim();

            // move the element while shim is disabled to make sure it re-aligns correctly
            element.setXY([575, 325]);
            element.setSize(250, 315);

            element.enableShim();

            expectBox(575, 325, 250, 315);
        });

        it("should not attempt to hide a disabled shim when the element is hidden", function() {
            element.enableShim();
            element.disableShim();

            spyOn(element.shim, 'hide').andCallThrough();

            element.hide();

            expect(element.shim.hide).not.toHaveBeenCalled();
        });

        it("should not show a disabled shim when the element is shown", function() {
            element.enableShim();

            element.hide();

            element.disableShim();

            element.show();

            expect(element.shim.el).toBeNull();
            expect(element.shim.disabled).toBe(true);
        });
    });

    describe("shadow", function() {
        var offsets = {
                // offsets for the default 'drop' shadow
                x: 4,
                y: 4,
                w: -4,
                h: -4
            },
            element;

        beforeEach(function() {
            element = Ext.getBody().createChild({
                style: 'position:absolute;left:250px;top:150px;width:200px;height:100px;'
            });
        });

        afterEach(function() {
            if (!element.isDestroyed) {
                element.destroy();
            }
        });

        function expectBox(x, y, w, h) {
            var box = element.shadow.el.getBox();

            expect(box.x).toBe(x + offsets.x);
            expect(box.y).toBe(y + offsets.y);
            expect(box.width).toBe(w + offsets.w);
            expect(box.height).toBe(h + offsets.h);
        }

        it("should not have a shadow by default", function() {
            expect(element.shadow).toBeUndefined();
        });

        it("should create a shadow", function() {
            element.enableShadow();

            expect(element.shadow instanceof Ext.dom.Shadow).toBe(true);
        });

        it("should show the shadow upon creation", function() {
            element.enableShadow();

            expect(element.shadow.el.isVisible()).toBe(true);
        });

        it("should insert the shadow as the previousSibling of its target", function() {
            element.enableShadow();

            expect(element.dom.previousSibling).toBe(element.shadow.el.dom);
        });

        it("should have the correct CSS class", function() {
            element.enableShadow();

            expect(element.shadow.el).toHaveCls(
                Ext.supports.CSS3BoxShadow ? 'x-css-shadow' : 'x-ie-shadow'
            );
        });

        it("should have a role of 'presentation'", function() {
            element.enableShadow();

            expect(element.shadow.el.dom.getAttribute('role')).toBe('presentation');
        });

        it("should not show the shadow if the element is not visible", function() {
            element.hide();

            element.enableShadow();

            expect(element.shadow.el).toBeUndefined();
        });

        it("should not show the shadow upon creation if ths isVisible parameter is false", function() {
            // private api used by components to avoid an isVisible check
            element.enableShadow(null, false);

            expect(element.shadow.el).toBeUndefined();
        });

        it("should hide the shadow when the element is destroyed", function() {
            element.enableShadow();

            var shadow = element.shadow,
                shadowEl = shadow.el;

            element.destroy();

            expect(shadow.el).toBeNull();
            expect(shadowEl.isVisible()).toBe(false);
            // should NOT destroy the shadow - it is returned to the pool
            expect(shadowEl.isDestroyed).toBeFalsy();
        });

        it("should use DISPLAY visibilityMode for the shadow", function() {
            element.enableShadow();

            expect(element.shadow.el.getVisibilityMode()).toBe(Ext.Element.DISPLAY);
        });

        it("should allow fixed positioning to be configured", function() {
            element.enableShadow({
                fixed: true
            });

            expect(element.shadow.el.getStyle('position')).toBe('fixed');
        });

        it("should set the z-index of the shadow when the z-index of the element is set", function() {
            element.enableShadow();

            element.setZIndex(174);

            expect(parseInt(element.shadow.el.getStyle('z-index'), 10)).toBe(174);
        });

        it("should set the shadow's offset", function() {
            element.enableShadow({
                offset: 10
            });

            expect(element.shadow.offset).toBe(10);
        });

        it("should set the shadow's mode", function() {
            element.enableShadow({
                mode: 'drop'
            });

            expect(element.shadow.mode).toBe('drop');
        });

        it("should align the shadow to the target element", function() {
            element.enableShadow();

            expectBox(250, 150, 200, 100);
        });

        it("should realign the shadow using passed coordinates", function() {
            element.enableShadow();

            element.shadow.realign(25, 50, 75, 85);

            expectBox(25, 50, 75, 85);
        });

        it("should realign the shadow in response to setLeft()", function() {
            element.enableShadow();

            element.setLeft(300);

            expectBox(300, 150, 200, 100);
        });

        it("should realign the shadow in response to setTop()", function() {
            element.enableShadow();

            element.setTop(300);

            expectBox(250, 300, 200, 100);
        });

        it("should realign the shadow in response to setLocalX()", function() {
            element.enableShadow();

            element.setLocalX(300);

            expectBox(300, 150, 200, 100);
        });

        it("should realign the shadow in response to setLocalY()", function() {
            element.enableShadow();

            element.setLocalY(300);

            expectBox(250, 300, 200, 100);
        });

        it("should realign the shadow in response to setLocalXY()", function() {
            element.enableShadow();

            element.setLocalXY(300, 400);

            expectBox(300, 400, 200, 100);
        });

        it("should realign the shadow in response to setX()", function() {
            element.enableShadow();

            element.setX(300);

            expectBox(300, 150, 200, 100);
        });

        it("should realign the shadow in response to setY()", function() {
            element.enableShadow();

            element.setY(300);

            expectBox(250, 300, 200, 100);
        });

        it("should realign the shadow in response to setXY()", function() {
            element.enableShadow();

            element.setXY([300, 400]);

            expectBox(300, 400, 200, 100);
        });

        it("should realign the shadow in response to setWidth()", function() {
            element.enableShadow();

            element.setWidth(400);

            expectBox(250, 150, 400, 100);
        });

        it("should realign the shadow in response to setHeight()", function() {
            element.enableShadow();

            element.setHeight(400);

            expectBox(250, 150, 200, 400);
        });

        it("should realign the shadow in response to setSize()", function() {
            element.enableShadow();

            element.setSize(450, 550);

            expectBox(250, 150, 450, 550);
        });

        it("should realign the shadow in response to setBox()", function() {
            element.enableShadow();

            element.setBox({
                x: 300,
                y: 400,
                width: 500,
                height: 600
            });

            expectBox(300, 400, 500, 600);
        });

        it("should disable the shadow", function() {
            element.enableShadow();

            var shadowEl = element.shadow.el;

            element.disableShadow();

            expect(element.shadow.el).toBeNull();
            expect(shadowEl.isVisible()).toBe(false);
        });

        it("should re-enable the shadow after disabling", function() {
            element.enableShadow();

            element.disableShadow();

            element.enableShadow();

            expect(element.shadow.el.isVisible()).toBe(true);
            expectBox(250, 150, 200, 100);
        });

        it("should realign the shadow when re-enabling if the target el was moved", function() {
            element.enableShadow();

            element.disableShadow();

            element.setXY([500, 450]);
            element.setSize(50, 60);

            element.enableShadow();

            expectBox(500, 450, 50, 60);
        });

        it("should hide the shadow when the target element is hidden", function() {
            element.enableShadow();

            var shadow = element.shadow,
                shadowEl = shadow.el;

            element.hide();

            expect(shadow.el).toBeNull();
            expect(shadowEl.isVisible()).toBe(false);
        });

        it("should show and realign the shadow when the target element is shown", function() {
            element.hide();

            element.enableShadow();

            element.show();

            expect(element.shadow.el.isVisible()).toBe(true);
            expectBox(250, 150, 200, 100);
        });

        it("should hide the shadow when the target element is hidden using setDisplayed", function() {
            element.enableShadow();

            var shadow = element.shadow,
                shadowEl = shadow.el;

            element.setDisplayed(false);

            expect(shadow.el).toBeNull();
            expect(shadowEl.isVisible()).toBe(false);
        });

        it("should show and realign the shadow when the target element is shown using setDisplayed", function() {
            element.setDisplayed(false);

            element.enableShadow();

            element.setDisplayed(true);

            expect(element.shadow.el.isVisible()).toBe(true);
            expectBox(250, 150, 200, 100);
        });

        it("should disable the shadow when disableShadow is called", function() {
            element.enableShadow();

            var shadow = element.shadow.el;

            expect(element.shadow.disabled).toBe(false);
            expect(element.shadow.el.isVisible()).toBe(true);

            element.disableShadow();

            expect(element.shadow.disabled).toBe(true);
            expect(shadow.isVisible()).toBe(false);
            expect(element.shadow.el).toBeNull();
        });

        it("should enable a disabled shadow when enableShadow is called", function() {
            element.enableShadow();
            element.disableShadow();
            element.enableShadow();

            expect(element.shadow.disabled).toBe(false);
            expect(element.shadow.el.isVisible()).toBe(true);
        });

        it("should realign a disabled shadow when it is re-enabled", function() {
            element.enableShadow();
            element.disableShadow();

            // move the element while shadow is disabled to make sure it re-aligns correctly
            element.setXY([575, 325]);
            element.setSize(250, 315);

            element.enableShadow();

            expectBox(575, 325, 250, 315);
        });

        it("should not attempt to hide a disabled shadow when the element is hidden", function() {
            element.enableShadow();
            element.disableShadow();

            spyOn(element.shadow, 'hide').andCallThrough();

            element.hide();

            expect(element.shadow.hide).not.toHaveBeenCalled();
        });

        it("should not show a disabled shadow when the element is shown", function() {
            element.enableShadow();

            element.hide();

            element.disableShadow();

            element.show();

            expect(element.shadow.el).toBeNull();
            expect(element.shadow.disabled).toBe(true);
        });

        (Ext.supports.Opacity ? it : xit)("should set the opacity of the shadow when the opacity of the element is changed", function() {
            element.enableShadow();

            element.setStyle('opacity', '0.5');

            expect(element.shadow.el.getStyle('opacity')).toBe('0.5');
        });

        describe("animate:false (default)", function() {
            it("should hide the shadow during setWidth animation", function() {
                var animationDone = false,
                    shadow, shadowEl;

                element.enableShadow();

                shadow = element.shadow;
                shadowEl = shadow.el;

                expect(shadowEl.isVisible()).toBe(true);

                element.setWidth(400, {
                    duration: 200,
                    listeners: {
                        afteranimate: function() {
                            animationDone = true;
                        }
                    }
                });

                waitsFor(function() {
                    return !shadow.el && !shadowEl.isVisible();
                }, "Shadow was never hidden", 150);

                waitsFor(function() {
                    return animationDone;
                }, "Animation never completed", 300);

                runs(function() {
                    expect(element.shadow.el.isVisible()).toBe(true);
                    expectBox(250, 150, 400, 100);
                });
            });

            it("should hide the shadow during setHeight animation", function() {
                var animationDone = false,
                    shadow, shadowEl;

                element.enableShadow();

                shadow = element.shadow;
                shadowEl = shadow.el;

                expect(shadowEl.isVisible()).toBe(true);

                element.setHeight(500, {
                    duration: 200,
                    listeners: {
                        afteranimate: function() {
                            animationDone = true;
                        }
                    }
                });

                waitsFor(function() {
                    return !shadow.el && !shadowEl.isVisible();
                }, "Shadow was never hidden", 150);

                waitsFor(function() {
                    return animationDone;
                }, "Animation never completed", 300);

                runs(function() {
                    expect(element.shadow.el.isVisible()).toBe(true);
                    expectBox(250, 150, 200, 500);
                });
            });

            it("should hide the shadow during setSize animation", function() {
                var animationDone = false,
                    shadow, shadowEl;

                element.enableShadow();

                shadow = element.shadow;
                shadowEl = shadow.el;

                expect(shadowEl.isVisible()).toBe(true);

                element.setSize(500, 400, {
                    duration: 200,
                    listeners: {
                        afteranimate: function() {
                            animationDone = true;
                        }
                    }
                });

                waitsFor(function() {
                    return !shadow.el && !shadowEl.isVisible();
                }, "Shadow was never hidden", 150);

                waitsFor(function() {
                    return animationDone;
                }, "Animation never completed", 300);

                runs(function() {
                    expect(element.shadow.el.isVisible()).toBe(true);
                    expectBox(250, 150, 500, 400);
                });
            });

            it("should hide the shadow during setX animation", function() {
                var animationDone = false,
                    shadow, shadowEl;

                element.enableShadow();

                shadow = element.shadow;
                shadowEl = shadow.el;

                expect(shadowEl.isVisible()).toBe(true);

                element.setX(300, {
                    duration: 200,
                    listeners: {
                        afteranimate: function() {
                            animationDone = true;
                        }
                    }
                });

                waitsFor(function() {
                    return !shadow.el && !shadowEl.isVisible();
                }, "Shadow was never hidden", 150);

                waitsFor(function() {
                    return animationDone;
                }, "Animation never completed", 300);

                runs(function() {
                    expect(element.shadow.el.isVisible()).toBe(true);
                    expectBox(300, 150, 200, 100);
                });
            });

            it("should hide the shadow during setY animation", function() {
                var animationDone = false,
                    shadow, shadowEl;

                element.enableShadow();

                shadow = element.shadow;
                shadowEl = shadow.el;

                expect(shadowEl.isVisible()).toBe(true);

                element.setY(350, {
                    duration: 200,
                    listeners: {
                        afteranimate: function() {
                            animationDone = true;
                        }
                    }
                });

                waitsFor(function() {
                    return !shadow.el && !shadowEl.isVisible();
                }, "Shadow was never hidden", 150);

                waitsFor(function() {
                    return animationDone;
                }, "Animation never completed", 300);

                runs(function() {
                    expect(element.shadow.el.isVisible()).toBe(true);
                    expectBox(250, 350, 200, 100);
                });
            });

            it("should hide the shadow during setXY animation", function() {
                var animationDone = false,
                    shadow, shadowEl;

                element.enableShadow();

                shadow = element.shadow;
                shadowEl = shadow.el;

                expect(shadowEl.isVisible()).toBe(true);

                element.setXY([350, 400], {
                    duration: 200,
                    listeners: {
                        afteranimate: function() {
                            animationDone = true;
                        }
                    }
                });

                waitsFor(function() {
                    return !shadow.el && !shadowEl.isVisible();
                }, "Shadow was never hidden", 150);

                waitsFor(function() {
                    return animationDone;
                }, "Animation never completed", 300);

                runs(function() {
                    expect(element.shadow.el.isVisible()).toBe(true);
                    expectBox(350, 400, 200, 100);
                });
            });

            describe("with disabled shadow", function() {
                it("should not show the shadow after setWidth animation", function() {
                    var animationDone = false;

                    element.enableShadow();
                    element.disableShadow();

                    spyOn(element.shadow, 'show').andCallThrough();

                    element.setWidth(400, {
                        duration: 10,
                        listeners: {
                            afteranimate: function() {
                                animationDone = true;
                            }
                        }
                    });

                    waitsFor(function() {
                        return animationDone;
                    }, "Animation never completed", 100);

                    runs(function() {
                        expect(element.shadow.show).not.toHaveBeenCalled();
                        expect(element.shadow.el).toBeNull();
                    });
                });

                it("should not show the shadow after setHeight animation", function() {
                    var animationDone = false;

                    element.enableShadow();
                    element.disableShadow();

                    spyOn(element.shadow, 'show').andCallThrough();

                    element.setHeight(400, {
                        duration: 10,
                        listeners: {
                            afteranimate: function() {
                                animationDone = true;
                            }
                        }
                    });

                    waitsFor(function() {
                        return animationDone;
                    }, "Animation never completed", 100);

                    runs(function() {
                        expect(element.shadow.show).not.toHaveBeenCalled();
                        expect(element.shadow.el).toBeNull();
                    });
                });

                it("should not show the shadow after setSize animation", function() {
                    var animationDone = false;

                    element.enableShadow();
                    element.disableShadow();

                    spyOn(element.shadow, 'show').andCallThrough();

                    element.setSize(400, 500, {
                        duration: 10,
                        listeners: {
                            afteranimate: function() {
                                animationDone = true;
                            }
                        }
                    });

                    waitsFor(function() {
                        return animationDone;
                    }, "Animation never completed", 100);

                    runs(function() {
                        expect(element.shadow.show).not.toHaveBeenCalled();
                        expect(element.shadow.el).toBeNull();
                    });
                });

                it("should not show the shadow after setX animation", function() {
                    var animationDone = false;

                    element.enableShadow();
                    element.disableShadow();

                    spyOn(element.shadow, 'show').andCallThrough();

                    element.setX(300, {
                        duration: 10,
                        listeners: {
                            afteranimate: function() {
                                animationDone = true;
                            }
                        }
                    });

                    waitsFor(function() {
                        return animationDone;
                    }, "Animation never completed", 100);

                    runs(function() {
                        expect(element.shadow.show).not.toHaveBeenCalled();
                        expect(element.shadow.el).toBeNull();
                    });
                });

                it("should not show the shadow after setY animation", function() {
                    var animationDone = false;

                    element.enableShadow();
                    element.disableShadow();

                    spyOn(element.shadow, 'show').andCallThrough();

                    element.setY(300, {
                        duration: 10,
                        listeners: {
                            afteranimate: function() {
                                animationDone = true;
                            }
                        }
                    });

                    waitsFor(function() {
                        return animationDone;
                    }, "Animation never completed", 100);

                    runs(function() {
                        expect(element.shadow.show).not.toHaveBeenCalled();
                        expect(element.shadow.el).toBeNull();
                    });
                });

                it("should not show the shadow after setXY animation", function() {
                    var animationDone = false;

                    element.enableShadow();
                    element.disableShadow();

                    spyOn(element.shadow, 'show').andCallThrough();

                    element.setXY([400, 350], {
                        duration: 10,
                        listeners: {
                            afteranimate: function() {
                                animationDone = true;
                            }
                        }
                    });

                    waitsFor(function() {
                        return animationDone;
                    }, "Animation never completed", 100);

                    runs(function() {
                        expect(element.shadow.show).not.toHaveBeenCalled();
                        expect(element.shadow.el).toBeNull();
                    });
                });
            });
        });

        describe("animate:true", function() {
            it("should not hide the shadow during setWidth animation", function() {
                var animationDone = false;

                element.enableShadow({
                    animate: true
                });

                spyOn(element.shadow, 'hide').andCallThrough();

                expect(element.shadow.el.isVisible()).toBe(true);

                element.setWidth(400, {
                    duration: 50,
                    listeners: {
                        afteranimate: function() {
                            animationDone = true;
                        }
                    }
                });

                waitsFor(function() {
                    return animationDone;
                }, "Animation never completed", 300);

                runs(function() {
                    expect(element.shadow.hide).not.toHaveBeenCalled();
                    expect(element.shadow.el.isVisible()).toBe(true);
                    expectBox(250, 150, 400, 100);
                });
            });

            it("should not hide the shadow during setHeight animation", function() {
                var animationDone = false;

                element.enableShadow({
                    animate: true
                });

                spyOn(element.shadow, 'hide').andCallThrough();

                expect(element.shadow.el.isVisible()).toBe(true);

                element.setHeight(500, {
                    duration: 50,
                    listeners: {
                        afteranimate: function() {
                            animationDone = true;
                        }
                    }
                });

                waitsFor(function() {
                    return animationDone;
                }, "Animation never completed", 300);

                runs(function() {
                    expect(element.shadow.hide).not.toHaveBeenCalled();
                    expect(element.shadow.el.isVisible()).toBe(true);
                    expectBox(250, 150, 200, 500);
                });
            });

            it("should not hide the shadow during setSize animation", function() {
                var animationDone = false;

                element.enableShadow({
                    animate: true
                });

                spyOn(element.shadow, 'hide').andCallThrough();

                expect(element.shadow.el.isVisible()).toBe(true);

                element.setSize(500, 400, {
                    duration: 50,
                    listeners: {
                        afteranimate: function() {
                            animationDone = true;
                        }
                    }
                });

                waitsFor(function() {
                    return animationDone;
                }, "Animation never completed", 300);

                runs(function() {
                    expect(element.shadow.hide).not.toHaveBeenCalled();
                    expect(element.shadow.el.isVisible()).toBe(true);
                    expectBox(250, 150, 500, 400);
                });
            });

            it("should not hide the shadow during setX animation", function() {
                var animationDone = false;

                element.enableShadow({
                    animate: true
                });

                spyOn(element.shadow, 'hide').andCallThrough();

                expect(element.shadow.el.isVisible()).toBe(true);

                element.setX(300, {
                    duration: 50,
                    listeners: {
                        afteranimate: function() {
                            animationDone = true;
                        }
                    }
                });

                waitsFor(function() {
                    return animationDone;
                }, "Animation never completed", 300);

                runs(function() {
                    expect(element.shadow.hide).not.toHaveBeenCalled();
                    expect(element.shadow.el.isVisible()).toBe(true);
                    expectBox(300, 150, 200, 100);
                });
            });

            it("should not hide the shadow during setY animation", function() {
                var animationDone = false;

                element.enableShadow({
                    animate: true
                });

                spyOn(element.shadow, 'hide').andCallThrough();

                expect(element.shadow.el.isVisible()).toBe(true);

                element.setY(350, {
                    duration: 50,
                    listeners: {
                        afteranimate: function() {
                            animationDone = true;
                        }
                    }
                });

                waitsFor(function() {
                    return animationDone;
                }, "Animation never completed", 300);

                runs(function() {
                    expect(element.shadow.hide).not.toHaveBeenCalled();
                    expect(element.shadow.el.isVisible()).toBe(true);
                    expectBox(250, 350, 200, 100);
                });
            });

            it("should not hide the shadow during setXY animation", function() {
                var animationDone = false;

                element.enableShadow({
                    animate: true
                });

                spyOn(element.shadow, 'hide').andCallThrough();

                expect(element.shadow.el.isVisible()).toBe(true);

                element.setXY([350, 400], {
                    duration: 50,
                    listeners: {
                        afteranimate: function() {
                            animationDone = true;
                        }
                    }
                });

                waitsFor(function() {
                    return animationDone;
                }, "Animation never completed", 300);

                runs(function() {
                    expect(element.shadow.hide).not.toHaveBeenCalled();
                    expect(element.shadow.el.isVisible()).toBe(true);
                    expectBox(350, 400, 200, 100);
                });
            });
        });
    });

    describe("shim and shadow together", function() {
        var element;

        beforeEach(function() {
            element = Ext.getBody().createChild({
                style: 'position:absolute;left:250px;top:150px;width:200px;height:100px;'
            });
        });

        afterEach(function() {
            element.destroy();
        });

        function expectShimBox(x, y, w, h) {
            var box = element.shim.el.getBox();

            expect(box.x).toBe(x);
            expect(box.y).toBe(y);
            expect(box.width).toBe(w);
            expect(box.height).toBe(h);
        }

        it("should place the shim before the shadow in the dom if the shadow was created first", function() {
            element.enableShadow();
            element.enableShim();

            expect(element.prev()).toBe(element.shadow.el);
            expect(element.shadow.el.prev()).toBe(element.shim.el);
        });

        it("should place the shim before the shadow in the dom if the shim was created first", function() {
            element.enableShim();
            element.enableShadow();

            expect(element.prev()).toBe(element.shadow.el);
            expect(element.shadow.el.prev()).toBe(element.shim.el);
        });

        it("should have the correct dom order after hiding and showing the shim", function() {
            element.enableShim();
            element.enableShadow();

            element.shim.hide();

            // reset the pool to make sure we're pulling a fresh element out of the pool
            // and not reusing the existing element that's already in the right order
            element.shim.getPool().reset();

            element.shim.show();

            expect(element.prev()).toBe(element.shadow.el);
            expect(element.shadow.el.prev()).toBe(element.shim.el);
        });

        it("should have the correct dom order after hiding and showing the shadow", function() {
            element.enableShim();
            element.enableShadow();

            element.shadow.hide();

            // reset the pool to make sure we're pulling a fresh element out of the pool
            // and not reusing the existing element that's already in the right order
            element.shadow.getPool().reset();

            element.shadow.show();

            expect(element.prev()).toBe(element.shadow.el);
            expect(element.shadow.el.prev()).toBe(element.shim.el);
        });

        it("should size the shim to include the target and shadow with mode=='drop'", function() {
            element.enableShim();
            element.enableShadow({
                mode: 'drop',
                offset: 30
            });

            expectShimBox(250, 150, 230, 130);
        });

        it("should size the shim to include the target and shadow with mode=='sides'", function() {
            element.enableShadow({
                mode: 'sides',
                offset: 30
            });
            element.enableShim();

            expectShimBox(220, 150, 260, 130);
        });

        it("should size the shim to include the target and shadow with mode=='frame'", function() {
            element.enableShim();
            element.enableShadow({
                mode: 'frame',
                offset: 30
            });

            expectShimBox(220, 120, 260, 160);
        });

        it("should size the shim to include the target and shadow with mode=='bottom'", function() {
            element.enableShadow({
                mode: 'bottom',
                offset: 30
            });
            element.enableShim();

            expectShimBox(220, 150, 260, 130);
        });
    });

}, "/src/dom/Element.js");
