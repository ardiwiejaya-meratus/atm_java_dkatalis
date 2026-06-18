# ATM System - Testing Report

## Test Coverage

This project includes comprehensive unit tests covering all major components:

### 1. Model Tests (`AccountTest.java`)
- Account creation and initialization
- Balance operations (add, subtract)
- Debt tracking operations
- BigDecimal precision verification
- Multiple operations in sequence

**Total Tests:** 9

### 2. Service Tests (`ATMServiceTest.java`)
- Login/logout functionality
- Deposit operations with debt settlement
- Withdrawal operations (including negative balance)
- Transfer operations (sufficient and insufficient balance)
- Edge cases (zero amounts, decimal precision)
- Full integration test following example session from requirements

**Total Tests:** 18

### 3. Repository Tests (`AccountRepositoryTest.java`)
- Account creation and retrieval
- Multiple account management
- Fresh repository instances
- Data isolation between repositories

**Total Tests:** 7

**Total Unit Tests: 34**

## Running Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=AccountTest
mvn test -Dtest=ATMServiceTest
mvn test -Dtest=AccountRepositoryTest
```

### Run Specific Test Method
```bash
mvn test -Dtest=ATMServiceTest#testExampleSessionFromRequirement
```

## Test Categories

### Functional Tests
- ✅ Login creates new account
- ✅ Multiple deposits accumulate correctly
- ✅ Withdrawals work with sufficient balance
- ✅ Transfers work with sufficient balance
- ✅ Transfers with insufficient balance create debt
- ✅ Deposits automatically settle debt

### Edge Case Tests
- ✅ Deposit with existing debt (partial settlement)
- ✅ Transfer to non-existent user (auto-creates)
- ✅ Transfer to self (no-op)
- ✅ Zero amount transfers
- ✅ Decimal precision (0.1 + 0.2 = 0.3)
- ✅ Negative balance allowed

### Error Handling Tests
- ✅ Operations without login throw IllegalStateException
- ✅ Invalid amount formats handled gracefully
- ✅ Negative amounts rejected in UI
- ✅ Repository returns null for non-existent accounts

### Integration Tests
- ✅ Full session from problem statement
- ✅ Multi-user interactions
- ✅ Complex debt scenarios
- ✅ Multiple transfers with debt tracking

## Test Results Summary

All 34 tests pass successfully.

```
[INFO] Tests run: 34, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

## Code Quality Metrics

### BigDecimal Usage
- ✅ All monetary amounts use BigDecimal
- ✅ No floating-point arithmetic
- ✅ Precision verified in tests

### Exception Handling
- ✅ IllegalStateException for invalid operations
- ✅ IllegalArgumentException for invalid input
- ✅ NumberFormatException caught in CLI
- ✅ Graceful error messages to user

### Design Patterns
- ✅ Repository Pattern for data access
- ✅ Service Pattern for business logic
- ✅ Separation of concerns (Model, Repository, Service, UI)
- ✅ Immutable operations where possible

## Edge Cases Covered

| Case | Behavior | Test |
|------|----------|------|
| Deposit when owing money | Auto-settles debt | `testDepositWithDebtSettlement` |
| Partial debt settlement | Tracks remaining debt | `testDepositPartialDebtSettlement` |
| Transfer more than balance | Creates negative balance (debt) | `testTransferInsufficientBalance` |
| Transfer to self | No-op (balance unchanged) | `testTransferToSelf` |
| Zero amount transfer | Allowed | `testZeroAmountOperations` |
| Decimal precision | 0.1 + 0.2 = 0.3 (exact) | `testBigDecimalPrecision` |
| Fresh repository per run | No data persistence | `testFreshRepositoryPerInstance` |
| Invalid amount format | Error message shown | UI error handling |
| Negative amount input | Rejected with error | UI validation |

## Performance Considerations

- HashMap lookups: O(1) average case
- No N+1 query problems (no persistence layer)
- No memory leaks (proper object references)
- Suitable for in-memory simulation

## Assumptions Validated

1. ✅ Debt represented as negative `owed` field
2. ✅ Deposits automatically settle debt (FIFO)
3. ✅ Insufficient balance transfers allowed (with debt)
4. ✅ Fresh state on each application start
5. ✅ BigDecimal for precise money calculations
6. ✅ User auto-created on login/transfer

## Known Limitations

1. **Single owed field** - Simplified debt tracking (vs. map of creditors)
   - Works for problem scope
   - Would need Debt entity in production system

2. **No persistence** - Data lost on application exit
   - By design for simulation
   - Would add database layer in production

3. **CLI parsing** - Simple split parsing
   - Works for alphanumeric names
   - Would need regex/proper parser for special characters

## Conclusion

The ATM system passes all tests and correctly implements all requirements. Edge cases are handled gracefully, and the solution follows best practices for Java application design.

All monetary calculations use BigDecimal ensuring precision. The code is well-tested, maintainable, and ready for production use (with appropriate persistence layer additions).
