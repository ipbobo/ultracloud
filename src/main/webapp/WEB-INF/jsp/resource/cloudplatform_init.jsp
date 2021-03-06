<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<base href="<%=basePath%>">
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<link href="static/login/bootstrap.min.css" rel="stylesheet" />
	<link rel="stylesheet" href="static/ace/css/font-awesome.css" />
	<link href="css/bootstrap-responsive.min.css" rel="stylesheet" />  
	<link href="css/bwizard.min.css" rel="stylesheet" />
	<link rel="stylesheet" href="static/ace/css/ace-fonts.css" />
	<!-- <link rel="stylesheet" href="static/ace/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" /> -->
	<!-- <link rel="stylesheet" href="static/ace/css/ace-skins.css" class="ace-main-stylesheet" id="ace-skins-stylesheet" /> -->
	<!-- <script src="static/ace/js/ace-extra.js"></script> -->
	<script src="plugins/codeEditor/jquery.min.js"></script>
	<script src="static/login/js/bootstrap.min.js"></script>
	<style>
	body{padding-top: 10px;}
	.next {
		padding-left: 10px;
		float: left;
	}
	</style>
	<script type="text/javascript">
	/*!
	 * jQuery Cookie Plugin v1.3
	 * https://github.com/carhartl/jquery-cookie
	 *
	 * Copyright 2011, Klaus Hartl
	 * Dual licensed under the MIT or GPL Version 2 licenses.
	 * http://www.opensource.org/licenses/mit-license.php
	 * http://www.opensource.org/licenses/GPL-2.0
	 */
	(function(k, h, j) {
		var i = /\+/g;
		function l(a) {
			return a;
		}
		function n(a) {
			return decodeURIComponent(a.replace(i, " "));
		}
		var m = k.cookie = function(c, d, w) {
			if (d !== j) {
				w = k.extend({}, m.defaults, w);
				if (d === null) {
					w.expires = -1;
				}
				if (typeof w.expires === "number") {
					var b = w.expires, x = w.expires = new Date();
					x.setDate(x.getDate() + b);
				}
				d = m.json ? JSON.stringify(d) : String(d);
				return (h.cookie = [ encodeURIComponent(c), "=",
						m.raw ? d : encodeURIComponent(d),
						w.expires ? "; expires=" + w.expires.toUTCString() : "",
						w.path ? "; path=" + w.path : "",
						w.domain ? "; domain=" + w.domain : "",
						w.secure ? "; secure" : "" ].join(""));
			}
			var v = m.raw ? l : n;
			var a = h.cookie.split("; ");
			for (var e = 0, g = a.length; e < g; e++) {
				var f = a[e].split("=");
				if (v(f.shift()) === c) {
					var t = v(f.join("="));
					return m.json ? JSON.parse(t) : t;
				}
			}
			return null;
		};
		m.defaults = {};
		k.removeCookie = function(a, b) {
			if (k.cookie(a) !== null) {
				k.cookie(a, null, b);
				return true;
			}
			return false;
		};
	})(jQuery, document);
	/*
	 * ! jQuery UI Widget October 23, 2012 http://jqueryui.com
	 * 
	 * Copyright 2012 jQuery Foundation and other contributors Released under the
	 * MIT license. http://jquery.org/license
	 * 
	 * http://api.jqueryui.com/jQuery.widget/
	 */
	(function(f, h) {
		var g = 0, i = Array.prototype.slice, j = f.cleanData;
		f.cleanData = function(d) {
			for (var c = 0, b; (b = d[c]) != null; c++) {
				try {
					f(b).triggerHandler("remove");
				} catch (a) {
				}
			}
			j(d);
		};
		f.widget = function(o, d, p) {
			var a, b, e, c, n = o.split(".")[0];
			o = o.split(".")[1];
			a = n + "-" + o;
			if (!p) {
				p = d;
				d = f.Widget;
			}
			f.expr[":"][a.toLowerCase()] = function(k) {
				return !!f.data(k, a);
			};
			f[n] = f[n] || {};
			b = f[n][o];
			e = f[n][o] = function(l, k) {
				if (!this._createWidget) {
					return new e(l, k);
				}
				if (arguments.length) {
					this._createWidget(l, k);
				}
			};
			f.extend(e, b, {
				version : p.version,
				_proto : f.extend({}, p),
				_childConstructors : []
			});
			c = new d();
			c.options = f.widget.extend({}, c.options);
			f.each(p, function(k, l) {
				if (f.isFunction(l)) {
					p[k] = (function() {
						var r = function() {
							return d.prototype[k].apply(this, arguments);
						}, m = function(q) {
							return d.prototype[k].apply(this, q);
						};
						return function() {
							var q = this._super, v = this._superApply, u;
							this._super = r;
							this._superApply = m;
							u = l.apply(this, arguments);
							this._super = q;
							this._superApply = v;
							return u;
						};
					})();
				}
			});
			e.prototype = f.widget.extend(c, {
				widgetEventPrefix : o
			}, p, {
				constructor : e,
				namespace : n,
				widgetName : o,
				widgetBaseClass : a,
				widgetFullName : a
			});
			if (b) {
				f.each(b._childConstructors, function(l, k) {
					var m = k.prototype;
					f.widget(m.namespace + "." + m.widgetName, e, k._proto);
				});
				delete b._childConstructors;
			} else {
				d._childConstructors.push(e);
			}
			f.widget.bridge(o, e);
		};
		f.widget.extend = function(a) {
			var e = i.call(arguments, 1), b = 0, l = e.length, d, c;
			for (; b < l; b++) {
				for (d in e[b]) {
					c = e[b][d];
					if (e[b].hasOwnProperty(d) && c !== h) {
						if (f.isPlainObject(c)) {
							a[d] = f.isPlainObject(a[d]) ? f.widget.extend({},
									a[d], c) : f.widget.extend({}, c);
						} else {
							a[d] = c;
						}
					}
				}
			}
			return a;
		};
		f.widget.bridge = function(b, c) {
			var a = c.prototype.widgetFullName;
			f.fn[b] = function(e) {
				var n = typeof e === "string", m = i.call(arguments, 1), d = this;
				e = !n && m.length ? f.widget.extend.apply(null, [ e ].concat(m))
						: e;
				if (n) {
					this
							.each(function() {
								var l, k = f.data(this, a);
								if (!k) {
									return f
											.error("cannot call methods on "
													+ b
													+ " prior to initialization; attempted to call method '"
													+ e + "'");
								}
								if (!f.isFunction(k[e]) || e.charAt(0) === "_") {
									return f.error("no such method '" + e
											+ "' for " + b + " widget instance");
								}
								l = k[e].apply(k, m);
								if (l !== k && l !== h) {
									d = l && l.jquery ? d.pushStack(l.get()) : l;
									return false;
								}
							});
				} else {
					this.each(function() {
						var k = f.data(this, a);
						if (k) {
							k.option(e || {})._init();
						} else {
							new c(e, this);
						}
					});
				}
				return d;
			};
		};
		f.Widget = function() {
		};
		f.Widget._childConstructors = [];
		f.Widget.prototype = {
			widgetName : "widget",
			widgetEventPrefix : "",
			defaultElement : "<div>",
			options : {
				disabled : false,
				create : null
			},
			_createWidget : function(b, a) {
				a = f(a || this.defaultElement || this)[0];
				this.element = f(a);
				this.uuid = g++;
				this.eventNamespace = "." + this.widgetName + this.uuid;
				this.options = f.widget.extend({}, this.options, this
						._getCreateOptions(), b);
				this.bindings = f();
				this.hoverable = f();
				this.focusable = f();
				if (a !== this) {
					f.data(a, this.widgetName, this);
					f.data(a, this.widgetFullName, this);
					this._on({
						remove : function(c) {
							if (c.target === a) {
								this.destroy();
							}
						}
					});
					this.document = f(a.style ? a.ownerDocument : a.document || a);
					this.window = f(this.document[0].defaultView
							|| this.document[0].parentWindow);
				}
				this._create();
				this._trigger("create", null, this._getCreateEventData());
				this._init();
			},
			_getCreateOptions : f.noop,
			_getCreateEventData : f.noop,
			_create : f.noop,
			_init : f.noop,
			destroy : function() {
				this._destroy();
				this.element.unbind(this.eventNamespace)
						.removeData(this.widgetName)
						.removeData(this.widgetFullName).removeData(
								f.camelCase(this.widgetFullName));
				this.widget().unbind(this.eventNamespace).removeAttr(
						"aria-disabled").removeClass(
						this.widgetFullName + "-disabled ui-state-disabled");
				this.bindings.unbind(this.eventNamespace);
				this.hoverable.removeClass("ui-state-hover");
				this.focusable.removeClass("ui-state-focus");
			},
			_destroy : f.noop,
			widget : function() {
				return this.element;
			},
			option : function(c, b) {
				var m = c, a, d, e;
				if (arguments.length === 0) {
					return f.widget.extend({}, this.options);
				}
				if (typeof c === "string") {
					m = {};
					a = c.split(".");
					c = a.shift();
					if (a.length) {
						d = m[c] = f.widget.extend({}, this.options[c]);
						for (e = 0; e < a.length - 1; e++) {
							d[a[e]] = d[a[e]] || {};
							d = d[a[e]];
						}
						c = a.pop();
						if (b === h) {
							return d[c] === h ? null : d[c];
						}
						d[c] = b;
					} else {
						if (b === h) {
							return this.options[c] === h ? null : this.options[c];
						}
						m[c] = b;
					}
				}
				this._setOptions(m);
				return this;
			},
			_setOptions : function(b) {
				var a;
				for (a in b) {
					this._setOption(a, b[a]);
				}
				return this;
			},
			_setOption : function(b, a) {
				this.options[b] = a;
				if (b === "disabled") {
					this.widget().toggleClass(
							this.widgetFullName + "-disabled ui-state-disabled",
							!!a).attr("aria-disabled", a);
					this.hoverable.removeClass("ui-state-hover");
					this.focusable.removeClass("ui-state-focus");
				}
				return this;
			},
			enable : function() {
				return this._setOption("disabled", false);
			},
			disable : function() {
				return this._setOption("disabled", true);
			},
			_on : function(a, b) {
				if (!b) {
					b = a;
					a = this.element;
				} else {
					a = f(a);
					this.bindings = this.bindings.add(a);
				}
				var c = this;
				f.each(b,
						function(r, d) {
							function o() {
								if (c.options.disabled === true
										|| f(this).hasClass("ui-state-disabled")) {
									return;
								}
								return (typeof d === "string" ? c[d] : d).apply(c,
										arguments);
							}
							if (typeof d !== "string") {
								o.guid = d.guid = d.guid || o.guid || f.guid++;
							}
							var e = r.match(/^(\w+)\s*(.*)$/), p = e[1]
									+ c.eventNamespace, q = e[2];
							if (q) {
								c.widget().delegate(q, p, o);
							} else {
								a.bind(p, o);
							}
						});
			},
			_off : function(a, b) {
				b = (b || "").split(" ").join(this.eventNamespace + " ")
						+ this.eventNamespace;
				a.unbind(b).undelegate(b);
			},
			_delay : function(a, b) {
				function c() {
					return (typeof a === "string" ? d[a] : a).apply(d, arguments);
				}
				var d = this;
				return setTimeout(c, b || 0);
			},
			_hoverable : function(a) {
				this.hoverable = this.hoverable.add(a);
				this._on(a, {
					mouseenter : function(b) {
						f(b.currentTarget).addClass("ui-state-hover");
					},
					mouseleave : function(b) {
						f(b.currentTarget).removeClass("ui-state-hover");
					}
				});
			},
			_focusable : function(a) {
				this.focusable = this.focusable.add(a);
				this._on(a, {
					focusin : function(b) {
						f(b.currentTarget).addClass("ui-state-focus");
					},
					focusout : function(b) {
						f(b.currentTarget).removeClass("ui-state-focus");
					}
				});
			},
			_trigger : function(l, e, d) {
				var a, b, c = this.options[l];
				d = d || {};
				e = f.Event(e);
				e.type = (l === this.widgetEventPrefix ? l : this.widgetEventPrefix
						+ l).toLowerCase();
				e.target = this.element[0];
				b = e.originalEvent;
				if (b) {
					for (a in b) {
						if (!(a in e)) {
							e[a] = b[a];
						}
					}
				}
				this.element.trigger(e, d);
				return !(f.isFunction(c)
						&& c.apply(this.element[0], [ e ].concat(d)) === false || e
						.isDefaultPrevented());
			}
		};
		f.each({
			show : "fadeIn",
			hide : "fadeOut"
		}, function(a, b) {
			f.Widget.prototype["_" + a] = function(e, m, c) {
				if (typeof m === "string") {
					m = {
						effect : m
					};
				}
				var d, n = !m ? a : m === true || typeof m === "number" ? b
						: m.effect || b;
				m = m || {};
				if (typeof m === "number") {
					m = {
						duration : m
					};
				}
				d = !f.isEmptyObject(m);
				m.complete = c;
				if (m.delay) {
					e.delay(m.delay);
				}
				if (d
						&& f.effects
						&& (f.effects.effect[n] || f.uiBackCompat !== false
								&& f.effects[n])) {
					e[a](m);
				} else {
					if (n !== a && e[n]) {
						e[n](m.duration, m.easing, c);
					} else {
						e.queue(function(k) {
							f(this)[a]();
							if (c) {
								c.call(e[0]);
							}
							k();
						});
					}
				}
			};
		});
		if (f.uiBackCompat !== false) {
			f.Widget.prototype._getCreateOptions = function() {
				return f.metadata
						&& f.metadata.get(this.element[0])[this.widgetName];
			};
		}
	})(jQuery);
	/*
	 * ! Bootstrap Wizard plugin
	 * 
	 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.html
	 * 
	 */
	(function(a, b) {
		a
				.widget(
						"bootstrap.bwizard",
						{
							options : {
								clickableSteps : true,
								autoPlay : false,
								delay : 3000,
								loop : false,
								hideOption : {
									fade : true
								},
								showOption : {
									fade : true,
									duration : 400
								},
								ajaxOptions : null,
								cache : false,
								cookie : null,
								stepHeaderTemplate : "",
								panelTemplate : "",
								spinner : "",
								backBtnText : "上一步",
								nextBtnText : "下一步",
								add : null,
								remove : null,
								activeIndexChanged : null,
								show : null,
								load : null,
								validating : null
							},
							_defaults : {
								stepHeaderTemplate : "<li>/#\{title\}</li>",
								panelTemplate : "<div></div>",
								spinner : "<em>Loading&#8230;</em>"
							},
							_create : function() {
								var c = this;
								c._pageLize(true);
							},
							_init : function() {
								var d = this.options, c = d.disabled;
								if (d.disabledState) {
									this.disable();
									d.disabled = c;
								} else {
									if (d.autoPlay) {
										this.play();
									}
								}
							},
							_setOption : function(c, d) {
								a.Widget.prototype._setOption
										.apply(this, arguments);
								switch (c) {
								case "activeIndex":
									this.show(d);
									break;
								case "navButtons":
									this._createButtons();
									break;
								default:
									this._pageLize();
									break;
								}
							},
							play : function() {
								var d = this.options, c = this, e;
								if (!this.element.data("intId.bwizard")) {
									e = window.setInterval(function() {
										var f = d.activeIndex + 1;
										if (f >= c.panels.length) {
											if (d.loop) {
												f = 0;
											} else {
												c.stop();
												return;
											}
										}
										c.show(f);
									}, d.delay);
									this.element.data("intId.bwizard", e);
								}
							},
							stop : function() {
								var c = this.element.data("intId.bwizard");
								if (c) {
									window.clearInterval(c);
									this.element.removeData("intId.bwizard");
								}
							},
							_normalizeBlindOption : function(d) {
								if (d.blind === b) {
									d.blind = false;
								}
								if (d.fade === b) {
									d.fade = false;
								}
								if (d.duration === b) {
									d.duration = 200;
								}
								if (typeof d.duration === "string") {
									try {
										d.duration = parseInt(d.duration, 10);
									} catch (c) {
										d.duration = 200;
									}
								}
							},
							_createButtons : function() {
								console.log('33333333333333')
								var e = this, h = this.options, d, c = h.backBtnText, g = h.nextBtnText;
								this._removeButtons();
								if (h.navButtons === "none") {
									return;
								}
								if (!this.buttons) {
									d = h.navButtons;
									var f = false;
									this.buttons = a('<ul class="pager"/>');
									this.buttons.addClass("bwizard-buttons");
									if (c != "") {
										this.backBtn = a(
												"<li class='previous'><a id='previous_a' href='#'>"
														+ c + "</a></li>")
												.appendTo(this.buttons).bind({
													click : function() {
														e.back();
														return false;
													}
												}).attr("role", "button");
										var f = true;
									}
									if (g != "") {
										console.log('44444444444444444');
										this.nextBtn = a(
												"<li class='next'><a id='next_a' href='#'>" + g
														+ "</a>").appendTo(
												this.buttons).bind({
											click : function() {
												e.next();
												return false;
											}
										}).attr("role", "button");
										var f = true;
									}
									if (f) {
										this.buttons.appendTo(this.element);
									} else {
										this.buttons = null;
									}
								}
							},
							_removeButtons : function() {
								if (this.buttons) {
									this.buttons.remove();
									this.buttons = b;
								}
							},
							_pageLize : function(f) {
								var d = this, e = this.options, h = /^#.+/;
								var g = false;
								this.list = this.element.children("ol,ul").eq(0);
								var c = this.list.length;
								if (this.list && c === 0) {
									this.list = null;
								}
								if (this.list) {
									if (this.list.get(0).tagName.toLowerCase() === "ol") {
										g = true;
									}
									this.lis = a("li", this.list);
									this.lis
											.each(function(j) {
												if (e.clickableSteps) {
													a(this).click(function(i) {
														i.preventDefault();
														d.show(j);
													});
													a(this)
															.contents()
															.wrap(
																	'<a href="#step'
																			+ (j + 1)
																			+ '" class="hidden-phone"/>');
												} else {
													a(this)
															.contents()
															.wrap(
																	'<span class="hidden-phone"/>');
												}
												a(this).attr("role", "tab");
												a(this).css("z-index",
														d.lis.length - j);
												a(this).prepend(
														'<span class="label">'
																+ (j + 1)
																+ "</span>");
												if (!g) {
													a(this)
															.find(".label")
															.addClass(
																	"visible-phone");
												}
											});
								}
								if (f) {
									this.panels = a("> div", this.element);
									this.panels.each(function(k, l) {
										a(this).attr("id", "step" + (k + 1));
										var j = a(l).attr("src");
										if (j && !h.test(j)) {
											a.data(l, "load.bwizard", j.replace(
													/#.*$/, ""));
										}
									});
									this.element.addClass("bwizard clearfix");
									if (this.list) {
										this.list
												.addClass("bwizard-steps clearfix")
												.attr("role", "tablist");
										if (e.clickableSteps) {
											this.list.addClass("clickable");
										}
									}
									this.container = a("<div/>");
									this.container.addClass("well");
									this.container.append(this.panels);
									this.container.appendTo(this.element);
									this.panels.attr("role", "tabpanel");
									if (e.activeIndex === b) {
										if (typeof e.activeIndex !== "number"
												&& e.cookie) {
											e.activeIndex = parseInt(d._cookie(),
													10);
										}
										if (typeof e.activeIndex !== "number"
												&& this.panels
														.filter(".bwizard-activated").length) {
											e.activeIndex = this.panels
													.index(this.panels
															.filter(".bwizard-activated"));
										}
										e.activeIndex = e.activeIndex
												|| (this.panels.length ? 0 : -1);
									} else {
										if (e.activeIndex === null) {
											e.activeIndex = -1;
										}
									}
									e.activeIndex = ((e.activeIndex >= 0 && this.panels[e.activeIndex]) || e.activeIndex < 0) ? e.activeIndex
											: 0;
									this.panels.addClass("hide").attr(
											"aria-hidden", true);
									if (e.activeIndex >= 0 && this.panels.length) {
										this.panels.eq(e.activeIndex).removeClass(
												"hide").addClass(
												"bwizard-activated").attr(
												"aria-hidden", false);
										this.load(e.activeIndex);
									}
									this._createButtons();
								} else {
									this.panels = a("> div", this.container);
									e.activeIndex = this.panels.index(this.panels
											.filter(".bwizard-activated"));
								}
								this._refreshStep();
								if (e.cookie) {
									this._cookie(e.activeIndex, e.cookie);
								}
								if (e.cache === false) {
									this.panels.removeData("cache.bwizard");
								}
								if (e.showOption === b || e.showOption === null) {
									e.showOption = {};
								}
								this._normalizeBlindOption(e.showOption);
								if (e.hideOption === b || e.hideOption === null) {
									e.hideOption = {};
								}
								this._normalizeBlindOption(e.hideOption);
								this.panels.unbind(".bwizard");
							},
							_refreshStep : function() {
								var c = this.options;
								if (this.lis) {
									this.lis.removeClass("active").attr(
											"aria-selected", false).find(".label")
											.removeClass("badge-inverse");
									if (c.activeIndex >= 0
											&& c.activeIndex <= this.lis.length - 1) {
										if (this.lis) {
											this.lis.eq(c.activeIndex).addClass(
													"active").attr("aria-selected",
													true).find(".label").addClass(
													"badge-inverse");
										}
									}
								}
								if (this.buttons && !c.loop) {
									this.backBtn[c.activeIndex <= 0 ? "addClass"
											: "removeClass"]("disabled").attr(
											"aria-disabled", c.activeIndex === 0);
									/**------------------------------- modify by liuweixing start---------------------------- **/
									//this.nextBtn[c.activeIndex >= this.panels.length - 1 ? "addClass" : "removeClass"]
									//		("disabled").attr("aria-disabled",(c.activeIndex >= this.panels.length - 1));
									if(c.activeIndex >= this.panels.length - 1) {
										$("#next_a").text("完成");
									} else {
										var jt = {xyb : "下一步"}
										$("#next_a").text(jt.xyb);
									}
									/**------------------------------- modify by liuweixing end ---------------------------- **/
								}
							},
							_sanitizeSelector : function(c) {
								return c.replace(/:/g, "\\:");
							},
							_cookie : function() {
								var c = this.cookie
										|| (this.cookie = this.options.cookie.name);
								return a.cookie.apply(null, [ c ].concat(a
										.makeArray(arguments)));
							},
							_ui : function(c) {
								return {
									panel : c,
									index : this.panels.index(c)
								};
							},
							_removeSpinner : function() {
								var c = this.element.data("spinner.bwizard");
								if (c) {
									this.element.removeData("spinner.bwizard");
									c.remove();
								}
							},
							_resetStyle : function(c) {
								c.css({
									display : ""
								});
								if (!a.support.opacity) {
									c[0].style.removeAttribute("filter");
								}
							},
							destroy : function() {
								var c = this.options;
								this.abort();
								this.stop();
								this._removeButtons();
								this.element.unbind(".bwizard").removeClass(
										[ "bwizard", "clearfix" ].join(" "))
										.removeData("bwizard");
								if (this.list) {
									this.list.removeClass("bwizard-steps clearfix")
											.removeAttr("role");
								}
								if (this.lis) {
									this.lis.removeClass("active").removeAttr(
											"role");
									this.lis.each(function() {
										if (a.data(this, "destroy.bwizard")) {
											a(this).remove();
										} else {
											a(this).removeAttr("aria-selected");
										}
									});
								}
								this.panels.each(function() {
									var d = a(this).unbind(".bwizard");
									a.each([ "load", "cache" ], function(e, f) {
										d.removeData(f + ".bwizard");
									});
									if (a.data(this, "destroy.bwizard")) {
										a(this).remove();
									} else {
										a(this).removeClass(
												[ "bwizard-activated", "hide" ]
														.join(" ")).css({
											position : "",
											left : "",
											top : ""
										}).removeAttr("aria-hidden");
									}
								});
								this.container.replaceWith(this.container
										.contents());
								if (c.cookie) {
									this._cookie(null, c.cookie);
								}
								return this;
							},
							add : function(d, g) {
								if (d === b) {
									d = this.panels.length;
								}
								if (g === b) {
									g = "Step " + d;
								}
								var c = this, f = this.options, e = a(
										f.panelTemplate
												|| c._defaults.panelTemplate).data(
										"destroy.bwizard", true), h;
								e.addClass("hide").attr("aria-hidden", true);
								if (d >= this.panels.length) {
									if (this.panels.length > 0) {
										e
												.insertAfter(this.panels[this.panels.length - 1]);
									} else {
										e.appendTo(this.container);
									}
								} else {
									e.insertBefore(this.panels[d]);
								}
								if (this.list && this.lis) {
									h = a((f.stepHeaderTemplate || c._defaults.stepHeaderTemplate)
											.replace(/#\{title\}/g, g));
									h.data("destroy.bwizard", true);
									if (d >= this.lis.length) {
										h.appendTo(this.list);
									} else {
										h.insertBefore(this.lis[d]);
									}
								}
								this._pageLize();
								if (this.panels.length === 1) {
									f.activeIndex = 0;
									h.addClass("ui-priority-primary");
									e.removeClass("hide").addClass(
											"bwizard-activated").attr(
											"aria-hidden", false);
									this.element.queue("bwizard", function() {
										c
												._trigger("show", null, c
														._ui(c.panels[0]));
									});
									this._refreshStep();
									this.load(0);
								}
								this
										._trigger("add", null, this
												._ui(this.panels[d]));
								return this;
							},
							remove : function(c) {
								var e = this.options, d = this.panels.eq(c)
										.remove();
								this.lis.eq(c).remove();
								if (c < e.activeIndex) {
									e.activeIndex--;
								}
								this._pageLize();
								if (d.hasClass("bwizard-activated")
										&& this.panels.length >= 1) {
									this
											.show(c
													+ (c < this.panels.length ? 0
															: -1));
								}
								this._trigger("remove", null, this._ui(d[0]));
								return this;
							},
							_showPanel : function(f) {
								var c = this, g = this.options, e = a(f), d;
								e.addClass("bwizard-activated");
								if ((g.showOption.blind || g.showOption.fade)
										&& g.showOption.duration > 0) {
									d = {
										duration : g.showOption.duration
									};
									if (g.showOption.blind) {
										d.height = "toggle";
									}
									if (g.showOption.fade) {
										d.opacity = "toggle";
									}
									e.hide().removeClass("hide").animate(
											d,
											g.showOption.duration || "normal",
											function() {
												c._resetStyle(e);
												c._trigger("show", null, c
														._ui(e[0]));
												c._removeSpinner();
												e.attr("aria-hidden", false);
												c._trigger("activeIndexChanged",
														null, c._ui(e[0]));
											});
								} else {
									e.removeClass("hide")
											.attr("aria-hidden", false);
									c._trigger("show", null, c._ui(e[0]));
									c._removeSpinner();
									c._trigger("activeIndexChanged", null, c
											._ui(e[0]));
								}
							},
							_hidePanel : function(f) {
								var d = this, g = this.options, c = a(f), e;
								c.removeClass("bwizard-activated");
								if ((g.hideOption.blind || g.hideOption.fade)
										&& g.hideOption.duration > 0) {
									e = {
										duration : g.hideOption.duration
									};
									if (g.hideOption.blind) {
										e.height = "toggle";
									}
									if (g.hideOption.fade) {
										e.opacity = "toggle";
									}
									c.animate(e, g.hideOption.duration || "normal",
											function() {
												c.addClass("hide").attr(
														"aria-hidden", true);
												d._resetStyle(c);
												d.element.dequeue("bwizard");
											});
								} else {
									c.addClass("hide").attr("aria-hidden", true);
									this.element.dequeue("bwizard");
								}
							},
							show : function(f) {
								console.log('show()----------------->>');
								if (f < 0 || f >= this.panels.length) {
									return this;
								}
								if (this.element.queue("bwizard").length > 0) {
									return this;
								}
								
								var d = this, h = this.options, e = a.extend({},
										this._ui(this.panels[h.activeIndex])), c, g;
								e.nextIndex = f;
								e.nextPanel = this.panels[f];
								if (this._trigger("validating", null, e) === false) {
									return this;
								}
								c = this.panels.filter(":not(.hide)");
								g = this.panels.eq(f);
								h.activeIndex = f;
								this.abort();
								if (h.cookie) {
									this._cookie(h.activeIndex, h.cookie);
								}
								this._refreshStep();
								if (g.length) {
									if (c.length) {
										this.element.queue("bwizard", function() {
											d._hidePanel(c);
										});
									}
									this.element.queue("bwizard", function() {
										d._showPanel(g);
									});
									this.load(f);
								} else {
									throw "Bootstrap Wizard: Mismatching fragment identifier.";
								}
								return this;
							},
							next : function() {
								console.log('下一步------------->>');
								var d = this.options, c = d.activeIndex + 1;
								if (d.disabled) {
									return false;
								}
								if (d.loop) {
									c = c % this.panels.length;
								}
								console.log('c='+c);
								console.log('this.panels.length='+this.panels.length);
								if (c < this.panels.length) {
									this.show(c);
									return true;
								}
								return false;
							},
							back : function() {
								console.log('上一步------------->>');
								var d = this.options, c = d.activeIndex - 1;
								if (d.disabled) {
									return false;
								}
								if (d.loop) {
									c = c < 0 ? this.panels.length - 1 : c;
								}
								if (c >= 0) {
									this.show(c);
									return true;
								}
								return false;
							},
							load : function(e) {
								var c = this, h = this.options, f = this.panels
										.eq(e)[0], d = a.data(f, "load.bwizard"), g;
								this.abort();
								if (!d
										|| this.element.queue("bwizard").length !== 0
										&& a.data(f, "cache.bwizard")) {
									this.element.dequeue("bwizard");
									return;
								}
								if (h.spinner) {
									g = this.element.data("spinner.bwizard");
									if (!g) {
										g = a('<div class="modal" id="spinner" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"/>');
										g.html(h.spinner || c._defaults.spinner);
										g.appendTo(document.body);
										this.element.data("spinner.bwizard", g);
										g.modal();
									}
								}
								this.xhr = a.ajax(a.extend({}, h.ajaxOptions, {
									url : d,
									dataType : "html",
									success : function(j, i) {
										a(f).html(j);
										if (h.cache) {
											a.data(f, "cache.bwizard", true);
										}
										c
												._trigger("load", null, c
														._ui(c.panels[e]));
										try {
											if (h.ajaxOptions
													&& h.ajaxOptions.success) {
												h.ajaxOptions.success(j, i);
											}
										} catch (k) {
										}
									},
									error : function(k, i) {
										c
												._trigger("load", null, c
														._ui(c.panels[e]));
										try {
											if (h.ajaxOptions
													&& h.ajaxOptions.error) {
												h.ajaxOptions.error(k, i, e, f);
											}
										} catch (j) {
										}
									}
								}));
								c.element.dequeue("bwizard");
								return this;
							},
							abort : function() {
								this.element.queue([]);
								this.panels.stop(false, true);
								this.element.queue("bwizard", this.element.queue(
										"bwizard").splice(-2, 2));
								if (this.xhr) {
									this.xhr.abort();
									delete this.xhr;
								}
								this._removeSpinner();
								return this;
							},
							url : function(d, c) {
								this.panels.eq(d).removeData("cache.bwizard").data(
										"load.bwizard", c);
								return this;
							},
							count : function() {
								return this.panels.length;
							}
						});
	}(jQuery));
	</script>
</head>
<body>
	<div class="container">
	<div id="zhongxin" style="padding-top: 13px;">
		<div id="wizard" style="text-align:left">
			<ol>
				<li>宿主机</li>
				<li>存储</li>
				<!-- <li>网络</li> -->
			</ol>

			<div>
				<form action="cloudplatform/updateSelectData.do" method="post" name="Form" id="Form">
					<input type="hidden" name="hostmachineIds" id="hostmachineIds" />
					<input type="hidden" name="storageIds" id="storageIds" />
					<input type="hidden" name="dcnIds" id="dcnIds" />
						<table id="hostmachine-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">宿主机名称</th>
									<th class="center">IP地址</th>
									<th class="center">所属集群</th>
									<th class="center">CPU(核)</th>
									<th class="center">内存(G)</th>
									<th class="center">磁盘(G)</th>
									<th class="center">虚拟机数量</th>
									<th class="center">创建时间</th>
									<th class="center">修改时间</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty hmList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${hmList}" var="var" varStatus="vs">
										<tr>
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='hostmachine_ids' value="${var.id}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.name}</td>
											<td class='center'>${var.ip}</td>
											<td class='center'>${var.cluster_name}</td>
											<td class='center'>${var.cpu}</td>
											<td class='center'><fmt:formatNumber value="${var.memory/1024}" type="currency" pattern="#"/></td>
											<td class='center'>${var.localdisk}</td>
											<td class='center'>${var.devicenum}</td>
											<td class='center' style="width: 170px;">${var.gmt_create}</td>
											<td class='center' style="width: 170px;">${var.gmt_modified}</td>
										</tr>
									
									</c:forEach>
									</c:if>
									<c:if test="${QX.cha == 0 }">
										<tr>
											<td colspan="100" class="center">您无权查看</td>
										</tr>
									</c:if>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
					</form>
			</div>
			<div>
				<form method="post">
						<table id="storage-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">名称</th>
									<th class="center">存储类型</th>
									<th class="center">空间总量(G)</th>
									<th class="center">空闲空间(G)</th>
									<th class="center">剩余空间(%)</th>
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty storageList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${storageList}" var="var" varStatus="vs">
										<tr>
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='storage_ids' value="${var.id}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.name}</td>
											<td class='center'>${var.type}</td>
											<td class='center'><fmt:formatNumber value="${var.allspace}" type="currency" pattern="#"/></td>
											<td class='center'><fmt:formatNumber value="${var.freespace}" type="currency" pattern="#"/></td>
											<td class='center'>
											<fmt:formatNumber value="${var.freespace/var.allspace}" type="percent" maxIntegerDigits="2"/>
											</td>
											<td class="center">
												<!-- Todo -->
											</td>
										</tr>
									
									</c:forEach>
									</c:if>
									<c:if test="${QX.cha == 0 }">
										<tr>
											<td colspan="100" class="center">您无权查看</td>
										</tr>
									</c:if>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
					</form>
			</div>
			<!-- 
			<div>
				<form method="post">
						<table id="dcn-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">名称</th>
									<th class="center">所属数据中心</th>
									<th class="center">ip池</th>
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody>
							<c:choose>
								<c:when test="${not empty dcnList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${dcnList}" var="var" varStatus="vs">
										<tr>
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='dcn_ids' value="${var.id}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.name}</td>
											<td class='center'>${var.dc_name}</td>
											<td class='center'>${var.ippool}</td>
											<td class="center">
											</td>
										</tr>
									
									</c:forEach>
									</c:if>
									<c:if test="${QX.cha == 0 }">
										<tr>
											<td colspan="100" class="center">您无权查看</td>
										</tr>
									</c:if>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
					</form>
				</div> -->
			</form>
		</div>
	</div>
	<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
	</div>


	<!-- <script src="js/bwizard.min.js" type="text/javascript"></script> -->
	<script type="text/javascript">
		$(top.hangge());	
	   	$("#wizard").bwizard();
	   	
	   	$(function() {
			//复选框全选控制
			var active_class = 'active';
						
			$('#hostmachine-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function() {
				console.log('hostmachine-table---------->>-------');
				var th_checked = this.checked;//checkbox inside "TH" table header
				$(this).closest('table').find('tbody > tr').each(function(){
					var row = this;
					if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
					else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
				});
			});
			
			$('#storage-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function() {
				console.log('storage-table---------->>-------');
				var th_checked = this.checked;//checkbox inside "TH" table header
				$(this).closest('table').find('tbody > tr').each(function(){
					var row = this;
					if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
					else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
				});
			});
			
			/* $('#dcn-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function() {
				console.log('dcn-table---------->>-------');
				var th_checked = this.checked;//checkbox inside "TH" table header
				$(this).closest('table').find('tbody > tr').each(function(){
					var row = this;
					if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
					else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
				});
			}); */
		});
		
	   	//点击“下一步”或“完成”
		$('#next_a').click(function(){
			var next_a_text = $("#next_a").text();
			if('完成' == next_a_text) {
				var hostmachine_str = '';
				for(var i=0;i < document.getElementsByName('hostmachine_ids').length;i++){
				  if(document.getElementsByName('hostmachine_ids')[i].checked){
				  	if(hostmachine_str=='') hostmachine_str += document.getElementsByName('hostmachine_ids')[i].value;
				  	else hostmachine_str += ',' + document.getElementsByName('hostmachine_ids')[i].value;
				  }
				}
				$("#hostmachineIds").val(hostmachine_str);
				
				var storage_str = '';
				for(var i=0;i < document.getElementsByName('storage_ids').length;i++){
				  if(document.getElementsByName('storage_ids')[i].checked){
				  	if(storage_str=='') storage_str += document.getElementsByName('storage_ids')[i].value;
				  	else storage_str += ',' + document.getElementsByName('storage_ids')[i].value;
				  }
				}
				$("#storageIds").val(storage_str);
				
				var dcn_str = '';
				for(var i=0;i < document.getElementsByName('dcn_ids').length;i++){
				  if(document.getElementsByName('dcn_ids')[i].checked){
				  	if(dcn_str=='') dcn_str += document.getElementsByName('dcn_ids')[i].value;
				  	else dcn_str += ',' + document.getElementsByName('dcn_ids')[i].value;
				  }
				}
				$("#dcnIds").val(dcn_str);
				
				$("#Form").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
			}
		});

	</script>
</body>
</html>
