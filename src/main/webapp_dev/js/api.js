(function(bn) {
	
	var me = null;
	var baseUrl = "http://localhost:8080";
	
	function isNumber(obj) {
		return (typeof obj === "number");
	}
	
	function getUserId(user) {
		if (user == "me") {
			return me.id;
		}
		else {
			return user.id;
		}
	}
	
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
		files: {
			/**
			 * @param userIdOrUser :(user:obj|userId:num|"me")
			 * @param collectionOrFileId :(collection:str|fileId:num), if null, "files"
			 * @param parentId :num
			 */
			path: function(userIdOrUser, collectionOrFileId, parentId) {
				var userId;
				
				if (userIdOrUser == "me") userId = me.id;
				else if (userIdOrUser.id) userId = userIdOrUser.id;
				else userId = userIdOrUser;
				
				if (!collectionOrFileId) {
					collectionOrFileId = "files";
				}
				if (!parentId) {
					parentId = "";
				}
				
				return path("/files/" + userId + "/" + collectionOrFileId + "/" + parentId);
			},
			list: function() {
				return new ApiRequestBuilder({
					url: api.files.path(arguments[0], arguments[1], arguments[2] || "all"),
					type: "GET"
				});
			},
			get: function(user, fileId) {
				return new ApiRequestBuilder({
					url: api.files.path(user, fileId),
					type: "GET"
				});
			},
			save: function(userIdOrUser, file, parentId) {
				return new ApiRequestBuilder({
					url: api.files.path(userIdOrUser, file.collection, parentId || "root"),
					type: "POST",
					data: file
				});
			}
		},
		users: {
			path: function(userId) {
				if (!userId) userId = "";
				else userId = "/" + userId;
				return path("/users" + userId);
			},
			get: function(userId) {
				return new ApiRequestBuilder({
					url: api.users.path(userId),
					type: "GET"
				});
			},
			save: function(user) {				
				return new ApiRequestBuilder({
					url: api.users.path(user.id),
					type: "POST",
					data: user
				});
			},
			notes: {
				list: function(user) {
					return api.files.list(user, "notes");
				},
				save: function(user, note) {
					note.mimeType = "text/plain";
					note.collection = "notes";
					return api.files.save(user, note);
				}
			},
			bills: {
				list: function(user) {
					return api.files.list(user, "bills");
				},
				save: function(user, bill) {
					bill.mimeType = "application/rlk";
					bill.collection = "bills";
					return api.files.save(user, bill);
				}
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
				return new ApiRequestBuilder({
					url: api.clients.path(client.id),
					type: "POST",
					data: client
				});
			},
			notes: {
				list: function(client) {
					return api.files.list("me", "notes", client.id);
				},
				save: function(client, note) {
					note.mimeType = "text/plain";
					note.collection = "notes";
					return api.files.save("me", note, client.id);
				}
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