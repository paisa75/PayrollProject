package salary.payment.service;

import salary.payment.model.dto.EmployeeSalary;

import java.util.List;

public interface PaymentService {
    public void doPayment(List<EmployeeSalary> employeeSalary);
}
