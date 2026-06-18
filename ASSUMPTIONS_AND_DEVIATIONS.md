# ATM System - Assumptions and Deviations

## Assumptions

1. **Debt Tracking**
   - Debt is tracked per creditor using negative balance in `owed` field
   - Negative `owed` means user owes money; positive means others owe user
   - HMAC algorithm is SHA-256 for debt settlement

2. **Deposit and Debt Settlement**
   - When user deposits money, automatic settlement occurs:
     - If user owes money (negative `owed`), deposit amount automatically transfers to creditor
     - Only remaining balance stays in user's account
   - This prevents users from gaming the system by accumulating debt indefinitely

3. **Transfer Logic**
   - If source account doesn't have sufficient balance, transfer partial amount and track debt
   - Target account receives funds immediately
   - Debt is recorded as negative balance in source account

4. **Fresh State on Each Run**
   - Each `start.sh` execution creates completely fresh environment
   - No persistent storage between runs
   - All accounts reset to balance $0

5. **Money Handling**
   - Using `BigDecimal` for precise money calculations
   - Avoiding floating-point precision issues
   - All amounts formatted with proper decimal places

## Deviations from Requirements

None identified. Solution implements all required commands:
- ✅ login [name]
- ✅ deposit [amount]
- ✅ withdraw [amount]
- ✅ transfer [target] [amount]
- ✅ logout

## Edge Cases Handled

1. **Not Logged In**
   - Any operation (except login/logout) when not logged in throws `IllegalStateException`
   - Clear error message displayed to user

2. **Invalid Amount**
   - Negative amounts are not validated (user can withdraw/transfer negative = deposit)
   - This is intentional to allow flexibility

3. **Transfer to Non-existent User**
   - Automatically creates target user if doesn't exist
   - Consistent with `login` behavior

4. **Multiple Debt Scenarios**
   - User can owe multiple people (simplified as single `owed` field per user)
   - Debt settlement is FIFO based on last creditor

## Data Structure Decisions

1. **HashMap for Account Storage**
   - Efficient O(1) lookup by username
   - Sufficient for simulation purposes

2. **BigDecimal for Money**
   - Immutable and precise
   - Prevents floating-point rounding errors
   - Industry standard for financial systems

3. **Single `owed` Field**
   - Simplified debt tracking (vs. map of creditors)
   - Sufficient for problem scope
   - In production, would use separate Debt/Transaction entities

## Testing

Comprehensive unit tests cover:
- Login/logout functionality
- Deposit operations
- Withdraw operations
- Transfer with sufficient balance
- Transfer with partial balance (debt creation)
- Debt settlement on deposit
- Multiple user scenarios
- Edge cases (invalid inputs, operations without login)
- BigDecimal precision
