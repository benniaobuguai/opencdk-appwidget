package com.opencdk.appwidget.utils;

import java.util.Random;

public class DataProvider {

	public static final String[] NEWS_ARRAY = new String[] { "习近平两会全纪录  两会专题", "任正非李彦宏都欣赏的他，成功之路为何戛然而止",
			"85后中国最牛富二代坐拥900亿 比王思聪低调", "谷歌工程师：AlphaGo是如何学会下围棋的", "司机将被AlphaGo淘汰 想像力与经验成人类最后堡垒",
			"阿里巴巴探索虚拟现实VR 力图增强3D网上购物体验", "AlphaGo背后：谷歌有支“机器人军团”", "谷歌花2.5亿美元买下8栋办公楼:可容3000员工",
			"金蝶国际公布2015全年业绩 云服务业务大幅增长80.1%", "从最新数据看中国可穿戴市场 暗流涌动 缺乏惊天一击", "papi酱只有一个 模仿不是捷径",
			"从最新数据看中国可穿戴市场 暗流涌动 缺乏惊天一击", "京东拍卖频道正式上线 包含珍品和海关拍卖等业务", "智人时代：人类智能与机器智能平分秋色", "小米和360互诉不正当竞争 均索赔2000万元",
			"从“德国匠心之旅”看华为消费者云业务的扎实功力", "大众旅游时代下的微观样本：酒店创业风口来了？", "腾讯2015年全年总收入1028.63亿元 同比增长22%" };

	public static int[] getRandomArray(int total) {
		int[] array = new int[total];
		for (int i = 0; i < total; i++) {
			array[i] = i;
		}
		Random random = new Random();
		int temp2;
		int end = total;
		for (int i = 0; i < total; i++) {
			int temp = random.nextInt(end);
			temp2 = array[temp];
			array[temp] = array[end - 1];
			array[end - 1] = temp2;
			end--;
		}

		return array;
	}

	public static String[] getRandomNewsArray(int count) {
		int[] randomArray = getRandomArray(count);
		String[] newsArray = new String[count];
		for (int i = 0; i < count; i++) {
			newsArray[i] = NEWS_ARRAY[randomArray[i]];
		}

		return newsArray;
	}
	
}
