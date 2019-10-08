package yongs.temp.web;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import yongs.temp.dao.EmployeeRepository;
import yongs.temp.model.Employee;

public class EmployeeHandler {
	private Logger logger = LoggerFactory.getLogger(EmployeeHandler.class);
	
	EmployeeRepository repository;
	
	public EmployeeHandler(EmployeeRepository repo) {
		repository = repo;
	}
	
	public Mono<ServerResponse> findById(ServerRequest request) {
		logger.debug("flex-F-employee|EmployeeHandler|findById()");
		int id = Integer.valueOf(request.pathVariable("id"));
		// Empty일때 404 Not Found를 보내고 싶은경우
		Mono<ServerResponse> notFound = ServerResponse.notFound().build();		
		Mono<Employee> employeeMono = repository.findById(id);
		
		return employeeMono.flatMap(employee -> ServerResponse.ok()
				.contentType(APPLICATION_JSON)
				.body(BodyInserters.fromObject(employee)))
				.switchIfEmpty(notFound);
	}

	public Mono<ServerResponse> findByName(ServerRequest request) {
		logger.debug("flex-F-employee|EmployeeHandler|findByName()");
		String name = String.valueOf(request.pathVariable("name"));

		Flux<Employee> employeeFlux = repository.findByName(name);
		Mono<List<Employee>> employeeMono = employeeFlux.collectList();
		
		return employeeMono.flatMap(employee -> ServerResponse.ok()
				.contentType(APPLICATION_JSON)
				.body(BodyInserters.fromObject(employee)));
	}
	
	public Mono<ServerResponse> findAll(ServerRequest request) {
		logger.debug("flex-F-employee|EmployeeHandler|findAll()");
		Flux<Employee> employeeFlux = repository.findAll();
		return ServerResponse.ok().contentType(APPLICATION_JSON).body(employeeFlux, Employee.class);	
	}

    public Mono<ServerResponse> create(ServerRequest request) {
		logger.debug("flex-F-employee|EmployeeHandler|create()");		
		// Employee Mono를 최종적으로 리턴하지 않으면 subscribe()가 수행되지 않는다.
        Mono<Employee> employeeMono = request.bodyToMono(Employee.class).flatMap(repository::save);
		return ServerResponse.ok().contentType(APPLICATION_JSON).body(employeeMono, Employee.class);
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
    	logger.debug("flex-F-employee|EmployeeHandler|delete()");
        String id = request.pathVariable("id");
        Mono<Void> voidMono = repository.deleteById(new Integer(id));
		// Void Mono를 최종적으로 리턴하지 않으면 subscribe()가 수행되지 않는다.        
        return voidMono.flatMap(employee -> ServerResponse.ok()
				.body(BodyInserters.fromObject(employee)));
    }
    
	public Mono<ServerResponse> hello(ServerRequest request) {
		logger.debug("flex-F-employee|EmployeeHandler|hello()");
        return ServerResponse.ok().body(Mono.just("Hello, WebFlux"), String.class);
	}
}
