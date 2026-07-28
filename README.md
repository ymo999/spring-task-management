# spring-task-management

個人でのタスク管理に加え、チームを作成してタスクを共有できるWebアプリケーション。

> 🚧 **開発中**：現在フェーズ2（ユーザー機能）まで完了しています。以下は開発途中の内容です。

---

## 目次
- [概要](#概要)
- [使用技術](#使用技術)
- [実装済み機能](#実装済み機能)
- [今後実装予定の機能](#今後実装予定の機能)
- [画面一覧](#画面一覧)
- [データベース設計](#データベース設計)
- [セットアップ手順](#セットアップ手順)
- [開発の背景](#開発の背景)

---

## 概要

個人・チームの両方でタスクを管理できるタスク管理アプリです。Spring Bootの学習成果のアウトプットを兼ねたポートフォリオとして開発しています。

**主な特徴（予定含む）**
- 会員登録・ログイン機能（Spring Securityによる認証）
- 個人タスクのCRUD管理
- チームを作成し、メンバー間でタスクを共有

---

## 使用技術

| 項目 | 内容 |
|---|---|
| 言語 | Java 17（LTS） |
| フレームワーク | Spring Boot 4.1.0 |
| ビルドツール | Gradle |
| DB | PostgreSQL |
| ORM | Spring Data JPA |
| テンプレートエンジン | Thymeleaf |
| 認証 | Spring Security |
| テスト | JUnit 5, MockMvc（予定） |
| バージョン管理 | Git / GitHub |

---

## 実装済み機能

### ユーザー機能
- ✅ 会員登録（パスワードはBCryptでハッシュ化して保存）
- ✅ ログイン／ログアウト（Spring Security + DB連携のカスタム認証）
- ✅ ユーザープロフィール表示（ログイン中のメールアドレス表示）

### 基盤・セキュリティ
- ✅ PostgreSQLとの接続（環境変数によるDB接続情報の管理、機密情報のGit管理対象外化）
- ✅ Spring Securityによるエンドポイントごとのアクセス制御

---

## 今後実装予定の機能

- ⬜ タスクのCRUD機能（個人タスク）
- ⬜ チームの作成・メンバー招待
- ⬜ チーム内タスクの共有・一覧表示
- ⬜ バリデーション・エラーハンドリングの強化
- ⬜（任意）REST API化・テストコードの追加

---

## 画面一覧

| 画面 | 概要 | 状態 |
|---|---|---|
| ログイン画面 | ログインフォーム | ✅ 実装済み |
| 会員登録画面 | 新規ユーザー登録フォーム | ⬜ 未実装（現在は動作確認用エンドポイントのみ） |
| ホーム画面 | ログイン後の仮画面 | ✅ 実装済み（暫定） |
| プロフィール画面 | ログイン中ユーザーの情報表示 | ✅ 実装済み |
| タスク一覧画面 | 個人タスク／チームタスクの一覧表示 | ⬜ 未実装 |
| タスク詳細・編集画面 | タスクの詳細確認・編集 | ⬜ 未実装 |
| チーム一覧画面 | 所属チームの一覧表示 | ⬜ 未実装 |
| チーム詳細画面 | チームメンバー・タスクの表示、メンバー招待 | ⬜ 未実装 |

---

## データベース設計

**users（ユーザー）**
| カラム名 | 型 | 説明 |
|---|---|---|
| id | BIGINT (PK) | ユーザーID |
| username | VARCHAR | ユーザー名 |
| email | VARCHAR (UNIQUE) | メールアドレス |
| password | VARCHAR | パスワード（ハッシュ化） |
| created_at | TIMESTAMP | 作成日時 |

**teams（チーム）** ※未実装
| カラム名 | 型 | 説明 |
|---|---|---|
| id | BIGINT (PK) | チームID |
| name | VARCHAR | チーム名 |
| owner_id | BIGINT (FK → users.id) | チーム作成者 |
| created_at | TIMESTAMP | 作成日時 |

**team_members（チームメンバー：中間テーブル）** ※未実装
| カラム名 | 型 | 説明 |
|---|---|---|
| id | BIGINT (PK) | ID |
| team_id | BIGINT (FK → teams.id) | チームID |
| user_id | BIGINT (FK → users.id) | ユーザーID |
| joined_at | TIMESTAMP | 参加日時 |

**tasks（タスク）** ※未実装
| カラム名 | 型 | 説明 |
|---|---|---|
| id | BIGINT (PK) | タスクID |
| title | VARCHAR | タスク名 |
| description | TEXT | 詳細 |
| status | VARCHAR | ステータス（未着手／進行中／完了） |
| due_date | DATE | 期限日 |
| team_id | BIGINT (FK → teams.id, NULL可) | 所属チーム（個人タスクの場合はNULL） |
| assignee_id | BIGINT (FK → users.id) | 担当者 |
| created_by | BIGINT (FK → users.id) | 作成者 |
| created_at | TIMESTAMP | 作成日時 |

---

## セットアップ手順

### 前提条件
- JDK 17以上
- PostgreSQL
- Gradle（付属のWrapperを使用可）

### 1. リポジトリのクローン
```bash
git clone <このリポジトリのURL>
cd spring-task-management
```

### 2. PostgreSQLにデータベースを作成
```sql
CREATE DATABASE task_management;
```

### 3. 環境変数の設定

本プロジェクトはDB接続情報（ユーザー名・パスワード等）をソースコードにハードコーディングせず、環境変数から読み込む方式を採用しています。

以下の環境変数を設定してください（IDEの実行構成、またはOSの環境変数として設定）。

| 変数名 | 必須 | デフォルト値 | 説明 |
|---|---|---|---|
| `DB_HOST` | 任意 | `localhost` | DBホスト名 |
| `DB_PORT` | 任意 | `5432` | DBポート番号 |
| `DB_NAME` | 任意 | `task_management` | データベース名 |
| `DB_USERNAME` | 任意 | `postgres` | DB接続ユーザー名 |
| `DB_PASSWORD` | **必須** | なし | DB接続パスワード |

**設定例（IntelliJ実行構成の環境変数欄）**
```
DB_USERNAME=postgres;DB_PASSWORD=your_password
```

### 4. アプリケーションの起動
```bash
./gradlew bootRun
```

起動後、`http://localhost:8080/login` にアクセスしてください。

> 💡 会員登録画面は未実装のため、現時点でユーザーを作成するには一時的な動作確認用エンドポイントを利用するか、直接DBにレコードを投入する必要があります（後日、正式な会員登録画面を実装予定です）。

---

## 開発の背景

Spring Bootの自主学習と並行し、学習内容を実践するアウトプットとして開発しています。

開発の詳細な過程（つまずいた点や学びを含む）は、[`TaskManagementApp_開発ログ.md`](./TaskManagementApp_開発ログ.md) にまとめています。

### AIとの協働について

![Built with Claude](https://img.shields.io/badge/Built%20with-Claude-blueviolet)

本プロジェクトは、AI（Claude）との対話を通じて開発しています。実装方針の相談、エラーのトラブルシューティング、Spring Boot・Spring Securityの概念理解などについて、AIとの質疑応答を重ねながら学習・実装を進めました。コードは提示された内容を理解した上で自身で実装しており、単なるコード生成の丸写しではなく、学習プロセスの一環として活用しています。

