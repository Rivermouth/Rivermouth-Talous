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
				if (!callback) {
					console.log(resp);
				}
				else {
					callback(resp);
				}
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
			savePath: function(userIdOrUser, collection, parentId) {
				return api.files.path(userIdOrUser, collection, parentId || "root");
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
				if (!file.collection) {
					throw "Collection not set.";
				}
				return new ApiRequestBuilder({
					url: api.files.savePath(userIdOrUser, file.collection, parentId),
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
			files: {
				list: function(user) {
					return api.files.list(user, "!notes");
				},
				save: function(user, file) {
					return api.files.save(user, file);
				}
			}
		}
	};	
	
	function childCrudApi(parentNamePlural, parentIdFn, entityNamePlural, additionalApiEndpoints) {
		var childApi = {
			path: function(entityId) {
				if (!entityId) entityId = "";
				else entityId = "/" + entityId;
				return path("/" + parentNamePlural + "/" + parentIdFn() + "/" + entityNamePlural + entityId);
			},
			get: function(entityId) {
				return new ApiRequestBuilder({
					url: this.path(entityId),
					type: "GET"
				});
			},
			list: function() {
				return new ApiRequestBuilder({
					url: this.path(),
					type: "GET"
				});
			},
			save: function(entity) {				
				return new ApiRequestBuilder({
					url: this.path(entity.id),
					type: "POST",
					data: entity
				});
			}
		};
		
		for (var k in additionalApiEndpoints) {
			if (!additionalApiEndpoints.hasOwnProperty(k)) continue;
			childApi[k] = additionalApiEndpoints[k];
		}
		
		return childApi;
	}
	
	function childFileNoteApi(entityNamePlural) {
		return {
			notes: {
				list: function(entity) {
					return api.files.list("me", entityNamePlural, entity.id);
				},
				save: function(entity, file) {
					file.mimeType = "text/plain";
					file.collection = "notes";
					return api.files.save("me", file, entity.id);
				}
			},
			files: {
				savePath: function(collection, entity) {
					return api.files.path("me", collection, entity.id || "root");
				},
				list: function(entity) {
					return api.files.list("me", "!notes", entity.id);
				},
				save: function(entity, file) {
					return api.files.save("me", file, entity.id);
				}
			}
		}
	}
	
	api.clients = childCrudApi("users", function() { return me.id; }, "clients", childFileNoteApi("clients"));
	
	api.employees = childCrudApi("users", function() { return me.id; }, "employees", childFileNoteApi("employees"));
	
	
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