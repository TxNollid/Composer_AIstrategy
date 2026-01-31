(defsymphony
 "AI Infrastructure - Buy & Hold Winners"
 {:asset-class "EQUITIES", :rebalance-threshold 0.12}

 ;; Philosophy: Stay invested in AI winners. Only reduce in CONFIRMED bear.
 ;; Confirmed bear = BOTH SPY AND QQQ below 200-day MA
 (if
  (< (current-price "SPY") (moving-average-price "SPY" {:window 200}))

  ;; SPY below 200: Check QQQ
  [(if
    (< (current-price "QQQ") (moving-average-price "QQQ" {:window 200}))

    ;; CONFIRMED BEAR: Both below 200
    [(weight-equal
      [;; Still hold AI mega-caps (they recover first)
       (weight-equal
        [(asset "NVDA" "NVIDIA Corp")
         (asset "MSFT" "Microsoft Corp")
         (asset "GOOGL" "Alphabet Inc")
         (asset "META" "Meta Platforms Inc")])

       ;; Cash component
       (asset "SHY" "iShares 1-3 Year Treasury Bond ETF")

       ;; Opportunistic inverse (only if extreme weakness)
       (if
        (< (current-price "QQQ") (moving-average-price "QQQ" {:window 50}))
        [(if
          (> (rsi "UVXY" {:window 14}) 65)
          [(asset "SQQQ" "ProShares UltraPro Short QQQ")]
          [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")])]
        [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")])])]

    ;; SPY weak but QQQ strong: Not a confirmed bear
    [(weight-equal
      [;; NVDA concentration (appears multiple times for higher weight)
       (asset "NVDA" "NVIDIA Corp")
       (asset "NVDA" "NVIDIA Corp")

       ;; Other mega-caps
       (asset "MSFT" "Microsoft Corp")
       (asset "GOOGL" "Alphabet Inc")
       (asset "META" "Meta Platforms Inc")

       ;; AMD and AVGO
       (asset "AMD" "Advanced Micro Devices")
       (asset "AVGO" "Broadcom Inc")

       ;; Infrastructure
       (weight-equal
        [(asset "EQIX" "Equinix Inc")
         (asset "CEG" "Constellation Energy Corp")])

       ;; Leverage on semiconductors
       (asset "SOXL" "Direxion Daily Semiconductor Bull 3X")])])]

  ;; SPY above 200: EVERYTHING ELSE - stay fully invested
  [(weight-equal
    [;; CORE AI COMPUTE - Heavy NVDA concentration
     (if
      (> (rsi "NVDA" {:window 14}) 60)

      ;; NVDA momentum: Overweight with leverage
      [(weight-equal
        [(asset "NVDA" "NVIDIA Corp")
         (asset "NVDA" "NVIDIA Corp")
         (asset "NVDA" "NVIDIA Corp")
         (asset "AMD" "Advanced Micro Devices")
         (asset "AVGO" "Broadcom Inc")
         (asset "SOXL" "Direxion Daily Semiconductor Bull 3X")])]

      ;; Normal: Still heavy NVDA
      [(weight-equal
        [(asset "NVDA" "NVIDIA Corp")
         (asset "NVDA" "NVIDIA Corp")
         (asset "AMD" "Advanced Micro Devices")
         (asset "AVGO" "Broadcom Inc")])])

     ;; CORE AI COMPUTE duplicate for higher overall weight
     (if
      (> (rsi "NVDA" {:window 14}) 60)
      [(weight-equal
        [(asset "NVDA" "NVIDIA Corp")
         (asset "AMD" "Advanced Micro Devices")
         (asset "AVGO" "Broadcom Inc")])]
      [(weight-equal
        [(asset "NVDA" "NVIDIA Corp")
         (asset "AMD" "Advanced Micro Devices")])])

     ;; AI SOFTWARE & PLATFORMS
     (if
      (> (rsi "META" {:window 14}) (rsi "NVDA" {:window 14}))

      ;; Software leading: Overweight high-growth
      [(weight-equal
        [(asset "META" "Meta Platforms Inc")
         (asset "GOOGL" "Alphabet Inc")
         (asset "MSFT" "Microsoft Corp")
         (asset "PLTR" "Palantir Technologies")
         (asset "SNOW" "Snowflake Inc")
         (asset "TECL" "Direxion Daily Technology Bull 3X")])]

      ;; Hardware leading: Mega-cap safety
      [(weight-equal
        [(asset "MSFT" "Microsoft Corp")
         (asset "GOOGL" "Alphabet Inc")
         (asset "META" "Meta Platforms Inc")])])

     ;; AI SOFTWARE duplicate for weight
     (weight-equal
      [(asset "MSFT" "Microsoft Corp")
       (asset "GOOGL" "Alphabet Inc")
       (asset "META" "Meta Platforms Inc")])

     ;; NETWORKING
     (weight-equal
      [(asset "AVGO" "Broadcom Inc")
       (asset "ANET" "Arista Networks Inc")])

     ;; POWER & INFRASTRUCTURE
     (if
      (> (current-price "CEG") (moving-average-price "CEG" {:window 200}))
      ;; Nuclear theme working
      [(weight-equal
        [(asset "CEG" "Constellation Energy Corp")
         (asset "VST" "Vistra Corp")
         (asset "SMR" "NuScale Power Corp")])]
      ;; Weak: Data centers
      [(weight-equal
        [(asset "EQIX" "Equinix Inc")
         (asset "DLR" "Digital Realty Trust")])])

     ;; TACTICAL MOMENTUM
     (if
      (> (rsi "PLTR" {:window 14}) (rsi "COPX" {:window 14}))
      ;; Software momentum
      [(weight-equal
        [(asset "PLTR" "Palantir Technologies")
         (asset "AI" "C3.ai Inc")
         (asset "PATH" "UiPath Inc")])]
      ;; Materials momentum
      [(weight-equal
        [(asset "COPX" "Global X Copper Miners ETF")
         (asset "URNM" "Sprott Uranium Miners ETF")
         (asset "MP" "MP Materials Corp")])])])])
)
