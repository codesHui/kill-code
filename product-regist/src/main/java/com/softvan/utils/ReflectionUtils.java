package com.softvan.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.reflect.*;
import java.util.*;


/**
 * 反射工具类.提供访问私有变量,获取泛型类型Class,
 * 提取集合中元素的属性, 转换字符串到对象等Util函数.
 *
 * @modify by xiayouxue 14-5-8 新增方法"调用Getter方法的集合形式"
 */
public class ReflectionUtils {

    /**
     * log
     */
    private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

    /**
     * 调用Getter方法.
     */
    public static Object invokeGetterMethod(Object obj, String propertyName) {
        String getterMethodName = "get" + StringUtils.capitalize(propertyName);
        return invokeMethod(obj, getterMethodName, new Class[]{}, new Object[]{});
    }

    /**
     * 调用Getter方法的集合形式
     */
    public static <T> Set<T> invokeGetterMethod(Set set, String propertyName) {
        Set<T> values = new HashSet<T>();
        for (Object object : set) {
            values.add((T) invokeGetterMethod(object, propertyName));
        }
        return values;
    }

    /**
     * 调用Getter方法的集合形式
     */
    public static <T> List<T> invokeGetterMethod(List list, String propertyName) {
        List<T> values = new ArrayList<T>();
        for (Object object : list) {
            values.add((T) invokeGetterMethod(object, propertyName));
        }
        return values;
    }

    /**
     * 调用Setter方法.使用value的Class来查找Setter方法.
     */
    public static void invokeSetterMethod(Object obj, String propertyName, Object value) {
        invokeSetterMethod(obj, propertyName, value, null);
    }

    /**
     * 调用Setter方法.
     *
     * @param propertyType 用于查找Setter方法,为空时使用value的Class替代.
     */
    public static void invokeSetterMethod(Object obj, String propertyName, Object value, Class<?> propertyType) {
        Class<?> type = propertyType != null ? propertyType : value.getClass();
        String setterMethodName = "set" + StringUtils.capitalize(propertyName);
        invokeMethod(obj, setterMethodName, new Class[]{type}, new Object[]{value});
    }

    /**
     * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
     */
    public static Object getFieldValue(final Object obj, final String fieldName) {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
        }

        Object result = null;
        try {
            result = field.get(obj);
        } catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常", e);
        }
        return result;
    }

    /**
     * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
     */
    public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
        }

        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常:{}", e);
        }
    }

    /**
     * 循环向上转型, 获取对象的DeclaredField,	 并强制设置为可访问.
     * <p/>
     * 如向上转型到Object仍无法找到, 返回null.
     */
    public static Field getAccessibleField(final Object obj, final String fieldName) {
        Assert.notNull(obj, "object不能为空");
        Assert.hasText(fieldName, "fieldName");
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {//NOSONAR
                // Field不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    /**
     * 直接调用对象方法, 无视private/protected修饰符.
     * 用于一次性调用的情况.
     */
    public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,
                                      final Object[] args) {
        Method method = getAccessibleMethod(obj, methodName, parameterTypes);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
        }

        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问.
     * 如向上转型到Object仍无法找到, 返回null.
     * <p/>
     * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
     */
    public static Method getAccessibleMethod(final Object obj, final String methodName,
                                             final Class<?>... parameterTypes) {
        Assert.notNull(obj, "object不能为空");

        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Method method = superClass.getDeclaredMethod(methodName, parameterTypes);

                method.setAccessible(true);

                return method;

            } catch (NoSuchMethodException e) {//NOSONAR
                // Method不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    /**
     * 通过反射, 获得Class定义中声明的父类的泛型参数的类型.
     * 如无法找到, 返回Object.class.
     * eg.
     * public UserDao extends HibernateDao<User>
     *
     * @param clazz The class to introspect
     * @return the first generic declaration, or Object.class if cannot be determined
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射, 获得Class定义中声明的父类的泛型参数的类型.
     * 如无法找到, 返回Object.class.
     * <p/>
     * 如public UserDao extends HibernateDao<User,Long>
     *
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     * @return the index generic declaration, or Object.class if cannot be determined
     */
    @SuppressWarnings("unchecked")
    public static Class getSuperClassGenricType(final Class clazz, final int index) {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
                    + params.length);
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
            return Object.class;
        }

        return (Class) params[index];
    }

    /**
     * 将反射时的checked exception转换为unchecked exception.
     */
    public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
        if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
                || e instanceof NoSuchMethodException) {
            return new IllegalArgumentException("Reflection Exception.", e);
        } else if (e instanceof InvocationTargetException) {
            return new RuntimeException("Reflection Exception.", ((InvocationTargetException) e).getTargetException());
        } else if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException("Unexpected Checked Exception.", e);
    }

    /**
     * 将集合中的对象以某个属性值转换成map
     */
    public static <K, V> Map<K, V> toMap(Collection<V> collection, String propertyName) {
        Map<K, V> map = new HashMap<K, V>();
        for (V t : collection) {
            map.put((K) invokeGetterMethod(t, propertyName), t);
        }
        return map;
    }

    /**
     * 在集合中查找字段名称和字段值匹配的元素
     */
    public static <T> T findNameValueMatched(List<T> list, String fieldName, Object fieldValue) {
        for (T t : list) {
            if (fieldValue.equals(invokeGetterMethod(t, fieldName))) {
                return t;
            }
        }
        return null;
    }

    /**
     * 在集合中查找字段名称和字段值匹配的元素
     */
    public static <T> List<T> findNameValueMatcheds(List<T> list, String fieldName, Object fieldValue) {
        List<T> matched = new ArrayList<T>();
        for (T t : list) {
            if (fieldValue.equals(invokeGetterMethod(t, fieldName))) {
                matched.add(t);
            }
        }
        return matched;
    }

    /**
     * 在集合中查找字段名称和字段值匹配的元素
     */
    public static <T> List<T> findNameValueMatched(List<T> list, String fieldName, List fieldValues) {
        List<T> targetList = new ArrayList<T>();
        for (int i = 0; i < fieldValues.size(); i++) {
            T t = findNameValueMatched(list, fieldName, fieldValues.get(i));
            if (t != null) {
                targetList.add(t);
            }
        }
        return targetList;
    }


    /**
     * 获取层级字段值
     */
    public static Object getTierFieldValue(Object object, String fieldName) {
        String[] tierFieldNames = fieldName.split("\\.");
        Object current = object;
        String tierFieldName;
        try {
            for (int i = 0; i < tierFieldNames.length; i++) {
                if (current == null) {
                    return null;
                }
                tierFieldName = tierFieldNames[i];
                Method method = current.getClass().getMethod("get" + StringUtils.capitalize(tierFieldName));
                current = method.invoke(current);
            }
            return current;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(String.format("获取层级字段%s异常", fieldName), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(String.format("获取层级字段%s异常", fieldName), e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(String.format("获取层级字段%s异常", fieldName), e);
        }
    }

    /** 获取实体类字段类型 */
    public static Class getEntityFieldType(Field field) {
        Class<?> fieldType = field.getType();
        if (fieldType.isAssignableFrom(Collection.class)) {
            ParameterizedType pt = (ParameterizedType) field.getGenericType();
            fieldType = (Class) pt.getActualTypeArguments()[0];
        }
        return fieldType;
    }
}
