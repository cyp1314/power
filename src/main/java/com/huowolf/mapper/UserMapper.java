package com.huowolf.mapper;

import com.huowolf.dto.UserTable;
import com.huowolf.model.User;
import com.huowolf.util.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    @Select("select user.*,area.`name` as area,department.`name` as department from user,area,department " +
            "where user.`area_id`=area.`id` and user.`department_id`=department.`id` order by id")
    List<UserTable> findAllUserTable();

    @Select("SELECT user.*,area.`name` AS area,department.`name` AS department FROM USER,AREA,department WHERE user.`area_id`=area.`id` " +
            "AND user.`department_id`=department.`id` AND user.`area_id`=#{areaId} AND user.`department_id`=#{departmentId} order by id")
    List<UserTable> findUserTableByAreaIdAndDepartmentId(@Param("areaId") Integer areaId, @Param("departmentId") Integer departmentId);

    @Select("SELECT user.*,area.`name` AS area,department.`name` AS department FROM USER,AREA,department WHERE user.`area_id`=area.`id` " +
            "AND user.`department_id`=department.`id` AND user.`${search_key}` LIKE concat('%',#{search_content},'%') ORDER BY id")
    List<UserTable> searchUserTable(@Param("search_key") String search_key, @Param("search_content")String search_content);

    @Select("SELECT user.*,area.`name` AS area,department.`name` AS department FROM USER,AREA,department WHERE user.`area_id`=area.`id` " +
            "AND user.`department_id`=department.`id` AND user.`${search_key}` LIKE concat('%',#{search_content},'%') AND user.`area_id`=#{areaId} AND user.`department_id`=#{departmentId} order by id")
    List<UserTable> searchUserTableWithAreaIdAndDepartmentId(
            @Param("search_key") String search_key,
            @Param("search_content")String search_content,
            @Param("areaId") Integer areaId,
            @Param("departmentId") Integer departmentId
    );

    @Select("SELECT user.*,area.`name` AS AREA,department.`name` AS department FROM USER,AREA,department " +
            "WHERE user.`area_id`=area.`id` AND user.`department_id`=department.`id` AND user.`id`= #{id}")
    UserTable findUserTableById(Integer id);
}