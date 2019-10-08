package yongs.temp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import yongs.temp.dao.EmployeeRepository;

@Configuration
public class EmployeeRouter {
	@Autowired
	EmployeeRepository repository;
	
	@Bean
    public RouterFunction<ServerResponse> EmployeeRoute() {
		EmployeeHandler employeeHandler = new EmployeeHandler(repository);
		return RouterFunctions
				.route(RequestPredicates.GET("/all"), employeeHandler::findAll)
				.andRoute(RequestPredicates.GET("/id/{id}"), employeeHandler::findById)
				.andRoute(RequestPredicates.GET("/name/{name}"), employeeHandler::findByName)
				.andRoute(RequestPredicates.POST("/create"), employeeHandler::create)
				.andRoute(RequestPredicates.DELETE("/delete/{id}"), employeeHandler::delete)
				.andRoute(RequestPredicates.path("/"), employeeHandler::hello);
	}
}
