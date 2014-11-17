(function(bn) {
	
	var me = null;
	var baseUrl = "http://localhost:8080";
	
	function path(url) {
		return baseUrl + url;
	}
	
	function objectToFormSerialized(obj, prefix) {
		var buffer = null;
		for (var k in obj) {
			var val = obj[k];
			
			if (buffer === null) buffer = "";
			else buffer += "&";
			
			if (typeof val == "object") {
				buffer += objectToFormSerialized(val, k);
			}
			else {
				if (prefix) buffer += prefix + ".";
				buffer += k + "=" + obj[k];
			}
		}
		return buffer;
	}
	
	function ApiRequestBuilder(params, proxyCallback) {
		this.params = params;
		this.proxyCallback = proxyCallback;
	}
	
	ApiRequestBuilder.prototype = {
		
		done: function(resp, callback) {
			if (this.proxyCallback) {
				this.proxyCallback(resp, callback);
			}
			else {
				callback(resp);
			}
		},
		
		execute: function(callback, errorCallback) {			
			var self = this;
			
			var options = {
				url: this.params.url,
				type: this.params.type,
				contentType: "application/x-www-form-urlencoded;charset=UTF-8", //"application/json;charset=UTF-8",
				error: function(resp) {
					self.done(resp, errorCallback);
				},
				cache: this.params.cache
			};
			
			if (this.params.data) options.data = objectToFormSerialized(this.params.data);
			
			bn.ajaxJSON(options, function(resp) {
				self.done(resp, callback);
			});
		}
		
	};
	
	var api = {
		auth: function(data) {
			return new ApiRequestBuilder({
				url: path("/auth"),
				type: "POST",
				data: data
			});
		},
		register: function(data) {
			return new ApiRequestBuilder({
				url: path("/register"),
				type: "POST",
				data: data
			});
		},
		me: function() {			
			return new ApiRequestBuilder({
				url: path("/auth"),
				type: "GET",
				cache: true
			}, function(resp, callback) {
				me = resp.data;
				callback(resp);
			});
		},
		users: {
			get: function(userId) {
				return new ApiRequestBuilder({
					url: path("/users/" + userId),
					type: "GET"
				});
			},
			save: function(user) {
				var p, t;
				
				if (!user.id) {
					p = path("/users");
					t = "PUT";
				}
				else {
					p = path("/users/" + user.id);
					t = "POST";
				}
				
				return new ApiRequestBuilder({
					url: p,
					type: t,
					data: user
				});
			}
		},
		clients: {
			path: function(clientId) {
				if (!clientId) clientId = "";
				else clientId = "/" + clientId;
				return path("/users/" + me.id + "/clients" + clientId);
			},
			get: function(clientId) {
				return new ApiRequestBuilder({
					url: api.clients.path(clientId),
					type: "GET"
				});
			},
			list: function() {
				return new ApiRequestBuilder({
					url: api.clients.path(),
					type: "GET"
				});
			},
			save: function(client) {
				var p, t;
				
				if (!client.id) {
					p = api.clients.path();
					t = "POST";
				}
				else {
					p = api.clients.path(client.id);
					t = "POST";
				}
				
				return new ApiRequestBuilder({
					url: p,
					type: t,
					data: client
				});
			}
		}
	};
	
	bn.api = api;
	
	// Example data
	bn.user = {
		"kind": "user",
		"data": {
			"id": 1,
			"name": {
				"firstName": "Karri",
				"lastName": "Rasinmäki"
			},
			"role": null,
			"address": null,
			"email": "rasinmaki@rivermouth.fi",
			"tel": null,
			"company": null,
			"clients": [
				{
					"id": 6,
					"name": "Herman & Noice Oy",
					"vatNumber": "FI23128124",
					"address": {
						"street": "Vilhovuorenkatu 7-9",
						"zip": "00500",
						"city": "Helsinki",
						"country": "Finland"
					},
					"projects": [],
					"notes": []
				}
			],
			"notes": [
				{
					"id": 2,
					"title": "Muista laskutus",
					"content": "Tee viimestään 15. päivä"
				},
				{
					"id": 3,
					"title": "Muista laskutus",
					"content": "Tee viimestään 15. päivä"
				},
				{
					"id": 4,
					"title": "Muista laskutus",
					"content": "Tee viimestään 15. päivä"
				},
				{
					"id": 5,
					"title": "Muista laskutus",
					"content": "Tee viimestään 15. päivä"
				}
			],
			"googleId": null,
			"facebookId": null
		}
	};
	
})(window.bn);