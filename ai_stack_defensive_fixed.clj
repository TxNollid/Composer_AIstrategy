(defsymphony
 "AI Infrastructure Stack - Enhanced Drawdown Protection"
 {:asset-class "EQUITIES", :rebalance-threshold 0.05}

 ;; Layer 1: Multi-Timeframe Market Regime (SPY 200-day then 50-day MA)
 (if
  (> (current-price "SPY") (moving-average-price "SPY" {:window 200}))

  ;; SPY above 200-day: Check 50-day for strength confirmation
  [(if
    (> (current-price "SPY") (moving-average-price "SPY" {:window 50}))

    ;; STRONG BULL: Both MAs aligned - Check volatility
    [(if
      (< (rsi "UVXY" {:window 14}) 60)

      ;; Market calm: Deploy sector rotation
      [(weight-equal
        [;; PROCESSORS - Check momentum
         (if
          (> (current-price "NVDA") (moving-average-price "NVDA" {:window 50}))
          [(if
            (> (rsi "NVDA" {:window 14}) 35)
            [(if
              (< (rsi "NVDA" {:window 14}) 65)
              [(weight-equal
                [(asset "NVDA" "NVIDIA Corp")
                 (asset "AMD" "Advanced Micro Devices")
                 (asset "AVGO" "Broadcom Inc")])]
              [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")])]
            [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")])]
          [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")])

         ;; MODELS & APPLICATIONS
         (if
          (> (current-price "META") (moving-average-price "META" {:window 50}))
          [(if
            (> (rsi "META" {:window 14}) 35)
            [(if
              (< (rsi "META" {:window 14}) 65)
              [(weight-equal
                [(asset "META" "Meta Platforms Inc")
                 (asset "GOOGL" "Alphabet Inc")
                 (asset "MSFT" "Microsoft Corp")
                 (asset "PLTR" "Palantir Technologies")])]
              [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")])]
            [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")])]
          [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")])

         ;; DATA CENTERS
         (if
          (> (current-price "EQIX") (moving-average-price "EQIX" {:window 50}))
          [(if
            (> (rsi "EQIX" {:window 14}) 35)
            [(weight-equal
              [(asset "EQIX" "Equinix Inc")
               (asset "DLR" "Digital Realty Trust")])]
            [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")])]
          [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")])

         ;; ENERGY
         (if
          (> (rsi "CEG" {:window 14}) 35)
          [(weight-equal
            [(asset "CEG" "Constellation Energy Corp")
             (asset "VST" "Vistra Corp")])]
          [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")])

         ;; RAW MATERIALS
         (if
          (> (current-price "MP") (moving-average-price "MP" {:window 50}))
          [(if
            (> (rsi "MP" {:window 14}) 35)
            [(weight-equal
              [(asset "MP" "MP Materials Corp")
               (asset "REMX" "VanEck Rare Earth/Strategic Metals ETF")
               (asset "URNM" "Sprott Uranium Miners ETF")])]
            [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")])]
          [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")])])]

      ;; Volatility spike: Full cash
      [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")])]

    ;; PULLBACK: Above 200 but below 50
    [(weight-equal
      [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")
       (weight-equal
        [(asset "MSFT" "Microsoft Corp")
         (asset "GOOGL" "Alphabet Inc")
         (asset "NVDA" "NVIDIA Corp")])])])]

  ;; BEAR: Below 200-day MA
  [(if
    (< (current-price "SPY") (moving-average-price "SPY" {:window 50}))

    ;; Severe bear: Inverse ETFs
    [(weight-equal
      [(asset "SQQQ" "ProShares UltraPro Short QQQ")
       (asset "SOXS" "Direxion Daily Semiconductor Bear 3X")])]

    ;; Mild bear: Cash
    [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")])])
)
