var services = angular.module('services', ['ngResource']);

services.factory('Department', ['$resource',
    function($resource) {
        return $resource('/departments/:departmentId', {}, {
            update: {method:'PUT', params:{departmentId:'@id'}}
        });
    }]);

services.factory('Employee', ['$resource',
    function($resource) {
        return $resource('/employees/:employeeId', {}, {
            query: {method:'GET', params:{departmentId:'@id'}, url:'/departments/:departmentId/employees', isArray:true},
            update: {method:'PUT', params:{employeeId:'@id'}},
            count: {method:'GET', url:'/employees/count'}
        });
    }]);

services.factory('Municipality', ['$resource',
    function($resource) {
        return $resource('/municipalities/:municipalityId', {}, {
        });
    }]);