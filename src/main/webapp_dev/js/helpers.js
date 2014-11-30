(function(doc, bn) {
	
	function AjaxBuilder() {
		this.url = null;
		this.type = "GET";
		this.xhr = new XMLHttpRequest();
	}
	
	AjaxBuilder.cache = {};
	
	AjaxBuilder.prototype = {
		
		setUrl: function(url) {
			this.url = url;
			return this;
		},
		
		setType: function(type) {
			this.type = type.toUpperCase();
			return this;
		},
		
		setData: function(data) {
			this.data = data;
			return this;
		},
		
		setContentType: function(value) {
			if (this.type == "GET") {
				console.warn("Cannot set request header 'Content-Type' for request type 'GET'.");
				return this;
			}
			this.xhr.setRequestHeader("Content-Type", value);
			return this;
		},
		
		setParseJson: function(bool) {
			this.parseJson = bool;
			return this;
		},
		
		setEnableCache: function(bool) {
			this.enableCache = (bool ? true : false);
			return this;
		},
		
		setSuccessCallback: function(callback) {
			this.success = callback;
			return this;
		},
		
		setErrorCallback: function(callback) {
			this.error = callback;
			return this;
		},
		
		success: function() {},
		error: function() {},
		
		open: function() {
			if (!this.url) throw "No destination url defined.";
			this.xhr.open(this.type, this.url, true);
		},
		
		getCacheKey: function() {
			return  "bn-ajax-cached-" + this.url;
		},
		
		/**
		 * Try load from cache.
		 *
		 * @return true if data exists in cahce, false otherwise
		 */
		loadFromCache: function() {			
			var data = AjaxBuilder.cache[this.getCacheKey()]; // || localStorage.getItem(this.getCacheKey());
			if (data == null) return false;
			
			var resp = (this.parseJson ? JSON.parse(data) : data);
			this.success(resp);
			return true;
		},
		
		saveToCache: function(data) {
			AjaxBuilder.cache[this.getCacheKey()] = data;
//			localStorage.setItem(this.getCacheKey(), data);
		},
		
		send: function() {
			if (this.enableCache && this.loadFromCache()) {
				return;
			}
			
			var self = this;
			var r = this.xhr;
			
			this.xhr.onreadystatechange = function() {
				if (r.readyState != 4) return;

				var statusCode = r.status;
				var resp = (self.parseJson ? JSON.parse(r.responseText) : r.responseText);
				
				if (self.enableCache) {
					self.saveToCache(r.responseText);
				}

				if (statusCode >= 200 && statusCode < 300) {
					self.success(resp);
				}
				else {
					self.error(resp);
				}
			};
			
			if (this.data) this.xhr.send(this.data);
			else this.xhr.send();
		}
		
	};
	
	/**
	 * params: 
	 *   url: string, url
	 *   type: string, get/post/put/...
	 *   data: string, data to send along post request.
	 *         in order to send Json data you must stringify it first
	 *   success: function(data), callback function
	 *
	 * // optional args
	 * success: function(data), callback function
	 * parseJson: boolean, parse return value json to object?
	 */
	bn.ajax = function(params, success, parseJson) {
		if (!params) params = {};
		if (success) params.success = success;
		
		if (!parseJson) parseJson = false;
		else parseJson = true;
		
		var ajax = new AjaxBuilder().setUrl(params.url).setParseJson(parseJson).setEnableCache(params.cache);
		ajax.xhr.withCredentials = true;
		
		if (params.type) ajax.setType(params.type);
		if (params.data) ajax.setData(params.data);
		
		if (params.success) ajax.setSuccessCallback(params.success);
		if (params.error) ajax.setErrorCallback(params.error);
		
		ajax.open();
		
		if (params.contentType) ajax.setContentType(params.contentType);
		
		ajax.send();
	};
	
	bn.ajaxJSON = function(params, success) {
		bn.ajax(params, success, true);
	};
	
	bn.isObjectFieldsBlank = function isObjectFieldsBlankFn(obj) {
		for (var k in obj) {
			if (obj[k] !== null && obj[k].length > 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Clone object
	 * @param obj, object to clone
	 * @return clone of given object
	 */
	function clone(obj) {
		var copy;

		// Handle the 3 simple types, and null or undefined
		if (null == obj || "object" != typeof obj) return obj;

		// Handle Date
		if (obj instanceof Date) {
			copy = new Date();
			copy.setTime(obj.getTime());
			return copy;
		}

		// Handle Array
		if (obj instanceof Array) {
			copy = [];
			for (var i = 0, len = obj.length; i < len; i++) {
				copy[i] = clone(obj[i]);
			}
			return copy;
		}

		// Handle Object
		if (obj instanceof Object) {
			copy = {};
			for (var attr in obj) {
				if (obj.hasOwnProperty(attr)) copy[attr] = clone(obj[attr]);
			}
			return copy;
		}

		throw new Error("Unable to copy obj! Its type isn't supported.");
	}
	bn.clone = clone;
	
	bn.openFileFromDisk = function(accept, callback, readAsFn) {
		if (!callback) return false;
		
		if (!readAsFn) readAsFn = "readAsText";
		
		var inp = doc.createElement("input");
		inp.type = "file";
		inp.setAttribute("accept", accept);
		
		inp.onchange = function(evt) {
			var file = evt.target.files[0];
			if (!file) return;
			
			var reader = new FileReader();
			reader.onload = function(ev) {
				callback(file.name.replace(".rlk", ""), ev.target.result);
			};
			reader[readAsFn](file);
			
			inp.remove();
		};
		
		doc.body.appendChild(inp);
		inp.click();
	};
	
	bn.saveFileToDisk = function(fileName, mimeType, data) {
		var a         = doc.createElement("a");
		a.href        = "data:" + mimeType + "," + data;
		a.target      = "_blank";
		a.download    = fileName;

		doc.body.appendChild(a);
		a.click();
		
		a.remove();
	};
	
})(document, window.bn);