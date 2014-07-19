package com.wenzchao.wechat.entity.message.mass;

/**
 * 群发图片信息
 * 
 * @author Venz
 *
 */
public class ImageMass extends Mass {

	private Media image;

	public Media getImage() {
		return image;
	}

	public void setImage(Media image) {
		this.image = image;
	}

}
