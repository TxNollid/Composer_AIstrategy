# AI Infrastructure Trading Strategies for Composer

A collection of algorithmic trading strategies designed for the AI infrastructure secular bull market, built using [Composer](https://www.composer.trade)'s symphony syntax.

## 🏆 Performance Summary (2025)

| Strategy | Total Return | Max Drawdown | Calmar Ratio | Status |
|----------|--------------|--------------|--------------|--------|
| **Momentum Bull** | **44%** | **-23%** | **1.77** | ✅ **RECOMMENDED** |
| **Relative Strength** | **41%** | **-24%** | **1.55** | ✅ Strong |
| Defensive | N/A | N/A | N/A | ⚠️ Too conservative |

## 📁 Repository Structure

### 🎯 Production-Ready Strategies (Use These)

**Primary Strategy:**
- `ai_momentum_bull_fixed.clj` - **Winner on all metrics** (44% return, 1.77 Calmar)
  - Simple 5-sector equal weight allocation
  - 200-day MA triggers only (avoids whipsaw)
  - Tactical leverage when RSI > 55
  - Best for trending markets

**Alternative Strategy:**
- `ai_relative_strength_fixed.clj` - Strong performer (41% return, 1.55 Calmar)
  - Always 100% invested in bull markets
  - Sector rotation based on relative strength
  - Perfect for "Liberation Day" scenarios (tech leadership)
  - Best for rotating markets

**Defensive Strategy:**
- `ai_stack_defensive_fixed.clj` - Maximum drawdown protection
  - Multi-timeframe filters (50-day & 200-day MA)
  - Volatility spike detection
  - Quick to cash during uncertainty
  - Best for risk-averse investors

**Conservative Alternative:**
- `ai_buy_hold_winners_fixed.clj` - NVDA concentration (50%+ allocation)
  - Buy and hold AI winners
  - Only exits when both SPY and QQQ < 200-day MA
  - Maximum AI exposure
  - Best for strong AI conviction

### 📊 Documentation

- `TOP_TWO_COMPARISON.md` - **Detailed analysis** of Momentum Bull vs Relative Strength
- `FIXED_STRATEGIES.md` - Syntax error fixes and implementation details
- `ENHANCED_STRATEGIES.md` - Strategy philosophy and design rationale
- `STRATEGY_COMPARISON.md` - Original defensive strategies comparison
- `AI_Strategy.md` - AI infrastructure stack ticker universe

### 📖 Reference Files

- `SampleSyntax` - Example Composer symphony syntax
- `Instructions.md` - Setup instructions (empty template)

### ⚠️ Deprecated Files (Don't Use - Syntax Errors)

- `ai_stack_defensive.clj` - Uses unsupported `(and ...)` operator
- `ai_momentum_bull.clj` - Uses unsupported `(weight ...)` function
- `ai_relative_strength.clj` - Syntax errors
- `ai_buy_hold_winners.clj` - Syntax errors
- `ai_stack_strategy.clj` - Original strategy (too defensive)
- `ai_stack_trend_cash.clj` - Original strategy (too defensive)

## 🚀 Quick Start

1. **Choose a strategy** (recommended: `ai_momentum_bull_fixed.clj`)
2. **Copy the file contents**
3. **Paste into Composer** symphony editor
4. **Backtest** against your preferred timeframe
5. **Deploy** when satisfied with performance

## 💡 Strategy Philosophy

These strategies are designed for the **AI infrastructure secular bull market** (2020s and beyond):

### Core Thesis
- AI infrastructure is a multi-decade investment theme
- Stay invested during the mega-trend, rotate within AI sectors
- Only exit during confirmed bear markets (both SPY and QQQ < 200-day MA)
- Minimize cash drag to maximize participation in secular growth

### AI Infrastructure Stack (9 Layers)

1. **Raw Materials** - Rare earths, copper, uranium (MP, COPX, URNM)
2. **Equipment** - Semiconductor manufacturing (ASML, AMAT, LRCX)
3. **Foundries** - Chip fabrication (TSM, GFS)
4. **Memory** - HBM, storage (MU, WDC)
5. **Processors** - GPUs, TPUs (NVDA, AMD, AVGO) 🔥
6. **Networking** - Data interconnect (ANET, CSCO)
7. **Energy** - Nuclear, utilities (CEG, VST, SMR)
8. **Data Centers** - Infrastructure REITs (EQIX, DLR)
9. **Models & Applications** - AI software (META, GOOGL, MSFT, PLTR) 🔥

## 📈 Key Features

### Momentum Bull Strategy
- ✅ Equal weight 5 sectors (Processors, Models, Data Centers, Energy, Materials)
- ✅ NVDA 200-day MA as sector health indicator
- ✅ Tactical leverage: SOXL/TECL when RSI > 55
- ✅ QQQ vs SPY check for tech leadership
- ✅ Capitulation buying when UVXY RSI > 70
- ✅ Clean, simple logic (fewer execution errors)

### Relative Strength Strategy
- ✅ Always 100% invested (zero cash drag in bulls)
- ✅ Sector rotation based on RSI comparisons
- ✅ Overweight winners via asset repetition
- ✅ Four market regimes (bull/mixed/tech leadership/bear)
- ✅ Perfect Liberation Day handling (QQQ strong + SPY weak)
- ✅ Dynamic sector allocation (10-40% per sector)

## ⚙️ Technical Details

### Composer Syntax Constraints

Composer's symphony language has limitations:
- ❌ No `(and condition1 condition2)` operator
- ❌ No `(weight asset1 0.40 asset2 0.60)` function
- ✅ Only `weight-equal` for allocations
- ✅ Must use nested `if` statements for multiple conditions
- ✅ Asset repetition creates concentration (NVDA appears 3x = 60% weight)

### How We Work Around Limitations

**Approximate percentages with repetition:**
```clojure
(weight-equal
  [(asset "NVDA" "NVIDIA Corp")      ; Gets 1/4 = 25%
   (asset "NVDA" "NVIDIA Corp")      ; Gets 1/4 = 25%
   (asset "AMD" "Advanced Micro")    ; Gets 1/4 = 25%
   (asset "MSFT" "Microsoft Corp")]) ; Gets 1/4 = 25%
```
Result: NVDA 50%, AMD 25%, MSFT 25%

**Multiple conditions via nesting:**
```clojure
(if (> price ma200)
  [(if (> price ma50)
    [allocate-aggressive]
    [allocate-moderate])]
  [allocate-defensive])
```

## 📊 Backtesting Tips

When testing these strategies, look for:

1. **Total Return vs SPY** - Target 110-150% of SPY
2. **2020-2024 Performance** - Should capture AI mega-trend
3. **April 2025 "Liberation Day"** - Tech resilience test
4. **November 2025 Drawdown** - Recovery speed matters
5. **Sharpe/Calmar Ratio** - Risk-adjusted returns (target >1.5)
6. **Max Drawdown** - Acceptable: 20-30% for aggressive strategies
7. **Turnover** - Lower is better (taxes and execution costs)

## 🎯 Use Cases

### Choose Momentum Bull If:
- You want the statistically proven winner
- You prefer simplicity and transparency
- You trade less frequently
- Future markets look like 2025 (bull with periodic pullbacks)
- You value risk-adjusted returns over pure alpha

### Choose Relative Strength If:
- You believe in active sector rotation
- You expect clear tech vs materials divergences
- You want to be 100% invested always
- Future has "Liberation Day" scenarios (tech leadership)
- You're comfortable with complexity

### Choose Defensive If:
- Drawdown protection is paramount
- You're risk-averse or near retirement
- You expect extended volatility
- You prefer safety over maximizing returns
- You sleep better with 50%+ cash buffers

### Choose Buy & Hold Winners If:
- You have strong AI conviction (NVDA heavy)
- You want minimum turnover (taxes)
- You believe in concentration over diversification
- You can stomach volatility for long-term gains

## 🔧 Customization

Feel free to modify these strategies:

- **Risk tolerance**: Adjust RSI thresholds (55 → 60 for more aggressive)
- **Leverage**: Increase SOXL/TECL allocation for higher risk/return
- **Sectors**: Add or remove from the 5-sector allocation
- **Triggers**: Change 200-day MA to 150-day for earlier signals
- **Cash allocation**: Add minimum cash buffer for safety

## ⚠️ Risk Disclaimer

- These strategies involve **leveraged ETFs** (SOXL, TECL, TQQQ) which amplify both gains and losses
- **Inverse ETFs** (SQQQ, SOXS) are used in bear markets - these can decay over time
- Past performance (44% in 2025) does not guarantee future results
- **Maximum drawdowns of 23-24%** occurred - ensure you can tolerate this
- Strategies are optimized for AI secular bull - may underperform in non-AI environments
- Not financial advice - do your own due diligence

## 📞 Support

For Composer-specific questions:
- [Composer Documentation](https://www.composer.trade/docs)
- [Composer Community](https://www.composer.trade/community)

For strategy questions or improvements:
- Open an issue in this repository
- Submit a pull request with enhancements

## 📄 License

MIT License - Feel free to use, modify, and distribute these strategies.

## 🤝 Contributing

Contributions welcome! Areas for improvement:

- Additional sector allocations
- Alternative momentum indicators
- Better drawdown protection mechanisms
- Backtests across different time periods
- Tax-optimized variations
- Lower leverage alternatives

## 🙏 Acknowledgments

Built with assistance from Claude (Anthropic) using:
- AI infrastructure market research
- Composer symphony syntax
- Quantitative strategy design
- Risk management principles

---

**Remember:** The best strategy is the one you can stick with through volatility. Start with paper trading before deploying real capital.

**Last Updated:** January 31, 2026
