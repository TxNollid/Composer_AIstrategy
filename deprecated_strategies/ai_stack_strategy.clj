(defsymphony
 "AI Infrastructure Stack - Risk-Adjusted Sector Rotation"
 {:asset-class "EQUITIES", :rebalance-threshold 0.08}

 ;; Layer 1: Overall Market Regime Check (SPY vs 200-day MA)
 (if
  (> (current-price "SPY") (moving-average-price "SPY" {:window 200}))

  ;; BULL MARKET: Proceed with sector rotation and risk layering
  [(weight-equal
    [;; Layer 2: Sector Selection with Layer 3: Risk Checks

     ;; PROCESSORS (Layer 5) - Core AI compute
     (if
      (and
       (> (rsi "NVDA" {:window 14}) 30)
       (< (rsi "NVDA" {:window 14}) 70))
      ;; Normal conditions: Hold quality processors
      [(weight-equal
        [(asset "NVDA" "NVIDIA Corp")
         (asset "AMD" "Advanced Micro Devices")
         (asset "AVGO" "Broadcom Inc")])]
      ;; Overbought (RSI > 70): Risk-off for this sector
      (if
       (> (rsi "NVDA" {:window 14}) 70)
       [(asset "SOXS" "Direxion Daily Semiconductor Bear 3X")]
       ;; Oversold (RSI < 30): Risk-on for this sector
       [(asset "SOXL" "Direxion Daily Semiconductor Bull 3X")]))

     ;; MODELS & APPLICATIONS (Layer 9) - AI software layer
     (if
      (and
       (> (rsi "META" {:window 14}) 30)
       (< (rsi "META" {:window 14}) 70))
      ;; Normal conditions: Hold AI leaders
      [(weight-equal
        [(asset "META" "Meta Platforms Inc")
         (asset "GOOGL" "Alphabet Inc")
         (asset "MSFT" "Microsoft Corp")
         (asset "PLTR" "Palantir Technologies")])]
      ;; Overbought: Risk-off
      (if
       (> (rsi "META" {:window 14}) 70)
       [(asset "TECS" "Direxion Daily Technology Bear 3X")]
       ;; Oversold: Risk-on
       [(asset "TECL" "Direxion Daily Technology Bull 3X")]))

     ;; DATA CENTERS (Layer 8) - Infrastructure backbone
     (if
      (and
       (> (rsi "EQIX" {:window 14}) 30)
       (< (rsi "EQIX" {:window 14}) 70))
      ;; Normal conditions: Hold REITs
      [(weight-equal
        [(asset "EQIX" "Equinix Inc")
         (asset "DLR" "Digital Realty Trust")])]
      ;; Overbought: Risk-off
      (if
       (> (rsi "EQIX" {:window 14}) 70)
       [(asset "DRV" "Direxion Daily Real Estate Bear 3X")]
       ;; Oversold: Risk-on
       [(asset "DRN" "Direxion Daily Real Estate Bull 3X")]))

     ;; ENERGY INFRASTRUCTURE (Layer 7) - Power for AI
     (if
      (and
       (> (rsi "CEG" {:window 14}) 30)
       (< (rsi "CEG" {:window 14}) 70))
      ;; Normal conditions: Nuclear & utilities
      [(weight-equal
        [(asset "CEG" "Constellation Energy Corp")
         (asset "VST" "Vistra Corp")
         (asset "SMR" "NuScale Power Corp")])]
      ;; Overbought: Risk-off
      (if
       (> (rsi "CEG" {:window 14}) 70)
       [(asset "ERY" "Direxion Daily Energy Bear 2X")]
       ;; Oversold: Risk-on
       [(asset "UTSL" "Direxion Daily Utilities Bull 3X")]))

     ;; RAW MATERIALS (Layer 1) - Foundational inputs
     (if
      (and
       (> (rsi "MP" {:window 14}) 30)
       (< (rsi "MP" {:window 14}) 70))
      ;; Normal conditions: Rare earths, copper, uranium
      [(weight-equal
        [(asset "MP" "MP Materials Corp")
         (asset "REMX" "VanEck Rare Earth/Strategic Metals ETF")
         (asset "COPX" "Global X Copper Miners ETF")
         (asset "URNM" "Sprott Uranium Miners ETF")])]
      ;; Overbought: Risk-off
      (if
       (> (rsi "MP" {:window 14}) 70)
       [(asset "SMN" "ProShares UltraShort Materials")]
       ;; Oversold: Risk-on
       [(asset "UYM" "ProShares Ultra Basic Materials")]))])]

  ;; BEAR MARKET: SPY below 200-day MA - Full Risk-Off Mode
  [(weight-equal
    [;; Defensive positioning with inverse ETFs
     (asset "SOXS" "Direxion Daily Semiconductor Bear 3X")
     (asset "SQQQ" "ProShares UltraPro Short QQQ")
     (asset "TECS" "Direxion Daily Technology Bear 3X")])])
)
