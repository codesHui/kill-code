package com.softvan.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.springframework.util.StringUtils;

/**
 * 验证码生成器。
 * <pre>
 * 可生成数字、大写、小写字母及三者混合类型的验证码。
 * 支持自定义验证码字符数量；
 * 支持自定义验证码图片的大小；
 * 支持自定义需排除的特殊字符；
 * 支持自定义干扰线数量；
 * 支持自定义验证码图文颜色；
 * </pre>
 */
public class VerifyCodeUtil {
	/**
	 * 验证码类型仅为数字，即0-9.
	 */
	public static final int TYPE_NUM_ONLY = 0;
	/**
	 * 验证码类型仅为字母，即大小写字母混合。
	 */
	public static final int TYPE_LETTER_ONLY = 1;
	/**
	 * 验证码类型为数字和大小写字母混合。
	 */
	public static final int TYPE_ALL_MIXED = 2;
	/**
	 * 验证码类型为数字和大写字母混合。
	 */
	public static final int TYPE_NUM_UPPER = 3;
	/**
	 * 验证码类型为数字和小写字母混合。
	 */
	public static final int TYPE_NUM_LOWER = 4;
	/**
	 * 验证码类型仅为大写字母。
	 */
	public static final int TYPE_UPPER_ONLY = 5;
	/**
	 * 验证码类型仅为小写字母。
	 */
	public static final int TYPE_LOWER_ONLY = 6;
	
	/**
	 * 生成验证码字符串。
	 * @param type
	 * 验证码类型。
	 * @param length
	 * 验证码长度，要求大于0的整数。
	 * @param exclude
	 * 需排除的特殊字符(无需排除则为null)。
	 * @return
	 * 验证码字符串。
	 */
	public static String generateTextCode(int type, int length, String exclude){
		if(length <= 0) return null;
		StringBuffer verifyCodeBuffer = new StringBuffer();
		int i = 0;
		Random random = new Random(System.currentTimeMillis());
		switch(type){
			case TYPE_NUM_ONLY://仅数字
				while(i < length){
					int t = random.nextInt(10);
					//排序特殊字符
					if(StringUtils.isEmpty(exclude) || exclude.indexOf(((Integer)t).toString()) < 0){
						verifyCodeBuffer.append(t);
						i++;
					}
				}
				break;
			case TYPE_LETTER_ONLY://仅大小字母混合
				while(i < length){
					int t = random.nextInt(123);
					if((t >= 97 || (t >= 65 && t <= 90)) && (StringUtils.isEmpty(exclude) || exclude.indexOf((char)t) < 0)){
						verifyCodeBuffer.append((char)t);
						i++;
					}
				}
				break;
			case TYPE_ALL_MIXED://数字、大小写字母混合
				while(i < length){
					int t = random.nextInt(123);
					if((t >= 97 || (t >= 65 && t <=90) || (t >= 48 && t <= 57)) && (StringUtils.isEmpty(exclude) || exclude.indexOf((char)t) < 0)){
						verifyCodeBuffer.append((char)t);
						i++;
					}
				}
				break;
			case TYPE_NUM_UPPER://数字和大写字母混合
				while(i < length){
					int t = random.nextInt(91);
					if((t >= 65 || (t >= 48  && t <= 57)) && (StringUtils.isEmpty(exclude) || exclude.indexOf((char)t) < 0)){
						verifyCodeBuffer.append((char)t);
						i++;
					}
				}
				break;
			case TYPE_NUM_LOWER://数字和小写字母混合
				while(i < length){
					int t = random.nextInt(123);
					if((t >= 97 || (t >= 48 && t <= 57)) && (StringUtils.isEmpty(exclude) || exclude.indexOf((char)t) < 0)){
						verifyCodeBuffer.append((char)t);
						i++;
					}
				}
				break;
			case  TYPE_UPPER_ONLY://仅大写字母
				while(i < length){
					int t = random.nextInt(91);
					if((t >= 65) && (StringUtils.isEmpty(exclude) || exclude.indexOf((char)t) < 0)){
						verifyCodeBuffer.append((char)t);
						i++;
					}
				}
				break;
			case TYPE_LOWER_ONLY://小写字母
				while(i < length){
					int t = random.nextInt(123);
					if((t >= 97) && (StringUtils.isEmpty(exclude) || exclude.indexOf((char)t) < 0)){
						verifyCodeBuffer.append((char)t);
						i++;
					}
				}
				break;
		}
		return verifyCodeBuffer.toString();
	}
	/**
	 * 生成随机颜色。
	 * @return
	 * 随机颜色。
	 */
	private static Color generateRandomColor(){
		Random random = new Random();
		return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
	}
	/**
	 * 已有验证码，生成验证码图片。
	 * @param textCode
	 * 文本验证码。
	 * @param width
	 * 图片宽度
	 * (注意：宽度过小，容易造成验证码文本显示不全，如4个字符的文本可使用85到90的宽度)
	 * @param height
	 * 图片高度。
	 * @param interLine
	 * 图片干扰线的条数。
	 * @param randromLocation
	 * 每个字符的高低位置是否随机。
	 * @param backColor
	 * 图片颜色，若为null则随机。
	 * @param foreColor
	 * 字体颜色，若为null则随机。
	 * @param lineColor
	 * 干扰线颜色，若为null则随机。
	 * @return
	 * 图片缓存对象。
	 */
	public static BufferedImage generateImageCode(String textCode, int width, int height, int interLine, boolean randromLocation,Color backColor, Color foreColor, Color lineColor){
		//创建内存图像
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//获取图形上下文
		Graphics graphics = bufferedImage.getGraphics();
		//画背景图
		graphics.setColor(backColor == null ? generateRandomColor() : backColor);
		graphics.fillRect(0, 0, width, height);
		//画干扰线
		if(interLine > 0){
			Random random = new Random();
			int x = 0, y = 0, x1 = width, y1 = 0;
			for(int i = 0; i < interLine; i++){
				graphics.setColor(lineColor == null ? generateRandomColor() : lineColor);
				y = random.nextInt(height);
				y1 = random.nextInt(height);
				graphics.drawLine(x, y, x1,y1);
			}
		}
		if(!StringUtils.isEmpty(textCode)){
			//字体大小为图片高度的80%
			int fsize = (int)(height * 0.8),
				 fx = height - fsize,
				 fy = fsize;
			//设置字体
			graphics.setFont(new Font("Default", Font.PLAIN, fsize));
			//绘制验证码字符
			for(int i = 0; i < textCode.length(); i++){
				fy =  randromLocation ? (int)((Math.random() * 0.3 + 0.6) * height) : fy;
				graphics.setColor(foreColor == null ? generateRandomColor() : foreColor);
				graphics.drawString(textCode.charAt(i) + " ",  fx, fy);
				fx += fsize * 0.9;
			}
		}
		graphics.dispose();
		return bufferedImage;
	}
	/**
	 * 生成图片验证码。
	 * @param type
	 * 验证码类型，参见本类静态属性。
	 * @param length
	 * 验证码长度，要求大于0的整数。
	 * @param exclude
	 * 需排除的特殊字符。
	 * @param width
	 * 图片宽度，若过小，容易造成显示不全，如4字符的文本可使用85-90的宽度。
	 * @param height
	 * 图片高度。
	 * @param interLine
	 * 图片中干扰线的条数。
	 * @param randromLocation
	 * 每个字符的高低位置是否随机。
	 * @param backColor
	 * 图片颜色，若为null则随机。
	 * @param foreColor
	 * 字体颜色，若为null则随机。
	 * @param lineColor
	 * 干扰线颜色，若为null则随机。
	 * @return
	 * 图片缓存对象。
	 */
	public static BufferedImage generateImageCode(int type,int length,String exclude, int width, int height, int interLine, boolean randromLocation,Color backColor, Color foreColor, Color lineColor){
		String textCode = generateTextCode(type, length, exclude);
		return generateImageCode(textCode, width, height, interLine, randromLocation, backColor, foreColor, lineColor);
	}
}