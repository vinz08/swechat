package com.wenzchao.wechat.entity.message.in;

/**
 * 扫描带参数二维码事件
 * 
 * @author Venz
 * 
 */
public class QRCodeEvent extends KeyEvent {
	
	private String Ticket; // 二维码的ticket，可用来换取二维码图片

	public String getTicket() {
		return Ticket;
	}

	public void setTicket(String ticket) {
		Ticket = ticket;
	}

}
