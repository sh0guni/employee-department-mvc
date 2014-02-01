var app = angular.module('project', ['ngRoute', 'controllers', 'ui.bootstrap']);

app.config(['$routeProvider', function($routeProvider) {
   $routeProvider.when('/', {controller:'IndexCtrl', templateUrl:'partials/index.html'}).
   otherwise({redirectTo: '/'});
}]);