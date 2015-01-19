(function(win, doc, Holder, Info, api, main, hbel) {
	
	function loginView() {
		var loginData = {
			email: "",
			password: ""
		};
		
		var holder = new Holder(null, {"class": "login"});
		
		var form = new Info(loginData);
		
		holder.body.set(hbel("h1", null, null, "Login"), form);
		
		form.field.email.type = "email";
		form.field.password.type = "password";
		form.save = function() {
			api.auth(this.data).execute(function(resp) {
				main.open("");
			});
		};
		console.log(form);
		
		var loginButton = hbel("button", {
			onclick: function() {
				form.save();
			}
		}, null, "Login");
		
		var openRegisterViewButton = hbel("a", {
			href: "#",
			onclick: function(evt) {
				evt.preventDefault();
				main.open("signup");
				main.switchView();
			}
		}, null, "or sign up");
		
		holder.footer.set([loginButton, openRegisterViewButton]);
		
		main.container.appendChild(holder.render());
	}
	
	bn.loginView = loginView;
	
})(window, document, bn.Holder, bn.Info, bn.api, bn.main, hbel);