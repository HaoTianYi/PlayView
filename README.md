# PlayView
一个仿有道词典播放音乐的控件

## 运行效果

![201610211208view](http://oaxelf1sk.bkt.clouddn.com/201610211208view.gif)
## 基本使用

- 添加布局

```
    <win.haotinayi.playviewlib.PlayView
        android:id="@+id/pv"
        android:layout_width="150dp"
        android:layout_height="150dp"
        hty:haotyBackground="#80DEEA"
        hty:haotyColColor="#304FFE"
    />
```

- 开始动画

```
    public void start(View view) {
        PlayView playView = (PlayView) findViewById(R.id.pv);
        playView.startViewAnim();
    }
```
