package com.youzhi.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Generated by JFinal, do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     _MappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class _MappingKit {
	
	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("cart", "c_id", Cart.class);
		arp.addMapping("goods", "id", Goods.class);
		arp.addMapping("order", "o_id", Order.class);
		arp.addMapping("student", "id", Student.class);
	}
}
