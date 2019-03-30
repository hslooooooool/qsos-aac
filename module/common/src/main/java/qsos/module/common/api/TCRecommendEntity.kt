package qsos.module.common.api

import qsos.library.base.entity.BaseEntity

/**
 * @author : 华清松
 * @date : 2019/1/24
 * @description : 推荐数据实体
 */
class TCRecommendEntity : BaseEntity {
    /**
     * is_history : true
     * counts : 1
     * feedList : [{"post_id":13828311,"type":"multi-photo","url":"https://tuchong.com/1107603/13828311/","site_id":"1107603","author_id":"1107603","published_at":"2016-12-11 08:14:40","excerpt":"海纳百川有容乃大,壁立千仞无欲则刚","favorites":537,"comments":27,"delete":false,"update":false,"content":"海纳百川有容乃大,壁立千仞无欲则刚","title":"海纳百川有容乃大","image_count":1,"images":[{"img_id":21522551,"user_id":1107603,"title":"001","excerpt":"","width":2500,"height":1646,"description":""}],"tags":["Kase卡色现金大奖赛","风光","色彩","佳能","自然","江苏"],"event_tags":["Kase卡色现金大奖赛"],"data_type":"post","created_at":"2016-12-11 08:14:40","sites":[],"site":{"site_id":"1107603","type":"user","name":"燕子Photography","domain":"","description":"自然风光摄影师；图虫网资深摄影师；江苏摄影家协会会员；A色艺术摄影教学机构辅导老师；热爱风光摄影和城市建筑摄影（微信：jiangyan-741006）（新浪微博：燕子Photography）","followers":3662,"url":"https://tuchong.com/1107603/","icon":"https://s1.tuchong.com/sites/110/1107603/logo_small.jpg?4","verified":false,"verified_type":0,"verified_reason":"","verifications":0,"verification_list":[]},"recom_type":"","rqt_id":"","is_favorite":false}]
     * message : 添加成功
     * more : true
     * result : SUCCESS
     */

    var isIs_history: Boolean = false
    /*返回数据长度*/
    var counts: Int = 0
    /*返回信息*/
    var message: String? = null
    /*是否有更多*/
    var isMore: Boolean = false
    /*是否成功，SUCCESS，FAIL*/
    var result: String? = null
    /*详细数据列表*/
    var feedList: ArrayList<FeedListBean>? = null

    class FeedListBean : BaseEntity {
        /**
         * post_id : 13828311
         * type : multi-photo
         * url : https://tuchong.com/1107603/13828311/
         * site_id : 1107603
         * author_id : 1107603
         * published_at : 2016-12-11 08:14:40
         * excerpt : 海纳百川有容乃大,壁立千仞无欲则刚
         * favorites : 537
         * comments : 27
         * delete : false
         * update : false
         * content : 海纳百川有容乃大,壁立千仞无欲则刚
         * title : 海纳百川有容乃大
         * image_count : 1
         * images : [{"img_id":21522551,"user_id":1107603,"title":"001","excerpt":"","width":2500,"height":1646,"description":""}]
         * tags : ["Kase卡色现金大奖赛","风光","色彩","佳能","自然","江苏"]
         * event_tags : ["Kase卡色现金大奖赛"]
         * data_type : post
         * created_at : 2016-12-11 08:14:40
         * sites : []
         * site : {"site_id":"1107603","type":"user","name":"燕子Photography","domain":"","description":"自然风光摄影师；图虫网资深摄影师；江苏摄影家协会会员；A色艺术摄影教学机构辅导老师；热爱风光摄影和城市建筑摄影（微信：jiangyan-741006）（新浪微博：燕子Photography）","followers":3662,"url":"https://tuchong.com/1107603/","icon":"https://s1.tuchong.com/sites/110/1107603/logo_small.jpg?4","verified":false,"verified_type":0,"verified_reason":"","verifications":0,"verification_list":[]}
         * recom_type :
         * rqt_id :
         * is_favorite : false
         */

        /*用于翻页*/
        var post_id: Int = 0
        /*multi-photo表组图*/
        var type: String? = null
        /*作者主页*/
        var url: String? = null
        /*站点ID*/
        var site_id: String? = null
        /*作者ID*/
        var author_id: String? = null
        /*发表日期*/
        var published_at: String? = null
        /*摘要*/
        var excerpt: String? = null
        /*多少人喜爱*/
        var favorites: Int = 0
        /*多少人评论*/
        var comments: Int = 0
        /*是否已删除*/
        var isDelete: Boolean = false
        /**/
        var isUpdate: Boolean = false
        /*描述*/
        var content: String? = null
        /*标题*/
        var title: String? = null
        /*图片数*/
        var image_count: Int = 0
        /*数据类型*/
        var data_type: String? = null
        /*创建时间*/
        var created_at: String? = null
        /**/
        var site: SiteBean? = null
        var recom_type: String? = null
        var rqt_id: String? = null
        var isIs_favorite: Boolean = false
        var images: List<ImagesBean>? = null
        var tags: List<String>? = null
        var event_tags: List<String>? = null
        var sites: List<*>? = null

        class SiteBean {
            /**
             * site_id : 1107603
             * type : user
             * name : 燕子Photography
             * domain :
             * description : 自然风光摄影师；图虫网资深摄影师；江苏摄影家协会会员；A色艺术摄影教学机构辅导老师；热爱风光摄影和城市建筑摄影（微信：jiangyan-741006）（新浪微博：燕子Photography）
             * followers : 3662
             * url : https://tuchong.com/1107603/
             * icon : https://s1.tuchong.com/sites/110/1107603/logo_small.jpg?4
             * verified : false
             * verified_type : 0
             * verified_reason :
             * verifications : 0
             * verification_list : []
             */

            var site_id: String? = null
            var type: String? = null
            var name: String? = null
            var domain: String? = null
            var description: String? = null
            var followers: Int = 0
            var url: String? = null
            var icon: String? = null
            var isVerified: Boolean = false
            var verified_type: Int = 0
            var verified_reason: String? = null
            var verifications: Int = 0
            var verification_list: List<*>? = null
        }

        class ImagesBean {
            /**
             * img_id : 21522551
             * user_id : 1107603
             * title : 001
             * excerpt :
             * width : 2500
             * height : 1646
             * description :
             */

            var img_id: Int = 0
            var user_id: Int = 0
            var title: String? = null
            var excerpt: String? = null
            var width: Int = 0
            var height: Int = 0
            var description: String? = null
        }
    }
}
