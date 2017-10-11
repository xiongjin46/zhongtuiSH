package com.push.PushMerchant.weight.selectImage;


import com.push.PushMerchant.base.BaseBean;

/**
 * 类说明：
 * 
 * @author AMeng
 * @date 2015-11-17
 * @version 1.0
 */
public class ImageItem extends BaseBean {
	String path;
	int position;

	public ImageItem(String path) {
		super();
		this.path = path;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
