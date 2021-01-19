package com.eju.demo.api

import com.eju.service.BaseResult
import com.eju.service.PagedList
import com.eju.service.UrlManager
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*
import java.io.Serializable

interface DemoService{


    @GET("/rescue/detail")
    @Headers("version:1.0.9")
    fun getHelpDetail(@Query("rescue_id") id:String): Call<BaseResult<HelpDetail>>


    @GET("/rescue/detail")
    @Headers("version:1.0.9")
    suspend fun getHelpDetail1(@Query("rescue_id") id:String): BaseResult<HelpDetail>


    @FormUrlEncoded
    @POST("/message/list/")
    @Headers("version:1.0.9","user-id:3","user-token:2eb3fd2d9c3cf1e798d5c7805adb86449f8dd425")
    fun msgList(@Field("start_index")start_index:Int,
                @Field("count")count:Int): Call<BaseResult<PagedList<Message>>>
}




data class HelpDetail(
    var address: String = "",
    var avatar: String = "",
    var category: Int = 0,
    var category_desc: String = "",
    var comments: Int = 0,
    var create_time: String = "",
    var feature: String = "",
    var gender: String = "",
    var is_recommend: Int = 0,
    var kind: Int = 0,
    var kind_desc: String = "",
    var kind_name: String = "",
    var lat: Double = 0.0,
    var lon: Double = 0.0,
    var mobile: String = "",
    var modify_time: String = "",
    var pickup_time: String = "",
    var pics: String = "",
    var reason: String = "",
    var rescue_id: Int = 0,
    var share_counts: Int = 0,
    var title: String = "",
    var user_id: Int = 0,
    var user_name: String = "",
    var view_counts: Int = 0,
    var wx: String = ""
):Serializable

data class Message(

    val business_id:String,
    val business_type:Int,  //业务类型： 1-救助 2-看游记
    val business_title:String,
    val business_cover:String,  //封面图，可能存在多个以英文逗号分隔
    val business_avatar:String,

    val user_id:String,  //被留言人的用户id
    val username:String, //被留言人的用户名
    val content:String,  //被留言人的留言内容

    val commentator_user_id:String,  //留言人的用户id
    val commentator_user_name:String, //留言人的用户名
    val commentator_content:String,   //	留言人的留言内容
    val commentator_user_avatar:String,   //	留言人的头像
    @SerializedName("commentator_comment_id")
    val comment_id:String,   //	留言id
    val create_time:String,   //create_time
    val has_read:Int

):Serializable