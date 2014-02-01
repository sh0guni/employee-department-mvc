var controllers = angular.module('controllers', []);

controllers.controller('IndexCtrl', ['$scope', '$rootScope',
    function ($scope, $rootScope) {
	    $rootScope.title = 'TITLE';
		$scope.h1Text = 'Hello World';
	}]
);