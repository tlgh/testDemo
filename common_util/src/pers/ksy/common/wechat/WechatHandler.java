package pers.ksy.common.wechat;

import pers.ksy.common.wechat.ReceiveXmlEntity;

/**
 * 微信消息接收处理器
 *
 * <p>detailed comment
 * @author ksy 2015年8月3日
 * @see
 * @since 1.3.2
 */
public interface WechatHandler {
	
	/**
	 * 微信消息处理
	 * @param xmlEntity
	 * @param xml
	 * @return
	 */
	String massageHandle(ReceiveXmlEntity xmlEntity, String xml);
}
