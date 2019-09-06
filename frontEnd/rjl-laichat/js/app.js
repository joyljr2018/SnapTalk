window.app = {
	
	/**
	 * image server url
	 */ 
	imgServerUrl: 'http://192.168.1.19:88/rjl/',
	//back end server address
	serverUrl: 'http://192.168.1.9:8080',
	/**
	 * check if str is blank
	 * true: not blank
	 * false: blank
	 * @param {Object} str
	 */
	isNotNull: function(str) {
		if (str != null && str != "" && str != undefined) {
			return true;
		} else {
			return false;
		}
	},
	/**
	 * encap msg popup, use h5+UI
	 * @param {Object} msg
	 * @param {Object} type
	 * 
	 */
	showToast: function(msg,type) {
		plus.nativeUI.toast(msg,{icon:"image/" + type +".png", verticalAlign:"center"})
	},
	/**
	 * save user's global object, login information cache'
	 */
	setUserGlobalInfo: function(user) {
		var userInfoStr = JSON.stringify(user);
		plus.storage.setItem("userInfo",userInfoStr);
	},
	/**
	 * obtain user's global object'
	 */
	getUserGlobalInfo: function() {
		
		var userInfoStr = plus.storage.getItem("userInfo");
		return JSON.parse(userInfoStr);
	},
	
	userLogout: function() {
		plus.storage.removeItem("userInfo");
	},
}