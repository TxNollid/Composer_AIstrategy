(defsymphony
 "AI Infrastructure Stack - Trend Following with Cash Allocation"
 {:asset-class "EQUITIES", :rebalance-threshold 0.06}

 ;; Layer 1: Calculate Market Trend Strength
 ;; Using SPY distance from both 50-day and 200-day MAs as signal
 (if
  ;; Strong uptrend: Price > 200-day MA AND above 50-day MA
  (and
   (> (current-price "SPY") (moving-average-price "SPY" {:window 200}))
   (> (current-price "SPY") (moving-average-price "SPY" {:window 50})))

  ;; === STRONG UPTREND: 80% equities, 20% quality ===
  [(weight
    [;; 20% in PROCESSORS (aggressive AI core)
     [(weight-equal
       [(if
         (> (current-price "NVDA") (moving-average-price "NVDA" {:window 50}))
         [(weight-equal
           [(asset "NVDA" "NVIDIA Corp")
            (asset "AMD" "Advanced Micro Devices")])]
         [(asset "SPY" "SPDR S&P 500 ETF Trust")])])]
     0.20

     ;; 20% in MODELS & APPLICATIONS (mega-cap AI)
     [(weight-equal
       [(if
         (> (current-price "MSFT") (moving-average-price "MSFT" {:window 50}))
         [(weight-equal
           [(asset "MSFT" "Microsoft Corp")
            (asset "GOOGL" "Alphabet Inc")
            (asset "META" "Meta Platforms Inc")])]
         [(asset "SPY" "SPDR S&P 500 ETF Trust")])])]
     0.20

     ;; 15% in DATA CENTERS (stable infrastructure)
     [(if
       (> (current-price "EQIX") (moving-average-price "EQIX" {:window 100}))
       [(asset "EQIX" "Equinix Inc")]
       [(asset "SPY" "SPDR S&P 500 ETF Trust")])]
     0.15

     ;; 15% in ENERGY (nuclear power theme)
     [(weight-equal
       [(asset "CEG" "Constellation Energy Corp")
        (asset "VST" "Vistra Corp")])]
     0.15

     ;; 10% in RAW MATERIALS (commodity exposure)
     [(weight-equal
       [(asset "COPX" "Global X Copper Miners ETF")
        (asset "URNM" "Sprott Uranium Miners ETF")])]
     0.10

     ;; 20% CASH (defensive buffer)
     [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")]
     0.20])]

  ;; === NOT IN STRONG UPTREND: Check for weak uptrend vs downtrend ===
  [(if
    ;; Weak uptrend: Above 200-day but below 50-day (pullback)
    (> (current-price "SPY") (moving-average-price "SPY" {:window 200}))

    ;; PULLBACK MODE: 40% equities, 60% cash
    [(weight
      [;; 20% mega-cap quality only
       [(weight-equal
         [(asset "MSFT" "Microsoft Corp")
          (asset "GOOGL" "Alphabet Inc")
          (asset "AAPL" "Apple Inc")])]
       0.20

       ;; 20% defensive infrastructure
       [(weight-equal
         [(asset "EQIX" "Equinix Inc")
          (asset "CEG" "Constellation Energy Corp")])]
       0.20

       ;; 60% CASH - waiting for trend to resume
       [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")]
       0.60])]

    ;; DOWNTREND: Below 200-day MA
    [(if
      ;; Severe downtrend: Also below 50-day
      (< (current-price "SPY") (moving-average-price "SPY" {:window 50}))

      ;; SEVERE BEAR: 70% cash, 30% inverse
      [(weight
        [;; 30% inverse for downside capture
         [(weight-equal
           [(asset "SQQQ" "ProShares UltraPro Short QQQ")
            (asset "SOXS" "Direxion Daily Semiconductor Bear 3X")])]
         0.30

         ;; 70% cash preservation
         [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")]
         0.70])]

      ;; MILD BEAR: 100% cash - capital preservation
      [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")])])])
)
