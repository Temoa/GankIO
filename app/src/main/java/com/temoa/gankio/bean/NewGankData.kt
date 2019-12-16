package com.temoa.gankio.bean

import java.io.Serializable

/**
 * Created by Temoa
 * on 2016/10/20 17:19
 */
class NewGankData : Serializable {
  /**
   * error : false
   * results : [{"_id":"58080e33421aa91369f95950","createdAt":"2016-10-20T08:22:11.221Z","desc":"以组件的方式维护列表加载状态","images":["http://img.gank.io/fb48a6eb-7d39-4e9e-a04a-4af780e44e64"],"publishedAt":"2016-10-20T11:39:59.546Z","source":"chrome","type":"Android","url":"https://github.com/gitsindonesia/baso","used":true,"who":"gitsindonesia"},{"_id":"58080e60421aa90e6f21b411","createdAt":"2016-10-20T08:22:56.842Z","desc":"风车效果的 LayoutManager，做的很漂亮。","images":["http://img.gank.io/1c25e996-6592-42a2-82dc-bdbf15daf3f0"],"publishedAt":"2016-10-20T11:39:59.546Z","source":"chrome","type":"Android","url":"https://github.com/Cleveroad/FanLayoutManager","used":true,"who":"代码家"},{"_id":"58058e16421aa90e799ec1b5","createdAt":"2016-10-18T10:51:02.87Z","desc":"文本编辑工具，轻松搞定一段文字不同颜色的需求","images":["http://img.gank.io/c318a175-59f0-4dbe-b2e6-8ec886fb2a61"],"publishedAt":"2016-10-19T11:47:24.946Z","source":"web","type":"Android","url":"https://github.com/THEONE10211024/ColorPhrase","used":true,"who":"xiayong"},{"_id":"5805ade4421aa90e6f21b3f3","createdAt":"2016-10-18T13:06:44.306Z","desc":"又一个 Material ViewPagerIndicator （指示器）","images":["http://img.gank.io/6fa3957e-80ca-46a9-bf93-73a8aad0113f"],"publishedAt":"2016-10-19T11:47:24.946Z","source":"chrome","type":"Android","url":"https://github.com/ronaldsmartin/Material-ViewPagerIndicator","used":true,"who":"代码家"},{"_id":"5805ae97421aa90e6f21b3f5","createdAt":"2016-10-18T13:09:43.964Z","desc":"教你用 Google Vision Api 实现的人脸检测效果。","publishedAt":"2016-10-19T11:47:24.946Z","source":"chrome","type":"Android","url":"https://hackernoon.com/machine-learning-for-android-developers-with-the-mobile-vision-api-part-1-face-detection-e7e24a3e472f#.k02j2joo0","used":true,"who":"代码家"},{"_id":"5806ae3e421aa90e6f21b3fa","createdAt":"2016-10-19T07:20:30.116Z","desc":"做的很棒的一个 Android 辐射数据统计效果","images":["http://img.gank.io/07c56cae-9edc-4028-845e-168cc8a515a6"],"publishedAt":"2016-10-19T11:47:24.946Z","source":"chrome","type":"Android","url":"https://github.com/DmitriyZaitsev/RadarChartView","used":true,"who":"代码家"},{"_id":"5806b10f421aa90e799ec1c2","createdAt":"2016-10-19T07:32:31.743Z","desc":"ColorTextView：让特定的文字呈现出特定的颜色，很小的一个工具库，但是特定场景下格外有用。","images":["http://img.gank.io/7a2eb02f-c366-4461-9989-6f1bbc5ab751"],"publishedAt":"2016-10-19T11:47:24.946Z","source":"chrome","type":"Android","url":"https://github.com/zhonghanwen/ColorTextView","used":true,"who":"代码家"},{"_id":"5806b5c3421aa90e6f21b3fb","createdAt":"2016-10-19T07:52:35.528Z","desc":"在MDCC中冯森林老师的《回归初心，从容器化到组件化》，为我们这些没有那么多精力折腾黑科技开发者们打开了另一扇门","publishedAt":"2016-10-19T11:47:24.946Z","source":"web","type":"Android","url":"http://kymjs.com/code/2016/10/18/01","used":true,"who":"张涛"},{"_id":"5800a66b421aa95dd351b12e","createdAt":"2016-10-14T17:33:31.111Z","desc":"自定义 自动补充 email 的 EditText","images":["http://img.gank.io/3ba627c7-23d2-4476-8527-7d872b608de1"],"publishedAt":"2016-10-18T11:50:35.205Z","source":"web","type":"Android","url":"https://github.com/wangshaolei/AutoFillEmailEditText","used":true,"who":"fearless"}]
   */
  var isError = false
  /**
   * _id : 58080e33421aa91369f95950
   * createdAt : 2016-10-20T08:22:11.221Z
   * desc : 以组件的方式维护列表加载状态
   * images : ["http://img.gank.io/fb48a6eb-7d39-4e9e-a04a-4af780e44e64"]
   * publishedAt : 2016-10-20T11:39:59.546Z
   * source : chrome
   * type : Android
   * url : https://github.com/gitsindonesia/baso
   * used : true
   * who : gitsindonesia
   */
  var results: ArrayList<Results>? = null

  class Results : Serializable {
    private var _id: String? = null
    var createdAt: String? = null
    var desc: String? = null
    var publishedAt: String? = null
    var source: String? = null
    var type: String? = null
    var url: String? = null
    var isUsed = false
    var who: String? = null
    var images: List<String>? = null
    fun get_id(): String? {
      return _id
    }

    fun set_id(_id: String?) {
      this._id = _id
    }

    companion object {
      private const val serialVersionUID = -201211611201L
    }
  }

  companion object {
    private const val serialVersionUID = -201211611211L
  }
}