# hongchenglearing
红橙视频学习

note:
1. textview基线计算
2. .gitignore文件设置
3. 自定义view步骤：        
   1. 分析效果
   2. 确定自定义属性，编写attrs.xml
   3. 在布局中使用
   4. 在自定义view中获取自定义属性
   5. 处理onMeasure
   6. 处理onDraw
4. 自定义viewGroup步骤：
   1. onMeasure():
      a. for循环测量子view；
      b. 根据子view计算和指定布局
   2. onLayout():
      a. for循环摆放所有的子view
   3. 如果要绘制，需要重写dispatchDraw
