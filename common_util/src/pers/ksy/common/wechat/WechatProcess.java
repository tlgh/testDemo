package pers.ksy.common.wechat;


/**
 * 微信xml消息处理流程逻辑类
 *
 * <p>detailed comment
 * @author ksy 2015年8月3日
 * @see
 * @since 1.3.2
 */
public class WechatProcess {
	private WechatHandler wechatHandler;
	private Class<? extends WechatHandler> handlerClass;
	private InstanceType instanceType;

	
	public WechatProcess(Class<? extends WechatHandler> handlerClass,
			InstanceType instanceType) {
		super();
		this.handlerClass = handlerClass;
		this.instanceType = instanceType;
	}

	/**
	 * 解析处理xml、获取智能回复结果（通过图灵机器人api接口）
	 * 
	 * @param xml 接收到的微信数据
	 * @return 最终的解析结果（xml格式数据）
	 */
	public String processWechatMag(String xml) {
		/** 解析xml数据 */
		ReceiveXmlEntity xmlEntity = new ReceiveXmlProcess().getMsgEntity(xml);

		String result = getWechatHandler().massageHandle(xmlEntity, xml);

		result = new FormatXmlProcess().formatXmlAnswer(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), result);

		return result;
	}

	public WechatHandler getWechatHandler() {
		if (null == wechatHandler) {
			switch (instanceType) {
			case REFLECT:
				try {
					wechatHandler = handlerClass.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				break;
			case SPRING:
				wechatHandler = pers.ksy.common.spring4.SpringUtil.getBean(handlerClass);
				break;
			}
		}
		return wechatHandler;
	}

	public enum InstanceType {
		REFLECT, SPRING
	}
}
