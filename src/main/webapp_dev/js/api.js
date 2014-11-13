(function(bn) {
	
	var baseUrl = "http://localhost:8080";
	
	function path(url) {
		return baseUrl + url;
	}
	
	function ApiRequestBuilder(params) {
		this.params = params;
	}
	
	ApiRequestBuilder.prototype = {
		
		execute: function(callback) {
			bn.ajaxJSON({
				url: this.params.url,
				type: this.params.type
			}, callback);
		}
		
	};
	
	bn.api = {
		users: {
			get: function(userId) {
				return new ApiRequestBuilder({
					url: path("/users/" + userId),
					type: "GET"
				});
			},
			save: function(user) {
				return new ApiRequestBuilder({
					url: path("/users"),
					type: (user.id === null ? "PUT" : "POST"),
					data: user
				});
			}
		}
	};
	
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