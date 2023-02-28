package com.at.wangshixiu.whr.common;

import com.at.wangshixiu.whr.common.util.MyStringUtils;
import com.at.wangshixiu.whr.entity.sys.SysUser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author 王世秀
 * @Date 2022/12/24
 * @Description QueryWrapper条件构建类
 */

@Slf4j
public class GeneratorQueryWrapper {
    public static void main(String[] args) {
        SysUser sysUser = new SysUser();
        sysUser.setUsername("admin");
        sysUser.setRealName("王世秀");
        sysUser.setPassword("123456");
        sysUser.setCreateBy("测试");
        //QueryWrapper<SysUser> queryWrapper = initQueryWrapper(sysUser);

    }

    /**
     * 初始化 MybatisPlus的查询条件
     *
     * @param searchObj 目标对象
     * @param <T> 泛型对象
     * @return 构造好的条件查询器
     */
    public static <T> QueryWrapper<T> initQueryWrapper(T searchObj, HttpServletRequest request) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        installPlus(queryWrapper, searchObj, request);
        return queryWrapper;
    }

    /**
     * 构造MybatisPlus的查询条件器
     *
     * @param queryWrapper 查询条件构造器
     * @param searchObj 目标对象
     * @param <T> 泛型对象
     */
    private static <T> void installPlus(QueryWrapper<T> queryWrapper, T searchObj, HttpServletRequest request) {
        Map<String, QueryEntity> objIsNotNullField = getObjIsNotNullField(searchObj, request.getParameterMap());
        for (String entityField : objIsNotNullField.keySet()) {
            QueryEntity queryEntity = objIsNotNullField.get(entityField);
            Object columnValue = queryEntity.getColumnValue();
            if (columnValue instanceof String) {
                handleFieldIsString(queryWrapper, columnValue, queryEntity);
            } else if (columnValue instanceof Date) {
                Date value = (Date) columnValue;
            }
            //if ("createTime".equals(field)) {
            //    queryWrapper.gt("create_time", objIsNotNullField.get("createTime"));
            //} else {
            //    //TODO 高级查询还要处理
            //    queryWrapper.like(field, objIsNotNullField.get(field));
            //}
        }
        // TODO 该用户的数据规则

        //queryWrapper.orderByDesc("create_time");
    }

    /**
     * *
     *
     * @param queryWrapper
     * @param columnValue
     * @param queryEntity
     * @param <T>
     */
    private static <T> void handleFieldIsString(QueryWrapper<T> queryWrapper, Object columnValue, QueryEntity queryEntity) {
        String value = (String) columnValue;
        boolean flag = value.startsWith("%") || value.endsWith("%") || (value.startsWith("%") && value.endsWith("%"));
        if (flag) {
            queryWrapper.like(queryEntity.getColumn(), value);
        } else {
            queryWrapper.eq(queryEntity.getColumn(), value);
        }
    }


    /**
     * 获取对象不为null值的属性 + 请求参数
     *
     * @param searchObj 目标对象
     * @return map  key带有下划线的属性名称    value对应的数据
     */
    @SneakyThrows
    private static <T> Map<String, QueryEntity> getObjIsNotNullField(T searchObj, Map<String, String[]> parameterMap) {
        // 保存结果信息  key  实体类字段   value 描述信息
        Map<String, QueryEntity> map = new HashMap<>(16);
        // 获取实体类对象属性
        getTargetObjNotNullField(searchObj.getClass(), map);
        // 获取request 中的排序 和排序字段
        getParameterMap(parameterMap, map);

        return map;
    }

    /**
     * 获取目标对象的属性
     *
     * @param searchObj 目标对象
     * @param map 保存
     */
    @SneakyThrows
    private static <T> void getTargetObjNotNullField(T searchObj, Map<String, QueryEntity> map) {
        Class<?> objClass = searchObj.getClass();
        Map<String, Method> objAllGetMethod = getObjAllGetMethod(objClass);
        for (Field declaredField : objClass.getDeclaredFields()) {
            String fieldName = declaredField.getName();
            Class<?> type = declaredField.getType();
            Object resultData = objAllGetMethod.get(fieldName).invoke(searchObj);
            if (null != resultData) {
                QueryEntity queryEntity = new QueryEntity(MyStringUtils.humpToUnderline(fieldName), type, resultData);
                map.put(fieldName, queryEntity);
            }
        }

    }

    /**
     * 向map中添加request中的参数
     * @param parameterMap request中的参数
     * @param map 保存
     */
    private static void getParameterMap(Map<String, String[]> parameterMap, Map<String, QueryEntity> map) {
        Optional<String[]>  optionalStrings= Optional.ofNullable(parameterMap.get("order"));
        String[] orders = parameterMap.get("order");
        if (null != orders){
            String order = parameterMap.get("order")[0];
            map.put("order", new QueryEntity("order", String.class, order));
        }

        String orderColumn = parameterMap.get("orderColumn")[0];

        map.put("orderColumn", new QueryEntity("orderColumn", String.class, orderColumn));

        for (String s : parameterMap.keySet()) {
            if (s.contains("_start")) {
                String start = s.replace("_start", "");
                map.put(s, new QueryEntity(MyStringUtils.humpToUnderline(start), Date.class, parameterMap.get(s)[0]));
            }else if (s.contains("_end")){
                String start = s.replace("_end", "");
                map.put(s, new QueryEntity(MyStringUtils.humpToUnderline(start), Date.class, parameterMap.get(s)[0]));
            }
        }
    }

    /**
     * 获取目标对象的所有get方法
     *
     * @param objClass 目标对象的class对象
     * @return map  key属性名  value 属性名对应的get方法
     */
    private static <T> Map<String, Method> getObjAllGetMethod(Class<?> objClass) {
        Map<String, Method> map = new HashMap<>(16);
        for (Method declaredMethod : objClass.getDeclaredMethods()) {
            String name = declaredMethod.getName();
            if (name.startsWith("get")) {
                //方法名称转成属性名
                String fieldName = MyStringUtils.toLowerCaseFirstOne(name.substring(3));
                map.put(fieldName, declaredMethod);
            }
        }
        return map;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class QueryEntity {
        private String column;  //数据库对应字段
        private Class columnType; //字段类型
        private Object columnValue; //字段值
        private boolean isOrder;  //是否是排序字段
        private boolean isRange; //是否是范围查询

        public QueryEntity(String column, Class columnType, Object columnValue) {
            this.column = column;
            this.columnType = columnType;
            this.columnValue = columnValue;
        }
    }
}
