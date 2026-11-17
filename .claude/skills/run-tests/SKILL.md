---
name: run-tests
description: Run the Maven test suite for the movie ticketing app and summarize failures. Use when asked to run or verify tests.
---

1. Run `mvn test` from the repository root.
2. If tests fail, read the surefire reports in `target/surefire-reports/` and report each failing test with its cause.
3. Do not modify tests to make them pass without asking.
