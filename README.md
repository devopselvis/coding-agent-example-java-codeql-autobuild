# coding-agent-example-java-codeql-autobuild 

A demonstration Java application with intentional security vulnerabilities for CodeQL scanning.

## Overview

This repository contains a simple Java application built with Maven that includes several common security vulnerabilities designed to be detected by GitHub's CodeQL static analysis tool.

## Application Structure

- **Main Application**: `com.example.app.VulnerableApplication` - Entry point that demonstrates various vulnerabilities
- **Database Layer**: `com.example.database.UserDatabase` - Contains SQL injection vulnerabilities
- **Security Utils**: `com.example.security.CryptoUtils` - Contains weak cryptographic implementations
- **Web/File Handling**: `com.example.web.FileController` - Contains path traversal and command injection vulnerabilities
- **LDAP Authentication**: `com.example.ldap.LdapAuth` - Contains LDAP injection vulnerabilities

## Intentional Vulnerabilities

This application contains the following types of security vulnerabilities:

1. **SQL Injection** - Direct string concatenation in SQL queries
2. **Command Injection** - Unsanitized user input passed to system commands
3. **Path Traversal** - File operations without path validation
4. **LDAP Injection** - Unescaped user input in LDAP filters
5. **Weak Cryptography** - Use of MD5 and weak random number generation
6. **Hard-coded Secrets** - Embedded credentials and encryption keys
7. **Vulnerable Dependencies** - Uses `commons-collections:3.2.1` which has known deserialization vulnerabilities (CVE-2015-7501). This dependency appears in multiple paths in the dependency graph:
   - As a direct dependency
   - As a transitive dependency through `commons-beanutils:1.9.2`
   - As a transitive dependency through `commons-digester:2.1` → `commons-beanutils:1.8.3`

## CodeQL Analysis

The repository includes a GitHub Actions workflow (`.github/workflows/codeql-analysis.yml`) that:

- Runs CodeQL analysis on push and pull requests
- Uses the autobuild functionality for Java
- Includes security-and-quality queries for comprehensive coverage
- Runs weekly scheduled scans

## Building and Running

```bash
# Compile the application
mvn clean compile

# Run tests
mvn test

# Run the application (demonstrates vulnerabilities)
mvn exec:java -Dexec.mainClass="com.example.app.VulnerableApplication"

# View dependency tree to see multiple paths to commons-collections
mvn dependency:tree -Dverbose
```

### Viewing Multiple Dependency Paths

To see how `commons-collections:3.2.1` appears in multiple paths in the dependency graph, run:

```bash
mvn dependency:tree -Dverbose | grep -E "commons-collections|commons-beanutils|commons-digester"
```

Expected output shows `commons-collections:3.2.1` appearing as:
- A direct dependency
- A transitive dependency through `commons-beanutils` (marked as "omitted for duplicate")
- A transitive dependency through `commons-digester` → `commons-beanutils`

## Warning

⚠️ **This application contains intentional security vulnerabilities and should never be deployed in a production environment.** It is designed solely for educational purposes and CodeQL demonstration.

## License

This project is for educational and demonstration purposes only.
