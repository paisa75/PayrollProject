package salary.payment.service;

import salary.payment.model.dto.EmployeeSalary;

import java.util.List;
<<<<<<< HEAD

public interface PaymentService {
    public void doPayment(List<EmployeeSalary> employeeSalary);
=======
import java.util.concurrent.ExecutionException;

public interface PaymentService {
    public void doPayment(List<EmployeeSalary> employeeSalary) throws ExecutionException, InterruptedException;
>>>>>>> origin/main
}
