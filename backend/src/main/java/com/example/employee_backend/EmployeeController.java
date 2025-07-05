package com.example.employee_backend;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final EmployeeRepository repo;
    private final MeterRegistry meterRegistry;

    public EmployeeController(EmployeeRepository repo, MeterRegistry meterRegistry) {
        this.repo = repo;
        this.meterRegistry = meterRegistry;
    }

    // GET all employees
    @GetMapping
    public List<Employee> getAll() {
        // ✅ Increment custom metric
        meterRegistry.counter("custom.employee.api.calls").increment();

        return repo.findAll();
    }

    // POST new employee (check for duplicate by email)
    @PostMapping
    public Employee add(@RequestBody Employee emp) {
        return repo.findByEmail(emp.getEmail()).orElseGet(() -> repo.save(emp));
    }

    // DELETE employee by ID
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
