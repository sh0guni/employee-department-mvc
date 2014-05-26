var controllers = angular.module('controllers', []);

controllers.controller('IndexCtrl', ['$scope', '$rootScope', '$modal', 'Department', 'Employee',
        function ($scope, $rootScope, $modal, Department, Employee) {
            $rootScope.title = 'Department app';
            $scope.departments = Department.query();
            $scope.departmentName = 'joku';
            //$scope.chosenDepartment = Department.get({departmentId:1});
            //$scope.employees = Department.employees({departmentId:1});
            $scope.chooseDepartment = function(departmentId) {
                var departmentToChoose = Department.get({departmentId:departmentId});
                $scope.chosenDepartment = departmentToChoose;
                $scope.employees = Employee.query({departmentId:departmentId});
            }

            $scope.addDepartment = function(departmentName) {
                var newDepartment = new Department();
                newDepartment.name = departmentName;
                newDepartment.$save();
                $scope.departments.push(newDepartment);
            }

            $scope.openNewEmployeeDlg = function() {
                var modalInstance = $modal.open({
                    templateUrl: '/partials/_add_employee.html',
                    controller: 'AddNewEmployeeCtrl',
                    scope: $scope
                });

                modalInstance.result.then(
                    function(newEmployee) {
                        $scope.employees.push(newEmployee);
                    },
                    function() {
                    }
                );
            }

            $scope.deleteEmployee = function(employee, employeeIndex) {
                employee.$remove({employeeId:employee.id});
                $scope.employees.splice(employeeIndex, 1);
            }
        }]
);

controllers.controller('AddNewEmployeeCtrl', ['$scope', '$modalInstance', 'Employee',
    function($scope, $modalInstance, Employee) {
    $scope.employee = new Employee();
    $scope.employee.contractBeginDate = new Date();
    $scope.employee.departmentId = $scope.chosenDepartment.id;

    $scope.contractBeginDateOptions = {
        'year-format': "'yyyy'",
        'starting-day': 1,
        open: false
    };

    $scope.openContractBeginDate = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.contractBeginDateOptions.open = true;
    };

    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    };

    $scope.submit = function() {
        $scope.submitting = true;
        $scope.employee.$save(function(data) {
                $scope.submitting = false;
                $modalInstance.close(data);
            }, function(data, status) {
                $scope.submitting = false;
                if (status === 400)
                    $scope.badRequest = data;
                else if (status === 409)
                    $scope.badRequest = 'The name is already used.';
            }
        );
    };
}]);