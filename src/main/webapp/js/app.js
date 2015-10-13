var app = angular.module('project',[
    'ngRoute',
    'controllers',
    'services',
    'ngSanitize',
    'ui.bootstrap',
    'ui.select'
]);

app.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/', {
                controller:'IndexCtrl',
                templateUrl:'partials/index.html'
            });/*.
            otherwise({
                redirectTo: '/'
            });*/
}]);

app.config(function(uiSelectConfig) {
    uiSelectConfig.theme = 'bootstrap';
});