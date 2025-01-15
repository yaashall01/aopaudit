# AOP Audit Logging with Spring AOP

## Overview
This project demonstrates how to use **Aspect-Oriented Programming (AOP)** with **Spring AOP** to implement an **audit logging system**. The purpose is to capture and log user activities across specific application layers, such as service and controller layers, while maintaining clean and modular code.

## What is AOP?
Aspect-Oriented Programming (AOP) is a programming paradigm that allows you to encapsulate cross-cutting concerns (like logging, security, or transaction management) in a modular way. Instead of scattering logging logic throughout your codebase, AOP centralizes it into reusable units called **aspects**.

### Key Concepts of AOP
- **Aspect**: A module containing cross-cutting logic, such as logging or security.
- **Advice**: The action performed by an aspect. Examples include `Before`, `After`, and `Around` advices.
- **Join Point**: A specific point during the execution of a program, such as the invocation of a method.
- **Pointcut**: A predicate that matches join points. It defines where advice should be applied.
- **Weaving**: The process of linking aspects with the application code.

## What is Spring AOP?
Spring AOP is the implementation of AOP provided by the Spring Framework. It uses proxies to implement aspects and supports various types of advices, such as `@Before`, `@After`, and `@Around`.

### How Spring AOP Works in This Project
In this project, Spring AOP is used to:
- Intercept method executions in the service layer.
- Capture details about the method call (e.g., method name, arguments, user IP address).
- Save these details to the database for audit purposes.

We achieve this by:
1. Defining a custom annotation (`@Loggable`) to mark methods for auditing.
2. Creating an aspect (`AuditLoggingAspect`) that intercepts methods annotated with `@Loggable`.
3. Using Hibernate and JPA to store audit logs in the database.

## Project Features
- **CRUD Operations**: Manage users with RESTful APIs.
- **Audit Logging**: Log user activities, including method names, arguments, return values, and IP addresses.
- **Dynamic Annotations**: Use `@Loggable` to specify which methods to audit.

## Steps to Implement Audit Logging with Spring AOP

### 1. Define a Custom Annotation
Create a custom annotation, `@Loggable`, to mark methods for logging:
```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable {
    String action() default "DEFAULT_ACTION";
}
```

### 2. Create the Aspect
Write an aspect to intercept methods annotated with `@Loggable`:
```java
@Aspect
@Component
public class AuditLoggingAspect {

    @Pointcut("@annotation(com.example.aoplogging.annotation.Loggable)")
    public void loggableMethods() {}

    @AfterReturning(pointcut = "loggableMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        // Capture and log details
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Loggable loggable = signature.getMethod().getAnnotation(Loggable.class);
        
        String action = loggable.action();
        String methodName = signature.getName();
        String arguments = Arrays.toString(joinPoint.getArgs());
        
        AuditLog auditLog = new AuditLog();
        auditLog.setAction(action);
        auditLog.setMethodName(methodName);
        auditLog.setArguments(arguments);
        auditLogRepository.save(auditLog);
    }
}
```

### 3. Create the Entity and Repository for Audit Logs
Define an entity to store audit logs:
```java
@Entity
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String action;
    private String methodName;
    private String arguments;
}
```

Create a repository to manage `AuditLog` persistence:
```java
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {}
```

### 4. Annotate Methods with `@Loggable`
Use `@Loggable` to mark methods for logging in the service layer:
```java
@Service
public class UserService {

    @Loggable(action = "CREATE_USER")
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Loggable(action = "DELETE_USER")
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
```

### 5. Verify Logging
- Start the application.
- Use REST API endpoints to perform actions.
- Check the `audit_log` table in the database for logged actions.

## How to Run the Project
1. Clone the repository.
2. Build the project:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. Access the H2 database console at:
   ```
   http://localhost:8080/h2-console
   ```
5. Query the `audit_log` table:
   ```sql
   SELECT * FROM audit_log;
   ```

## Example Endpoints
- `POST /users`: Create a new user.
- `DELETE /users/{id}`: Delete a user.

## Conclusion
This project demonstrates how to implement AOP-based audit logging using Spring AOP. By separating cross-cutting concerns like logging, we achieve cleaner and more maintainable code. Let me know if you have any questions or suggestions!

