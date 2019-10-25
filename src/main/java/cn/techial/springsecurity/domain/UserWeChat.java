package cn.techial.springsecurity.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842
 * <p>
 * openid	用户的唯一标识
 * nickname	用户昵称
 * sex	用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
 * province	用户个人资料填写的省份
 * city	普通用户个人资料填写的城市
 * country	国家，如中国为CN
 * headimgurl	用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
 * privilege	用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
 * unionid	只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
 *
 * @author techial
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserWeChat {

    private Integer id;

    @JsonProperty("openid")
    private String openId;

    @JsonProperty("appid")
    private String appId;

    @JsonProperty("nickname")
    private String nickName;

    private String sex;

    private String language;

    private String city;

    private String country;

    @JsonProperty("province")
    private String province;

    @JsonProperty("headimg_url")
    private String avatar;

    @JsonProperty("bind_wechat_time")
    private Long bondTime;

    @JsonProperty("update_wechat_time")
    private Long updatedTime;

    @JsonProperty("unionid")
    private String unionId;

}
