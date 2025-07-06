package com.example.employee_backend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository repository;

    @MockBean
    private MeterRegistry meterRegistry;

    @Test
    void shouldReturnAllEmployees() throws Exception {
        // ✅ Prepare a mock Counter
        Counter counter = mock(Counter.class);

        // ✅ Return mock counter when meterRegistry.counter(...) is called
        when(meterRegistry.counter(eq("custom.employee.api.calls"), any(String[].class))).thenReturn(counter);

        // ✅ Save employees in DB
        repository.save(new Employee("Alice", "alice@example.com", "HR"));
        repository.save(new Employee("Bob", "bob@example.com", "Sales"));

        // ✅ Perform GET request and check response
        var result = mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response).contains("Alice", "Bob");

        // ✅ Optionally verify counter increment was called
        verify(counter).increment();
    }
}
