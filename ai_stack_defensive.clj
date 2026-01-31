(defsymphony
 "AI Infrastructure Stack - Enhanced Drawdown Protection"
 {:asset-class "EQUITIES", :rebalance-threshold 0.05}

 ;; Layer 1: Multi-Timeframe Market Regime (SPY 50-day & 200-day MA)
 (if
  (and
   ;; Primary: SPY above 200-day MA
   (> (current-price "SPY") (moving-average-price "SPY" {:window 200}))
   ;; Secondary: SPY above 50-day MA for shorter-term trend
   (> (current-price "SPY") (moving-average-price "SPY" {:window 50})))

  ;; STRONG BULL: Both MAs aligned - Full risk deployment
  [(if
    ;; Additional check: VIX proxy (UVXY) is not spiking
    (< (rsi "UVXY" {:window 14}) 60)

    ;; Market calm: Deploy sector rotation with normal allocations
    [(weight-equal
      [;; PROCESSORS - Strict momentum filter
       (if
        (and
         (> (current-price "NVDA") (moving-average-price "NVDA" {:window 50}))
         (> (rsi "NVDA" {:window 14}) 35)
         (< (rsi "NVDA" {:window 14}) 65))
        [(weight-equal
          [(asset "NVDA" "NVIDIA Corp")
           (asset "AMD" "Advanced Micro Devices")
           (asset "AVGO" "Broadcom Inc")])]
        ;; Exit to cash proxy if momentum breaks
        [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")])

       ;; MODELS & APPLICATIONS - Momentum + RSI filter
       (if
        (and
         (> (current-price "META") (moving-average-price "META" {:window 50}))
         (> (rsi "META" {:window 14}) 35)
         (< (rsi "META" {:window 14}) 65))
        [(weight-equal
          [(asset "META" "Meta Platforms Inc")
           (asset "GOOGL" "Alphabet Inc")
           (asset "MSFT" "Microsoft Corp")
           (asset "PLTR" "Palantir Technologies")])]
        [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")])

       ;; DATA CENTERS - Defensive infrastructure
       (if
        (and
         (> (current-price "EQIX") (moving-average-price "EQIX" {:window 50}))
         (> (rsi "EQIX" {:window 14}) 35))
        [(weight-equal
          [(asset "EQIX" "Equinix Inc")
           (asset "DLR" "Digital Realty Trust")])]
        [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")])

       ;; ENERGY - Nuclear & utilities (defensive)
       (if
        (> (rsi "CEG" {:window 14}) 35)
        [(weight-equal
          [(asset "CEG" "Constellation Energy Corp")
           (asset "VST" "Vistra Corp")
           (asset "UTSL" "Direxion Daily Utilities Bull 3X")])]
        [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")])

       ;; RAW MATERIALS - Commodity exposure
       (if
        (and
         (> (current-price "MP") (moving-average-price "MP" {:window 50}))
         (> (rsi "MP" {:window 14}) 35))
        [(weight-equal
          [(asset "MP" "MP Materials Corp")
           (asset "REMX" "VanEck Rare Earth/Strategic Metals ETF")
           (asset "URNM" "Sprott Uranium Miners ETF")])]
        [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")])])]

    ;; Volatility spike detected: Move to defensive/cash
    [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")])]

  ;; WEAK BULL or BEAR MARKET: At least one MA broken
  [(if
    ;; Check if we're in a mild pullback (above 200 but below 50)
    (> (current-price "SPY") (moving-average-price "SPY" {:window 200}))

    ;; PULLBACK MODE: 50% defensive, 50% quality large caps only
    [(weight-equal
      [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")
       (weight-equal
        [(asset "MSFT" "Microsoft Corp")
         (asset "GOOGL" "Alphabet Inc")
         (asset "NVDA" "NVIDIA Corp")])])]

    ;; FULL BEAR: Below 200-day MA - Maximum defense
    [(if
      ;; Check if trend is deteriorating badly
      (< (current-price "SPY") (moving-average-price "SPY" {:window 50}))

      ;; Severe bear: 100% inverse ETFs
      [(weight-equal
        [(asset "SQQQ" "ProShares UltraPro Short QQQ")
         (asset "SOXS" "Direxion Daily Semiconductor Bear 3X")])]

      ;; Mild bear: 100% bonds
      [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")])])])
)
