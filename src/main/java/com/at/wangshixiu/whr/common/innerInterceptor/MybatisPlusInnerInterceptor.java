//package com.at.wangshixiu.whr.common.innerInterceptor;
//
//import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
//import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
//import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
//
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.session.ResultHandler;
//import org.apache.ibatis.session.RowBounds;
//
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Map;
//
//import lombok.SneakyThrows;
//
///**
// * @Author 王世秀
// * @Date 2023/2/16
// * @Description 对sql进行权限查询处理
// */
//public class MybatisPlusInnerInterceptor extends JsqlParserSupport implements InnerInterceptor {
//
//
//    //@Override
//    //protected void processSelect(Select select, int index, String sql, Object obj) {
//    //    SelectBody selectBody = select.getSelectBody();
//    //    if (selectBody instanceof PlainSelect) {
//    //        this.setWhere((PlainSelect) selectBody, (String) obj);
//    //    } else if (selectBody instanceof SetOperationList) {
//    //        SetOperationList setOperationList = (SetOperationList) selectBody;
//    //        List<SelectBody> selectBodyList = setOperationList.getSelects();
//    //        selectBodyList.forEach(s -> this.setWhere((PlainSelect) s, (String) obj));
//    //    }
//    //}
//
//
//    @Override
//    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
//        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
//        mpBs.sql(this.parserSingle(mpBs.sql(), ms.getId()));
//    }
//
//    /**
//     * @param className 实体类对象的class
//     * @return
//     */
//    @SneakyThrows
//    public Map<String, Object> filterData(String className) {
//        Class<?> aClass = Class.forName(className);
//        HashMap<String, Object> objectObjectHashMap = new HashMap<>(16);
//
//        return objectObjectHashMap;
//    }
//
//    public String getClassName(MappedStatement ms) {
//        String replace = ms.getId().replace("Mapper", "")
//                .replace("mapper", "entity");
//        return replace.substring(0, replace.lastIndexOf("."));
//    }
//}
