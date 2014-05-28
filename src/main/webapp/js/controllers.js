var controllers = angular.module('controllers', []);

controllers.controller('IndexCtrl', ['$scope', '$rootScope', '$modal', 'Department', 'Employee',
        function ($scope, $rootScope, $modal, Department, Employee) {
            $rootScope.title = 'Department app';
            $scope.alerts = [];
            $scope.departments = Department.query();
            $scope.totalEmployees = Employee.count();

            $scope.chooseDepartment = function(departmentId, departmentIndex) {
                var departmentToChoose = Department.get({departmentId:departmentId});
                $scope.chosenDepartment = departmentToChoose;
                $scope.chosenDepartmentIndex = departmentIndex;
                $scope.employees = Employee.query({departmentId:departmentId});
            }

            $scope.openNewDepartmentDlg = function() {
                var modalInstance = $modal.open({
                    templateUrl: '/partials/_add_department.html',
                    controller: 'AddNewDepartmentCtrl',
                    scope: $scope
                });

                modalInstance.result.then(
                    function(newDepartment) {
                        $scope.departments.push(newDepartment);
                    },
                    function() {
                    }
                );
            }

            $scope.openEditDepartmentDlg = function(department) {
                var modalInstance = $modal.open({
                    templateUrl: '/partials/_edit_department.html',
                    controller: 'EditDepartmentCtrl',
                    scope: $scope,
                    resolve: {
                        originalDepartment: function() {
                            return department;
                        }
                    }
                });

                modalInstance.result.then(
                    function(editedDepartment) {
                        angular.copy(editedDepartment, department);
                    },
                    function() {
                    }
                );
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
                        $scope.departments[$scope.chosenDepartmentIndex].employeeCount++;
                        $scope.totalEmployees.count++;
                    },
                    function() {
                    }
                );
            }

            $scope.openEditEmployeeDlg = function(employee) {
                var modalInstance = $modal.open({
                    templateUrl: '/partials/_edit_employee.html',
                    controller: 'EditEmployeeCtrl',
                    scope: $scope,
                    resolve: {
                        originalEmployee: function() {
                            return employee;
                        }
                    }
                });

                modalInstance.result.then(
                    function(editedEmployee) {
                        angular.copy(editedEmployee, employee);
                    }
                );
            }

            $scope.deleteDepartment = function(department, departmentIndex) {
                if (department.employeeCount > 0) {
                    $scope.alerts.push({
                        type: 'danger',
                        msg: 'Cannot delete a department with employees'
                    });
                    return;
                }
                var modalInstance = $modal.open({
                    templateUrl: '/partials/_dialog.html',
                    controller: 'DialogCtrl',
                    resolve: {
                        header: function () {
                            return 'Please confirm';
                        },
                        bodyText: function () {
                            return 'Delete department ' + department.name + '?';
                        }
                    }
                })

                modalInstance.result.then(
                    function(){
                        department.$remove({departmentId:department.id});
                        $scope.departments.splice(departmentIndex, 1);
                    },function(){
                    }
                );
            }

            $scope.deleteEmployee = function(employee, employeeIndex) {
                var modalInstance = $modal.open({
                    templateUrl: '/partials/_dialog.html',
                    controller: 'DialogCtrl',
                    resolve: {
                        header: function () {
                            return 'Please confirm';
                        },
                        bodyText: function () {
                            return 'Delete employee ' + employee.firstName + ' ' + employee.lastName + '?';
                        }
                    }
                })

                modalInstance.result.then(
                    function(){
                        employee.$remove({employeeId:employee.id});
                        $scope.employees.splice(employeeIndex, 1);
                        $scope.departments[$scope.chosenDepartmentIndex].employeeCount--;
                    },function(){
                    }
                );
            }

            $scope.closeAlert = function(index) {
                $scope.alerts.splice(index, 1);
            };
        }]
    );

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
            $scope.submitting = true;
            $scope.department.$save(function(data) {
                    $scope.submitting = false;
                    $modalInstance.close(data);
                }, function(data, status) {
                }
            );
        };
    }]);

controllers.controller('EditDepartmentCtrl', ['$scope', '$modalInstance', 'originalDepartment',
    function($scope, $modalInstance, originalDepartment) {
        $scope.department = angular.copy(originalDepartment);

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
                    $modalInstance.dismiss('fail');
                }
            );
        };
    }]);

controllers.controller('EditEmployeeCtrl', ['$scope', '$modalInstance', 'originalEmployee',
    function($scope, $modalInstance, originalEmployee) {
        $scope.employee = angular.copy(originalEmployee);
        $scope.employee.contractBeginDate = new Date(originalEmployee.contractBeginDate);

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

        $scope.toggleDropdown = function($event) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.status.isopen = !$scope.status.isopen;
        };

        $scope.cancel = function() {
            $modalInstance.dismiss('cancel');
        };

        $scope.submit = function() {
            $scope.submitting = true;
            $scope.employee.$update({employeeId:$scope.employee.id}, function(data) {
                $modalInstance.close(data);
            });
        };
    }]);