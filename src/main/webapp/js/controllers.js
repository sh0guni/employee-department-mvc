var controllers = angular.module('controllers', []);

controllers.controller('IndexCtrl', ['$scope', '$rootScope', '$http',
    function ($scope, $rootScope, $http) {
	    $rootScope.title = 'TITLE';
		$scope.h1Text = 'Hello World';
        $http.get('/departments').success(function(data) {
            $scope.departments = data;
        });
	}]
);