package com.test.project.system.richeng.mapper;

import java.util.List;
import com.test.project.system.richeng.domain.TRicheng;

/**
 * 日程管理Mapper接口
 * 
 * @author test
 * @date 2022-04-15
 */
public interface TRichengMapper 
{
    /**
     * 查询日程管理
     * 
     * @param id 日程管理ID
     * @return 日程管理
     */
    public TRicheng selectTRichengById(Long id);

    /**
     * 查询日程管理列表
     * 
     * @param tRicheng 日程管理
     * @return 日程管理集合
     */
    public List<TRicheng> selectTRichengList(TRicheng tRicheng);

    /**
     * 新增日程管理
     * 
     * @param tRicheng 日程管理
     * @return 结果
     */
    public int insertTRicheng(TRicheng tRicheng);

    /**
     * 修改日程管理
     * 
     * @param tRicheng 日程管理
     * @return 结果
     */
    public int updateTRicheng(TRicheng tRicheng);

    /**
     * 删除日程管理
     * 
     * @param id 日程管理ID
     * @return 结果
     */
    public int deleteTRichengById(Long id);

    /**
     * 批量删除日程管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTRichengByIds(String[] ids);
}
