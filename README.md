# SelfViewDemo
高仿QQ健康 有动画
##效果
![图片标题](https://leanote.com/api/file/getImage?fileId=57164391ab64415fa600184f)
##实现主要过程
 - 圆弧
>采用自定义圆弧，重写动画效果实现颜色圆弧变动

 - 中间的内容
>采用 RelativeLayout布局，数字变动监听圆弧控件内的动画内的applyTransformation内的interpolatedTime的值
 - 虚线
>采用shape划线，当然这里有坑，在4.0以上需要设置setLayerType(View.LAYER_TYPE_SOFTWARE,null)

 - 圆柱
>这是采用自定义的圆柱，两个圆加上一条直线就是这个效果
 
 - 圆柱等分布局
>圆柱和小面的日期一起在一个大的RelativeLayout作为一个Item放在LinearLayout中，设置weight 为1

 - 圆形头像
>下面的圆形头像则偷懒用了CircleImageView
