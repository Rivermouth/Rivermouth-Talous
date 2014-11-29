(function(win, doc, Holder, Card, Info, api, bn) {
	
	var IS_AUTHORIZED = false;
	
	var body = doc.body;
	
	var container = doc.getElementById("container");
	
	function open(path) {
		location.hash = "#/" + path;
	}
	
	function switchView() {
		var path = location.hash.substr(1);
		if (path.charAt(0) == "/") path = path.substring(1);
		
		var pathParts = path.split("/");
		
		container.innerHTML = "";
		
		switch (pathParts[0]) {
			case "clients":
				bn.clientView(pathParts[1]);
				break;
			case "login":
				if (IS_AUTHORIZED) {
					open("");
				}
				bn.loginView();
				break;
			case "signup":
			case "register":
				bn.signupView();
				break;
			default:
				bn.mainView();
				break;
		}
	}
	
	function loadDone() {
		win.addEventListener("hashchange", function(evt) {
			switchView();
		}, false);
		
		switchView();
	}
	
	function startApp() {
		api.me().execute(
			function(resp) {
				console.log(resp);
				IS_AUTHORIZED = true;
				loadDone();
			},
			function(errorResp) {
				console.warn(errorResp);
				open("login");
				IS_AUTHORIZED = false;
				loadDone();
			}
		);
	}
	
	bn.main = {
		container: container,
		open: open,
		switchView: switchView,
		startApp: startApp
	};
	
})(window, document, bn.Holder, bn.Card, bn.Info, bn.api, bn);
