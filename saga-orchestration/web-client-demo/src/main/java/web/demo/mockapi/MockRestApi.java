package web.demo.mockapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.demo.model.Employee;

@RestController
@RequestMapping("/mock")
public class MockRestApi {
	private static Map<Long, Employee> employees;
	private static Long nextID = 1L;
	static {
		employees = new HashMap<>();
		employees.put(nextID, new Employee(nextID++, "John", "Doe", 80000));
		employees.put(nextID, new Employee(nextID++, "Mary", "Jackson", 75000));
		employees.put(nextID, new Employee(nextID++, "Peter", "Grey", 60000));
		employees.put(nextID, new Employee(nextID++, "Max", "Simpson", 67000));
		employees.put(nextID, new Employee(nextID++, "Lisa", "O'Melly", 45000));
		employees.put(nextID, new Employee(nextID++, "Josephine", "Rose", 52000));
	}

	@GetMapping("/{id}")
	public Employee getEmployee(@PathVariable Long id) {
		Optional<Employee> employee = Optional.ofNullable(employees.get(id));
		return employee.orElse(null);
	}

	@GetMapping
	public List<Employee> getAllEmployee() {
		return new ArrayList<>(employees.values());
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	// cosumes : request by user, produces: return by server
	public ResponseEntity<Employee> create(@RequestBody Employee newEmployee) {
		newEmployee.setId(nextID++);
		employees.put(newEmployee.getId(), newEmployee);
		return ResponseEntity.status(HttpStatus.CREATED).header("Location", "/rest/employees/" + newEmployee.getId())
				.body(newEmployee);
	}

	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> update(@PathVariable long id, @RequestBody Employee request) {
		if (!employees.containsKey(id)) {
			return ResponseEntity.notFound().build();
		} else {
			Employee employee = employees.get(id);
			employee.setFirstName(request.getFirstName());
			employee.setLastName(request.getLastName());
			employee.setYearlyIncome(request.getYearlyIncome());
			return ResponseEntity.ok(employee);
		}
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id) {
		Employee removedEmployee = employees.remove(id);
		return removedEmployee != null ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}
}
