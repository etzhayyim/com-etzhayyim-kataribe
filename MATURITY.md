# kataribe (語部) — Maturity Ledger

`/loop` の進捗台帳。各イテレーションで **1項目** だけ成熟度を上げ、ここに記録する。
honest framing (G8): できていないことは「未」と明記する。

- Actor: `did:web:kataribe.etzhayyim.com` · ADR-2605263600 · **R0 scaffold**
- 不変条件(全イテレーション厳守): R0 では cell 非実行(import時 RuntimeError) ·
  community press / publishing / translation substrate であり commercial journalism /
  paid press class / official publisher ではない(OBSERVATIONAL directory) ·
  G3 no-ad-revenue · G4 非黙示録的トーン · G5 no-commercial-platform · G6 cross-doctrinal ·
  G8 非捏造 · G10 whistleblower 暗号化 · G12 Murakumo-only translation · G14 verified-channel-only ·
  コミットはユーザー明示時のみ

## イテレーション記録

### 2026-06-02
**publication-channel registry hardening。** `registry/channels.seed.edn`(151件・30+管轄・
全件 `unverified-seed`)に fail-closed machine floor を追加: テスト
`70-tools/scripts/audit/test_kataribe_registry_seed.py`(8 invariants, green)— parses+非空
channels / unique channelId / 全件 G14 unverified-seed / accessUrl+provenance+lastVerified 非空
(http(s)許容) / >=12 distinct jurisdictions / channelKind が閉 enum / notes 非空かつ
non-commercial・observational 境界明記 / top-level int freshnessWindowDays。三層 G14 人手検証
ワークフロー `registry/VERIFICATION.md`(per-field checklist + per-jurisdiction official-source
provenance fail-closed + 非商用/観測専用境界 re-check; honest: **0 verified**)を追加。

### 2026-06-17 (loop) — manifest+lexicon charter-gate test (構造ゲート pin)
`test/kataribe/methods/test_charter_gates.cljc` が manifest G1–G13 と 5 canonical EDN lexicon の press/翻訳ゲートを固定する。worldwide channel registry の fail-closed invariants は `test/kataribe/registry_seed_test.clj` が所有し、全 suite は `run_tests.clj` から実行する。

> **2026-06-17 substrate-native migration (ADR-2606160842):** the charter-gate test above was ported Python→Clojure (`methods/test_charter_gates.py` → `methods/test_charter_gates.cljc`, ns `kataribe.methods.test-charter-gates`, reads the lexicons via cheshire/edn) and the Python was pruned. Run via `bb --classpath src:test run_tests.clj` (now `exec bb`) or `bb run test:charter` (all 34 charter suites; 244 tests / 924 assertions green). Assertions unchanged (1:1 port).
