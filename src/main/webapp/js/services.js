var services = angular.module('services', ['ngResource']);

services.factory('Department', ['$resource',
    function($resource) {
        return $resource('/departments/:departmentId', {}, {
        });
    }]);

services.factory('Employee', ['$resource',
    function($resource) {
        return $resource('/employees/:employeeId', {}, {
            query: {method:'GET', params:{departmentId:'@id'}, url:'/departments/:departmentId/employees', isArray:true}
        });
    }]);