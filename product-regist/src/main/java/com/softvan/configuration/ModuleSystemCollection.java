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
 * 模块系统集合。
 * */
public class ModuleSystemCollection implements Collection<ModuleSystem>,Comparator<ModuleSystem>, Serializable {
	private static final long serialVersionUID = 1L;
	private List<ModuleSystem> systems;
	/**
	 * 构造函数。
	 * */
	public ModuleSystemCollection(){
		this.systems = new ArrayList<ModuleSystem>();
	}
	/**
	 * 获取模块系统。
	 * @param id
	 * 	系统Id。
	 * @return 模块系统对象。
	 * */
	public ModuleSystem item(String id){
		if(id != null && !id.trim().isEmpty() && this.size() > 0){
			for(int i = 0; i < this.size(); i++){
				ModuleSystem ms = this.systems.get(i);
				if(ms != null && ms.getId().equalsIgnoreCase(id)){
					return ms;
				}
			}
		}
		return null;
	}
	/**
	 * 获取模块系统集合数量。
	 * @return 集合数量。
	 * */
	@Override
	public int size() {
		return this.systems.size();
	}
	/**
	 * 集合是否为空。
	 * @return 是否为空。
	 * */
	@Override
	public boolean isEmpty() {
		return this.systems.isEmpty();
	}
	/**
	 * 
	 * */
	@Override
	public boolean contains(Object o) {
		if(o == null || this.systems.size() == 0) return false;
		if(o instanceof ModuleSystem){
			for(ModuleSystem ms : this.systems){
				if(ms == null) continue;
				if(ms.getId().equalsIgnoreCase(((ModuleSystem)o).getId())){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 
	 * */
	@Override
	public Iterator<ModuleSystem> iterator() {
		Collections.sort(this.systems, this);
		return this.systems.iterator();
	}
	/**
	 * 
	 * */
	@Override
	public Object[] toArray() {
		Collections.sort(this.systems, this);
		return this.systems.toArray();
	}
	/**
	 * 
	 * */
	@Override
	public <T> T[] toArray(T[] a) {
		Collections.sort(this.systems, this);
		return this.systems.toArray(a);
	}
	/**
	 * 添加模块系统对象。
	 * @param e
	 * 	模块系统对象。
	 * @return 是否成功。
	 * */
	@Override
	public boolean add(ModuleSystem e) {
		if(e == null) return false;
		if(!this.contains(e)){
			return this.systems.add(e);
		}
		return false;
	}
	/**
	 * 移除模块系统对象。
	 * @param o
	 * 	模块系统对象。
	 * */
	@Override
	public boolean remove(Object o) {
		 if(o== null) return false;
		 return this.systems.remove(o);
	}
	/**
	 * 集合对象是否存在。
	 * @param c
	 * 	集合。
	 * @return 是否存在。
	 * */
	@Override
	public boolean containsAll(Collection<?> c) {
		if(c == null || c.size() == 0) return false;
		return this.systems.containsAll(c);
	}
	/**
	 * 添加模块系统集合。
	 * @param c
	 * 	模块系统集合。
	 * @return 添加是否成功。
	 * */
	@Override
	public boolean addAll(Collection<? extends ModuleSystem> c) {
		if(c == null || c.size() == 0) return false;
		return this.systems.addAll(c);
	}
	/**
	 * 移除集合中的对象。
	 * @param c
	 * 	集合对象。
	 * @return 移除是否成功。
	 * */
	@Override
	public boolean removeAll(Collection<?> c) {
		if(c == null || c.size() == 0) return false;
		return this.systems.removeAll(c);
	}
	/**
	 * 
	 * */
	@Override
	public boolean retainAll(Collection<?> c) {
		 if(c == null || c.size() == 0) return false;
		 return this.systems.retainAll(c);
	}
	/**
	 * 清除集合。
	 * */
	@Override
	public void clear() {
		this.systems.clear();
	}
	/**
	 * 排序比较。
	 * */
	@Override
	public int compare(ModuleSystem x, ModuleSystem y) {
		int result = x.getName().compareToIgnoreCase(y.getName());
		if(result == 0){
			result= x.getSign().compareToIgnoreCase(y.getSign());
		}
		return result;
	}
	/*
	 * 对象字符串。
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if(this.systems != null && this.systems.size() > 0){
			return Arrays.toString(this.systems.toArray(new ModuleSystem[0]));
		}
		return super.toString();
	}
	
}