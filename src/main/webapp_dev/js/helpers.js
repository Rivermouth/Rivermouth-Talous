(function(bn) {
	
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
		if (!params || !params.url || !(params.success && success)) throw "Invalid params";
		
		params.type = params.type || "GET";
		if (success) params.success = success;
		
		var r = new XMLHttpRequest();
		r.open(params.type, params.url, true);
		r.onreadystatechange = function () {
			if (r.readyState != 4 || r.status != 200) return;
			if (success) {
				if (parseJson) success(JSON.parse(r.responseText));
				else success(r.responseText);
			}
		};
		if (params.data) r.send(params.data);
	};
	
	bn.ajaxJSON = function(params, success) {
		bn.ajax(params, success, true);
	};
	
})(window.bn);