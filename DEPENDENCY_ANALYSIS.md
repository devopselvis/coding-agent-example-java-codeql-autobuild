# Dependency Analysis

## Vulnerable Dependency in Multiple Paths

This project demonstrates a vulnerable dependency (`commons-collections:3.2.1`) appearing in multiple paths in the dependency graph.

### The Vulnerable Package

**Package**: `commons-collections:3.2.1`

**Known Vulnerabilities**:
- CVE-2015-6420: Serialized-object interfaces in certain Cisco Collaboration and Social Media implementations allow remote attackers to execute arbitrary commands
- CVE-2017-15708: InvokerTransformer code was present in commons-collections4 1.0-4.1 which allowed remote code execution

### Dependency Paths

The `commons-collections:3.2.1` package appears in the following paths in the dependency graph:

1. **Direct Dependency**
   ```
   vulnerable-app
   └── commons-collections:3.2.1
   ```

2. **Transitive Dependency via commons-beanutils**
   ```
   vulnerable-app
   └── commons-beanutils:1.9.2
       └── commons-collections:3.2.1
   ```

3. **Transitive Dependency via commons-digester**
   ```
   vulnerable-app
   └── commons-digester:2.1
       └── commons-beanutils:1.8.3
           └── commons-collections:3.2.1
   ```

### Verification

To verify that the package appears in multiple paths, run:

```bash
mvn dependency:tree -Dverbose
```

Look for lines showing `commons-collections` with annotations like "omitted for duplicate" which indicates it's being pulled in through multiple paths.

Example output:
```
[INFO] +- commons-collections:commons-collections:jar:3.2.1:compile
[INFO] +- commons-beanutils:commons-beanutils:jar:1.9.2:compile
[INFO] |  \- (commons-collections:commons-collections:jar:3.2.1:compile - omitted for duplicate)
[INFO] +- commons-digester:commons-digester:jar:2.1:compile
[INFO] |  +- (commons-beanutils:commons-beanutils:jar:1.8.3:compile - omitted for conflict with 1.9.2)
```

### Why This Matters

In real-world scenarios, vulnerable dependencies often appear in multiple paths through the dependency graph. This makes them:
- More challenging to identify
- Harder to remediate (requires updating multiple parent dependencies)
- More likely to be overlooked by basic security scanning

This repository intentionally includes this pattern to demonstrate how dependency scanning tools like GitHub's Dependabot and CodeQL can detect such vulnerabilities across the entire dependency graph.

## Viewing the Full Dependency Graph

To see the complete dependency tree:

```bash
# Standard view (duplicates are omitted)
mvn dependency:tree

# Verbose view (shows all paths including duplicates)
mvn dependency:tree -Dverbose

# Filter to see only commons-related dependencies
mvn dependency:tree -Dverbose | grep commons
```

## Security Recommendations

⚠️ **For educational purposes only**

In a production environment, you would:
1. Upgrade to a patched version of the vulnerable library
2. If no patch exists, find alternative libraries
3. Use dependency scanning tools to continuously monitor for vulnerabilities
4. Implement Software Composition Analysis (SCA) in your CI/CD pipeline
