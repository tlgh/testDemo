/**
 * Created by boil on 2015-12-14.
 */
define(['main'], function (App) {
    App.Utils.ArrayUtil = function () {

    };

    
    App.Utils.ArrayUtil.prototype = {
    	/**
	     * @param conditions
	     *    like this [{key:id,value:1}]
	     * @type {{findBy: Function}}
	     */
        findBy: function (array, conditions) {
            var list = [];
            var length = array.length;
            for (var i = 0; i < length; i++) {
                var data = array[i];
                if (checkConditions(data, conditions)) {
                    list.push(data);
                }
            }
            return list;
        },
        /**
         * 
         * @param {Array} array 
         * @param {Object} params 
         * default 
         * { 
         * 	parentKey: null,
         * 	childrenKey: 'children' 
         * }
         * @param {Object} parent
         */
        toTree: function(array, params, parent) {
			params = $.extend({
				parentKey: null,
				childrenKey: 'children'
			}, params);
			var list = this.findBy(array, [{
				key: params.parentKey,
				value: parent
			}]);
			for (var i = 0; i < list.length; i++) {
				list[i][params.childrenKey] = this.toTree(array, params, list[i].id);
			}
			return list;
		},
		/**
         * 
         * @param {Array} array 
         * @param {Object} params 
         * default 
         * { 
         * 	childrenKey: 'children' 
         * }
         * @param {Function} handle function(child, level){}
         * 
         */
		traverseTree: function(tree, handle, level, params) {
			level = level ? level : 0;
			params = $.extend({
				childrenKey: 'children'
			}, params);
			if (!tree instanceof Array) {
				tree = [tree];
			}
			for (var i = 0; i < tree.length; i++) {
				handle(tree[i], level);
				this.traverseTree(tree[i][params.childrenKey], handle, (level + 1), params);
			}
		}
    };
    function checkConditions(data, conditions) {
        for (var i = 0; i < conditions.length; i++) {
            var condition = conditions[i];
            var value = getValueFromCondition(data, condition.key);
            if (condition.value != value) {
                return false;
            }
        }
        return true;
    }

    function getValueFromCondition(data, key) {
        var keys = key.split('.');
        var result = data;
        for (var i = 0; i < keys.length; i++) {
        	if (result) {
				result = result[keys[i]];
			}
        }
        return result;
    }

    App.Utils.ArrayUtil.prototype.constructor = App.Utils.ArrayUtil;
    return new App.Utils.ArrayUtil();
});
