package com.softvan.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
/**
 * 模块集合定义。
 * */
public class ModuleDefineCollection implements Collection<ModuleDefine>,Comparator<ModuleDefine>, Serializable {
	private static final long serialVersionUID = 1L;
	private List<ModuleDefine> list;
	/**
	 * 构造函数。
	 * */
	public ModuleDefineCollection(){
		this.list = new ArrayList<ModuleDefine>();
	}
	/**
	 * 获取模块。
	 * @param index
	 * 	索引。
	 * @return 模块。
	 * */
	public ModuleDefine item(int index){
		if(index > -1 && index < this.size()){
			return this.list.get(index);
		}
		return null;
	}
	/**
	 * 获取模块。
	 * @param id
	 * 	模块Id。
	 * @return 模块。
	 * */
	public ModuleDefine item(String id){
		if(id != null && !id.isEmpty() && this.size() > 0){
			for(int i = 0; i < this.size(); i++){
				ModuleDefine m = this.list.get(i);
				if(m != null && m.getId().equalsIgnoreCase(id)){
					return m;
				}
			}
		}
		return null;
	}
	/**
	 * 获取模块集合大小。
	 * @return 集合大小。
	 * */
	@Override
	public int size() {
		return this.list.size();
	}
	/**
	 * 模块集合是否为空。
	 * @return 是否为空。
	 * */
	@Override
	public boolean isEmpty() {
		return this.list.isEmpty();
	}
	/**
	 * 模块对象是否存在。
	 * @param o
	 * 	模块对象。
	 * @return 是否存在。
	 * */
	@Override
	public boolean contains(Object o) {
		if(o != null && (o instanceof ModuleDefine) && this.list.size() > 0){
			for(ModuleDefine define : this.list){
				if(define == null) continue;
				if(define.getId().equalsIgnoreCase(((ModuleDefine)o).getId())){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 迭代接口。
	 * @return 迭代接口。
	 * */
	@Override
	public Iterator<ModuleDefine> iterator() {
		Collections.sort(this.list, this);
		return this.list.iterator();
	}
	/**
	 * 转化为数组。
	 * @return 数组。
	 * */
	@Override
	public Object[] toArray() {
		Collections.sort(this.list, this);
		return this.list.toArray();
	}
	/**
	 * 转化为数组。
	 * @return 数组。
	 * */
	@Override
	public <T> T[] toArray(T[] a) {
		Collections.sort(this.list,this);
		return this.list.toArray(a);
	}
	/**
	 * 添加模块对象。
	 * @param e
	 * 	模块对象。
	 * @return 是否添加成功。
	 * */
	@Override
	public boolean add(ModuleDefine e) {
		if(e != null && !this.contains(e)){
			return this.list.add(e);
		}
		return false;
	}
	/**
	 * 移除模块对象。
	 * @param o
	 * 	模块对象。
	 * @return 是否移除成功。
	 * */
	@Override
	public boolean remove(Object o) {
		if(o != null){
			return this.list.remove(o);
		}
		return false;
	}
	/**
	 * 集合是否存在。
	 * @param c
	 * 	集合。
	 * @return 是否存在。
	 * */
	@Override
	public boolean containsAll(Collection<?> c) {
		if(c != null && c.size() > 0){
			return this.list.containsAll(c);
		}
		return false;
	}
	/**
	 * 添加集合到集合。
	 * @param c
	 * 	集合。
	 * @return 是否成功。
	 * */
	@Override
	public boolean addAll(Collection<? extends ModuleDefine> c) {
		if(c != null && c.size() > 0){
			return this.list.addAll(c);
		}
		return false;
	}
	/**
	 * 移除集合中的项。
	 * @param c
	 * 	集合。
	 * @return 是否移除。
	 * */
	@Override
	public boolean removeAll(Collection<?> c) {
		if(c != null && c.size() > 0){
			return this.list.removeAll(c);
		}
		return false;
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		if(c != null && c.size() > 0){
			return this.list.retainAll(c);
		}
		return false;
	}
	/**
	 * 移除集合中的元素。
	 * */
	@Override
	public void clear() {
		this.list.clear();
	}
	/**
	 * 排序比较。
	 * @param x
	 * 	模块对象。
	 * @param y
	 * 	模块对象。
	 * @return
	 * 	排序比较值。
	 * */
	@Override
	public int compare(ModuleDefine x, ModuleDefine y) {
		 return x.getOrder() - y.getOrder();
	}
	/*
	 * 对象字符串。
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if(this.list != null && this.list.size() > 0){
			return Arrays.toString(this.list.toArray(new ModuleDefine[0]));
		}
		return super.toString();
	}
}