controllers.controller('DialogCtrl', ['$scope', '$modalInstance', 'header', 'bodyText',
    function ($scope, $modalInstance, header, bodyText) {
        $scope.dialogHeader = header;
        $scope.dialogBodyText = bodyText;
        $scope.ok = function () {
            $modalInstance.close();
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    }]);

controllers.controller('AddNewDepartmentCtrl', ['$scope', '$modalInstance', 'Department',
    function($scope, $modalInstance, Department) {
        $scope.department = new Department();

        $scope.cancel = function() {
            $modalInstance.dismiss('cancel');
        };

        $scope.submit = function() {
            $scope.department.$save(function(data) {
                    $modalInstance.close(data);
                }, function(data, status) {
                    $modalInstance.dismiss('fail');
                }
            );
        };
    }]);

controllers.controller('EditDepartmentCtrl', ['$scope', '$modalInstance', 'Department', 'originalDepartment',
    function($scope, $modalInstance, Department, originalDepartment) {
        $scope.department = new Department();
        $scope.department.id = originalDepartment.id;
        $scope.department.name = originalDepartment.name;

        $scope.cancel = function() {
            $modalInstance.dismiss('cancel');
        };

        $scope.submit = function() {
            $scope.submitting = true;
            $scope.department.$update({departmentId:$scope.department.id}, function(data) {
                $modalInstance.close(data);
            });
        };
    }]);

controllers.controller('AddNewEmployeeCtrl', ['$scope', '$modalInstance', 'Employee', 'departmentId', 'Municipality',
    function($scope, $modalInstance, Employee, departmentId, Municipality) {
        $scope.employee = new Employee();
        $scope.employee.contractBeginDate = new Date();
        $scope.employee.departmentId = departmentId;
        $scope.municipalities = Municipality.query();
        $scope.municipality = {};

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
            $scope.employee.municipalityId = $scope.municipality.selected.id;
            $scope.employee.$save(function(data) {
                    $modalInstance.close(data);
                }, function(data, status) {
                    $scope.submitting = false;
                    $modalInstance.dismiss('fail');
                }
            );
        };
    }]);

controllers.controller('EditEmployeeCtrl', ['$scope', '$modalInstance', 'originalEmployee', 'originalDepartment', 'Municipality',
    function($scope, $modalInstance, originalEmployee, originalDepartment, Municipality) {
        $scope.employee = angular.copy(originalEmployee);
        $scope.employee.contractBeginDate = new Date(originalEmployee.contractBeginDate);
        $scope.department = originalDepartment;
        $scope.municipalities = Municipality.query();
        $scope.municipality = {};
        $scope.municipality.selected = Municipality.get({municipalityId:originalEmployee.municipalityId});

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
            $scope.employee.municipalityId = $scope.municipality.selected.id;
            $scope.employee.$update({employeeId:$scope.employee.id}, function(data) {
                $modalInstance.close(data);
            });
        };
    }]);