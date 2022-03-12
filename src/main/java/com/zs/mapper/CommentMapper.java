package com.zs.mapper;

import com.zs.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Created by zs on 2022/3/11.
 */
@Mapper
public interface CommentMapper {

    @Transactional
    int insert(@Param("comment") Comment comment);

    /**
     * 根据 com_parent_id == -1 and bid = bid 查询
     * @param bid
     * @return
     */
    List<Comment> listCommentsByBid(@Param("bid") Long bid);

    /**
     * 根据 com_id 查询
     * @param comId
     * @return
     */
    List<Comment> listCommentsByComId(@Param("comId") Long comId);


}
