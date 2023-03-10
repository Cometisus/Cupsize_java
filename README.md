# バストサイズ推定プログラム Cupsize_java
## 概要
2次元キャラクターの体型データからバストサイズを推定しようというn番煎じのプログラムです。
このプログラム独自の点としては、現実の体型データ（[AIST人体寸法データベース 1991-92](https://www.airc.aist.go.jp/dhrt/91-92/index.html)）をもとに統計的な分析を行い、
その結果をもとに体型の推定を行なっているという点です。

## 使い方（簡易版）
### 起動
- Windows版：cupsize.batをダブルクリック
- Mac版：cupsize.commandをダブルクリック
- jar版：JDKをインストールし、Cupsize.jarをダブルクリック、もしくはコマンド実行（java -jar Cupsize.jar）
Java18.0.2での動作を確認しております。

### 入力
入力値は以下の通りです。
- 身長
-　（トップ）バスト
- ウエスト
- ヒップ
- 体重
- 太もも

このうち、バストとウエストは入力が必須です。
身長については、バストサイズの推定には必要がありませんが、その他の体型データの推定に用いられているため、
未入力の場合、出力されるデータが減ります。
その他の値はオプションです。

- 計算に平均身長を利用する

  体型の計算に現実の身長データの平均値を利用します。

値を入力しCalculateボタンを押すことで計算が行われ、その結果を表示します。

### 出力
出力は大きく3つの項目に分かれています。

- Basic

バストサイズに関係する体型データを表示します。
  - アンダーバスト
  - バストサイズ
  - カップサイズ
    
    ウエストの値とバストの値から算出されたアンダーバスト、バストサイズ、カップサイズの値を表示します。
  
  - アドバイス
  - フィットする市販品
  - 使用できるサイズ全般

    [美少女、バストカップ数測定スクリプト](http://negifukyu.x.fc2.com/bustcheck/cupchecker.html)を大いに参考にした部分です。（問題があれば削除します。）
    比較のために用意した部分であり、また元のソースコードの可読性を自分なりに上げてみたというものです。
    
  - ゴールデンカノン
    
    こちらも[美少女、バストカップ数測定スクリプト](http://negifukyu.x.fc2.com/bustcheck/cupchecker.html)を大いに参考にしていますが、いくつかデータを加えています。
    アンダーバストについてはゴールデンカノンのデータを見つけられませんでしたが（そもそもあるのかが不明）上記サイトの解説内に、
    身長の0.44倍程度がアンダーバストの理想値であるという記述があったため、それを採用しています。
  
- Statistics
  
  このプログラムの計算はあくまでも推定であるため、実際とはずれが出る場合があります。
  ここでは、計算値からバストサイズがどの程度ずれる可能性があるのかを統計データから計算し、その結果を示します。
  - 標準誤差
    
    推定量の標準偏差、すなわち同じバスト、同じウエストを持った女性を集めた時の、バストサイズのばらつき具合を示します。
    
  - 確率
  
    同じバスト、同じウエストを持った女性が、どのバストサイズである確率が何%であるのかを示します。
  
  - バストサイズ偏差値
    
    統計データ、および二次元キャラクターの体型データを用いて、入力された体型をもつ女性のバストサイズの偏差値を求めます。
    平均値が50、平均よりも値が大きいほどバストサイズが大きいことを示します。
    
  - 上位
  
    入力された体型をもつ女性のバストサイズが上位何%程度の値なのかを偏差値から計算します。
    
- Others

  バストサイズ以外の体型データについて計算した結果を表示します。
  
  - BMI
    
    入力されたキャラクターのBMI値を計算します。
    BMI：一般的なBMIの計算式をもとに計算したBMI値です。
    判定(BMI)：日本肥満学会の基準の基準に照らし合わせた時、どの分類に属するのかを示したものです。
    新しいBMI：BMIの改良の一つにロイド・N・トレフェゼンによる指標というものがあるようなので、追加したものになります。
              （従来のBMIと区別するための名称が見つからなかったため、提唱者本人のページに記載されていた"new BMI"という語をそのまま訳しています。）
    判定(新しいBMI)：新しいBMIの値を日本肥満学会の基準の基準に照らし合わせた時、どの分類に属するのかを示したものです。
    適正体重：日本肥満学会では、BMIが22の場合を標準体重としていることから、入力された身長からBMIが22となる時の体重を求めたものを表示します。
    美容体重：BMIが20の場合は美容体重と呼ばれており、入力された身長からBMIが20となる時の体重を求めたものを表示します。
    
  - バスト関連体格指数
  
    バストサイズに関係する体格指数を表示します。
    
    - バスト指数
    
      バスト指数（トップバスト ÷ 身長）を計算します。
      判定はワコールの基準に照らし合わせた時、どの分類に属するのかを示したものです。
      - 貧弱バスト：0.50未満
      - 標準バスト：0.50〜0.54
      - 美しいバスト：0.54〜0.56
      - 大きなバスト：0.56以上
    
    - バストサイズ指数
    
      バスト指数の計算式のトップバストをバストサイズに置き換えてみたものです。
    
    - プロポーション指数
    
      プロポーション指数（(トップバスト×身長×ヒップ)÷(アンダーバスト×ウエスト^2) ）を計算します。
    
    - PI
    
      プロポーションインデックス（PI）値（{ウエスト+(太股左+太股右)-ヒップ-(トップバスト-アンダーバスト)}÷身長）を計算します。
      判定はPI値の基準に照らし合わせた時、どの分類に属するのかを示したものです。
      - メリハリゴールド：30.0未満
      - メリハリシルバー：30.0～35.0
      - メリハリ体型：35.0～37.5
      - 標準体型：PI値目安37.5～42.5
      - チャレンジ体型：42.5以上

      
    - 乳房の片側体積
    
      乳房の片側の体積を求めます。[おっぱい解析向けライブラリを書いてみる!? 第2回](http://www.hirax.net/diaryweb/2013/02/18.html)を参考にしています。
      
    - 乳房の片側重量
    
      乳房の片側の重量を求めます。上記の体積に脂肪の比重（約0.9）をかけたものになります。
      
    - 予測体重
    
      実際の体型データを参考にして、ウエストと身長の値のから体重の値を推測します。
      ただし、これだけではバスト部分の考慮がされていないため、上記の乳房の片側重量の2倍を最後に加えて出力しています。

  - その他機能

    - Quit
    
      終了します
    
    - Clear
    
      入力値と計算結果をクリアします
    
    - Reset
    
      計算結果をクリアし、入力値を初期化します
    
    - Database
    
      二次元キャラクターの体型値を格納したファイル（CharacterData.dat）からデータを読み取り一覧を表示します。
      名前やバストサイズ等でソート、検索をすることも可能です。
      また、キャラクターを選択し、Selectボタンを押すことで、そのキャラクターの体型データを自動で入力します。
      Mainボタンで入力画面に戻ります。Quitボタンで終了します。

- 今後の予定
  
  - 詳しい解説を作る
  - キャラクターデータを充実させる
  - Webページ版（JavaScript）を作る
  - より良い推定方法の検討
  - その他デバッグ、機能追加等

- 参考
  - [AIST人体寸法データベース 1991-92](https://www.airc.aist.go.jp/dhrt/91-92/index.html)
  - [美少女、バストカップ数測定スクリプト](http://negifukyu.x.fc2.com/bustcheck/cupchecker.html)
  - [おっぱい解析向けライブラリを書いてみる!? 第2回](http://www.hirax.net/diaryweb/2013/02/18.html)
