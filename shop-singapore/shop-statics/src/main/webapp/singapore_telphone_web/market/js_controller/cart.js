var $cart = {
	push: function(item) {
		var items = this.get_items();
		items.push(item);
		window.localStorage.shopping_cart_items = JSON.stringify(items);
	},
	remove: function(id) {
		var items = this.get_items();
		var mathed_items = items.filter(function(item) {
			return item.id != id;
		});
		window.localStorage.shopping_cart_items = JSON.stringify(mathed_items);
	},

	update_num: function(id, delta) {
		var items = this.get_items();
		items = items.map(function(item) {
			if (item.id == id) {
				item.num += delta;
			}
			return item;
		});
		window.localStorage.shopping_cart_items = JSON.stringify(items);
	},

	is_empty: function() {
		return this.get_items().length == 0;
	},

	get_items: function() {
		var items = [];
		try {
			items = JSON.parse(window.localStorage.shopping_cart_items);
		} catch(e) {
			items = [];
		}

		return items;
	}
}