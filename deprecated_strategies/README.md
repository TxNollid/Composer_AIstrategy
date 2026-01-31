# Deprecated Strategies

⚠️ **DO NOT USE THESE FILES** - They contain syntax errors and will not work in Composer.

## Why These Are Deprecated

These strategy files use Composer syntax that is **not supported**:

1. ❌ **`(and condition1 condition2)`** operator - Composer doesn't support compound AND logic
2. ❌ **`(weight asset1 0.40 asset2 0.60)`** function - Custom percentage weights not available
3. ❌ Complex nested conditions that break parser

## Working Alternatives

For each deprecated file, use the corresponding v1.0 version in the parent directory:

| Deprecated File | Working Alternative | Notes |
|-----------------|-------------------|-------|
| `ai_momentum_bull.clj` | `../ai_momentum_bull_v1.0.clj` | 44% return, 1.77 Calmar |
| `ai_relative_strength.clj` | `../ai_relative_strength_v1.0.clj` | 41% return, 1.55 Calmar |
| `ai_stack_defensive.clj` | `../ai_stack_defensive_v1.0.clj` | Max drawdown protection |
| `ai_buy_hold_winners.clj` | `../ai_buy_hold_winners_v1.0.clj` | NVDA concentration |
| `ai_stack_strategy.clj` | N/A | Original attempt, too defensive |
| `ai_stack_trend_cash.clj` | N/A | Original attempt, too defensive |

## What Was Fixed in v1.0

The v1.0 strategies use **only supported Composer syntax**:

✅ Nested `if` statements instead of `(and ...)`
✅ `weight-equal` only (no custom percentages)
✅ Asset repetition to create concentration (NVDA appears 3x = higher weight)
✅ Clean, parseable Clojure/EDN structure

## Historical Context

These files are preserved for reference to show the evolution of the strategies:

1. **Initial concept** - Complex logic with unsupported syntax
2. **Syntax errors discovered** - Composer rejected these
3. **Fixed versions** - Simplified to supported syntax
4. **Backtested and proven** - v1.0 versions delivered 41-44% returns in 2025

## Technical Details

### Example Error (Don't Do This):

```clojure
;; BROKEN - This will not parse in Composer
(if
 (and
  (> (current-price "SPY") (moving-average-price "SPY" {:window 200}))
  (> (current-price "SPY") (moving-average-price "SPY" {:window 50})))

 [(weight
   [(asset "NVDA" "NVIDIA") 0.40
    (asset "MSFT" "Microsoft") 0.60])]

 [(asset "SHY" "Bonds")])
```

### Correct Syntax (Do This):

```clojure
;; WORKING - Nested ifs and weight-equal only
(if
 (> (current-price "SPY") (moving-average-price "SPY" {:window 200}))

 [(if
   (> (current-price "SPY") (moving-average-price "SPY" {:window 50}))

   ;; Approximate 40/60 with repetition
   [(weight-equal
     [(asset "NVDA" "NVIDIA")
      (asset "NVDA" "NVIDIA")
      (asset "MSFT" "Microsoft")
      (asset "MSFT" "Microsoft")
      (asset "MSFT" "Microsoft")])] ; NVDA=40%, MSFT=60%

   [(asset "SHY" "Bonds")])]

 [(asset "SHY" "Bonds")])
```

## When Were These Created?

- **January 31, 2026** - Initial strategy development
- **Same day** - Syntax errors discovered through testing
- **Same day** - v1.0 versions created and proven

## Can These Be Fixed?

These files **have already been fixed** - that's what the v1.0 versions are!

The deprecated files are kept only for:
- Historical reference
- Learning examples (what NOT to do)
- Comparison with working syntax

## Usage

**Don't use anything in this folder.**

Go back to the parent directory and use:
- `ai_momentum_bull_v1.0.clj` ⭐ (Recommended)
- `ai_relative_strength_v1.0.clj`
- `ai_stack_defensive_v1.0.clj`
- `ai_buy_hold_winners_v1.0.clj`

---

**Last Updated:** January 31, 2026
