package com.example.mapper;

import com.example.entity.AddNav;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface NavMapper {

    //添加导航
    @Insert("insert into pre_nav (position_id,title,picture,link_type,link_target,status) " +
            "values (#{positionId},#{titleName},#{picture},#{linkType},#{linkTarget},1)")
    void insertNav(AddNav addNav);

    //获取导航
    @Select("select * from pre_nav where status!=0")
    List<AddNav> getAllNav();

    //删除导航
    @Delete("update pre_nav set status=0 WHERE id =#{id}")
    void deleteNav(long id);

//    @Delete("update pre_nav set status=0 where link_target=#{id}")
//    void deleteNavByProduct(long id);

    //根据商品id查询
    @Select("select * from pre_nav where link_target=#{linkTarget}")
    List<AddNav> getOne(long linkTarget);

    @Update("update pre_nav set position_id=#{positionId},title=#{titleName},link_type=#{linkType} where id=#{id}")
    void updateNav(AddNav addNav);

}
