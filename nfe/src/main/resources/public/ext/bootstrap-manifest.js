var Ext = Ext || { }; Ext.manifest = {
  "paths": {
    "Ext": "src",
    "Ext-more": "overrides/Ext-more.js",
    "Ext.AbstractManager": "packages/sencha-core/src/AbstractManager.js",
    "Ext.Ajax": "packages/sencha-core/src/Ajax.js",
    "Ext.AnimationQueue": "packages/sencha-core/src/AnimationQueue.js",
    "Ext.Array": "packages/sencha-core/src/lang/Array.js",
    "Ext.Assert": "packages/sencha-core/src/lang/Assert.js",
    "Ext.Base": "packages/sencha-core/src/class/Base.js",
    "Ext.Boot": ".sencha/package/Boot.js",
    "Ext.Class": "packages/sencha-core/src/class/Class.js",
    "Ext.ClassManager": "packages/sencha-core/src/class/ClassManager.js",
    "Ext.ComponentManager": "packages/sencha-core/src/ComponentManager.js",
    "Ext.ComponentQuery": "packages/sencha-core/src/ComponentQuery.js",
    "Ext.Config": "packages/sencha-core/src/class/Config.js",
    "Ext.Configurator": "packages/sencha-core/src/class/Configurator.js",
    "Ext.Date": "packages/sencha-core/src/lang/Date.js",
    "Ext.Error": "packages/sencha-core/src/lang/Error.js",
    "Ext.Evented": "packages/sencha-core/src/Evented.js",
    "Ext.Factory": "packages/sencha-core/src/mixin/Factoryable.js",
    "Ext.Function": "packages/sencha-core/src/lang/Function.js",
    "Ext.GlobalEvents": "packages/sencha-core/src/GlobalEvents.js",
    "Ext.Inventory": "packages/sencha-core/src/class/Inventory.js",
    "Ext.JSON": "packages/sencha-core/src/JSON.js",
    "Ext.Loader": "packages/sencha-core/src/class/Loader.js",
    "Ext.Mixin": "packages/sencha-core/src/class/Mixin.js",
    "Ext.Msg": "src/window/MessageBox.js",
    "Ext.Number": "packages/sencha-core/src/lang/Number.js",
    "Ext.Object": "packages/sencha-core/src/lang/Object.js",
    "Ext.Script": "packages/sencha-core/src/class/Inventory.js",
    "Ext.String": "packages/sencha-core/src/lang/String.js",
    "Ext.String.format": "packages/sencha-core/src/Template.js",
    "Ext.TaskQueue": "packages/sencha-core/src/TaskQueue.js",
    "Ext.Template": "packages/sencha-core/src/Template.js",
    "Ext.Util": "packages/sencha-core/src/Util.js",
    "Ext.Version": "packages/sencha-core/src/util/Version.js",
    "Ext.Widget": "packages/sencha-core/src/Widget.js",
    "Ext.XTemplate": "packages/sencha-core/src/XTemplate.js",
    "Ext.app.ViewModel": "packages/sencha-core/src/app/ViewModel.js",
    "Ext.app.bind": "packages/sencha-core/src/app/bind",
    "Ext.browser": "packages/sencha-core/src/env/Browser.js",
    "Ext.class": "packages/sencha-core/src/class",
    "Ext.data": "packages/sencha-core/src/data",
    "Ext.direct": "packages/sencha-core/src/direct",
    "Ext.dom": "packages/sencha-core/src/dom",
    "Ext.dom.ButtonElement": "src/dom/ButtonElement.js",
    "Ext.dom.Layer": "src/dom/Layer.js",
    "Ext.env": "packages/sencha-core/src/env",
    "Ext.event": "packages/sencha-core/src/event",
    "Ext.feature": "packages/sencha-core/src/env/Feature.js",
    "Ext.fx.Animation": "packages/sencha-core/src/fx/Animation.js",
    "Ext.fx.Runner": "packages/sencha-core/src/fx/Runner.js",
    "Ext.fx.State": "packages/sencha-core/src/fx/State.js",
    "Ext.fx.animation": "packages/sencha-core/src/fx/animation",
    "Ext.fx.easing": "packages/sencha-core/src/fx/easing",
    "Ext.fx.layout": "packages/sencha-core/src/fx/layout",
    "Ext.fx.runner": "packages/sencha-core/src/fx/runner",
    "Ext.lang": "packages/sencha-core/src/lang",
    "Ext.mixin": "packages/sencha-core/src/mixin",
    "Ext.os": "packages/sencha-core/src/env/OS.js",
    "Ext.overrides": "overrides",
    "Ext.overrides.util.Positionable": "overrides/Positionable.js",
    "Ext.perf": "packages/sencha-core/src/perf",
    "Ext.scroll": "packages/sencha-core/src/scroll",
    "Ext.supports": "packages/sencha-core/src/env/Feature.js",
    "Ext.util": "packages/sencha-core/src/util",
    "Ext.util.Animate": "src/util/Animate.js",
    "Ext.util.CSS": "src/util/CSS.js",
    "Ext.util.ClickRepeater": "src/util/ClickRepeater.js",
    "Ext.util.ComponentDragger": "src/util/ComponentDragger.js",
    "Ext.util.Cookies": "src/util/Cookies.js",
    "Ext.util.ElementContainer": "src/util/ElementContainer.js",
    "Ext.util.Floating": "src/util/Floating.js",
    "Ext.util.Focusable": "src/util/Focusable.js",
    "Ext.util.FocusableContainer": "src/util/FocusableContainer.js",
    "Ext.util.Format.format": "packages/sencha-core/src/Template.js",
    "Ext.util.History": "src/util/History.js",
    "Ext.util.KeyMap": "src/util/KeyMap.js",
    "Ext.util.KeyNav": "src/util/KeyNav.js",
    "Ext.util.Memento": "src/util/Memento.js",
    "Ext.util.ProtoElement": "src/util/ProtoElement.js",
    "Ext.util.Queue": "src/util/Queue.js",
    "Ext.util.Renderable": "src/util/Renderable.js",
    "Ext.util.StoreHolder": "src/util/StoreHolder.js"
  },
  "loadOrder": [
    {
      "path": "packages/sencha-core/src/event/ListenerStack.js",
      "requires": [],
      "uses": [],
      "idx": 0
    },
    {
      "path": "packages/sencha-core/src/event/Controller.js",
      "requires": [],
      "uses": [],
      "idx": 1
    },
    {
      "path": "packages/sencha-core/src/event/Dispatcher.js",
      "requires": [
        0,
        1
      ],
      "uses": [],
      "idx": 2
    },
    {
      "path": "packages/sencha-core/src/class/Mixin.js",
      "requires": [],
      "uses": [],
      "idx": 3
    },
    {
      "path": "packages/sencha-core/src/mixin/Identifiable.js",
      "requires": [],
      "uses": [],
      "idx": 4
    },
    {
      "path": "packages/sencha-core/src/mixin/Observable.js",
      "requires": [
        2,
        3,
        4
      ],
      "uses": [],
      "idx": 5
    },
    {
      "path": "packages/sencha-core/src/util/HashMap.js",
      "requires": [
        5
      ],
      "uses": [],
      "idx": 6
    },
    {
      "path": "packages/sencha-core/src/AbstractManager.js",
      "requires": [
        6
      ],
      "uses": [],
      "idx": 7
    },
    {
      "path": "packages/sencha-core/src/data/flash/BinaryXhr.js",
      "requires": [],
      "uses": [
        24
      ],
      "idx": 8
    },
    {
      "path": "packages/sencha-core/src/data/Connection.js",
      "requires": [
        5,
        8
      ],
      "uses": [
        23,
        24
      ],
      "idx": 9
    },
    {
      "path": "packages/sencha-core/src/Ajax.js",
      "requires": [
        9
      ],
      "uses": [],
      "idx": 10
    },
    {
      "path": "packages/sencha-core/src/AnimationQueue.js",
      "requires": [],
      "uses": [],
      "idx": 11
    },
    {
      "path": "packages/sencha-core/src/ComponentManager.js",
      "requires": [],
      "uses": [],
      "idx": 12
    },
    {
      "path": "packages/sencha-core/src/util/Operators.js",
      "requires": [],
      "uses": [],
      "idx": 13
    },
    {
      "path": "packages/sencha-core/src/util/LruCache.js",
      "requires": [
        6
      ],
      "uses": [],
      "idx": 14
    },
    {
      "path": "packages/sencha-core/src/ComponentQuery.js",
      "requires": [
        12,
        13,
        14
      ],
      "uses": [
        60
      ],
      "idx": 15
    },
    {
      "path": "packages/sencha-core/src/Evented.js",
      "requires": [
        5
      ],
      "uses": [],
      "idx": 16
    },
    {
      "path": "packages/sencha-core/src/util/Positionable.js",
      "requires": [
        18
      ],
      "uses": [
        23,
        113
      ],
      "idx": 17
    },
    {
      "path": "overrides/Positionable.js",
      "requires": [],
      "uses": [],
      "idx": 18
    },
    {
      "path": "packages/sencha-core/src/dom/UnderlayPool.js",
      "requires": [],
      "uses": [
        23
      ],
      "idx": 19
    },
    {
      "path": "packages/sencha-core/src/dom/Underlay.js",
      "requires": [
        19
      ],
      "uses": [],
      "idx": 20
    },
    {
      "path": "packages/sencha-core/src/dom/Shadow.js",
      "requires": [
        20
      ],
      "uses": [],
      "idx": 21
    },
    {
      "path": "packages/sencha-core/src/dom/Shim.js",
      "requires": [
        20
      ],
      "uses": [],
      "idx": 22
    },
    {
      "path": "packages/sencha-core/src/dom/Element.js",
      "requires": [
        5,
        17,
        21,
        22,
        55
      ],
      "uses": [
        24,
        25,
        26,
        60,
        65,
        113,
        218,
        270
      ],
      "idx": 23
    },
    {
      "path": "packages/sencha-core/src/GlobalEvents.js",
      "requires": [
        5,
        23,
        56
      ],
      "uses": [],
      "idx": 24
    },
    {
      "path": "packages/sencha-core/src/dom/Fly.js",
      "requires": [
        23
      ],
      "uses": [],
      "idx": 25
    },
    {
      "path": "packages/sencha-core/src/dom/CompositeElementLite.js",
      "requires": [
        25
      ],
      "uses": [
        23
      ],
      "idx": 26
    },
    {
      "path": "src/rtl/dom/Element.js",
      "requires": [
        26
      ],
      "uses": [
        23
      ],
      "idx": 27
    },
    {
      "path": "packages/sencha-core/src/util/Filter.js",
      "requires": [],
      "uses": [],
      "idx": 28
    },
    {
      "path": "packages/sencha-core/src/util/DelayedTask.js",
      "requires": [],
      "uses": [
        24
      ],
      "idx": 29
    },
    {
      "path": "packages/sencha-core/src/util/Event.js",
      "requires": [
        29
      ],
      "uses": [],
      "idx": 30
    },
    {
      "path": "packages/sencha-core/src/util/Observable.js",
      "requires": [
        30
      ],
      "uses": [],
      "idx": 31
    },
    {
      "path": "packages/sencha-core/src/util/AbstractMixedCollection.js",
      "requires": [
        28,
        31
      ],
      "uses": [],
      "idx": 32
    },
    {
      "path": "packages/sencha-core/src/util/Sorter.js",
      "requires": [],
      "uses": [],
      "idx": 33
    },
    {
      "path": "packages/sencha-core/src/util/Sortable.js",
      "requires": [
        33
      ],
      "uses": [
        35
      ],
      "idx": 34
    },
    {
      "path": "packages/sencha-core/src/util/MixedCollection.js",
      "requires": [
        32,
        34
      ],
      "uses": [],
      "idx": 35
    },
    {
      "path": "packages/sencha-core/src/util/TaskRunner.js",
      "requires": [],
      "uses": [
        24
      ],
      "idx": 36
    },
    {
      "path": "src/fx/target/Target.js",
      "requires": [],
      "uses": [],
      "idx": 37
    },
    {
      "path": "src/fx/target/Element.js",
      "requires": [
        37
      ],
      "uses": [],
      "idx": 38
    },
    {
      "path": "src/fx/target/ElementCSS.js",
      "requires": [
        38
      ],
      "uses": [],
      "idx": 39
    },
    {
      "path": "src/fx/target/CompositeElement.js",
      "requires": [
        38
      ],
      "uses": [],
      "idx": 40
    },
    {
      "path": "src/fx/target/CompositeElementCSS.js",
      "requires": [
        39,
        40
      ],
      "uses": [],
      "idx": 41
    },
    {
      "path": "src/fx/target/Sprite.js",
      "requires": [
        37
      ],
      "uses": [],
      "idx": 42
    },
    {
      "path": "src/fx/target/CompositeSprite.js",
      "requires": [
        42
      ],
      "uses": [],
      "idx": 43
    },
    {
      "path": "src/fx/target/Component.js",
      "requires": [
        37
      ],
      "uses": [
        24
      ],
      "idx": 44
    },
    {
      "path": "src/fx/Queue.js",
      "requires": [
        6
      ],
      "uses": [],
      "idx": 45
    },
    {
      "path": "src/fx/Manager.js",
      "requires": [
        35,
        36,
        38,
        39,
        40,
        41,
        42,
        43,
        44,
        45
      ],
      "uses": [],
      "idx": 46
    },
    {
      "path": "src/fx/Animator.js",
      "requires": [
        31,
        46
      ],
      "uses": [
        52
      ],
      "idx": 47
    },
    {
      "path": "src/fx/CubicBezier.js",
      "requires": [],
      "uses": [],
      "idx": 48
    },
    {
      "path": "src/fx/Easing.js",
      "requires": [
        48
      ],
      "uses": [],
      "idx": 49
    },
    {
      "path": "src/fx/DrawPath.js",
      "requires": [],
      "uses": [],
      "idx": 50
    },
    {
      "path": "src/fx/PropertyHandler.js",
      "requires": [
        50
      ],
      "uses": [],
      "idx": 51
    },
    {
      "path": "src/fx/Anim.js",
      "requires": [
        31,
        46,
        47,
        48,
        49,
        51
      ],
      "uses": [],
      "idx": 52
    },
    {
      "path": "src/util/Animate.js",
      "requires": [
        46,
        52
      ],
      "uses": [],
      "idx": 53
    },
    {
      "path": "packages/sencha-core/src/dom/GarbageCollector.js",
      "requires": [],
      "uses": [],
      "idx": 54
    },
    {
      "path": "overrides/dom/Element.js",
      "requires": [
        23,
        25,
        26,
        53,
        54
      ],
      "uses": [
        46,
        47,
        52,
        60,
        218,
        305,
        316,
        371,
        373,
        429
      ],
      "idx": 55
    },
    {
      "path": "overrides/GlobalEvents.js",
      "requires": [],
      "uses": [],
      "idx": 56
    },
    {
      "path": "packages/sencha-core/src/JSON.js",
      "requires": [],
      "uses": [],
      "idx": 57
    },
    {
      "path": "packages/sencha-core/src/TaskQueue.js",
      "requires": [
        11
      ],
      "uses": [],
      "idx": 58
    },
    {
      "path": "packages/sencha-core/src/util/Format.js",
      "requires": [],
      "uses": [
        60,
        218
      ],
      "idx": 59
    },
    {
      "path": "packages/sencha-core/src/Template.js",
      "requires": [
        59
      ],
      "uses": [
        218
      ],
      "idx": 60
    },
    {
      "path": "packages/sencha-core/src/mixin/Inheritable.js",
      "requires": [
        3
      ],
      "uses": [],
      "idx": 61
    },
    {
      "path": "packages/sencha-core/src/mixin/Bindable.js",
      "requires": [],
      "uses": [
        66
      ],
      "idx": 62
    },
    {
      "path": "packages/sencha-core/src/Widget.js",
      "requires": [
        16,
        61,
        62,
        98
      ],
      "uses": [
        12,
        15,
        23
      ],
      "idx": 63
    },
    {
      "path": "src/util/ProtoElement.js",
      "requires": [],
      "uses": [
        23,
        218
      ],
      "idx": 64
    },
    {
      "path": "packages/sencha-core/src/dom/CompositeElement.js",
      "requires": [
        26
      ],
      "uses": [],
      "idx": 65
    },
    {
      "path": "packages/sencha-core/src/mixin/Factoryable.js",
      "requires": [],
      "uses": [],
      "idx": 66
    },
    {
      "path": "packages/sencha-core/src/scroll/Scroller.js",
      "requires": [
        16,
        66
      ],
      "uses": [
        84,
        86
      ],
      "idx": 67
    },
    {
      "path": "src/rtl/scroll/Scroller.js",
      "requires": [],
      "uses": [],
      "idx": 68
    },
    {
      "path": "packages/sencha-core/src/fx/easing/Abstract.js",
      "requires": [],
      "uses": [],
      "idx": 69
    },
    {
      "path": "packages/sencha-core/src/fx/easing/Momentum.js",
      "requires": [
        69
      ],
      "uses": [],
      "idx": 70
    },
    {
      "path": "packages/sencha-core/src/fx/easing/Bounce.js",
      "requires": [
        69
      ],
      "uses": [],
      "idx": 71
    },
    {
      "path": "packages/sencha-core/src/fx/easing/BoundMomentum.js",
      "requires": [
        69,
        70,
        71
      ],
      "uses": [],
      "idx": 72
    },
    {
      "path": "packages/sencha-core/src/fx/easing/Linear.js",
      "requires": [
        69
      ],
      "uses": [],
      "idx": 73
    },
    {
      "path": "packages/sencha-core/src/fx/easing/EaseOut.js",
      "requires": [
        73
      ],
      "uses": [],
      "idx": 74
    },
    {
      "path": "packages/sencha-core/src/util/translatable/Abstract.js",
      "requires": [
        16,
        73
      ],
      "uses": [
        11
      ],
      "idx": 75
    },
    {
      "path": "packages/sencha-core/src/util/translatable/Dom.js",
      "requires": [
        75
      ],
      "uses": [],
      "idx": 76
    },
    {
      "path": "packages/sencha-core/src/util/translatable/CssTransform.js",
      "requires": [
        76
      ],
      "uses": [],
      "idx": 77
    },
    {
      "path": "packages/sencha-core/src/util/translatable/ScrollPosition.js",
      "requires": [
        76
      ],
      "uses": [],
      "idx": 78
    },
    {
      "path": "packages/sencha-core/src/util/translatable/ScrollParent.js",
      "requires": [
        76
      ],
      "uses": [],
      "idx": 79
    },
    {
      "path": "packages/sencha-core/src/util/translatable/CssPosition.js",
      "requires": [
        76
      ],
      "uses": [],
      "idx": 80
    },
    {
      "path": "packages/sencha-core/src/util/Translatable.js",
      "requires": [
        77,
        78,
        79,
        80
      ],
      "uses": [],
      "idx": 81
    },
    {
      "path": "packages/sencha-core/src/scroll/Indicator.js",
      "requires": [
        63
      ],
      "uses": [],
      "idx": 82
    },
    {
      "path": "src/rtl/scroll/Indicator.js",
      "requires": [],
      "uses": [],
      "idx": 83
    },
    {
      "path": "packages/sencha-core/src/scroll/TouchScroller.js",
      "requires": [
        24,
        67,
        72,
        74,
        81,
        82
      ],
      "uses": [],
      "idx": 84
    },
    {
      "path": "src/rtl/scroll/TouchScroller.js",
      "requires": [],
      "uses": [],
      "idx": 85
    },
    {
      "path": "packages/sencha-core/src/scroll/DomScroller.js",
      "requires": [
        67
      ],
      "uses": [],
      "idx": 86
    },
    {
      "path": "src/rtl/scroll/DomScroller.js",
      "requires": [],
      "uses": [],
      "idx": 87
    },
    {
      "path": "src/util/ElementContainer.js",
      "requires": [],
      "uses": [],
      "idx": 88
    },
    {
      "path": "src/util/Renderable.js",
      "requires": [
        23
      ],
      "uses": [
        96,
        126,
        218
      ],
      "idx": 89
    },
    {
      "path": "src/rtl/util/Renderable.js",
      "requires": [],
      "uses": [],
      "idx": 90
    },
    {
      "path": "src/state/Provider.js",
      "requires": [
        31
      ],
      "uses": [],
      "idx": 91
    },
    {
      "path": "src/state/Manager.js",
      "requires": [
        91
      ],
      "uses": [],
      "idx": 92
    },
    {
      "path": "src/state/Stateful.js",
      "requires": [
        92
      ],
      "uses": [
        36
      ],
      "idx": 93
    },
    {
      "path": "src/util/Floating.js",
      "requires": [],
      "uses": [
        23,
        24,
        311
      ],
      "idx": 94
    },
    {
      "path": "src/util/Focusable.js",
      "requires": [
        29
      ],
      "uses": [
        96
      ],
      "idx": 95
    },
    {
      "path": "src/Component.js",
      "requires": [
        12,
        15,
        17,
        24,
        31,
        53,
        61,
        62,
        64,
        65,
        67,
        84,
        86,
        88,
        89,
        93,
        94,
        95
      ],
      "uses": [
        18,
        23,
        29,
        46,
        55,
        56,
        98,
        118,
        119,
        121,
        123,
        126,
        147,
        218,
        219,
        306,
        307,
        308,
        311,
        321,
        323,
        390,
        463,
        597,
        609,
        615
      ],
      "idx": 96
    },
    {
      "path": "src/layout/container/border/Region.js",
      "requires": [],
      "uses": [],
      "idx": 97
    },
    {
      "path": "overrides/Widget.js",
      "requires": [
        63,
        96
      ],
      "uses": [
        23,
        323
      ],
      "idx": 98
    },
    {
      "path": "src/rtl/Component.js",
      "requires": [],
      "uses": [
        23
      ],
      "idx": 99
    },
    {
      "path": "packages/sencha-core/src/event/gesture/Recognizer.js",
      "requires": [
        4
      ],
      "uses": [],
      "idx": 100
    },
    {
      "path": "packages/sencha-core/src/event/gesture/SingleTouch.js",
      "requires": [
        100
      ],
      "uses": [],
      "idx": 101
    },
    {
      "path": "packages/sencha-core/src/event/gesture/DoubleTap.js",
      "requires": [
        101
      ],
      "uses": [],
      "idx": 102
    },
    {
      "path": "packages/sencha-core/src/event/gesture/Drag.js",
      "requires": [
        101
      ],
      "uses": [],
      "idx": 103
    },
    {
      "path": "packages/sencha-core/src/event/gesture/Swipe.js",
      "requires": [
        101
      ],
      "uses": [],
      "idx": 104
    },
    {
      "path": "packages/sencha-core/src/event/gesture/EdgeSwipe.js",
      "requires": [
        104
      ],
      "uses": [
        23
      ],
      "idx": 105
    },
    {
      "path": "packages/sencha-core/src/event/gesture/LongPress.js",
      "requires": [
        101
      ],
      "uses": [],
      "idx": 106
    },
    {
      "path": "packages/sencha-core/src/event/gesture/MultiTouch.js",
      "requires": [
        100
      ],
      "uses": [],
      "idx": 107
    },
    {
      "path": "packages/sencha-core/src/event/gesture/Pinch.js",
      "requires": [
        107
      ],
      "uses": [],
      "idx": 108
    },
    {
      "path": "packages/sencha-core/src/event/gesture/Rotate.js",
      "requires": [
        107
      ],
      "uses": [],
      "idx": 109
    },
    {
      "path": "packages/sencha-core/src/event/gesture/Tap.js",
      "requires": [
        101
      ],
      "uses": [],
      "idx": 110
    },
    {
      "path": "packages/sencha-core/src/event/publisher/Publisher.js",
      "requires": [],
      "uses": [],
      "idx": 111
    },
    {
      "path": "packages/sencha-core/src/util/Offset.js",
      "requires": [],
      "uses": [],
      "idx": 112
    },
    {
      "path": "packages/sencha-core/src/util/Region.js",
      "requires": [
        112
      ],
      "uses": [],
      "idx": 113
    },
    {
      "path": "packages/sencha-core/src/util/Point.js",
      "requires": [
        113
      ],
      "uses": [],
      "idx": 114
    },
    {
      "path": "packages/sencha-core/src/event/Event.js",
      "requires": [
        114,
        118
      ],
      "uses": [],
      "idx": 115
    },
    {
      "path": "packages/sencha-core/src/event/publisher/Dom.js",
      "requires": [
        24,
        111,
        115,
        119
      ],
      "uses": [],
      "idx": 116
    },
    {
      "path": "src/rtl/event/Event.js",
      "requires": [],
      "uses": [
        23
      ],
      "idx": 117
    },
    {
      "path": "overrides/event/Event.js",
      "requires": [],
      "uses": [
        24
      ],
      "idx": 118
    },
    {
      "path": "overrides/event/publisher/Dom.js",
      "requires": [
        116
      ],
      "uses": [],
      "idx": 119
    },
    {
      "path": "packages/sencha-core/src/event/publisher/Gesture.js",
      "requires": [
        11,
        114,
        116,
        121
      ],
      "uses": [
        54,
        115
      ],
      "idx": 120
    },
    {
      "path": "overrides/event/publisher/Gesture.js",
      "requires": [],
      "uses": [
        115
      ],
      "idx": 121
    },
    {
      "path": "packages/sencha-core/src/event/publisher/Focus.js",
      "requires": [
        116
      ],
      "uses": [
        115
      ],
      "idx": 122
    },
    {
      "path": "overrides/Ext-more.js",
      "requires": [
        2,
        100,
        101,
        102,
        103,
        104,
        105,
        106,
        107,
        108,
        109,
        110,
        116,
        120,
        122
      ],
      "uses": [],
      "idx": 123
    },
    {
      "path": "packages/sencha-core/src/util/XTemplateParser.js",
      "requires": [],
      "uses": [],
      "idx": 124
    },
    {
      "path": "packages/sencha-core/src/util/XTemplateCompiler.js",
      "requires": [
        124
      ],
      "uses": [],
      "idx": 125
    },
    {
      "path": "packages/sencha-core/src/XTemplate.js",
      "requires": [
        60,
        125
      ],
      "uses": [],
      "idx": 126
    },
    {
      "path": "packages/sencha-core/src/util/CollectionKey.js",
      "requires": [
        4
      ],
      "uses": [],
      "idx": 127
    },
    {
      "path": "packages/sencha-core/src/util/Grouper.js",
      "requires": [
        33
      ],
      "uses": [],
      "idx": 128
    },
    {
      "path": "packages/sencha-core/src/util/Collection.js",
      "requires": [
        5,
        28,
        33,
        127,
        128
      ],
      "uses": [
        193,
        194,
        195
      ],
      "idx": 129
    },
    {
      "path": "packages/sencha-core/src/util/Scheduler.js",
      "requires": [
        5,
        129
      ],
      "uses": [],
      "idx": 130
    },
    {
      "path": "packages/sencha-core/src/util/ObjectTemplate.js",
      "requires": [
        126
      ],
      "uses": [],
      "idx": 131
    },
    {
      "path": "packages/sencha-core/src/data/schema/Role.js",
      "requires": [],
      "uses": [
        66
      ],
      "idx": 132
    },
    {
      "path": "packages/sencha-core/src/data/schema/Association.js",
      "requires": [
        132
      ],
      "uses": [],
      "idx": 133
    },
    {
      "path": "packages/sencha-core/src/data/schema/OneToOne.js",
      "requires": [
        133
      ],
      "uses": [],
      "idx": 134
    },
    {
      "path": "packages/sencha-core/src/data/schema/ManyToOne.js",
      "requires": [
        133
      ],
      "uses": [],
      "idx": 135
    },
    {
      "path": "packages/sencha-core/src/data/schema/ManyToMany.js",
      "requires": [
        133
      ],
      "uses": [],
      "idx": 136
    },
    {
      "path": "packages/sencha-core/src/util/Inflector.js",
      "requires": [],
      "uses": [],
      "idx": 137
    },
    {
      "path": "packages/sencha-core/src/data/schema/Namer.js",
      "requires": [
        66,
        137
      ],
      "uses": [],
      "idx": 138
    },
    {
      "path": "packages/sencha-core/src/data/schema/Schema.js",
      "requires": [
        66,
        131,
        134,
        135,
        136,
        138
      ],
      "uses": [],
      "idx": 139
    },
    {
      "path": "packages/sencha-core/src/data/Batch.js",
      "requires": [
        5
      ],
      "uses": [],
      "idx": 140
    },
    {
      "path": "packages/sencha-core/src/data/matrix/Slice.js",
      "requires": [],
      "uses": [],
      "idx": 141
    },
    {
      "path": "packages/sencha-core/src/data/matrix/Side.js",
      "requires": [
        141
      ],
      "uses": [],
      "idx": 142
    },
    {
      "path": "packages/sencha-core/src/data/matrix/Matrix.js",
      "requires": [
        142
      ],
      "uses": [],
      "idx": 143
    },
    {
      "path": "packages/sencha-core/src/data/session/ChangesVisitor.js",
      "requires": [],
      "uses": [],
      "idx": 144
    },
    {
      "path": "packages/sencha-core/src/data/session/ChildChangesVisitor.js",
      "requires": [
        144
      ],
      "uses": [],
      "idx": 145
    },
    {
      "path": "packages/sencha-core/src/data/session/BatchVisitor.js",
      "requires": [],
      "uses": [
        140
      ],
      "idx": 146
    },
    {
      "path": "packages/sencha-core/src/data/Session.js",
      "requires": [
        139,
        140,
        143,
        144,
        145,
        146
      ],
      "uses": [],
      "idx": 147
    },
    {
      "path": "packages/sencha-core/src/util/Schedulable.js",
      "requires": [],
      "uses": [],
      "idx": 148
    },
    {
      "path": "packages/sencha-core/src/app/bind/BaseBinding.js",
      "requires": [
        148
      ],
      "uses": [],
      "idx": 149
    },
    {
      "path": "packages/sencha-core/src/app/bind/Binding.js",
      "requires": [
        149
      ],
      "uses": [],
      "idx": 150
    },
    {
      "path": "packages/sencha-core/src/app/bind/AbstractStub.js",
      "requires": [
        148,
        150
      ],
      "uses": [],
      "idx": 151
    },
    {
      "path": "packages/sencha-core/src/app/bind/Stub.js",
      "requires": [
        150,
        151
      ],
      "uses": [
        156
      ],
      "idx": 152
    },
    {
      "path": "packages/sencha-core/src/app/bind/LinkStub.js",
      "requires": [
        152
      ],
      "uses": [],
      "idx": 153
    },
    {
      "path": "packages/sencha-core/src/app/bind/RootStub.js",
      "requires": [
        151,
        152,
        153
      ],
      "uses": [],
      "idx": 154
    },
    {
      "path": "packages/sencha-core/src/app/bind/Multi.js",
      "requires": [
        149
      ],
      "uses": [],
      "idx": 155
    },
    {
      "path": "packages/sencha-core/src/app/bind/Formula.js",
      "requires": [
        14,
        148
      ],
      "uses": [],
      "idx": 156
    },
    {
      "path": "packages/sencha-core/src/app/bind/Template.js",
      "requires": [
        59
      ],
      "uses": [],
      "idx": 157
    },
    {
      "path": "packages/sencha-core/src/app/bind/TemplateBinding.js",
      "requires": [
        149,
        155,
        157
      ],
      "uses": [],
      "idx": 158
    },
    {
      "path": "packages/sencha-core/src/data/AbstractStore.js",
      "requires": [
        5,
        28,
        66,
        129,
        139
      ],
      "uses": [
        212
      ],
      "idx": 159
    },
    {
      "path": "packages/sencha-core/src/data/LocalStore.js",
      "requires": [
        3
      ],
      "uses": [
        129
      ],
      "idx": 160
    },
    {
      "path": "packages/sencha-core/src/data/ChainedStore.js",
      "requires": [
        159,
        160
      ],
      "uses": [
        212
      ],
      "idx": 161
    },
    {
      "path": "packages/sencha-core/src/app/ViewModel.js",
      "requires": [
        4,
        66,
        130,
        147,
        153,
        154,
        155,
        156,
        158,
        161
      ],
      "uses": [
        29,
        139
      ],
      "idx": 162
    },
    {
      "path": "packages/sencha-core/src/data/Error.js",
      "requires": [],
      "uses": [],
      "idx": 163
    },
    {
      "path": "packages/sencha-core/src/data/ErrorCollection.js",
      "requires": [
        35,
        163
      ],
      "uses": [
        172
      ],
      "idx": 164
    },
    {
      "path": "packages/sencha-core/src/data/operation/Operation.js",
      "requires": [],
      "uses": [],
      "idx": 165
    },
    {
      "path": "packages/sencha-core/src/data/operation/Create.js",
      "requires": [
        165
      ],
      "uses": [],
      "idx": 166
    },
    {
      "path": "packages/sencha-core/src/data/operation/Destroy.js",
      "requires": [
        165
      ],
      "uses": [],
      "idx": 167
    },
    {
      "path": "packages/sencha-core/src/data/operation/Read.js",
      "requires": [
        165
      ],
      "uses": [],
      "idx": 168
    },
    {
      "path": "packages/sencha-core/src/data/operation/Update.js",
      "requires": [
        165
      ],
      "uses": [],
      "idx": 169
    },
    {
      "path": "packages/sencha-core/src/data/SortTypes.js",
      "requires": [],
      "uses": [],
      "idx": 170
    },
    {
      "path": "packages/sencha-core/src/data/validator/Validator.js",
      "requires": [
        66
      ],
      "uses": [],
      "idx": 171
    },
    {
      "path": "packages/sencha-core/src/data/field/Field.js",
      "requires": [
        66,
        170,
        171
      ],
      "uses": [],
      "idx": 172
    },
    {
      "path": "packages/sencha-core/src/data/field/Boolean.js",
      "requires": [
        172
      ],
      "uses": [],
      "idx": 173
    },
    {
      "path": "packages/sencha-core/src/data/field/Date.js",
      "requires": [
        172
      ],
      "uses": [],
      "idx": 174
    },
    {
      "path": "packages/sencha-core/src/data/field/Integer.js",
      "requires": [
        172
      ],
      "uses": [],
      "idx": 175
    },
    {
      "path": "packages/sencha-core/src/data/field/Number.js",
      "requires": [
        172
      ],
      "uses": [],
      "idx": 176
    },
    {
      "path": "packages/sencha-core/src/data/field/String.js",
      "requires": [
        172
      ],
      "uses": [],
      "idx": 177
    },
    {
      "path": "packages/sencha-core/src/data/identifier/Generator.js",
      "requires": [
        66
      ],
      "uses": [],
      "idx": 178
    },
    {
      "path": "packages/sencha-core/src/data/identifier/Sequential.js",
      "requires": [
        178
      ],
      "uses": [],
      "idx": 179
    },
    {
      "path": "packages/sencha-core/src/data/Model.js",
      "requires": [
        139,
        164,
        165,
        166,
        167,
        168,
        169,
        171,
        172,
        173,
        174,
        175,
        176,
        177,
        178,
        179
      ],
      "uses": [
        66,
        182,
        217
      ],
      "idx": 180
    },
    {
      "path": "packages/sencha-core/src/data/ResultSet.js",
      "requires": [],
      "uses": [],
      "idx": 181
    },
    {
      "path": "packages/sencha-core/src/data/reader/Reader.js",
      "requires": [
        5,
        66,
        126,
        181
      ],
      "uses": [
        139
      ],
      "idx": 182
    },
    {
      "path": "packages/sencha-core/src/data/writer/Writer.js",
      "requires": [
        66
      ],
      "uses": [],
      "idx": 183
    },
    {
      "path": "packages/sencha-core/src/data/proxy/Proxy.js",
      "requires": [
        5,
        66,
        139,
        182,
        183
      ],
      "uses": [
        140,
        165,
        166,
        167,
        168,
        169,
        180
      ],
      "idx": 184
    },
    {
      "path": "packages/sencha-core/src/data/proxy/Client.js",
      "requires": [
        184
      ],
      "uses": [],
      "idx": 185
    },
    {
      "path": "packages/sencha-core/src/data/proxy/Memory.js",
      "requires": [
        185
      ],
      "uses": [
        28,
        34
      ],
      "idx": 186
    },
    {
      "path": "packages/sencha-core/src/data/ProxyStore.js",
      "requires": [
        159,
        165,
        166,
        167,
        168,
        169,
        180,
        184,
        186
      ],
      "uses": [
        29,
        139
      ],
      "idx": 187
    },
    {
      "path": "packages/sencha-core/src/data/proxy/Server.js",
      "requires": [
        184
      ],
      "uses": [
        60,
        211
      ],
      "idx": 188
    },
    {
      "path": "packages/sencha-core/src/data/proxy/Ajax.js",
      "requires": [
        10,
        188
      ],
      "uses": [],
      "idx": 189
    },
    {
      "path": "packages/sencha-core/src/data/reader/Json.js",
      "requires": [
        57,
        182
      ],
      "uses": [],
      "idx": 190
    },
    {
      "path": "packages/sencha-core/src/data/writer/Json.js",
      "requires": [
        183
      ],
      "uses": [],
      "idx": 191
    },
    {
      "path": "packages/sencha-core/src/util/Group.js",
      "requires": [
        129
      ],
      "uses": [],
      "idx": 192
    },
    {
      "path": "packages/sencha-core/src/util/SorterCollection.js",
      "requires": [
        33,
        129
      ],
      "uses": [],
      "idx": 193
    },
    {
      "path": "packages/sencha-core/src/util/FilterCollection.js",
      "requires": [
        28,
        129
      ],
      "uses": [],
      "idx": 194
    },
    {
      "path": "packages/sencha-core/src/util/GroupCollection.js",
      "requires": [
        129,
        192,
        193,
        194
      ],
      "uses": [],
      "idx": 195
    },
    {
      "path": "packages/sencha-core/src/data/Store.js",
      "requires": [
        29,
        160,
        180,
        187,
        189,
        190,
        191,
        195
      ],
      "uses": [
        128,
        200,
        212
      ],
      "idx": 196
    },
    {
      "path": "packages/sencha-core/src/data/reader/Array.js",
      "requires": [
        190
      ],
      "uses": [],
      "idx": 197
    },
    {
      "path": "packages/sencha-core/src/data/ArrayStore.js",
      "requires": [
        186,
        196,
        197
      ],
      "uses": [],
      "idx": 198
    },
    {
      "path": "packages/sencha-core/src/data/PageMap.js",
      "requires": [
        14
      ],
      "uses": [],
      "idx": 199
    },
    {
      "path": "packages/sencha-core/src/data/BufferedStore.js",
      "requires": [
        28,
        33,
        128,
        187,
        199
      ],
      "uses": [
        193,
        194,
        195
      ],
      "idx": 200
    },
    {
      "path": "packages/sencha-core/src/direct/Manager.js",
      "requires": [
        31,
        35
      ],
      "uses": [],
      "idx": 201
    },
    {
      "path": "packages/sencha-core/src/data/proxy/Direct.js",
      "requires": [
        188,
        201
      ],
      "uses": [],
      "idx": 202
    },
    {
      "path": "packages/sencha-core/src/data/DirectStore.js",
      "requires": [
        196,
        202
      ],
      "uses": [],
      "idx": 203
    },
    {
      "path": "packages/sencha-core/src/data/JsonP.js",
      "requires": [],
      "uses": [
        24
      ],
      "idx": 204
    },
    {
      "path": "packages/sencha-core/src/data/proxy/JsonP.js",
      "requires": [
        188,
        204
      ],
      "uses": [],
      "idx": 205
    },
    {
      "path": "packages/sencha-core/src/data/JsonPStore.js",
      "requires": [
        190,
        196,
        205
      ],
      "uses": [],
      "idx": 206
    },
    {
      "path": "packages/sencha-core/src/data/JsonStore.js",
      "requires": [
        189,
        190,
        191,
        196
      ],
      "uses": [],
      "idx": 207
    },
    {
      "path": "packages/sencha-core/src/data/ModelManager.js",
      "requires": [
        139
      ],
      "uses": [
        180
      ],
      "idx": 208
    },
    {
      "path": "packages/sencha-core/src/data/NodeInterface.js",
      "requires": [
        5,
        173,
        175,
        177,
        191
      ],
      "uses": [
        139
      ],
      "idx": 209
    },
    {
      "path": "packages/sencha-core/src/data/NodeStore.js",
      "requires": [
        196,
        209
      ],
      "uses": [
        180
      ],
      "idx": 210
    },
    {
      "path": "packages/sencha-core/src/data/Request.js",
      "requires": [],
      "uses": [],
      "idx": 211
    },
    {
      "path": "packages/sencha-core/src/data/StoreManager.js",
      "requires": [
        35,
        198
      ],
      "uses": [
        66,
        186,
        191,
        196,
        197
      ],
      "idx": 212
    },
    {
      "path": "packages/sencha-core/src/mixin/Queryable.js",
      "requires": [],
      "uses": [
        15
      ],
      "idx": 213
    },
    {
      "path": "packages/sencha-core/src/data/TreeModel.js",
      "requires": [
        180,
        209,
        213
      ],
      "uses": [],
      "idx": 214
    },
    {
      "path": "packages/sencha-core/src/data/TreeStore.js",
      "requires": [
        33,
        209,
        210,
        214
      ],
      "uses": [],
      "idx": 215
    },
    {
      "path": "packages/sencha-core/src/data/Types.js",
      "requires": [
        170
      ],
      "uses": [],
      "idx": 216
    },
    {
      "path": "packages/sencha-core/src/data/Validation.js",
      "requires": [
        180
      ],
      "uses": [],
      "idx": 217
    },
    {
      "path": "packages/sencha-core/src/dom/Helper.js",
      "requires": [
        219
      ],
      "uses": [
        60
      ],
      "idx": 218
    },
    {
      "path": "overrides/dom/Helper.js",
      "requires": [],
      "uses": [],
      "idx": 219
    },
    {
      "path": "packages/sencha-core/src/dom/Query.js",
      "requires": [
        13,
        218
      ],
      "uses": [
        14
      ],
      "idx": 220
    },
    {
      "path": "packages/sencha-core/src/data/reader/Xml.js",
      "requires": [
        182,
        220
      ],
      "uses": [],
      "idx": 221
    },
    {
      "path": "packages/sencha-core/src/data/writer/Xml.js",
      "requires": [
        183
      ],
      "uses": [],
      "idx": 222
    },
    {
      "path": "packages/sencha-core/src/data/XmlStore.js",
      "requires": [
        189,
        196,
        221,
        222
      ],
      "uses": [],
      "idx": 223
    },
    {
      "path": "packages/sencha-core/src/data/identifier/Negative.js",
      "requires": [
        179
      ],
      "uses": [],
      "idx": 224
    },
    {
      "path": "packages/sencha-core/src/data/identifier/Uuid.js",
      "requires": [
        178
      ],
      "uses": [],
      "idx": 225
    },
    {
      "path": "packages/sencha-core/src/data/proxy/WebStorage.js",
      "requires": [
        179,
        185
      ],
      "uses": [
        33,
        60,
        181
      ],
      "idx": 226
    },
    {
      "path": "packages/sencha-core/src/data/proxy/LocalStorage.js",
      "requires": [
        226
      ],
      "uses": [],
      "idx": 227
    },
    {
      "path": "packages/sencha-core/src/data/proxy/Rest.js",
      "requires": [
        189
      ],
      "uses": [],
      "idx": 228
    },
    {
      "path": "packages/sencha-core/src/data/proxy/SessionStorage.js",
      "requires": [
        226
      ],
      "uses": [],
      "idx": 229
    },
    {
      "path": "packages/sencha-core/src/data/proxy/Sql.js",
      "requires": [
        185
      ],
      "uses": [
        129,
        181
      ],
      "idx": 230
    },
    {
      "path": "packages/sencha-core/src/data/validator/Bound.js",
      "requires": [
        171
      ],
      "uses": [
        60
      ],
      "idx": 231
    },
    {
      "path": "packages/sencha-core/src/data/validator/Format.js",
      "requires": [
        171
      ],
      "uses": [],
      "idx": 232
    },
    {
      "path": "packages/sencha-core/src/data/validator/Email.js",
      "requires": [
        232
      ],
      "uses": [],
      "idx": 233
    },
    {
      "path": "packages/sencha-core/src/data/validator/List.js",
      "requires": [
        171
      ],
      "uses": [],
      "idx": 234
    },
    {
      "path": "packages/sencha-core/src/data/validator/Exclusion.js",
      "requires": [
        234
      ],
      "uses": [],
      "idx": 235
    },
    {
      "path": "packages/sencha-core/src/data/validator/Inclusion.js",
      "requires": [
        234
      ],
      "uses": [],
      "idx": 236
    },
    {
      "path": "packages/sencha-core/src/data/validator/Length.js",
      "requires": [
        231
      ],
      "uses": [],
      "idx": 237
    },
    {
      "path": "packages/sencha-core/src/data/validator/Presence.js",
      "requires": [
        171
      ],
      "uses": [],
      "idx": 238
    },
    {
      "path": "packages/sencha-core/src/data/validator/Range.js",
      "requires": [
        231
      ],
      "uses": [],
      "idx": 239
    },
    {
      "path": "packages/sencha-core/src/direct/Event.js",
      "requires": [],
      "uses": [],
      "idx": 240
    },
    {
      "path": "packages/sencha-core/src/direct/RemotingEvent.js",
      "requires": [
        240
      ],
      "uses": [
        201
      ],
      "idx": 241
    },
    {
      "path": "packages/sencha-core/src/direct/ExceptionEvent.js",
      "requires": [
        241
      ],
      "uses": [],
      "idx": 242
    },
    {
      "path": "packages/sencha-core/src/direct/Provider.js",
      "requires": [
        31
      ],
      "uses": [],
      "idx": 243
    },
    {
      "path": "packages/sencha-core/src/direct/JsonProvider.js",
      "requires": [
        243
      ],
      "uses": [
        201,
        242
      ],
      "idx": 244
    },
    {
      "path": "packages/sencha-core/src/direct/PollingProvider.js",
      "requires": [
        10,
        29,
        244
      ],
      "uses": [
        201,
        242,
        302
      ],
      "idx": 245
    },
    {
      "path": "packages/sencha-core/src/direct/RemotingMethod.js",
      "requires": [],
      "uses": [],
      "idx": 246
    },
    {
      "path": "packages/sencha-core/src/direct/Transaction.js",
      "requires": [],
      "uses": [],
      "idx": 247
    },
    {
      "path": "packages/sencha-core/src/direct/RemotingProvider.js",
      "requires": [
        29,
        35,
        244,
        246,
        247
      ],
      "uses": [
        10,
        57,
        201,
        242
      ],
      "idx": 248
    },
    {
      "path": "packages/sencha-core/src/util/paintmonitor/Abstract.js",
      "requires": [],
      "uses": [
        23
      ],
      "idx": 249
    },
    {
      "path": "packages/sencha-core/src/util/paintmonitor/CssAnimation.js",
      "requires": [
        249
      ],
      "uses": [],
      "idx": 250
    },
    {
      "path": "packages/sencha-core/src/util/paintmonitor/OverflowChange.js",
      "requires": [
        249
      ],
      "uses": [],
      "idx": 251
    },
    {
      "path": "packages/sencha-core/src/util/PaintMonitor.js",
      "requires": [
        250,
        251
      ],
      "uses": [],
      "idx": 252
    },
    {
      "path": "packages/sencha-core/src/event/publisher/ElementPaint.js",
      "requires": [
        58,
        111,
        252
      ],
      "uses": [],
      "idx": 253
    },
    {
      "path": "packages/sencha-core/src/mixin/Templatable.js",
      "requires": [
        3
      ],
      "uses": [
        23
      ],
      "idx": 254
    },
    {
      "path": "packages/sencha-core/src/util/sizemonitor/Abstract.js",
      "requires": [
        58,
        254
      ],
      "uses": [],
      "idx": 255
    },
    {
      "path": "packages/sencha-core/src/util/sizemonitor/Default.js",
      "requires": [
        255
      ],
      "uses": [],
      "idx": 256
    },
    {
      "path": "packages/sencha-core/src/util/sizemonitor/Scroll.js",
      "requires": [
        255
      ],
      "uses": [
        58
      ],
      "idx": 257
    },
    {
      "path": "packages/sencha-core/src/util/sizemonitor/OverflowChange.js",
      "requires": [
        255
      ],
      "uses": [
        58
      ],
      "idx": 258
    },
    {
      "path": "packages/sencha-core/src/util/SizeMonitor.js",
      "requires": [
        256,
        257,
        258
      ],
      "uses": [],
      "idx": 259
    },
    {
      "path": "packages/sencha-core/src/event/publisher/ElementSize.js",
      "requires": [
        111,
        259
      ],
      "uses": [
        58
      ],
      "idx": 260
    },
    {
      "path": "packages/sencha-core/src/fx/State.js",
      "requires": [],
      "uses": [],
      "idx": 261
    },
    {
      "path": "packages/sencha-core/src/fx/animation/Abstract.js",
      "requires": [
        16,
        261
      ],
      "uses": [],
      "idx": 262
    },
    {
      "path": "packages/sencha-core/src/fx/animation/Slide.js",
      "requires": [
        262
      ],
      "uses": [],
      "idx": 263
    },
    {
      "path": "packages/sencha-core/src/fx/animation/SlideOut.js",
      "requires": [
        263
      ],
      "uses": [],
      "idx": 264
    },
    {
      "path": "packages/sencha-core/src/fx/animation/Fade.js",
      "requires": [
        262
      ],
      "uses": [],
      "idx": 265
    },
    {
      "path": "packages/sencha-core/src/fx/animation/FadeOut.js",
      "requires": [
        265
      ],
      "uses": [],
      "idx": 266
    },
    {
      "path": "packages/sencha-core/src/fx/animation/Flip.js",
      "requires": [
        262
      ],
      "uses": [],
      "idx": 267
    },
    {
      "path": "packages/sencha-core/src/fx/animation/Pop.js",
      "requires": [
        262
      ],
      "uses": [],
      "idx": 268
    },
    {
      "path": "packages/sencha-core/src/fx/animation/PopOut.js",
      "requires": [
        268
      ],
      "uses": [],
      "idx": 269
    },
    {
      "path": "packages/sencha-core/src/fx/Animation.js",
      "requires": [
        263,
        264,
        265,
        266,
        267,
        268,
        269
      ],
      "uses": [
        262
      ],
      "idx": 270
    },
    {
      "path": "packages/sencha-core/src/fx/runner/Css.js",
      "requires": [
        16,
        270
      ],
      "uses": [],
      "idx": 271
    },
    {
      "path": "packages/sencha-core/src/fx/runner/CssTransition.js",
      "requires": [
        11,
        271
      ],
      "uses": [
        270
      ],
      "idx": 272
    },
    {
      "path": "packages/sencha-core/src/fx/Runner.js",
      "requires": [
        272
      ],
      "uses": [],
      "idx": 273
    },
    {
      "path": "packages/sencha-core/src/fx/animation/Cube.js",
      "requires": [
        262
      ],
      "uses": [],
      "idx": 274
    },
    {
      "path": "packages/sencha-core/src/fx/animation/Wipe.js",
      "requires": [
        270
      ],
      "uses": [],
      "idx": 275
    },
    {
      "path": "packages/sencha-core/src/fx/animation/WipeOut.js",
      "requires": [
        275
      ],
      "uses": [],
      "idx": 276
    },
    {
      "path": "packages/sencha-core/src/fx/easing/EaseIn.js",
      "requires": [
        73
      ],
      "uses": [],
      "idx": 277
    },
    {
      "path": "packages/sencha-core/src/fx/easing/Easing.js",
      "requires": [
        73
      ],
      "uses": [],
      "idx": 278
    },
    {
      "path": "packages/sencha-core/src/fx/layout/card/Abstract.js",
      "requires": [
        16
      ],
      "uses": [],
      "idx": 279
    },
    {
      "path": "packages/sencha-core/src/fx/layout/card/Style.js",
      "requires": [
        270,
        279
      ],
      "uses": [],
      "idx": 280
    },
    {
      "path": "packages/sencha-core/src/fx/layout/card/Slide.js",
      "requires": [
        280
      ],
      "uses": [],
      "idx": 281
    },
    {
      "path": "packages/sencha-core/src/fx/layout/card/Cover.js",
      "requires": [
        280
      ],
      "uses": [],
      "idx": 282
    },
    {
      "path": "packages/sencha-core/src/fx/layout/card/Reveal.js",
      "requires": [
        280
      ],
      "uses": [],
      "idx": 283
    },
    {
      "path": "packages/sencha-core/src/fx/layout/card/Fade.js",
      "requires": [
        280
      ],
      "uses": [],
      "idx": 284
    },
    {
      "path": "packages/sencha-core/src/fx/layout/card/Flip.js",
      "requires": [
        280
      ],
      "uses": [],
      "idx": 285
    },
    {
      "path": "packages/sencha-core/src/fx/layout/card/Pop.js",
      "requires": [
        280
      ],
      "uses": [],
      "idx": 286
    },
    {
      "path": "packages/sencha-core/src/fx/layout/card/Scroll.js",
      "requires": [
        73,
        279
      ],
      "uses": [
        11
      ],
      "idx": 287
    },
    {
      "path": "packages/sencha-core/src/fx/layout/Card.js",
      "requires": [
        281,
        282,
        283,
        284,
        285,
        286,
        287
      ],
      "uses": [
        279
      ],
      "idx": 288
    },
    {
      "path": "packages/sencha-core/src/fx/layout/card/Cube.js",
      "requires": [
        280
      ],
      "uses": [],
      "idx": 289
    },
    {
      "path": "packages/sencha-core/src/fx/layout/card/ScrollCover.js",
      "requires": [
        287
      ],
      "uses": [],
      "idx": 290
    },
    {
      "path": "packages/sencha-core/src/fx/layout/card/ScrollReveal.js",
      "requires": [
        287
      ],
      "uses": [],
      "idx": 291
    },
    {
      "path": "packages/sencha-core/src/fx/runner/CssAnimation.js",
      "requires": [
        271
      ],
      "uses": [
        270
      ],
      "idx": 292
    },
    {
      "path": "packages/sencha-core/src/mixin/Hookable.js",
      "requires": [
        3
      ],
      "uses": [],
      "idx": 293
    },
    {
      "path": "packages/sencha-core/src/mixin/Mashup.js",
      "requires": [
        3
      ],
      "uses": [],
      "idx": 294
    },
    {
      "path": "packages/sencha-core/src/mixin/Responsive.js",
      "requires": [
        3,
        24
      ],
      "uses": [
        23
      ],
      "idx": 295
    },
    {
      "path": "packages/sencha-core/src/mixin/Selectable.js",
      "requires": [
        3
      ],
      "uses": [
        35
      ],
      "idx": 296
    },
    {
      "path": "packages/sencha-core/src/mixin/Traversable.js",
      "requires": [
        3
      ],
      "uses": [],
      "idx": 297
    },
    {
      "path": "packages/sencha-core/src/perf/Accumulator.js",
      "requires": [
        126
      ],
      "uses": [],
      "idx": 298
    },
    {
      "path": "packages/sencha-core/src/perf/Monitor.js",
      "requires": [
        298
      ],
      "uses": [],
      "idx": 299
    },
    {
      "path": "packages/sencha-core/src/util/Base64.js",
      "requires": [],
      "uses": [],
      "idx": 300
    },
    {
      "path": "packages/sencha-core/src/util/LocalStorage.js",
      "requires": [],
      "uses": [],
      "idx": 301
    },
    {
      "path": "packages/sencha-core/src/util/TaskManager.js",
      "requires": [
        36
      ],
      "uses": [],
      "idx": 302
    },
    {
      "path": "packages/sencha-core/src/util/TextMetrics.js",
      "requires": [
        23
      ],
      "uses": [],
      "idx": 303
    },
    {
      "path": "src/Action.js",
      "requires": [],
      "uses": [],
      "idx": 304
    },
    {
      "path": "src/ElementLoader.js",
      "requires": [
        31
      ],
      "uses": [
        9,
        10
      ],
      "idx": 305
    },
    {
      "path": "src/ComponentLoader.js",
      "requires": [
        305
      ],
      "uses": [],
      "idx": 306
    },
    {
      "path": "src/layout/SizeModel.js",
      "requires": [],
      "uses": [],
      "idx": 307
    },
    {
      "path": "src/layout/Layout.js",
      "requires": [
        66,
        126,
        307
      ],
      "uses": [
        597
      ],
      "idx": 308
    },
    {
      "path": "src/layout/container/Container.js",
      "requires": [
        88,
        126,
        308
      ],
      "uses": [
        218
      ],
      "idx": 309
    },
    {
      "path": "src/layout/container/Auto.js",
      "requires": [
        309
      ],
      "uses": [
        126
      ],
      "idx": 310
    },
    {
      "path": "src/ZIndexManager.js",
      "requires": [
        193,
        194
      ],
      "uses": [
        23,
        24,
        129
      ],
      "idx": 311
    },
    {
      "path": "src/container/Container.js",
      "requires": [
        35,
        96,
        213,
        310,
        311
      ],
      "uses": [
        12,
        15,
        32,
        66
      ],
      "idx": 312
    },
    {
      "path": "src/layout/container/Editor.js",
      "requires": [
        309
      ],
      "uses": [],
      "idx": 313
    },
    {
      "path": "src/Editor.js",
      "requires": [
        312,
        313
      ],
      "uses": [
        12,
        23,
        29
      ],
      "idx": 314
    },
    {
      "path": "src/EventManager.js",
      "requires": [],
      "uses": [
        24
      ],
      "idx": 315
    },
    {
      "path": "src/util/KeyMap.js",
      "requires": [],
      "uses": [],
      "idx": 316
    },
    {
      "path": "src/util/KeyNav.js",
      "requires": [
        316
      ],
      "uses": [],
      "idx": 317
    },
    {
      "path": "src/FocusManager.js",
      "requires": [
        6,
        12,
        15,
        31,
        96,
        317
      ],
      "uses": [
        23,
        29
      ],
      "idx": 318
    },
    {
      "path": "src/Img.js",
      "requires": [
        96
      ],
      "uses": [],
      "idx": 319
    },
    {
      "path": "src/util/StoreHolder.js",
      "requires": [
        212
      ],
      "uses": [],
      "idx": 320
    },
    {
      "path": "src/LoadMask.js",
      "requires": [
        96,
        320
      ],
      "uses": [
        24,
        212
      ],
      "idx": 321
    },
    {
      "path": "src/layout/component/Component.js",
      "requires": [
        308
      ],
      "uses": [],
      "idx": 322
    },
    {
      "path": "src/layout/component/Auto.js",
      "requires": [
        322
      ],
      "uses": [],
      "idx": 323
    },
    {
      "path": "src/layout/component/ProgressBar.js",
      "requires": [
        323
      ],
      "uses": [],
      "idx": 324
    },
    {
      "path": "src/ProgressBar.js",
      "requires": [
        60,
        65,
        96,
        302,
        324
      ],
      "uses": [
        52,
        126
      ],
      "idx": 325
    },
    {
      "path": "src/ProgressBarWidget.js",
      "requires": [
        63,
        325
      ],
      "uses": [
        126
      ],
      "idx": 326
    },
    {
      "path": "src/app/EventDomain.js",
      "requires": [
        30
      ],
      "uses": [],
      "idx": 327
    },
    {
      "path": "src/app/domain/Component.js",
      "requires": [
        63,
        96,
        327
      ],
      "uses": [],
      "idx": 328
    },
    {
      "path": "src/app/EventBus.js",
      "requires": [
        328
      ],
      "uses": [
        327
      ],
      "idx": 329
    },
    {
      "path": "src/app/domain/Global.js",
      "requires": [
        327
      ],
      "uses": [],
      "idx": 330
    },
    {
      "path": "src/app/BaseController.js",
      "requires": [
        31,
        329,
        330
      ],
      "uses": [
        336,
        337,
        466
      ],
      "idx": 331
    },
    {
      "path": "src/app/Util.js",
      "requires": [],
      "uses": [],
      "idx": 332
    },
    {
      "path": "src/app/domain/Store.js",
      "requires": [
        159,
        327
      ],
      "uses": [],
      "idx": 333
    },
    {
      "path": "src/app/route/Queue.js",
      "requires": [],
      "uses": [
        35
      ],
      "idx": 334
    },
    {
      "path": "src/app/route/Route.js",
      "requires": [],
      "uses": [
        60
      ],
      "idx": 335
    },
    {
      "path": "src/util/History.js",
      "requires": [
        31
      ],
      "uses": [
        302
      ],
      "idx": 336
    },
    {
      "path": "src/app/route/Router.js",
      "requires": [
        334,
        335,
        336
      ],
      "uses": [],
      "idx": 337
    },
    {
      "path": "src/app/Controller.js",
      "requires": [
        12,
        212,
        328,
        331,
        332,
        333,
        337
      ],
      "uses": [
        15,
        139
      ],
      "idx": 338
    },
    {
      "path": "src/panel/Bar.js",
      "requires": [
        312
      ],
      "uses": [],
      "idx": 339
    },
    {
      "path": "src/rtl/panel/Bar.js",
      "requires": [],
      "uses": [],
      "idx": 340
    },
    {
      "path": "src/panel/Title.js",
      "requires": [
        96
      ],
      "uses": [],
      "idx": 341
    },
    {
      "path": "src/rtl/panel/Title.js",
      "requires": [],
      "uses": [],
      "idx": 342
    },
    {
      "path": "src/panel/Tool.js",
      "requires": [
        96
      ],
      "uses": [
        386
      ],
      "idx": 343
    },
    {
      "path": "src/panel/Header.js",
      "requires": [
        147,
        323,
        339,
        341,
        343
      ],
      "uses": [
        12
      ],
      "idx": 344
    },
    {
      "path": "src/toolbar/Fill.js",
      "requires": [
        96
      ],
      "uses": [],
      "idx": 345
    },
    {
      "path": "src/layout/container/boxOverflow/None.js",
      "requires": [
        66
      ],
      "uses": [],
      "idx": 346
    },
    {
      "path": "src/toolbar/Item.js",
      "requires": [
        96
      ],
      "uses": [],
      "idx": 347
    },
    {
      "path": "src/toolbar/Separator.js",
      "requires": [
        347
      ],
      "uses": [],
      "idx": 348
    },
    {
      "path": "src/dom/ButtonElement.js",
      "requires": [
        23
      ],
      "uses": [],
      "idx": 349
    },
    {
      "path": "src/button/Manager.js",
      "requires": [],
      "uses": [],
      "idx": 350
    },
    {
      "path": "src/menu/Manager.js",
      "requires": [
        35,
        316
      ],
      "uses": [
        12,
        569
      ],
      "idx": 351
    },
    {
      "path": "src/util/ClickRepeater.js",
      "requires": [
        31
      ],
      "uses": [],
      "idx": 352
    },
    {
      "path": "src/button/Button.js",
      "requires": [
        96,
        213,
        303,
        316,
        349,
        350,
        351,
        352
      ],
      "uses": [
        115,
        386
      ],
      "idx": 353
    },
    {
      "path": "src/rtl/button/Button.js",
      "requires": [],
      "uses": [],
      "idx": 354
    },
    {
      "path": "src/layout/container/boxOverflow/Menu.js",
      "requires": [
        346,
        348,
        353
      ],
      "uses": [
        147,
        323,
        345,
        365,
        378,
        569
      ],
      "idx": 355
    },
    {
      "path": "src/rtl/layout/container/boxOverflow/Menu.js",
      "requires": [],
      "uses": [],
      "idx": 356
    },
    {
      "path": "src/layout/container/boxOverflow/Scroller.js",
      "requires": [
        5,
        23,
        346,
        352
      ],
      "uses": [],
      "idx": 357
    },
    {
      "path": "src/rtl/layout/container/boxOverflow/Scroller.js",
      "requires": [],
      "uses": [],
      "idx": 358
    },
    {
      "path": "src/dd/DragDropManager.js",
      "requires": [
        113
      ],
      "uses": [
        386,
        429
      ],
      "idx": 359
    },
    {
      "path": "src/resizer/Splitter.js",
      "requires": [
        96,
        126
      ],
      "uses": [
        483
      ],
      "idx": 360
    },
    {
      "path": "src/layout/container/Box.js",
      "requires": [
        59,
        309,
        346,
        355,
        357,
        359,
        360
      ],
      "uses": [
        66,
        147,
        307,
        323
      ],
      "idx": 361
    },
    {
      "path": "src/rtl/layout/container/Box.js",
      "requires": [],
      "uses": [],
      "idx": 362
    },
    {
      "path": "src/layout/container/HBox.js",
      "requires": [
        361
      ],
      "uses": [],
      "idx": 363
    },
    {
      "path": "src/rtl/layout/container/HBox.js",
      "requires": [],
      "uses": [],
      "idx": 364
    },
    {
      "path": "src/layout/container/VBox.js",
      "requires": [
        361
      ],
      "uses": [],
      "idx": 365
    },
    {
      "path": "src/rtl/layout/container/VBox.js",
      "requires": [],
      "uses": [],
      "idx": 366
    },
    {
      "path": "src/util/FocusableContainer.js",
      "requires": [
        3,
        317
      ],
      "uses": [
        96
      ],
      "idx": 367
    },
    {
      "path": "src/rtl/util/FocusableContainer.js",
      "requires": [],
      "uses": [],
      "idx": 368
    },
    {
      "path": "src/toolbar/Toolbar.js",
      "requires": [
        147,
        312,
        323,
        345,
        363,
        365,
        367
      ],
      "uses": [
        348,
        518
      ],
      "idx": 369
    },
    {
      "path": "src/dd/DragDrop.js",
      "requires": [
        359
      ],
      "uses": [
        23
      ],
      "idx": 370
    },
    {
      "path": "src/dd/DD.js",
      "requires": [
        359,
        370
      ],
      "uses": [
        23
      ],
      "idx": 371
    },
    {
      "path": "src/rtl/dd/DD.js",
      "requires": [],
      "uses": [],
      "idx": 372
    },
    {
      "path": "src/dd/DDProxy.js",
      "requires": [
        371
      ],
      "uses": [
        359
      ],
      "idx": 373
    },
    {
      "path": "src/dd/StatusProxy.js",
      "requires": [
        96
      ],
      "uses": [],
      "idx": 374
    },
    {
      "path": "src/dd/DragSource.js",
      "requires": [
        359,
        373,
        374
      ],
      "uses": [
        147,
        323
      ],
      "idx": 375
    },
    {
      "path": "src/panel/Proxy.js",
      "requires": [],
      "uses": [
        23
      ],
      "idx": 376
    },
    {
      "path": "src/panel/DD.js",
      "requires": [
        375,
        376
      ],
      "uses": [],
      "idx": 377
    },
    {
      "path": "src/layout/component/Dock.js",
      "requires": [
        322
      ],
      "uses": [
        15,
        23,
        307
      ],
      "idx": 378
    },
    {
      "path": "src/util/Memento.js",
      "requires": [],
      "uses": [],
      "idx": 379
    },
    {
      "path": "src/container/DockingContainer.js",
      "requires": [
        23,
        35
      ],
      "uses": [
        15,
        32,
        218
      ],
      "idx": 380
    },
    {
      "path": "src/panel/Panel.js",
      "requires": [
        23,
        35,
        52,
        126,
        312,
        316,
        344,
        369,
        377,
        378,
        379,
        380
      ],
      "uses": [
        29,
        64,
        65,
        96,
        147,
        310,
        323,
        343,
        463
      ],
      "idx": 381
    },
    {
      "path": "src/rtl/panel/Panel.js",
      "requires": [],
      "uses": [],
      "idx": 382
    },
    {
      "path": "src/tip/Tip.js",
      "requires": [
        381
      ],
      "uses": [
        96
      ],
      "idx": 383
    },
    {
      "path": "src/tip/ToolTip.js",
      "requires": [
        383
      ],
      "uses": [
        23
      ],
      "idx": 384
    },
    {
      "path": "src/tip/QuickTip.js",
      "requires": [
        384
      ],
      "uses": [],
      "idx": 385
    },
    {
      "path": "src/tip/QuickTipManager.js",
      "requires": [
        385
      ],
      "uses": [],
      "idx": 386
    },
    {
      "path": "src/rtl/tip/QuickTipManager.js",
      "requires": [],
      "uses": [],
      "idx": 387
    },
    {
      "path": "src/app/Application.js",
      "requires": [
        35,
        336,
        338,
        386,
        390
      ],
      "uses": [
        337
      ],
      "idx": 388
    },
    {
      "path": "src/rtl/layout/component/Dock.js",
      "requires": [],
      "uses": [
        378
      ],
      "idx": 389
    },
    {
      "path": "overrides/app/Application.js",
      "requires": [],
      "uses": [
        388
      ],
      "idx": 390
    },
    {
      "path": "src/app/domain/View.js",
      "requires": [
        327
      ],
      "uses": [
        96
      ],
      "idx": 391
    },
    {
      "path": "src/app/ViewController.js",
      "requires": [
        66,
        331,
        391
      ],
      "uses": [],
      "idx": 392
    },
    {
      "path": "src/form/Labelable.js",
      "requires": [
        3,
        55,
        126
      ],
      "uses": [
        23,
        385
      ],
      "idx": 393
    },
    {
      "path": "src/rtl/form/Labelable.js",
      "requires": [],
      "uses": [],
      "idx": 394
    },
    {
      "path": "src/form/field/Field.js",
      "requires": [],
      "uses": [],
      "idx": 395
    },
    {
      "path": "src/form/field/Base.js",
      "requires": [
        29,
        96,
        126,
        393,
        395
      ],
      "uses": [
        218
      ],
      "idx": 396
    },
    {
      "path": "src/form/field/Display.js",
      "requires": [
        59,
        126,
        396
      ],
      "uses": [],
      "idx": 397
    },
    {
      "path": "src/layout/container/Fit.js",
      "requires": [
        309
      ],
      "uses": [],
      "idx": 398
    },
    {
      "path": "src/panel/Table.js",
      "requires": [
        381,
        398
      ],
      "uses": [
        29,
        212,
        218,
        406,
        418,
        436,
        442,
        581,
        582,
        616,
        622
      ],
      "idx": 399
    },
    {
      "path": "src/selection/Model.js",
      "requires": [
        31,
        66,
        320
      ],
      "uses": [
        129
      ],
      "idx": 400
    },
    {
      "path": "src/selection/DataViewModel.js",
      "requires": [
        317,
        400
      ],
      "uses": [],
      "idx": 401
    },
    {
      "path": "src/view/NavigationModel.js",
      "requires": [
        31,
        66
      ],
      "uses": [
        317
      ],
      "idx": 402
    },
    {
      "path": "src/rtl/view/NavigationModel.js",
      "requires": [],
      "uses": [],
      "idx": 403
    },
    {
      "path": "src/view/AbstractView.js",
      "requires": [
        26,
        96,
        320,
        321,
        401,
        402
      ],
      "uses": [
        11,
        60,
        66,
        126,
        212,
        218,
        302
      ],
      "idx": 404
    },
    {
      "path": "src/view/View.js",
      "requires": [
        404
      ],
      "uses": [],
      "idx": 405
    },
    {
      "path": "src/grid/CellContext.js",
      "requires": [],
      "uses": [],
      "idx": 406
    },
    {
      "path": "src/view/TableLayout.js",
      "requires": [
        323
      ],
      "uses": [],
      "idx": 407
    },
    {
      "path": "src/grid/locking/RowSynchronizer.js",
      "requires": [],
      "uses": [],
      "idx": 408
    },
    {
      "path": "src/view/NodeCache.js",
      "requires": [
        26
      ],
      "uses": [
        23,
        25
      ],
      "idx": 409
    },
    {
      "path": "src/view/Table.js",
      "requires": [
        29,
        35,
        405,
        406,
        407,
        408,
        409
      ],
      "uses": [
        25,
        96,
        126,
        436
      ],
      "idx": 410
    },
    {
      "path": "src/rtl/view/Table.js",
      "requires": [],
      "uses": [],
      "idx": 411
    },
    {
      "path": "src/grid/Panel.js",
      "requires": [
        399,
        410
      ],
      "uses": [],
      "idx": 412
    },
    {
      "path": "src/form/CheckboxManager.js",
      "requires": [
        35
      ],
      "uses": [],
      "idx": 413
    },
    {
      "path": "src/form/field/Checkbox.js",
      "requires": [
        126,
        396,
        413
      ],
      "uses": [],
      "idx": 414
    },
    {
      "path": "src/app/bindinspector/Util.js",
      "requires": [],
      "uses": [
        60
      ],
      "idx": 415
    },
    {
      "path": "src/app/bindinspector/ComponentDetail.js",
      "requires": [
        96,
        147,
        312,
        323,
        363,
        365,
        381,
        397,
        412,
        414,
        415
      ],
      "uses": [
        60,
        345,
        353,
        369,
        378,
        398,
        460
      ],
      "idx": 416
    },
    {
      "path": "src/tree/View.js",
      "requires": [
        410
      ],
      "uses": [
        126
      ],
      "idx": 417
    },
    {
      "path": "src/selection/RowModel.js",
      "requires": [
        401,
        406
      ],
      "uses": [],
      "idx": 418
    },
    {
      "path": "src/selection/TreeModel.js",
      "requires": [
        418
      ],
      "uses": [],
      "idx": 419
    },
    {
      "path": "src/rtl/selection/TreeModel.js",
      "requires": [],
      "uses": [],
      "idx": 420
    },
    {
      "path": "src/grid/ColumnLayout.js",
      "requires": [
        363,
        399
      ],
      "uses": [],
      "idx": 421
    },
    {
      "path": "src/rtl/grid/ColumnLayout.js",
      "requires": [],
      "uses": [],
      "idx": 422
    },
    {
      "path": "src/plugin/Abstract.js",
      "requires": [],
      "uses": [],
      "idx": 423
    },
    {
      "path": "src/dd/DragTracker.js",
      "requires": [
        31
      ],
      "uses": [
        113
      ],
      "idx": 424
    },
    {
      "path": "src/grid/plugin/HeaderResizer.js",
      "requires": [
        113,
        423,
        424
      ],
      "uses": [
        438
      ],
      "idx": 425
    },
    {
      "path": "src/rtl/grid/plugin/HeaderResizer.js",
      "requires": [],
      "uses": [],
      "idx": 426
    },
    {
      "path": "src/dd/DragZone.js",
      "requires": [
        375
      ],
      "uses": [
        430,
        432
      ],
      "idx": 427
    },
    {
      "path": "src/grid/header/DragZone.js",
      "requires": [
        427
      ],
      "uses": [],
      "idx": 428
    },
    {
      "path": "src/dd/DDTarget.js",
      "requires": [
        370
      ],
      "uses": [],
      "idx": 429
    },
    {
      "path": "src/dd/ScrollManager.js",
      "requires": [
        359
      ],
      "uses": [],
      "idx": 430
    },
    {
      "path": "src/dd/DropTarget.js",
      "requires": [
        429,
        430
      ],
      "uses": [],
      "idx": 431
    },
    {
      "path": "src/dd/Registry.js",
      "requires": [],
      "uses": [],
      "idx": 432
    },
    {
      "path": "src/dd/DropZone.js",
      "requires": [
        431,
        432
      ],
      "uses": [
        359
      ],
      "idx": 433
    },
    {
      "path": "src/grid/header/DropZone.js",
      "requires": [
        433
      ],
      "uses": [
        359
      ],
      "idx": 434
    },
    {
      "path": "src/grid/plugin/HeaderReorderer.js",
      "requires": [
        423,
        428,
        434
      ],
      "uses": [],
      "idx": 435
    },
    {
      "path": "src/grid/header/Container.js",
      "requires": [
        312,
        317,
        367,
        421,
        425,
        435
      ],
      "uses": [
        29,
        147,
        323,
        365,
        378,
        438,
        543,
        566,
        568,
        569
      ],
      "idx": 436
    },
    {
      "path": "src/grid/ColumnComponentLayout.js",
      "requires": [
        323
      ],
      "uses": [],
      "idx": 437
    },
    {
      "path": "src/grid/column/Column.js",
      "requires": [
        157,
        421,
        436,
        437
      ],
      "uses": [
        59,
        425
      ],
      "idx": 438
    },
    {
      "path": "src/rtl/grid/column/Column.js",
      "requires": [],
      "uses": [],
      "idx": 439
    },
    {
      "path": "src/tree/Column.js",
      "requires": [
        438
      ],
      "uses": [],
      "idx": 440
    },
    {
      "path": "src/rtl/tree/Column.js",
      "requires": [],
      "uses": [],
      "idx": 441
    },
    {
      "path": "src/grid/NavigationModel.js",
      "requires": [
        402
      ],
      "uses": [
        25,
        115,
        317,
        406
      ],
      "idx": 442
    },
    {
      "path": "src/rtl/grid/NavigationModel.js",
      "requires": [],
      "uses": [],
      "idx": 443
    },
    {
      "path": "src/tree/NavigationModel.js",
      "requires": [
        442
      ],
      "uses": [
        115
      ],
      "idx": 444
    },
    {
      "path": "src/tree/Panel.js",
      "requires": [
        215,
        399,
        417,
        419,
        440,
        444
      ],
      "uses": [
        147,
        212,
        310,
        437
      ],
      "idx": 445
    },
    {
      "path": "src/form/field/VTypes.js",
      "requires": [],
      "uses": [],
      "idx": 446
    },
    {
      "path": "src/form/trigger/Trigger.js",
      "requires": [
        66,
        352
      ],
      "uses": [
        23,
        126
      ],
      "idx": 447
    },
    {
      "path": "src/form/field/Text.js",
      "requires": [
        303,
        396,
        446,
        447
      ],
      "uses": [
        29,
        59,
        60,
        65
      ],
      "idx": 448
    },
    {
      "path": "src/app/bindinspector/ComponentList.js",
      "requires": [
        445,
        448
      ],
      "uses": [
        15,
        147,
        310,
        323,
        345,
        353,
        369,
        378,
        384,
        415,
        437,
        440
      ],
      "idx": 449
    },
    {
      "path": "src/resizer/BorderSplitter.js",
      "requires": [
        360
      ],
      "uses": [
        610
      ],
      "idx": 450
    },
    {
      "path": "src/layout/container/Border.js",
      "requires": [
        52,
        97,
        309,
        450
      ],
      "uses": [
        59,
        147,
        323
      ],
      "idx": 451
    },
    {
      "path": "src/rtl/layout/container/Border.js",
      "requires": [],
      "uses": [],
      "idx": 452
    },
    {
      "path": "src/layout/container/Card.js",
      "requires": [
        398
      ],
      "uses": [],
      "idx": 453
    },
    {
      "path": "src/tab/Tab.js",
      "requires": [
        317,
        353
      ],
      "uses": [],
      "idx": 454
    },
    {
      "path": "src/layout/component/Body.js",
      "requires": [
        323
      ],
      "uses": [],
      "idx": 455
    },
    {
      "path": "src/tab/Bar.js",
      "requires": [
        114,
        339,
        367,
        454,
        455
      ],
      "uses": [
        113
      ],
      "idx": 456
    },
    {
      "path": "src/rtl/tab/Bar.js",
      "requires": [],
      "uses": [],
      "idx": 457
    },
    {
      "path": "src/tab/Panel.js",
      "requires": [
        381,
        453,
        456
      ],
      "uses": [
        147,
        323,
        454
      ],
      "idx": 458
    },
    {
      "path": "src/app/bindinspector/Environment.js",
      "requires": [
        129
      ],
      "uses": [
        12,
        498
      ],
      "idx": 459
    },
    {
      "path": "src/app/bindinspector/ViewModelDetail.js",
      "requires": [
        445
      ],
      "uses": [
        60,
        147,
        310,
        415,
        437,
        440
      ],
      "idx": 460
    },
    {
      "path": "src/app/bindinspector/noconflict/BaseModel.js",
      "requires": [
        180
      ],
      "uses": [],
      "idx": 461
    },
    {
      "path": "src/app/bindinspector/Container.js",
      "requires": [
        96,
        147,
        312,
        323,
        363,
        415,
        416,
        449,
        451,
        458,
        459,
        460,
        461
      ],
      "uses": [
        139,
        310,
        378,
        381,
        398
      ],
      "idx": 462
    },
    {
      "path": "src/util/ComponentDragger.js",
      "requires": [
        424
      ],
      "uses": [
        23,
        113
      ],
      "idx": 463
    },
    {
      "path": "src/window/Window.js",
      "requires": [
        113,
        381,
        463
      ],
      "uses": [],
      "idx": 464
    },
    {
      "path": "src/app/bindinspector/Inspector.js",
      "requires": [
        386,
        398,
        462,
        464
      ],
      "uses": [
        147,
        323,
        451,
        459
      ],
      "idx": 465
    },
    {
      "path": "src/app/domain/Controller.js",
      "requires": [
        327,
        338
      ],
      "uses": [
        331
      ],
      "idx": 466
    },
    {
      "path": "src/app/domain/Direct.js",
      "requires": [
        243,
        327
      ],
      "uses": [],
      "idx": 467
    },
    {
      "path": "src/button/Split.js",
      "requires": [
        353
      ],
      "uses": [],
      "idx": 468
    },
    {
      "path": "src/button/Cycle.js",
      "requires": [
        468
      ],
      "uses": [],
      "idx": 469
    },
    {
      "path": "src/button/Segmented.js",
      "requires": [
        312,
        353
      ],
      "uses": [],
      "idx": 470
    },
    {
      "path": "src/rtl/button/Segmented.js",
      "requires": [],
      "uses": [],
      "idx": 471
    },
    {
      "path": "src/layout/container/Table.js",
      "requires": [
        309
      ],
      "uses": [],
      "idx": 472
    },
    {
      "path": "src/container/ButtonGroup.js",
      "requires": [
        381,
        472
      ],
      "uses": [],
      "idx": 473
    },
    {
      "path": "src/container/Monitor.js",
      "requires": [],
      "uses": [
        35
      ],
      "idx": 474
    },
    {
      "path": "src/plugin/Responsive.js",
      "requires": [
        295
      ],
      "uses": [],
      "idx": 475
    },
    {
      "path": "src/plugin/Viewport.js",
      "requires": [
        475
      ],
      "uses": [
        23
      ],
      "idx": 476
    },
    {
      "path": "src/container/Viewport.js",
      "requires": [
        295,
        312,
        476
      ],
      "uses": [],
      "idx": 477
    },
    {
      "path": "src/layout/container/Anchor.js",
      "requires": [
        310
      ],
      "uses": [],
      "idx": 478
    },
    {
      "path": "src/dashboard/Panel.js",
      "requires": [
        381
      ],
      "uses": [
        12
      ],
      "idx": 479
    },
    {
      "path": "src/dashboard/Column.js",
      "requires": [
        312,
        478,
        479
      ],
      "uses": [],
      "idx": 480
    },
    {
      "path": "src/layout/container/Column.js",
      "requires": [
        310
      ],
      "uses": [],
      "idx": 481
    },
    {
      "path": "src/rtl/layout/container/Column.js",
      "requires": [],
      "uses": [],
      "idx": 482
    },
    {
      "path": "src/resizer/SplitterTracker.js",
      "requires": [
        113,
        424
      ],
      "uses": [
        23
      ],
      "idx": 483
    },
    {
      "path": "src/rtl/resizer/SplitterTracker.js",
      "requires": [],
      "uses": [],
      "idx": 484
    },
    {
      "path": "src/layout/container/ColumnSplitterTracker.js",
      "requires": [
        483
      ],
      "uses": [],
      "idx": 485
    },
    {
      "path": "src/layout/container/ColumnSplitter.js",
      "requires": [
        360,
        485
      ],
      "uses": [],
      "idx": 486
    },
    {
      "path": "src/layout/container/Dashboard.js",
      "requires": [
        481,
        486
      ],
      "uses": [
        147,
        323
      ],
      "idx": 487
    },
    {
      "path": "src/dashboard/DropZone.js",
      "requires": [
        431
      ],
      "uses": [],
      "idx": 488
    },
    {
      "path": "src/dashboard/Part.js",
      "requires": [
        4,
        66,
        131
      ],
      "uses": [],
      "idx": 489
    },
    {
      "path": "src/dashboard/Dashboard.js",
      "requires": [
        381,
        480,
        487,
        488,
        489
      ],
      "uses": [
        66,
        92,
        129
      ],
      "idx": 490
    },
    {
      "path": "src/dom/Layer.js",
      "requires": [
        23
      ],
      "uses": [
        218
      ],
      "idx": 491
    },
    {
      "path": "src/enums.js",
      "requires": [],
      "uses": [],
      "idx": 492
    },
    {
      "path": "src/flash/Component.js",
      "requires": [
        96
      ],
      "uses": [],
      "idx": 493
    },
    {
      "path": "src/form/action/Action.js",
      "requires": [],
      "uses": [],
      "idx": 494
    },
    {
      "path": "src/form/action/Load.js",
      "requires": [
        9,
        494
      ],
      "uses": [
        10
      ],
      "idx": 495
    },
    {
      "path": "src/form/action/Submit.js",
      "requires": [
        494
      ],
      "uses": [
        10,
        218
      ],
      "idx": 496
    },
    {
      "path": "src/form/field/TextArea.js",
      "requires": [
        29,
        126,
        448
      ],
      "uses": [
        59,
        303
      ],
      "idx": 497
    },
    {
      "path": "src/window/MessageBox.js",
      "requires": [
        325,
        353,
        363,
        369,
        448,
        464,
        478,
        497
      ],
      "uses": [
        96,
        147,
        312,
        323,
        324
      ],
      "idx": 498
    },
    {
      "path": "src/form/Basic.js",
      "requires": [
        29,
        31,
        35,
        164,
        495,
        496,
        498
      ],
      "uses": [
        474
      ],
      "idx": 499
    },
    {
      "path": "src/form/FieldAncestor.js",
      "requires": [
        3,
        474
      ],
      "uses": [],
      "idx": 500
    },
    {
      "path": "src/layout/component/field/FieldContainer.js",
      "requires": [
        323
      ],
      "uses": [],
      "idx": 501
    },
    {
      "path": "src/form/FieldContainer.js",
      "requires": [
        312,
        393,
        500,
        501
      ],
      "uses": [],
      "idx": 502
    },
    {
      "path": "src/layout/container/CheckboxGroup.js",
      "requires": [
        309
      ],
      "uses": [
        218
      ],
      "idx": 503
    },
    {
      "path": "src/form/CheckboxGroup.js",
      "requires": [
        395,
        396,
        414,
        502,
        503
      ],
      "uses": [],
      "idx": 504
    },
    {
      "path": "src/form/FieldSet.js",
      "requires": [
        312,
        500
      ],
      "uses": [
        23,
        64,
        96,
        147,
        218,
        309,
        323,
        343,
        414,
        478,
        599
      ],
      "idx": 505
    },
    {
      "path": "src/form/Label.js",
      "requires": [
        59,
        96
      ],
      "uses": [],
      "idx": 506
    },
    {
      "path": "src/form/Panel.js",
      "requires": [
        36,
        381,
        499,
        500
      ],
      "uses": [],
      "idx": 507
    },
    {
      "path": "src/form/RadioManager.js",
      "requires": [
        35
      ],
      "uses": [],
      "idx": 508
    },
    {
      "path": "src/form/field/Radio.js",
      "requires": [
        414,
        508
      ],
      "uses": [],
      "idx": 509
    },
    {
      "path": "src/form/RadioGroup.js",
      "requires": [
        367,
        504,
        509
      ],
      "uses": [
        508
      ],
      "idx": 510
    },
    {
      "path": "src/form/action/DirectAction.js",
      "requires": [
        3
      ],
      "uses": [
        201
      ],
      "idx": 511
    },
    {
      "path": "src/form/action/DirectLoad.js",
      "requires": [
        201,
        495,
        511
      ],
      "uses": [],
      "idx": 512
    },
    {
      "path": "src/form/action/DirectSubmit.js",
      "requires": [
        201,
        496,
        511
      ],
      "uses": [],
      "idx": 513
    },
    {
      "path": "src/form/action/StandardSubmit.js",
      "requires": [
        496
      ],
      "uses": [],
      "idx": 514
    },
    {
      "path": "src/form/field/Picker.js",
      "requires": [
        317,
        448
      ],
      "uses": [],
      "idx": 515
    },
    {
      "path": "src/view/BoundListKeyNav.js",
      "requires": [
        402
      ],
      "uses": [
        317
      ],
      "idx": 516
    },
    {
      "path": "src/layout/component/BoundList.js",
      "requires": [
        323
      ],
      "uses": [],
      "idx": 517
    },
    {
      "path": "src/toolbar/TextItem.js",
      "requires": [
        126,
        347
      ],
      "uses": [],
      "idx": 518
    },
    {
      "path": "src/form/trigger/Spinner.js",
      "requires": [
        447
      ],
      "uses": [],
      "idx": 519
    },
    {
      "path": "src/form/field/Spinner.js",
      "requires": [
        317,
        448,
        519
      ],
      "uses": [],
      "idx": 520
    },
    {
      "path": "src/form/field/Number.js",
      "requires": [
        520
      ],
      "uses": [
        59,
        60
      ],
      "idx": 521
    },
    {
      "path": "src/toolbar/Paging.js",
      "requires": [
        320,
        369,
        518,
        521
      ],
      "uses": [
        60,
        147,
        323,
        519
      ],
      "idx": 522
    },
    {
      "path": "src/view/BoundList.js",
      "requires": [
        23,
        213,
        405,
        516,
        517,
        522
      ],
      "uses": [
        126,
        147,
        323
      ],
      "idx": 523
    },
    {
      "path": "src/form/field/ComboBox.js",
      "requires": [
        29,
        212,
        320,
        515,
        523
      ],
      "uses": [
        23,
        28,
        115,
        126,
        129,
        147,
        180,
        194,
        218,
        401,
        516,
        517
      ],
      "idx": 524
    },
    {
      "path": "src/picker/Month.js",
      "requires": [
        96,
        126,
        352,
        353
      ],
      "uses": [
        147,
        323
      ],
      "idx": 525
    },
    {
      "path": "src/picker/Date.js",
      "requires": [
        46,
        96,
        126,
        317,
        352,
        353,
        468,
        525
      ],
      "uses": [
        60,
        147,
        218,
        323
      ],
      "idx": 526
    },
    {
      "path": "src/form/field/Date.js",
      "requires": [
        515,
        526
      ],
      "uses": [
        60,
        147,
        323
      ],
      "idx": 527
    },
    {
      "path": "src/form/field/FileButton.js",
      "requires": [
        353
      ],
      "uses": [],
      "idx": 528
    },
    {
      "path": "src/rtl/form/field/FileButton.js",
      "requires": [],
      "uses": [],
      "idx": 529
    },
    {
      "path": "src/form/trigger/Component.js",
      "requires": [
        447
      ],
      "uses": [],
      "idx": 530
    },
    {
      "path": "src/form/field/File.js",
      "requires": [
        448,
        528,
        530
      ],
      "uses": [
        147,
        323
      ],
      "idx": 531
    },
    {
      "path": "src/rtl/form/field/File.js",
      "requires": [],
      "uses": [],
      "idx": 532
    },
    {
      "path": "src/form/field/Hidden.js",
      "requires": [
        396
      ],
      "uses": [],
      "idx": 533
    },
    {
      "path": "src/picker/Color.js",
      "requires": [
        96,
        126
      ],
      "uses": [],
      "idx": 534
    },
    {
      "path": "src/layout/component/field/HtmlEditor.js",
      "requires": [
        501
      ],
      "uses": [],
      "idx": 535
    },
    {
      "path": "src/form/field/HtmlEditor.js",
      "requires": [
        59,
        302,
        347,
        365,
        369,
        386,
        395,
        502,
        534,
        535
      ],
      "uses": [
        29,
        60,
        96,
        147,
        218,
        323,
        378,
        569
      ],
      "idx": 536
    },
    {
      "path": "src/form/field/Tag.js",
      "requires": [
        161,
        196,
        400,
        524
      ],
      "uses": [
        24,
        28,
        126
      ],
      "idx": 537
    },
    {
      "path": "src/picker/Time.js",
      "requires": [
        196,
        523
      ],
      "uses": [
        28
      ],
      "idx": 538
    },
    {
      "path": "src/form/field/Time.js",
      "requires": [
        516,
        524,
        527,
        538
      ],
      "uses": [
        60,
        126,
        147,
        401,
        517
      ],
      "idx": 539
    },
    {
      "path": "src/form/field/Trigger.js",
      "requires": [
        218,
        352,
        448
      ],
      "uses": [],
      "idx": 540
    },
    {
      "path": "src/grid/CellEditor.js",
      "requires": [
        314
      ],
      "uses": [],
      "idx": 541
    },
    {
      "path": "src/rtl/grid/CellEditor.js",
      "requires": [],
      "uses": [],
      "idx": 542
    },
    {
      "path": "src/grid/ColumnManager.js",
      "requires": [],
      "uses": [],
      "idx": 543
    },
    {
      "path": "src/grid/RowEditorButtons.js",
      "requires": [
        312
      ],
      "uses": [
        147,
        323,
        353,
        381
      ],
      "idx": 544
    },
    {
      "path": "src/grid/RowEditor.js",
      "requires": [
        317,
        384,
        507,
        544
      ],
      "uses": [
        23,
        24,
        147,
        310,
        312,
        323,
        378,
        397
      ],
      "idx": 545
    },
    {
      "path": "src/grid/Scroller.js",
      "requires": [],
      "uses": [],
      "idx": 546
    },
    {
      "path": "src/view/DropZone.js",
      "requires": [
        433
      ],
      "uses": [
        96,
        147,
        323
      ],
      "idx": 547
    },
    {
      "path": "src/grid/ViewDropZone.js",
      "requires": [
        547
      ],
      "uses": [],
      "idx": 548
    },
    {
      "path": "src/grid/column/Action.js",
      "requires": [
        438
      ],
      "uses": [],
      "idx": 549
    },
    {
      "path": "src/grid/column/Boolean.js",
      "requires": [
        438
      ],
      "uses": [],
      "idx": 550
    },
    {
      "path": "src/grid/column/Check.js",
      "requires": [
        438
      ],
      "uses": [],
      "idx": 551
    },
    {
      "path": "src/grid/column/Date.js",
      "requires": [
        438
      ],
      "uses": [
        59
      ],
      "idx": 552
    },
    {
      "path": "src/grid/column/Number.js",
      "requires": [
        59,
        438
      ],
      "uses": [],
      "idx": 553
    },
    {
      "path": "src/grid/column/RowNumberer.js",
      "requires": [
        438
      ],
      "uses": [],
      "idx": 554
    },
    {
      "path": "src/grid/column/Template.js",
      "requires": [
        126,
        438
      ],
      "uses": [
        551
      ],
      "idx": 555
    },
    {
      "path": "src/grid/column/Widget.js",
      "requires": [
        438
      ],
      "uses": [],
      "idx": 556
    },
    {
      "path": "src/grid/feature/Feature.js",
      "requires": [
        31
      ],
      "uses": [],
      "idx": 557
    },
    {
      "path": "src/grid/feature/AbstractSummary.js",
      "requires": [
        557
      ],
      "uses": [
        180
      ],
      "idx": 558
    },
    {
      "path": "src/grid/feature/GroupStore.js",
      "requires": [
        31
      ],
      "uses": [
        129
      ],
      "idx": 559
    },
    {
      "path": "src/grid/feature/Grouping.js",
      "requires": [
        557,
        558,
        559
      ],
      "uses": [
        126,
        436
      ],
      "idx": 560
    },
    {
      "path": "src/grid/feature/GroupingSummary.js",
      "requires": [
        560
      ],
      "uses": [],
      "idx": 561
    },
    {
      "path": "src/grid/feature/RowBody.js",
      "requires": [
        557
      ],
      "uses": [
        126
      ],
      "idx": 562
    },
    {
      "path": "src/grid/feature/Summary.js",
      "requires": [
        558
      ],
      "uses": [
        96,
        147,
        180,
        323
      ],
      "idx": 563
    },
    {
      "path": "src/rtl/grid/feature/Summary.js",
      "requires": [],
      "uses": [],
      "idx": 564
    },
    {
      "path": "src/menu/Item.js",
      "requires": [
        96,
        213
      ],
      "uses": [
        23,
        351,
        386
      ],
      "idx": 565
    },
    {
      "path": "src/menu/CheckItem.js",
      "requires": [
        565
      ],
      "uses": [
        351
      ],
      "idx": 566
    },
    {
      "path": "src/menu/KeyNav.js",
      "requires": [
        317
      ],
      "uses": [
        351
      ],
      "idx": 567
    },
    {
      "path": "src/menu/Separator.js",
      "requires": [
        565
      ],
      "uses": [],
      "idx": 568
    },
    {
      "path": "src/menu/Menu.js",
      "requires": [
        351,
        365,
        381,
        565,
        566,
        567,
        568
      ],
      "uses": [
        12,
        23,
        24,
        147,
        323
      ],
      "idx": 569
    },
    {
      "path": "src/grid/filters/filter/Base.js",
      "requires": [
        66,
        147,
        365,
        378,
        569
      ],
      "uses": [
        28,
        29
      ],
      "idx": 570
    },
    {
      "path": "src/grid/filters/filter/SingleFilter.js",
      "requires": [
        570
      ],
      "uses": [],
      "idx": 571
    },
    {
      "path": "src/grid/filters/filter/Boolean.js",
      "requires": [
        571
      ],
      "uses": [],
      "idx": 572
    },
    {
      "path": "src/grid/filters/filter/TriFilter.js",
      "requires": [
        570
      ],
      "uses": [],
      "idx": 573
    },
    {
      "path": "src/grid/filters/filter/Date.js",
      "requires": [
        147,
        323,
        566,
        573
      ],
      "uses": [
        526,
        569
      ],
      "idx": 574
    },
    {
      "path": "src/grid/filters/filter/List.js",
      "requires": [
        571
      ],
      "uses": [
        186,
        191,
        197,
        198
      ],
      "idx": 575
    },
    {
      "path": "src/grid/filters/filter/Number.js",
      "requires": [
        147,
        323,
        519,
        573
      ],
      "uses": [
        521
      ],
      "idx": 576
    },
    {
      "path": "src/grid/filters/filter/String.js",
      "requires": [
        147,
        323,
        448,
        571
      ],
      "uses": [],
      "idx": 577
    },
    {
      "path": "src/grid/filters/Filters.js",
      "requires": [
        320,
        423,
        570,
        571,
        572,
        573,
        574,
        575,
        576,
        577
      ],
      "uses": [
        66
      ],
      "idx": 578
    },
    {
      "path": "src/grid/locking/HeaderContainer.js",
      "requires": [
        436,
        543
      ],
      "uses": [],
      "idx": 579
    },
    {
      "path": "src/grid/locking/View.js",
      "requires": [
        31,
        95,
        96,
        320,
        404
      ],
      "uses": [
        12,
        321,
        410,
        442
      ],
      "idx": 580
    },
    {
      "path": "src/grid/locking/Lockable.js",
      "requires": [
        96,
        410,
        436,
        579,
        580
      ],
      "uses": [
        29,
        147,
        212,
        310,
        323,
        360,
        361
      ],
      "idx": 581
    },
    {
      "path": "src/grid/plugin/BufferedRenderer.js",
      "requires": [
        423
      ],
      "uses": [
        29
      ],
      "idx": 582
    },
    {
      "path": "src/grid/plugin/Editing.js",
      "requires": [
        31,
        317,
        396,
        410,
        423,
        438
      ],
      "uses": [
        12,
        147,
        323,
        406
      ],
      "idx": 583
    },
    {
      "path": "src/grid/plugin/CellEditing.js",
      "requires": [
        29,
        541,
        583
      ],
      "uses": [
        35,
        147,
        313,
        323
      ],
      "idx": 584
    },
    {
      "path": "src/grid/plugin/DragDrop.js",
      "requires": [
        423
      ],
      "uses": [
        548,
        643
      ],
      "idx": 585
    },
    {
      "path": "src/grid/plugin/RowEditing.js",
      "requires": [
        545,
        583
      ],
      "uses": [],
      "idx": 586
    },
    {
      "path": "src/rtl/grid/plugin/RowEditing.js",
      "requires": [],
      "uses": [],
      "idx": 587
    },
    {
      "path": "src/grid/plugin/RowExpander.js",
      "requires": [
        423,
        562
      ],
      "uses": [
        126,
        438
      ],
      "idx": 588
    },
    {
      "path": "src/grid/property/Grid.js",
      "requires": [
        412
      ],
      "uses": [
        12,
        126,
        147,
        180,
        313,
        323,
        396,
        410,
        448,
        519,
        521,
        524,
        527,
        541,
        584,
        590,
        593
      ],
      "idx": 589
    },
    {
      "path": "src/grid/property/HeaderContainer.js",
      "requires": [
        59,
        436
      ],
      "uses": [],
      "idx": 590
    },
    {
      "path": "src/grid/property/Property.js",
      "requires": [
        180
      ],
      "uses": [],
      "idx": 591
    },
    {
      "path": "src/grid/property/Reader.js",
      "requires": [
        182
      ],
      "uses": [
        181
      ],
      "idx": 592
    },
    {
      "path": "src/grid/property/Store.js",
      "requires": [
        186,
        196,
        591,
        592
      ],
      "uses": [
        191
      ],
      "idx": 593
    },
    {
      "path": "src/util/Queue.js",
      "requires": [],
      "uses": [],
      "idx": 594
    },
    {
      "path": "src/layout/ContextItem.js",
      "requires": [],
      "uses": [
        35,
        46,
        52,
        307
      ],
      "idx": 595
    },
    {
      "path": "src/rtl/layout/ContextItem.js",
      "requires": [],
      "uses": [],
      "idx": 596
    },
    {
      "path": "src/layout/Context.js",
      "requires": [
        46,
        52,
        299,
        308,
        594,
        595
      ],
      "uses": [],
      "idx": 597
    },
    {
      "path": "src/layout/SizePolicy.js",
      "requires": [],
      "uses": [],
      "idx": 598
    },
    {
      "path": "src/layout/component/FieldSet.js",
      "requires": [
        455
      ],
      "uses": [],
      "idx": 599
    },
    {
      "path": "src/layout/container/Absolute.js",
      "requires": [
        478
      ],
      "uses": [],
      "idx": 600
    },
    {
      "path": "src/rtl/layout/container/Absolute.js",
      "requires": [],
      "uses": [],
      "idx": 601
    },
    {
      "path": "src/layout/container/Accordion.js",
      "requires": [
        365
      ],
      "uses": [],
      "idx": 602
    },
    {
      "path": "src/layout/container/Center.js",
      "requires": [
        398
      ],
      "uses": [],
      "idx": 603
    },
    {
      "path": "src/layout/container/Form.js",
      "requires": [
        310
      ],
      "uses": [],
      "idx": 604
    },
    {
      "path": "src/layout/container/SegmentedButton.js",
      "requires": [
        309
      ],
      "uses": [],
      "idx": 605
    },
    {
      "path": "src/menu/ColorPicker.js",
      "requires": [
        534,
        569
      ],
      "uses": [
        147,
        323,
        351
      ],
      "idx": 606
    },
    {
      "path": "src/menu/DatePicker.js",
      "requires": [
        526,
        569
      ],
      "uses": [
        147,
        323,
        351
      ],
      "idx": 607
    },
    {
      "path": "src/panel/Pinnable.js",
      "requires": [
        3
      ],
      "uses": [
        147,
        323,
        343
      ],
      "idx": 608
    },
    {
      "path": "src/plugin/Manager.js",
      "requires": [],
      "uses": [],
      "idx": 609
    },
    {
      "path": "src/resizer/BorderSplitterTracker.js",
      "requires": [
        113,
        483
      ],
      "uses": [],
      "idx": 610
    },
    {
      "path": "src/rtl/resizer/BorderSplitterTracker.js",
      "requires": [],
      "uses": [],
      "idx": 611
    },
    {
      "path": "src/resizer/Handle.js",
      "requires": [
        96
      ],
      "uses": [],
      "idx": 612
    },
    {
      "path": "src/resizer/ResizeTracker.js",
      "requires": [
        424
      ],
      "uses": [],
      "idx": 613
    },
    {
      "path": "src/rtl/resizer/ResizeTracker.js",
      "requires": [],
      "uses": [],
      "idx": 614
    },
    {
      "path": "src/resizer/Resizer.js",
      "requires": [
        31
      ],
      "uses": [
        23,
        60,
        96,
        218,
        613
      ],
      "idx": 615
    },
    {
      "path": "src/selection/CellModel.js",
      "requires": [
        401,
        406
      ],
      "uses": [],
      "idx": 616
    },
    {
      "path": "src/rtl/selection/CellModel.js",
      "requires": [],
      "uses": [],
      "idx": 617
    },
    {
      "path": "src/slider/Thumb.js",
      "requires": [
        59,
        424
      ],
      "uses": [
        52
      ],
      "idx": 618
    },
    {
      "path": "src/slider/Tip.js",
      "requires": [
        383
      ],
      "uses": [],
      "idx": 619
    },
    {
      "path": "src/slider/Multi.js",
      "requires": [
        59,
        60,
        396,
        618,
        619
      ],
      "uses": [
        218
      ],
      "idx": 620
    },
    {
      "path": "src/rtl/slider/Multi.js",
      "requires": [],
      "uses": [],
      "idx": 621
    },
    {
      "path": "src/selection/CheckboxModel.js",
      "requires": [
        418
      ],
      "uses": [],
      "idx": 622
    },
    {
      "path": "src/slider/Single.js",
      "requires": [
        620
      ],
      "uses": [],
      "idx": 623
    },
    {
      "path": "src/slider/Widget.js",
      "requires": [
        63,
        620
      ],
      "uses": [
        52,
        59
      ],
      "idx": 624
    },
    {
      "path": "src/sparkline/Shape.js",
      "requires": [],
      "uses": [],
      "idx": 625
    },
    {
      "path": "src/sparkline/CanvasBase.js",
      "requires": [
        625
      ],
      "uses": [],
      "idx": 626
    },
    {
      "path": "src/sparkline/CanvasCanvas.js",
      "requires": [
        626
      ],
      "uses": [],
      "idx": 627
    },
    {
      "path": "src/sparkline/VmlCanvas.js",
      "requires": [
        626
      ],
      "uses": [],
      "idx": 628
    },
    {
      "path": "src/sparkline/Base.js",
      "requires": [
        63,
        126,
        627,
        628
      ],
      "uses": [
        147,
        310,
        378,
        384
      ],
      "idx": 629
    },
    {
      "path": "src/sparkline/BarBase.js",
      "requires": [
        629
      ],
      "uses": [],
      "idx": 630
    },
    {
      "path": "src/sparkline/RangeMap.js",
      "requires": [],
      "uses": [],
      "idx": 631
    },
    {
      "path": "src/sparkline/Bar.js",
      "requires": [
        126,
        630,
        631
      ],
      "uses": [],
      "idx": 632
    },
    {
      "path": "src/sparkline/Box.js",
      "requires": [
        126,
        629
      ],
      "uses": [],
      "idx": 633
    },
    {
      "path": "src/sparkline/Bullet.js",
      "requires": [
        126,
        629
      ],
      "uses": [],
      "idx": 634
    },
    {
      "path": "src/sparkline/Discrete.js",
      "requires": [
        126,
        630
      ],
      "uses": [],
      "idx": 635
    },
    {
      "path": "src/sparkline/Line.js",
      "requires": [
        126,
        629,
        631
      ],
      "uses": [],
      "idx": 636
    },
    {
      "path": "src/sparkline/Pie.js",
      "requires": [
        126,
        629
      ],
      "uses": [],
      "idx": 637
    },
    {
      "path": "src/sparkline/TriState.js",
      "requires": [
        126,
        630,
        631
      ],
      "uses": [],
      "idx": 638
    },
    {
      "path": "src/state/CookieProvider.js",
      "requires": [
        91
      ],
      "uses": [],
      "idx": 639
    },
    {
      "path": "src/state/LocalStorageProvider.js",
      "requires": [
        91,
        301
      ],
      "uses": [],
      "idx": 640
    },
    {
      "path": "src/toolbar/Breadcrumb.js",
      "requires": [
        215,
        312,
        367,
        468
      ],
      "uses": [
        212
      ],
      "idx": 641
    },
    {
      "path": "src/toolbar/Spacer.js",
      "requires": [
        96
      ],
      "uses": [],
      "idx": 642
    },
    {
      "path": "src/view/DragZone.js",
      "requires": [
        427
      ],
      "uses": [
        60
      ],
      "idx": 643
    },
    {
      "path": "src/tree/ViewDragZone.js",
      "requires": [
        643
      ],
      "uses": [
        60
      ],
      "idx": 644
    },
    {
      "path": "src/tree/ViewDropZone.js",
      "requires": [
        547
      ],
      "uses": [],
      "idx": 645
    },
    {
      "path": "src/tree/plugin/TreeViewDragDrop.js",
      "requires": [
        423
      ],
      "uses": [
        644,
        645
      ],
      "idx": 646
    },
    {
      "path": "src/util/CSS.js",
      "requires": [],
      "uses": [
        23
      ],
      "idx": 647
    },
    {
      "path": "src/util/Cookies.js",
      "requires": [],
      "uses": [],
      "idx": 648
    },
    {
      "path": "src/view/MultiSelectorSearch.js",
      "requires": [
        381
      ],
      "uses": [
        28,
        147,
        212,
        323,
        378,
        398,
        412,
        448
      ],
      "idx": 649
    },
    {
      "path": "src/view/MultiSelector.js",
      "requires": [
        147,
        378,
        398,
        412,
        649
      ],
      "uses": [],
      "idx": 650
    },
    {
      "path": "src/window/Toast.js",
      "requires": [
        464
      ],
      "uses": [
        29
      ],
      "idx": 651
    }
  ],
  "classes": {
    "Ext.AbstractManager": {
      "idx": 7,
      "alias": [],
      "alternates": []
    },
    "Ext.Action": {
      "idx": 304,
      "alias": [],
      "alternates": []
    },
    "Ext.Ajax": {
      "idx": 10,
      "alias": [],
      "alternates": []
    },
    "Ext.AnimationQueue": {
      "idx": 11,
      "alias": [],
      "alternates": []
    },
    "Ext.Component": {
      "idx": 96,
      "alias": [
        "widget.box",
        "widget.component"
      ],
      "alternates": [
        "Ext.AbstractComponent"
      ]
    },
    "Ext.ComponentLoader": {
      "idx": 306,
      "alias": [],
      "alternates": []
    },
    "Ext.ComponentManager": {
      "idx": 12,
      "alias": [],
      "alternates": [
        "Ext.ComponentMgr"
      ]
    },
    "Ext.ComponentQuery": {
      "idx": 15,
      "alias": [],
      "alternates": []
    },
    "Ext.Editor": {
      "idx": 314,
      "alias": [
        "widget.editor"
      ],
      "alternates": []
    },
    "Ext.ElementLoader": {
      "idx": 305,
      "alias": [],
      "alternates": []
    },
    "Ext.EventManager": {
      "idx": 315,
      "alias": [],
      "alternates": []
    },
    "Ext.Evented": {
      "idx": 16,
      "alias": [],
      "alternates": [
        "Ext.EventedBase"
      ]
    },
    "Ext.FocusManager": {
      "idx": 318,
      "alias": [],
      "alternates": [
        "Ext.FocusMgr"
      ]
    },
    "Ext.GlobalEvents": {
      "idx": 24,
      "alias": [],
      "alternates": [
        "Ext.globalEvents"
      ]
    },
    "Ext.Img": {
      "idx": 319,
      "alias": [
        "widget.image",
        "widget.imagecomponent"
      ],
      "alternates": []
    },
    "Ext.LoadMask": {
      "idx": 321,
      "alias": [
        "widget.loadmask"
      ],
      "alternates": []
    },
    "Ext.Mixin": {
      "idx": 3,
      "alias": [],
      "alternates": []
    },
    "Ext.ProgressBar": {
      "idx": 325,
      "alias": [
        "widget.progressbar"
      ],
      "alternates": []
    },
    "Ext.ProgressBarWidget": {
      "idx": 326,
      "alias": [
        "widget.progressbarwidget"
      ],
      "alternates": []
    },
    "Ext.TaskQueue": {
      "idx": 58,
      "alias": [],
      "alternates": []
    },
    "Ext.Template": {
      "idx": 60,
      "alias": [],
      "alternates": []
    },
    "Ext.Widget": {
      "idx": 63,
      "alias": [
        "widget.widget"
      ],
      "alternates": []
    },
    "Ext.XTemplate": {
      "idx": 126,
      "alias": [],
      "alternates": []
    },
    "Ext.ZIndexManager": {
      "idx": 311,
      "alias": [],
      "alternates": [
        "Ext.WindowGroup"
      ]
    },
    "Ext.app.Application": {
      "idx": 388,
      "alias": [],
      "alternates": []
    },
    "Ext.app.BaseController": {
      "idx": 331,
      "alias": [],
      "alternates": []
    },
    "Ext.app.Controller": {
      "idx": 338,
      "alias": [],
      "alternates": []
    },
    "Ext.app.EventBus": {
      "idx": 329,
      "alias": [],
      "alternates": []
    },
    "Ext.app.EventDomain": {
      "idx": 327,
      "alias": [],
      "alternates": []
    },
    "Ext.app.Util": {
      "idx": 332,
      "alias": [],
      "alternates": []
    },
    "Ext.app.ViewController": {
      "idx": 392,
      "alias": [],
      "alternates": []
    },
    "Ext.app.ViewModel": {
      "idx": 162,
      "alias": [
        "viewmodel.default"
      ],
      "alternates": []
    },
    "Ext.app.bind.AbstractStub": {
      "idx": 151,
      "alias": [],
      "alternates": []
    },
    "Ext.app.bind.BaseBinding": {
      "idx": 149,
      "alias": [],
      "alternates": []
    },
    "Ext.app.bind.Binding": {
      "idx": 150,
      "alias": [],
      "alternates": []
    },
    "Ext.app.bind.Formula": {
      "idx": 156,
      "alias": [],
      "alternates": []
    },
    "Ext.app.bind.LinkStub": {
      "idx": 153,
      "alias": [],
      "alternates": []
    },
    "Ext.app.bind.Multi": {
      "idx": 155,
      "alias": [],
      "alternates": []
    },
    "Ext.app.bind.RootStub": {
      "idx": 154,
      "alias": [],
      "alternates": []
    },
    "Ext.app.bind.Stub": {
      "idx": 152,
      "alias": [],
      "alternates": []
    },
    "Ext.app.bind.Template": {
      "idx": 157,
      "alias": [],
      "alternates": []
    },
    "Ext.app.bind.TemplateBinding": {
      "idx": 158,
      "alias": [],
      "alternates": []
    },
    "Ext.app.bindinspector.ComponentDetail": {
      "idx": 416,
      "alias": [
        "widget.bindinspector-componentdetail"
      ],
      "alternates": []
    },
    "Ext.app.bindinspector.ComponentList": {
      "idx": 449,
      "alias": [
        "widget.bindinspector-componentlist"
      ],
      "alternates": []
    },
    "Ext.app.bindinspector.Container": {
      "idx": 462,
      "alias": [
        "widget.bindinspector-container"
      ],
      "alternates": []
    },
    "Ext.app.bindinspector.Environment": {
      "idx": 459,
      "alias": [],
      "alternates": []
    },
    "Ext.app.bindinspector.Inspector": {
      "idx": 465,
      "alias": [],
      "alternates": []
    },
    "Ext.app.bindinspector.Util": {
      "idx": 415,
      "alias": [],
      "alternates": []
    },
    "Ext.app.bindinspector.ViewModelDetail": {
      "idx": 460,
      "alias": [
        "widget.bindinspector-viewmodeldetail"
      ],
      "alternates": []
    },
    "Ext.app.bindinspector.noconflict.BaseModel": {
      "idx": 461,
      "alias": [],
      "alternates": []
    },
    "Ext.app.domain.Component": {
      "idx": 328,
      "alias": [],
      "alternates": []
    },
    "Ext.app.domain.Controller": {
      "idx": 466,
      "alias": [],
      "alternates": []
    },
    "Ext.app.domain.Direct": {
      "idx": 467,
      "alias": [],
      "alternates": []
    },
    "Ext.app.domain.Global": {
      "idx": 330,
      "alias": [],
      "alternates": []
    },
    "Ext.app.domain.Store": {
      "idx": 333,
      "alias": [],
      "alternates": []
    },
    "Ext.app.domain.View": {
      "idx": 391,
      "alias": [],
      "alternates": []
    },
    "Ext.app.route.Queue": {
      "idx": 334,
      "alias": [],
      "alternates": []
    },
    "Ext.app.route.Route": {
      "idx": 335,
      "alias": [],
      "alternates": []
    },
    "Ext.app.route.Router": {
      "idx": 337,
      "alias": [],
      "alternates": []
    },
    "Ext.button.Button": {
      "idx": 353,
      "alias": [
        "widget.button"
      ],
      "alternates": [
        "Ext.Button"
      ]
    },
    "Ext.button.Cycle": {
      "idx": 469,
      "alias": [
        "widget.cycle"
      ],
      "alternates": [
        "Ext.CycleButton"
      ]
    },
    "Ext.button.Manager": {
      "idx": 350,
      "alias": [],
      "alternates": [
        "Ext.ButtonToggleManager"
      ]
    },
    "Ext.button.Segmented": {
      "idx": 470,
      "alias": [
        "widget.segmentedbutton"
      ],
      "alternates": []
    },
    "Ext.button.Split": {
      "idx": 468,
      "alias": [
        "widget.splitbutton"
      ],
      "alternates": [
        "Ext.SplitButton"
      ]
    },
    "Ext.container.ButtonGroup": {
      "idx": 473,
      "alias": [
        "widget.buttongroup"
      ],
      "alternates": [
        "Ext.ButtonGroup"
      ]
    },
    "Ext.container.Container": {
      "idx": 312,
      "alias": [
        "widget.container"
      ],
      "alternates": [
        "Ext.Container",
        "Ext.AbstractContainer"
      ]
    },
    "Ext.container.DockingContainer": {
      "idx": 380,
      "alias": [],
      "alternates": []
    },
    "Ext.container.Monitor": {
      "idx": 474,
      "alias": [],
      "alternates": []
    },
    "Ext.container.Viewport": {
      "idx": 477,
      "alias": [
        "widget.viewport"
      ],
      "alternates": [
        "Ext.Viewport"
      ]
    },
    "Ext.dashboard.Column": {
      "idx": 480,
      "alias": [
        "widget.dashboard-column"
      ],
      "alternates": []
    },
    "Ext.dashboard.Dashboard": {
      "idx": 490,
      "alias": [
        "widget.dashboard"
      ],
      "alternates": []
    },
    "Ext.dashboard.DropZone": {
      "idx": 488,
      "alias": [],
      "alternates": []
    },
    "Ext.dashboard.Panel": {
      "idx": 479,
      "alias": [
        "widget.dashboard-panel"
      ],
      "alternates": []
    },
    "Ext.dashboard.Part": {
      "idx": 489,
      "alias": [
        "part.part"
      ],
      "alternates": []
    },
    "Ext.data.AbstractStore": {
      "idx": 159,
      "alias": [],
      "alternates": []
    },
    "Ext.data.ArrayStore": {
      "idx": 198,
      "alias": [
        "store.array"
      ],
      "alternates": [
        "Ext.data.SimpleStore"
      ]
    },
    "Ext.data.Batch": {
      "idx": 140,
      "alias": [],
      "alternates": []
    },
    "Ext.data.BufferedStore": {
      "idx": 200,
      "alias": [
        "store.buffered"
      ],
      "alternates": []
    },
    "Ext.data.ChainedStore": {
      "idx": 161,
      "alias": [
        "store.chained"
      ],
      "alternates": []
    },
    "Ext.data.Connection": {
      "idx": 9,
      "alias": [],
      "alternates": []
    },
    "Ext.data.DirectStore": {
      "idx": 203,
      "alias": [
        "store.direct"
      ],
      "alternates": []
    },
    "Ext.data.Error": {
      "idx": 163,
      "alias": [],
      "alternates": []
    },
    "Ext.data.ErrorCollection": {
      "idx": 164,
      "alias": [],
      "alternates": [
        "Ext.data.Errors"
      ]
    },
    "Ext.data.JsonP": {
      "idx": 204,
      "alias": [],
      "alternates": []
    },
    "Ext.data.JsonPStore": {
      "idx": 206,
      "alias": [
        "store.jsonp"
      ],
      "alternates": []
    },
    "Ext.data.JsonStore": {
      "idx": 207,
      "alias": [
        "store.json"
      ],
      "alternates": []
    },
    "Ext.data.LocalStore": {
      "idx": 160,
      "alias": [],
      "alternates": []
    },
    "Ext.data.Model": {
      "idx": 180,
      "alias": [],
      "alternates": [
        "Ext.data.Record"
      ]
    },
    "Ext.data.ModelManager": {
      "idx": 208,
      "alias": [],
      "alternates": [
        "Ext.ModelMgr"
      ]
    },
    "Ext.data.NodeInterface": {
      "idx": 209,
      "alias": [],
      "alternates": []
    },
    "Ext.data.NodeStore": {
      "idx": 210,
      "alias": [
        "store.node"
      ],
      "alternates": []
    },
    "Ext.data.PageMap": {
      "idx": 199,
      "alias": [],
      "alternates": []
    },
    "Ext.data.ProxyStore": {
      "idx": 187,
      "alias": [],
      "alternates": []
    },
    "Ext.data.Request": {
      "idx": 211,
      "alias": [],
      "alternates": []
    },
    "Ext.data.ResultSet": {
      "idx": 181,
      "alias": [],
      "alternates": []
    },
    "Ext.data.Session": {
      "idx": 147,
      "alias": [],
      "alternates": []
    },
    "Ext.data.SortTypes": {
      "idx": 170,
      "alias": [],
      "alternates": []
    },
    "Ext.data.Store": {
      "idx": 196,
      "alias": [
        "store.store"
      ],
      "alternates": []
    },
    "Ext.data.StoreManager": {
      "idx": 212,
      "alias": [],
      "alternates": [
        "Ext.StoreMgr",
        "Ext.data.StoreMgr",
        "Ext.StoreManager"
      ]
    },
    "Ext.data.TreeModel": {
      "idx": 214,
      "alias": [],
      "alternates": []
    },
    "Ext.data.TreeStore": {
      "idx": 215,
      "alias": [
        "store.tree"
      ],
      "alternates": []
    },
    "Ext.data.Types": {
      "idx": 216,
      "alias": [],
      "alternates": []
    },
    "Ext.data.Validation": {
      "idx": 217,
      "alias": [],
      "alternates": []
    },
    "Ext.data.XmlStore": {
      "idx": 223,
      "alias": [
        "store.xml"
      ],
      "alternates": []
    },
    "Ext.data.field.Boolean": {
      "idx": 173,
      "alias": [
        "data.field.bool",
        "data.field.boolean"
      ],
      "alternates": []
    },
    "Ext.data.field.Date": {
      "idx": 174,
      "alias": [
        "data.field.date"
      ],
      "alternates": []
    },
    "Ext.data.field.Field": {
      "idx": 172,
      "alias": [
        "data.field.auto"
      ],
      "alternates": [
        "Ext.data.Field"
      ]
    },
    "Ext.data.field.Integer": {
      "idx": 175,
      "alias": [
        "data.field.int",
        "data.field.integer"
      ],
      "alternates": []
    },
    "Ext.data.field.Number": {
      "idx": 176,
      "alias": [
        "data.field.float",
        "data.field.number"
      ],
      "alternates": []
    },
    "Ext.data.field.String": {
      "idx": 177,
      "alias": [
        "data.field.string"
      ],
      "alternates": []
    },
    "Ext.data.flash.BinaryXhr": {
      "idx": 8,
      "alias": [],
      "alternates": []
    },
    "Ext.data.identifier.Generator": {
      "idx": 178,
      "alias": [
        "data.identifier.default"
      ],
      "alternates": []
    },
    "Ext.data.identifier.Negative": {
      "idx": 224,
      "alias": [
        "data.identifier.negative"
      ],
      "alternates": []
    },
    "Ext.data.identifier.Sequential": {
      "idx": 179,
      "alias": [
        "data.identifier.sequential"
      ],
      "alternates": []
    },
    "Ext.data.identifier.Uuid": {
      "idx": 225,
      "alias": [
        "data.identifier.uuid"
      ],
      "alternates": []
    },
    "Ext.data.matrix.Matrix": {
      "idx": 143,
      "alias": [],
      "alternates": []
    },
    "Ext.data.matrix.Side": {
      "idx": 142,
      "alias": [],
      "alternates": []
    },
    "Ext.data.matrix.Slice": {
      "idx": 141,
      "alias": [],
      "alternates": []
    },
    "Ext.data.operation.Create": {
      "idx": 166,
      "alias": [
        "data.operation.create"
      ],
      "alternates": []
    },
    "Ext.data.operation.Destroy": {
      "idx": 167,
      "alias": [
        "data.operation.destroy"
      ],
      "alternates": []
    },
    "Ext.data.operation.Operation": {
      "idx": 165,
      "alias": [],
      "alternates": [
        "Ext.data.Operation"
      ]
    },
    "Ext.data.operation.Read": {
      "idx": 168,
      "alias": [
        "data.operation.read"
      ],
      "alternates": []
    },
    "Ext.data.operation.Update": {
      "idx": 169,
      "alias": [
        "data.operation.update"
      ],
      "alternates": []
    },
    "Ext.data.proxy.Ajax": {
      "idx": 189,
      "alias": [
        "proxy.ajax"
      ],
      "alternates": [
        "Ext.data.HttpProxy",
        "Ext.data.AjaxProxy"
      ]
    },
    "Ext.data.proxy.Client": {
      "idx": 185,
      "alias": [],
      "alternates": [
        "Ext.data.ClientProxy"
      ]
    },
    "Ext.data.proxy.Direct": {
      "idx": 202,
      "alias": [
        "proxy.direct"
      ],
      "alternates": [
        "Ext.data.DirectProxy"
      ]
    },
    "Ext.data.proxy.JsonP": {
      "idx": 205,
      "alias": [
        "proxy.jsonp",
        "proxy.scripttag"
      ],
      "alternates": [
        "Ext.data.ScriptTagProxy"
      ]
    },
    "Ext.data.proxy.LocalStorage": {
      "idx": 227,
      "alias": [
        "proxy.localstorage"
      ],
      "alternates": [
        "Ext.data.LocalStorageProxy"
      ]
    },
    "Ext.data.proxy.Memory": {
      "idx": 186,
      "alias": [
        "proxy.memory"
      ],
      "alternates": [
        "Ext.data.MemoryProxy"
      ]
    },
    "Ext.data.proxy.Proxy": {
      "idx": 184,
      "alias": [
        "proxy.proxy"
      ],
      "alternates": [
        "Ext.data.DataProxy",
        "Ext.data.Proxy"
      ]
    },
    "Ext.data.proxy.Rest": {
      "idx": 228,
      "alias": [
        "proxy.rest"
      ],
      "alternates": [
        "Ext.data.RestProxy"
      ]
    },
    "Ext.data.proxy.Server": {
      "idx": 188,
      "alias": [
        "proxy.server"
      ],
      "alternates": [
        "Ext.data.ServerProxy"
      ]
    },
    "Ext.data.proxy.SessionStorage": {
      "idx": 229,
      "alias": [
        "proxy.sessionstorage"
      ],
      "alternates": [
        "Ext.data.SessionStorageProxy"
      ]
    },
    "Ext.data.proxy.Sql": {
      "idx": 230,
      "alias": [
        "proxy.sql"
      ],
      "alternates": [
        "Ext.data.proxy.SQL"
      ]
    },
    "Ext.data.proxy.WebStorage": {
      "idx": 226,
      "alias": [],
      "alternates": [
        "Ext.data.WebStorageProxy"
      ]
    },
    "Ext.data.reader.Array": {
      "idx": 197,
      "alias": [
        "reader.array"
      ],
      "alternates": [
        "Ext.data.ArrayReader"
      ]
    },
    "Ext.data.reader.Json": {
      "idx": 190,
      "alias": [
        "reader.json"
      ],
      "alternates": [
        "Ext.data.JsonReader"
      ]
    },
    "Ext.data.reader.Reader": {
      "idx": 182,
      "alias": [
        "reader.base"
      ],
      "alternates": [
        "Ext.data.Reader",
        "Ext.data.DataReader"
      ]
    },
    "Ext.data.reader.Xml": {
      "idx": 221,
      "alias": [
        "reader.xml"
      ],
      "alternates": [
        "Ext.data.XmlReader"
      ]
    },
    "Ext.data.schema.Association": {
      "idx": 133,
      "alias": [],
      "alternates": []
    },
    "Ext.data.schema.ManyToMany": {
      "idx": 136,
      "alias": [],
      "alternates": []
    },
    "Ext.data.schema.ManyToOne": {
      "idx": 135,
      "alias": [],
      "alternates": []
    },
    "Ext.data.schema.Namer": {
      "idx": 138,
      "alias": [
        "namer.default"
      ],
      "alternates": []
    },
    "Ext.data.schema.OneToOne": {
      "idx": 134,
      "alias": [],
      "alternates": []
    },
    "Ext.data.schema.Role": {
      "idx": 132,
      "alias": [],
      "alternates": []
    },
    "Ext.data.schema.Schema": {
      "idx": 139,
      "alias": [
        "schema.default"
      ],
      "alternates": []
    },
    "Ext.data.session.BatchVisitor": {
      "idx": 146,
      "alias": [],
      "alternates": []
    },
    "Ext.data.session.ChangesVisitor": {
      "idx": 144,
      "alias": [],
      "alternates": []
    },
    "Ext.data.session.ChildChangesVisitor": {
      "idx": 145,
      "alias": [],
      "alternates": []
    },
    "Ext.data.validator.Bound": {
      "idx": 231,
      "alias": [
        "data.validator.bound"
      ],
      "alternates": []
    },
    "Ext.data.validator.Email": {
      "idx": 233,
      "alias": [
        "data.validator.email"
      ],
      "alternates": []
    },
    "Ext.data.validator.Exclusion": {
      "idx": 235,
      "alias": [
        "data.validator.exclusion"
      ],
      "alternates": []
    },
    "Ext.data.validator.Format": {
      "idx": 232,
      "alias": [
        "data.validator.format"
      ],
      "alternates": []
    },
    "Ext.data.validator.Inclusion": {
      "idx": 236,
      "alias": [
        "data.validator.inclusion"
      ],
      "alternates": []
    },
    "Ext.data.validator.Length": {
      "idx": 237,
      "alias": [
        "data.validator.length"
      ],
      "alternates": []
    },
    "Ext.data.validator.List": {
      "idx": 234,
      "alias": [
        "data.validator.list"
      ],
      "alternates": []
    },
    "Ext.data.validator.Presence": {
      "idx": 238,
      "alias": [
        "data.validator.presence"
      ],
      "alternates": []
    },
    "Ext.data.validator.Range": {
      "idx": 239,
      "alias": [
        "data.validator.range"
      ],
      "alternates": []
    },
    "Ext.data.validator.Validator": {
      "idx": 171,
      "alias": [
        "data.validator.base"
      ],
      "alternates": []
    },
    "Ext.data.writer.Json": {
      "idx": 191,
      "alias": [
        "writer.json"
      ],
      "alternates": [
        "Ext.data.JsonWriter"
      ]
    },
    "Ext.data.writer.Writer": {
      "idx": 183,
      "alias": [
        "writer.base"
      ],
      "alternates": [
        "Ext.data.DataWriter",
        "Ext.data.Writer"
      ]
    },
    "Ext.data.writer.Xml": {
      "idx": 222,
      "alias": [
        "writer.xml"
      ],
      "alternates": [
        "Ext.data.XmlWriter"
      ]
    },
    "Ext.dd.DD": {
      "idx": 371,
      "alias": [],
      "alternates": []
    },
    "Ext.dd.DDProxy": {
      "idx": 373,
      "alias": [],
      "alternates": []
    },
    "Ext.dd.DDTarget": {
      "idx": 429,
      "alias": [],
      "alternates": []
    },
    "Ext.dd.DragDrop": {
      "idx": 370,
      "alias": [],
      "alternates": []
    },
    "Ext.dd.DragDropManager": {
      "idx": 359,
      "alias": [],
      "alternates": [
        "Ext.dd.DragDropMgr",
        "Ext.dd.DDM"
      ]
    },
    "Ext.dd.DragSource": {
      "idx": 375,
      "alias": [],
      "alternates": []
    },
    "Ext.dd.DragTracker": {
      "idx": 424,
      "alias": [],
      "alternates": []
    },
    "Ext.dd.DragZone": {
      "idx": 427,
      "alias": [],
      "alternates": []
    },
    "Ext.dd.DropTarget": {
      "idx": 431,
      "alias": [],
      "alternates": []
    },
    "Ext.dd.DropZone": {
      "idx": 433,
      "alias": [],
      "alternates": []
    },
    "Ext.dd.Registry": {
      "idx": 432,
      "alias": [],
      "alternates": []
    },
    "Ext.dd.ScrollManager": {
      "idx": 430,
      "alias": [],
      "alternates": []
    },
    "Ext.dd.StatusProxy": {
      "idx": 374,
      "alias": [],
      "alternates": []
    },
    "Ext.direct.Event": {
      "idx": 240,
      "alias": [
        "direct.event"
      ],
      "alternates": []
    },
    "Ext.direct.ExceptionEvent": {
      "idx": 242,
      "alias": [
        "direct.exception"
      ],
      "alternates": []
    },
    "Ext.direct.JsonProvider": {
      "idx": 244,
      "alias": [
        "direct.jsonprovider"
      ],
      "alternates": []
    },
    "Ext.direct.Manager": {
      "idx": 201,
      "alias": [],
      "alternates": []
    },
    "Ext.direct.PollingProvider": {
      "idx": 245,
      "alias": [
        "direct.pollingprovider"
      ],
      "alternates": []
    },
    "Ext.direct.Provider": {
      "idx": 243,
      "alias": [
        "direct.provider"
      ],
      "alternates": []
    },
    "Ext.direct.RemotingEvent": {
      "idx": 241,
      "alias": [
        "direct.rpc"
      ],
      "alternates": []
    },
    "Ext.direct.RemotingMethod": {
      "idx": 246,
      "alias": [],
      "alternates": []
    },
    "Ext.direct.RemotingProvider": {
      "idx": 248,
      "alias": [
        "direct.remotingprovider"
      ],
      "alternates": []
    },
    "Ext.direct.Transaction": {
      "idx": 247,
      "alias": [
        "direct.transaction"
      ],
      "alternates": [
        "Ext.Direct.Transaction"
      ]
    },
    "Ext.dom.ButtonElement": {
      "idx": 349,
      "alias": [],
      "alternates": []
    },
    "Ext.dom.CompositeElement": {
      "idx": 65,
      "alias": [],
      "alternates": [
        "Ext.CompositeElement"
      ]
    },
    "Ext.dom.CompositeElementLite": {
      "idx": 26,
      "alias": [],
      "alternates": [
        "Ext.CompositeElementLite"
      ]
    },
    "Ext.dom.Element": {
      "idx": 23,
      "alias": [],
      "alternates": [
        "Ext.Element"
      ]
    },
    "Ext.dom.Fly": {
      "idx": 25,
      "alias": [],
      "alternates": [
        "Ext.dom.Element.Fly"
      ]
    },
    "Ext.dom.GarbageCollector": {
      "idx": 54,
      "alias": [],
      "alternates": []
    },
    "Ext.dom.Helper": {
      "idx": 218,
      "alias": [],
      "alternates": [
        "Ext.DomHelper",
        "Ext.core.DomHelper"
      ]
    },
    "Ext.dom.Layer": {
      "idx": 491,
      "alias": [],
      "alternates": [
        "Ext.Layer"
      ]
    },
    "Ext.dom.Query": {
      "idx": 220,
      "alias": [],
      "alternates": [
        "Ext.core.DomQuery",
        "Ext.DomQuery"
      ]
    },
    "Ext.dom.Shadow": {
      "idx": 21,
      "alias": [],
      "alternates": [
        "Ext.Shadow"
      ]
    },
    "Ext.dom.Shim": {
      "idx": 22,
      "alias": [],
      "alternates": []
    },
    "Ext.dom.Underlay": {
      "idx": 20,
      "alias": [],
      "alternates": []
    },
    "Ext.dom.UnderlayPool": {
      "idx": 19,
      "alias": [],
      "alternates": []
    },
    "Ext.event.Controller": {
      "idx": 1,
      "alias": [],
      "alternates": []
    },
    "Ext.event.Dispatcher": {
      "idx": 2,
      "alias": [],
      "alternates": []
    },
    "Ext.event.Event": {
      "idx": 115,
      "alias": [],
      "alternates": [
        "Ext.EventObjectImpl"
      ]
    },
    "Ext.event.ListenerStack": {
      "idx": 0,
      "alias": [],
      "alternates": []
    },
    "Ext.event.gesture.DoubleTap": {
      "idx": 102,
      "alias": [],
      "alternates": []
    },
    "Ext.event.gesture.Drag": {
      "idx": 103,
      "alias": [],
      "alternates": []
    },
    "Ext.event.gesture.EdgeSwipe": {
      "idx": 105,
      "alias": [],
      "alternates": []
    },
    "Ext.event.gesture.LongPress": {
      "idx": 106,
      "alias": [],
      "alternates": []
    },
    "Ext.event.gesture.MultiTouch": {
      "idx": 107,
      "alias": [],
      "alternates": []
    },
    "Ext.event.gesture.Pinch": {
      "idx": 108,
      "alias": [],
      "alternates": []
    },
    "Ext.event.gesture.Recognizer": {
      "idx": 100,
      "alias": [],
      "alternates": []
    },
    "Ext.event.gesture.Rotate": {
      "idx": 109,
      "alias": [],
      "alternates": []
    },
    "Ext.event.gesture.SingleTouch": {
      "idx": 101,
      "alias": [],
      "alternates": []
    },
    "Ext.event.gesture.Swipe": {
      "idx": 104,
      "alias": [],
      "alternates": []
    },
    "Ext.event.gesture.Tap": {
      "idx": 110,
      "alias": [],
      "alternates": []
    },
    "Ext.event.publisher.Dom": {
      "idx": 116,
      "alias": [],
      "alternates": []
    },
    "Ext.event.publisher.ElementPaint": {
      "idx": 253,
      "alias": [],
      "alternates": []
    },
    "Ext.event.publisher.ElementSize": {
      "idx": 260,
      "alias": [],
      "alternates": []
    },
    "Ext.event.publisher.Focus": {
      "idx": 122,
      "alias": [],
      "alternates": []
    },
    "Ext.event.publisher.Gesture": {
      "idx": 120,
      "alias": [],
      "alternates": [
        "Ext.event.publisher.TouchGesture"
      ]
    },
    "Ext.event.publisher.Publisher": {
      "idx": 111,
      "alias": [],
      "alternates": []
    },
    "Ext.flash.Component": {
      "idx": 493,
      "alias": [
        "widget.flash"
      ],
      "alternates": [
        "Ext.FlashComponent"
      ]
    },
    "Ext.form.Basic": {
      "idx": 499,
      "alias": [],
      "alternates": [
        "Ext.form.BasicForm"
      ]
    },
    "Ext.form.CheckboxGroup": {
      "idx": 504,
      "alias": [
        "widget.checkboxgroup"
      ],
      "alternates": []
    },
    "Ext.form.CheckboxManager": {
      "idx": 413,
      "alias": [],
      "alternates": []
    },
    "Ext.form.FieldAncestor": {
      "idx": 500,
      "alias": [],
      "alternates": []
    },
    "Ext.form.FieldContainer": {
      "idx": 502,
      "alias": [
        "widget.fieldcontainer"
      ],
      "alternates": []
    },
    "Ext.form.FieldSet": {
      "idx": 505,
      "alias": [
        "widget.fieldset"
      ],
      "alternates": []
    },
    "Ext.form.Label": {
      "idx": 506,
      "alias": [
        "widget.label"
      ],
      "alternates": []
    },
    "Ext.form.Labelable": {
      "idx": 393,
      "alias": [],
      "alternates": []
    },
    "Ext.form.Panel": {
      "idx": 507,
      "alias": [
        "widget.form"
      ],
      "alternates": [
        "Ext.FormPanel",
        "Ext.form.FormPanel"
      ]
    },
    "Ext.form.RadioGroup": {
      "idx": 510,
      "alias": [
        "widget.radiogroup"
      ],
      "alternates": []
    },
    "Ext.form.RadioManager": {
      "idx": 508,
      "alias": [],
      "alternates": []
    },
    "Ext.form.action.Action": {
      "idx": 494,
      "alias": [],
      "alternates": [
        "Ext.form.Action"
      ]
    },
    "Ext.form.action.DirectAction": {
      "idx": 511,
      "alias": [],
      "alternates": []
    },
    "Ext.form.action.DirectLoad": {
      "idx": 512,
      "alias": [
        "formaction.directload"
      ],
      "alternates": [
        "Ext.form.Action.DirectLoad"
      ]
    },
    "Ext.form.action.DirectSubmit": {
      "idx": 513,
      "alias": [
        "formaction.directsubmit"
      ],
      "alternates": [
        "Ext.form.Action.DirectSubmit"
      ]
    },
    "Ext.form.action.Load": {
      "idx": 495,
      "alias": [
        "formaction.load"
      ],
      "alternates": [
        "Ext.form.Action.Load"
      ]
    },
    "Ext.form.action.StandardSubmit": {
      "idx": 514,
      "alias": [
        "formaction.standardsubmit"
      ],
      "alternates": []
    },
    "Ext.form.action.Submit": {
      "idx": 496,
      "alias": [
        "formaction.submit"
      ],
      "alternates": [
        "Ext.form.Action.Submit"
      ]
    },
    "Ext.form.field.Base": {
      "idx": 396,
      "alias": [
        "widget.field"
      ],
      "alternates": [
        "Ext.form.Field",
        "Ext.form.BaseField"
      ]
    },
    "Ext.form.field.Checkbox": {
      "idx": 414,
      "alias": [
        "widget.checkbox",
        "widget.checkboxfield"
      ],
      "alternates": [
        "Ext.form.Checkbox"
      ]
    },
    "Ext.form.field.ComboBox": {
      "idx": 524,
      "alias": [
        "widget.combo",
        "widget.combobox"
      ],
      "alternates": [
        "Ext.form.ComboBox"
      ]
    },
    "Ext.form.field.Date": {
      "idx": 527,
      "alias": [
        "widget.datefield"
      ],
      "alternates": [
        "Ext.form.DateField",
        "Ext.form.Date"
      ]
    },
    "Ext.form.field.Display": {
      "idx": 397,
      "alias": [
        "widget.displayfield"
      ],
      "alternates": [
        "Ext.form.DisplayField",
        "Ext.form.Display"
      ]
    },
    "Ext.form.field.Field": {
      "idx": 395,
      "alias": [],
      "alternates": []
    },
    "Ext.form.field.File": {
      "idx": 531,
      "alias": [
        "widget.filefield",
        "widget.fileuploadfield"
      ],
      "alternates": [
        "Ext.form.FileUploadField",
        "Ext.ux.form.FileUploadField",
        "Ext.form.File"
      ]
    },
    "Ext.form.field.FileButton": {
      "idx": 528,
      "alias": [
        "widget.filebutton"
      ],
      "alternates": []
    },
    "Ext.form.field.Hidden": {
      "idx": 533,
      "alias": [
        "widget.hidden",
        "widget.hiddenfield"
      ],
      "alternates": [
        "Ext.form.Hidden"
      ]
    },
    "Ext.form.field.HtmlEditor": {
      "idx": 536,
      "alias": [
        "widget.htmleditor"
      ],
      "alternates": [
        "Ext.form.HtmlEditor"
      ]
    },
    "Ext.form.field.Number": {
      "idx": 521,
      "alias": [
        "widget.numberfield"
      ],
      "alternates": [
        "Ext.form.NumberField",
        "Ext.form.Number"
      ]
    },
    "Ext.form.field.Picker": {
      "idx": 515,
      "alias": [
        "widget.pickerfield"
      ],
      "alternates": [
        "Ext.form.Picker"
      ]
    },
    "Ext.form.field.Radio": {
      "idx": 509,
      "alias": [
        "widget.radio",
        "widget.radiofield"
      ],
      "alternates": [
        "Ext.form.Radio"
      ]
    },
    "Ext.form.field.Spinner": {
      "idx": 520,
      "alias": [
        "widget.spinnerfield"
      ],
      "alternates": [
        "Ext.form.Spinner"
      ]
    },
    "Ext.form.field.Tag": {
      "idx": 537,
      "alias": [
        "widget.tagfield"
      ],
      "alternates": []
    },
    "Ext.form.field.Text": {
      "idx": 448,
      "alias": [
        "widget.textfield"
      ],
      "alternates": [
        "Ext.form.TextField",
        "Ext.form.Text"
      ]
    },
    "Ext.form.field.TextArea": {
      "idx": 497,
      "alias": [
        "widget.textarea",
        "widget.textareafield"
      ],
      "alternates": [
        "Ext.form.TextArea"
      ]
    },
    "Ext.form.field.Time": {
      "idx": 539,
      "alias": [
        "widget.timefield"
      ],
      "alternates": [
        "Ext.form.TimeField",
        "Ext.form.Time"
      ]
    },
    "Ext.form.field.Trigger": {
      "idx": 540,
      "alias": [
        "widget.trigger",
        "widget.triggerfield"
      ],
      "alternates": [
        "Ext.form.TriggerField",
        "Ext.form.TwinTriggerField",
        "Ext.form.Trigger"
      ]
    },
    "Ext.form.field.VTypes": {
      "idx": 446,
      "alias": [],
      "alternates": [
        "Ext.form.VTypes"
      ]
    },
    "Ext.form.trigger.Component": {
      "idx": 530,
      "alias": [
        "trigger.component"
      ],
      "alternates": []
    },
    "Ext.form.trigger.Spinner": {
      "idx": 519,
      "alias": [
        "trigger.spinner"
      ],
      "alternates": []
    },
    "Ext.form.trigger.Trigger": {
      "idx": 447,
      "alias": [
        "trigger.trigger"
      ],
      "alternates": []
    },
    "Ext.fx.Anim": {
      "idx": 52,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.Animation": {
      "idx": 270,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.Animator": {
      "idx": 47,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.CubicBezier": {
      "idx": 48,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.DrawPath": {
      "idx": 50,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.Easing": {
      "idx": 49,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.Manager": {
      "idx": 46,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.PropertyHandler": {
      "idx": 51,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.Queue": {
      "idx": 45,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.Runner": {
      "idx": 273,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.State": {
      "idx": 261,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.animation.Abstract": {
      "idx": 262,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.animation.Cube": {
      "idx": 274,
      "alias": [
        "animation.cube"
      ],
      "alternates": []
    },
    "Ext.fx.animation.Fade": {
      "idx": 265,
      "alias": [
        "animation.fade",
        "animation.fadeIn"
      ],
      "alternates": [
        "Ext.fx.animation.FadeIn"
      ]
    },
    "Ext.fx.animation.FadeOut": {
      "idx": 266,
      "alias": [
        "animation.fadeOut"
      ],
      "alternates": []
    },
    "Ext.fx.animation.Flip": {
      "idx": 267,
      "alias": [
        "animation.flip"
      ],
      "alternates": []
    },
    "Ext.fx.animation.Pop": {
      "idx": 268,
      "alias": [
        "animation.pop",
        "animation.popIn"
      ],
      "alternates": [
        "Ext.fx.animation.PopIn"
      ]
    },
    "Ext.fx.animation.PopOut": {
      "idx": 269,
      "alias": [
        "animation.popOut"
      ],
      "alternates": []
    },
    "Ext.fx.animation.Slide": {
      "idx": 263,
      "alias": [
        "animation.slide",
        "animation.slideIn"
      ],
      "alternates": [
        "Ext.fx.animation.SlideIn"
      ]
    },
    "Ext.fx.animation.SlideOut": {
      "idx": 264,
      "alias": [
        "animation.slideOut"
      ],
      "alternates": []
    },
    "Ext.fx.animation.Wipe": {
      "idx": 275,
      "alias": [],
      "alternates": [
        "Ext.fx.animation.WipeIn"
      ]
    },
    "Ext.fx.animation.WipeOut": {
      "idx": 276,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.easing.Abstract": {
      "idx": 69,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.easing.Bounce": {
      "idx": 71,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.easing.BoundMomentum": {
      "idx": 72,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.easing.EaseIn": {
      "idx": 277,
      "alias": [
        "easing.ease-in"
      ],
      "alternates": []
    },
    "Ext.fx.easing.EaseOut": {
      "idx": 74,
      "alias": [
        "easing.ease-out"
      ],
      "alternates": []
    },
    "Ext.fx.easing.Easing": {
      "idx": 278,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.easing.Linear": {
      "idx": 73,
      "alias": [
        "easing.linear"
      ],
      "alternates": []
    },
    "Ext.fx.easing.Momentum": {
      "idx": 70,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.layout.Card": {
      "idx": 288,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.layout.card.Abstract": {
      "idx": 279,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.layout.card.Cover": {
      "idx": 282,
      "alias": [
        "fx.layout.card.cover"
      ],
      "alternates": []
    },
    "Ext.fx.layout.card.Cube": {
      "idx": 289,
      "alias": [
        "fx.layout.card.cube"
      ],
      "alternates": []
    },
    "Ext.fx.layout.card.Fade": {
      "idx": 284,
      "alias": [
        "fx.layout.card.fade"
      ],
      "alternates": []
    },
    "Ext.fx.layout.card.Flip": {
      "idx": 285,
      "alias": [
        "fx.layout.card.flip"
      ],
      "alternates": []
    },
    "Ext.fx.layout.card.Pop": {
      "idx": 286,
      "alias": [
        "fx.layout.card.pop"
      ],
      "alternates": []
    },
    "Ext.fx.layout.card.Reveal": {
      "idx": 283,
      "alias": [
        "fx.layout.card.reveal"
      ],
      "alternates": []
    },
    "Ext.fx.layout.card.Scroll": {
      "idx": 287,
      "alias": [
        "fx.layout.card.scroll"
      ],
      "alternates": []
    },
    "Ext.fx.layout.card.ScrollCover": {
      "idx": 290,
      "alias": [
        "fx.layout.card.scrollcover"
      ],
      "alternates": []
    },
    "Ext.fx.layout.card.ScrollReveal": {
      "idx": 291,
      "alias": [
        "fx.layout.card.scrollreveal"
      ],
      "alternates": []
    },
    "Ext.fx.layout.card.Slide": {
      "idx": 281,
      "alias": [
        "fx.layout.card.slide"
      ],
      "alternates": []
    },
    "Ext.fx.layout.card.Style": {
      "idx": 280,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.runner.Css": {
      "idx": 271,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.runner.CssAnimation": {
      "idx": 292,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.runner.CssTransition": {
      "idx": 272,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.target.Component": {
      "idx": 44,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.target.CompositeElement": {
      "idx": 40,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.target.CompositeElementCSS": {
      "idx": 41,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.target.CompositeSprite": {
      "idx": 43,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.target.Element": {
      "idx": 38,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.target.ElementCSS": {
      "idx": 39,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.target.Sprite": {
      "idx": 42,
      "alias": [],
      "alternates": []
    },
    "Ext.fx.target.Target": {
      "idx": 37,
      "alias": [],
      "alternates": []
    },
    "Ext.grid.CellContext": {
      "idx": 406,
      "alias": [],
      "alternates": []
    },
    "Ext.grid.CellEditor": {
      "idx": 541,
      "alias": [],
      "alternates": []
    },
    "Ext.grid.ColumnComponentLayout": {
      "idx": 437,
      "alias": [
        "layout.columncomponent"
      ],
      "alternates": []
    },
    "Ext.grid.ColumnLayout": {
      "idx": 421,
      "alias": [
        "layout.gridcolumn"
      ],
      "alternates": []
    },
    "Ext.grid.ColumnManager": {
      "idx": 543,
      "alias": [],
      "alternates": [
        "Ext.grid.ColumnModel"
      ]
    },
    "Ext.grid.NavigationModel": {
      "idx": 442,
      "alias": [
        "view.navigation.grid"
      ],
      "alternates": []
    },
    "Ext.grid.Panel": {
      "idx": 412,
      "alias": [
        "widget.grid",
        "widget.gridpanel"
      ],
      "alternates": [
        "Ext.list.ListView",
        "Ext.ListView",
        "Ext.grid.GridPanel"
      ]
    },
    "Ext.grid.RowEditor": {
      "idx": 545,
      "alias": [
        "widget.roweditor"
      ],
      "alternates": []
    },
    "Ext.grid.RowEditorButtons": {
      "idx": 544,
      "alias": [
        "widget.roweditorbuttons"
      ],
      "alternates": []
    },
    "Ext.grid.Scroller": {
      "idx": 546,
      "alias": [],
      "alternates": []
    },
    "Ext.grid.ViewDropZone": {
      "idx": 548,
      "alias": [],
      "alternates": []
    },
    "Ext.grid.column.Action": {
      "idx": 549,
      "alias": [
        "widget.actioncolumn"
      ],
      "alternates": [
        "Ext.grid.ActionColumn"
      ]
    },
    "Ext.grid.column.Boolean": {
      "idx": 550,
      "alias": [
        "widget.booleancolumn"
      ],
      "alternates": [
        "Ext.grid.BooleanColumn"
      ]
    },
    "Ext.grid.column.Check": {
      "idx": 551,
      "alias": [
        "widget.checkcolumn"
      ],
      "alternates": [
        "Ext.ux.CheckColumn",
        "Ext.grid.column.CheckColumn"
      ]
    },
    "Ext.grid.column.Column": {
      "idx": 438,
      "alias": [
        "widget.gridcolumn"
      ],
      "alternates": [
        "Ext.grid.Column"
      ]
    },
    "Ext.grid.column.Date": {
      "idx": 552,
      "alias": [
        "widget.datecolumn"
      ],
      "alternates": [
        "Ext.grid.DateColumn"
      ]
    },
    "Ext.grid.column.Number": {
      "idx": 553,
      "alias": [
        "widget.numbercolumn"
      ],
      "alternates": [
        "Ext.grid.NumberColumn"
      ]
    },
    "Ext.grid.column.RowNumberer": {
      "idx": 554,
      "alias": [
        "widget.rownumberer"
      ],
      "alternates": [
        "Ext.grid.RowNumberer"
      ]
    },
    "Ext.grid.column.Template": {
      "idx": 555,
      "alias": [
        "widget.templatecolumn"
      ],
      "alternates": [
        "Ext.grid.TemplateColumn"
      ]
    },
    "Ext.grid.column.Widget": {
      "idx": 556,
      "alias": [
        "widget.widgetcolumn"
      ],
      "alternates": []
    },
    "Ext.grid.feature.AbstractSummary": {
      "idx": 558,
      "alias": [
        "feature.abstractsummary"
      ],
      "alternates": []
    },
    "Ext.grid.feature.Feature": {
      "idx": 557,
      "alias": [
        "feature.feature"
      ],
      "alternates": []
    },
    "Ext.grid.feature.GroupStore": {
      "idx": 559,
      "alias": [],
      "alternates": []
    },
    "Ext.grid.feature.Grouping": {
      "idx": 560,
      "alias": [
        "feature.grouping"
      ],
      "alternates": []
    },
    "Ext.grid.feature.GroupingSummary": {
      "idx": 561,
      "alias": [
        "feature.groupingsummary"
      ],
      "alternates": []
    },
    "Ext.grid.feature.RowBody": {
      "idx": 562,
      "alias": [
        "feature.rowbody"
      ],
      "alternates": []
    },
    "Ext.grid.feature.Summary": {
      "idx": 563,
      "alias": [
        "feature.summary"
      ],
      "alternates": []
    },
    "Ext.grid.filters.Filters": {
      "idx": 578,
      "alias": [
        "plugin.gridfilters"
      ],
      "alternates": []
    },
    "Ext.grid.filters.filter.Base": {
      "idx": 570,
      "alias": [],
      "alternates": []
    },
    "Ext.grid.filters.filter.Boolean": {
      "idx": 572,
      "alias": [
        "grid.filter.boolean"
      ],
      "alternates": []
    },
    "Ext.grid.filters.filter.Date": {
      "idx": 574,
      "alias": [
        "grid.filter.date"
      ],
      "alternates": []
    },
    "Ext.grid.filters.filter.List": {
      "idx": 575,
      "alias": [
        "grid.filter.list"
      ],
      "alternates": []
    },
    "Ext.grid.filters.filter.Number": {
      "idx": 576,
      "alias": [
        "grid.filter.number",
        "grid.filter.numeric"
      ],
      "alternates": []
    },
    "Ext.grid.filters.filter.SingleFilter": {
      "idx": 571,
      "alias": [],
      "alternates": []
    },
    "Ext.grid.filters.filter.String": {
      "idx": 577,
      "alias": [
        "grid.filter.string"
      ],
      "alternates": []
    },
    "Ext.grid.filters.filter.TriFilter": {
      "idx": 573,
      "alias": [],
      "alternates": []
    },
    "Ext.grid.header.Container": {
      "idx": 436,
      "alias": [
        "widget.headercontainer"
      ],
      "alternates": []
    },
    "Ext.grid.header.DragZone": {
      "idx": 428,
      "alias": [],
      "alternates": []
    },
    "Ext.grid.header.DropZone": {
      "idx": 434,
      "alias": [],
      "alternates": []
    },
    "Ext.grid.locking.HeaderContainer": {
      "idx": 579,
      "alias": [],
      "alternates": []
    },
    "Ext.grid.locking.Lockable": {
      "idx": 581,
      "alias": [],
      "alternates": [
        "Ext.grid.Lockable"
      ]
    },
    "Ext.grid.locking.RowSynchronizer": {
      "idx": 408,
      "alias": [],
      "alternates": []
    },
    "Ext.grid.locking.View": {
      "idx": 580,
      "alias": [],
      "alternates": [
        "Ext.grid.LockingView"
      ]
    },
    "Ext.grid.plugin.BufferedRenderer": {
      "idx": 582,
      "alias": [
        "plugin.bufferedrenderer"
      ],
      "alternates": []
    },
    "Ext.grid.plugin.CellEditing": {
      "idx": 584,
      "alias": [
        "plugin.cellediting"
      ],
      "alternates": []
    },
    "Ext.grid.plugin.DragDrop": {
      "idx": 585,
      "alias": [
        "plugin.gridviewdragdrop"
      ],
      "alternates": []
    },
    "Ext.grid.plugin.Editing": {
      "idx": 583,
      "alias": [
        "editing.editing"
      ],
      "alternates": []
    },
    "Ext.grid.plugin.HeaderReorderer": {
      "idx": 435,
      "alias": [
        "plugin.gridheaderreorderer"
      ],
      "alternates": []
    },
    "Ext.grid.plugin.HeaderResizer": {
      "idx": 425,
      "alias": [
        "plugin.gridheaderresizer"
      ],
      "alternates": []
    },
    "Ext.grid.plugin.RowEditing": {
      "idx": 586,
      "alias": [
        "plugin.rowediting"
      ],
      "alternates": []
    },
    "Ext.grid.plugin.RowExpander": {
      "idx": 588,
      "alias": [
        "plugin.rowexpander"
      ],
      "alternates": []
    },
    "Ext.grid.property.Grid": {
      "idx": 589,
      "alias": [
        "widget.propertygrid"
      ],
      "alternates": [
        "Ext.grid.PropertyGrid"
      ]
    },
    "Ext.grid.property.HeaderContainer": {
      "idx": 590,
      "alias": [],
      "alternates": [
        "Ext.grid.PropertyColumnModel"
      ]
    },
    "Ext.grid.property.Property": {
      "idx": 591,
      "alias": [],
      "alternates": [
        "Ext.PropGridProperty"
      ]
    },
    "Ext.grid.property.Reader": {
      "idx": 592,
      "alias": [],
      "alternates": []
    },
    "Ext.grid.property.Store": {
      "idx": 593,
      "alias": [],
      "alternates": [
        "Ext.grid.PropertyStore"
      ]
    },
    "Ext.layout.Context": {
      "idx": 597,
      "alias": [],
      "alternates": []
    },
    "Ext.layout.ContextItem": {
      "idx": 595,
      "alias": [],
      "alternates": []
    },
    "Ext.layout.Layout": {
      "idx": 308,
      "alias": [],
      "alternates": []
    },
    "Ext.layout.SizeModel": {
      "idx": 307,
      "alias": [],
      "alternates": []
    },
    "Ext.layout.component.Auto": {
      "idx": 323,
      "alias": [
        "layout.autocomponent"
      ],
      "alternates": []
    },
    "Ext.layout.component.Body": {
      "idx": 455,
      "alias": [
        "layout.body"
      ],
      "alternates": []
    },
    "Ext.layout.component.BoundList": {
      "idx": 517,
      "alias": [
        "layout.boundlist"
      ],
      "alternates": []
    },
    "Ext.layout.component.Component": {
      "idx": 322,
      "alias": [],
      "alternates": []
    },
    "Ext.layout.component.Dock": {
      "idx": 378,
      "alias": [
        "layout.dock"
      ],
      "alternates": [
        "Ext.layout.component.AbstractDock"
      ]
    },
    "Ext.layout.component.FieldSet": {
      "idx": 599,
      "alias": [
        "layout.fieldset"
      ],
      "alternates": []
    },
    "Ext.layout.component.ProgressBar": {
      "idx": 324,
      "alias": [
        "layout.progressbar"
      ],
      "alternates": []
    },
    "Ext.layout.component.field.FieldContainer": {
      "idx": 501,
      "alias": [
        "layout.fieldcontainer"
      ],
      "alternates": []
    },
    "Ext.layout.component.field.HtmlEditor": {
      "idx": 535,
      "alias": [
        "layout.htmleditor"
      ],
      "alternates": []
    },
    "Ext.layout.container.Absolute": {
      "idx": 600,
      "alias": [
        "layout.absolute"
      ],
      "alternates": [
        "Ext.layout.AbsoluteLayout"
      ]
    },
    "Ext.layout.container.Accordion": {
      "idx": 602,
      "alias": [
        "layout.accordion"
      ],
      "alternates": [
        "Ext.layout.AccordionLayout"
      ]
    },
    "Ext.layout.container.Anchor": {
      "idx": 478,
      "alias": [
        "layout.anchor"
      ],
      "alternates": [
        "Ext.layout.AnchorLayout"
      ]
    },
    "Ext.layout.container.Auto": {
      "idx": 310,
      "alias": [
        "layout.auto",
        "layout.autocontainer"
      ],
      "alternates": []
    },
    "Ext.layout.container.Border": {
      "idx": 451,
      "alias": [
        "layout.border"
      ],
      "alternates": [
        "Ext.layout.BorderLayout"
      ]
    },
    "Ext.layout.container.Box": {
      "idx": 361,
      "alias": [
        "layout.box"
      ],
      "alternates": [
        "Ext.layout.BoxLayout"
      ]
    },
    "Ext.layout.container.Card": {
      "idx": 453,
      "alias": [
        "layout.card"
      ],
      "alternates": [
        "Ext.layout.CardLayout"
      ]
    },
    "Ext.layout.container.Center": {
      "idx": 603,
      "alias": [
        "layout.center",
        "layout.ux.center"
      ],
      "alternates": [
        "Ext.ux.layout.Center"
      ]
    },
    "Ext.layout.container.CheckboxGroup": {
      "idx": 503,
      "alias": [
        "layout.checkboxgroup"
      ],
      "alternates": []
    },
    "Ext.layout.container.Column": {
      "idx": 481,
      "alias": [
        "layout.column"
      ],
      "alternates": [
        "Ext.layout.ColumnLayout"
      ]
    },
    "Ext.layout.container.ColumnSplitter": {
      "idx": 486,
      "alias": [
        "widget.columnsplitter"
      ],
      "alternates": []
    },
    "Ext.layout.container.ColumnSplitterTracker": {
      "idx": 485,
      "alias": [],
      "alternates": []
    },
    "Ext.layout.container.Container": {
      "idx": 309,
      "alias": [
        "layout.container"
      ],
      "alternates": [
        "Ext.layout.ContainerLayout"
      ]
    },
    "Ext.layout.container.Dashboard": {
      "idx": 487,
      "alias": [
        "layout.dashboard"
      ],
      "alternates": []
    },
    "Ext.layout.container.Editor": {
      "idx": 313,
      "alias": [
        "layout.editor"
      ],
      "alternates": []
    },
    "Ext.layout.container.Fit": {
      "idx": 398,
      "alias": [
        "layout.fit"
      ],
      "alternates": [
        "Ext.layout.FitLayout"
      ]
    },
    "Ext.layout.container.Form": {
      "idx": 604,
      "alias": [
        "layout.form"
      ],
      "alternates": [
        "Ext.layout.FormLayout"
      ]
    },
    "Ext.layout.container.HBox": {
      "idx": 363,
      "alias": [
        "layout.hbox"
      ],
      "alternates": [
        "Ext.layout.HBoxLayout"
      ]
    },
    "Ext.layout.container.SegmentedButton": {
      "idx": 605,
      "alias": [
        "layout.segmentedbutton"
      ],
      "alternates": []
    },
    "Ext.layout.container.Table": {
      "idx": 472,
      "alias": [
        "layout.table"
      ],
      "alternates": [
        "Ext.layout.TableLayout"
      ]
    },
    "Ext.layout.container.VBox": {
      "idx": 365,
      "alias": [
        "layout.vbox"
      ],
      "alternates": [
        "Ext.layout.VBoxLayout"
      ]
    },
    "Ext.layout.container.border.Region": {
      "idx": 97,
      "alias": [],
      "alternates": []
    },
    "Ext.layout.container.boxOverflow.Menu": {
      "idx": 355,
      "alias": [
        "box.overflow.Menu",
        "box.overflow.menu"
      ],
      "alternates": [
        "Ext.layout.boxOverflow.Menu"
      ]
    },
    "Ext.layout.container.boxOverflow.None": {
      "idx": 346,
      "alias": [
        "box.overflow.None",
        "box.overflow.none"
      ],
      "alternates": [
        "Ext.layout.boxOverflow.None"
      ]
    },
    "Ext.layout.container.boxOverflow.Scroller": {
      "idx": 357,
      "alias": [
        "box.overflow.Scroller",
        "box.overflow.scroller"
      ],
      "alternates": [
        "Ext.layout.boxOverflow.Scroller"
      ]
    },
    "Ext.menu.CheckItem": {
      "idx": 566,
      "alias": [
        "widget.menucheckitem"
      ],
      "alternates": []
    },
    "Ext.menu.ColorPicker": {
      "idx": 606,
      "alias": [
        "widget.colormenu"
      ],
      "alternates": []
    },
    "Ext.menu.DatePicker": {
      "idx": 607,
      "alias": [
        "widget.datemenu"
      ],
      "alternates": []
    },
    "Ext.menu.Item": {
      "idx": 565,
      "alias": [
        "widget.menuitem"
      ],
      "alternates": [
        "Ext.menu.TextItem"
      ]
    },
    "Ext.menu.KeyNav": {
      "idx": 567,
      "alias": [],
      "alternates": []
    },
    "Ext.menu.Manager": {
      "idx": 351,
      "alias": [],
      "alternates": [
        "Ext.menu.MenuMgr"
      ]
    },
    "Ext.menu.Menu": {
      "idx": 569,
      "alias": [
        "widget.menu"
      ],
      "alternates": []
    },
    "Ext.menu.Separator": {
      "idx": 568,
      "alias": [
        "widget.menuseparator"
      ],
      "alternates": []
    },
    "Ext.mixin.Bindable": {
      "idx": 62,
      "alias": [],
      "alternates": []
    },
    "Ext.mixin.Factoryable": {
      "idx": 66,
      "alias": [],
      "alternates": []
    },
    "Ext.mixin.Hookable": {
      "idx": 293,
      "alias": [],
      "alternates": []
    },
    "Ext.mixin.Identifiable": {
      "idx": 4,
      "alias": [],
      "alternates": []
    },
    "Ext.mixin.Inheritable": {
      "idx": 61,
      "alias": [],
      "alternates": []
    },
    "Ext.mixin.Mashup": {
      "idx": 294,
      "alias": [],
      "alternates": []
    },
    "Ext.mixin.Observable": {
      "idx": 5,
      "alias": [],
      "alternates": []
    },
    "Ext.mixin.Queryable": {
      "idx": 213,
      "alias": [],
      "alternates": []
    },
    "Ext.mixin.Responsive": {
      "idx": 295,
      "alias": [],
      "alternates": []
    },
    "Ext.mixin.Selectable": {
      "idx": 296,
      "alias": [],
      "alternates": []
    },
    "Ext.mixin.Templatable": {
      "idx": 254,
      "alias": [],
      "alternates": []
    },
    "Ext.mixin.Traversable": {
      "idx": 297,
      "alias": [],
      "alternates": []
    },
    "Ext.overrides.GlobalEvents": {
      "idx": 56,
      "alias": [],
      "alternates": []
    },
    "Ext.overrides.Widget": {
      "idx": 98,
      "alias": [],
      "alternates": []
    },
    "Ext.overrides.app.Application": {
      "idx": 390,
      "alias": [],
      "alternates": []
    },
    "Ext.overrides.dom.Element": {
      "idx": 55,
      "alias": [],
      "alternates": []
    },
    "Ext.overrides.dom.Helper": {
      "idx": 219,
      "alias": [],
      "alternates": []
    },
    "Ext.overrides.event.Event": {
      "idx": 118,
      "alias": [],
      "alternates": []
    },
    "Ext.overrides.event.publisher.Dom": {
      "idx": 119,
      "alias": [],
      "alternates": []
    },
    "Ext.overrides.event.publisher.Gesture": {
      "idx": 121,
      "alias": [],
      "alternates": []
    },
    "Ext.overrides.util.Positionable": {
      "idx": 18,
      "alias": [],
      "alternates": []
    },
    "Ext.panel.Bar": {
      "idx": 339,
      "alias": [],
      "alternates": []
    },
    "Ext.panel.DD": {
      "idx": 377,
      "alias": [],
      "alternates": []
    },
    "Ext.panel.Header": {
      "idx": 344,
      "alias": [
        "widget.header"
      ],
      "alternates": []
    },
    "Ext.panel.Panel": {
      "idx": 381,
      "alias": [
        "widget.panel"
      ],
      "alternates": [
        "Ext.Panel"
      ]
    },
    "Ext.panel.Pinnable": {
      "idx": 608,
      "alias": [],
      "alternates": []
    },
    "Ext.panel.Proxy": {
      "idx": 376,
      "alias": [],
      "alternates": [
        "Ext.dd.PanelProxy"
      ]
    },
    "Ext.panel.Table": {
      "idx": 399,
      "alias": [
        "widget.tablepanel"
      ],
      "alternates": []
    },
    "Ext.panel.Title": {
      "idx": 341,
      "alias": [
        "widget.title"
      ],
      "alternates": []
    },
    "Ext.panel.Tool": {
      "idx": 343,
      "alias": [
        "widget.tool"
      ],
      "alternates": []
    },
    "Ext.perf.Accumulator": {
      "idx": 298,
      "alias": [],
      "alternates": []
    },
    "Ext.perf.Monitor": {
      "idx": 299,
      "alias": [],
      "alternates": [
        "Ext.Perf"
      ]
    },
    "Ext.picker.Color": {
      "idx": 534,
      "alias": [
        "widget.colorpicker"
      ],
      "alternates": [
        "Ext.ColorPalette"
      ]
    },
    "Ext.picker.Date": {
      "idx": 526,
      "alias": [
        "widget.datepicker"
      ],
      "alternates": [
        "Ext.DatePicker"
      ]
    },
    "Ext.picker.Month": {
      "idx": 525,
      "alias": [
        "widget.monthpicker"
      ],
      "alternates": [
        "Ext.MonthPicker"
      ]
    },
    "Ext.picker.Time": {
      "idx": 538,
      "alias": [
        "widget.timepicker"
      ],
      "alternates": []
    },
    "Ext.plugin.Abstract": {
      "idx": 423,
      "alias": [],
      "alternates": [
        "Ext.AbstractPlugin"
      ]
    },
    "Ext.plugin.Manager": {
      "idx": 609,
      "alias": [],
      "alternates": [
        "Ext.PluginManager",
        "Ext.PluginMgr"
      ]
    },
    "Ext.plugin.Responsive": {
      "idx": 475,
      "alias": [
        "plugin.responsive"
      ],
      "alternates": []
    },
    "Ext.plugin.Viewport": {
      "idx": 476,
      "alias": [
        "plugin.viewport"
      ],
      "alternates": []
    },
    "Ext.resizer.BorderSplitter": {
      "idx": 450,
      "alias": [
        "widget.bordersplitter"
      ],
      "alternates": []
    },
    "Ext.resizer.BorderSplitterTracker": {
      "idx": 610,
      "alias": [],
      "alternates": []
    },
    "Ext.resizer.Handle": {
      "idx": 612,
      "alias": [],
      "alternates": []
    },
    "Ext.resizer.ResizeTracker": {
      "idx": 613,
      "alias": [],
      "alternates": []
    },
    "Ext.resizer.Resizer": {
      "idx": 615,
      "alias": [],
      "alternates": [
        "Ext.Resizable"
      ]
    },
    "Ext.resizer.Splitter": {
      "idx": 360,
      "alias": [
        "widget.splitter"
      ],
      "alternates": []
    },
    "Ext.resizer.SplitterTracker": {
      "idx": 483,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.Component": {
      "idx": 99,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.button.Button": {
      "idx": 354,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.button.Segmented": {
      "idx": 471,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.dd.DD": {
      "idx": 372,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.dom.Element": {
      "idx": 27,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.event.Event": {
      "idx": 117,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.form.Labelable": {
      "idx": 394,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.form.field.File": {
      "idx": 532,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.form.field.FileButton": {
      "idx": 529,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.grid.CellEditor": {
      "idx": 542,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.grid.ColumnLayout": {
      "idx": 422,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.grid.NavigationModel": {
      "idx": 443,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.grid.column.Column": {
      "idx": 439,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.grid.feature.Summary": {
      "idx": 564,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.grid.plugin.HeaderResizer": {
      "idx": 426,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.grid.plugin.RowEditing": {
      "idx": 587,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.layout.ContextItem": {
      "idx": 596,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.layout.component.Dock": {
      "idx": 389,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.layout.container.Absolute": {
      "idx": 601,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.layout.container.Border": {
      "idx": 452,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.layout.container.Box": {
      "idx": 362,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.layout.container.Column": {
      "idx": 482,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.layout.container.HBox": {
      "idx": 364,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.layout.container.VBox": {
      "idx": 366,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.layout.container.boxOverflow.Menu": {
      "idx": 356,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.layout.container.boxOverflow.Scroller": {
      "idx": 358,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.panel.Bar": {
      "idx": 340,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.panel.Panel": {
      "idx": 382,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.panel.Title": {
      "idx": 342,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.resizer.BorderSplitterTracker": {
      "idx": 611,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.resizer.ResizeTracker": {
      "idx": 614,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.resizer.SplitterTracker": {
      "idx": 484,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.scroll.DomScroller": {
      "idx": 87,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.scroll.Indicator": {
      "idx": 83,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.scroll.Scroller": {
      "idx": 68,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.scroll.TouchScroller": {
      "idx": 85,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.selection.CellModel": {
      "idx": 617,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.selection.TreeModel": {
      "idx": 420,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.slider.Multi": {
      "idx": 621,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.tab.Bar": {
      "idx": 457,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.tip.QuickTipManager": {
      "idx": 387,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.tree.Column": {
      "idx": 441,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.util.FocusableContainer": {
      "idx": 368,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.util.Renderable": {
      "idx": 90,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.view.NavigationModel": {
      "idx": 403,
      "alias": [],
      "alternates": []
    },
    "Ext.rtl.view.Table": {
      "idx": 411,
      "alias": [],
      "alternates": []
    },
    "Ext.scroll.DomScroller": {
      "idx": 86,
      "alias": [
        "scroller.dom"
      ],
      "alternates": []
    },
    "Ext.scroll.Indicator": {
      "idx": 82,
      "alias": [
        "widget.scrollindicator"
      ],
      "alternates": []
    },
    "Ext.scroll.Scroller": {
      "idx": 67,
      "alias": [
        "scroller.scroller"
      ],
      "alternates": []
    },
    "Ext.scroll.TouchScroller": {
      "idx": 84,
      "alias": [
        "scroller.touch"
      ],
      "alternates": []
    },
    "Ext.selection.CellModel": {
      "idx": 616,
      "alias": [
        "selection.cellmodel"
      ],
      "alternates": []
    },
    "Ext.selection.CheckboxModel": {
      "idx": 622,
      "alias": [
        "selection.checkboxmodel"
      ],
      "alternates": []
    },
    "Ext.selection.DataViewModel": {
      "idx": 401,
      "alias": [
        "selection.dataviewmodel"
      ],
      "alternates": []
    },
    "Ext.selection.Model": {
      "idx": 400,
      "alias": [
        "selection.abstract"
      ],
      "alternates": [
        "Ext.AbstractSelectionModel"
      ]
    },
    "Ext.selection.RowModel": {
      "idx": 418,
      "alias": [
        "selection.rowmodel"
      ],
      "alternates": []
    },
    "Ext.selection.TreeModel": {
      "idx": 419,
      "alias": [
        "selection.treemodel"
      ],
      "alternates": []
    },
    "Ext.slider.Multi": {
      "idx": 620,
      "alias": [
        "widget.multislider"
      ],
      "alternates": [
        "Ext.slider.MultiSlider"
      ]
    },
    "Ext.slider.Single": {
      "idx": 623,
      "alias": [
        "widget.slider",
        "widget.sliderfield"
      ],
      "alternates": [
        "Ext.Slider",
        "Ext.form.SliderField",
        "Ext.slider.SingleSlider",
        "Ext.slider.Slider"
      ]
    },
    "Ext.slider.Thumb": {
      "idx": 618,
      "alias": [],
      "alternates": []
    },
    "Ext.slider.Tip": {
      "idx": 619,
      "alias": [
        "widget.slidertip"
      ],
      "alternates": []
    },
    "Ext.slider.Widget": {
      "idx": 624,
      "alias": [
        "widget.sliderwidget"
      ],
      "alternates": []
    },
    "Ext.sparkline.Bar": {
      "idx": 632,
      "alias": [
        "widget.sparklinebar"
      ],
      "alternates": []
    },
    "Ext.sparkline.BarBase": {
      "idx": 630,
      "alias": [],
      "alternates": []
    },
    "Ext.sparkline.Base": {
      "idx": 629,
      "alias": [],
      "alternates": []
    },
    "Ext.sparkline.Box": {
      "idx": 633,
      "alias": [
        "widget.sparklinebox"
      ],
      "alternates": []
    },
    "Ext.sparkline.Bullet": {
      "idx": 634,
      "alias": [
        "widget.sparklinebullet"
      ],
      "alternates": []
    },
    "Ext.sparkline.CanvasBase": {
      "idx": 626,
      "alias": [],
      "alternates": []
    },
    "Ext.sparkline.CanvasCanvas": {
      "idx": 627,
      "alias": [],
      "alternates": []
    },
    "Ext.sparkline.Discrete": {
      "idx": 635,
      "alias": [
        "widget.sparklinediscrete"
      ],
      "alternates": []
    },
    "Ext.sparkline.Line": {
      "idx": 636,
      "alias": [
        "widget.sparklineline"
      ],
      "alternates": []
    },
    "Ext.sparkline.Pie": {
      "idx": 637,
      "alias": [
        "widget.sparklinepie"
      ],
      "alternates": []
    },
    "Ext.sparkline.RangeMap": {
      "idx": 631,
      "alias": [],
      "alternates": []
    },
    "Ext.sparkline.Shape": {
      "idx": 625,
      "alias": [],
      "alternates": []
    },
    "Ext.sparkline.TriState": {
      "idx": 638,
      "alias": [
        "widget.sparklinetristate"
      ],
      "alternates": []
    },
    "Ext.sparkline.VmlCanvas": {
      "idx": 628,
      "alias": [],
      "alternates": []
    },
    "Ext.state.CookieProvider": {
      "idx": 639,
      "alias": [],
      "alternates": []
    },
    "Ext.state.LocalStorageProvider": {
      "idx": 640,
      "alias": [
        "state.localstorage"
      ],
      "alternates": []
    },
    "Ext.state.Manager": {
      "idx": 92,
      "alias": [],
      "alternates": []
    },
    "Ext.state.Provider": {
      "idx": 91,
      "alias": [],
      "alternates": []
    },
    "Ext.state.Stateful": {
      "idx": 93,
      "alias": [],
      "alternates": []
    },
    "Ext.tab.Bar": {
      "idx": 456,
      "alias": [
        "widget.tabbar"
      ],
      "alternates": []
    },
    "Ext.tab.Panel": {
      "idx": 458,
      "alias": [
        "widget.tabpanel"
      ],
      "alternates": [
        "Ext.TabPanel"
      ]
    },
    "Ext.tab.Tab": {
      "idx": 454,
      "alias": [
        "widget.tab"
      ],
      "alternates": []
    },
    "Ext.tip.QuickTip": {
      "idx": 385,
      "alias": [
        "widget.quicktip"
      ],
      "alternates": [
        "Ext.QuickTip"
      ]
    },
    "Ext.tip.QuickTipManager": {
      "idx": 386,
      "alias": [],
      "alternates": [
        "Ext.QuickTips"
      ]
    },
    "Ext.tip.Tip": {
      "idx": 383,
      "alias": [
        "widget.tip"
      ],
      "alternates": [
        "Ext.Tip"
      ]
    },
    "Ext.tip.ToolTip": {
      "idx": 384,
      "alias": [
        "widget.tooltip"
      ],
      "alternates": [
        "Ext.ToolTip"
      ]
    },
    "Ext.toolbar.Breadcrumb": {
      "idx": 641,
      "alias": [
        "widget.breadcrumb"
      ],
      "alternates": []
    },
    "Ext.toolbar.Fill": {
      "idx": 345,
      "alias": [
        "widget.tbfill"
      ],
      "alternates": [
        "Ext.Toolbar.Fill"
      ]
    },
    "Ext.toolbar.Item": {
      "idx": 347,
      "alias": [
        "widget.tbitem"
      ],
      "alternates": [
        "Ext.Toolbar.Item"
      ]
    },
    "Ext.toolbar.Paging": {
      "idx": 522,
      "alias": [
        "widget.pagingtoolbar"
      ],
      "alternates": [
        "Ext.PagingToolbar"
      ]
    },
    "Ext.toolbar.Separator": {
      "idx": 348,
      "alias": [
        "widget.tbseparator"
      ],
      "alternates": [
        "Ext.Toolbar.Separator"
      ]
    },
    "Ext.toolbar.Spacer": {
      "idx": 642,
      "alias": [
        "widget.tbspacer"
      ],
      "alternates": [
        "Ext.Toolbar.Spacer"
      ]
    },
    "Ext.toolbar.TextItem": {
      "idx": 518,
      "alias": [
        "widget.tbtext"
      ],
      "alternates": [
        "Ext.Toolbar.TextItem"
      ]
    },
    "Ext.toolbar.Toolbar": {
      "idx": 369,
      "alias": [
        "widget.toolbar"
      ],
      "alternates": [
        "Ext.Toolbar"
      ]
    },
    "Ext.tree.Column": {
      "idx": 440,
      "alias": [
        "widget.treecolumn"
      ],
      "alternates": []
    },
    "Ext.tree.NavigationModel": {
      "idx": 444,
      "alias": [
        "view.navigation.tree"
      ],
      "alternates": []
    },
    "Ext.tree.Panel": {
      "idx": 445,
      "alias": [
        "widget.treepanel"
      ],
      "alternates": [
        "Ext.tree.TreePanel",
        "Ext.TreePanel"
      ]
    },
    "Ext.tree.View": {
      "idx": 417,
      "alias": [
        "widget.treeview"
      ],
      "alternates": []
    },
    "Ext.tree.ViewDragZone": {
      "idx": 644,
      "alias": [],
      "alternates": []
    },
    "Ext.tree.ViewDropZone": {
      "idx": 645,
      "alias": [],
      "alternates": []
    },
    "Ext.tree.plugin.TreeViewDragDrop": {
      "idx": 646,
      "alias": [
        "plugin.treeviewdragdrop"
      ],
      "alternates": []
    },
    "Ext.util.AbstractMixedCollection": {
      "idx": 32,
      "alias": [],
      "alternates": []
    },
    "Ext.util.Animate": {
      "idx": 53,
      "alias": [],
      "alternates": []
    },
    "Ext.util.Base64": {
      "idx": 300,
      "alias": [],
      "alternates": []
    },
    "Ext.util.CSS": {
      "idx": 647,
      "alias": [],
      "alternates": []
    },
    "Ext.util.ClickRepeater": {
      "idx": 352,
      "alias": [],
      "alternates": []
    },
    "Ext.util.Collection": {
      "idx": 129,
      "alias": [],
      "alternates": []
    },
    "Ext.util.CollectionKey": {
      "idx": 127,
      "alias": [],
      "alternates": []
    },
    "Ext.util.ComponentDragger": {
      "idx": 463,
      "alias": [],
      "alternates": []
    },
    "Ext.util.Cookies": {
      "idx": 648,
      "alias": [],
      "alternates": []
    },
    "Ext.util.ElementContainer": {
      "idx": 88,
      "alias": [],
      "alternates": []
    },
    "Ext.util.Event": {
      "idx": 30,
      "alias": [],
      "alternates": []
    },
    "Ext.util.Filter": {
      "idx": 28,
      "alias": [],
      "alternates": []
    },
    "Ext.util.FilterCollection": {
      "idx": 194,
      "alias": [],
      "alternates": []
    },
    "Ext.util.Floating": {
      "idx": 94,
      "alias": [],
      "alternates": []
    },
    "Ext.util.Focusable": {
      "idx": 95,
      "alias": [],
      "alternates": []
    },
    "Ext.util.FocusableContainer": {
      "idx": 367,
      "alias": [],
      "alternates": []
    },
    "Ext.util.Format": {
      "idx": 59,
      "alias": [],
      "alternates": []
    },
    "Ext.util.Group": {
      "idx": 192,
      "alias": [],
      "alternates": []
    },
    "Ext.util.GroupCollection": {
      "idx": 195,
      "alias": [],
      "alternates": []
    },
    "Ext.util.Grouper": {
      "idx": 128,
      "alias": [],
      "alternates": []
    },
    "Ext.util.HashMap": {
      "idx": 6,
      "alias": [],
      "alternates": []
    },
    "Ext.util.History": {
      "idx": 336,
      "alias": [],
      "alternates": [
        "Ext.History"
      ]
    },
    "Ext.util.Inflector": {
      "idx": 137,
      "alias": [],
      "alternates": []
    },
    "Ext.util.KeyMap": {
      "idx": 316,
      "alias": [],
      "alternates": [
        "Ext.KeyMap"
      ]
    },
    "Ext.util.KeyNav": {
      "idx": 317,
      "alias": [],
      "alternates": [
        "Ext.KeyNav"
      ]
    },
    "Ext.util.LocalStorage": {
      "idx": 301,
      "alias": [],
      "alternates": []
    },
    "Ext.util.LruCache": {
      "idx": 14,
      "alias": [],
      "alternates": []
    },
    "Ext.util.Memento": {
      "idx": 379,
      "alias": [],
      "alternates": []
    },
    "Ext.util.MixedCollection": {
      "idx": 35,
      "alias": [],
      "alternates": []
    },
    "Ext.util.ObjectTemplate": {
      "idx": 131,
      "alias": [],
      "alternates": []
    },
    "Ext.util.Observable": {
      "idx": 31,
      "alias": [],
      "alternates": []
    },
    "Ext.util.Offset": {
      "idx": 112,
      "alias": [],
      "alternates": []
    },
    "Ext.util.PaintMonitor": {
      "idx": 252,
      "alias": [],
      "alternates": []
    },
    "Ext.util.Point": {
      "idx": 114,
      "alias": [],
      "alternates": []
    },
    "Ext.util.Positionable": {
      "idx": 17,
      "alias": [],
      "alternates": []
    },
    "Ext.util.ProtoElement": {
      "idx": 64,
      "alias": [],
      "alternates": []
    },
    "Ext.util.Queue": {
      "idx": 594,
      "alias": [],
      "alternates": []
    },
    "Ext.util.Region": {
      "idx": 113,
      "alias": [],
      "alternates": []
    },
    "Ext.util.Renderable": {
      "idx": 89,
      "alias": [],
      "alternates": []
    },
    "Ext.util.Schedulable": {
      "idx": 148,
      "alias": [],
      "alternates": []
    },
    "Ext.util.Scheduler": {
      "idx": 130,
      "alias": [],
      "alternates": []
    },
    "Ext.util.SizeMonitor": {
      "idx": 259,
      "alias": [],
      "alternates": []
    },
    "Ext.util.Sortable": {
      "idx": 34,
      "alias": [],
      "alternates": []
    },
    "Ext.util.Sorter": {
      "idx": 33,
      "alias": [],
      "alternates": []
    },
    "Ext.util.SorterCollection": {
      "idx": 193,
      "alias": [],
      "alternates": []
    },
    "Ext.util.StoreHolder": {
      "idx": 320,
      "alias": [],
      "alternates": []
    },
    "Ext.util.TaskManager": {
      "idx": 302,
      "alias": [],
      "alternates": [
        "Ext.TaskManager"
      ]
    },
    "Ext.util.TaskRunner": {
      "idx": 36,
      "alias": [],
      "alternates": []
    },
    "Ext.util.TextMetrics": {
      "idx": 303,
      "alias": [],
      "alternates": []
    },
    "Ext.util.Translatable": {
      "idx": 81,
      "alias": [],
      "alternates": []
    },
    "Ext.util.XTemplateCompiler": {
      "idx": 125,
      "alias": [],
      "alternates": []
    },
    "Ext.util.XTemplateParser": {
      "idx": 124,
      "alias": [],
      "alternates": []
    },
    "Ext.util.paintmonitor.Abstract": {
      "idx": 249,
      "alias": [],
      "alternates": []
    },
    "Ext.util.paintmonitor.CssAnimation": {
      "idx": 250,
      "alias": [],
      "alternates": []
    },
    "Ext.util.paintmonitor.OverflowChange": {
      "idx": 251,
      "alias": [],
      "alternates": []
    },
    "Ext.util.sizemonitor.Abstract": {
      "idx": 255,
      "alias": [],
      "alternates": []
    },
    "Ext.util.sizemonitor.Default": {
      "idx": 256,
      "alias": [],
      "alternates": []
    },
    "Ext.util.sizemonitor.OverflowChange": {
      "idx": 258,
      "alias": [],
      "alternates": []
    },
    "Ext.util.sizemonitor.Scroll": {
      "idx": 257,
      "alias": [],
      "alternates": []
    },
    "Ext.util.translatable.Abstract": {
      "idx": 75,
      "alias": [],
      "alternates": []
    },
    "Ext.util.translatable.CssPosition": {
      "idx": 80,
      "alias": [],
      "alternates": []
    },
    "Ext.util.translatable.CssTransform": {
      "idx": 77,
      "alias": [],
      "alternates": []
    },
    "Ext.util.translatable.Dom": {
      "idx": 76,
      "alias": [],
      "alternates": []
    },
    "Ext.util.translatable.ScrollParent": {
      "idx": 79,
      "alias": [],
      "alternates": []
    },
    "Ext.util.translatable.ScrollPosition": {
      "idx": 78,
      "alias": [],
      "alternates": []
    },
    "Ext.view.AbstractView": {
      "idx": 404,
      "alias": [],
      "alternates": []
    },
    "Ext.view.BoundList": {
      "idx": 523,
      "alias": [
        "widget.boundlist"
      ],
      "alternates": [
        "Ext.BoundList"
      ]
    },
    "Ext.view.BoundListKeyNav": {
      "idx": 516,
      "alias": [
        "view.navigation.boundlist"
      ],
      "alternates": []
    },
    "Ext.view.DragZone": {
      "idx": 643,
      "alias": [],
      "alternates": []
    },
    "Ext.view.DropZone": {
      "idx": 547,
      "alias": [],
      "alternates": []
    },
    "Ext.view.MultiSelector": {
      "idx": 650,
      "alias": [
        "widget.multiselector"
      ],
      "alternates": []
    },
    "Ext.view.MultiSelectorSearch": {
      "idx": 649,
      "alias": [
        "widget.multiselector-search"
      ],
      "alternates": []
    },
    "Ext.view.NavigationModel": {
      "idx": 402,
      "alias": [
        "view.navigation.default"
      ],
      "alternates": []
    },
    "Ext.view.NodeCache": {
      "idx": 409,
      "alias": [],
      "alternates": []
    },
    "Ext.view.Table": {
      "idx": 410,
      "alias": [
        "widget.gridview",
        "widget.tableview"
      ],
      "alternates": [
        "Ext.grid.View"
      ]
    },
    "Ext.view.TableLayout": {
      "idx": 407,
      "alias": [
        "layout.tableview"
      ],
      "alternates": []
    },
    "Ext.view.View": {
      "idx": 405,
      "alias": [
        "widget.dataview"
      ],
      "alternates": [
        "Ext.DataView"
      ]
    },
    "Ext.window.MessageBox": {
      "idx": 498,
      "alias": [
        "widget.messagebox"
      ],
      "alternates": []
    },
    "Ext.window.Toast": {
      "idx": 651,
      "alias": [
        "widget.toast"
      ],
      "alternates": []
    },
    "Ext.window.Window": {
      "idx": 464,
      "alias": [
        "widget.window"
      ],
      "alternates": [
        "Ext.Window"
      ]
    }
  },
  "packages": {
    "ext": {
      "creator": "Sencha",
      "output": "${package.dir}/build",
      "requires": [
        "sencha-core",
        "ext"
      ],
      "type": "framework",
      "version": "5.0.2.1447"
    },
    "sencha-core": {
      "creator": "Sencha",
      "output": "${package.dir}/build",
      "requires": [],
      "slicer": {
        "js": []
      },
      "type": "code",
      "version": "5.0.0"
    }
  },
  "bootRelative": true
};
